package com.winway.android.edcollection.project.activity;

import android.os.Bundle;
import android.view.Window;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseActivity;
import com.winway.android.edcollection.project.controll.ProjectingControll;
import com.winway.android.edcollection.project.viewholder.ProjectingViewHolder;

/**
 * 项目选择页面
 * 
 * @author lyh
 * @version 创建时间：2016年12月6日 下午2:38:52
 * 
 */
public class ProjectingActivity extends BaseActivity<ProjectingControll, ProjectingViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_projecting);

	}

}
