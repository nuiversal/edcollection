package com.winway.android.edcollection.datacenter.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.winway.android.edcollection.base.entity.IndexEcNodeEntity;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.LineDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.NodeDevicesEntity;
import com.winway.android.edcollection.datacenter.service.NodeInfoCenter;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;

import android.annotation.SuppressLint;

/**
 * 数据处理工具类
 * @author mr-lao
 *
 */
@SuppressLint("UseSparseArrays")
public class DataDealUtil {
	/**
	 * 对通过指定线路的所有通道按照线路节点顺序进行排序
	 * @param data
	 * @param nodeInfoCenter
	 * @param lineId
	 */
	public static void sortLineChannel(List<ChannelDataEntity> data, NodeInfoCenter nodeInfoCenter, String lineId) {
		HashMap<Integer, ChannelDataEntity> map = new HashMap<Integer, ChannelDataEntity>();
		int indexs[] = new int[data.size()];
		int end = 0;
		for (int i = 0; i < data.size(); i++) {
			ChannelDataEntity channelData = data.get(i);
			String oid = channelData.getStartNode() == null ? null : channelData.getStartNode().getOid();
			EcLineNodesEntity lineNode = nodeInfoCenter.getLineNode(oid, lineId);
			if (null == lineNode) {
				// 通道起点不在此线路中，将此通道排列在最后
				indexs[i] = indexs.length - end - 1;
				end++;
			} else {
				indexs[i] = lineNode.getNIndex();
			}
			if (map.containsKey(indexs[i])) {
				indexs[i] = indexs[i - 1] + 1;
			}
			map.put(indexs[i], channelData);
		}
		// 从小到大排序
		Arrays.sort(indexs);
		data.clear();
		for (int i : indexs) {
			data.add(map.get(i));
		}
	}

	/**
	 * 对通道的路径点进行排序
	 * @param nodes
	 */
	public static void sortChannelNodes(List<IndexEcNodeEntity<EcChannelNodesEntity>> nodes) {
		HashMap<Integer, IndexEcNodeEntity<EcChannelNodesEntity>> map = new HashMap<Integer, IndexEcNodeEntity<EcChannelNodesEntity>>();
		int indexs[] = new int[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			IndexEcNodeEntity<EcChannelNodesEntity> entity = nodes.get(i);
			indexs[i] = entity.getIndex().getNIndex();
			map.put(indexs[i], entity);
		}
		// 从小到大排序
		Arrays.sort(indexs);
		nodes.clear();
		for (int i : indexs) {
			nodes.add(map.get(i));
		}
	}

	/**
	 * 抽取第一个路径点设备【设备对象，设备主键ID，设备名称】,如【DeviceEntity<T>,T.id,T.name】
	 * @param enity
	 * @return
	 */
	public static Object[] pickUpFirstNodeDevice(NodeDevicesEntity enity) {
		if (null == enity) {
			return null;
		}
		Object[] rest = null;
		if (enity.getDistribution_room() != null && !enity.getDistribution_room().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getDistribution_room().get(0);
			rest[1] = enity.getDistribution_room().get(0).getData().getObjId();
			rest[2] = enity.getDistribution_room().get(0).getData().getSbmc();
		} else if (enity.getDypdx() != null && !enity.getDypdx().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getDypdx().get(0);
			rest[1] = enity.getDypdx().get(0).getData().getObjId();
			rest[2] = enity.getDypdx().get(0).getData().getSbmc();
		} else if (enity.getKgz() != null && !enity.getKgz().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getKgz().get(0);
			rest[1] = enity.getKgz().get(0).getData().getObjId();
			rest[2] = enity.getKgz().get(0).getData().getSbmc();
		} else if (enity.getStation() != null && !enity.getStation().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getStation().get(0);
			rest[1] = enity.getStation().get(0).getData().getEcSubstationId();
			rest[2] = enity.getStation().get(0).getData().getName();
		} else if (enity.getTower() != null && !enity.getTower().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getTower().get(0);
			rest[1] = enity.getTower().get(0).getData().getObjId();
			rest[2] = enity.getTower().get(0).getData().getTowerNo();
		} else if (enity.getTranformer() != null && !enity.getTranformer().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getTranformer().get(0);
			rest[1] = enity.getTranformer().get(0).getData().getObjId();
			rest[2] = enity.getTranformer().get(0).getData().getSbmc();
		} else if (enity.getWell() != null && !enity.getWell().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getWell().get(0);
			rest[1] = enity.getWell().get(0).getData().getObjId();
			rest[2] = enity.getWell().get(0).getData().getSbmc();
		} else if (enity.getXsbdz() != null && !enity.getXsbdz().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getXsbdz().get(0);
			rest[1] = enity.getXsbdz().get(0).getData().getObjId();
			rest[2] = enity.getXsbdz().get(0).getData().getSbmc();
		}
		if (null != rest) {
			for (int i = 0; i < rest.length; i++) {
				if (null == rest[i]) {
					rest[i] = "";
				}
			}
		}
		return rest;
	}

	/**
	 * 抽取第一个线路附属设备【设备对象，设备主键ID，设备名称】,如【DeviceEntity<T>,T.id,T.name】
	 * 抽取第一个，只适合环网柜、电缆分支箱、低压电缆分支箱
	 * @param enity
	 * @return
	 */
	public static Object[] pickUpFirstLineDevice(LineDevicesEntity enity) {
		if (null == enity) {
			return null;
		}
		Object[] rest = null;
		if (null != enity.getHwgEntityVoList() && !enity.getHwgEntityVoList().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getHwgEntityVoList().get(0);
			rest[1] = enity.getHwgEntityVoList().get(0).getData().getObjId();
			rest[2] = enity.getHwgEntityVoList().get(0).getData().getSbmc();
		}
		if (null != enity.getDlfzxEntityVoList() && !enity.getDlfzxEntityVoList().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getDlfzxEntityVoList().get(0);
			rest[1] = enity.getDlfzxEntityVoList().get(0).getData().getObjId();
			rest[2] = enity.getDlfzxEntityVoList().get(0).getData().getSbmc();
		}
		if (null != enity.getDydlfzxEntityVoList() && !enity.getDydlfzxEntityVoList().isEmpty()) {
			rest = new Object[3];
			rest[0] = enity.getDydlfzxEntityVoList().get(0);
			rest[0] = enity.getDydlfzxEntityVoList().get(0).getData().getObjId();
			rest[0] = enity.getDydlfzxEntityVoList().get(0).getData().getSbmc();
		}
		if (null != rest) {
			for (int i = 0; i < rest.length; i++) {
				if (null == rest[i]) {
					rest[i] = "";
				}
			}
		}
		return rest;
	}
}
