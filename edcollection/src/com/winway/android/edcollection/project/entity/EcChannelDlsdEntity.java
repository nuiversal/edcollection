package com.winway.android.edcollection.project.entity;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * EcChannelDlsd实体类
 * 
 * @author zgq
 * @since 2017-07-17 11:02:56
 */
@Table(name = "ec_channel_dlsd")
public class EcChannelDlsdEntity implements Serializable{

        @Id
        @Column(column = "OBJ_ID")
        private String objId;//隧道标识
        @Column(column = "PRJID")
        private String prjid;//所属项目
        @Column(column = "ORGID")
        private String orgid;//所属机构
        @Column(column = "SDBH")
        private String sdbh;//隧道编号
        @Column(column = "SSDS")
        private String ssds;//所属地市
        @Column(column = "YWDW")
        private String ywdw;//运维单位
        @Column(column = "YWBZ")
        private String ywbz;//维护班组,可以不填，那么导入后设备的所属地市默认为登录人所在的班组;如果填写，请填写ISC_SPECIALORG_UNIT_LOCEXT表中对应单位的ISC_ID值
        @Column(column = "SBZT")
        private String sbzt;//设备状态,未投运,在运,现场留用
        @Column(column = "ZCXZ")
        private String zcxz;//资产性质,国家电网公司,分部,省（直辖市、自治区）公司,子公司,用户
        @Column(column = "ZCDW")
        private String zcdw;//资产单位,请填写ISC_SPECIALORG_UNIT_LOCEXT表中对应单位的ISC_ID值
        @Column(column = "TYRQ")
        private Date tyrq;//投运日期
        @Column(column = "DQTZ")
        private String dqtz;//地区特征,市中心区,市区,城镇,县城区,农村,乡镇,农牧区
        @Column(column = "DAMC")
        private String damc;//档案名称
        @Column(column = "JGXS")
        private String jgxs;//结构型式,砖混,顶管,方沟,单衬,双衬,盾构,浇筑
        @Column(column = "XGFS")
        private String xgfs;//悬挂方式,支架,涨圈,挂钩
        @Column(column = "JMLX")
        private String jmlx;//截面类型
        @Column(column = "DMCC")
        private String dmcc;//断面尺寸
        @Column(column = "SDCD")
        private Double sdcd;//隧道长度(m)
        @Column(column = "JSL")
        private Integer jsl;//井数量(个)
        @Column(column = "SGDW")
        private String sgdw;//施工单位
        @Column(column = "JGRQ")
        private Date jgrq;//峻工日期
        @Column(column = "TZBH")
        private String tzbh;//图纸编号
        @Column(column = "ZYFL")
        private String zyfl;//专业分类:输电,配电
        @Column(column = "SFAZFHC")
        private String sfazfhc;//是否安装防火槽盒
        @Column(column = "SDRL")
        private Integer sdrl;//隧道容量
        @Column(column = "CGSL")
        private Integer cgsl;//穿管数量
        @Column(column = "BZ")
        private String bz;//备注
        @Column(column = "CJSJ")
        private Date cjsj;//采集时间
        @Column(column = "GXSJ")
        private Date gxsj;//更新时间
        @Column(column = "SDMC")
        private String sdmc;//隧道名称
        @Column(column = "JQDJL")
        private String jqdjl;//方向+距起点距离
        @Column(column = "SDDCD")
        private Double sddcd;//隧道段长度
    
        
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
        
        public String getSdbh() {
		    return sdbh;
	    }
        
	    public void setSdbh(String sdbh) {
		    this.sdbh = sdbh;
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
        
        public String getYwbz() {
		    return ywbz;
	    }
        
	    public void setYwbz(String ywbz) {
		    this.ywbz = ywbz;
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
        
        public Date getTyrq() {
		    return tyrq;
	    }
        
	    public void setTyrq(Date tyrq) {
		    this.tyrq = tyrq;
	    }
        
        public String getDqtz() {
		    return dqtz;
	    }
        
	    public void setDqtz(String dqtz) {
		    this.dqtz = dqtz;
	    }
        
        public String getDamc() {
		    return damc;
	    }
        
	    public void setDamc(String damc) {
		    this.damc = damc;
	    }
        
        public String getJgxs() {
		    return jgxs;
	    }
        
	    public void setJgxs(String jgxs) {
		    this.jgxs = jgxs;
	    }
        
        public String getXgfs() {
		    return xgfs;
	    }
        
	    public void setXgfs(String xgfs) {
		    this.xgfs = xgfs;
	    }
        
        public String getJmlx() {
		    return jmlx;
	    }
        
	    public void setJmlx(String jmlx) {
		    this.jmlx = jmlx;
	    }
        
        public String getDmcc() {
		    return dmcc;
	    }
        
	    public void setDmcc(String dmcc) {
		    this.dmcc = dmcc;
	    }
        
        public Double getSdcd() {
		    return sdcd;
	    }
        
	    public void setSdcd(Double sdcd) {
		    this.sdcd = sdcd;
	    }
        
        public Integer getJsl() {
		    return jsl;
	    }
        
	    public void setJsl(Integer jsl) {
		    this.jsl = jsl;
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
        
        public String getSfazfhc() {
		    return sfazfhc;
	    }
        
	    public void setSfazfhc(String sfazfhc) {
		    this.sfazfhc = sfazfhc;
	    }
        
        public Integer getSdrl() {
		    return sdrl;
	    }
        
	    public void setSdrl(Integer sdrl) {
		    this.sdrl = sdrl;
	    }
        
        public Integer getCgsl() {
		    return cgsl;
	    }
        
	    public void setCgsl(Integer cgsl) {
		    this.cgsl = cgsl;
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
        
        public String getSdmc() {
		    return sdmc;
	    }
        
	    public void setSdmc(String sdmc) {
		    this.sdmc = sdmc;
	    }
        
        public String getJqdjl() {
		    return jqdjl;
	    }
        
	    public void setJqdjl(String jqdjl) {
		    this.jqdjl = jqdjl;
	    }
        
        public Double getSddcd() {
		    return sddcd;
	    }
        
	    public void setSddcd(Double sddcd) {
		    this.sddcd = sddcd;
	    }
    
}
 
