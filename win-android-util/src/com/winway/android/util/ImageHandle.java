package com.winway.android.util;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * 图片处理类
 * 
 * @author struggle
 * 
 */
public class ImageHandle {
	private static ImageHandle imageHandle;

	private ImageHandle() {

	}

	public static ImageHandle getInstance() {
		if (imageHandle == null) {
			synchronized (ImageHandle.class) {
				if (imageHandle == null) {
					imageHandle = new ImageHandle();
				}
			}

		}
		return imageHandle;
	}

	/**
	 * 计算InSampleSize大小
	 * 
	 * @param options
	 * @param reqWidth
	 *            期望压缩后的宽度（如480）
	 * @param reqHeight
	 *            期望压缩后的高度（如800）
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;// 图片实际高度
		final int width = options.outWidth;// 图片实际宽度
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	/**
	 * 根据图片路径和
	 * 
	 * @param strPath
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public Bitmap decodeSampledBitmapFromResource(String strPath, Activity activity) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;// 为true表示不加载到内存
		BitmapFactory.decodeFile(strPath, options);// 这段代码之后,options.outWidth 和
													// options.outHeight就是图片的实际宽和高
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int reqWidth = dm.widthPixels;// 希望显示的宽高
		int reqHeight = dm.heightPixels;
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(strPath, options);
	}

	
}
