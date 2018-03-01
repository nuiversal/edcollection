package com.winway.android.edcollection.colist.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.PopLineAdapter;
import com.winway.android.edcollection.adding.util.SelectCollectObjPopWindow;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.colist.adapter.CollLineAdapter;
import com.winway.android.edcollection.colist.adapter.CollTimeAdapter;
import com.winway.android.edcollection.colist.adapter.CollTypeAdapter;
import com.winway.android.edcollection.colist.bll.GetNodeTypeAndCollLineBll;
import com.winway.android.edcollection.colist.controll.CollectedFragmentControll;
import com.winway.android.edcollection.colist.entity.CollectedObject;
import com.winway.android.edcollection.colist.entity.PathPointTypeData;
import com.winway.android.edcollection.project.bll.NodeBll;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.util.FileUtil;
import com.winway.android.util.LogUtil;
import com.winway.android.util.ToastUtil;

/**
 * 已采集下拉框
 * 
 * @author ly
 * 
 */
public class CollectedPopUtil {
	private static CollectedPopUtil instance;
	private Context mCotext;
	private PopupWindow mPopup;
	private BaseAdapter mAdapter;// 接收出入的适配器
	private ListView colectedpoplist;// PopupWindow显示的listview
	private Object valueObject;// 选择的值
	private CollTimeAdapter timeAdapter;// 采集时间适配器
	private CollLineAdapter lineAdapter;// 路径点类型适配器
	private CollTypeAdapter typeAdapter;// 采集路线适配器
	private View collectedClassifyView;//
	private TextView tvTime;// 采集时间的下拉框内容
	private TextView tvType;// 路径点类型的下拉框内容
	private TextView tvLine;// 采集路线的下拉框内容
	private NodeBll nodeBll;
	private Integer timeType;// 下拉框时间采集的选项
	private String lineNo;// 下拉框线路编号
	private String lineType;// 下拉框路径点类型
	private List<EcLineLabelEntity> alineLable;//电子标签
	private CollectedPopUtil() {
	}

	/**
	 * 获取单例
	 * 
	 * @param activity
	 * @return CollectedPopUtil对象
	 */
	public static CollectedPopUtil getIntance(Context mCotext) {
		if (instance == null) {
			synchronized (SelectCollectObjPopWindow.class) {
				if (instance == null) {
					instance = new CollectedPopUtil(mCotext);
				}
			}
		}
		return instance;
	}

