package com.winway.android.basicbusiness.business;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.DbException;
import com.winway.android.basicbusiness.entity.EdpParamInfoEntity;
import com.winway.android.basicbusiness.entity.EdpParamTypeInfoEntity;
import com.winway.android.basicbusiness.entity.ParamInfoResult;
import com.winway.android.basicbusiness.entity.ParamTypeResult;
import com.winway.android.basicbusiness.transport.PageDataResult;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.ewidgets.net.service.BaseService.WayFrom;
import com.winway.android.ewidgets.net.service.impl.LineServiceImpl;
import com.winway.android.util.AsynToastUtil;
import com.winway.android.util.GsonUtils;

/**
 * 获取edp相关数据
 * 
 * @author zgq
 *
 */
public class DicsGeter {

	/**
	 * 获取数据字典列表(下载)
	 * 
	 * @param context
	 * @throws Exception 
	 */
	public static void downLoadDics(LineServiceImpl net, String url, final HashMap<String, String> params,
			final BaseDBUtil baseDBUtil) throws Exception {
		net.getRequestString(url, params, null, new RequestCallBack<String>() {
			@Override
			public void error(int code, WayFrom from) {
				AsynToastUtil.getInstance().showToast("获取数据字典失败，请检查网络是否正常或者服务器地址是否正确");
			}

			@Override
			public void callBack(String json, WayFrom from) {
				Gson gson = GsonUtils.build();
				Type listType = new TypeToken<PageDataResult<ParamTypeResult>>() {
				}.getType();
				PageDataResult<ParamTypeResult> pageDataResult = gson.fromJson(json, listType);
				if (pageDataResult == null || pageDataResult.getCode() != 0) {
					AsynToastUtil.getInstance().showToast("获取数据字典失败");
					return;
				}
				if (pageDataResult.getCode() != 0) {
					AsynToastUtil.getInstance().showToast("获取数据字典失败:" + pageDataResult.getMsg());
					return;
				}
				List<ParamTypeResult> list = pageDataResult.getRows();
				if (null == list || list.isEmpty()) {
					if (params.containsKey("typeno")) {
						AsynToastUtil.getInstance().showToast("无【" + params.get("typeno") + "】相关的数据字典");
					} else {
						AsynToastUtil.getInstance().showToast("数据字典下载失败");
					}
					return;
				}
				// 入库
				try {
					saveParamsToDb(baseDBUtil, list);
					if (params.containsKey("typeno")) {
						AsynToastUtil.getInstance().showToast("【" + params.get("typeno") + "】数据字典已经下载完成");
					} else {
						AsynToastUtil.getInstance().showToast("数据字典下载成功");
					}
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * 下载某种类型的数据字典
	 * @time 2017年9月8日 上午11:02:35
	 * @param url
	 * @param credit
	 * @param dicsType
	 * @param baseDBUtil
	 * @param downAnyway
	 * @throws Exception
	 */
	public static void downLoadDics(String url, String credit, String dicsType, BaseDBUtil baseDBUtil,
			boolean downAnyway) throws Exception {
		if (!downAnyway) {
			if (null != getParamTypeById(baseDBUtil, dicsType)) {
				return;
			}
			if (null != getParamTypeByNo(baseDBUtil, dicsType)) {
				return;
			}
		}
		HashMap<String, String> params = new HashMap<>();
		params.put("credit", credit);
		params.put("action", "getdics");
		params.put("typeno", dicsType);
		LineServiceImpl net = new LineServiceImpl();
		downLoadDics(net, url, params, baseDBUtil);
	}

	/**
	 * 下载用户类型的数据字典
	 * @time 2017年9月8日 上午10:02:33
	 * @param url
	 * @param credit
	 * @param baseDBUtil
	 * @param downAnyway   true表示不管如何，都要执行下载操作；false表示如果数据已经存在于缓存当中，就不必再进行下载
	 * @throws Exception
	 */
	public static void downLoadUserDics(String url, String credit, BaseDBUtil baseDBUtil, boolean downAnyway)
			throws Exception {
		downLoadDics(url, credit, "EDP_USER_DICS", baseDBUtil, downAnyway);
	}

	/**
	 * 保存数据字典到数据库
	 * 
	 * @param paramTypelist
	 * @throws DbException 
	 */
	private static void saveParamsToDb(BaseDBUtil baseDBUtil, List<ParamTypeResult> paramTypelist) throws DbException {
		for (int i = 0; i < paramTypelist.size(); i++) {
			ParamTypeResult paramTypeResult = paramTypelist.get(i);
			String typeNo = paramTypeResult.getParamTypeNo();
			EdpParamTypeInfoEntity edpParamTypeInfoEntity = new EdpParamTypeInfoEntity();
			edpParamTypeInfoEntity.setParamTypeNo(typeNo);
			edpParamTypeInfoEntity.setParamTypeName(paramTypeResult.getParamTypeName());
			edpParamTypeInfoEntity.setParamTypeId(paramTypeResult.getParamTypeId());
			// 保存或编辑参数类型
			baseDBUtil.saveOrUpdate(edpParamTypeInfoEntity);
			List<ParamInfoResult> paramInfoList = paramTypeResult.getParams();
			if (paramInfoList == null || paramInfoList.size() < 1) {
				continue;
			}
			for (int j = 0; j < paramInfoList.size(); j++) {
				ParamInfoResult paramInfoResult = paramInfoList.get(j);
				EdpParamInfoEntity edpParamInfoEntity = new EdpParamInfoEntity();
				edpParamInfoEntity.setParamName(paramInfoResult.getParamName());
				edpParamInfoEntity.setParamId(paramInfoResult.getParamId());
				edpParamInfoEntity.setParamValue(paramInfoResult.getParamValue());
				edpParamInfoEntity.setOrderNo(paramInfoResult.getOrderNo());
				edpParamInfoEntity.setParamTypeNo(typeNo);
				// 保存或编辑参数信息
				baseDBUtil.saveOrUpdate(edpParamInfoEntity);
			}
		}

	}

	/**
	 * 获取字典类型
	 * @time 2017年9月8日 上午9:51:14
	 * @param baseDBUtil
	 * @param typeId
	 * @return
	 * @throws DbException
	 */
	public static EdpParamTypeInfoEntity getParamTypeById(BaseDBUtil baseDBUtil, String typeId) throws DbException {
		baseDBUtil.createTableIfNotExist(EdpParamTypeInfoEntity.class);
		return baseDBUtil.getById(EdpParamTypeInfoEntity.class, typeId);
	}

	/**
	 * 获取字典类型
	 * @time 2017年9月8日 上午9:54:33
	 * @param baseDBUtil
	 * @param typeNo
	 * @return
	 * @throws DbException
	 */
	public static EdpParamTypeInfoEntity getParamTypeByNo(BaseDBUtil baseDBUtil, String typeNo) throws DbException {
		baseDBUtil.createTableIfNotExist(EdpParamTypeInfoEntity.class);
		EdpParamTypeInfoEntity queryEntity = new EdpParamTypeInfoEntity();
		queryEntity.setParamTypeNo(typeNo);
		List<EdpParamTypeInfoEntity> list = baseDBUtil.excuteQuery(queryEntity);
		if (null == list || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取字典表中记录数
	 * 
	 * @return
	 * @throws DbException 
	 */
	public static int getParamsCount(BaseDBUtil baseDBUtil) throws DbException {
		baseDBUtil.createTableIfNotExist(EdpParamTypeInfoEntity.class);
		List<EdpParamTypeInfoEntity> paramTypeList = baseDBUtil.getAll(EdpParamTypeInfoEntity.class);
		if (paramTypeList == null || paramTypeList.size() < 1) {
			return 0;
		}
		return paramTypeList == null ? 0 : paramTypeList.size();
	}

	/**
	 * 根据字典ID获取字典数据
	 * 
	 * @param paramTypeNo
	 * @param deptId
	 * @return
	 * @throws DbException 
	 */
	public static EdpParamInfoEntity getParamInfoByParamId(BaseDBUtil baseDBUtil, String paramId) throws DbException {
		baseDBUtil.createTableIfNotExist(EdpParamInfoEntity.class);
		return baseDBUtil.getById(EdpParamInfoEntity.class, paramId);
	}
}
