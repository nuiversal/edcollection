package com.winway.android.edcollection.datacenter.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.datacenter.api.DataCenter.CallBack;
import com.winway.android.edcollection.datacenter.entity.edp.OrgTreeResult;
import com.winway.android.edcollection.datacenter.loader.LoaderConfig;
import com.winway.android.edcollection.datacenter.loader.LoaderInit;
import com.winway.android.edcollection.login.entity.EdpOrgInfoEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.ewidgets.net.NetManager;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.util.FileUtil;

import android.content.Context;
import android.os.Looper;

/**
 * 机构信息中心
 * @author mr-lao
 *
 */
public class OrgCenter {
	private Context context;
	private BaseDBUtil dbUtil;
	private BaseDBUtil globalDbUtil;
	private BaseService netService = null;

	public OrgCenter(Context context, String dbUrl) {
		if (dbUrl == null) {
			dbUrl = LoaderConfig.dbUrl;
		}
		dbUtil = new BaseDBUtil(context, new File(dbUrl));
		init(context, dbUtil);
	}

	public OrgCenter(Context context, BaseDBUtil dbUtil) {
		init(context, dbUtil);
	}

	public OrgCenter(Context context) {
		this.dbUtil = new BaseDBUtil(context,
				new File(FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db"));
		init(context, dbUtil);
	}

	void init(Context context, BaseDBUtil dbUtil) {
		this.context = context;
		this.dbUtil = dbUtil;
		this.globalDbUtil = new BaseDBUtil(context, new File(FileUtil.AppRootPath + "/db/common/global.db"));
		initLoader();
		initNetService();
	}

	private void initLoader() {
		LoaderInit.initGetOrgTreeLoader(context);
	}

	private void initNetService() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			// 采用离线优先加载方式
			netService = NetManager.getTreeLoderService(NetManager.DATA_PACKET_FIRST);
		} else {
			// 工作线程，只能使用离线
			netService = NetManager.getDataPackageService(context);
		}
	}

	/**
	 * 获得机构树
	 * @param orgId
	 * @param logicSysNo
	 * @param credit
	 * @param callback
	 */
	public void getOrgTree(String orgId, String logicSysNo, String credit, final CallBack<OrgTreeResult> callback) {
		if (null == callback || orgId == null) {
			return;
		}
		String url = GlobalEntry.loginServerUrl + "edp";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "getorgtree");
		params.put("credit", credit + "");
		params.put("orgid", orgId + "");
		params.put("logic_sys_no", logicSysNo + "");
		BaseService.RequestCallBack<OrgTreeResult> netcall = new RequestCallBack<OrgTreeResult>() {
			@Override
			public void error(int code,BaseService.WayFrom from) {
				callback.call(null);
			}

			@Override
			public void callBack(OrgTreeResult request,BaseService.WayFrom from) {
				callback.call(request);
			}
		};
		try {
			netService.getRequest(url, params, null, OrgTreeResult.class, netcall);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得机构下的所有变电站信息列表
	 *  orgId=机构ID值
	 * @param orgId
	 * @return
	 */
	public List<EcSubstationEntity> getSubStationsList(String orgId) {
		String expr = "ORGID='" + orgId + "'";
		List<EcSubstationEntity> subList;
		try {
			subList = dbUtil.excuteWhereQuery(EcSubstationEntity.class, expr);
			if (subList != null && subList.size() > 0) {
				return subList;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final static HashMap<String, Object> cacheMap = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	static <T> T getFromCache(String key) {
		if (cacheMap.containsKey(key)) {
			return (T) cacheMap.get(key);
		}
		return null;
	}

	/**
	 * 根据项目id获取项目名称
	 * @param prjid
	 */
	public EmProjectEntity getPrjName(String prjid) {
		String key = "getPrjName_" + prjid;
		EmProjectEntity entity = getFromCache(key);
		if (null != entity) {
			return entity;
		}
		try {
			entity = globalDbUtil.getById(EmProjectEntity.class, prjid);
			if (entity == null) {
				return null;
			}
			cacheMap.put(key, entity);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * 根据
	 * @param orgid
	 * @return
	 */
	public EdpOrgInfoEntity getOrgName(String orgid) {
		String key = "getOrgName_" + orgid;
		EdpOrgInfoEntity entity = getFromCache(key);
		if (null != entity) {
			return entity;
		}
		try {
			entity = globalDbUtil.getById(EdpOrgInfoEntity.class, orgid);
			if (entity == null) {
				return null;
			}
			cacheMap.put(key, entity);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return entity;

	}
}
