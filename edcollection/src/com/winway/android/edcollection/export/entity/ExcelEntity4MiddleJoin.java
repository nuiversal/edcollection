package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年1月30日 上午10:53:04
* Excel中间接头
*/
public class ExcelEntity4MiddleJoin {
	@CellColum(headerName = "标识器编号", index = 0)
	private String markerno;
	@CellColum(headerName = "线路编号", index = 1)
	private String lineno;
	@CellColum(headerName = "设备名称", index = 2)
	private String deviceName;
	@CellColum(headerName = "设备型号", index = 3)
	private String devMode;
	@CellColum(headerName = "工艺特征", index = 4)
	private String techFeature;
	@CellColum(headerName = "生产厂家", index = 5)
	private String productFactory;
	@CellColum(headerName = "生产日期", index = 6)
	private Date productTime;
	@CellColum(headerName = "投运日期", index = 7)
	private Date useDate;
	@CellColum(headerName = "安装单位", index = 8)
	private String instalUnit;
	@CellColum(headerName = "安装人员", index = 9)
	private String instalPerson;
	@CellColum(headerName = "采集时间", index = 10)
	private Date cjsj;
	@CellColum(headerName = "备注", index = 11)
	private String remark;

	public String getMarkerno() {
		return markerno;
	}

	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}

	public String getLineno() {
		return lineno;
	}

	public void setLineno(String lineno) {
		this.lineno = lineno;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDevMode() {
		return devMode;
	}

	public void setDevMode(String devMode) {
		this.devMode = devMode;
	}

	public String getTechFeature() {
		return techFeature;
	}

	public void setTechFeature(String techFeature) {
		this.techFeature = techFeature;
	}

	public String getProductFactory() {
		return productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	public Date getProductTime() {
		return productTime;
	}

	public void setProductTime(Date productTime) {
		this.productTime = productTime;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getInstalUnit() {
		return instalUnit;
	}

	public void setInstalUnit(String instalUnit) {
		this.instalUnit = instalUnit;
	}

	public String getInstalPerson() {
		return instalPerson;
	}

	public void setInstalPerson(String instalPerson) {
		this.instalPerson = instalPerson;
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
