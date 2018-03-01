package com.winway.android.edcollection.datacenter.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.dto.MessageBase;
import com.winway.android.edcollection.project.entity.EmProjectMoreinfoEntity;
import com.winway.android.ewidgets.net.service.impl.BaseDataPackageLoader;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.UUIDGen;

/**
 * @author lyh
 * @version 创建时间：2017年1月4日 上午11:53:09
 * 
 */
public class UpdateProjectLoader extends BaseDataPackageLoader {

	Context context;
	BaseBll<EmProjectMoreinfoEntity> bll;

	public UpdateProjectLoader(Context context,
			BaseBll<EmProjectMoreinfoEntity> bll) {
		super();
		this.context = context;
		this.bll = bll;
		if (null == bll) {
			String dbUrl = LoaderConfig.dbUrl;
			this.bll = new BaseBll<EmProjectMoreinfoEntity>(context, dbUrl,
					EmProjectMoreinfoEntity.class);
		}
	}

	@Override
	public boolean canProcessThis(String url, Map<String, String> params,
			Map<String, String> heades) {
		// TODO Auto-generated method stub
		if (params != null && !params.isEmpty()) {
			String action = params.get("action");
			if (("em_updateproject").equals(action)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void getRequestString(String url, Map<String, String> params,
			Map<String, String> headers, RequestCallBack<String> call)
			throws Exception {
		MessageBase msg = new MessageBase();
		String type = params.get("type");
		String data = params.get("data");
		Gson gson = GsonUtils.build();
		String json = gson.toJson(msg);
		if (type == null
				|| (!type.equals("1") && !type.equals("2") && !type.equals("3"))) {
			msg.setCode(-1);
			msg.setMsg("无效数据类型,请确定数据类型type为1|2|3");
			call.callBack(json,WayFrom.DATA_PACKAGE);
		}
		List<EmProjectMoreinfoEntity> entities = new ArrayList<EmProjectMoreinfoEntity>();
		for (int i = 0; i < 1; i++) {
			EmProjectMoreinfoEntity entity = new EmProjectMoreinfoEntity();
			entity.setContractNo("100");
			entity.setWorkZone("ddd");
			entity.setTaskTypes("dddddd");
			entities.add(entity);
		}
		data = gson.toJson(entities);
		EmProjectMoreinfoEntity entity = gson.fromJson(data,
				EmProjectMoreinfoEntity.class);
		
		if (entity == null) {
			msg.setCode(-1);
			msg.setMsg("gson转entity失败");
			call.callBack(json,WayFrom.DATA_PACKAGE);
		}
		// 新增
		if (type.equals("1")) {
			entity.setEmProjectId(UUIDGen.randomUUID());
			boolean saveEntity = bll.save(entity);
			resultMessage(saveEntity, call, msg, json);
		}
		// 修改
		if (type.equals("2")) {
			if (entity.getEmProjectId().trim() == null) {
				msg.setCode(0);
				msg.setMsg("无效的项目id,请检查");
				call.callBack(json,WayFrom.DATA_PACKAGE);
			}
			boolean updateEntity = bll.update(entity);
			resultMessage(updateEntity, call, msg, json);
		}
		// 删除
		if (type.equals("3")) {
			if (entity.getEmProjectId().trim() == null) {
				msg.setCode(0);
				msg.setMsg("无效的项目id,请检查");
				call.callBack(json,WayFrom.DATA_PACKAGE);
			}
			boolean deleteById = bll.deleteById(entity.getEmProjectId());
				resultMessage(deleteById, call, msg, json);
		}
	}

	private void resultMessage(Boolean b, RequestCallBack<String> call, MessageBase msg,
			String json) {
		if (b) {
			msg.setCode(0);
			msg.setMsg("修改成功");
			call.callBack(json,WayFrom.DATA_PACKAGE);
		}else {
			msg.setCode(-1);
			msg.setMsg("修改失败");
			call.callBack(json,WayFrom.DATA_PACKAGE);
		}
		
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
