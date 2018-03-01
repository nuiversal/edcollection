package com.winway.android.edcollection.rtk.utils;

import android.annotation.SuppressLint;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.rtk.entity.RtkLocationInfo;
import com.winway.android.util.LogUtil;
import com.winway.android.util.NumberUtils;

public class RtkUtils {
	/**
	 * 读取流
	 * 
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	/**
	 * public int indexOf(int ch, int fromIndex)
	 * 返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索
	 * 
	 * @param srcText
	 * @param findText
	 * @return
	 */
	public static int appearNumber(String srcText, String findText) {
		int count = 0;
		int index = 0;
		while ((index = srcText.indexOf(findText, index)) != -1) {
			index = index + findText.length();
			count++;
		}
		return count;
	}

	/**
	 * 将字符串形式表示的十六进制数转换为byte数组
	 */
	@SuppressLint("DefaultLocale")
	public static byte[] hexStringToBytes(String hexString) {
		hexString = hexString.toUpperCase();
		String[] hexStrings = hexString.split(" ");
		byte[] bytes = new byte[hexStrings.length];
		for (int i = 0; i < hexStrings.length; i++) {
			char[] hexChars = hexStrings[i].toCharArray();
			bytes[i] = (byte) (charToByte(hexChars[0]) << 4 | charToByte(hexChars[1]));
		}
		return bytes;
	}

	/**
	 * 将byte数组转换为字符串形式表示的十六进制数方便查看
	 */
	public static StringBuffer bytesToString(byte[] bytes) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String s = Integer.toHexString(bytes[i] & 0xff);
			if (s.length() < 2)
				sBuffer.append('0');
			sBuffer.append(s + " ");
		}
		return sBuffer;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 解析rkt数据
	 * 
	 * @param arr
	 */
	public static void parseRtkData(String[] arr) {
		// 获取经纬度
		String tmpX = arr[4];// 经度字符串
		String tmpY = arr[2];// 纬度字符串
		if (tmpX == null || tmpY == null) {
			return;
		}
		if (tmpX.isEmpty() || tmpY.isEmpty()) {
			return;
		}
		String beforTmpX = tmpX.substring(0, 3);
		String afterTmpX = tmpX.substring(3, tmpX.length());
		double lon = Double.parseDouble(beforTmpX) + Double.parseDouble(afterTmpX) / 60;
		String beforTmpY = tmpY.substring(0, 2);
		String afterTmpY = tmpY.substring(2, tmpY.length());
		double lat = Double.parseDouble(beforTmpY) + Double.parseDouble(afterTmpY) / 60;
		LogUtil.e("经度", lon + "");
		// 保存数据
		RtkLocationInfo locationInfo = new RtkLocationInfo();
		String x = NumberUtils.format(lon, 10);
		locationInfo.setLon(Double.parseDouble(x));
		String y = NumberUtils.format(lat, 10);
		locationInfo.setLat(Double.parseDouble(y));
		locationInfo.setGpsState(Integer.parseInt(arr[6]));
		locationInfo.setSatellites(Integer.parseInt(arr[7]));
		locationInfo.setHdop(Double.parseDouble(arr[8]));
		locationInfo.setAltitude(Double.parseDouble(arr[9]));
		GlobalEntry.rtkLocationInfo = locationInfo;// 保存到全局
	}
}
