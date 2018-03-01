package com.winway.android.edcollection.base.view;

import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.util.TypeUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

public abstract class BaseFragmentActivity<T extends BaseFragmentsControll<X>,X extends BaseViewHolder> extends FragmentActivity{

	protected T action;
	
	/**
	 * 初始化界面
	 * @param savedInstanceState
	 */
	protected abstract void initView(Bundle savedInstanceState);
	
	@SuppressWarnings({ "unchecked"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(savedInstanceState);
		Class<T> clazz = TypeUtil.getTypeClass(this);
		try {
			this.action = clazz.newInstance();
			action.initBusiness(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		action.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		action.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		action.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		action.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		action.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		action.onRestart();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		super.onKeyDown(keyCode, event);
		return action.onKeyDown(keyCode, event);
		
	}
	
}
