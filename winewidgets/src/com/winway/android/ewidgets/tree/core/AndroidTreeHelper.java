package com.winway.android.ewidgets.tree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.ewidgets.net.service.BaseService.WayFrom;
import com.winway.android.ewidgets.net.service.impl.BaseServiceImpl;
import com.winway.android.ewidgets.net.service.impl.DataPackageServiceImpl;
import com.winway.android.ewidgets.net.service.impl.LineServiceImpl;

/**
 * 安卓树辅助类
 * @author mr-lao
 * @date 2016-12-16
 */
public class AndroidTreeHelper {
	class LoaderConfig {
		public String url;
		public Map<String, String> params;
		public Map<String, String> headers;
		public Class<?> cls;
	}

	public HashMap<Integer, LoaderConfig> loaderConfigMap;
	private BaseServiceImpl net;

	void init() {
		net = new BaseServiceImpl();
		net.setDinamicService(false);
		ArrayList<BaseService> list = new ArrayList<>();
		list.add(new LineServiceImpl());
		list.add(new DataPackageServiceImpl());
		net.setServiceList(list);
	}

	Gson getGson() {
		return new Gson();
	}

	void load() throws Exception {
		final LoaderConfig config = loaderConfigMap.get(0);
		net.getRequestString(config.url, config.params, config.headers,
				new RequestCallBack<String>() {
					@Override
					public void error(int code,WayFrom from) {

					}

					@Override
					public void callBack(String request,WayFrom from) {
						Object fromJson = getGson().fromJson(request, config.cls);
					}
				});
	}
}
