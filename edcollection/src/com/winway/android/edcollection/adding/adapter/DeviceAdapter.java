package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeviceAdapter extends BaseAdapter{
	private Context context;
	private List<EcLineLabelEntity> models;
	
	 public DeviceAdapter(Context context,List<EcLineLabelEntity> models) {
		 this.context=context;
		 this.models=models;
	 }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder =null;
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.layout_devicequery_lv_item, null);
			viewHolder.lableName = (TextView) convertView.findViewById(R.id.tv_deviceName);
			viewHolder.lableID = (TextView) convertView.findViewById(R.id.tv_deviceID);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		EcLineLabelEntity ecLineLabelEntity = models.get(position);
		viewHolder.lableName.setText(ecLineLabelEntity.getDevName());
		viewHolder.lableID.setText(ecLineLabelEntity.getLabelNo());
		convertView.setBackgroundResource(R.drawable.list_item_selector);
		return convertView;
	}

	class ViewHolder{
		public TextView lableName;
		public TextView lableID;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return models.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return models.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
