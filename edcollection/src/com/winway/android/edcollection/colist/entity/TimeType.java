package com.winway.android.edcollection.colist.entity;

public enum TimeType {
	QB(0, "全部"), DT(1, "当天"), BZ(2, "本周"), BY(3, "本月");
	private int no;
	private String value;

	TimeType(int no, String value) {
		this.no = no;
		this.value = value;
	}

	public int getNo() {
		return no;
	}

	public String getValue() {
		return value;
	}
}
