package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcTransformer实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:42
 */
@Table(name = "ec_transformer")
public class EcTransformerEntity implements Serializable{

        @Id
        @Column(column = "OBJ_ID")
        private String objId;//OBJ_ID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "CREATE_TIME")
        private Date createTime;//CREATE_TIME
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//UPDATE_TIME
        @Column(column = "SBMC")
        private String sbmc;//SBMC
        @Column(column = "MAINTENANCE_DEPARTMENT")
        private String maintenanceDepartment;//MAINTENANCE_DEPARTMENT
        @Column(column = "VOLTAGE")
        private String voltage;//VOLTAGE
        @Column(column = "RUNNING_STATUS")
        private String runningStatus;//RUNNING_STATUS
        @Column(column = "STATION_HOUSE")
        private String stationHouse;//STATION_HOUSE
        @Column(column = "SPACE")
        private String space;//SPACE
        @Column(column = "EQUIPMENT_MODEL")
        private String equipmentModel;//EQUIPMENT_MODEL
        @Column(column = "RATED_CAPACITY")
        private Integer ratedCapacity;//RATED_CAPACITY
        @Column(column = "PROPERTY_RIGHTS")
        private String propertyRights;//PROPERTY_RIGHTS
        @Column(column = "COMMISSIONING_DATE")
        private Date commissioningDate;//COMMISSIONING_DATE
        @Column(column = "MANUFACTURER")
        private String manufacturer;//MANUFACTURER
        @Column(column = "MANUFACTURING_NUMBER")
        private String manufacturingNumber;//MANUFACTURING_NUMBER
        @Column(column = "USER_IMPORTANT_GRADE")
        private String userImportantGrade;//USER_IMPORTANT_GRADE
        @Column(column = "ZG_MARK")
        private String zgMark;//ZG_MARK
        @Column(column = "CUSTOMER_NAME")
        private String customerName;//CUSTOMER_NAME
        @Column(column = "CUSTOMER_NUMBER")
        private String customerNumber;//CUSTOMER_NUMBER
        @Column(column = "NO_LOAD_CURRENT")
        private Integer noLoadCurrent;//NO_LOAD_CURRENT
        @Column(column = "NO_LOAD_LOSS")
        private Integer noLoadLoss;//NO_LOAD_LOSS
        @Column(column = "LOAD_LOSS")
        private Integer loadLoss;//LOAD_LOSS
        @Column(column = "REMARKS")
        private String remarks;//REMARKS
    
        
        public String getObjId() {
		    return objId;
	    }
        
	    public void setObjId(String objId) {
		    this.objId = objId;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
        
        public String getPrjid() {
		    return prjid;
	    }
        
	    public void setPrjid(String prjid) {
		    this.prjid = prjid;
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
        
        public String getSbmc() {
		    return sbmc;
	    }
        
	    public void setSbmc(String sbmc) {
		    this.sbmc = sbmc;
	    }
        
        public String getMaintenanceDepartment() {
		    return maintenanceDepartment;
	    }
        
	    public void setMaintenanceDepartment(String maintenanceDepartment) {
		    this.maintenanceDepartment = maintenanceDepartment;
	    }
        
        public String getVoltage() {
		    return voltage;
	    }
        
	    public void setVoltage(String voltage) {
		    this.voltage = voltage;
	    }
        
        public String getRunningStatus() {
		    return runningStatus;
	    }
        
	    public void setRunningStatus(String runningStatus) {
		    this.runningStatus = runningStatus;
	    }
        
        public String getStationHouse() {
		    return stationHouse;
	    }
        
	    public void setStationHouse(String stationHouse) {
		    this.stationHouse = stationHouse;
	    }
        
        public String getSpace() {
		    return space;
	    }
        
	    public void setSpace(String space) {
		    this.space = space;
	    }
        
        public String getEquipmentModel() {
		    return equipmentModel;
	    }
        
	    public void setEquipmentModel(String equipmentModel) {
		    this.equipmentModel = equipmentModel;
	    }
        
        public Integer getRatedCapacity() {
		    return ratedCapacity;
	    }
        
	    public void setRatedCapacity(Integer ratedCapacity) {
		    this.ratedCapacity = ratedCapacity;
	    }
        
        public String getPropertyRights() {
		    return propertyRights;
	    }
        
	    public void setPropertyRights(String propertyRights) {
		    this.propertyRights = propertyRights;
	    }
        
        public Date getCommissioningDate() {
		    return commissioningDate;
	    }
        
	    public void setCommissioningDate(Date commissioningDate) {
		    this.commissioningDate = commissioningDate;
	    }
        
        public String getManufacturer() {
		    return manufacturer;
	    }
        
	    public void setManufacturer(String manufacturer) {
		    this.manufacturer = manufacturer;
	    }
        
        public String getManufacturingNumber() {
		    return manufacturingNumber;
	    }
        
	    public void setManufacturingNumber(String manufacturingNumber) {
		    this.manufacturingNumber = manufacturingNumber;
	    }
        
        public String getUserImportantGrade() {
		    return userImportantGrade;
	    }
        
	    public void setUserImportantGrade(String userImportantGrade) {
		    this.userImportantGrade = userImportantGrade;
	    }
        
        public String getZgMark() {
		    return zgMark;
	    }
        
	    public void setZgMark(String zgMark) {
		    this.zgMark = zgMark;
	    }
        
        public String getCustomerName() {
		    return customerName;
	    }
        
	    public void setCustomerName(String customerName) {
		    this.customerName = customerName;
	    }
        
        public String getCustomerNumber() {
		    return customerNumber;
	    }
        
	    public void setCustomerNumber(String customerNumber) {
		    this.customerNumber = customerNumber;
	    }
        
        public Integer getNoLoadCurrent() {
		    return noLoadCurrent;
	    }
        
	    public void setNoLoadCurrent(Integer noLoadCurrent) {
		    this.noLoadCurrent = noLoadCurrent;
	    }
        
        public Integer getNoLoadLoss() {
		    return noLoadLoss;
	    }
        
	    public void setNoLoadLoss(Integer noLoadLoss) {
		    this.noLoadLoss = noLoadLoss;
	    }
        
        public Integer getLoadLoss() {
		    return loadLoss;
	    }
        
	    public void setLoadLoss(Integer loadLoss) {
		    this.loadLoss = loadLoss;
	    }
        
        public String getRemarks() {
		    return remarks;
	    }
        
	    public void setRemarks(String remarks) {
		    this.remarks = remarks;
	    }
    
}
 
