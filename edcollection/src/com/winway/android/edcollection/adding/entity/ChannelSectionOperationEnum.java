package com.winway.android.edcollection.adding.entity;

/**
 * 通道截面中的小圆，中圆，大圆
 * 
 * @author lyh
 * @version 创建时间：2017年4月19日
 *
 */
public enum ChannelSectionOperationEnum {
	xy(0, "小圆"), zy(1, "中圆"), dy(2, "大圆");
	private int value;
	private String name;

	private ChannelSectionOperationEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

}
