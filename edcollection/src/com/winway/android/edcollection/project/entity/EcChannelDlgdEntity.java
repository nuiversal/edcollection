package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcChannelDlgd实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:16
 */
@Table(name = "ec_channel_dlgd")
public class EcChannelDlgdEntity implements Serializable{

        @Id
        @Column(column = "OBJ_ID")
        private String objId;//OBJ_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "MBMC")
        private String mbmc;//MBMC
        @Column(column = "ROW")
        private Integer row;//ROW
        @Column(column = "COL")
        private Integer col;//COL
        @Column(column = "GDBH")
        private String gdbh;//GDBH
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
        @Column(column = "DQTZ")
        private String dqtz;//DQTZ
        @Column(column = "SBZT")
        private String sbzt;//SBZT
        @Column(column = "TYRQ")
        private Date tyrq;//TYRQ
        @Column(column = "DAMC")
        private String damc;//DAMC
        @Column(column = "JMLX")
        private String jmlx;//JMLX
        @Column(column = "CL")
        private String cl;//CL
        @Column(column = "GJ")
        private Double gj;//GJ
        @Column(column = "GDCD")
        private Double gdcd;//GDCD
        @Column(column = "CGSL")
        private Integer cgsl;//CGSL
        @Column(column = "JSL")
        private Integer jsl;//JSL
        @Column(column = "GDLX")
        private String gdlx;//GDLX
        @Column(column = "SGDW")
        private String sgdw;//SGDW
        @Column(column = "SGFS")
        private String sgfs;//SGFS
        @Column(column = "JGRQ")
        private Date jgrq;//JGRQ
        @Column(column = "TZBH")
        private String tzbh;//TZBH
        @Column(column = "ZYFL")
        private String zyfl;//ZYFL
        @Column(column = "GDRL")
        private Integer gdrl;//GDRL
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
        
        public String getMbmc() {
		    return mbmc;
	    }
        
	    public void setMbmc(String mbmc) {
		    this.mbmc = mbmc;
	    }
        
        public Integer getRow() {
		    return row;
	    }
        
	    public void setRow(Integer row) {
		    this.row = row;
	    }
        
        public Integer getCol() {
		    return col;
	    }
        
	    public void setCol(Integer col) {
		    this.col = col;
	    }
        
        public String getGdbh() {
		    return gdbh;
	    }
        
	    public void setGdbh(String gdbh) {
		    this.gdbh = gdbh;
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
        
        public String getDqtz() {
		    return dqtz;
	    }
        
	    public void setDqtz(String dqtz) {
		    this.dqtz = dqtz;
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
        
        public String getDamc() {
		    return damc;
	    }
        
	    public void setDamc(String damc) {
		    this.damc = damc;
	    }
        
        public String getJmlx() {
		    return jmlx;
	    }
        
	    public void setJmlx(String jmlx) {
		    this.jmlx = jmlx;
	    }
        
        public String getCl() {
		    return cl;
	    }
        
	    public void setCl(String cl) {
		    this.cl = cl;
	    }
        
        public Double getGj() {
		    return gj;
	    }
        
	    public void setGj(Double gj) {
		    this.gj = gj;
	    }
        
        public Double getGdcd() {
		    return gdcd;
	    }
        
	    public void setGdcd(Double gdcd) {
		    this.gdcd = gdcd;
	    }
        
        public Integer getCgsl() {
		    return cgsl;
	    }
        
	    public void setCgsl(Integer cgsl) {
		    this.cgsl = cgsl;
	    }
        
        public Integer getJsl() {
		    return jsl;
	    }
        
	    public void setJsl(Integer jsl) {
		    this.jsl = jsl;
	    }
        
        public String getGdlx() {
		    return gdlx;
	    }
        
	    public void setGdlx(String gdlx) {
		    this.gdlx = gdlx;
	    }
        
        public String getSgdw() {
		    return sgdw;
	    }
        
	    public void setSgdw(String sgdw) {
		    this.sgdw = sgdw;
	    }
        
        public String getSgfs() {
		    return sgfs;
	    }
        
	    public void setSgfs(String sgfs) {
		    this.sgfs = sgfs;
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
        
        public Integer getGdrl() {
		    return gdrl;
	    }
        
	    public void setGdrl(Integer gdrl) {
		    this.gdrl = gdrl;
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
 
