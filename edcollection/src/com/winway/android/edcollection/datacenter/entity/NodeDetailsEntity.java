package com.winway.android.edcollection.datacenter.entity;

import java.util.List;

import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;

/** 
 * 节点信息中心实体类
* @author lyh  
* @version 创建时间：2017年1月6日 下午7:21:30 
* 
*/
public class NodeDetailsEntity {

	// 节点
	private EcNodeEntity data;
	// 附属设备
	private List<LineDevicesEntity> devices;

	public EcNodeEntity getData() {
		return data;
	}

	public void setData(EcNodeEntity data) {
		this.data = data;
	}

	public List<LineDevicesEntity> getDevices() {
		return devices;
	}

	public void setDevices(List<LineDevicesEntity> devices) {
		this.devices = devices;
	}

}
