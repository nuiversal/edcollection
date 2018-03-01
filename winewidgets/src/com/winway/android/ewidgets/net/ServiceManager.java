package com.winway.android.ewidgets.net;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.ewidgets.net.filter.RequestInterceptor;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.impl.BaseServiceImpl;
import com.winway.android.ewidgets.net.service.impl.DataPackageServiceImpl;
import com.winway.android.ewidgets.net.service.impl.LineServiceImpl;
import com.winway.android.ewidgets.net.service.impl.LineServiceImpl.DownLoadProgressListener;
import com.winway.android.util.ListUtil;

/**
* @author winway-laohw
* @time 2017年9月19日 上午9:11:55
*/
public class ServiceManager {
	private final static ServiceManager manager = new ServiceManager();

	private ServiceManager() {
	}

	public static class ServiceConfig {
		public static class LineServiceConfig {
			public boolean asyn = true;
			public String fileDir;
			public RequestInterceptor globalInterceptor;
			public List<RequestInterceptor> interceptorList;
			public DownLoadProgressListener progressListener;
		}

		private LineServiceConfig lineServiceConfig;
		private List<BaseService> serviceList;

		public void setLineServiceConfig(LineServiceConfig lineServiceConfig) {
			this.lineServiceConfig = lineServiceConfig;
			if (serviceList != null && !serviceList.isEmpty() && lineServiceConfig != null) {
				for (BaseService service : serviceList) {
					if (service instanceof LineServiceImpl) {
						((LineServiceImpl) service).setAsyn(lineServiceConfig.asyn);
						((LineServiceImpl) service).setFileDir(lineServiceConfig.fileDir);
						((LineServiceImpl) service).setmDownLoadProgressListener(lineServiceConfig.progressListener);
						((LineServiceImpl) service).setRequestInterceptorList(lineServiceConfig.interceptorList);
						LineServiceImpl.globleRequestInterceptor = lineServiceConfig.globalInterceptor;
					}
				}
			}
		}

		public void setServiceList(List<BaseService> serviceList) {
			this.serviceList = serviceList;
			setLineServiceConfig(this.lineServiceConfig);
		}

		public boolean dinamicService;
		public BaseService defaultService;
	}

	public static final String Order_Datapackage_Line = "Order_Datapackage_Line";
	public static final String Order_Line_Datapackage = "Order_Line_Datapackage";
	public static final String Order_Auto_Create = "Order_Auto_Create";

	public static ServiceConfig createServiceConfig(String order, boolean dinamicService) {
		ServiceConfig config = new ServiceConfig();
		// 服务
		DataPackageServiceImpl dataPackageServiceImpl = new DataPackageServiceImpl();
		LineServiceImpl lineServiceImpl = new LineServiceImpl();

		ArrayList<BaseService> serviceList = new ArrayList<BaseService>();
		if (Order_Datapackage_Line.equals(order)) {
			serviceList.add(dataPackageServiceImpl);
			serviceList.add(lineServiceImpl);
			config.defaultService = dataPackageServiceImpl;
		} else if (Order_Line_Datapackage.equals(order)) {
			serviceList.add(lineServiceImpl);
			serviceList.add(dataPackageServiceImpl);
			config.defaultService = lineServiceImpl;
		}
		config.setServiceList(serviceList);
		config.dinamicService = dinamicService;
		return config;
	}

	private static BaseServiceImpl createBaseServiceImpl(boolean dinamicService, BaseService defaultService,
			ArrayList<BaseService> serviceList) {
		BaseServiceImpl impl = new BaseServiceImpl();
		impl.setDinamicService(dinamicService);
		impl.setDefaultService(defaultService);
		impl.setServiceList(serviceList);
		return impl;
	}

	public static BaseService createService(ServiceConfig config) {
		ArrayList<BaseService> serviceList = null;
		if (config.serviceList instanceof ArrayList) {
			serviceList = (ArrayList<BaseService>) config.serviceList;
		} else {
			serviceList = new ArrayList<BaseService>();
			ListUtil.copyList(serviceList, config.serviceList);
		}
		return createBaseServiceImpl(config.dinamicService, config.defaultService, serviceList);
	}

	public static BaseService createService(String order, boolean dinamicService) {
		return createService(createServiceConfig(order, dinamicService));
	}

	public static BaseService createService(String order, boolean dinamicService, boolean asyn) {
		ServiceConfig config = ServiceManager.createServiceConfig(ServiceManager.Order_Datapackage_Line, false);
		ServiceConfig.LineServiceConfig lineServiceConfig = new ServiceConfig.LineServiceConfig();
		lineServiceConfig.asyn = asyn;
		config.setLineServiceConfig(lineServiceConfig);
		return createService(config);
	}
}
