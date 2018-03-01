package com.winway.android.edcollection.datacenter.loader;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.dto.EmProjectEntityDto;
import com.winway.android.edcollection.project.dto.GetProjectListResult;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.ewidgets.net.service.impl.BaseDataPackageLoader;
import com.winway.android.util.GsonUtils;

/**
 * 1.1获取工程作业信息
 * 
 * @author lyh
 * @version 创建时间：2017年1月3日 上午11:32:31
 * 
 */
public class GetprojectsInfoLoader extends BaseDataPackageLoader {
	BaseBll<EmProjectEntity> bll;
	BaseBll<EmProjectEntityDto> bllDto;
	Context context;

	public GetprojectsInfoLoader(Context context, BaseBll<EmProjectEntity> bll) {
		super();
		this.bll = bll;
		this.context = context;
		if (null == bll) {
			String dbUrl = LoaderConfig.dbUrl;
			this.bll = new BaseBll<EmProjectEntity>(context, dbUrl, EmProjectEntity.class);
		}
		if (null == bllDto) {
			String dbUrl = LoaderConfig.dbUrl;
			this.bllDto = new BaseBll<EmProjectEntityDto>(context, dbUrl, EmProjectEntityDto.class);
		}
	}

	@Override
	public boolean canProcessThis(String url, Map<String, String> params, Map<String, String> heades) {
		if (params != null && !params.isEmpty()) {
			String action = params.get("action");
			if ("em_getprojects".equals(action)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void getRequestString(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception {
		GetProjectListResult result = new GetProjectListResult();
		String name = params.get("name");
		String expr = "PRJ_NAME='" + name + "'";
		List<EmProjectEntityDto> queryByExpr = bllDto.queryByExpr(EmProjectEntityDto.class, expr);
		if (queryByExpr != null && queryByExpr.size() > 0) {
			result.setCode(0);
			result.setMsg("获取成功");
			result.setList(queryByExpr);
		} else {
			result.setCode(-1);
			result.setMsg("获取工程作业信息失败!");
		}
		Gson gson = GsonUtils.build();
		String json = gson.toJson(result);
		call.callBack(json, WayFrom.DATA_PACKAGE);
	}

	@Override
	public void getRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void downLoadFile(String url, Map<String, String> params, Map<String, String> headers, String filepath,
			RequestCallBack<File> call) throws Exception {
		// TODO Auto-generated method stub

	}

}
