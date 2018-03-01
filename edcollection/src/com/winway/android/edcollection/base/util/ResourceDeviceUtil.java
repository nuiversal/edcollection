package com.winway.android.edcollection.base.util;

import java.util.HashMap;

import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.service.DeviceCenter;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
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
 * 获得设备资源
 * 
 * @author mr-lao
 *
 */
public class ResourceDeviceUtil {
	static final HashMap<String, Class<?>> map = new HashMap<String, Class<?>>();
	static final HashMap<Class<?>, String> map2 = new HashMap<Class<?>, String>();
	static {
		map.put(ResourceEnum.XL.getValue() + "", EcLineEntity.class);
		map.put(ResourceEnum.BDZ.getValue() + "", EcSubstationEntityVo.class);
		map.put(ResourceEnum.KGZ.getValue() + "", EcKgzEntityVo.class);
		map.put(ResourceEnum.PDS.getValue() + "", EcDistributionRoomEntityVo.class);
		map.put("" + ResourceEnum.HWG.getValue(), EcHwgEntityVo.class);
		map.put("" + ResourceEnum.XSBDZ.getValue(), EcXsbdzEntityVo.class);
		map.put("" + ResourceEnum.DLFZX.getValue(), EcDlfzxEntityVo.class);
		map.put("" + ResourceEnum.DYDLFZX.getValue(), EcDydlfzxEntityVo.class);
		map.put("" + ResourceEnum.DYPDX.getValue(), EcDypdxEntityVo.class);
		map.put("" + ResourceEnum.BYQ.getValue(), EcTransformerEntityVo.class);
		map.put("" + ResourceEnum.PG.getValue(), EcChannelDlgdEntity.class);
		map.put("" + ResourceEnum.GD.getValue(), EcChannelDlgEntity.class);
		map.put("" + ResourceEnum.SD.getValue(), EcChannelDlsdEntity.class);
		map.put("" + ResourceEnum.QJ.getValue(), EcChannelDlqEntity.class);
		map.put("" + ResourceEnum.ZM.getValue(), EcChannelDlzmEntity.class);
		map.put("" + ResourceEnum.DLC.getValue(), EcChannelDlcEntity.class);
		map.put("" + ResourceEnum.JKXL.getValue(), EcChannelJkEntity.class);
		map.put("" + ResourceEnum.TLG.getValue(), EcChannelDgEntity.class);
		map.put("" + ResourceEnum.DLJ.getValue(), EcWorkWellEntityVo.class);
		map.put("" + ResourceEnum.JG.getValue(), EcWorkWellCoverEntity.class);
		map.put("" + ResourceEnum.GT.getValue(), EcTowerEntityVo.class);
		map.put("" + ResourceEnum.ZJJT.getValue(), EcMiddleJointEntityVo.class);
		map.put("" + ResourceEnum.DZBZQ.getValue(), EcNodeEntity.class);
		map.put("" + ResourceEnum.DZBQ.getValue(), EcLineLabelEntityVo.class);
		map.put("" + ResourceEnum.TD.getValue(), EcChannelEntity.class);

		map2.put(EcLineEntity.class, ResourceEnum.XL.getValue() + "");
		map2.put(EcSubstationEntityVo.class, ResourceEnum.BDZ.getValue() + "");
		map2.put(EcSubstationEntity.class, ResourceEnum.BDZ.getValue() + "");
		map2.put(EcKgzEntityVo.class, ResourceEnum.KGZ.getValue() + "");
		map2.put(EcKgzEntity.class, ResourceEnum.KGZ.getValue() + "");
		map2.put(EcDistributionRoomEntityVo.class, ResourceEnum.PDS.getValue() + "");
		map2.put(EcDistributionRoomEntity.class, ResourceEnum.PDS.getValue() + "");
		map2.put(EcHwgEntityVo.class, "" + ResourceEnum.HWG.getValue());
		map2.put(EcHwgEntity.class, "" + ResourceEnum.HWG.getValue());
		map2.put(EcXsbdzEntityVo.class, "" + ResourceEnum.XSBDZ.getValue());
		map2.put(EcXsbdzEntity.class, "" + ResourceEnum.XSBDZ.getValue());
		map2.put(EcDlfzxEntityVo.class, "" + ResourceEnum.DLFZX.getValue());
		map2.put(EcDlfzxEntity.class, "" + ResourceEnum.DLFZX.getValue());
		map2.put(EcDydlfzxEntityVo.class, "" + ResourceEnum.DYDLFZX.getValue());
		map2.put(EcDydlfzxEntity.class, "" + ResourceEnum.DYDLFZX.getValue());
		map2.put(EcDypdxEntityVo.class, "" + ResourceEnum.DYPDX.getValue());
		map2.put(EcDypdxEntity.class, "" + ResourceEnum.DYPDX.getValue());
		map2.put(EcTransformerEntityVo.class, "" + ResourceEnum.BYQ.getValue());
		map2.put(EcTransformerEntity.class, "" + ResourceEnum.BYQ.getValue());
		map2.put(EcChannelDlgdEntity.class, "" + ResourceEnum.PG.getValue());
		map2.put(EcChannelDlgEntity.class, "" + ResourceEnum.GD.getValue());
		map2.put(EcChannelDlsdEntity.class, "" + ResourceEnum.SD.getValue());
		map2.put(EcChannelDlqEntity.class, "" + ResourceEnum.QJ.getValue());
		map2.put(EcChannelDlzmEntity.class, "" + ResourceEnum.ZM.getValue());
		map2.put(EcChannelDlcEntity.class, "" + ResourceEnum.DLC.getValue());
		map2.put(EcChannelJkEntity.class, "" + ResourceEnum.JKXL.getValue());
		map2.put(EcChannelDgEntity.class, "" + ResourceEnum.TLG.getValue());
		map2.put(EcWorkWellEntityVo.class, "" + ResourceEnum.DLJ.getValue());
		map2.put(EcWorkWellEntity.class, "" + ResourceEnum.DLJ.getValue());
		map2.put(EcWorkWellCoverEntity.class, "" + ResourceEnum.JG.getValue());
		map2.put(EcTowerEntityVo.class, "" + ResourceEnum.GT.getValue());
		map2.put(EcTowerEntity.class, "" + ResourceEnum.GT.getValue());
		map2.put(EcMiddleJointEntityVo.class, "" + ResourceEnum.ZJJT.getValue());
		map2.put(EcMiddleJointEntity.class, "" + ResourceEnum.ZJJT.getValue());
		map2.put(EcNodeEntity.class, "" + ResourceEnum.DZBZQ.getValue());
		map2.put(EcLineLabelEntityVo.class, "" + ResourceEnum.DZBQ.getValue());
		map2.put(EcLineLabelEntity.class, "" + ResourceEnum.DZBQ.getValue());
		map2.put(EcChannelEntity.class, "" + ResourceEnum.TD.getValue());

	}

