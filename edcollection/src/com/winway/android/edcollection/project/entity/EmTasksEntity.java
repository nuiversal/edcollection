package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EmTasks实体类
 * 
 * @author zgq
 * @since 2017-07-30 10:26:47
 */
@Table(name = "em_tasks")
public class EmTasksEntity implements Serializable{

        @Id
        @Column(column = "EM_TASKS_ID")
        private String emTasksId;//EM_TASKS_ID
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "TASK_NAME")
        private String taskName;//TASK_NAME
        @Column(column = "TASK_DESC")
        private String taskDesc;//TASK_DESC
        @Column(column = "CREATE_TIME")
        private Date createTime;//CREATE_TIME
        @Column(column = "STATUS")
        private Integer status;//状态| 0 未确认(未发布-允许修改），1 发布，3 采集完成，4 已审核，5 已完结
        @Column(column = "EXECUTOR")
        private String executor;//负责人|  EDP 中的用户ID
        @Column(column = "BEGIN_TIME")
        private Date beginTime;//BEGIN_TIME
        @Column(column = "COMPLETE_TIME")
        private Date completeTime;//COMPLETE_TIME
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//UPDATE_TIME
        @Column(column = "EXPE_TIME")
        private Date expeTime;//预计完成时间
        @Column(column = "BALL_TYPE_COUNT")
        private Integer ballTypeCount;//球形数目
        @Column(column = "NAIL_TYPE_COUNT")
        private Integer nailTypeCount;//钉形数目
        @Column(column = "ELEC_LABEL_COUNT")
        private Integer elecLabelCount;//电子标签
        @Column(column = "LINE_LENGTH")
        private Double lineLength;//线路长度
        @Column(column = "PIPE_LENGTH")
        private Double pipeLength;//顶管长度
        @Column(column = "PIPE_COUNTS")
        private Integer pipeCounts;//顶管段数
    
        
        public String getEmTasksId() {
		    return emTasksId;
	    }
        
	    public void setEmTasksId(String emTasksId) {
		    this.emTasksId = emTasksId;
	    }
        
        public String getPrjid() {
		    return prjid;
	    }
        
	    public void setPrjid(String prjid) {
		    this.prjid = prjid;
	    }
        
        public String getTaskName() {
		    return taskName;
	    }
        
	    public void setTaskName(String taskName) {
		    this.taskName = taskName;
	    }
        
        public String getTaskDesc() {
		    return taskDesc;
	    }
        
	    public void setTaskDesc(String taskDesc) {
		    this.taskDesc = taskDesc;
	    }
        
        public Date getCreateTime() {
		    return createTime;
	    }
        
	    public void setCreateTime(Date createTime) {
		    this.createTime = createTime;
	    }
        
        public Integer getStatus() {
		    return status;
	    }
        
	    public void setStatus(Integer status) {
		    this.status = status;
	    }
        
        public String getExecutor() {
		    return executor;
	    }
        
	    public void setExecutor(String executor) {
		    this.executor = executor;
	    }
        
        public Date getBeginTime() {
		    return beginTime;
	    }
        
	    public void setBeginTime(Date beginTime) {
		    this.beginTime = beginTime;
	    }
        
        public Date getCompleteTime() {
		    return completeTime;
	    }
        
	    public void setCompleteTime(Date completeTime) {
		    this.completeTime = completeTime;
	    }
        
        public Date getUpdateTime() {
		    return updateTime;
	    }
        
	    public void setUpdateTime(Date updateTime) {
		    this.updateTime = updateTime;
	    }
        
        public Date getExpeTime() {
		    return expeTime;
	    }
        
	    public void setExpeTime(Date expeTime) {
		    this.expeTime = expeTime;
	    }
        
        public Integer getBallTypeCount() {
		    return ballTypeCount;
	    }
        
	    public void setBallTypeCount(Integer ballTypeCount) {
		    this.ballTypeCount = ballTypeCount;
	    }
        
        public Integer getNailTypeCount() {
		    return nailTypeCount;
	    }
        
	    public void setNailTypeCount(Integer nailTypeCount) {
		    this.nailTypeCount = nailTypeCount;
	    }
        
        public Integer getElecLabelCount() {
		    return elecLabelCount;
	    }
        
	    public void setElecLabelCount(Integer elecLabelCount) {
		    this.elecLabelCount = elecLabelCount;
	    }
        
        public Double getLineLength() {
		    return lineLength;
	    }
        
	    public void setLineLength(Double lineLength) {
		    this.lineLength = lineLength;
	    }
        
        public Double getPipeLength() {
		    return pipeLength;
	    }
        
	    public void setPipeLength(Double pipeLength) {
		    this.pipeLength = pipeLength;
	    }
        
        public Integer getPipeCounts() {
		    return pipeCounts;
	    }
        
	    public void setPipeCounts(Integer pipeCounts) {
		    this.pipeCounts = pipeCounts;
	    }
    
}
 
