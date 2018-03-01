package com.winway.android.sensor.marker;

import java.util.concurrent.ExecutorService;

import com.winway.android.sensor.R;
import com.winway.android.sensor.label.BluetoothUtil;
import com.winway.android.sensor.label.BluetoothUtil.BluetoothConnectListener;
import com.winway.android.sensor.label.BluetoothUtil.DeviceEntity;
import com.winway.android.sensor.rfid.RFIDUtil;
import com.winway.android.util.LogUtil;
import com.winway.android.util.ToastUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * 标识器ID读取Activity，注意，在使用的时候，请在AndroidManifest.xml文件中将MarkerReadActivity设成成Dialog模式
 * 使用者打开此Activity的时候，请使用startActivityForResult(intent,requestCode)方法
 * @author mr-lao
 *
 */
public class MarkerReadActivity extends Activity {
	public static final String INTENT_KEY_MARKER_ID = "Marker_ID";
	private TextView textView;
	BluetoothUtil bluetoothUtil;
	String tag = "marker-reader";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_label_read);
		textView = (TextView) findViewById(R.id.text);
		// 启动动画
		runReadingAnimation();
		// 蓝牙
		if (!BluetoothUtil.isEnableBluetooth()) {
			// 打开蓝牙
			BluetoothUtil.enableBluetooth();
			ToastUtils.show(getBaseContext(), "正在打开蓝牙，请过几秒钟后再读取");
			finish();
			return;
		}
		connectBluetooth();
	}

	private void connectBluetooth() {
		try {
			bluetoothUtil = new BluetoothUtil(this);
			if (bluetoothUtil.getConnectDevice() == null) {
				bluetoothUtil.startScanfBluetooth();
				bluetoothUtil.setBluetoothConnectListener(new BluetoothConnectListener() {
					@Override
					public void onConnect(DeviceEntity device) {
						startReader(device.address);
					}
				});
			} else {
				startReader(bluetoothUtil.getConnectDevice().getAddress());
			}
		} catch (Exception e) {
			ToastUtils.show(this, "请连接正确的蓝牙");
			RFIDUtil.setSingletonNull();
			finish();
		}
	}

	static ExecutorService threadPoll = java.util.concurrent.Executors.newFixedThreadPool(1);

	MarkerReader reader = null;

	MarkerReader.ReaderListener listener = new MarkerReader.ReaderListener() {
		@Override
		public void onFinish() {
			finish();
		}

		@Override
		public void onFailed(Exception e) {
			finish();
		}

		@Override
		public void onDataReceiver(int type, String data) {
			if (type == 2) {
				Message msg = Message.obtain();
				msg.obj = MarkerReader.extractMarkerID(data);
				msg.what = DATA_READ;
				h.sendMessage(msg);
			}
			LogUtil.i(tag, "type = " + type + " , data = " + data);
		}
	};

	private void startReader(String address) {
		reader = new MarkerReader(address, listener);
		threadPoll.execute(reader);
	}

	@Override
	protected void onDestroy() {
		stop = true;
		if (null != bluetoothUtil) {
			bluetoothUtil.stopScanBluetooth();
		}
		if (null != reader) {
			reader.stopRead();
		}
		super.onDestroy();
	}

	boolean stop = false;
	int TEXT_ANIMATION = 1;
	int RFID_UTIL_NULL = 2;
	int DATA_READ = 3;
	String animationText = ".";
	@SuppressLint("HandlerLeak")
	private Handler h = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == TEXT_ANIMATION) {
				textView.setText("正在读取中" + animationText);
			} else if (msg.what == RFID_UTIL_NULL) {
				ToastUtils.show(getApplicationContext(), "3M机器连接错误，请重新连接");
				finish();
			} else if (msg.what == DATA_READ) {
				resultMakerID((String) msg.obj);
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
	void resultMakerID(String markerID) {
		Intent data = new Intent();
		data.putExtra(INTENT_KEY_MARKER_ID, markerID);
		setResult(Activity.RESULT_OK, data);
		this.finish();
	}

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
