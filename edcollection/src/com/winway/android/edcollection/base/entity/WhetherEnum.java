package com.winway.android.edcollection.base.entity;

/**
 * 是否操作
 * 
 * @author zgq
 *
 */
public enum WhetherEnum {
	YES(1, "是"), NO(0, "否");

	private final Integer value;
	private final String name;

	WhetherEnum(Integer value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Integer getValue() {
		return this.value;
	}
}
