package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelDLZMControll;
import com.winway.android.edcollection.adding.viewholder.ChanelDLZMViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/**
 * 电缆直埋
 *@author lyh
 *@data 2017年2月13日
 */
public class ChannelDLZMActivity extends BaseActivity<ChannelDLZMControll, ChanelDLZMViewHolder>{

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_channel_dlzm);
	}

}
