package com.winway.android.edcollection.base.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.db.converter.DateColumnConverter;
import com.lidroid.xutils.db.sqlite.CursorUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.Column;
import com.lidroid.xutils.db.table.Table;
import com.lidroid.xutils.exception.DbException;
import com.winway.android.util.LogUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

public class BaseDBUtil {
	private DbUtils dbUtils = null;
	private final static String defaultDBName = "patrol";
	private final static int defaultDBVersion = 1;

	// 更新数据库的时候使用
	private DbUpgradeListener dbUpgradeListener = new DbUpgradeListener() {
		@Override
		public void onUpgrade(DbUtils arg0, int arg1, int arg2) {

		}
	};

	void init(Context context, String dbName, int dbVersion) {
		dbUtils = DbUtils.create(context, dbName, dbVersion, dbUpgradeListener);
	}

	/**
	 * 读取程序内部数据库文件，建议使用此构造方法
	 * 
	 * @param context
	 */
	public BaseDBUtil(Context context) {
		this(context, defaultDBName, defaultDBVersion);
	}

	public BaseDBUtil(Context context, String dbname) {
		this(context, dbname, defaultDBVersion);
	}

	public BaseDBUtil(Context context, int dbversion) {
		this(context, defaultDBName, dbversion);
	}

	public BaseDBUtil(Context context, String dbname, int dbversion) {
		init(context, dbname, dbversion);
	}

	public BaseDBUtil(DbUtils dbUtils) {
		this.dbUtils = dbUtils;
	}

	/**
	 * 读取外部数据库文件，建议使用此构造方法
	 * @param context
	 * @param dbFile
	 */
	public BaseDBUtil(Context context, File dbFile) {
		DaoConfig config = new DaoConfig(context);
		config.setDbDir(dbFile.getParentFile().getAbsolutePath());
		config.setDbName(dbFile.getName());
		config.setDbUpgradeListener(dbUpgradeListener);
		config.setDbVersion(defaultDBVersion);
		dbUtils = DbUtils.create(config);
	}

	public void save(Object entity) throws DbException {
		dbUtils.save(entity);
	}

	public void save(List<?> list) throws DbException {
		dbUtils.saveAll(list);
	}

	public void saveOrUpdate(Object entity) throws DbException {
		dbUtils.saveOrUpdate(entity);
	}

	public void delete(Object entity) throws DbException {
		dbUtils.delete(entity);
	}

	public void deleteById(Class<?> cls, Object idValue) throws DbException {
		dbUtils.deleteById(cls, idValue);
	}

	public void update(Object entity) throws DbException {
		dbUtils.update(entity);
	}

	public void update(List<?> list) throws DbException {
		dbUtils.update(list);
	}

	public <T> T getById(Class<T> cls, Object idValue) throws DbException {
		return dbUtils.findById(cls, idValue);
	}

	public <T> List<T> getAll(Class<T> cls) throws DbException {
		return dbUtils.findAll(cls);
	}

	/**
	 * 执行SQL语句
	 * @param sql  严格的SQL语句，如delete from table_name where clounm_name = value;
	 */
	public void excuteSQL(String sql) {
		dbUtils.getDatabase().execSQL(sql);
	}

	/**
	 * 执行SQL语句
	 * @param sql  严格的SQL语句，如select * from table_name where clounm_name = value;
	 */
	public Cursor excuteQuery(String sql) throws DbException {
		return dbUtils.execQuery(sql);
	}

	/**
	 * 
	 * @param cls
	 * @param sql  严格的SQL语句，如select * from table_name where clounm_name = value;
	 * @return
	 * @throws DbException
	 */
	public <T> List<T> excuteQuery(Class<T> cls, String sql) throws DbException {
		Cursor cursor = dbUtils.execQuery(sql);
		List<T> list = new ArrayList<T>();
		while (cursor.moveToNext()) {
			T entity = CursorUtils.getEntity(dbUtils, cursor, cls, 0);
			list.add(entity);
		}
		return list;
	}

