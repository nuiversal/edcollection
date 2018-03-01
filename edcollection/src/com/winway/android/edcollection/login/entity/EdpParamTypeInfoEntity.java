package com.winway.android.edcollection.login.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.util.Date;

/**
 * EdpParamTypeInfo实体类
 * 
 * @author zgq
 * @since 2017/2/15 10:47:11
 */
@Table(name = "edp_param_type_info")
public class EdpParamTypeInfoEntity {

        @Id
        @Column(column = "param_type_id")
        private String paramTypeId;//参数类型ID
        @Column(column = "logic_sys_no")
        private String logicSysNo;//逻辑系统标识
        @Column(column = "param_type_no")
        private String paramTypeNo;//参数类型标识
        @Column(column = "param_type_name")
        private String paramTypeName;//参数类型名称
        @Column(column = "up_no")
        private String upNo;//上级标识
        @Column(column = "remark")
        private String remark;//备注
        @Column(column = "update_user")
        private String updateUser;//更新操作者
        @Column(column = "update_time")
        private Date updateTime;//更新时间
    
        
        public String getParamTypeId() {
		    return paramTypeId;
	    }
        
	    public void setParamTypeId(String paramTypeId) {
		    this.paramTypeId = paramTypeId;
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
        
        public String getParamTypeName() {
		    return paramTypeName;
	    }
        
	    public void setParamTypeName(String paramTypeName) {
		    this.paramTypeName = paramTypeName;
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
        
        public String getUpdateUser() {
		    return updateUser;
	    }
        
	    public void setUpdateUser(String updateUser) {
		    this.updateUser = updateUser;
	    }
        
        public Date getUpdateTime() {
		    return updateTime;
	    }
        
	    public void setUpdateTime(Date updateTime) {
		    this.updateTime = updateTime;
	    }
    
}
 
