package com.winway.android.edcollection.base;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.db.sqlite.CursorUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.winway.android.util.TypeUtil;

import android.content.Context;
import android.database.Cursor;

/**
 * 数据库操作
 * 
 * @author zgq
 *
 * @param <T>
 */
public class BaseBll<T> {

	protected DbUtils dbUtils;
	protected Class<T> entityClass;

	/**
	 * 
	 * @param context
	 * @param dbUrl
	 *            db全路径,如(/sdcard/winway_edcollection/db/common/aa.db)
	 */
	@SuppressWarnings("unchecked")
	public BaseBll(Context context, String dbUrl) {
		if (dbUrl != null && !dbUrl.isEmpty()) {
			DaoConfig config = new DaoConfig(context);
			String dbDir = dbUrl.substring(0, dbUrl.lastIndexOf("/"));
			String dbName = dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
			config.setDbDir(dbDir);
			config.setDbName(dbName);
			dbUtils = DbUtils.create(config);
		}
		entityClass = TypeUtil.getTypeClass(this);
	}

	/**
	 * @update 增加此构造器，目的是使此BaseBll可以用途纯数据库工具类使用
	 * @author mr-lao
	 * @param context
	 * @param dbUrl
	 * @param enCls
	 *            db全路径,如(/sdcard/winway_edcollection/db/common/aa.db)
	 */
	@SuppressWarnings("unchecked")
	public BaseBll(Context context, String dbUrl, Class<T> enCls) {
		if (dbUrl != null && !dbUrl.isEmpty()) {
			DaoConfig config = new DaoConfig(context);
			String dbDir = dbUrl.substring(0, dbUrl.lastIndexOf("/"));
			String dbName = dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
			config.setDbDir(dbDir);
			config.setDbName(dbName);
			dbUtils = DbUtils.create(config);
		}
		entityClass = enCls;
	}

	/**
	 * 增加
	 * 
	 * @param entity
	 * @return
	 */
	public boolean save(Object entity) {
		boolean bResult = false;
		try {
			dbUtils.save(entity);
			bResult = true;
		} catch (DbException e) {
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	/**
	 * 批量增加
	 * 
	 * @param list
	 * @return
	 */
	public boolean saveAll(List<?> list) {
		boolean bResult = false;
		try {
			dbUtils.saveAll(list);
			bResult = true;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 */
	public boolean update(Object entity) {
		boolean bResult = false;
		try {
			dbUtils.saveOrUpdate(entity);
			bResult = true;
		} catch (DbException e) {
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	/**
	 * 批量修改
	 * 
	 * @param list
	 * @return
	 */
	public boolean updateAll(List<?> list) {
		boolean bResult = false;
		try {
			dbUtils.updateAll(list);
			bResult = true;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	/**
	 * 增加或者修改
	 * 
	 * @param entity
	 * @return
	 */
	public boolean saveOrUpdate(Object entity) {
		boolean bResult = false;
		try {
			dbUtils.saveOrUpdate(entity);
			bResult = true;
		} catch (DbException e) {
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return findAll(entityClass);
	}

	/**
	 * 查询全部
	 * 
	 * @param entityClass
	 * @return
	 */
	public <X> List<X> findAll(Class<X> entityClass) {
		try {
			return dbUtils.findAll(entityClass);
		} catch (DbException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public T findById(Object id) {
		return findById(entityClass, id);
	}

	/**
	 * 根据id查询
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <X> X findById(Class<X> entityClass, Object id) {
		try {
			return dbUtils.findById(entityClass, id);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取最后一条记录（只适用主键自增的情况）
	 * 
	 * @param entityClass
	 * @param priKeyColumn
	 *            主键列名
	 * @return
	 */
	public <X> X findLastRecord(Class<X> entityClass, String priKeyColumn) {
		try {
			Selector selector = Selector.from(entityClass);
			String expr = "1=1 order by " + priKeyColumn + " desc";
			selector.expr(expr);
			return dbUtils.findFirst(selector);
		} catch (DbException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据条件表达式查询数据
	 * 
	 * @param cls
	 * @param expr
	 *            SQL表达式（如：name='admin'）
	 * @return
	 */
	public List<T> queryByExpr(Class<?> cls, String expr) {
		List<T> list = null;
		try {
			Selector selector = Selector.from(cls);
			WhereBuilder whereBuilder = WhereBuilder.b();
			whereBuilder.expr(expr);
			selector.where(whereBuilder);
			list = dbUtils.findAll(selector);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list = null;
		}
		return list;
	}

	/**
	 * 根据条件表达式查询数据
	 * 
	 * @param cls
	 * @param expr
	 *            SQL表达式（如：name='admin'）
	 * @return
	 */
	public <X> List<X> queryByExpr2(Class<X> cls, String expr) {
		List<X> list = null;
		try {
			Selector selector = Selector.from(cls);
			WhereBuilder whereBuilder = WhereBuilder.b();
			whereBuilder.expr(expr);
			selector.where(whereBuilder);
			list = dbUtils.findAll(selector);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list = null;
		}
		return list;
	}

	/**
	 * 
	 * @param cls
	 * @param sql  严格的SQL语句，如select * from table_name where clounm_name = value;
	 * @return
	 * @throws DbException
	 */
	public <X> List<X> excuteQuery(Class<X> cls, String sql) throws DbException {
		Cursor cursor = dbUtils.execQuery(sql);
		List<X> list = new ArrayList<X>();
		while (cursor.moveToNext()) {
			X entity = CursorUtils.getEntity(dbUtils, cursor, cls, 0);
			list.add(entity);
		}
		return list;
	}

	/**
	 * 分页获取数据
	 * 
	 * @param entityClass
	 * @param where
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public <X> List<X> queryBypaging(Class<X> entityClass, String where, int pageIndex, int pageSize) {
		try {
			Selector selector = Selector.from(entityClass);
			WhereBuilder whereBuilder = WhereBuilder.b();
			whereBuilder.expr(where);
			selector.where(whereBuilder);
			selector.limit(pageSize).offset(pageSize * (pageIndex - 1));
			return dbUtils.findAll(selector);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据id删除记录
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteById(Object id) {
		boolean bResult = false;
		try {
			dbUtils.deleteById(entityClass, id);
			bResult = true;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	/**
	 * 根据id删除记录
	 * 
	 * @param cls
	 * @param id
	 * @return
	 */
	public boolean deleteById(Class<?> cls, Object id) {
		boolean bResult = false;
		try {
			dbUtils.deleteById(cls, id);
			bResult = true;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	/**
	 * 批次删除
	 * 
	 * @param list
	 * @return
	 */
	public boolean deleteAll(List<?> list) {
		boolean bResult = false;
		try {
			dbUtils.deleteAll(list);
			bResult = true;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	/**
	 * 删除库
	 * 
	 * @return
	 */
	public boolean dropDb() {
		boolean bResult = false;
		try {
			dbUtils.dropDb();
			bResult = true;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	/**
	 * 删除表
	 * 
	 * @param cls
	 * @return
	 */
	public boolean dropTable(Class<?> cls) {
		boolean bResult = false;
		try {
			dbUtils.dropTable(cls);
			bResult = true;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}

	public <X> boolean saveOrUpdate(List<X> list) {
		try {
			dbUtils.saveOrUpdateAll(list);
			return true;
		} catch (DbException e) {
			e.printStackTrace();
			return false;
		}
	}

	public DbUtils getDbUtils() {
		return dbUtils;
	}

}
