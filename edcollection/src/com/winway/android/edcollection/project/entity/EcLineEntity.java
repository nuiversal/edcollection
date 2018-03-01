package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcLine实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:34
 */
@Table(name = "ec_line")
public class EcLineEntity implements Serializable {

	@Id
	@Column(column = "EC_LINE_ID")
	private String ecLineId;// EC_LINE_ID
	@Column(column = "NAME")
	private String name;// NAME
	@Column(column = "LINE_NO")
	private String lineNo;// LINE_NO
	@Column(column = "PARENT_NO")
	private String parentNo;// PARENT_NO
	@Column(column = "VOLTAGE")
	private String voltage;// VOLTAGE
	@Column(column = "START_STATION_TYPE")
	private Integer startStationType;// START_STATION_TYPE
	@Column(column = "START_STATION")
	private String startStation;// START_STATION
	@Column(column = "END_STATION_TYPE")
	private Integer endStationType;// END_STATION_TYPE
	@Column(column = "END_STATION")
	private String endStation;// END_STATION
	@Column(column = "DRAW_OFFSET")
	private Double drawOffset;// DRAW_OFFSET
	@Column(column = "GROUP_NAME")
	private String groupName;// GROUP_NAME
	@Column(column = "PRJID")
	private String prjid;// PRJID
	@Column(column = "ORGID")
	private String orgid;// ORGID
	@Column(column = "GEOM")
	private String geom;// GEOM
	@Column(column = "MAINTENANCE_DEPARTMENT")
	private String maintenanceDepartment;// MAINTENANCE_DEPARTMENT
	@Column(column = "EQUIPMENT_MODEL")
	private String equipmentModel;// EQUIPMENT_MODEL
	@Column(column = "PROPERTY_RIGHTS")
	private String propertyRights;// PROPERTY_RIGHTS
	@Column(column = "MANUFACTURER")
	private String manufacturer;// MANUFACTURER
	@Column(column = "MANUFACTURING_NUMBER")
	private String manufacturingNumber;// MANUFACTURING_NUMBER
	@Column(column = "COMMISSIONING_DATE")
	private Date commissioningDate;// COMMISSIONING_DATE
	@Column(column = "ACCOMPLISH_DATE")
	private Date accomplishDate;// ACCOMPLISH_DATE
	@Column(column = "DIAGRAM_LENGTH")
	private Double diagramLength;// DIAGRAM_LENGTH
	@Column(column = "MEASURED_LENGTH")
	private Double measuredLength;// MEASURED_LENGTH
	@Column(column = "LAYING_MODE")
	private String layingMode;// LAYING_MODE
	@Column(column = "REMARKS")
	private String remarks;// REMARKS
	@Column(column = "CREATE_TIME")
	private Date createTime;// CREATE_TIME
	@Column(column = "UPDATE_TIME")
	private Date updateTime;// UPDATE_TIME
	@Column(column = "SORT_NUM")
	private Integer sortNum;// SORT_NUM
	@Column(column = "LINE_TYPE")
	private Integer lineType;// LINE_TYPE
	@Column(column = "WIRE_MATERIAL")
	private String wireMaterial;// WIRE_MATERIAL

	public String getEcLineId() {
		return ecLineId;
	}

	public void setEcLineId(String ecLineId) {
		this.ecLineId = ecLineId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public Integer getStartStationType() {
		return startStationType;
	}

	public void setStartStationType(Integer startStationType) {
		this.startStationType = startStationType;
	}

	public String getStartStation() {
		return startStation;
	}

	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	public Integer getEndStationType() {
		return endStationType;
	}

	public void setEndStationType(Integer endStationType) {
		this.endStationType = endStationType;
	}

	public String getEndStation() {
		return endStation;
	}

	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}

	public Double getDrawOffset() {
		return drawOffset;
	}

	public void setDrawOffset(Double drawOffset) {
		this.drawOffset = drawOffset;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public String getMaintenanceDepartment() {
		return maintenanceDepartment;
	}

	public void setMaintenanceDepartment(String maintenanceDepartment) {
		this.maintenanceDepartment = maintenanceDepartment;
	}

	public String getEquipmentModel() {
		return equipmentModel;
	}

	public void setEquipmentModel(String equipmentModel) {
		this.equipmentModel = equipmentModel;
	}

	public String getPropertyRights() {
		return propertyRights;
	}

	public void setPropertyRights(String propertyRights) {
		this.propertyRights = propertyRights;
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

	public Date getCommissioningDate() {
		return commissioningDate;
	}

	public void setCommissioningDate(Date commissioningDate) {
		this.commissioningDate = commissioningDate;
	}

	public Date getAccomplishDate() {
		return accomplishDate;
	}

	public void setAccomplishDate(Date accomplishDate) {
		this.accomplishDate = accomplishDate;
	}

	public Double getDiagramLength() {
		return diagramLength;
	}

	public void setDiagramLength(Double diagramLength) {
		this.diagramLength = diagramLength;
	}

	public Double getMeasuredLength() {
		return measuredLength;
	}

	public void setMeasuredLength(Double measuredLength) {
		this.measuredLength = measuredLength;
	}

	public String getLayingMode() {
		return layingMode;
	}

	public void setLayingMode(String layingMode) {
		this.layingMode = layingMode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public Integer getLineType() {
		return lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}

	public String getWireMaterial() {
		return wireMaterial;
	}

	public void setWireMaterial(String wireMaterial) {
		this.wireMaterial = wireMaterial;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}

}
