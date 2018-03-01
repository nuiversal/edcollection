package com.winway.android.edcollection.adding.entity;

/**
 * 产权属性|1 公用，2 专用
 * 
 * @author lyh
 * @version 创建时间：2016年12月29日 下午4:05:22
 * 
 */
public enum RightPro {
	GY("公用", 1), ZY("专用", 2);

	private final String name;
	private final Integer value;

	RightPro(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public Integer getValue() {
		return this.value;
	}

}
