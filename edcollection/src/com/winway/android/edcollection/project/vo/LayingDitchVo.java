package com.winway.android.edcollection.project.vo;

import java.io.Serializable;

import com.winway.android.edcollection.adding.entity.DitchCable;

public class LayingDitchVo extends DitchCable implements Serializable {
	private Integer layingDitchNum; //同沟回路数
	private Integer unknownLoopNum; //未知同沟回路数

	public Integer getLayingDitchNum() {
		return layingDitchNum;
	}

	public void setLayingDitchNum(Integer layingDitchNum) {
		this.layingDitchNum = layingDitchNum;
	}
	
	public void setUnknownLoopNum(Integer unknownLoopNum) {
		this.unknownLoopNum = unknownLoopNum;
	}
	public Integer getUnknownLoopNum() {
		return unknownLoopNum;
	}
}
