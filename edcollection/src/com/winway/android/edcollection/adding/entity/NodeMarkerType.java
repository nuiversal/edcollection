package com.winway.android.edcollection.adding.entity;

/**
 * 路径点的节点类型
 * 
 * @author zgq
 *
 */
public enum NodeMarkerType {

	BSQ("标识球", 1), // 标识球
	BSD("标识钉", 2), // 标识钉
	XND("虚拟点", 0),// 虚拟点
	GT("杆塔", 3),// 杆塔
	AJH("安键环",4);
	private String name;
	private Integer value;

	NodeMarkerType(String name, Integer value) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}
}
