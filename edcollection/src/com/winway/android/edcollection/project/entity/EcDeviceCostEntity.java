package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcDeviceCost实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:25
 */
@Table(name = "ec_device_cost")
public class EcDeviceCostEntity implements Serializable{

        @Id
        @Column(column = "EC_DEVICE_COST_ID")
        private String ecDeviceCostId;//EC_DEVICE_COST_ID
        @Column(column = "DEVICE_CODE")
        private String deviceCode;//设备分类编号
        @Column(column = "VALUATION_TYPE")
        private Integer valuationType;//计价类型(1按长度，2按单价)
        @Column(column = "PRICE")
        private Double price;//单价
        @Column(column = "UNIT")
        private Integer unit;//价格单位
    
        
        public String getEcDeviceCostId() {
		    return ecDeviceCostId;
	    }
        
	    public void setEcDeviceCostId(String ecDeviceCostId) {
		    this.ecDeviceCostId = ecDeviceCostId;
	    }
        
        public String getDeviceCode() {
		    return deviceCode;
	    }
        
	    public void setDeviceCode(String deviceCode) {
		    this.deviceCode = deviceCode;
	    }
        
        public Integer getValuationType() {
		    return valuationType;
	    }
        
	    public void setValuationType(Integer valuationType) {
		    this.valuationType = valuationType;
	    }
        
        public Double getPrice() {
		    return price;
	    }
        
	    public void setPrice(Double price) {
		    this.price = price;
	    }
        
        public Integer getUnit() {
		    return unit;
	    }
        
	    public void setUnit(Integer unit) {
		    this.unit = unit;
	    }
    
}
 
