package com.winway.android.edcollection.login.entity;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * EdpUserInfo实体类
 * 
 * @author zgq
 * @since 2017/2/22 9:18:00
 */
@Table(name = "edp_user_info")
public class EdpUserInfoEntity implements Serializable {

	@Id
	@Column(column = "user_id")
	private String userId;// 用户ID
	@Column(column = "user_no")
	private String userNo;// 用户标识
	@Column(column = "user_pwd")
	private String userPwd;// 用户密码
	@Column(column = "user_name")
	private String userName;// 用户名称
	@Column(column = "sex")
	private String sex;// 性别
	@Column(column = "birthday")
	private String birthday;// 生日
	@Column(column = "email")
	private String email;// 邮箱
	@Column(column = "address")
	private String address;// 地址
	@Column(column = "postcode")
	private String postcode;// 邮编
	@Column(column = "mobile")
	private String mobile;// 手机号码
	@Column(column = "tel")
	private String tel;// 电话号码
	@Column(column = "org_no")
	private String orgNo;// 机构标识
	@Column(column = "dept_no")
	private String deptNo;// 部门标识
	@Column(column = "user_sts")
	private String userSts;// 用户状态
	@Column(column = "is_builtin")
	private String isBuiltin;// 是否内置
	@Column(column = "remark")
	private String remark;// 备注
	@Column(column = "last_pwd_update_time")
	private Date lastPwdUpdateTime;// 最后密码修改时间
	@Column(column = "last_update_user")
	private String lastUpdateUser;// 最后修改人
	@Column(column = "last_update_time")
	private Date lastUpdateTime;// 最后修改时间
	@Column(column = "user_icon")
	private String userIcon;// 用户图标
	@Column(column = "logic_sys_no")
	private String logicSysNo;// 逻辑系统标识

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getUserSts() {
		return userSts;
	}

	public void setUserSts(String userSts) {
		this.userSts = userSts;
	}

	public String getIsBuiltin() {
		return isBuiltin;
	}

	public void setIsBuiltin(String isBuiltin) {
		this.isBuiltin = isBuiltin;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getLastPwdUpdateTime() {
		return lastPwdUpdateTime;
	}

	public void setLastPwdUpdateTime(Date lastPwdUpdateTime) {
		this.lastPwdUpdateTime = lastPwdUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

}
