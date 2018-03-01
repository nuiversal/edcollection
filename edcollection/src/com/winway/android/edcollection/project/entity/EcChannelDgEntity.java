package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcChannelDg实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:13
 */
@Table(name = "ec_channel_dg")
public class EcChannelDgEntity implements Serializable{

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
        @Column(column = "TDMC")
        private String tdmc;//TDMC
        @Column(column = "ZCD")
        private Double zcd;//ZCD
        @Column(column = "JGXS")
        private String jgxs;//JGXS
        @Column(column = "ZX")
        private String zx;//ZX
        @Column(column = "LTGX")
        private String ltgx;//LTGX
        @Column(column = "HXPM")
        private String hxpm;//HXPM
        @Column(column = "ZXPM")
        private String zxpm;//ZXPM
        @Column(column = "KD")
        private Double kd;//KD
        @Column(column = "TLGSL")
        private Integer tlgsl;//TLGSL
        @Column(column = "TLGGJ")
        private Double tlggj;//TLGGJ
        @Column(column = "GCCZ")
        private String gccz;//GCCZ
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
        
        public String getTdmc() {
		    return tdmc;
	    }
        
	    public void setTdmc(String tdmc) {
		    this.tdmc = tdmc;
	    }
        
        public Double getZcd() {
		    return zcd;
	    }
        
	    public void setZcd(Double zcd) {
		    this.zcd = zcd;
	    }
        
        public String getJgxs() {
		    return jgxs;
	    }
        
	    public void setJgxs(String jgxs) {
		    this.jgxs = jgxs;
	    }
        
        public String getZx() {
		    return zx;
	    }
        
	    public void setZx(String zx) {
		    this.zx = zx;
	    }
        
        public String getLtgx() {
		    return ltgx;
	    }
        
	    public void setLtgx(String ltgx) {
		    this.ltgx = ltgx;
	    }
        
        public String getHxpm() {
		    return hxpm;
	    }
        
	    public void setHxpm(String hxpm) {
		    this.hxpm = hxpm;
	    }
        
        public String getZxpm() {
		    return zxpm;
	    }
        
	    public void setZxpm(String zxpm) {
		    this.zxpm = zxpm;
	    }
        
        public Double getKd() {
		    return kd;
	    }
        
	    public void setKd(Double kd) {
		    this.kd = kd;
	    }
        
        public Integer getTlgsl() {
		    return tlgsl;
	    }
        
	    public void setTlgsl(Integer tlgsl) {
		    this.tlgsl = tlgsl;
	    }
        
        public Double getTlggj() {
		    return tlggj;
	    }
        
	    public void setTlggj(Double tlggj) {
		    this.tlggj = tlggj;
	    }
        
        public String getGccz() {
		    return gccz;
	    }
        
	    public void setGccz(String gccz) {
		    this.gccz = gccz;
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
 
