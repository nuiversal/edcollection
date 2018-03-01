package com.winway.android.sensor.nfc;

public class ByteHexUtil {
	/**
	 * 将字节数组转换成16进制的字符
	 * @param bytes
	 * @return
	 */
	public static String byte2hexStirng(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = bytes.length - 1; i >= 0; --i) {
			int b = bytes[i] & 0xff;
			if (b < 0x10)
				sb.append('0');
			sb.append(Integer.toHexString(b));
			if (i > 0) {
				sb.append("");
			}
		}
		return sb.toString();
	}
}
