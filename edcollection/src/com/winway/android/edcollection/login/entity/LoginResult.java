package com.winway.android.edcollection.login.entity;

import com.winway.android.edcollection.base.dto.MessageBase;

/**
 * 登录结果
 * 
 * @author zgq
 *
 */
public class LoginResult extends MessageBase {
	private String credit;
	private String uid;
	private String loginname;
	private String username;
	private String orgid;
	private String orgname;
	private String roleid;
	private String rolename;
	private String customno;
	private String mapCenter;
	private String logicSysNo;

	// 业务字段
	private String passWord;
	private String deptid;// 部门ID
	private String deptname;// 部门名称

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getCustomno() {
		return customno;
	}

	public void setCustomno(String customno) {
		this.customno = customno;
	}

	public String getMapCenter() {
		return mapCenter;
	}

	public void setMapCenter(String mapCenter) {
		this.mapCenter = mapCenter;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	@Override
	public String toString() {
		return "LoginResult [credit=" + credit + ", uid=" + uid + ", loginname=" + loginname + ", username=" + username
				+ ", orgid=" + orgid + ", orgname=" + orgname + ", roleid=" + roleid + ", rolename=" + rolename
				+ ", customno=" + customno + ", mapCenter=" + mapCenter + ", logicSysNo=" + logicSysNo + ", passWord="
				+ passWord + ", deptid=" + deptid + ", deptname=" + deptname + "]";
	}

}
