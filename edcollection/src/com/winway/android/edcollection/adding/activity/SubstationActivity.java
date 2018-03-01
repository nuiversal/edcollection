package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.SubstationControll;
import com.winway.android.edcollection.adding.viewholder.SubstationViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/**
 * 变电站
 * 
 * @author lyh
 * @version 创建时间：2016年12月19日 下午5:30:59
 * 
 */
public class SubstationActivity extends BaseActivity<SubstationControll, SubstationViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.substation);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
