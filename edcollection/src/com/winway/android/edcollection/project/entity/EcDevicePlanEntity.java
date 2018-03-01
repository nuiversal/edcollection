package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcDevicePlan实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:25
 */
@Table(name = "ec_device_plan")
public class EcDevicePlanEntity implements Serializable{

        @Id
        @Column(column = "EC_DEVICE_PLAN_ID")
        private String ecDevicePlanId;//EC_DEVICE_PLAN_ID
        @Column(column = "OID")
        private String oid;//EC_NODE表的OID
        @Column(column = "DEVICE_CODE")
        private String deviceCode;//设备分类编号
        @Column(column = "NAME")
        private String name;//设备名称
        @Column(column = "DEVICE_MODEL")
        private String deviceModel;//设备型号
        @Column(column = "REMARKS")
        private String remarks;//备注
        @Column(column = "INSTALL_POSITION")
        private String installPosition;//安装位置
        @Column(column = "CREATE_TIME")
        private Date createTime;//创建时间
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//更新时间
    
        
        public String getEcDevicePlanId() {
		    return ecDevicePlanId;
	    }
        
	    public void setEcDevicePlanId(String ecDevicePlanId) {
		    this.ecDevicePlanId = ecDevicePlanId;
	    }
        
        public String getOid() {
		    return oid;
	    }
        
	    public void setOid(String oid) {
		    this.oid = oid;
	    }
        
        public String getDeviceCode() {
		    return deviceCode;
	    }
        
	    public void setDeviceCode(String deviceCode) {
		    this.deviceCode = deviceCode;
	    }
        
        public String getName() {
		    return name;
	    }
        
	    public void setName(String name) {
		    this.name = name;
	    }
        
        public String getDeviceModel() {
		    return deviceModel;
	    }
        
	    public void setDeviceModel(String deviceModel) {
		    this.deviceModel = deviceModel;
	    }
        
        public String getRemarks() {
		    return remarks;
	    }
        
	    public void setRemarks(String remarks) {
		    this.remarks = remarks;
	    }
        
        public String getInstallPosition() {
		    return installPosition;
	    }
        
	    public void setInstallPosition(String installPosition) {
		    this.installPosition = installPosition;
	    }
        
        public Date getCreateTime() {
		    return createTime;
	    }
        
	    public void setCreateTime(Date createTime) {
		    this.createTime = createTime;
	    }
        
        public Date getUpdateTime() {
		    return updateTime;
	    }
        
	    public void setUpdateTime(Date updateTime) {
		    this.updateTime = updateTime;
	    }
    
}
 
