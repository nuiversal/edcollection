package com.winway.android.map.entity;

import java.io.Serializable;

/**
 * 图形对象（空间图形对象和业务对象关联）
 * 
 * @author zgq
 *
 */
public class GraphicObj implements Serializable{

	private String objId;// 唯一值
	private Object graphicObj;// 图形对象
	private Object businessObj;// 业务对象

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public Object getGraphicObj() {
		return graphicObj;
	}

	public void setGraphicObj(Object graphicObj) {
		this.graphicObj = graphicObj;
	}

	public Object getBusinessObj() {
		return businessObj;
	}

	public void setBusinessObj(Object businessObj) {
		this.businessObj = businessObj;
	}

}
