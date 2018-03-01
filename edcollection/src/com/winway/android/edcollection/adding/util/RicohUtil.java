package com.winway.android.edcollection.adding.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.winway.android.util.ToastUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

/**
 * 理光景达S APP辅助类
 * 
 * @author mr-lao
 * 
 */
@SuppressLint("SimpleDateFormat")
public class RicohUtil {

	// 打开理光景达SAPP
	public static boolean openRicohAPP(Context context) {
		try {
			Intent intent = new Intent();
			intent.setClassName("cn.theta360", "cn.theta360.TitleActivity");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setAction(Intent.ACTION_MAIN);
			context.startActivity(intent);
			return true;
		} catch (Exception e) {
			ToastUtils.show(context, "请先安装“理光景达S”APP");
			return false;
		}
	}

	// 拷贝图片，无时间判断
	public static void copyPicture(String descPath) throws IOException {
		FileInputStream fis = new FileInputStream(getLastFile());
		File descFile = new File(descPath);
		if (!descFile.exists()) {
			if (!descFile.getParentFile().exists()) {
				descFile.getParentFile().mkdirs();
			}
			descFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(descFile);
		int flag = 0;
		byte buffer[] = new byte[1024];
		while ((flag = fis.read(buffer)) != -1) {
			fos.write(buffer, 0, flag);
		}
		fis.close();
		fos.close();
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	// 拷贝图片，有时间判断，那建议使用此文件
	public static void copyPicture(String descPath, long time) throws IOException {
		File lastFile = getLastFile();
		if (lastFile == null) {
			return;
		}
		String fileName = lastFile.getName();
		long time2 = Long.parseLong(fileName.substring(fileName.indexOf('_') + 1, fileName.length() - 4));
		String timestr = sdf.format(new Date(time));
		if (time2 < Long.parseLong(timestr)) {
			return;
		}
		FileInputStream fis = new FileInputStream(lastFile);
		File descFile = new File(descPath);
		if (!descFile.exists()) {
			if (!descFile.getParentFile().exists()) {
				descFile.getParentFile().mkdirs();
			}
			descFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(descFile);
		int flag = 0;
		byte buffer[] = new byte[1024];
		while ((flag = fis.read(buffer)) != -1) {
			fos.write(buffer, 0, flag);
		}
		fis.close();
		fos.close();
	}

	// 获取最拍照的一张图片
	public static File getLastFile() {
		// 图片文件夹
		File dir = new File(Environment.getExternalStorageDirectory(), "Pictures/RICOH THETA");
		// 所有图片文件
		File[] listFiles = dir.listFiles();
		// 解析 R0010128_20160606141124.JPG
		for (File file : listFiles) {
			boolean isThis = true;
			long time1 = Long
					.parseLong(file.getName().substring(file.getName().indexOf('_') + 1, file.getName().length() - 4));
			for (int i = 0; i < listFiles.length; i++) {
				long time2 = Long.parseLong(listFiles[i].getName().substring(listFiles[i].getName().indexOf('_') + 1,
						listFiles[i].getName().length() - 4));
				if (time1 < time2) {
					isThis = false;
					break;
				}
			}
			if (isThis) {
				return file;
			}
		}
		return null;
	}
}
