package com.winway.android.ewidgets.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.DataPackageLoader;
import com.winway.android.ewidgets.net.service.LocalService;
import com.winway.android.ewidgets.net.service.impl.BaseServiceImpl;
import com.winway.android.ewidgets.net.service.impl.DataPackageServiceImpl;
import com.winway.android.ewidgets.net.service.impl.LineServiceImpl;
import com.winway.android.ewidgets.net.service.impl.LocalServiceImpl;
import com.winway.android.ewidgets.net.util.NetUtil;
import com.winway.android.util.FileUtil;

import android.content.Context;

/**
 * 网络请求管理器
 * 
 * @author mr-lao
 *
 */
@Deprecated
public class NetManager {
	private static String fileDir = null;

	static {
		String path = FileUtil.getInnerSDCardDir().getAbsolutePath() + "/patrol/net/files_dir/dd";
		try {
			fileDir = FileUtil.getFile(path).getParentFile().getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static BaseServiceImpl createBaseServiceImpl(ArrayList<BaseService> serviceList,
			BaseService defaultService, LocalService localService, boolean dinamicService) {
		BaseServiceImpl impl = new BaseServiceImpl();
		impl.setServiceList(serviceList);
		impl.setDefaultService(defaultService);
		impl.setLocalService(localService);
		impl.setDinamicService(dinamicService);
		return impl;
	}

	/**
	 * 在线、本地、数据包
	 */
	public static final String ODER_LINE_LOCAL_DATA = "ODER_LINE_LOCAL_DATA";
	/**
	 * 本地、在线、数据包
	 */
	public static final String ODER_LOCAL_LINE_DATA = "ODER_LOCAL_LINE_DATA";
	/**
	 * 本地、数据包、在线
	 */
	public static final String ODER_LOCAL_DATA_LINE = "ODER_LOCAL_DATA_LINE";
	/**
	 * 数据包、在线、本地
	 */
	public static final String ODER_DATA_LINE_LOCAL = "ODER_DATA_LINE_LOCAL";
	/**
	 * 数据包、本地、在线
	 */
	public static final String ODER_DATA_LOCAL_LINE = "ODER_DATA_LOCAL_LINE";
	/**
	 * 在线、数据包、本地
	 */
	public static final String ODER_LINE_DATA_LOCAL = "ODER_LINE_DATA_LOCAL";

	static ArrayList<BaseService> createServiceList(String orders, Context context) {
		LineServiceImpl line = new LineServiceImpl();
		line.setFileDir(fileDir);
		LocalServiceImpl local = new LocalServiceImpl(context);
		DataPackageServiceImpl data = new DataPackageServiceImpl();
		ArrayList<BaseService> list = new ArrayList<BaseService>();
		if (ODER_LINE_LOCAL_DATA.equals(orders)) {
			list.add(line);
			list.add(local);
			list.add(data);
		} else if (ODER_LOCAL_LINE_DATA.equals(orders)) {
			list.add(local);
			list.add(line);
			list.add(data);
		} else if (ODER_LOCAL_DATA_LINE.equals(orders)) {
			list.add(local);
			list.add(data);
			list.add(line);
		} else if (ODER_DATA_LINE_LOCAL.equals(orders)) {
			list.add(data);
			list.add(line);
			list.add(local);
		} else if (ODER_DATA_LOCAL_LINE.equals(orders)) {
			list.add(data);
			list.add(local);
			list.add(line);
		} else if (ODER_LINE_DATA_LOCAL.equals(orders)) {
			list.add(line);
			list.add(data);
			list.add(local);
		}
		return list;
	}

	/**
	 * 根据 line local data 优先进行创建服务对象
	 * 
	 * @param context
	 * @return
	 */
	public static BaseService getDefaultService(Context context) {
		ArrayList<BaseService> serviceList = createServiceList(ODER_LINE_LOCAL_DATA, context);
		return createBaseServiceImpl(serviceList, serviceList.get(0),
				(LocalServiceImpl) serviceList.get(1), true);
	}

	/**
	 * 根据有网络状态来创建服务对象
	 * 
	 * @param context
	 * @return
	 */
	public static BaseService getAutoService(Context context) {
		boolean connected = NetUtil.isNetworkConnected(context);
		if (connected) {
			ArrayList<BaseService> serviceList = createServiceList(ODER_LINE_LOCAL_DATA, context);
			return createBaseServiceImpl(serviceList, serviceList.get(0),
					(LocalServiceImpl) serviceList.get(1), true);
		} else {
			ArrayList<BaseService> serviceList = createServiceList(ODER_LOCAL_DATA_LINE, context);
			return createBaseServiceImpl(serviceList, serviceList.get(0), null, true);
		}
	}

	/**
	 * 获取加载图片的服务对象
	 * 
	 * @param context
	 * @return
	 */
	public static BaseService getImageLoderService(Context context, boolean onlin) {
		BaseServiceImpl impl = null;
		if (onlin) {
			ArrayList<BaseService> serviceList = createServiceList(ODER_LINE_LOCAL_DATA, context);
			impl = createBaseServiceImpl(serviceList, serviceList.get(0),
					(LocalServiceImpl) serviceList.get(1), false);
		} else {
			ArrayList<BaseService> serviceList = createServiceList(ODER_LOCAL_DATA_LINE, context);
			impl = createBaseServiceImpl(serviceList, serviceList.get(0),
					(LocalServiceImpl) serviceList.get(0), false);
		}
		impl.setDinamicService(false);
		return impl;
	}

	/**
	 * 获取策略服务对象
	 * @param context
	 * @param orders  策略算法
	 * @return
	 */
	public static BaseService getService(Context context, String orders, boolean dinamicService) {
		ArrayList<BaseService> serviceList = createServiceList(orders, context);
		if (orders.equals(ODER_LINE_DATA_LOCAL)) {
			return createBaseServiceImpl(serviceList, serviceList.get(0),
					(LocalService) serviceList.get(2), dinamicService);
		} else if (orders.equals(ODER_LINE_LOCAL_DATA)) {
			return createBaseServiceImpl(serviceList, serviceList.get(0),
					(LocalService) serviceList.get(1), dinamicService);
		} else {
			return createBaseServiceImpl(serviceList, serviceList.get(0), null, dinamicService);
		}
	}

	/**
	 * 获取在线网络请求服务
	 * 
	 * @return
	 */
	public static BaseService getLineService() {
		LineServiceImpl impl = new LineServiceImpl();
		impl.setFileDir(fileDir);
		return impl;
	}

	/**
	 * 获取本地网络请求服务
	 * 
	 * @param context
	 * @return
	 */
	public static BaseService getLocalService(Context context) {
		return new LocalServiceImpl(context);
	}

	/**
	 * 获得数据包网络请求服务
	 * 
	 * @param context
	 * @return
	 */
	public static BaseService getDataPackageService(Context context) {
		return new DataPackageServiceImpl();
	}

	/** 在线优先 */
	public static final int ONLINE_FIRST = 1;
	/** 数据包优先 */
	public static final int DATA_PACKET_FIRST = 2;

	/**
	 * 根据first的值获取相应的安卓树的数据服务加载
	 * 
	 * @param first
	 *            first的值为ONLINE_FIRST（在线优先）或者DATA_PACKET_FIRST（数据包优先）
	 * @return
	 */
	public static BaseService getTreeLoderService(int first) {
		BaseServiceImpl base = new BaseServiceImpl();
		ArrayList<BaseService> list = new ArrayList<>();
		LineServiceImpl line = new LineServiceImpl();
		DataPackageServiceImpl data = new DataPackageServiceImpl();
		if (first == ONLINE_FIRST) {
			list.add(line);
			list.add(data);
			base.setDefaultService(line);
		} else {
			list.add(data);
			list.add(line);
			base.setDefaultService(data);
		}
		base.setServiceList(list);
		base.setDinamicService(false);
		return base;
	}

	/**
	 * 往数据据服务里增加数据包加载器
	 * 
	 * @param base
	 * @param loader
	 */
	public static void addDataPackageLoader(BaseServiceImpl base, DataPackageLoader loader) {
		ArrayList<BaseService> serviceList = base.getServiceList();
		if (null == serviceList || serviceList.isEmpty()) {
			return;
		}
		for (BaseService baseService : serviceList) {
			if (baseService instanceof DataPackageServiceImpl) {
				((DataPackageServiceImpl) baseService).addLoader(loader);
			}
		}
	}

}
