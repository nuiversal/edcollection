package com.winway.android.edcollection.project.entity;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * EcWorkWell实体类
 * 
 * @author zgq
 * @since 2017-07-17 11:03:01
 */
@Table(name = "ec_work_well")
public class EcWorkWellEntity implements Serializable{

        @Id
        @Column(column = "OBJ_ID")
        private String objId;//工井标识
        @Column(column = "DMXX")
        private String dmxx;//断面信息
        @Column(column = "GCMC")
        private String gcmc;//工程名称
        @Column(column = "GBHD")
        private Double gbhd;//盖板厚度|单位厘米
        @Column(column = "GBKS")
        private Integer gbks;//盖板块数
        @Column(column = "PRJID")
        private String prjid;//所属项目
        @Column(column = "ORGID")
        private String orgid;//所属机构
        @Column(column = "YWDW")
        private String ywdw;//运维单位
        @Column(column = "WHBZ")
        private String whbz;//维护班组
        @Column(column = "SBMC")
        private String sbmc;//设备名称
        @Column(column = "SSZRQ")
        private String sszrq;//所属责任区
        @Column(column = "GJXZ")
        private String gjxz;//工井形状
        @Column(column = "SSDS")
        private String ssds;//所属地市
        @Column(column = "DQTZ")
        private String dqtz;//地区特征
        @Column(column = "DLWZ")
        private String dlwz;//地理位置
        @Column(column = "JWZ")
        private String jwz;//井位置
        @Column(column = "JLX")
        private String jlx;//井类型|直线井,接头井,转角井,三通井,四通井,顶管工井
        @Column(column = "JG")
        private String jg;//结构|带室,不带室
        @Column(column = "JMGC")
        private Double jmgc;//井面高程(m)
        @Column(column = "NDGC")
        private Double ndgc;//内底高程(m)
        @Column(column = "JGXZ")
        private String jgxz;//井盖形状|方形,圆形
        @Column(column = "JGCC")
        private Double jgcc;//井盖尺寸(m)
        @Column(column = "JGCZ")
        private String jgcz;//井盖材质|球墨,铁,水泥,复合材料,聚酯,其他
        @Column(column = "JGSCCJ")
        private String jgsccj;//井盖生产厂家
        @Column(column = "JGCCRQ")
        private Date jgccrq;//井盖出厂日期
        @Column(column = "PTCS")
        private String ptcs;//平台层数
        @Column(column = "SGDW")
        private String sgdw;//施工单位
        @Column(column = "SGRQ")
        private Date sgrq;//施工日期
        @Column(column = "JGRQ")
        private Date jgrq;//峻工日期
        @Column(column = "TZBH")
        private String tzbh;//图纸编号
        @Column(column = "ZCXZ")
        private String zcxz;//资产性质|国家电网公司,分部,省（直辖市、自治区）公司,子公司,用户
        @Column(column = "ZCDW")
        private String zcdw;//资产单位
        @Column(column = "ZCBH")
        private String zcbh;//资产编号
        @Column(column = "ZYFL")
        private String zyfl;//专业分类
        @Column(column = "BZ")
        private String bz;//备注
        @Column(column = "CJSJ")
        private Date cjsj;//采集时间
        @Column(column = "GXSJ")
        private Date gxsj;//更新时间
        @Column(column = "DLLDJL")
        private Double dlldjl;//电缆离地面距离(cm)
        @Column(column = "DLLJDJL")
        private Double dlljdjl;//电缆离井底距离(cm)
        @Column(column = "DLLZCJL")
        private Double dllzcjl;//电缆离左侧距离(cm)
        @Column(column = "DLLYCJL")
        private Double dllycjl;//电缆离右侧距离(cm)
        @Column(column = "GJCD")
        private Double gjcd;//工井长度(cm)
        @Column(column = "GJKD")
        private Double gjkd;//工井宽度(cm)
        @Column(column = "GJSD")
        private Double gjsd;//工井深度(cm)
        @Column(column = "JKJGBH")
        private String jkjgbh;//监控井盖编号
        @Column(column = "XYGJFX")
        private String xygjfx;//下一工井方向
        @Column(column = "JXYGJJL")
        private Double jxygjjl;//距下一工井距离
        @Column(column = "FSSSQK")
        private String fsssqk;//附属设施情况
        @Column(column = "TDDMC")
        private String tddmc;//通道段名称
        @Column(column = "JQDWZ")
        private String jqdwz;//方向+距起点位置
    
        
        public String getObjId() {
		    return objId;
	    }
        
	    public void setObjId(String objId) {
		    this.objId = objId;
	    }
        
        public String getDmxx() {
		    return dmxx;
	    }
        
	    public void setDmxx(String dmxx) {
		    this.dmxx = dmxx;
	    }
        
        public String getGcmc() {
		    return gcmc;
	    }
        
	    public void setGcmc(String gcmc) {
		    this.gcmc = gcmc;
	    }
        
        public Double getGbhd() {
		    return gbhd;
	    }
        
	    public void setGbhd(Double gbhd) {
		    this.gbhd = gbhd;
	    }
        
        public Integer getGbks() {
		    return gbks;
	    }
        
