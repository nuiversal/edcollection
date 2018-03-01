package com.winway.android.sensor.rfid;

import java.io.Serializable;

import com.winway.android.util.LogUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * 此工具类，使操作蓝牙模块变得更简单
 * 
 * @author mr-lao
 * 
 */
public class RFIDUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int BLUETOOTH_CONNECTION_BEGIN = 1111111;
	public static final int BLUETOOTH_CONNECTION_END = 1111112;
	private String TAG = "rfid-model";

	private Activity context;
	private RFIDReaderService rfidServer;

	public interface RFIDResultListener {
		void onResult(int what, String msg);
	}

	private RFIDResultListener rfResultListener;

	public void setRFIDResultListener(RFIDResultListener rfResultListener) {
		this.rfResultListener = rfResultListener;
	}

	@SuppressLint("HandlerLeak")
	public final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RFIDReaderService.MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case RFIDReaderService.STATE_CONNECTED:
					// 正在连接
					LogUtil.i(TAG, "蓝牙正在连接");
					break;
				case RFIDReaderService.STATE_CONNECTING:
					// 蓝牙已经连接
					LogUtil.i(TAG, "蓝牙已经连接");
					break;
				case RFIDReaderService.STATE_LISTEN:
					LogUtil.i(TAG, "state_listen");
					break;
				case RFIDReaderService.STATE_NONE:
					// 找不到蓝牙设备
					LogUtil.i(TAG, "找不到蓝牙设备");
					break;
				}
				break;
			case RFIDReaderService.MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				String writeMessage = DataProcessUtil.byteToHexString(writeBuf);
				LogUtil.i(TAG, "蓝牙模块发送消息 ：" + writeMessage);
				break;
			case RFIDReaderService.MESSAGE_READ:
				// 接收到的数据
				byte[] readBuf = (byte[]) msg.obj;
				String processData = DataProcessUtil.processData(readBuf, msg.arg1);
				if (processData == null) {
					// 数据格式不正确
				} else {
					// 数据格式符合要求
					if (null != rfResultListener) {
						rfResultListener.onResult(RFIDReaderService.MESSAGE_READ, processData);
					}
					stopScanf = true;
				}
				LogUtil.i(TAG, "接收到数据：" + processData);
				break;
			case RFIDReaderService.MESSAGE_DEVICE_NAME:
				// Log.i("info", "蓝牙连接名：");
				break;
			case RFIDReaderService.MESSAGE_TOAST:
				// Toast.makeText(context,
				// ""+msg.getData().getString(RFIDReaderService.TOAST),
				// Toast.LENGTH_SHORT).show();
				// 10-11 11:10:17.800: I/info(16999): handler : 无法连接设备
				LogUtil.i(TAG, "handler : " + msg.getData().getString(RFIDReaderService.TOAST));
				if (context instanceof UtilActivity) {
					((UtilActivity) context).faildMessage();
				}
				setSingletonNull();
				break;
			}
		}
	};

	public RFIDUtil(Activity ac) {
		this.context = ac;
	}

	private RFIDUtil() {
	}

	static RFIDUtil rfidUtil = null;

	public static RFIDUtil getSingleton(String addr) {
		if (null != addr) {
			synchronized ("lock") {
				if (null == rfidUtil) {
					rfidUtil = new RFIDUtil();
					rfidUtil.start(addr);
				}
			}
		}
		singleListener = null;
		return rfidUtil;
	}

	public static RFIDUtil getSingleton(String addr, SingletonNullListener listener) {
		if (null != addr) {
			synchronized ("lock") {
				if (null == rfidUtil) {
					rfidUtil = new RFIDUtil();
					singleListener = listener;
					rfidUtil.start(addr);
				}
			}
		} else {
			singleListener = listener;
		}
		return rfidUtil;
	}

	public static void setSingletonNull() {
		rfidUtil = null;
		if (null != singleListener) {
			singleListener.singleIsNull();
		}
	}

	private static SingletonNullListener singleListener = null;

	public void setSingletonNullListener(SingletonNullListener listener) {
		singleListener = listener;
	}

	public interface SingletonNullListener {
		public void singleIsNull();
	}

	public static final int INIT_CODE_NODEVICE = 0;
	public static final int INIT_CODE_ENABLE_DEVICE = 1;
	public static final int INIT_CODE_SUCCESS = 2;

	public int init() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(context, "蓝牙不可用", Toast.LENGTH_SHORT).show();
			return INIT_CODE_NODEVICE;
		}
		if (!mBluetoothAdapter.isEnabled()) {
			// 打开蓝牙
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			context.startActivityForResult(enableIntent, BLUETOOTH_CONNECTION_BEGIN);
			return INIT_CODE_ENABLE_DEVICE;
		}
		return INIT_CODE_SUCCESS;
	}

	// 连接蓝牙设备
	public void connecteBluetooth() {
		Intent serverIntent = new Intent(context, DeviceListActivity.class);
		context.startActivityForResult(serverIntent, BLUETOOTH_CONNECTION_END);
	}

	// 扫描
	public boolean stopScanf = false;

	// 扫描
	public void scanfRFID() {
		stopScanf = false;
		new Thread() {
			@Override
			public void run() {
				while (!stopScanf) {
					String command = "AA09200000000002020455";
					rfidServer.write(DataProcessUtil.hexStringToBytes(command));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {

					}
				}
			}
		}.start();
	}

	// 停止
	public void stop() {
		rfidServer.stop();
	}

	// 启动
	public void start(String addr) {
		if (null == rfidServer) {
			rfidServer = new RFIDReaderService(context, handler);
		}
		if (rfidServer.getState() == RFIDReaderService.STATE_NONE) {
			// 连接
			BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(addr);
			rfidServer.connect(device);
		}
	}
}
