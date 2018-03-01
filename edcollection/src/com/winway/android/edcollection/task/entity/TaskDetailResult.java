package com.winway.android.edcollection.task.entity;

import java.util.List;

import com.winway.android.edcollection.base.dto.MessageBase;

/**
 * 获取任务明细接口返回结果实体
 * @author mr-lao
 *
 */
public class TaskDetailResult extends MessageBase {
	public List<TaskDetailItemData> list;

	public List<TaskDetailItemData> getList() {
		return list;
	}

	public void setList(List<TaskDetailItemData> list) {
		this.list = list;
	}

}
