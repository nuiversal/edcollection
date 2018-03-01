package com.winway.android.edcollection.datacenter.entity.edp;

import java.util.List;

public class BaseOrgResult {

	private String orgid;
	private String orgname;
	private String orgno;
	private String customno;

	private String mapCenter;

	private List<UserResult> userlist;// 用户列表

	private int shareright = 15;// 上级机构对该机构的访问权限，0 拒绝，1 读取，2 插入，4 修改，8 删除

	public BaseOrgResult() {
	}

	public String getMapCenter() {
		return mapCenter;
	}

	public void setMapCenter(String mapCenter) {
		this.mapCenter = mapCenter;
	}

	public BaseOrgResult(String orgid, String orgname, String customno) {
		super();
		this.orgid = orgid;
		this.orgname = orgname;
		this.customno = customno;
	}

	public BaseOrgResult(String orgid, String orgname, String customno, int shareright) {
		super();
		this.orgid = orgid;
		this.orgname = orgname;
		this.customno = customno;
		this.shareright = shareright;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getCustomno() {
		return customno;
	}

	public void setCustomno(String customno) {
		this.customno = customno;
	}

	public int getShareright() {
		return shareright;
	}

	public void setShareright(int shareright) {
		this.shareright = shareright;
	}

	public String getOrgno() {
		return orgno;
	}

	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}

	public List<UserResult> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<UserResult> userlist) {
		this.userlist = userlist;
	}

}
