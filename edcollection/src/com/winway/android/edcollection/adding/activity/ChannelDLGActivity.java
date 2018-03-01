package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelDLGControll;
import com.winway.android.edcollection.adding.viewholder.ChannelDLGViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

/**
 * 电缆沟
 * 
 * @author
 *
 */
public class ChannelDLGActivity extends BaseActivity<ChannelDLGControll, ChannelDLGViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_channel_dlg);
	}
}
