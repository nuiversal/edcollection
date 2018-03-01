package com.winway.android.basicbusiness.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EmCDataLog实体类
 *
 * @author zgq
 * @since 2017/2/19 9:41:39
 */
@Table(name = "em_c_data_log")
public class EmCDataLogEntity implements Serializable {

	@Id
	@Column(column = "EM_C_DATA_LOG_ID")
	private Integer emCDataLogId;// EM_C_DATA_LOG_ID
	@Column(column = "PRJID")
	private String prjid;// PRJID
	@Column(column = "TABLE_NAME")
	private String tableName;// 采集数据类型| 0 其他，1 标识器，2 电子标签
	@Column(column = "DATA_KEY")
	private String dataKey;// DATA_KEY
	@Column(column = "EM_TASKS_ID")
	private String emTasksId;// EM_TASKS_ID
	@Column(column = "EM_TASK_ITEM_ID")
	private String emTaskItemId;// 任务明细ID| 如果该数据是任务相关的，填写任务明细ID，否则为空。
	@Column(column = "OPT_TYPE")
	private Integer optType;// 操作类型| 1 新增，2 修改，3 删除
	@Column(column = "EXECUTOR")
	private String executor;// EXECUTOR
	@Column(column = "UPDATE_TIME")
	private Date updateTime;// UPDATE_TIME
	@Column(column = "IS_UPLOAD")
	private Integer isUpload;// 是否已上传| 移动端使用
	@Column(column = "DESCRIPTION")
	private String description;// 描述|简单描述操作，比如“插入标识器aaaaaa"
	@Column(column = "ORGID")
	private String orgid;// 机构ID

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
