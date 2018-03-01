package com.winway.android.edcollection.test;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

/**
 * 主界面
 * 
 * @author zgq
 *
 */
public class MainActivity extends BaseActivity<TestMainControll, TestMainViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.main_test);
	}

}
