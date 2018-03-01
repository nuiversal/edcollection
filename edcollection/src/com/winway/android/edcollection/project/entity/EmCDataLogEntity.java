package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EmCDataLog实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:50
 */
@Table(name = "em_c_data_log")
public class EmCDataLogEntity implements Serializable{

        @Id
        @Column(column = "EM_C_DATA_LOG_ID")
        private Integer emCDataLogId;//EM_C_DATA_LOG_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "TABLE_NAME")
        private String tableName;//TABLE_NAME
        @Column(column = "DATA_KEY")
        private String dataKey;//DATA_KEY
        @Column(column = "EM_TASKS_ID")
        private String emTasksId;//EM_TASKS_ID
        @Column(column = "EM_TASK_ITEM_ID")
        private String emTaskItemId;//EM_TASK_ITEM_ID
        @Column(column = "OPT_TYPE")
        private Integer optType;//OPT_TYPE
        @Column(column = "EXECUTOR")
        private String executor;//EXECUTOR
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//UPDATE_TIME
        @Column(column = "IS_UPLOAD")
        private Integer isUpload;//IS_UPLOAD
        @Column(column = "DESCRIPTION")
        private String description;//DESCRIPTION
        @Column(column = "ORGID")
        private String orgid;//ORGID
    
        
        public Integer getEmCDataLogId() {
		    return emCDataLogId;
	    }
        
	    public void setEmCDataLogId(Integer emCDataLogId) {
		    this.emCDataLogId = emCDataLogId;
	    }
        
        public String getPrjid() {
		    return prjid;
	    }
        
	    public void setPrjid(String prjid) {
		    this.prjid = prjid;
	    }
        
        public String getTableName() {
		    return tableName;
	    }
        
	    public void setTableName(String tableName) {
		    this.tableName = tableName;
	    }
        
        public String getDataKey() {
		    return dataKey;
	    }
        
	    public void setDataKey(String dataKey) {
		    this.dataKey = dataKey;
	    }
        
        public String getEmTasksId() {
		    return emTasksId;
	    }
        
	    public void setEmTasksId(String emTasksId) {
		    this.emTasksId = emTasksId;
	    }
        
        public String getEmTaskItemId() {
		    return emTaskItemId;
	    }
        
	    public void setEmTaskItemId(String emTaskItemId) {
		    this.emTaskItemId = emTaskItemId;
	    }
        
        public Integer getOptType() {
		    return optType;
	    }
        
	    public void setOptType(Integer optType) {
		    this.optType = optType;
	    }
        
        public String getExecutor() {
		    return executor;
	    }
        
	    public void setExecutor(String executor) {
		    this.executor = executor;
	    }
        
        public Date getUpdateTime() {
		    return updateTime;
	    }
        
	    public void setUpdateTime(Date updateTime) {
		    this.updateTime = updateTime;
	    }
        
        public Integer getIsUpload() {
		    return isUpload;
	    }
        
	    public void setIsUpload(Integer isUpload) {
		    this.isUpload = isUpload;
	    }
        
        public String getDescription() {
		    return description;
	    }
        
	    public void setDescription(String description) {
		    this.description = description;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
    
}
 
