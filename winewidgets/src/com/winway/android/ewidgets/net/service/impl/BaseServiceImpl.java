package com.winway.android.ewidgets.net.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.LineService;
import com.winway.android.ewidgets.net.service.LocalService;

/**
 * 基本网络请求实现类，根据网络状态、用户设置等信息（建议使用此实现类）调用网络实现、本地实现、数据包实现为用户提供服务
 * 
 * 此接口的目的，是为了解决用户获取数据的过程，什么时候该用离线、什么时候该用在线这个问题。用户通过BaseServiceImpl获取数据，就无需关心什么时候得用在线请求，什么时候得用网络请求了。
 * 
 * @author mr-lao
 *
 */
public class BaseServiceImpl implements BaseService {
	// 服务器队列
	private ArrayList<BaseService> serviceList = null;
	// 默认服务器
	private BaseService defaultService = null;
	private int defaultServiceIndex = 0;

	// 本地存储实体(如果不需要缓存，则实体请设置为null，默认为null)
	private LocalService localService = null;

	// 根据最优算法动态改变默认服务器
	private boolean dinamicService = true;

	public boolean isDinamicService() {
		return dinamicService;
	}

	public void setDinamicService(boolean dinamicService) {
		this.dinamicService = dinamicService;
	}

	public BaseServiceImpl() {

	}

	public BaseServiceImpl(ArrayList<BaseService> serviceList) {
		this.serviceList = serviceList;
		defaultService = this.serviceList.get(0);
	}

	public BaseServiceImpl(ArrayList<BaseService> serviceList, int defaultService) {
		this.serviceList = serviceList;
		this.defaultService = this.serviceList.get(defaultService);
	}

	public BaseServiceImpl(ArrayList<BaseService> serviceList, BaseService defaultService) {
		this.serviceList = serviceList;
		this.defaultService = defaultService;
	}

	public void setServiceList(ArrayList<BaseService> serviceList) {
		this.serviceList = serviceList;
		if (this.defaultService == null) {
			this.defaultService = serviceList.get(0);
		}
	}

	public BaseService getDefaultService() {
		return defaultService;
	}

	public void setDefaultService(BaseService defaultService) {
		this.defaultService = defaultService;
	}

	public void setLocalService(LocalService localService) {
		this.localService = localService;
	}

	public ArrayList<BaseService> getServiceList() {
		return serviceList;
	}

	/**
	 * 执行任务类
	 * 
	 * @author mr-lao
	 *
	 */
	class ExcuteTask {
		BaseService.RequestCallBack<?> mCall = null;
		int count = 0;
		int defaultServiceIndex = 0;
		BaseService defaultService;

		public ExcuteTask() {
			this.defaultServiceIndex = BaseServiceImpl.this.defaultServiceIndex;
			this.defaultService = BaseServiceImpl.this.defaultService;
		}

		// 重置服务器
		private void reset() {
			count = 0;
			mCall = null;
			if (dinamicService) {
				BaseServiceImpl.this.defaultService = this.defaultService;
				BaseServiceImpl.this.defaultServiceIndex = this.defaultServiceIndex;
			}
		}

		// 移动到下一个服务器
		private boolean defaultNext() {
			this.defaultServiceIndex++;
			if (this.defaultServiceIndex >= serviceList.size()) {
				this.defaultServiceIndex = 0;
			}
			this.defaultService = serviceList.get(defaultServiceIndex);
			count++;
			if (count >= serviceList.size()) {
				reset();
				return false;
			}
			return true;
		}

		@SuppressWarnings("unchecked")
		<T> void excuteTask(final String url, final Map<String, String> params, final Map<String, String> headers,
				final RequestCallBack<T> call, final RequestMethod requestMethod, final Class<T> cls, final File file,
				final String fileparam, final Map<String, File> files, final String filepath, final String method)
				throws Exception {
			try {
				// 启动默认处理器
				if (null == mCall) {
					mCall = new RequestCallBack<T>() {
						@Override
						public void error(int code, WayFrom from) {
							// 执行下一个
							if (defaultNext()) {
								try {
									excuteTask(url, params, headers, call, requestMethod, cls, file, fileparam, files,
											filepath, method);
								} catch (Exception e) {
									call.error(code, from);
									reset();
								}
							} else {
								call.error(code, from);
								reset();
							}
						}

						@Override
						public void callBack(T request, WayFrom from) {
							// 成功
							call.callBack(request, from);
							reset();
							// 进行本地缓存处理
							if (ExcuteTask.this.defaultService instanceof LineService) {
								if (null == localService) {
									return;
								}
								if (request instanceof String) {
									localService.saveString(url, params, headers, (String) request);
								} else if (request instanceof File) {
									localService.saveFile(url, params, headers, (File) request);
								} else {
									localService.saveT(url, params, headers, request);
								}
							}
						}
					};
				}
				// 进行处理
				if (method.equals(GetRequestString)) {
					this.defaultService.getRequestString(url, params, headers, (RequestCallBack<String>) mCall);
				} else if (method.equals(PostRequestString)) {
					this.defaultService.postRequestString(url, params, headers, (RequestCallBack<String>) mCall);
				} else if (method.equals(RequestString)) {
					this.defaultService.requestString(url, params, requestMethod, headers,
							(RequestCallBack<String>) mCall);
				} else if (method.equals(GetRequestFile)) {
					this.defaultService.getRequestFile(url, params, headers, (RequestCallBack<File>) mCall);
				} else if (method.equals(PostRequestFile)) {
					this.defaultService.postRequestFile(url, params, headers, (RequestCallBack<File>) mCall);
				} else if (method.equals(RequestFile)) {
					this.defaultService.requestFile(url, params, requestMethod, headers, (RequestCallBack<File>) mCall);
				} else if (method.equals(GetRequest)) {
					this.defaultService.getRequest(url, params, headers, cls, (RequestCallBack<T>) mCall);
				} else if (method.equals(PostRequest)) {
					this.defaultService.postRequest(url, params, headers, cls, (RequestCallBack<T>) mCall);
				} else if (method.equals(Request)) {
					this.defaultService.request(url, params, headers, cls, requestMethod, (RequestCallBack<T>) mCall);
				} else if (method.equals(UploadFile)) {
					this.defaultService.uploadFile(url, file, fileparam, headers, (RequestCallBack<String>) mCall);
				} else if (method.equals(UploadFile_A)) {
					this.defaultService.uploadFile(url, files, params, headers, (RequestCallBack<String>) mCall);
				} else if (method.equals(UploadFile_B)) {
					this.defaultService.uploadFile(url, files, params, headers, cls, (RequestCallBack<T>) mCall);
				} else if (method.equals(DownLoadFile)) {
					this.defaultService.downLoadFile(url, params, headers, filepath, (RequestCallBack<File>) mCall);
				}
			} catch (Exception e) {
				if (defaultNext()) {
					/*excuteTask(url, params, headers, call, requestMethod, cls, null, null, null, filepath, method);*/
					excuteTask(url, params, headers, call, requestMethod, cls, file, fileparam, files, filepath, method);
					e.printStackTrace();
				} else {
					reset();
					throw e;
				}
			}
		}

	}

