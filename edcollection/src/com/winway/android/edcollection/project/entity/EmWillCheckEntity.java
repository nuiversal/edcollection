package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EmWillCheck实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:55
 */
@Table(name = "em_will_check")
public class EmWillCheckEntity implements Serializable{

        @Id
        @Column(column = "EM_WILL_CHECK_ID")
        private Integer emWillCheckId;//EM_WILL_CHECK_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "DATA_TYPE")
        private Integer dataType;//DATA_TYPE
        @Column(column = "DATA_KEY")
        private String dataKey;//DATA_KEY
        @Column(column = "EM_TASKS_ID")
        private String emTasksId;//EM_TASKS_ID
        @Column(column = "EM_TASK_ITEM_ID")
        private String emTaskItemId;//EM_TASK_ITEM_ID
        @Column(column = "IS_SRC_EXISTS")
        private Integer isSrcExists;//IS_SRC_EXISTS
        @Column(column = "OPT_TYPE")
        private Integer optType;//OPT_TYPE
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//UPDATE_TIME
        @Column(column = "UPLOAD_TIME")
        private Date uploadTime;//UPLOAD_TIME
        @Column(column = "EXECUTOR")
        private String executor;//EXECUTOR
        @Column(column = "CHECK_STATUS")
        private Integer checkStatus;//CHECK_STATUS
        @Column(column = "CHECK_TIME")
        private Date checkTime;//CHECK_TIME
        @Column(column = "CHECKER")
        private String checker;//CHECKER
        @Column(column = "CHECK_RESULT")
        private Integer checkResult;//CHECK_RESULT
        @Column(column = "PROBLEM")
        private String problem;//PROBLEM
    
        
        public Integer getEmWillCheckId() {
		    return emWillCheckId;
	    }
        
	    public void setEmWillCheckId(Integer emWillCheckId) {
		    this.emWillCheckId = emWillCheckId;
	    }
        
        public String getPrjid() {
		    return prjid;
	    }
        
	    public void setPrjid(String prjid) {
		    this.prjid = prjid;
	    }
        
        public Integer getDataType() {
		    return dataType;
	    }
        
	    public void setDataType(Integer dataType) {
		    this.dataType = dataType;
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
        
        public Integer getIsSrcExists() {
		    return isSrcExists;
	    }
        
	    public void setIsSrcExists(Integer isSrcExists) {
		    this.isSrcExists = isSrcExists;
	    }
        
        public Integer getOptType() {
		    return optType;
	    }
        
	    public void setOptType(Integer optType) {
		    this.optType = optType;
	    }
        
        public Date getUpdateTime() {
		    return updateTime;
	    }
        
	    public void setUpdateTime(Date updateTime) {
		    this.updateTime = updateTime;
	    }
        
        public Date getUploadTime() {
		    return uploadTime;
	    }
        
	    public void setUploadTime(Date uploadTime) {
		    this.uploadTime = uploadTime;
	    }
        
        public String getExecutor() {
		    return executor;
	    }
        
	    public void setExecutor(String executor) {
		    this.executor = executor;
	    }
        
        public Integer getCheckStatus() {
		    return checkStatus;
	    }
        
	    public void setCheckStatus(Integer checkStatus) {
		    this.checkStatus = checkStatus;
	    }
        
        public Date getCheckTime() {
		    return checkTime;
	    }
        
	    public void setCheckTime(Date checkTime) {
		    this.checkTime = checkTime;
	    }
        
        public String getChecker() {
		    return checker;
	    }
        
	    public void setChecker(String checker) {
		    this.checker = checker;
	    }
        
        public Integer getCheckResult() {
		    return checkResult;
	    }
        
	    public void setCheckResult(Integer checkResult) {
		    this.checkResult = checkResult;
	    }
        
        public String getProblem() {
		    return problem;
	    }
        
	    public void setProblem(String problem) {
		    this.problem = problem;
	    }
    
}
 