	    public void setGbks(Integer gbks) {
		    this.gbks = gbks;
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
        
        public String getSbmc() {
		    return sbmc;
	    }
        
	    public void setSbmc(String sbmc) {
		    this.sbmc = sbmc;
	    }
        
        public String getSszrq() {
		    return sszrq;
	    }
        
	    public void setSszrq(String sszrq) {
		    this.sszrq = sszrq;
	    }
        
        public String getGjxz() {
		    return gjxz;
	    }
        
	    public void setGjxz(String gjxz) {
		    this.gjxz = gjxz;
	    }
        
        public String getSsds() {
		    return ssds;
	    }
        
	    public void setSsds(String ssds) {
		    this.ssds = ssds;
	    }
        
        public String getDqtz() {
		    return dqtz;
	    }
        
	    public void setDqtz(String dqtz) {
		    this.dqtz = dqtz;
	    }
        
        public String getDlwz() {
		    return dlwz;
	    }
        
	    public void setDlwz(String dlwz) {
		    this.dlwz = dlwz;
	    }
        
        public String getJwz() {
		    return jwz;
	    }
        
	    public void setJwz(String jwz) {
		    this.jwz = jwz;
	    }
        
        public String getJlx() {
		    return jlx;
	    }
        
	    public void setJlx(String jlx) {
		    this.jlx = jlx;
	    }
        
        public String getJg() {
		    return jg;
	    }
        
	    public void setJg(String jg) {
		    this.jg = jg;
	    }
        
        public Double getJmgc() {
		    return jmgc;
	    }
        
	    public void setJmgc(Double jmgc) {
		    this.jmgc = jmgc;
	    }
        
        public Double getNdgc() {
		    return ndgc;
	    }
        
	    public void setNdgc(Double ndgc) {
		    this.ndgc = ndgc;
	    }
        
        public String getJgxz() {
		    return jgxz;
	    }
        
	    public void setJgxz(String jgxz) {
		    this.jgxz = jgxz;
	    }
        
        public Double getJgcc() {
		    return jgcc;
	    }
        
	    public void setJgcc(Double jgcc) {
		    this.jgcc = jgcc;
	    }
        
        public String getJgcz() {
		    return jgcz;
	    }
        
	    public void setJgcz(String jgcz) {
		    this.jgcz = jgcz;
	    }
        
        public String getJgsccj() {
		    return jgsccj;
	    }
        
	    public void setJgsccj(String jgsccj) {
		    this.jgsccj = jgsccj;
	    }
        
        public Date getJgccrq() {
		    return jgccrq;
	    }
        
	    public void setJgccrq(Date jgccrq) {
		    this.jgccrq = jgccrq;
	    }
        
        public String getPtcs() {
		    return ptcs;
	    }
        
	    public void setPtcs(String ptcs) {
		    this.ptcs = ptcs;
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
        
        public String getTzbh() {
		    return tzbh;
	    }
        
	    public void setTzbh(String tzbh) {
		    this.tzbh = tzbh;
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
        
        public Double getDlldjl() {
		    return dlldjl;
	    }
        
	    public void setDlldjl(Double dlldjl) {
		    this.dlldjl = dlldjl;
	    }
        
        public Double getDlljdjl() {
		    return dlljdjl;
	    }
        
	    public void setDlljdjl(Double dlljdjl) {
		    this.dlljdjl = dlljdjl;
	    }
        
        public Double getDllzcjl() {
		    return dllzcjl;
	    }
        
	    public void setDllzcjl(Double dllzcjl) {
		    this.dllzcjl = dllzcjl;
	    }
        
        public Double getDllycjl() {
		    return dllycjl;
	    }
        
	    public void setDllycjl(Double dllycjl) {
		    this.dllycjl = dllycjl;
	    }
        
        public Double getGjcd() {
		    return gjcd;
	    }
        
	    public void setGjcd(Double gjcd) {
		    this.gjcd = gjcd;
	    }
        
        public Double getGjkd() {
		    return gjkd;
	    }
        
	    public void setGjkd(Double gjkd) {
		    this.gjkd = gjkd;
	    }
        
        public Double getGjsd() {
		    return gjsd;
	    }
        
	    public void setGjsd(Double gjsd) {
		    this.gjsd = gjsd;
	    }
        
        public String getJkjgbh() {
		    return jkjgbh;
	    }
        
	    public void setJkjgbh(String jkjgbh) {
		    this.jkjgbh = jkjgbh;
	    }
        
        public String getXygjfx() {
		    return xygjfx;
	    }
        
	    public void setXygjfx(String xygjfx) {
		    this.xygjfx = xygjfx;
	    }
        
        public Double getJxygjjl() {
		    return jxygjjl;
	    }
        
	    public void setJxygjjl(Double jxygjjl) {
		    this.jxygjjl = jxygjjl;
	    }
        
        public String getFsssqk() {
		    return fsssqk;
	    }
        
	    public void setFsssqk(String fsssqk) {
		    this.fsssqk = fsssqk;
	    }
        
        public String getTddmc() {
		    return tddmc;
	    }
        
	    public void setTddmc(String tddmc) {
		    this.tddmc = tddmc;
	    }
        
        public String getJqdwz() {
		    return jqdwz;
	    }
        
	    public void setJqdwz(String jqdwz) {
		    this.jqdwz = jqdwz;
	    }
    
}
 
