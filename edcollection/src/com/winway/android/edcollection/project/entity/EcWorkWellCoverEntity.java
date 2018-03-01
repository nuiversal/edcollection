package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcWorkWellCover实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:46
 */
@Table(name = "ec_work_well_cover")
public class EcWorkWellCoverEntity implements Serializable{

        @Id
        @Column(column = "OBJ_ID")
        private String objId;//OBJ_ID
        @Column(column = "XH")
        private String xh;//XH
        @Column(column = "SBMC")
        private String sbmc;//SBMC
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "SSGJ")
        private String ssgj;//SSGJ
        @Column(column = "BIZ")
        private String biz;//BIZ
        @Column(column = "YWDW")
        private String ywdw;//YWDW
        @Column(column = "WHBZ")
        private String whbz;//WHBZ
        @Column(column = "SSZRQ")
        private String sszrq;//SSZRQ
        @Column(column = "JGCD")
        private Double jgcd;//JGCD
        @Column(column = "JGKD")
        private Double jgkd;//JGKD
        @Column(column = "JGHD")
        private Double jghd;//JGHD
        @Column(column = "CJSJ")
        private Date cjsj;//CJSJ
        @Column(column = "GXSJ")
        private Date gxsj;//GXSJ
        @Column(column = "BZ")
        private String bz;//BZ
    
        
        public String getObjId() {
		    return objId;
	    }
        
	    public void setObjId(String objId) {
		    this.objId = objId;
	    }
        
        public String getXh() {
		    return xh;
	    }
        
	    public void setXh(String xh) {
		    this.xh = xh;
	    }
        
        public String getSbmc() {
		    return sbmc;
	    }
        
	    public void setSbmc(String sbmc) {
		    this.sbmc = sbmc;
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
        
        public String getSsgj() {
		    return ssgj;
	    }
        
	    public void setSsgj(String ssgj) {
		    this.ssgj = ssgj;
	    }
        
        public String getBiz() {
		    return biz;
	    }
        
	    public void setBiz(String biz) {
		    this.biz = biz;
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
        
        public String getSszrq() {
		    return sszrq;
	    }
        
	    public void setSszrq(String sszrq) {
		    this.sszrq = sszrq;
	    }
        
        public Double getJgcd() {
		    return jgcd;
	    }
        
	    public void setJgcd(Double jgcd) {
		    this.jgcd = jgcd;
	    }
        
        public Double getJgkd() {
		    return jgkd;
	    }
        
	    public void setJgkd(Double jgkd) {
		    this.jgkd = jgkd;
	    }
        
        public Double getJghd() {
		    return jghd;
	    }
        
	    public void setJghd(Double jghd) {
		    this.jghd = jghd;
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
        
        public String getBz() {
		    return bz;
	    }
        
	    public void setBz(String bz) {
		    this.bz = bz;
	    }
    
}
 
