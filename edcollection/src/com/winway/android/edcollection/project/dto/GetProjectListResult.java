package com.winway.android.edcollection.project.dto;

import java.util.List;

import com.winway.android.edcollection.base.dto.MessageBase;
import com.winway.android.edcollection.project.entity.EmMembersEntity;

public class GetProjectListResult extends MessageBase {

	private List<EmProjectEntityDto> list;
	private List<EmMembersEntity> members;

	public List<EmMembersEntity> getMembers() {
		return members;
	}

	public void setMembers(List<EmMembersEntity> members) {
		this.members = members;
	}

	public void setList(List<EmProjectEntityDto> list) {
		this.list = list;
	}

	public List<EmProjectEntityDto> getList() {
		return list;
	}

	private Integer p_count = null;

	public Integer getP_count() {
		return p_count;
	}

	public void setP_count(Integer p_count) {
		this.p_count = p_count;
	}

}
