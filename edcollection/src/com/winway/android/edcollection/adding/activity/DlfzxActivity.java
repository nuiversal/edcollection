package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.DlfzxControll;
import com.winway.android.edcollection.adding.viewholder.DlfzxViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 电缆分支箱
 * 
 * @author
 *
 */
public class DlfzxActivity extends BaseActivity<DlfzxControll, DlfzxViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.dlfzx);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
