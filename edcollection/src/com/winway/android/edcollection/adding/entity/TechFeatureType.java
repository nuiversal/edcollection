package com.winway.android.edcollection.adding.entity;

/**
 * 工艺特征| 1 冷缩式，2 热缩式，3 预制式，4 充油式
 * 
 * @author ly
 *
 */
public enum TechFeatureType {
	lss(1, "冷缩式"), rss(2, "热缩式"), yzs(3, "预制式"), cys(4, "充油式");

	private int index;
	private String value;

	TechFeatureType(int index, String value) {
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
