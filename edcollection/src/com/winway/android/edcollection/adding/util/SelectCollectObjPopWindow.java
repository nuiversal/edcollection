package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.AddTempLineActivity;
import com.winway.android.edcollection.adding.activity.SelectLineActivity;
import com.winway.android.edcollection.adding.activity.SubstationActivity;
import com.winway.android.edcollection.adding.adapter.PopLineAdapter;
import com.winway.android.edcollection.adding.adapter.PopLineAdapter.ViewHolder;
import com.winway.android.edcollection.adding.adapter.PopTypeAdapter;
import com.winway.android.edcollection.adding.bll.SelectCollectObjBll;
import com.winway.android.edcollection.adding.controll.SelectLineControll;
import com.winway.android.edcollection.adding.controll.SubstationControll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.main.controll.MainControll;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.map.util.MapUtils;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.DeviceUtils;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.ToastUtil;

/**
 * 选择采集对象PopupWindow
 * 
 * @author ly
 *
 */
public class SelectCollectObjPopWindow {
	private Activity mActivity;
	private PopupWindow mPopup;
	private ListView lvlinelist;
	private ListView lvtypelist;
	private ImageView ivline;
	private ImageView ivtype;
	private TextView searchLine;
	private TextView ivAddLine;
	private boolean lineIsOpen = true;// 是否显示线路列表，默认显示
	private boolean typeIsOpen = true;// 是否显示路径点类型列表，默认显示
	private static PopLineAdapter lineAdapter;
	private PopTypeAdapter typeAdapter;
	private Object typeObject;
	private View contentView;
	private static SelectCollectObjPopWindow instance;
	private SelectCollectObjBll selectCollectObjBll = null;
	private TextView titleLine;
	private ArrayList<EcLineEntity> lineList;
	private ArrayList<EcLineEntity> templineList = new ArrayList<EcLineEntity>();
	private TextView tvAddsubstation;
	private CommonBll commonBll;

	private SelectCollectObjPopWindow() {
	};

	/**
	 * 获取单例
	 * 
	 * @param activity
	 * @return NewPopUtil对象
	 */
	public static SelectCollectObjPopWindow getIntance(Activity activity) {
		if (instance == null) {
			synchronized (SelectCollectObjPopWindow.class) {
				if (instance == null) {
					instance = new SelectCollectObjPopWindow(activity);
				}
			}
		}

		return instance;
	}

	private SelectCollectObjPopWindow(Activity activity) {
		this.mActivity = activity;
		commonBll = new CommonBll(mActivity, GlobalEntry.prjDbUrl);
		selectCollectObjBll = new SelectCollectObjBll(mActivity,GlobalEntry.prjDbUrl);
	}

	/**
	 * 显示PopupWindow
	 * 
	 * @param view
	 */
	public void showNewPopWid(View view, TextView titleLine) {
		this.titleLine = titleLine;
		initUI(view);
		initData();
		initEvent();
	}

	// 初始化事件
	private void initEvent() {
		ivline.setOnClickListener(orl);
		searchLine.setOnClickListener(orl);
		ivtype.setOnClickListener(orl);
		ivAddLine.setOnClickListener(orl);
		tvAddsubstation.setOnClickListener(orl);
	}

	/**
	 * 删除线路，如果此线路已有关联的路径点，不能删除。
	 * 
	 * @param ecLineId
	 */
	public void deleteLine(int position) {
		List<EcNodeEntity> nodes = new ArrayList<>();
		String expr = "EC_LINE_ID ='" + lineList.get(position).getEcLineId() + "'";
		List<EcLineNodesEntity> lineNodesList = commonBll.queryByExpr2(EcLineNodesEntity.class, expr);
		if (lineNodesList != null && lineNodesList.size() > 0) {
			for (EcLineNodesEntity ecLineNodesEntity : lineNodesList) {
				String expr2 = "OID ='" + ecLineNodesEntity.getOid() + "'";
				List<EcNodeEntity> nodeList = commonBll.queryByExpr2(EcNodeEntity.class, expr2);
				if (nodeList != null && nodeList.size() > 0) {
					nodes.addAll(nodeList);
				}
			}
		}
		if (nodes != null && nodes.size() > 0) {
			ToastUtil.show(mActivity, "此线路已有采集数据,不能删除", 200);
			nodes.clear();
			return;
		} else {
			commonBll.deleteById(EcLineEntity.class, lineList.get(position).getEcLineId());
			commonBll.handleDataLog(lineList.get(position).getEcLineId(), TableNameEnum.SDXL.getTableName(),
					DataLogOperatorType.delete, WhetherEnum.NO, "删除线路", false);
			lineList.remove(position);
			templineList.clear();
			templineList.addAll(lineList);
			// 刷新台帐树
			TreeRefleshUtil util = TreeRefleshUtil.getSingleton();
			if (util != null) {
				util.refleshWholeTree();
			}
			lineAdapter.notifyDataSetInvalidated();
			titleLine.setText("");
			lineAdapter.initSelected();
			lineAdapter.getSelectdValue();
			ToastUtil.show(mActivity, "删除线路成功", 200);
		}
	}

