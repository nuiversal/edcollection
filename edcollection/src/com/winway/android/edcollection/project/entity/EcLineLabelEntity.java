package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcLineLabel实体类
 * 
 * @author zgq
 * @since 2017-07-30 10:26:45
 */
@Table(name = "ec_line_label")
public class EcLineLabelEntity implements Serializable {

	@Id
	@Column(column = "OBJ_ID")
	private String objId;// OBJ_ID
	@Column(column = "LINE_NO")
	private String lineNo;// 线路编号
	@Column(column = "BIND_TARGET")
	private Integer bindTarget;// 绑扎对象| 1 电缆本体，2 中间接头，3 终端设备
	@Column(column = "LABEL_NO")
	private String labelNo;// LABEL_NO
	@Column(column = "SEQUENCE")
	private Integer sequence;// SEQUENCE
	@Column(column = "DISTANCE")
	private Double distance;// DISTANCE
	@Column(column = "PRJID")
	private String prjid;// PRJID
	@Column(column = "ORGID")
	private String orgid;// ORGID
	@Column(column = "DEVICE_TYPE")
	private String deviceType;// 设备类型
	@Column(column = "DEV_OBJ_ID")
	private String devObjId;// 设备标识
	@Column(column = "CJSJ")
	private Date cjsj;// CJSJ
	@Column(column = "GXSJ")
	private Date gxsj;// GXSJ
	@Column(column = "ZJGJFX")
	private String zjgjfx;// 最近工井方向
	@Column(column = "JZJGJJL")
	private Double jzjgjjl;// 距最近工井距离
	@Column(column = "FSSSQK")
	private String fsssqk;// 附属设施情况
	@Column(column = "WZDT")
	private String wzdt;// 位置地图
	@Column(column = "ZWTDFBQK")
	private String zwtdfbqk;// 周围通道分布情况
	@Column(column = "TDNDLQK")
	private String tdndlqk;// 通道内电缆情况
	@Column(column = "DBLKXX")
	private String dblkxx;// 地标路口信息
	@Column(column = "OID")
	private String oid;// 路径点ID
	@Column(column = "DEV_NAME")
	private String devName;// 设备名称
	@Column(column = "DXXH")
	private String dxxh;// 对象型号
	@Column(column = "DXCD")
	private Double dxcd;// 对象长度

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public Integer getBindTarget() {
		return bindTarget;
	}

	public void setBindTarget(Integer bindTarget) {
		this.bindTarget = bindTarget;
	}

	public String getLabelNo() {
		return labelNo;
	}

	public void setLabelNo(String labelNo) {
		this.labelNo = labelNo;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
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

	public String getZjgjfx() {
		return zjgjfx;
	}

	public void setZjgjfx(String zjgjfx) {
		this.zjgjfx = zjgjfx;
	}

	public Double getJzjgjjl() {
		return jzjgjjl;
	}

	public void setJzjgjjl(Double jzjgjjl) {
		this.jzjgjjl = jzjgjjl;
	}

	public String getFsssqk() {
		return fsssqk;
	}

	public void setFsssqk(String fsssqk) {
		this.fsssqk = fsssqk;
	}

	public String getWzdt() {
		return wzdt;
	}

	public void setWzdt(String wzdt) {
		this.wzdt = wzdt;
	}

	public String getZwtdfbqk() {
		return zwtdfbqk;
	}

	public void setZwtdfbqk(String zwtdfbqk) {
		this.zwtdfbqk = zwtdfbqk;
	}

	public String getTdndlqk() {
		return tdndlqk;
	}

	public void setTdndlqk(String tdndlqk) {
		this.tdndlqk = tdndlqk;
	}

	public String getDblkxx() {
		return dblkxx;
	}

	public void setDblkxx(String dblkxx) {
		this.dblkxx = dblkxx;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getDxxh() {
		return dxxh;
	}

	public void setDxxh(String dxxh) {
		this.dxxh = dxxh;
	}

	public Double getDxcd() {
		return dxcd;
	}

	public void setDxcd(Double dxcd) {
		this.dxcd = dxcd;
	}

}
