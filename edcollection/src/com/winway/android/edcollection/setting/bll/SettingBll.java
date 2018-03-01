package com.winway.android.edcollection.setting.bll;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.project.dto.SyncDataResult;
import com.winway.android.edcollection.project.dto.TableDataItem;
import com.winway.android.edcollection.project.dto.TableRow;
import com.winway.android.edcollection.project.entity.CDbVersionEntity;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.edcollection.setting.entity.OperateType;
import com.winway.android.edcollection.setting.entity.VersionInfos;
import com.winway.android.network.HttpUtils;
import com.winway.android.network.HttpUtils.CallBack;
import com.winway.android.util.DateUtils;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.LogUtil;
import android.annotation.SuppressLint;
import android.content.Context;

/**
 * 设置业务逻辑
 * 
 * @author
 *
 */
@SuppressLint("DefaultLocale")
public class SettingBll extends BaseBll<EmCDataLogEntity> {
	private ArrayList<EmCDataLogEntity> upoladDataList = new ArrayList<EmCDataLogEntity>();

	public SettingBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 保存数据更新日志信息
	 * 
	 * @param cDataLogEntity
	 * @return
	 */
	public boolean saveCDataLog(EmCDataLogEntity cDataLogEntity) {
		return this.save(cDataLogEntity);
	}

	/**
	 * 根据表名和id查出具体的数据
	 * 
	 * @param cls
	 * @param id
	 * @return
	 */
	public Object findByTableNameAndId(Class<?> cls, String id) {
		Object object = findById(cls, id);
		return object;

	}

	/**
	 * 保存更新的数据
	 * 
	 * @param syncDataResult
	 */
	public void saveUpdateData(SyncDataResult syncDataResult) {
		Gson gson = GsonUtils.build();
		List<TableDataItem> tableDataItemList = syncDataResult.getTables();// 获取需要更新的列表
		if (tableDataItemList == null || tableDataItemList.isEmpty())
			return;
		// 循环更新数据的Tables
		for (TableDataItem item : tableDataItemList) {
			// 循环表的枚举类
			for (TableNameEnum table : TableNameEnum.values()) {// 遍历表
				if (table.getTableName().equalsIgnoreCase(item.getName())) {
					List<TableRow> rows = item.getRows();
					for (TableRow row : rows) {// 遍历表中记录
						String entityJsoon = gson.toJson(row.getEntity());
						Object entity = gson.fromJson(entityJsoon, table.getCls());
						if (entity != null) {
							this.saveOrUpdate(entity);// 保存返回的实体对象
						}

					}
				}
			}
		}
	}

