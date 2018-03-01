package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelDLGDControll;
import com.winway.android.edcollection.adding.viewholder.ChannelDLGDViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

/**
 * 电缆管道
 * 
 * @author
 *
 */
public class ChannelDLGDActivity extends BaseActivity<ChannelDLGDControll, ChannelDLGDViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_channel_dlgd);
	}
}
