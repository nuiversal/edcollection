package com.winway.android.edcollection.base.dto;

import java.util.Map;

/**
 * 基本传输类
 * 
 * @author zgq
 *
 */
public class MessageBase {
	private int code = 0;
	private String msg = "";
	private Object result;
	private Map<String, Object> attributes;// 其他参数

	public MessageBase() {

	}

	public MessageBase(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}
