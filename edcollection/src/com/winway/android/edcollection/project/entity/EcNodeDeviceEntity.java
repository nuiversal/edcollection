package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcNodeDevice实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:38
 */
@Table(name = "ec_node_device")
public class EcNodeDeviceEntity implements Serializable{

        @Id
        @Column(column = "EC_NODE_DEVICE_ID")
        private String ecNodeDeviceId;//EC_NODE_DEVICE_ID
        @Column(column = "OID")
        private String oid;//OID
        @Column(column = "DEVICE_TYPE")
        private String deviceType;//DEVICE_TYPE
        @Column(column = "DEV_OBJ_ID")
        private String devObjId;//DEV_OBJ_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "SBID")
        private String sbid;//SBID
    
        
        public String getEcNodeDeviceId() {
		    return ecNodeDeviceId;
	    }
        
	    public void setEcNodeDeviceId(String ecNodeDeviceId) {
		    this.ecNodeDeviceId = ecNodeDeviceId;
	    }
        
        public String getOid() {
		    return oid;
	    }
        
	    public void setOid(String oid) {
		    this.oid = oid;
	    }
        
        public String getDeviceType() {
		    return deviceType;
	    }
        
	    public void setDeviceType(String deviceType) {
		    this.deviceType = deviceType;
	    }
        
        public String getDevObjId() {
		    return devObjId;
	    }
        
	    public void setDevObjId(String devObjId) {
		    this.devObjId = devObjId;
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
    
}
 
