package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelDLCControll;
import com.winway.android.edcollection.adding.viewholder.ChannelDLCViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

public class ChannelDLCActivity extends BaseActivity<ChannelDLCControll,ChannelDLCViewHolder>{

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_channel_dlc);
	}

}
