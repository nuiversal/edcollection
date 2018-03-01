package com.winway.android.edcollection.setting.entity;

/**
 * 设置 操作类型
 * 
 * @author ly
 *
 */
public enum OperateType {
	TBSJ(0, "同步数据"), SCFJ(1, "上传附件"), GXSJZD(2, "更新数据字典"),LY(3,"蓝牙"),RTK(4,"RTK"),BFSJ(5,"备份数据"),LXWM(6,"关于");

	private Integer index;
	private String value;

	OperateType(Integer index, String value) {
		this.index = index;
		this.value = value;
	}

	public Integer getIndex() {
		return index;
	}

	public String getValue() {
		return value;
	}

}
