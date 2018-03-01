package com.winway.android.edcollection.datacenter.api;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.util.ResourceDeviceUtil;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.LineDetailsEntity;
import com.winway.android.edcollection.datacenter.entity.LineDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.NodeAndNodeLineDeviceEntity;
import com.winway.android.edcollection.datacenter.entity.NodeDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.SubStationDetailsEntity;
import com.winway.android.edcollection.datacenter.entity.edp.OrgTreeResult;
import com.winway.android.edcollection.datacenter.service.ChannelCenter;
import com.winway.android.edcollection.datacenter.service.DeviceCenter;
import com.winway.android.edcollection.datacenter.service.LabelCenter;
import com.winway.android.edcollection.datacenter.service.LineInfoCenter;
import com.winway.android.edcollection.datacenter.service.NodeInfoCenter;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.datacenter.service.OrgCenter;
import com.winway.android.edcollection.datacenter.service.SubInfoCenter;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcVRRefEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDypdxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcKgzEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.edcollection.project.vo.EcXsbdzEntityVo;

import android.content.Context;

/**
 * 项目数据中心（此数据中心除了getOrgTree方法可能运行在工作线程中，其他的所有方法都运行在主线程中）
 * 
 * @author mr-lao
 *
 */
public class DataCenterImpl implements DataCenter {
	/**
	 * 数据中心的数据缓存器
	 * 
	 * @author mr-lao
	 *
	 */
	public static class DataCacheCenter {
		HashMap<String, Object> datamap = new HashMap<String, Object>();

		public void put(String key, Object data) {
			datamap.put(key, data);
		}

		@SuppressWarnings("unchecked")
		public <T> T get(String key) {
			return (T) datamap.get(key);
		}

		public void remove(String key) {
			datamap.remove(key);
		}

		public void clear() {
			datamap.clear();
		}

		public Set<String> keySet() {
			return datamap.keySet();
		}
	}

	/** 缓存对象 */
	public static DataCacheCenter dataCacheCenter = new DataCacheCenter();

	/** 是否使用缓存（值为true时，数据中心的每个方法都会优先从缓存器里面取数据给调用者；值为false则每次都去数据库中取值返回给调用者） */
	private boolean useCache = false;

	public boolean isUseCache() {
		return useCache;
	}

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	// 机构信息中心
	private OrgCenter orgCenter;
	// 变电站信息中心
	private SubInfoCenter subInfoCenter;
	// 线路信息中心
	private LineInfoCenter lineInfoCenter;
	// 路径点信息中心
	private NodeInfoCenter nodeInfoCenter;
	// 设备信息中心
	private DeviceCenter deviceCenter;
	// 通道信息中心
	private ChannelCenter channelCenter;
	// 离线附件
	private OfflineAttachCenter offlineAttachCenter;
	// 标签信息中心
	private LabelCenter labelCenter;

	public OrgCenter getOrgCenter() {
		return orgCenter;
	}

	public SubInfoCenter getSubInfoCenter() {
		return subInfoCenter;
	}

	public LineInfoCenter getLineInfoCenter() {
		return lineInfoCenter;
	}

	public NodeInfoCenter getNodeInfoCenter() {
		return nodeInfoCenter;
	}

	public DeviceCenter getDeviceCenter() {
		return deviceCenter;
	}

	public ChannelCenter getChannelCenter() {
		return channelCenter;
	}

	public OfflineAttachCenter getOfflineAttachCenter() {
		return offlineAttachCenter;
	}

	public DataCenterImpl(Context context, String projectDBUrl) {
		initCenter(context, projectDBUrl);
	}

	private void initCenter(Context context, String projectDBUrl) {
		BaseDBUtil dbUtil = new BaseDBUtil(context, new File(projectDBUrl));
		deviceCenter = new DeviceCenter(dbUtil);
		orgCenter = new OrgCenter(context, dbUtil);
		subInfoCenter = new SubInfoCenter(dbUtil);
		offlineAttachCenter = new OfflineAttachCenter(dbUtil);
		labelCenter = new LabelCenter(dbUtil);

		lineInfoCenter = new LineInfoCenter(dbUtil, deviceCenter);
		nodeInfoCenter = new NodeInfoCenter(dbUtil, deviceCenter);
		channelCenter = new ChannelCenter(dbUtil, nodeInfoCenter);

	}

