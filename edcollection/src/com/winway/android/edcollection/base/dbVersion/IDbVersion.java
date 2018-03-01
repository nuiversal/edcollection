package com.winway.android.edcollection.base.dbVersion;

import java.util.List;
import java.util.Map;

import com.winway.android.edcollection.project.entity.CDbVersionEntity;

import android.content.Context;

/**
 * 版本变更
 * 
 * @author winway_zgq
 *
 */
public interface IDbVersion {
	/**
	 * 获取程序中版本列表（assets文件夹）
	 * 
	 * @param context
	 * @param fileName
	 *            如：app_ecloud_version.xml
	 * @return
	 */
	List<String> getAppDbVersions(Context context, String fileName);

	/**
	 * 获取db数据库最新版本
	 * 
	 * @param context
	 * @param dbUrl
	 *            db路径
	 * @param componentNo
	 *            组件编号
	 * @return
	 */
	CDbVersionEntity getDbVersion(Context context, String dbUrl, String componentNo);

	/**
	 * 处理db版本升级
	 * 
	 * @param context
	 * @param dbUrl
	 * @param appFileName
	 *            assets文件夹下的文件名(程序版本信息)
	 * @param sqlFoldRootPath
	 *            assets文件夹下的文件目录
	 * @param componentNo
	 *            组件编号
	 * @return
	 */
	boolean handleUpgrade(Context context, String dbUrl, String appFileName, String sqlFoldRootPath,
			String componentNo);
}
