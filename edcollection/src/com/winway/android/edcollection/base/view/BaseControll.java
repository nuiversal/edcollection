package com.winway.android.edcollection.base.view;

import com.lidroid.xutils.ViewUtils;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.util.TypeUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 控制层，主要用来进行界面的按键响应或跳转控制
 * 
 * @author bobo
 *
 * @param <T>
 */
public abstract class BaseControll<T extends BaseViewHolder> {
	// Activity对象，业务可以用来获得Activity中拥有的功能代码
	protected Activity mActivity;
	// ViewHolder对象，存放业务需要乃至的View控件
	protected T viewHolder;
	private Toast toast;

	public Activity getActivity() {
		return mActivity;
	}

	public T getViewHolder() {
		return viewHolder;
	}

	@SuppressWarnings({ "unchecked" })
	public BaseControll() {
		Class<T> holderClass = TypeUtil.getTypeClass(this);
		try {
			this.viewHolder = holderClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void initBusiness(Activity mActivity) {
		this.mActivity = mActivity;
		ViewUtils.inject(this.viewHolder, mActivity);
		ViewUtils.inject(this, mActivity);
		init();
	}

	/**
	 * 提示信息
	 * 
	 * @param msg
	 */
	public void showToast(String msg) {
		if (toast == null) {
			toast = Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	/**
	 * 启动一个activity
	 * 
	 * @param clazz
	 */
	@SuppressWarnings("rawtypes")
	public void startActivity(Class clazz) {
		Intent intent = new Intent(mActivity, clazz);
		mActivity.startActivity(intent);
	}

	/**
	 * 启动activity
	 * 
	 * @param intent
	 */
	public void startActivity(Intent intent) {
		mActivity.startActivity(intent);
	}

	/**
	 * 开启activity(Bundle带参数)
	 * 
	 * @param clazz
	 * @param bundle
	 */
	public void startActivity(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(mActivity, clazz);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		mActivity.startActivity(intent);
	}

	/**
	 * 当Activity调用setViewHolder或initBusiness方法 时，会调用init()方法。
	 * 子类通过实现init()方法来初始化自身的一些属性和数据，init()方法是业务类的入口方法。
	 */
	public abstract void init();

	// 申明一些与Activity中请用的方法名字相同的方法

	/**
	 * 处理从Activity跳到另外一个Activity返回来的数据（需要用到的话，子类重写此方法即可，不需要用到的话，请忽略）
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	/**
	 * 获得从另一个Activity跳转到此Activity的意图对象，借此，获得跳转Activity带来的数据
	 * 
	 * @return
	 */
	public Intent getIntent() {
		return mActivity.getIntent();
	}

	/**
	 * 请在Activity的onDestroy()方法中调用
	 */
	public void onDestroy() {

	}

	/**
	 * 请在Activity的onResume()方法中调用
	 */
	public void onResume() {

	}

	/**
	 * 请在Activity的onPause()方法中调用
	 */
	public void onPause() {

	}

	/**
	 * 请在Activity的onStop()方法中调用
	 */
	public void onStop() {

	}

	/**
	 * 请在Activity的onRestart()方法中调用
	 */
	public void onRestart() {

	}

	/**
	 * 请在Activity的onRestart()方法中调用
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}
}
