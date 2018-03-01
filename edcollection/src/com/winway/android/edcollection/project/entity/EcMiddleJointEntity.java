package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcMiddleJoint实体类
 * 
 * @author zgq
 * @since 2017-07-17 14:00:33
 */
@Table(name = "ec_middle_joint")
public class EcMiddleJointEntity implements Serializable{

        @Id
        @Column(column = "OBJ_ID")
        private String objId;//OBJ_ID
        @Column(column = "SBMC")
        private String sbmc;//SBMC
        @Column(column = "DEV_MODEL")
        private String devModel;//DEV_MODEL
        @Column(column = "TECH_FEATURE")
        private Integer techFeature;//工艺特征| 1 冷缩式，2 热缩式，3 预制式，4 充油式
        @Column(column = "MANUFACUTRER")
        private String manufacutrer;//MANUFACUTRER
        @Column(column = "SC_DATE")
        private Date scDate;//SC_DATE
        @Column(column = "TY_DATE")
        private Date tyDate;//TY_DATE
        @Column(column = "INSTALLATION_UNIT")
        private String installationUnit;//INSTALLATION_UNIT
        @Column(column = "WORKER")
        private String worker;//WORKER
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "CJSJ")
        private Date cjsj;//CJSJ
        @Column(column = "GXSJ")
        private Date gxsj;//GXSJ
        @Column(column = "JOINT_TYPE")
        private String jointType;//类型（直通接头、绝缘接头等）
        @Column(column = "CJLXFS")
        private String cjlxfs;//厂家联系方式
        @Column(column = "ZJJTGZQK")
        private String zjjtgzqk;//中间接头故障情况
        @Column(column = "SCXJRQ")
        private Date scxjrq;//上次巡检日期
        @Column(column = "DLZLL")
        private Integer dlzll;//电缆载流量
        @Column(column = "ZJJTWD")
        private Double zjjtwd;//中间接头温度
        @Column(column = "JDDLQK")
        private String jddlqk;//接地电流情况
    
        
        public String getObjId() {
		    return objId;
	    }
        
	    public void setObjId(String objId) {
		    this.objId = objId;
	    }
        
        public String getSbmc() {
		    return sbmc;
	    }
        
	    public void setSbmc(String sbmc) {
		    this.sbmc = sbmc;
	    }
        
        public String getDevModel() {
		    return devModel;
	    }
        
	    public void setDevModel(String devModel) {
		    this.devModel = devModel;
	    }
        
        public Integer getTechFeature() {
		    return techFeature;
	    }
        
	    public void setTechFeature(Integer techFeature) {
		    this.techFeature = techFeature;
	    }
        
        public String getManufacutrer() {
		    return manufacutrer;
	    }
        
	    public void setManufacutrer(String manufacutrer) {
		    this.manufacutrer = manufacutrer;
	    }
        
        public Date getScDate() {
		    return scDate;
	    }
        
	    public void setScDate(Date scDate) {
		    this.scDate = scDate;
	    }
        
        public Date getTyDate() {
		    return tyDate;
	    }
        
	    public void setTyDate(Date tyDate) {
		    this.tyDate = tyDate;
	    }
        
        public String getInstallationUnit() {
		    return installationUnit;
	    }
        
	    public void setInstallationUnit(String installationUnit) {
		    this.installationUnit = installationUnit;
	    }
        
        public String getWorker() {
		    return worker;
	    }
        
	    public void setWorker(String worker) {
		    this.worker = worker;
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
        
        public String getJointType() {
		    return jointType;
	    }
        
	    public void setJointType(String jointType) {
		    this.jointType = jointType;
	    }
        
        public String getCjlxfs() {
		    return cjlxfs;
	    }
        
	    public void setCjlxfs(String cjlxfs) {
		    this.cjlxfs = cjlxfs;
	    }
        
        public String getZjjtgzqk() {
		    return zjjtgzqk;
	    }
        
	    public void setZjjtgzqk(String zjjtgzqk) {
		    this.zjjtgzqk = zjjtgzqk;
	    }
        
        public Date getScxjrq() {
		    return scxjrq;
	    }
        
	    public void setScxjrq(Date scxjrq) {
		    this.scxjrq = scxjrq;
	    }
        
        public Integer getDlzll() {
		    return dlzll;
	    }
        
	    public void setDlzll(Integer dlzll) {
		    this.dlzll = dlzll;
	    }
        
        public Double getZjjtwd() {
		    return zjjtwd;
	    }
        
	    public void setZjjtwd(Double zjjtwd) {
		    this.zjjtwd = zjjtwd;
	    }
        
        public String getJddlqk() {
		    return jddlqk;
	    }
        
	    public void setJddlqk(String jddlqk) {
		    this.jddlqk = jddlqk;
	    }
    
}
 
