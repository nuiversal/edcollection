package com.winway.android.edcollection.datacenter.api;

import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.LineDetailsEntity;
import com.winway.android.edcollection.datacenter.entity.LineDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.NodeAndNodeLineDeviceEntity;
import com.winway.android.edcollection.datacenter.entity.NodeDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.SubStationDetailsEntity;
import com.winway.android.edcollection.datacenter.entity.edp.OrgTreeResult;
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

/**
 * 数据中心接口
 * @author mr-lao
 *
 */
public interface DataCenter {
	/**
	 * 回调器
	 * 
	 * @author mr-lao
	 *
	 * @param <T>
	 */
	public interface CallBack<T> {
		void call(T data);
	}

	/**
	 * 获取机构树
	 * 
	 * @param orgId
	 * @param credit
	 * @param call
	 */
	public void getOrgTree(String orgId, String logicSysNo, String credit, CallBack<OrgTreeResult> callback);

	/**
	 * 获得机构下的所有变电站信息列表
	 * 
	 * @param orgId
	 * @param credit
	 * @param call
	 */
	public List<EcSubstationEntity> getSubStationsList(String orgId, String credit,
			CallBack<List<EcSubstationEntity>> callback);

	/**
	 * 获取变电站下的所有线路信息
	 * 
	 * @param subStationId
	 * @param credit
	 * @param containsLines
	 * @param callback
	 */
	public SubStationDetailsEntity getSubStationDetails(String subStationId, String credit, boolean containsLines,
			CallBack<SubStationDetailsEntity> callback);

	/**
	 * 获取变电站下的所有线路信息
	 * 
	 * @param subStationId
	 * @param credit
	 * @param callback
	 */
	public List<EcLineEntity> getSubStationLineList(String subStationId, String credit,
			CallBack<List<EcLineEntity>> callback);

	/**
	 * 获取具体的线路信息
	 * 
	 * @param lineId
	 * @param credit
	 * @param containsNodes
	 * @param callback
	 */
	public LineDetailsEntity getLineDetails(String lineId, String credit, boolean containsNodes,
			CallBack<LineDetailsEntity> callback);

	/**
	 * 获取线路下的所有节点信息
	 * 
	 * @param lineId
	 * @param credit
	 * @param callback
	 */
	public List<EcNodeEntity> getLineNodeList(String lineId, String credit, CallBack<List<EcNodeEntity>> callback);

	/**
	 * 获取线路的所有线路附属设备
	 * 
	 * @param lineId
	 * @param credit
	 * @param callback
	 */
	public LineDevicesEntity getLineDevicesList(String lineId, String credit, CallBack<LineDevicesEntity> callback);

