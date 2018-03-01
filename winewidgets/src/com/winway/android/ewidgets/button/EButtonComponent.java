package com.winway.android.ewidgets.button;

import com.winway.android.ewidgets.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * 按钮组件
 * 
 * @author winway_zgq
 *
 */
public class EButtonComponent extends Button {

	public EButtonComponent(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public EButtonComponent(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		// 设置默认按钮背景资源
		this.setBackgroundResource(R.drawable.button_search);
		// 获得自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EButtonComponent);
		int count = ta.getIndexCount();
		for (int i = 0; i < count; i++) {
			int itemId = ta.getIndex(i); // 获取某个属性的Id值
			if (itemId == R.styleable.EButtonComponent_ewidget_ebutton_backgroundDrawable) {// 设置按钮背景资源
				Drawable drawable = ta.getDrawable(itemId);
				this.setBackgroundDrawable(drawable);
			}

		}
	}

}
