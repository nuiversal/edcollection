package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.DistributionRoomControll;
import com.winway.android.edcollection.adding.viewholder.DistributionRoomViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

/**
 * 配电室
 * 
 * @author lyh
 * @version 创建时间：2016年12月21日 上午10:02:48
 * 
 */
public class DistributionRoomActivity extends BaseActivity<DistributionRoomControll, DistributionRoomViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.channel_pds);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
