package com.winway.android.edcollection.rtk.entity;

import android.bluetooth.BluetoothSocket;

/**
 * 蓝牙连接结果
 * 
 * @author zgq
 *
 */
public class BluetoothConnectResult {
	private BluetoothSocket bluetoothSocket;// 客户端socket
	private boolean isConnect;// 连接是否成功

	public BluetoothSocket getBluetoothSocket() {
		return bluetoothSocket;
	}

	public void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
		this.bluetoothSocket = bluetoothSocket;
	}

	public boolean isConnect() {
		return isConnect;
	}

	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

}
