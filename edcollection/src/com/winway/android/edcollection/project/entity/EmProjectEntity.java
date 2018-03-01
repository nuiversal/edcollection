package com.winway.android.edcollection.project.entity;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * EmProject实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:54
 */
@Table(name = "em_project")
public class EmProjectEntity implements Serializable {

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
	private Integer status;// STATUS
	@Column(column = "IS_SUBMIT")
	private Integer isSubmit;// IS_SUBMIT
	@Column(column = "ORG_ID")
	private String orgId;// ORG_ID
	@Column(column = "CUSTOM_NO")
	private String customNo;// CUSTOM_NO
	@Column(column = "SHARE_KEY")
	private String shareKey;// SHARE_KEY
	@Column(column = "REMARKS")
	private String remarks;// REMARKS
	@Column(column = "ORG_DUTY_NAME")
	private String orgDutyName;// ORG_DUTY_NAME
	@Column(column = "ORG_DUTY_PERSON")
	private String orgDutyPerson;// ORG_DUTY_PERSON
	@Column(column = "ORG_TEL")
	private String orgTel;// ORG_TEL
	@Column(column = "BUSINESS_PERSON")
	private String businessPerson;// BUSINESS_PERSON
	@Column(column = "PRJ_MANAGER")
	private String prjManager;// PRJ_MANAGER
	@Column(column = "CONTRACT_NO")
	private String contractNo;// CONTRACT_NO
	@Column(column = "CONTRACT_CONTENT")
	private String contractContent;// CONTRACT_CONTENT
	@Column(column = "CONSTRUCTION_LINES")
	private String constructionLines;// CONSTRUCTION_LINES
	@Column(column = "CONSTRUCTION_UNIT")
	private String constructionUnit;// CONSTRUCTION_UNIT
	@Column(column = "CONSTRUCTION_LOCATION")
	private String constructionLocation;// CONSTRUCTION_LOCATION
	@Column(column = "CONSTRUCTION_UNIT_PERSON")
	private String constructionUnitPerson;// CONSTRUCTION_UNIT_PERSON
	@Column(column = "CONSTRUCTION_UNIT_TEL")
	private String constructionUnitTel;// CONSTRUCTION_UNIT_TEL
	@Column(column = "GET_THINGS_LOCATION")
	private String getThingsLocation;// GET_THINGS_LOCATION
	@Column(column = "BALL_TYPE_COUNT")
	private Integer ballTypeCount;// BALL_TYPE_COUNT
	@Column(column = "NAIL_TYPE_COUNT")
	private Integer nailTypeCount;// NAIL_TYPE_COUNT
	@Column(column = "ELEC_LABEL_COUNT")
	private Integer elecLabelCount;// ELEC_LABEL_COUNT
	@Column(column = "WORK_ZONE")
	private String workZone;// WORK_ZONE
	@Column(column = "LINE_LENGTH")
	private Double lineLength;// LINE_LENGTH
	@Column(column = "PIPE_LENGTH")
	private Double pipeLength;// PIPE_LENGTH
	@Column(column = "PIPE_COUNTS")
	private Integer pipeCounts;// PIPE_COUNTS
	@Column(column = "CAPITAL_CONSTRUCTION_TYPE")
	private Integer capitalConstructionType;// CAPITAL_CONSTRUCTION_TYPE
	@Column(column = "STOCK_TYPE")
	private Integer stockType;// STOCK_TYPE
	@Column(column = "TASK_TYPES")
	private String taskTypes;// TASK_TYPES
	@Column(column = "WORK_STANDARD")
	private Integer workStandard;// WORK_STANDARD

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
