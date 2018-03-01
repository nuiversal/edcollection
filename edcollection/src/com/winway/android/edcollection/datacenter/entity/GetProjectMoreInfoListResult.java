package com.winway.android.edcollection.datacenter.entity;

import com.winway.android.edcollection.base.dto.MessageBase;
import com.winway.android.edcollection.project.entity.EmProjectMoreinfoEntity;

public class GetProjectMoreInfoListResult extends MessageBase {
	EmProjectMoreinfoEntity prjdetail = new EmProjectMoreinfoEntity();

	public void setPrjdetail(EmProjectMoreinfoEntity prjdetail) {
		this.prjdetail = prjdetail;
	}

	public EmProjectMoreinfoEntity getPrjdetail() {
		return prjdetail;
	}
	private Integer p_count =  null ;

	public Integer getP_count() {
		return p_count;
	}

	public void setP_count(Integer p_count) {
		this.p_count = p_count;
	}
}
