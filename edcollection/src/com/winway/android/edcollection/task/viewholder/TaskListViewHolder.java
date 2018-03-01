package com.winway.android.edcollection.task.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * 任务列表
 * 
 * @author zgq
 *
 */
public class TaskListViewHolder extends BaseViewHolder {
	@ViewInject(R.id.lv_tasklist_list)
	private ListView lvTasklist;// 任务列表
	@ViewInject(R.id.img_reflesh_task)
	private ImageView ivTaskReflesh;

	public ListView getLvTasklist() {
		return lvTasklist;
	}

	public void setLvTasklist(ListView lvTasklist) {
		this.lvTasklist = lvTasklist;
	}

	public ImageView getIvTaskReflesh() {
		return ivTaskReflesh;
	}

	public void setIvTaskReflesh(ImageView ivTaskReflesh) {
		this.ivTaskReflesh = ivTaskReflesh;
	}

}
