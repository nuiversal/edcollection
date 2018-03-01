package com.winway.android.ewidgets.tree.utils;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author mr-lao
 * @date 2016-12-15
 *
 */
public class UIConsisident {
	public interface CallFromThread {
		void call(Object obj);
	}

	private static HashMap<String, Object> threadSession = new HashMap<>();
	private static HashMap<String, CallFromThread> callsMap = new HashMap<>();

	public static void putCall(String key, CallFromThread call) {
		callsMap.put(key, call);
	}

	public void put(String key, Object obj) {
		threadSession.put(key, obj);
		Message msg = Message.obtain();
		msg.obj = key;
		handler.sendMessage(msg);
	}

	public static void putInCurrentThread(String key, Object obj) {
		String uuid = key;
		if (callsMap.containsKey(uuid)) {
			callsMap.get(uuid).call(obj);
			callsMap.remove(uuid);
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String uuid = (String) msg.obj;
			if (callsMap.containsKey(uuid)) {
				callsMap.get(uuid).call(threadSession.get(uuid));
				callsMap.remove(uuid);
				threadSession.remove(uuid);
			}
		}
	};
}
