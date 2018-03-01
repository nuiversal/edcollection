package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年1月30日 上午9:58:25
* 电子标签实体
*/
public class ExcelEntity4Label {
	@CellColum(headerName = "标签编号", index = 0)
	private String labelno;
	@CellColum(headerName = "线路编号", index = 1)
	private String lineno;
	@CellColum(headerName = "标识器编号", index = 2)
	private String markerno;
	@CellColum(headerName = "绑扎对象", index = 3)
	private String bindTarget;
	@CellColum(headerName = "设备名称", index = 4)
	private String deviceName;
	@CellColum(headerName = "采集时间", index = 5)
	private Date cjsj;

	public String getLabelno() {
		return labelno;
	}

	public void setLabelno(String labelno) {
		this.labelno = labelno;
	}

	public String getLineno() {
		return lineno;
	}

	public void setLineno(String lineno) {
		this.lineno = lineno;
	}

	public String getMarkerno() {
		return markerno;
	}

	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}

	public String getBindTarget() {
		return bindTarget;
	}

	public void setBindTarget(String bindTarget) {
		this.bindTarget = bindTarget;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

}
