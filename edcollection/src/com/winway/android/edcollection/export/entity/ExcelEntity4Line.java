package com.winway.android.edcollection.export.entity;
/**
* @author winway-laohw
* @time 2018年1月29日 下午1:38:35
*/

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
 * 线路Excel
 * @author mr-lao
 *
 */
public class ExcelEntity4Line {
	@CellColum(headerName = "线路名称", index = 0)
	private String name;
	@CellColum(headerName = "线路编号", index = 1)
	private String lineno;
	@CellColum(headerName = "父线路编号", index = 2)
	private String parentNo;
	@CellColum(headerName = "电压", index = 3)
	private String voltage;
	@CellColum(headerName = "所属变电站编号", index = 4)
	private String startStation;
	@CellColum(headerName = "结束变电站编号", index = 5)
	private String endStation;
	@CellColum(headerName = "主网配网", index = 6)
	private String zhupei;
	@CellColum(headerName = "维护部门", index = 7)
	private String maintenanceDepartment;
	@CellColum(headerName = "设备型号", index = 8)
	private String devicemodel;
	@CellColum(headerName = "产权性质", index = 9)
	private String propertyRights;
	@CellColum(headerName = "生产厂家", index = 10)
	private String produtFactory;
	@CellColum(headerName = "出厂编号", index = 11)
	private String factoryNumber;
	@CellColum(headerName = "投运日期", index = 12)
	private Date commonsionDate;
	@CellColum(headerName = "电缆材质", index = 13)
	private String meterial;
	@CellColum(headerName = "采集时间", index = 14, lenth = 10)
	private Date cjsj;
	@CellColum(headerName = "备注", index = 15)
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLineno() {
		return lineno;
	}

	public void setLineno(String lineno) {
		this.lineno = lineno;
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

	public String getStartStation() {
		return startStation;
	}

	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	public String getEndStation() {
		return endStation;
	}

	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}

	public String getZhupei() {
		return zhupei;
	}

	public void setZhupei(String zhupei) {
		this.zhupei = zhupei;
	}

	public String getMaintenanceDepartment() {
		return maintenanceDepartment;
	}

	public void setMaintenanceDepartment(String maintenanceDepartment) {
		this.maintenanceDepartment = maintenanceDepartment;
	}

	public String getDevicemodel() {
		return devicemodel;
	}

	public void setDevicemodel(String devicemodel) {
		this.devicemodel = devicemodel;
	}

	public String getPropertyRights() {
		return propertyRights;
	}

	public void setPropertyRights(String propertyRights) {
		this.propertyRights = propertyRights;
	}

	public String getProdutFactory() {
		return produtFactory;
	}

	public void setProdutFactory(String produtFactory) {
		this.produtFactory = produtFactory;
	}

	public String getFactoryNumber() {
		return factoryNumber;
	}

	public void setFactoryNumber(String factoryNumber) {
		this.factoryNumber = factoryNumber;
	}

	public Date getCommonsionDate() {
		return commonsionDate;
	}

	public void setCommonsionDate(Date commonsionDate) {
		this.commonsionDate = commonsionDate;
	}

	public String getMeterial() {
		return meterial;
	}

	public void setMeterial(String meterial) {
		this.meterial = meterial;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
