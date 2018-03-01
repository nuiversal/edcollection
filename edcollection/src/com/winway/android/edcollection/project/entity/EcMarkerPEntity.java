package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcMarkerP实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:34
 */
@Table(name = "ec_marker_p")
public class EcMarkerPEntity implements Serializable{

        @Id
        @Column(column = "OID")
        private String oid;//OID
        @Column(column = "MARKER_TYPE")
        private String markerType;//标识器类型|1 标识钉，2 标识球
        @Column(column = "LAY_TYPE")
        private String layType;//敷设类型
        @Column(column = "MARKER_NO")
        private String markerNo;//标识器编号
        @Column(column = "STATION_NO")
        private String stationNo;//变电站编号
        @Column(column = "VOLTAGE")
        private Double voltage;//电压等级
        @Column(column = "LINE_NO")
        private String lineNo;//线路编号
        @Column(column = "LINENAME")
        private String linename;//线路名称
        @Column(column = "TINDEX")
        private Double tindex;//连接顺序
        @Column(column = "LONGTITUTE")
        private Double longtitute;//坐标经度
        @Column(column = "LAGTITUTE")
        private Double lagtitute;//坐标纬度
        @Column(column = "LONGTITUTE_2D")
        private Double longtitute2d;//2D坐标经度
        @Column(column = "LAGTITUTE_2D")
        private Double lagtitute2d;//2D坐标纬度
        @Column(column = "INSTALL_POSITION")
        private String installPosition;//安装位置
        @Column(column = "GEO_LOCATION")
        private String geoLocation;//地理位置
        @Column(column = "CABLE_DEEPTH")
        private Double cableDeepth;//深度|距电缆沟、槽、顶管底深度
        @Column(column = "CABLE_WIDTH")
        private Double cableWidth;//沟槽宽度
        @Column(column = "DEVICE_DESC")
        private String deviceDesc;//设备描述
        @Column(column = "CABLE_LOOP")
        private Double cableLoop;//同沟电缆回路数
        @Column(column = "UP_MARKER_SPACE")
        private Double upMarkerSpace;//上一设备距离
        @Column(column = "PATH_POINT_TYPE")
        private String pathPointType;//路径点类型
        @Column(column = "CABLE_NAME_PHASE")
        private String cableNamePhase;//同沟电缆名称
        @Column(column = "UP_MARKER_LAY")
        private String upMarkerLay;//上一段敷设方式
        @Column(column = "REMARK")
        private String remark;//备注
        @Column(column = "BG_PHOTO")
        private String bgPhoto;//背景图|多图用分号
        @Column(column = "POS_PHOTO")
        private String posPhoto;//位置图|多图用分号
        @Column(column = "PLACE_MARKER_TIME")
        private Date placeMarkerTime;//放置时间
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//最后更新时间
        @Column(column = "ORGID")
        private String orgid;//ORGID
        @Column(column = "UNKNOW_CABLE_LOOP")
        private Double unknowCableLoop;//UNKNOW_CABLE_LOOP
    
        
        public String getOid() {
		    return oid;
	    }
        
	    public void setOid(String oid) {
		    this.oid = oid;
	    }
        
        public String getMarkerType() {
		    return markerType;
	    }
        
	    public void setMarkerType(String markerType) {
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
        
        public String getStationNo() {
		    return stationNo;
	    }
        
	    public void setStationNo(String stationNo) {
		    this.stationNo = stationNo;
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
        
        public Double getTindex() {
		    return tindex;
	    }
        
	    public void setTindex(Double tindex) {
		    this.tindex = tindex;
	    }
        
        public Double getLongtitute() {
		    return longtitute;
	    }
        
	    public void setLongtitute(Double longtitute) {
		    this.longtitute = longtitute;
	    }
        
        public Double getLagtitute() {
		    return lagtitute;
	    }
        
	    public void setLagtitute(Double lagtitute) {
		    this.lagtitute = lagtitute;
	    }
        
        public Double getLongtitute2d() {
		    return longtitute2d;
	    }
        
	    public void setLongtitute2d(Double longtitute2d) {
		    this.longtitute2d = longtitute2d;
	    }
        
        public Double getLagtitute2d() {
		    return lagtitute2d;
	    }
        
	    public void setLagtitute2d(Double lagtitute2d) {
		    this.lagtitute2d = lagtitute2d;
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
        
        public Double getCableLoop() {
		    return cableLoop;
	    }
        
	    public void setCableLoop(Double cableLoop) {
		    this.cableLoop = cableLoop;
	    }
        
        public Double getUpMarkerSpace() {
		    return upMarkerSpace;
	    }
        
	    public void setUpMarkerSpace(Double upMarkerSpace) {
		    this.upMarkerSpace = upMarkerSpace;
	    }
        
        public String getPathPointType() {
		    return pathPointType;
	    }
        
	    public void setPathPointType(String pathPointType) {
		    this.pathPointType = pathPointType;
	    }
        
        public String getCableNamePhase() {
		    return cableNamePhase;
	    }
        
	    public void setCableNamePhase(String cableNamePhase) {
		    this.cableNamePhase = cableNamePhase;
	    }
        
        public String getUpMarkerLay() {
		    return upMarkerLay;
	    }
        
	    public void setUpMarkerLay(String upMarkerLay) {
		    this.upMarkerLay = upMarkerLay;
	    }
        
        public String getRemark() {
		    return remark;
	    }
        
	    public void setRemark(String remark) {
		    this.remark = remark;
	    }
        
        public String getBgPhoto() {
		    return bgPhoto;
	    }
        
	    public void setBgPhoto(String bgPhoto) {
		    this.bgPhoto = bgPhoto;
	    }
        
        public String getPosPhoto() {
		    return posPhoto;
	    }
        
	    public void setPosPhoto(String posPhoto) {
		    this.posPhoto = posPhoto;
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
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
        
        public Double getUnknowCableLoop() {
		    return unknowCableLoop;
	    }
        
	    public void setUnknowCableLoop(Double unknowCableLoop) {
		    this.unknowCableLoop = unknowCableLoop;
	    }
    
}
 
