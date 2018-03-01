package com.winway.android.sensor.nfc.bluetoothnfc;

import java.io.UnsupportedEncodingException;

import com.acs.bluetooth.Acr1255uj1Reader;
import com.acs.bluetooth.Acr1255uj1Reader.OnBatteryLevelAvailableListener;
import com.acs.bluetooth.Acr1255uj1Reader.OnBatteryLevelChangeListener;
import com.acs.bluetooth.Acr3901us1Reader;
import com.acs.bluetooth.Acr3901us1Reader.OnBatteryStatusAvailableListener;
import com.acs.bluetooth.Acr3901us1Reader.OnBatteryStatusChangeListener;
import com.acs.bluetooth.BluetoothReader;
import com.acs.bluetooth.BluetoothReader.OnAtrAvailableListener;
import com.acs.bluetooth.BluetoothReader.OnAuthenticationCompleteListener;
import com.acs.bluetooth.BluetoothReader.OnCardPowerOffCompleteListener;
import com.acs.bluetooth.BluetoothReader.OnCardStatusAvailableListener;
import com.acs.bluetooth.BluetoothReader.OnCardStatusChangeListener;
import com.acs.bluetooth.BluetoothReader.OnDeviceInfoAvailableListener;
import com.acs.bluetooth.BluetoothReader.OnEnableNotificationCompleteListener;
import com.acs.bluetooth.BluetoothReader.OnEscapeResponseAvailableListener;
import com.acs.bluetooth.BluetoothReader.OnResponseApduAvailableListener;
import com.acs.bluetooth.BluetoothReaderGattCallback;
import com.acs.bluetooth.BluetoothReaderGattCallback.OnConnectionStateChangeListener;
import com.acs.bluetooth.BluetoothReaderManager;
import com.acs.bluetooth.BluetoothReaderManager.OnReaderDetectionListener;
import com.winway.android.sensor.R;
import com.winway.android.sensor.label.BluetoothUtil;
import com.winway.android.sensor.label.BluetoothUtil.BluetoothConnectListener;
import com.winway.android.sensor.label.BluetoothUtil.DeviceEntity;
import com.winway.android.sensor.nfc.NFCActivity;
import com.winway.android.util.ToastUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 用于读取高频电子标签（符合NFC标签的），可以使用手机自带功能读取，也可以通过蓝牙接连ACS模块读取
 * 此Activity连接的是ACS的高频读写模块，目前测试得，只支持1443协议的标签，不支持1556协议的标签
 * 注意，我们公司winway目前拥有两种高频标签，都是符合NFC标签的，一种是白色陶瓷标签（1556协议），另一种黄色的扎带的塑料标签（1443协议）
 * @date 2017-06-15
 * @author mr-lao
 *
 */
public class BluetoothNFC_ACS_Activity extends NFCActivity {
	public static final String KEY_NFC_ID = "KEY_NFC_ID";

	private BluetoothUtil bluetoothUtil;
	/* Detected reader. */
	private static BluetoothReader mBluetoothReader;
	/* ACS Bluetooth reader library. */
	private BluetoothReaderManager mBluetoothReaderManager;
	private BluetoothReaderGattCallback mGattCallback;
	private BluetoothGatt mBluetoothGatt;
	// 密钥
	private String master_key = null;
	private static final byte[] AUTO_POLLING_START = { (byte) 0xE0, 0x00, 0x00, 0x40, 0x01 };
	private static final byte[] AUTO_POLLING_STOP = { (byte) 0xE0, 0x00, 0x00, 0x40, 0x00 };

	private TextView nfcidTV;
	private Button initReaderBtn;
	private Button startPolingBtn;
	private Button stopPolingBtn;
	private Button getNFCIDBtn;
	private Button resultBackBtn;
	private Button connectBluetoothBtn;

	/**设备的蓝牙地址*/
	private static String device_bluetooth_adress = null;

	private static boolean isThePhoneHasNFC = true;

	private boolean isTest = false;

	@SuppressLint("NewApi")
	private void connectDevice() {
		BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
		BluetoothDevice remoteDevice = defaultAdapter.getRemoteDevice(device_bluetooth_adress);
		mBluetoothGatt = remoteDevice.connectGatt(getBaseContext(), false, mGattCallback);
		initReaderBtn.setEnabled(true);
		isThePhoneHasNFC = false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isTest = getIntent().getBooleanExtra("istest", false);
		setContentView(R.layout.activity_bluetooth_nfc);
		BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
		if (null == defaultAdapter) {
			ToastUtils.show(this, "此设备无蓝牙");
			finish();
			return;
		}
		initView();
		initEvent();
		initNFCReader();
		initBluetooth();
		setOnNFCListener(nfcListener);
	}

