package com.winway.android.edcollection.adding.entity;
/**
 * 专业分类枚举
 *@author lyh
 *@data 2017年2月13日
 */
public enum ZYFLTypeEnum {
	
	sd(1,"输电"), //输电
	pd(2,"配电"); //配电
	
	private int index;
	private String value;
	
	private ZYFLTypeEnum(int index, String value) {
		this.index = index;
		this.value = value;
	}
	
	public int getIndex() {
		return index;
	}
	public String getValue() {
		return value;
	}
}
