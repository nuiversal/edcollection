package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcKgz实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:32
 */
@Table(name = "ec_kgz")
public class EcKgzEntity implements Serializable{

        @Id
        @Column(column = "OBJ_ID")
        private String objId;//OBJ_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "SBID")
        private String sbid;//SBID
        @Column(column = "SBMC")
        private String sbmc;//SBMC
        @Column(column = "YXBH")
        private String yxbh;//YXBH
        @Column(column = "DXMPID")
        private String dxmpid;//DXMPID
        @Column(column = "DYDJ")
        private String dydj;//DYDJ
        @Column(column = "SSDS")
        private String ssds;//SSDS
        @Column(column = "YWDW")
        private String ywdw;//YWDW
        @Column(column = "WHBZ")
        private String whbz;//WHBZ
        @Column(column = "SBZT")
        private String sbzt;//SBZT
        @Column(column = "TYRQ")
        private Date tyrq;//TYRQ
        @Column(column = "ZCXZ")
        private String zcxz;//ZCXZ
        @Column(column = "ZCDW")
        private String zcdw;//ZCDW
        @Column(column = "SFZNBDZ")
        private String sfznbdz;//SFZNBDZ
        @Column(column = "BYJCXJG")
        private String byjcxjg;//BYJCXJG
        @Column(column = "FWFS")
        private String fwfs;//FWFS
        @Column(column = "DZZYJB")
        private String dzzyjb;//DZZYJB
        @Column(column = "SFDW")
        private String sfdw;//SFDW
        @Column(column = "SFNW")
        private String sfnw;//SFNW
        @Column(column = "ZBFS")
        private String zbfs;//ZBFS
        @Column(column = "BZFS")
        private String bzfs;//BZFS
        @Column(column = "WHDJ")
        private String whdj;//WHDJ
        @Column(column = "GCBH")
        private String gcbh;//GCBH
        @Column(column = "GCMC")
        private String gcmc;//GCMC
        @Column(column = "BZ")
        private String bz;//BZ
        @Column(column = "ADDRESS")
        private String address;//ADDRESS
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
        
        public String getSbid() {
		    return sbid;
	    }
        
	    public void setSbid(String sbid) {
		    this.sbid = sbid;
	    }
        
        public String getSbmc() {
		    return sbmc;
	    }
        
	    public void setSbmc(String sbmc) {
		    this.sbmc = sbmc;
	    }
        
        public String getYxbh() {
		    return yxbh;
	    }
        
	    public void setYxbh(String yxbh) {
		    this.yxbh = yxbh;
	    }
        
        public String getDxmpid() {
		    return dxmpid;
	    }
        
	    public void setDxmpid(String dxmpid) {
		    this.dxmpid = dxmpid;
	    }
        
        public String getDydj() {
		    return dydj;
	    }
        
	    public void setDydj(String dydj) {
		    this.dydj = dydj;
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
        
        public String getSbzt() {
		    return sbzt;
	    }
        
	    public void setSbzt(String sbzt) {
		    this.sbzt = sbzt;
	    }
        
        public Date getTyrq() {
		    return tyrq;
	    }
        
	    public void setTyrq(Date tyrq) {
		    this.tyrq = tyrq;
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
        
        public String getSfznbdz() {
		    return sfznbdz;
	    }
        
	    public void setSfznbdz(String sfznbdz) {
		    this.sfznbdz = sfznbdz;
	    }
        
        public String getByjcxjg() {
		    return byjcxjg;
	    }
        
	    public void setByjcxjg(String byjcxjg) {
		    this.byjcxjg = byjcxjg;
	    }
        
        public String getFwfs() {
		    return fwfs;
	    }
        
	    public void setFwfs(String fwfs) {
		    this.fwfs = fwfs;
	    }
        
        public String getDzzyjb() {
		    return dzzyjb;
	    }
        
	    public void setDzzyjb(String dzzyjb) {
		    this.dzzyjb = dzzyjb;
	    }
        
        public String getSfdw() {
		    return sfdw;
	    }
        
	    public void setSfdw(String sfdw) {
		    this.sfdw = sfdw;
	    }
        
        public String getSfnw() {
		    return sfnw;
	    }
        
	    public void setSfnw(String sfnw) {
		    this.sfnw = sfnw;
	    }
        
        public String getZbfs() {
		    return zbfs;
	    }
        
	    public void setZbfs(String zbfs) {
		    this.zbfs = zbfs;
	    }
        
        public String getBzfs() {
		    return bzfs;
	    }
        
	    public void setBzfs(String bzfs) {
		    this.bzfs = bzfs;
	    }
        
        public String getWhdj() {
		    return whdj;
	    }
        
	    public void setWhdj(String whdj) {
		    this.whdj = whdj;
	    }
        
        public String getGcbh() {
		    return gcbh;
	    }
        
	    public void setGcbh(String gcbh) {
		    this.gcbh = gcbh;
	    }
        
        public String getGcmc() {
		    return gcmc;
	    }
        
	    public void setGcmc(String gcmc) {
		    this.gcmc = gcmc;
	    }
        
        public String getBz() {
		    return bz;
	    }
        
	    public void setBz(String bz) {
		    this.bz = bz;
	    }
        
        public String getAddress() {
		    return address;
	    }
        
	    public void setAddress(String address) {
		    this.address = address;
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
 
