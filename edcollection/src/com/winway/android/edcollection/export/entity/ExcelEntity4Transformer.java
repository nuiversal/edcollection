package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
* @author yzm
* 
* Excel变压室4.0实体
*/
public class ExcelEntity4Transformer {
	
	@CellColum(headerName = "标识器编号", index = 0)
	private String markerno;

	@CellColum(headerName = "创建时间", index = 1)
	private Date  createTime;			// 创建时间
	
	@CellColum(headerName = "设备名称", index = 2)
	private String  sbmc;					// 设备名称
	
	@CellColum(headerName = "维护部门", index = 3)
	private String  maintenanceDepartment;	// 维护部门
	
	@CellColum(headerName = "电压等级", index = 4)
	private String  voltage;				// 电压等级
	
	@CellColum(headerName = "运行状态", index = 5)
	private String  runningStatus;			// 运行状态
	
	@CellColum(headerName = "所属站房", index = 6)
	private String  stationHouse;			// 所属站房
	
	@CellColum(headerName = "所属间隔", index = 7)
	private String  space;					// 所属间隔
	
	@CellColum(headerName = "设备型号", index = 8)
	private String  equipmentModel;			// 设备型号
	
	@CellColum(headerName = "额定容量", index = 9)
	private Integer  ratedCapacity;			// 额定容量
	
	@CellColum(headerName = "产权性质", index = 10)
	private String  propertyRights;			// 产权性质
	
	@CellColum(headerName = "投运日期", index = 11)
	private Date  commissioningDate;		// 投运日期
	
	@CellColum(headerName = "生产厂家", index = 12)
	private String  manufacturer;			// 生产厂家
	
	@CellColum(headerName = "出厂编号", index = 13)
	private String  manufacturingNumber;	// 出厂编号
	
	@CellColum(headerName = "用户重要等级", index = 14)
	private String  userImportantGrade;		// 用户重要等级
	
	@CellColum(headerName = "专/公标志", index = 15)
	private String  zgMark;					// 专/公标志
	
	@CellColum(headerName = "客户名称", index = 16)
	private String  customerName;			// 客户名称
	
	@CellColum(headerName = "客户编号", index = 17)
	private String  customerNumber;			// 客户编号
	
	@CellColum(headerName = "空载电流(A)", index = 18)
	private Integer  noLoadCurrent;			// 空载电流(A)
	
	@CellColum(headerName = "空载损耗(kW)", index = 19)
	private Integer  noLoadLoss;			// 空载损耗(kW)
	
	@CellColum(headerName = "负载损耗(kW)", index = 20)
	private Integer  loadLoss;				// 负载损耗(kW)
	
	@CellColum(headerName = "备注", index = 21)
	private String  remarks;				// 备注	
	
	public String getMarkerno() {
		return markerno;
	}

	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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