	/**
	 * 
	 * @param cls
	 * @param where where语句，（如：name='admin'）
	 * @return
	 * @throws DbException
	 */
	public <T> List<T> excuteWhereQuery(Class<T> cls, String where) throws DbException {
		Selector selector = Selector.from(cls);
		WhereBuilder whereBuilder = WhereBuilder.b();
		whereBuilder.expr(where);
		selector.where(whereBuilder);
		return dbUtils.findAll(selector);
	}

	public DbUtils getDbUtils() {
		return dbUtils;
	}

	public static final int EGNORE_DEFAULT_VALUE = 0;

	/**
	 * 查询Entity中如果有字段为数字类型的话
	 */
	public int EGNORE_VALUE = EGNORE_DEFAULT_VALUE;

	/**
	 * 过滤数字默认值造成的查询结果与目标结果不符合
	 * @param value
	 * @return
	 */
	boolean isEgnore(Object value) {
		if (value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float) {
			Number num = (Number) value;
			if (num.doubleValue() <= EGNORE_VALUE) {
				return true;
			}
		}
		return false;
	}

	/**时间格式，要查寻的时候使用到*/
	@SuppressLint("SimpleDateFormat")
	public SimpleDateFormat SQL_DATE_PATTER = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 创建查询SQL语句
	 * @param dbUtils
	 * @param cls
	 * @param queryEntity   要查询的实体
	 * @param option   值为"="、"<="、">="、"<"、">"、"like"、"in"等
	 * @param exp   值为"and"、"or"等
	 * @return
	 */
	<T> String createSQL(DbUtils dbUtils, Class<T> cls, T queryEntity, String option, String exp) {
		Table table = Table.get(dbUtils, cls);
		String SQL = "select * from " + table.tableName + " where ";
		// 获取字段
		HashMap<String, Column> columnMap = table.columnMap;
		Set<String> keySet = columnMap.keySet();
		for (String key : keySet) {
			String querySQL = "";
			Column column = columnMap.get(key);
			Object columnValue = column.getColumnValue(queryEntity);
			if (null == columnValue || isEgnore(columnValue)) {
				continue;
			}
			if (column.getColumnConverter() instanceof DateColumnConverter) {
				querySQL = column.getColumnName() + " like '%" + SQL_DATE_PATTER.format(new Date((Long) columnValue))
						+ "%'";
			} else {
				if ("like".equalsIgnoreCase(option)) {
					// like查询只有在字段属性为字符串类型的时候才会生效
					if (columnValue instanceof String) {
						querySQL = column.getColumnName() + " like '%" + columnValue + "%'";
					} else if (columnValue instanceof Date) {
						// 日期
						querySQL = column.getColumnName() + " like '%" + SQL_DATE_PATTER.format((Date) columnValue)
								+ "%'";
					} else {
						querySQL = column.getColumnName() + " " + "=" + columnValue;
					}
				} else {
					if (columnValue instanceof String) {
						querySQL = column.getColumnName() + " " + option + " '" + columnValue + "'";
					} else if (columnValue instanceof Date) {
						// 只有模糊查询的时候，才支持日期搜索
						/*querySQL = column.getColumnName() + " " + option + " "
								+ ((Date) columnValue).getTime();*/
					} else {
						querySQL = column.getColumnName() + " " + option + columnValue;
					}
				}
			}
			SQL += querySQL + " " + exp + " ";
		}
		int lastIndexOf = SQL.lastIndexOf(exp);
		if (lastIndexOf < 0) {
			SQL = "select * from " + table.tableName;
		} else {
			SQL = SQL.substring(0, lastIndexOf);
		}
		return SQL;
	}

	/**
	 * 建议使用此方法代替excuteWhereQuery（）方法
	 * 方法会根据queryEntity里面的字段中的值自动生成SQL语句，将执行生成的SQL语句并返回结果。
	 * 举例：
	 * queryEntity的类型为EcLineEntity(<code>EcLineEntity</code>)，如果queryEntity里的name值为“闸坡线”，其他属性值为null,
	 * option的值为"="，exp的值为"and",，那么方法会创建"select * from ec_line where NAME = '闸坡线'"的SQL语句，并将此SQL语句
	 * 执行，返回SQL结果。
	 * @param cls
	 * @param queryEntity  查询实体
	 * @param option 值为"="、"<="、">="、"<"、">"、"like"、"in"等
	 * @param exp 值为"and"、"or"等
	 * @param group SQL分组
	 * @param order SQL排序
	 * @return
	 * @throws DbException
	 */
	public <T> List<T> excuteQuery(Class<T> cls, T queryEntity, String option, String exp, String group, String order)
			throws DbException {
		String SQL = createSQL(dbUtils, cls, queryEntity, option, exp);
		if (!TextUtils.isEmpty(group)) {
			SQL += " group by " + group;
		}
		if (!TextUtils.isEmpty(order)) {
			SQL += " order by " + order;
		}
		return excuteQuery(cls, SQL);
	}

