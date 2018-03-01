package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * CMap实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:19
 */
@Table(name = "c_map")
public class CMapEntity implements Serializable{

        @Id
        @Column(column = "C_MAP_ID")
        private Integer cMapId;//C_MAP_ID
        @Column(column = "MAP_NO")
        private String mapNo;//MAP_NO
        @Column(column = "DOC_NAME")
        private String docName;//DOC_NAME
        @Column(column = "DOC_PATH")
        private String docPath;//DOC_PATH
        @Column(column = "CREATE_TIME")
        private Date createTime;//CREATE_TIME
        @Column(column = "LAST_VISIT")
        private Double lastVisit;//LAST_VISIT
    
        
        public Integer getCMapId() {
		    return cMapId;
	    }
        
	    public void setCMapId(Integer cMapId) {
		    this.cMapId = cMapId;
	    }
        
        public String getMapNo() {
		    return mapNo;
	    }
        
	    public void setMapNo(String mapNo) {
		    this.mapNo = mapNo;
	    }
        
        public String getDocName() {
		    return docName;
	    }
        
	    public void setDocName(String docName) {
		    this.docName = docName;
	    }
        
        public String getDocPath() {
		    return docPath;
	    }
        
	    public void setDocPath(String docPath) {
		    this.docPath = docPath;
	    }
        
        public Date getCreateTime() {
		    return createTime;
	    }
        
	    public void setCreateTime(Date createTime) {
		    this.createTime = createTime;
	    }
        
        public Double getLastVisit() {
		    return lastVisit;
	    }
        
	    public void setLastVisit(Double lastVisit) {
		    this.lastVisit = lastVisit;
	    }
    
}
 
