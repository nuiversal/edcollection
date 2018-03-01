package com.winway.android.edcollection.map.util;

import java.util.List;

import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.ToastHandler;
import com.winway.android.edcollection.base.util.CoordsUtils;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.LineDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.NodeDevicesEntity;
import com.winway.android.edcollection.map.SpatialAnalysis;
import com.winway.android.edcollection.map.entity.SpatialAnalysisDataType;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
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
import com.winway.android.util.FileUtil;
import com.winway.android.util.LogUtil;

import android.content.Context;
import ocn.himap.base.HiCoordinate;
import ocn.himap.datamanager.HiElement;

/**
 * 组织数据到数据集
 * 
 * @author zgq
 *
 */
public class CombineSpatialDatasUtil {
	private static CombineSpatialDatasUtil instance;
	private ToastHandler taostHandler;

	private CombineSpatialDatasUtil() {

	}

	public void setToastHandler(ToastHandler h) {
		taostHandler = h;
	}

	public static CombineSpatialDatasUtil getInstane() {
		if (instance == null) {
			synchronized (CombineSpatialDatasUtil.class) {
				if (instance == null) {
					instance = new CombineSpatialDatasUtil();
				}
			}
		}

		return instance;
	}

	/**
	 * 构建空间查询用到的数据
	 * 
	 * @param context
	 */
	public void loadSpatialDatas(Context context) {
		// 构建空间分析用的数据集
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		LogUtil.i(projectBDUrl);
		DataCenter dataCenter = new DataCenterImpl(context, projectBDUrl);
		List<EcLineEntity> lineEntities = dataCenter.getLineListByOrgid(GlobalEntry.loginResult.getOrgid(), null, null);
		if (lineEntities == null) {
			return;
		}
		for (int i = 0; i < lineEntities.size(); i++) {
			EcLineEntity ecLineEntity = lineEntities.get(i);
			String lineId = ecLineEntity.getEcLineId();
			// 路径点
			List<EcNodeEntity> nodeList = dataCenter.getLineNodeList(lineId, null, null);
			if (nodeList == null) {
				continue;
			}
			for (int j = 0; j < nodeList.size(); j++) {
				EcNodeEntity ecNodeEntity = nodeList.get(j);
				if (ecNodeEntity == null) {
					continue;
				}
				double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
				HiElement hiElement = new HiElement();
				hiElement.Crds.add(new HiCoordinate(xy[0], xy[1]));
				hiElement.DataReference = ecNodeEntity;
				hiElement.Text = ecNodeEntity.getOid();
				// 添加元素到数据集中
				SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElement);
				// 路径点设备
				NodeDevicesEntity nodeDevicesEntity = dataCenter.getPathNodeDetails(ecNodeEntity.getOid(), null, null);
				if (nodeDevicesEntity==null) {
					return;
				}
				// 路径点设备-1配电室
				List<DeviceEntity<EcDistributionRoomEntityVo>> pdsDevices = nodeDevicesEntity.getDistribution_room();
				if (pdsDevices != null && pdsDevices.size() > 0) {
					EcDistributionRoomEntityVo pdsVo = pdsDevices.get(0).getData();
					HiElement hiElementPds = combineHiElement(xy, pdsVo, pdsVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementPds);
				}
				// 路径点设备-2低压配电箱
				List<DeviceEntity<EcDypdxEntityVo>> dypdxDevices = nodeDevicesEntity.getDypdx();
				if (dypdxDevices != null && dypdxDevices.size() > 0) {
					EcDypdxEntityVo dypdxVo = dypdxDevices.get(0).getData();
					HiElement hiElementDypdx = combineHiElement(xy, dypdxVo, dypdxVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementDypdx);
				}
				// 路径点设备-3开关站
				List<DeviceEntity<EcKgzEntityVo>> kgzDevices = nodeDevicesEntity.getKgz();
				if (kgzDevices != null && kgzDevices.size() > 0) {
					EcKgzEntityVo kgzVo = kgzDevices.get(0).getData();
					HiElement hiElementKgz = combineHiElement(xy, kgzVo, kgzVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementKgz);
				}
				// 路径点设备-4变电站
				List<DeviceEntity<EcSubstationEntityVo>> stationDevices = nodeDevicesEntity.getStation();
				if (stationDevices != null && stationDevices.size() > 0) {
					EcSubstationEntityVo stationVo = stationDevices.get(0).getData();
					HiElement hiElementSubstation = combineHiElement(xy, stationVo, stationVo.getEcSubstationId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementSubstation);
				}

				// 路径点设备-5杆塔
				List<DeviceEntity<EcTowerEntityVo>> towerDevices = nodeDevicesEntity.getTower();
				if (towerDevices != null && towerDevices.size() > 0) {
					EcTowerEntityVo towerVo = towerDevices.get(0).getData();
					HiElement hiElementTower = combineHiElement(xy, towerVo, towerVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementTower);
				}

				// 路径点设备-6变压器
				List<DeviceEntity<EcTransformerEntityVo>> byqDevices = nodeDevicesEntity.getTranformer();
				if (byqDevices != null && byqDevices.size() > 0) {
					EcTransformerEntityVo byqVo = byqDevices.get(0).getData();
					HiElement hiElementByq = combineHiElement(xy, byqVo, byqVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementByq);
				}

				// 路径点设备-7工井
				List<DeviceEntity<EcWorkWellEntityVo>> wellDevices = nodeDevicesEntity.getWell();
				if (wellDevices != null && wellDevices.size() > 0) {
					EcWorkWellEntityVo wellVo = wellDevices.get(0).getData();
					HiElement hiElementWell = combineHiElement(xy, wellVo, wellVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementWell);
				}

				// 路径点设备-8箱式变电站
				List<DeviceEntity<EcXsbdzEntityVo>> xsbdzDevices = nodeDevicesEntity.getXsbdz();
				if (xsbdzDevices != null && xsbdzDevices.size() > 0) {
					EcXsbdzEntityVo xsbdzVo = xsbdzDevices.get(0).getData();
					HiElement hiElementXsbdz = combineHiElement(xy, xsbdzVo, xsbdzVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementXsbdz);
				}

			}

			// 线路附属设备
			LineDevicesEntity lineDevicesEntity = dataCenter.getLineDevicesList(lineId, null, null);
			if (lineDevicesEntity == null) {
				return;
			}
			// 线路附属设备-1电子标签
			List<DeviceEntity<EcLineLabelEntityVo>> labelList = lineDevicesEntity.getLineLabelEntityVoList();
			if (labelList != null && labelList.size() > 0) {
				for (int j = 0; j < labelList.size(); j++) {
					DeviceEntity<EcLineLabelEntityVo> labelDevice = labelList.get(j);
					EcLineLabelEntityVo ecLineLabelEntityVo = labelDevice.getData();
					EcNodeEntity ecNodeEntity = labelDevice.getNode();
					if (ecNodeEntity == null) {
						taostHandler.showMessage("电子标签数据不正确：" + ecLineLabelEntityVo.getDevName());
						continue;
					}
					double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
					HiElement hiElementLabel = combineHiElement(xy, ecLineLabelEntityVo, ecLineLabelEntityVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementLabel);
				}
			}

			// 线路附属设备-2中间接头
			List<DeviceEntity<EcMiddleJointEntityVo>> zjjtList = lineDevicesEntity.getMiddleJointEntityVoList();
			if (zjjtList != null && zjjtList.size() > 0) {
				for (int j = 0; j < zjjtList.size(); j++) {
					DeviceEntity<EcMiddleJointEntityVo> zjjtDevice = zjjtList.get(j);
					EcMiddleJointEntityVo zjjtVo = zjjtDevice.getData();
					EcNodeEntity ecNodeEntity = zjjtDevice.getNode();
					if (ecNodeEntity == null) {
						taostHandler.showMessage("中间接头数据不正确：" + zjjtVo.getSbmc());
						continue;
					}
					double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
					HiElement hiElementZjjt = combineHiElement(xy, zjjtVo, zjjtVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementZjjt);
				}
			}

			// 线路附属设备-3电缆分支箱
			List<DeviceEntity<EcDlfzxEntityVo>> dlfzxList = lineDevicesEntity.getDlfzxEntityVoList();
			if (dlfzxList != null && dlfzxList.size() > 0) {
				for (int j = 0; j < dlfzxList.size(); j++) {
					DeviceEntity<EcDlfzxEntityVo> dlfzxListDevice = dlfzxList.get(j);
					EcDlfzxEntityVo dlfzxVo = dlfzxListDevice.getData();
					EcNodeEntity ecNodeEntity = dlfzxListDevice.getNode();
					if (ecNodeEntity == null) {
						taostHandler.showMessage("电缆分支箱数据不正确：" + dlfzxVo.getSbmc());
						continue;
					}
					double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
					HiElement hiElementDlfzx = combineHiElement(xy, dlfzxVo, dlfzxVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementDlfzx);
				}
			}

			// 线路附属设备-4低压电缆分支箱
			List<DeviceEntity<EcDydlfzxEntityVo>> dydlfzxList = lineDevicesEntity.getDydlfzxEntityVoList();
			if (dydlfzxList != null && dydlfzxList.size() > 0) {
				for (int j = 0; j < dydlfzxList.size(); j++) {
					DeviceEntity<EcDydlfzxEntityVo> dydlfzxListDevice = dydlfzxList.get(j);
					EcDydlfzxEntityVo dyDlfzxVo = dydlfzxListDevice.getData();
					EcNodeEntity ecNodeEntity = dydlfzxListDevice.getNode();
					if (ecNodeEntity == null) {
						taostHandler.showMessage("低压电缆分支箱数据不正确：" + dyDlfzxVo.getSbmc());
						continue;
					}
					double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
					HiElement hiElementDydlfzx = combineHiElement(xy, dyDlfzxVo, dyDlfzxVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementDydlfzx);
				}
			}

			// 线路附属设备-5环网柜
			List<DeviceEntity<EcHwgEntityVo>> hwgList = lineDevicesEntity.getHwgEntityVoList();
			if (hwgList != null && hwgList.size() > 0) {
				for (int j = 0; j < hwgList.size(); j++) {
					DeviceEntity<EcHwgEntityVo> hwgListDevice = hwgList.get(j);
					EcHwgEntityVo hwgVo = hwgListDevice.getData();
					EcNodeEntity ecNodeEntity = hwgListDevice.getNode();
					if (ecNodeEntity == null) {
						taostHandler.showMessage("环网柜数据不正确：" + hwgVo.getSbmc());
						continue;
					}
					double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
					HiElement hiElementHwg = combineHiElement(xy, hwgVo, hwgVo.getObjId());
					// 添加元素到数据集中
					SpatialAnalysis.getInstance().addElement2Dataset(SpatialAnalysisDataType.point, hiElementHwg);
				}
			}

		}

	}

	/**
	 * 构建元素
	 * 
	 * @param xy
	 * @param dataReference
	 * @param text
	 * @return
	 */
	public HiElement combineHiElement(double[] xy, Object dataReference, String text) {
		HiElement hiElement = new HiElement();
		hiElement.Crds.add(new HiCoordinate(xy[0], xy[1]));
		hiElement.DataReference = dataReference;
		hiElement.Text = text;
		return hiElement;
	}
}
