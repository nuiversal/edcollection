package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelDGControll;
import com.winway.android.edcollection.adding.viewholder.ChannelDGViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/**
 * 拖拉管、顶管
 * 
 * @author
 *
 */
public class ChannelDGActivity extends BaseActivity<ChannelDGControll, ChannelDGViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_channel_dg);
	}

}
