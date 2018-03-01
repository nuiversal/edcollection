package com.winway.android.edcollection.adding.fragment;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelControll;
import com.winway.android.edcollection.adding.viewholder.ChannelViewHolder;
import com.winway.android.edcollection.base.view.BaseFragment;
import com.winway.android.util.LogUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通道采集
 * 
 * @author zgq
 *
 */
public class ChannelFragment extends BaseFragment<ChannelControll, ChannelViewHolder> {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_channel, container, false);
		return view;
	}

}
