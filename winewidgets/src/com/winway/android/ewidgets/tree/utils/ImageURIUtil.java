package com.winway.android.ewidgets.tree.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.ewidgets.net.service.BaseService.WayFrom;
import com.winway.android.ewidgets.net.service.impl.LineServiceImpl;
import com.winway.android.ewidgets.tree.utils.UIConsisident.CallFromThread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

/**
 * 生成和解释图片URI的工具类，一般和<code>DefaultNodeContentMSG</code>和<code>DefaultTreeNodeHolder</code>一起使用
 * @author mr-lao
 * @date 2016-12-15
 *
 */
public class ImageURIUtil {
	private static String split = "://";

	/**
	 * 创建ImageURI
	 * @param file
	 * @return
	 */
	public static String createImageURI(File file) {
		String chema = "file";
		return chema + split + file.getAbsolutePath();
	}

	public static final int RES_TYPE_DRAWABLE = 1;
	public static final int RES_TYPE_ASSEST = 2;
	public static final int RES_TYPE_RAW = 3;

	/**
	 * 创建ImageURI
	 * @param res  drawable图片的ID、或者assest中的图片文件名字、或者是raw中的图片文件ID
	 * @param type  1:drawable  2、assest  3、raw
	 * @return
	 */
	public static String createImageURI(String res, int type) {
		String chema = "res";
		if (type == 1) {
			chema += ":drawable";
		} else if (type == 2) {
			chema += ":assest";
		} else {
			chema += ":raw";
		}
		return chema + split + res;
	}

	/**
	 * 创建darawable图片的路径
	 * @param drawableid
	 * @return
	 */
	public static String createDrawableImageURI(int drawableid) {
		return createImageURI("" + drawableid, 1);
	}

	/**
	 * 创建Assest图片路径
	 * @param imagename
	 * @return
	 */
	public static String createAssestImageURI(String imagename) {
		return createImageURI(imagename, 2);
	}

	/**
	 * 创建raw图片路径
	 * @param rawid
	 * @return
	 */
	public static String createRawImageURI(int rawid) {
		return createImageURI("" + rawid, 3);
	}

	/**
	 * 创建ImageURI
	 * @param url   网络地址
	 * @return
	 */
	public static String createImageURI(String url) {
		String chema = "net";
		return chema + split + url;
	}

	/**
	 * 创建ImageURI
	 * @param filePath
	 * @return
	 */
	public static String createImageURIFromFile(String filePath) {
		String chema = "file";
		return chema + split + filePath;
	}

	/**
	 * 创建ImageURI
	 * @param file
	 * @return
	 */
	public static String createImageURIFromFile(File file) {
		String chema = "file";
		return chema + split + file.getAbsolutePath();
	}

	public static class ReturnResult<T> {
		public boolean hasRunThread = false;
		public T resutl;
		public String uuid;

		public ReturnResult(boolean has, T res) {
			hasRunThread = has;
			resutl = res;
		}
	}

	static String createUUID() {
		return UUID.randomUUID().toString();
	}

	// final static UIConsisident UI = new UIConsisident();

	public static ReturnResult<File> getImageFile(String imageURI) {
		if (null == imageURI || imageURI.trim().equals("")) {
			return null;
		}
		String[] array = imageURI.split(split);
		if (array == null || array.length < 2) {
			return null;
		}
		String shema = array[0];
		String url = array[1];
		if ("file".equals(shema)) {
			// 文件图片
			return new ReturnResult<File>(false, new File(url));
		}
		if ("net".equals(shema)) {
			// 网络图片
			String uuid = createUUID();
			ReturnResult<File> rrt = new ReturnResult<File>(true, null);
			rrt.uuid = uuid;
			return rrt;
		}
		return null;
	}

	public static ReturnResult<InputStream> getImageStream(String imageURI, Context context) throws IOException {
		if (null == imageURI || imageURI.trim().equals("")) {
			return null;
		}
		String[] array = imageURI.split(split);
		if (array == null || array.length < 2) {
			return null;
		}
		String schema = array[0];
		String url = array[1];
		if ("file".equals(schema)) {
			// 文件图片
			return new ReturnResult<InputStream>(false, new FileInputStream(new File(url)));
		}
		if (schema.contains("res")) {
			String type = schema.split(":")[1];
			if ("assest".equals(type)) {
				return new ReturnResult<InputStream>(false, context.getAssets().open(url));
			}
			if ("raw".equals(type)) {
				return new ReturnResult<InputStream>(false,
						context.getResources().openRawResource(Integer.parseInt(url)));
			}
		}
		if ("net".equals(schema)) {
			// 网络图片
			final String uuid = createUUID();
			LineServiceImpl line = new LineServiceImpl();
			try {
				line.downLoadFile(url, null, null, null, new RequestCallBack<File>() {
					@Override
					public void error(int code, WayFrom from) {

					}

					@Override
					public void callBack(File request, WayFrom from) {
						try {
							UIConsisident.putInCurrentThread(uuid, new FileInputStream(request));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			ReturnResult<InputStream> rrt = new ReturnResult<InputStream>(true, null);
			rrt.uuid = uuid;
			return rrt;
		}
		return null;
	}

	public static Drawable getImageDrawable(String imageURI, Context context) {
		if (null == imageURI || imageURI.trim().equals("")) {
			return null;
		}
		if (!imageURI.contains("res:drawable" + split)) {
			return null;
		}
		String[] array = imageURI.split(split);
		if (array == null || array.length < 2) {
			return null;
		}
		String url = array[1];
		return context.getResources().getDrawable(Integer.parseInt(url));
	}

	public static ReturnResult<Bitmap> getBitmap(String imageURI, Context context) throws IOException {
		ReturnResult<InputStream> imageStream = getImageStream(imageURI, context);
		if (null == imageStream) {
			return null;
		}
		if (null == imageStream.resutl) {
			return new ReturnResult<Bitmap>(true, null);
		}
		return new ReturnResult<Bitmap>(false, BitmapFactory.decodeStream(imageStream.resutl));
	}

	/**
	 * 加载图片
	 * @param imageURI
	 * @param context
	 * @param view
	 * @throws IOException 
	 */
	public static void loadImage(String imageURI, final Context context, final View view) throws IOException {
		Drawable drawable = getImageDrawable(imageURI, context);
		if (null != drawable) {
			view.setBackground(drawable);
			return;
		}

		ReturnResult<InputStream> imageStream = getImageStream(imageURI, context);
		if (imageStream == null) {
			return;
		}
		if (imageStream.resutl != null) {
			view.setBackground(
					new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(imageStream.resutl)));
			return;
		}
		// 网络加载的图片
		UIConsisident.putCall(imageStream.uuid, new CallFromThread() {
			@Override
			public void call(Object obj) {
				view.setBackground(
						new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream((InputStream) obj)));
			}
		});
	}

	public static void loadImage(String imageURI, Context context, final ImageView view) throws IOException {
		Drawable drawable = getImageDrawable(imageURI, context);
		if (null != drawable) {
			view.setImageDrawable(drawable);
			return;
		}

		ReturnResult<InputStream> imageStream = getImageStream(imageURI, context);
		if (imageStream == null) {
			return;
		}
		if (imageStream.resutl != null) {
			view.setImageBitmap(BitmapFactory.decodeStream(imageStream.resutl));
			return;
		}
		// 网络加载的图片
		UIConsisident.putCall(imageStream.uuid, new CallFromThread() {
			@Override
			public void call(Object obj) {
				view.setImageBitmap(BitmapFactory.decodeStream((InputStream) obj));
			}
		});
	}
}
