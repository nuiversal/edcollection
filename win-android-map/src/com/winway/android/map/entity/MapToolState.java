package com.winway.android.map.entity;

/**
 * 当前地图工具状态
 * 
 * @author zgq
 *
 */
public enum MapToolState {

	MEASURE_DISTANCE(0), MEASURE_AREA(1);// 0测距，1侧面

	private final int value;

	MapToolState(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

}
