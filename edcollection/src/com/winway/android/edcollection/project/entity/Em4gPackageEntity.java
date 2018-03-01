package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * Em4gPackage实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:48
 */
@Table(name = "em_4g_package")
public class Em4gPackageEntity implements Serializable{

        @Id
        @Column(column = "EM_4G_PACKAGE_ID")
        private Integer em4gPackageId;//EM_4G_PACKAGE_ID
        @Column(column = "USER_ID")
        private String userId;//USER_ID
        @Column(column = "PACKAGE_TYPE")
        private Integer packageType;//PACKAGE_TYPE
        @Column(column = "RATE")
        private Integer rate;//RATE
        @Column(column = "CYCLE_DATE")
        private Date cycleDate;//CYCLE_DATE
        @Column(column = "BEGIN_USE_DATE")
        private Date beginUseDate;//BEGIN_USE_DATE
        @Column(column = "END_USE_DATE")
        private Date endUseDate;//END_USE_DATE
        @Column(column = "CURRENT_USED")
        private Integer currentUsed;//CURRENT_USED
        @Column(column = "IS_USING")
        private Integer isUsing;//IS_USING
    
        
        public Integer getEm4gPackageId() {
		    return em4gPackageId;
	    }
        
	    public void setEm4gPackageId(Integer em4gPackageId) {
		    this.em4gPackageId = em4gPackageId;
	    }
        
        public String getUserId() {
		    return userId;
	    }
        
	    public void setUserId(String userId) {
		    this.userId = userId;
	    }
        
        public Integer getPackageType() {
		    return packageType;
	    }
        
	    public void setPackageType(Integer packageType) {
		    this.packageType = packageType;
	    }
        
        public Integer getRate() {
		    return rate;
	    }
        
	    public void setRate(Integer rate) {
		    this.rate = rate;
	    }
        
        public Date getCycleDate() {
		    return cycleDate;
	    }
        
	    public void setCycleDate(Date cycleDate) {
		    this.cycleDate = cycleDate;
	    }
        
        public Date getBeginUseDate() {
		    return beginUseDate;
	    }
        
	    public void setBeginUseDate(Date beginUseDate) {
		    this.beginUseDate = beginUseDate;
	    }
        
        public Date getEndUseDate() {
		    return endUseDate;
	    }
        
	    public void setEndUseDate(Date endUseDate) {
		    this.endUseDate = endUseDate;
	    }
        
        public Integer getCurrentUsed() {
		    return currentUsed;
	    }
        
	    public void setCurrentUsed(Integer currentUsed) {
		    this.currentUsed = currentUsed;
	    }
        
        public Integer getIsUsing() {
		    return isUsing;
	    }
        
	    public void setIsUsing(Integer isUsing) {
		    this.isUsing = isUsing;
	    }
    
}
 
