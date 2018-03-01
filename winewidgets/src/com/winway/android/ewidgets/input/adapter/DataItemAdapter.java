package com.winway.android.ewidgets.input.adapter;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.input.entity.DataItem;
import com.winway.android.util.MBaseAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

/**
 * 
 * @author winway_zgq
 *
 */
public class DataItemAdapter extends MBaseAdapter<DataItem> implements Filterable {
	private MFilter mFilter;// 过滤器
	private ArrayList<DataItem> mUnfilteredData;// 未过滤的数据集
	ArrayList<DataItem> newValues;// 存放结果的数据数据集

	public DataItemAdapter(Context context, ArrayList<DataItem> models) {
		super(context, models);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.ewidgets_dataitem_listitem, null);
			viewHolder = new ViewHolder();
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_ewidgets_dataitem_listitem_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		DataItem dataItem = models.get(position);
		viewHolder.tvName.setText(dataItem.getName());

		return convertView;
	}

	class ViewHolder {
		public TextView tvName;
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new MFilter();
		}
		return mFilter;
	}

	private class MFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();

			if (mUnfilteredData == null) {
				mUnfilteredData = new ArrayList<DataItem>();
				mUnfilteredData.addAll(models);
			}

			if (prefix == null || prefix.length() == 0) {
				ArrayList<DataItem> list = mUnfilteredData;
				results.values = list;
				results.count = list.size();
			} else {
				String regex = "(?i)(.*)" + prefix + "(.*)";

				ArrayList<DataItem> unfilteredValues = mUnfilteredData;
				int count = unfilteredValues.size();

				newValues = new ArrayList<DataItem>(count);

				for (int i = 0; i < count; i++) {
					DataItem item = unfilteredValues.get(i);
					if (item != null) {
						if (item.getName() != null && item.getName().matches(regex)) {
							newValues.add(item);
						}
					}
				}
				results.values = newValues;
				results.count = newValues.size();
			}

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			models = (ArrayList<DataItem>) results.values;
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}

		@Override
		public CharSequence convertResultToString(Object resultValue) {
			return ((DataItem) resultValue).getName();
		}
	}

	public ArrayList<DataItem> getNewValues() {
		return newValues;
	}

	public void updateDatas(List<DataItem> models) {
		this.models.clear();
		this.models.addAll(models);
		if (null != mUnfilteredData) {
			this.mUnfilteredData.addAll(models);
		}
		notifyDataSetChanged();
	}

}
