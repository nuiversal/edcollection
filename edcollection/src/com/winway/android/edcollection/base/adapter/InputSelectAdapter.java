package com.winway.android.edcollection.base.adapter;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InputSelectAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> datas;

	public InputSelectAdapter(Context context, List<String> datas) {
		this.mContext = context;
		this.datas = datas;
	}

	public InputSelectAdapter(Context context, String[] datasArray) {
		this.mContext = context;
		datas = new ArrayList<>();
		for (int i = 0; i < datasArray.length; i++) {
			datas.add(datasArray[i]);
		}
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.inse_item_layout, null);
			holder.tvValue = (TextView) convertView.findViewById(R.id.list_val);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvValue.setText(datas.get(position));
		return convertView;
	}

	private class ViewHolder {
		public TextView tvValue;
	}
}
