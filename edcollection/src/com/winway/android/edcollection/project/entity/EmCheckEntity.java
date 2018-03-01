package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EmCheck实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:51
 */
@Table(name = "em_check")
public class EmCheckEntity implements Serializable{

        @Id
        @Column(column = "EM_CHECK_ID")
        private Integer emCheckId;//EM_CHECK_ID
        @Column(column = "EM_WILL_CHECK_ID")
        private Integer emWillCheckId;//EM_WILL_CHECK_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "CHECKER")
        private String checker;//CHECKER
        @Column(column = "CHECK_TIME")
        private Date checkTime;//CHECK_TIME
        @Column(column = "CHECK_RESULT")
        private Integer checkResult;//CHECK_RESULT
        @Column(column = "PROBLEM")
        private String problem;//PROBLEM
    
        
        public Integer getEmCheckId() {
		    return emCheckId;
	    }
        
	    public void setEmCheckId(Integer emCheckId) {
		    this.emCheckId = emCheckId;
	    }
        
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
        
        public String getChecker() {
		    return checker;
	    }
        
	    public void setChecker(String checker) {
		    this.checker = checker;
	    }
        
        public Date getCheckTime() {
		    return checkTime;
	    }
        
	    public void setCheckTime(Date checkTime) {
		    this.checkTime = checkTime;
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
 
