package com.winway.android.ewidgets.net.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.winway.android.ewidgets.net.entity.ErrorType;
import com.winway.android.ewidgets.net.entity.ParamsWrapter;
import com.winway.android.ewidgets.net.entity.UrlWrapter;
import com.winway.android.ewidgets.net.filter.RequestInterceptor;
import com.winway.android.ewidgets.net.filter.RequestInterceptor.ExcuteRequest;
import com.winway.android.ewidgets.net.filter.ResponseInterceptor;
import com.winway.android.ewidgets.net.service.LineService;
import com.winway.android.util.GsonUtils;

/**
 * 
 * @author lyh
 * 使用方法：
 * 1、设置asyn参数的值控制HTTP请求同步执行或者是异常执行，默认是
 * 2、设置mDownLoadProgressListener值，可以监听文件下载进度
 * 3、设置拦截器，可以在执行HTTP请求之前将请求拦截下来，适合场景：全局在线登陆
 */
public class LineServiceImpl implements LineService {
	/**true异步执行HTTP请求，false同步执行HTTP请求*/
	public boolean asyn = true;

	/**true异步执行HTTP请求，false同步执行HTTP请求*/
	public boolean isAsyn() {
		return asyn;
	}

	/**true异步执行HTTP请求，false同步执行HTTP请求*/
	public void setAsyn(boolean asyn) {
		this.asyn = asyn;
	}

	/**
	 * 回调包装器
	 * @author mr-lao
	 *
	 */
	@SuppressWarnings("hiding")
	class CallBackWrapter<Object> {
		public RequestCallBack<Object> call = null;
		public Object value;
		public int errorCode = -1000;
	}

	/**
	 * 进行UI回调
	 */
	@SuppressLint("HandlerLeak")
	private static Handler uiHandler;

	<T> void callBackToUI(RequestCallBack<T> call, T value, Integer errorCode) {
		if (asyn) {
			CallBackWrapter<T> wrapter = new CallBackWrapter<T>();
			wrapter.value = value;
			wrapter.call = call;
			if (errorCode != null) {
				wrapter.errorCode = errorCode;
			}
			Message msg = Message.obtain();
			msg.obj = wrapter;
			uiHandler.sendMessage(msg);
		} else {

			// 增加返回拦截 2017-10-11
			if (null != globalResponseInterceptor) {
				boolean intercept = globalResponseInterceptor.interceptResult(value);
				if (intercept) {
					return;
				}
			}

			if (errorCode == null) {
				call.callBack(value, WayFrom.INTERNET);
			} else {
				call.error(errorCode, WayFrom.INTERNET);
			}
		}
	}

	@SuppressLint("HandlerLeak")
	public LineServiceImpl() {
		this.init();
		if (null == uiHandler && asyn) {
			// 只有开启了异步，才会实例化uiHandler对象
			uiHandler = new Handler() {
				public void handleMessage(android.os.Message msg) {
					@SuppressWarnings("unchecked")
					CallBackWrapter<Object> wrapter = (CallBackWrapter<java.lang.Object>) msg.obj;

					/********************************结果拦截*********************************/
					if (null != globalResponseInterceptor) {
						boolean intercept = globalResponseInterceptor.interceptResult(wrapter.value);
						if (intercept) {
							return;
						}
					}
					/********************************结果拦截*********************************/

					if (wrapter.errorCode > -1000) {
						wrapter.call.error(wrapter.errorCode, WayFrom.INTERNET);
					} else {
						wrapter.call.callBack(wrapter.value, WayFrom.INTERNET);
					}
				}
			};
		}
	}

	// 文件目录
	private String fileDir;

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	// okhttp实例
	private OkHttpClient mOkHttpClient;
	// 超时时间
	public static final int TIMEOUT = 1000 * 60;

