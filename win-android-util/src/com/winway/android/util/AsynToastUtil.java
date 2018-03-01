package com.winway.android.util;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

/**
 * 异步Toast工具类
 * @author mr-lao
 *
 */
public class AsynToastUtil {
	private Application app;
	private static AsynToastUtil instance;

	private AsynToastUtil(Application app) {
		super();
		this.app = app;
	}

	/**
	 * 初始化方法，建议在Application.onCreate()方法中执行
	 * @param app
	 */
	public static void init(Application app) {
		instance = new AsynToastUtil(app);
	}

	/**
	 * @deprecate  
	 * 因为此方法在一些情况下会出现异常，为了保证程序的健壮性，因此方法已经不推荐使用，请使用AsynToastUtil getInstance()代替
	 * @param app
	 * @return
	 */
	public static AsynToastUtil getInstance(Application app) {
		if (null == instance) {
			synchronized (AsynToastUtil.class) {
				if (null == instance) {
					instance = new AsynToastUtil(app);
				}
			}
		}
		return instance;
	}

	public static AsynToastUtil getInstance() {
		if (null == instance) {
			throw new RuntimeException("请执行init()方法初始化");
		}
		return instance;
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) msg.obj;
			String text = (String) map.get("text");
			int duration = (int) map.get("duration");
			mainShowToast(text, duration);
		}
	};

	public boolean isInMainThread() {
		return Looper.myLooper() == Looper.getMainLooper();
	}

	private void mainShowToast(String text, int duration) {
		Toast.makeText(app, text, duration).show();
	}

	public void showToast(String text, int duration) {
		if (isInMainThread()) {
			mainShowToast(text, duration);
		} else {
			Message msg = Message.obtain();
			HashMap<String, Object> map = new HashMap<>();
			map.put("text", text);
			map.put("duration", duration);
			msg.obj = map;
			handler.sendMessage(msg);
		}
	}

	public void showToast(String text) {
		showToast(text, Toast.LENGTH_SHORT);
	}

	public void showShortToast(String text) {
		showToast(text, Toast.LENGTH_SHORT);
	}

	public void showLongToast(String text) {
		showToast(text, Toast.LENGTH_LONG);
	}

}
