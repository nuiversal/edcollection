package com.winway.android.edcollection.mobileArk.sso.entity;

/**
 * 获取凭据回调消息
 * 
 * @author winway_zgq
 *
 */
public class SSOTokenMessage extends SSOMessageBase {
	private String token;// 登录凭据

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