	private CollectedPopUtil(Context cotext) {
		this.mCotext = cotext;
		nodeBll = new NodeBll(cotext,
				FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db");
	}

	public void setAdapter(BaseAdapter adapter) {
		this.mAdapter = adapter;
	}

	/**
	 * 显示PopupWindow
	 * 
	 * @param view
	 */
	public void showNewPopWid(View view) {
		this.collectedClassifyView = view;
		View contentView = LayoutInflater.from(mCotext).inflate(R.layout.collected_pop_item, null);
		colectedpoplist = (ListView) contentView.findViewById(R.id.lv_colectedpop_list);
		colectedpoplist.setOnItemClickListener(oicl);
		if (mAdapter != null) {
			colectedpoplist.setAdapter(mAdapter);
			colectedpoplist.setDivider(new ColorDrawable(Color.GRAY));
			colectedpoplist.setDividerHeight(1);
			int width = view.getMeasuredWidth();
			if (null == mPopup || !mPopup.isShowing()) {
				// 设置PopupWindow的大小
				mPopup = new PopupWindow(contentView, width, LayoutParams.WRAP_CONTENT);
				mPopup.setFocusable(true); // 让popwin获取焦点
				mPopup.setOutsideTouchable(true); // 设置点击屏幕其它地方弹出框消失
				mPopup.setBackgroundDrawable(new ColorDrawable(color.transparent));
				mPopup.showAsDropDown(view, 10, 10);
			}
		}

	}

	/**
	 * 关闭PopupWindow
	 */
	public void closeNewPopWid() {
		if (mPopup != null && mPopup.isShowing()) {
			mPopup.dismiss();
		}
	}

	// listview item点击事件
	private OnItemClickListener oicl = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch (parent.getId()) {
			// 时间
			case R.id.lv_colectedpop_list:
				if (mAdapter.getClass() == CollTimeAdapter.class) {
					// 时间选择
					timeAdapter = (CollTimeAdapter) mAdapter;
					timeAdapter.setSelectedPosition(position);
					valueObject = timeAdapter.getItem(timeAdapter.getSelectedPosition());
					tvTime = (TextView) collectedClassifyView.findViewById(R.id.tv_collected_classify_time);
					tvTime.setText((String) valueObject);// 修改采集时间的下拉框内容
					tvTime.setTextColor(mCotext.getResources().getColor(R.color.main_tab_f));
					timeType = position;
				} else if (mAdapter.getClass() == CollLineAdapter.class) {
					// 线路选择
					lineAdapter = (CollLineAdapter) mAdapter;
					lineAdapter.setSelectedPosition(position);
					// 得到当前选中的item
					EcLineEntity item = (EcLineEntity) lineAdapter.getItem(lineAdapter.getSelectedPosition());
					// 拿到线路名称
					String name = item.getName();
					tvLine = (TextView) collectedClassifyView.findViewById(R.id.tv_collected_classify_line);
					tvLine.setText(name);// 修改采集路线的下拉框内容
					tvLine.setTextColor(mCotext.getResources().getColor(R.color.main_tab_f));
					lineNo = item.getLineNo();
				} else if (mAdapter.getClass() == CollTypeAdapter.class) {
					// 路径点类型选择
					typeAdapter = (CollTypeAdapter) mAdapter;
					typeAdapter.setSelectedPosition(position);
					valueObject = typeAdapter.getItem(typeAdapter.getSelectedPosition());
					tvType = (TextView) collectedClassifyView.findViewById(R.id.tv_collected_classify_pointType);
					tvType.setText((String) valueObject);// 修改路径点类型的下拉框内容
					tvType.setTextColor(mCotext.getResources().getColor(R.color.main_tab_f));
					lineType = (String)valueObject;
				}
				// 进行选择操作
				selectInfo(timeType, lineType, lineNo);
				mAdapter.notifyDataSetInvalidated();
				closeNewPopWid();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 根据采集时间、路径点类型、采集路线查询
	 * 
	 * @param timeType
	 *            0 当天，1 本周 ，2本月
	 * @param lineType
	 *            1 标识球，2 标识钉，0 路径点
	 * @param lineNo
	 *            线路编号
	 * @return
	 */
	public void selectInfo(Integer timeType, String lineType, String lineNo) {
		List<CollectedObject> newData = nodeBll.getTimeOrLineTypeOrLineNo(timeType, lineType, lineNo);
		//LogUtil.i(lineNo);
		if(lineNo==null || lineNo.equals("-1")){
			alineLable=CollectedFragmentControll.allLineLable;
		}else {
			alineLable=nodeBll.getAlineLable(lineNo);
			if (!"-1".equals(lineNo)) {
				GetNodeTypeAndCollLineBll.getPointTypes(newData,lineType,alineLable);
				Collections.sort(newData, new Comparator<CollectedObject>() {
					@Override
					public int compare(CollectedObject lhs, CollectedObject rhs) {
						return Integer.valueOf(lhs.getOrderNo())-Integer.valueOf(rhs.getOrderNo());
					}
				});
				CollectedFragmentControll.itemAdapter.update(newData);// 修改
				return;
			}
		}
		if (newData == null || newData.size() == 0) {
			ToastUtil.showShort(mCotext, "无数据");
			PathPointTypeData.reSet();
			CollectedFragmentControll.itemAdapter.update(newData);// 修改
			return;
		}
		if("全部".equals(lineType)){
			GetNodeTypeAndCollLineBll.getPointTypes(newData,lineType,alineLable);
			
			CollectedFragmentControll.itemAdapter.update(newData);// 修改
			return;
		}else if (lineType==null) {
			GetNodeTypeAndCollLineBll.getPointTypes(newData,lineType,alineLable);
			CollectedFragmentControll.itemAdapter.update(newData);// 修改
			return;
		}
		GetNodeTypeAndCollLineBll.getPointTypes(newData, lineType,alineLable);
		CollectedFragmentControll.itemAdapter.update(newData);// 修改
	}

	/**
	 * 获取选中的值
	 * 
	 * @return
	 */
	public Object getLineValue() {
		if (mAdapter.getClass() == CollTimeAdapter.class) {
			CollTimeAdapter timeAdapter = (CollTimeAdapter) mAdapter;
			valueObject = timeAdapter.getItem(timeAdapter.getSelectedPosition());
		} else if (mAdapter.getClass() == PopLineAdapter.class) {
			CollLineAdapter lineAdapter = (CollLineAdapter) mAdapter;
			valueObject = lineAdapter.getItem(lineAdapter.getSelectedPosition());
		} else if (mAdapter.getClass() == CollTypeAdapter.class) {
			CollTypeAdapter typeAdapter = (CollTypeAdapter) mAdapter;
			valueObject = typeAdapter.getItem(typeAdapter.getSelectedPosition());
		}
		return valueObject;
	}
}
