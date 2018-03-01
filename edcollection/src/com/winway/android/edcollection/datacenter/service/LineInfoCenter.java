package com.winway.android.edcollection.datacenter.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.LineDetailsEntity;
import com.winway.android.edcollection.datacenter.entity.LineDevicesEntity;
import com.winway.android.edcollection.datacenter.loader.LoaderConfig;
import com.winway.android.edcollection.project.entity.EcLineDeviceEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;

import android.content.Context;

/**
 * 线路信息中心
 * 
 * @author lyh
 * @version 创建时间：2017年1月5日 上午10:57:49
 * 
 */
public class LineInfoCenter {
	BaseDBUtil dbUtil;
	// 设备中心
	DeviceCenter deviceCenter;

	public LineInfoCenter(Context context, String dbUrl) {
		super();
		if (dbUrl == null) {
			dbUrl = LoaderConfig.dbUrl;
		}
		dbUtil = new BaseDBUtil(context, new File(dbUrl));
		deviceCenter = new DeviceCenter(dbUtil);
		init();
	}

	public LineInfoCenter(BaseDBUtil dbUtil) {
		this.dbUtil = dbUtil;
		deviceCenter = new DeviceCenter(dbUtil);
		init();
	}

	public LineInfoCenter(BaseDBUtil dbUtil, DeviceCenter deviceCenter) {
		super();
		this.dbUtil = dbUtil;
		this.deviceCenter = deviceCenter;
		init();
	}

	void init() {
		lineNodeIndexMap.clear();
	}

