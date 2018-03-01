package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcChannelDlg实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:14
 */
@Table(name = "ec_channel_dlg")
public class EcChannelDlgEntity implements Serializable{

        @Id
        @Column(column = "OBJ_ID")
        private String objId;//OBJ_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "DLGBH")
        private String dlgbh;//DLGBH
        @Column(column = "SSDS")
        private String ssds;//SSDS
        @Column(column = "YWDW")
        private String ywdw;//YWDW
        @Column(column = "WHBZ")
        private String whbz;//WHBZ
        @Column(column = "ZCXZ")
        private String zcxz;//ZCXZ
        @Column(column = "ZCDW")
        private String zcdw;//ZCDW
        @Column(column = "ZCBH")
        private String zcbh;//ZCBH
        @Column(column = "TYRQ")
        private Date tyrq;//TYRQ
        @Column(column = "SBZT")
        private String sbzt;//SBZT
        @Column(column = "DQTZ")
        private String dqtz;//DQTZ
        @Column(column = "DMCC")
        private String dmcc;//DMCC
        @Column(column = "DLGCD")
        private Double dlgcd;//DLGCD
        @Column(column = "DLGBGSL")
        private Integer dlgbgsl;//DLGBGSL
        @Column(column = "GBCZ")
        private String gbcz;//GBCZ
        @Column(column = "SGDW")
        private String sgdw;//SGDW
        @Column(column = "JGRQ")
        private Date jgrq;//JGRQ
        @Column(column = "TZBH")
        private String tzbh;//TZBH
        @Column(column = "ZYFL")
        private String zyfl;//ZYFL
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
        
        public String getDlgbh() {
		    return dlgbh;
	    }
        
	    public void setDlgbh(String dlgbh) {
		    this.dlgbh = dlgbh;
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
        
        public String getZcbh() {
		    return zcbh;
	    }
        
	    public void setZcbh(String zcbh) {
		    this.zcbh = zcbh;
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
        
        public String getDqtz() {
		    return dqtz;
	    }
        
	    public void setDqtz(String dqtz) {
		    this.dqtz = dqtz;
	    }
        
        public String getDmcc() {
		    return dmcc;
	    }
        
	    public void setDmcc(String dmcc) {
		    this.dmcc = dmcc;
	    }
        
        public Double getDlgcd() {
		    return dlgcd;
	    }
        
	    public void setDlgcd(Double dlgcd) {
		    this.dlgcd = dlgcd;
	    }
        
        public Integer getDlgbgsl() {
		    return dlgbgsl;
	    }
        
	    public void setDlgbgsl(Integer dlgbgsl) {
		    this.dlgbgsl = dlgbgsl;
	    }
        
        public String getGbcz() {
		    return gbcz;
	    }
        
	    public void setGbcz(String gbcz) {
		    this.gbcz = gbcz;
	    }
        
        public String getSgdw() {
		    return sgdw;
	    }
        
	    public void setSgdw(String sgdw) {
		    this.sgdw = sgdw;
	    }
        
        public Date getJgrq() {
		    return jgrq;
	    }
        
	    public void setJgrq(Date jgrq) {
		    this.jgrq = jgrq;
	    }
        
        public String getTzbh() {
		    return tzbh;
	    }
        
	    public void setTzbh(String tzbh) {
		    this.tzbh = tzbh;
	    }
        
        public String getZyfl() {
		    return zyfl;
	    }
        
	    public void setZyfl(String zyfl) {
		    this.zyfl = zyfl;
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
 
