package com.winway.android.util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;

public class ProgressBarUtil {
	public static ProgressDialog mProgressDialog;// 进度条
	private static ProgressBarUtil instance;

	private ProgressBarUtil() {
	}

	public static ProgressBarUtil getInstance() {
		if (instance == null) {
			synchronized (ProgressBarUtil.class) {
				if (instance == null) {
					instance = new ProgressBarUtil();
				}
			}
		}
		return instance;
	}

	@SuppressLint("InlinedApi")
	public void showBar(Context context,String message) {
		try {
			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT); // 得到一个对象
			}
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // 设置为矩形进度条
			mProgressDialog.setTitle("提示");
			mProgressDialog.setMessage(message);
			// mProgressDialog.setIcon(R.drawable.biandianzhan_n);
			mProgressDialog.setIndeterminate(false); // 设置进度条是否为不明确
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 取消
	 */
	public void cancel() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.cancel();
		}
		mProgressDialog = null;
	}

	/**
	 * 关闭
	 */
	public void dismiss() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		mProgressDialog = null;
	}

	public interface onProgressDialogCancelListener {
		public void onCancle();
	}
}
