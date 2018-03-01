package com.winway.android.db.bll;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.Where;
import com.winway.android.db.dao.BaseDao;

import android.content.Context;

/**
 * 基本业务逻辑层类
 * 
 * @author struggle
 *
 * @param <T>
 */
public class BaseBll<T> {
	BaseDao<T> baseDao = null;

	public BaseBll(Context context, Class<T> cla) {
		if (baseDao == null) {
			baseDao = new BaseDao<T>(context, cla);
		}
	}

	/**
	 * 获取dao对象
	 * 
	 * @return
	 */
	public Dao<T, Integer> getDao() {
		return baseDao.getDao();
	}

	/**
	 * 增加记录
	 * 
	 * @param t
	 * @return
	 */
	public int add(T t) {
		return baseDao.add(t);
	}

	/**
	 * 如果表里面不存在，才插入记录
	 * 
	 * @param t
	 * @return
	 */
	public T addIfNotExists(T t) {
		return baseDao.addIfNotExists(t);
	}

	/**
	 * 创建或者更新
	 * 
	 * @param t
	 * @return
	 */
	public int addOrUpdate(T t) {
		return baseDao.addOrUpdate(t);
	}

	/**
	 * 根据id删除记录
	 * 
	 * @param id
	 * @return
	 */
	public int del(Integer id) {
		return baseDao.del(id);
	}

	/**
	 * 修改记录
	 * 
	 * @param t
	 * @return
	 */
	public int update(T t) {
		return baseDao.update(t);
	}

	/**
	 * 根据id查询实体
	 * 
	 * @param id
	 * @return
	 */
	public T queryEntityById(Integer id) {
		return baseDao.queryEntityById(id);
	}

	/**
	 * 获取数据列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<T> getDataList(long pageIndex, long pageSize) {
		return getDataList(pageIndex, pageSize);
	}

	/**
	 * 获取所有数据
	 */
	public List<T> queryAll() {
		return baseDao.queryAll();
	}

	/**
	 * 查询通过sql进行查询
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryBySql(String sql) {
		return baseDao.queryBySql(sql);
	}

	/**
	 * 通过sql进行查询
	 * 
	 * @param orderBySql
	 * @param where
	 * @return
	 */
	public List<T> queryBySqlT(String orderBySql, Where<T, Integer> where) {
		return baseDao.queryBySqlT(orderBySql, where);
	}

	/**
	 * 通过对象查询
	 * 
	 * @param t
	 * @return
	 */
	public List<T> queryForMatch(T t) {
		return baseDao.queryForMatch(t);
	}

	/**
	 * 带排序的查询
	 * 
	 * @param orgid
	 * @param map
	 * @return
	 */
	public List<T> queryBuider(String orgid, Map<String, Boolean> map) {
		return baseDao.queryBuider(orgid, map);
	}

	/**
	 * 根据条件语句查询数据
	 * 
	 * @param map
	 * @return
	 */
	public List<T> queryByMap(Map<String, Object> map) {
		return baseDao.queryByMap(map);
	}

	/**
	 * 删除记录
	 * 
	 * @param collection
	 * @return
	 */
	public int delByCollection(Collection<T> collection) {
		return baseDao.delByCollection(collection);
	}

	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public int getTotal() {
		return baseDao.getTotal();
	}

	/**
	 * 批量插入
	 * 
	 * @param list
	 * @return
	 */
	public int batchInsert(List<T> list) {
		return baseDao.batchInsert(list);
	}

}
