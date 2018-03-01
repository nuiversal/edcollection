package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.XsbdzControll;
import com.winway.android.edcollection.adding.viewholder.XsbdzViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/**
 * @author lyh
 * @data 2017年2月15日
 */
public class XsbdzActivity extends BaseActivity<XsbdzControll, XsbdzViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_xsbdz);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
