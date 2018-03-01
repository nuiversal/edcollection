package com.winway.android.edcollection.adding.entity;

/**
 * 绑扎对象| 1 电缆本体，2 中间接头，3 终端设备
 * 
 * @author ly
 *
 */
public enum BindTargetType {
	dlbt(1, "电缆本体"), zjjt(2, "中间接头"), zdsb(3, "终端设备");
	private int index;
	private String type;

	BindTargetType(int index, String type) {
		this.index = index;
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}

}
