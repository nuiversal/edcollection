package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.DypdxControll;
import com.winway.android.edcollection.adding.viewholder.DypdxViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 低压配电箱
 * 
 * @author
 *
 */
public class DypdxActivity extends BaseActivity<DypdxControll, DypdxViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.dypdx);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
