package com.winway.android.edcollection.task.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;

import android.widget.TextView;

public class EmTaskDetailsHolder extends BaseViewHolder {
	@ViewInject(R.id.tv_task_name)
	private TextView taskNameTV;
	@ViewInject(R.id.tv_task_project_name)
	private TextView prjNameTV;
	@ViewInject(R.id.tv_task_create_time)
	private TextView createTimeTV;
	@ViewInject(R.id.tv_task_excutor)
	private TextView excutorTV;
	@ViewInject(R.id.tv_task_state)
	private TextView stateTV;
	@ViewInject(R.id.tv_task_begin_time)
	private TextView beginTV;
	@ViewInject(R.id.tv_task_complete_time)
	private TextView completeTV;
	@ViewInject(R.id.tv_task_update_time)
	private TextView updateTimeTV;
	@ViewInject(R.id.tv_task_produce)
	private TextView produceTV;

	public TextView getPrjNameTV() {
		return prjNameTV;
	}

	public void setPrjNameTV(TextView prjNameTV) {
		this.prjNameTV = prjNameTV;
	}

	public TextView getCreateTimeTV() {
		return createTimeTV;
	}

	public void setCreateTimeTV(TextView createTimeTV) {
		this.createTimeTV = createTimeTV;
	}

	public TextView getExcutorTV() {
		return excutorTV;
	}

	public void setExcutorTV(TextView excutorTV) {
		this.excutorTV = excutorTV;
	}

	public TextView getStateTV() {
		return stateTV;
	}

	public void setStateTV(TextView stateTV) {
		this.stateTV = stateTV;
	}

	public TextView getBeginTV() {
		return beginTV;
	}

	public void setBeginTV(TextView beginTV) {
		this.beginTV = beginTV;
	}

	public TextView getCompleteTV() {
		return completeTV;
	}

	public void setCompleteTV(TextView completeTV) {
		this.completeTV = completeTV;
	}

	public TextView getUpdateTimeTV() {
		return updateTimeTV;
	}

	public void setUpdateTimeTV(TextView updateTimeTV) {
		this.updateTimeTV = updateTimeTV;
	}

	public TextView getProduceTV() {
		return produceTV;
	}

	public void setProduceTV(TextView produceTV) {
		this.produceTV = produceTV;
	}

	public TextView getTaskNameTV() {
		return taskNameTV;
	}

	public void setTaskNameTV(TextView taskNameTV) {
		this.taskNameTV = taskNameTV;
	}

}
