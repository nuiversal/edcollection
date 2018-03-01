package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcChannel实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:12
 */
@Table(name = "ec_channel")
public class EcChannelEntity implements Serializable {

	@Id
	@Column(column = "EC_CHANNEL_ID")
	private String ecChannelId;// 通道ID
	@Column(column = "PRJID")
	private String prjid;// 所属项目
	@Column(column = "ORGID")
	private String orgid;// 所属机构
	@Column(column = "SSDLTD")
	private String ssdltd;// 所属电力通道
	@Column(column = "CREATE_TIME")
	private Date createTime;// 创建时间
	@Column(column = "UPDATE_TIME")
	private Date updateTime;// 更新时间
	@Column(column = "SORT_NUM")
	private Integer sortNum;// 序号
	@Column(column = "START_DEVICE_NUM")
	private String startDeviceNum;// 起点设备编号(节点ID)
	@Column(column = "END_DEVICE_NUM")
	private String endDeviceNum;// 终点设备编号(节点ID)
	@Column(column = "SURPLUS_CAPACITY")
	private Integer surplusCapacity;// 剩余容量
	@Column(column = "TOTAL_CAPACITY")
	private Integer totalCapacity;// 总容量
	@Column(column = "CHANNEL_TYPE")
	private String channelType;// 通道类型| 统一资源分类
	@Column(column = "NAME")
	private String name;// 名称
	@Column(column = "CHANNEL_LENGTH")
	private Double channelLength;// 通道长度(米)
	@Column(column = "CHANNEL_WIDTH")
	private Double channelWidth;// 通道宽度
	@Column(column = "CHANNEL_HEIGHT")
	private Double channelHeight;// 通道深度
	@Column(column = "CHANNEL_MATERIAL")
	private String channelMaterial;// 通道材质
	@Column(column = "CHANNEL_STATE")
	private Integer channelState;// 通道状态 0：规划的通道 1：已建成的通道
	@Column(column = "VOLTAGE")
	private String voltage;// 电压等级
	@Column(column = "SBID")
	private String sbid;// 非用户填写字段，由系统功能生成该字段值(PMS中的设备ID)
	@Column(column = "YXDW")
	private String yxdw;// 运行单位
	@Column(column = "SSZRQ")
	private String sszrq;// 所属责任区
	@Column(column = "REMARKS")
	private String remarks;// 备注

	public String getEcChannelId() {
		return ecChannelId;
	}

	public void setEcChannelId(String ecChannelId) {
		this.ecChannelId = ecChannelId;
	}

	public String getPrjid() {
		return prjid;
	}

	public void setPrjid(String prjid) {
		this.prjid = prjid;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getStartDeviceNum() {
		return startDeviceNum;
	}

	public void setStartDeviceNum(String startDeviceNum) {
		this.startDeviceNum = startDeviceNum;
	}

	public String getEndDeviceNum() {
		return endDeviceNum;
	}

	public void setEndDeviceNum(String endDeviceNum) {
		this.endDeviceNum = endDeviceNum;
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

	public Double getChannelLength() {
		return channelLength;
	}

	public void setChannelLength(Double channelLength) {
		this.channelLength = channelLength;
	}

	public Double getChannelWidth() {
		return channelWidth;
	}

	public void setChannelWidth(Double channelWidth) {
		this.channelWidth = channelWidth;
	}

	public Double getChannelHeight() {
		return channelHeight;
	}

	public void setChannelHeight(Double channelHeight) {
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

	public String getSbid() {
		return sbid;
	}

	public void setSbid(String sbid) {
		this.sbid = sbid;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return getName();
	}

}