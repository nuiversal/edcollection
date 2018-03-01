package com.winway.android.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * GPS帮助类
 * qiu
 */
public class GpsUtil {
	
	/**
	 * 判断GPS是否打开
	 * @param activity
	 * @return
	 */
	public static boolean isGPSOpen(Activity activity) {
		LocationManager alm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			System.out.println("GPS模块正常");
            return true;
        } else {
        	return false;
        }
	}
	/**
	 * 如果GPS没启动则打开GPS设置界面
	 * @param activity
	 */
	public static void openGPSSettings(Activity activity) {
        if (!isGPSOpen(activity)) {
        	Toast.makeText(activity, "请开启GPS！", Toast.LENGTH_SHORT).show();
        	//有的版本是：Settings.ACTION_SECURITY_SETTINGS，以后再细分
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            //此为设置完成后返回到获取界面
            activity.startActivityForResult(intent,0); 
        }
    }
	
	/**
	 * 获取当前位置
	 * @param activity
	 * @return
	 */
	public static Location getLocation(Activity activity) {
		// 获取位置管理服务
		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) activity
				.getSystemService(serviceName);
		// 查找到服务信息
		Criteria criteria = new Criteria();
		// 高精度
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		// 低功耗
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 获取GPS信息
		String provider = locationManager.getBestProvider(criteria, true);
		
		// 通过GPS获取位置
		if (provider == null) {
			return null;
		}
		Location location = locationManager.getLastKnownLocation(provider);
		locationManager.requestLocationUpdates(provider, 3000, 5,
				locationListener);
		return location;
	}
	
	private final static LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

	};
	
	/**
	 * 设置经纬度
	 * @param compontView 经纬度的两个控件数组
	 * @param activity
	 */
	public static void setLocation(View compontView[],Activity activity){
		if(compontView!=null && compontView.length == 2){
			EditText jindDu =  (EditText) compontView[0];
			EditText weiDu =  (EditText) compontView[1];
			
			// 三位小数的formater
			NumberFormat numberFormat=NumberFormat.getInstance();
			numberFormat.setMaximumFractionDigits(10);
			if (!GpsUtil.isGPSOpen(activity)) {
				GpsUtil.openGPSSettings(activity);
				return;
			}
			Location location = GpsUtil.getLocation(activity);
			if (location != null) {
				// 经度
				String longitude = numberFormat.format(location.getLongitude());
				// 纬度
				String latitude = numberFormat.format(location.getLatitude());
				System.out.println("经度：" + longitude + ", 纬度：" + latitude);
				Toast.makeText(activity, "经度：" + longitude + ", 纬度：" + latitude, Toast.LENGTH_SHORT).show();
				jindDu.setText(longitude);
				weiDu.setText(latitude);
			} else {
				Toast.makeText(activity, "获取当前位置异常，请检查GPS信号。", Toast.LENGTH_LONG).show();
			}
		}
	}
	/**
	 * 设置经纬度（同一个editText中）
	 * @param compontView
	 * @param activity
	 */
	public static void  setLocation(View compontView,Activity activity) {
		if(compontView!=null ){
			EditText gps =  (EditText) compontView;
		
		//	EditText weiDu =  (EditText) compontView[1];
			
			// 三位小数的formater
			NumberFormat numberFormat=NumberFormat.getInstance();
			numberFormat.setMaximumFractionDigits(10);
			if (!GpsUtil.isGPSOpen(activity)) {
				GpsUtil.openGPSSettings(activity);
				return;
			}
			Location location = GpsUtil.getLocation(activity);
			List numberList = new ArrayList();
			
			String longitude = null;
			String latitude = null;
			if (location != null) {
				for (int i = 0; i <10; i++) {
					// 经度
					 longitude = numberFormat.format(location.getLongitude());
					  latitude = numberFormat.format(location.getLatitude());
					  Log.i("tag", "经度：" + longitude + "纬度：" + latitude);
				}
				 numberList.add(location);
				//numberList.add(latitude);
				
				double sum = 0.0;
				for (int i = 0; i < numberList.size(); i++) {
					double number = (Double) numberList.get(i);
					sum += number;
				}
				System.out.println("平均数为："+sum/numberList.size());
				
			double svgNum = sum/numberList.size();
					
			Log.i("svg", svgNum+"");
			Log.i("sum", sum/numberList.size()+"");
				 gps.setText(longitude + ";" + latitude);
				 
				 Toast.makeText(activity, "经度：" + svgNum+""+sum/numberList.size()+"" , Toast.LENGTH_SHORT).show();
			//	jindDu.setText(longitude);
			//	weiDu.setText(latitude);
			//	int i = 10 * Integer.parseInt(longitude);
				// 纬度
			//	String latitude = numberFormat.format(location.getLatitude());
			//	System.out.println("经度：" + longitude );
			//	Toast.makeText(activity, "经度：" + longitude , Toast.LENGTH_SHORT).show();
			//	jindDu.setText(longitude);
		//		weiDu.setText(latitude);
			} else {
				Toast.makeText(activity, "获取当前位置异常，请检查GPS信号。", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	/**
	 * 请求GPS位置
	 * @param context
	 * @param listener
	 */
	public static Location requstLocaltion(Context context, LocationListener listener) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		// 高精度
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		// 低功耗
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 获取GPS信息
		String provider = locationManager.getBestProvider(criteria, true);
		// 通过GPS获取位置
		locationManager.requestLocationUpdates(provider, 3000, 5, listener);
		Location location = locationManager.getLastKnownLocation(provider);
		return location;
	}
}
