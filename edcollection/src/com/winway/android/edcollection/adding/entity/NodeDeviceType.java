package com.winway.android.edcollection.adding.entity;

/**
 * 路径点设备类型
 * 
 * @author zgq
 *
 */
public enum NodeDeviceType {// 设备类型| 1 变电站，2 配电房，3 变压器，4 杆塔，5 工井，6 箱式变电站，7 开关站， 8 低压配电箱
	BDZ("变电站", 1), PDF("配电室", 2), BYQ("变压器", 3), GT("杆塔", 4), GJ("工井", 5)
	,XSBDZ("箱式变电站", 6),KGZ("开关站", 7),DYPDX("低压配电箱", 8);

	private String name;
	private Integer value;

	NodeDeviceType(String name, Integer value) {
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
