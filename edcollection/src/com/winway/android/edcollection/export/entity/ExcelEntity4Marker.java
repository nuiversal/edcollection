package com.winway.android.edcollection.export.entity;

import java.util.Date;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年1月29日 下午1:40:39
* 标识器Excel（节点）
*/
public class ExcelEntity4Marker {
	@CellColum(headerName = "标识器编号", index = 0)
	private String markerno;
	@CellColum(headerName = "经度", index = 1)
	private double longutide;
	@CellColum(headerName = "纬度", index = 2)
	private double lagitude;
	@CellColum(headerName = "地面高程", index = 3)
	private String altitude;
	@CellColum(headerName = "路径点类型", index = 4)
	private String markerType;// '路径点类型| 1 标识球，2 标识钉，3 杆塔，0 路径点',
	@CellColum(headerName = "敷设方式", index = 5)
	private String layType;
	@CellColum(headerName = "安装位置", index = 6)
	private String installPosition;
	@CellColum(headerName = "地理位置", index = 7)
	private String geoLocation;
	@CellColum(headerName = "沟槽深度", index = 8)
	private String gcheight;
	@CellColum(headerName = "沟槽宽度", index = 9)
	private String gcwidth;
	@CellColum(headerName = "描述", index = 10)
	private String desc;
	@CellColum(headerName = "同沟电缆回路数", index = 11)
	private Integer loop;
	@CellColum(headerName = "放置时间", index = 12)
	private Date placeTime;
	@CellColum(headerName = "备注", index = 13)
	private String remark;

	public String getMarkerno() {
		return markerno;
	}

	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}

	public double getLongutide() {
		return longutide;
	}

	public void setLongutide(double longutide) {
		this.longutide = longutide;
	}

	public double getLagitude() {
		return lagitude;
	}

	public void setLagitude(double lagitude) {
		this.lagitude = lagitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getMarkerType() {
		return markerType;
	}

	public void setMarkerType(String markerType) {
		this.markerType = markerType;
	}

	public String getLayType() {
		return layType;
	}

	public void setLayType(String layType) {
		this.layType = layType;
	}

	public String getInstallPosition() {
		return installPosition;
	}

	public void setInstallPosition(String installPosition) {
		this.installPosition = installPosition;
	}

	public String getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	public String getGcheight() {
		return gcheight;
	}

	public void setGcheight(String gcheight) {
		this.gcheight = gcheight;
	}

	public String getGcwidth() {
		return gcwidth;
	}

	public void setGcwidth(String gcwidth) {
		this.gcwidth = gcwidth;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getLoop() {
		return loop;
	}

	public void setLoop(Integer loop) {
		this.loop = loop;
	}

	public Date getPlaceTime() {
		return placeTime;
	}

	public void setPlaceTime(Date placeTime) {
		this.placeTime = placeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
