package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcLineP实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:32
 */
@Table(name = "ec_line_p")
public class EcLinePEntity implements Serializable{

        @Id
        @Column(column = "EC_LINE_ID")
        private String ecLineId;//电缆ID
        @Column(column = "VOLTAGE")
        private Double voltage;//电压等级
        @Column(column = "LINE_NO")
        private String lineNo;//线路编号
        @Column(column = "LINE_NAME")
        private String lineName;//线路名称
        @Column(column = "START_STATION")
        private String startStation;//所属电站
        @Column(column = "END_STATION")
        private String endStation;//结束电站
        @Column(column = "ORGID")
        private String orgid;//ORGID
    
        
        public String getEcLineId() {
		    return ecLineId;
	    }
        
	    public void setEcLineId(String ecLineId) {
		    this.ecLineId = ecLineId;
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
        
        public String getLineName() {
		    return lineName;
	    }
        
	    public void setLineName(String lineName) {
		    this.lineName = lineName;
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
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
    
}
 
