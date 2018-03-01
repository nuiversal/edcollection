package com.winway.android.edcollection.about.entity;

public enum DBEnum {
	ECLOUD("ECLOUD_DATAPLUG"), ED("ED_DATAPLUG");

	private String value;

	DBEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
