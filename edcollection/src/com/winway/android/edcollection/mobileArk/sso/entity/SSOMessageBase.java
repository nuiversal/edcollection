package com.winway.android.edcollection.mobileArk.sso.entity;

/**
 * 基本消息
 * @author winway_zgq
 *
 */
public class SSOMessageBase {
	private int resultCode;// 结果码
	private String resultMessage;// 结果消息

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

}
