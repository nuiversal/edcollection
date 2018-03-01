package com.winway.android.edcollection.adding.entity;

/** 
* @author lyh  
* @version 创建时间：2017年1月18日 上午11:10:57 
* 
*/
public enum LineTypeEnum {
	dlgcxl(1, "电缆工程线路"), jkgcxl(2, "架空工程线路"), dl(3, "电缆"), jkdx(4, "架空导线");
	private int index;
	private String type;

	LineTypeEnum(int index, String type) {
		this.index = index;
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}
}
