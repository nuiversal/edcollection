package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * D38270000004实体类
 * 
 * @author zgq
 * @since 2016/12/27 14:33:27
 */
@Table(name = "d38270000004")
public class D38270000004Entity {

        @Id
        @Column(column = "oid")
        private String oid;//oid
        @Column(column = "geom")
        private String geom;//geom
        @Column(column = "OBJID")
        private Double objid;//OBJID
        @Column(column = "DEVNAME")
        private String devname;//DEVNAME
        @Column(column = "CLASSID")
        private Double classid;//CLASSID
        @Column(column = "VCODE")
        private Double vcode;//VCODE
        @Column(column = "FID")
        private Double fid;//FID
    
        
        public String getOid() {
		    return oid;
	    }
        
	    public void setOid(String oid) {
		    this.oid = oid;
	    }
        
        public String getGeom() {
		    return geom;
	    }
        
	    public void setGeom(String geom) {
		    this.geom = geom;
	    }
        
        public Double getObjid() {
		    return objid;
	    }
        
	    public void setObjid(Double objid) {
		    this.objid = objid;
	    }
        
        public String getDevname() {
		    return devname;
	    }
        
	    public void setDevname(String devname) {
		    this.devname = devname;
	    }
        
        public Double getClassid() {
		    return classid;
	    }
        
	    public void setClassid(Double classid) {
		    this.classid = classid;
	    }
        
        public Double getVcode() {
		    return vcode;
	    }
        
	    public void setVcode(Double vcode) {
		    this.vcode = vcode;
	    }
        
        public Double getFid() {
		    return fid;
	    }
        
	    public void setFid(Double fid) {
		    this.fid = fid;
	    }
    
}
 
