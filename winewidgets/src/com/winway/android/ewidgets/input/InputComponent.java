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
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 输入框组件
 * 
 * @author zgq
 *
 */
public class InputComponent extends RelativeLayout {

	private TextView tvTitle;
	private TextView tvUnit;
	private EditText edtTxt;
	private ImageView ivClear;// 清空文本
	private ImageView ivShowHexagram;// 显示六角星
	private boolean isShowUnit = false;// 是否显示单位
	private boolean isShowTitle = true;// 默认显示
	private boolean isShowClearIcon = true;// 是否显示清空按钮，默认显示
	private boolean isShowHexagram = false;// 是否显示星号，默认不显示

	public InputComponent(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public InputComponent(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	@SuppressLint("Recycle")
	private void init(Context context, AttributeSet attrs) {
		// 初始化ui
		View view = View.inflate(getContext(), R.layout.ewidgets_input_item, this);
		// 初始化控件
		tvTitle = (TextView) view.findViewById(R.id.tv_ewidgets_input_item_title);
		tvUnit = (TextView) view.findViewById(R.id.tv_ewidgets_input_item_unit);
		edtTxt = (EditText) view.findViewById(R.id.edtTxt_ewidgets_input_item);
		ivClear = (ImageView) view.findViewById(R.id.iv_ewidgets_input_item_clear);
		ivShowHexagram = (ImageView) view.findViewById(R.id.iv_ewidgets_input_item_required);
		// 获得自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EInputComponentAttr);
		int count = ta.getIndexCount();
		for (int i = 0; i < count; i++) {
			int itemId = ta.getIndex(i); // 获取某个属性的Id值
			if (itemId == R.styleable.EInputComponentAttr_ewidget_inputCom_isShowUnit) {// 设置当前单位是否显示的情况
				isShowUnit = ta.getBoolean(itemId, false);
				if (isShowUnit) {
					tvUnit.setVisibility(View.VISIBLE);
				} else {
					tvUnit.setVisibility(View.GONE);
				}
			} else if (itemId == R.styleable.EInputComponentAttr_ewidget_inputCom_isShowTitle) {// 设置当前标题是否显示
				isShowTitle = ta.getBoolean(itemId, false);
				if (isShowTitle) {
					tvTitle.setVisibility(View.VISIBLE);
				} else {
					tvTitle.setVisibility(View.GONE);
				}
			} else if (itemId == R.styleable.EInputComponentAttr_ewidget_inputCom_hint) {// 输入框提示
				edtTxt.setHint(ta.getString(itemId));
			} else if (itemId == R.styleable.EInputComponentAttr_ewidget_inputCom_title) {// 标题
				tvTitle.setText(ta.getString(itemId));
			} else if (itemId == R.styleable.EInputComponentAttr_ewidget_inputCom_unit) {// 单位
				tvUnit.setText(ta.getString(itemId));
			} else if (itemId == R.styleable.EInputComponentAttr_ewidget_inputCom_isShowClearIcon) {// 是否显示清空按钮
				isShowClearIcon = ta.getBoolean(itemId, false);
				if (isShowClearIcon) {
					ivClear.setVisibility(View.VISIBLE);
				} else {
					ivClear.setVisibility(View.GONE);
				}
			} else if (itemId == R.styleable.EInputComponentAttr_ewidget_inputCom_isShowHexagram) {// 是否显示星号
				isShowHexagram=ta.getBoolean(itemId, false);
				if (isShowHexagram) {
					ivShowHexagram.setVisibility(View.VISIBLE);
				} else {
					ivShowHexagram.setVisibility(View.GONE);
				}
			}

		}

		// 设置输入框事件监听
		edtTxt.addTextChangedListener(new EditTextWatcher());
		edtTxt.setOnFocusChangeListener(new EditTextFocusChangeListener());
		// 清空按钮监听器
		ivClear.setOnClickListener(new ClearClickHandler());

		// 回收资源
		ta.recycle();
	}

	// 输入框文本改变
	class EditTextWatcher implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (isShowClearIcon == false) {
				ivClear.setVisibility(View.GONE);
				return;
			}
			if (!edtTxt.getText().toString().isEmpty()) {
				ivClear.setVisibility(View.VISIBLE);
			} else {
				ivClear.setVisibility(View.GONE);
			}

		}

	}

	// 输入框焦点改变
	class EditTextFocusChangeListener implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if (isShowClearIcon == false) {
				ivClear.setVisibility(View.GONE);
				return;
			}
			if (hasFocus && !edtTxt.getText().toString().isEmpty()) {
				ivClear.setVisibility(View.VISIBLE);
			} else {
				ivClear.setVisibility(View.GONE);
			}

		}

	}

	// 清空输入框
	class ClearClickHandler implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			edtTxt.setText("");
			ivClear.setVisibility(View.GONE);
		}

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
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setUnit(String title) {
		tvUnit.setText(title);
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

	/**
	 * 是否显示了清空按钮
	 * 
	 * @return
	 */
	public boolean isShowClearIcon() {
		if (ivClear.getVisibility() == View.VISIBLE) {
			return true;
		} else {
			return false;
		}
	}

	public void setShowClearIcon(boolean isShowClearIcon) {
		if (isShowClearIcon) {
			ivClear.setVisibility(View.VISIBLE);
		} else {
			ivClear.setVisibility(View.GONE);
		}
	}
}