	/**
	 * 从缓存中加载数据
	 * 
	 * @param key
	 * @return
	 */
	<T> T getFromCacheDataCenter(String key) {
		if (useCache) {
			return dataCacheCenter.get(key);
		}
		return null;
	}

	/**
	 * KEY值为："getSubStationsList_" + orgId;
	 */
	@Override
	public List<EcSubstationEntity> getSubStationsList(String orgId, String credit,
			CallBack<List<EcSubstationEntity>> callback) {
		String key = "getSubStationsList_" + orgId;
		List<EcSubstationEntity> subStationsList = getFromCacheDataCenter(key);
		if (null == subStationsList) {
			subStationsList = orgCenter.getSubStationsList(orgId);
			if (subStationsList != null && !subStationsList.isEmpty() && useCache) {
				dataCacheCenter.put(key, subStationsList);
			}
		}
		if (null != callback) {
			callback.call(subStationsList);
		}
		return subStationsList;
	}

	public String getSubName(String stationNo) {
		String subName = subInfoCenter.getSubName(stationNo);
		return subName;
	}

	/**
	 * KEY值为："getSubStationDetails_" + subStationId + "_" + containsLines
	 */
	@Override
	public SubStationDetailsEntity getSubStationDetails(String subStationId, String credit, boolean containsLines,
			CallBack<SubStationDetailsEntity> callback) {
		if (null == subStationId) {
			return null;
		}
		String key = "getSubStationDetails_" + subStationId + "_" + containsLines;
		SubStationDetailsEntity subStationDetails = getFromCacheDataCenter(key);
		if (null == subStationDetails) {
			subStationDetails = subInfoCenter.getSubStationDetails(subStationId, containsLines);
			if (subStationDetails != null && useCache) {
				dataCacheCenter.put(key, subStationDetails);
			}
		}
		if (null != callback) {
			callback.call(subStationDetails);
		}
		return subStationDetails;
	}

	/**
	 * KEY值为："getSubStationLineList_" + subStationId
	 */
	@Override
	public List<EcLineEntity> getSubStationLineList(String subStationId, String credit,
			CallBack<List<EcLineEntity>> callback) {
		if (subStationId == null) {
			return null;
		}
		String key = "getSubStationLineList_" + subStationId;
		List<EcLineEntity> subStationLineList = getFromCacheDataCenter(key);
		if (null == subStationLineList) {
			subStationLineList = subInfoCenter.getSubStationLineList(subStationId);
			if (subStationLineList != null && useCache && !subStationLineList.isEmpty()) {
				dataCacheCenter.put(key, subStationLineList);
			}
		}
		if (null != callback) {
			callback.call(subStationLineList);
		}
		return subStationLineList;
	}

	/**
	 * KEY值为："getLineDetails_" + lineId + "_" + containsNodes
	 * 
	 * @return
	 */
	@Override
	public LineDetailsEntity getLineDetails(String lineId, String credit, boolean containsNodes,
			CallBack<LineDetailsEntity> callback) {
		if (null == lineId) {
			return null;
		}
		String key = "getLineDetails_" + lineId + "_" + containsNodes;
		LineDetailsEntity lineDetails = getFromCacheDataCenter(key);
		if (null == lineDetails) {
			lineDetails = lineInfoCenter.getLineDetails(lineId, containsNodes);
			if (lineDetails != null && useCache) {
				dataCacheCenter.put(key, lineDetails);
			}
		}
		if (null != callback) {
			callback.call(lineDetails);
		}
		return lineDetails;
	}

	/**
	 * KEY值为："getLineNodeList_" + lineId
	 * 
	 * @return
	 */
	@Override
	public List<EcNodeEntity> getLineNodeList(String lineId, String credit, CallBack<List<EcNodeEntity>> callback) {
		if (lineId == null) {
			return null;
		}
		String key = "getLineNodeList_" + lineId;
		List<EcNodeEntity> lineNodeList = getFromCacheDataCenter(key);
		if (null == lineNodeList) {
			lineNodeList = lineInfoCenter.getLineNodeList(lineId);
			if (lineNodeList != null && useCache && !lineNodeList.isEmpty()) {
				dataCacheCenter.put(key, lineNodeList);
			}
		}
		// loadSpatialAnalysisData(lineNodeList);
		if (null != callback) {
			callback.call(lineNodeList);
		}
		return lineNodeList;
	}

