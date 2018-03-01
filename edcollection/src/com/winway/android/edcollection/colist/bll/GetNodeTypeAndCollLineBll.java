package com.winway.android.edcollection.colist.bll;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.colist.entity.CollectedObject;
import com.winway.android.edcollection.colist.entity.PathPointTypeData;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;

/**
 * @author lyh
 * @version 创建时间：2016年12月23日 上午10:35:51
 * 
 */
public class GetNodeTypeAndCollLineBll extends BaseBll<Object> {

	public GetNodeTypeAndCollLineBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取路径点类型
	 * 
	 * @param mActivity
	 * @return
	 */
	public ArrayList<String> getWayPointType(Activity mActivity) {
		ArrayList<String> type = new ArrayList<String>();
		return type;
	}

	/**
	 * 将已采集路径点类型分类并获取
	 * 
	 * @param newData
	 * @param data
	 * @return
	 */
	public static void getPointTypes(List<CollectedObject> newData, String lineType,List<EcLineLabelEntity> allLineLabel) {
		PathPointTypeData.reSet();
		if ("全部".equals(lineType)) {
			PathPointTypeData.setLineType(lineType);
			PathPointTypeData.setNewData(newData);
			for (CollectedObject collectedObject : newData) {
				if (collectedObject.getObjType() == null) {
					continue;
				}
				if (collectedObject.getObjType().equals("标识球")) {
					List<String> MarkingBallList = new ArrayList<>();
					if (PathPointTypeData.getMarkingBallList() == null) {
						PathPointTypeData.setMarkingBallList(MarkingBallList);
					}
					PathPointTypeData.getMarkingBallList().add(collectedObject.getObjType());
				}
				if (collectedObject.getObjType().equals("标识钉")) {
					List<String> MarkingNailList = new ArrayList<>();
					if (PathPointTypeData.getMarkingNailList() == null) {
						PathPointTypeData.setMarkingNailList(MarkingNailList);
					}
					PathPointTypeData.getMarkingNailList().add(collectedObject.getObjType());
				}
				if (collectedObject.getObjType().equals("虚拟点")) {
					List<String> VirtualPointList = new ArrayList<>();
					if (PathPointTypeData.getVirtualPointList() == null) {
						PathPointTypeData.setVirtualPointList(VirtualPointList);
					}
					PathPointTypeData.getVirtualPointList().add(collectedObject.getObjType());
				}
				if (collectedObject.getObjType().equals("杆塔")) {
					List<String> towerList = new ArrayList<>();
					if (PathPointTypeData.getTowerList() == null) {
						PathPointTypeData.setTowerList(towerList);
					}
					PathPointTypeData.getTowerList().add(collectedObject.getObjType());
				}
				if (collectedObject.getObjType().equals("安键环")) {
					List<String> ajhList = new ArrayList<>();
					if (PathPointTypeData.getAjhList() == null) {
						PathPointTypeData.setAjhList(ajhList);
					}
					PathPointTypeData.getAjhList().add(collectedObject.getObjType());
				}
			}
			if(allLineLabel!=null && allLineLabel.size()>0){
				PathPointTypeData.setDzbqList(allLineLabel);
			}
		} else if (lineType == null) {
			PathPointTypeData.setLineType("全部");
			PathPointTypeData.setNewData(newData);
			for (CollectedObject collectedObject : newData) {
				if (collectedObject.getObjType() == null) {
					continue;
				}
				if (collectedObject.getObjType().equals("标识球")) {
					List<String> MarkingBallList = new ArrayList<>();
					if (PathPointTypeData.getMarkingBallList() == null) {
						PathPointTypeData.setMarkingBallList(MarkingBallList);
					}
					PathPointTypeData.getMarkingBallList().add(collectedObject.getObjType());
				}
				if (collectedObject.getObjType().equals("标识钉")) {
					List<String> MarkingNailList = new ArrayList<>();
					if (PathPointTypeData.getMarkingNailList() == null) {
						PathPointTypeData.setMarkingNailList(MarkingNailList);
					}
					PathPointTypeData.getMarkingNailList().add(collectedObject.getObjType());
				}
				if (collectedObject.getObjType().equals("虚拟点")) {
					List<String> VirtualPointList = new ArrayList<>();
					if (PathPointTypeData.getVirtualPointList() == null) {
						PathPointTypeData.setVirtualPointList(VirtualPointList);
					}
					PathPointTypeData.getVirtualPointList().add(collectedObject.getObjType());
				}
				if (collectedObject.getObjType().equals("杆塔")) {
					List<String> towerList = new ArrayList<>();
					if (PathPointTypeData.getTowerList() == null) {
						PathPointTypeData.setTowerList(towerList);
					}
					PathPointTypeData.getTowerList().add(collectedObject.getObjType());
				}
				if (collectedObject.getObjType().equals("安键环")) {
					List<String> ajhList = new ArrayList<>();
					if (PathPointTypeData.getAjhList() == null) {
						PathPointTypeData.setAjhList(ajhList);
					}
					PathPointTypeData.getAjhList().add(collectedObject.getObjType());
				}
			}
			if(allLineLabel!=null && allLineLabel.size()>0){
				PathPointTypeData.setDzbqList(allLineLabel);
			}
		} else {
			PathPointTypeData.setLineType(lineType);
			PathPointTypeData.setNewData(newData);
		}

	}
}
