package com.winway.android.edcollection.login.viewholder;

import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;

/**
 * 选择项目ui初始化
 * @author lyh
 * @version 创建时间：2016年12月7日 上午10:14:38
 * 
 */
public class ProjectingViewHolder extends BaseViewHolder{

	// 返回
	@ViewInject(R.id.tv_projecting_title_cancel)
	private TextView cancelTV;
	
	// 确定
	@ViewInject(R.id.tv_projecting_title_confirm)
	private TextView confirmTV;
	// 项目名称列表
	@ViewInject(R.id.lv_projecting_project)
	private ListView projectLV;

	public TextView getCancelTV() {
		return cancelTV;
	}

	public void setCancelTV(TextView cancelTV) {
		this.cancelTV = cancelTV;
	}

	public TextView getConfirmTV() {
		return confirmTV;
	}

	public void setConfirmTV(TextView confirmTV) {
		this.confirmTV = confirmTV;
	}

	public ListView getProjectLV() {
		return projectLV;
	}

	public void setProjectLV(ListView projectLV) {
		this.projectLV = projectLV;
	}

}
