package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
*@author yzm
* 
* Excel低压电缆分支箱4.0实体
*/
public class ExcelEntity4Dydlfzx {
	
	@CellColum(headerName = "标识器编号", index = 0)
	private String markerno;

	@CellColum(headerName = "设备标识", index = 1)
	private String  sbid;	// 设备标识
	 
	@CellColum(headerName = "设备名称", index = 2)
	private String  sbmc;	// 设备名称
	 
	@CellColum(headerName = "运行编号", index = 3)
	private String  yxbh;	// 运行编号
	 
	@CellColum(headerName = "电系铭牌", index = 4)
	private String  dxmpid;// 电系铭牌
	 
	@CellColum(headerName = "电压等级", index = 5)
	private String  dldj;	// 电压等级
	 
	@CellColum(headerName = "所属地市", index = 6)
	private String  ssds;	// 所属地市
	 
	@CellColum(headerName = "运维单位", index = 7)
	private String  ywdw;	// 运维单位
	 
	@CellColum(headerName = "维护班组", index = 8)
	private String  whbz;	// 维护班组
	 
	@CellColum(headerName = "设备类型", index = 9)
	private String  sblx;	// 设备类型
	 
	@CellColum(headerName = "投运日期", index = 10)
	private Date  tyrq;	// 投运日期
	 
	@CellColum(headerName = "资产性质", index = 11)
	private String  sbzt;	// 设备状态
	 
	@CellColum(headerName = "资产性质", index = 12)
	private String  zcxz;	// 资产性质
	 
	@CellColum(headerName = "资产单位", index = 13)
	private String  zcdw;	// 资产单位
	 
	@CellColum(headerName = "型号", index = 14)
	private String  xh;	// 型号
	 
	@CellColum(headerName = "生产厂家", index = 15)
	private String  sccj;	// 生产厂家
	 
	@CellColum(headerName = "出厂编号", index = 16)
	private String  ccbh;	// 出厂编号
	 
	@CellColum(headerName = "出厂日期", index = 17)
	private Date  ccrq;	// 出厂日期
	 
	@CellColum(headerName = "额定电压", index = 18)
	private String  eddy;	// 额定电压
	 
	@CellColum(headerName = "额定电流", index = 19)
	private String  eddl;	// 额定电流
	 
	@CellColum(headerName = "备注", index = 20)
	private String  bz;	// 备注
	 
	@CellColum(headerName = "采集时间", index = 21)
	private Date  cjsj;// 采集时间

	public String getMarkerno() {
		return markerno;
	}

	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getSbid() {
		return sbid;
	}

	public void setSbid(String sbid) {
		this.sbid = sbid;
	}

	public String getSbmc() {
		return sbmc;
	}

	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
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

	public String getDldj() {
		return dldj;
	}

	public void setDldj(String dldj) {
		this.dldj = dldj;
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

	public String getWhbz() {
		return whbz;
	}

	public void setWhbz(String whbz) {
		this.whbz = whbz;
	}

	public String getSblx() {
		return sblx;
	}

	public void setSblx(String sblx) {
		this.sblx = sblx;
	}

	public Date getTyrq() {
		return tyrq;
	}

	public void setTyrq(Date tyrq) {
		this.tyrq = tyrq;
	}

	public String getSbzt() {
		return sbzt;
	}

	public void setSbzt(String sbzt) {
		this.sbzt = sbzt;
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

	public String getEddy() {
		return eddy;
	}

	public void setEddy(String eddy) {
		this.eddy = eddy;
	}

	public String getEddl() {
		return eddl;
	}

	public void setEddl(String eddl) {
		this.eddl = eddl;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Date getCjsj() {
		return cjsj;
	}
	
}