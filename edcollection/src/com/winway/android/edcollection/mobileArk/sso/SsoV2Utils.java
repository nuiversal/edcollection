package com.winway.android.edcollection.mobileArk.sso;

import com.fiberhome.mobiark.sso_v2.util.SSOFileUtil;
import com.fiberhome.mobileark.sso_v2.GetParamListener;
import com.fiberhome.mobileark.sso_v2.GetTokenListener;
import com.fiberhome.mobileark.sso_v2.MobileArkSSOAgent;
import com.fiberhome.mobileark.sso_v2.SSOStatusListener;

import android.R.integer;
import android.content.Context;

/**
 * ssov2单点登录相关操作
 * 
 * @author winway_zgq
 *
 */
public class SsoV2Utils {

	private static volatile SsoV2Utils instance = null;
	private static MobileArkSSOAgent agent = null;

	private SsoV2Utils() {

	}

	public static SsoV2Utils getInstance(Context context) {
		if (instance == null) {
			synchronized (SsoV2Utils.class) {
				if (instance == null) {
					instance = new SsoV2Utils();
					agent = MobileArkSSOAgent.getInstance(context);
				}
			}
		}

		return instance;
	}

	/**
	 * 设置包名
	 * 
	 * @param packageName
	 */
	public void setMobilearkPackagename(String packageName) {
		agent.setMobilearkPackagename(packageName);
	}

	/**
	 * 初始化代理类
	 * 
	 * @param context
	 * @param ssoStatusListener
	 *            初始化代理类回调
	 */
	public void initSSOAgent(SSOStatusListener ssoStatusListener) {
		agent.initSSOAgent(ssoStatusListener);
	}

	/**
	 * 获取登录凭据
	 * 
	 * @param listener
	 */
	public void getToken(GetTokenListener listener) {
		agent.getToken(listener);
	}

	/**
	 * 获取登录凭据（同步方法，不建议使用）
	 * 
	 * @return
	 */
	public String getToken() {
		return agent.getToken();
	}

	/**
	 * 获取登录参数
	 * 
	 * @param key
	 *            如（“loginname”, “password”,”ecid” ）
	 * @param listener
	 */
	public void getParam(String key, GetParamListener listener) {
		agent.getParam(key, listener);
	}

}
