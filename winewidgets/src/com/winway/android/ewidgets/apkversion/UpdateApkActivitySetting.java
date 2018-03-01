package com.winway.android.ewidgets.apkversion;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.winway.android.ewidgets.R;
import com.winway.android.util.ToastUtils;

/**
 * 校验是否需要更新新版本,一般是在设置界面中检查更新时调用此类
 * 调用此类需传UpdataApkConfig（config）参数过来。
 * 注：UpdataApkConfig类中的属性都必须赋值
 * 
 * @author lyh
 *
 */
public class UpdateApkActivitySetting extends FragmentActivity {
	private TextView tvVersionCode;
	private TextView tvVersionName;
	private Button m_btnCheckNewestVersion;
	private ProgressDialog m_progressDlg;
	private UpdataApkConfig config;
	private UpdateApkManager updateManager;
	public boolean isMainInto = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apk_version);
		Intent intent = getIntent();
		config = (UpdataApkConfig)intent.getSerializableExtra("config");
		isMainInto = (Boolean) intent.getBooleanExtra("isMainInto", false);
		if (config.localApkPath == null && 
				config.serverApkAddress == null && config.serverDataAddress == null) {
			ToastUtils.show(this, "参数未配置!");
			return;
		}
		updateManager = new UpdateApkManager(config,this);
		// 初始化相关变量
		initVariable();
	}

	private void initVariable() {
		m_btnCheckNewestVersion = (Button) findViewById(R.id.btn_check_version);
		tvVersionCode = (TextView) findViewById(R.id.tv_version_code);
		tvVersionName = (TextView) findViewById(R.id.tv_version_name);
		tvVersionCode.setText("当前版本号 : "+config.localVerCode);
		tvVersionName.setText("当前版本名称 : "+config.localVerName);
		m_progressDlg = new ProgressDialog(this);
		m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
		m_progressDlg.setIndeterminate(false);
		m_btnCheckNewestVersion.setOnClickListener(btnClickListener);
	}

	OnClickListener btnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new CheckNewestVersionAsyncTask().execute();
		}
	};

	class CheckNewestVersionAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (updateManager.postCheckNewestVersionCommand2Server()) {
				if (UpdateApkActivityMain.serverVerCode > config.localVerCode) {
					UpdateApkActivityMain.isNeddUpdate = true;
					return true;
				} else {
					UpdateApkActivityMain.isNeddUpdate = false;
					return false;
				}
			}
			UpdateApkActivityMain.isNeddUpdate = false;
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if (result) {// 如果有最新版本
				doNewVersionUpdate(); // 更新新版本
			} else {
				if (!isMainInto) { //如果不是从主界面进来则
					notNewVersionDlgShow(); // 提示当前为最新版本
				}
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
	 * 提示当前为最新版本
	 */
	private void notNewVersionDlgShow() {
		String str = "当前版本已是最新版,无需更新!";
		UpdateApkDialogFragment dialog = new UpdateApkDialogFragment(str,m_progressDlg,this);
        dialog.show(getSupportFragmentManager(), "dialog");
        //原生dialog
//		Dialog dialog = new AlertDialog.Builder(this).setTitle("软件更新").setMessage(str)// 设置内容
//				.setPositiveButton("确定",// 设置确定按钮
//						new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								finish();
//							}
//						}).create();// 创建
//		// 显示对话框
//		dialog.show();
	}

	/**
	 * 提示更新新版本
	 */
	private void doNewVersionUpdate() {
		
		String str = "当 前 版 本 ：" + config.localVerName + "  版本号：" + config.localVerCode + "\n" + "发现新版本： " + UpdateApkActivityMain.serverVerName + " 版本号："
				+ UpdateApkActivityMain.serverVerCode + "\n" +"           是否更新？";
		 UpdateApkDialogFragment dialog = new UpdateApkDialogFragment(str,m_progressDlg,this);
         dialog.show(getSupportFragmentManager(), "dialog");
         //原生dialog
//		Dialog dialog = new AlertDialog.Builder(this).setTitle("软件更新").setMessage(str)
//		// 设置内容
//				.setPositiveButton("更新",// 设置确定按钮
//						new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								m_progressDlg.setTitle("正在下载");
//								m_progressDlg.setMessage("请稍候...");
//								downFile(config.updateSoftAddress); // 开始下载
//							}
//						}).setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int whichButton) {
//						// 点击"取消"按钮之后退出程序
//						finish();
//					}
//				}).create();// 创建
//		// 显示对话框
//		dialog.show();
	}
}
