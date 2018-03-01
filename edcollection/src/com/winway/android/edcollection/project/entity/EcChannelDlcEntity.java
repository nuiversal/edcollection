package com.winway.android.edcollection.project.entity;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "ec_channel_dlc")
public class EcChannelDlcEntity implements Serializable{
	 @Id
     @Column(column = "OBJ_ID")
     private String objId;//电缆直埋标识
     @Column(column = "PRJID")
     private String prjid;//所属项目
     @Column(column = "ORGID")
     private String orgid;//所属机构
     @Column(column = "YXBH")
     private String yxbh;//运行编号
     @Column(column = "SSDS")
     private String ssds;//所属地市
     @Column(column = "YWDW")
     private String ywdw;//运维单位
     @Column(column = "CJSJ")
     private Date cjsj;//采集时间
     @Column(column = "GXSJ")
     private Date gxsj;//更新时间
     
     
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
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
	public String getYxbh() {
		return yxbh;
	}
	public void setYxbh(String yxbh) {
		this.yxbh = yxbh;
	}
	public String getSsds() {
		return ssds;
	}
	public void setSsds(String ssds) {
		this.ssds = ssds;
	}
	public String getYwdw() {
		return ywdw;
	}
	public void setYwdw(String ywdw) {
		this.ywdw = ywdw;
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
