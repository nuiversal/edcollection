package com.winway.android.edcollection.test;

import java.io.File;

import com.winway.android.edcollection.login.activity.LoginActivity;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.ewidgets.net.service.impl.LineServiceImpl;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.FileUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FileUploadTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Button btn = new Button(this);
		setContentView(btn);
		btn.setText("上传文件");
		final File file = new File(FileUtil.getInnerSDCardDir().getAbsolutePath() + "/hello.jpg");
		final LineServiceImpl line = new LineServiceImpl();
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AndroidBasicComponentUtils.launchActivityForResult(FileUploadTest.this,
						AttachmentTest.class, null, null, 0);
				/*try {
					line.uploadFile("http://192.168.10.173:8080/vr-web/file/upload", file, "file",
							null, new RequestCallBack<String>() {
				
								@Override
								public void error(int code) {
									Log.i("info", "error " + code);
								}
				
								@Override
								public void callBack(String request) {
									Log.i("info", "" + request);
								}
							});
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
		});
	}
}
