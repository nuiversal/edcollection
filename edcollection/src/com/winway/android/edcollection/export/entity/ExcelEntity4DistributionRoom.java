package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
*@author yzm
* 
* Excel配电室4.0实体
*/
public class ExcelEntity4DistributionRoom {
	
	@CellColum(headerName = "标识器编号", index = 0)
	private String markerno;

	@CellColum(headerName = "设备标识", index = 1)
	private String  sbid;		// 设备标识
	
	@CellColum(headerName = "设备名称", index = 2)
	private String  sbmc;		// 设备名称
	
	@CellColum(headerName = "运行编号", index = 3)
	private String  yxbh;		// 运行编号
	
	@CellColum(headerName = "电系铭牌", index = 4)
	private String  dxmpid;		// 电系铭牌
	
	@CellColum(headerName = "电压等级", index = 5)
	private String  dydj;		// 电压等级
	
	@CellColum(headerName = "所属地市", index = 6)
	private String  ssds;		// 所属地市
	
	@CellColum(headerName = "运维单位", index = 7)
	private String  ywdw;		// 运维单位
	
	@CellColum(headerName = "运行编号", index = 8)
	private String  whbz;		// 运行编号
	
	@CellColum(headerName = "设备状态", index = 9)
	private String  sbzt;		// 设备状态
	
	@CellColum(headerName = "投运日期", index = 10)
	private Date  tyrq;			// 投运日期
	
	@CellColum(headerName = "配变台数", index = 11)
	private Integer  pbts;		// 配变台数
	
	@CellColum(headerName = "配变总容量", index = 12)
	private String  pbzrl;		// 配变总容量
	
	@CellColum(headerName = "无功补偿容量", index = 13)
	private String  wgbcrl;		// 无功补偿容量
	
	@CellColum(headerName = "防误方式", index = 14)
	private String  fwfs;		// 防误方式
	
	@CellColum(headerName = "接地电阻", index = 15)
	private Integer  jddz;		// 接地电阻
	
	@CellColum(headerName = "站址", index = 16)
	private String  zz;			// 站址
	
	@CellColum(headerName = "地区特征", index = 17)
	private String  dqtz;		// 地区特征
	
	@CellColum(headerName = "重要程度", index = 18)
	private String  zycd;		// 重要程度
	
	@CellColum(headerName = "资产性质", index = 19)
	private String  zcxz;		// 资产性质
	
	@CellColum(headerName = "资产单位", index = 20)
	private String  zcdw;		// 资产单位
	
	@CellColum(headerName = "备注", index = 21)
	private String  bz;			// 备注
	
	@CellColum(headerName = "采集时间", index = 22)
	private Date  cjsj;	// 采集时间
	
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

	public String getWhbz() {
		return whbz;
	}

	public void setWhbz(String whbz) {
		this.whbz = whbz;
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

	public Integer getPbts() {
		return pbts;
	}

	public void setPbts(Integer pbts) {
		this.pbts = pbts;
	}

	public String getPbzrl() {
		return pbzrl;
	}

	public void setPbzrl(String pbzrl) {
		this.pbzrl = pbzrl;
	}

	public String getWgbcrl() {
		return wgbcrl;
	}

	public void setWgbcrl(String wgbcrl) {
		this.wgbcrl = wgbcrl;
	}

	public String getFwfs() {
		return fwfs;
	}

	public void setFwfs(String fwfs) {
		this.fwfs = fwfs;
	}

	public Integer getJddz() {
		return jddz;
	}

	public void setJddz(Integer jddz) {
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