	// 初始化数据
	private void initData() {
		// 已先线路列表
		ArrayList<EcLineEntity> selectedLineList = SelectLineControll.selectedDatas;
		if (selectedLineList != null && selectedLineList.size() > 0) {
			lineList.clear();
			lineList.addAll(selectedLineList);
		} else {
			// 拿到所有线路名称
			lineList = selectCollectObjBll.getLineListByOrgId(GlobalEntry.currentProject.getOrgId(), mActivity);
			// 将所有线路添加到临时线路列表
			templineList.addAll(lineList);
		}
		if (lineAdapter == null) {
			lineAdapter = new PopLineAdapter(mActivity, lineList);
			lineAdapter.setPopLineItemClickListener(popLineItemClickListener);
		}
		lvlinelist.setAdapter(lineAdapter);
		if (typeAdapter == null) {
			String[] nodeType = mActivity.getResources().getStringArray(R.array.nodeType);
			typeAdapter = new PopTypeAdapter(mActivity, new ArrayList<String>(Arrays.asList(nodeType)));
			typeAdapter.setPopTypeItemClickListener(popTypeItemClickListener);
		}
		lvtypelist.setAdapter(typeAdapter);

	}

	/**
	 * 更新列表
	 */
	public void updateLineList() {
		// 已先线路列表
		ArrayList<EcLineEntity> selectedLineList = SelectLineControll.selectedDatas;
		if (selectedLineList != null && selectedLineList.size() > 0) {
			ArrayList<EcLineEntity> lineList1 = selectCollectObjBll
					.getLineListByOrgId(GlobalEntry.currentProject.getOrgId(), mActivity);
			// 所有线路的数量大于临时线路的数量，说明是增加了线路
			if (lineList1.size() > templineList.size()) {
				EcLineEntity ecLineEntity = lineList1.get(0);
				selectedLineList.add(ecLineEntity);
				templineList.add(ecLineEntity);
			}
			lineList.clear();
			lineList.addAll(selectedLineList);
		} else {
			lineList = selectCollectObjBll.getLineListByOrgId(GlobalEntry.currentProject.getOrgId(), mActivity);
		}
		if (lineList != null && lineList.size() > 0) {
			lineAdapter = null;
			lineAdapter = new PopLineAdapter(mActivity, lineList);
			lineAdapter.setPopLineItemClickListener(popLineItemClickListener);
			if (lvlinelist == null) {
				contentView = LayoutInflater.from(mActivity).inflate(R.layout.new_pop, null);
				lvlinelist = (ListView) contentView.findViewById(R.id.lv_newpop_linelist);
			}
			lvlinelist.setAdapter(lineAdapter);
		}	
	}

	// 初始化界面
	private void initUI(View view) {
		contentView = LayoutInflater.from(mActivity).inflate(R.layout.new_pop, null);
		lvlinelist = (ListView) contentView.findViewById(R.id.lv_newpop_linelist);
		lvtypelist = (ListView) contentView.findViewById(R.id.lv_newpop_typelist);
		ivline = (ImageView) contentView.findViewById(R.id.iv_new_pop_fold);
		ivtype = (ImageView) contentView.findViewById(R.id.iv_newpop_type);
		searchLine = (TextView) contentView.findViewById(R.id.tv_new_pop_search);
		ivAddLine = (TextView) contentView.findViewById(R.id.tv_new_pop_addTempLine);
		tvAddsubstation = (TextView) contentView.findViewById(R.id.tv_new_pop_addSubstation);
		// 获取屏幕的宽高
		int width = DeviceUtils.getScreenWidth(mActivity);
		int height = DeviceUtils.getScreenHeight(mActivity);
		if (null == mPopup || !mPopup.isShowing()) {
			// 设置PopupWindow的大小
			mPopup = new PopupWindow(contentView, width, height / 2);
			mPopup.showAsDropDown(view, 0, 0);
		}
	}

	/**
	 * 关闭PopupWindow
	 */
	public void closeNewPopWid() {
		if (mPopup != null) {
			mPopup.dismiss();
		}
	}

