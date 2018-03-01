package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcLineBranch实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:31
 */
@Table(name = "ec_line_branch")
public class EcLineBranchEntity implements Serializable{

        @Id
        @Column(column = "EC_LINE_BRANCH_ID")
        private String ecLineBranchId;//线路分支ID
        @Column(column = "ORGID")
        private String orgid;//机构ID
        @Column(column = "CREATE_TIME")
        private Date createTime;//创建时间
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//更新时间
        @Column(column = "SORT_NUM")
        private Integer sortNum;//序号
        @Column(column = "STATION_ID")
        private String stationId;//所属变电站ID
        @Column(column = "STATION_NAME")
        private String stationName;//所属变电站
        @Column(column = "FEEDER")
        private String feeder;//所属馈线
        @Column(column = "PARENT_NAME")
        private String parentName;//父分支名称
        @Column(column = "NAME")
        private String name;//分支名称
        @Column(column = "RUNNING_STATUS")
        private String runningStatus;//运行状态
        @Column(column = "COMMISSIONING_DATE")
        private Date commissioningDate;//投产日期
        @Column(column = "LINE_TYPE")
        private String lineType;//线路类别
        @Column(column = "LINE_FUNCTION")
        private String lineFunction;//线路功能
        @Column(column = "PROPERTY_RIGHTS")
        private String propertyRights;//产权性质
        @Column(column = "DRAW_OFFSET")
        private Double drawOffset;//线路偏移
        @Column(column = "TOTAL_LENGTH")
        private Double totalLength;//总长度（km）
        @Column(column = "OVERHEAD_LENGTH")
        private Double overheadLength;//架空长度（km）
        @Column(column = "CABLE_LENGTH")
        private Double cableLength;//电缆长度（km）
        @Column(column = "REMARKS")
        private String remarks;//备注
    
        
        public String getEcLineBranchId() {
		    return ecLineBranchId;
	    }
        
	    public void setEcLineBranchId(String ecLineBranchId) {
		    this.ecLineBranchId = ecLineBranchId;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
        
        public Date getCreateTime() {
		    return createTime;
	    }
        
	    public void setCreateTime(Date createTime) {
		    this.createTime = createTime;
	    }
        
        public Date getUpdateTime() {
		    return updateTime;
	    }
        
	    public void setUpdateTime(Date updateTime) {
		    this.updateTime = updateTime;
	    }
        
        public Integer getSortNum() {
		    return sortNum;
	    }
        
	    public void setSortNum(Integer sortNum) {
		    this.sortNum = sortNum;
	    }
        
        public String getStationId() {
		    return stationId;
	    }
        
	    public void setStationId(String stationId) {
		    this.stationId = stationId;
	    }
        
        public String getStationName() {
		    return stationName;
	    }
        
	    public void setStationName(String stationName) {
		    this.stationName = stationName;
	    }
        
        public String getFeeder() {
		    return feeder;
	    }
        
	    public void setFeeder(String feeder) {
		    this.feeder = feeder;
	    }
        
        public String getParentName() {
		    return parentName;
	    }
        
	    public void setParentName(String parentName) {
		    this.parentName = parentName;
	    }
        
        public String getName() {
		    return name;
	    }
        
	    public void setName(String name) {
		    this.name = name;
	    }
        
        public String getRunningStatus() {
		    return runningStatus;
	    }
        
	    public void setRunningStatus(String runningStatus) {
		    this.runningStatus = runningStatus;
	    }
        
        public Date getCommissioningDate() {
		    return commissioningDate;
	    }
        
	    public void setCommissioningDate(Date commissioningDate) {
		    this.commissioningDate = commissioningDate;
	    }
        
        public String getLineType() {
		    return lineType;
	    }
        
	    public void setLineType(String lineType) {
		    this.lineType = lineType;
	    }
        
        public String getLineFunction() {
		    return lineFunction;
	    }
        
	    public void setLineFunction(String lineFunction) {
		    this.lineFunction = lineFunction;
	    }
        
        public String getPropertyRights() {
		    return propertyRights;
	    }
        
	    public void setPropertyRights(String propertyRights) {
		    this.propertyRights = propertyRights;
	    }
        
        public Double getDrawOffset() {
		    return drawOffset;
	    }
        
	    public void setDrawOffset(Double drawOffset) {
		    this.drawOffset = drawOffset;
	    }
        
        public Double getTotalLength() {
		    return totalLength;
	    }
        
	    public void setTotalLength(Double totalLength) {
		    this.totalLength = totalLength;
	    }
        
        public Double getOverheadLength() {
		    return overheadLength;
	    }
        
	    public void setOverheadLength(Double overheadLength) {
		    this.overheadLength = overheadLength;
	    }
        
        public Double getCableLength() {
		    return cableLength;
	    }
        
	    public void setCableLength(Double cableLength) {
		    this.cableLength = cableLength;
	    }
        
        public String getRemarks() {
		    return remarks;
	    }
        
	    public void setRemarks(String remarks) {
		    this.remarks = remarks;
	    }
    
}
 
