package com.winway.android.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 分组器
 * 
 * @author mr-lao
 * 
 */
public class GroupFilter<T> {

	public static class FilterParams {
		// get方法名
		public String methodName;
	}

	private FilterParams params;

	public GroupFilter() {

	}

	public GroupFilter(FilterParams params) {
		this.params = params;
	}

	public void filter(List<T> list) throws Exception {
		filter(list, params);
	}

	/**
	 * @action 过滤数据
	 * @param list
	 *            数据
	 * @param params
	 *            过滤条件
	 * @throws Exception
	 */
	public HashMap<String, List<T>> filter(List<T> list, FilterParams params) throws Exception {
		HashMap<String, List<T>> map = new HashMap<String, List<T>>();
		Class<? extends Object> cls = list.get(0).getClass();
		Iterator<T> iterator = list.iterator();
		while (iterator.hasNext()) {
			T next = iterator.next();
			Method method = cls.getMethod(params.methodName);
			String key = (String) method.invoke(next);
			List<T> listGroup = map.get(key);
			if (null == listGroup) {
				listGroup = new ArrayList<T>();
				map.put(key, listGroup);
			}
			listGroup.add(next);
		}
		return map;
	}

	public static class NotStringException extends Exception {
		private static final long serialVersionUID = 1L;
		private Object obj;

		public NotStringException(Object o) {
			obj = o;
		}

		@Override
		public String getMessage() {
			// TODO Auto-generated method stub
			return obj.toString() + " is not String class";
		}
	}

}
