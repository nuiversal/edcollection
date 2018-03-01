package com.winway.android.network;

import java.net.URLEncoder;

/**
 *  URL编码工具类，将完整的URL编码
 * @author mr-lao
 *
 */
public class URLEncoderUtil {

	@SuppressWarnings("deprecation")
	public static String encode(String url, String charset) {
		int index = url.indexOf("?") + 1;
		if (index < 0) {
			return url;
		}
		String paramsString = url.substring(index);
		String[] paramsArray = paramsString.split("&");
		StringBuilder sb = new StringBuilder(url.substring(0, index));
		for (String param : paramsArray) {
			String[] split = param.split("=");
			if (split == null || split.length < 2) {
				continue;
			}
			sb.append(URLEncoder.encode(split[0])).append("=").append(URLEncoder.encode(split[1])).append("&");
		}
		if (sb.lastIndexOf("&") > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
}
