package com.winway.android.edcollection.login.entity;

/**
 * 
 * @author xs
 *
 */
public class ServerInfo {
	// 登录地址
	private String loginServerAddr;
	// 数据服务接口地址
	private String dataServerAddr;
	// 文件通讯服务地址
	private String fileConnectServerAddr;

	public String getLoginServerAddr() {
		return loginServerAddr;
	}

	public void setLoginServerAddr(String loginServerAddr) {
		this.loginServerAddr = loginServerAddr;
	}

	public String getDataServerAddr() {
		return dataServerAddr;
	}

	public void setDataServerAddr(String dataServerAddr) {
		this.dataServerAddr = dataServerAddr;
	}

	public String getFileConnectServerAddr() {
		return fileConnectServerAddr;
	}

	public void setFileConnectServerAddr(String fileConnectServerAddr) {
		this.fileConnectServerAddr = fileConnectServerAddr;
	}
}
