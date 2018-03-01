package com.winway.android.sensor.geolocation;

/**
 * 地理位置定位接口
 * 
 * @author ly
 *
 */
public interface Geolocation {
	/**
	 * 打开
	 * 
	 * @return
	 */
	public boolean open();

	/**
	 * 关闭
	 */
	public void close();
}
