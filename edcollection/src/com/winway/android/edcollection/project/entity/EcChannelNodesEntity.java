package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcChannelNodes实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:21
 */
@Table(name = "ec_channel_nodes")
public class EcChannelNodesEntity implements Serializable{

        @Id
        @Column(column = "EC_CHANNEL_NODES_ID")
        private String ecChannelNodesId;//EC_CHANNEL_NODES_ID
        @Column(column = "EC_CHANNEL_ID")
        private String ecChannelId;//EC_CHANNEL_ID
        @Column(column = "OID")
        private String oid;//OID
        @Column(column = "N_INDEX")
        private Integer nIndex;//N_INDEX
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "UP_SPACE")
        private Double upSpace;//UP_SPACE
    
        
        public String getEcChannelNodesId() {
		    return ecChannelNodesId;
	    }
        
	    public void setEcChannelNodesId(String ecChannelNodesId) {
		    this.ecChannelNodesId = ecChannelNodesId;
	    }
        
        public String getEcChannelId() {
		    return ecChannelId;
	    }
        
	    public void setEcChannelId(String ecChannelId) {
		    this.ecChannelId = ecChannelId;
	    }
        
        public String getOid() {
		    return oid;
	    }
        
	    public void setOid(String oid) {
		    this.oid = oid;
	    }
        
        public Integer getNIndex() {
		    return nIndex;
	    }
        
	    public void setNIndex(Integer nIndex) {
		    this.nIndex = nIndex;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
        
        public String getPrjid() {
		    return prjid;
	    }
        
	    public void setPrjid(String prjid) {
		    this.prjid = prjid;
	    }
        
        public Double getUpSpace() {
		    return upSpace;
	    }
        
	    public void setUpSpace(Double upSpace) {
		    this.upSpace = upSpace;
	    }
    
}
 
