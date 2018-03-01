package com.winway.android.util;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * 图片帮助类
 * @author qiu
 *
 */
public class ImageHelper {

	/**
	 * 计算比例
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
			int reqHeight) {
		// 图像原始高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	/**
	 * 按比例读取资源文件
	 * @param res
	 * @param resId 资源文件id
	 * @param reqWidth 要显示的宽度
	 * @param reqHeight 要显示的高度
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth,
			int reqHeight) {

		// 首先设置 inJustDecodeBounds=true 来检查尺寸
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// 计算压缩比例
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// 设置inJustDecodeBounds为false
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * 按比例读取SDCARD的文件
	 * @param imagePath 文件路径
	 * @param reqWidth 要显示的宽度
	 * @param reqHeight 要显示的高度
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromImagePath(String imagePath, int reqWidth,
			int reqHeight) {

		// 首先设置 inJustDecodeBounds=true 来检查尺寸
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// BitmapFactory.decodeResource(res, resId, options);
		BitmapFactory.decodeFile(imagePath, options);

		// 计算压缩比例
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// 设置inJustDecodeBounds为false
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(imagePath, options);
	}

	/**
	 * 处理拍照后的返回数据，保存到相应的位置
	 * 在方法OnActivityResult()中使用
	 * @param data OnActivityResult 方法中的Intent data
	 * @param picPath 保存路径
	 * @param saveName 保存文件名
	 * @param format 设置JPEG或PNG格式
	 * @param quality 压缩质量
	 * @return 是否保存成功
	 */
	public static boolean dealTakePhotoOnActivityResult(Intent data, String picPath,
			String saveName, CompressFormat format, int quality) {
		// 是否保存成功
		boolean flag = false;
		if (data != null) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap bitmap = (Bitmap) extras.get("data");
				FileOutputStream outStream = null;
				try {
					File file = new File(picPath, saveName);
					File dir = new File(picPath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					if (!file.exists()) {
						file.createNewFile();
					}
					outStream = new FileOutputStream(file);
					// 默认JPEG
					if (format == null) {
						format = CompressFormat.JPEG;
					}
					flag = bitmap.compress(format, quality, outStream);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

				try {
					outStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return flag;
			}
		}
		return flag;
	}
}