	/**
	 * 执行任务
	 * 
	 * @param url
	 *            不能为空
	 * @param params
	 *            可以为空
	 * @param headers
	 *            可以为空
	 * @param call
	 *            不能为空
	 * @param requestMethod
	 *            可以为空
	 * @param cls
	 *            可以为空
	 * @param file
	 *            可以为空
	 * @param fileparam
	 *            可以为空
	 * @param files
	 *            可以为空
	 * @param filepath
	 *            可以为空
	 * @param method
	 *            可以为空
	 * @throws Exception
	 */
	<T> void excute(final String url, final Map<String, String> params, final Map<String, String> headers,
			final RequestCallBack<T> call, final RequestMethod requestMethod, final Class<T> cls, final File file,
			final String fileparam, final Map<String, File> files, final String filepath, final String method)
			throws Exception {
		new ExcuteTask().excuteTask(url, params, headers, call, requestMethod, cls, file, fileparam, files, filepath,
				method);
	}

	private static final String GetRequestString = "getRequestString";

	@Override
	public void getRequestString(final String url, final Map<String, String> params, final Map<String, String> headers,
			final RequestCallBack<String> call) throws Exception {
		excute(url, params, headers, call, null, null, null, null, null, null, GetRequestString);
	}

	private static final String PostRequestString = "postRequestString";

	@Override
	public void postRequestString(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception {
		excute(url, params, headers, call, null, null, null, null, null, null, PostRequestString);
	}

	private static final String RequestString = "requestString";

	@Override
	public void requestString(String url, Map<String, String> params, RequestMethod method,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		excute(url, params, headers, call, method, null, null, null, null, null, RequestString);
	}

	private static final String GetRequestFile = "getRequestFile";

	@Override
	public void getRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		excute(url, params, headers, call, null, null, null, null, null, null, GetRequestFile);
	}

	private static final String PostRequestFile = "postRequestFile";

	@Override
	public void postRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		excute(url, params, headers, call, null, null, null, null, null, null, PostRequestFile);
	}

	private static final String RequestFile = "requestFile";

	@Override
	public void requestFile(String url, Map<String, String> params, RequestMethod method, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		excute(url, params, headers, call, method, null, null, null, null, null, RequestFile);
	}

	private static final String GetRequest = "getRequest";

	@Override
	public <T> void getRequest(String url, Map<String, String> params, Map<String, String> headers, Class<T> cls,
			RequestCallBack<T> call) throws Exception {
		excute(url, params, headers, call, null, cls, null, null, null, null, GetRequest);
	}

	private static final String PostRequest = "postRequest";

	@Override
	public <T> void postRequest(String url, Map<String, String> params, Map<String, String> headers, Class<T> cls,
			RequestCallBack<T> call) throws Exception {
		excute(url, params, headers, call, null, cls, null, null, null, null, PostRequest);
	}

	private static final String Request = "request";

	@Override
	public <T> void request(String url, Map<String, String> params, Map<String, String> headers, Class<T> cls,
			RequestMethod method, RequestCallBack<T> call) throws Exception {
		excute(url, params, headers, call, method, cls, null, null, null, null, Request);
	}

	private static final String UploadFile = "uploadFile";

	@Override
	public void uploadFile(String url, File file, String fileparam, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception {
		excute(url, null, headers, call, null, null, file, fileparam, null, null, UploadFile);
	}

	private static final String UploadFile_A = "uploadFileA";

	@Override
	public void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		excute(url, params, headers, call, null, null, null, null, files, null, UploadFile_A);
	}

	private static final String UploadFile_B = "uploadFileB";

	@Override
	public <T> void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, Class<T> cls, RequestCallBack<T> call) throws Exception {
		excute(url, params, headers, call, null, cls, null, null, files, null, UploadFile_B);
	}

	private static final String DownLoadFile = "downLoadFile";

	@Override
	public void downLoadFile(String url, Map<String, String> params, Map<String, String> headers, String filepath,
			RequestCallBack<File> call) throws Exception {
		excute(url, params, headers, call, null, null, null, null, null, filepath, DownLoadFile);
	}

}
