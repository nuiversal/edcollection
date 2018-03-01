package com.winway.android.ewidgets.net.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.winway.android.ewidgets.net.entity.ErrorType;
import com.winway.android.ewidgets.net.service.DataPackageLoader;
import com.winway.android.util.GsonUtils;

/**
 * 抽象的数据加载器（建议所有的DataPackageLoader都继承此抽象加载器，不要直接继承加载器接口）
 * 
 * @author mr-lao
 * @date 2016-12-28
 */
public abstract class BaseDataPackageLoader implements DataPackageLoader {

	@Override
	public abstract void getRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception;

	@Override
	public void postRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		getRequestString(url, params, headers, call);
	}

	@Override
	public void requestString(String url, Map<String, String> params, RequestMethod method,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		getRequestString(url, params, headers, call);
	}

	@Override
	public abstract void getRequestFile(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<File> call) throws Exception;

	@Override
	public void postRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		getRequestFile(url, params, headers, call);
	}

	@Override
	public void requestFile(String url, Map<String, String> params, RequestMethod method,
			Map<String, String> headers, RequestCallBack<File> call) throws Exception {
		getRequestFile(url, params, headers, call);
	}

	@Override
	public <T> void getRequest(String url, Map<String, String> params, Map<String, String> headers,
			final Class<T> cls, final RequestCallBack<T> call) throws Exception {
		getRequestString(url, params, headers, new RequestCallBack<String>() {
			@Override
			public void error(int code,WayFrom from) {
				call.error(code,WayFrom.DATA_PACKAGE);
			}

			@Override
			public void callBack(String request,WayFrom from) {
				try {
					T objREST = GsonUtils.build().fromJson(request, cls);
					call.callBack(objREST,WayFrom.DATA_PACKAGE);
				} catch (Exception e) {
					call.error(ErrorType.ERROR_PROCESS_RESPONSE,WayFrom.DATA_PACKAGE);
				}
			}
		});
	}

	@Override
	public <T> void postRequest(String url, Map<String, String> params, Map<String, String> headers,
			Class<T> cls, RequestCallBack<T> call) throws Exception {
		getRequest(url, params, headers, cls, call);
	}

	@Override
	public <T> void request(String url, Map<String, String> params, Map<String, String> headers,
			Class<T> cls, RequestMethod method, RequestCallBack<T> call) throws Exception {
		getRequest(url, params, headers, cls, call);
	}

	@Override
	public void uploadFile(String url, File file, String fileparam, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception {

	}

	@Override
	public void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {

	}

	@Override
	public <T> void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, Class<T> cls, RequestCallBack<T> call) throws Exception {

	}

	@Override
	public abstract void downLoadFile(String url, Map<String, String> params,
			Map<String, String> headers, String filepath, RequestCallBack<File> call)
			throws Exception;

}
