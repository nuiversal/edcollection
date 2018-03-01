package com.winway.android.util;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * View控件背景工具类
 * @author mr-lao
 *
 */
public class ViewBackgroundUtil {
	public static void initBackgroundColor(View bgView, int normalColor, int pressColor) {
		initBackgroundColor(bgView, bgView, normalColor, pressColor);
	}

	public static void initBackgroundColor(View bgView, String normalColor, String pressColor) {
		initBackgroundColor(bgView, bgView, normalColor, pressColor);
	}

	public static void initBackgroundImage(View bgView, int normalImageResId, int pressImageResId) {
		initBackgroundImage(bgView, bgView, normalImageResId, pressImageResId);
	}

	public static void initBackgroundColor(View touchView, View bgView, int normalColor, int pressColor) {
		initBackgroundColor(touchView, bgView, normalColor, pressColor, false);
	}

	public static void initBackgroundColor(View touchView, View bgView, int normalColor, int pressColor,
			boolean touchReturn) {
		touchView.setOnTouchListener(new LOnTouchListener(normalColor, pressColor, 1, bgView, touchReturn));
	}

	public static void initBackgroundColor(View touchView, View bgView, String normalColor, String pressColor) {
		initBackgroundColor(touchView, bgView, Color.parseColor(normalColor), Color.parseColor(pressColor));
	}

	public static void initBackgroundImage(View touchView, View bgView, int normalImageResId, int pressImageResId) {
		initBackgroundImage(touchView, bgView, normalImageResId, pressImageResId, false);
	}

	public static void initBackgroundImage(View touchView, View bgView, int normalImageResId, int pressImageResId,
			boolean touchReturn) {
		touchView.setOnTouchListener(new LOnTouchListener(normalImageResId, pressImageResId, 0, bgView, touchReturn));
	}

	private static class LOnTouchListener implements OnTouchListener {
		int bgNormalColor, bgPressColor;
		int bgNormalImage, bgPressImage;
		int type = 0;
		View bgView;
		public boolean touchReturn = false;

		/**
		 * type=1表示设置的是背景颜色，type=2表示的是设置背景图片 
		 * @param normal
		 * @param press
		 * @param type
		 */
		@SuppressLint("ClickableViewAccessibility")
		public LOnTouchListener(int normal, int press, int type, View bgView, boolean touchReturn) {
			this.type = type;
			if (type == 1) {
				bgNormalColor = normal;
				bgPressColor = press;
			} else {
				bgNormalImage = normal;
				bgPressImage = press;
			}
			this.bgView = bgView;
			this.touchReturn = touchReturn;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (type == 1) {
					// 背景颜色
					bgView.setBackgroundColor(bgPressColor);
				} else {
					// 背景图片
					bgView.setBackgroundResource(bgPressImage);
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE
					|| event.getAction() == MotionEvent.ACTION_CANCEL) {
				if (type == 1) {
					// 背景颜色
					bgView.setBackgroundColor(bgNormalColor);
				} else {
					// 背景图片
					bgView.setBackgroundResource(bgNormalImage);
				}
				if (event.getAction() == MotionEvent.ACTION_UP && touchReturn) {
					v.performClick();
				}
			}
			return touchReturn;
		}
	}
}