	// // 构建空间分析用到的数据
	// private void loadSpatialAnalysisData(List<EcNodeEntity> lineNodeList) {
	// // 构建空间分析用的数据集
	// Gson gson = GsonUtils.build();
	// Type pointGeometryType = new TypeToken<pointGeometry>() {
	// }.getType();
	// for (int i = 0; i < lineNodeList.size(); i++) {
	// EcNodeEntity ecNodeEntity = lineNodeList.get(i);
	// pointGeometry pointGeometry = gson.fromJson(ecNodeEntity.getGeom(),
	// pointGeometryType);
	// HiElement hiElement = new HiElement();
	// hiElement.Crds.add(new HiCoordinate(pointGeometry.getX(),
	// pointGeometry.getY()));
	// hiElement.DataReference = ecNodeEntity;
	// hiElement.Text = ecNodeEntity.getOid();
	// // 添加元素到数据集中
	// SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point,
	// hiElement, ecNodeEntity);
	// }
	// }

	@Override
	public void getOrgTree(String orgId, String logicSysNo, String credit, final CallBack<OrgTreeResult> callback) {
		orgCenter.getOrgTree(orgId, logicSysNo, credit, callback);
	}

	/**
	 * KEY值为："getLineDevicesList_" + lineId
	 * 
	 * @return
	 */
	@Override
	public LineDevicesEntity getLineDevicesList(String lineId, String credit, CallBack<LineDevicesEntity> callback) {
		if (lineId == null) {
			return null;
		}
		String key = "getLineDevicesList_" + lineId;
		LineDevicesEntity lineDevice = getFromCacheDataCenter(key);
		if (null == lineDevice) {
			lineDevice = lineInfoCenter.getLineDevice(lineId);
			if (lineDevice != null && useCache) {
				dataCacheCenter.put(key, lineDevice);
			}
		}
		if (null != callback) {
			callback.call(lineDevice);
		}
		return lineDevice;
	}

