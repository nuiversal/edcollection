package com.winway.android.ewidgets.tree.bll;

import java.util.Map;

/**
 * 所有TreeNote内容的父类
 * @author mr-lao
 *
 */
@Deprecated
public class BaseNoteContentMSG {
	/**
	 * 唯一标识
	 */
	private long UNIQE_MARK;
	/**
	 * HTTP请求路径
	 */
	private String url;
	/**
	 * HTTP请求参数
	 */
	private Map<String, String> params;
	/**
	 * HTTP请求头
	 */
	private Map<String, String> headers;
	/**
	 * GET 或者 POST
	 */
	private String httpMethod;

	public long getUNIQE_MARK() {
		return UNIQE_MARK;
	}

	public void setUNIQE_MARK(long uNIQE_MARK) {
		UNIQE_MARK = uNIQE_MARK;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

}
