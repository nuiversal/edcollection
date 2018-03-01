package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcMarkerIds实体类
 * 
 * @author zgq
 * @since 2017-07-05 15:46:40
 */
@Table(name = "ec_marker_ids")
public class EcMarkerIdsEntity implements Serializable{

        @Id
        @Column(column = "ID")
        private String id;//ID
        @Column(column = "EC_MARKER_IDS_ID")
        private String ecMarkerIdsId;//EC_MARKER_IDS_ID
        @Column(column = "SAVE_TIME")
        private Date saveTime;//SAVE_TIME
    
        
        public String getId() {
		    return id;
	    }
        
	    public void setId(String id) {
		    this.id = id;
	    }
        
        public String getEcMarkerIdsId() {
		    return ecMarkerIdsId;
	    }
        
	    public void setEcMarkerIdsId(String ecMarkerIdsId) {
		    this.ecMarkerIdsId = ecMarkerIdsId;
	    }
        
        public Date getSaveTime() {
		    return saveTime;
	    }
        
	    public void setSaveTime(Date saveTime) {
		    this.saveTime = saveTime;
	    }
    
}
 
