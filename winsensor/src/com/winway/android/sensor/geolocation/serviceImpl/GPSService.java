package com.winway.android.sensor.geolocation.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.winway.android.util.LogUtil;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;

/**
 * 定位服务
 * 
 * @author xs
 *
 */
public class GPSService extends Service {

	private LocationManager locationManager;
	private Criteria mCriteria;
	private LocationListener locationListener;
	private String locationProvider;
	private GpsStatus.Listener gpsStatusListener;

	private SensorManager sensorManager;
	private Sensor mOrientationSensor;// 方向
	private Sensor mPressureSensor;// 气压
	private SensorEventListener sensorEventListener;

	private int lastDegree;
	private static Location lastLocation;

	private static ArrayList<GPSChangeListener> listeners;
	private MyBinder binder = new MyBinder();

	@Override
	public void onCreate() {
		super.onCreate();
		initGPSConfig();
		loadSensorConfig();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	/**
	 * 添加监听
	 * 
	 * @param listener
	 */
	public static void addListener(GPSChangeListener listener) {
		if (listener != null && lastLocation != null) {
			listener.onChanged(lastLocation);
		}
		if (listeners == null) {
			listeners = new ArrayList<>();
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * 移除监听
	 * 
	 * @param listener
	 */
	public static void removeListener(GPSChangeListener listener) {
		if (listeners != null && listener != null) {
			if (listeners.contains(listener)) {
				listeners.remove(listener);
			}
		}
	}

	/**
	 * 初始化
	 */
	public void initGPSConfig() {
		LogUtil.d("gps", "定位服务已启动");
		destroy();
		if (locationManager == null) {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		}
		if (mCriteria == null) {
			mCriteria = new Criteria();
			mCriteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精度
			mCriteria.setAltitudeRequired(true);// 海拔
			mCriteria.setBearingRequired(true);// 方位
			mCriteria.setCostAllowed(true);// GSM自费
			mCriteria.setPowerRequirement(Criteria.POWER_LOW);// 低电量
		}
		if (TextUtils.isEmpty(locationProvider)) {
			// locationProvider = locationManager.getBestProvider(mCriteria,
			// true);
			locationProvider = LocationManager.GPS_PROVIDER;
		}
		if (gpsStatusListener == null) {
			gpsStatusListener = new GpsStatus.Listener() {
				public void onGpsStatusChanged(int event) {
					switch (event) {
					// 第一次定位
					case GpsStatus.GPS_EVENT_FIRST_FIX:
						break;
					// 卫星状态改变
					case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
						// 获取当前状态
						GpsStatus gpsStatus = locationManager.getGpsStatus(null);
						// 获取卫星颗数的默认最大值
						int maxSatellites = gpsStatus.getMaxSatellites();
						// 创建一个迭代器保存所有卫星
						Iterator<GpsSatellite> itrator = gpsStatus.getSatellites().iterator();
						int count = 0;
						List<GpsSatellite> satelliteList = new ArrayList<GpsSatellite>();
						while (itrator.hasNext() && count <= maxSatellites) {
							GpsSatellite satellite = itrator.next();
							satelliteList.add(satellite);
							count++;
						}
						for (GPSChangeListener listener : listeners) {
							listener.onGPSCount(count);
						}
						// 输出卫星信息
						// for (int i = 0; i < satelliteList.size(); i++) {
						// // 卫星的方位角，浮点型数据,0-360
						// Log.i(TAG, satelliteList.get(i).getAzimuth() + "");
						// // 卫星的高度角，浮点型数据0-90
						// Log.i(TAG, satelliteList.get(i).getElevation() + "");
						// // 卫星的伪随机噪声码，整形数据
						// Log.i(TAG, satelliteList.get(i).getPrn() + "");
						// // 卫星的信噪比，浮点型数据，越大信号越好
						// Log.i(TAG, satelliteList.get(i).getSnr() + "");
						// // 卫星是否有年历表，布尔型数据
						// Log.i(TAG, satelliteList.get(i).hasAlmanac() + "");
						// // 卫星是否有星历表，布尔型数据
						// Log.i(TAG, satelliteList.get(i).hasEphemeris() + "");
						// // 卫星是否被用于近期的GPS修正计算
						// Log.i(TAG, satelliteList.get(i).hasAlmanac() + "");
						// }
						break;
					// 定位启动
					case GpsStatus.GPS_EVENT_STARTED:
						for (GPSChangeListener listener : listeners) {
							listener.onStart();
						}
						break;
					// 定位结束
					case GpsStatus.GPS_EVENT_STOPPED:
						break;
					}
				};
			};
		}
		if (locationListener == null) {
			locationListener = new LocationListener() {
				@Override
				public void onStatusChanged(String provider, int status, Bundle bundle) {
					switch (status) {
					// GPS状态为可见时
					case LocationProvider.AVAILABLE:
						break;
					// GPS状态为服务区外时
					case LocationProvider.OUT_OF_SERVICE:
						break;
					// GPS状态为暂停服务时
					case LocationProvider.TEMPORARILY_UNAVAILABLE:
						break;
					}
				}

				@Override
				public void onProviderEnabled(String arg0) {
					// GPS打开时
					for (GPSChangeListener listener : listeners) {
						listener.onEnabled();
					}
				}

				@Override
				public void onProviderDisabled(String arg0) {
					// GPS关闭时
					for (GPSChangeListener listener : listeners) {
						listener.onDisabled();
					}
				}

				@Override
				public void onLocationChanged(Location location) {
					// GPS位置改变时
					getLocation(location);
				}
			};
		}
		loadGPSConfig();
	}

	/**
	 * 加载GPS配置
	 */
	private void loadGPSConfig() {
		locationManager.addGpsStatusListener(gpsStatusListener);
		locationManager.requestLocationUpdates(locationProvider, 1, 1, locationListener);
		Location location = locationManager.getLastKnownLocation(locationProvider);
		getLocation(location);
	}

	/**
	 * 获取定位信息
	 */
	private void getLocation(Location location) {
		if (location != null) {
			lastLocation = location;
			for (GPSChangeListener listener : listeners) {
				listener.onChanged(location);
			}
		}
	}

	/**
	 * 加载传感器配置
	 */
	@SuppressWarnings("deprecation")
	private void loadSensorConfig() {
		if (sensorManager == null) {
			sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		}
		if (mOrientationSensor == null) {
			mOrientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		}
		if (mPressureSensor == null) {
			mPressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
		}
		if (sensorEventListener == null) {
			sensorEventListener = new SensorEventListener() {
				@Override
				public void onSensorChanged(SensorEvent event) {
					if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
						int degree = Math.abs(Math.round(event.values[0]));
						if (Math.abs(degree - lastDegree) > 1.0f) {
							lastDegree = degree;
							for (GPSChangeListener listener : listeners) {
								listener.onOrientation(lastDegree);
							}
						}
					}
					if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
					}
				}

				@Override
				public void onAccuracyChanged(Sensor arg0, int arg1) {

				}
			};
			sensorManager.registerListener(sensorEventListener, mOrientationSensor, SensorManager.SENSOR_DELAY_GAME);
			sensorManager.registerListener(sensorEventListener, mPressureSensor, SensorManager.SENSOR_DELAY_GAME);
		}
	}

	/**
	 * 销毁
	 */
	private void destroy() {
		if (locationManager != null) {
			if (gpsStatusListener != null) {
				locationManager.removeGpsStatusListener(gpsStatusListener);
			}
		}
		if (locationManager != null) {
			if (locationListener != null) {
				locationManager.removeUpdates(locationListener);
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		destroy();
	}

	public interface GPSChangeListener {

		/**
		 * GPS关闭
		 */
		void onEnabled();

		/**
		 * GPS卫星数量
		 */
		void onGPSCount(int count);

		/**
		 * GPS打开
		 */
		void onDisabled();

		/**
		 * 开始定位
		 */
		void onStart();

		/**
		 * 位置改变
		 * 
		 * @param location
		 */
		void onChanged(Location location);

		/**
		 * 手机旋转角度
		 * 
		 * @param degree
		 */
		void onOrientation(int degree);
	}

	public class MyBinder extends Binder {
		public GPSService getService() {
			return GPSService.this;
		}
	}
}
