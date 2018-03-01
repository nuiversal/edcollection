package com.winway.android.ewidgets.input;

import com.winway.android.ewidgets.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 多行输入框组件
 * 
 * @author zgq
 *
 */
public class InputComponentMultiline extends LinearLayout {

	private EditText edtTxt;

	public InputComponentMultiline(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public InputComponentMultiline(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	@SuppressLint("Recycle")
	private void init(Context context, AttributeSet attrs) {
		// 初始化ui
		View view = View.inflate(getContext(), R.layout.ewidgets_input_item_multiline, this);
		// 初始化控件
		edtTxt = (EditText) view.findViewById(R.id.edtTxt_ewidgets_input_item_multiline);

		// 获得自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EMultilineInputComponentAttr);
		int count = ta.getIndexCount();
		for (int i = 0; i < count; i++) {
			int itemId = ta.getIndex(i); // 获取某个属性的Id值
			if (itemId == R.styleable.EMultilineInputComponentAttr_ewidget_inputComMultiline_hint) {// 输入框提示
				edtTxt.setHint(ta.getString(itemId));
			}

		}

		// 回收资源
		ta.recycle();
	}

	/**
	 * 设置输入框提示
	 * 
	 * @param title
	 */
	public void setEdtTextHit(String hit) {
		edtTxt.setHint(hit);
	}

	/**
	 * 获取输入框的值
	 */
	public String getEdtTextValue() {
		return edtTxt.getText().toString();
	}

	/**
	 * 设置输入框的值
	 * 
	 * @param value
	 */
	public void setEdtText(String value) {
		edtTxt.setText(value);
	}

	/**
	 * 获取输入框
	 * 
	 * @return
	 */
	public EditText getEditTextView() {
		return edtTxt;
	}

	/**
	 * 设置输入框的类型
	 * 
	 * @param inputType
	 */
	public void setEditTextInputType(int inputType) {
		edtTxt.setInputType(inputType);
	}

	/**
	 * 控制输入框焦点
	 * 
	 * @param enabled
	 */
	public void setEditTextFocus(boolean enabled) {
		edtTxt.setFocusable(enabled);
	}

	/**
	 * 设置组件获得焦点
	 */
	public void setFocus() {
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
	}

}
