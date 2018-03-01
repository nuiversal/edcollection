package com.winway.android.edcollection.task.entity;

/**
 * 任务明细状态 状态|0 未采集，1 已采集，2 已审核
 *
 * @author lyh
 * @data 2017年2月25日
 */
public enum TaskStateTypeEnum {
	wcj(0, "未采集"), ycj(1, "已采集"), ytg(2, "已通过"),wtg(3,"未通过");

	private int state;
	private String Value;

	private TaskStateTypeEnum(int state, String value) {
		this.state = state;
		Value = value;
	}

	public int getState() {
		return state;
	}

	public String getValue() {
		return Value;
	}

}
