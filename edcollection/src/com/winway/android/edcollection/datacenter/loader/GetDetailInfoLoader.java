package com.winway.android.edcollection.datacenter.loader;

import java.io.File;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.datacenter.entity.GetProjectMoreInfoListResult;
import com.winway.android.edcollection.project.entity.EmProjectMoreinfoEntity;
import com.winway.android.ewidgets.net.service.impl.BaseDataPackageLoader;
import com.winway.android.util.GsonUtils;

/**
 * 1.2获取工程详情
 * 
 * @author lyh
 * @version 创建时间：2017年1月3日 下午3:40:31
 * 
 */
public class GetDetailInfoLoader extends BaseDataPackageLoader {
	BaseBll<EmProjectMoreinfoEntity> bll;
	Context context;

	public GetDetailInfoLoader(Context context, BaseBll<EmProjectMoreinfoEntity> bll) {
		super();
		this.bll = bll;
		this.context = context;
		if (null == bll) {
			String dbUrl = LoaderConfig.dbUrl;
			this.bll = new BaseBll<EmProjectMoreinfoEntity>(context, dbUrl,
					EmProjectMoreinfoEntity.class);
		}
	}
	
	@Override
	public boolean canProcessThis(String url, Map<String, String> params,
			Map<String, String> heades) {
		if (params != null && !params.isEmpty()) {
			String action = params.get("action");
			if (("em_getdetailinfo").equals(action)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void getRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call)
			throws Exception {
		String id = params.get("prj");
		GetProjectMoreInfoListResult result = new GetProjectMoreInfoListResult();
		EmProjectMoreinfoEntity entity = bll.findById(id);
		if (entity != null) {
			result.setCode(0);
			result.setMsg("获取成功");
			result.setPrjdetail(entity);
		} else {
			result.setCode(-1);
			result.setMsg("获取项目详细信息失败!");
		}
		Gson gson = GsonUtils.build();
		String json = gson.toJson(result);
		call.callBack(json,WayFrom.DATA_PACKAGE);
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
