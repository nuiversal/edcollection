package com.winway.android.edcollection.task.entity;

import java.util.List;

import com.winway.android.edcollection.base.dto.MessageBase;
import com.winway.android.edcollection.project.entity.EmTasksEntity;

public class TaskResult extends MessageBase {
	public List<EmTasksEntity> list;

	public List<EmTasksEntity> getList() {
		return list;
	}

	public void setList(List<EmTasksEntity> list) {
		this.list = list;
	}

}
