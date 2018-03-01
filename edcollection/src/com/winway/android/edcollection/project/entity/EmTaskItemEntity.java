package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EmTaskItem实体类
 * 
 * @author zgq
 * @since 2017-07-30 10:26:46
 */
@Table(name = "em_task_item")
public class EmTaskItemEntity implements Serializable{

        @Id
        @Column(column = "EM_TASK_ITEM_ID")
        private String emTaskItemId;//EM_TASK_ITEM_ID
        @Column(column = "EM_TASKS_ID")
        private String emTasksId;//EM_TASKS_ID
        @Column(column = "DEVICE_TYPE")
        private Integer deviceType;//设备类型| 设备或设施的类型（统一定义设备设施类型枚举值）
        @Column(column = "OBJ_ID")
        private String objId;//对象ID| 设备设施的ID
        @Column(column = "STATUS")
        private Integer status;//状态|0 采集，1 已采集，2 已审核
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//UPDATE_TIME
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "REMARK")
        private String remark;//REMARK
    
        
        public String getEmTaskItemId() {
		    return emTaskItemId;
	    }
        
	    public void setEmTaskItemId(String emTaskItemId) {
		    this.emTaskItemId = emTaskItemId;
	    }
        
        public String getEmTasksId() {
		    return emTasksId;
	    }
        
	    public void setEmTasksId(String emTasksId) {
		    this.emTasksId = emTasksId;
	    }
        
        public Integer getDeviceType() {
		    return deviceType;
	    }
        
	    public void setDeviceType(Integer deviceType) {
		    this.deviceType = deviceType;
	    }
        
        public String getObjId() {
		    return objId;
	    }
        
	    public void setObjId(String objId) {
		    this.objId = objId;
	    }
        
        public Integer getStatus() {
		    return status;
	    }
        
	    public void setStatus(Integer status) {
		    this.status = status;
	    }
        
        public Date getUpdateTime() {
		    return updateTime;
	    }
        
	    public void setUpdateTime(Date updateTime) {
		    this.updateTime = updateTime;
	    }
        
        public String getPrjid() {
		    return prjid;
	    }
        
	    public void setPrjid(String prjid) {
		    this.prjid = prjid;
	    }
        
        public String getRemark() {
		    return remark;
	    }
        
	    public void setRemark(String remark) {
		    this.remark = remark;
	    }
    
}
 
