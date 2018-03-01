package com.winway.android.edcollection.about.impl;

import com.winway.android.edcollection.about.IAbout;
import com.winway.android.edcollection.about.entity.DBEnum;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.dbVersion.IDbVersion;
import com.winway.android.edcollection.base.dbVersion.impl.DbVersionImpl;
import com.winway.android.edcollection.project.entity.CDbVersionEntity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * IAbout实现类
 * 
 * @author xs
 *
 */
public class AboutImpl implements IAbout {
	private IDbVersion dbVersion = null;

	public AboutImpl() {
		dbVersion = new DbVersionImpl();
	}

	@Override
	public String getAppVersion(Activity mActivity) {
		PackageManager manager = mActivity.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(mActivity.getPackageName(), 0);
			return "v" + info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getEcloudDBVersion(Activity mActivity) {
		CDbVersionEntity ecloudDBVersionEntity = dbVersion.getDbVersion(mActivity, GlobalEntry.prjDbUrl,
				DBEnum.ECLOUD.getValue());
		return "v" + ecloudDBVersionEntity.getVersion1() + "." + ecloudDBVersionEntity.getVersion2() + "."
				+ ecloudDBVersionEntity.getVersion3();
	}

	@Override
	public String getEDDBVersion(Activity mActivity) {
		CDbVersionEntity edDBVersionEntity = dbVersion.getDbVersion(mActivity, GlobalEntry.prjDbUrl,
				DBEnum.ED.getValue());
		return "v" + +edDBVersionEntity.getVersion1() + "." + edDBVersionEntity.getVersion2() + "."
				+ edDBVersionEntity.getVersion3();
	}

	@Override
	public String getAppName(Activity mActivity) {
		PackageManager manager = mActivity.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(mActivity.getPackageName(), 0);
			int labelRes = info.applicationInfo.labelRes;
			return mActivity.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
