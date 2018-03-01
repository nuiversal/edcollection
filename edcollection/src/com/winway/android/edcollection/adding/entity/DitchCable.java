package com.winway.android.edcollection.adding.entity;

import java.io.Serializable;

import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;

/**
 * 同沟电缆
 * 
 * @author ly
 *
 */
public class DitchCable implements Serializable {
	private String id;// 用来修改静态数据时所用的
	private String lineId;// 线路id
	private String lineNo;// 线路编号
	private String LineName;// 线路名称
	private Integer lineNodeSort;// 线路节点序号
	private String LayType;// 敷设方式
	private Float PreviousNodeDistance;// 上一节点距离
	private String deviceDescription;//设备描述
	private EcHwgEntityVo hwgEntityVo;// 接收环网柜的数据
	private EcLineLabelEntityVo lineLabelEntityVo;// 接收电子标签的数据
	private EcDlfzxEntityVo dlfzxEntityVo;// 接收电缆分支箱的数据
	private EcDydlfzxEntityVo dydlfzxEntityVo;// 接收低压电缆分支箱的数据
	private EcMiddleJointEntityVo middleJointEntityVo;// 电缆中间接头
	private boolean isEditAdd = false;// 是否是编辑状态下的添加
	private String lineNodesId;
	private String oid;
	
	public String getDeviceDescription() {
		return deviceDescription;
	}

	public void setDeviceDescription(String deviceDescription) {
		this.deviceDescription = deviceDescription;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getLineName() {
		return LineName;
	}

	public void setLineName(String lineName) {
		LineName = lineName;
	}

	public Integer getLineNodeSort() {
		return lineNodeSort;
	}

	public void setLineNodeSort(Integer lineNodeSort) {
		this.lineNodeSort = lineNodeSort;
	}

	public String getLayType() {
		return LayType;
	}

	public void setLayType(String layType) {
		LayType = layType;
	}

	public EcHwgEntityVo getHwgEntityVo() {
		return hwgEntityVo;
	}

	public void setHwgEntityVo(EcHwgEntityVo hwgEntityVo) {
		this.hwgEntityVo = hwgEntityVo;
	}

	public EcLineLabelEntityVo getLineLabelEntityVo() {
		return lineLabelEntityVo;
	}

	public void setLineLabelEntityVo(EcLineLabelEntityVo lineLabelEntityVo) {
		this.lineLabelEntityVo = lineLabelEntityVo;
	}

	public EcDlfzxEntityVo getDlfzxEntityVo() {
		return dlfzxEntityVo;
	}

	public void setDlfzxEntityVo(EcDlfzxEntityVo dlfzxEntityVo) {
		this.dlfzxEntityVo = dlfzxEntityVo;
	}

	public EcDydlfzxEntityVo getDydlfzxEntityVo() {
		return dydlfzxEntityVo;
	}

	public void setDydlfzxEntityVo(EcDydlfzxEntityVo dydlfzxEntityVo) {
		this.dydlfzxEntityVo = dydlfzxEntityVo;
	}

	public EcMiddleJointEntityVo getMiddleJointEntityVo() {
		return middleJointEntityVo;
	}

	public void setMiddleJointEntityVo(EcMiddleJointEntityVo middleJointEntityVo) {
		this.middleJointEntityVo = middleJointEntityVo;
	}

	public boolean isEditAdd() {
		return isEditAdd;
	}

	public void setEditAdd(boolean isEditAdd) {
		this.isEditAdd = isEditAdd;
	}

	public String getLineNodesId() {
		return lineNodesId;
	}

	public void setLineNodesId(String lineNodesId) {
		this.lineNodesId = lineNodesId;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Float getPreviousNodeDistance() {
		return PreviousNodeDistance;
	}

	public void setPreviousNodeDistance(Float previousNodeDistance) {
		PreviousNodeDistance = previousNodeDistance;
	}

}
