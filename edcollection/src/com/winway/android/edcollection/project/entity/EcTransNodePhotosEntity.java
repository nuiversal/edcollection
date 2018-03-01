package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcTransNodePhotos实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:41
 */
@Table(name = "ec_trans_node_photos")
public class EcTransNodePhotosEntity implements Serializable{

        @Id
        @Column(column = "id")
        private String id;//id
        @Column(column = "oid")
        private String oid;//oid
        @Column(column = "bg_photo")
        private String bgPhoto;//bg_photo
        @Column(column = "pos_photo")
        private String posPhoto;//pos_photo
        @Column(column = "orgid")
        private String orgid;//orgid
        @Column(column = "prjid")
        private String prjid;//prjid
    
        
        public String getId() {
		    return id;
	    }
        
	    public void setId(String id) {
		    this.id = id;
	    }
        
        public String getOid() {
		    return oid;
	    }
        
	    public void setOid(String oid) {
		    this.oid = oid;
	    }
        
        public String getBgPhoto() {
		    return bgPhoto;
	    }
        
	    public void setBgPhoto(String bgPhoto) {
		    this.bgPhoto = bgPhoto;
	    }
        
        public String getPosPhoto() {
		    return posPhoto;
	    }
        
	    public void setPosPhoto(String posPhoto) {
		    this.posPhoto = posPhoto;
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
    
}
 
