package com.winway.android.edcollection.adding.entity;

/**
 * 线路附属设备
 * 
 * @author zgq
 *
 */
public enum LineDeviceType {// 设备类型| 1 电子标签，2 分接箱, 3中间接头
	DZBQ("电子标签", 1), FJX("分接箱", 2), ZJJT("中间接头", 3),HWG("环网柜", 4);;

	private String name;
	private Integer value;

	LineDeviceType(String name, Integer value) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}
}
