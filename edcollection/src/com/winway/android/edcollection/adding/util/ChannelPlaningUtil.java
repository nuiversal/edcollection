package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.text.TextUtils;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.adding.entity.channelplaning.ChannelPlaningEntity;
import com.winway.android.edcollection.adding.entity.channelplaning.JMEntity;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.project.entity.EcChannelSectionEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.util.GsonUtils;

public class ChannelPlaningUtil {
	static final HashMap<String, ChannelPlaningEntity> map = new HashMap<>();
	static final HashMap<String, EcChannelSectionEntity> selectionmap = new HashMap<>();

	/**
	 * 从内存中获取截面
	 * 
	 * @param channelId
	 * @param baseDBUtil
	 * @return
	 */
	public static ChannelPlaningEntity getChannelPlanning(String channelId, String nodeId, BaseDBUtil baseDBUtil) {
		if (map.containsKey(channelId)) {
			return map.get(channelId);
		}
		return getChannelPlanningFromDatabase(channelId, nodeId, baseDBUtil);
	}

	/**
	 * 从数据中获取截面
	 * 
	 * @param channelId
	 * @param baseDBUtil
	 * @return
	 */
	public static ChannelPlaningEntity getChannelPlanningFromDatabase(String channelId, String nodeId,
			BaseDBUtil baseDBUtil) {
		if (null == baseDBUtil || TextUtils.isEmpty(nodeId) || TextUtils.isEmpty(channelId)) {
			return null;
		}
		ChannelPlaningEntity channelPlaningEntity = new ChannelPlaningEntity();
		channelPlaningEntity.setChannelId(channelId);

		try {
			// 如果离线附件表不存在，创建离线附件表
			baseDBUtil.createTableIfNotExist(OfflineAttach.class);
			// 查找通道node点处的所有截面
			EcChannelSectionEntity queryEntity = new EcChannelSectionEntity();
			queryEntity.setEcChannelId(channelId);
			queryEntity.setEcNodeId(nodeId);
			ArrayList<JMEntity> jmList = new ArrayList<>();
			List<EcChannelSectionEntity> list = baseDBUtil.excuteQuery(EcChannelSectionEntity.class, queryEntity);
			if (list == null || list.isEmpty()) {
				return null;
			}
			// 解释截面
			for (EcChannelSectionEntity ecChannelSectionEntity : list) {
				selectionmap.put(ecChannelSectionEntity.getEcChannelSectionId(), ecChannelSectionEntity);
				JMEntity jmEntity = GsonUtils.build().fromJson(ecChannelSectionEntity.getSectionDesc(), JMEntity.class);
				// 查找相片
				OfflineAttach queryAttach = new OfflineAttach();
				queryAttach.setWorkNo(ecChannelSectionEntity.getEcChannelSectionId());
				List<OfflineAttach> attachList = baseDBUtil.excuteQuery(OfflineAttach.class, queryAttach);
				if (null != attachList && !attachList.isEmpty()) {
					for (OfflineAttach offlineAttach : attachList) {
						if (offlineAttach.getFileName().contains("_JMBG")) {
							// 截面背景图
							jmEntity.setBackgroundImage(offlineAttach.getFilePath());
						}
					}
				}
				jmList.add(jmEntity);
			}

			channelPlaningEntity.setJmList(jmList);

			// 找全景图
			/*
			 * EcVRRefEntity queryVRRefEntity = new EcVRRefEntity();
			 * queryVRRefEntity.setDeviceType(ResourceEnum.TD.getValue());
			 * queryVRRefEntity.setDevObjId(channelId);
			 * queryVRRefEntity.setOid(nodeId); List<EcVRRefEntity> vrRefList =
			 * baseDBUtil.excuteQuery(queryVRRefEntity); if (null != vrRefList
			 * && !vrRefList.isEmpty()) { for (EcVRRefEntity ecVRRefEntity :
			 * vrRefList) { OfflineAttach queryAttach = new OfflineAttach();
			 * queryAttach.setWorkNo(ecVRRefEntity.getId()); } }
			 */
			return channelPlaningEntity;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从db中获取截面信息
	 * 
	 * @param channelId
	 * @param resType
	 * @param objId
	 * @param baseDBUtil
	 * @return
	 */
	public static ChannelPlaningEntity getChannelSectionFromDb(String channelId, String resType, String objId,
			BaseDBUtil baseDBUtil) {
		if (TextUtils.isEmpty(channelId) || TextUtils.isEmpty(resType) || TextUtils.isEmpty(objId)
				|| baseDBUtil == null) {
			return null;
		}
		ChannelPlaningEntity channelPlaningEntity = new ChannelPlaningEntity();
		channelPlaningEntity.setChannelId(channelId);
		try {
			// 查找通道node点处的所有截面
			EcChannelSectionEntity queryEntity = new EcChannelSectionEntity();
			queryEntity.setEcChannelId(channelId);
			queryEntity.setResType(resType);
			queryEntity.setObjid(objId);

			ArrayList<JMEntity> jmList = new ArrayList<>();
			List<EcChannelSectionEntity> list = baseDBUtil.excuteQuery(EcChannelSectionEntity.class, queryEntity);
			if (list == null || list.isEmpty()) {
				return null;
			}
			// 解释截面
			for (EcChannelSectionEntity ecChannelSectionEntity : list) {
				selectionmap.put(ecChannelSectionEntity.getEcChannelSectionId(), ecChannelSectionEntity);
				JMEntity jmEntity = GsonUtils.build().fromJson(ecChannelSectionEntity.getSectionDesc(), JMEntity.class);
				// 查找相片
				OfflineAttach queryAttach = new OfflineAttach();
				queryAttach.setWorkNo(ecChannelSectionEntity.getEcChannelSectionId());
				List<OfflineAttach> attachList = baseDBUtil.excuteQuery(OfflineAttach.class, queryAttach);
				if (null != attachList && !attachList.isEmpty()) {
					for (OfflineAttach offlineAttach : attachList) {
						if (offlineAttach.getFileName().contains("_JMBG")) {
							// 截面背景图
							jmEntity.setBackgroundImage(offlineAttach.getFilePath());
						}
					}
				}
				jmList.add(jmEntity);
			}
			channelPlaningEntity.setJmList(jmList);
			return channelPlaningEntity;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 保存截面到内存中
	 * 
	 * @param entity
	 */
	public static void putChannelPlaningEntity(ChannelPlaningEntity entity) {
		map.put(entity.getChannelId(), entity);
	}

	/**
	 * 移除通道
	 * 
	 * @param channelId
	 */
	public static void removeChannelPlanningEntity(String channelId) {
		map.remove(channelId);

	}

	/**
	 * 清除内存中的所有通道
	 */
	public static void clean() {
		map.clear();
	}

	/**
	 * 
	 * @param sectionid
	 * @return
	 */
	public static EcChannelSectionEntity getEcChannelSection(String sectionid) {
		return selectionmap.get(sectionid);
	}

}
