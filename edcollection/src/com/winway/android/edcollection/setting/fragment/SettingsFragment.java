package com.winway.android.edcollection.setting.fragment;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseFragment;
import com.winway.android.edcollection.setting.controll.SettingsFragmentControll;
import com.winway.android.edcollection.setting.viewholder.SettingsFragmentViewHolder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 设置
 * 
 * @author zgq
 *
 */
public class SettingsFragment extends BaseFragment<SettingsFragmentControll, SettingsFragmentViewHolder> {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, container, false);
		// this.fragment=this;
		return view;
	}
}
