package com.winway.android.edcollection.task.controll;

import java.text.SimpleDateFormat;

import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.datacenter.service.OrgCenter;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.edcollection.project.entity.EmTasksEntity;
import com.winway.android.edcollection.task.viewholder.EmTaskDetailsHolder;
import com.winway.android.util.StringUtils;

import android.annotation.SuppressLint;

public class EmTaskDetailsController extends BaseControll<EmTaskDetailsHolder> {
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void init() {
		EmTasksEntity taskEntity = (EmTasksEntity) getIntent().getSerializableExtra("EM_TASK_ENTITY");
		OrgCenter orgCenter = new OrgCenter(mActivity);
		viewHolder.getTaskNameTV().setText(StringUtils.nullStrToEmpty(taskEntity.getTaskName()));
		EmProjectEntity prjName = orgCenter.getPrjName(taskEntity.getPrjid());
		if (null != prjName) {
			viewHolder.getPrjNameTV().setText(prjName.getPrjName() + "");
		}
		viewHolder.getBeginTV().setText(null == taskEntity.getBeginTime() ? "" : sdf.format(taskEntity.getBeginTime()));
		viewHolder.getCompleteTV().setText(null == taskEntity.getCompleteTime() ? "" : sdf.format(taskEntity.getCompleteTime()));
		viewHolder.getExcutorTV().setText(StringUtils.nullStrToEmpty(GlobalEntry.loginResult.getUsername()));
		viewHolder.getProduceTV().setText(StringUtils.nullStrToEmpty(taskEntity.getTaskDesc()));
		viewHolder.getCompleteTV().setText(null == taskEntity.getCompleteTime() ? "" : sdf.format(taskEntity.getCompleteTime()));
		String statestr = "";
		if (null == taskEntity.getStatus() || 0 == taskEntity.getStatus()) {
			statestr = "未确认";
		} else if (1 == taskEntity.getStatus()) {
			statestr = "已审批";
		} else if (2 == taskEntity.getStatus()) {
			statestr = "已下载";
		} else if (3 == taskEntity.getStatus()) {
			statestr = "采集完成";
		} else if (4 == taskEntity.getStatus()) {
			statestr = "已审核";
		} else if (5 == taskEntity.getStatus()) {
			statestr = "已完结";
		}
		viewHolder.getStateTV().setText(statestr);
		// viewHolder.getUpdateTimeTV().setText(null == taskEntity.get);
	}

}
