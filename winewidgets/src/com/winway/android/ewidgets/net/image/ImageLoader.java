package com.winway.android.ewidgets.net.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.BaseService.WayFrom;
import com.winway.android.util.DigestUtils;
import com.winway.android.util.ImageHelper;
import com.winway.android.util.LogUtil;
import com.winway.android.util.ThreadPoolUtil;
import com.winway.android.util.ThreadPoolUtil.AsynCirculation;
import com.winway.android.util.UUIDGen;

/**
 * 支持离线图片的ImageLoder
 * @note 此图片下载器，不支持后台下载，开发者请控制好Activity销毁可能产生的图片不完整问题
 * @author mr-lao
 *
 */
public class ImageLoader {
	public final String UUID;
	private BaseService net;
	private HashMap<String, File> imageFileCache;
	private ImageLoaderConfig defaultImageLoaderConfig;
	// 缩略图存放目录
	private String scaleImageDir;
	/**等待时间8分钟*/
	public int waitTime = 480000;
	private boolean needDestroy = false;

	/**
	 * 销毁加载器
	 */
	public void destroy() {
		needDestroy = true;
	}

	public void setScaleImageDir(String scaleImageDir) {
		this.scaleImageDir = scaleImageDir;
	}

	public ImageLoader(BaseService net, ImageLoaderConfig config, String uuid) {
		this.net = net;
		if (TextUtils.isEmpty(uuid)) {
			this.UUID = UUIDGen.randomUUID();
		} else {
			this.UUID = uuid;
		}
		init(config);
	}

	public ImageLoader(BaseService net, String uuid) {
		this.net = net;
		if (TextUtils.isEmpty(uuid)) {
			this.UUID = UUIDGen.randomUUID();
		} else {
			this.UUID = uuid;
		}
		init(null);
	}

	void init(ImageLoaderConfig config) {
		if (null == config) {
			createDefaultImageConfig();
		} else {
			defaultImageLoaderConfig = config;
		}
		imageFileCache = new HashMap<>();
	}

	private void createDefaultImageConfig() {
		defaultImageLoaderConfig = new ImageLoaderConfig();
		defaultImageLoaderConfig.imageWidth = 400;
		defaultImageLoaderConfig.imageHeight = 400;
	}

	/**
	 * 下载队列
	 */
	private static HashMap<String, ArrayList<String>> downLoadUrlmap = new HashMap<String, ArrayList<String>>();

	private void putLoadUrl(String url) {
		ArrayList<String> urlList = downLoadUrlmap.get(UUID);
		if (null == urlList) {
			urlList = new ArrayList<String>();
			downLoadUrlmap.put(UUID, urlList);
		}
		urlList.add(url);
	}

	private void removeLoadUrl(String url) {
		ArrayList<String> urlList = downLoadUrlmap.get(UUID);
		if (null != urlList) {
			urlList.remove(url);
			if (urlList.isEmpty()) {
				downLoadUrlmap.remove(UUID);
			}
		}
	}

