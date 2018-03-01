package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcDydlfzx实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:28
 */
@Table(name = "ec_dydlfzx")
public class EcDydlfzxEntity implements Serializable{

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
        @Column(column = "DLDJ")
        private String dldj;//DLDJ
        @Column(column = "SSDS")
        private String ssds;//SSDS
        @Column(column = "YWDW")
        private String ywdw;//YWDW
        @Column(column = "WHBZ")
        private String whbz;//WHBZ
        @Column(column = "SBLX")
        private String sblx;//SBLX
        @Column(column = "TYRQ")
        private Date tyrq;//TYRQ
        @Column(column = "SBZT")
        private String sbzt;//SBZT
        @Column(column = "ZCXZ")
        private String zcxz;//ZCXZ
        @Column(column = "ZCDW")
        private String zcdw;//ZCDW
        @Column(column = "XH")
        private String xh;//XH
        @Column(column = "SCCJ")
        private String sccj;//SCCJ
        @Column(column = "CCBH")
        private String ccbh;//CCBH
        @Column(column = "CCRQ")
        private Date ccrq;//CCRQ
        @Column(column = "EDDY")
        private String eddy;//EDDY
        @Column(column = "EDDL")
        private String eddl;//EDDL
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
        
        public String getDldj() {
		    return dldj;
	    }
        
	    public void setDldj(String dldj) {
		    this.dldj = dldj;
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
        
        public String getSblx() {
		    return sblx;
	    }
        
	    public void setSblx(String sblx) {
		    this.sblx = sblx;
	    }
        
        public Date getTyrq() {
		    return tyrq;
	    }
        
	    public void setTyrq(Date tyrq) {
		    this.tyrq = tyrq;
	    }
        
        public String getSbzt() {
		    return sbzt;
	    }
        
	    public void setSbzt(String sbzt) {
		    this.sbzt = sbzt;
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
        
        public String getXh() {
		    return xh;
	    }
        
	    public void setXh(String xh) {
		    this.xh = xh;
	    }
        
        public String getSccj() {
		    return sccj;
	    }
        
	    public void setSccj(String sccj) {
		    this.sccj = sccj;
	    }
        
        public String getCcbh() {
		    return ccbh;
	    }
        
	    public void setCcbh(String ccbh) {
		    this.ccbh = ccbh;
	    }
        
        public Date getCcrq() {
		    return ccrq;
	    }
        
	    public void setCcrq(Date ccrq) {
		    this.ccrq = ccrq;
	    }
        
        public String getEddy() {
		    return eddy;
	    }
        
	    public void setEddy(String eddy) {
		    this.eddy = eddy;
	    }
        
        public String getEddl() {
		    return eddl;
	    }
        
	    public void setEddl(String eddl) {
		    this.eddl = eddl;
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
 
