package com.winway.android.ewidgets.net.filter;

/**
 * 接口结果拦截器（只针对字符串返回结果生效）
* @author winway-laohw
* @time 2017年10月11日 下午5:37:23
*/
public interface ResponseInterceptor {

	/**
	 * 拦截泛型结果
	 * @time 2017年10月11日 下午5:42:44
	 * @param obj
	 * @return
	 */
	boolean interceptResult(Object result);
}