	/**
	 * 获取最新已经上传的时间
	 * 
	 * @return
	 */
	public Date findLastlogtime() {
		String expr = "IS_UPLOAD = '" + WhetherEnum.YES.getValue() + "' order by UPDATE_TIME desc";
		try {

			Selector selector = Selector.from(EmCDataLogEntity.class);
			WhereBuilder whereBuilder = WhereBuilder.b();
			whereBuilder.expr(expr);
			selector.where(whereBuilder);
			EmCDataLogEntity log = this.dbUtils.findFirst(selector);
			if (log != null) {
				return log.getUpdateTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据项目id查询未上传的数据
	 * 
	 * @return
	 */
	public SyncDataResult findNotUploadByPrj(String prjId) {
		SyncDataResult syncData = new SyncDataResult();
		List<TableDataItem> tableList = new ArrayList<TableDataItem>();
		// 循环表枚举类查找未上传的数据
		for (TableNameEnum table : TableNameEnum.values()) {// 遍历所有数据表
			if (table.getTableName().equals("ec_channel")) {
				LogUtil.i("sysdata", "ec_channel同步中");
			}
			String expr = "TABLE_NAME = '" + table.getTableName().toUpperCase() + "' and PRJID = '" + prjId
					+ "' and IS_UPLOAD = '" + WhetherEnum.NO.getValue() + "' ";
			List<EmCDataLogEntity> entities = this.queryByExpr(EmCDataLogEntity.class, expr);
			if (entities == null || entities.size() < 1) {
				continue;
			}
			upoladDataList.addAll(entities);
			TableDataItem tableDataItem = new TableDataItem();
			tableDataItem.setName(entities.get(0).getTableName().toUpperCase());// 统一转大写
			List<TableRow> rows = new ArrayList<TableRow>();
			for (EmCDataLogEntity entity : entities) {// 遍历单表中未上传的数据
				TableRow row = new TableRow();
				row.setKey(entity.getDataKey());
				row.setType(entity.getOptType());
				row.setUpdate(DateUtils.date2Str(entity.getUpdateTime(), DateUtils.datetimeMSFormat));
				row.setRecorder(entity.getExecutor());
				// 查出具体数据
				Object objEntity = findByTableNameAndId(table.getCls(), entity.getDataKey());
				if (objEntity == null) {
					continue;
				}
				row.setEntity(objEntity);
				rows.add(row);
			}
			tableDataItem.setRows(rows);
			tableList.add(tableDataItem);
		}
		syncData.setTime(DateUtils.date2Str(DateUtils.datetimeMSFormat));// 当前时间
		syncData.setTables(tableList);

		return syncData;
	}

	/**
	 * 状态修改
	 * 
	 * @param dataLog
	 * @param state
	 */
	public void updateDataState(EmCDataLogEntity dataLog, Integer state) {
		dataLog.setIsUpload(state);
		this.update(dataLog);
	}

	/**
	 * 获取未上传的
	 * 
	 * @return
	 */
	public ArrayList<EmCDataLogEntity> getUpoladDataList() {
		return this.upoladDataList;
	}

	/**
	 * 获取服务端组件版本
	 * 
	 * @return null或ComponentVersion
	 */
	public CDbVersionEntity getServerComVersion() {
		String url = GlobalEntry.dataServerUrl + File.separator + "?action=getdbversion&component=ED_DATAPLUG&credit="
				+ GlobalEntry.loginResult.getCredit();
		String jsonStr = HttpUtils.doGet(url);
		if (jsonStr == null) {
			return null;
		}
		final Gson gson = GsonUtils.build();
		Type syscDataType = new TypeToken<VersionInfos>() {
		}.getType();
		VersionInfos versionInfos = gson.fromJson(jsonStr, syscDataType);
		if (versionInfos.getCode() == 0) {
			List<CDbVersionEntity> componentVersions = versionInfos.getVersions();
			if (componentVersions != null && componentVersions.size() > 0) {
				return componentVersions.get(0);
			}
		}
		return null;

	}

	/**
	 * 根据日志删除本地的数据
	 */
	public void delLocalDatasByLog(Context context) {
		BaseDBUtil baseDBUtil = new BaseDBUtil(context, GlobalEntry.prjDbUrl);
		EmCDataLogEntity queryEntity = new EmCDataLogEntity();
		queryEntity.setOptType(DataLogOperatorType.delete.getValue());
		List<EmCDataLogEntity> cDataLogs = null;
		try {
			cDataLogs = baseDBUtil.excuteQuery(queryEntity);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (cDataLogs != null && cDataLogs.size() > 0) {
			Map<String, List<String>> table2Prikeys = new HashMap<>();
			List<Integer> logs = new ArrayList<>();
			for (EmCDataLogEntity entity : cDataLogs) {
				logs.add(entity.getEmCDataLogId());
				String tbName = entity.getTableName();
				Class<?> cls = TableNameEnum.getClsByTableName(tbName);
				try {
					Object object= baseDBUtil.getById(cls, entity.getDataKey());
					if (object==null) {
						continue;
					}
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (table2Prikeys.get(tbName) != null) {
					table2Prikeys.get(tbName).add(entity.getDataKey());
				} else {
					List<String> prikeys = new ArrayList<>();
					prikeys.add(entity.getDataKey());
					table2Prikeys.put(tbName, prikeys);
				}
			}
			// 执行数据库操作
			try {
				// 开启事务
				baseDBUtil.begingTransation();
				for (Entry<String, List<String>> entry : table2Prikeys.entrySet()) {
					String tableName = entry.getKey();
					Class<?> cls = TableNameEnum.getClsByTableName(tableName);
					List<String> prikeys = entry.getValue();
					// 删除实际对象数据
					baseDBUtil.deleteByIds(cls, prikeys);
				}
				// 删除日志
				baseDBUtil.deleteByIds(EmCDataLogEntity.class, logs);
				// 提交事务
				baseDBUtil.commitTransation();

			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				// 结束事务
				baseDBUtil.endTransation();
			}

		}
	}

}
