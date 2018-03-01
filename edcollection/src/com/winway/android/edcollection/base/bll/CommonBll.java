package com.winway.android.edcollection.base.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.ewidgets.input.entity.DataItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

/**
 * 全局业务操作(项目db)
 * 
 * @author zgq
 *
 */
public class CommonBll extends BaseBll<Object> {

	public CommonBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 获取操作日志
	 * 
	 * @param oid
	 * @return
	 */
	public EmCDataLogEntity getDataLogEntity(String datakey) {
		String expr = "DATA_KEY = '" + datakey + "'";
		List<EmCDataLogEntity> list = this.queryByExpr2(EmCDataLogEntity.class, expr);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 删除日志
	 * @param datakey
	 */
	public void deleteDataLog(String dataKey) {
		String expr = "DATA_KEY='"+dataKey+"'";
		List<EmCDataLogEntity> list = this.queryByExpr2(EmCDataLogEntity.class, expr);
		deleteAll(list);
	}
	
	/**
	 * 查询线路数据转换成关键字查询需要的数据
	 * 
	 * @param substationNo
	 * @return
	 */
	public List<DataItem> searchLineTransformationDataItems(String substationNo) {
		List<DataItem> dataItems = new ArrayList<>();
		String sql = "select * from ec_line where START_STATION = '" + substationNo + "'";
		try {
			List<EcLineEntity> lineList = this.excuteQuery(EcLineEntity.class, sql);
			if (lineList != null && lineList.size() > 0) {
				for (EcLineEntity lineEntity : lineList) {
					DataItem dataItem = new DataItem();
					dataItem.setBusiObj(lineEntity);
					dataItem.setName(lineEntity.getName());
					dataItems.add(dataItem);
				}
			}
			return dataItems;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询变电站下的线路
	 * 
	 * @param substationNo
	 * @return
	 */
	public List<EcLineEntity> searchLine(String substationNo) {
		String sql = "select * from ec_line where START_STATION = '" + substationNo + "'";
		try {
			List<EcLineEntity> lineList = this.excuteQuery(EcLineEntity.class, sql);
			return lineList;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
	 * @return
	 */
	public boolean handleDataLog(String objId, String tableName, DataLogOperatorType dataLogOperatorType,
			WhetherEnum whetherEnum, String desc, boolean isLocalAdd) {
		EmCDataLogEntity dataLogEntity = new EmCDataLogEntity();
		String expr = "DATA_KEY='" + objId + "' and TABLE_NAME = '" + tableName.toUpperCase(Locale.CHINA) + "'";
		List<EmCDataLogEntity> list = queryByExpr2(EmCDataLogEntity.class, expr);
		if (list != null && list.size() > 0) {// 日志表存在
			EmCDataLogEntity emCDataLogEntity = list.get(0);
			Integer uploadState = emCDataLogEntity.getIsUpload();
			if (null != uploadState) {
				if (uploadState == 0) {// 未上传
					// 1、本地已经采集过的数据,但是未上传;或者已经上传过的数据，但是经过了编辑操作
					if (emCDataLogEntity.getOptType().equals(DataLogOperatorType.Add.getValue())) {
						dataLogOperatorType = DataLogOperatorType.Add;
					}
					//其他情况外面控制
					
				} else {// 已经上传的数据，只能对他进行编辑或删除操作
					// 2、服务端获取的数据或者本地采集的且已上传
					// 由外面决定
					if (emCDataLogEntity.getOptType().equals(DataLogOperatorType.Add.getValue())) {
						dataLogOperatorType = DataLogOperatorType.update;
					}

				}
				dataLogEntity.setEmCDataLogId(emCDataLogEntity.getEmCDataLogId());
			}
		} else {// 日志表不存在
			if (isLocalAdd) {
				// 1、新增记录情况
				dataLogOperatorType = DataLogOperatorType.Add;
			} else {
				// 2、服务端下载的历史数据,而且历史数据是没有日志数据情况
				// 由外面决定
			}

		}
		dataLogEntity.setOptType(dataLogOperatorType.getValue());
		dataLogEntity.setDataKey(objId);
		dataLogEntity.setDescription(desc);
		dataLogEntity.setExecutor(GlobalEntry.loginResult.getUid());
		dataLogEntity.setIsUpload(whetherEnum.getValue());
		dataLogEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
		dataLogEntity.setTableName(tableName.toUpperCase());// 使用大写
		dataLogEntity.setUpdateTime(new Date());
		dataLogEntity.setOrgid(GlobalEntry.loginResult.getOrgid());
		dataLogEntity.setEmTasksId(GlobalEntry.currentTask == null ? "" : GlobalEntry.currentTask.getEmTasksId());
		return saveOrUpdate(dataLogEntity);
	}

	boolean isLocal = true;

	public void initIsLocal(String objId, String tableName, String primaryKey) {
		isLocal = true;
		String SQL = "select * from " + tableName + " where " + primaryKey + " = '" + objId + "'";
		try {
			Cursor cursor = this.getDbUtils().execQuery(SQL);
			if (cursor.moveToNext()) {
				isLocal = false;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public boolean handleDataLog(String objId, String tableName, DataLogOperatorType dataLogOperatorType,
			WhetherEnum whetherEnum, String desc) {
		if (!isLocal) {
			dataLogOperatorType = DataLogOperatorType.update;
		}
		return handleDataLog(objId, tableName, dataLogOperatorType, whetherEnum, desc, isLocal);
	}
	

	/**
	 * 
	 * @param objId
	 * @param tableName
	 * @param whetherEnum
	 * @param desc
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public boolean handleDataLog3(String objId, String tableName, WhetherEnum whetherEnum, String desc) {
		EmCDataLogEntity dataLogEntity = new EmCDataLogEntity();
		DataLogOperatorType dataLogOperatorType = DataLogOperatorType.update;
		String expr = "DATA_KEY='" + objId + "' and TABLE_NAME = '" + tableName.toLowerCase() + "'";
		List<EmCDataLogEntity> list = queryByExpr2(EmCDataLogEntity.class, expr);
		if (null == list || list.isEmpty()) {
			expr = "DATA_KEY='" + objId + "' and TABLE_NAME = '" + tableName.toUpperCase() + "'";
			list = queryByExpr2(EmCDataLogEntity.class, expr);
		}
		if (list != null && list.size() > 0) {
			dataLogEntity = list.get(0);
			// 判断日志是否已经被上传过了
			if (dataLogEntity.getIsUpload() == WhetherEnum.YES.getValue()
					&& dataLogEntity.getOptType() != DataLogOperatorType.delete.getValue()) {
				// 上传过的数据，操作状态为修改
				dataLogEntity.setOptType(DataLogOperatorType.update.getValue());
			}
		} else {
			dataLogEntity.setOptType(dataLogOperatorType.getValue());
		}
		dataLogEntity.setDataKey(objId);
		dataLogEntity.setDescription(desc);
		dataLogEntity.setExecutor(GlobalEntry.loginResult.getUid());
		dataLogEntity.setIsUpload(whetherEnum.getValue());
		dataLogEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
		dataLogEntity.setTableName(tableName.toUpperCase());
		dataLogEntity.setUpdateTime(new Date());
		return saveOrUpdate(dataLogEntity);
	}

	public List<EcNodeEntity> getNodeEctityList(String markerId) {
		String sql = "select * from ec_node where MARKER ='" + markerId + "'";
		try {
			List<EcNodeEntity> ecNodeEntities = excuteQuery(EcNodeEntity.class, sql);
			return ecNodeEntities;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
