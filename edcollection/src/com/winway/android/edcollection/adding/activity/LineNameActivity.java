package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.LineNameControll;
import com.winway.android.edcollection.adding.viewholder.LineNameViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

/**
 * 线路名称
 * 
 * @author 
 *
 */
public class LineNameActivity extends BaseActivity<LineNameControll, LineNameViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.line_name);
	}

}