	private void init() {
		fileDir = Environment.getExternalStorageDirectory().getPath() + "/winway/";
		mOkHttpClient = new OkHttpClient();
		// 设置超时时间
		mOkHttpClient.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).writeTimeout(TIMEOUT, TimeUnit.SECONDS)
				.readTimeout(TIMEOUT, TimeUnit.SECONDS).build();
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
	@Override
	public void getRequestString(String url, Map<String, String> params, Map<String, String> headers,
			final RequestCallBack<String> call) throws Exception {
		if (isHasRequestIntercept()) {
			final UrlWrapter Lurl = new UrlWrapter(url);
			final ParamsWrapter<String, String> Lparams = new ParamsWrapter<String, String>(params);
			final ParamsWrapter<String, String> Lheaders = new ParamsWrapter<String, String>(headers);
			boolean intercept = requestIntercept(Lurl, Lparams, Lheaders, RequestMethod.GET, new ExcuteRequest() {
				@Override
				public void request() {
					try {
						Request request = iterGet(Lurl.url, Lparams.params, Lheaders.params);
						callBack(call, request);
					} catch (Exception e) {
						e.printStackTrace();
						callBackToUI(call, null, ErrorType.ERROR_METHOD_EXCEPTION);
					}
				}

				@Override
				public boolean needAsyn() {
					return asyn;
				}

				@Override
				public void cancle() {
					callBackToUI(call, null, ErrorType.ERROR_INTERCEPTOR_CANCLE);
				}
			});
			if (intercept) {
				return;
			}
		}
		Request request = iterGet(url, params, headers);
		callBack(call, request);
	}

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
	@Override
	public void postRequestString(String url, Map<String, String> params, Map<String, String> headers,
			final RequestCallBack<String> call) throws Exception {
		if (isHasRequestIntercept()) {
			final UrlWrapter Lurl = new UrlWrapter(url);
			final ParamsWrapter<String, String> Lparams = new ParamsWrapter<String, String>(params);
			final ParamsWrapter<String, String> Lheaders = new ParamsWrapter<String, String>(headers);
			boolean intercept = requestIntercept(Lurl, Lparams, Lheaders, RequestMethod.POST, new ExcuteRequest() {
				@Override
				public void request() {
					try {
						Request request = iterPost(Lurl.url, Lparams.params, Lheaders.params);
						callBack(call, request);
					} catch (Exception e) {
						e.printStackTrace();
						callBackToUI(call, null, ErrorType.ERROR_METHOD_EXCEPTION);
					}
				}

				@Override
				public boolean needAsyn() {
					return asyn;
				}

				@Override
				public void cancle() {
					callBackToUI(call, null, ErrorType.ERROR_INTERCEPTOR_CANCLE);
				}
			});
			if (intercept) {
				return;
			}
		}
		Request request = iterPost(url, params, headers);
		callBack(call, request);
	}

	/**
	 * 使用POST或GET方法向服务器发送请求，服务器返回String类型的数据
	 * 
	 * @param url
	 * @param params
	 * @param method
	 * @return
	 * @throws Exception
	 */
	@Override
	public void requestString(String url, Map<String, String> params, RequestMethod method,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		if (method.equals(RequestMethod.GET)) {
			getRequestString(url, params, headers, call);
		} else if (method.equals(RequestMethod.POST)) {
			postRequestString(url, params, headers, call);
		}
	}

