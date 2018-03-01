package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.DeviceReadQueryControll;
import com.winway.android.edcollection.adding.viewholder.DeviceReadQueryViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

public class DeviceReadQueryActivity extends BaseActivity<DeviceReadQueryControll, DeviceReadQueryViewHolder>{

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_device_readquery);
	}

}
