package com.winway.android.ewidgets.net.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winway.android.ewidgets.net.service.DataPackageLoader;
import com.winway.android.ewidgets.net.service.DataPackageService;
import com.winway.android.util.ListUtil;
import android.annotation.SuppressLint;
import android.text.TextUtils;

/**
 * 离线数据加载的接口
 * 
 * 一般来说，客户端通过接口（URL）向服务器请求数据，但这一切都是建立于网络链接的条件下的，如果，网络链接不可用的时候，就不能向服务器发送URL请求了，
 * 客户端就无法获得想要的数据了。 我们都知道，客户端向服务器发送请求的最终目的是为了获取数据。如果，我们早已经将服务器上的数据都下载到服务端之中了呢？那么，
 * 客户端就无须向服务器发送请求了，也能获得客户端想到的数据。
 * 然而，这里有一个问题，就是，客户端拥有服务器的数据，但是，客户端又如何从这些数据之中提取中和服务器请求URL相同的数据呢？这是个问题！因此，客户端仅仅有了服务器的数据是不能组装成
 * URL对应的目标数据的，还得需要对数据处理的逻辑程序，客户端才能从数据包中获得它想要的URL对应的数据。
 * 
 * 此接口的设计，重点在于数据加载器<code>DataPackageLoader</code>
 * ，数据加载器的作用是根据URL将从服务器下载到的数据提取出并组装成URL的目标数据，做到和服务器一样的 效果。
 * 
 * @author mr-lao
 * @date 2016-12-28
 *
 */
public class DataPackageServiceImpl implements DataPackageService {
	public static final List<DataPackageLoader> systemLoaders = new ArrayList<>();

	static {
		// systemLoaders.add(new ExampleLoaderImpl());
	}

	public static void addSystemDataPackageLoader(DataPackageLoader loader) {
		systemLoaders.add(loader);
	}

	public DataPackageServiceImpl() {
		loaderList = new ArrayList<>();
		ListUtil.copyList(loaderList, systemLoaders);
	}

	/** 包数据加载器 */
	private List<DataPackageLoader> loaderList = null;

	public List<DataPackageLoader> getLoaderList() {
		return loaderList;
	}

	public void setLoaderList(List<DataPackageLoader> loaderList) {
		ListUtil.copyList(this.loaderList, loaderList);
	}

	public void addLoader(DataPackageLoader loader) {
		loaderList.add(loader);
	}

	private HashMap<String, DataPackageLoader> loaderMap = new HashMap<>();

	private static String getKey(String url, Map<String, String> params,
			Map<String, String> headers) {
		String key = "" + url;
		if (null != params) {
			Set<String> paramsKeySet = params.keySet();
			for (String k : paramsKeySet) {
				url += k;
			}
		}
		if (null != headers) {
			Set<String> headersKeySet = headers.keySet();
			for (String k : headersKeySet) {
				url += k;
			}
		}
		return key;
	}

	DataPackageLoader getLoader(String url, Map<String, String> params,
			Map<String, String> headers) {
		String key = getKey(url, params, headers);
		if (loaderMap.containsKey(key)) {
			return loaderMap.get(key);
		}
		if (null == loaderList || loaderList.isEmpty()) {
			return null;
		}
		for (DataPackageLoader loader : loaderList) {
			if (loader.canProcessThis(url, params, headers)) {
				loaderMap.put(key, loader);
				return loader;
			}
		}
		return null;
	}

