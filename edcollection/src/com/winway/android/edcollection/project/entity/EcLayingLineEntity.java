package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcLayingLine实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:30
 */
@Table(name = "ec_laying_line")
public class EcLayingLineEntity implements Serializable{

        @Id
        @Column(column = "EC_LAYING_LINE_ID")
        private String ecLayingLineId;//敷设段ID
        @Column(column = "EC_LINE_ID")
        private String ecLineId;//电缆ID
        @Column(column = "LAY_TYPE")
        private String layType;//敷设类型
        @Column(column = "VOLTAGE")
        private Double voltage;//电压等级
        @Column(column = "LINE_NO")
        private String lineNo;//线路编号
        @Column(column = "LINENAME")
        private String linename;//线路名称
        @Column(column = "STATION_NO")
        private String stationNo;//变电站编号
        @Column(column = "TINDEX1")
        private Double tindex1;//顺序
        @Column(column = "TINDEX2")
        private Double tindex2;//TINDEX2
        @Column(column = "ATT_TYPE")
        private Double attType;//挂接类型| 1 主网属性，2 配网属性
        @Column(column = "HLPL")
        private Double hlpl;//HLPL
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "CABLE_NAME_PHASE")
        private String cableNamePhase;//同沟电缆名称
        @Column(column = "TOTAL_PIPE_NUM")
        private Integer totalPipeNum;//总管道数
        @Column(column = "USED_PIPE_NUM")
        private Integer usedPipeNum;//已使用管道数
    
        
        public String getEcLayingLineId() {
		    return ecLayingLineId;
	    }
        
	    public void setEcLayingLineId(String ecLayingLineId) {
		    this.ecLayingLineId = ecLayingLineId;
	    }
        
        public String getEcLineId() {
		    return ecLineId;
	    }
        
	    public void setEcLineId(String ecLineId) {
		    this.ecLineId = ecLineId;
	    }
        
        public String getLayType() {
		    return layType;
	    }
        
	    public void setLayType(String layType) {
		    this.layType = layType;
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
        
        public String getLinename() {
		    return linename;
	    }
        
	    public void setLinename(String linename) {
		    this.linename = linename;
	    }
        
        public String getStationNo() {
		    return stationNo;
	    }
        
	    public void setStationNo(String stationNo) {
		    this.stationNo = stationNo;
	    }
        
        public Double getTindex1() {
		    return tindex1;
	    }
        
	    public void setTindex1(Double tindex1) {
		    this.tindex1 = tindex1;
	    }
        
        public Double getTindex2() {
		    return tindex2;
	    }
        
	    public void setTindex2(Double tindex2) {
		    this.tindex2 = tindex2;
	    }
        
        public Double getAttType() {
		    return attType;
	    }
        
	    public void setAttType(Double attType) {
		    this.attType = attType;
	    }
        
        public Double getHlpl() {
		    return hlpl;
	    }
        
	    public void setHlpl(Double hlpl) {
		    this.hlpl = hlpl;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
        
        public String getCableNamePhase() {
		    return cableNamePhase;
	    }
        
	    public void setCableNamePhase(String cableNamePhase) {
		    this.cableNamePhase = cableNamePhase;
	    }
        
        public Integer getTotalPipeNum() {
		    return totalPipeNum;
	    }
        
	    public void setTotalPipeNum(Integer totalPipeNum) {
		    this.totalPipeNum = totalPipeNum;
	    }
        
        public Integer getUsedPipeNum() {
		    return usedPipeNum;
	    }
        
	    public void setUsedPipeNum(Integer usedPipeNum) {
		    this.usedPipeNum = usedPipeNum;
	    }
    
}
 
