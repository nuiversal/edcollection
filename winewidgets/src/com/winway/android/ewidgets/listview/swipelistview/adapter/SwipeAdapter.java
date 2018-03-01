
package com.winway.android.ewidgets.listview.swipelistview.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 滑动listview适配器
 * 
 * @author zgq
 *
 * @param <T>
 */
public class SwipeAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> models;

	protected int mRightWidth = 0;

	public SwipeAdapter(Context ctx, List<T> data, int rightWidth) {
		mContext = ctx;
		this.models = data;
		mRightWidth = rightWidth;
	}

	@Override
	public int getCount() {
		return models.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

	/** 更新数据 */
	public void update(List<T> models) {
		if (models == null) {
			return;
		}
		this.models.clear();
		for (T t : models) {
			this.models.add(t);
		}
		notifyDataSetChanged();
	}

	public List<T> getItems() {
		return models;
	}

	/**
	 * 单击事件监听器
	 */
	protected onRightItemClickListener mListener = null;

	public void setOnRightItemClickListener(onRightItemClickListener listener) {
		mListener = listener;
	}

	public interface onRightItemClickListener {
		void onRightItemClick(View v, int position);
	}

}