	/**
	 * 获取设备的资源编码
	 * 
	 * @param deviceClass
	 * @return
	 */
	public static String getDeviceCode(Class<?> deviceClass) {
		return map2.get(deviceClass);
	}

	/**
	 * 获取设备资源
	 * 
	 * @param deviceID
	 * @param deviceCode
	 * @param dbUtil
	 * @return
	 */
	public static <T> T getDevice(String deviceID, String deviceCode, BaseDBUtil dbUtil) {
		try {
			@SuppressWarnings("unchecked")
			Class<T> cls = (Class<T>) map.get(deviceCode);
			return dbUtil.getById(cls, deviceID);
		} catch (Exception e) {

		}
		return null;
	}

	public static Object getDeviceObj(String deviceID, String deviceCode, BaseDBUtil dbUtil) {
		try {
			Class<?> cls = (Class<?>) map.get(deviceCode);
			return dbUtil.getById(cls, deviceID);
		} catch (Exception e) {

		}
		return null;
	}

	public static Object getDeviceObj(String deviceID, String deviceCode, BaseBll<?> baseBLL) {
		try {
			Class<?> cls = (Class<?>) map.get(deviceCode);
			return baseBLL.findById(cls, deviceID);
		} catch (Exception e) {

		}
		return null;
	}

	public static EcNodeEntity getEcNodeEntity(String deviceID, String deviceCode, BaseDBUtil dbUtil,
			DataCenterImpl dataCenter) {
		return getEcNodeEntity(deviceID, deviceCode, dbUtil, dataCenter.getDeviceCenter());
	}

