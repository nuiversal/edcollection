package com.winway.android.edcollection.datacenter.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.LineDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.NodeAndNodeLineDeviceEntity;
import com.winway.android.edcollection.datacenter.entity.NodeDevicesEntity;
import com.winway.android.edcollection.datacenter.loader.LoaderConfig;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcLineDeviceEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
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
 * 节点信息中心
 * 
 * @author lyh
 * @version 创建时间：2017年1月5日 上午11:34:43
 * 
 */
public class NodeInfoCenter {
	BaseDBUtil dbUtil;
	// 设备中心
	DeviceCenter deviceCenter;

	public NodeInfoCenter(Context context, String dbUrl) {
		super();
		if (dbUrl == null) {
			dbUrl = LoaderConfig.dbUrl;
		}
		dbUtil = new BaseDBUtil(context, new File(dbUrl));
		deviceCenter = new DeviceCenter(dbUtil);
	}

	public NodeInfoCenter(BaseDBUtil dbUtil) {
		this.dbUtil = dbUtil;
		deviceCenter = new DeviceCenter(dbUtil);
	}

	public NodeInfoCenter(BaseDBUtil dbUtil, DeviceCenter deviceCenter) {
		super();
		this.dbUtil = dbUtil;
		this.deviceCenter = deviceCenter;
	}

