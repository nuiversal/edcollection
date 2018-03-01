package com.winway.android.sensor.geolocation;

import android.content.Context;
import android.location.Location;

/**
 * 地理位置定位实现类
 * 
 * @author ly
 *
 */
public class GPS implements Geolocation {
	private static GPS instance;
	private Context mContext;
	public static Location nowLoaction;

	private GPS() {
	}

	private GPS(Context context) {
		this.mContext = context;
	}

	public static GPS getInstance(Context context) {
		if (instance == null) {
			synchronized (GPS.class) {
				if (instance == null) {
					instance = new GPS(context);
				}
			}
		}
		return instance;
	}

	@Override
	public boolean open() {
		boolean isOpen = GPSconfig.getInstance(mContext).open();
		if (isOpen) {
			GPSconfig.getInstance(mContext).loadConfig();
		}
		return isOpen;
	}

	@Override
	public void close() {
		GPSconfig.getInstance(mContext).close();
	}
}
