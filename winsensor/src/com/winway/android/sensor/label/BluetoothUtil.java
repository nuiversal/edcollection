package com.winway.android.sensor.label;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.sensor.R;
import com.winway.android.sensor.rfid.RFIDUtil;
import com.winway.android.util.DensityUtil;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.LogUtil;
import com.winway.android.util.ToastUtils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 蓝牙工具类
 * @author mr-lao
 *
 */
public class BluetoothUtil {
	BluetoothDevice connectedDivece = null;
	private Context context;
	long scanfStartTime = 0;

	public BluetoothUtil(Context context) {
		this.context = context;
	}

	public static boolean isEnableBluetooth() {
		return BluetoothAdapter.getDefaultAdapter().isEnabled();
	}

	public static void enableBluetooth() {
		BluetoothAdapter.getDefaultAdapter().enable();
	}

	boolean isRegisterReceiver = false;

	public void startScanfBluetooth() {
		if (!isEnableBluetooth()) {
			return;
		}
		scanfStartTime = System.currentTimeMillis();
		BluetoothAdapter.getDefaultAdapter().startDiscovery();
		if (!deviceList.isEmpty()) {
			showDiscoverBluetoothListDialog();
		}
		if (!isRegisterReceiver) {
			IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			context.registerReceiver(mReceiver, filter);
			isRegisterReceiver = true;
		}
	}

	public void stopScanBluetooth() {
		BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
		if (isRegisterReceiver) {
			context.unregisterReceiver(mReceiver);
			isRegisterReceiver = false;
		}
	}

	/**
	 * 获取已经连接的蓝牙
	 * @return
	 */
	public BluetoothDevice getConnectDevice() {
		if (null != connectedDivece) {
			return connectedDivece;
		}
		getConnectBluetoothDevice();
		return connectedDivece;
	}

	private void getConnectBluetoothDevice() {
		int a2dp = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.A2DP);
		int headset = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.HEADSET);
		int health = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.HEALTH);
		int flag = -1;
		if (a2dp == BluetoothProfile.STATE_CONNECTED) {
			flag = a2dp;
		} else if (headset == BluetoothProfile.STATE_CONNECTED) {
			flag = headset;
		} else if (health == BluetoothProfile.STATE_CONNECTED) {
			flag = health;
		}
		if (flag != -1) {
			BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new BluetoothProfile.ServiceListener() {
				@Override
				public void onServiceDisconnected(int profile) {
					connectedDivece = null;
				}

				@Override
				public void onServiceConnected(int profile, BluetoothProfile proxy) {
					List<BluetoothDevice> mDevices = proxy.getConnectedDevices();
					if (mDevices != null && mDevices.size() > 0) {
						for (BluetoothDevice device : mDevices) {
							connectedDivece = device;
						}
					}
				}
			}, flag);
		}
	}

	private ArrayList<DeviceEntity> deviceList = new ArrayList<>();

	public static class DeviceEntity {
		public String name;
		public String address;

		@Override
		public String toString() {
			return name + "";
		}
	}

	long searchEndTime = 13000;
	private DialogUtil dialogUtil = null;
	ArrayAdapter<DeviceEntity> adapter = null;

	private void showDiscoverBluetoothListDialog() {
		if (searchEndTime <= System.currentTimeMillis() - scanfStartTime) {
			stopScanBluetooth();
		}
		if (deviceList.isEmpty()) {
			ToastUtils.show(context, "没有搜索到任何蓝牙");
			return;
		}
		if (null == dialogUtil) {
			dialogUtil = new DialogUtil((Activity) context);
			View view = View.inflate(context, R.layout.dialog_bluetooth_device_discover, null);
			ListView listView = (ListView) view.findViewById(R.id.listview_device_data);
			adapter = new ArrayAdapter<>(context, R.layout.listview_comment_item_sensor, R.id.text, deviceList);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					stopScanBluetooth();
					dialogUtil.dismissDialog();
					if (null != bluetoothConnectListener) {
						bluetoothConnectListener.onConnect(deviceList.get(position));
					}
				}
			});
			dialogUtil.showAlertDialog(view, DensityUtil.dip2px(context, 240));
			dialogUtil.dialogOutsideTouchCancel(false);
		} else {
			dialogUtil.showAlertDialog(null);
			adapter.notifyDataSetChanged();
		}
		// stopScanBluetooth();
	}

	boolean containDevice(DeviceEntity device) {
		if (deviceList.isEmpty()) {
			return false;
		}
		if (deviceList.contains(device)) {
			return true;
		}
		for (DeviceEntity de : deviceList) {
			if (de.address.equals(device.address) && de.name.equals(device.name)) {
				return true;
			}
		}
		return false;
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(BluetoothDevice.ACTION_FOUND)) {
				// 获得已经搜索到的蓝牙设备
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				/*if (!containDevice(device)) {
					discoverBluetoothList.add(device);
				}*/
				DeviceEntity entity = new DeviceEntity();
				entity.name = device.getName();
				entity.address = device.getAddress();
				if (null == entity.name) {
					entity.name = "null";
				}
				if (!containDevice(entity)) {
					deviceList.add(entity);
					showDiscoverBluetoothListDialog();
				}
			}
		}
	};

	private BluetoothConnectListener bluetoothConnectListener = null;

	public BluetoothConnectListener getBluetoothConnectListener() {
		return bluetoothConnectListener;
	}

	public void setBluetoothConnectListener(BluetoothConnectListener bluetoothConnectListener) {
		this.bluetoothConnectListener = bluetoothConnectListener;
	}

	public interface BluetoothConnectListener {
		public void onConnect(DeviceEntity device);
	}

	private static BroadcastReceiver bluetoothStateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			RFIDUtil.setSingletonNull();
			LogUtil.i("bluetooth-state", "蓝牙连接已经断开啦");
		}
	};

	public static void registerBluetoothStateReceiver(Context context) {
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		context.registerReceiver(bluetoothStateReceiver, filter);
	}

	public static void unregisterBluetoothStateReceiver(Context context) {
		context.unregisterReceiver(bluetoothStateReceiver);
	}
}
