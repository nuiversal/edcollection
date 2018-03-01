package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年1月30日 上午11:28:46
* Excel箱式变电站
*/
public class ExcelEntity4Xsbdz {
	@CellColum(headerName = "箱式变电站名称", index = 0)
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
	@CellColum(headerName = "是否代维", index = 9)
	private String sfdw;
	@CellColum(headerName = "是否农网", index = 10)
	private String sfnw;
	@CellColum(headerName = "箱变类型", index = 11)
	private String xblx;
	@CellColum(headerName = "是否具有环网", index = 12)
	private String sfjyhw;
	@CellColum(headerName = "型号", index = 13)
	private String xh;
	@CellColum(headerName = "生产厂家", index = 14)
	private String sccj;
	@CellColum(headerName = "出厂编号", index = 15)
	private String ccbh;
	@CellColum(headerName = "出厂日期", index = 16)
	private Date ccrq;
	@CellColum(headerName = "投运日期", index = 17)
	private Date tyrq;
	@CellColum(headerName = "配变总容量", index = 18)
	private String pbzrl;
	@CellColum(headerName = "接地电阻(Ω)", index = 19)
	private int jddz;
	@CellColum(headerName = "站址", index = 20)
	private String zz;
	@CellColum(headerName = "地区特征", index = 21)
	private String dqtz;
	@CellColum(headerName = "重要程度", index = 22)
	private String zycd;
	@CellColum(headerName = "资产性质", index = 23)
	private String zcxz;
	@CellColum(headerName = "资产单位", index = 24)
	private String zcdw;
	@CellColum(headerName = "设备增加方式", index = 25)
	private String sbzjfs;
	@CellColum(headerName = "备注", index = 26)
	private String bz;

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

	public String getXblx() {
		return xblx;
	}

	public void setXblx(String xblx) {
		this.xblx = xblx;
	}

	public String getSfjyhw() {
		return sfjyhw;
	}

	public void setSfjyhw(String sfjyhw) {
		this.sfjyhw = sfjyhw;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getSccj() {
		return sccj;
	}

	public void setSccj(String sccj) {
		this.sccj = sccj;
	}

	public String getCcbh() {
		return ccbh;
	}

	public void setCcbh(String ccbh) {
		this.ccbh = ccbh;
	}

	public Date getCcrq() {
		return ccrq;
	}

	public void setCcrq(Date ccrq) {
		this.ccrq = ccrq;
	}

	public Date getTyrq() {
		return tyrq;
	}

	public void setTyrq(Date tyrq) {
		this.tyrq = tyrq;
	}

	public String getPbzrl() {
		return pbzrl;
	}

	public void setPbzrl(String pbzrl) {
		this.pbzrl = pbzrl;
	}

	public int getJddz() {
		return jddz;
	}

	public void setJddz(int jddz) {
		this.jddz = jddz;
	}

	public String getZz() {
		return zz;
	}

	public void setZz(String zz) {
		this.zz = zz;
	}

	public String getDqtz() {
		return dqtz;
	}

	public void setDqtz(String dqtz) {
		this.dqtz = dqtz;
	}

	public String getZycd() {
		return zycd;
	}

	public void setZycd(String zycd) {
		this.zycd = zycd;
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

	public String getSbzjfs() {
		return sbzjfs;
	}

	public void setSbzjfs(String sbzjfs) {
		this.sbzjfs = sbzjfs;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}
