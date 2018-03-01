package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
*@author yzm
* 
* Excel通道4.0实体
*/
public class ExcelEntity4Channel {
	@CellColum(headerName = "通道主键", index = 0)
	private String channelid;
	@CellColum(headerName = "所属电力通道", index = 1)
	private String ssdltd;// 所属电力通道
	@CellColum(headerName = "创建时间", index = 2)
	private Date createTime;// 创建时间
	@CellColum(headerName = "剩余容量", index = 3)
	private Integer surplusCapacity;// 剩余容量
	@CellColum(headerName = "总容量", index = 4)
	private Integer totalCapacity;// 总容量
	@CellColum(headerName = "通道类型", index = 5)
	private String channelType;// 通道类型
	@CellColum(headerName = "名称", index = 6)
	private String name;// 名称
	@CellColum(headerName = "通道长度(米)", index = 7)
	private String channelLength;// 通道长度(米)
	@CellColum(headerName = "通道宽度", index = 8)
	private String channelWidth;// 通道宽度
	@CellColum(headerName = "通道深度", index = 9)
	private String channelHeight;// 通道深度
	@CellColum(headerName = "通道材质", index = 10)
	private String channelMaterial;// 通道材质
	@CellColum(headerName = "通道状态", index = 11)
	private Integer channelState;// 通道状态 0：规划的通道 1：已建成的通道
	@CellColum(headerName = "电压等级", index = 12)
	private String voltage;// 电压等级
	@CellColum(headerName = "运行单位", index = 13)
	private String yxdw;// 运行单位
	@CellColum(headerName = "所属责任区", index = 14)
	private String sszrq;// 所属责任区
	@CellColum(headerName = "井数量", index = 15)
	private String jsl;
	@CellColum(headerName = "地区特征", index = 16)
	private String dqtz;
	@CellColum(headerName = "管道类型", index = 17)
	private String gdlx;// 管道类型
	@CellColum(headerName = "施工单位", index = 18)
	private String sgdw;
	@CellColum(headerName = "管材材质", index = 19)
	private String gccz;
	@CellColum(headerName = "防火材料", index = 20)
	private String fhcl;
	@CellColum(headerName = "备注", index = 21)
	private String remarks;// 备注

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getSsdltd() {
		return ssdltd;
	}

	public void setSsdltd(String ssdltd) {
		this.ssdltd = ssdltd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSurplusCapacity() {
		return surplusCapacity;
	}

	public void setSurplusCapacity(Integer surplusCapacity) {
		this.surplusCapacity = surplusCapacity;
	}

	public Integer getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(Integer totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannelLength() {
		return channelLength;
	}

	public void setChannelLength(String channelLength) {
		this.channelLength = channelLength;
	}

	public String getChannelWidth() {
		return channelWidth;
	}

	public void setChannelWidth(String channelWidth) {
		this.channelWidth = channelWidth;
	}

	public String getChannelHeight() {
		return channelHeight;
	}

	public void setChannelHeight(String channelHeight) {
		this.channelHeight = channelHeight;
	}

	public String getChannelMaterial() {
		return channelMaterial;
	}

	public void setChannelMaterial(String channelMaterial) {
		this.channelMaterial = channelMaterial;
	}

	public Integer getChannelState() {
		return channelState;
	}

	public void setChannelState(Integer channelState) {
		this.channelState = channelState;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getYxdw() {
		return yxdw;
	}

	public void setYxdw(String yxdw) {
		this.yxdw = yxdw;
	}

	public String getSszrq() {
		return sszrq;
	}

	public void setSszrq(String sszrq) {
		this.sszrq = sszrq;
	}

	public String getJsl() {
		return jsl;
	}

	public void setJsl(String jsl) {
		this.jsl = jsl;
	}

	public String getDqtz() {
		return dqtz;
	}

	public void setDqtz(String dqtz) {
		this.dqtz = dqtz;
	}

	public String getGdlx() {
		return gdlx;
	}

	public void setGdlx(String gdlx) {
		this.gdlx = gdlx;
	}

	public String getSgdw() {
		return sgdw;
	}

	public void setSgdw(String sgdw) {
		this.sgdw = sgdw;
	}

	public String getGccz() {
		return gccz;
	}

	public void setGccz(String gccz) {
		this.gccz = gccz;
	}

	public String getFhcl() {
		return fhcl;
	}

	public void setFhcl(String fhcl) {
		this.fhcl = fhcl;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}