package com.winway.android.ewidgets.input;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.input.adapter.DataItemAdapter;
import com.winway.android.ewidgets.input.entity.DataItem;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 带下拉框的输入组件
 * 
 * @author zgq
 *
 */
public class InputSelectComponent extends RelativeLayout {

	private TextView tvTitle;
	private ImageView ivSelect;
	private AutoCompleteTextView actvTxt;
	private View view;// 父容器
	private Context context;

	private PopupWindow mPopup; // 点击图片弹出popupwindow
	// private PopupWindow mSearchPopupWindow;// 查询结果popwindow
	private BaseAdapter adapter;// 适配器
	private DataItemAdapter searchResultAdapter;// 查询结果适配器
	private Integer selectPo = 0;// 选中的item
	private Integer searchfrom = 1;// 从第几位开始查询
	private List<DataItem> dataItems;// 需要查询的数据集

	public InputSelectComponent(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public InputSelectComponent(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	@SuppressLint("Recycle")
	private void init(Context context, AttributeSet attrs) {
		this.context = context;
		// 初始化ui
		View view = View.inflate(getContext(), R.layout.ewidgets_input_select_item, this);
		this.view = view;
		// 初始化控件
		tvTitle = (TextView) view.findViewById(R.id.tv_ewidget_input_select_item_title);
		actvTxt = (AutoCompleteTextView) view.findViewById(R.id.actvTxt_ewidget_input_select_item);
		ivSelect = (ImageView) view.findViewById(R.id.iv_ewidget_input_select_item_select);

		// 获得自定义属性
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EInputSelectComponentAttr);
		int count = ta.getIndexCount();
		for (int i = 0; i < count; i++) {
			int itemId = ta.getIndex(i); // 获取某个属性的Id值
			if (itemId == R.styleable.EInputSelectComponentAttr_ewidget_inputSelect_hint) {// 输入框提示
				actvTxt.setHint(ta.getString(itemId));
			} else if (itemId == R.styleable.EInputSelectComponentAttr_ewidget_inputSelect_title) { // 标题
				tvTitle.setText(ta.getString(itemId));
			} else if (itemId == R.styleable.EInputSelectComponentAttr_ewidget_inputSelect_justSelect) {
				if (ta.getBoolean(itemId, false)) {
					setJustSelect(false);
				}
			}
		}

		// 回收资源
		ta.recycle();

		// 绑定监听器
		ivSelect.setOnClickListener(orcl);
		// edtTxt.setOnClickListener(orcl);
		// edtTxt.addTextChangedListener(new TextWatcherListener());
	}

	/**
	 * 是否开启查询
	 */
	// private boolean isOpenSearch = false;

	// class TextWatcherListener implements TextWatcher {
	//
	// @Override
	// public void beforeTextChanged(CharSequence s, int start, int count, int
	// after) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onTextChanged(CharSequence s, int start, int before, int count) {
	// // TODO Auto-generated method stub
	// if (isOpenSearch == false) {
	// return;
	// }
	// if (s.length() < 1) {
	// return;
	// }
	// if (textChangeListener != null) {
	// List<DataItem> datas = textChangeListener.search();
	// if (datas != null && datas.size() > 0) {
	// dataItems = datas;
	// // 加载数据
	// searchResultAdapter = new DataItemAdapter(context, (ArrayList<DataItem>)
	// datas);
	// // 显示弹窗
	// showSearchPopupWindow(edtTxt, context);
	//
	// }
	// }
	// }
	//
	// @Override
	// public void afterTextChanged(Editable s) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// }

	/**
	 * 文本改变监听
	 * 
	 * @author winway_zgq
	 *
	 */
	// public interface TextChangeListener {
	// List<DataItem> search();
	//
	// DataItem setSearchItem(DataItem dataItem);
	// }

	// private TextChangeListener textChangeListener = null;

	// public void setTextChangeListener(TextChangeListener textChangeListener) {
	// this.textChangeListener = textChangeListener;
	// }

	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.iv_ewidget_input_select_item_select) {// 下拉按钮触发
				showPopupWindow(actvTxt, context);
			}
			// else if (id == R.id.edtTxt_ewidget_input_select_item) {// 输入框
			// isOpenSearch = true;
			// }
		}
	};

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	/**
	 * 设置输入框提示
	 * 
	 * @param title
	 */
	public void setEdtTextHit(String hit) {
		actvTxt.setText(hit);
	}

	/**
	 * 获取输入框的值
	 */
	public String getEdtTextValue() {
		return actvTxt.getText().toString();
	}

	/**
	 * 设置输入框的值
	 * 
	 * @param value
	 */
	public void setEdtTextValue(String value) {
		actvTxt.setText(value);
	}

	/**
	 * 获取输入框view
	 * 
	 * @return
	 */
	public EditText getEditTextView() {
		return actvTxt;
	}

	/**
	 * 设置从第几位开始查询
	 * 
	 * @param searchfrom
	 */
	public void setSearchfrom(Integer searchfrom) {
		this.searchfrom = searchfrom;
	}

	private ListView listview;
	// private ListView lvSearchResult;
	// private LinearLayout llSearchBox;

	// 弹出框
	private void showPopupWindow(View v, Context context) {
		// 一个自定义的布局，作为显示的内容
		if (mPopup == null) {
			View contentView = View.inflate(context, R.layout.spinner_window_layout, null);
			mPopup = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			mPopup.setWidth(v.getWidth() + ivSelect.getMeasuredWidth());
			listview = (ListView) contentView.findViewById(R.id.lv_spinner_window_layout_list);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(itemClick);
			listview.setDividerHeight(0);
			mPopup.setFocusable(true); // 让popwin获取焦点
			mPopup.setOutsideTouchable(true); // 设置点击屏幕其它地方弹出框消失
			mPopup.setBackgroundDrawable(new ColorDrawable(color.transparent));
		}
		// 设置好参数之后再show
		mPopup.showAsDropDown(v, 0, 5);
	}

	// // 显示查询结果窗口
	// private void showSearchPopupWindow(View v, Context context) {
	// // if (mSearchPopupWindow==null) {
	// View contentView =
	// LayoutInflater.from(this.getContext()).inflate(R.layout.spinner_window_layout,
	// null);
	// mSearchPopupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT, true);
	// mSearchPopupWindow.setWidth(v.getWidth() + ivSelect.getMeasuredWidth());
	// ListView listview = (ListView)
	// contentView.findViewById(R.id.lv_spinner_window_layout_list);
	// llSearchBox = (LinearLayout)
	// contentView.findViewById(R.id.ll_spinner_window_layout_search);
	// lvSearchResult = (ListView)
	// contentView.findViewById(R.id.lv_spinner_window_layout_searchResult);
	// lvSearchResult.setOnItemClickListener(searchResItemClick);
	// mSearchPopupWindow.setFocusable(true); // 让popwin获取焦点
	// mSearchPopupWindow.setOutsideTouchable(true); // 设置点击屏幕其它地方弹出框消失
	// mSearchPopupWindow.setBackgroundDrawable(new
	// ColorDrawable(color.transparent));
	// // 控制显示
	// listview.setVisibility(View.GONE);
	// llSearchBox.setVisibility(View.VISIBLE);
	// // }
	// lvSearchResult.setAdapter(searchResultAdapter);
	// // 设置好参数之后再show
	// mSearchPopupWindow.showAsDropDown(v, 0, 5);
	// }

	/**
	 * listview项点击监听
	 */
	private OnItemClickListener itemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			// isOpenSearch = false;
			mPopup.dismiss();
			actvTxt.setText(adapter.getItem(position).toString());
			selectPo = position;
			if (downSelectListener != null) {
				downSelectListener.handle(position);
			}

		}
	};

	// /**
	// * 查询结果项点击监听
	// */
	// private OnItemClickListener searchResItemClick = new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position, long
	// id) {
	// // TODO Auto-generated method stub
	// isOpenSearch = false;
	// if (textChangeListener != null) {
	// DataItem dataItem = dataItems.get(position);
	// textChangeListener.setSearchItem(dataItems.get(position));
	// mSearchPopupWindow.dismiss();
	// edtTxt.setText(dataItem.getName());
	// }
	// }
	// };

	public interface DropDownSelectListener {
		void handle(int index);
	}

	private DropDownSelectListener downSelectListener = null;

	/**
	 * 绑定回调
	 * 
	 * @param downSelectListener
	 */
	public void setDropDownSelectListener(DropDownSelectListener downSelectListener) {
		this.downSelectListener = downSelectListener;
	}

	/**
	 * 添加适配器
	 * 
	 * @param adapter
	 */
	public void setAdapter(BaseAdapter adapter) {
		this.adapter = adapter;
		try {
			actvTxt.setText(adapter.getItem(0).toString());
		} catch (Exception e) {

		}
	}

	/**
	 * 设置选中
	 * 
	 * @param position
	 */
	public void setSelect(int position) {
		actvTxt.setText(adapter.getItem(position).toString());
		this.selectPo = position;
	}

	public Integer getSelectedItemPosition() {
		return this.selectPo;
	}

	/**
	 * 设置查询适配器
	 * 
	 * @param dataItems
	 */
	public void setDataItemList(List<DataItem> dataItems) {
		this.dataItems = dataItems;
		if (searchResultAdapter == null) {
			searchResultAdapter = new DataItemAdapter(context, (ArrayList<DataItem>) dataItems);
		} else {
			searchResultAdapter.updateDatas(dataItems);
		}
		actvTxt.setAdapter(searchResultAdapter);
		actvTxt.setThreshold(searchfrom);
		actvTxt.setOnItemClickListener(searchClickItemListener);
	}

	/**
	 * 查询点击Item监听
	 */
	private OnItemClickListener searchClickItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (searchResultAdapter == null) {
				return;
			}
			if (searchResultAdapter.getNewValues() == null || searchResultAdapter.getNewValues().size() < 1) {
				return;
			}
			DataItem dataItem = searchResultAdapter.getNewValues().get(position);
			searchResultListener.searchResult(dataItem);
		}
	};

	/**
	 * 查询结果监听接口
	 * 
	 * @author xs
	 *
	 */
	public interface SearchResultListener {
		void searchResult(DataItem dataItem);
	}

	private SearchResultListener searchResultListener;// 查询结果监听器

	/**
	 * 设置查询结果监听器
	 * 
	 * @param searchResultListener
	 */
	public void setSearchResultListener(SearchResultListener searchResultListener) {
		this.searchResultListener = searchResultListener;
	}

	/**
	 * 设置输入框的类型
	 * 
	 * @param inputType
	 */
	public void setEditTextInputType(int inputType) {
		actvTxt.setInputType(inputType);
	}

	/**
	 * 把组件设置成只能下拉选择（不能输入），同时，根据hasTitle的值设置title文字的显示
	 * 
	 * @param hasTitle
	 */
	public void setJustSelect(boolean hasTitle) {
		if (!hasTitle) {
			tvTitle.setVisibility(View.GONE);
		}
		actvTxt.setEnabled(false);
		actvTxt.setHint("");
	}

}
