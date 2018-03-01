package com.winway.android.ewidgets.apkversion;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.winway.android.ewidgets.R;

/**
 * @author lyh
 */
public class UpdateApkDialogFragment extends DialogFragment {
	private String message;
	private ProgressDialog m_progressDlg;
	private UpdataApkConfig config;
	private UpdateApkManager updateManager;
	private Activity mActivity;

	public UpdateApkDialogFragment(String message, ProgressDialog m_progressDlg,Activity mActivity) {
		super();
		this.message = message;
		this.m_progressDlg = m_progressDlg;
		this.mActivity = mActivity;
		config = new UpdataApkConfig();
		updateManager = new UpdateApkManager(config, mActivity);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity());
		// 设置弹窗动画
		dialog.getWindow().getAttributes().windowAnimations = R.style.Animation_CustomDialog;
		// 设置窗口不显示标题
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		// 设置窗口布局
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		dialog.setContentView(R.layout.dialog_custom);
		// 设置窗口背景颜色
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// 设置点窗口外面是否可以关闭窗口
		dialog.setCanceledOnTouchOutside(true);
		// 弹窗中显示的内容
		TextView message = (TextView) dialog.findViewById(R.id.message);
		// 要显示的文字
		message.setText(this.message);
		// 如果当前是最新版本，则把确定按钮隐藏，否则显示
		if (!UpdateApkActivityMain.isNeddUpdate) {
			dialog.findViewById(R.id.positive_button).setVisibility(View.GONE);
		} else {
			dialog.findViewById(R.id.positive_button).setVisibility(View.VISIBLE);
			// 点击确定按钮
			dialog.findViewById(R.id.positive_button).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					m_progressDlg.setTitle("正在下载");
					m_progressDlg.setMessage("请稍候...");
					updateManager.downFile(config.serverApkAddress, m_progressDlg); // 开始下载
					dismiss();
				}
			});
		}

		// 取消按钮
		dialog.findViewById(R.id.positive_button_1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				skipActivity(UpdateApkActivityMain.tagerClass);
			}
		});

		// 点击关闭窗口按钮
		dialog.findViewById(R.id.close_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				skipActivity(UpdateApkActivityMain.tagerClass);
			}
		});
		return dialog;
	}
	/**
	 * 界面跳转
	 * @param tagerClass 目标activity
	 */
	private void skipActivity(Class<?> tagerClass) {
		Intent intent = new Intent(mActivity, tagerClass);
		mActivity.startActivity(intent);
		mActivity.finish();
	}
}