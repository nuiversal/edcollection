package com.winway.android.sensor.rfid;

import com.winway.android.sensor.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * 用来打开蓝牙并连接蓝牙模块的过度性质的Activity（使用的时候，需要在清单文件中注册Activity）
 * 
 * @author mr-lao
 *
 */

public class UtilActivity extends Activity {
	public static RFIDUtil util;
	public static final String RFIDUTIL = "RFIDUTIL";
	public static final int resultCode_UtilActivity = 0x112;// 结果码
	public static final int requestCode_UtilActivity = 0x200;// 请求码
	public static final int connect_faild_UtilActivity = 12000;// 结果码
	private TextView msgTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_bluetooth);
		msgTV = (TextView) findViewById(R.id.message);
		util = new RFIDUtil(this);
		int init = util.init();
		if (RFIDUtil.INIT_CODE_NODEVICE == init) {
			finish();
		} else if (RFIDUtil.INIT_CODE_SUCCESS == init) {
			util.connecteBluetooth();
		}
	}

	private Handler h = new Handler();
	boolean faild = false;

	public void faildMessage() {
		faild = true;
	}

	void resultFaild() {
		util = null;
		setResult(connect_faild_UtilActivity);
		finish();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			msgTV.setText("正在连接蓝牙中。。。");
			if (requestCode == RFIDUtil.BLUETOOTH_CONNECTION_BEGIN) {
				// 连接蓝牙
				util.connecteBluetooth();
			}
			if (requestCode == RFIDUtil.BLUETOOTH_CONNECTION_END) {
				String addr = data.getStringExtra("device_address");
				// 启动服务器
				util.start(addr);
				h.postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (!faild) {
							setResult(resultCode_UtilActivity);
							finish();
						} else {
							resultFaild();
						}
					}
				}, 1000 * 8);
			}
		} else {
			resultFaild();
		}
	}
}