	public static EcNodeEntity getEcNodeEntity(String deviceID, String deviceCode, BaseDBUtil dbUtil,
			DeviceCenter deviceCenter) {
		Object deviceObj = getDevice(deviceID, deviceCode, dbUtil);
		if (deviceObj == null) {
			return null;
		}
		if (deviceObj instanceof EcWorkWellEntity) {
			// 电缆井
			DeviceEntity<EcWorkWellEntityVo> workWell = deviceCenter
					.getWorkWell(((EcWorkWellEntity) deviceObj).getObjId());
			if (null != workWell) {
				return workWell.getNode();
			}
		} else if (deviceObj instanceof EcLineEntity) {
			// 电缆
			return null;
		} else if (deviceObj instanceof EcSubstationEntity) {
			// 变电站
			DeviceEntity<EcSubstationEntityVo> station = deviceCenter
					.getStation(((EcSubstationEntity) deviceObj).getEcSubstationId());
			if (null != station) {
				return station.getNode();
			}
		} else if (deviceObj instanceof EcKgzEntity) {
			//
			DeviceEntity<EcKgzEntityVo> kgz = deviceCenter.getKgz(((EcKgzEntity) deviceObj).getObjId());
			if (null != kgz) {
				return kgz.getNode();
			}
		} else if (deviceObj instanceof EcDistributionRoomEntity) {
			// 配电房
			DeviceEntity<EcDistributionRoomEntityVo> distributionRoom = deviceCenter
					.getDistributionRoom(((EcDistributionRoomEntity) deviceObj).getObjId());
			if (null != distributionRoom) {
				return distributionRoom.getNode();
			}
		} else if (deviceObj instanceof EcHwgEntity) {
			// 环网柜
			DeviceEntity<EcHwgEntityVo> hwg = deviceCenter.getHwg(((EcHwgEntity) deviceObj).getObjId());
			if (null != hwg) {
				return hwg.getNode();
			}
		} else if (deviceObj instanceof EcXsbdzEntity) {
			// 箱式变电
			DeviceEntity<EcXsbdzEntityVo> xsbdz = deviceCenter.getXsbdz(((EcXsbdzEntity) deviceObj).getObjId());
			if (null != xsbdz) {
				return xsbdz.getNode();
			}
		} else if (deviceObj instanceof EcDlfzxEntity) {
			// 电缆分接箱
			DeviceEntity<EcDlfzxEntityVo> dlfzx = deviceCenter.getDlfzx(((EcDlfzxEntity) deviceObj).getObjId());
			if (null != dlfzx) {
				return dlfzx.getNode();
			}
		} else if (deviceObj instanceof EcDydlfzxEntity) {
			// 低压电缆分接箱
			DeviceEntity<EcDydlfzxEntityVo> dydlfzx = deviceCenter.getDydlfzx(((EcDydlfzxEntity) deviceObj).getObjId());
			if (null != dydlfzx) {
				return dydlfzx.getNode();
			}
		} else if (deviceObj instanceof EcDypdxEntity) {
			// 低压配电箱
			DeviceEntity<EcDypdxEntityVo> dypdx = deviceCenter.getDypdx(((EcDypdxEntity) deviceObj).getObjId());
			if (null != dypdx) {
				return dypdx.getNode();
			}
		} else if (deviceObj instanceof EcTransformerEntity) {
			// 变压器
			DeviceEntity<EcTransformerEntityVo> transformer = deviceCenter
					.getTransformer(((EcTransformerEntity) deviceObj).getObjId());
			if (null != transformer) {
				return transformer.getNode();
			}
		} else if (deviceObj instanceof EcTowerEntity) {
			// 杆塔
			DeviceEntity<EcTowerEntityVo> tower = deviceCenter.getTower(((EcTowerEntity) deviceObj).getObjId());
			if (null != tower) {
				return tower.getNode();
			}
		} else if (deviceObj instanceof EcMiddleJointEntity) {
			// 中间接头
			DeviceEntity<EcMiddleJointEntityVo> middle = deviceCenter
					.getMiddle(((EcMiddleJointEntity) deviceObj).getObjId());
			if (null != middle) {
				return middle.getNode();
			}
		} else if (deviceObj instanceof EcLineLabelEntity) {
			// 电子标签
			DeviceEntity<EcLineLabelEntityVo> label = deviceCenter.getLabel(((EcLineLabelEntity) deviceObj).getObjId());
			if (null != label) {
				return label.getNode();
			}
		} else if (deviceObj instanceof EcChannelDgEntity) {
			// 顶管
			DeviceEntity<EcChannelDgEntity> dgEntity = deviceCenter.getDg(((EcChannelDgEntity) deviceObj).getObjId());
			if (null != dgEntity) {
				return dgEntity.getNode();
			}
		} else if (deviceObj instanceof EcChannelDlgEntity) {
			// 沟道
			DeviceEntity<EcChannelDlgEntity> dgEntity = deviceCenter.getGd(((EcChannelDlgEntity) deviceObj).getObjId());
			if (null != dgEntity) {
				return dgEntity.getNode();
			}
		} else if (deviceObj instanceof EcChannelDlgdEntity) {
			// 排管
			DeviceEntity<EcChannelDlgdEntity> dgEntity = deviceCenter
					.getPg(((EcChannelDlgdEntity) deviceObj).getObjId());
			if (null != dgEntity) {
				return dgEntity.getNode();
			}
		} else if (deviceObj instanceof EcChannelDlqEntity) {
			// 桥架
			DeviceEntity<EcChannelDlqEntity> dgEntity = deviceCenter.getQj(((EcChannelDlqEntity) deviceObj).getObjId());
			if (null != dgEntity) {
				return dgEntity.getNode();
			}
		} else if (deviceObj instanceof EcChannelDlsdEntity) {
			// 隧道
			DeviceEntity<EcChannelDlsdEntity> dgEntity = deviceCenter
					.getSd(((EcChannelDlsdEntity) deviceObj).getObjId());
			if (null != dgEntity) {
				return dgEntity.getNode();
			}
		} else if (deviceObj instanceof EcChannelDlzmEntity) {
			// 直埋
			DeviceEntity<EcChannelDlzmEntity> dgEntity = deviceCenter
					.getZm(((EcChannelDlzmEntity) deviceObj).getObjId());
			if (null != dgEntity) {
				return dgEntity.getNode();
			}
		} else if (deviceObj instanceof EcChannelJkEntity) {
			// 架空
			DeviceEntity<EcChannelJkEntity> dgEntity = deviceCenter.getJk(((EcChannelJkEntity) deviceObj).getObjId());
			if (null != dgEntity) {
				return dgEntity.getNode();
			}
		} else if (deviceObj instanceof EcChannelDlcEntity) {
			// 电缆槽
			DeviceEntity<EcChannelDlcEntity> dgEntity = deviceCenter
					.getDlc(((EcChannelDlcEntity) deviceObj).getObjId());
			if (null != dgEntity) {
				return dgEntity.getNode();
			}
		}
		return null;
	}

