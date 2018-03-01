package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * CDataset实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:18
 */
@Table(name = "c_dataset")
public class CDatasetEntity implements Serializable{

        @Id
        @Column(column = "C_DATASET_ID")
        private Integer cDatasetId;//数据集ID
        @Column(column = "NAME")
        private String name;//数据集名称
        @Column(column = "DATASET_NO")
        private String datasetNo;//数据集编号
        @Column(column = "STORE_NAME")
        private String storeName;//存储名称|物理表名称或者文件名称
        @Column(column = "FEATURE_TYPE")
        private Double featureType;//要素类型|参考Shape文件的要是类型值
        @Column(column = "KEY_FLD")
        private String keyFld;//关键字段
        @Column(column = "GEO_FLD")
        private String geoFld;//空间字段
        @Column(column = "GEO_FORMAT")
        private Double geoFormat;//编码类型|1 WKB,2 WKT
        @Column(column = "SRID")
        private Double srid;//SRID
        @Column(column = "PROJECTION")
        private String projection;//投影字符串|Proj4 字符串
        @Column(column = "MINX")
        private Double minx;//投影字符串
        @Column(column = "MINY")
        private Double miny;//最小Y
        @Column(column = "MAXX")
        private Double maxx;//最大X
        @Column(column = "MAXY")
        private Double maxy;//最大Y
        @Column(column = "RECORD_COUNT")
        private Double recordCount;//RECORD_COUNT
        @Column(column = "UPDATE_TIME")
        private Date updateTime;//更新时间
        @Column(column = "NAME_CN")
        private String nameCn;//NAME_CN
    
        
        public Integer getCDatasetId() {
		    return cDatasetId;
	    }
        
	    public void setCDatasetId(Integer cDatasetId) {
		    this.cDatasetId = cDatasetId;
	    }
        
        public String getName() {
		    return name;
	    }
        
	    public void setName(String name) {
		    this.name = name;
	    }
        
        public String getDatasetNo() {
		    return datasetNo;
	    }
        
	    public void setDatasetNo(String datasetNo) {
		    this.datasetNo = datasetNo;
	    }
        
        public String getStoreName() {
		    return storeName;
	    }
        
	    public void setStoreName(String storeName) {
		    this.storeName = storeName;
	    }
        
        public Double getFeatureType() {
		    return featureType;
	    }
        
	    public void setFeatureType(Double featureType) {
		    this.featureType = featureType;
	    }
        
        public String getKeyFld() {
		    return keyFld;
	    }
        
	    public void setKeyFld(String keyFld) {
		    this.keyFld = keyFld;
	    }
        
        public String getGeoFld() {
		    return geoFld;
	    }
        
	    public void setGeoFld(String geoFld) {
		    this.geoFld = geoFld;
	    }
        
        public Double getGeoFormat() {
		    return geoFormat;
	    }
        
	    public void setGeoFormat(Double geoFormat) {
		    this.geoFormat = geoFormat;
	    }
        
        public Double getSrid() {
		    return srid;
	    }
        
	    public void setSrid(Double srid) {
		    this.srid = srid;
	    }
        
        public String getProjection() {
		    return projection;
	    }
        
	    public void setProjection(String projection) {
		    this.projection = projection;
	    }
        
        public Double getMinx() {
		    return minx;
	    }
        
	    public void setMinx(Double minx) {
		    this.minx = minx;
	    }
        
        public Double getMiny() {
		    return miny;
	    }
        
	    public void setMiny(Double miny) {
		    this.miny = miny;
	    }
        
        public Double getMaxx() {
		    return maxx;
	    }
        
	    public void setMaxx(Double maxx) {
		    this.maxx = maxx;
	    }
        
        public Double getMaxy() {
		    return maxy;
	    }
        
	    public void setMaxy(Double maxy) {
		    this.maxy = maxy;
	    }
        
        public Double getRecordCount() {
		    return recordCount;
	    }
        
	    public void setRecordCount(Double recordCount) {
		    this.recordCount = recordCount;
	    }
        
        public Date getUpdateTime() {
		    return updateTime;
	    }
        
	    public void setUpdateTime(Date updateTime) {
		    this.updateTime = updateTime;
	    }
        
        public String getNameCn() {
		    return nameCn;
	    }
        
	    public void setNameCn(String nameCn) {
		    this.nameCn = nameCn;
	    }
    
}
 
