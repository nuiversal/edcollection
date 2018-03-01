package com.winway.android.ewidgets.net.service;

import java.io.File;
import java.util.Map;

/**
 * 网络请求接口
 * 
 * @author mr-lao
 *
 */
public interface BaseService {

	/**
	 * 请求方法
	 * 
	 * @author mr-lao
	 *
	 */
	public enum RequestMethod {
		GET, POST, DELETE
	}

	public enum WayFrom {
		INTERNET, LOCAL, DATA_PACKAGE
	}

	interface RequestCallBack<T> {
		void error(int code, WayFrom from);

		void callBack(T request, WayFrom from);
	}

	/**
	 * 使用GET方法向服务器发送请求，服务器返回String类型的数据
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数，形式[key,value]
	 * @return
	 * @throws Exception
	 */
	public void getRequestString(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception;

	/**
	 * 使用POST方法向服务器发送请求，服务器返回String类型的数据
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数，形式[key,value]
	 * @return
	 * @throws Exception
	 */
	public void postRequestString(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception;

	/**
	 * 使用POST或GET方法向服务器发送请求，服务器返回String类型的数据
	 * 
	 * @param url
	 * @param params
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public void requestString(String url, Map<String, String> params, RequestMethod method, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception;

	/**
	 * 使用GET方法向服务器发送请求，服务器返回的是文件数据
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void getRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception;

	/**
	 * 使用POST方法向服务器发送请求，服务器返回的是文件数据
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void postRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception;

	/**
	 * 使用POST或GET方法向服务器发送请求，服务器返回的是文件数据
	 * 
	 * @param url
	 * @param params
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public void requestFile(String url, Map<String, String> params, RequestMethod method, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception;

	/**
	 * 使用GET方法向服务器发送请求，服务器返回JSON格式的String类型的数据，接口使用GSON将数据转化成用户需要的数据实体
	 * 
	 * @param url
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> void getRequest(String url, Map<String, String> params, Map<String, String> headers, Class<T> cls,
			RequestCallBack<T> call) throws Exception;

	/**
	 * 使用POST方法向服务器发送请求，服务器返回JSON格式的String类型的数据，接口使用GSON将数据转化成用户需要的数据实体
	 * 
	 * @param url
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> void postRequest(String url, Map<String, String> params, Map<String, String> headers, Class<T> cls,
			RequestCallBack<T> call) throws Exception;

	/**
	 * 使用POST或GET方法向服务器发送请求，服务器返回JSON格式的String类型的数据，接口使用GSON将数据转化成用户需要的数据实体
	 * 
	 * @param url
	 * @param params
	 * @param cls
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public <T> void request(String url, Map<String, String> params, Map<String, String> headers, Class<T> cls,
			RequestMethod method, RequestCallBack<T> call) throws Exception;

	/**
	 * 向服务器上传单个文件
	 * 
	 * @param url
	 *            请求地址
	 * @param file
	 *            上传的文件
	 * @param fileparam
	 *            文件对应的参数
	 * @return
	 * @throws Exception
	 */
	public void uploadFile(String url, File file, String fileparam, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception;

	/**
	 * 向服务器上传一个或多个文件
	 * 
	 * @param url
	 *            请求地址（如果上传多个文件，则接口必须支持多个文件上传）
	 * @param files
	 *            要上传的文件，数据格式[key,value]，key的值是文件的参数，value的值是文件
	 * @param params
	 *            其他请求参数
	 * @return
	 * @throws Exception
	 */
	public void uploadFile(String url, Map<String, File> files, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception;

	/**
	 * 向服务器上传一个或多个文件，服务器返回JSON格式的String类型数据
	 * 
	 * @param url
	 *            请求地址（如果上传多个文件，则接口必须支持多个文件上传）
	 * @param files
	 *            要上传的文件，数据格式[key,value]，key的值是文件的参数，value的值是文件
	 * @param params
	 *            其他请求参数
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, Class<T> cls, RequestCallBack<T> call) throws Exception;

	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param params
	 * @param filepath
	 *            如果此参数不为空，则接口将下载文件存放到filepath指定的路径，如果为此参数为空，则接口将文件下载到一个随机的路径
	 * @return
	 * @throws Exception
	 */
	public void downLoadFile(String url, Map<String, String> params, Map<String, String> headers, String filepath,
			RequestCallBack<File> call) throws Exception;

}
