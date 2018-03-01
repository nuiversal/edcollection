package com.winway.android.ewidgets.net.image;

public class ImageLoaderConfig {
	/**
	 * 是否要对图片进行压缩，如果是true，则对图片进行宽度为imageWidth、长度为imageHeight压缩。false直接显示原图。
	 * 默认情况下，此值为true。
	 */
	public boolean needScale = true;
	public int imageWidth;
	public int imageHeight;
	/**
	 * 存放缩略图的文件夹
	 */
	public String scaleImageDir;
}
