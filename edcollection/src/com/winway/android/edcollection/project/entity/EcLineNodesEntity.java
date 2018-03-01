package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcLineNodes实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:36
 */
@Table(name = "ec_line_nodes")
public class EcLineNodesEntity implements Serializable{

        @Id
        @Column(column = "EC_LINE_NODES_ID")
        private String ecLineNodesId;//EC_LINE_NODES_ID
        @Column(column = "EC_LINE_ID")
        private String ecLineId;//EC_LINE_ID
        @Column(column = "OID")
        private String oid;//OID
        @Column(column = "TGXX")
        private String tgxx;//TGXX
        @Column(column = "N_INDEX")
        private Integer nIndex;//N_INDEX
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "UP_SPACE")
        private Float upSpace;//UP_SPACE
        @Column(column = "UP_LAY")
        private String upLay;//UP_LAY
        @Column(column = "CABLE_DEEPTH")
        private Double cableDeepth;//CABLE_DEEPTH
        @Column(column = "DEVICE_DESC")
        private String deviceDesc;//DEVICE_DESC
        @Column(column = "REMARK")
        private String remark;//REMARK
    
        
        public String getEcLineNodesId() {
		    return ecLineNodesId;
	    }
        
	    public void setEcLineNodesId(String ecLineNodesId) {
		    this.ecLineNodesId = ecLineNodesId;
	    }
        
        public String getEcLineId() {
		    return ecLineId;
	    }
        
	    public void setEcLineId(String ecLineId) {
		    this.ecLineId = ecLineId;
	    }
        
        public String getOid() {
		    return oid;
	    }
        
	    public void setOid(String oid) {
		    this.oid = oid;
	    }
        
        public String getTgxx() {
		    return tgxx;
	    }
        
	    public void setTgxx(String tgxx) {
		    this.tgxx = tgxx;
	    }
        
        public Integer getNIndex() {
		    return nIndex;
	    }
        
	    public void setNIndex(Integer nIndex) {
		    this.nIndex = nIndex;
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
        
        public Float getUpSpace() {
		    return upSpace;
	    }
        
	    public void setUpSpace(Float upSpace) {
		    this.upSpace = upSpace;
	    }
        
        public String getUpLay() {
		    return upLay;
	    }
        
	    public void setUpLay(String upLay) {
		    this.upLay = upLay;
	    }
        
        public Double getCableDeepth() {
		    return cableDeepth;
	    }
        
	    public void setCableDeepth(Double cableDeepth) {
		    this.cableDeepth = cableDeepth;
	    }
        
        public String getDeviceDesc() {
		    return deviceDesc;
	    }
        
	    public void setDeviceDesc(String deviceDesc) {
		    this.deviceDesc = deviceDesc;
	    }
        
        public String getRemark() {
		    return remark;
	    }
        
	    public void setRemark(String remark) {
		    this.remark = remark;
	    }
    
}
 
