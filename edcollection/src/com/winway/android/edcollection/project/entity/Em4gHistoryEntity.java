package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * Em4gHistory实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:48
 */
@Table(name = "em_4g_history")
public class Em4gHistoryEntity implements Serializable{

        @Id
        @Column(column = "EM_4G_HISTORY_ID")
        private Integer em4gHistoryId;//EM_4G_HISTORY_ID
        @Column(column = "EM_4G_PACKAGE_ID")
        private Integer em4gPackageId;//EM_4G_PACKAGE_ID
        @Column(column = "CYCLE_NO")
        private String cycleNo;//CYCLE_NO
        @Column(column = "PACKAGE_RATE")
        private Integer packageRate;//PACKAGE_RATE
        @Column(column = "USED_RATE")
        private Integer usedRate;//USED_RATE
        @Column(column = "RECORD_TIME")
        private Date recordTime;//RECORD_TIME
    
        
        public Integer getEm4gHistoryId() {
		    return em4gHistoryId;
	    }
        
	    public void setEm4gHistoryId(Integer em4gHistoryId) {
		    this.em4gHistoryId = em4gHistoryId;
	    }
        
        public Integer getEm4gPackageId() {
		    return em4gPackageId;
	    }
        
	    public void setEm4gPackageId(Integer em4gPackageId) {
		    this.em4gPackageId = em4gPackageId;
	    }
        
        public String getCycleNo() {
		    return cycleNo;
	    }
        
	    public void setCycleNo(String cycleNo) {
		    this.cycleNo = cycleNo;
	    }
        
        public Integer getPackageRate() {
		    return packageRate;
	    }
        
	    public void setPackageRate(Integer packageRate) {
		    this.packageRate = packageRate;
	    }
        
        public Integer getUsedRate() {
		    return usedRate;
	    }
        
	    public void setUsedRate(Integer usedRate) {
		    this.usedRate = usedRate;
	    }
        
        public Date getRecordTime() {
		    return recordTime;
	    }
        
	    public void setRecordTime(Date recordTime) {
		    this.recordTime = recordTime;
	    }
    
}
 
