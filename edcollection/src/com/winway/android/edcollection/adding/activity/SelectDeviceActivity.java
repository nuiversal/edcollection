package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.SelectDeviceControll;
import com.winway.android.edcollection.adding.viewholder.SelectDeviceViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.app.Activity;
import android.os.Bundle;

/**
 * 关联设备
 * 
 * @author zgq
 *
 */
public class SelectDeviceActivity extends BaseActivity<SelectDeviceControll, SelectDeviceViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.select_device);
	}

}
