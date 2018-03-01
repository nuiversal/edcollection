package com.winway.android.edcollection.login.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.util.Date;

/**
 * EdpParamInfo实体类
 * 
 * @author zgq
 * @since 2017/2/15 10:47:10
 */
@Table(name = "edp_param_info")
public class EdpParamInfoEntity {

	@Id
	@Column(column = "param_id")
	private String paramId;// 参数ID
	@Column(column = "logic_sys_no")
	private String logicSysNo;// 逻辑系统标识
	@Column(column = "param_type_no")
	private String paramTypeNo;// 参数类型标识
	@Column(column = "param_value")
	private String paramValue;// 参数值
	@Column(column = "param_name")
	private String paramName;// 参数名称
	@Column(column = "order_no")
	private Integer orderNo;// 顺序编号
	@Column(column = "up_no")
	private String upNo;// 上级标识
	@Column(column = "remark")
	private String remark;// 备注
	@Column(column = "update_time")
	private Date updateTime;// 更新时间
	@Column(column = "update_user")
	private String updateUser;// 操作者
	@Column(column = "is_update")
	private String isUpdate;// is_update
	@Column(column = "param_sts")
	private String paramSts;// 参数状态

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getParamTypeNo() {
		return paramTypeNo;
	}

	public void setParamTypeNo(String paramTypeNo) {
		this.paramTypeNo = paramTypeNo;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getUpNo() {
		return upNo;
	}

	public void setUpNo(String upNo) {
		this.upNo = upNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getParamSts() {
		return paramSts;
	}

	public void setParamSts(String paramSts) {
		this.paramSts = paramSts;
	}

}
