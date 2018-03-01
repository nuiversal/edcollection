package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
*@author yzm
* 
* Excel井盖4.0实体
*/
public class ExcelEntity4WorkWellCover {
	
	@CellColum(headerName = "标识器编号", index = 0)
	private String markerno;

	@CellColum(headerName = "序号", index = 1)
	private String  xh;			// 序号
	
	@CellColum(headerName = "设备名称", index = 2)
	private String  sbmc;		// 设备名称
	
	@CellColum(headerName = "所属工井", index = 3)
	private String  ssgj;		// 所属工井
	
	@CellColum(headerName = "标志", index = 4)
	private String  biz;		// 标志
	
	@CellColum(headerName = "运维单位", index = 5)
	private String  ywdw;		// 运维单位
	
	@CellColum(headerName = "维护班组", index = 6)
	private String  whbz;		// 维护班组
	
	@CellColum(headerName = "所属责任区", index = 7)
	private String  sszrq;		// 所属责任区
	
	@CellColum(headerName = "井盖长度", index = 8)
	private String  jgcd;	// 井盖长度
	
	@CellColum(headerName = "井盖宽度", index = 9)
	private String  jgkd;	// 井盖宽度
	
	@CellColum(headerName = "井盖厚度", index = 10)
	private String  jghd;	// 井盖厚度
	
	@CellColum(headerName = "采集时间", index = 11)
	private Date  cjsj;	// 采集时间
	
	@CellColum(headerName = "备注", index = 12)
	private String  bz;			// 备注
	
	public String getMarkerno() {
		return markerno;
	}

	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getSbmc() {
		return sbmc;
	}

	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
	}

	public String getSsgj() {
		return ssgj;
	}

	public void setSsgj(String ssgj) {
		this.ssgj = ssgj;
	}

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	public String getYwdw() {
		return ywdw;
	}

	public void setYwdw(String ywdw) {
		this.ywdw = ywdw;
	}

	public String getWhbz() {
		return whbz;
	}

	public void setWhbz(String whbz) {
		this.whbz = whbz;
	}

	public String getSszrq() {
		return sszrq;
	}

	public void setSszrq(String sszrq) {
		this.sszrq = sszrq;
	}

	public String getJgcd() {
		return jgcd;
	}

	public void setJgcd(String jgcd) {
		this.jgcd = jgcd;
	}

	public String getJgkd() {
		return jgkd;
	}

	public void setJgkd(String jgkd) {
		this.jgkd = jgkd;
	}

	public String getJghd() {
		return jghd;
	}

	public void setJghd(String jghd) {
		this.jghd = jghd;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
}