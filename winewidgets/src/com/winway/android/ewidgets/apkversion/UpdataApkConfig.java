package com.winway.android.ewidgets.apkversion;

import java.io.Serializable;

/**
 * 配置更新需要的一些参数
 * 
 * @author lyh
 *
 */
public class UpdataApkConfig implements Serializable{
	/** 当前apk版本号 */
	public static long localVerCode; 
	/** 当前apk版本名称 */
	public static String localVerName;
	/** 下载到本地要给这个apk存放的路径 */
	public static String localApkPath = "";
	/** 服务器更新包存放json的地址 如：http://192.168.43.133:8080/ver.json */
	public static String serverDataAddress = "";
	/** 服务器软件更新包存放apk的地址  如：http://192.168.43.133:8080/helloWorldDemo.apk*/
	public static String serverApkAddress = "";	
}