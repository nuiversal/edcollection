package com.winway.android.edcollection.base.service;

import com.winway.android.edcollection.login.service.ValidateLoginService;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * service操作类（项目内使用）
 * 
 * @author zgq
 *
 */
public class ServiceUtils {

	private static ServiceUtils instance;

	public static ServiceUtils getInstance() {
		if (instance == null) {
			synchronized (ServiceUtils.class) {
				if (instance == null) {
					instance = new ServiceUtils();
				}
			}
		}
		return instance;

	}

	private ValidateLoginService.ValidateLoginBinder validateLogin;
	private Context context;

	/**
	 * 登录凭据验证服务
	 */
	private ServiceConnection ValidateLoginConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			validateLogin = (ValidateLoginService.ValidateLoginBinder) service;
			validateLogin.isEffectiveCredit(context);
		}

	};

	/**
	 * 开启验证登录服务
	 */
	public void startValidateLoginService(Context context) {
		// TODO Auto-generated method stub
		// 启动登录验证服务
		this.context = context;
		Intent bindIntent = new Intent(context, ValidateLoginService.class);
		context.bindService(bindIntent, ValidateLoginConnection, Context.BIND_AUTO_CREATE);
	}

	/**
	 * 停止验证登录服务
	 * 
	 * @param context
	 */
	public void stopValidateLoginService(Context context) {
		context.unbindService(ValidateLoginConnection);
	}

}
