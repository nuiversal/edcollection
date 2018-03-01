package com.winway.android.edcollection.adding.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备状态枚举
 *@author lyh
 *@data 2017年2月13日
 */
public enum SBZTEnum {

	wty(1,"未投运"), //未投运
	zy(2,"在运"), //在运
	xcly(3,"现成留用"); //现场留用
	
	private int index;
	private String value;
	
	private SBZTEnum(int index, String value) {
		this.index = index;
		this.value = value;
	}
	
	public int getIndex() {
		return index;
	}
	public String getValue() {
		return value;
	}
	
	public List<String> getSBZTList() {
		List<String> sbztList = new ArrayList<String>();
		sbztList.add(SBZTEnum.wty.getValue());
		sbztList.add(SBZTEnum.zy.getValue());
		sbztList.add(SBZTEnum.xcly.getValue());
		return sbztList;
	}
}
