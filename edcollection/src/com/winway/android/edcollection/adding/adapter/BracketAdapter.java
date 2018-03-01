package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.winway.android.edcollection.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * 截面支架的适配器
 * 
 * @author lyh
 *
 */
public class BracketAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> list;

	public BracketAdapter() {
	}

	public BracketAdapter(Context mContext, ArrayList<String> list) {
		super();
		this.mContext = mContext;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.adapter_choose_bracket, null);
			viewHolder.tvNum = (TextView) convertView.findViewById(R.id.tv_adapter_choose_bracket_item);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvNum.setText(list.get(position));
		return convertView;
	}

	public class ViewHolder{
		public TextView tvNum;
	}
}
