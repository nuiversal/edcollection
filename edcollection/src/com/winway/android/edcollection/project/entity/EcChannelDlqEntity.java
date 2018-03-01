package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcChannelDlq实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:17
 */
@Table(name = "ec_channel_dlq")
public class EcChannelDlqEntity implements Serializable{

        @Id
        @Column(column = "OBJ_ID")
        private String objId;//OBJ_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "YXBH")
        private String yxbh;//YXBH
        @Column(column = "SSDS")
        private String ssds;//SSDS
        @Column(column = "YWDW")
        private String ywdw;//YWDW
        @Column(column = "WHBZ")
        private String whbz;//WHBZ
        @Column(column = "DQTZ")
        private String dqtz;//DQTZ
        @Column(column = "CZ")
        private String cz;//CZ
        @Column(column = "FHCL")
        private String fhcl;//FHCL
        @Column(column = "SGDW")
        private String sgdw;//SGDW
        @Column(column = "SGRQ")
        private Date sgrq;//SGRQ
        @Column(column = "JGRQ")
        private Date jgrq;//JGRQ
        @Column(column = "ZCXZ")
        private String zcxz;//ZCXZ
        @Column(column = "ZCDW")
        private String zcdw;//ZCDW
        @Column(column = "ZYFL")
        private String zyfl;//ZYFL
        @Column(column = "SBZT")
        private String sbzt;//SBZT
        @Column(column = "BZ")
        private String bz;//BZ
        @Column(column = "CJSJ")
        private Date cjsj;//CJSJ
        @Column(column = "GXSJ")
        private Date gxsj;//GXSJ
    
        
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
        
        public String getWhbz() {
		    return whbz;
	    }
        
	    public void setWhbz(String whbz) {
		    this.whbz = whbz;
	    }
        
        public String getDqtz() {
		    return dqtz;
	    }
        
	    public void setDqtz(String dqtz) {
		    this.dqtz = dqtz;
	    }
        
        public String getCz() {
		    return cz;
	    }
        
	    public void setCz(String cz) {
		    this.cz = cz;
	    }
        
        public String getFhcl() {
		    return fhcl;
	    }
        
	    public void setFhcl(String fhcl) {
		    this.fhcl = fhcl;
	    }
        
        public String getSgdw() {
		    return sgdw;
	    }
        
	    public void setSgdw(String sgdw) {
		    this.sgdw = sgdw;
	    }
        
        public Date getSgrq() {
		    return sgrq;
	    }
        
	    public void setSgrq(Date sgrq) {
		    this.sgrq = sgrq;
	    }
        
        public Date getJgrq() {
		    return jgrq;
	    }
        
	    public void setJgrq(Date jgrq) {
		    this.jgrq = jgrq;
	    }
        
        public String getZcxz() {
		    return zcxz;
	    }
        
	    public void setZcxz(String zcxz) {
		    this.zcxz = zcxz;
	    }
        
        public String getZcdw() {
		    return zcdw;
	    }
        
	    public void setZcdw(String zcdw) {
		    this.zcdw = zcdw;
	    }
        
        public String getZyfl() {
		    return zyfl;
	    }
        
	    public void setZyfl(String zyfl) {
		    this.zyfl = zyfl;
	    }
        
        public String getSbzt() {
		    return sbzt;
	    }
        
	    public void setSbzt(String sbzt) {
		    this.sbzt = sbzt;
	    }
        
        public String getBz() {
		    return bz;
	    }
        
	    public void setBz(String bz) {
		    this.bz = bz;
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
 
