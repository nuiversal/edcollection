package com.winway.android.edcollection.task.adapter;

import java.util.ArrayList;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.map.MapPositionPicker;
import com.winway.android.edcollection.project.entity.EmTasksEntity;
import com.winway.android.edcollection.task.activity.EmTaskDetailActivity;
import com.winway.android.edcollection.task.activity.TaskDetailActivity;
import com.winway.android.ewidgets.button.EButtonComponent;
import com.winway.android.ewidgets.textview.ExpandableTextView;
import com.winway.android.util.AndroidBasicComponentUtils;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 任务列表适配器
 * 
 * @update 2017-02-17(能不在Adapter中使用事件监听器，就尽量不要使用)
 * @author zgq
 *
 */
public class TaskListAdapter extends MBaseAdapter<EmTasksEntity> {

	public TaskListAdapter(Context context, ArrayList<EmTasksEntity> models) {
		super(context, models);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.tasklist_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvTaskName = (TextView) convertView.findViewById(R.id.tv_tasklist_item_taskName);
			viewHolder.tvTaskDesc = (ExpandableTextView) convertView.findViewById(R.id.tv_tasklist_item_taskDesc);
			viewHolder.tvStateState = (TextView) convertView.findViewById(R.id.tv_tasklist_item_taskState);
			viewHolder.ivTaskState = (ImageView) convertView.findViewById(R.id.iv_tasklist_item_taskState);
			viewHolder.btnProp = (EButtonComponent) convertView.findViewById(R.id.btn_tasklist_item_prop);
			viewHolder.btnDetail = (EButtonComponent) convertView.findViewById(R.id.btn_tasklist_item_detail);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 赋值
		EmTasksEntity taskEntity = models.get(position);
		viewHolder.tvTaskName.setText(taskEntity.getTaskName());
		viewHolder.tvTaskDesc.setText(taskEntity.getTaskDesc());
		viewHolder.ivTaskState.setTag(position);
		viewHolder.tvTaskDesc.setTag(position);
		viewHolder.ivTaskState.setOnClickListener(mOnClickListener);
		viewHolder.tvTaskDesc.setOnClickListener(mOnClickListener);
		//
		viewHolder.btnProp.setPadding(20, 20, 20, 20);
		viewHolder.btnDetail.setPadding(20, 20, 20, 20);
		viewHolder.btnProp.setTag(position);
		viewHolder.btnDetail.setTag(position);
		viewHolder.btnProp.setOnClickListener(mOnClickListener);
		viewHolder.btnDetail.setOnClickListener(mOnClickListener);
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
		viewHolder.tvStateState.setText(statestr);
		return convertView;
	}

	private View.OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_tasklist_item_detail:
				int position1 = 0;
				try {
					position1 = Integer.parseInt(v.getTag().toString());
				} catch (Exception e) {
				}
				AndroidBasicComponentUtils.launchActivity(context, TaskDetailActivity.class, "taskId",
						models.get(position1).getEmTasksId());
				break;
			case R.id.btn_tasklist_item_prop:
				int position2 = 0;
				try {
					position2 = Integer.parseInt(v.getTag().toString());
				} catch (Exception e) {
				}
				AndroidBasicComponentUtils.launchActivity(context, EmTaskDetailActivity.class, "EM_TASK_ENTITY",
						models.get(position2));
				break;
			default:
				break;
			}

		}
	};

	class ViewHolder {
		// 任务名称
		TextView tvTaskName;
		// 任务描述
		ExpandableTextView tvTaskDesc;
		// 任务状态
		ImageView ivTaskState;
		// 任务状态
		TextView tvStateState;
		// 属性
		EButtonComponent btnProp;
		// 选点
		EButtonComponent btnDetail;
	}

}
