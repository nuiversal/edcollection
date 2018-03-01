package com.winway.android.edcollection.rtk.controll;

import java.util.ArrayList;
import java.util.List;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.rtk.adapter.BluetoothDevicesAdapter;
import com.winway.android.edcollection.rtk.utils.BlueToothUtil;
import com.winway.android.edcollection.rtk.viewholder.DiscoveryViewHolder;
import com.winway.android.util.BroadcastReceiverUtils;
import com.winway.android.util.ProgressUtils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 蓝牙设备查找
 * 
 * @author zgq
 *
 */
public class DiscoveryControll extends BaseControll<DiscoveryViewHolder> {

	private ListView lvDisveryDevices;
	private BluetoothDevicesAdapter adapter = null;// 列表适配器

	@Override
	public void init() {
		// TODO Auto-generated method stub
		initDatas();
		initEvents();

	}

	/** 取得默认的蓝牙适配器 */
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	/** 用来存储搜索到的蓝牙设备 */
	private List<BluetoothDevice> _devices = new ArrayList<BluetoothDevice>();
	/** 是否完成搜索 */
	private volatile boolean _discoveryFinished = false;
	/** 广播接收者 */
	private BroadcastReceiver bReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals(BluetoothDevice.ACTION_FOUND)) {// 发现远程设备
				/* 从intent中取得搜索结果数据 */
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				/* 将结果添加到列表中 */
				if (isExistDevice(device) == false) {
					_devices.add(device);
				}
				/* 显示列表 */
				showDevices();
			} else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {// 完成设备查找
				/* 卸载注册的接收器 */
				mActivity.unregisterReceiver(bReceiver);
				_discoveryFinished = true;
				// 关闭对话框
				ProgressUtils.getInstance().dismiss();
			}
		}
	};

	/**
	 * 判断是否存在设备，避免重复添加（true表示已经存在，false表示不存在）
	 * 
	 * @param mBluetoothDevice
	 * @return
	 */
	private boolean isExistDevice(BluetoothDevice mBluetoothDevice) {
		for (int i = 0; i < _devices.size(); i++) {
			BluetoothDevice bDevice = _devices.get(i);
			if (bDevice.getAddress().equals(mBluetoothDevice.getAddress())) {
				return true;
			}
		}
		return false;
	}

	// 列表项点击事件
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Intent result = new Intent();
			result.putExtra(BluetoothDevice.EXTRA_DEVICE, _devices.get(position));
			mActivity.setResult(Activity.RESULT_OK, result);
			mActivity.finish();
			// 停止扫描
			_bluetooth.cancelDiscovery();
		}
	};

	// 显示设备
	private void showDevices() {
		// TODO Auto-generated method stub
		ArrayList<String> datas = new ArrayList<String>();
		for (int i = 0, size = _devices.size(); i < size; ++i) {
			StringBuilder b = new StringBuilder();
			BluetoothDevice d = _devices.get(i);
			b.append(d.getAddress());
			b.append('\n');
			b.append(d.getName());
			String s = b.toString();
			datas.add(s);
		}
		//
		if (adapter == null) {
			adapter = new BluetoothDevicesAdapter(mActivity, datas);
			lvDisveryDevices.setAdapter(adapter);
		} else {
			adapter.update(datas);
		}

	}

	private void initEvents() {
		// TODO Auto-generated method stub
		lvDisveryDevices.setOnItemClickListener(itemClickListener);
		viewHolder.getHcDiscoveryHead().getReturnView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.finish();
			}
		});
	}

	// 初始化数据
	private void initDatas() {
		// TODO Auto-generated method stub
		lvDisveryDevices = viewHolder.getLvDisveryDevices();
		/* 如果蓝牙适配器没有打开，则结果 */
		if (!_bluetooth.isEnabled()) {
			mActivity.finish();
			return;
		}
		// 获取配对的设备
		List<BluetoothDevice> devices = BlueToothUtil.getPairedDevices(_bluetooth);
		_devices.addAll(devices);
		showDevices();

		// 注册广播
		BroadcastReceiverUtils.getInstance().registReceiver(mActivity, BluetoothDevice.ACTION_FOUND, bReceiver);
		BroadcastReceiverUtils.getInstance().registReceiver(mActivity, BluetoothAdapter.ACTION_DISCOVERY_FINISHED,
				bReceiver);

		// 显示进度对话框
		ProgressUtils.getInstance().show("设备搜索中..", false, mActivity);

		// 启动查找的线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				/* 开始搜索 */
				_bluetooth.startDiscovery();
				while (true) {
					if (_discoveryFinished) {
						break;
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}

}