	/**
	 * 1、获得具体的线路信息
	 * 
	 * @param lineId=线路ID值
	 * @param containsNodes=限定返回的数据中是否要包含节点数据，
	 *            true表示返回的数据中要包含节点数据，false则表示返回的数据中不包含节点数据
	 */
	public LineDetailsEntity getLineDetails(String lineId, boolean containsNodes) {
		EcLineEntity data;
		try {
			LineDetailsEntity datas = new LineDetailsEntity();
			data = dbUtil.getById(EcLineEntity.class, lineId);
			if (data != null) {
				datas.setData(data);
			}
			if (containsNodes == true) {
				List<EcNodeEntity> nodes = getLineNodeList(lineId);
				if (nodes != null && nodes.size() > 0) {
					datas.setNodes(nodes);
				}
			}
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 线路节点与
	public static HashMap<String, EcLineNodesEntity> lineNodeIndexMap = new HashMap<>();

	private static void putLineNodeIndex(String lineId, String nodeId, EcLineNodesEntity index) {
		lineNodeIndexMap.put(lineId + nodeId, index);
	}

	public static EcLineNodesEntity getLineNodeIndex(String lineId, String nodeId) {
		return lineNodeIndexMap.get(lineId + nodeId);
	}

	/**
	 * 2、获得线路线路所有节点
	 * 
	 * @param lineId=线路ID值
	 * @return
	 */
	public List<EcNodeEntity> getLineNodeList(String lineId) {
		List<EcLineNodesEntity> lineNodesEntities;
		try {
			List<EcNodeEntity> nodeEntities = new ArrayList<EcNodeEntity>();
			// 根据线路id拿到所有线路节点关联的数据
			String expr = "EC_LINE_ID='" + lineId + "' order by N_INDEX";
			lineNodesEntities = dbUtil.excuteWhereQuery(EcLineNodesEntity.class, expr);
			if (lineNodesEntities != null && lineNodesEntities.size() > 0) {
				// 循环遍历拿到oid
				for (EcLineNodesEntity ecNodeEntity : lineNodesEntities) {
					EcNodeEntity nodeEntity = dbUtil.getById(EcNodeEntity.class, ecNodeEntity.getOid());
					if (null == nodeEntity) {
						continue;
					}
					nodeEntities.add(nodeEntity);
					// 缓存
					putLineNodeIndex(lineId, nodeEntity.getOid(), ecNodeEntity);
				}
			}
			return nodeEntities;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 3、获得线路附属的所有设备
	 * 
	 * @param lineId=线路ID
	 */
	public LineDevicesEntity getLineDevice(String lineId) {
		String deviceType; // 设备类型
		String devObjId; // 对象id
		try {
			LineDevicesEntity devicesEntity = new LineDevicesEntity();
			List<DeviceEntity<EcLineLabelEntityVo>> lineLabelEntities = new ArrayList<DeviceEntity<EcLineLabelEntityVo>>();// 标签
			List<DeviceEntity<EcDistBoxEntityVo>> distBoxEntities = new ArrayList<DeviceEntity<EcDistBoxEntityVo>>();// 分接箱
			List<DeviceEntity<EcMiddleJointEntityVo>> middleJointEntities = new ArrayList<DeviceEntity<EcMiddleJointEntityVo>>(); // 电缆中间接头
			List<DeviceEntity<EcHwgEntityVo>> hwgEntities = new ArrayList<DeviceEntity<EcHwgEntityVo>>(); // 电缆中间接头
			List<DeviceEntity<EcDlfzxEntityVo>> dlfzxEntities = new ArrayList<DeviceEntity<EcDlfzxEntityVo>>(); // 电缆中间接头
			List<DeviceEntity<EcDydlfzxEntityVo>> dydlfzxEntities = new ArrayList<DeviceEntity<EcDydlfzxEntityVo>>(); // 电缆中间接头
			// 根据线路id拿到当前线路数据
			EcLineEntity lineEntity = dbUtil.getById(EcLineEntity.class, lineId);
			if (lineEntity == null) {
				return null;
			}
			// 拿到线路编号
			String lineNo = lineEntity.getLineNo();
			// 根据线路编号拿到所有线路附属设备
			EcLineDeviceEntity lineDeviceQueryEntity = new EcLineDeviceEntity();
			/***** lineDeviceQueryEntity先通过线路ID去查找，如果找不到，就根据线路编号去查找 *****/
			lineDeviceQueryEntity.setLineNo(lineEntity.getEcLineId());
			List<EcLineDeviceEntity> lineDeviceEntities = dbUtil.excuteQuery(EcLineDeviceEntity.class,
					lineDeviceQueryEntity);
			if (null == lineDeviceEntities || lineDeviceEntities.isEmpty()) {
				lineDeviceQueryEntity.setLineNo(lineNo);
				lineDeviceEntities = dbUtil.excuteQuery(EcLineDeviceEntity.class, lineDeviceQueryEntity);
			}
			/***** lineDeviceQueryEntity先通过线路ID去查找，如果找不到，就根据线路编号去查找 *****/
			if (lineDeviceEntities != null && lineDeviceEntities.size() > 0) {
				for (EcLineDeviceEntity ecLineDeviceEntity : lineDeviceEntities) {
					// 拿到设备类型
					deviceType =ecLineDeviceEntity.getDeviceType();
					// 拿到对象ID
					devObjId = ecLineDeviceEntity.getDevObjId();
					// 判断设备类型，1表示电子标签，2表示分接箱，3表示电缆中间接头
					if (ResourceEnum.DZBQ.getValue().equals(deviceType)) {// 标签
						DeviceEntity<EcLineLabelEntityVo> label = deviceCenter.getLabel(devObjId);
						if (null != label) {
							lineLabelEntities.add(label);
						}
					} else if (ResourceEnum.ZJJT.getValue().equals(deviceType)) {// 中间接头
						DeviceEntity<EcMiddleJointEntityVo> middle = deviceCenter.getMiddle(devObjId);
						if (null != middle) {
							middleJointEntities.add(middle);
						}
					} else if (ResourceEnum.HWG.getValue().equals(deviceType)) {// 环网柜
						DeviceEntity<EcHwgEntityVo> hwg = deviceCenter.getHwg(devObjId);
						if (null != hwg) {
							hwgEntities.add(hwg);
						}
					} else if (ResourceEnum.DLFZX.getValue().equals(deviceType)) { // 电缆分支箱
						DeviceEntity<EcDlfzxEntityVo> dlfzx = deviceCenter.getDlfzx(devObjId);
						if (null != dlfzx) {
							dlfzxEntities.add(dlfzx);
						}
					} else if (ResourceEnum.DYDLFZX.getValue().equals(deviceType)) { // 低压电缆分支箱
						DeviceEntity<EcDydlfzxEntityVo> dydlfzx = deviceCenter.getDydlfzx(devObjId);
						if (null != dydlfzx) {
							dydlfzxEntities.add(dydlfzx);
						}
					}
				}
			}
			devicesEntity.setBoxList(distBoxEntities);
			devicesEntity.setLineLabelEntityVoList(lineLabelEntities);
			devicesEntity.setMiddleJointEntityVoList(middleJointEntities);
			devicesEntity.setHwgEntityVoList(hwgEntities);
			devicesEntity.setDlfzxEntityVoList(dlfzxEntities);
			devicesEntity.setDydlfzxEntityVoList(dydlfzxEntities);
			return devicesEntity;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 1、获得具体的线路信息
	 * 
	 * @param lineNo=线路编号
	 * @param containsNodes=限定返回的数据中是否要包含节点数据，
	 *            true表示返回的数据中要包含节点数据，false则表示返回的数据中不包含节点数据
	 */
	public LineDetailsEntity getLineDetailsByLineNo(String lineNo, boolean containsNodes) {
		try {
			EcLineEntity queryEntity = new EcLineEntity();
			queryEntity.setLineNo(lineNo);
			queryEntity = dbUtil.excuteQuery(EcLineEntity.class, queryEntity).get(0);
			if (queryEntity == null) {
				return null;
			}
			LineDetailsEntity datas = new LineDetailsEntity();
			datas.setData(queryEntity);
			if (containsNodes == true) {
				List<EcNodeEntity> nodes = getLineNodeList(lineNo);
				if (nodes != null && nodes.size() > 0) {
					datas.setNodes(nodes);
				}
			}
			return datas;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 按机构id获取线路列表
	 * 
	 * @param orgid
	 * @return
	 */
	public List<EcLineEntity> getLineListByOrgid(String orgid) {
		// TODO Auto-generated method stub
		try {
			EcLineEntity queryEntity = new EcLineEntity();
			queryEntity.setOrgid(orgid);
			List<EcLineEntity> list = dbUtil.excuteQuery(EcLineEntity.class, queryEntity);
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
