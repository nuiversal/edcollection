package com.winway.android.edcollection.datacenter.loader;

import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.login.entity.EdpOrgInfoEntity;
import com.winway.android.edcollection.project.entity.EcMarkerIdsEntity;
import com.winway.android.ewidgets.net.service.impl.DataPackageServiceImpl;
import com.winway.android.util.FileUtil;

import android.content.Context;

/**
 * 离线数据包加载器初始化
 * @author mr-lao
 *
 */
public class LoaderInit {

	static boolean hadInit = false;

	public static void init(Context context, String dbUrl) {
		if (hadInit) {
			return;
		}
		initGetOrgTreeLoader(context);
		initVerifyIDLoader(context, dbUrl);
	}

	static boolean hadInitOrgTree = false;

	/**
	 * 初始化机构树加载器
	 * @param context
	 */
	public static void initGetOrgTreeLoader(Context context) {
		if (hadInitOrgTree) {
			return;
		}
		/**添加机构数据加载器*/
		BaseBll<EdpOrgInfoEntity> orgBll = new BaseBll<EdpOrgInfoEntity>(context,
				FileUtil.AppRootPath + "/db/common/global.db", EdpOrgInfoEntity.class);
		GetOrgTreeLoader orgTreeLoader = new GetOrgTreeLoader(orgBll);
		DataPackageServiceImpl.addSystemDataPackageLoader(orgTreeLoader);
		/**加载完成*/
		hadInitOrgTree = true;
	}

	static boolean hadInitVerifyID = false;

	/**
	 * 初始化ID验证加载器
	 * @param context
	 * @param dbUrl
	 */
	public static void initVerifyIDLoader(Context context, String dbUrl) {
		if (hadInitVerifyID) {
			return;
		}
		BaseBll<EcMarkerIdsEntity> bll = new BaseBll<EcMarkerIdsEntity>(context, dbUrl,
				EcMarkerIdsEntity.class);
		VerifyIDLoader verifyIDLoader = new VerifyIDLoader(context, bll);
		DataPackageServiceImpl.addSystemDataPackageLoader(verifyIDLoader);
		hadInitVerifyID = true;
	}

}
