package com.winway.android.edcollection.task.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 任务明细
 * 
 * @author Administrator
 *
 */
public class TaskDetailViewHolder extends BaseViewHolder {
	@ViewInject(R.id.img_taskdetail_info)
	private ImageView ivTaskInfo;
	@ViewInject(R.id.lv_taskdetail_list)
	private ListView lvTasklist;// 任务列表
	@ViewInject(R.id.img_taskdetail_refresh)
	private ImageView ivTaskReflesh; // 刷新图标
	@ViewInject(R.id.img_taskdetail_upload)
	private ImageView ivTaskUpload; // 上传图标
	@ViewInject(R.id.tv_taskdetail_msg)
	private TextView tvMessage;

	public ImageView getIvTaskUpload() {
		return ivTaskUpload;
	}

	public void setIvTaskUpload(ImageView ivTaskUpload) {
		this.ivTaskUpload = ivTaskUpload;
	}

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

	public TextView getTvMessage() {
		return tvMessage;
	}

	public void setTvMessage(TextView tvMessage) {
		this.tvMessage = tvMessage;
	}

	public ImageView getIvTaskInfo() {
		return ivTaskInfo;
	}

	public void setIvTaskInfo(ImageView ivTaskInfo) {
		this.ivTaskInfo = ivTaskInfo;
	}

}