	private void resultOK(String nfcid) {
		if (isTest) {
			return;
		}
		Intent data = new Intent();
		data.putExtra(KEY_NFC_ID, nfcid);
		setResult(Activity.RESULT_OK, data);
		finish();
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
			nfcidTV.setText(hex);
			isThePhoneHasNFC = true;
			resultOK(hex);
		}

		@Override
		public void setDec(long dec) {

		}
	};

	/***
	 * 重置静态变量
	 */
	private static void resetStaticParams() {
		device_bluetooth_adress = null;
		mBluetoothReader = null;
	}

	@SuppressLint("NewApi")
	private void initBluetooth() {
		bluetoothUtil = new BluetoothUtil(this);
		if (TextUtils.isEmpty(device_bluetooth_adress) && !isThePhoneHasNFC) {
			bluetoothUtil.startScanfBluetooth();
		} else {
			if (!TextUtils.isEmpty(device_bluetooth_adress)) {
				connectBluetoothBtn.setText("连接蓝牙（蓝牙已经连接）");
			}
			initReaderBtn.setEnabled(false);
		}
		bluetoothUtil.setBluetoothConnectListener(new BluetoothConnectListener() {
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
			@Override
			public void onConnect(DeviceEntity device) {
				device_bluetooth_adress = device.address;
				connectDevice();
			}
		});
		mGattCallback = new BluetoothReaderGattCallback();
		/* Register BluetoothReaderGattCallback's listeners */
		mGattCallback.setOnConnectionStateChangeListener(new OnConnectionStateChangeListener() {
			@Override
			public void onConnectionStateChange(final BluetoothGatt gatt, final int state, final int newState) {
				runOnUiThread(new Runnable() {
					@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
					@Override
					public void run() {
						if (state != BluetoothGatt.GATT_SUCCESS) {
							// 蓝牙连接失败
							ToastUtils.show(getApplicationContext(), "蓝牙连接失败");
							connectBluetoothBtn.setText("连接蓝牙（蓝牙已经失败了）");
							resetStaticParams();
							return;
						}
						if (newState == BluetoothProfile.STATE_CONNECTED) {
							ToastUtils.show(getApplicationContext(), "蓝牙连接成功");
							connectBluetoothBtn.setText("连接蓝牙（蓝牙已经成功连接）");
							/* Detect the connected reader. */
							if (mBluetoothReaderManager != null) {
								mBluetoothReaderManager.detectReader(gatt, mGattCallback);
							}
						} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
							ToastUtils.show(getApplicationContext(), "蓝牙已经断开");
							connectBluetoothBtn.setText("连接蓝牙（蓝牙连接已经断开了）");
							// 断开连接
							mBluetoothReader = null;
							if (mBluetoothGatt != null) {
								mBluetoothGatt.close();
								mBluetoothGatt = null;
							}
							resetStaticParams();
						}
					}
				});
			}
		});
	}

	private void initView() {
		nfcidTV = (TextView) findViewById(R.id.textview_nfc_id);
		initReaderBtn = (Button) findViewById(R.id.button_init_reader);
		startPolingBtn = (Button) findViewById(R.id.button_start_poling);
		stopPolingBtn = (Button) findViewById(R.id.button_stop_poling);
		getNFCIDBtn = (Button) findViewById(R.id.button_get_nfcid);
		resultBackBtn = (Button) findViewById(R.id.button_result_back_data);
		connectBluetoothBtn = (Button) findViewById(R.id.button_connect_bluetooth);
	}

	private void initEvent() {
		// 初始化
		initReaderBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/* Retrieve master key from edit box. */
				if (mBluetoothReader == null) {
					return;
				}
				byte masterKey[] = Utils.getEditTextinHexBytes(master_key);
				if (masterKey != null && masterKey.length > 0) {
					if (!mBluetoothReader.authenticate(masterKey)) {
						ToastUtils.show(getBaseContext(), "初始化失败");
					} else {
						ToastUtils.show(getBaseContext(), "始化成功");
						initReaderBtn.setEnabled(false);
					}
				} else {
					ToastUtils.show(getBaseContext(), "密钥初始化失败");
				}
			}
		});
		// 开始轮询
		startPolingBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBluetoothReader == null) {
					return;
				}
				if (!mBluetoothReader.transmitEscapeCommand(AUTO_POLLING_START)) {
					ToastUtils.show(getBaseContext(), "轮询失败");
				}
			}
		});
		// 停止轮询
		stopPolingBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBluetoothReader == null) {
					return;
				}
				if (!mBluetoothReader.transmitEscapeCommand(AUTO_POLLING_STOP)) {
					ToastUtils.show(getBaseContext(), "停止轮询失败");
				} else {
					ToastUtils.show(getBaseContext(), "停止轮询成功");
				}
			}
		});
		// 获取标签ID
		getNFCIDBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null == mBluetoothReader) {
					return;
				}
				byte apduCommand[] = Utils.getEditTextinHexBytes("FFCA000000");
				if (apduCommand != null && apduCommand.length > 0) {
					/* Transmit APDU command. */
					mBluetoothReader.transmitApdu(apduCommand);
				}
			}
		});
		// 返回结果集
		resultBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String nfcid = nfcidTV.getText().toString();
				if (TextUtils.isEmpty(nfcid)) {
					ToastUtils.show(getBaseContext(), "无标签信息");
					finish();
				} else {
					isTest = false;
					resultOK(nfcid);
				}
			}
		});
		// 连接蓝牙
		connectBluetoothBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
				if (!defaultAdapter.isEnabled()) {
					defaultAdapter.enable();
					ToastUtils.show(getBaseContext(), "正在开启蓝牙，请稍后再试");
					return;
				}
				device_bluetooth_adress = null;
				bluetoothUtil.startScanfBluetooth();
			}
		});
	}

	private void initNFCReader() {
		/* Initialize mBluetoothReaderManager. */
		mBluetoothReaderManager = new BluetoothReaderManager();
		/* Register BluetoothReaderManager's listeners */
		mBluetoothReaderManager.setOnReaderDetectionListener(new OnReaderDetectionListener() {
			@Override
			public void onReaderDetection(BluetoothReader reader) {
				if (reader instanceof Acr3901us1Reader) {
					/* The connected reader is ACR3901U-S1 reader. */
				} else if (reader instanceof Acr1255uj1Reader) {
					/* The connected reader is ACR1255U-J1 reader. */
				}
				mBluetoothReader = reader;
				setListener(mBluetoothReader);
				activateReader(mBluetoothReader);
			}
		});
		if (null != mBluetoothReader) {
			setListener(mBluetoothReader);
			activateReader(mBluetoothReader);
		}
	}

	/* Start the process to enable the reader's notifications. */
	private void activateReader(BluetoothReader reader) {
		if (reader == null) {
			return;
		}
		if (reader instanceof Acr3901us1Reader) {
			/* Start pairing to the reader. */
			((Acr3901us1Reader) mBluetoothReader).startBonding();
			master_key = "FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF";
		} else if (mBluetoothReader instanceof Acr1255uj1Reader) {
			/* Enable notification. */
			mBluetoothReader.enableNotification(true);
			try {
				master_key = Utils.toHexString("ACR1255U-J1 Auth".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Update listener
	 */
	private void setListener(BluetoothReader reader) {
		/* Update status change listener */
		if (mBluetoothReader instanceof Acr3901us1Reader) {
			((Acr3901us1Reader) mBluetoothReader).setOnBatteryStatusChangeListener(new OnBatteryStatusChangeListener() {

				@Override
				public void onBatteryStatusChange(BluetoothReader bluetoothReader, final int batteryStatus) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

						}
					});
				}

			});
		} else if (mBluetoothReader instanceof Acr1255uj1Reader) {
			((Acr1255uj1Reader) mBluetoothReader).setOnBatteryLevelChangeListener(new OnBatteryLevelChangeListener() {

				@Override
				public void onBatteryLevelChange(BluetoothReader bluetoothReader, final int batteryLevel) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

						}
					});
				}

			});
		}
		mBluetoothReader.setOnCardStatusChangeListener(new OnCardStatusChangeListener() {
			@Override
			public void onCardStatusChange(BluetoothReader bluetoothReader, final int sta) {

			}
		});

		/* Wait for authentication completed. */
		mBluetoothReader.setOnAuthenticationCompleteListener(new OnAuthenticationCompleteListener() {

			@Override
			public void onAuthenticationComplete(BluetoothReader bluetoothReader, final int errorCode) {

			}

		});

		/* Wait for receiving ATR string. */
		mBluetoothReader.setOnAtrAvailableListener(new OnAtrAvailableListener() {

			@Override
			public void onAtrAvailable(BluetoothReader bluetoothReader, final byte[] atr, final int errorCode) {

			}

		});

		/* Wait for power off response. */
		mBluetoothReader.setOnCardPowerOffCompleteListener(new OnCardPowerOffCompleteListener() {
			@Override
			public void onCardPowerOffComplete(BluetoothReader bluetoothReader, final int result) {

			}
		});

		/* Wait for response APDU. */
		mBluetoothReader.setOnResponseApduAvailableListener(new OnResponseApduAvailableListener() {

			@Override
			public void onResponseApduAvailable(BluetoothReader bluetoothReader, final byte[] apdu,
					final int errorCode) {
				// TODO 这里就是NFC结果
				runOnUiThread(new Runnable() {
					public void run() {
						byte[] bytes = apdu;
						StringBuilder sb = new StringBuilder();
						for (int i = bytes.length - 1; i >= 0; --i) {
							int b = bytes[i] & 0xff;
							if (b < 0x10)
								sb.append('0');
							sb.append(Integer.toHexString(b));
							if (i > 0) {
								sb.append("");
							}
						}
						if (sb.length() > 4) {
							sb.delete(0, 4);
						}
						nfcidTV.setText(sb.toString());
						resultOK(sb.toString());
					}
				});
			}

		});

		/* Wait for escape command response. */
		mBluetoothReader.setOnEscapeResponseAvailableListener(new OnEscapeResponseAvailableListener() {

			@Override
			public void onEscapeResponseAvailable(BluetoothReader bluetoothReader, final byte[] response,
					final int errorCode) {
			}

		});

		/* Wait for device info available. */
		mBluetoothReader.setOnDeviceInfoAvailableListener(new OnDeviceInfoAvailableListener() {
			@Override
			public void onDeviceInfoAvailable(BluetoothReader bluetoothReader, final int infoId, final Object o,
					final int status) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (status != BluetoothGatt.GATT_SUCCESS) {
							return;
						}
						switch (infoId) {
						case BluetoothReader.DEVICE_INFO_SYSTEM_ID:
							break;
						case BluetoothReader.DEVICE_INFO_MODEL_NUMBER_STRING:

							break;
						case BluetoothReader.DEVICE_INFO_SERIAL_NUMBER_STRING:

							break;
						case BluetoothReader.DEVICE_INFO_FIRMWARE_REVISION_STRING:

							break;
						case BluetoothReader.DEVICE_INFO_HARDWARE_REVISION_STRING:

							break;
						case BluetoothReader.DEVICE_INFO_MANUFACTURER_NAME_STRING:

							break;
						default:
							break;
						}
					}
				});
			}

		});

		/* Wait for battery level available. */
		if (mBluetoothReader instanceof Acr1255uj1Reader) {
			((Acr1255uj1Reader) mBluetoothReader)
					.setOnBatteryLevelAvailableListener(new OnBatteryLevelAvailableListener() {

						@Override
						public void onBatteryLevelAvailable(BluetoothReader bluetoothReader, final int batteryLevel,
								int status) {
						}

					});
		}

		/* Handle on battery status available. */
		if (mBluetoothReader instanceof Acr3901us1Reader) {
			((Acr3901us1Reader) mBluetoothReader)
					.setOnBatteryStatusAvailableListener(new OnBatteryStatusAvailableListener() {
						@Override
						public void onBatteryStatusAvailable(BluetoothReader bluetoothReader, final int batteryStatus,
								int status) {

						}
					});
		}

		/* Handle on slot status available. */
		mBluetoothReader.setOnCardStatusAvailableListener(new OnCardStatusAvailableListener() {
			@Override
			public void onCardStatusAvailable(BluetoothReader bluetoothReader, final int cardStatus,
					final int errorCode) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (errorCode != BluetoothReader.ERROR_SUCCESS) {

						} else {

						}
					}
				});
			}
		});

		mBluetoothReader.setOnEnableNotificationCompleteListener(new OnEnableNotificationCompleteListener() {
			@Override
			public void onEnableNotificationComplete(BluetoothReader bluetoothReader, final int result) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (result != BluetoothGatt.GATT_SUCCESS) {
							/* Fail */

						} else {

						}
					}
				});
			}

		});
	}

	@Override
	public void onBackPressed() {
		String nfcid = nfcidTV.getText().toString();
		if (!TextUtils.isEmpty(nfcid)) {
			resultOK(nfcid);
		}
		super.onBackPressed();
	}
}
