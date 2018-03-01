package com.winway.android.edcollection.colist.fragment;


import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseFragment;
import com.winway.android.edcollection.colist.controll.CollectedFragmentControll;
import com.winway.android.edcollection.colist.viewholder.CollectedFragmentViewHolder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 已采
 * 
 * @author zgq
 *
 */
public class CollectedFragment extends BaseFragment<CollectedFragmentControll, CollectedFragmentViewHolder> {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_collected, container, false);
		return view;
	}
}