	/**
	 * 处理所有的参数对象，保证所有的请求参数对象不为空，并将URL中的参数封装到params中，以及将params中的参数KEY全部设置成小写
	 * @param params
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	private Map<String, String> processParams(Map<String, String> p, String url) {
		HashMap<String, String> params = new HashMap<>();
		if (null != p) {
			// 将params中的key设置成小写
			Set<String> keySet = p.keySet();
			if (keySet != null) {
				for (String key : keySet) {
					params.put(key.toLowerCase(), p.get(key));
				}
			}
		}
		// 处理URL中的参数
		if (!TextUtils.isEmpty(url)) {
			int start = url.indexOf("?");
			if (start >= 0) {
				String substring = url.substring(start + 1, url.length());
				if (!TextUtils.isEmpty(substring)) {
					String[] split = substring.split("&");
					if (null != split) {
						for (String string : split) {
							String ps[] = string.split("=", 2);
							if (null != ps && ps.length >= 2) {
								String key = ps[0].toLowerCase();
								if (key != null) {
									key = key.toLowerCase();
								}
								params.put(ps[0].toLowerCase(), ps[1]);
							}
						}
					}
				}
			}
		}
		return params;
	}

	@Override
	public void getRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		params = processParams(params, url);
		DataPackageLoader loader = getLoader(url, params, headers);
		if (null == loader) {
			call.error(-1,WayFrom.DATA_PACKAGE);
		} else {
			loader.getRequestString(url, params, headers, call);
		}
	}

	@Override
	public void postRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		params = processParams(params, url);
		DataPackageLoader loader = getLoader(url, params, headers);
		if (null == loader) {
			call.error(-1,WayFrom.DATA_PACKAGE);
		} else {
			loader.postRequestString(url, params, headers, call);
		}
	}

	@Override
	public void requestString(String url, Map<String, String> params, RequestMethod method,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		params = processParams(params, url);
		DataPackageLoader loader = getLoader(url, params, headers);
		if (null == loader) {
			call.error(-1,WayFrom.DATA_PACKAGE);
		} else {
			loader.requestString(url, params, method, headers, call);
		}
	}

	@Override
	public void getRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		params = processParams(params, url);
		DataPackageLoader loader = getLoader(url, params, headers);
		if (null == loader) {
			call.error(-1,WayFrom.DATA_PACKAGE);
		} else {
			loader.getRequestFile(url, params, headers, call);
		}
	}

	@Override
	public void postRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		params = processParams(params, url);
		DataPackageLoader loader = getLoader(url, params, headers);
		if (null == loader) {
			call.error(-1,WayFrom.DATA_PACKAGE);
		} else {
			loader.postRequestFile(url, params, headers, call);
		}
	}

	@Override
	public void requestFile(String url, Map<String, String> params, RequestMethod method,
			Map<String, String> headers, RequestCallBack<File> call) throws Exception {
		params = processParams(params, url);
		DataPackageLoader loader = getLoader(url, params, headers);
		if (null == loader) {
			call.error(-1,WayFrom.DATA_PACKAGE);
		} else {
			loader.requestFile(url, params, method, headers, call);
		}
	}

	@Override
	public <T> void getRequest(String url, Map<String, String> params, Map<String, String> headers,
			Class<T> cls, RequestCallBack<T> call) throws Exception {
		params = processParams(params, url);
		DataPackageLoader loader = getLoader(url, params, headers);
		if (null == loader) {
			call.error(-1,WayFrom.DATA_PACKAGE);
		} else {
			loader.getRequest(url, params, headers, cls, call);
		}
	}

	@Override
	public <T> void postRequest(String url, Map<String, String> params, Map<String, String> headers,
			Class<T> cls, RequestCallBack<T> call) throws Exception {
		params = processParams(params, url);
		DataPackageLoader loader = getLoader(url, params, headers);
		if (null == loader) {
			call.error(-1,WayFrom.DATA_PACKAGE);
		} else {
			loader.postRequest(url, params, headers, cls, call);
		}
	}

	@Override
	public <T> void request(String url, Map<String, String> params, Map<String, String> headers,
			Class<T> cls, RequestMethod method, RequestCallBack<T> call) throws Exception {
		params = processParams(params, url);
		DataPackageLoader loader = getLoader(url, params, headers);
		if (null == loader) {
			call.error(-1,WayFrom.DATA_PACKAGE);
		} else {
			loader.request(url, params, headers, cls, method, call);
		}
	}

	@Override
	public void uploadFile(String url, File file, String fileparam, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception {
		call.equals(-1);
	}

	@Override
	public void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call) throws Exception {
		call.error(-1,WayFrom.DATA_PACKAGE);
	}

	@Override
	public <T> void uploadFile(String url, Map<String, File> files, Map<String, String> params,
			Map<String, String> headers, Class<T> cls, RequestCallBack<T> call) throws Exception {
		call.error(-1,WayFrom.DATA_PACKAGE);
	}

	@Override
	public void downLoadFile(String url, Map<String, String> params, Map<String, String> headers,
			String filepath, RequestCallBack<File> call) throws Exception {
		params = processParams(params, url);
		DataPackageLoader loader = getLoader(url, params, headers);
		if (null == loader) {
			call.error(-1,WayFrom.DATA_PACKAGE);
		} else {
			loader.downLoadFile(url, params, headers, filepath, call);
		}
	}

}
