package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcDistributionRoom实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:25
 */
@Table(name = "ec_distribution_room")
public class EcDistributionRoomEntity implements Serializable{

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
        @Column(column = "SFNW")
        private String sfnw;//SFNW
        @Column(column = "SFDW")
        private String sfdw;//SFDW
        @Column(column = "PBTS")
        private Integer pbts;//PBTS
        @Column(column = "PBZRL")
        private String pbzrl;//PBZRL
        @Column(column = "WGBCRL")
        private String wgbcrl;//WGBCRL
        @Column(column = "FWFS")
        private String fwfs;//FWFS
        @Column(column = "SFDLJZW")
        private String sfdljzw;//SFDLJZW
        @Column(column = "SFDXZ")
        private String sfdxz;//SFDXZ
        @Column(column = "JDDZ")
        private Integer jddz;//JDDZ
        @Column(column = "ZZ")
        private String zz;//ZZ
        @Column(column = "DQTZ")
        private String dqtz;//DQTZ
        @Column(column = "ZYCD")
        private String zycd;//ZYCD
        @Column(column = "GCBH")
        private String gcbh;//GCBH
        @Column(column = "GCMC")
        private String gcmc;//GCMC
        @Column(column = "ZCXZ")
        private String zcxz;//ZCXZ
        @Column(column = "ZCDW")
        private String zcdw;//ZCDW
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
        
        public String getSfnw() {
		    return sfnw;
	    }
        
	    public void setSfnw(String sfnw) {
		    this.sfnw = sfnw;
	    }
        
        public String getSfdw() {
		    return sfdw;
	    }
        
	    public void setSfdw(String sfdw) {
		    this.sfdw = sfdw;
	    }
        
        public Integer getPbts() {
		    return pbts;
	    }
        
	    public void setPbts(Integer pbts) {
		    this.pbts = pbts;
	    }
        
        public String getPbzrl() {
		    return pbzrl;
	    }
        
	    public void setPbzrl(String pbzrl) {
		    this.pbzrl = pbzrl;
	    }
        
        public String getWgbcrl() {
		    return wgbcrl;
	    }
        
	    public void setWgbcrl(String wgbcrl) {
		    this.wgbcrl = wgbcrl;
	    }
        
        public String getFwfs() {
		    return fwfs;
	    }
        
	    public void setFwfs(String fwfs) {
		    this.fwfs = fwfs;
	    }
        
        public String getSfdljzw() {
		    return sfdljzw;
	    }
        
	    public void setSfdljzw(String sfdljzw) {
		    this.sfdljzw = sfdljzw;
	    }
        
        public String getSfdxz() {
		    return sfdxz;
	    }
        
	    public void setSfdxz(String sfdxz) {
		    this.sfdxz = sfdxz;
	    }
        
        public Integer getJddz() {
		    return jddz;
	    }
        
	    public void setJddz(Integer jddz) {
		    this.jddz = jddz;
	    }
        
        public String getZz() {
		    return zz;
	    }
        
	    public void setZz(String zz) {
		    this.zz = zz;
	    }
        
        public String getDqtz() {
		    return dqtz;
	    }
        
	    public void setDqtz(String dqtz) {
		    this.dqtz = dqtz;
	    }
        
        public String getZycd() {
		    return zycd;
	    }
        
	    public void setZycd(String zycd) {
		    this.zycd = zycd;
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
 