	/**
	 * 使用GET方法向服务器发送请求，服务器返回的是文件数据
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public void getRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			final RequestCallBack<File> call) throws Exception {
		if (isHasRequestIntercept()) {
			final UrlWrapter Lurl = new UrlWrapter(url);
			final ParamsWrapter<String, String> Lparams = new ParamsWrapter<String, String>(params);
			final ParamsWrapter<String, String> Lheaders = new ParamsWrapter<String, String>(headers);
			boolean intercept = requestIntercept(Lurl, Lparams, Lheaders, RequestMethod.GET, new ExcuteRequest() {
				@Override
				public void request() {
					Request request = iterGet(Lurl.url, Lparams.params, Lheaders.params);
					fileCallBack(call, Lurl.url, request);
				}

				@Override
				public boolean needAsyn() {
					return asyn;
				}

				@Override
				public void cancle() {
					callBackToUI(call, null, ErrorType.ERROR_INTERCEPTOR_CANCLE);
				}
			});
			if (intercept) {
				return;
			}
		}
		Request request = iterGet(url, params, headers);
		fileCallBack(call, url, request);
	}

	/**
	 * 使用POST方法向服务器发送请求，服务器返回的是文件数据
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public void postRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			final RequestCallBack<File> call) throws Exception {
		if (isHasRequestIntercept()) {
			final UrlWrapter Lurl = new UrlWrapter(url);
			final ParamsWrapter<String, String> Lparams = new ParamsWrapter<String, String>(params);
			final ParamsWrapter<String, String> Lheaders = new ParamsWrapter<String, String>(headers);
			boolean intercept = requestIntercept(Lurl, Lparams, Lheaders, RequestMethod.POST, new ExcuteRequest() {
				@Override
				public void request() {
					Request request = iterPost(Lurl.url, Lparams.params, Lheaders.params);
					fileCallBack(call, Lurl.url, request);
				}

				@Override
				public boolean needAsyn() {
					return asyn;
				}

				@Override
				public void cancle() {
					callBackToUI(call, null, ErrorType.ERROR_INTERCEPTOR_CANCLE);
				}
			});
			if (intercept) {
				return;
			}
		}
		Request request = iterPost(url, params, headers);
		fileCallBack(call, url, request);
	}

	/**
	 * 使用POST或GET方法向服务器发送请求，服务器返回的是文件数据
	 * 
	 * @param url
	 * @param params
	 * @param method
	 * @return
	 * @throws Exception
	 */
	@Override
	public void requestFile(String url, Map<String, String> params, RequestMethod method, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		if (method.equals(RequestMethod.GET)) {
			getRequestFile(url, params, headers, call);
		} else if (method.equals(RequestMethod.POST)) {
			postRequestFile(url, params, headers, call);
		}
	}

