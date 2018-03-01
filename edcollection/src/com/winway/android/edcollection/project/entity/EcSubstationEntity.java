package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcSubstation实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:39
 */
@Table(name = "ec_substation")
public class EcSubstationEntity implements Serializable {

	@Id
	@Column(column = "EC_SUBSTATION_ID")
	private String ecSubstationId;// ec_substation_ID
	@Column(column = "VOLTAGE")
	private String voltage;// VOLTAGE
	@Column(column = "DEV_TYPE")
	private String devType;// DEV_TYPE
	@Column(column = "DEV_ID")
	private String devId;// DEV_ID
	@Column(column = "STATION_NO")
	private String stationNo;// STATION_NO
	@Column(column = "NAME")
	private String name;// NAME
	@Column(column = "RIGHT_PRO")
	private Integer rightPro;// RIGHT_PRO
	@Column(column = "ORGID")
	private String orgid;// ORGID
	@Column(column = "PRJID")
	private String prjid;// PRJID
	@Column(column = "CJSJ")
	private Date cjsj;// CJSJ
	@Column(column = "GXSJ")
	private Date gxsj;// GXSJ

	public String getEcSubstationId() {
		return ecSubstationId;
	}

	public void setEcSubstationId(String ecSubstationId) {
		this.ecSubstationId = ecSubstationId;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getStationNo() {
		return stationNo;
	}

	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRightPro() {
		return rightPro;
	}

	public void setRightPro(Integer rightPro) {
		this.rightPro = rightPro;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getPrjid() {
		return prjid;
	}

	public void setPrjid(String prjid) {
		this.prjid = prjid;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
