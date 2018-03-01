package com.winway.android.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * ????????????????????????????????????????????????????????????????????
 * 
 * @author mr-lao
 *
 */
public class ClonesUtil {

	/**
	 * ??????????????????????????????????????
	 * 
	 * @param desc  
	 * @param source
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> void clones(T desc, T source)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Class<?> cls = source.getClass();
		List<String> fieldNames = ClassMenager.getFieldNames(cls);
		if (null == fieldNames || fieldNames.isEmpty()) {
			return;
		}
		for (String name : fieldNames) {
			Object object = ClassMenager.getGetMethod(cls, name).invoke(source);
			ClassMenager.getSetMethod(cls, name).invoke(desc, object);
		}
	}
}
