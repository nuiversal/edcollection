package com.winway.android.edcollection.login.dto;

import java.util.ArrayList;
import java.util.List;

public class ParamTypeResult {

	private String paramTypeId;// 参数类型ID
	private String paramTypeNo;// 参数类型标识
	private String paramTypeName;// 参数类型名称
	private List<ParamInfoResult> params = new ArrayList<ParamInfoResult>();

	public String getParamTypeId() {
		return paramTypeId;
	}

	public void setParamTypeId(String paramTypeId) {
		this.paramTypeId = paramTypeId;
	}

	public void setParams(List<ParamInfoResult> params) {
		this.params = params;
	}

	public String getParamTypeNo() {
		return paramTypeNo;
	}

	public void setParamTypeNo(String paramTypeNo) {
		this.paramTypeNo = paramTypeNo;
	}

	public String getParamTypeName() {
		return paramTypeName;
	}

	public void setParamTypeName(String paramTypeName) {
		this.paramTypeName = paramTypeName;
	}

	public List<ParamInfoResult> getParams() {
		return params;
	}

}
