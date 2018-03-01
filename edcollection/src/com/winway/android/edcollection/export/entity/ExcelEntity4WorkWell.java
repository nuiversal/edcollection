package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年1月30日 上午11:08:30
* Excel工井
*/
public class ExcelEntity4WorkWell {
	@CellColum(headerName = "工井名称", index = 0)
	private String deviceName;
	@CellColum(headerName = "标识器编号", index = 1)
	private String markerno;
	@CellColum(headerName = "断面尺寸", index = 2)
	private String dmcc;
	@CellColum(headerName = "下一工井方向", index = 3)
	private String xygjfx;
	@CellColum(headerName = "距下一工井距离", index = 4)
	private String jxygjjl;
	@CellColum(headerName = "附属设施情况", index = 5)
	private String fsssqk;
	@CellColum(headerName = "盖板厚度", index = 6)
	private String gbhd;
	@CellColum(headerName = "盖板块数", index = 7)
	private Integer GBKS;
	@CellColum(headerName = "运维单位", index = 8)
	private String YWDW;
	@CellColum(headerName = "维护班组", index = 9)
	private String WHBZ;
	@CellColum(headerName = "工井形状", index = 10)
	private String GJXZ;
	@CellColum(headerName = "地区特征", index = 11)
	private String DQTZ;
	@CellColum(headerName = "井类型", index = 12)
	private String JLX;
	@CellColum(headerName = "结构", index = 13) // 结构|带室,不带室
	private String JG;
	@CellColum(headerName = "井面高程", index = 14)
	private String jmgc;
	@CellColum(headerName = "内底高程", index = 15)
	private String ndgc;
	@CellColum(headerName = "井盖形状", index = 16)
	private String jgxz;
	@CellColum(headerName = "井盖尺寸", index = 17)
	private String jgcc;
	@CellColum(headerName = "井盖材质", index = 18)
	private String jgcz;
	@CellColum(headerName = "井盖出厂日期", index = 19)
	private Date jgccrq;
	@CellColum(headerName = "平台层数", index = 20)
	private String ptcs;
	@CellColum(headerName = "施工单位", index = 21)
	private String sgdw;
	@CellColum(headerName = "施工日期", index = 22)
	private Date sgrq;
	@CellColum(headerName = "峻工日期", index = 23)
	private Date jqrq;
	@CellColum(headerName = "工井长度", index = 24)
	private String gjcd;
	@CellColum(headerName = "工井宽度", index = 25)
	private String gjkd;
	@CellColum(headerName = "工井深度", index = 26)
	private String gjsd;
	@CellColum(headerName = "采集时间", index = 27, lenth = 12)
	private Date cjsj;
	@CellColum(headerName = "备注", index = 28)
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

	public String getDmcc() {
		return dmcc;
	}

	public void setDmcc(String dmcc) {
		this.dmcc = dmcc;
	}

	public String getXygjfx() {
		return xygjfx;
	}

	public void setXygjfx(String xygjfx) {
		this.xygjfx = xygjfx;
	}

	public String getJxygjjl() {
		return jxygjjl;
	}

	public void setJxygjjl(String jxygjjl) {
		this.jxygjjl = jxygjjl;
	}

	public String getFsssqk() {
		return fsssqk;
	}

	public void setFsssqk(String fsssqk) {
		this.fsssqk = fsssqk;
	}

	public String getGbhd() {
		return gbhd;
	}

	public void setGbhd(String gbhd) {
		this.gbhd = gbhd;
	}

	public Integer getGBKS() {
		return GBKS;
	}

	public void setGBKS(Integer gBKS) {
		GBKS = gBKS;
	}

	public String getYWDW() {
		return YWDW;
	}

	public void setYWDW(String yWDW) {
		YWDW = yWDW;
	}

	public String getWHBZ() {
		return WHBZ;
	}

	public void setWHBZ(String wHBZ) {
		WHBZ = wHBZ;
	}

	public String getGJXZ() {
		return GJXZ;
	}

	public void setGJXZ(String gJXZ) {
		GJXZ = gJXZ;
	}

	public String getDQTZ() {
		return DQTZ;
	}

	public void setDQTZ(String dQTZ) {
		DQTZ = dQTZ;
	}

	public String getJLX() {
		return JLX;
	}

	public void setJLX(String jLX) {
		JLX = jLX;
	}

	public String getJG() {
		return JG;
	}

	public void setJG(String jG) {
		JG = jG;
	}

	public String getJmgc() {
		return jmgc;
	}

	public void setJmgc(String jmgc) {
		this.jmgc = jmgc;
	}

	public String getNdgc() {
		return ndgc;
	}

	public void setNdgc(String ndgc) {
		this.ndgc = ndgc;
	}

	public String getJgxz() {
		return jgxz;
	}

	public void setJgxz(String jgxz) {
		this.jgxz = jgxz;
	}

	public String getJgcc() {
		return jgcc;
	}

	public void setJgcc(String jgcc) {
		this.jgcc = jgcc;
	}

	public String getJgcz() {
		return jgcz;
	}

	public void setJgcz(String jgcz) {
		this.jgcz = jgcz;
	}

	public Date getJgccrq() {
		return jgccrq;
	}

	public void setJgccrq(Date jgccrq) {
		this.jgccrq = jgccrq;
	}

	public String getPtcs() {
		return ptcs;
	}

	public void setPtcs(String ptcs) {
		this.ptcs = ptcs;
	}

	public String getSgdw() {
		return sgdw;
	}

	public void setSgdw(String sgdw) {
		this.sgdw = sgdw;
	}

	public Date getSgrq() {
		return sgrq;
	}

	public void setSgrq(Date sgrq) {
		this.sgrq = sgrq;
	}

	public Date getJqrq() {
		return jqrq;
	}

	public void setJqrq(Date jqrq) {
		this.jqrq = jqrq;
	}

	public String getGjcd() {
		return gjcd;
	}

	public void setGjcd(String gjcd) {
		this.gjcd = gjcd;
	}

	public String getGjkd() {
		return gjkd;
	}

	public void setGjkd(String gjkd) {
		this.gjkd = gjkd;
	}

	public String getGjsd() {
		return gjsd;
	}

	public void setGjsd(String gjsd) {
		this.gjsd = gjsd;
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
