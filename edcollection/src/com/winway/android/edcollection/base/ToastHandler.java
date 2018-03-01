package com.winway.android.edcollection.base;

import com.winway.android.util.ToastUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 此Handler必须在主线程中new出来才行，工作线程会出错的
 * @author mr-lao
 *
 */
public class ToastHandler extends Handler {
	Context context;

	public ToastHandler(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void handleMessage(Message msg) {
		String text = (String) msg.obj;
		ToastUtils.show(context, text);
	}

	public void showMessage(String msg) {
		Message message = Message.obtain();
		message.obj = msg;
		this.sendMessage(message);
	}
}
