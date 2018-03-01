package com.winway.android.edcollection.project.dto;

import java.util.List;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.edcollection.project.entity.EmTasksEntity;

/**
 * 任务传输类
 * 
 * @author winway_zgq
 *
 */
@Table(name = "em_project")
public class EmProjectEntityDto extends EmProjectEntity {
	// 不入库
	@Transient
	private List<EmTasksEntity> tasks;// 任务列表

	public List<EmTasksEntity> getTasks() {
		return tasks;
	}

	public void setTasks(List<EmTasksEntity> tasks) {
		this.tasks = tasks;
	}

}
