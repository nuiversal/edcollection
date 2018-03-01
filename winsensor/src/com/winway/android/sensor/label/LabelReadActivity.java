package com.winway.android.sensor.label;

import com.winway.android.sensor.R;
import com.winway.android.sensor.label.BluetoothUtil.BluetoothConnectListener;
import com.winway.android.sensor.label.BluetoothUtil.DeviceEntity;
import com.winway.android.sensor.nfc.NFCActivity;
import com.winway.android.sensor.rfid.RFIDUtil;
import com.winway.android.sensor.rfid.RFIDUtil.RFIDResultListener;
import com.winway.android.util.ToastUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * 标签读取Activity，注意，在使用的时候，请在AndroidManifest.xml文件中将LabelReadActivity设成成Dialog模式
 * 使用者打开此Activity的时候，请使用startActivityForResult(intent,requestCode)方法
 * @author mr-lao
 *
 */
public class LabelReadActivity extends NFCActivity {
	public static final String INTENT_KEY_LABEL_ID = "Label_ID";
	private TextView textView;
	RFIDUtil rfidUtil = null;
	BluetoothUtil bluetoothUtil;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_label_read);
		setOnNFCListener(nfcListener);
		textView = (TextView) findViewById(R.id.text);
		// 启动动画
		runReadingAnimation();
		// 蓝牙
		if (!BluetoothUtil.isEnableBluetooth()) {
			return;
		}
		try {
			rfidUtil = RFIDUtil.getSingleton(null);
			if (null != rfidUtil) {
				rfidUtil.setRFIDResultListener(rfidListent);
				rfidUtil.setSingletonNullListener(singleListener);
				rfidUtil.scanfRFID();
				return;
			}
			bluetoothUtil = new BluetoothUtil(this);
			if (bluetoothUtil.getConnectDevice() == null) {
				bluetoothUtil.startScanfBluetooth();
				bluetoothUtil.setBluetoothConnectListener(new BluetoothConnectListener() {
					@Override
					public void onConnect(final DeviceEntity device) {
						h.postAtTime(new Runnable() {
							@Override
							public void run() {
								scanfRFID(device.address);
							}
						}, 8 * 1000);
					}
				});
			} else {
				scanfRFID(bluetoothUtil.getConnectDevice().getAddress());
			}
		} catch (Exception e) {
			ToastUtils.show(this, "请连接正确的蓝牙");
			RFIDUtil.setSingletonNull();
			finish();
		}
	}

	RFIDUtil.SingletonNullListener singleListener = new RFIDUtil.SingletonNullListener() {
		@Override
		public void singleIsNull() {
			h.sendEmptyMessage(RFID_UTIL_NULL);
		}

	};

	void scanfRFID(String address) {
		try {
			rfidUtil = RFIDUtil.getSingleton(address, singleListener);
			// rfidUtil.setSingletonNullListener(singleListener);
			rfidUtil.setRFIDResultListener(rfidListent);
			rfidUtil.scanfRFID();
			ToastUtils.show(this, "蓝牙连接成功，开始读取标签数据");
		} catch (Exception e) {
			ToastUtils.show(this, "读取标签数据错误，请连接正确的蓝牙");
			e.printStackTrace();
			RFIDUtil.setSingletonNull();
			finish();
		}
	}

	/**
	 * 处理RFID读取结果
	 */
	RFIDResultListener rfidListent = new RFIDResultListener() {
		@Override
		public void onResult(int what, String msg) {
			resultLabelID(msg);
		}
	};

	@Override
	protected void onDestroy() {
		stop = true;
		if (null != bluetoothUtil) {
			bluetoothUtil.stopScanBluetooth();
		}
		if (null != rfidUtil) {
			rfidUtil.stopScanf = true;
		}
		super.onDestroy();
	}

	boolean stop = false;
	int TEXT_ANIMATION = 1;
	int RFID_UTIL_NULL = 2;
	String animationText = ".";
	@SuppressLint("HandlerLeak")
	private Handler h = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == TEXT_ANIMATION) {
				textView.setText("正在读取中" + animationText);
			} else if (msg.what == RFID_UTIL_NULL) {
				ToastUtils.show(getApplicationContext(), "蓝牙模块错误，请重新连接蓝牙模块");
				finish();
			}
		};
	};

	void runReadingAnimation() {
		new Thread() {
			public void run() {
				while (!stop) {
					h.sendEmptyMessage(TEXT_ANIMATION);
					try {
						Thread.sleep(300);
					} catch (Exception e) {
					}
					if (animationText.length() == 1) {
						animationText = "..";
					} else if (animationText.length() == 2) {
						animationText = "...";
					} else if (animationText.length() == 3) {
						animationText = "....";
					} else if (animationText.length() == 4) {
						animationText = ".....";
					} else if (animationText.length() == 5) {
						animationText = "......";
					} else if (animationText.length() == 6) {
						animationText = ".";
					}
				}
			}
		}.start();
	}

	/**
	 * 返回标签ID
	 * @param labelID
	 */
	void resultLabelID(String labelID) {
		Intent data = new Intent();
		data.putExtra(INTENT_KEY_LABEL_ID, labelID);
		setResult(Activity.RESULT_OK, data);
		this.finish();
	}

	/**
	 * 处理NFC结果
	 */
	private OnNFCListener nfcListener = new OnNFCListener() {

		@Override
		public void setReversed(long rev) {

		}

		@Override
		public void setId(byte[] id) {

		}

		@Override
		public void setHex(String hex) {
			resultLabelID(hex);
		}

		@Override
		public void setDec(long dec) {

		}
	};

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN && isOutOfBounds(this, event)) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	private boolean isOutOfBounds(Activity context, MotionEvent event) {
		final int x = (int) event.getX();
		final int y = (int) event.getY();
		final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
		final View decorView = context.getWindow().getDecorView();
		return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop)) || (y > (decorView.getHeight() + slop));
	}
}
