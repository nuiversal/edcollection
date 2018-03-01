package com.winway.android.edcollection.task.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseActivity;
import com.winway.android.edcollection.task.controll.TaskDetailListControll;
import com.winway.android.edcollection.task.viewholder.TaskDetailViewHolder;

import android.os.Bundle;

/**
 * 任务明细
 * 
 * @author Administrator
 *
 */
public class TaskDetailActivity extends BaseActivity<TaskDetailListControll, TaskDetailViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.taskdetail);
	}

}
