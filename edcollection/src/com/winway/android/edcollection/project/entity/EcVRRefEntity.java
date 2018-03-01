package com.winway.android.edcollection.project.entity;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 全景关联表
 * 
 * @author mr-lao
 *
 */
@Table(name = "ec_vr_ref")
public class EcVRRefEntity implements Serializable{
	@Id
	@Column(column = "ID")
	private String id;// 主键
	@Column(column = "OID")
	private String oid;// 路径点ID
	@Column(column = "DEVICE_TYPE")
	private String deviceType;// 统一资源编号
	@Column(column = "DEV_OBJ_ID")
	private String devObjId;// 对象ID
	@Column(column = "NAME")
	private String name;// 全景名称
	@Column(column = "ORGID")
	private String orgid;// 机构ID
	@Column(column = "CJSJ")
	private Date cjsj;// 采集时间
	@Column(column = "GXSJ")
	private Date gxsj;// 更新时间
	@Column(column = "REMARK")
	private String remark;// 备注
	@Column(column = "PRJID")
	private String prjid; // 项目id

	public String getPrjid() {
		return prjid;
	}

	public void setPrjid(String prjid) {
		this.prjid = prjid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDevObjId() {
		return devObjId;
	}

	public void setDevObjId(String devObjId) {
		this.devObjId = devObjId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
