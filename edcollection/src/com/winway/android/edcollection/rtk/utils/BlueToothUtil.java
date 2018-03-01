package com.winway.android.edcollection.rtk.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.winway.android.edcollection.rtk.entity.BluetoothConnectResult;
import com.winway.android.edcollection.rtk.entity.RtkConstant;
import com.winway.android.util.LogUtil;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * 蓝牙工具类
 * 
 * @author zgq
 *
 */
public class BlueToothUtil {

	private static BluetoothConnectResult bConnectResult = null;

	/**
	 * 是否打开蓝牙
	 * 
	 * @return
	 */
	public static boolean isOpenBluetooth() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		return bluetoothAdapter.isEnabled();
	}

	/**
	 * 是否连接Rtk
	 * 
	 * @return
	 */
	public static boolean isConnectedRtk() {

		if (bConnectResult == null) {
			return false;
		}
		return bConnectResult.isConnect();

	}

	/**
	 * 打开蓝牙
	 */
	public static void openBluetooth(BluetoothAdapter mBluetoothAdapter, Context context, int requestCode) {
		// TODO Auto-generated method stub
		if (mBluetoothAdapter != null) {
			if (!mBluetoothAdapter.isEnabled()) {
				// 用户打开蓝牙
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				((Activity) context).startActivityForResult(intent, requestCode);

			} else {
				// 蓝牙已经打开
				Toast.makeText(context, "蓝牙已经打开", Toast.LENGTH_SHORT).show();
			}
		} else {
			// 不支持蓝牙
			Toast.makeText(context, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 关闭蓝牙
	 */
	public static void closeBluetooth(BluetoothAdapter mBluetoothAdapter) {
		// TODO Auto-generated method stub
		mBluetoothAdapter.disable();
	}

	/**
	 * 获取配对设备
	 * 
	 * @param bluetoothAdapter
	 * @return
	 */
	public static List<BluetoothDevice> getPairedDevices(BluetoothAdapter bluetoothAdapter) {
		List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
		Set<BluetoothDevice> set = bluetoothAdapter.getBondedDevices();
		for (BluetoothDevice bluetoothDevice : set) {
			devices.add(bluetoothDevice);
		}
		return devices;
	}

	/**
	 * 连接蓝牙设备
	 * 
	 * @param mBluetoothDevice
	 */
	public static BluetoothConnectResult connect(BluetoothDevice mBluetoothDevice) {

		if (bConnectResult == null) {
			bConnectResult = new BluetoothConnectResult();
		}
		BluetoothSocket mBluetoothSocket = null;
		try {
			mBluetoothSocket = mBluetoothDevice
					.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			// 连接
			mBluetoothSocket.connect();

			// Class<?> clazz = mBluetoothDevice.getClass();
			// Class<?>[] paramTypes = new Class<?>[] { Integer.TYPE };
			//
			// Method m = clazz.getMethod("createRfcommSocket", paramTypes);
			// Object[] params = new Object[] { Integer.valueOf(1) };
			//
			// mBluetoothSocket = (BluetoothSocket) m.invoke(mBluetoothDevice,
			// params);
			// mBluetoothSocket.connect();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		finally {
			// 如果连接成功，开始接收数据
			if (mBluetoothSocket.isConnected()) {
				bConnectResult.setConnect(true);
			} else {
				bConnectResult.setConnect(false);
			}
			bConnectResult.setBluetoothSocket(mBluetoothSocket);

		}
		return bConnectResult;
	}

	/**
	 * 关闭蓝牙socket连接
	 */
	public static void closeConnect() {
		if (bConnectResult == null) {
			return;
		}
		BluetoothSocket bluetoothSocket = bConnectResult.getBluetoothSocket();
		bConnectResult.setConnect(false);
		if (bluetoothSocket != null) {
			try {
				bluetoothSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 数据接收
	 */
	public static void dataReceiver(final BluetoothConnectResult bConnectResult, final Handler uiHandler) {
		if (bConnectResult == null) {
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (bConnectResult.isConnect()) {
					BluetoothSocket bluetoothSocket = null;
					OutputStream outputStream = null;
					InputStream inputStream = null;
					try {
						LogUtil.d("dataReceiver", "dataReceiver");
						// 1、写入数据
						bluetoothSocket = bConnectResult.getBluetoothSocket();
						outputStream = bluetoothSocket.getOutputStream();
						outputStream.write(RtkUtils.hexStringToBytes(RtkConstant.closeCom1));
						outputStream.write(RtkUtils.hexStringToBytes(RtkConstant.gga));
						// 2、读取数据
						inputStream = bluetoothSocket.getInputStream();
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = inputStream.read(buffer)) != -1) {
							String str = new String(buffer, 0, len);
							// Log.e("输出", str);
							Message msg = new Message();
							msg.what = RtkConstant.get_coords;
							msg.obj = str;
							uiHandler.sendMessage(msg);
						}

					} catch (IOException e) {
						uiHandler.sendEmptyMessage(RtkConstant.LINK_BREAK_OFF);
						bConnectResult.setConnect(false);
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							if (bluetoothSocket != null) {
								bluetoothSocket.close();
							}
							if (outputStream != null) {
								outputStream.close();
							}
							if (inputStream != null) {
								inputStream.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

}
