package com.winway.android.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 自定义进度条
 * 
 * @author zgq
 * 
 */
public class ProgressUtils {

	private static ProgressDialog dialog;// 进度对话框
	private static ProgressUtils instance;

	private ProgressUtils() {
	}

	public static ProgressUtils getInstance() {
		if (instance == null) {
			synchronized (ProgressUtils.class) {
				if (instance == null) {
					instance = new ProgressUtils();
				}
			}
		}
		return instance;
	}

	/**
	 * 显示等候框
	 * 
	 * @param txt 提示文本
	 * @param cancelabled 是否可取消
	 * @param context
	 */

	public void show(String txt, boolean cancelabled, Context context) {
		cancel();
		dismiss();
		dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(cancelabled);
		dialog.setMessage(txt);
		dialog.show();
	}

	/**
	 * 取消
	 */
	public void cancel() {
		if (dialog != null && dialog.isShowing()) {
			dialog.cancel();
		}
		dialog = null;
	}

	/**
	 * 销毁等候框
	 */
	public void dismiss() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog = null;
	}

	public interface onProgressDialogCancelListener {
		public void onCancle();
	}
}
