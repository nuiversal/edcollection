package com.winway.android.edcollection.adding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.MarkerCollectControll;
import com.winway.android.edcollection.adding.viewholder.MarkerCollectViewHolder;
import com.winway.android.edcollection.base.view.BaseFragmentActivity;

/**
 * 标识器采集页面
 * 
 * @author lyh
 * @version 创建时间：2016年12月12日 下午1:42:30
 * 
 */
public class MarkerCollectActivity extends BaseFragmentActivity<MarkerCollectControll, MarkerCollectViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_marker_collect);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   
	}
	
	

}
