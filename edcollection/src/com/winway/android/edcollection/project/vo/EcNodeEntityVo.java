package com.winway.android.edcollection.project.vo;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Transient;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;

/**
 * 节点传输类
 * @author zgq
 *
 */
public class EcNodeEntityVo extends EcNodeEntity implements Serializable{
	// 不入库
	@Transient
	private Double longitude;// 经度
	@Transient
	private Double latitude;// 纬度
	private EcSubstationEntity ecSubstationEntity;// 变电站
	private EcTowerEntity ecTowerEntity;// 岗塔
	private EcTransformerEntity ecTransformerEntity;// 变压器
	private EcDistributionRoomEntity ecDistributionRoomEntity;// 配电房
	private EcWorkWellEntity ecWorkWellEntity;// 工井

	public EcSubstationEntity getEcSubstationEntity() {
		return ecSubstationEntity;
	}

	public void setEcSubstationEntity(EcSubstationEntity ecSubstationEntity) {
		this.ecSubstationEntity = ecSubstationEntity;
	}

	public EcTowerEntity getEcTowerEntity() {
		return ecTowerEntity;
	}

	public void setEcTowerEntity(EcTowerEntity ecTowerEntity) {
		this.ecTowerEntity = ecTowerEntity;
	}

	public EcTransformerEntity getEcTransformerEntity() {
		return ecTransformerEntity;
	}

	public void setEcTransformerEntity(EcTransformerEntity ecTransformerEntity) {
		this.ecTransformerEntity = ecTransformerEntity;
	}

	public EcDistributionRoomEntity getEcDistributionRoomEntity() {
		return ecDistributionRoomEntity;
	}

	public void setEcDistributionRoomEntity(EcDistributionRoomEntity ecDistributionRoomEntity) {
		this.ecDistributionRoomEntity = ecDistributionRoomEntity;
	}

	public EcWorkWellEntity getEcWorkWellEntity() {
		return ecWorkWellEntity;
	}

	public void setEcWorkWellEntity(EcWorkWellEntity ecWorkWellEntity) {
		this.ecWorkWellEntity = ecWorkWellEntity;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

}
