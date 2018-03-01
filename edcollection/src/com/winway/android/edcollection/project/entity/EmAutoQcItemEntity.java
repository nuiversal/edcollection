package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EmAutoQcItem实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:49
 */
@Table(name = "em_auto_qc_item")
public class EmAutoQcItemEntity implements Serializable{

        @Id
        @Column(column = "EM_AUTO_QC_ITEM_ID")
        private String emAutoQcItemId;//EM_AUTO_QC_ITEM_ID
        @Column(column = "EM_AUTO_QC_ID")
        private String emAutoQcId;//EM_AUTO_QC_ID
        @Column(column = "DEV_TYPE")
        private Integer devType;//DEV_TYPE
        @Column(column = "OBJ_ID")
        private String objId;//OBJ_ID
        @Column(column = "PROBLEM_TYPES")
        private Integer problemTypes;//PROBLEM_TYPES
        @Column(column = "DESCRIPTION")
        private String description;//DESCRIPTION
    
        
        public String getEmAutoQcItemId() {
		    return emAutoQcItemId;
	    }
        
	    public void setEmAutoQcItemId(String emAutoQcItemId) {
		    this.emAutoQcItemId = emAutoQcItemId;
	    }
        
        public String getEmAutoQcId() {
		    return emAutoQcId;
	    }
        
	    public void setEmAutoQcId(String emAutoQcId) {
		    this.emAutoQcId = emAutoQcId;
	    }
        
        public Integer getDevType() {
		    return devType;
	    }
        
	    public void setDevType(Integer devType) {
		    this.devType = devType;
	    }
        
        public String getObjId() {
		    return objId;
	    }
        
	    public void setObjId(String objId) {
		    this.objId = objId;
	    }
        
        public Integer getProblemTypes() {
		    return problemTypes;
	    }
        
	    public void setProblemTypes(Integer problemTypes) {
		    this.problemTypes = problemTypes;
	    }
        
        public String getDescription() {
		    return description;
	    }
        
	    public void setDescription(String description) {
		    this.description = description;
	    }
    
}
 
