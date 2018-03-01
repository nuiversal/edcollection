package com.winway.android.edcollection.adding.entity;

/**
 * 管道类型枚举
 * 
 * @author lyh
 * @data 2017年2月13日
 */
public enum GDLXTypeEnum {

	DL(1, "电力排管"), // 电力排管
	SZ(2, "市政排管"), // 市政排管
	YH(3, "用户排管"), // 用户排管
	XQ(4, "小区排管"); // 小区排管
	private int index;
	private String value;

	private GDLXTypeEnum(int index, String value) {
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
