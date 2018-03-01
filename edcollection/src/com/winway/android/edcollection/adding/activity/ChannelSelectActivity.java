package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelSelectController;
import com.winway.android.edcollection.adding.viewholder.ChannelSelectViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

public class ChannelSelectActivity
		extends BaseActivity<ChannelSelectController, ChannelSelectViewHolder> {
	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_select_channel);
	}

}
