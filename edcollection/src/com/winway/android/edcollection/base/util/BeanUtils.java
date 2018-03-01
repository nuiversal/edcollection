package com.winway.android.edcollection.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zgq
 *
 */
public class BeanUtils {

	/**
	 * 将对象的所有属性设置为null
	 * 
	 * @param obj
	 * @return
	 */
	public static void setFieldsValueNull(Object obj) {
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.get(obj) != null) { // 判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
					f.set(obj, null);
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 清空对象属性的值
	 * 
	 * @param obj
	 */
	public static void setObjectFieldsEmpty(Object obj) {
		// 对obj反射
		Class objClass = obj.getClass();
		// Method[] objmethods = objClass.getDeclaredMethods();//获取自己定义的方法
		Method[] objmethods = objClass.getMethods();//获取所有的方法，包括继承而来的
		Map objMeMap = new HashMap();
		for (int i = 0; i < objmethods.length; i++) {
			Method method = objmethods[i];
			objMeMap.put(method.getName(), method);
		}
		for (int i = 0; i < objmethods.length; i++) {
			String methodName = objmethods[i].getName();
			if (methodName != null && methodName.startsWith("get")) {
				try {
					Object returnObj = objmethods[i].invoke(obj, new Object[0]);
					Method setmethod = (Method) objMeMap.get("set" + methodName.split("get")[1]);
					if (returnObj != null) {
						returnObj = null;
					}
					setmethod.invoke(obj, returnObj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

		}
	}

}
