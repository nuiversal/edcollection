package com.winway.android.db.base;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;

/**
 * 数据库帮助类
 * 
 * @author struggle
 *
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	/**
	 * 数据库的名称
	 */
	public static String DATABASE_NAME = "";
	/**
	 * 需要建表的类列表
	 */
	public static List<Class<?>> tableList = new ArrayList<Class<?>>();
	private Map<String, Dao> daos = new HashMap<String, Dao>();
	/**
	 * 数据库版本
	 */
	public static int databaseVersion = 1;

	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, databaseVersion);// 建库
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			// 建表
			createTbs(connectionSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			// 建表
			createTbs(connectionSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建表
	 */
	public static void createTbs(ConnectionSource connectionSource) throws Exception {
		for (int i = 0; i < tableList.size(); i++) {
			TableUtils.createTableIfNotExists(connectionSource, tableList.get(i));
		}
	}

	private static DatabaseHelper instance;

	/**
	 * 单例获取该Helper
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized DatabaseHelper getHelper(Context context) {
		context = context.getApplicationContext();
		if (instance == null) {
			synchronized (DatabaseHelper.class) {
				if (instance == null) {
					instance = new DatabaseHelper(context);
				}
			}
		}

		return instance;
	}

	public synchronized Dao getDao(Class clazz) throws SQLException {
		Dao dao = null;
		String className = clazz.getSimpleName();

		if (daos.containsKey(className)) {
			dao = daos.get(className);
		}
		if (dao == null) {
			dao = super.getDao(clazz);
			daos.put(className, dao);
		}
		return dao;
	}

	/**
	 * 释放资源
	 */
	@Override
	public void close() {
		super.close();

		for (String key : daos.keySet()) {
			Dao dao = daos.get(key);
			dao = null;
		}
	}

}
