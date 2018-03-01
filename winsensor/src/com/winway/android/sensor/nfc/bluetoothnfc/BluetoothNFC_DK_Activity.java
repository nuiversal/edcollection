package com.winway.android.sensor.nfc.bluetoothnfc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.dk.bleNfc.BleNfcDeviceService;
import com.dk.bleNfc.BleManager.BleManager;
import com.dk.bleNfc.BleManager.Scanner;
import com.dk.bleNfc.BleManager.ScannerCallback;
import com.dk.bleNfc.DeviceManager.BleNfcDevice;
import com.dk.bleNfc.DeviceManager.ComByteManager;
import com.dk.bleNfc.DeviceManager.DeviceManager;
import com.dk.bleNfc.DeviceManager.DeviceManagerCallback;
import com.dk.bleNfc.Exception.CardNoResponseException;
import com.dk.bleNfc.Exception.DeviceNoResponseException;
import com.dk.bleNfc.Tool.StringTool;
import com.dk.bleNfc.card.CpuCard;
import com.dk.bleNfc.card.FeliCa;
import com.dk.bleNfc.card.Iso14443bCard;
import com.dk.bleNfc.card.Iso15693Card;
import com.dk.bleNfc.card.Mifare;
import com.dk.bleNfc.card.Ntag21x;
import com.winway.android.sensor.R;
import com.winway.android.sensor.nfc.ByteHexUtil;
import com.winway.android.sensor.nfc.NFCActivity;
import com.winway.android.util.ToastUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * 使用方法：
 * 1、注册service
 *    <service android:name="com.dk.bleNfc.BleNfcDeviceService" />
 * 2、注册activity
 *    <activity
            android:name="com.winway.android.sensor.nfc.bluetoothnfc.BluetoothNFC_DK_Activity"/>
   3、启动ActivityForResult
 * 用于读取高频电子标签（符合NFC标签的），可以使用手机自带功能读取，也可以通过蓝牙接连DK模块读取
 * DK模块是深圳德科科技公司生产的，支持1443、1556等，建议开发者使用此模块
 * 注意，我们公司winway目前拥有两种高频标签，都是符合NFC标签的，一种是白色陶瓷标签（1556协议），另一种黄色的扎带的塑料标签（1443协议）
 * @date 2017-06-15
 * @author mr-lao
 *
 */
public class BluetoothNFC_DK_Activity extends NFCActivity {
	public static final String RESULT_DATA_KEY = "RESULT_DATA_KEY";
	boolean isInDevelopment = false;

	private BleNfcDevice bleNfcDevice;
	private Scanner mScanner;
	private BluetoothDevice mNearestBle = null;
	private Lock mNearestBleLock = new ReentrantLock();// 锁对象
	private int lastRssi = -100;

