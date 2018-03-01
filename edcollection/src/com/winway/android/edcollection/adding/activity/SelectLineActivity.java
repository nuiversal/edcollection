package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.SelectLineControll;
import com.winway.android.edcollection.adding.viewholder.SelectLineViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/** 
* @author lyh  
* @version 创建时间：2017年2月9日 上午10:33:09 
* 
*/
public class SelectLineActivity extends
		BaseActivity<SelectLineControll, SelectLineViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_select_line);
	}

}
