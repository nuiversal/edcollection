package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcDestroyInfo实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:23
 */
@Table(name = "ec_destroy_info")
public class EcDestroyInfoEntity implements Serializable{

        @Column(column = "CREATE_TIME")
        private String createTime;//CREATE_TIME
        @Column(column = "CREATE_USER")
        private String createUser;//CREATE_USER
        @Id
        @Column(column = "OBJ_ID")
        private String objId;//OBJ_ID
        @Column(column = "ID_1")
        private String id1;//ID_1
        @Column(column = "NAME")
        private String name;//NAME
        @Column(column = "TYPE")
        private String type;//TYPE
        @Column(column = "VISABLE")
        private String visable;//VISABLE
        @Column(column = "NOTE")
        private String note;//NOTE
        @Column(column = "PICTURE")
        private String picture;//PICTURE
        @Column(column = "GEOM")
        private String geom;//GEOM
        @Column(column = "ORGID")
        private String orgid;//ORGID
    
        
        public String getCreateTime() {
		    return createTime;
	    }
        
	    public void setCreateTime(String createTime) {
		    this.createTime = createTime;
	    }
        
        public String getCreateUser() {
		    return createUser;
	    }
        
	    public void setCreateUser(String createUser) {
		    this.createUser = createUser;
	    }
        
        public String getObjId() {
		    return objId;
	    }
        
	    public void setObjId(String objId) {
		    this.objId = objId;
	    }
        
        public String getId1() {
		    return id1;
	    }
        
	    public void setId1(String id1) {
		    this.id1 = id1;
	    }
        
        public String getName() {
		    return name;
	    }
        
	    public void setName(String name) {
		    this.name = name;
	    }
        
        public String getType() {
		    return type;
	    }
        
	    public void setType(String type) {
		    this.type = type;
	    }
        
        public String getVisable() {
		    return visable;
	    }
        
	    public void setVisable(String visable) {
		    this.visable = visable;
	    }
        
        public String getNote() {
		    return note;
	    }
        
	    public void setNote(String note) {
		    this.note = note;
	    }
        
        public String getPicture() {
		    return picture;
	    }
        
	    public void setPicture(String picture) {
		    this.picture = picture;
	    }
        
        public String getGeom() {
		    return geom;
	    }
        
	    public void setGeom(String geom) {
		    this.geom = geom;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
    
}
 
