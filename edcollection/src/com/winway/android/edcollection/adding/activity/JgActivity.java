package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.JgControll;
import com.winway.android.edcollection.adding.viewholder.JgViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/** 
 * @author lyh
 * @version 创建时间：2017年3月28日 
 *
 */
public class JgActivity extends BaseActivity<JgControll, JgViewHolder>{

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.jg);
	}

}
