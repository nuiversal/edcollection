package com.winway.android.edcollection.login.utils;

import org.apache.shiro.util.ByteSource;

/**
 * 密码工具
 * 
 * @author zgq
 *
 */
public class PasswordUtil {
	public static final String CREDENTIALS_SALT = "WINWAY_EDP";

	/**
	 * 获得加密后的密码字符串
	 * 
	 * @param password
	 *            密码
	 * @return String 加密后的密码
	 */
	public static String getEDPHashedPasswordBase64(String password) {

		return new org.apache.shiro.crypto.hash.Sha256Hash(password, ByteSource.Util.bytes(CREDENTIALS_SALT))
				.toBase64();
	}

	public static boolean checkEDPPassword(String password, String source) {
		String pwd = getEDPHashedPasswordBase64(password);
		return pwd.equals(source);
	}

	public static void main(String[] args) {
		String pwd = getEDPHashedPasswordBase64("123456");
		System.out.println(pwd);
	}
}
