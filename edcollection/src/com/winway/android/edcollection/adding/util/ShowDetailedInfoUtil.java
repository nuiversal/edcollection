package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;

import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.project.entity.EcDistBoxEntity;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.ewidgets.tree.core.TreeNode;

/**
 * 详细信息
 * 
 * @author lyh
 * @version 创建时间：2017年1月12日 下午4:03:57
 * 
 */
public class ShowDetailedInfoUtil {
	private static OfflineAttachCenter center;
	private static HashMap<String, List<String>> map = new HashMap<String, List<String>>();
	private static List<String> positionList = new ArrayList<String>();
	private static List<String> backgroundList = new ArrayList<String>();

	public static void show(TreeNode node, Object objData, Activity mActivity, String projectBDUrl, String lineid) {

		center = new OfflineAttachCenter(mActivity, projectBDUrl);
		if (null == objData) {
			return;
		}

		/*// 拿到节点实体的数据，并将它们放进集合里面去
		ArrayList<Object[]> list = TableDataUtil.getByEcNodeEntity((EcNodeEntity) objData, lineid);
		// 拿到标识器类型
		Integer markerType = ((EcNodeEntity) objData).getMarkerType();
		// 拿到oid
		String oid = ((EcNodeEntity) objData).getOid();
		// 根据oid去离线附件表拿到照片集合
		List<OfflineAttach> attachList = center.getNode(oid);
		if (attachList == null) {
			return;
		}
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
		// 根据标识器类型判断显示详细信息
		if (markerType == 1) {
			TableShowUtil.showTable(list, mActivity, "标识球", map);
		} else if (markerType == 2) {
			TableShowUtil.showTable(list, mActivity, "标识钉", map);
		} else if (markerType == 0){
			TableShowUtil.showTable(list, mActivity, "路径点", map);
		} else {
			TableShowUtil.showTable(list, mActivity, "杆塔", map);
		}
*/
		TableShowUtil.showNodeDetailed((EcNodeEntity) objData, lineid, center, mActivity);
	}

	public static void showLabel(TreeNode node, Object data, Activity mActivity, String projectBDUrl) {
		/*// 拿到标签实体的数据，并将它们放进集合里面去
		ArrayList<Object[]> list = TableDataUtil.getByEcLineLabelEntity((EcLineLabelEntity) data, null);
		// 拿到标签的id
		String ecLineLabelId = ((EcLineLabelEntity) data).getObjId();
		// 根据id去离线附件中心拿到标签实体
		EcLineLabelEntityVo label = center.getLabel(ecLineLabelId);
		if (label == null) {
			return;
		}
		// 获取图片集合
		List<OfflineAttach> attr = label.getAttr();
		if (attr != null && attr.size() > 0) {
			// 遍历图片集合
			for (OfflineAttach offlineAttach : attr) {
				// 获取文件路径
				String filePath = offlineAttach.getFilePath();
				// 将文件路径放进集合
				positionList.add(filePath);
			}
			map.put("position_image", positionList);
			TableShowUtil.showTable(list, mActivity, "标签", map);
		} else {
			TableShowUtil.showTable(list, mActivity, "标签", null);
		}*/
		TableShowUtil.showLabelDetailed((EcLineLabelEntity) data, center, mActivity);
	}

	/**
	 * 显示设备详情
	 * 
	 * @param businessObj
	 * @param id
	 * @param mActivity
	 */
	public static void showDevice(Object businessObj, String id, Activity mActivity, OfflineAttachCenter center,
			DataCenter dataCenter) {
		// 节点
		if (businessObj instanceof EcNodeEntity) {
			if (id.indexOf("#") != -1) {
				String lineid = id.split("#").length > 1 ? id.split("#")[1] : null;
				TableShowUtil.showNodeDetailed((EcNodeEntity) businessObj, lineid, center, mActivity);
			} else {
				TableShowUtil.showNodeDetailed((EcNodeEntity) businessObj, null, center, mActivity);
			}
		} else if (businessObj instanceof DeviceEntity) {
			Object data = ((DeviceEntity<?>) businessObj).getData();
			if (data == null) {
				return;
			}
			getDevice(data, id, mActivity, center, null);
		} else {
			getDevice(businessObj, id, mActivity, center, dataCenter);
		}
	}

