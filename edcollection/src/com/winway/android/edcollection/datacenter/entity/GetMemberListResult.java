package com.winway.android.edcollection.datacenter.entity;

import java.util.List;

import com.winway.android.edcollection.base.dto.MessageBase;
import com.winway.android.edcollection.project.entity.EmMembersEntity;

public class GetMemberListResult extends MessageBase {
	public List<EmMembersItem> list ;

	public List<EmMembersItem> getList() {
		return list;
	}

	public void setList(List<EmMembersItem> list) {
		this.list = list;
	}
	private Integer p_count =  null ;

	public Integer getP_count() {
		return p_count;
	}

	public void setP_count(Integer p_count) {
		this.p_count = p_count;
	}
}
