package com.winway.android.edcollection.datacenter.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.datacenter.entity.EmMembersItem;
import com.winway.android.edcollection.datacenter.entity.GetMemberListResult;
import com.winway.android.edcollection.project.entity.EmMembersEntity;
import com.winway.android.ewidgets.net.service.impl.BaseDataPackageLoader;
import com.winway.android.util.GsonUtils;

/**
 * 1.3获取工程成员列表
 * 
 * @author lyh
 * @version 创建时间：2017年1月3日 下午4:05:38
 * 
 */
public class GetPrjUsersLoader extends BaseDataPackageLoader {
	Context context;
	BaseBll<EmMembersEntity> bll;

	public GetPrjUsersLoader(Context context, BaseBll<EmMembersEntity> bll) {
		super();
		this.context = context;
		this.bll = bll;
		if (null == bll) {
			String dbUrl = LoaderConfig.dbUrl;
			this.bll = new BaseBll<EmMembersEntity>(context, dbUrl,
					EmMembersEntity.class);
		}
	}

	@Override
	public boolean canProcessThis(String url, Map<String, String> params,
			Map<String, String> heades) {
		if (params != null && !params.isEmpty()) {
			String action = params.get("action");
			if (("em_getprjusers").equals(action)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void getRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call)
			throws Exception {
		// TODO Auto-generated method stub
		GetMemberListResult result = new GetMemberListResult();
		Gson gson = GsonUtils.build();
		String json = gson.toJson(result);
		String id = params.get("prj");
		if (id == null) {
			result.setCode(-1);
			result.setMsg("获取项工程成员列表失败!prj不能为空");
			call.callBack(json,WayFrom.DATA_PACKAGE);
		}
		String expr = "EM_PROJECT_ID='" + id + "'";
		List<EmMembersEntity> queryByExpr = bll.queryByExpr(
				EmMembersEntity.class, expr);
		if (queryByExpr != null && queryByExpr.size() > 0) {
			List<EmMembersItem> list = new ArrayList<EmMembersItem>();
			for (EmMembersEntity en : queryByExpr) {
				EmMembersItem object = new EmMembersItem();
				object.setId(Integer.valueOf(en.getEmMembersId()));
				object.setUserid(en.getUserName());
				object.setName(en.getUserName());
				object.setRid(en.getRoleId());
				object.setRname(en.getRoleName());
				list.add(object);
			}
			result.setCode(0);
			result.setMsg("获取成功");
			result.setList(list);
		} else {
			result.setCode(-1);
			result.setMsg("获取项工程成员列表失败!");
		}
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
