package com.winway.android.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//值过滤器
public class ValueFillter<T> {

	public static class FilterParams {
		// get方法名
		public String methodName;
		// get方法的返回值
		public Object returnValue;

		public FilterParams() {

		}

		public FilterParams(String methodName, Object returnValue) {
			super();
			this.methodName = methodName;
			this.returnValue = returnValue;
		}
	}

	public static <T> ValueFillter<T> createValueFillter(String methodName, Object returnValue) {
		return new ValueFillter<T>(createFilterParamsList(methodName, returnValue));
	}

	public static List<FilterParams> createFilterParamsList(String methodName, Object returnValue) {
		ArrayList<FilterParams> paramsLists = new ArrayList<FilterParams>();
		paramsLists.add(createFilterParams(methodName, returnValue));
		return paramsLists;
	}

	public static FilterParams createFilterParams(String methodName, Object returnValue) {
		return new FilterParams(methodName, returnValue);
	}

	private List<FilterParams> params;

	public ValueFillter() {

	}

	public ValueFillter(List<FilterParams> params) {
		this.params = params;
	}

	public void filter(List<T> list, List<T> removedContainer) throws Exception {
		filter(list, params, removedContainer);
	}

	/**
	 * @action 过滤数据
	 * @param list
	 *            数据
	 * @param params
	 *            过滤条件
	 * @param add
	 * @throws Exception
	 */
	public void filter(List<T> list, List<FilterParams> params, List<T> removedContainer) throws Exception {
		Class<? extends Object> cls = list.get(0).getClass();
		Iterator<T> iterator = list.iterator();
		while (iterator.hasNext()) {
			T next = iterator.next();
			for (FilterParams p : params) {
				Method method = cls.getMethod(p.methodName);
				Object invoke = method.invoke(next);
				if (invoke.equals(p.returnValue) || invoke == p.returnValue) {
					iterator.remove();
					if (removedContainer != null) {
						removedContainer.add(next);
					}
					break;
				}
			}
		}
	}

}
