package com.winway.android.edcollection.rtk.entity;

/**
 * 位置信息
 * 
 * @author zgq
 *
 */
public class RtkLocationInfo {
	private Double lon;// 经度
	private Double lat;// 纬度
	private Integer satellites;// 卫星数量
	private Integer gpsState;// gps定位状态（ 0初始化， 1单点定位， 2码差分， 3无效PPS， 4固定解， 5浮点解，
								// 6正在估算 7，人工输入固定值， 8模拟模式， 9WAAS差分）
	private Double hdop;// 水平精度因子
	private Double altitude;// 高程

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Integer getSatellites() {
		return satellites;
	}

	public void setSatellites(Integer satellites) {
		this.satellites = satellites;
	}

	public Integer getGpsState() {
		return gpsState;
	}

	public void setGpsState(Integer gpsState) {
		this.gpsState = gpsState;
	}

	public Double getHdop() {
		return hdop;
	}

	public void setHdop(Double hdop) {
		this.hdop = hdop;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

}
