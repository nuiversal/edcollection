package com.winway.android.edcollection.rtk.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseActivity;
import com.winway.android.edcollection.rtk.controll.DiscoveryControll;
import com.winway.android.edcollection.rtk.viewholder.DiscoveryViewHolder;
import com.winway.android.util.BroadcastReceiverUtils;
import com.winway.android.util.ProgressUtils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 发现查找设备
 * 
 * @author zgq
 *
 */
public class DiscoveryActivity extends BaseActivity<DiscoveryControll, DiscoveryViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.discovery);
	}

}
