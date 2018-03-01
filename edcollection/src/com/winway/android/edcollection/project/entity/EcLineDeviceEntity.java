package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcLineDevice实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:34
 */
@Table(name = "ec_line_device")
public class EcLineDeviceEntity implements Serializable{

        @Id
        @Column(column = "EC_LINE_DEVICE_ID")
        private String ecLineDeviceId;//EC_LINE_DEVICE_ID
        @Column(column = "OID")
        private String oid;//OID
        @Column(column = "EC_LINE_NODES_ID")
        private String ecLineNodesId;//EC_LINE_NODES_ID
        @Column(column = "LINE_NO")
        private String lineNo;//LINE_NO
        @Column(column = "DEVICE_TYPE")
        private String deviceType;//DEVICE_TYPE
        @Column(column = "DEV_OBJ_ID")
        private String devObjId;//DEV_OBJ_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
    
        
        public String getEcLineDeviceId() {
		    return ecLineDeviceId;
	    }
        
	    public void setEcLineDeviceId(String ecLineDeviceId) {
		    this.ecLineDeviceId = ecLineDeviceId;
	    }
        
        public String getOid() {
		    return oid;
	    }
        
	    public void setOid(String oid) {
		    this.oid = oid;
	    }
        
        public String getEcLineNodesId() {
		    return ecLineNodesId;
	    }
        
	    public void setEcLineNodesId(String ecLineNodesId) {
		    this.ecLineNodesId = ecLineNodesId;
	    }
        
        public String getLineNo() {
		    return lineNo;
	    }
        
	    public void setLineNo(String lineNo) {
		    this.lineNo = lineNo;
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
    
}
 
