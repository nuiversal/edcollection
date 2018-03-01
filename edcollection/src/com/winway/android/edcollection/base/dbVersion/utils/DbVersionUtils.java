package com.winway.android.edcollection.base.dbVersion.utils;

import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.dbVersion.IDbVersion;
import com.winway.android.edcollection.base.dbVersion.impl.DbVersionImpl;
import com.winway.android.util.ProgressUtils;
import com.winway.android.util.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/**
 * 处理版本变更操作业务处理类
 * 
 * @author winway_zgq
 *
 */
public class DbVersionUtils {
	private static volatile DbVersionUtils instance;
	private static final Object lock = new Object();

	private DbVersionUtils() {
	}

	public static DbVersionUtils getInstance() {
		if (instance == null) {
			synchronized (lock) {
				instance = new DbVersionUtils();
			}
		}
		return instance;
	}

	private DbUpgrade dbUpgrade = null;

	/**
	 * 执行数据版本升级操作
	 * 
	 * @param context
	 * @param dbUpgrade
	 */
	public void handleDbUpgrade(final Context context, final DbUpgrade dbUpgrade) {
		this.dbUpgrade = dbUpgrade;
		ProgressUtils.getInstance().show("软件升级中...", false, context);
		final int flag_success = 1;
		final int flag_fail = 2;
		final Handler uiHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				String result = (String) msg.obj;
				switch (msg.what) {
				case flag_success: {
					dbUpgrade.success(result);
					break;
				}
				case flag_fail: {
					dbUpgrade.fail(result);
					break;
				}
				default:
					break;
				}
				ProgressUtils.getInstance().dismiss();

			}
		};
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				IDbVersion dbVersion = new DbVersionImpl();
				// 基础db升级1
				boolean upgradeBasicRes = dbVersion.handleUpgrade(context, GlobalEntry.prjDbUrl, "app_ed_version.xml",
						"sql/ed/", "ED_DATAPLUG");
				if (upgradeBasicRes == false) {
					message.obj = "基础db更新失败！";
					message.what = flag_fail;
					uiHandler.sendMessage(message);
					return;
				}
				// 基础db升级2
				boolean upgradeCloudRes = dbVersion.handleUpgrade(context, GlobalEntry.prjDbUrl, "app_ecloud_version.xml",
						"sql/ecloud/", "ECLOUD_DATAPLUG");
				if (upgradeCloudRes == false) {
					message.obj = "基础db更新失败！";
					message.what = flag_fail;
					uiHandler.sendMessage(message);
					return;
				}
				if (upgradeBasicRes && upgradeCloudRes) {
					message.obj = "升级成功！";
					message.what = flag_success;
					uiHandler.sendMessage(message);
					return;
				}

			}
		}).start();
	}

	public interface DbUpgrade {
		/**
		 * 
		 * @param result
		 */
		void success(String result);

		/**
		 * 
		 * @param result
		 */
		void fail(String result);
	}

}