	private static void getDevice(Object data, String id, Activity mActivity, OfflineAttachCenter center,
			DataCenter dataCenter) {
		if (data instanceof EcSubstationEntity) {
			// 变电站
			TableShowUtil.showStationDetailed((EcSubstationEntity) data, center, mActivity);
		} else if (data instanceof EcLineLabelEntity) {
			if (!id.contains("#LINE_NAME#")) {
				List<EcLineEntity> lineEntities = dataCenter.getLineListByOrgid(GlobalEntry.loginResult.getOrgid(), null,
						null);
				for (int i = 0; i < lineEntities.size(); i++) {
					EcLineEntity ecLineEntity = lineEntities.get(i);
					if (((EcLineLabelEntity) data).getLineNo().equals(ecLineEntity.getEcLineId())) {
						TableShowUtil.showLabelDetailed((EcLineLabelEntity) data, center, mActivity);
					}
				}
			} else {
				// 标签
				TableShowUtil.showLabelDetailed((EcLineLabelEntity) data, center, mActivity);
			}
		} else if (data instanceof EcMiddleJointEntity) {
			// 电缆中间接头
			TableShowUtil.showMiddleDetailed((EcMiddleJointEntity) data, center, mActivity);
		} else if (data instanceof EcWorkWellEntity) {
			// 工井
			TableShowUtil.showWellDetailed((EcWorkWellEntity) data, center, mActivity);
		} else if (data instanceof EcDistBoxEntity) {
			// 分接箱
			TableShowUtil.showBoxDetailed((EcDistBoxEntity) data, center, mActivity);
		} else if (data instanceof EcTowerEntity) {
			// 杆塔
			TableShowUtil.showTowerDetailed((EcTowerEntity) data, center, mActivity);
		} else if (data instanceof EcDistributionRoomEntity) {
			// 配电房
			TableShowUtil.showRoomDetailed((EcDistributionRoomEntity) data, center, mActivity);
		} else if (data instanceof EcTransformerEntity) {
			// 变压器
			TableShowUtil.showTransDetailed((EcTransformerEntity) data, center, mActivity);
		} else if (data instanceof EcHwgEntity) {
			// 环网柜
			TableShowUtil.showHwgDetailed((EcHwgEntity) data, mActivity, TableDataUtil.treeDataCenter);
		} else if (data instanceof EcXsbdzEntity) {
			// 箱式变电站
			TableShowUtil.showXsbdzDetailed((EcXsbdzEntity) data, mActivity, TableDataUtil.treeDataCenter);
		} else if (data instanceof EcDlfzxEntity) {
			// 电缆分支箱
			TableShowUtil.showDlfzxDetailed((EcDlfzxEntity) data, mActivity, TableDataUtil.treeDataCenter);
		} else if (data instanceof EcDydlfzxEntity) {
			// 低压电缆分支箱
			TableShowUtil.showDydlfzxDetailed((EcDydlfzxEntity) data, mActivity, TableDataUtil.treeDataCenter);
		} else if (data instanceof EcDypdxEntity) {
			// 低压配电箱
			TableShowUtil.showDypdxDetailed((EcDypdxEntity) data, mActivity, TableDataUtil.treeDataCenter);
		} else if (data instanceof EcKgzEntity) {
			// 低压配电箱
			TableShowUtil.showKgzDetailed((EcKgzEntity) data, mActivity, TableDataUtil.treeDataCenter);
		} else if (data instanceof EcWorkWellCoverEntity) {
			// 井盖
			TableShowUtil.showCover((EcWorkWellCoverEntity) data, TableDataUtil.treeDataCenter, mActivity);
		}
	}
}
