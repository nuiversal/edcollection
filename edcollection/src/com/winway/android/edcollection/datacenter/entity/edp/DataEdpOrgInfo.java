package com.winway.android.edcollection.datacenter.entity.edp;

import java.util.Date;

/**
 * EdpOrgInfo实体类
 * 
 * @author guoqiaobin
 * @since 2016/2/19 11:50:47
 */

public class DataEdpOrgInfo {

	private String orgId;// 机构ID

	private String logicSysNo;// 逻辑系统标识

	private String orgNo;// 机构标识

	private String orgName;// 机构名称

	private String upNo;// 上级标识

	private String orgSts;// 机构状态

	private String remark;// 备注

	private String lastUpdateUser;// 最后修改人

	private Date lastUpdateTime;// 最后修改时间

	private String customNo;

	private String mapCenter;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUpNo() {
		return upNo;
	}

	public void setUpNo(String upNo) {
		this.upNo = upNo;
	}

	public String getOrgSts() {
		return orgSts;
	}

	public void setOrgSts(String orgSts) {
		this.orgSts = orgSts;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCustomNo() {
		return customNo;
	}

	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}

	public String getMapCenter() {
		return mapCenter;
	}

	public void setMapCenter(String mapCenter) {
		this.mapCenter = mapCenter;
	}

}
