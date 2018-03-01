package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcLineBase实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:31
 */
@Table(name = "ec_line_base")
public class EcLineBaseEntity implements Serializable{

        @Id
        @Column(column = "EC_LINE_BASE_ID")
        private String ecLineBaseId;//线路ID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "PARENT_ID")
        private String parentId;//上级线路ID
        @Column(column = "VOLTAGE")
        private Double voltage;//电压等级
        @Column(column = "LINE_NO")
        private String lineNo;//线路编号
        @Column(column = "NAME")
        private String name;//线路名称
        @Column(column = "START_STATION")
        private String startStation;//所属电站
        @Column(column = "END_STATION")
        private String endStation;//结束电站
        @Column(column = "DRAW_OFFSET")
        private Double drawOffset;//线路偏移
        @Column(column = "GROUP_NAME")
        private String groupName;//GROUP_NAME
    
        
        public String getEcLineBaseId() {
		    return ecLineBaseId;
	    }
        
	    public void setEcLineBaseId(String ecLineBaseId) {
		    this.ecLineBaseId = ecLineBaseId;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
        
        public String getParentId() {
		    return parentId;
	    }
        
	    public void setParentId(String parentId) {
		    this.parentId = parentId;
	    }
        
        public Double getVoltage() {
		    return voltage;
	    }
        
	    public void setVoltage(Double voltage) {
		    this.voltage = voltage;
	    }
        
        public String getLineNo() {
		    return lineNo;
	    }
        
	    public void setLineNo(String lineNo) {
		    this.lineNo = lineNo;
	    }
        
        public String getName() {
		    return name;
	    }
        
	    public void setName(String name) {
		    this.name = name;
	    }
        
        public String getStartStation() {
		    return startStation;
	    }
        
	    public void setStartStation(String startStation) {
		    this.startStation = startStation;
	    }
        
        public String getEndStation() {
		    return endStation;
	    }
        
	    public void setEndStation(String endStation) {
		    this.endStation = endStation;
	    }
        
        public Double getDrawOffset() {
		    return drawOffset;
	    }
        
	    public void setDrawOffset(Double drawOffset) {
		    this.drawOffset = drawOffset;
	    }
        
        public String getGroupName() {
		    return groupName;
	    }
        
	    public void setGroupName(String groupName) {
		    this.groupName = groupName;
	    }
    
}
 
