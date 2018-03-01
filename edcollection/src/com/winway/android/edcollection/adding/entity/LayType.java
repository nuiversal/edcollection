package com.winway.android.edcollection.adding.entity;

/**
 * 敷设类型
 * 
 * @author zgq
 *
 */
public enum LayType {

	jk(1, "架空"), // 架空
	dlg(2, "电缆沟"), // 电缆沟
	dlc(3, "电缆槽"), // 电缆槽
	mg(4, "埋管"), // 埋管
	pg(4, "排管"), // 排管
	dg(5, "顶管"),// 顶管
	tlg(5, "拖拉管"),// 拖拉管
	sd(6, "隧道"),// 隧道
	qj(7, "桥架"),// 桥架
	zhg(8, "综合沟"),// 综合沟
	zm(9, "直埋");// 直埋

	private int index;
	private String value; // 敷设类型| 1 架空, 2 电缆沟 3 电缆槽 4 埋管 5 顶管

	LayType(int i, String value) {
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
