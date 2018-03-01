package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcChannelLines实体类
 * 
 * @author zgq
 * @since 2017-07-05 15:46:40
 */
@Table(name = "ec_channel_lines")
public class EcChannelLinesEntity implements Serializable{

        @Id
        @Column(column = "EC_CHANNEL_LINES_ID")
        private String ecChannelLinesId;//EC_CHANNEL_LINES_ID
        @Column(column = "EC_CHANNEL_ID")
        private String ecChannelId;//通道标识
        @Column(column = "LINE_ID")
        private String lineId;//LINE_ID
        @Column(column = "PRJID")
        private String prjid;//所属项目
        @Column(column = "ORGID")
        private String orgid;//所属机构
    
        
        public String getEcChannelLinesId() {
		    return ecChannelLinesId;
	    }
        
	    public void setEcChannelLinesId(String ecChannelLinesId) {
		    this.ecChannelLinesId = ecChannelLinesId;
	    }
        
        public String getEcChannelId() {
		    return ecChannelId;
	    }
        
	    public void setEcChannelId(String ecChannelId) {
		    this.ecChannelId = ecChannelId;
	    }
        
        public String getLineId() {
		    return lineId;
	    }
        
	    public void setLineId(String lineId) {
		    this.lineId = lineId;
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
 
