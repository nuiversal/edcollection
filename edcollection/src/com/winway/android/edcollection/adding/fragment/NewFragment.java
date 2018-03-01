package com.winway.android.edcollection.adding.fragment;


import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.NewFragmentControll;
import com.winway.android.edcollection.adding.viewholder.NewFragmentViewHolder;
import com.winway.android.edcollection.base.view.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 新增
 * 
 * @author zgq
 *
 */
public class NewFragment extends BaseFragment<NewFragmentControll, NewFragmentViewHolder>{

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new, container, false);
		return view;
	}
}
