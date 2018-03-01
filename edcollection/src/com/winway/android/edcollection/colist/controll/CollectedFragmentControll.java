package com.winway.android.edcollection.colist.controll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.bll.SelectCollectObjBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.view.BaseFragmentControll;
import com.winway.android.edcollection.colist.adapter.CollLineAdapter;
import com.winway.android.edcollection.colist.adapter.CollTimeAdapter;
import com.winway.android.edcollection.colist.adapter.CollTypeAdapter;
import com.winway.android.edcollection.colist.adapter.CollViewPageAdapter;
import com.winway.android.edcollection.colist.bll.GetNodeTypeAndCollLineBll;
import com.winway.android.edcollection.colist.entity.CollectedObject;
import com.winway.android.edcollection.colist.entity.PathPointTypeData;
import com.winway.android.edcollection.colist.util.CollectedPopUtil;
import com.winway.android.edcollection.colist.util.CountNumberDialog;
import com.winway.android.edcollection.colist.viewholder.CollectedFragmentViewHolder;
import com.winway.android.edcollection.main.controll.MainControll;
import com.winway.android.edcollection.project.bll.NodeBll;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ToastUtil;

/***
 * 已采列表
 * 
 * @author Administrator
 *
 */
public class CollectedFragmentControll extends BaseFragmentControll<CollectedFragmentViewHolder> {

	private SelectCollectObjBll scObjBllbll;
	private NodeBll nodeBll;
	public static CollViewPageAdapter itemAdapter;
	private GetNodeTypeAndCollLineBll collLineBll;
	private PathPointTypeData pathPointTypeData;
	private boolean initFlag = false;

	public static boolean isCheckMapBtn = false;
	private CountNumberDialog countNumberDialog;
	public static List<EcLineLabelEntity> allLineLable;
	private boolean isRefresh = false;
	private boolean isInit = false;

