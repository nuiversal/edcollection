package com.winway.android.edcollection.adding.entity;

/**
 * 材质类型枚举
 * @author lyh
 * @data 2017年2月13日
 */
public enum CZTypeEnum {
	zt(1, "铸铁"), // 铸铁
	gd(2, "管道"), // 管道
	sn(3, "水泥"); // 水泥

	private int index;
	private String value;

	private CZTypeEnum(int index, String value) {
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
