package com.winway.android.edcollection.base.widgets;

import com.winway.android.edcollection.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * head栏组件
 * 
 * @author zgq
 *
 */
public class HeadComponent extends RelativeLayout {

	private TextView tvReturn;
	private TextView tvTitle;
	private TextView tvOk;

	private boolean isShowReturn;// 是否显示返回按钮
	private boolean isShowOk;// 是否显示确定按钮

	public HeadComponent(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		 //if (!isInEditMode()) {
		init(context, attrs);
		 //}

	}

	public HeadComponent(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		//if (!isInEditMode()) {
		init(context, attrs);
		 //}

	}

	@SuppressLint("Recycle")
	private void init(Context context, AttributeSet attrs) {
		// 初始化ui
		View view = View.inflate(getContext(), R.layout.head_item, this);
		// 初始化控件
		tvTitle = (TextView) view.findViewById(R.id.tv_head_item_title);
		tvReturn = (TextView) view.findViewById(R.id.tv_head_item_return);
		tvOk = (TextView) view.findViewById(R.id.tv_head_item_ok);

		// 获得自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HeadComponentAttr);
		int count = ta.getIndexCount();
		for (int i = 0; i < count; i++) {
			int itemId = ta.getIndex(i); // 获取某个属性的Id值
			switch (itemId) {
			case R.styleable.HeadComponentAttr_headTitle: // 设置标题
			{
				tvTitle.setText(ta.getString(itemId));
				break;
			}
			case R.styleable.HeadComponentAttr_isShowReturn: // 是否显示返回按钮
			{
				isShowReturn = ta.getBoolean(itemId, false);
				if (isShowReturn) {
					tvReturn.setVisibility(View.VISIBLE);
				} else {
					tvReturn.setVisibility(View.GONE);
				}

				break;
			}
			case R.styleable.HeadComponentAttr_isShowOk: // 是否显示确定按钮
			{
				isShowOk = ta.getBoolean(itemId, false);
				if (isShowOk) {
					tvOk.setVisibility(View.VISIBLE);
				} else {
					tvOk.setVisibility(View.GONE);
				}

				break;
			}

			default:
				break;
			}
		}

		// 回收资源
		ta.recycle();
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	/**
	 * 获取返回view
	 * 
	 * @return
	 */
	public TextView getReturnView() {
		return this.tvReturn;
	}

	/**
	 * 获取确定view
	 * 
	 * @return
	 */
	public TextView getOkView() {
		return this.tvOk;
	}

	/**
	 * 
	 * @return
	 */
	public TextView getTitleView() {
		return this.tvTitle;
	}

}
