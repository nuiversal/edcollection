package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcChannelSectionPoints实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:22
 */
@Table(name = "ec_channel_section_points")
public class EcChannelSectionPointsEntity implements Serializable {

	@Id
	@Column(column = "EC_CHANNEL_SECTION_POINTS_ID")
	private String ecChannelSectionPointsId;// EC_CHANNEL_SECTION_POINTS_ID
	@Column(column = "EC_CHANNEL_SECTION_ID")
	private String ecChannelSectionId;// EC_CHANNEL_SECTION_ID
	@Column(column = "IDX_NO")
	private String idxNo;// IDX_NO
	@Column(column = "DIAMETER")
	private Double diameter;// DIAMETER
	@Column(column = "LINE_ID")
	private String lineId;// LINE_ID
	@Column(column = "IS_PLUGGING")
	private Integer isPlugging;// IS_PLUGGING
	@Column(column = "PHASE_SEQ")
	private String phaseSeq; // 电缆相序
	@Column(column = "PRJID")
	private String prjid;// PRJID
	@Column(column = "ORGID")
	private String orgid;// ORGID
	@Column(column = "CJSJ")
	private Date cjsj;// CJSJ
	@Column(column = "GXSJ")
	private Date gxsj;// GXSJ

	public String getPhaseSeq() {
		return phaseSeq;
	}

	public void setPhaseSeq(String phaseSeq) {
		this.phaseSeq = phaseSeq;
	}

	public String getEcChannelSectionPointsId() {
		return ecChannelSectionPointsId;
	}

	public void setEcChannelSectionPointsId(String ecChannelSectionPointsId) {
		this.ecChannelSectionPointsId = ecChannelSectionPointsId;
	}

	public String getEcChannelSectionId() {
		return ecChannelSectionId;
	}

	public void setEcChannelSectionId(String ecChannelSectionId) {
		this.ecChannelSectionId = ecChannelSectionId;
	}

	public String getIdxNo() {
		return idxNo;
	}

	public void setIdxNo(String idxNo) {
		this.idxNo = idxNo;
	}

	public Double getDiameter() {
		return diameter;
	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Integer getIsPlugging() {
		return isPlugging;
	}

	public void setIsPlugging(Integer isPlugging) {
		this.isPlugging = isPlugging;
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

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public Date getGxsj() {
		return gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

}
