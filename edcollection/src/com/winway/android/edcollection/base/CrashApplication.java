package com.winway.android.edcollection.base;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.winway.android.edcollection.base.util.InitOperate;
import com.winway.android.util.DateUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.PreferencesUtils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

/**
 * 处理全局异常
 * 
 * @author mr-lao
 *
 */

public class CrashApplication extends Application implements Thread.UncaughtExceptionHandler {
	private SimpleDateFormat sdf;
	public static Application application = null;
	// 线程池
	public static ExecutorService fixedThreadPool = null;

	@Override
	public void onCreate() {
		super.onCreate();
		// 设置Thread Exception Handler
		Thread.setDefaultUncaughtExceptionHandler(this);
		application = this;
		fixedThreadPool = Executors.newFixedThreadPool(5);

		// 初始化操作开始

		InitOperate.getInstance().createFolder();
		String dbUrl = FileUtil.AppRootPath + "/db/common/global.db";
		InitOperate.getInstance().createGlobalDb(getApplicationContext(), dbUrl);

		// 初始化服务器信息
		initServerInfo();
		// 初始化操作结束

	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();
		String logpath = FileUtil.AppRootPath + "/log/" + DateUtils.date2Str(new Date(), DateUtils.yyyymmddhhmmss)
				+ ".txt";

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logpath, true), "UTF-8"));
			if (null == sdf) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			writer.println(sdf.format(new Date()));
			ex.printStackTrace(writer);
			writer.println();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public void initServerInfo() {
		PreferencesUtils.PREFERENCE_NAME = "serverinfo&port";
		GlobalEntry.loginServerUrl = PreferencesUtils.getString(getApplicationContext(), "loginServerAddr",
				GlobalEntry.loginServerUrl);
		GlobalEntry.dataServerUrl = PreferencesUtils.getString(getApplicationContext(), "dataServerAddr",
				GlobalEntry.dataServerUrl);
		GlobalEntry.fileServerUrl = PreferencesUtils.getString(getApplicationContext(), "fileConnectServerAddr",
				GlobalEntry.fileServerUrl);
	}
}
