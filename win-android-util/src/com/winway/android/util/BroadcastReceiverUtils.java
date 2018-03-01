package com.winway.android.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 广播接收者工具类
 * 
 * @author zgq
 *
 */
public class BroadcastReceiverUtils {

	private static BroadcastReceiverUtils instance;

	private BroadcastReceiverUtils() {
	}

	public static BroadcastReceiverUtils getInstance() {
		if (instance == null) {
			synchronized (BroadcastReceiverUtils.class) {
				if (instance == null) {
					instance = new BroadcastReceiverUtils();
				}
			}
		}
		return instance;
	}

	/**
	 * 注册广播
	 * 
	 * @param context
	 * @param flag
	 *            广播唯一标识
	 * @param bReceiver
	 */
	public void registReceiver(Context context, String flag, BroadcastReceiver bReceiver) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(flag);
		context.registerReceiver(bReceiver, filter);
	}

	/**
	 * 发送广播
	 * 
	 * @param context
	 * @param flag
	 *            广播唯一标识
	 */
	public void sendCommand(Context context, String flag) {
		Intent intent = new Intent();
		intent.setAction(flag);
		context.sendBroadcast(intent);
	}
}
