package com.winway.android.edcollection.base.view;

import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.util.TypeUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<T extends BaseFragmentControll<X>, X extends BaseViewHolder> extends Fragment {

	protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = initView(inflater, container, savedInstanceState);
		Class<T> clazz = TypeUtil.getTypeClass(this);
		try {
			this.action = clazz.newInstance();
			action.initBusiness(getActivity(), v, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	protected T action;

	/**
	 * 获取片段对应的FragmentControll
	 * @return
	 */
	public T getAction() {
		return this.action;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		action.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		action.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		action.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		action.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		action.onStop();
	}

}
