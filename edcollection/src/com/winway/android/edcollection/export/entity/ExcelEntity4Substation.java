package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年1月29日 下午1:38:09
* 变电站Excel
*/
public class ExcelEntity4Substation {
	@CellColum(headerName = "变电站名称", otherHeaderNames = "stationname(变电站名称)", index = 0, lenth = 16)
	private String stationName;
	@CellColum(headerName = "编号", otherHeaderNames = "station_no(变电站编号)", index = 1, lenth = 16)
	private String stationNo;
	@CellColum(headerName = "电压等级", otherHeaderNames = "voltage(电压等级[kV])", index = 2, lenth = 5)
	private String votage;
	@CellColum(headerName = "产权性质", index = 3, lenth = 8)
	private String rightPro;
	@CellColum(headerName = "路径点编号", otherHeaderNames = "node_no(路径点编号)", index = 4, lenth = 16)
	private String nodeno;
	@CellColum(headerName = "采集时间", index = 5, lenth = 10)
	private Date cjsj;
	@CellColum(headerName = "备注", index = 6)
	private String remark;

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationNo() {
		return stationNo;
	}

	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}

	public String getVotage() {
		return votage;
	}

	public void setVotage(String votage) {
		this.votage = votage;
	}

	public String getRightPro() {
		return rightPro;
	}

	public void setRightPro(String rightPro) {
		this.rightPro = rightPro;
	}

	public String getNodeno() {
		return nodeno;
	}

	public void setNodeno(String nodeno) {
		this.nodeno = nodeno;
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
