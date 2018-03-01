package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcNode实体类
 * 
 * @author mr-lao
 * @since 2017/6/17 14:36:38
 */
@Table(name = "ec_node")
public class EcNodeEntity implements Serializable{

        @Id
        @Column(column = "OID")
        private String oid;//OID
        @Column(column = "GEOM")
        private String geom;//GEOM
        @Column(column = "ALTITUDE")
        private Double altitude;//ALTITUDE
        @Column(column = "COORDINATE_NO")
        private String coordinateNo;//COORDINATE_NO
        @Column(column = "MARKER_TYPE")
        private Integer markerType;//MARKER_TYPE
        @Column(column = "LAY_TYPE")
        private String layType;//LAY_TYPE
        @Column(column = "MARKER_NO")
        private String markerNo;//MARKER_NO
        @Column(column = "PRJID")
        private String prjid;//PRJID
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "INSTALL_POSITION")
        private String installPosition;//INSTALL_POSITION
        @Column(column = "GEO_LOCATION")
        private String geoLocation;//GEO_LOCATION
        @Column(column = "CABLE_DEEPTH")
        private Double cableDeepth;//CABLE_DEEPTH
        @Column(column = "CABLE_WIDTH")
        private Double cableWidth;//CABLE_WIDTH
        @Column(column = "DEVICE_DESC")
        private String deviceDesc;//DEVICE_DESC
        @Column(column = "CABLE_LOOP")
        private Integer cableLoop;//CABLE_LOOP
        @Column(column = "REMARK")
        private String remark;//REMARK
        @Column(column = "PLACE_MARKER_TIME")
        private Date placeMarkerTime;//PLACE_MARKER_TIME
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//UPDATE_TIME
    
        
        public String getOid() {
		    return oid;
	    }
        
	    public void setOid(String oid) {
		    this.oid = oid;
	    }
        
        public String getGeom() {
		    return geom;
	    }
        
	    public void setGeom(String geom) {
		    this.geom = geom;
	    }
        
        public Double getAltitude() {
		    return altitude;
	    }
        
	    public void setAltitude(Double altitude) {
		    this.altitude = altitude;
	    }
        
        public String getCoordinateNo() {
		    return coordinateNo;
	    }
        
	    public void setCoordinateNo(String coordinateNo) {
		    this.coordinateNo = coordinateNo;
	    }
        
        public Integer getMarkerType() {
		    return markerType;
	    }
        
	    public void setMarkerType(Integer markerType) {
		    this.markerType = markerType;
	    }
        
        public String getLayType() {
		    return layType;
	    }
        
	    public void setLayType(String layType) {
		    this.layType = layType;
	    }
        
        public String getMarkerNo() {
		    return markerNo;
	    }
        
	    public void setMarkerNo(String markerNo) {
		    this.markerNo = markerNo;
	    }
        
        public String getPrjid() {
		    return prjid;
	    }
        
	    public void setPrjid(String prjid) {
		    this.prjid = prjid;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
        
        public String getInstallPosition() {
		    return installPosition;
	    }
        
	    public void setInstallPosition(String installPosition) {
		    this.installPosition = installPosition;
	    }
        
        public String getGeoLocation() {
		    return geoLocation;
	    }
        
	    public void setGeoLocation(String geoLocation) {
		    this.geoLocation = geoLocation;
	    }
        
        public Double getCableDeepth() {
		    return cableDeepth;
	    }
        
	    public void setCableDeepth(Double cableDeepth) {
		    this.cableDeepth = cableDeepth;
	    }
        
        public Double getCableWidth() {
		    return cableWidth;
	    }
        
	    public void setCableWidth(Double cableWidth) {
		    this.cableWidth = cableWidth;
	    }
        
        public String getDeviceDesc() {
		    return deviceDesc;
	    }
        
	    public void setDeviceDesc(String deviceDesc) {
		    this.deviceDesc = deviceDesc;
	    }
        
        public Integer getCableLoop() {
		    return cableLoop;
	    }
        
	    public void setCableLoop(Integer cableLoop) {
		    this.cableLoop = cableLoop;
	    }
        
        public String getRemark() {
		    return remark;
	    }
        
	    public void setRemark(String remark) {
		    this.remark = remark;
	    }
        
        public Date getPlaceMarkerTime() {
		    return placeMarkerTime;
	    }
        
	    public void setPlaceMarkerTime(Date placeMarkerTime) {
		    this.placeMarkerTime = placeMarkerTime;
	    }
        
        public Date getUpdateTime() {
		    return updateTime;
	    }
        
	    public void setUpdateTime(Date updateTime) {
		    this.updateTime = updateTime;
	    }
    
}
 
