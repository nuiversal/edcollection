package com.winway.android.edcollection.datacenter.entity;

import java.util.List;

import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcDypdxEntityVo;
import com.winway.android.edcollection.project.vo.EcKgzEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.edcollection.project.vo.EcXsbdzEntityVo;

/**
 * 设备类型| 1 变电站，2 配电房，3 变压器，4 杆塔，5 工井',
 * 
 * @author lyh
 * @version 创建时间：2017年1月9日 上午8:57:55
 * 
 */
public class NodeDevicesEntity {
	// 工井
	private List<DeviceEntity<EcWorkWellEntityVo>> well;
	// 变电站
	private List<DeviceEntity<EcSubstationEntityVo>> station;
	// 杆塔
	private List<DeviceEntity<EcTowerEntityVo>> tower;
	// 变压器
	private List<DeviceEntity<EcTransformerEntityVo>> tranformer;
	// 配电室-以前
	private List<DeviceEntity<EcDistributionRoomEntityVo>> distribution_room;
	// 箱式变电站
	private List<DeviceEntity<EcXsbdzEntityVo>> xsbdz;
	// 开关站
	private List<DeviceEntity<EcKgzEntityVo>> kgz;
	// 低压配电箱
	private List<DeviceEntity<EcDypdxEntityVo>> dypdx;

	public List<DeviceEntity<EcXsbdzEntityVo>> getXsbdz() {
		return xsbdz;
	}

	public void setXsbdz(List<DeviceEntity<EcXsbdzEntityVo>> xsbdz) {
		this.xsbdz = xsbdz;
	}

	public List<DeviceEntity<EcKgzEntityVo>> getKgz() {
		return kgz;
	}

	public void setKgz(List<DeviceEntity<EcKgzEntityVo>> kgz) {
		this.kgz = kgz;
	}

	public List<DeviceEntity<EcDypdxEntityVo>> getDypdx() {
		return dypdx;
	}

	public void setDypdx(List<DeviceEntity<EcDypdxEntityVo>> dypdx) {
		this.dypdx = dypdx;
	}

	public List<DeviceEntity<EcWorkWellEntityVo>> getWell() {
		return well;
	}

	public void setWell(List<DeviceEntity<EcWorkWellEntityVo>> well) {
		this.well = well;
	}

	public List<DeviceEntity<EcSubstationEntityVo>> getStation() {
		return station;
	}

	public void setStation(List<DeviceEntity<EcSubstationEntityVo>> station) {
		this.station = station;
	}

	public List<DeviceEntity<EcTowerEntityVo>> getTower() {
		return tower;
	}

	public void setTower(List<DeviceEntity<EcTowerEntityVo>> tower) {
		this.tower = tower;
	}

	public List<DeviceEntity<EcTransformerEntityVo>> getTranformer() {
		return tranformer;
	}

	public void setTranformer(
			List<DeviceEntity<EcTransformerEntityVo>> tranformer) {
		this.tranformer = tranformer;
	}

	public List<DeviceEntity<EcDistributionRoomEntityVo>> getDistribution_room() {
		return distribution_room;
	}

	public void setDistribution_room(
			List<DeviceEntity<EcDistributionRoomEntityVo>> distribution_room) {
		this.distribution_room = distribution_room;
	}

}
