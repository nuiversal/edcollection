package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelPlanningController;
import com.winway.android.edcollection.adding.viewholder.ChannelPlanningViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

/**
 * 通道截面
 * @author mr-lao
 *
 */
public class ChannelPlanningActivity extends BaseActivity<ChannelPlanningController, ChannelPlanningViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_channel_planing);
	}

}
