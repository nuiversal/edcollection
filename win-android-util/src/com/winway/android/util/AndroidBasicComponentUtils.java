package com.winway.android.util;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 安卓四大组件工具类
 * 
 * @author zgq
 *
 */
public class AndroidBasicComponentUtils {
	public static boolean addFlags = true;

	/**
	 * 开启activity
	 */
	public static void launchActivity(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		if (addFlags) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		}
		context.startActivity(intent);
	}

	/**
	 * 开启activity(带参数)
	 */
	public static void launchActivity(Context context, Class<?> activity, Intent intent) {
		intent.setClass(context, activity);
		context.startActivity(intent);
	}

	/**
	 * 开启activity(带参数)
	 */
	public static void launchActivity(Context context, Class<?> activity, Bundle bundle) {
		Intent intent = new Intent(context, activity);
		intent.putExtras(bundle);
		if (addFlags) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		}
		context.startActivity(intent);
	}

	/**
	 * 开启activity(带参数)
	 */
	public static void launchActivity(Context context, Class<?> activity, String key, int value) {
		Bundle bundle = new Bundle();
		bundle.putInt(key, value);
		launchActivity(context, activity, bundle);
	}

	public static void launchActivity(Context context, Class<?> activity, String key, String value) {
		Bundle bundle = new Bundle();
		bundle.putString(key, value);
		launchActivity(context, activity, bundle);
	}

	/**
	 * 开启activity(带参数)
	 */
	public static void launchActivity(Context context, Class<?> activity, String key, Serializable value) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(key, value);
		launchActivity(context, activity, bundle);
	}

	/**
	 * 开启activity(带参数)
	 */
	public static void launchActivity(Context context, Class<?> activity, String[] keys, Serializable[] values) {
		Bundle bundle = new Bundle();
		if (keys != null && values != null) {
			if (keys.length != values.length) {
				throw new RuntimeException("key.length != values.length");
			}
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i];
				Serializable value = values[i];
				bundle.putSerializable(key, value);
			}
		}
		launchActivity(context, activity, bundle);
	}

	public static void launchActivityForResult(Activity context, Class<?> activity, int requestCode) {
		Intent intent = new Intent(context, activity);
		if (addFlags) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		}
		context.startActivityForResult(intent, requestCode);
	}

	public static void launchActivityForResult(Activity activity, Intent intent, int requestCode) {
		activity.startActivityForResult(intent, requestCode);
	}

	public static void launchActivityForResult(Activity activity, Class<?> activityClass, String[] keys,
			String[] values, int requestCode) {
		Intent intent = new Intent(activity, activityClass);
		if (null != keys && null != values) {
			if (keys.length != values.length) {
				throw new RuntimeException("KEYS值和VALUES值数量不一致");
			}
			for (int i = 0; i < keys.length; i++) {
				intent.putExtra(keys[i], values[i]);
			}
		}
		activity.startActivityForResult(intent, requestCode);
	}

	/** 启动一个服务 */
	public static void launchService(Context context, Class<?> service) {
		Intent intent = new Intent(context, service);
		context.startService(intent);
	}

	public static void stopService(Context context, Class<?> service) {
		Intent intent = new Intent(context, service);
		context.stopService(intent);
	}
}
