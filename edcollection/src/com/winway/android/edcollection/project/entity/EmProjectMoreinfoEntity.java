package com.winway.android.edcollection.project.entity;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * EmProjectMoreinfo实体类
 * 
 * @author zgq
 * @since 2016/12/27 14:33:51
 */
@Table(name = "em_project_moreinfo")
public class EmProjectMoreinfoEntity {

	@Id
	@Column(column = "EM_PROJECT_ID")
	private String emProjectId;// EM_PROJECT_ID
	@Column(column = "PRJ_NO")
	private String prjNo;// PRJ_NO
	@Column(column = "PRJ_NAME")
	private String prjName;// PRJ_NAME
	@Column(column = "REGION")
	private String region;// REGION
	@Column(column = "REGION_NO")
	private String regionNo;// REGION_NO
	@Column(column = "PRJ_DESC")
	private String prjDesc;// PRJ_DESC
	@Column(column = "BEGIN_TIME")
	private Date beginTime;// BEGIN_TIME
	@Column(column = "ACCEPT_TIME")
	private Date acceptTime;// ACCEPT_TIME
	@Column(column = "COMPLETE_TIME")
	private Date completeTime;// COMPLETE_TIME
	@Column(column = "LEADER")
	private String leader;// LEADER
	@Column(column = "STATUS")
	private Integer status;// 项目状态| 0 未启动，1 施工中，2 已验收，3 通过工程质检，4 已完结
	@Column(column = "IS_SUBMIT")
	private Integer isSubmit;// 是否已经提交正式库
	@Column(column = "ORG_ID")
	private String orgId;// 对应机构ID| 只需要对应项目对应的本级机构，如果广州市、东莞市，下级区县EDP有关联。
	@Column(column = "CUSTOM_NO")
	private String customNo;// CUSTOM_NO
	@Column(column = "SHARE_KEY")
	private String shareKey;// SHARE_KEY
	@Column(column = "REMARKS")
	private String remarks;// REMARKS
	@Column(column = "ORG_DUTY_NAME")
	private String orgDutyName;// 权属单位
	@Column(column = "ORG_DUTY_PERSON")
	private String orgDutyPerson;// ORG_DUTY_PERSON
	@Column(column = "ORG_TEL")
	private String orgTel;// ORG_TEL
	@Column(column = "BUSINESS_PERSON")
	private String businessPerson;// 销售人员
	@Column(column = "PRJ_MANAGER")
	private String prjManager;// 工程部经理
	@Column(column = "CONTRACT_NO")
	private String contractNo;// CONTRACT_NO
	@Column(column = "CONTRACT_CONTENT")
	private String contractContent;// 球4个，电子标签6条
	@Column(column = "CONSTRUCTION_LINES")
	private String constructionLines;// A线编号;B线编号.....
	@Column(column = "CONSTRUCTION_UNIT")
	private String constructionUnit;// CONSTRUCTION_UNIT
	@Column(column = "CONSTRUCTION_LOCATION")
	private String constructionLocation;// CONSTRUCTION_LOCATION
	@Column(column = "CONSTRUCTION_UNIT_PERSON")
	private String constructionUnitPerson;// CONSTRUCTION_UNIT_PERSON
	@Column(column = "CONSTRUCTION_UNIT_TEL")
	private String constructionUnitTel;// CONSTRUCTION_UNIT_TEL
	@Column(column = "GET_THINGS_LOCATION")
	private String getThingsLocation;// 客户自带货去现场
	@Column(column = "BALL_TYPE_COUNT")
	private Integer ballTypeCount;// BALL_TYPE_COUNT
	@Column(column = "NAIL_TYPE_COUNT")
	private Integer nailTypeCount;// NAIL_TYPE_COUNT
	@Column(column = "ELEC_LABEL_COUNT")
	private Integer elecLabelCount;// ELEC_LABEL_COUNT
	@Column(column = "WORK_ZONE")
	private String workZone;// 路段信息或者起始变电站等
	@Column(column = "LINE_LENGTH")
	private Double lineLength;// LINE_LENGTH
	@Column(column = "PIPE_LENGTH")
	private Double pipeLength;// PIPE_LENGTH
	@Column(column = "PIPE_COUNTS")
	private Integer pipeCounts;// PIPE_COUNTS
	@Column(column = "CAPITAL_CONSTRUCTION_TYPE")
	private Integer capitalConstructionType;// 主网基建、配网基建、其他基建
	@Column(column = "STOCK_TYPE")
	private Integer stockType;// 主网存量、配网存量、其他存量
	@Column(column = "TASK_TYPES")
	private String taskTypes;// 安装标示器、电子标签、探测路径、探测顶管、线对识别、按键环、仅数据采集不包安装、市政测绘
	@Column(column = "WORK_STANDARD")
	private Integer workStandard;// 常规标准、按客户设计标准、需要专项讨论

