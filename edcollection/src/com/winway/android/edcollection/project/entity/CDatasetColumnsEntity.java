package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * CDatasetColumns实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:19
 */
@Table(name = "c_dataset_columns")
public class CDatasetColumnsEntity implements Serializable{

        @Id
        @Column(column = "C_DATASET_COLUMNS_ID")
        private Integer cDatasetColumnsId;//字段ID
        @Column(column = "C_DATASET_ID")
        private Integer cDatasetId;//数据集ID
        @Column(column = "NAME")
        private String name;//字段名称
        @Column(column = "NAME_CN")
        private String nameCn;//中文名称
        @Column(column = "DATA_TYPE")
        private Double dataType;//字段类型
        @Column(column = "LENGTH")
        private Double length;//字段长度
        @Column(column = "QUERYABLE")
        private Short queryable;//是否可查询
        @Column(column = "RESULTVISIBLE")
        private Short resultvisible;//是否查询结果显示
        @Column(column = "IS_NAMED")
        private Short isNamed;//是否名称字段
        @Column(column = "SHOW_INDEX")
        private Integer showIndex;//显示顺序
    
        
        public Integer getCDatasetColumnsId() {
		    return cDatasetColumnsId;
	    }
        
	    public void setCDatasetColumnsId(Integer cDatasetColumnsId) {
		    this.cDatasetColumnsId = cDatasetColumnsId;
	    }
        
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
        
        public String getNameCn() {
		    return nameCn;
	    }
        
	    public void setNameCn(String nameCn) {
		    this.nameCn = nameCn;
	    }
        
        public Double getDataType() {
		    return dataType;
	    }
        
	    public void setDataType(Double dataType) {
		    this.dataType = dataType;
	    }
        
        public Double getLength() {
		    return length;
	    }
        
	    public void setLength(Double length) {
		    this.length = length;
	    }
        
        public Short getQueryable() {
		    return queryable;
	    }
        
	    public void setQueryable(Short queryable) {
		    this.queryable = queryable;
	    }
        
        public Short getResultvisible() {
		    return resultvisible;
	    }
        
	    public void setResultvisible(Short resultvisible) {
		    this.resultvisible = resultvisible;
	    }
        
        public Short getIsNamed() {
		    return isNamed;
	    }
        
	    public void setIsNamed(Short isNamed) {
		    this.isNamed = isNamed;
	    }
        
        public Integer getShowIndex() {
		    return showIndex;
	    }
        
	    public void setShowIndex(Integer showIndex) {
		    this.showIndex = showIndex;
	    }
    
}
 
