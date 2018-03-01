package com.winway.android.media.photo.photoselector.util;

import java.io.File;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

/**
 * 拍照工具类
 * 
 * @author zgq
 *
 */
public class PhotoUtils {

	/**
	 * 拍照
	 * 
	 * @param fileDir
	 * @param fileName
	 * @param activity
	 * @param requestCode
	 */
	public static void takePicture(String fileDir, String fileName, Activity activity, int requestCode) {
		// 创建存放图片的文件夹
		File dir = new File(fileDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(dir, fileName)));
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 拍照
	 * 
	 * @param fileDir
	 * @param fileName
	 * @param fragment
	 * @param requestCode
	 */
	public static void takePicture(String fileDir, String fileName, Fragment fragment, int requestCode) {
		// 创建存放图片的文件夹
		File dir = new File(fileDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(dir, fileName)));
		fragment.startActivityForResult(intent, requestCode);
	}

	/**
	 * 显示图片
	 * 
	 * @param imgPath
	 * @param options
	 * @param imageView
	 */
	public static void showImage(Activity activity, String imgPath, ImageView imageView) {
		ImageLoader loader = ImageLoader.getInstance();
		loader.init(ImageLoaderConfiguration.createDefault(activity));
		loader.displayImage(imgPath, imageView);
	}
}
