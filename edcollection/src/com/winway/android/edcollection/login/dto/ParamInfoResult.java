package com.winway.android.edcollection.login.dto;

import java.math.BigDecimal;

public class ParamInfoResult {
	private String paramId;// 参数ID
	private String paramValue;// 参数值
	private String paramName;// 参数名称
	private Integer orderNo;// 顺序编号

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

}
