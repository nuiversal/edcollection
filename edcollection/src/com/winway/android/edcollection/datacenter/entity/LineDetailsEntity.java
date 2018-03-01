package com.winway.android.edcollection.datacenter.entity;

import java.util.List;

import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;

/** 
* @author lyh  
* @version 创建时间：2017年1月9日 上午8:53:48 
* 
*/
public class LineDetailsEntity {
	// 线路
	private EcLineEntity data;
	// 节点
	private List<EcNodeEntity> nodes;

	public EcLineEntity getData() {
		return data;
	}

	public void setData(EcLineEntity data) {
		this.data = data;
	}

	public List<EcNodeEntity> getNodes() {
		return nodes;
	}

	public void setNodes(List<EcNodeEntity> nodes) {
		this.nodes = nodes;
	}

}
