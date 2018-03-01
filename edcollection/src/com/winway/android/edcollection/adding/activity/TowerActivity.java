package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.TowerControll;
import com.winway.android.edcollection.adding.viewholder.TowerViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 杆塔
 * 
 * @author
 *
 */
public class TowerActivity extends BaseActivity<TowerControll, TowerViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.tower);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
