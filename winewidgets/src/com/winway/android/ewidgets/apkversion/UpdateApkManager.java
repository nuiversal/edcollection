package com.winway.android.ewidgets.apkversion;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import com.winway.android.util.FileUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * 
 * @author lyh
 *
 */
public class UpdateApkManager {
	private UpdataApkConfig config;
	private Activity mActivity;
	private Handler m_mainHandler = new Handler();
	private File file;

	public UpdateApkManager(UpdataApkConfig config, Activity mActivity) {
		super();
		this.config = config;
		this.mActivity = mActivity;
	}

	/**
	 * 向服务器发送查询请求，返回查到的StringBuilder类型数据
	 * 
	 * @param ArrayList
	 *            <NameValuePair> vps POST进来的参值对
	 * @return StringBuilder builder 返回查到的结果
	 * @throws Exception
	 */
	public StringBuilder post_to_server(List<NameValuePair> vps) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpResponse response = null;
			// 创建httpost.访问本地服务器网址
			HttpPost httpost = new HttpPost(config.serverDataAddress);
			StringBuilder builder = new StringBuilder();

			httpost.setEntity(new UrlEncodedFormEntity(vps, HTTP.UTF_8));
			response = httpclient.execute(httpost); // 执行

			if (response.getEntity() != null) {
				// 如果服务器端JSON没写对，这句是会出异常，是执行不过去的
				BufferedReader reader = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				String s = reader.readLine();
				for (; s != null; s = reader.readLine()) {
					builder.append(s);
				}
			}
			return builder;

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("msg", e.getMessage());
			return null;
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();// 关闭连接
				// 这两种释放连接的方法都可以
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("msg", e.getMessage());
			}
		}
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @param packageName 包名
	 * @return
	 */
	public static int getVerCode(Context context, String packageName) {
		int verCode = -1;
		try {
			// 注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
			verCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e("msg", e.getMessage());
		}
		return verCode;
	}
	/**
	 * 获取版本名称
	 * 
	 * @param context
	 * @param packageName 包名
	 * @return
	 */
	public static String getVerName(Context context, String packageName) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e("msg", e.getMessage());
		}
		return verName;
	}

	/**
	 * 获取服务器版本号
	 * 
	 * @return
	 */
	public String getServerVersion() {
		String serverJson = null;
		byte[] buffer = new byte[128];

		try {
			URL serverURL = new URL(config.serverDataAddress);
			HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();
			BufferedInputStream bis = new BufferedInputStream(connect.getInputStream());
			int n = 0;
			while ((n = bis.read(buffer)) != -1) {
				serverJson = new String(buffer);
			}
		} catch (Exception e) {
			System.out.println("获取服务器版本号异常！" + e);
			return null;
		}

		return serverJson;
	}

	/**
	 * 从服务器获取当前最新版本号，如果成功返回TURE，如果失败，返回FALSE
	 * 
	 * @param m_newVerName
	 *            版本名称
	 * @param m_newVerCode
	 *            版本号
	 * @return
	 */
	public Boolean postCheckNewestVersionCommand2Server() {
		// StringBuilder builder = new StringBuilder();
		JSONArray jsonArray = null;
		try {
			String serverJson = getServerVersion();
			if (serverJson.equals("") || serverJson == null) {
				return false;
			}
			// 构造POST方法的{name:value} 参数对
			// List<NameValuePair> vps = new ArrayList<NameValuePair>();
			// 将参数传入post方法中
			// vps.add(new BasicNameValuePair("action", "checkNewestVersion"));
			// builder = Common.post_to_server(vps);
			// Log.e("msg", builder.toString());

			jsonArray = new JSONArray(serverJson);
			if (jsonArray.length() > 0) {
				JSONObject object = jsonArray.getJSONObject(0);
				UpdateApkActivityMain.serverVerName = object.getString("versionName");
				UpdateApkActivityMain.serverVerCode = object.getLong("version");
				return true;
			}

			return false;
		} catch (Exception e) {
			Log.e("msg", e.getMessage());
			UpdateApkActivityMain.serverVerName = "";
			UpdateApkActivityMain.serverVerCode = 0;
			return false;
		}
	}

	/**
	 * 下载
	 * 
	 * @param url
	 * @param m_progressDlg
	 */
	public void downFile(final String url, final ProgressDialog m_progressDlg) {
		m_progressDlg.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();

					m_progressDlg.setMax((int) length);// 设置进度条的最大值

					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					int lastIndexOf = config.serverApkAddress.lastIndexOf("/");
					String localAppName = config.serverApkAddress.substring(lastIndexOf+1, config.serverApkAddress.length());
					if (is != null) {
						File dir = new File(config.localApkPath);
						if (!dir.exists()) {
							dir.mkdirs();
						}
						file = new File(dir,
								localAppName);
						if (!file.exists()) {
							file.createNewFile();
						} else {
							file.delete();
						}
						fileOutputStream = new FileOutputStream(file);
						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							if (length > 0) {
								m_progressDlg.setProgress(count);
							}
						}
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down(m_progressDlg); // 告诉HANDER已经下载完成了，可以安装了
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					m_progressDlg.dismiss();
				} catch (IOException e) {
					e.printStackTrace();
					m_progressDlg.dismiss();
				}
			}
		}.start();
	}

	/**
	 * 告诉HANDER已经下载完成了，可以安装了
	 */
	private void down(final ProgressDialog m_progressDlg) {
		
		m_mainHandler.post(new Runnable() {
			public void run() {
				m_progressDlg.cancel();
				update();
			}
		});
	}
	/**
	 * 安装程序
	 */
	void update() {
		//执行动作
		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			//执行的数据类型 
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
			mActivity.startActivity(intent);
		}else {
			System.out.println("找不到指定的文件");
		}
	}

}
