package com.winway.android.util;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

/**
 * @author qiu ?????????
 */
public class PhotoUtil {

	public static String curWholePath = "";

	private Activity activity;
	private int requestCode;

	public PhotoUtil(int requestCode, Activity activity) {
		this.requestCode = requestCode;
		this.activity = activity;
	}

	/**
	 * ??????
	 * 
	 * @param wholePath
	 * @param prefixName
	 * @return
	 */
	public static boolean delPhotoByFileName(String wholePath, String prefixName) {
		boolean flag = true;
		List<String> filePathLst = FileUtil.getImagesPathfromDir(wholePath);
		if (filePathLst != null && filePathLst.size() > 0) {
			for (String filePath : filePathLst) {
				if (filePath.contains(prefixName)) {
					flag = FileUtil.deleteFile(filePath);
				}
			}
		}

		return flag;
	}

	/**
	 * ????Intent????
	 * 
	 * @param pathStr
	 *            ???????·??????a/b,a/b/c??
	 */
	public void startTakePictureIntent(String pathStr) {
		startTakePictureIntent(pathStr, null);
	}

	/**
	 * ????Intent????
	 * 
	 * @param pathStr
	 *            ???????·??????a/b,a/b/c??
	 * @param prefixName
	 *            ?????????
	 */
	public void startTakePictureIntent(String pathStr, String prefixName) {
		if (pathStr == null) {
			pathStr = "";
		}
		String wholePath = FileUtil.getWinwayitPath() + "picture/" + pathStr;
		startTakePictureIntentByWholePath(wholePath, prefixName);
	}

	/**
	 * ????Intent????
	 * 
	 * @param pathStr
	 *            ???????·??????a/b,a/b/c??
	 * @param prefixName
	 *            ?????????
	 * @param onlyFlag
	 *            ??????????????????????????
	 */
	public void startTakePictureIntent(String pathStr, String prefixName, boolean onlyFlag) {
		if (pathStr == null) {
			pathStr = "";
		}
		String wholePath = FileUtil.getWinwayitPath() + "picture/" + pathStr;
		if (onlyFlag && !StringUtils.getInstance().isEmpty(prefixName)) {
			delPhotoByFileName(wholePath, prefixName);
		}

		startTakePictureIntentByWholePath(wholePath, prefixName);
	}

	/**
	 * ????Intent????
	 * 
	 * @param wholePath
	 *            ???????·????sd??????·????
	 * @param prefixName
	 *            ?????????
	 * @param onlyFlag
	 *            ??????????????????????????
	 */
	public void startTakePictureIntentByWholePath(String wholePath, String prefixName, boolean onlyFlag) {
		if (onlyFlag && !StringUtils.getInstance().isEmpty(prefixName)) {
			delPhotoByFileName(wholePath, prefixName);
		}
		startTakePictureIntentByWholePath(wholePath, prefixName);
	}

	/**
	 * ????Intent????
	 * 
	 * @param wholePath
	 *            ???????·????sd??????·????
	 * @param prefixName
	 *            ?????????
	 */
	public void startTakePictureIntentByWholePath(String wholePath, String prefixName) {
		// if(StringUtils.getInstance().isEmpty(prefixName)){
		// prefixName = "???";
		// }
		// try {
		// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// FileUtil.creatSDCardDirByWholePath(wholePath);
		// File file = new File(wholePath,
		// prefixName+"("+System.currentTimeMillis()+").jpg");
		// PhotoUtil.curWholePath = file.toString();
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		// activity.startActivityForResult(intent, requestCode);
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// }
	}

	/**
	 * ?????(??????,??????activity?????)
	 * 
	 * @param photoPath
	 *            ???·??
	 */
	public void lookPictureFunc(String photoPath) {
		List<String> filesPathList = FileUtil.getImagesPathfromDir(photoPath);

		if (filesPathList == null || filesPathList.size() == 0) {
			Toast.makeText(activity, "?????????????", Toast.LENGTH_SHORT).show();
		} else {
			Intent intent = new Intent();
			intent.setClass(activity, null);
			intent.putExtra("photoPath", photoPath);
			activity.startActivityForResult(intent, requestCode);
		}
	}

	/**
	 * ??????????
	 * 
	 * @param activity
	 * @param path
	 * @param requestCode
	 */
	public static void takePhoto(final Activity activity, final String path, final String fileName,
			final int requestCode) {
		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File file = new File(path + "/" + fileName);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * ???????
	 * 
	 * @param activity
	 * @param path
	 */
	public static void openPhoto(final Activity activity, final String path) {
		File file = new File(path);
		if (file != null && file.exists() && file.isFile()) {
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "image/*");
			activity.startActivity(intent);
		} else {
			Toast.makeText(activity, "?????????", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * ???????
	 * 
	 * @param activity
	 * @param path
	 */
	public static void openPhoto(final Activity activity, final List<String> listPath) {

//		File fileTmp = new File(listPath.get(0));
		if (listPath != null && listPath.size() > 0) {
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			for (int i = 0; i < listPath.size(); i++) {
				File file = new File(listPath.get(i));
				intent.setDataAndType(Uri.fromFile(file), "image/*");
			}
			activity.startActivity(intent);
		} else {
			Toast.makeText(activity, "?????????", Toast.LENGTH_SHORT).show();
		}

	}
}
