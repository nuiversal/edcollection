package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
* @author yzm
* 
* Excel杆塔4.0实体
*/
public class ExcelEntity4Tower {

	@CellColum(headerName = "标识器编号", index = 0)
	private String markerno;

	@CellColum(headerName = "创建时间", index = 1)
	private Date  createTime;	// 创建时间
	
	@CellColum(headerName = "杆塔编号", index = 2)
	private String  towerNo;		// 杆塔编号
	
	@CellColum(headerName = "维护部门", index = 3)
	private String  maintenanceDepartment;// 维护部门
	
	@CellColum(headerName = "运行状态", index = 4)
	private String  runningStatus;	// 运行状态
	
	@CellColum(headerName = "产权性质", index = 5)
	private String propertyRights;	// 产权性质
	
	@CellColum(headerName = "投运日期", index = 6)
	private Date  commissioningDate;// 投运日期
	
	@CellColum(headerName = "生产厂家", index = 7)
	private String  manufacturer;	// 生产厂家
	
	@CellColum(headerName = "设备型号", index = 8)
	private String  equipmentModel;	// 设备型号
	
	@CellColum(headerName = "出厂编号", index = 9)
	private String  manufacturingNumber;// 出厂编号
	
	@CellColum(headerName = "出厂日期", index = 10)
	private Date  accomplishDate;	// 出厂日期
	
	@CellColum(headerName = "杆塔材质", index = 11)
	private String  material;		// 杆塔材质
	
	@CellColum(headerName = "转角度数", index = 12)
	private String  cornerDegree;	// 转角度数
	
	@CellColum(headerName = "杆塔全高(m)", index = 13)
	private Float  height;			// 杆塔全高(m)
	
	@CellColum(headerName = "备注", index = 14)
	private String  remarks;		// 备注
	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMarkerno() {
		return markerno;
	}

	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}
	
}