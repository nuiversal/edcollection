package com.winway.android.edcollection.adding.entity;

/**
 * 通道类型
 * 
 * @author zgq
 *
 */
public enum ChannelType {

	dlg(1, "电缆沟"), // 电缆沟
	dlgd(2, "电缆管道"), // 电缆管道
	dlq(3, "电缆桥"), // 电缆桥
	dlsd(4, "电缆隧道"), // 电缆隧道
	dlzm(5, "电缆直埋"), // 电缆直埋
	dg(6, "顶管");// 顶管

	private int index;
	private String value;

	ChannelType(int i, String value) {
		// TODO Auto-generated constructor stub
		this.index = i;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public int getIndex() {
		return this.index;
	}
}
