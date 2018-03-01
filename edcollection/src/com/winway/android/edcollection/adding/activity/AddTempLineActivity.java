package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.AddTempLineControll;
import com.winway.android.edcollection.adding.viewholder.AddTempLineViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

/**
 * 添加临时线路
 * 
 * @author zgq
 *
 */
public class AddTempLineActivity extends BaseActivity<AddTempLineControll, AddTempLineViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_addtempline);
	}

}
