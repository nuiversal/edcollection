package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

import android.widget.RelativeLayout;

public class ChannelDLCViewHolder extends BaseViewHolder{

	@ViewInject(R.id.inCon_channel_dlc_running_no)
	private InputComponent incomRunningNo; // 运行编号

	@ViewInject(R.id.inCon_channel_dlc__operation_ywdw)
	private InputSelectComponent incomOperationYwdw;// 运维单位

	// @ViewInject(R.id.inCon_channel_dlzm_operation_remark)
	// private InputComponent incomOperationBz;// 备注

	// @ViewInject(R.id.inCon_channel_dlzm_operation_tdcd)
	// private InputComponent incomOperationTdcd;// 通道长度

	@ViewInject(R.id.hc_tower_head)
	private HeadComponent hcHead;// head组件
	
	@ViewInject(R.id.rl_channel_dlzm__operation)
	private RelativeLayout rlNameConventions;

	public RelativeLayout getRlNameConventions() {
		return rlNameConventions;
	}

	public void setRlNameConventions(RelativeLayout rlNameConventions) {
		this.rlNameConventions = rlNameConventions;
	}

	public void setIncomOperationYwdw(InputSelectComponent incomOperationYwdw) {
		this.incomOperationYwdw = incomOperationYwdw;
	}

	private CommentChannelViewHolder commentChannelViewHolder = new CommentChannelViewHolder();

	public CommentChannelViewHolder getCommentChannelViewHolder() {
		return commentChannelViewHolder;
	}

	public InputComponent getIncomRunningNo() {
		return incomRunningNo;
	}

	public void setIncomRunningNo(InputComponent incomRunningNo) {
		this.incomRunningNo = incomRunningNo;
	}

	public InputSelectComponent getIncomOperationYwdw() {
		return incomOperationYwdw;
	}

	public void setIncomOperationUnit(InputSelectComponent incomOperationYwdw) {
		this.incomOperationYwdw = incomOperationYwdw;
	}

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}
}
