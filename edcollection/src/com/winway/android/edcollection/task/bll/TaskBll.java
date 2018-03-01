package com.winway.android.edcollection.task.bll;

import java.util.ArrayList;

import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.entity.EmTasksEntity;

import android.content.Context;

/**
 * 任务业务处理
 * 
 * @author zgq
 *
 */
public class TaskBll extends BaseBll<EmTasksEntity> {

	public TaskBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取任务列表
	 * 
	 * @param prjId
	 * @return
	 */
	public ArrayList<EmTasksEntity> getTaskList(String prjId) {
		// TODO Auto-generated method stub
		ArrayList<EmTasksEntity> list = new ArrayList<EmTasksEntity>();
		for (int i = 0; i < 6; i++) {
			EmTasksEntity emTasksEntity = new EmTasksEntity();
			emTasksEntity.setTaskName("task" + i);
			emTasksEntity.setTaskDesc("taskDesc" + i);
			emTasksEntity.setEmTasksId(i+"");
			list.add(emTasksEntity);
		}
		return list;
	}

}
