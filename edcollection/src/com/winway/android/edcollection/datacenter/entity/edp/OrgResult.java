package com.winway.android.edcollection.datacenter.entity.edp;

import java.util.List;

public class OrgResult extends BaseOrgResult {

	private List<OrgResult> list;// 子机构列表

	public List<OrgResult> getList() {
		return list;
	}

	public void setList(List<OrgResult> list) {
		this.list = list;
	}

}
