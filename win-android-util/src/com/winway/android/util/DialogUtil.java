package com.winway.android.util;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * Created by mr-lao on 2015/9/6.
 */
public class DialogUtil {
	private Activity mActivity;
	private Dialog mDialog;

	public DialogUtil(Activity activity) {
		mActivity = activity;
	}

	public void showAlertDialog(android.view.View view) {
		if (null != mDialog) {
			if (mDialog.isShowing()) {
				return;
			}
			mDialog.show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			if (null == view) {
				builder.setMessage("错误，请设置View");
				builder.setPositiveButton("确定", null);
			} else {
				mDialog = builder.create();
				mDialog.show();
				mDialog.getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				Window window = mDialog.getWindow();
				window.setContentView(view);
			}
			mDialog.show();
		}
	}

	public void showAlertDialog(android.view.View view, int width) {
		if (null != mDialog) {
			if (mDialog.isShowing()) {
				return;
			}
			mDialog.show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			if (null == view) {
				builder.setMessage("错误，请设置View");
				builder.setPositiveButton("确定", null);
			} else {
				mDialog = builder.create();
				mDialog.show();
				mDialog.getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				Window window = mDialog.getWindow();
				window.setContentView(view);
				LayoutParams attributes = mDialog.getWindow().getAttributes();
				attributes.width = width;
				mDialog.getWindow().setAttributes(attributes);
			}
			mDialog.show();
		}
	}

	public void showAlertDialog(android.view.View view, boolean canInputText) {
		if (null != mDialog) {
			if (mDialog.isShowing()) {
				return;
			}
			mDialog.show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			if (null == view) {
				builder.setMessage("错误，请设置View");
				builder.setPositiveButton("确定", null);
			} else {
				mDialog = builder.create();
				mDialog.show();
				if (canInputText) {
					mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
							| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
					mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				}
				Window window = mDialog.getWindow();
				window.setContentView(view);
			}
			mDialog.show();
		}
	}

	public void showAlertDialog(String title, String cancle, DialogInterface.OnClickListener cancleC, String enter,
			DialogInterface.OnClickListener enterC) {
		if (null != mDialog) {
			if (mDialog.isShowing()) {
				return;
			}
			mDialog.show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			builder.setTitle(title);
			builder.setPositiveButton(enter, enterC);
			builder.setNegativeButton(cancle, cancleC);
			mDialog = builder.create();
			mDialog.show();
		}
	}
	
	/**
	 * 对话框
	 * @param title
	 * @param cancle
	 * @param cancleC
	 * @param enter
	 * @param enterC
	 * @param theme
	 */
	public void showAlertDialog(String title, String cancle, DialogInterface.OnClickListener cancleC, String enter,
			DialogInterface.OnClickListener enterC,int theme) {
		if (null != mDialog) {
			if (mDialog.isShowing()) {
				return;
			}
			mDialog.show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity,theme);
			builder.setTitle(title);
			builder.setPositiveButton(enter, enterC);
			builder.setNegativeButton(cancle, cancleC);
			mDialog = builder.create();
			mDialog.show();
		}
	}

	public void showAlertDialog(List<String> datas, DialogInterface.OnClickListener listener, boolean dataChage) {
		String[] ds = new String[datas.size()];
		for (int i = 0; i < ds.length; i++) {
			ds[i] = datas.get(i);
		}
		showAlertDialog(ds, listener, dataChage);
	}

	public void showAlertDialog(String title, List<String> datas, DialogInterface.OnClickListener listener,
			boolean dataChage) {
		String[] ds = new String[datas.size()];
		for (int i = 0; i < ds.length; i++) {
			ds[i] = datas.get(i);
		}
		showAlertDialog(title, ds, listener, dataChage);
	}

	public void showAlertDialog(String title, String message, List<String> datas, DialogInterface.OnClickListener listener,
			boolean dataChage) {
		String[] ds = new String[datas.size()];
		for (int i = 0; i < ds.length; i++) {
			ds[i] = datas.get(i);
		}
		showAlertDialog(title, message, ds, listener, dataChage);
	}

	public void showAlertDialog(String[] datas, DialogInterface.OnClickListener listener, boolean dataChage) {
		if (dataChage) {
			mDialog = null;
		}
		if (null != mDialog) {
			mDialog.show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			builder.setItems(datas, listener);
			mDialog = builder.create();
			mDialog.show();
		}
	}

	public void showAlertDialog(String title, String[] datas, DialogInterface.OnClickListener listener, boolean dataChage) {
		if (dataChage) {
			mDialog = null;
		}
		if (null != mDialog) {
			mDialog.show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			builder.setTitle(title);
			builder.setItems(datas, listener);
			mDialog = builder.create();
			mDialog.show();
		}
	}

	public void showAlertDialog(String title, String message, String[] datas, DialogInterface.OnClickListener listener,
			boolean dataChage) {
		if (dataChage) {
			mDialog = null;
		}
		if (null != mDialog) {
			if (mDialog.isShowing()) {
				return;
			}
			mDialog.show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			builder.setTitle(title);
			builder.setMessage(message);
			builder.setItems(datas, listener);
			mDialog = builder.create();
			mDialog.show();
		}
	}

	public void showDialog(android.view.View view) {
		if (null != mDialog) {
			if (mDialog.isShowing()) {
				return;
			}
			mDialog.show();
		} else {
			mDialog = new Dialog(mActivity);
			mDialog.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			mDialog.show();
			Window window = mDialog.getWindow();
			if (view != null) {
				window.setContentView(view);
			}
			mDialog.show();
		}
	}

	public void showMutiChooiceDialog(String[] items, String title, boolean[] checkedItems,
			final OnMultiChoiceClickListener listener) {
		if (null == mDialog) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			builder.setTitle(title);
			builder.setMultiChoiceItems(items, checkedItems, listener);
			mDialog = builder.create();
		}
		mDialog.show();
	}

	public void dialogOutsideTouchCancel(boolean cancel) {
		mDialog.setCanceledOnTouchOutside(cancel);
	}

	public void dismissDialog() {
		if (null != mDialog) {
			mDialog.dismiss();
		}
	}

	public void destroy() {
		mActivity = null;
		mDialog = null;
	}
}
