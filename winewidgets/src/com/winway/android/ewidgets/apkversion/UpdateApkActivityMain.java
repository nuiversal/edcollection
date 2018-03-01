package com.winway.android.ewidgets.apkversion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.winway.android.ewidgets.R;

/**
 * 在程序启动后调用此类，校验是否需要更新新版本
 * 调用此类时需要传更新参数配置类（config）和目标activity(tagerClass-不更新版本时跳转到的activity)传过来
 * 注：UpdataApkConfig类中的属性都必须赋值
 * @author lyh
 *
 */
public class UpdateApkActivityMain extends FragmentActivity {
	private ProgressDialog m_progressDlg;
	private UpdateApkManager updateManager;
	private UpdataApkConfig config;
	/** 是否需要更新 */
	public static boolean isNeddUpdate = false; 
	/** 目标activity */
	public static Class<?> tagerClass; 
	/** 服务器版本号 */
	public static long serverVerCode; 
	/** 服务器版本名称 */
	public static String serverVerName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apk_version);
		Intent intent = getIntent();
		tagerClass = (Class<?>)intent.getSerializableExtra("tagerClass");
		config = (UpdataApkConfig) intent.getSerializableExtra("config");
		updateManager = new UpdateApkManager(config,this);
		// 初始化相关变量
		initVariable();
	}

	private void initVariable() {
		findViewById(R.id.btn_check_version).setVisibility(View.GONE);
		findViewById(R.id.tv_version_code).setVisibility(View.GONE);
		findViewById(R.id.tv_version_name).setVisibility(View.GONE);
		m_progressDlg = new ProgressDialog(this);
		m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
		m_progressDlg.setIndeterminate(false);
		new CheckNewestVersionAsyncTask().execute();
	}

	class CheckNewestVersionAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (updateManager.postCheckNewestVersionCommand2Server()) {
				if (serverVerCode > config.localVerCode) {
					isNeddUpdate = true;
					return true;
				} else {
					isNeddUpdate = false;
					return false;
				}
			}
			isNeddUpdate = false;
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if (result) {// 如果有最新版本
				doNewVersionUpdate(); // 更新新版本
			} else {
				skipActivity(tagerClass);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}

	/**
	 * 界面跳转
	 * @param tagerClass 目标activity
	 */
	private void skipActivity(Class<?> tagerClass) {
		Intent intent = new Intent(this, tagerClass);
		this.startActivity(intent);
		this.finish();
	}

	/**
	 * 提示更新新版本
	 */
	private void doNewVersionUpdate() {
		String str = "当 前 版 本 ：" + config.localVerName + "  版本号：" + config.localVerCode + "\n" + "发现新版本： " + serverVerName + " 版本号："
				+ serverVerCode + "\n" +"           是否更新？";
		 UpdateApkDialogFragment dialog = new UpdateApkDialogFragment(str,m_progressDlg,this);
         dialog.show(getSupportFragmentManager(), "dialog");
	}
}
