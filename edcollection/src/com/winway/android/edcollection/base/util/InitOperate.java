package com.winway.android.edcollection.base.util;

import java.io.File;

import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.util.FileUtil;

import android.content.Context;

/**
 * 初始化app有关操作
 * 
 * @author zgq
 *
 */
public class InitOperate {
	private static InitOperate instance;

	public static InitOperate getInstance() {
		if (instance == null) {
			synchronized (InitOperate.class) {
				if (instance == null) {
					instance = new InitOperate();
				}
			}
		}
		return instance;
	}

	/**
	 * 创建项目相关的文件夹
	 */
	public void createFolder() {
		if (FileUtil.isSDCardEnable()) {
			FileUtil.AppRootPath = FileUtil.getSDCardPath() + "winway_edcollection" + File.separator;
			// 创建地图相关路径
			FileUtil.createPath(FileUtil.getSDCardPath() + "winway_edcollection");
			FileUtil.createPath(FileUtil.AppRootPath + File.separator + "winway_maps");
			FileUtil.createPath(FileUtil.AppRootPath + File.separator + "winway_maps" + File.separator + "maps");
			FileUtil.createPath(FileUtil.AppRootPath + File.separator + "winway_maps" + File.separator + "cache");
			FileUtil.createPath(FileUtil.AppRootPath + File.separator + "winway_maps" + File.separator + "maps"
					+ File.separator + "airscape");
			FileUtil.createPath(FileUtil.AppRootPath + File.separator + "winway_maps" + File.separator + "maps"
					+ File.separator + "typical");
			FileUtil.createPath(FileUtil.AppRootPath + File.separator + "winway_maps" + File.separator + "maps"
					+ File.separator + "layers");
			FileUtil.createPath(FileUtil.AppRootPath + File.separator + "winway_maps" + File.separator + "maps"
					+ File.separator + "layers" + File.separator + "tdtTextLayer");
			// apk更新存放目录
			FileUtil.createPath(FileUtil.AppRootPath + "/update_apk");
			// 日志目录
			FileUtil.createPath(FileUtil.AppRootPath + "/log");
			// 日志crash目录
			FileUtil.createPath(FileUtil.AppRootPath + "/log/crash");
			// 创建db存放目录
			FileUtil.createPath(FileUtil.AppRootPath + "/db");
			// 公共db
			FileUtil.createPath(FileUtil.AppRootPath + "/db/common");
			// 项目db
			FileUtil.createPath(FileUtil.AppRootPath + "/db/project");
			// 图片目录
//			FileUtil.createPath(FileUtil.AppRootPath + "/picture");
			// 通道截面图片目录
//			FileUtil.createPath(FileUtil.AppRootPath + "/picture/section");
			// 下载目录
			FileUtil.createPath(FileUtil.AppRootPath + "/download");
			FileUtil.createPath(FileUtil.AppRootPath + "/download/offline-attach");
			//导出目录
			FileUtil.createPath(FileUtil.AppRootPath + "/export");
		}

	}

	/**
	 * 创建全局的db
	 * 
	 * @param context
	 * @param dbUrl
	 */
	public void createGlobalDb(Context context, String dbUrl) {
		try {
			new BaseBll<Object>(context, dbUrl);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