	/**
	 * KEY值为："getLineLabelList_" + lineId
	 * 
	 * @return
	 */
	@Override
	public List<DeviceEntity<EcLineLabelEntityVo>> getLineLabelList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcLineLabelEntityVo>>> callback) {
		if (lineId == null) {
			return null;
		}
		String key = "getLineLabelList_" + lineId;
		List<DeviceEntity<EcLineLabelEntityVo>> labelList = getFromCacheDataCenter(key);
		if (null == labelList) {
			LineDevicesEntity lineDevice = lineInfoCenter.getLineDevice(lineId);
			labelList = lineDevice.getLineLabelEntityVoList();
			if (labelList != null && useCache && !labelList.isEmpty()) {
				dataCacheCenter.put(key, labelList);
			}
		}
		if (null != callback) {
			callback.call(labelList);
		}
		return labelList;
	}

	/**
	 * KEY值为："getNodeDetail_" + nodeId
	 * 
	 * @return
	 */
	@Override
	public EcNodeEntity getNodeDetail(String nodeId, String credit, CallBack<EcNodeEntity> callback) {
		if (null == nodeId) {
			return null;
		}
		String key = "getNodeDetail_" + nodeId;
		EcNodeEntity nodeEntity = getFromCacheDataCenter(key);
		if (null == nodeEntity) {
			nodeEntity = nodeInfoCenter.getNodeDetails(nodeId);
			if (nodeEntity != null && useCache) {
				dataCacheCenter.put(key, nodeEntity);
			}
		}
		if (null != callback) {
			callback.call(nodeEntity);
		}
		return nodeEntity;
	}

	/**
	 * KEY值为："getNodeDetails_" + nodeId + "_" + containsLineDevices + "_"+
	 * containsPathNodeDevices
	 * 
	 * @return
	 */
	@Override
	public NodeAndNodeLineDeviceEntity getNodeDetails(String nodeId, String credit, boolean containsLineDevices,
			boolean containsPathNodeDevices, CallBack<NodeAndNodeLineDeviceEntity> callback) {
		if (null == nodeId) {
			return null;
		}
		String key = "getNodeDetails_" + nodeId + "_" + containsLineDevices + "_" + containsPathNodeDevices;
		NodeAndNodeLineDeviceEntity nodeAndNodeLineDeviceEntity = getFromCacheDataCenter(key);
		if (null == nodeAndNodeLineDeviceEntity) {
			nodeAndNodeLineDeviceEntity = nodeInfoCenter.getNodeDetails(nodeId, containsLineDevices,
					containsPathNodeDevices);
			if (nodeAndNodeLineDeviceEntity != null && useCache) {
				dataCacheCenter.put(key, nodeAndNodeLineDeviceEntity);
			}
		}
		if (null != callback) {
			callback.call(nodeAndNodeLineDeviceEntity);
		}
		return nodeAndNodeLineDeviceEntity;
	}

	@Override
	public LineDevicesEntity getNodeLineDevicesList(String nodeId, String credit, CallBack<LineDevicesEntity> callback) {
		if (null == nodeId) {
			return null;
		}
		String key = "getNodeLineDevicesList_" + nodeId;
		LineDevicesEntity lineDevicesEntity = getFromCacheDataCenter(key);
		if (null == lineDevicesEntity) {
			lineDevicesEntity = nodeInfoCenter.getNodeLineDevicesist(nodeId);
			if (lineDevicesEntity != null && useCache) {
				dataCacheCenter.put(key, lineDevicesEntity);
			}
		}
		if (null != callback) {
			callback.call(lineDevicesEntity);
		}
		return lineDevicesEntity;
	}

	@Override
	public NodeDevicesEntity getPathNodeDetails(String nodeId, String credit, CallBack<NodeDevicesEntity> callback) {
		if (null == nodeId) {
			return null;
		}
		String key = "getPathNodeDetails_" + nodeId;
		NodeDevicesEntity nodeDevicesEntity = getFromCacheDataCenter(key);
		if (null == nodeDevicesEntity) {
			nodeDevicesEntity = nodeInfoCenter.getPathNodeDetails(nodeId);
			if (nodeDevicesEntity != null && useCache) {
				dataCacheCenter.put(key, nodeDevicesEntity);
			}
		}
		if (null != callback) {
			callback.call(nodeDevicesEntity);
		}
		return nodeDevicesEntity;
	}

	@Override
	public List<EcLineEntity> getAcrossLineInNode(String nodeId, String credit, CallBack<List<EcLineEntity>> callback) {
		if (null == nodeId) {
			return null;
		}
		String key = "getAcrossLineInNode_" + nodeId;
		List<EcLineEntity> data = getFromCacheDataCenter(key);
		if (data == null || data.size() <= 0) {
			data = nodeInfoCenter.getAcrossLineInNode(nodeId);
			if (data != null && !data.isEmpty() && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public DeviceEntity<EcLineLabelEntityVo> getLabel(String labelId, String credit,
			CallBack<DeviceEntity<EcLineLabelEntityVo>> callback) {
		if (null == labelId) {
			return null;
		}
		String key = "getLabel_" + labelId;
		DeviceEntity<EcLineLabelEntityVo> label = getFromCacheDataCenter(key);
		if (label == null) {
			label = deviceCenter.getLabel(labelId);
			if (label != null && useCache) {
				dataCacheCenter.put(key, label);
			}
		}
		if (null != callback) {
			callback.call(label);
		}
		return label;
	}

	@Override
	public DeviceEntity<EcDistBoxEntityVo> getDistBox(String boxId, String credit,
			CallBack<DeviceEntity<EcDistBoxEntityVo>> callback) {
		if (null == boxId) {
			return null;
		}
		String key = "getDistBox_" + boxId;
		DeviceEntity<EcDistBoxEntityVo> data = getFromCacheDataCenter(key);
		if (null == data) {
			data = deviceCenter.getDistBox(boxId);
			if (data != null && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public DeviceEntity<EcMiddleJointEntityVo> getMiddle(String middleId, String credit,
			CallBack<DeviceEntity<EcMiddleJointEntityVo>> callback) {
		if (null == middleId) {
			return null;
		}
		String key = "getMiddle_" + middleId;
		DeviceEntity<EcMiddleJointEntityVo> data = getFromCacheDataCenter(key);
		if (data == null) {
			data = deviceCenter.getMiddle(middleId);
			if (data != null && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public DeviceEntity<EcWorkWellEntityVo> getWorkWell(String wellId, String credit,
			CallBack<DeviceEntity<EcWorkWellEntityVo>> callback) {
		if (null == wellId) {
			return null;
		}
		String key = "getWorkWell_" + wellId;
		DeviceEntity<EcWorkWellEntityVo> data = getFromCacheDataCenter(key);
		if (data == null) {
			data = deviceCenter.getWorkWell(wellId);
			if (data != null && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public DeviceEntity<EcDistributionRoomEntityVo> getDistributionRoom(String roomId, String credit,
			CallBack<DeviceEntity<EcDistributionRoomEntityVo>> callback) {
		if (null == roomId) {
			return null;
		}
		String key = "getDistributionRoom_" + roomId;
		DeviceEntity<EcDistributionRoomEntityVo> data = getFromCacheDataCenter(key);
		if (null == data) {
			data = deviceCenter.getDistributionRoom(roomId);
			if (data != null && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public DeviceEntity<EcSubstationEntityVo> getStation(String stationId, String credit,
			CallBack<DeviceEntity<EcSubstationEntityVo>> callback) {
		if (null == stationId) {
			return null;
		}
		String key = "getStation_" + stationId;
		DeviceEntity<EcSubstationEntityVo> data = getFromCacheDataCenter(key);
		if (null == data) {
			data = deviceCenter.getStation(stationId);
			if (data != null && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public DeviceEntity<EcTransformerEntityVo> getTransformer(String transformerId, String credit,
			CallBack<DeviceEntity<EcTransformerEntityVo>> callback) {
		if (null == transformerId) {
			return null;
		}
		String key = "getTransformer_" + transformerId;
		DeviceEntity<EcTransformerEntityVo> data = getFromCacheDataCenter(key);
		if (data == null) {
			data = deviceCenter.getTransformer(transformerId);
			if (data != null && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public DeviceEntity<EcTowerEntityVo> getTower(String towerId, String credit,
			CallBack<DeviceEntity<EcTowerEntityVo>> callback) {
		if (null == towerId) {
			return null;
		}
		String key = "getTower_" + towerId;
		DeviceEntity<EcTowerEntityVo> data = getFromCacheDataCenter(key);
		if (data == null) {
			data = deviceCenter.getTower(towerId);
			if (data != null && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public ChannelDataEntity getChannel(String channelId, String credit, CallBack<ChannelDataEntity> callback) {
		if (null == channelId) {
			return null;
		}
		String key = "getChannel_" + channelId;
		ChannelDataEntity data = getFromCacheDataCenter(key);
		if (null == data) {
			data = channelCenter.getChannel(channelId);
			if (data != null && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public EcLineNodesEntity getLineNode(String oid, String lineId, String credit, CallBack<EcLineNodesEntity> callback) {
		if (null == oid || null == lineId) {
			return null;
		}
		String key = "getLineNode_" + oid + "_" + lineId;
		EcLineNodesEntity lineNode = getFromCacheDataCenter(key);
		if (null == lineNode) {
			lineNode = nodeInfoCenter.getLineNode(oid, lineId);
			if (lineNode != null && useCache) {
				dataCacheCenter.put(key, lineNode);
			}
		}
		if (null != callback) {
			callback.call(lineNode);
		}
		return lineNode;
	}

	@Override
	public List<EcLineNodesEntity> getLineNodeListByOid(String oid, String credit,
			CallBack<List<EcLineNodesEntity>> callback) {
		if (null == oid) {
			return null;
		}
		String key = "getLineNodeListByOid_" + oid;
		List<EcLineNodesEntity> lineNodeListByOid = getFromCacheDataCenter(key);
		if (null == lineNodeListByOid) {
			lineNodeListByOid = nodeInfoCenter.getLineNodeListByOid(oid);
			if (useCache && lineNodeListByOid != null && !lineNodeListByOid.isEmpty()) {
				dataCacheCenter.put(key, lineNodeListByOid);
			}
		}
		if (null != callback) {
			callback.call(lineNodeListByOid);
		}
		return lineNodeListByOid;
	}

	@Override
	public LineDetailsEntity getLineDetailsByLineNo(String lineNo, boolean containsNodes,
			CallBack<LineDetailsEntity> callback) {
		if (null == lineNo) {
			return null;
		}
		String key = "getLineDetailsByLineNo_" + lineNo + "_" + containsNodes;
		LineDetailsEntity data = getFromCacheDataCenter(key);
		if (null == data) {
			data = lineInfoCenter.getLineDetailsByLineNo(lineNo, containsNodes);
			if (data != null && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<DeviceEntity<EcMiddleJointEntityVo>> getLineMiddleList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcMiddleJointEntityVo>>> callback) {
		if (null == lineId) {
			return null;
		}
		String key = "getLineMiddleList_" + lineId;
		List<DeviceEntity<EcMiddleJointEntityVo>> data = getFromCacheDataCenter(key);
		if (null == data) {
			LineDevicesEntity lineDevice = lineInfoCenter.getLineDevice(lineId);
			if (null != lineDevice) {
				data = lineDevice.getMiddleJointEntityVoList();
				if (null != data && useCache && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<DeviceEntity<EcDistBoxEntityVo>> getLineDistBoxList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcDistBoxEntityVo>>> callback) {
		if (null == lineId) {
			return null;
		}
		String key = "getLineDistBoxList_" + lineId;
		List<DeviceEntity<EcDistBoxEntityVo>> data = getFromCacheDataCenter(key);
		if (null == data) {
			LineDevicesEntity lineDevice = lineInfoCenter.getLineDevice(lineId);
			if (null != lineDevice) {
				data = lineDevice.getBoxList();
				if (null != data && useCache && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<DeviceEntity<EcWorkWellEntityVo>> getNodeWellList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcWorkWellEntityVo>>> callback) {
		if (null == nodeid) {
			return null;
		}
		String key = "getNodeWellList_" + nodeid;
		List<DeviceEntity<EcWorkWellEntityVo>> data = getFromCacheDataCenter(key);
		if (null == data) {
			NodeDevicesEntity pathNodeDetails = nodeInfoCenter.getPathNodeDetails(nodeid);
			if (null != pathNodeDetails) {
				data = pathNodeDetails.getWell();
				if (useCache && null != data && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<DeviceEntity<EcSubstationEntityVo>> getNodeStationList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcSubstationEntityVo>>> callback) {
		if (null == nodeid) {
			return null;
		}
		String key = "getNodeStationList_" + nodeid;
		List<DeviceEntity<EcSubstationEntityVo>> data = getFromCacheDataCenter(key);
		if (null == data) {
			NodeDevicesEntity pathNodeDetails = nodeInfoCenter.getPathNodeDetails(nodeid);
			if (null != pathNodeDetails) {
				data = pathNodeDetails.getStation();
				if (useCache && null != data && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<DeviceEntity<EcTowerEntityVo>> getNodeTowerList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcTowerEntityVo>>> callback) {
		if (null == nodeid) {
			return null;
		}
		String key = "getNodeTowerList_" + nodeid;
		List<DeviceEntity<EcTowerEntityVo>> data = getFromCacheDataCenter(key);
		if (null == data) {
			NodeDevicesEntity pathNodeDetails = nodeInfoCenter.getPathNodeDetails(nodeid);
			if (null != pathNodeDetails) {
				data = pathNodeDetails.getTower();
				if (useCache && null != data && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<DeviceEntity<EcTransformerEntityVo>> getNodedTranformerList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcTransformerEntityVo>>> callback) {
		if (null == nodeid) {
			return null;
		}
		String key = "getNodedTranformerList_" + nodeid;
		List<DeviceEntity<EcTransformerEntityVo>> data = getFromCacheDataCenter(key);
		if (null == data) {
			NodeDevicesEntity pathNodeDetails = nodeInfoCenter.getPathNodeDetails(nodeid);
			if (null != pathNodeDetails) {
				data = pathNodeDetails.getTranformer();
				if (useCache && null != data && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<DeviceEntity<EcDistributionRoomEntityVo>> getNodeDistributionRoomList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcDistributionRoomEntityVo>>> callback) {
		if (null == nodeid) {
			return null;
		}
		String key = "getNodeDistributionRoomList_" + nodeid;
		List<DeviceEntity<EcDistributionRoomEntityVo>> data = getFromCacheDataCenter(key);
		if (null == data) {
			NodeDevicesEntity pathNodeDetails = nodeInfoCenter.getPathNodeDetails(nodeid);
			if (null != pathNodeDetails) {
				data = pathNodeDetails.getDistribution_room();
				if (useCache && null != data && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<OfflineAttach> getOfflineAttachListByWorkNo(String workNo, String credit,
			CallBack<List<OfflineAttach>> callback) {
		if (null == workNo) {
			return null;
		}
		String key = "getOfflineAttachListByWorkNo_" + workNo;
		List<OfflineAttach> data = getFromCacheDataCenter(key);
		if (null == data) {
			data = offlineAttachCenter.getOfflineAttachListByWorkNo(workNo);
			if (null != data) {
				if (useCache && data != null && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<DeviceEntity<EcHwgEntityVo>> getHwgList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcHwgEntityVo>>> callback) {
		if (lineId == null) {
			return null;
		}
		String key = "getHwgList_" + lineId;
		List<DeviceEntity<EcHwgEntityVo>> hwgList = getFromCacheDataCenter(key);
		if (null == hwgList) {
			LineDevicesEntity lineDevice = lineInfoCenter.getLineDevice(lineId);
			hwgList = lineDevice.getHwgEntityVoList();
			if (hwgList != null && useCache && !hwgList.isEmpty()) {
				dataCacheCenter.put(key, hwgList);
			}
		}
		if (null != callback) {
			callback.call(hwgList);
		}
		return hwgList;
	}

	@Override
	public List<DeviceEntity<EcDlfzxEntityVo>> getDlfzxList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcDlfzxEntityVo>>> callback) {
		if (lineId == null) {
			return null;
		}
		String key = "getDlfzxList_" + lineId;
		List<DeviceEntity<EcDlfzxEntityVo>> dlfzxList = getFromCacheDataCenter(key);
		if (null == dlfzxList) {
			LineDevicesEntity lineDevice = lineInfoCenter.getLineDevice(lineId);
			dlfzxList = lineDevice.getDlfzxEntityVoList();
			if (dlfzxList != null && useCache && !dlfzxList.isEmpty()) {
				dataCacheCenter.put(key, dlfzxList);
			}
		}
		if (null != callback) {
			callback.call(dlfzxList);
		}
		return dlfzxList;
	}

	@Override
	public List<DeviceEntity<EcDydlfzxEntityVo>> getDydlfzxList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcDydlfzxEntityVo>>> callback) {
		if (lineId == null) {
			return null;
		}
		String key = "getDydlfzxList_" + lineId;
		List<DeviceEntity<EcDydlfzxEntityVo>> dydlfzxList = getFromCacheDataCenter(key);
		if (null == dydlfzxList) {
			LineDevicesEntity lineDevice = lineInfoCenter.getLineDevice(lineId);
			dydlfzxList = lineDevice.getDydlfzxEntityVoList();
			if (dydlfzxList != null && useCache && !dydlfzxList.isEmpty()) {
				dataCacheCenter.put(key, dydlfzxList);
			}
		}
		if (null != callback) {
			callback.call(dydlfzxList);
		}
		return dydlfzxList;
	}

	// 箱式变电站
	@Override
	public List<DeviceEntity<EcXsbdzEntityVo>> getNodeXsbdzList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcXsbdzEntityVo>>> callback) {
		if (null == nodeid) {
			return null;
		}
		String key = "getNodeXsbdzList_" + nodeid;
		List<DeviceEntity<EcXsbdzEntityVo>> data = getFromCacheDataCenter(key);
		if (null == data) {
			NodeDevicesEntity pathNodeDetails = nodeInfoCenter.getPathNodeDetails(nodeid);
			if (null != pathNodeDetails) {
				data = pathNodeDetails.getXsbdz();
				if (useCache && null != data && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	// 开关站
	@Override
	public List<DeviceEntity<EcKgzEntityVo>> getNodeKgzList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcKgzEntityVo>>> callback) {
		if (null == nodeid) {
			return null;
		}
		String key = "getNodeKgzList_" + nodeid;
		List<DeviceEntity<EcKgzEntityVo>> data = getFromCacheDataCenter(key);
		if (null == data) {
			NodeDevicesEntity pathNodeDetails = nodeInfoCenter.getPathNodeDetails(nodeid);
			if (null != pathNodeDetails) {
				data = pathNodeDetails.getKgz();
				if (useCache && null != data && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	// 低压配电箱
	@Override
	public List<DeviceEntity<EcDypdxEntityVo>> getNodeDypdxList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcDypdxEntityVo>>> callback) {
		if (null == nodeid) {
			return null;
		}
		String key = "getNodeDypdxList_" + nodeid;
		List<DeviceEntity<EcDypdxEntityVo>> data = getFromCacheDataCenter(key);
		if (null == data) {
			NodeDevicesEntity pathNodeDetails = nodeInfoCenter.getPathNodeDetails(nodeid);
			if (null != pathNodeDetails) {
				data = pathNodeDetails.getDypdx();
				if (useCache && null != data && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<EcLineEntity> getLineListByOrgid(String orgid, String credit, CallBack<List<EcLineEntity>> callback) {
		if (null == orgid) {
			return null;
		}
		String key = "getLineListByOrgid_" + orgid;
		List<EcLineEntity> data = getFromCacheDataCenter(key);
		if (null == data) {
			List<EcLineEntity> ecLineEntities = lineInfoCenter.getLineListByOrgid(orgid);
			if (null != ecLineEntities) {
				data = ecLineEntities;
				if (useCache && null != data && !data.isEmpty()) {
					dataCacheCenter.put(key, data);
				}
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public EcLineEntity getLineDeviceTargetLine(String deviceId, String credit, CallBack<EcLineEntity> callback) {
		if (null == deviceId) {
			return null;
		}
		String key = "etLineDeviceTargetLine_" + deviceId;
		EcLineEntity data = getFromCacheDataCenter(key);
		if (null == data) {
			data = deviceCenter.getLineDeviceTargetLine(deviceId);
			if (null != data && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<ChannelDataEntity> getChannelListByLineID(String lineid, String credit,
			CallBack<List<ChannelDataEntity>> callback) {
		if (lineid == null) {
			return null;
		}
		List<ChannelDataEntity> data = null;
		String key = "getChannelListByLineID_" + lineid;
		data = getFromCacheDataCenter(key);
		if (null == data) {
			data = channelCenter.getChannelListByLineID(lineid);
			if (null != data && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<ChannelDataEntity> getAllChannel(String credit, CallBack<List<ChannelDataEntity>> callback) {
		List<ChannelDataEntity> data = null;
		String key = "getAllChannel";
		data = getFromCacheDataCenter(key);
		if (null == data) {
			data = channelCenter.getAllChannel();
			if (null != data && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<DeviceEntity<EcWorkWellCoverEntity>> getWellCoverList(String credit, String wellId, boolean needNode,
			CallBack<List<DeviceEntity<EcWorkWellCoverEntity>>> callback) {
		if (null == wellId) {
			return null;
		}
		String key = "getWellCoverList_" + wellId + "_" + needNode;
		List<DeviceEntity<EcWorkWellCoverEntity>> data = getFromCacheDataCenter(key);
		if (null == data) {
			data = deviceCenter.getWellCoverList(wellId, needNode);
			if (null != data && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<EcLineLabelEntity> getDeviceLabel(String credit, Object deviceObj, CallBack<List<EcLineLabelEntity>> callback) {
		if (null == deviceObj) {
			return null;
		}
		String key = "getLabel_" + ResourceDeviceUtil.getDevicePrimaryKey(deviceObj);
		List<EcLineLabelEntity> data = getFromCacheDataCenter(key);
		if (null == data) {
			data = labelCenter.getDeviceLabel(deviceObj);
			if (null != data && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public Object getLabelTargetDevice(String credit, String labelno, CallBack<Object> callback) {
		if (null == labelno) {
			return null;
		}
		String key = "getLabelTargetDevice_" + labelno;
		Object data = getFromCacheDataCenter(key);
		if (null == data) {
			data = labelCenter.getLabelTargetDevice(labelno);
			if (null != data && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}
	
	@Override
	public List<EcVRRefEntity> getPanoramicByNodeOid(String objId, String credit, CallBack<List<EcVRRefEntity>> callback) {
		if (objId == null) {
			return null;
		}
		String key = "getPanoramicByNodeOid_" + objId;
		List<EcVRRefEntity> data = getFromCacheDataCenter(key);
		if (null == data) {
			data = deviceCenter.getPanoramicByNodeOid(objId);
			if (null != data && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}

	@Override
	public List<EcVRRefEntity> getPanoramicByDeviceId(String objId, String credit, CallBack<List<EcVRRefEntity>> callback) {
		if (objId == null) {
			return null;
		}
		String key = "getPanoramicByDeviceId_" + objId;
		List<EcVRRefEntity> data = getFromCacheDataCenter(key);
		if (null == data) {
			data = deviceCenter.getPanoramicByDeviceId(objId);
			if (null != data && useCache) {
				dataCacheCenter.put(key, data);
			}
		}
		if (null != callback) {
			callback.call(data);
		}
		return data;
	}
	
	@Override
	public EcChannelNodesEntity getChannelNode(String oid, String channelId, String credit, CallBack<EcChannelNodesEntity> callback) {
		if (null == oid || null == channelId) {
			return null;
		}
		String key = "getChannelNode_" + oid + "_" + channelId;
		EcChannelNodesEntity channelNode = getFromCacheDataCenter(key);
		if (null == channelNode) {
			channelNode = nodeInfoCenter.getChannelNode(oid, channelId);
			if (channelNode != null && useCache) {
				dataCacheCenter.put(key, channelNode);
			}
		}
		if (null != callback) {
			callback.call(channelNode);
		}
		return channelNode;
	}

}
