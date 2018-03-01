package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.KgzControll;
import com.winway.android.edcollection.adding.viewholder.KgzViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/**
 * 
 * 开关站
 * 
 * @author lyh
 *
 */
public class KgzActivity extends BaseActivity<KgzControll, KgzViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.channel_switch_station);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
