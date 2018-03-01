package com.winway.android.edcollection.setting.adapter;

import java.util.ArrayList;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.rtk.utils.BlueToothUtil;
import com.winway.android.edcollection.setting.entity.OperateType;
import com.winway.android.edcollection.setting.entity.SettingListViewItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author xs
 * 
 */
public class SettingAdapter extends MBaseAdapter<SettingListViewItem> {

	private Context context;
	private ArrayList<SettingListViewItem> list;

	public SettingAdapter(Context context, ArrayList<SettingListViewItem> models) {
		super(context, models);
		this.context = context;
		this.list = models;
	}

	@Override
	public int getItemViewType(int position) {
		if (list.get(position).getSettingItem().equals("")) {
			return 1;
		}
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			holder = new ViewHolder();
			switch (type) {
			case 0:
				convertView = View.inflate(context, R.layout.activity_setting_lv_item, null);
				holder.tvSettingItem = (TextView) convertView.findViewById(R.id.tv_setting_item);
				holder.tvSettingState = (TextView) convertView.findViewById(R.id.tv_setting_state);
				holder.ivSettingPic = (ImageView) convertView.findViewById(R.id.iv_setting_iamge);
				convertView.setTag(holder);
				break;
			case 1:
				convertView = View.inflate(context, R.layout.activity_setting_lv_item2, null);
			default:
				break;
			}
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (!list.get(position).getSettingItem().isEmpty()) {
			SettingListViewItem item = list.get(position);
			holder.tvSettingItem.setText(item.getSettingItem());
			holder.tvSettingState.setText(item.getSettingState());
			if (item.getSettingItem().equals(OperateType.SCFJ.getValue())) {// 上传附件
				holder.tvSettingState.setVisibility(View.VISIBLE);
			} else if (item.getSettingItem().equals(OperateType.LY.getValue())) {// 蓝牙
				// 判断蓝牙是否打开
				if (BlueToothUtil.isOpenBluetooth()) {
					holder.tvSettingState.setText("已打开");
				} else {
					holder.tvSettingState.setText("已关闭");
				}
			} else if (item.getSettingItem().equals(OperateType.RTK.getValue())) {// RTK
				// 判断RTK是否连接
				if (BlueToothUtil.isConnectedRtk()) {
					holder.tvSettingState.setText("已连接");
				} else {
					holder.tvSettingState.setText("未连接");
				}
			}

		}
		return convertView;
	}

	class ViewHolder {
		TextView tvSettingItem;
		TextView tvSettingState;
		ImageView ivSettingPic;
	}
}
