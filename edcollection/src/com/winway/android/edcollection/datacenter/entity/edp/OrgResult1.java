package com.winway.android.edcollection.datacenter.entity.edp;

public class OrgResult1 extends BaseOrgResult {

	private String parentOrgId;

	private String parentOrgNo;

	public OrgResult1(String orgid, String orgname, String customno, String parentOrgId) {
		super(orgid, orgname, customno);
		this.parentOrgId = parentOrgId;
	}

	public OrgResult1() {

	}

	public String getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getParentOrgNo() {
		return parentOrgNo;
	}

	public void setParentOrgNo(String parentOrgNo) {
		this.parentOrgNo = parentOrgNo;
	}

}
