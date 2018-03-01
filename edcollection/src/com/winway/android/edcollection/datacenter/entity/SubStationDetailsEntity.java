package com.winway.android.edcollection.datacenter.entity;

import java.util.List;

import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;

/** 
 * 变电站信息中心实体类
* @author lyh  
* @version 创建时间：2017年1月6日 下午7:21:30 
* 
*/
public class SubStationDetailsEntity {

	//变电站
	private EcSubstationEntity data;
	//线路
	private List<EcLineEntity> lines;

	public EcSubstationEntity getData() {
		return data;
	}

	public void setData(EcSubstationEntity data) {
		this.data = data;
	}

	public List<EcLineEntity> getLines() {
		return lines;
	}

	public void setLines(List<EcLineEntity> lines) {
		this.lines = lines;
	}

}
