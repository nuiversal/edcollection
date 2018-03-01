package com.winway.android.network;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Http请求的工具类
 * 
 * @author zgq
 *
 */
public class HttpUtils {

	private static final int READ_TIMEOUT = 30000;
	private static final int CONNECT_TIMEOUT = 30000;

	public interface CallBack {
		void onRequestComplete(String result);

		void onError(Exception exception);
	}

	/**
	 * Get请求
	 * 
	 * @param urlStr
	 * @param callBack
	 */
	public static void doGet(final String urlStr, final CallBack callBack) {
		new Thread() {
			public void run() {
				try {
					String result = doGet(urlStr);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (callBack != null) {
						callBack.onError(e);
					}
				}

			};
		}.start();
	}

	/**
	 * Post请求
	 * 
	 * @param urlStr
	 * @param params
	 * @param callBack
	 * @throws Exception
	 */
	public static void doPost(final String urlStr, final Map<String, String> params, final CallBack callBack)
			throws Exception {
		new Thread() {
			public void run() {
				try {
					String result = doPost(urlStr, params);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (callBack != null) {
						callBack.onError(e);
					}
				}

			};
		}.start();

	}

	/**
	 * Get请求，获得返回数据
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(READ_TIMEOUT);
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else {
				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
			}
			conn.disconnect();
		}

		return null;

	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 map的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, String> params) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(READ_TIMEOUT);
			conn.setConnectTimeout(CONNECT_TIMEOUT);

			if (params != null && params.size() > 0) {
				String paramsStr = getQuery(params);
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(paramsStr);
				// flush输出流的缓冲
				out.flush();
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 构建post参数
	 * 
	 * @param params
	 * @return
	 */
	private static String getQuery(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		Set<Entry<String, String>> sets = params.entrySet();
		for (Entry<String, String> entry : sets) {
			if (first) {
				first = false;
			} else {
				result.append("&");
			}
			params.entrySet();
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}
		return result.toString();
	}

	/**
	 * 加载图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getUrlImage(String url) {

		Bitmap img = null;
		try {
			URL picurl = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) picurl.openConnection();
			conn.setConnectTimeout(6000);// 设置超时
			conn.setDoInput(true);
			conn.setUseCaches(false);// 不缓存
			conn.connect();
			InputStream is = conn.getInputStream();// 获得图片的数据流
			img = BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			img = null;
		}
		return img;
	}

	/**
	 * 上传文件到服务器
	 * 
	 * @param uploadUrl
	 *            服务端地址
	 * @param srcPath
	 *            本地文件路径
	 */
	@SuppressLint("NewApi")
	public static String uploadFile(String uploadUrl, String srcPath) throws Exception {
		String result = null;
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		DataOutputStream dos = null;
		InputStream is = null;
		// URL编码，防止中文乱码处理
		URL url = new URL(URLEncoderUtil.encode(uploadUrl, "UTF-8"));
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
		httpURLConnection.setRequestProperty("Charset", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		// httpURLConnection.setChunkedStreamingMode(1024*1024);///支持大文件上传,分块传输造成文件损坏情况？是服务端不支持分块传输?
		//httpURLConnection.setFixedLengthStreamingMode(file.length());

		dos = new DataOutputStream(httpURLConnection.getOutputStream());
		dos.writeBytes(twoHyphens + boundary + end);
		dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
				+ srcPath.substring(srcPath.lastIndexOf("/") + 1) + "\"" + end);
		dos.writeBytes("Content-Type:application/octet-stream" + end);
		dos.writeBytes(end);
		// 将SD
		// 文件通过输入流读到Java代码中
		FileInputStream fis = new FileInputStream(srcPath);
		byte[] buffer = new byte[8192]; // 8k
		int count = 0;
		while ((count = fis.read(buffer)) != -1) {
			dos.write(buffer, 0, count);

		}
		fis.close();
		dos.writeBytes(end);
		dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
		dos.flush();
		// 读取服务器返回结果
		is = httpURLConnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sBuffer = new StringBuffer();
		String lineTmp = null;
		while ((lineTmp = br.readLine()) != null) {
			sBuffer.append(lineTmp);
		}
		result = sBuffer.toString();
		// 关闭流
		if (dos != null) {
			dos.close();
		}
		if (is != null) {
			is.close();
		}
		return result;
	}

	/**
	 * 上传文件到服务端
	 * 
	 * @param uploadUrl
	 * @param srcPath
	 * @param callBack
	 */
	public static void uploadFile(final String uploadUrl, final String srcPath, final CallBack callBack) {
		new Thread() {
			public void run() {
				try {
					String result = uploadFile(uploadUrl, srcPath);
					if (callBack != null) {
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (callBack != null) {
						callBack.onError(e);
					}
				}

			};
		}.start();
	}

	/**
	 * 下载文件
	 * @param pUrl     接口url
	 * @param fileName 保存文件名
	 * @param savePath 保存路径
	 * @param callBack
	 */
	public static void downLoadFile(final String pUrl, final String fileName, final String savePath,
			final CallBack callBack) {
		new Thread() {
			public void run() {
				try {
					HttpDownloadUtils.downloadFile(pUrl, savePath,fileName);
					if (callBack != null) {
						String result="{code:0,msg:'下载成功'}";
						callBack.onRequestComplete(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (callBack != null) {
						callBack.onError(e);
					}
				}

			};
		}.start();
	}

	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

}