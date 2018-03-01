package com.winway.android.sensor.geolocation.serviceImpl;

import com.winway.android.sensor.geolocation.Geolocation;
import com.winway.android.util.ToastUtil;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.provider.Settings;

/**
 * GPS
 * 
 * @author xs
 *
 */
/**
 * @author in
 *
 */
public class GPS implements Geolocation {
	/**
	 * 单例
	 */
	private static GPS instance;

	private Context context;// 上下文
	private LocationManager locationManager;// 位置管理器
	private GPSLocationChangeListener gpsLocationChangeListener;
	private GPSPositionListener onGPSPositionListener;

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			destroy();
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			GPSService.MyBinder binder = (GPSService.MyBinder) service;
			binder.getService().initGPSConfig();
		}
	};

	/**
	 * 单例
	 * 
	 * @param context
	 * @return
	 */
	public static GPS getInstance(Context context) {
		if (null == instance) {
			synchronized (GPS.class) {
				if (instance == null) {
					instance = new GPS(context);
				}
			}
		}
		return instance;
	}

	private GPS() {
		throw new AssertionError();
	}

	private GPS(Context context) {
		this.context = context;
	}

	@Override
	public boolean open() {
		GPSService.addListener(new GPSService.GPSChangeListener() {

			@Override
			public void onEnabled() {
			}

			@Override
			public void onGPSCount(int count) {
			}

			@Override
			public void onDisabled() {
				ToastUtil.showShort(context, "GPS功能不可用，请打开GPS功能");
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onChanged(Location location) {
				if (location != null) {
					if (null != gpsLocationChangeListener) {
						gpsLocationChangeListener.onGPSChanged(location);
					}
					if (null != onGPSPositionListener) {
						onGPSPositionListener.onPosition(location);
					}
				}
			}

			@Override
			public void onOrientation(int degree) {

			}
		});
		Intent serviceIntent = new Intent(context, GPSService.class);
		// context.startService(serviceIntent);
		context.bindService(serviceIntent, conn, Context.BIND_AUTO_CREATE);
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			ToastUtil.showShort(context, "请打开GPS功能");
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			((Activity) context).startActivityForResult(intent, 0);
			return false;
		}
		return true;
	}

	@Override
	public void close() {
		context.unbindService(conn);
		// context.stopService(new Intent(context, GPSService.class));
	}

	/**
	 * GPS位置改变监听
	 * 
	 * @author xs
	 *
	 */
	public interface GPSLocationChangeListener {
		void onGPSChanged(Location location);
	}

	public void setGpsLocationChangeListener(GPSLocationChangeListener gpsLocationChangeListener) {
		this.gpsLocationChangeListener = gpsLocationChangeListener;
	}

	public interface GPSPositionListener {
		void onPosition(Location position);
	}

	public void setOnGPSPositionListener(GPSPositionListener onGPSPositionListener) {
		this.onGPSPositionListener = onGPSPositionListener;
	}

	/**
	 * 销毁
	 */
	private void destroy() {
		if (null != instance) {
			if (null != gpsLocationChangeListener) {
				gpsLocationChangeListener = null;
			}
			instance = null;
			System.gc();
		}
	}

}