	/**
	 * 判断图片是否还在下载
	 * @param url
	 * @return
	 */
	private boolean isLoadingThisUrl(String url) {
		if (downLoadUrlmap.isEmpty()) {
			return false;
		}
		if (downLoadUrlmap.containsKey(UUID)) {
			return downLoadUrlmap.get(UUID).contains(url);
		}
		Set<String> keySet = downLoadUrlmap.keySet();
		for (String uuid : keySet) {
			ArrayList<String> urlList = downLoadUrlmap.get(uuid);
			if (urlList == null || urlList.isEmpty()) {
				continue;
			}
			if (urlList.contains(url)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断加载器是否拥有下载任务
	 * @return
	 */
	public boolean hasDownLoadTask() {
		ArrayList<String> urlList = downLoadUrlmap.get(UUID);
		if (null != urlList) {
			return true;
		}
		return false;
	}

	/**
	 * 开始下载图片
	 * @param imageview
	 * @param url
	 * @param filepath
	 * @param config
	 */
	private void startLoadImageFile(final ImageView imageview, final String url, final String filepath,
			final ImageLoaderConfig config) {
		// downLoadUrlList.add(url);
		putLoadUrl(url);
		try {
			net.downLoadFile(url, null, null, filepath, new BaseService.RequestCallBack<File>() {
				@Override
				public void error(int code, WayFrom from) {
					// downLoadUrlList.remove(url);
					removeLoadUrl(url);
				}

				@Override
				public void callBack(File request, WayFrom from) {
					// downLoadUrlList.remove(url);
					removeLoadUrl(url);
					imageFileCache.put(url, request);
					if (imageview.getTag().equals(filepath) || url.equals(imageview.getTag())) {
						showImage(imageview, url, config);
					}
				}
			});
		} catch (Exception e) {
			// downLoadUrlList.remove(url);
			removeLoadUrl(url);
			e.printStackTrace();
		}
	}

	static String LOCK = "";

	// 获取或者创建缩略图
	private Bitmap getOrCreateScaleBitmap(ImageLoaderConfig conf, File file) throws IOException {
		String scaleFilePath = null;
		if (!TextUtils.isEmpty(conf.scaleImageDir)) {
			scaleFilePath = conf.scaleImageDir + "/" + DigestUtils.md5(file.getAbsolutePath()) + ".jpg";
		} else if (!TextUtils.isEmpty(scaleImageDir)) {
			scaleFilePath = scaleImageDir + "/" + DigestUtils.md5(file.getAbsolutePath()) + ".jpg";
		} else {
			scaleImageDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/winway/scale-image";
			scaleFilePath = scaleImageDir + "/" + DigestUtils.md5(file.getAbsolutePath()) + ".jpg";
		}
		File scaleFile = null;
		if (scaleFilePath != null) {
			scaleFile = new File(scaleFilePath);
			if (scaleFile.exists()) {
				// 检查文件是否损坏
				if (checkImageFileIdDamage(scaleFile.getAbsolutePath())) {
					scaleFile.delete();
				} else {
					return BitmapFactory.decodeFile(scaleFilePath);
				}
			}
		}
		// 进行线程同步，防止创建缩略图片出错
		synchronized (LOCK) {
			if (!scaleFile.exists()) {
				if (!scaleFile.getParentFile().exists()) {
					scaleFile.getParentFile().mkdirs();
				}
				scaleFile.createNewFile();
				Bitmap bitmap = ImageHelper.decodeSampledBitmapFromImagePath(file.getAbsolutePath(), conf.imageWidth,
						conf.imageHeight);
				if (null != scaleFile && bitmap != null) {
					saveScaleBitmapToFile(bitmap, scaleFile);
					/*saveScaleBitmapToFile(file, scaleFile, conf.imageWidth, conf.imageHeight);*/
				}
				return bitmap;
			} else {
				return BitmapFactory.decodeFile(scaleFile.getAbsolutePath());
			}
		}
	}

	/**
	 * 保存图片缩略图
	 * @param sourceFile
	 * @param scaleFile
	 * @param width
	 * @param height
	 */
	@SuppressWarnings("unused")
	private void saveScaleBitmapToFile(final File sourceFile, final File scaleFile, final int width, final int height) {
		ThreadPoolUtil.excute(new Runnable() {
			@Override
			public void run() {
				try {
					Bitmap bitmap = ImageHelper.decodeSampledBitmapFromImagePath(sourceFile.getAbsolutePath(), width,
							height);
					bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(scaleFile));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 保存图片缩略图
	 * @param bitmap
	 * @param scaleFile
	 */
	private void saveScaleBitmapToFile(final Bitmap bitmap, final File scaleFile) {
		ThreadPoolUtil.excute(new Runnable() {
			@Override
			public void run() {
				try {
					bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(scaleFile));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 判断文件是否已经损坏
	 * @param imgFile
	 * @return
	 */
	static boolean checkImageFileIdDamage(String imgFile) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imgFile, options); // filePath代表图片路径
		if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
			// 表示图片已损毁
			return true;
		}
		return false;
	}

	/**
	 * 显示图片
	 * @param imageview  图片控件
	 * @param url   图片的URL地址
	 * @param config   图片的显示配置
	 */
	private void showImage(ImageView imageview, String url, ImageLoaderConfig config) {
		synchronized (url) {
			File imageFile = imageFileCache.get(url);
			// 检查图片文件是否不正常，不正常则重新下载
			if (checkImageFileIdDamage(imageFile.getAbsolutePath())) {
				String filepath = imageFile.getAbsolutePath();
				imageFile.delete();
				startLoadImageFile(imageview, url, filepath, config);
				return;
			}
			ImageLoaderConfig conf = config == null ? defaultImageLoaderConfig : config;
			if (conf.needScale) {
				try {
					Bitmap bitmap = getOrCreateScaleBitmap(conf, imageFile);
					if (null != bitmap) {
						imageview.setImageBitmap(bitmap);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
				if (null != bitmap) {
					imageview.setImageBitmap(bitmap);
				}
			}
		}
	}

	/**
	 * 显示图片
	 * @param imageview
	 * @param url    图片下载的URL
	 * @param filepath   如果filepath图片文件存在，则直接显示图片，否则把从URL链接上下载下来之后的图片保存到filepath文件中
	 */
	public void displayIamge(ImageView imageview, String url, String filepath) {
		displayIamge(imageview, url, filepath, defaultImageLoaderConfig);
	}

	/**
	 * 显示图片
	 * @param imageview
	 * @param url  图片下载的URL
	 * @param filepath   如果filepath图片文件存在，则直接显示图片，否则把从URL链接上下载下来之后的图片保存到filepath文件中
	 * @param config   图片显示配置
	 */
	public void displayIamge(ImageView imageview, String url, String filepath, ImageLoaderConfig config) {
		if (TextUtils.isEmpty(filepath)) {
			// 不指定文件下载路径
			imageview.setTag(url);
			if (imageFileCache.containsKey(url)) {
				// 说明文件已经下载过了，直接显示，否则进行下载
				showImage(imageview, url, config);
			} else {
				startLoadImageFile(imageview, url, filepath, config);
			}
		} else {
			// 指定了文件下载路径
			imageview.setTag(filepath);
			File imgFile = new File(filepath);
			if (imgFile.exists()) {
				// 图片文件存在，直接显示
				if (isLoadingThisUrl(url)) {
					// 判断URL指定图片是否正在网络加载
					watingDisplayIamge(imageview, url, filepath, config);
				} else {
					// 文件存在，已经加载完了
					imageFileCache.put(url, imgFile);
					imageview.setTag(filepath);
					showImage(imageview, url, config);
				}
			} else {
				// 图片文件不存在，下载
				startLoadImageFile(imageview, url, filepath, config);
			}
		}
	}

	/**
	 * 等待显示图片
	 * 等待原因：ImageLoader实例loaderA正在加载URL001图片，此时，loaderB实例也要加载URL001图片，为了避免重复的加载，
	 * loaderB要检测loaderA是否已经完成加载，并使用loaderA加载得到的图片显示。
	 * @param imageview
	 * @param url
	 * @param filepath
	 * @param config
	 */
	private void watingDisplayIamge(final ImageView imageview, final String url, final String filepath,
			final ImageLoaderConfig config) {
		ThreadPoolUtil.excuteAsynCirculation(new AsynCirculation() {
			boolean stop = false;
			boolean loadingFinish = false;
			long startTime = 0;

			@Override
			public void runWorkThread() {
				stop = true;
				startTime = System.currentTimeMillis();
				while (System.currentTimeMillis() - startTime < waitTime && !needDestroy) {
					if (isLoadingThisUrl(url)) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						break;
					}
				}
				if (!isLoadingThisUrl(url)) {
					loadingFinish = true;
				}
				LogUtil.i("info", url + "," + loadingFinish);
			}

			@Override
			public void runMainThread() {
				if (loadingFinish && !needDestroy) {
					// 图片文件存在，直接显示
					imageFileCache.put(url, new File(filepath));
					showImage(imageview, url, config);
				}
			}

			@Override
			public boolean needStop() {
				if (needDestroy) {
					return true;
				}
				return stop;
			}
		}, -1);
	}
}
