package com.winway.android.db.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.winway.android.db.base.DatabaseHelper;

import android.content.Context;

/**
 * 基础dao
 * 
 * @author struggle
 *
 * @param <T>
 */
public class BaseDao<T> {
	private Dao<T, Integer> dao;
	private DatabaseHelper helper;
	private Context context;

	public BaseDao(Context context, Class<T> cla) {
		try {
			this.context = context;
			helper = DatabaseHelper.getHelper(context);// 获取数据库帮助类
			dao = helper.getDao(cla);// 获取dao对象
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取dao对象
	 * 
	 * @return
	 */
	public Dao<T, Integer> getDao() {
		return dao;
	}

	/**
	 * 增加一条记录
	 * 
	 * @param t
	 * @return
	 */
	public int add(T t) {
		int result = 0;
		try {
			result = dao.create(t);
		} catch (Exception e) {
			e.printStackTrace();
			result = -1;
		}
		return result;

	}

	/**
	 * 如果表里面不存在，才插入记录
	 * 
	 * @param t
	 * @return
	 */
	public T addIfNotExists(T t) {
		T result = null;
		try {
			result = dao.createIfNotExists(t);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	/**
	 * 创建或者更新
	 * 
	 * @param t
	 * @return
	 */
	public int addOrUpdate(T t) {
		int result = 0;
		try {
			CreateOrUpdateStatus crStatus = dao.createOrUpdate(t);
			result = crStatus.getNumLinesChanged();
		} catch (Exception e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}

	/**
	 * 根据id删除元素
	 * 
	 * @param id
	 * @return
	 */
	public int del(Integer id) {
		int result = 0;
		try {
			result = dao.deleteById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
		}
		return result;
	}

	/**
	 * 修改记录
	 * 
	 * @param t
	 * @return
	 */
	public int update(T t) {
		int result = 0;
		try {
			result = dao.update(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
		}
		return result;
	}

	/**
	 * 根据id查询实体
	 * 
	 * @param id
	 * @return
	 */
	public T queryEntityById(Integer id) {
		T t = null;
		try {
			t = dao.queryForId(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			t = null;
		}
		return t;

	}

	/**
	 * 获取数据列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<T> getDataList(long pageIndex, long pageSize) {
		List<T> list = null;
		long index = (pageIndex - 1) * pageSize;// 表示查询的起始位置
		try {
			QueryBuilder<T, Integer> builder = dao.queryBuilder();
			builder.offset(index);
			builder.limit(pageSize);
			list = builder.query();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			list = null;
		}
		return list;
	}

	/**
	 * 获取所有数据
	 */
	public List<T> queryAll() {
		List<T> list = null;
		try {
			list = dao.queryForAll();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			list = null;
		}
		return list;
	}

	/**
	 * 通过原生sql进行查询
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryBySql(String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			GenericRawResults<String[]> results = dao.queryRaw(sql);
			String[] columnNames = results.getColumnNames();// 列数组
			List<String[]> datas = results.getResults();// datas.size表示记录总数
			System.out.println(datas.size());
			for (int i = 0; i < datas.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				String[] row = datas.get(i);// 一条记录
				for (int j = 0; j < row.length; j++) {
					String key = columnNames[j];
					Object value = row[j];
					map.put(key, value);
				}
				list.add(map);
			}
			System.out.println(list.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			list = null;
		}
		return list;
	}

	/**
	 * 查询通过sql
	 * 
	 * @param orderBysql
	 * @param where
	 * @return
	 */
	public List<T> queryBySqlT(String orderBysql, Where<T, Integer> where) {
		List<T> list = null;
		try {
			QueryBuilder<T, Integer> qBuilder = dao.queryBuilder();
			qBuilder.orderByRaw(orderBysql);
			qBuilder.setWhere(where);
			PreparedQuery<T> preparedQuery = qBuilder.prepare();
			list = dao.query(preparedQuery);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 通过对象查询
	 * 
	 * @param t
	 * @return
	 */
	public List<T> queryForMatch(T t) {
		List<T> list = null;
		try {
			list = dao.queryForMatching(t);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list = null;
		}
		return list;
	}

	/**
	 * 支持排序的查询
	 * 
	 * @param rawSql
	 * @return
	 */
	public List<T> queryBuider(String orgid, Map<String, Boolean> map) {
		List<T> list = null;
		try {
			int size = map.size();
			if (size == 1) {
				Set<Entry<String, Boolean>> entrySet = map.entrySet();
				Iterator<Entry<String, Boolean>> iterator = entrySet.iterator();
				String key = "";
				Boolean value = true;
				while (iterator.hasNext()) {
					Entry<String, Boolean> entry = iterator.next();
					key = entry.getKey();
					value = entry.getValue();
				}
				list = dao.queryBuilder().orderBy(key, value).where().eq("ORGID", orgid).query();
			} else if (size == 2) {
				Set<Entry<String, Boolean>> entrySet = map.entrySet();
				Iterator<Entry<String, Boolean>> iterator = entrySet.iterator();
				List<String> keys = new ArrayList<String>();
				List<Boolean> values = new ArrayList<Boolean>();
				while (iterator.hasNext()) {
					Entry<String, Boolean> entry = iterator.next();
					String key = entry.getKey();
					Boolean value = entry.getValue();
					keys.add(key);
					values.add(value);
				}
				list = dao.queryBuilder().orderBy(keys.get(1), values.get(1)).orderBy(keys.get(0), values.get(0))
						.where().eq("ORGID", orgid).query();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list = null;
		}

		return list;
	}

	/**
	 * 根据条件语句查询数据
	 */
	public List<T> queryByMap(Map<String, Object> map) {
		List<T> list = null;
		try {
			list = dao.queryForFieldValues(map);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			list = null;
		}
		return list;
	}

	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public int getTotal() {
		int res = 0;
		try {
			List<T> list = dao.queryForAll();
			res = list.size();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			res = -1;
		}
		return res;
	}

	/**
	 * 批量添加数据
	 */
	public int batchInsert(final List<T> list) {
		int res = 0;
		try {
			TransactionManager.callInTransaction(DatabaseHelper.getHelper(context).getConnectionSource(),
					new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							// TODO Auto-generated method stub
							for (int i = 0; i < list.size(); i++) {
								T t = list.get(i);
								dao.create(t);
							}
							// 数据操作
							return null;
						}

					});
			res = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = -1;
		}
		return res;
	}

	/**
	 * 删除记录
	 * 
	 * @param map
	 * @return
	 */
	public int delByCollection(Collection<T> collection) {
		// TODO Auto-generated method stub
		int res = 0;
		try {
			res = dao.delete(collection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = -1;
		}
		return res;

	}
}
