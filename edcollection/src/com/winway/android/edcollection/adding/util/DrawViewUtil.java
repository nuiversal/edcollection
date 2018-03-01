package com.winway.android.edcollection.adding.util;

import java.util.List;

import com.winway.android.edcollection.adding.customview.GJDrawView;
import com.winway.android.edcollection.adding.customview.Point;
import com.winway.android.util.DensityUtil;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

/**
 * 画板工具类
 * 
 * @author mr-lao
 *
 */
public class DrawViewUtil {

	/**
	 * 创建迷你画板
	 * 
	 * @param context
	 * @param copyDrawView
	 * @return
	 */
	public static GJDrawView createMiniDrawView(Context context, GJDrawView copyDrawView, List<Point> points,
			List<Point> zjPoints) {
		int width = DensityUtil.dip2px(context, 80);
		int height = DensityUtil.dip2px(context, 80);
		float scale = ((float) width) / ((float) copyDrawView.getWidth());
		GJDrawView drawView = new GJDrawView(context, width, height, scale);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.bottomMargin = DensityUtil.dip2px(context, 4);
		layoutParams.topMargin = DensityUtil.dip2px(context, 4);
		layoutParams.leftMargin = DensityUtil.dip2px(context, 4);
		layoutParams.rightMargin = DensityUtil.dip2px(context, 4);
		drawView.setLayoutParams(layoutParams);
		drawView.setTouchEditor(null);
		if (null == points) {
			drawView.setPoints(copyDrawView.getPoints());
		} else {
			drawView.setPoints(points);
		}
		if (null == zjPoints) {
			drawView.setLinesLocation(copyDrawView.getLinesLocation());
		} else {
			drawView.setLinesLocation(zjPoints);
		}
		return drawView;
	}

	static final int TAG_SELECTED = 8 << 24;

	/**
	 * 选择具体的迷你画板
	 * 
	 * @param miniDrawView
	 * @param list
	 */
	public static void selectMiniDrawView(GJDrawView miniDrawView, List<GJDrawView> list) {
		for (GJDrawView drawview : list) {
			if (drawview != miniDrawView) {
				drawview.setBackgroundColor(Color.WHITE);
				drawview.setTag(TAG_SELECTED, false);
			}
		}
		miniDrawView.setBackgroundColor(Color.GRAY);
		miniDrawView.setTag(TAG_SELECTED, true);
	}

	/**
	 * 获取选中的迷你画板
	 * 
	 * @param list
	 * @return
	 */
	public static GJDrawView getSelectedDrawView(List<GJDrawView> list) {
		for (GJDrawView drawview : list) {
			Boolean tag = (Boolean) drawview.getTag(TAG_SELECTED);
			if (tag != null && tag) {
				return drawview;
			}
		}
		return null;
	}

	/**
	 * 一个也不选择
	 * 
	 * @param list
	 */
	public static void selectNull(List<GJDrawView> list) {
		for (GJDrawView drawview : list) {
			drawview.setBackgroundColor(Color.WHITE);
			drawview.setTag(TAG_SELECTED, false);
		}
	}
}
