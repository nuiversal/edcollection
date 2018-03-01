package com.winway.android.edcollection.login.entity;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * EdpRoleInfo实体类
 * 
 * @author zgq
 * @since 2017/2/22 9:22:39
 */
@Table(name = "edp_role_info")
public class EdpRoleInfoEntity implements Serializable{

        @Id
        @Column(column = "role_id")
        private String roleId;//角色ID
        @Column(column = "logic_sys_no")
        private String logicSysNo;//逻辑系统标识
        @Column(column = "role_no")
        private String roleNo;//角色标识
        @Column(column = "role_name")
        private String roleName;//角色名称
        @Column(column = "role_type")
        private String roleType;//角色类型
        @Column(column = "role_sts")
        private String roleSts;//角色状态
        @Column(column = "remark")
        private String remark;//备注
        @Column(column = "last_update_user")
        private String lastUpdateUser;//最后修改人
        @Column(column = "last_update_time")
        private Date lastUpdateTime;//最后修改时间
    
        
        public String getRoleId() {
		    return roleId;
	    }
        
	    public void setRoleId(String roleId) {
		    this.roleId = roleId;
	    }
        
        public String getLogicSysNo() {
		    return logicSysNo;
	    }
        
	    public void setLogicSysNo(String logicSysNo) {
		    this.logicSysNo = logicSysNo;
	    }
        
        public String getRoleNo() {
		    return roleNo;
	    }
        
	    public void setRoleNo(String roleNo) {
		    this.roleNo = roleNo;
	    }
        
        public String getRoleName() {
		    return roleName;
	    }
        
	    public void setRoleName(String roleName) {
		    this.roleName = roleName;
	    }
        
        public String getRoleType() {
		    return roleType;
	    }
        
	    public void setRoleType(String roleType) {
		    this.roleType = roleType;
	    }
        
        public String getRoleSts() {
		    return roleSts;
	    }
        
	    public void setRoleSts(String roleSts) {
		    this.roleSts = roleSts;
	    }
        
        public String getRemark() {
		    return remark;
	    }
        
	    public void setRemark(String remark) {
		    this.remark = remark;
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
    
}
 
