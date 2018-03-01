package com.winway.android.edcollection.base.view;

import java.util.List;

import com.winway.android.edcollection.base.BaseViewHolder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 拥有片段管理的控制器，BaseFragmentActivity特有的
 * 
 * @author bobo
 *
 * @param <T>
 */
public abstract class BaseFragmentsControll<T extends BaseViewHolder> extends BaseControll<T> {

	protected List<Fragment> fragments;
	protected FragmentManager fragmentManager;// 片段管理器
	protected int selectedfragment = 0;// 记录上一次的 framgment 下标

	public abstract List<Fragment> getFragments();

	public abstract int content_id();

	protected void setFragmentSelect(int i) {
		FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
		if (!fragments.get(i).isAdded()) { // 先判断是否被add过
			if(i!=selectedfragment){
				beginTransaction.hide(fragments.get(selectedfragment)); // 隐藏当前的fragment，add下一个
			}
			beginTransaction.add(content_id(), fragments.get(i));
		} else {
			beginTransaction.hide(fragments.get(selectedfragment)); // 隐藏当前的fragment，显示下一个
			beginTransaction.show(fragments.get(i));
		}
		selectedfragment = i;
		beginTransaction.commit();
	}

	@Override
	protected void initBusiness(Activity mActivity) {
		fragmentManager = ((FragmentActivity) mActivity).getSupportFragmentManager();
		fragments = getFragments();
		super.initBusiness(mActivity);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		fragments.get(selectedfragment).onActivityResult(requestCode, resultCode, data);
	}
	
	

}
