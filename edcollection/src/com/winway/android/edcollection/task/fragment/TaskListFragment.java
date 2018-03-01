package com.winway.android.edcollection.task.fragment;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseFragment;
import com.winway.android.edcollection.task.controll.TaskDetailFragmentControll;
import com.winway.android.edcollection.task.controll.TaskListControll;
import com.winway.android.edcollection.task.viewholder.TaskDetailViewHolder;
import com.winway.android.edcollection.task.viewholder.TaskListViewHolder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 任务列表
 * 
 * @author zgq
 *
 */
public class TaskListFragment extends BaseFragment<TaskDetailFragmentControll, TaskDetailViewHolder> {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// View view = inflater.inflate(R.layout.tasklist, container, false);
		View view = inflater.inflate(R.layout.taskdetail, container, false);
		return view;
	}

}
