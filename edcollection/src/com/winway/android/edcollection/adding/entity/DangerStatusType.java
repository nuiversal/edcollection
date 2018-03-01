package com.winway.android.edcollection.adding.entity;

/**
 * 隐患类别
 * 
 * @author winway_zgq
 *
 */
public enum DangerStatusType {
	ZC("正常"),SGJCC("水管交叉处"), YQGJCC("燃气管交叉处"),RLGJCC("热力管交叉处"),TSXJCC("通讯线交叉处"),BYHDQ("白蚁活动区"),SJYTFSQ("酸碱液体腐蚀区"),QT("其他");

	private String value = null;

	DangerStatusType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
