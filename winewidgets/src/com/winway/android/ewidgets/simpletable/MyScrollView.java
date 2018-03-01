package com.winway.android.ewidgets.simpletable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
* @author winway-laohw
* @time 2017年9月1日 下午2:17:17
*/
public class MyScrollView extends ScrollView {
	private OnScrollListener onScrollListener;

	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	public interface OnScrollListener {
		public void onBottom();

		public void onTop();
	}

	public MyScrollView(Context context) {
		super(context);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// 判断ScrollView已经滚动到达了底部
		if (getChildAt(0).getHeight() - getHeight() <= getScrollY() + 24) {
			if (null != onScrollListener) {
				onScrollListener.onBottom();
			}
		}
		return super.onTouchEvent(ev);
	}

}
