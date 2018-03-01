package com.winway.android.edcollection.login.bll;

import android.content.Context;

import com.winway.android.edcollection.login.entity.ServerInfo;
import com.winway.android.util.PreferencesUtils;

public class SettingServerBill {
	private Context context;

	public SettingServerBill(Context context) {
		this.context = context;
	}

	/**
	 * 保存服务信息到preference
	 * 
	 * @param serverInfo
	 */
	public void saveServerInfo(ServerInfo serverInfo) {
		PreferencesUtils.PREFERENCE_NAME = "serverinfo&port";
		PreferencesUtils.putString(context, "loginServerAddr", serverInfo.getLoginServerAddr());
		PreferencesUtils.putString(context, "dataServerAddr", serverInfo.getDataServerAddr());
		PreferencesUtils.putString(context, "fileConnectServerAddr", serverInfo.getFileConnectServerAddr());
	}
}
