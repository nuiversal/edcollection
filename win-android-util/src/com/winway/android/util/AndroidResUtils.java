package com.winway.android.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 操作安卓res资源的工具类
 * 
 * @author zgq
 *
 */
public class AndroidResUtils {
	/**
	 * 从raw文件夹中获取图片资源
	 * 
	 * @param context
	 * @param rId
	 * @return
	 */
	public static Bitmap getBitmapFromRaw(Context context, int rId) {
		Bitmap bitmap = null;
		InputStream inputStream = context.getResources().openRawResource(rId);
		bitmap = BitmapFactory.decodeStream(inputStream);
		return bitmap;
	}

	/**
	 * 从Assets文件夹中获取图片资源
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static Bitmap getBitmapFromAsset(Context context, String fileName) {
		AssetManager assetManager = context.getResources().getAssets();
		Bitmap bitmap = null;
		try {
			InputStream inputStream = assetManager.open(fileName);
			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bitmap = null;
		}
		return bitmap;
	}

	/**
	 * 从Assets文件夹中获取文件流
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static InputStream getInputStreamFromAsset(Context context, String fileName) {
		AssetManager assetManager = context.getResources().getAssets();
		InputStream inputStream = null;
		try {
			inputStream = assetManager.open(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inputStream = null;
		}
		return inputStream;
	}

	/**
	 * 获取raw文件夹中的静态资源
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public static InputStream getFileStreamFromRaw(Context context, int id) {
		InputStream inputStream = null;
		try {
			inputStream = context.getResources().openRawResource(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inputStream = null;
		}
		return inputStream;
	}
}
