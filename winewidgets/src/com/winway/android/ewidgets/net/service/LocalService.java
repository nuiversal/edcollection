package com.winway.android.ewidgets.net.service;

import java.io.File;
import java.util.Map;

/**
 * 本地请求接口
 * 
 * @author mr-lao
 *
 */
public interface LocalService extends BaseService {
	/**
	 * 将接口对应的字符串数据存储到本地
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 */
	public void saveString(String url, Map<String, String> params, Map<String, String> headers, String result);

	/**
	 * 将接口对应的实体结果数据存储到本地
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @param result
	 */
	public <T> void saveT(String url, Map<String, String> params, Map<String, String> headers, T result);

	/**
	 * 将接口对应的文件结果数据存储到本地
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @param result
	 */
	public void saveFile(String url, Map<String, String> params, Map<String, String> headers, File result);
}
