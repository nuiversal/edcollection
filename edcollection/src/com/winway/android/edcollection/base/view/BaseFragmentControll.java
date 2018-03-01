package com.winway.android.edcollection.base.view;

import com.lidroid.xutils.ViewUtils;
import com.winway.android.edcollection.base.BaseViewHolder;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
/**
 * Fragment控制器，BaseFragment特有的
 * @author bobo
 *
 * @param <T>
 */
public abstract class BaseFragmentControll<T extends BaseViewHolder> extends BaseControll<T> {
	
	protected View mView;
	
	protected Fragment mFragment;
	
	protected void initBusiness(Activity activity, View view,Fragment fragment){
		this.mActivity = activity;
		this.mView = view;
		this.mFragment=fragment;
		ViewUtils.inject(this.viewHolder, view);
		ViewUtils.inject(this, view);
		init();
	}
	
}
