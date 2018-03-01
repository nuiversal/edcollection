package com.winway.android.ewidgets.net.service;

import java.util.Map;

/**
 * 数据包加载器，在使用数据包服务的时候使用
 * 数据包加载器是加载数据包中的数据，并组装成符合接口要示的数据
 * @author mr-lao
 * @date 2016-12-18
 *
 */
public interface DataPackageLoader extends DataPackageService {
	/**
	 * 数据包接口实现类调用数据加载器的canProcessThis方法，根据返回值判断此数据加载器是否可以处理URL这个请求
	 * @param url
	 * @param params
	 * @param heades
	 * @return  true或者false
	 */
	public boolean canProcessThis(String url, Map<String, String> params,
			Map<String, String> heades);
}
