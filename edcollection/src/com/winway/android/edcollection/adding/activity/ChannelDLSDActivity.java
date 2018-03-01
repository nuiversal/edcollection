package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelDLSDControll;
import com.winway.android.edcollection.adding.viewholder.ChannelDLSDViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

/**
 * 电缆隧道
 * @author zgq
 *
 */
public class ChannelDLSDActivity extends BaseActivity<ChannelDLSDControll, ChannelDLSDViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_channel_dlsd);
	}
}