	/**
	 * 1、获得具体节点信息
	 * 
	 * @param nodeId
	 */
	public EcNodeEntity getNodeDetails(String nodeId) {
		try {
			EcNodeEntity data = dbUtil.getById(EcNodeEntity.class, nodeId);
			if (data != null) {
				return data;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 2、获得具体节点信息
	 * 
	 * @param nodeId
	 *            =节点ID
	 * @param containsDevices
	 *            =限定返回的数据中是否要包含线路附属设备数据，
	 *            true表示返回的数据中要包含线路附属设备数据，false则表示返回的数据中不包含线路附属设备数据
	 *            containsPathNodeDevices=路径点设备
	 */
	public NodeAndNodeLineDeviceEntity getNodeDetails(String nodeId, boolean containsLineDevices,
			boolean containsPathNodeDevices) {
		NodeAndNodeLineDeviceEntity datas = new NodeAndNodeLineDeviceEntity();
		EcNodeEntity data = this.getNodeDetails(nodeId);
		if (data == null) {
			return null;
		}
		if (containsLineDevices) {
			LineDevicesEntity lineDatas = this.getNodeLineDevicesist(nodeId);
			if (lineDatas != null) {
				datas.setLineDatas(lineDatas);
			}
		}
		if (containsPathNodeDevices) {
			NodeDevicesEntity nodeDatas = this.getPathNodeDetails(nodeId);
			if (nodeDatas != null) {
				datas.setNodeDatas(nodeDatas);
			}
		}
		datas.setData(data);
		return datas;
	}

	/**
	 * 3、获取节点关联的线路附属设备
	 * 
	 * @param nodeId
	 */
	public LineDevicesEntity getNodeLineDevicesist(String nodeId) {
		// 所有设备实体
		LineDevicesEntity datas = new LineDevicesEntity();
		// 设备类型
		String deviceType;
		// 对象id
		String objid;
		try {
			// 标签集合
			List<DeviceEntity<EcLineLabelEntityVo>> labelList = new ArrayList<DeviceEntity<EcLineLabelEntityVo>>();
			// 分接箱集合
			List<DeviceEntity<EcDistBoxEntityVo>> boxList = new ArrayList<DeviceEntity<EcDistBoxEntityVo>>();
			// 中间接头集合
			List<DeviceEntity<EcMiddleJointEntityVo>> middleList = new ArrayList<DeviceEntity<EcMiddleJointEntityVo>>();
			List<DeviceEntity<EcHwgEntityVo>> hwgList = new ArrayList<DeviceEntity<EcHwgEntityVo>>(); // 电缆中间接头
			List<DeviceEntity<EcDlfzxEntityVo>> dlfzxList = new ArrayList<DeviceEntity<EcDlfzxEntityVo>>(); // 电缆中间接头
			List<DeviceEntity<EcDydlfzxEntityVo>> dydlfzxList = new ArrayList<DeviceEntity<EcDydlfzxEntityVo>>(); // 电缆中间接头
			// 根据节点id查询
			EcNodeEntity nodeEntity = this.getNodeDetails(nodeId);
			if (nodeEntity != null) {
				// 拿到oid并查询
				EcLineDeviceEntity queryEntity = new EcLineDeviceEntity();
				queryEntity.setOid(nodeEntity.getOid());
				List<EcLineDeviceEntity> DeviceEntities = dbUtil.excuteQuery(EcLineDeviceEntity.class, queryEntity);
				if (DeviceEntities != null && DeviceEntities.size() > 0) {
					for (EcLineDeviceEntity ecNodeDeviceEntity : DeviceEntities) {
						deviceType = ecNodeDeviceEntity.getDeviceType();
						objid = ecNodeDeviceEntity.getDevObjId();
						if (ResourceEnum.DZBQ.getValue().equals(deviceType)) {// 标签
							DeviceEntity<EcLineLabelEntityVo> label = deviceCenter.getLabel(objid);
							if (label != null) {
								labelList.add(label);
							}
						} else if (ResourceEnum.ZJJT.getValue().equals(deviceType)) {// 中间接头
							DeviceEntity<EcMiddleJointEntityVo> middle = deviceCenter.getMiddle(objid);
							if (middle != null) {
								middleList.add(middle);
							}
						} else if (ResourceEnum.HWG.getValue().equals(deviceType)) {// 环网柜
							DeviceEntity<EcHwgEntityVo> hwg = deviceCenter.getHwg(objid);
							if (hwg != null) {
								hwgList.add(hwg);
							}
						} else if (ResourceEnum.DLFZX.getValue().equals(deviceType)) { // 电缆分支箱
							DeviceEntity<EcDlfzxEntityVo> middle = deviceCenter.getDlfzx(objid);
							if (middle != null) {
								dlfzxList.add(middle);
							}
						} else if (ResourceEnum.DYDLFZX.getValue().equals(deviceType)) { // 低压电缆分支箱
							DeviceEntity<EcDydlfzxEntityVo> middle = deviceCenter.getDydlfzx(objid);
							if (middle != null) {
								dydlfzxList.add(middle);
							}
						}
					}
				}
			}
			if (datas != null) {
				datas.setLineLabelEntityVoList(labelList);
				datas.setBoxList(boxList);
				datas.setMiddleJointEntityVoList(middleList);
				datas.setHwgEntityVoList(hwgList);
				datas.setDlfzxEntityVoList(dlfzxList);
				datas.setDydlfzxEntityVoList(dydlfzxList);
				return datas;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 4、获取节点关联的路径点设备，如1 变电站，2 配电房，3 变压器，4 杆塔，5 工井
	 * 
	 * @param nodeId
	 */
	public NodeDevicesEntity getPathNodeDetails(String nodeId) {
		// 所有设备实体
		NodeDevicesEntity datas = new NodeDevicesEntity();
		// 设备类型
		String deviceType;
		// 对象id
		String objid;
		try {
			// 变电站集合
			List<DeviceEntity<EcSubstationEntityVo>> station = new ArrayList<DeviceEntity<EcSubstationEntityVo>>();
			// 箱式配电站
			List<DeviceEntity<EcXsbdzEntityVo>> xsbdz = new ArrayList<DeviceEntity<EcXsbdzEntityVo>>();
			// 开关站
			List<DeviceEntity<EcKgzEntityVo>> kgz = new ArrayList<DeviceEntity<EcKgzEntityVo>>();
			// 低压配电箱
			List<DeviceEntity<EcDypdxEntityVo>> dypdx = new ArrayList<DeviceEntity<EcDypdxEntityVo>>();
			// 配电房集合
			List<DeviceEntity<EcDistributionRoomEntityVo>> distribution_room = new ArrayList<DeviceEntity<EcDistributionRoomEntityVo>>();
			// 变压器集合
			List<DeviceEntity<EcTransformerEntityVo>> tranformer = new ArrayList<DeviceEntity<EcTransformerEntityVo>>();
			// 杆塔集合
			List<DeviceEntity<EcTowerEntityVo>> tower = new ArrayList<DeviceEntity<EcTowerEntityVo>>();
			// 工井集合
			List<DeviceEntity<EcWorkWellEntityVo>> well = new ArrayList<DeviceEntity<EcWorkWellEntityVo>>();
			// 根据节点id查询
			EcNodeEntity nodeEntity = this.getNodeDetails(nodeId);
			if (nodeEntity != null) {
				// 节点设备关联表
				EcNodeDeviceEntity queryEntity = new EcNodeDeviceEntity();
				queryEntity.setOid(nodeEntity.getOid());
				List<EcNodeDeviceEntity> nodeDeviceEntities = dbUtil.excuteQuery(EcNodeDeviceEntity.class, queryEntity);
				// 获取设备
				if (nodeDeviceEntities != null && nodeDeviceEntities.size() > 0) {
					for (EcNodeDeviceEntity ecNodeDeviceEntity : nodeDeviceEntities) {
						deviceType = ecNodeDeviceEntity.getDeviceType();
						objid = ecNodeDeviceEntity.getDevObjId();
						if (ResourceEnum.BDZ.getValue().equals(deviceType)) {// 变电站
							DeviceEntity<EcSubstationEntityVo> stationEntity = deviceCenter.getStation(objid);
							if (stationEntity != null) {
								station.add(stationEntity);
							}
						} else if (ResourceEnum.PDS.getValue().equals(deviceType)) { // 配电房
							DeviceEntity<EcDistributionRoomEntityVo> distributionRoomEntity = deviceCenter
									.getDistributionRoom(objid);
							if (distributionRoomEntity != null) {
								distribution_room.add(distributionRoomEntity);
							}
						} else if (ResourceEnum.BYQ.getValue().equals(deviceType)) {// 变压器
							DeviceEntity<EcTransformerEntityVo> tranDeviceEntity = deviceCenter.getTransformer(objid);
							if (tranDeviceEntity != null) {
								tranformer.add(tranDeviceEntity);
							}
						} else if (ResourceEnum.GT.getValue().equals(deviceType)) {// 杆塔
							DeviceEntity<EcTowerEntityVo> towDeviceEntity = deviceCenter.getTower(objid);
							if (towDeviceEntity != null) {
								tower.add(towDeviceEntity);
							}
						} else if (ResourceEnum.DLJ.getValue().equals(deviceType)) {// 工井
							DeviceEntity<EcWorkWellEntityVo> workwDeviceEntity = deviceCenter.getWorkWell(objid);
							if (workwDeviceEntity != null) {
								well.add(workwDeviceEntity);
							}
						} else if (ResourceEnum.XSBDZ.getValue().equals(deviceType)) {// 箱式变电站
							DeviceEntity<EcXsbdzEntityVo> xsbdzDeviceEntity = deviceCenter.getXsbdz(objid);
							if (xsbdzDeviceEntity != null) {
								xsbdz.add(xsbdzDeviceEntity);
							}
						} else if (ResourceEnum.KGZ.getValue().equals(deviceType)) {// 开关站
							DeviceEntity<EcKgzEntityVo> kgzDeviceEntity = deviceCenter.getKgz(objid);
							if (kgzDeviceEntity != null) {
								kgz.add(kgzDeviceEntity);
							}
						} else if (ResourceEnum.DYPDX.getValue().equals(deviceType)) {// 低压配电箱
							DeviceEntity<EcDypdxEntityVo> dypdxDeviceEntity = deviceCenter.getDypdx(objid);
							if (dypdxDeviceEntity != null) {
								dypdx.add(dypdxDeviceEntity);
							}
						}
					}
				}
			}
			if (null != datas) {
				datas.setStation(station);
				datas.setDistribution_room(distribution_room);
				datas.setTranformer(tranformer);
				datas.setTower(tower);
				datas.setWell(well);
				datas.setXsbdz(xsbdz);
				datas.setKgz(kgz);
				datas.setDypdx(dypdx);
				return datas;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 5、获得穿过节点的所有线路（作用是获得标识器标识的所有线路信息）
	 * 
	 * @param nodeId
	 */
	public List<EcLineEntity> getAcrossLineInNode(String nodeId) {
		try {
			EcNodeEntity nodeEntity = this.getNodeDetails(nodeId);
			if (nodeEntity == null) {
				return null;
			}
			String querySQL = "select * from ec_line where EC_LINE_ID in(select EC_LINE_ID from ec_line_nodes where OID = '"
					+ nodeEntity.getOid() + "')";
			List<EcLineEntity> list = dbUtil.excuteQuery(EcLineEntity.class, querySQL);
			if (list != null && !list.isEmpty()) {
				return list;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取某条线在某个节点的线路节点
	 * 
	 * @param oid
	 * @param lineId
	 * @return
	 */
	public EcLineNodesEntity getLineNode(String oid, String lineId) {
		EcLineNodesEntity lineNodeIndex = LineInfoCenter.getLineNodeIndex(lineId, oid);
		if (null != lineNodeIndex) {
			return lineNodeIndex;
		}
		try {
			EcLineNodesEntity queryEntity = new EcLineNodesEntity();
			queryEntity.setEcLineId(lineId);
			queryEntity.setOid(oid);
			List<EcLineNodesEntity> lineNodeEntity = dbUtil.excuteQuery(EcLineNodesEntity.class, queryEntity);
			if (lineNodeEntity == null || lineNodeEntity.isEmpty()) {
				return null;
			}
			return lineNodeEntity.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取某条线在某个节点的线路节点
	 * 
	 * @param oid
	 * @param lineId
	 * @return
	 */
	public EcChannelNodesEntity getChannelNode(String oid, String channelId) {
		try {
			EcChannelNodesEntity queryEntity = new EcChannelNodesEntity();
			queryEntity.setEcChannelId(channelId);
			queryEntity.setOid(oid);
			List<EcChannelNodesEntity> channelNodeEntity = dbUtil.excuteQuery(EcChannelNodesEntity.class, queryEntity);
			if (channelNodeEntity == null || channelNodeEntity.isEmpty()) {
				return null;
			}
			return channelNodeEntity.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据oid获取线路节点列表
	 * 
	 * @param oid
	 */
	public List<EcLineNodesEntity> getLineNodeListByOid(String oid) {
		try {
			EcLineNodesEntity queryEntity = new EcLineNodesEntity();
			queryEntity.setOid(oid);
			List<EcLineNodesEntity> lineNodeEntity = dbUtil.excuteQuery(EcLineNodesEntity.class, queryEntity);
			if (lineNodeEntity != null && !lineNodeEntity.isEmpty()) {
				return lineNodeEntity;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据机构id获取节点列表
	 * 
	 * @param orgid
	 * @return
	 */
	public List<EcNodeEntity> getNodeListByOrgid(String orgid) {
		EcNodeEntity queryEntity = new EcNodeEntity();
		queryEntity.setOrgid(orgid);
		try {
			List<EcNodeEntity> list = dbUtil.excuteQuery(EcNodeEntity.class, queryEntity);
			if (list != null && !list.isEmpty()) {
				return list;
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 根据项目id获取节点列表
	 * 
	 * @param prjid
	 * @return
	 */
	public List<EcNodeEntity> getNodeListByPrjid(String prjid) {
		EcNodeEntity queryEntity = new EcNodeEntity();
		queryEntity.setPrjid(prjid);
		try {
			List<EcNodeEntity> list = dbUtil.excuteQuery(EcNodeEntity.class, queryEntity);
			if (list != null && !list.isEmpty()) {
				return list;
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
