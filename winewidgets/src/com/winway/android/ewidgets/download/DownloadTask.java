package com.winway.android.ewidgets.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 文件下载器组件
 * 
 * @author zgq
 *
 */
public class DownloadTask extends AsyncTask<String, Integer, String> {
	private ProgressDialog mPDialog;
	private Context mContext;
	private PowerManager.WakeLock mWakeLock;
	private File mTargetFile;
	private DownloadTaskCallBack downloadTaskCallBack;

	/**
	 * 
	 * @param context
	 * @param targetFile
	 * @param dialogMessage
	 * @param downloadTaskCallBack
	 */
	public DownloadTask(Context context, File targetFile, String dialogMessage,
			DownloadTaskCallBack downloadTaskCallBack) {
		this.downloadTaskCallBack = downloadTaskCallBack;
		this.mContext = context;
		this.mTargetFile = targetFile;
		mPDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);

		mPDialog.setMessage(dialogMessage);
		mPDialog.setIndeterminate(true);
		mPDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mPDialog.setCancelable(false);
		// reference to instance to use inside listener
		final DownloadTask me = this;
		mPDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				me.cancel(true);
			}
		});
		Log.i("DownloadTask", "Constructor done");
	}

	@Override
	protected String doInBackground(String... sUrl) {
		InputStream input = null;
		OutputStream output = null;
		HttpURLConnection connection = null;
		try {
			URL url = new URL(sUrl[0]);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();

			// expect HTTP 200 OK, so we don't mistakenly save error report
			// instead of the file
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
			}
			Log.i("DownloadTask", "Response " + connection.getResponseCode());

			// this will be useful to display download percentage
			// might be -1: server did not report the length
			int fileLength = connection.getContentLength();

			// download the file
			input = connection.getInputStream();
			output = new FileOutputStream(mTargetFile, false);

			byte data[] = new byte[4096];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				// allow canceling with back button
				if (isCancelled()) {
					Log.i("DownloadTask", "Cancelled");
					input.close();
					return null;
				}
				total += count;
				// publishing the progress....
				if (fileLength > 0) // only if total length is known
					publishProgress((int) (total * 100 / fileLength));
				output.write(data, 0, count);
			}
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				if (output != null)
					output.close();
				if (input != null)
					input.close();
			} catch (IOException ignored) {
			}

			if (connection != null)
				connection.disconnect();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// take CPU lock to prevent CPU from going off if the user
		// presses the power button during download
		PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
		mWakeLock.acquire();

		mPDialog.show();

	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		super.onProgressUpdate(progress);
		// if we get here, length is known, now set indeterminate to false
		mPDialog.setIndeterminate(false);
		mPDialog.setMax(100);
		mPDialog.setProgress(progress[0]);

	}

	@Override
	protected void onPostExecute(String result) {
		Log.i("DownloadTask", "Work Done! PostExecute");
		mWakeLock.release();
		mPDialog.dismiss();
		if (result != null) {
			// 失败
			if (downloadTaskCallBack != null) {
				downloadTaskCallBack.fail(result);
			}
		} else {
			// 成功
			if (downloadTaskCallBack != null) {
				downloadTaskCallBack.success(result);
			}
		}
	}

	public interface DownloadTaskCallBack {
		void success(String result);

		void fail(String result);
	}
}
