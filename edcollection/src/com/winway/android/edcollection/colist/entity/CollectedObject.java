package com.winway.android.edcollection.colist.entity;

import java.util.Date;

/**
 * 已采集的对象
 * 
 * @author zgq
 *
 */
public class CollectedObject {

	private String oid;// 唯一id
	private String objType;// 对象类型
	private String no;// 编号
	private String layType;// 敷设方式
	private Date updateTime;// 更新时间
	private int isUpload;// 是否上传
	private Integer operatorType;// 操作类型
	private String orderNo; // 序号

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getLayType() {
		return layType;
	}

	public void setLayType(String layType) {
		this.layType = layType;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(int isUpload) {
		this.isUpload = isUpload;
	}

}