	/**
	 *  线路列表项点击监听
	 */
	private PopLineAdapter.OnPopLineItemClickListener popLineItemClickListener = new PopLineAdapter.OnPopLineItemClickListener() {
		@Override
		public void onClickItem(View v, int position) {
			ViewHolder holder = (ViewHolder) v.getTag();
			holder.cbIscheck.toggle();
			lineAdapter.getSelectedPosition().put(position, holder.cbIscheck.isChecked());
			lineAdapter.notifyDataSetChanged();
			List<EcLineEntity> lineEntities = lineAdapter.getSelectdValue();
			String line = "";
			if (lineEntities != null && lineEntities.size() > 0) {
				for (int i = 0; i < lineEntities.size(); i++) {
					if (i == 0) {
						line += lineEntities.get(i).getName();
					} else {
						line += "," + lineEntities.get(i).getName();
					}
				}
			} else {
				line = "请选择";
			}
			titleLine.setText(line);
		}

		@Override
		public void onLongClickItem(View v, int positoion) {
			chooseEditOrDelDialog(positoion);
		}
	};
	
	int index = 0;
	/**
	 * 选择线路编辑或删除弹框
	 * @param position
	 */
	private void chooseEditOrDelDialog(final int position) {
			final DialogUtil dialogUtil = new DialogUtil(mActivity);
			View layout = View.inflate(mActivity, R.layout.dialog_line_choose_operation_container, null);
			layout.findViewById(R.id.close).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialogUtil.dismissDialog();
					dialogUtil.destroy();
				}
			});

			RadioGroup radioGroup = (RadioGroup) layout.findViewById(R.id.rg_dialog_line_choose_container);
			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if (checkedId == R.id.rb_edit_line) {
						index = 0;
					} else if (checkedId == R.id.rb_delete_line) {
						index = 1;
					} 
				}
			});
			layout.findViewById(R.id.btn_dialog_line_choose_confim)
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							switch (index) {
							case 0: { //编辑线路
								Intent intent = new Intent();
								intent.putExtra("ecLineEntity", lineList.get(position));
								intent.setClass(mActivity, AddTempLineActivity.class);
								AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, MainControll.requestCode_addtempline);
								dialogUtil.dismissDialog();
								dialogUtil.destroy();
								break;
							}
							case 1: { //删除线路
								deleteLine(position);
								dialogUtil.dismissDialog();
								dialogUtil.destroy();
								index = 0;
								break;
							}
							default:
								break;
							}
						}
					});
			dialogUtil.showAlertDialog(layout);
	}
	
	// 路径点列表项点击监听
	private PopTypeAdapter.OnPopTypeItemClickListener popTypeItemClickListener = new PopTypeAdapter.OnPopTypeItemClickListener() {
		@Override
		public void onItemClick(int position) {
			typeAdapter.setSelectedPosition(position);
			typeAdapter.notifyDataSetChanged();
		}
	};

	/**
	 * 获取线路选中的值
	 * 
	 * @return
	 */
	public static List<EcLineEntity> getWaitingLines() {
		if (lineAdapter != null) {
			return lineAdapter.getSelectdValue();
		}
		return null;

	}

	/**
	 * 获取路径点选中的值
	 * 
	 * @return
	 */
	public Object getTypeValue() {
		typeObject = typeAdapter.getItem(typeAdapter.getSelectedPosition());
		return typeObject;
	}

	// 点击事件
	private OnClickListener orl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_new_pop_fold: {
				if (lineIsOpen) {
					lvlinelist.setVisibility(View.VISIBLE);
					ivline.setBackgroundResource(R.drawable.list_shouqi);
					lineIsOpen = false;
				} else {
					lvlinelist.setVisibility(View.GONE);
					ivline.setBackgroundResource(R.drawable.list_zhankai);
					lineIsOpen = true;
				}
				break;
			}
			case R.id.iv_newpop_type: {
				if (typeIsOpen) {
					lvtypelist.setVisibility(View.VISIBLE);
					ivtype.setBackgroundResource(R.drawable.list_shouqi);
					typeIsOpen = false;
				} else {
					lvtypelist.setVisibility(View.GONE);
					ivtype.setBackgroundResource(R.drawable.list_zhankai);
					typeIsOpen = true;
				}
				break;
			}
			case R.id.tv_new_pop_search: {
				AndroidBasicComponentUtils.launchActivityForResult(mActivity, SelectLineActivity.class,
						MainControll.flag_search_line);
				break;
			}
			case R.id.tv_new_pop_addSubstation: {// 新增变电站
				Intent intent = new Intent(mActivity, SubstationActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("EcSubstationEntity", MainControll.ecSubstationEntityVo);
				intent.putExtra("coord", MapUtils.getInstance().getMapCenter());
				intent.putExtra("tag", MainControll.requestCode_subtation + "");
				intent.putExtras(bundle);
				AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent,
						MainControll.requestCode_subtation);
				SubstationControll.isNewSubstation = true;
				mPopup.dismiss();
				break;
			}
			case R.id.tv_new_pop_addTempLine: {// 新增临时线路
				AndroidBasicComponentUtils.launchActivityForResult(mActivity, AddTempLineActivity.class,
						MainControll.requestCode_addtempline);
				break;
			}
			default:
				break;
			}
		}
	};
}