	/**
	 * 获得线路标签
	 * 
	 * @param lineId
	 * @param credit
	 * @param callback
	 */
	public List<DeviceEntity<EcLineLabelEntityVo>> getLineLabelList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcLineLabelEntityVo>>> callback);

	/**
	 * 获得环网柜
	 * 
	 * @param lineId
	 * @param credit
	 * @param callback
	 */
	public List<DeviceEntity<EcHwgEntityVo>> getHwgList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcHwgEntityVo>>> callback);

	/**
	 * 获得电缆分支箱
	 * 
	 * @param lineId
	 * @param credit
	 * @param callback
	 */
	public List<DeviceEntity<EcDlfzxEntityVo>> getDlfzxList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcDlfzxEntityVo>>> callback);

	/**
	 * 获得低压电缆分支箱
	 * 
	 * @param lineId
	 * @param credit
	 * @param callback
	 */
	public List<DeviceEntity<EcDydlfzxEntityVo>> getDydlfzxList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcDydlfzxEntityVo>>> callback);

	/**
	 * 获得具体节点信息
	 * 
	 * @param nodeId
	 * @param credit
	 * @param callback
	 */
	public EcNodeEntity getNodeDetail(String nodeId, String credit, CallBack<EcNodeEntity> callback);

	/**
	 * 具体的节点信息
	 * 
	 * @param nodeId
	 * @param credit
	 * @param containsLineDevices
	 * @param containsPathNodeDevices
	 * @param callback
	 */
	public NodeAndNodeLineDeviceEntity getNodeDetails(String nodeId, String credit, boolean containsLineDevices,
			boolean containsPathNodeDevices, CallBack<NodeAndNodeLineDeviceEntity> callback);

	/**
	 * 线路附属设备
	 * 
	 * @param nodeId
	 * @param credit
	 * @param callback
	 */
	public LineDevicesEntity getNodeLineDevicesList(String nodeId, String credit, CallBack<LineDevicesEntity> callback);

	/**
	 * 路径点设备
	 * 
	 * @param nodeId
	 * @param credit
	 * @param callback
	 */
	public NodeDevicesEntity getPathNodeDetails(String nodeId, String credit, CallBack<NodeDevicesEntity> callback);

	/**
	 * 通过节点的所有线路
	 * 
	 * @param nodeId
	 * @param credit
	 * @param callback
	 */
	public List<EcLineEntity> getAcrossLineInNode(String nodeId, String credit, CallBack<List<EcLineEntity>> callback);

	/**
	 * 获取标签设备
	 * 
	 * @param labelId
	 * @param credit
	 * @param callback
	 */
	public DeviceEntity<EcLineLabelEntityVo> getLabel(String labelId, String credit,
			CallBack<DeviceEntity<EcLineLabelEntityVo>> callback);

	/**
	 * 变电箱设备
	 * 
	 * @param boxId
	 * @param credit
	 * @param callback
	 */
	public DeviceEntity<EcDistBoxEntityVo> getDistBox(String boxId, String credit,
			CallBack<DeviceEntity<EcDistBoxEntityVo>> callback);

	/**
	 * 中间头设备
	 * 
	 * @param middleId
	 * @param credit
	 * @param callback
	 */
	public DeviceEntity<EcMiddleJointEntityVo> getMiddle(String middleId, String credit,
			CallBack<DeviceEntity<EcMiddleJointEntityVo>> callback);

	/**
	 * 工井设备
	 * 
	 * @param wellId
	 * @param credit
	 * @param callback
	 */
	public DeviceEntity<EcWorkWellEntityVo> getWorkWell(String wellId, String credit,
			CallBack<DeviceEntity<EcWorkWellEntityVo>> callback);

	/**
	 * 配电房设备
	 * 
	 * @param roomId
	 * @param credit
	 * @param callback
	 */
	public DeviceEntity<EcDistributionRoomEntityVo> getDistributionRoom(String roomId, String credit,
			CallBack<DeviceEntity<EcDistributionRoomEntityVo>> callback);

	/**
	 * 变电站设备
	 * 
	 * @param stationId
	 * @param credit
	 * @param callback
	 */
	public DeviceEntity<EcSubstationEntityVo> getStation(String stationId, String credit,
			CallBack<DeviceEntity<EcSubstationEntityVo>> callback);

	/**
	 * 变压器设备
	 * 
	 * @param transformerId
	 * @param credit
	 * @param callback
	 */
	public DeviceEntity<EcTransformerEntityVo> getTransformer(String transformerId, String credit,
			CallBack<DeviceEntity<EcTransformerEntityVo>> callback);

	/**
	 * 杆塔设备
	 * 
	 * @param towerId
	 * @param credit
	 * @param callback
	 */
	public DeviceEntity<EcTowerEntityVo> getTower(String towerId, String credit,
			CallBack<DeviceEntity<EcTowerEntityVo>> callback);

	/**
	 * 通道
	 * 
	 * @param channelId
	 * @param credit
	 * @param callback
	 */
	public ChannelDataEntity getChannel(String channelId, String credit, CallBack<ChannelDataEntity> callback);

	/**
	 * 获取某条线在某个节点的线路节点
	 * 
	 * @param oid
	 *            节点oid
	 * @param lineId
	 *            线路id
	 * @param credit
	 * @param callback
	 */
	public EcLineNodesEntity getLineNode(String oid, String lineId, String credit, CallBack<EcLineNodesEntity> callback);

	/**
	 * 根据oid获取线路节点列表
	 * 
	 * @param oid
	 *            节点oid
	 * @param credit
	 * @param callback
	 */
	public List<EcLineNodesEntity> getLineNodeListByOid(String oid, String credit,
			CallBack<List<EcLineNodesEntity>> callback);

	/**
	 * 根据线路编号获得线路详细信息
	 * 
	 * @param lineNo
	 * @param containsNodes
	 * @param callback
	 * @return
	 */
	public LineDetailsEntity getLineDetailsByLineNo(String lineNo, boolean containsNodes,
			CallBack<LineDetailsEntity> callback);

	/**
	 * 获取线路的所有中间接头
	 * 
	 * @param lineId
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<DeviceEntity<EcMiddleJointEntityVo>> getLineMiddleList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcMiddleJointEntityVo>>> callback);

	/**
	 * 获取线路的所有分接箱
	 * 
	 * @param lineId
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<DeviceEntity<EcDistBoxEntityVo>> getLineDistBoxList(String lineId, String credit,
			CallBack<List<DeviceEntity<EcDistBoxEntityVo>>> callback);

	/**
	 * 获得节点关联的所有工井
	 * 
	 * @param nodeid
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<DeviceEntity<EcWorkWellEntityVo>> getNodeWellList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcWorkWellEntityVo>>> callback);

	/**
	 * 获得节点关联的所有变电站
	 * 
	 * @param nodeid
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<DeviceEntity<EcSubstationEntityVo>> getNodeStationList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcSubstationEntityVo>>> callback);

	/**
	 * 获得节点关联的所有箱式变电站
	 * 
	 * @param nodeid
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<DeviceEntity<EcXsbdzEntityVo>> getNodeXsbdzList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcXsbdzEntityVo>>> callback);

	/**
	 * 获得节点关联的所有开关站
	 * 
	 * @param nodeid
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<DeviceEntity<EcKgzEntityVo>> getNodeKgzList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcKgzEntityVo>>> callback);

	/**
	 * 获得节点关联的所有低压配电箱
	 * 
	 * @param nodeid
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<DeviceEntity<EcDypdxEntityVo>> getNodeDypdxList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcDypdxEntityVo>>> callback);

	/**
	 * 获得节点关联的所有杆塔
	 * 
	 * @param nodeid
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<DeviceEntity<EcTowerEntityVo>> getNodeTowerList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcTowerEntityVo>>> callback);

	/**
	 * 获得节点关联的所有变压器
	 * 
	 * @param nodeid
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<DeviceEntity<EcTransformerEntityVo>> getNodedTranformerList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcTransformerEntityVo>>> callback);

	/**
	 * 获得节点关联的所有配电房
	 * 
	 * @param nodeid
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<DeviceEntity<EcDistributionRoomEntityVo>> getNodeDistributionRoomList(String nodeid, String credit,
			CallBack<List<DeviceEntity<EcDistributionRoomEntityVo>>> callback);

	/**
	 * 根据对象id去离线附件表拿到照片集合
	 * 
	 * @param workNo
	 * @return
	 */
	public List<OfflineAttach> getOfflineAttachListByWorkNo(String workNo, String credit,
			CallBack<List<OfflineAttach>> callback);

	/**
	 * @XXX 根据机构id获取线路列表(测试不完全，此方法慎用)
	 * 
	 * @param orgid
	 * @param credit
	 * @param callback
	 */
	public List<EcLineEntity> getLineListByOrgid(String orgid, String credit, CallBack<List<EcLineEntity>> callback);

	/**
	 * 获得线路附属设备关联的线路信息
	 * @param deviceId
	 * @param credit
	 * @param callback
	 * @return
	 */
	public EcLineEntity getLineDeviceTargetLine(String deviceId, String credit, CallBack<EcLineEntity> callback);

	/**
	 * 获得线路通过的所有通道
	 * @param lineid
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<ChannelDataEntity> getChannelListByLineID(String lineid, String credit,
			CallBack<List<ChannelDataEntity>> callback);

	/**
	 * 获得所有通道数据
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<ChannelDataEntity> getAllChannel(String credit, CallBack<List<ChannelDataEntity>> callback);

	/**
	 * 获取井盖
	 * @param wellId  工井主键
	 * @param needNode  是否要包含路径点数据
	 * @return
	 * @throws DbException
	 */
	public List<DeviceEntity<EcWorkWellCoverEntity>> getWellCoverList(String credit, String wellId, boolean needNode,
			CallBack<List<DeviceEntity<EcWorkWellCoverEntity>>> callback);

	/**
	 * 获取设备关联的所有电子标签
	 * @param credit
	 * @param deviceObj   设备对象
	 * @param callback
	 * @return
	 */
	public List<EcLineLabelEntity> getDeviceLabel(String credit, Object deviceObj, CallBack<List<EcLineLabelEntity>> callback);

	/**
	 * 获取电子标签关联的设备
	 * @param credit
	 * @param labelno   电子标签编号
	 * @param callback
	 * @return
	 */
	public Object getLabelTargetDevice(String credit, String labelno, CallBack<Object> callback);
	
	/**
	 * 根据路径点oid拿到全景列表
	 * 
	 * @param oid
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<EcVRRefEntity> getPanoramicByNodeOid(String nodeOid, String credit, CallBack<List<EcVRRefEntity>> callback);

	/**
	 * 根据设备主键id拿到全景列表
	 * 
	 * @param objId
	 * @param credit
	 * @param callback
	 * @return
	 */
	public List<EcVRRefEntity> getPanoramicByDeviceId(String objId, String credit, CallBack<List<EcVRRefEntity>> callback);
	
	/**
	 * 获取某条通道在某个节点的通道节点
	 * @param oid
	 * @param channelId
	 * @param credit
	 * @param callback
	 * @return
	 */
	public EcChannelNodesEntity getChannelNode(String oid, String channelId, String credit, CallBack<EcChannelNodesEntity> callback);
}
