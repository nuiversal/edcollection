package com.winway.android.ewidgets.input.entity;

/**
 * 条目项数据
 * @author winway_zgq
 *
 */
public class DataItem {
    private String name;//名称显示字段
    private Object busiObj;//业务对象
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getBusiObj() {
		return busiObj;
	}
	public void setBusiObj(Object busiObj) {
		this.busiObj = busiObj;
	}
    
    
}
