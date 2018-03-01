package com.winway.android.edcollection.datacenter.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.util.ResourceDeviceUtil;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.loader.LoaderConfig;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcLineDeviceEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcVRRefEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
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
 * 设备中心
 * 
 * @author lyh
 * @version 创建时间：2017年1月9日 上午10:37:27
 * 
 */
public class DeviceCenter {
	BaseDBUtil dbUtil;

	public DeviceCenter(Context context, String dbUrl) {
		super();
		if (dbUrl == null) {
			dbUrl = LoaderConfig.dbUrl;
		}
		dbUtil = new BaseDBUtil(context, new File(dbUrl));
	}

	public DeviceCenter(BaseDBUtil dbUtil) {
		super();
		this.dbUtil = dbUtil;
	}

	/**
	 * 通过线路设备的对象ID获得线路设备的路径点
	 * 
	 * @param devObjId
	 * @return
	 * @throws DbException
	 */
	public EcNodeEntity getLineDeviceNodeEntity(String devObjId) throws DbException {
		EcLineDeviceEntity queryEntity = new EcLineDeviceEntity();
		queryEntity.setDevObjId(devObjId);
		List<EcLineDeviceEntity> lineDeviceList = dbUtil.excuteQuery(EcLineDeviceEntity.class, queryEntity);
		if (null == lineDeviceList || lineDeviceList.isEmpty()) {
			return null;
		}
		return dbUtil.getById(EcNodeEntity.class, lineDeviceList.get(0).getOid());
	}

