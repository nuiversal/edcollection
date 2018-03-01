package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;

/** 
* @author lyh  
* @version 创建时间：2017年1月14日 下午12:10:01 
* 
*/
public class OfflinePictureUtil {

	/**
	 * 获得路径点图片
	 * @param nodeid
	 * @param center
	 * @return
	 */
	public static HashMap<String, List<String>> getEcLineNodeOfflinePicture(
			String nodeid, OfflineAttachCenter center) {
		List<OfflineAttach> attachList = center.getNode(nodeid);
		if (attachList == null) {
			return null;
		}
		List<String> positionList = new ArrayList<String>();
		List<String> backgroundList = new ArrayList<String>();
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		// 遍历照片集合
		for (OfflineAttach offlineAttach : attachList) {
			// 拿到文件路径
			String filePath = offlineAttach.getFilePath();
			// 截取文件路径中图片类型
			String string = filePath.split("/")[9];
			// 如果是位置图则将位置图的文件路径保存到位置集合并放进map集合
			if (string.equals("position")) {
				positionList.add(filePath);
				map.put("position_image", positionList);
				// 如果是背景图则将背景图的文件路径保存到背景集合并放进map集合
			} else if (string.equals("background")) {
				backgroundList.add(filePath);
				map.put("background_image", backgroundList);
			}
		}
		return map;
	}
	
	/**
	 * @author xs
	 * 获得路径点图片
	 * @param nodeid
	 * @param center
	 * @return
	 */
	public static HashMap<String, List<String>> getEcLineNodeOfflinePic(String nodeId,OfflineAttachCenter center){
		List<OfflineAttach> attachList = center.getNode(nodeId);
		if (attachList == null) {
			return null;
			
		}
		HashMap<String, List<String>> map = new HashMap<>();
		List<String> list = new ArrayList<>();
		for (OfflineAttach offlineAttach : attachList) {
			String filePath = offlineAttach.getFilePath();
			list.add(filePath);
		}
		map.put("position_image", list);
		return map;
	}

	/**
	 * 获取标签附件图片
	 * @param labelid
	 * @param center
	 * @return
	 */
	public static HashMap<String, List<String>> getLabelOfflinePicture(
			String labelid, OfflineAttachCenter center) {
		List<String> positionList = new ArrayList<String>();
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		EcLineLabelEntityVo label = center.getLabel(labelid);
		if (null == label) {
			return null;
		}
		List<OfflineAttach> attr = label.getAttr();
		if (null == attr || attr.isEmpty()) {
			return null;
		}
		for (OfflineAttach offlineAttach : attr) {
			String filePath = offlineAttach.getFilePath();
			positionList.add(filePath);
		}
		if (positionList.isEmpty()) {
			return null;
		}
		map.put("position_image", positionList);
		return map;
	}

