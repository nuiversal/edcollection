package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcDeviceClass实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:24
 */
@Table(name = "ec_device_class")
public class EcDeviceClassEntity implements Serializable{

        @Id
        @Column(column = "EC_DEVICE_CLASS_ID")
        private String ecDeviceClassId;//EC_DEVICE_CLASS_ID
        @Column(column = "CREATE_TIME")
        private Date createTime;//创建时间
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//更新时间
        @Column(column = "SORT_NUM")
        private Integer sortNum;//序号
        @Column(column = "NAME")
        private String name;//分类名称
        @Column(column = "CODE")
        private String code;//分类编号
        @Column(column = "PARENT_CODE")
        private String parentCode;//父级分类编号
    
        
        public String getEcDeviceClassId() {
		    return ecDeviceClassId;
	    }
        
	    public void setEcDeviceClassId(String ecDeviceClassId) {
		    this.ecDeviceClassId = ecDeviceClassId;
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
        
        public Integer getSortNum() {
		    return sortNum;
	    }
        
	    public void setSortNum(Integer sortNum) {
		    this.sortNum = sortNum;
	    }
        
        public String getName() {
		    return name;
	    }
        
	    public void setName(String name) {
		    this.name = name;
	    }
        
        public String getCode() {
		    return code;
	    }
        
	    public void setCode(String code) {
		    this.code = code;
	    }
        
        public String getParentCode() {
		    return parentCode;
	    }
        
	    public void setParentCode(String parentCode) {
		    this.parentCode = parentCode;
	    }
    
}
 
