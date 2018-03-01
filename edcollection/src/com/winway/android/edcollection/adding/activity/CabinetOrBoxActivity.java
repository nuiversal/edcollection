package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.CabinetOrBoxControll;
import com.winway.android.edcollection.adding.viewholder.CabinetOrBoxViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/**
 * 分接箱、环网柜
 * 
 * @author lyh
 * @version 创建时间：2016年12月19日 上午9:17:05
 * 
 */
public class CabinetOrBoxActivity extends
		BaseActivity<CabinetOrBoxControll, CabinetOrBoxViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.cabinet_or_box);
	}

}
