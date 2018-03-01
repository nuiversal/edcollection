package com.winway.android.sensor.geolocation;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

/**
 * GPS配置参数
 * 
 * @author ly
 *
 */
public class GPSconfig {

	private Context mContext;
	private LocationManager lm;
	private static final String TAG = "GPSconfig";
	private static GPSconfig instance;

	public static GPSconfig getInstance(Context context) {
		if (instance == null) {
			synchronized (GPSconfig.class) {
				if (instance == null) {
					instance = new GPSconfig(context);
				}
			}
		}
		return instance;
	}

	private GPSconfig() {
	}

	private GPSconfig(Context context) {
		this.mContext = context;
		// map=new HashMap<String,Object>();
	}

	public void loadConfig() {
		Log.w(TAG, "loadConfig");
		// 监听状态
		lm.addGpsStatusListener(listener);
		// 绑定监听，有4个参数
		// 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
		// 参数2，位置信息更新周期，单位毫秒
		// 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
		// 参数4，监听
		// 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

		// 1秒更新一次，或最小位移变化超过1米更新一次；
		// 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
	}

	/**
	 * 开启GPS
	 * 
	 * @return
	 */
	public boolean open() {
		// 判断GPS是否正常启动
		lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// 返回开启GPS导航设置界面
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			((Activity) mContext).startActivityForResult(intent, 0);
		}
		// 为获取地理位置信息时设置查询条件
		String bestProvider = lm.getBestProvider(getCriteria(), true);
		// 获取位置信息
		// 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
		// Location location = lm.getLastKnownLocation(bestProvider);
		Location location = getBestLocation(lm);
		// MainActivity.updateView(location);
		GPS.nowLoaction = location;
		if (location != null) {
			return true;
		}
		return false;
	}

	/**
	 * 关闭GPS
	 */
	public void close() {
		if (lm != null) {
			if (listener != null) {
				lm.removeGpsStatusListener(null);
			}
			if (locationListener != null) {
				lm.removeUpdates(locationListener);
			}
		}
	}

	// 位置监听
	private LocationListener locationListener = new LocationListener() {
		// 位置信息变化时触发
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			// GPS数据改变保存数据
			// UploadTrack.getInstance(mContext).dealTrack(location);
			GPS.nowLoaction = location;
			// MainActivity.updateView(location);
		}

		// GPS状态变化时触发
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		// GPS开启时触发
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Location location = lm.getLastKnownLocation(provider);
			GPS.nowLoaction = location;
			// UploadTrack.getInstance(mContext).dealTrack(location);
			// MainActivity.updateView(location);
		}

		// GPS禁用时触发
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onProviderDisabled");
		}
	};

	/**
	 * 返回查询条件
	 * 
	 * @return
	 */
	private Criteria getCriteria() {
		Criteria criteria = new Criteria();
		// 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 设置是否要求速度
		criteria.setSpeedRequired(false);
		// 设置是否允许运营商收费
		criteria.setCostAllowed(false);
		// 设置是否需要方位信息
		criteria.setBearingRequired(false);
		// 设置是否需要海拔信息
		criteria.setAltitudeRequired(false);
		// 设置对电源的需求
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}

	// 获取
	private Location getBestLocation(LocationManager locationManager) {
		Location result = null;
		if (locationManager != null) {
			result = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (result != null) {
				return result;
			} else {
				result = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				return result;
			}
		}
		return result;
	}

	// GPS状态监听
	GpsStatus.Listener listener = new GpsStatus.Listener() {
		public void onGpsStatusChanged(int event) {
			switch (event) {
			// 第一次定位
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				// Log.i(TAG, "第一次定位");
				break;
			// 卫星状态改变
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				// Log.i(TAG, "卫星状态改变");
				// // 获取当前状态
				GpsStatus gpsStatus = lm.getGpsStatus(null);
				// 获取卫星
				Iterable<GpsSatellite> iterable = gpsStatus.getSatellites();
				// 再次转换成Iterator
				Iterator<GpsSatellite> itrator = iterable.iterator();
				// 通过遍历重新整理为ArrayList
				ArrayList<GpsSatellite> satelliteList = new ArrayList<GpsSatellite>();
				int count = 0;
				int maxSatellites = gpsStatus.getMaxSatellites();
				while (itrator.hasNext() && count <= maxSatellites) {
					GpsSatellite satellite = itrator.next();
					satelliteList.add(satellite);
					count++;
				}
				// 输出卫星信息
				for (int i = 0; i < satelliteList.size(); i++) {
					// // 卫星的方位角，浮点型数据
					// Log.w("GpsActivity", "卫星的方位角" +
					// satelliteList.get(i).getAzimuth() + "");
					// // 卫星的高度，浮点型数据
					// Log.w("GpsActivity", "卫星的高度" +
					// satelliteList.get(i).getElevation() + "");
					// // 卫星的伪随机噪声码，整形数据
					// Log.w("GpsActivity", "卫星的伪随机噪声码" +
					// satelliteList.get(i).getPrn() + "");
					// // 卫星的信噪比，浮点型数据
					// Log.w("GpsActivity", "卫星的信噪比" +
					// satelliteList.get(i).getSnr() + "");
					// // 卫星是否有年历表，布尔型数据
					// Log.w("GpsActivity", "卫星是否有年历表" +
					// satelliteList.get(i).hasAlmanac() + "");
					// // 卫星是否有星历表，布尔型数据
					// Log.w("GpsActivity", "卫星是否有星历表" +
					// satelliteList.get(i).hasEphemeris() + "");
					// // 卫星是否被用于近期的GPS修正计算
					// //
					// Log.w("GpsActivity","卫星是否被用于近期的GPS修正计算"+satelliteList.get(i).hasAlmanac()+"");
					// satelliteList.get(i).usedInFix();
				}
				break;
			// 定位启动
			case GpsStatus.GPS_EVENT_STARTED:
				Log.w(TAG, "定位启动");
				break;
			// 定位结束
			case GpsStatus.GPS_EVENT_STOPPED:
				Log.w(TAG, "定位结束");
				break;
			}
		};
	};

	/**
	 * 判断是否有网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null != connectivity) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
}
