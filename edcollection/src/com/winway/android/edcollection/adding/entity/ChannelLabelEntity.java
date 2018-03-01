package com.winway.android.edcollection.adding.entity;

import java.io.Serializable;

import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;

/**
 * 
 * @author lyh
 *
 */
public class ChannelLabelEntity implements Serializable {
	private String typeNo; // 资源编号
	private String objId; // 对象id
	private EcLineLabelEntityVo ecLineLabelEntityVo; // 标签实体
	public String getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}

	public EcLineLabelEntityVo getEcLineLabelEntityVo() {
		return ecLineLabelEntityVo;
	}

	public void setEcLineLabelEntityVo(EcLineLabelEntityVo ecLineLabelEntityVo) {
		this.ecLineLabelEntityVo = ecLineLabelEntityVo;
	}

}
