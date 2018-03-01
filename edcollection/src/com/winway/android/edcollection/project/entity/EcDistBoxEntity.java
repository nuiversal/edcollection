package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * EcDistBox实体类
 * 
 * @author zgq
 * @since 2017/2/14 8:57:04
 */
@Table(name = "ec_dist_box")
public class EcDistBoxEntity {

        @Id
        @Column(column = "EC_DIST_BOX_ID")
        private String ecDistBoxId;//EC_DIST_BOX_ID
        @Column(column = "DEV_NAME")
        private String devName;//DEV_NAME
        @Column(column = "JOINT_TYPE")
        private Integer jointType;//接线类型| 1 分接箱，2 环网柜
        @Column(column = "LINES_COUNT")
        private Integer linesCount;//LINES_COUNT
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
    
        
        public String getEcDistBoxId() {
		    return ecDistBoxId;
	    }
        
	    public void setEcDistBoxId(String ecDistBoxId) {
		    this.ecDistBoxId = ecDistBoxId;
	    }
        
        public String getDevName() {
		    return devName;
	    }
        
	    public void setDevName(String devName) {
		    this.devName = devName;
	    }
        
        public Integer getJointType() {
		    return jointType;
	    }
        
	    public void setJointType(Integer jointType) {
		    this.jointType = jointType;
	    }
        
        public Integer getLinesCount() {
		    return linesCount;
	    }
        
	    public void setLinesCount(Integer linesCount) {
		    this.linesCount = linesCount;
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
 
