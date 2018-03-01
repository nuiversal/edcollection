package com.winway.android.util;

import android.util.SparseArray;
import android.view.View;

/**
 * BaseViewHolder
 * 
 * @author zgq
 *
 */
public class BaseViewHolder {
	/**
	 * 根据id从view中获取子元素
	 * 
	 * @param view
	 *            父容器
	 * @param id
	 *            id值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

}
