package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.WorkWellControll;
import com.winway.android.edcollection.adding.viewholder.GjViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/**
 * 工井
 * 
 * @author
 *
 */
public class WorkWellActivity extends BaseActivity<WorkWellControll, GjViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		   setContentView(R.layout.gj_activity);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
