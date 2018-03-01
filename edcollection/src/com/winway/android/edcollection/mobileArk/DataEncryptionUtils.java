package com.winway.android.edcollection.mobileArk;

import com.fiberhome.mobileark.crpto.api.AuthenticationInterfaceV2;
import com.fiberhome.mobileark.crpto.api.CryptoInterface;
import com.fiberhome.mobileark.crpto.api.CryptoSDKManager;
import com.fiberhome.mobileark.crpto.api.FileMInterface;
import com.fiberhome.mobileark.crpto.api.InitAuthenticationListener;

import android.content.Context;

/**
 * 数据加密
 * 
 * @author winway_zgq
 *
 */
public class DataEncryptionUtils {

	private static String key = "hdhwbjbs";

	/**
	 * 设置应用包名
	 * @param context
	 */
	public static void setMobilearkPackagename(Context context) {
		CryptoSDKManager.getInstance().getAuthenticationInterfaceV2(context)
				.setMobilearkPackagename(context.getPackageName());
	}

	/**
	 * 初始化鉴权接口
	 * 
	 * @param context
	 * @param initAuthenticationListener
	 */
	public static void initInterface(Context context, InitAuthenticationListener initAuthenticationListener) {
		AuthenticationInterfaceV2 authenticationInterfaceV2 = CryptoSDKManager.getInstance()
				.getAuthenticationInterfaceV2(context);
		authenticationInterfaceV2.initInterface(initAuthenticationListener);
	}

	/**
	 * 初始化密钥（在无服务器验证鉴权的情况下）
	 */
	public static void initPws() {
		CryptoInterface cryptoInterface = CryptoSDKManager.getInstance().getCryptoInterface();
		cryptoInterface.setPws(key);
	}

	/**
	 * 加密文件
	 * 
	 * @param filePath
	 * @param context
	 * @return
	 */
	public static boolean encryFile(String filePath, Context context) {
		FileMInterface fInterface = CryptoSDKManager.getInstance().getFileMInterface(context);
		return fInterface.encryFile(filePath);
	}

	/**
	 * 解密文件
	 * 
	 * @param filePath
	 * @param context
	 * @return
	 */
	public static String decryptFile(String filePath, Context context) {
		FileMInterface fInterface = CryptoSDKManager.getInstance().getFileMInterface(context);
		return fInterface.decryptFile(filePath);

	}
}
