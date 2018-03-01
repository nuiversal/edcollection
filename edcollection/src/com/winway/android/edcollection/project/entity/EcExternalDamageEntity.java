package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcExternalDamage实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:28
 */
@Table(name = "ec_external_damage")
public class EcExternalDamageEntity implements Serializable{

        @Id
        @Column(column = "ID")
        private String id;//编号
        @Column(column = "NAME")
        private String name;//地表名称
        @Column(column = "TYPE")
        private Integer type;//地表类型数字
        @Column(column = "IS_DISPLAY")
        private Integer isDisplay;//是否显示
        @Column(column = "REMARK")
        private String remark;//备注
        @Column(column = "LONGITUDE")
        private Double longitude;//经度
        @Column(column = "LATITUDE")
        private Double latitude;//纬度
        @Column(column = "CREATE_TIME")
        private Date createTime;//创建时间
        @Column(column = "CREATE_USER")
        private String createUser;//创建用户
        @Column(column = "IMAGE_URL")
        private String imageUrl;//图片路径
        @Column(column = "TYPE_CN")
        private String typeCn;//类型中文名
        @Column(column = "ORGID")
        private String orgid;//机构id
        @Column(column = "STATE")
        private Double state;//状态(1表示未上传，2表示处理中，3表示已处理)
    
        
        public String getId() {
		    return id;
	    }
        
	    public void setId(String id) {
		    this.id = id;
	    }
        
        public String getName() {
		    return name;
	    }
        
	    public void setName(String name) {
		    this.name = name;
	    }
        
        public Integer getType() {
		    return type;
	    }
        
	    public void setType(Integer type) {
		    this.type = type;
	    }
        
        public Integer getIsDisplay() {
		    return isDisplay;
	    }
        
	    public void setIsDisplay(Integer isDisplay) {
		    this.isDisplay = isDisplay;
	    }
        
        public String getRemark() {
		    return remark;
	    }
        
	    public void setRemark(String remark) {
		    this.remark = remark;
	    }
        
        public Double getLongitude() {
		    return longitude;
	    }
        
	    public void setLongitude(Double longitude) {
		    this.longitude = longitude;
	    }
        
        public Double getLatitude() {
		    return latitude;
	    }
        
	    public void setLatitude(Double latitude) {
		    this.latitude = latitude;
	    }
        
        public Date getCreateTime() {
		    return createTime;
	    }
        
	    public void setCreateTime(Date createTime) {
		    this.createTime = createTime;
	    }
        
        public String getCreateUser() {
		    return createUser;
	    }
        
	    public void setCreateUser(String createUser) {
		    this.createUser = createUser;
	    }
        
        public String getImageUrl() {
		    return imageUrl;
	    }
        
	    public void setImageUrl(String imageUrl) {
		    this.imageUrl = imageUrl;
	    }
        
        public String getTypeCn() {
		    return typeCn;
	    }
        
	    public void setTypeCn(String typeCn) {
		    this.typeCn = typeCn;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
        
        public Double getState() {
		    return state;
	    }
        
	    public void setState(Double state) {
		    this.state = state;
	    }
    
}
 
