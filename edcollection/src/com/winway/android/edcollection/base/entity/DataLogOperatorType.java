package com.winway.android.edcollection.base.entity;

/**
 * 数据更新日志
 * 
 * @author zgq
 *
 */
public enum DataLogOperatorType {// 操作类型| 1 新增，2 修改，3 删除
	Add(1, "新增"), update(2, "修改"), delete(3, "删除");

	private final Integer value;
	private final String name;

	DataLogOperatorType(Integer value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Integer getValue() {
		return this.value;
	}

}
