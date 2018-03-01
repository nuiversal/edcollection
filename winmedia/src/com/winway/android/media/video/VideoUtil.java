package com.winway.android.media.video;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

public class VideoUtil {
	/**
	 * 调用系统相机进行录像并保存到指定文件中
	 * @param activity
	 * @param requestCode
	 * @param outputFile   录像结果保存文件
	 */
	public static void makeVideo(Activity activity, int requestCode, File outputFile) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 调用系统播放器播放视频
	 * @param context
	 * @param videoFile
	 */
	public static void openVideo(Context context, File videoFile) {
		Uri uri = Uri.fromFile(videoFile);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String sufix = videoFile.getName().substring(videoFile.getName().indexOf(".") + 1);
		String mime = "video/mp4";
		if ("m4v".equalsIgnoreCase(sufix)) {
			mime = "video/m4v";
		} else if ("mpeg".equals(sufix)) {
			mime = "video/mpeg";
		}
		intent.setDataAndType(uri, mime);
		context.startActivity(intent);
	}
}
