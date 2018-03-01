package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelDLQControll;
import com.winway.android.edcollection.adding.viewholder.ChannelDLQViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/**
 * 电缆桥
 *@author lyh
 *@data 2017年2月13日
 */
public class ChannelDLQActivity extends BaseActivity<ChannelDLQControll, ChannelDLQViewHolder>{

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_channel_dlq);
	}

}
