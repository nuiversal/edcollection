package com.winway.android.edcollection.adding.activity;

import android.os.Bundle;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelEditControll;
import com.winway.android.edcollection.adding.viewholder.ChannelEditViewholder;
import com.winway.android.edcollection.base.view.BaseActivity;

/** 通道修改
 * @author lyh
 * @version 创建时间：2017年4月1日 
 *
 */
public class ChannelEditActivity extends BaseActivity<ChannelEditControll, ChannelEditViewholder>{

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.update_channel);
	}
}