	/**
	 * 获取设备名称
	 * 
	 * @param deviceObj
	 * @return
	 */
	public static String getDeviceName(Object deviceObj, CommonBll commonBll) {
		if (deviceObj == null) {
			return "";
		}
		String rest = "";
		if (deviceObj instanceof EcWorkWellEntity) {
			// 电缆井
			rest = ((EcWorkWellEntity) deviceObj).getSbmc();
		} else if (deviceObj instanceof EcLineEntity) {
			// 电缆
			rest = ((EcLineEntity) deviceObj).getName();
		} else if (deviceObj instanceof EcSubstationEntity) {
			// 变电站
			rest = ((EcSubstationEntity) deviceObj).getName();
		} else if (deviceObj instanceof EcKgzEntity) {
			//
			rest = ((EcKgzEntity) deviceObj).getSbmc();
		} else if (deviceObj instanceof EcDistributionRoomEntity) {
			// 配电房
			rest = ((EcDistributionRoomEntity) deviceObj).getSbmc();
		} else if (deviceObj instanceof EcHwgEntity) {
			// 环网柜
			rest = ((EcHwgEntity) deviceObj).getSbmc();
		} else if (deviceObj instanceof EcXsbdzEntity) {
			// 箱式变电
			rest = ((EcXsbdzEntity) deviceObj).getSbmc();
		} else if (deviceObj instanceof EcDlfzxEntity) {
			// 电缆分接箱
			rest = ((EcDlfzxEntity) deviceObj).getSbmc();
		} else if (deviceObj instanceof EcDydlfzxEntity) {
			// 低压电缆分接箱
			rest = ((EcDydlfzxEntity) deviceObj).getSbmc();
		} else if (deviceObj instanceof EcDypdxEntity) {
			// 低压配电箱
			rest = ((EcDypdxEntity) deviceObj).getSbmc();
		} else if (deviceObj instanceof EcTransformerEntity) {
			// 变压器
			rest = ((EcTransformerEntity) deviceObj).getSbmc();
		} else if (deviceObj instanceof EcChannelEntity) {
			rest = ((EcChannelEntity) deviceObj).getName();
		} else if (deviceObj instanceof EcChannelDlgdEntity) {
			EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
					((EcChannelDlgdEntity) deviceObj).getObjId());
			rest = ecChannelEntity.getName();
		} else if (deviceObj instanceof EcChannelDlgEntity) {
			EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
					((EcChannelDlgEntity) deviceObj).getObjId());
			rest = ecChannelEntity.getName();
		} else if (deviceObj instanceof EcChannelDlsdEntity) {
			EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
					((EcChannelDlsdEntity) deviceObj).getObjId());
			rest = ecChannelEntity.getName();
		} else if (deviceObj instanceof EcChannelDlqEntity) {
			EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
					((EcChannelDlqEntity) deviceObj).getObjId());
			rest = ecChannelEntity.getName();
		} else if (deviceObj instanceof EcChannelDlzmEntity) {
			EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
					((EcChannelDlzmEntity) deviceObj).getObjId());
			rest = ecChannelEntity.getName();
		} else if (deviceObj instanceof EcChannelDlcEntity) {
			EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
					((EcChannelDlcEntity) deviceObj).getObjId());
			rest = ecChannelEntity.getName();
		} else if (deviceObj instanceof EcChannelDgEntity) {
			EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
					((EcChannelDgEntity) deviceObj).getObjId());
			rest = ecChannelEntity.getName();
		} else if (deviceObj instanceof EcChannelJkEntity) {
			EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
					((EcChannelJkEntity) deviceObj).getObjId());
			rest = ecChannelEntity.getName();
		} else if (deviceObj instanceof EcTowerEntity) {
			// 杆塔
			rest = ((EcTowerEntity) deviceObj).getTowerNo() + "";
		} else if (deviceObj instanceof EcMiddleJointEntity) {
			// 中间接头
			rest = ((EcMiddleJointEntity) deviceObj).getSbmc();
		} else if (deviceObj instanceof EcLineLabelEntity) {
			// 电子标签
			rest = ((EcLineLabelEntity) deviceObj).getDevName();
		}
		return rest;
	}

	/**
	 * 获取设备主键（OBJID）
	 * 
	 * @param deviceObj
	 * @return
	 */
	public static String getDevicePrimaryKey(Object deviceObj) {
		if (deviceObj == null) {
			return "";
		}
		String rest = "";
		if (deviceObj instanceof EcWorkWellEntity) {
			// 电缆井
			rest = ((EcWorkWellEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcLineEntity) {
			// 电缆
			rest = ((EcLineEntity) deviceObj).getEcLineId();
		} else if (deviceObj instanceof EcSubstationEntity) {
			// 变电站
			rest = ((EcSubstationEntity) deviceObj).getEcSubstationId();
		} else if (deviceObj instanceof EcKgzEntity) {
			//
			rest = ((EcKgzEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcDistributionRoomEntity) {
			// 配电房
			rest = ((EcDistributionRoomEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcHwgEntity) {
			// 环网柜
			rest = ((EcHwgEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcXsbdzEntity) {
			// 箱式变电
			rest = ((EcXsbdzEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcDlfzxEntity) {
			// 电缆分接箱
			rest = ((EcDlfzxEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcDydlfzxEntity) {
			// 低压电缆分接箱
			rest = ((EcDydlfzxEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcDypdxEntity) {
			// 低压配电箱
			rest = ((EcDypdxEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcTransformerEntity) {
			// 变压器
			rest = ((EcTransformerEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcChannelEntity) {
			// 通道
			rest = ((EcChannelEntity) deviceObj).getEcChannelId();
		} else if (deviceObj instanceof EcChannelDlgdEntity) {
			// 电缆管道
			rest = ((EcChannelDlgdEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcChannelDlgEntity) {
			// 电缆管
			rest = ((EcChannelDlgEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcChannelDlsdEntity) {
			// 电缆隧道
			rest = ((EcChannelDlsdEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcChannelDlqEntity) {
			// 电缆桥
			rest = ((EcChannelDlqEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcChannelDlzmEntity) {
			// 电缆直埋
			rest = ((EcChannelDlzmEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcChannelDlcEntity) {
			//电缆槽
			rest = ((EcChannelDlcEntity)deviceObj).getObjId();
		} else if (deviceObj instanceof EcChannelDgEntity) {
			// 顶管
			rest = ((EcChannelDgEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcChannelJkEntity) {
			// 架空
			rest = ((EcChannelJkEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcTowerEntity) {
			// 杆塔
			rest = ((EcTowerEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcMiddleJointEntity) {
			// 中间接头
			rest = ((EcMiddleJointEntity) deviceObj).getObjId();
		} else if (deviceObj instanceof EcLineLabelEntity) {
			// 电子标签
			rest = ((EcLineLabelEntity) deviceObj).getObjId();
		}
		return rest;
	}
}