	/**
	 * 1、获得标签详细信息
	 * 
	 * @param labelId
	 */
	public DeviceEntity<EcLineLabelEntityVo> getLabel(String labelId) {
		try {
			// 根据标签id查询
			EcLineLabelEntityVo data = dbUtil.getById(EcLineLabelEntityVo.class, labelId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcLineLabelEntityVo> datas = new DeviceEntity<EcLineLabelEntityVo>();
			datas.setData(data);
			datas.setNode(getLineDeviceNodeEntity(data.getObjId()));// 此方法目前只是获得线路节点对应的坐标
			if (null == datas.getNode()) {
				// 说明不是线路设备，而是其他设备
				Object device = ResourceDeviceUtil.getDevice(data.getDevObjId(), data.getDeviceType() + "", dbUtil);
				EcNodeEntity nodeEntity = ResourceDeviceUtil.getEcNodeEntity(
						ResourceDeviceUtil.getDevicePrimaryKey(device), data.getDeviceType() + "", dbUtil, this);
				datas.setNode(nodeEntity);
			}
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 2、获得分接箱详细信息
	 * 
	 * @param boxId
	 */
	public DeviceEntity<EcDistBoxEntityVo> getDistBox(String boxId) {
		try {
			EcDistBoxEntityVo data = dbUtil.getById(EcDistBoxEntityVo.class, boxId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcDistBoxEntityVo> datas = new DeviceEntity<EcDistBoxEntityVo>();
			datas.setData(data);
			datas.setNode(getLineDeviceNodeEntity(data.getEcDistBoxId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 3、获得中间头详细信息
	 * 
	 * @param middleId
	 * @return
	 */
	public DeviceEntity<EcMiddleJointEntityVo> getMiddle(String middleId) {
		try {
			EcMiddleJointEntityVo data = dbUtil.getById(EcMiddleJointEntityVo.class, middleId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcMiddleJointEntityVo> datas = new DeviceEntity<EcMiddleJointEntityVo>();
			datas.setData(data);
			datas.setNode(getLineDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得环网柜详细信息
	 * 
	 * @param objId
	 * @return
	 */
	public DeviceEntity<EcHwgEntityVo> getHwg(String objId) {
		try {
			// 根据标签id查询
			EcHwgEntityVo data = dbUtil.getById(EcHwgEntityVo.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcHwgEntityVo> datas = new DeviceEntity<EcHwgEntityVo>();
			datas.setData(data);
			datas.setNode(getLineDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得电缆分支箱详细信息
	 * 
	 * @param objId
	 * @return
	 */
	public DeviceEntity<EcDlfzxEntityVo> getDlfzx(String objId) {
		try {
			// 根据标签id查询
			EcDlfzxEntityVo data = dbUtil.getById(EcDlfzxEntityVo.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcDlfzxEntityVo> datas = new DeviceEntity<EcDlfzxEntityVo>();
			datas.setData(data);
			datas.setNode(getLineDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得低压电缆分支箱详细信息
	 * 
	 * @param objId
	 * @return
	 */
	public DeviceEntity<EcDydlfzxEntityVo> getDydlfzx(String objId) {
		try {
			// 根据标签id查询
			EcDydlfzxEntityVo data = dbUtil.getById(EcDydlfzxEntityVo.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcDydlfzxEntityVo> datas = new DeviceEntity<EcDydlfzxEntityVo>();
			datas.setData(data);
			datas.setNode(getLineDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*********************************** 与节点附属设备关联的设备 ****************************************/

	/**
	 * 4、获得工井详细信息
	 * 
	 * @param wellId
	 * @return
	 */
	public DeviceEntity<EcWorkWellEntityVo> getWorkWell(String wellId) {
		try {
			EcWorkWellEntityVo data = dbUtil.getById(EcWorkWellEntityVo.class, wellId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcWorkWellEntityVo> datas = new DeviceEntity<EcWorkWellEntityVo>();
			datas.setData(data);
			datas.setNode(getNodeDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 5、获得配电房详细信息
	 * 
	 * @param roomId
	 */
	public DeviceEntity<EcDistributionRoomEntityVo> getDistributionRoom(String roomId) {
		try {
			EcDistributionRoomEntityVo data = dbUtil.getById(EcDistributionRoomEntityVo.class, roomId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcDistributionRoomEntityVo> datas = new DeviceEntity<EcDistributionRoomEntityVo>();
			datas.setData(data);
			datas.setNode(getNodeDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 6、获得变电站详细信息
	 * 
	 * @param stationId
	 */
	public DeviceEntity<EcSubstationEntityVo> getStation(String stationId) {
		try {
			EcSubstationEntityVo data = dbUtil.getById(EcSubstationEntityVo.class, stationId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcSubstationEntityVo> datas = new DeviceEntity<EcSubstationEntityVo>();
			datas.setData(data);
			datas.setNode(getNodeDeviceNodeEntity(data.getEcSubstationId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 7、获得变压器详细信息
	 * 
	 * @param transformerId
	 */
	public DeviceEntity<EcTransformerEntityVo> getTransformer(String transformerId) {
		try {
			EcTransformerEntityVo data = dbUtil.getById(EcTransformerEntityVo.class, transformerId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcTransformerEntityVo> datas = new DeviceEntity<EcTransformerEntityVo>();
			datas.setData(data);
			datas.setNode(getNodeDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 8、获得杆塔详细信息
	 * 
	 * @param towerId
	 */
	public DeviceEntity<EcTowerEntityVo> getTower(String towerId) {
		try {
			EcTowerEntityVo data = dbUtil.getById(EcTowerEntityVo.class, towerId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcTowerEntityVo> datas = new DeviceEntity<EcTowerEntityVo>();
			datas.setData(data);
			datas.setNode(getNodeDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 9、获得箱式变电站详细信息
	 * 
	 * @param xsbdzId
	 */
	public DeviceEntity<EcXsbdzEntityVo> getXsbdz(String xsbdzId) {
		try {
			EcXsbdzEntityVo data = dbUtil.getById(EcXsbdzEntityVo.class, xsbdzId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcXsbdzEntityVo> datas = new DeviceEntity<EcXsbdzEntityVo>();
			datas.setData(data);
			datas.setNode(getNodeDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 10、获得开关站详细信息
	 * 
	 * @param kgzId
	 */
	public DeviceEntity<EcKgzEntityVo> getKgz(String kgzId) {
		try {
			EcKgzEntityVo data = dbUtil.getById(EcKgzEntityVo.class, kgzId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcKgzEntityVo> datas = new DeviceEntity<EcKgzEntityVo>();
			datas.setData(data);
			datas.setNode(getNodeDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 11、获得低压配电箱详细信息
	 * 
	 * @param dypdxId
	 */
	public DeviceEntity<EcDypdxEntityVo> getDypdx(String dypdxId) {
		try {
			EcDypdxEntityVo data = dbUtil.getById(EcDypdxEntityVo.class, dypdxId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcDypdxEntityVo> datas = new DeviceEntity<EcDypdxEntityVo>();
			datas.setData(data);
			datas.setNode(getNodeDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过路径点设备的对象ID获得设备路径节点
	 * 
	 * @param devObjID
	 * @return
	 * @throws DbException
	 */
	public EcNodeEntity getNodeDeviceNodeEntity(String devObjID) throws DbException {
		EcNodeDeviceEntity queryEntity = new EcNodeDeviceEntity();
		queryEntity.setDevObjId(devObjID);
		List<EcNodeDeviceEntity> nodeDeviceList = dbUtil.excuteQuery(EcNodeDeviceEntity.class, queryEntity);
		if (null == nodeDeviceList || nodeDeviceList.isEmpty()) {
			return null;
		}
		return dbUtil.getById(EcNodeEntity.class, nodeDeviceList.get(0).getOid());
	}

	/**
	 * 获得线路附属设备关联的线路信息
	 * 
	 * @param deviceId
	 *            线路附属设备的主键值
	 * @return
	 * @throws DbException
	 */
	public EcLineEntity getLineDeviceTargetLine(String deviceId) {
		try {
			EcLineDeviceEntity queryDevice = new EcLineDeviceEntity();
			queryDevice.setDevObjId(deviceId);
			List<EcLineDeviceEntity> deviceList = dbUtil.excuteQuery(EcLineDeviceEntity.class, queryDevice);
			if (null == deviceList || deviceList.isEmpty()) {
				return null;
			}
			// 根据线路关联节点找线路，如果找到则返回线路
			EcLineNodesEntity lineNode = dbUtil.getById(EcLineNodesEntity.class, deviceList.get(0).getEcLineNodesId());
			if (lineNode != null) {
				return dbUtil.getById(EcLineEntity.class, lineNode.getEcLineId());
			}
			// 根据线编号点找线路，如果找到则返回线路
			EcLineEntity queryLine = new EcLineEntity();
			queryLine.setLineNo(deviceList.get(0).getLineNo());
			List<EcLineEntity> lineList = dbUtil.excuteQuery(EcLineEntity.class, queryLine);
			if (null != lineList && !lineList.isEmpty()) {
				return lineList.get(0);
			}
			// 根据线路ID找线路，如果找到则返回线路
			return dbUtil.getById(EcLineEntity.class, deviceList.get(0).getLineNo());
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取井盖
	 * @param wellId  工井主键
	 * @param needNode  是否要包含路径点数据
	 * @return
	 * @throws DbException
	 */
	public List<DeviceEntity<EcWorkWellCoverEntity>> getWellCoverList(String wellId, boolean needNode) {
		EcWorkWellCoverEntity queryEntity = new EcWorkWellCoverEntity();
		queryEntity.setSsgj(wellId);
		List<EcWorkWellCoverEntity> coverList = null;
		try {
			coverList = dbUtil.excuteQuery(EcWorkWellCoverEntity.class, queryEntity);
		} catch (DbException e) {
			e.printStackTrace();
		}
		if (coverList == null || coverList.isEmpty()) {
			return null;
		}
		List<DeviceEntity<EcWorkWellCoverEntity>> list = new ArrayList<>();
		EcNodeEntity nodeEntity = null;
		DeviceEntity<EcWorkWellEntityVo> workWell = null;
		if (needNode) {
			workWell = getWorkWell(wellId);
			if (null != workWell) {
				nodeEntity = workWell.getNode();
			}
		}
		for (EcWorkWellCoverEntity cover : coverList) {
			DeviceEntity<EcWorkWellCoverEntity> device = new DeviceEntity<>();
			device.setData(cover);
			device.setNode(nodeEntity);
			list.add(device);
		}
		return list;
	}
	
	/**
	 * 根据路径点oid拿到全景列表
	 * 
	 * @param oid
	 */
	public List<EcVRRefEntity> getPanoramicByNodeOid(String nodeOid) {
		List<EcVRRefEntity> list = null;
		try {
			EcVRRefEntity ecVRRefEntity = new EcVRRefEntity();
			ecVRRefEntity.setOid(nodeOid);
			list = dbUtil.excuteQuery(EcVRRefEntity.class, ecVRRefEntity);
			if (list != null && !list.isEmpty()) {
				return list;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据设备主键id拿到全景列表
	 * 
	 * @param objId
	 * @return
	 */
	public List<EcVRRefEntity> getPanoramicByDeviceId(String objId) {
		List<EcVRRefEntity> list = null;
		try {
			EcNodeDeviceEntity nodeDeviceEntity = new EcNodeDeviceEntity();
			nodeDeviceEntity.setDevObjId(objId);
			List<EcNodeDeviceEntity> deviceList = dbUtil.excuteQuery(EcNodeDeviceEntity.class, nodeDeviceEntity);
			if (deviceList == null || deviceList.isEmpty()) {
				return null;
			}
			EcVRRefEntity ecVRRefEntity = new EcVRRefEntity();
			for (EcNodeDeviceEntity ecNodeDeviceEntity : deviceList) {
				ecVRRefEntity.setOid(ecNodeDeviceEntity.getOid());
			}
			list = dbUtil.excuteQuery(EcVRRefEntity.class, ecVRRefEntity);
			if (list != null && !list.isEmpty()) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/*****************************************通道设备开始分隔线**************************************************/

	/**
	 * 获得顶管详细信息
	 * 
	 * @param objId
	 */
	public DeviceEntity<EcChannelDgEntity> getDg(String objId) {
		try {
			EcChannelDgEntity data = dbUtil.getById(EcChannelDgEntity.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcChannelDgEntity> datas = new DeviceEntity<EcChannelDgEntity>();
			datas.setData(data);
			datas.setNode(getChannelDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获得沟道详细信息
	 * 
	 * @param objId
	 */
	public DeviceEntity<EcChannelDlgEntity> getGd(String objId) {
		try {
			EcChannelDlgEntity data = dbUtil.getById(EcChannelDlgEntity.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcChannelDlgEntity> datas = new DeviceEntity<EcChannelDlgEntity>();
			datas.setData(data);
			datas.setNode(getChannelDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获得排管详细信息
	 * 
	 * @param objId
	 */
	public DeviceEntity<EcChannelDlgdEntity> getPg(String objId) {
		try {
			EcChannelDlgdEntity data = dbUtil.getById(EcChannelDlgdEntity.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcChannelDlgdEntity> datas = new DeviceEntity<EcChannelDlgdEntity>();
			datas.setData(data);
			datas.setNode(getChannelDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获得桥架详细信息
	 * 
	 * @param objId
	 */
	public DeviceEntity<EcChannelDlqEntity> getQj(String objId) {
		try {
			EcChannelDlqEntity data = dbUtil.getById(EcChannelDlqEntity.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcChannelDlqEntity> datas = new DeviceEntity<EcChannelDlqEntity>();
			datas.setData(data);
			datas.setNode(getChannelDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获得隧道详细信息
	 * 
	 * @param objId
	 */
	public DeviceEntity<EcChannelDlsdEntity> getSd(String objId) {
		try {
			EcChannelDlsdEntity data = dbUtil.getById(EcChannelDlsdEntity.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcChannelDlsdEntity> datas = new DeviceEntity<EcChannelDlsdEntity>();
			datas.setData(data);
			datas.setNode(getChannelDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获得直埋详细信息
	 * 
	 * @param objId
	 */
	public DeviceEntity<EcChannelDlzmEntity> getZm(String objId) {
		try {
			EcChannelDlzmEntity data = dbUtil.getById(EcChannelDlzmEntity.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcChannelDlzmEntity> datas = new DeviceEntity<EcChannelDlzmEntity>();
			datas.setData(data);
			datas.setNode(getChannelDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得电缆槽详细信息
	 * 
	 * @param objId
	 */
	public DeviceEntity<EcChannelDlcEntity> getDlc(String objId) {
		try {
			EcChannelDlcEntity data = dbUtil.getById(EcChannelDlcEntity.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcChannelDlcEntity> datas = new DeviceEntity<EcChannelDlcEntity>();
			datas.setData(data);
			datas.setNode(getChannelDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得架空详细信息
	 * 
	 * @param objId
	 */
	public DeviceEntity<EcChannelJkEntity> getJk(String objId) {
		try {
			EcChannelJkEntity data = dbUtil.getById(EcChannelJkEntity.class, objId);
			if (data == null) {
				return null;
			}
			DeviceEntity<EcChannelJkEntity> datas = new DeviceEntity<EcChannelJkEntity>();
			datas.setData(data);
			datas.setNode(getChannelDeviceNodeEntity(data.getObjId()));
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过通道设备的对象ID获得设备路径节点
	 * 
	 * @param devObjID
	 * @return
	 * @throws DbException
	 */
	public EcNodeEntity getChannelDeviceNodeEntity(String devObjID) throws DbException {
		EcChannelNodesEntity queryEntity = new EcChannelNodesEntity();
		queryEntity.setEcChannelId(devObjID);
		List<EcChannelNodesEntity> channelNodesList = dbUtil.excuteQuery(EcChannelNodesEntity.class, queryEntity);
		if (null == channelNodesList || channelNodesList.isEmpty()) {
			return null;
		}
		return dbUtil.getById(EcNodeEntity.class, channelNodesList.get(0).getOid());
	}	
	
	/*****************************************通道设备结束分隔线**************************************************/
}
