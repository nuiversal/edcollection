package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * CDbVersion实体类
 * 
 * @author zgq
 * @since 2017-07-27 16:56:56
 */
@Table(name = "c_db_version")
public class CDbVersionEntity implements Serializable{

        @Id
        @Column(column = "C_DB_VERSION_ID")
        private String cDbVersionId;//主键ID
        @Column(column = "COMPONENT_NO")
        private String componentNo;//组件编号
        @Column(column = "VERSION_1")
        private Integer version1;//大版本号
        @Column(column = "VERSION_2")
        private Integer version2;//子版本号
        @Column(column = "VERSION_3")
        private Integer version3;//小版本号
        @Column(column = "GXSJ")
        private Date gxsj;//更新时间
        @Column(column = "UPDATE_SERVER")
        private String updateServer;//更新服务器IP
        @Column(column = "UPDATE_APP")
        private String updateApp;//执行更新的应用
    
        
        public String getCDbVersionId() {
		    return cDbVersionId;
	    }
        
	    public void setCDbVersionId(String cDbVersionId) {
		    this.cDbVersionId = cDbVersionId;
	    }
        
        public String getComponentNo() {
		    return componentNo;
	    }
        
	    public void setComponentNo(String componentNo) {
		    this.componentNo = componentNo;
	    }
        
        public Integer getVersion1() {
		    return version1;
	    }
        
	    public void setVersion1(Integer version1) {
		    this.version1 = version1;
	    }
        
        public Integer getVersion2() {
		    return version2;
	    }
        
	    public void setVersion2(Integer version2) {
		    this.version2 = version2;
	    }
        
        public Integer getVersion3() {
		    return version3;
	    }
        
	    public void setVersion3(Integer version3) {
		    this.version3 = version3;
	    }
        
        public Date getGxsj() {
		    return gxsj;
	    }
        
	    public void setGxsj(Date gxsj) {
		    this.gxsj = gxsj;
	    }
        
        public String getUpdateServer() {
		    return updateServer;
	    }
        
	    public void setUpdateServer(String updateServer) {
		    this.updateServer = updateServer;
	    }
        
        public String getUpdateApp() {
		    return updateApp;
	    }
        
	    public void setUpdateApp(String updateApp) {
		    this.updateApp = updateApp;
	    }
    
}
 