	@Override
	public void init() {
		initDatas();
		initEvent();
		initFlag = true;
	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		countNumberDialog = new CountNumberDialog(mActivity, R.style.dialog_nameing_conventions);
		pathPointTypeData = new PathPointTypeData();
		// collLineBll = new GetNodeTypeAndCollLineBll(mActivity,
		// FileUtil.AppRootPath + "/db/project/" +
		// GlobalEntry.currentProject.getPrjNo() + ".db");
		scObjBllbll = new SelectCollectObjBll(mActivity,
				FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db");
		nodeBll = new NodeBll(mActivity,
				FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db");
		ArrayList<CollectedObject> models = (ArrayList<CollectedObject>) nodeBll.getAll();
		allLineLable = nodeBll.getAllLineLable();
		if (models != null && models.size() > 0) {
			GetNodeTypeAndCollLineBll.getPointTypes(models, "全部", allLineLable);
			/*
			 * if(allLineLable!=null && allLineLable.size()>0){
			 * PathPointTypeData.setDzbqList(allLineLable); }
			 */
		}
		itemAdapter = new CollViewPageAdapter(mActivity, models, nodeBll);
		viewHolder.getLvCollectedViewPage().setAdapter(itemAdapter);
	}

	public void refreshItems() {
		if (initFlag) {
			ArrayList<CollectedObject> models = (ArrayList<CollectedObject>) nodeBll.getAll();
			List<EcLineLabelEntity> allLineLable = nodeBll.getAllLineLable();
			if (models != null && models.size() > 0) {
				GetNodeTypeAndCollLineBll.getPointTypes(models, "全部", allLineLable);
				/*
				 * if(allLineLable!=null && allLineLable.size()>0){
				 * PathPointTypeData.setDzbqList(allLineLable); }
				 */
			}
			itemAdapter.update(models);
			viewHolder.getTv_collected_classify_time().setText("采集时间");
			viewHolder.getTv_collected_classify_line().setText("采集线路");
			viewHolder.getTv_collected_classify_pointType().setText("路径点类型");
		}
	}

	private void initEvent() {
		viewHolder.getLlCollectedTime().setOnClickListener(ocl);
		viewHolder.getLlCollectedPointType().setOnClickListener(ocl);
		viewHolder.getLlCollectedLine().setOnClickListener(ocl);
		viewHolder.getTvSum().setOnClickListener(ocl);
	}

	private OnClickListener ocl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_collected_classify_time: { // 采集时间
				String[] items = mActivity.getResources().getStringArray(R.array.collected_classify_time);
				CollTimeAdapter timeAdapter = new CollTimeAdapter(mActivity,
						new ArrayList<String>(Arrays.asList(items)));
				CollectedPopUtil.getIntance(mActivity).setAdapter(timeAdapter);
				CollectedPopUtil.getIntance(mActivity).showNewPopWid(viewHolder.getIncludeCollectedClassify());
			}

				break;
			case R.id.ll_collected_classify_pointType: { // 路径点类型
				// 拿到所有路径点类型
				String[] items = mActivity.getResources().getStringArray(R.array.collected_classify_pointType);
				CollTypeAdapter typeAdapter = new CollTypeAdapter(mActivity,
						new ArrayList<String>(Arrays.asList(items)));
				CollectedPopUtil.getIntance(mActivity).setAdapter(typeAdapter);
				CollectedPopUtil.getIntance(mActivity).showNewPopWid(viewHolder.getIncludeCollectedClassify());
			}

				break;
			case R.id.ll_collected_classify_line: { // 采集线路
				// 拿到到所有项目名称
				ArrayList<EcLineEntity> selectLineName = new ArrayList<EcLineEntity>();
				// 加条数据为查询所有线路
				EcLineEntity entity = new EcLineEntity();
				entity.setName("全部");
				entity.setLineNo(String.valueOf(-1));
				selectLineName.add(entity);
				selectLineName
						.addAll(scObjBllbll.getLineListByPrjId(GlobalEntry.currentProject.getEmProjectId(), mActivity));
				if (selectLineName.size() > 0) {
					CollLineAdapter lineAdapter = new CollLineAdapter(mActivity, selectLineName);
					CollectedPopUtil.getIntance(mActivity).setAdapter(lineAdapter);
					CollectedPopUtil.getIntance(mActivity).showNewPopWid(viewHolder.getIncludeCollectedClassify());
				}
			}
				break;
			case R.id.tv_sum: {// 统计
				if (PathPointTypeData.getLineType() == null && PathPointTypeData.getNewData() == null) {
					ToastUtil.show(mActivity, "没有采集数据,请采集", 200);
					return;
				}
				if (PathPointTypeData.getLineType().equals("全部")) {
					countNumberDialog.setType(PathPointTypeData.getLineType());
					if (PathPointTypeData.getNewData() != null && PathPointTypeData.getNewData().size() > 0) {
						if (PathPointTypeData.getDzbqList() != null && PathPointTypeData.getDzbqList().size() > 0) {
							int allNumber = PathPointTypeData.getNewData().size()
									+ PathPointTypeData.getDzbqList().size();
							countNumberDialog.setNumber(allNumber + "");
						} else {
							countNumberDialog.setNumber(PathPointTypeData.getNewData().size() + "");
						}
					}
					if (PathPointTypeData.getMarkingBallList() != null
							&& PathPointTypeData.getMarkingBallList().size() > 0) {
						countNumberDialog.setMbCount(PathPointTypeData.getMarkingBallList().size() + "");
					}
					if (PathPointTypeData.getMarkingNailList() != null
							&& PathPointTypeData.getMarkingNailList().size() > 0) {
						countNumberDialog.setMnCount(PathPointTypeData.getMarkingNailList().size() + "");
					}
					if (PathPointTypeData.getVirtualPointList() != null
							&& PathPointTypeData.getVirtualPointList().size() > 0) {
						countNumberDialog.setVpCount(PathPointTypeData.getVirtualPointList().size() + "");
					}
					if (PathPointTypeData.getTowerList() != null && PathPointTypeData.getTowerList().size() > 0) {
						countNumberDialog.setTowerCount(PathPointTypeData.getTowerList().size() + "");
					}
					if (PathPointTypeData.getAjhList() != null && PathPointTypeData.getAjhList().size() > 0) {
						countNumberDialog.setAjhCount(PathPointTypeData.getAjhList().size() + "");
					}
					if (PathPointTypeData.getDzbqList() != null && PathPointTypeData.getDzbqList().size() > 0) {
						countNumberDialog.setDzbqCount(PathPointTypeData.getDzbqList().size() + "");
					}
					if (isRefresh) {// 是否刷新数据
						countNumberDialog.initData();
						countNumberDialog.showPathpointType();
					}
					countNumberDialog.show();
					countNumberDialog.reset();
					isRefresh = true;

				} else {// 其他路径点类型
					countNumberDialog.setType(PathPointTypeData.getLineType());
					countNumberDialog.setNumber(pathPointTypeData.getNewData().size() + "");
					if (isRefresh) {// 是否刷新数据
						countNumberDialog.initData();// 如果数据有改变，刷新数据
					}
					countNumberDialog.show();
					countNumberDialog.hidePathpointType();
					countNumberDialog.reset();
					isRefresh = true;
				}
				break;
			}
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == MainControll.flag_marker_edit) {
				isCheckMapBtn = true;
			}
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
