package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EmDangerInfo实体类
 * 
 * @author mr-lao
 * @since 2017/6/19 14:45:10
 */
@Table(name = "em_danger_info")
public class EmDangerInfoEntity implements Serializable {

	@Id
	@Column(column = "EM_DANGER_INFO_ID")
	private String emDangerInfoId;// EM_DANGER_INFO_ID
	@Column(column = "LNK_TYPE")
	private String lnkType;// LNK_TYPE
	@Column(column = "LNK_OBJ_ID")
	private String lnkObjId;// LNK_OBJ_ID
	@Column(column = "COORDINATES")
	private String coordinates;// COORDINATES
	@Column(column = "DESCRIPTION")
	private String description;// DESCRIPTION
	@Column(column = "RECORDOR")
	private String recordor;// RECORDOR
	@Column(column = "RECORD_TIME")
	private Date recordTime;// RECORD_TIME
	@Column(column = "STATUS")
	private Integer status;// STATUS
	@Column(column = "DANGER_TYPE")
	private String dangerType;// 隐患类型

	public String getDangerType() {
		return dangerType;
	}

	public void setDangerType(String dangerType) {
		this.dangerType = dangerType;
	}

	public String getEmDangerInfoId() {
		return emDangerInfoId;
	}

	public void setEmDangerInfoId(String emDangerInfoId) {
		this.emDangerInfoId = emDangerInfoId;
	}

	public String getLnkType() {
		return lnkType;
	}

	public void setLnkType(String lnkType) {
		this.lnkType = lnkType;
	}

	public String getLnkObjId() {
		return lnkObjId;
	}

	public void setLnkObjId(String lnkObjId) {
		this.lnkObjId = lnkObjId;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRecordor() {
		return recordor;
	}

	public void setRecordor(String recordor) {
		this.recordor = recordor;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
