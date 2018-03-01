package com.winway.android.edcollection.adding.entity;

/**
 * 管孔现状：0表示通、1表示不通、2表示半通
 * 
 * @author lyh
 *
 */
public enum IsPluggingEnum {
	
	T("通",0),BuT("不通",1),BanT("半通",2);
	
	private String name;
	private Integer value;
	
	private IsPluggingEnum(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getValue() {
		return value;
	}
}
