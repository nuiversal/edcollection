package com.winway.android.edcollection.adding.entity;

/**
 * 电缆相序：A相、B相、C相、无
 * 
 * @author lyh
 *
 */
public enum PhaseSeqEnum {
	
	AX("A相", 1), BX("B相", 2), CX("C相", 3), W("无", 4);
	
	private String name;
	private Integer value;
	
	private PhaseSeqEnum(String name, Integer value) {
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