	/**
	 * 分页查询
	 * @param cls
	 * @param queryEntity
	 * @param option
	 * @param exp
	 * @param page   页数从1开始算起，请使用比0大的数值
	 * @param pageSize
	 * @return
	 * @throws DbException
	 */
	public <T> List<T> excuteQuery(Class<T> cls, T queryEntity, String option, String exp, int page, int pageSize)
			throws DbException {
		String SQL = createSQL(dbUtils, cls, queryEntity, option, exp);
		Table table = Table.get(dbUtils, cls);
		String idname = table.id.getColumnName();
		//SQL = SQL + " order by " + idname + " limit " + (page - 1) * pageSize + "," + pageSize;
		SQL = SQL + " order by UPDATE_TIME desc " + " limit " + (page - 1) * pageSize + "," + pageSize;
		return excuteQuery(cls, SQL);
	}

	public <T> List<T> excuteQuery(Class<T> cls, T queryEntity, int page, int pageSize) throws DbException {
		return excuteQuery(cls, queryEntity, "and", "=", page, pageSize);
	}

	public <T> List<T> excuteQuery(Class<T> cls, T queryEntity) throws DbException {
		return excuteQuery(cls, queryEntity, "=", "and", null, null);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> excuteQuery(T queryEntity) throws DbException {
		return excuteQuery((Class<T>) queryEntity.getClass(), queryEntity);
	}

	/**
	 * 保存或者更新（如果实体记录在数据库中已经存在的时候，执行更新操作；如果实体记录在数据库中不存在在的时候，执行新增操作）
	 * @param list
	 * @throws DbException
	 */
	public <T> void saveOrUpdate(List<T> list) throws DbException {
		dbUtils.saveOrUpdateAll(list);
	}

	/**
	 * 开启事务
	 */
	public void begingTransation() {
		dbUtils.getDatabase().beginTransaction();
	}

	/**
	 * 结束事务
	 */
	public void endTransation() {
		dbUtils.getDatabase().endTransaction();
	}

	/**
	 * 此方法适合查询多对多关联的数据，同时查找两张或者多张表，并将数据封装成JAVA实体返回
	 * 如果数据关联多的话，用这个方法查询效率会很高
	 * @param clst 数据实体，实体必须拥有XUtil的注解
	 * @param clsx 数据实体，实体必须拥有XUtil的注解
	 * @param sql  严格的SQL语句，例如select line.name from ec_line as line,ec_line_label as label where line.ec_line_id = label.line_no;
	 * @return
	 * @throws DbException
	 */
	public <T, X> List<SQLResult<T, X>> excuteSQL(Class<T> clst, Class<X> clsx, String sql) throws DbException {
		String SQL = sql;
		Cursor cursor = dbUtils.execQuery(SQL);
		ArrayList<SQLResult<T, X>> list = new ArrayList<>();
		while (cursor.moveToNext()) {
			T t = CursorUtils.getEntity(dbUtils, cursor, clst, 0);
			X x = CursorUtils.getEntity(dbUtils, cursor, clsx, 0);
			list.add(new SQLResult<T, X>(t, x));
		}
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}

	public static class SQLResult<T, X> {
		public T t;
		public X x;

		public SQLResult(T t, X x) {
			super();
			this.t = t;
			this.x = x;
		}
	}

	/**
	 * 根据实体来创建表（如果表已经存在了，则不创建）
	 * @param cls
	 * @return
	 */
	public boolean createTableIfNotExist(Class<?> cls) {
		try {
			dbUtils.createTableIfNotExist(cls);
			return true;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return false;
	}
	

}
