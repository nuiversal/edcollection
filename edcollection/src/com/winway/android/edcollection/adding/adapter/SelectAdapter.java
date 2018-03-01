package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.util.LogUtil;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

public class SelectAdapter extends BaseAdapter {
	/**dataList里面的数据必须重写toString方法，因为Item显示的文字由toString方法决定*/
	private List<?> dataList;
	private Context context;
	private ArrayList<Boolean> checkedList;

	public SelectAdapter(List<?> dataList, Context context) {
		super();
		this.dataList = dataList;
		this.context = context;
		checkedList = new ArrayList<Boolean>();
		initCheckedList();
	}

	void initCheckedList() {
		for (int i = checkedList.size(); i < dataList.size(); i++) {
			checkedList.add(false);
		}
	}

	void checkPositionItem(int position, boolean checked) {
		for (int i = 0; i < checkedList.size(); i++) {
			checkedList.set(i, false);
		}
		checkedList.set(position, checked);
		this.notifyDataSetChanged();
	}

	/**
	 * 返回用户选择的项位置，如果用户没有选择，则返回-1
	 * @return
	 */
	public int getCheckPosition() {
		for (int i = 0; i < checkedList.size(); i++) {
			if (checkedList.get(i))
				return i;
		}
		return -1;
	}

	@Override
	public void notifyDataSetChanged() {
		initCheckedList();
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.select_device_item, null);
			holder.checkRT = (RadioButton) convertView
					.findViewById(R.id.rBtn_select_device_item_select);
			holder.textTV = (TextView) convertView.findViewById(R.id.tv_select_device_item_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textTV.setText("" + dataList.get(position));
		holder.checkRT.setChecked(checkedList.get(position));
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checkPositionItem(position, !checkedList.get(position));
			}
		});
		holder.checkRT.setClickable(false);
		return convertView;
	}

	class ViewHolder {
		public TextView textTV;
		public RadioButton checkRT;
	}

}
