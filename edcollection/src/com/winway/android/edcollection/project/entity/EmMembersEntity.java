package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EmMembers实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:52
 */
@Table(name = "em_members")
public class EmMembersEntity implements Serializable{

        @Id
        @Column(column = "EM_MEMBERS_ID")
        private Integer emMembersId;//EM_MEMBERS_ID
        @Column(column = "EM_PROJECT_ID")
        private String emProjectId;//EM_PROJECT_ID
        @Column(column = "USER_ID")
        private String userId;//USER_ID
        @Column(column = "ROLE_ID")
        private String roleId;//ROLE_ID
        @Column(column = "USER_NAME")
        private String userName;//USER_NAME
        @Column(column = "ROLE_NAME")
        private String roleName;//ROLE_NAME
    
        
        public Integer getEmMembersId() {
		    return emMembersId;
	    }
        
	    public void setEmMembersId(Integer emMembersId) {
		    this.emMembersId = emMembersId;
	    }
        
        public String getEmProjectId() {
		    return emProjectId;
	    }
        
	    public void setEmProjectId(String emProjectId) {
		    this.emProjectId = emProjectId;
	    }
        
        public String getUserId() {
		    return userId;
	    }
        
	    public void setUserId(String userId) {
		    this.userId = userId;
	    }
        
        public String getRoleId() {
		    return roleId;
	    }
        
	    public void setRoleId(String roleId) {
		    this.roleId = roleId;
	    }
        
        public String getUserName() {
		    return userName;
	    }
        
	    public void setUserName(String userName) {
		    this.userName = userName;
	    }
        
        public String getRoleName() {
		    return roleName;
	    }
        
	    public void setRoleName(String roleName) {
		    this.roleName = roleName;
	    }
    
}
 
