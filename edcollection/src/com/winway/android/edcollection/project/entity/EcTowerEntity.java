package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcTower实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:40
 */
@Table(name = "ec_tower")
public class EcTowerEntity implements Serializable{

        @Id
        @Column(column = "OBJ_ID")
        private String objId;//OBJ_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "CREATE_TIME")
        private Date createTime;//CREATE_TIME
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//UPDATE_TIME
        @Column(column = "TOWER_NO")
        private String towerNo;//TOWER_NO
        @Column(column = "MAINTENANCE_DEPARTMENT")
        private String maintenanceDepartment;//MAINTENANCE_DEPARTMENT
        @Column(column = "RUNNING_STATUS")
        private String runningStatus;//RUNNING_STATUS
        @Column(column = "PROPERTY_RIGHTS")
        private String propertyRights;//PROPERTY_RIGHTS
        @Column(column = "COMMISSIONING_DATE")
        private Date commissioningDate;//COMMISSIONING_DATE
        @Column(column = "MANUFACTURER")
        private String manufacturer;//MANUFACTURER
        @Column(column = "EQUIPMENT_MODEL")
        private String equipmentModel;//EQUIPMENT_MODEL
        @Column(column = "MANUFACTURING_NUMBER")
        private String manufacturingNumber;//MANUFACTURING_NUMBER
        @Column(column = "ACCOMPLISH_DATE")
        private Date accomplishDate;//ACCOMPLISH_DATE
        @Column(column = "MATERIAL")
        private String material;//MATERIAL
        @Column(column = "CORNER_DEGREE")
        private String cornerDegree;//CORNER_DEGREE
        @Column(column = "HEIGHT")
        private Double height;//HEIGHT
        @Column(column = "REMARKS")
        private String remarks;//REMARKS
    
        
        public String getObjId() {
		    return objId;
	    }
        
	    public void setObjId(String objId) {
		    this.objId = objId;
	    }
        
        public String getPrjid() {
		    return prjid;
	    }
        
	    public void setPrjid(String prjid) {
		    this.prjid = prjid;
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
        
        public String getTowerNo() {
		    return towerNo;
	    }
        
	    public void setTowerNo(String towerNo) {
		    this.towerNo = towerNo;
	    }
        
        public String getMaintenanceDepartment() {
		    return maintenanceDepartment;
	    }
        
	    public void setMaintenanceDepartment(String maintenanceDepartment) {
		    this.maintenanceDepartment = maintenanceDepartment;
	    }
        
        public String getRunningStatus() {
		    return runningStatus;
	    }
        
	    public void setRunningStatus(String runningStatus) {
		    this.runningStatus = runningStatus;
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
        
        public String getEquipmentModel() {
		    return equipmentModel;
	    }
        
	    public void setEquipmentModel(String equipmentModel) {
		    this.equipmentModel = equipmentModel;
	    }
        
        public String getManufacturingNumber() {
		    return manufacturingNumber;
	    }
        
	    public void setManufacturingNumber(String manufacturingNumber) {
		    this.manufacturingNumber = manufacturingNumber;
	    }
        
        public Date getAccomplishDate() {
		    return accomplishDate;
	    }
        
	    public void setAccomplishDate(Date accomplishDate) {
		    this.accomplishDate = accomplishDate;
	    }
        
        public String getMaterial() {
		    return material;
	    }
        
	    public void setMaterial(String material) {
		    this.material = material;
	    }
        
        public String getCornerDegree() {
		    return cornerDegree;
	    }
        
	    public void setCornerDegree(String cornerDegree) {
		    this.cornerDegree = cornerDegree;
	    }
        
        public Double getHeight() {
		    return height;
	    }
        
	    public void setHeight(Double height) {
		    this.height = height;
	    }
        
        public String getRemarks() {
		    return remarks;
	    }
        
	    public void setRemarks(String remarks) {
		    this.remarks = remarks;
	    }
    
}
 
