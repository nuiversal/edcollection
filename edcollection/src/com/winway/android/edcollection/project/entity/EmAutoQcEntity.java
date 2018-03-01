package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EmAutoQc实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:49
 */
@Table(name = "em_auto_qc")
public class EmAutoQcEntity implements Serializable{

        @Id
        @Column(column = "EM_AUTO_QC_ID")
        private String emAutoQcId;//EM_AUTO_QC_ID
        @Column(column = "CHECKER")
        private String checker;//CHECKER
        @Column(column = "CHECK_TIME")
        private Date checkTime;//CHECK_TIME
        @Column(column = "CHECK_PS")
        private String checkPs;//CHECK_PS
        @Column(column = "REMARK")
        private String remark;//REMARK
    
        
        public String getEmAutoQcId() {
		    return emAutoQcId;
	    }
        
	    public void setEmAutoQcId(String emAutoQcId) {
		    this.emAutoQcId = emAutoQcId;
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
        
        public String getCheckPs() {
		    return checkPs;
	    }
        
	    public void setCheckPs(String checkPs) {
		    this.checkPs = checkPs;
	    }
        
        public String getRemark() {
		    return remark;
	    }
        
	    public void setRemark(String remark) {
		    this.remark = remark;
	    }
    
}
 
