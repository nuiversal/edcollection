package com.winway.android.ewidgets.search;

import java.util.Timer;
import java.util.TimerTask;

import com.winway.android.ewidgets.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 输入框组件
 * 
 * @author zgq
 *
 */
public class SearchComponent extends LinearLayout {

	private Context context;
	private EditText edtTxt;// 输入框
	private ImageView ivBack;// 返回按钮
	private ImageView ivClear;// 清空按钮
	private ImageView ivLoading;// 加载中
	private Button btnSearch;// 查询按钮
	private LinearLayout llContainer;// 容器

	public SearchComponent(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public SearchComponent(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	@SuppressLint("Recycle")
	private void init(Context context, AttributeSet attrs) {
		this.context = context;
		// 初始化ui
		View view = View.inflate(getContext(), R.layout.ewidgets_search, this);
		// 初始化控件
		edtTxt = (EditText) view.findViewById(R.id.edtTxt_ewidgets_search_value);
		ivBack = (ImageView) view.findViewById(R.id.iv_ewidgets_search_back);
		ivClear = (ImageView) view.findViewById(R.id.iv_ewidgets_search_clear);
		ivLoading = (ImageView) view.findViewById(R.id.iv_ewidgets_search_loading);
		btnSearch = (Button) view.findViewById(R.id.btn_ewidgets_search_ok);
		llContainer = (LinearLayout) view.findViewById(R.id.ll_ewidgets_search_container);
		// 获得自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ESearchComponent);
		int count = ta.getIndexCount();
		for (int i = 0; i < count; i++) {
			int itemId = ta.getIndex(i); // 获取某个属性的Id值
			if (itemId == R.styleable.ESearchComponent_ewidget_esearch_hint) {// 输入框提示
				edtTxt.setHint(ta.getString(itemId));
			}
		}

		// 设置输入框事件监听
		edtTxt.addTextChangedListener(new EditTextWatcher());
		edtTxt.setOnFocusChangeListener(new EditTextFocusChangeListener());
		// 按钮监听器
		ivClear.setOnClickListener(orcl);
		ivBack.setOnClickListener(orcl);
		btnSearch.setOnClickListener(orcl);

		// 回收资源
		ta.recycle();
	}

	/**
	 * 查询组件回调
	 * 
	 * @author winway_zgq
	 *
	 */
	public interface SearchComponentCallback {
		/**
		 * 返回
		 */
		void back();

		/**
		 * 加载中
		 */
		void loading();

		/**
		 * 清空
		 */
		void clear();

		/**
		 * 查询
		 */
		void search();

	}

	private SearchComponentCallback searchComponentCallback = null;

	public void setSearchComponentCallback(SearchComponentCallback sCallback) {
		this.searchComponentCallback = sCallback;
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
			if (!edtTxt.getText().toString().isEmpty()) {
				btnSearch.setVisibility(View.VISIBLE);
				ivLoading.setVisibility(View.VISIBLE);
				ivClear.setVisibility(View.GONE);
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						((Activity) context).runOnUiThread(new Runnable() {
							public void run() {
								ivLoading.setVisibility(View.GONE);
								ivClear.setVisibility(View.VISIBLE);
							}
						});

					}
				}, 1000);
				// 执行回调
				if (searchComponentCallback != null) {
					searchComponentCallback.loading();
				}

			} else {
				ivClear.setVisibility(View.GONE);
				btnSearch.setVisibility(View.GONE);
				ivLoading.setVisibility(View.GONE);
			}

		}

	}

	// 输入框焦点改变
	class EditTextFocusChangeListener implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if (hasFocus && !edtTxt.getText().toString().isEmpty()) {
				ivClear.setVisibility(View.VISIBLE);
			} else {
				ivClear.setVisibility(View.GONE);
			}

		}

	}

	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.iv_ewidgets_search_back) {// 返回
				if (searchComponentCallback != null) {
					searchComponentCallback.back();
				}
			} else if (id == R.id.iv_ewidgets_search_clear) {// 清空
				if (searchComponentCallback != null) {
					searchComponentCallback.clear();
				}
				edtTxt.setText("");
				ivClear.setVisibility(View.GONE);
			} else if (id == R.id.btn_ewidgets_search_ok) {// 查询
				if (searchComponentCallback != null) {
					searchComponentCallback.search();
				}
			}
		}
	};

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

	public ImageView getIvBack() {
		return ivBack;
	}

	public LinearLayout getLlContainer() {
		return llContainer;
	}

}
