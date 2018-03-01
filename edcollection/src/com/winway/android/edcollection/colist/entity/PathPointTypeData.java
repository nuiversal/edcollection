package com.winway.android.edcollection.colist.entity;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
/**
 * 路径点类型数据集
 * @author winway_cmx
 *
 */
public class PathPointTypeData {
	private static String lineType=null;
	private static String countNumber=null;
	private static List<CollectedObject> newData=null;
	//标识球
	private static List<String> MarkingBallList=null;
	//标识钉
	private static List<String> MarkingNailList=null;
	//虚拟点
	private static List<String> VirtualPointList=null;
	//杆塔
	private static List<String> towerList=null;
	//安键环
	private static List<String> ajhList=null;
	//电子标签
	private static List<EcLineLabelEntity> dzbqList=null;
	
	public static List<EcLineLabelEntity> getDzbqList() {
		return dzbqList;
	}
	public static void setDzbqList(List<EcLineLabelEntity> dzbqList) {
		PathPointTypeData.dzbqList = dzbqList;
	}
	public static List<String> getMarkingBallList() {
		return MarkingBallList;
	}
	public static void setMarkingBallList(List<String> markingBallList) {
		MarkingBallList = markingBallList;
	}
	public static List<String> getMarkingNailList() {
		return MarkingNailList;
	}
	public static void setMarkingNailList(List<String> markingNailList) {
		MarkingNailList = markingNailList;
	}
	public static List<String> getVirtualPointList() {
		return VirtualPointList;
	}
	public static void setVirtualPointList(List<String> virtualPointList) {
		VirtualPointList = virtualPointList;
	}
	public static List<String> getTowerList() {
		return towerList;
	}
	public static void setTowerList(List<String> towerList) {
		PathPointTypeData.towerList = towerList;
	}
	public static String getLineType() {
		return lineType;
	}
	public static void setLineType(String lineType) {
		PathPointTypeData.lineType = lineType;
	}
	
	public static String getCountNumber() {
		return countNumber;
	}
	public static void setCountNumber(String countNumber) {
		PathPointTypeData.countNumber = countNumber;
	}
	public static List<CollectedObject> getNewData() {
		return newData;
	}
	public static void setNewData(List<CollectedObject> newData) {
		PathPointTypeData.newData = newData;
	}
	
	public static List<String> getAjhList() {
		return ajhList;
	}
	public static void setAjhList(List<String> ajhList) {
		PathPointTypeData.ajhList = ajhList;
	}
	public static void reSet(){
		lineType=null;
		MarkingBallList=null;
		MarkingNailList=null;
		VirtualPointList=null;
		towerList=null;
		ajhList=null;
		dzbqList=null;
		countNumber=null;
	    newData=null;
	}
	
}
