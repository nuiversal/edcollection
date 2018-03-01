package com.winway.android.edcollection.damage.fragment;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseFragment;
import com.winway.android.edcollection.damage.controll.DamageControll;
import com.winway.android.edcollection.damage.viewholder.DamageViewHolder;
import com.winway.android.edcollection.task.controll.TaskListControll;
import com.winway.android.edcollection.task.viewholder.TaskListViewHolder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 隐患
 * 
 * @author zgq
 *
 */
public class DamageFragment extends BaseFragment<DamageControll, DamageViewHolder> {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.damage_collect, container, false);
		return view;
	}

}
