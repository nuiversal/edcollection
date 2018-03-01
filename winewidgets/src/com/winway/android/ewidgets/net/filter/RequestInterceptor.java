package com.winway.android.ewidgets.net.filter;

import java.io.File;

import com.winway.android.ewidgets.net.entity.ParamsWrapter;
import com.winway.android.ewidgets.net.entity.UrlWrapter;
import com.winway.android.ewidgets.net.service.BaseService.RequestMethod;

/**
* @author winway-laohw
* @time 2017年9月14日 下午2:27:28
* 请求拦截器，框架在执行HTTP请求之前，会首先执行拦截器。开发者可以使用拦截器，动态改变HTTP请求的URL、请求参数、请求头部，动态将HTTP请求停止等，
* 动态在执行HTTP请求之前执行一些操作。
* 使用例子：动态登陆
*/
public interface RequestInterceptor {

	/**
	 * 请求
	 * @time 2017年9月14日 下午2:50:08
	 * @param url
	 * @param params
	 * @param headers
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public boolean request(UrlWrapter url, ParamsWrapter<String, String> params, ParamsWrapter<String, String> headers,
			RequestMethod method, ExcuteRequest excute) throws Exception;

	/**
	 * 上传文件
	 * @time 2017年9月14日 下午2:50:15
	 * @param url
	 * @param file
	 * @param fileparam
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public boolean uploadFile(UrlWrapter url, File file, String fileparam, ParamsWrapter<String, String> headers,
			ExcuteRequest excute) throws Exception;

	/**
	 * 上传文件
	 * @time 2017年9月14日 下午2:50:22
	 * @param url
	 * @param files
	 * @param params
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public boolean uploadFile(UrlWrapter url, ParamsWrapter<String, File> files, ParamsWrapter<String, String> params,
			ParamsWrapter<String, String> headers, ExcuteRequest excute) throws Exception;

	/**
	 * 下载文件
	 * @time 2017年9月14日 下午2:50:30
	 * @param url
	 * @param params
	 * @param headers
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	public boolean downLoadFile(UrlWrapter url, ParamsWrapter<String, String> params,
			ParamsWrapter<String, String> headers, String filepath, ExcuteRequest excute) throws Exception;

	/**
	 * 执行HTTP请求
	 * @author mr-lao
	 *
	 */
	public interface ExcuteRequest {
		/**
		 * 执行请求
		 * @time 2017年9月14日 下午4:27:37
		 * @throws Exception
		 */
		public void request();

		/**
		 * 告知拦截器服务是否使用了同步，则方法的返回值目的是让拦截器能更好地处理线程引发的问题
		 * @time 2017年9月14日 下午4:28:02
		 * @return
		 */
		public boolean needAsyn();

		/**
		 * 取消
		 * @time 2017年9月14日 下午6:34:47
		 */
		public void cancle();
	}

	/**
	 * 拦截器的等级，等级越高，优先级就越高
	 * 比如，有两个拦截器A和B，A的等级是1，B的等级是2。当HTTP请求发生的时候，B拦截器会最先收到请求，如果B将请求拦截下来的话，则A就不会收到请求。
	 * @time 2017年9月14日 下午4:36:57
	 * @return
	 */
	public int getLevel();
}
