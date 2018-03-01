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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 登录输入框组件
 * 
 * @author zgq
 *
 */
public class LoginInputComponent extends LinearLayout {

	private EditText edtTxtUser;
	private EditText edtTxtPwd;
	private ImageView ivClearUser;// 清空文本
	private ImageView ivClearPwd;// 清空文本
	private boolean isEdtTxtUserFocus = false;
	private boolean isEdtTxtPwdFocus = false;

	public LoginInputComponent(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public LoginInputComponent(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	@SuppressLint("Recycle")
	private void init(Context context, AttributeSet attrs) {
		// 初始化ui
		View view = View.inflate(getContext(), R.layout.ewidgets_login_input, this);
		// 初始化控件
		edtTxtUser = (EditText) view.findViewById(R.id.edtTxt_ewidgets_login_loginName);
		edtTxtPwd = (EditText) view.findViewById(R.id.edtTxt_ewidgets_login_loginPwd);
		ivClearUser = (ImageView) view.findViewById(R.id.iv_ewidgets_login_clearLoginName);
		ivClearPwd = (ImageView) view.findViewById(R.id.iv_ewidgets_login_clearPwd);

		// 获得自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ELoginInputComponentAttr);
		int count = ta.getIndexCount();
		for (int i = 0; i < count; i++) {
			int itemId = ta.getIndex(i); // 获取某个属性的Id值
			if (itemId == R.styleable.ELoginInputComponentAttr_ewidget_loginInputCom_inputHeight) {// 输入框的高度
				float height = ta.getDimension(itemId, 40);
				edtTxtUser.setHeight((int) height);
				edtTxtPwd.setHeight((int) height);
			}

		}

		// 设置输入框事件监听
		edtTxtUser.addTextChangedListener(textWatcher);
		edtTxtUser.setOnFocusChangeListener(onFocusChangeListener);

		edtTxtPwd.addTextChangedListener(textWatcher);
		edtTxtPwd.setOnFocusChangeListener(onFocusChangeListener);
		// 清空按钮监听器
		ivClearUser.setOnClickListener(orcl);
		ivClearPwd.setOnClickListener(orcl);

		// 让组件获得焦点
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);

		// 回收资源
		ta.recycle();
	}

	// 输入框文本改变
	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (isEdtTxtUserFocus) {
				if (!edtTxtUser.getText().toString().isEmpty()) {
					ivClearUser.setVisibility(View.VISIBLE);
				} else {
					ivClearUser.setVisibility(View.GONE);
				}
			}
			if (isEdtTxtPwdFocus) {
				if (!edtTxtPwd.getText().toString().isEmpty()) {
					ivClearPwd.setVisibility(View.VISIBLE);
				} else {
					ivClearPwd.setVisibility(View.GONE);
				}
			}

		}
	};

	private OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.edtTxt_ewidgets_login_loginName) {
				if (hasFocus) {
					isEdtTxtUserFocus = true;
					isEdtTxtPwdFocus = false;
				}
				if (hasFocus && !edtTxtUser.getText().toString().isEmpty()) {
					ivClearUser.setVisibility(View.VISIBLE);
				} else {
					ivClearUser.setVisibility(View.GONE);
				}

			} else if (id == R.id.edtTxt_ewidgets_login_loginPwd) {
				if (hasFocus) {
					isEdtTxtUserFocus = false;
					isEdtTxtPwdFocus = true;
				}
				if (hasFocus && !edtTxtPwd.getText().toString().isEmpty()) {
					ivClearPwd.setVisibility(View.VISIBLE);
				} else {
					ivClearPwd.setVisibility(View.GONE);
				}
			}
		}
	};

	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.iv_ewidgets_login_clearLoginName) {
				edtTxtUser.setText("");
				ivClearUser.setVisibility(View.GONE);
			} else if (id == R.id.iv_ewidgets_login_clearPwd) {
				edtTxtPwd.setText("");
				ivClearPwd.setVisibility(View.GONE);
			}

		}
	};

	/**
	 * 获取登录名
	 * 
	 * @return
	 */
	public String getLoginName() {
		return this.edtTxtUser.getText().toString();
	}

	/**
	 * 获取登录密码
	 * 
	 * @return
	 */
	public String getLoginPwd() {
		return this.edtTxtPwd.getText().toString();
	}

	/**
	 * 获取用户名组件
	 * 
	 * @return
	 */
	public EditText getUserEditView() {
		return this.edtTxtUser;
	}

	/**
	 * 获取密码组件
	 * 
	 * @return
	 */
	public EditText getPwdEditView() {
		return this.edtTxtPwd;
	}

}
