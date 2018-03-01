package com.winway.android.edcollection.rtk.adapter;

import java.util.ArrayList;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 蓝牙设备列表适配器
 * 
 * @author zgq
 *
 */
public class BluetoothDevicesAdapter extends MBaseAdapter<String> {

	public BluetoothDevicesAdapter(Context context, ArrayList<String> models) {
		super(context, models);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.discovery_item, null);
			viewHolder.tvDevice = (TextView) convertView.findViewById(R.id.tv_discovery_item_device);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 赋值
		viewHolder.tvDevice.setText(models.get(position));

		return convertView;
	}

	class ViewHolder {
		public TextView tvDevice;
	}

}
