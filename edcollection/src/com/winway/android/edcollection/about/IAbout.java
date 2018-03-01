package com.winway.android.edcollection.about;

import android.app.Activity;

/**
 * 关于页面相关接口
 * 
 * @author xs
 *
 */
public interface IAbout {
	/**
	 * 获取APP版本信息
	 * 
	 * @param mActivity
	 * @return
	 */
	String getAppVersion(Activity mActivity);

	/**
	 * 获取EcloudDB版本信息
	 * 
	 * @param mActivity
	 * @return
	 */
	String getEcloudDBVersion(Activity mActivity);

	/**
	 * 获取EDDB版本信息
	 * 
	 * @param mActivity
	 * @return
	 */
	String getEDDBVersion(Activity mActivity);

	/**
	 * 获取APP名称
	 * 
	 * @param mActivity
	 * @return
	 */
	String getAppName(Activity mActivity);
}
