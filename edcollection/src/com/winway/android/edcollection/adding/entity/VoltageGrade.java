package com.winway.android.edcollection.adding.entity;

/**
 * 电压等级
 * 
 * @author lyh
 * @version 创建时间：2016年12月29日 上午11:20:47
 * 
 */
public enum VoltageGrade {
	VOLTAGE_10KV("10"), // 10kV
	VOLTAGE_110KV("110"), // 110kV
	VOLTAGE_220KV("220"); // 220kV

	private String value;

	VoltageGrade(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
