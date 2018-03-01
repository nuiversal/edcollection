package com.winway.android.edcollection.login.entity;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * EdpOrgInfo实体类
 * 
 * @author zgq
 * @since 2017/2/22 9:20:04
 */
@Table(name = "edp_org_info")
public class EdpOrgInfoEntity implements Serializable{

        @Id
        @Column(column = "org_id")
        private String orgId;//机构ID
        @Column(column = "logic_sys_no")
        private String logicSysNo;//逻辑系统标识
        @Column(column = "org_no")
        private String orgNo;//机构标识
        @Column(column = "org_name")
        private String orgName;//机构名称
        @Column(column = "up_no")
        private String upNo;//上级标识
        @Column(column = "org_sts")
        private String orgSts;//机构状态
        @Column(column = "remark")
        private String remark;//备注
        @Column(column = "last_update_user")
        private String lastUpdateUser;//最后修改人
        @Column(column = "last_update_time")
        private Date lastUpdateTime;//最后修改时间
        @Column(column = "custom_no")
        private String customNo;//custom_no
        @Column(column = "map_center")
        private String mapCenter;//map_center
    
        
        public String getOrgId() {
		    return orgId;
	    }
        
	    public void setOrgId(String orgId) {
		    this.orgId = orgId;
	    }
        
        public String getLogicSysNo() {
		    return logicSysNo;
	    }
        
	    public void setLogicSysNo(String logicSysNo) {
		    this.logicSysNo = logicSysNo;
	    }
        
        public String getOrgNo() {
		    return orgNo;
	    }
        
	    public void setOrgNo(String orgNo) {
		    this.orgNo = orgNo;
	    }
        
        public String getOrgName() {
		    return orgName;
	    }
        
	    public void setOrgName(String orgName) {
		    this.orgName = orgName;
	    }
        
        public String getUpNo() {
		    return upNo;
	    }
        
	    public void setUpNo(String upNo) {
		    this.upNo = upNo;
	    }
        
        public String getOrgSts() {
		    return orgSts;
	    }
        
	    public void setOrgSts(String orgSts) {
		    this.orgSts = orgSts;
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
        
        public String getCustomNo() {
		    return customNo;
	    }
        
	    public void setCustomNo(String customNo) {
		    this.customNo = customNo;
	    }
        
        public String getMapCenter() {
		    return mapCenter;
	    }
        
	    public void setMapCenter(String mapCenter) {
		    this.mapCenter = mapCenter;
	    }
    
}
 