	private final ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder service) {
			BleNfcDeviceService mBleNfcDeviceService = ((BleNfcDeviceService.LocalBinder) service).getService();
			bleNfcDevice = mBleNfcDeviceService.bleNfcDevice;
			mScanner = mBleNfcDeviceService.scanner;
			mBleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
			mBleNfcDeviceService.setScannerCallback(scannerCallback);
			// 开始搜索设备
			searchNearestBleDevice();
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {

		}
	};

	// 搜索最近的设备并连接
	private void searchNearestBleDevice() {
		if (!mScanner.isScanning() && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					synchronized (this) {
						mScanner.startScan(0);
						mNearestBleLock.lock();
						try {
							mNearestBle = null;
						} finally {
							mNearestBleLock.unlock();
						}
						lastRssi = -100;

						int searchCnt = 0;
						while ((mNearestBle == null) && (searchCnt < 10000) && (mScanner.isScanning())
								&& (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
							searchCnt++;
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						if (mScanner.isScanning() && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							mScanner.stopScan();
							mNearestBleLock.lock();
							try {
								if (mNearestBle != null) {
									mScanner.stopScan();
									// msgBuffer.append("正在连接设备...");
									bleNfcDevice.requestConnectBleDevice(mNearestBle.getAddress());
								} else {
									// msgBuffer.append("未找到设备！");
								}
							} finally {
								mNearestBleLock.unlock();
							}
						} else {
							mScanner.stopScan();
						}
					}
				}
			}).start();
		}
	}

	// Scanner 回调
	private ScannerCallback scannerCallback = new ScannerCallback() {
		@Override
		public void onReceiveScanDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
			super.onReceiveScanDevice(device, rssi, scanRecord);
			if (Build.VERSION.SDK_INT >= 21) { // StringTool.byteHexToSting(scanRecord.getBytes())
				System.out.println("Activity搜到设备：" + device.getName() + " 信号强度：" + rssi + " scanRecord："
						+ StringTool.byteHexToSting(scanRecord));
			}
			// 搜索蓝牙设备并记录信号强度最强的设备
			if ((scanRecord != null) && (StringTool.byteHexToSting(scanRecord).contains("017f5450"))) { // 从广播数据中过滤掉其它蓝牙设备
				// msgBuffer.append("搜到设备：").append(device.getName()).append("
				// 信号强度：").append(rssi).append("\r\n");
				if (mNearestBle != null) {
					if (rssi > lastRssi) {
						mNearestBleLock.lock();
						try {
							mNearestBle = device;
						} finally {
							mNearestBleLock.unlock();
						}
					}
				} else {
					mNearestBleLock.lock();
					try {
						mNearestBle = device;
					} finally {
						mNearestBleLock.unlock();
					}
					lastRssi = rssi;
				}
			}
		}

		@Override
		public void onScanDeviceStopped() {
			super.onScanDeviceStopped();
		}
	};

	// 设备操作类回调
	private DeviceManagerCallback deviceManagerCallback = new DeviceManagerCallback() {
		@Override
		public void onReceiveConnectBtDevice(boolean blnIsConnectSuc) {
			super.onReceiveConnectBtDevice(blnIsConnectSuc);
			if (blnIsConnectSuc) {
				// 连接上后延时500ms后再开始发指令
				try {
					Thread.sleep(500L);
					handler.sendEmptyMessage(CODE_DEVICE_CONECTION_SUCCESS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onReceiveDisConnectDevice(boolean blnIsDisConnectDevice) {
			super.onReceiveDisConnectDevice(blnIsDisConnectDevice);
			System.out.println("Activity设备断开链接");
		}

		@Override
		public void onReceiveConnectionStatus(boolean blnIsConnection) {
			super.onReceiveConnectionStatus(blnIsConnection);
			System.out.println("Activity设备链接状态回调");
		}

		@Override
		public void onReceiveInitCiphy(boolean blnIsInitSuc) {
			super.onReceiveInitCiphy(blnIsInitSuc);
		}

		@Override
		public void onReceiveDeviceAuth(byte[] authData) {
			super.onReceiveDeviceAuth(authData);
		}

		@Override
		// 寻到卡片回调
		public void onReceiveRfnSearchCard(boolean blnIsSus, int cardType, byte[] bytCardSn, byte[] bytCarATS) {
			super.onReceiveRfnSearchCard(blnIsSus, cardType, bytCardSn, bytCarATS);
			if (!blnIsSus || cardType == BleNfcDevice.CARD_TYPE_NO_DEFINE) {
				return;
			}
			System.out.println("Activity接收到激活卡片回调：UID->" + StringTool.byteHexToSting(bytCardSn) + " ATS->"
					+ StringTool.byteHexToSting(bytCarATS));
			final int cardTypeTemp = cardType;
			new Thread(new Runnable() {
				@Override
				public void run() {
					boolean isReadWriteCardSuc = false;
					try {
						if (bleNfcDevice.isAutoSearchCard()) {
							// 如果是自动寻卡的，寻到卡后，先关闭自动寻卡
							bleNfcDevice.stoptAutoSearchCard();
							isReadWriteCardSuc = readWriteCardDemo(cardTypeTemp);
							// 读卡结束，重新打开自动寻卡
							startAutoSearchCard();
						}
						// 打开蜂鸣器提示读卡完成
						if (isReadWriteCardSuc) {
							bleNfcDevice.openBeep(50, 50, 3); // 读写卡成功快响3声
						} else {
							bleNfcDevice.openBeep(100, 100, 2); // 读写卡失败慢响2声
						}
					} catch (DeviceNoResponseException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}

		@Override
		public void onReceiveRfmSentApduCmd(byte[] bytApduRtnData) {
			super.onReceiveRfmSentApduCmd(bytApduRtnData);
			System.out.println("Activity接收到APDU回调：" + StringTool.byteHexToSting(bytApduRtnData));
		}

		@Override
		public void onReceiveRfmClose(boolean blnIsCloseSuc) {
			super.onReceiveRfmClose(blnIsCloseSuc);
		}

		@Override
		// 按键返回回调
		public void onReceiveButtonEnter(byte keyValue) {
			if (keyValue == DeviceManager.BUTTON_VALUE_SHORT_ENTER) { // 按键短按
				System.out.println("Activity接收到按键短按回调");
			} else if (keyValue == DeviceManager.BUTTON_VALUE_LONG_ENTER) { // 按键长按
				System.out.println("Activity接收到按键长按回调");
			}
		}
	};

	// 开始自动寻卡
	private void startAutoSearchCard() throws DeviceNoResponseException {
		// 打开自动寻卡，200ms间隔，寻M1/UL卡
		boolean isSuc = bleNfcDevice.startAutoSearchCard((byte) 20, ComByteManager.ISO14443_P4);
		if (!isSuc) {
			ToastUtils.show(getBaseContext(), "不支持自动寻卡！");
		}
	}

	// 读写卡唯一编码信息
	private boolean readWriteCardDemo(int cardType) {
		String byteHexToSting = null;
		switch (cardType) {
		case DeviceManager.CARD_TYPE_ISO4443_B: // 寻到 B cpu卡
			final Iso14443bCard iso14443bCard = (Iso14443bCard) bleNfcDevice.getCard();
			if (iso14443bCard != null) {
				// 获取身份证DN码的指令流
				final byte[][] sfzCmdBytes = { { 0x00, (byte) 0xa4, 0x00, 0x00, 0x02, 0x60, 0x02 },
						{ 0x00, 0x36, 0x00, 0x00, 0x08 }, { (byte) 0x80, (byte) 0xB0, 0x00, 0x00, 0x20 }, };
				for (byte[] aBytes : sfzCmdBytes) {
					try {
						byte returnBytes[] = iso14443bCard.transceive(aBytes);
						byteHexToSting = ByteHexUtil.byte2hexStirng(returnBytes);
					} catch (CardNoResponseException e) {
						e.printStackTrace();
						return false;
					}
				}
			}
			break;
		case DeviceManager.CARD_TYPE_ISO4443_A: // 寻到A CPU卡
			final CpuCard cpuCard = (CpuCard) bleNfcDevice.getCard();
			if (cpuCard != null) {
				byteHexToSting = ByteHexUtil.byte2hexStirng(cpuCard.uid);
			}
			break;
		case DeviceManager.CARD_TYPE_FELICA: // 寻到FeliCa
			FeliCa feliCa = (FeliCa) bleNfcDevice.getCard();
			if (feliCa != null) {
				byte[] pServiceList = { (byte) 0x8b, 0x00 };
				byte[] pBlockList = { 0x00, 0x00, 0x00 };
				try {
					byte[] pBlockData = feliCa.read((byte) 1, pServiceList, (byte) 1, pBlockList);
					// TODO
					byteHexToSting = ByteHexUtil.byte2hexStirng(pBlockData);
				} catch (CardNoResponseException e) {
					e.printStackTrace();
					handler.sendEmptyMessage(CODE_ERROR);
					return false;
				}
			}
			break;
		case DeviceManager.CARD_TYPE_ULTRALIGHT: // 寻到Ultralight卡
			final Ntag21x ntag21x = (Ntag21x) bleNfcDevice.getCard();
			if (ntag21x != null) {
				byteHexToSting = ByteHexUtil.byte2hexStirng(ntag21x.uid);
			}
			break;
		case DeviceManager.CARD_TYPE_MIFARE: // 寻到Mifare卡
			final Mifare mifare = (Mifare) bleNfcDevice.getCard();
			if (mifare != null) {
				byteHexToSting = ByteHexUtil.byte2hexStirng(mifare.uid);
			}
			break;
		case DeviceManager.CARD_TYPE_ISO15693: // 寻到15693卡
			final Iso15693Card iso15693Card = (Iso15693Card) bleNfcDevice.getCard();
			if (iso15693Card != null) {
				// TODO
				byteHexToSting = ByteHexUtil.byte2hexStirng(iso15693Card.uid);
			}
			break;
		}
		Message msg = Message.obtain();
		msg.what = CODE_READ_SUCCESS;
		msg.obj = byteHexToSting;
		handler.sendMessage(msg);
		resultData(byteHexToSting);
		return true;
	}

	private final int CODE_ERROR = -1;
	private final int CODE_READ_SUCCESS = 0;
	private final int CODE_DEVICE_CONECTION_SUCCESS = 1;
	private final int CODE_DEVICE_POWER = 2;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == CODE_ERROR) {
				ToastUtils.show(getBaseContext(), null == msg.obj ? "读取失败" : msg.obj.toString());
			} else if (msg.what == CODE_READ_SUCCESS) {
				idText.setText(msg.obj.toString());
			} else if (msg.what == CODE_DEVICE_CONECTION_SUCCESS) {
				// 设备连接成功
				startAutoSearchCardAndGetDevicePower();
			} else if (msg.what == CODE_DEVICE_POWER) {
				powerText.setText(msg.obj.toString());
			}
		}
	};

	/**
	 * 设置设备电量和启动自动读取标签
	 */
	private void startAutoSearchCardAndGetDevicePower() {
		new Thread() {
			public void run() {
				try {
					double voltage = bleNfcDevice.getDeviceBatteryVoltage();
					String voltageStr = "设备电池电压:" + String.format("%.2f", voltage);
					if (voltage < 3.61) {
						voltageStr += ",设备电池电量低，请及时充电！";
					} else {
						voltageStr += ",设备电池电量充足！";
					}
					Message msg = Message.obtain();
					msg.what = CODE_DEVICE_POWER;
					msg.obj = voltageStr;
					handler.sendMessage(msg);
					bleNfcDevice.androidFastParams(true);
					// 开始自动寻卡
					startAutoSearchCard();
				} catch (DeviceNoResponseException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 返回结果
	 * @param data
	 */
	void resultData(String data) {
		if (isInDevelopment) {
			return;
		}
		Intent intent = new Intent();
		intent.putExtra(RESULT_DATA_KEY, data);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private TextView idText;
	private TextView powerText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth_nfc_dk);
		idText = (TextView) findViewById(R.id.textview_nfc_id);
		powerText = (TextView) findViewById(R.id.textview_device_power);
		Intent gattServiceIntent = new Intent(this, BleNfcDeviceService.class);
		bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
		setOnNFCListener(nfcListener);
		isInDevelopment = getIntent().getBooleanExtra("isInDevelopment", isInDevelopment);
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
			idText.setText(hex);
			resultData(hex);
		}

		@Override
		public void setDec(long dec) {

		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindService(mServiceConnection);
	}

	@Override
	public void onBackPressed() {
		String id = idText.getText().toString();
		if (!TextUtils.isEmpty(id)) {
			isInDevelopment = false;
			resultData(id);
		} else {
			super.onBackPressed();
		}
	}
}