	@Override
	public String toString() {
		return "EmProjectMoreinfoEntity [emProjectId=" + emProjectId
				+ ", prjNo=" + prjNo + ", prjName=" + prjName + ", region="
				+ region + ", regionNo=" + regionNo + ", prjDesc=" + prjDesc
				+ ", beginTime=" + beginTime + ", acceptTime=" + acceptTime
				+ ", completeTime=" + completeTime + ", leader=" + leader
				+ ", status=" + status + ", isSubmit=" + isSubmit + ", orgId="
				+ orgId + ", customNo=" + customNo + ", shareKey=" + shareKey
				+ ", remarks=" + remarks + ", orgDutyName=" + orgDutyName
				+ ", orgDutyPerson=" + orgDutyPerson + ", orgTel=" + orgTel
				+ ", businessPerson=" + businessPerson + ", prjManager="
				+ prjManager + ", contractNo=" + contractNo
				+ ", contractContent=" + contractContent
				+ ", constructionLines=" + constructionLines
				+ ", constructionUnit=" + constructionUnit
				+ ", constructionLocation=" + constructionLocation
				+ ", constructionUnitPerson=" + constructionUnitPerson
				+ ", constructionUnitTel=" + constructionUnitTel
				+ ", getThingsLocation=" + getThingsLocation
				+ ", ballTypeCount=" + ballTypeCount + ", nailTypeCount="
				+ nailTypeCount + ", elecLabelCount=" + elecLabelCount
				+ ", workZone=" + workZone + ", lineLength=" + lineLength
				+ ", pipeLength=" + pipeLength + ", pipeCounts=" + pipeCounts
				+ ", capitalConstructionType=" + capitalConstructionType
				+ ", stockType=" + stockType + ", taskTypes=" + taskTypes
				+ ", workStandard=" + workStandard + "]";
	}

	public String getEmProjectId() {
		return emProjectId;
	}

	public void setEmProjectId(String emProjectId) {
		this.emProjectId = emProjectId;
	}

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionNo() {
		return regionNo;
	}

	public void setRegionNo(String regionNo) {
		this.regionNo = regionNo;
	}

	public String getPrjDesc() {
		return prjDesc;
	}

	public void setPrjDesc(String prjDesc) {
		this.prjDesc = prjDesc;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(Integer isSubmit) {
		this.isSubmit = isSubmit;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCustomNo() {
		return customNo;
	}

	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}

	public String getShareKey() {
		return shareKey;
	}

	public void setShareKey(String shareKey) {
		this.shareKey = shareKey;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOrgDutyName() {
		return orgDutyName;
	}

	public void setOrgDutyName(String orgDutyName) {
		this.orgDutyName = orgDutyName;
	}

	public String getOrgDutyPerson() {
		return orgDutyPerson;
	}

	public void setOrgDutyPerson(String orgDutyPerson) {
		this.orgDutyPerson = orgDutyPerson;
	}

	public String getOrgTel() {
		return orgTel;
	}

	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}

	public String getBusinessPerson() {
		return businessPerson;
	}

	public void setBusinessPerson(String businessPerson) {
		this.businessPerson = businessPerson;
	}

	public String getPrjManager() {
		return prjManager;
	}

	public void setPrjManager(String prjManager) {
		this.prjManager = prjManager;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractContent() {
		return contractContent;
	}

	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}

	public String getConstructionLines() {
		return constructionLines;
	}

	public void setConstructionLines(String constructionLines) {
		this.constructionLines = constructionLines;
	}

	public String getConstructionUnit() {
		return constructionUnit;
	}

	public void setConstructionUnit(String constructionUnit) {
		this.constructionUnit = constructionUnit;
	}

	public String getConstructionLocation() {
		return constructionLocation;
	}

	public void setConstructionLocation(String constructionLocation) {
		this.constructionLocation = constructionLocation;
	}

	public String getConstructionUnitPerson() {
		return constructionUnitPerson;
	}

	public void setConstructionUnitPerson(String constructionUnitPerson) {
		this.constructionUnitPerson = constructionUnitPerson;
	}

	public String getConstructionUnitTel() {
		return constructionUnitTel;
	}

	public void setConstructionUnitTel(String constructionUnitTel) {
		this.constructionUnitTel = constructionUnitTel;
	}

	public String getGetThingsLocation() {
		return getThingsLocation;
	}

	public void setGetThingsLocation(String getThingsLocation) {
		this.getThingsLocation = getThingsLocation;
	}

	public Integer getBallTypeCount() {
		return ballTypeCount;
	}

	public void setBallTypeCount(Integer ballTypeCount) {
		this.ballTypeCount = ballTypeCount;
	}

	public Integer getNailTypeCount() {
		return nailTypeCount;
	}

	public void setNailTypeCount(Integer nailTypeCount) {
		this.nailTypeCount = nailTypeCount;
	}

	public Integer getElecLabelCount() {
		return elecLabelCount;
	}

	public void setElecLabelCount(Integer elecLabelCount) {
		this.elecLabelCount = elecLabelCount;
	}

	public String getWorkZone() {
		return workZone;
	}

	public void setWorkZone(String workZone) {
		this.workZone = workZone;
	}

	public Double getLineLength() {
		return lineLength;
	}

	public void setLineLength(Double lineLength) {
		this.lineLength = lineLength;
	}

	public Double getPipeLength() {
		return pipeLength;
	}

	public void setPipeLength(Double pipeLength) {
		this.pipeLength = pipeLength;
	}

	public Integer getPipeCounts() {
		return pipeCounts;
	}

	public void setPipeCounts(Integer pipeCounts) {
		this.pipeCounts = pipeCounts;
	}

	public Integer getCapitalConstructionType() {
		return capitalConstructionType;
	}

	public void setCapitalConstructionType(Integer capitalConstructionType) {
		this.capitalConstructionType = capitalConstructionType;
	}

	public Integer getStockType() {
		return stockType;
	}

	public void setStockType(Integer stockType) {
		this.stockType = stockType;
	}

	public String getTaskTypes() {
		return taskTypes;
	}

	public void setTaskTypes(String taskTypes) {
		this.taskTypes = taskTypes;
	}

	public Integer getWorkStandard() {
		return workStandard;
	}

	public void setWorkStandard(Integer workStandard) {
		this.workStandard = workStandard;
	}

}
