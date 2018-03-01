package com.winway.android.map.entity;

/**
 * 地图类型
 * 
 * @author zgq
 *
 */
public enum MapType {
	MAP_2D("2d"), MAP_25D("25d"), MAP_CUSTOM("custom");// 2d天地图，25仿真三维地图，custom客户地图

	private final String value;

	MapType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
