package com.winway.android.edcollection.datacenter.entity;

import java.util.List;

import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;

/**
 * 线路附属设备实体
 * 
 * @author mr-lao
 *
 */
public class LineDevicesEntity {
	// 分接箱
	private List<DeviceEntity<EcDistBoxEntityVo>> boxList;
	// 环网柜
	private List<DeviceEntity<EcHwgEntityVo>> hwgEntityVoList;
	// 电子标签
	private List<DeviceEntity<EcLineLabelEntityVo>> lineLabelEntityVoList;
	// 电缆分支箱
	private List<DeviceEntity<EcDlfzxEntityVo>> dlfzxEntityVoList;
	// 低压电缆分支箱
	private List<DeviceEntity<EcDydlfzxEntityVo>> dydlfzxEntityVoList;
	// 电缆中间接头
	private List<DeviceEntity<EcMiddleJointEntityVo>> middleJointEntityVoList;

	public List<DeviceEntity<EcHwgEntityVo>> getHwgEntityVoList() {
		return hwgEntityVoList;
	}

	public void setHwgEntityVoList(List<DeviceEntity<EcHwgEntityVo>> hwgEntityVoList) {
		this.hwgEntityVoList = hwgEntityVoList;
	}

	public List<DeviceEntity<EcLineLabelEntityVo>> getLineLabelEntityVoList() {
		return lineLabelEntityVoList;
	}

	public void setLineLabelEntityVoList(List<DeviceEntity<EcLineLabelEntityVo>> lineLabelEntityVoList) {
		this.lineLabelEntityVoList = lineLabelEntityVoList;
	}

	public List<DeviceEntity<EcDlfzxEntityVo>> getDlfzxEntityVoList() {
		return dlfzxEntityVoList;
	}

	public void setDlfzxEntityVoList(List<DeviceEntity<EcDlfzxEntityVo>> dlfzxEntityVoList) {
		this.dlfzxEntityVoList = dlfzxEntityVoList;
	}

	public List<DeviceEntity<EcDydlfzxEntityVo>> getDydlfzxEntityVoList() {
		return dydlfzxEntityVoList;
	}

	public void setDydlfzxEntityVoList(List<DeviceEntity<EcDydlfzxEntityVo>> dydlfzxEntityVoList) {
		this.dydlfzxEntityVoList = dydlfzxEntityVoList;
	}

	public List<DeviceEntity<EcMiddleJointEntityVo>> getMiddleJointEntityVoList() {
		return middleJointEntityVoList;
	}

	public void setMiddleJointEntityVoList(List<DeviceEntity<EcMiddleJointEntityVo>> middleJointEntityVoList) {
		this.middleJointEntityVoList = middleJointEntityVoList;
	}

	public List<DeviceEntity<EcDistBoxEntityVo>> getBoxList() {
		return boxList;
	}

	public void setBoxList(List<DeviceEntity<EcDistBoxEntityVo>> boxList) {
		this.boxList = boxList;
	}

}
