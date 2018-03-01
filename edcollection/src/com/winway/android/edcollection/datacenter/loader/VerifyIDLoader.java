package com.winway.android.edcollection.datacenter.loader;

import java.io.File;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.datacenter.entity.VerityIDResult;
import com.winway.android.edcollection.project.entity.EcMarkerIdsEntity;
import com.winway.android.ewidgets.net.service.impl.BaseDataPackageLoader;
import com.winway.android.util.GsonUtils;

/**
 * 验证标识器ID
 * 
 * @author lyh
 * @version 创建时间：2016年12月30日 下午2:15:23
 * 
 */
public class VerifyIDLoader extends BaseDataPackageLoader {
	Context context;
	BaseBll<EcMarkerIdsEntity> bll;

	public VerifyIDLoader(Context context, BaseBll<EcMarkerIdsEntity> bll) {
		super();
		this.context = context;
		this.bll = bll;
		if (null == bll) {
			String dbUrl = LoaderConfig.dbUrl;
			this.bll = new BaseBll<EcMarkerIdsEntity>(context, dbUrl,EcMarkerIdsEntity.class);
		}
	}

	@Override
	public void getRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call)
			throws Exception {
		String id = params.get("id");
		EcMarkerIdsEntity findById = bll.findById(id);
		String jsonREST = "";
		VerityIDResult rest = new VerityIDResult();
		if (findById != null) {
			rest.setMsg("数据库存在此ID");
			rest.setUnique(0);
		} else {
			rest.setUnique(1);
		}
		Gson gson = GsonUtils.build();
		jsonREST = gson.toJson(rest);
		call.callBack(jsonREST,WayFrom.DATA_PACKAGE);
	}

	@Override
	public boolean canProcessThis(String url, Map<String, String> params,
			Map<String, String> heades) {
		
		if (params !=null && !params.isEmpty()) {
			String action = params.get("action");
			if ("em_checkmarkerid".equals(action)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void getRequestFile(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<File> call)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void downLoadFile(String url, Map<String, String> params,
			Map<String, String> headers, String filepath,
			RequestCallBack<File> call) throws Exception {
		// TODO Auto-generated method stub

	}

}
