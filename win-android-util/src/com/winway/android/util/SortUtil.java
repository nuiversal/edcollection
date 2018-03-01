package com.winway.android.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ???????
 * @author mr-lao
 *
 */
public class SortUtil {
	/**
	 * ????
	 * @param map  map???????key???????????????????????????????????????????????Ч????Integer,Float,Double,Long,?????Comparable????
	 * @return
	 */
	public static <T, X> ArrayList<T> sort(Map<X, T> map) {
		ArrayList<X> keyList = new ArrayList<>();
		ListUtil.copyList(keyList, map.keySet());
		@SuppressWarnings("unchecked")
		X[] keyArray = (X[]) keyList.toArray();
		Arrays.sort(keyArray);
		ArrayList<T> resultList = new ArrayList<>();
		for (X key : keyArray) {
			resultList.add(map.get(key));
		}
		return resultList;
	}

	/**
	 * ????
	 * @param list  list???????key???????????????????????????????????????????????Ч????Integer,Float,Double,Long,?????Comparable????
	 * @return
	 */
	public static <T> ArrayList<T> sort(List<T> list) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) list.toArray();
		Arrays.sort(array);
		ArrayList<T> resultList = new ArrayList<>();
		for (T entity : array) {
			resultList.add(entity);
		}
		return resultList;
	}

	public static void sort(Object[] array) {
		Arrays.sort(array);
	}
}
