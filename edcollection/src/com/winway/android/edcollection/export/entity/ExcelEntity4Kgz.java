package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年1月30日 上午11:28:46
* Excel开关站
*/
public class ExcelEntity4Kgz {
	@CellColum(headerName = "开关名称", index = 0)
	private String deviceName;
	@CellColum(headerName = "标识器编号", index = 1)
	private String markerno;
	@CellColum(headerName = "运行编号", index = 2)
	private String yxbh;
	@CellColum(headerName = "电系铭牌ID", index = 3)
	private String dxmpid;
	@CellColum(headerName = "电压等级", index = 4)
	private String dydj;
	@CellColum(headerName = "所属地市", index = 5)
	private String ssds;
	@CellColum(headerName = "运维单位", index = 6)
	private String ywdw;
	@CellColum(headerName = "维护班组", index = 7)
	private String hwbz;
	@CellColum(headerName = "设备状态", index = 8)
	private String sbzt;
	@CellColum(headerName = "投运日期", index = 9)
	private Date tyrq;
	@CellColum(headerName = "资产性质", index = 10)
	private String zcxz;
	@CellColum(headerName = "资产单位", index = 11)
	private String zcdw;
	@CellColum(headerName = "是否智能变电站", index = 12)
	private String sfznbdz;
	@CellColum(headerName = "备用进出线间隔数", index = 13)
	private String byjcxjg;
	@CellColum(headerName = "防误方式", index = 14)
	private String fwfs;
	@CellColum(headerName = "电站重要级别", index = 15)
	private String dzzyjb;
	@CellColum(headerName = "是否代维", index = 16)
	private String sfdw;
	@CellColum(headerName = "是否农网", index = 17)
	private String sfnw;
	@CellColum(headerName = "值班方式", index = 18)
	private String zbfs;
	@CellColum(headerName = "布置方式", index = 19)
	private String bzfs;
	@CellColum(headerName = "污秽等级", index = 20)
	private String whdj;
	@CellColum(headerName = "站址", index = 21)
	private String zz;
	@CellColum(headerName = "采集时间", index = 22)
	private Date cjsj;
	@CellColum(headerName = "备注", index = 23)
	private String remark;
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getMarkerno() {
		return markerno;
	}
	
	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}
	
	public String getYxbh() {
		return yxbh;
	}
	
	public void setYxbh(String yxbh) {
		this.yxbh = yxbh;
	}
	
	public String getDxmpid() {
		return dxmpid;
	}
	
	public void setDxmpid(String dxmpid) {
		this.dxmpid = dxmpid;
	}
	
	public String getDydj() {
		return dydj;
	}
	
	public void setDydj(String dydj) {
		this.dydj = dydj;
	}
	
	public String getSsds() {
		return ssds;
	}
	
	public void setSsds(String ssds) {
		this.ssds = ssds;
	}
	
	public String getYwdw() {
		return ywdw;
	}
	
	public void setYwdw(String ywdw) {
		this.ywdw = ywdw;
	}
	
	public String getHwbz() {
		return hwbz;
	}
	
	public void setHwbz(String hwbz) {
		this.hwbz = hwbz;
	}
	
	public String getSbzt() {
		return sbzt;
	}
	
	public void setSbzt(String sbzt) {
		this.sbzt = sbzt;
	}
	
	public Date getTyrq() {
		return tyrq;
	}
	
	public void setTyrq(Date tyrq) {
		this.tyrq = tyrq;
	}
	
	public String getZcxz() {
		return zcxz;
	}
	
	public void setZcxz(String zcxz) {
		this.zcxz = zcxz;
	}
	
	public String getZcdw() {
		return zcdw;
	}
	
	public void setZcdw(String zcdw) {
		this.zcdw = zcdw;
	}
	
	public String getSfznbdz() {
		return sfznbdz;
	}
	
	public void setSfznbdz(String sfznbdz) {
		this.sfznbdz = sfznbdz;
	}
	
	public String getByjcxjg() {
		return byjcxjg;
	}
	
	public void setByjcxjg(String byjcxjg) {
		this.byjcxjg = byjcxjg;
	}
	
	public String getFwfs() {
		return fwfs;
	}
	
	public void setFwfs(String fwfs) {
		this.fwfs = fwfs;
	}
	
	public String getDzzyjb() {
		return dzzyjb;
	}
	
	public void setDzzyjb(String dzzyjb) {
		this.dzzyjb = dzzyjb;
	}
	
	public String getSfdw() {
		return sfdw;
	}
	
	public void setSfdw(String sfdw) {
		this.sfdw = sfdw;
	}
	
	public String getSfnw() {
		return sfnw;
	}
	
	public void setSfnw(String sfnw) {
		this.sfnw = sfnw;
	}
	
	public String getZbfs() {
		return zbfs;
	}
	
	public void setZbfs(String zbfs) {
		this.zbfs = zbfs;
	}
	
	public String getBzfs() {
		return bzfs;
	}
	
	public void setBzfs(String bzfs) {
		this.bzfs = bzfs;
	}
	
	public String getWhdj() {
		return whdj;
	}
	
	public void setWhdj(String whdj) {
		this.whdj = whdj;
	}
	
	public String getZz() {
		return zz;
	}
	
	public void setZz(String zz) {
		this.zz = zz;
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
