package com.winway.android.edcollection.base.entity;

/**
 * 几何对象
 * @author YAN
 *
 */
public class geometry {
	private String type;
	public geometry()
	{
		
	}
	public geometry(String type)
	{
		this.type=type;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
