package com.winway.android.edcollection.adding.entity;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 同上一标识器缓存类
 * 
 * @author zgq
 *
 */
@Table(name = "UpMarkerCache")
public class UpMarkerCache {

	@Id
	@Column(column = "id")
	private Integer id;// 字段ID
	@Column(column = "MARKER_TYPE")
	private Integer markerType;// 路径点类型| 1 标识球，2 标识钉，0 路径点
	@Column(column = "LAY_TYPE")
	private String layType;// 敷设类型| 1 架空, 2 电缆沟 3 电缆槽 4 埋管 5 顶管
	@Column(column = "INSTALL_POSITION")
	private String installPosition;// 安装位置
	@Column(column = "GEOGRAPHICAL_POSITION")
	private String geographicalPosition;//地理位置
	@Column(column = "CABLE_DEEPTH")
	private Double cableDeepth;// 深度|距电缆沟、槽、顶管底深度
	@Column(column = "CABLE_WIDTH")
	private Double cableWidth;// 沟槽宽度
	@Column(column = "DEVICE_DESC")
	private String deviceDesc;// 设备描述
	@Column(column = "REMARK")
	private String remark;// 备注

	
	public String getGeographicalPosition() {
		return geographicalPosition;
	}

	public void setGeographicalPosition(String geographicalPosition) {
		this.geographicalPosition = geographicalPosition;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMarkerType() {
		return markerType;
	}

	public void setMarkerType(Integer markerType) {
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

	public Double getCableDeepth() {
		return cableDeepth;
	}

	public void setCableDeepth(Double cableDeepth) {
		this.cableDeepth = cableDeepth;
	}

	public Double getCableWidth() {
		return cableWidth;
	}

	public void setCableWidth(Double cableWidth) {
		this.cableWidth = cableWidth;
	}

	public String getDeviceDesc() {
		return deviceDesc;
	}

	public void setDeviceDesc(String deviceDesc) {
		this.deviceDesc = deviceDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