	/**
	 * 使用GET方法向服务器发送请求，服务器返回JSON格式的String类型的数据，接口使用GSON将数据转化成用户需要的数据实体
	 * 
	 * @param url
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> void getRequest(String url, Map<String, String> params, Map<String, String> headers, final Class<T> cls,
			final RequestCallBack<T> call) throws Exception {
		if (isHasRequestIntercept()) {
			final UrlWrapter Lurl = new UrlWrapter(url);
			final ParamsWrapter<String, String> Lparams = new ParamsWrapter<String, String>(params);
			final ParamsWrapter<String, String> Lheaders = new ParamsWrapter<String, String>(headers);
			boolean intercept = requestIntercept(Lurl, Lparams, Lheaders, RequestMethod.GET, new ExcuteRequest() {
				@Override
				public void request() {
					try {
						Request request = iterGet(Lurl.url, Lparams.params, Lheaders.params);
						TcallBack(cls, call, request);
					} catch (Exception e) {
						e.printStackTrace();
						callBackToUI(call, null, ErrorType.ERROR_METHOD_EXCEPTION);
					}
				}

				@Override
				public boolean needAsyn() {
					return asyn;
				}

				@Override
				public void cancle() {
					callBackToUI(call, null, ErrorType.ERROR_INTERCEPTOR_CANCLE);
				}
			});
			if (intercept) {
				return;
			}
		}
		Request request = iterGet(url, params, headers);
		TcallBack(cls, call, request);
	}

	/**
	 * 使用POST方法向服务器发送请求，服务器返回JSON格式的String类型的数据，接口使用GSON将数据转化成用户需要的数据实体
	 * 
	 * @param url
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> void postRequest(String url, Map<String, String> params, Map<String, String> headers,
			final Class<T> cls, final RequestCallBack<T> call) throws Exception {
		if (isHasRequestIntercept()) {
			final UrlWrapter Lurl = new UrlWrapter(url);
			final ParamsWrapter<String, String> Lparams = new ParamsWrapter<String, String>(params);
			final ParamsWrapter<String, String> Lheaders = new ParamsWrapter<String, String>(headers);
			boolean intercept = requestIntercept(Lurl, Lparams, Lheaders, RequestMethod.POST, new ExcuteRequest() {
				@Override
				public void request() {
					try {
						Request request = iterPost(Lurl.url, Lparams.params, Lheaders.params);
						TcallBack(cls, call, request);
					} catch (Exception e) {
						e.printStackTrace();
						callBackToUI(call, null, ErrorType.ERROR_METHOD_EXCEPTION);
					}
				}

				@Override
				public boolean needAsyn() {
					return asyn;
				}

				@Override
				public void cancle() {
					callBackToUI(call, null, ErrorType.ERROR_INTERCEPTOR_CANCLE);
				}
			});
			if (intercept) {
				return;
			}
		}
		Request request = iterPost(url, params, headers);
		TcallBack(cls, call, request);
	}

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
	@Override
	public <T> void request(String url, Map<String, String> params, Map<String, String> headers, Class<T> cls,
			RequestMethod method, RequestCallBack<T> call) throws Exception {
		if (method.equals(RequestMethod.GET)) {
			getRequest(url, params, headers, cls, call);
		} else if (method.equals(RequestMethod.POST)) {
			postRequest(url, params, headers, cls, call);
		}
	}

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
	@Override
	public void uploadFile(String url, final File file, final String fileparam, Map<String, String> headers,
			final RequestCallBack<String> call) throws Exception {
		if (isHasRequestIntercept()) {
			final UrlWrapter Lurl = new UrlWrapter(url);
			final ParamsWrapter<String, String> Lheaders = new ParamsWrapter<String, String>(headers);
			boolean intercept = uploadFileIntercept(Lurl, file, fileparam, Lheaders, new ExcuteRequest() {
				@Override
				public void request() {
					if (file != null) {
						try {
							RequestBody fileBody = RequestBody.create(
									MediaType.parse("application/octet-stream;charset=utf-8"), file);
							MultipartBody.Builder builder = new MultipartBody.Builder();
							// 上传参数
							RequestBody requestBody = builder.addFormDataPart(fileparam, file.getName(), fileBody)
									.build();
							okhttp3.Request.Builder get = new Request.Builder();
							iterHeader(Lheaders.params, get);
							Request request = get.post(requestBody).url(Lurl.url).build();
							callBack(call, request);
						} catch (Exception e) {
							e.printStackTrace();
							callBackToUI(call, null, ErrorType.ERROR_METHOD_EXCEPTION);
						}
					}
				}

				@Override
				public boolean needAsyn() {
					return asyn;
				}

				@Override
				public void cancle() {
					callBackToUI(call, null, ErrorType.ERROR_INTERCEPTOR_CANCLE);
				}

			});
			if (intercept) {
				return;
			}
		}
		if (file != null) {
			RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream;charset=utf-8"), file);
			MultipartBody.Builder builder = new MultipartBody.Builder();
			// 上传参数
			RequestBody requestBody = builder.addFormDataPart(fileparam, file.getName(), fileBody).build();
			okhttp3.Request.Builder get = new Request.Builder();

			iterHeader(headers, get);
			Request request = get.post(requestBody).url(url).build();
			callBack(call, request);
		}
	}

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
	@Override
	public void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, final RequestCallBack<String> call) throws Exception {
		if (isHasRequestIntercept()) {
			final UrlWrapter Lurl = new UrlWrapter(url);
			final ParamsWrapter<String, File> Lfiles = new ParamsWrapter<String, File>(files);
			final ParamsWrapter<String, String> Lparams = new ParamsWrapter<String, String>(params);
			final ParamsWrapter<String, String> Lheaders = new ParamsWrapter<String, String>(headers);
			boolean intercept = uploadFileIntercept(Lurl, Lfiles, Lparams, Lheaders, new ExcuteRequest() {
				@Override
				public void request() {
					if (null != Lfiles.params) {
						try {
							Request request = FilesUpload(Lurl.url, Lfiles.params, Lparams.params, Lheaders.params);
							callBack(call, request);
						} catch (Exception e) {
							e.printStackTrace();
							callBackToUI(call, null, ErrorType.ERROR_METHOD_EXCEPTION);
						}
					}
				}

				@Override
				public boolean needAsyn() {
					return asyn;
				}

				@Override
				public void cancle() {
					callBackToUI(call, null, ErrorType.ERROR_INTERCEPTOR_CANCLE);
				}

			});
			if (intercept) {
				return;
			}
		}
		if (null != files) {
			Request request = FilesUpload(url, files, params, headers);
			callBack(call, request);
		}
	}

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
	@Override
	public <T> void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, final Class<T> cls, final RequestCallBack<T> call) throws Exception {
		if (isHasRequestIntercept()) {
			final UrlWrapter Lurl = new UrlWrapter(url);
			final ParamsWrapter<String, File> Lfiles = new ParamsWrapter<String, File>(files);
			final ParamsWrapter<String, String> Lparams = new ParamsWrapter<String, String>(params);
			final ParamsWrapter<String, String> Lheaders = new ParamsWrapter<String, String>(headers);
			boolean intercept = uploadFileIntercept(Lurl, Lfiles, Lparams, Lheaders, new ExcuteRequest() {
				@Override
				public void request() {
					try {
						Request request = FilesUpload(Lurl.url, Lfiles.params, Lparams.params, Lheaders.params);
						TcallBack(cls, call, request);
					} catch (Exception e) {
						e.printStackTrace();
						callBackToUI(call, null, ErrorType.ERROR_METHOD_EXCEPTION);
					}
				}

				@Override
				public boolean needAsyn() {
					return asyn;
				}

				@Override
				public void cancle() {
					callBackToUI(call, null, ErrorType.ERROR_INTERCEPTOR_CANCLE);
				}

			});
			if (intercept) {
				return;
			}
		}
		Request request = FilesUpload(url, files, params, headers);
		TcallBack(cls, call, request);
	}

	static String checkAndProccessUrl(String url) {
		if (url.contains("#")) {
			return url.replaceAll("#", "%23");
		}
		return url;
	}

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
	@Override
	public void downLoadFile(final String url, Map<String, String> params, Map<String, String> headers,
			final String filep, final RequestCallBack<File> call) throws Exception {
		String murl = checkAndProccessUrl(url);

		if (isHasRequestIntercept()) {
			final UrlWrapter Lurl = new UrlWrapter(murl);
			final ParamsWrapter<String, String> Lparams = new ParamsWrapter<String, String>(params);
			final ParamsWrapter<String, String> Lheaders = new ParamsWrapter<String, String>(headers);
			boolean intercept = downLoadFileIntercept(Lurl, Lparams, Lheaders, filep, new ExcuteRequest() {
				@Override
				public void request() {
					try {
						Request request = iterGet(Lurl.url, Lparams.params, Lheaders.params);
						Call calls = mOkHttpClient.newCall(request);
						if (asyn) {
							calls.enqueue(new Callback() {
								@Override
								public void onResponse(Call arg0, Response response) throws IOException {
									dealWithFileResponse(call, filep, response, url);
								}

								@Override
								public void onFailure(Call arg0, IOException arg1) {
									callBackToUI(call, null, ErrorType.ERROR_CONECTION);
								}
							});
						} else {
							dealWithFileResponse(call, filep, calls.execute(), url);
						}
					} catch (Exception e) {
						e.printStackTrace();
						callBackToUI(call, null, ErrorType.ERROR_METHOD_EXCEPTION);
					}
				}

				@Override
				public boolean needAsyn() {
					return asyn;
				}

				@Override
				public void cancle() {
					callBackToUI(call, null, ErrorType.ERROR_INTERCEPTOR_CANCLE);
				}
			});
			if (intercept) {
				return;
			}
		}

		Request request = iterGet(murl, params, headers);
		Call calls = mOkHttpClient.newCall(request);
		if (asyn) {
			calls.enqueue(new Callback() {
				@Override
				public void onResponse(Call arg0, Response response) throws IOException {
					dealWithFileResponse(call, filep, response, url);
				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {
					callBackToUI(call, null, ErrorType.ERROR_CONECTION);
				}
			});
		} else {
			dealWithFileResponse(call, filep, calls.execute(), url);
		}
	}

	/**
	 * 处理下载返回结果
	 * @time 2017年9月7日 上午9:48:01
	 * @param call
	 * @param filep
	 * @param response
	 * @param url
	 */
	private void dealWithFileResponse(RequestCallBack<File> call, String filep, Response response, String url) {
		String filepath = null;
		int code = response.code();
		if (code != 200) {
			callBackToUI(call, null, code);
			return;
		}
		if (filep == null) {
			filepath = new File(fileDir, System.currentTimeMillis() + "").getAbsolutePath();
		} else {
			filepath = filep;
		}
		long length = -1, progress = 0;
		String ContentLength = response.header("Content-Length");
		if (!TextUtils.isEmpty(ContentLength)) {
			length = Long.parseLong(ContentLength);
		}
		InputStream is = null;
		byte[] buf = new byte[2048];
		int len = 0;
		FileOutputStream fos = null;
		File file = null;
		try {
			is = response.body().byteStream();
			file = new File(filepath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			while ((len = is.read(buf)) != -1) {
				fos.write(buf, 0, len);
				// 进度条
				if (null != mDownLoadProgressListener) {
					progress += len;
					mDownLoadProgressListener.onProgress(url, length, progress, false);
				}
			}
			if (null != mDownLoadProgressListener) {
				mDownLoadProgressListener.onProgress(url, length, progress, true);
			}
			fos.flush();
			callBackToUI(call, file, null);
		} catch (IOException e) {
			if (file != null && file.exists()) {
				file.delete();
			}
			callBackToUI(call, null, ErrorType.ERROR_PROCESS_RESPONSE);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 遍历得到get方法的url,参数，头信息
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 */
	private Request iterGet(String url, Map<String, String> params, Map<String, String> headers) {
		okhttp3.Request.Builder builder = new Request.Builder();
		StringBuffer sb = null;

		if (null != params) {
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				String value = params.get(key);
				if (sb == null) {
					sb = new StringBuffer();
					if (url.contains("?")) {
						sb.append("&");
					} else {
						sb.append("?");
					}
				} else {
					sb.append("&");
				}
				sb.append(key);
				sb.append("=");
				try {
					sb.append(URLEncoder.encode(value, "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			builder.url(url + sb.toString());
		} else {
			builder.url(url);
		}

		okhttp3.Request.Builder get = builder;
		iterHeader(headers, get);

		Request request = get.build();
		return request;
	}

	/**
	 * 遍历得到post方法的url,参数，头信息
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	private Request iterPost(String url, Map<String, String> params, Map<String, String> headers) {
		Builder builder = new FormBody.Builder();

		if (null != params) {
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				String value = params.get(key);
				// 封装参数
				builder.add(key, value);
			}
		}

		FormBody formBody = builder.build();
		okhttp3.Request.Builder post = new Request.Builder().url(url).post(formBody);

		iterHeader(headers, post);

		return post.build();
	}

	/**
	 * 请求后返回的参数:String类型
	 * 
	 * @param call
	 * @throws IOException 
	 */
	public void callBack(final RequestCallBack<String> call, final Request request) throws IOException {
		Call calls = mOkHttpClient.newCall(request);
		if (asyn) {
			calls.enqueue(new Callback() {
				@Override
				public void onResponse(Call arg0, Response response) throws IOException {
					dealWithStringResponse(call, response);
				}

				@Override
				public void onFailure(Call arg0, IOException e) {
					e.printStackTrace();
					callBackToUI(call, null, ErrorType.ERROR_CONECTION);
				}
			});
		} else {
			dealWithStringResponse(call, calls.execute());
		}
	}

	/**
	 * 处理网络的文字返回结果
	 * @time 2017年9月7日 上午9:52:58
	 * @param call
	 * @param response
	 */
	private void dealWithStringResponse(RequestCallBack<String> call, Response response) {
		try {
			String str = response.body().string();
			callBackToUI(call, str, null);
		} catch (Exception e) {
			e.printStackTrace();
			callBackToUI(call, null, ErrorType.ERROR_PROCESS_RESPONSE);
		}
	}

	/**
	 * 请求后返回的参数:json格式
	 * 
	 * @param cls
	 * @param call
	 * @throws IOException 
	 */
	private <T> void TcallBack(final Class<T> cls, final RequestCallBack<T> call, final Request request)
			throws IOException {
		Call calls = mOkHttpClient.newCall(request);
		if (asyn) {
			calls.enqueue(new Callback() {
				@Override
				public void onResponse(Call arg0, Response response) throws IOException {
					dealWithJsonResponse(call, cls, response);
				}

				@Override
				public void onFailure(Call arg0, IOException e) {
					e.printStackTrace();
					callBackToUI(call, null, ErrorType.ERROR_CONECTION);
				}
			});
		} else {
			dealWithJsonResponse(call, cls, calls.execute());
		}
	}

	/**
	 * 处理JSON返回结果
	 * @time 2017年9月7日 上午9:56:47
	 * @param call
	 * @param cls
	 * @param response
	 */
	private <T> void dealWithJsonResponse(RequestCallBack<T> call, Class<T> cls, Response response) {
		try {
			String json = response.body().string();
			Gson gson = GsonUtils.build();
			T t = gson.fromJson(json, cls);
			callBackToUI(call, t, null);
		} catch (Exception e) {
			e.printStackTrace();
			callBackToUI(call, null, ErrorType.ERROR_PROCESS_RESPONSE);
		}
	}

	/**
	 * 请求后返回的参数:返回的是文件数据
	 */
	private void fileCallBack(final RequestCallBack<File> call, final String url, final Request request) {
		Call calls = mOkHttpClient.newCall(request);
		calls.enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response response) throws IOException {
				dealWithFileResponse(call, null, response, url);
			}

			@Override
			public void onFailure(Call arg0, IOException e) {
				e.printStackTrace();
				callBackToUI(call, null, ErrorType.ERROR_CONECTION);
			}
		});
	}

	/**
	 * 遍历头信息参数
	 * 
	 * @param headers
	 * @param get
	 */
	private void iterHeader(Map<String, String> headers, okhttp3.Request.Builder get) {
		if (null != headers) {
			Set<String> headersKeySet = headers.keySet();
			for (String key : headersKeySet) {
				String value = headers.get(key);
				get.addHeader(key, value);
			}
		}
	}

	/**
	 * 单个或多个文件上传
	 * 
	 * @param url
	 * @param files
	 * @param params
	 * @param headers
	 */
	private Request FilesUpload(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers) {
		Set<String> keySet = files.keySet();
		RequestBody requestBody = null;
		RequestBody fileBody = null;
		okhttp3.Request.Builder get = new Request.Builder();
		MultipartBody.Builder builder = new MultipartBody.Builder();

		for (String key : keySet) {
			File file = files.get(key);
			if (null != file) {
				fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
				builder.addFormDataPart(key, file.getName(), fileBody);
			}
		}
		// 头信息
		iterHeader(headers, get);
		// 参数
		if (params != null) {
			Set<String> keySet2 = params.keySet();
			for (String key2 : keySet2) {
				String value2 = params.get(key2);
				builder.addFormDataPart(key2, value2);
			}
		}
		requestBody = builder.build();
		return get.post(requestBody).url(url).build();
	}

	/**
	 * 下载进度条
	 * @author mr-lao
	 *
	 */
	public interface DownLoadProgressListener {
		/**
		 * 
		 * @time 2017年9月8日 下午4:51:52
		 * @param length     文件长度
		 * @param progress   进度
		 * @param finish     是否结束
		 */
		public void onProgress(String url, long length, long progress, boolean finish);
	}

	/**下载进度条监听器*/
	private DownLoadProgressListener mDownLoadProgressListener;

	public void setmDownLoadProgressListener(DownLoadProgressListener mDownLoadProgressListener) {
		this.mDownLoadProgressListener = mDownLoadProgressListener;
	}

	/**全局的请求拦截器*/
	public static RequestInterceptor globleRequestInterceptor = null;
	/**拦截器列表*/
	private List<RequestInterceptor> requestInterceptorList = null;

	{
		if (null != globleRequestInterceptor) {
			requestInterceptorList = new ArrayList<RequestInterceptor>();
			requestInterceptorList.add(globleRequestInterceptor);
		}
	}

	/**
	 * 排序
	 * @time 2017年9月14日 下午4:56:49
	 */
	private void sortRequestInterceptorList() {
		if (requestInterceptorList.size() < 2) {
			return;
		}
		RequestInterceptor tempArray[] = new RequestInterceptor[requestInterceptorList.size()];
		for (int i = 0; i < tempArray.length; i++) {
			tempArray[i] = requestInterceptorList.get(i);
		}
		// 排序
		for (int i = 0; i < tempArray.length; i++) {
			int iLevel = tempArray[i].getLevel();
			for (int j = i + 1; j < tempArray.length; j++) {
				RequestInterceptor temp = tempArray[j];
				int jLevel = temp.getLevel();
				if (iLevel < jLevel) {
					// 交换
					tempArray[j] = tempArray[i];
					tempArray[i] = temp;
				}
			}
		}
		requestInterceptorList.clear();
		for (int i = 0; i < tempArray.length; i++) {
			requestInterceptorList.add(tempArray[i]);
		}
	}

	/**
	 * 添加拦截器
	 * @time 2017年9月14日 下午4:44:57
	 * @param requestInterceptor
	 */
	public void addRequestInterceptor(RequestInterceptor requestInterceptor) {
		if (null == requestInterceptor) {
			requestInterceptorList = new ArrayList<RequestInterceptor>();
		}
		requestInterceptorList.add(requestInterceptor);
		sortRequestInterceptorList();
	}

	/**
	 * 设置拦截器队列
	 * @time 2017年9月14日 下午4:42:55
	 * @param requestInterceptList
	 */
	public void setRequestInterceptorList(List<RequestInterceptor> requestInterceptList) {
		if (requestInterceptList != null) {
			this.requestInterceptorList = requestInterceptList;
			if (null != globleRequestInterceptor) {
				this.requestInterceptorList.add(globleRequestInterceptor);
			}
			sortRequestInterceptorList();
		}
	}

	/**
	 * 判断是否拥有拦截器
	 * @time 2017年9月14日 下午3:29:52
	 * @return
	 */
	boolean isHasRequestIntercept() {
		if (null == requestInterceptorList || requestInterceptorList.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 请求拦截
	 * @time 2017年9月14日 下午5:49:35
	 * @param url
	 * @param params
	 * @param headers
	 * @param method
	 * @param excute
	 * @return
	 * @throws Exception
	 */
	boolean requestIntercept(UrlWrapter url, ParamsWrapter<String, String> params,
			ParamsWrapter<String, String> headers, RequestMethod method, ExcuteRequest excute) throws Exception {
		if (null != requestInterceptorList && !requestInterceptorList.isEmpty()) {
			for (RequestInterceptor requestInterceptor : requestInterceptorList) {
				if (requestInterceptor.request(url, params, headers, method, excute)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 上传拦截
	 * @time 2017年9月14日 下午5:49:25
	 * @param url
	 * @param file
	 * @param fileparam
	 * @param headers
	 * @param excute
	 * @return
	 * @throws Exception
	 */
	boolean uploadFileIntercept(UrlWrapter url, File file, String fileparam, ParamsWrapter<String, String> headers,
			ExcuteRequest excute) throws Exception {
		if (null != requestInterceptorList && !requestInterceptorList.isEmpty()) {
			for (RequestInterceptor requestInterceptor : requestInterceptorList) {
				if (requestInterceptor.uploadFile(url, file, fileparam, headers, excute)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 上传拦截
	 * @time 2017年9月14日 下午5:49:15
	 * @param url
	 * @param files
	 * @param params
	 * @param headers
	 * @param excute
	 * @return
	 * @throws Exception
	 */
	boolean uploadFileIntercept(UrlWrapter url, ParamsWrapter<String, File> files,
			ParamsWrapter<String, String> params, ParamsWrapter<String, String> headers, ExcuteRequest excute)
			throws Exception {
		if (null != requestInterceptorList && !requestInterceptorList.isEmpty()) {
			for (RequestInterceptor requestInterceptor : requestInterceptorList) {
				if (requestInterceptor.uploadFile(url, files, params, headers, excute)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 下载拦截
	 * @time 2017年9月14日 下午5:49:08
	 * @param url
	 * @param params
	 * @param headers
	 * @param filepath
	 * @param excute
	 * @return
	 * @throws Exception
	 */
	boolean downLoadFileIntercept(UrlWrapter url, ParamsWrapter<String, String> params,
			ParamsWrapter<String, String> headers, String filepath, ExcuteRequest excute) throws Exception {
		if (null != requestInterceptorList && !requestInterceptorList.isEmpty()) {
			for (RequestInterceptor requestInterceptor : requestInterceptorList) {
				if (requestInterceptor.downLoadFile(url, params, headers, filepath, excute)) {
					return true;
				}
			}
		}
		return false;
	}

	/**全局的拦截*/
	public static ResponseInterceptor globalResponseInterceptor;
}
