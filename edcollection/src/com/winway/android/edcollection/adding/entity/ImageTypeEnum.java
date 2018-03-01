package com.winway.android.edcollection.adding.entity;

public enum ImageTypeEnum {
	bg(0), po(1);

	private final Integer value;

	ImageTypeEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}
}
