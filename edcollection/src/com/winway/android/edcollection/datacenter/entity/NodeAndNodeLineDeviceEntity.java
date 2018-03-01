package com.winway.android.edcollection.datacenter.entity;

import com.winway.android.edcollection.project.entity.EcNodeEntity;

/** 
* @author lyh  
* @version 创建时间：2017年1月9日 下午3:31:21 
* 
*/
public class NodeAndNodeLineDeviceEntity {
	private EcNodeEntity data;
	private NodeDevicesEntity nodeDatas;
	private LineDevicesEntity lineDatas;

	public EcNodeEntity getData() {
		return data;
	}

	public void setData(EcNodeEntity data) {
		this.data = data;
	}

	public NodeDevicesEntity getNodeDatas() {
		return nodeDatas;
	}

	public void setNodeDatas(NodeDevicesEntity nodeDatas) {
		this.nodeDatas = nodeDatas;
	}

	public LineDevicesEntity getLineDatas() {
		return lineDatas;
	}

	public void setLineDatas(LineDevicesEntity lineDatas) {
		this.lineDatas = lineDatas;
	}

}
