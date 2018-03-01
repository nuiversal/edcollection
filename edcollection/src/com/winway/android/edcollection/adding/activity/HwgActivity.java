package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.HwgControll;
import com.winway.android.edcollection.adding.viewholder.HwgViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 环网柜
 * 
 * @author
 *
 */
public class HwgActivity extends BaseActivity<HwgControll, HwgViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.hwg);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
