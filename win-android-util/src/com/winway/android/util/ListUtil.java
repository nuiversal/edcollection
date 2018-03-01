package com.winway.android.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListUtil {
	/**
	 * 把一个集合列表数据全部复制到另一个集合列表中
	 * 
	 * @param desc
	 *            要复制到的集合
	 * @param source
	 *            被复制的集合
	 */
	public static <T> void copyList(List<T> desc, List<T> source) {
		for (int i = 0; i < source.size(); i++) {
			desc.add(source.get(i));
		}
	}

	public static <T> void copyList(List<T> desc, List<T> source, List<ValueFillter.FilterParams> params)
			throws Exception {
		ValueFillter<T> filter = new ValueFillter<T>(params);
		copyList(desc, source, filter);
	}

	/**
	 * 只复制集合source里符号filter过滤条件的数据到desc集合里
	 * 
	 * @param desc
	 * @param source
	 * @param filter
	 * @throws Exception
	 */
	public static <T> void copyList(List<T> desc, List<T> source, ValueFillter<T> filter) throws Exception {
		ArrayList<T> list = new ArrayList<T>();
		copyList(list, source);
		filter.filter(list, desc);
	}

	public static <T> void copyList(List<T> desc, T source[]) {
		for (int i = 0; i < source.length; i++) {
			desc.add(source[i]);
		}
	}

	/**
	 * 
	 * 
	 * @param list
	 * @param obj
	 * @return
	 */
	public static <T> int indexOf(List<T> list, Object obj) {
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			if (t.equals(obj)) {
				return i;
			}
			if (null != obj && obj.equals(t)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 判断元素是否在数组中
	 * 
	 * @param item
	 * @param items
	 * @return true表示已经存在，false表示不存在
	 */
	public static boolean isInList(Object item, List<?> items) {
		boolean bRes = false;
		if (item == null || items == null) {
			bRes = false;
		}
		for (int i = 0; i < items.size(); i++) {
			Object object = items.get(i);
			if (object.equals(item)) {
				bRes = true;
				break;
			}
		}
		return bRes;
	}

	/**
	 * 
	 * @param list
	 * @param obj
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static <T> int indexOf(List<T> list, Object obj, String methodName) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			if (t.equals(obj)) {
				return i;
			}
			if (null != obj && obj.equals(t)) {
				return i;
			}
			Method method = t.getClass().getMethod(methodName);
			Object invoke = method.invoke(t);
			if (invoke.equals(obj)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 将set里面的内容复制到list里面
	 * 
	 * @param desc
	 * @param sourse
	 */
	public static <T> void copyList(List<T> desc, Set<T> sourse) {
		for (T t : sourse) {
			desc.add(t);
		}
	}

	/**
	 * 抽取desc列表中的实体中的methodname方法返回值进入source集合，其中methodname一定是个无参方法并且具有返回值
	 * 
	 * @param desc
	 * @param source
	 * @param methodname
	 * @throws Exception
	 */
	public static <T> void extractionList(List<?> desc, List<T> source, String methodname) throws Exception {
		Method method = desc.get(0).getClass().getMethod(methodname);
		for (Object obj : desc) {
			@SuppressWarnings("unchecked")
			T invoke = (T) method.invoke(obj);
			source.add(invoke);
		}
	}

	/**
	 * 将MAP对象转化成ArrayList返回
	 * 
	 * @param map
	 * @return
	 */
	public static <T> List<T> mapToList(Map<?, T> map) {
		List<T> list = new ArrayList<>();
		Set<?> keySet = map.keySet();
		for (Object key : keySet) {
			list.add(map.get(key));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> void copyToContainerList(List<T> desc, List<?> source) {
		for (int i = 0; i < source.size(); i++) {
			desc.add((T) source.get(i));
		}
	}
}
