package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * EcDevice实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:24
 */
@Table(name = "ec_device")
public class EcDeviceEntity implements Serializable{

        @Id
        @Column(column = "OID")
        private String oid;//对象ID
        @Column(column = "DEVICE_NO")
        private String deviceNo;//设备编号
        @Column(column = "DEVICE_TYPE")
        private String deviceType;//设备类型(标签,中间头,杆塔)
        @Column(column = "MARKER_ID")
        private String markerId;//标识器ID
        @Column(column = "LINE_NO")
        private String lineNo;//电缆编号
        @Column(column = "ORGID")
        private String orgid;//机构ID
        @Column(column = "LINE_ID")
        private String lineId;//线路ID
        @Column(column = "LONGTITUTE")
        private Double longtitute;//坐标经度
        @Column(column = "LAGTITUTE")
        private Double lagtitute;//坐标纬度
        @Column(column = "INSTALL_POSITION")
        private String installPosition;//安装位置
        @Column(column = "DEVICE_RANGE")
        private String deviceRange;//始末范围
        @Column(column = "BG_PHOTO")
        private String bgPhoto;//背景图|多图用分号
        @Column(column = "POS_PHOTO")
        private String posPhoto;//位置图|多图用分号
        @Column(column = "DEVICE_MODEL")
        private String deviceModel;//设备型号
        @Column(column = "DEVICE_BRAND")
        private String deviceBrand;//设备品牌
        @Column(column = "PRODUCTION_DATE")
        private Date productionDate;//生产日期
        @Column(column = "CONSTRUCTION_ORGANIZATION")
        private String constructionOrganization;//制作/施工单位
        @Column(column = "PRODUCER")
        private String producer;//制作人员
        @Column(column = "INSTALL_DATE")
        private Date installDate;//安装时间
        @Column(column = "CABLE_ATTR_TYPE")
        private String cableAttrType;//电缆附件类型
        @Column(column = "IN_TOTAL")
        private Integer inTotal;//进口总管数
        @Column(column = "IN_EMPTY")
        private Integer inEmpty;//进口空管数
        @Column(column = "IN_CROSS_LOGIC")
        private String inCrossLogic;//进口截面逻辑
        @Column(column = "OUT_TOTAL")
        private Integer outTotal;//出口总管数
        @Column(column = "OUT_EMPTY")
        private Integer outEmpty;//出口空管数
        @Column(column = "OUT_CROSS_LOGIC")
        private String outCrossLogic;//出口截面逻辑
        @Column(column = "CROSS_LOGIC")
        private String crossLogic;//截面逻辑
        @Column(column = "batch")
        private String batch;//batch
        @Column(column = "IS_PANORAMA")
        private Integer isPanorama;//是否全景点
    
        
        public String getOid() {
		    return oid;
	    }
        
	    public void setOid(String oid) {
		    this.oid = oid;
	    }
        
        public String getDeviceNo() {
		    return deviceNo;
	    }
        
	    public void setDeviceNo(String deviceNo) {
		    this.deviceNo = deviceNo;
	    }
        
        public String getDeviceType() {
		    return deviceType;
	    }
        
	    public void setDeviceType(String deviceType) {
		    this.deviceType = deviceType;
	    }
        
        public String getMarkerId() {
		    return markerId;
	    }
        
	    public void setMarkerId(String markerId) {
		    this.markerId = markerId;
	    }
        
        public String getLineNo() {
		    return lineNo;
	    }
        
	    public void setLineNo(String lineNo) {
		    this.lineNo = lineNo;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
        
        public String getLineId() {
		    return lineId;
	    }
        
	    public void setLineId(String lineId) {
		    this.lineId = lineId;
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
        
        public String getInstallPosition() {
		    return installPosition;
	    }
        
	    public void setInstallPosition(String installPosition) {
		    this.installPosition = installPosition;
	    }
        
        public String getDeviceRange() {
		    return deviceRange;
	    }
        
	    public void setDeviceRange(String deviceRange) {
		    this.deviceRange = deviceRange;
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
        
        public String getDeviceModel() {
		    return deviceModel;
	    }
        
	    public void setDeviceModel(String deviceModel) {
		    this.deviceModel = deviceModel;
	    }
        
        public String getDeviceBrand() {
		    return deviceBrand;
	    }
        
	    public void setDeviceBrand(String deviceBrand) {
		    this.deviceBrand = deviceBrand;
	    }
        
        public Date getProductionDate() {
		    return productionDate;
	    }
        
	    public void setProductionDate(Date productionDate) {
		    this.productionDate = productionDate;
	    }
        
        public String getConstructionOrganization() {
		    return constructionOrganization;
	    }
        
	    public void setConstructionOrganization(String constructionOrganization) {
		    this.constructionOrganization = constructionOrganization;
	    }
        
        public String getProducer() {
		    return producer;
	    }
        
	    public void setProducer(String producer) {
		    this.producer = producer;
	    }
        
        public Date getInstallDate() {
		    return installDate;
	    }
        
	    public void setInstallDate(Date installDate) {
		    this.installDate = installDate;
	    }
        
        public String getCableAttrType() {
		    return cableAttrType;
	    }
        
	    public void setCableAttrType(String cableAttrType) {
		    this.cableAttrType = cableAttrType;
	    }
        
        public Integer getInTotal() {
		    return inTotal;
	    }
        
	    public void setInTotal(Integer inTotal) {
		    this.inTotal = inTotal;
	    }
        
        public Integer getInEmpty() {
		    return inEmpty;
	    }
        
	    public void setInEmpty(Integer inEmpty) {
		    this.inEmpty = inEmpty;
	    }
        
        public String getInCrossLogic() {
		    return inCrossLogic;
	    }
        
	    public void setInCrossLogic(String inCrossLogic) {
		    this.inCrossLogic = inCrossLogic;
	    }
        
        public Integer getOutTotal() {
		    return outTotal;
	    }
        
	    public void setOutTotal(Integer outTotal) {
		    this.outTotal = outTotal;
	    }
        
        public Integer getOutEmpty() {
		    return outEmpty;
	    }
        
	    public void setOutEmpty(Integer outEmpty) {
		    this.outEmpty = outEmpty;
	    }
        
        public String getOutCrossLogic() {
		    return outCrossLogic;
	    }
        
	    public void setOutCrossLogic(String outCrossLogic) {
		    this.outCrossLogic = outCrossLogic;
	    }
        
        public String getCrossLogic() {
		    return crossLogic;
	    }
        
	    public void setCrossLogic(String crossLogic) {
		    this.crossLogic = crossLogic;
	    }
        
        public String getBatch() {
		    return batch;
	    }
        
	    public void setBatch(String batch) {
		    this.batch = batch;
	    }
        
        public Integer getIsPanorama() {
		    return isPanorama;
	    }
        
	    public void setIsPanorama(Integer isPanorama) {
		    this.isPanorama = isPanorama;
	    }
    
}
 
