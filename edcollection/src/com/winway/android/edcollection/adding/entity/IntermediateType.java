package com.winway.android.edcollection.adding.entity;

/**
 * 中间接头类型：直通接头、绝缘接头
 * 
 * @author lyh
 *
 */
public enum IntermediateType {
	ztjt("直通接头", 1), jyjt("绝缘接头", 2);

	private String name;
	private int value;

	private IntermediateType(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
}
