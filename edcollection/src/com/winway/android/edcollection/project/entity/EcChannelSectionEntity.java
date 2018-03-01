package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcChannelSection实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:22
 */
@Table(name = "ec_channel_section")
public class EcChannelSectionEntity implements Serializable{

        @Id
        @Column(column = "EC_CHANNEL_SECTION_ID")
        private String ecChannelSectionId;//EC_CHANNEL_SECTION_ID
        @Column(column = "EC_CHANNEL_ID")
        private String ecChannelId;//EC_CHANNEL_ID
        @Column(column = "EC_NODE_ID")
        private String ecNodeId;//EC_NODE_ID
        @Column(column = "RES_TYPE")
        private String resType;//RES_TYPE
        @Column(column = "OBJID")
        private String objid;//OBJID
        @Column(column = "ROTATE")
        private Double rotate;//ROTATE
        @Column(column = "SECTION_DESC")
        private String sectionDesc;//SECTION_DESC
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "CJSJ")
        private Date cjsj;//CJSJ
        @Column(column = "GXSJ")
        private Date gxsj;//GXSJ
    
        
        public String getEcChannelSectionId() {
		    return ecChannelSectionId;
	    }
        
	    public void setEcChannelSectionId(String ecChannelSectionId) {
		    this.ecChannelSectionId = ecChannelSectionId;
	    }
        
        public String getEcChannelId() {
		    return ecChannelId;
	    }
        
	    public void setEcChannelId(String ecChannelId) {
		    this.ecChannelId = ecChannelId;
	    }
        
        public String getEcNodeId() {
		    return ecNodeId;
	    }
        
	    public void setEcNodeId(String ecNodeId) {
		    this.ecNodeId = ecNodeId;
	    }
        
        public String getResType() {
		    return resType;
	    }
        
	    public void setResType(String resType) {
		    this.resType = resType;
	    }
        
        public String getObjid() {
		    return objid;
	    }
        
	    public void setObjid(String objid) {
		    this.objid = objid;
	    }
        
        public Double getRotate() {
		    return rotate;
	    }
        
	    public void setRotate(Double rotate) {
		    this.rotate = rotate;
	    }
        
        public String getSectionDesc() {
		    return sectionDesc;
	    }
        
	    public void setSectionDesc(String sectionDesc) {
		    this.sectionDesc = sectionDesc;
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
 
