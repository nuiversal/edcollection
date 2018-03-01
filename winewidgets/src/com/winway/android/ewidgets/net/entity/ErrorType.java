package com.winway.android.ewidgets.net.entity;

public class ErrorType {
	/**
	 * 请求HHTP连接时错误
	 */
	public static final int ERROR_CONECTION = -1;

	/**
	 * 处理返回的Response数据出错
	 */
	public static final int ERROR_PROCESS_RESPONSE = -2;

	/**
	 * 方法异常
	 */
	public static final int ERROR_METHOD_EXCEPTION = -3;

	/**
	 * 拦截器取消
	 */
	public static final int ERROR_INTERCEPTOR_CANCLE = -4;
}
