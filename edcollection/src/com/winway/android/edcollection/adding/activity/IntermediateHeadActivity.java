package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.IntermediateHeadControll;
import com.winway.android.edcollection.adding.viewholder.IntermediateHeadViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 电缆中间接头
 * 
 * @author ly
 *
 */
public class IntermediateHeadActivity extends BaseActivity<IntermediateHeadControll, IntermediateHeadViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.intermediate_head);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