	/**
	 * 获取工井图片
	 * @param workWellId
	 * @param center
	 * @return
	 */
	public static HashMap<String, List<String>> getWorkWellOfflinePic(
			String ecWorkWellId, OfflineAttachCenter center) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		EcWorkWellEntityVo workWell = center.getWorkWell(ecWorkWellId);
		if (workWell == null) {
			return null;
		}
		List<OfflineAttach> comUploadEntityList = workWell
				.getComUploadEntityList();
		if (comUploadEntityList == null || comUploadEntityList.isEmpty()) {
			return null;
		}
		for (OfflineAttach offlineAttach : comUploadEntityList) {
			String filePath = offlineAttach.getFilePath();
			list.add(filePath);
		}
		if (list.isEmpty()) {
			return null;
		}
		map.put("position_image", list);
		return map;
	}
	
	/**
	 * 获取配电房图片
	 * @param ecDistributionRoomId
	 * @param center
	 * @return
	 */
	public static HashMap<String, List<String>> getRoomOfflinePic(
			String ecDistributionRoomId, OfflineAttachCenter center) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		EcDistributionRoomEntityVo room = center.getRoom(ecDistributionRoomId);
		if (room == null) {
			return null;
		}
		List<OfflineAttach> comUploadEntityList = room
				.getComUploadEntityList();
		if (comUploadEntityList == null || comUploadEntityList.isEmpty()) {
			return null;
		}
		for (OfflineAttach offlineAttach : comUploadEntityList) {
			String filePath = offlineAttach.getFilePath();
			list.add(filePath);
		}
		if (list.isEmpty()) {
			return null;
		}
		map.put("position_image", list);
		return map;
	}
	
	/**
	 * 获取变电站图片
	 * @param ecSubstationId
	 * @param center
	 * @return
	 */
	public static HashMap<String, List<String>> getStationOfflinePic(
			String ecSubstationId, OfflineAttachCenter center) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		EcSubstationEntityVo station = center.getStation(ecSubstationId);
		if (station == null) {
			return null;
		}
		List<OfflineAttach> comUploadEntityList = station
				.getComUploadEntityList();
		if (comUploadEntityList == null || comUploadEntityList.isEmpty()) {
			return null;
		}
		for (OfflineAttach offlineAttach : comUploadEntityList) {
			String filePath = offlineAttach.getFilePath();
			list.add(filePath);
		}
		if (list.isEmpty()) {
			return null;
		}
		map.put("position_image", list);
		return map;
	}
	
	/**
	 * 获取变压器图片
	 * @param ecTransformerId
	 * @param center
	 * @return
	 */
	public static HashMap<String, List<String>> getTransOfflinePic(
			String ecTransformerId, OfflineAttachCenter center) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		EcTransformerEntityVo trans = center.getTrans(ecTransformerId);
		if (trans == null) {
			return null;
		}
		List<OfflineAttach> comUploadEntityList = trans
				.getComUploadEntityList();
		if (comUploadEntityList == null || comUploadEntityList.isEmpty()) {
			return null;
		}
		for (OfflineAttach offlineAttach : comUploadEntityList) {
			String filePath = offlineAttach.getFilePath();
			list.add(filePath);
		}
		if (list.isEmpty()) {
			return null;
		}
		map.put("position_image", list);
		return map;
	}
	
	/**
	 * 获取杆塔图片
	 * @param ecTowerId
	 * @param center
	 * @return
	 */
	public static HashMap<String, List<String>> getTowerOfflinePic(
			String ecTowerId, OfflineAttachCenter center) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		EcTowerEntityVo tower = center.getTower(ecTowerId);
		if (tower == null) {
			return null;
		}
		List<OfflineAttach> comUploadEntityList = tower
				.getComUploadEntityList();
		if (comUploadEntityList == null || comUploadEntityList.isEmpty()) {
			return null;
		}
		for (OfflineAttach offlineAttach : comUploadEntityList) {
			String filePath = offlineAttach.getFilePath();
			list.add(filePath);
		}
		if (list.isEmpty()) {
			return null;
		}
		map.put("position_image", list);
		return map;
	}
	
	/**
	 * 拿到中间接头图片
	 * @param ecMiddleJointId
	 * @param center
	 * @return
	 */
	public static HashMap<String, List<String>> getMiddleOfflinePic(
			String ecMiddleJointId, OfflineAttachCenter center) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		 EcMiddleJointEntityVo middle = center.getMiddle(ecMiddleJointId);
		if (middle == null) {
			return null;
		}
		List<OfflineAttach> comUploadEntityList = middle.getAttr();
		if (comUploadEntityList == null || comUploadEntityList.isEmpty()) {
			return null;
		}
		for (OfflineAttach offlineAttach : comUploadEntityList) {
			String filePath = offlineAttach.getFilePath();
			list.add(filePath);
		}
		if (list.isEmpty()) {
			return null;
		}
		map.put("position_image", list);
		return map;
	}
	
	/**
	 * 获取分接箱图片
	 * @param ecDistBoxId
	 * @param center
	 * @return
	 */
	public static HashMap<String, List<String>> getBoxOfflinePic(
			String ecDistBoxId, OfflineAttachCenter center) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		EcDistBoxEntityVo box = center.getBox(ecDistBoxId);
		if (box == null) {
			return null;
		}
		List<OfflineAttach> comUploadEntityList = box.getAttr();
		if (comUploadEntityList == null || comUploadEntityList.isEmpty()) {
			return null;
		}
		for (OfflineAttach offlineAttach : comUploadEntityList) {
			String filePath = offlineAttach.getFilePath();
			list.add(filePath);
		}
		if (list.isEmpty()) {
			return null;
		}
		map.put("position_image", list);
		return map;
	}
}
