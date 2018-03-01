package com.winway.android.basicbusiness.bll;

import java.util.Date;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.basicbusiness.entity.DataLogOperatorType;
import com.winway.android.basicbusiness.entity.EmCDataLogEntity;
import com.winway.android.basicbusiness.entity.WhetherEnum;
import com.winway.android.db.xutils.BaseDBUtil;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * 全局业务操作(项目db)
 * 
 * @author zgq
 *
 */
@SuppressLint("DefaultLocale")
public class DataLogBll extends BaseDBUtil {

	public DataLogBll(Context context, String dbUrl) {
		super(context, dbUrl);
	}

	/**
	 * 数据日志操作
	 * 
	 * @param objId
	 *            操作数据的主键
	 * @param tableName
	 *            操作数据的表名
	 * @param dataLogOperatorType
	 *            数据操作枚举
	 * @param whetherEnum
	 *            是否已上传
	 * @param desc
	 *            描述，没有传 null
	 * @param isLocalAdd
	 *            是否是本地新增情况
	 * @param uid
	 *            用户id
	 * 
	 * @param orgId 机构id
	 * 
	 * @return
	 * @throws DbException
	 */
	public boolean handleDataLog(String objId, String tableName, DataLogOperatorType dataLogOperatorType,
			WhetherEnum whetherEnum, String desc, boolean isLocalAdd, String uid, String orgId) throws DbException {
		this.createTableIfNotExist(EmCDataLogEntity.class);
		EmCDataLogEntity dataLogEntity = new EmCDataLogEntity();
		EmCDataLogEntity queryEntity = new EmCDataLogEntity();
		queryEntity.setDataKey(objId);
		queryEntity.setTableName(tableName.toUpperCase());
		List<EmCDataLogEntity> list = null;
		list = excuteQuery(EmCDataLogEntity.class, queryEntity);
		if (list != null && list.size() > 0) {// 日志表存在
			EmCDataLogEntity emCDataLogEntity = list.get(0);
			Integer uploadState = emCDataLogEntity.getIsUpload();
			if (uploadState == 0) {
				// 1、本地已经采集过的数据,但是未上传
				dataLogOperatorType = DataLogOperatorType.Add;
			} else {
				// 2、服务端获取的数据或者本地采集的且已上传
				// 由外面决定
				dataLogOperatorType = DataLogOperatorType.update;
			}
			if (emCDataLogEntity.getOptType() == DataLogOperatorType.update.getValue()) {
				// 本身是修改状态
				dataLogOperatorType = DataLogOperatorType.update;
			}
			dataLogEntity.setEmCDataLogId(emCDataLogEntity.getEmCDataLogId());
		} else {// 日志表不存在
			if (isLocalAdd) {
				// 1、新增记录情况
				dataLogOperatorType = DataLogOperatorType.Add;
			} else {
				// 2、服务端下载的历史数据
				// 由外面决定
			}

		}
		dataLogEntity.setOptType(dataLogOperatorType.getValue());
		dataLogEntity.setDataKey(objId);
		dataLogEntity.setDescription(desc);
		dataLogEntity.setExecutor(uid);
		dataLogEntity.setIsUpload(whetherEnum.getValue());
		dataLogEntity.setTableName(tableName.toUpperCase());// 使用大写
		dataLogEntity.setUpdateTime(new Date());
		dataLogEntity.setOrgid(orgId);
		saveOrUpdate(dataLogEntity);
		return true;
	}

}
