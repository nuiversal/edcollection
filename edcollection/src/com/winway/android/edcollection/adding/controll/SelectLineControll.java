package com.winway.android.edcollection.adding.controll;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.SearchLineAdapter;
import com.winway.android.edcollection.adding.adapter.SelectedLineListAdapter;
import com.winway.android.edcollection.adding.bll.SelectCollectObjBll;
import com.winway.android.edcollection.adding.viewholder.SelectLineViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcLineEntity;

/**搜索线路
 * @author lyh
 * @version 创建时间：2017年2月9日 上午10:34:09
 * 
 */
public class SelectLineControll extends BaseControll<SelectLineViewHolder> {
	private SelectCollectObjBll selectCollectObjBll = null;
	private SearchLineAdapter lineAdapter;
	private ArrayList<EcLineEntity> lineList;
	private SelectedLineListAdapter selectedLineAdapter;
	public static ArrayList<EcLineEntity> selectedDatas = new ArrayList<EcLineEntity>();
	private ArrayList<EcLineEntity> backupsLineList;
	// 存放搜索到的线路
	public static ArrayList<EcLineEntity> tempLineList;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		initData();
		initEvent();
	}

	// 初始化数据
	private void initData() {
		if (selectCollectObjBll == null) {
			selectCollectObjBll = new SelectCollectObjBll(mActivity, GlobalEntry.prjDbUrl);
		}
		// 拿到所有线路名称
		lineList = selectCollectObjBll.getLineListByOrgId(
				GlobalEntry.currentProject.getOrgId(), mActivity);
		if (lineList != null && lineList.size() > 0) {
			// 备份所有线路
			backupsLineList = new ArrayList<EcLineEntity>();
			backupsLineList.addAll(lineList);

			if (lineAdapter == null) {
				// 已选路列表
//				if (selectedDatas == null && selectedDatas.size() <= 0) {
//					selectedDatas = new ArrayList<EcLineEntity>();
//				}
				if (lineList.size() < selectedDatas.size()) {
					selectedDatas.clear();
					selectedDatas.addAll(lineList);
				}
				if (selectedDatas.size() >0 && selectedDatas!=null) {
					for(int i = 0 ; i < selectedDatas.size() ; i++){
						boolean flag = true ;
						for (int j = 0; j < lineList.size() ; j ++ ){
							if (selectedDatas.get(i).getName().equals(lineList.get(j).getName())){
								flag = false; // 相等则说明存在
								continue;
							}
						}
						// 如果遍历完，仍没有相等的，则说明不存在，flag仍然为真
						if(flag){
							selectedDatas.remove(i); // 移除
						}
					}
				}
				selectedLineAdapter = new SelectedLineListAdapter(mActivity, selectedDatas);
				viewHolder.getLvLineList().setAdapter(selectedLineAdapter);
				
				// 添加线路列表
				lineAdapter = new SearchLineAdapter(mActivity, lineList, selectedLineAdapter,
						selectedDatas);
				viewHolder.getLvAddLineList().setAdapter(lineAdapter);
			}
		}
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		// TODO Auto-generated method stub
		viewHolder.getBtnSearch().setOnClickListener(ocl);
		viewHolder.getBtnAddAllLine().setOnClickListener(ocl);
		viewHolder.getBtnRemoveAllLine().setOnClickListener(ocl);
//		viewHolder.getBtnConfirm().setOnClickListener(ocl);
		viewHolder.getHcTitle().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcTitle().getOkView().setOnClickListener(ocl);
	}

	private OnClickListener ocl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_select_line_search: { // 搜索图标
				tempLineList = new ArrayList<EcLineEntity>();
				// 拿到搜索条件
				String searchLine = viewHolder.getIcSearch().getEdtTextValue();
				if (searchLine.equals("")) {
					lineList.clear();
					lineList.addAll(backupsLineList);
					lineAdapter.notifyDataSetChanged();
				} else {
					for (EcLineEntity ecLineEntity : backupsLineList) {
						// 拿到线路名称
						String name = ecLineEntity.getName();
						// 查询线路名称里是否有输入框中输入的线路名称
						if (name.contains(searchLine)) {
							tempLineList.add(ecLineEntity);
						}
					}
					lineList.clear();
					lineList.addAll(tempLineList);
					lineAdapter.notifyDataSetChanged();
				}
				break;
			}
			case R.id.btn_NoSelect_line: { //添加所有线路
				selectedDatas.clear();
				selectedDatas.addAll(backupsLineList);
				lineAdapter.setSelectedDatas(selectedDatas);
				selectedLineAdapter.notifyDataSetChanged();
				//adapter只能new一次
//				selectedLineAdapter = null;
//				selectedLineAdapter = new SelectedLineListAdapter(mActivity, selectedDatas);
//				viewHolder.getLvLineList().setAdapter(selectedLineAdapter);
				break;
			}
			case R.id.btn_selected_line: { //移除所有线路
				if (selectedDatas != null && selectedDatas.size() >0) {
					selectedDatas.clear();
					selectedLineAdapter.notifyDataSetChanged();
				}
				break;
			}
			case R.id.tv_head_item_ok: { // 确定
				lineAdapter.notifyDataSetChanged();
//				selectCollectObjBll.updateAll(lineList);
				mActivity.setResult(Activity.RESULT_OK);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_return: {
				mActivity.finish();
			}
			default:
				break;
			}
		}
	};
}
