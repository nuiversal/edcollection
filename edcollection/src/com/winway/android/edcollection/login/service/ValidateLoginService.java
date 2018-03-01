package com.winway.android.edcollection.login.service;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.dto.MessageBase;
import com.winway.android.map.entity.MapCache;
import com.winway.android.network.HttpUtils;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.LogUtil;
import com.winway.android.util.NetWorkUtils;

import android.R.bool;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * 登录验证服务
 * 
 * @author zgq
 *
 */
public class ValidateLoginService extends Service {

	/**
	 * 线程执行的时间间隔
	 */
	private final int time_interval = 1000 * 60 * 1;// 默认1分钟验证一次
	/**
	 * 是否开启线程轮训
	 */
	private boolean validate_flag = true;
	public static final String TAG = "ValidateLoginService";
	private ValidateLoginBinder mBinder = new ValidateLoginBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		LogUtil.d(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtil.d(TAG, "onDestroy");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		LogUtil.d(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		LogUtil.d(TAG, "onStart");
		super.onStart(intent, startId);
	}

	/**
	 * 组件之间通信桥梁
	 * 
	 * @author zgq
	 *
	 */
	public class ValidateLoginBinder extends Binder {

		public void startDownload() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// 执行具体的下载任务
					while (true) {
						LogUtil.d(TAG, "startDownload() executed");

					}

				}
			}).start();
		}

		/**
		 * 是否有效的登录凭据
		 * 
		 * @return
		 */
		public void isEffectiveCredit(final Context context) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (validate_flag) {
						if (NetWorkUtils.isConnected(context)) {// 有网络
							String url = GlobalEntry.loginServerUrl + "verfy?credit="
									+ GlobalEntry.loginResult.getCredit() + "&getinfo=0";
							String data = HttpUtils.doGet(url);
							if (data != null) {
								// 解析
								Gson gson = GsonUtils.build();
								Type typeMbase = new TypeToken<MessageBase>() {
								}.getType();
								MessageBase messageBase = gson.fromJson(data, typeMbase);
								if (messageBase.getCode() < 0) {
									GlobalEntry.isEffectiveCredit = false;
								} else {
									GlobalEntry.isEffectiveCredit = true;
								}

							} else {
								GlobalEntry.isEffectiveCredit = false;
							}
						} else {
							GlobalEntry.isEffectiveCredit = false;
						}

						// 线程休眠
						try {
							Thread.sleep(time_interval);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();

		}

	}

}
