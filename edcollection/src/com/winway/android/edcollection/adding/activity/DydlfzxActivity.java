package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.DydlfzxControll;
import com.winway.android.edcollection.adding.viewholder.DydlfzxViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 低压电缆分支箱
 * 
 * @author
 *
 */
public class DydlfzxActivity extends BaseActivity<DydlfzxControll, DydlfzxViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.dydlfzx);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
