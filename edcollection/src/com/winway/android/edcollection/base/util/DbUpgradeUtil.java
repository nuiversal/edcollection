package com.winway.android.edcollection.base.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.db.annotation.Column;

/**
 * 
 * @author zgq
 *
 */
public class DbUpgradeUtil {
	public static String getDbFieldByProNameXutil(Class<?> cls) {
		// 获取实体字段名对应属性名数组
		List<String> fieldNames = new ArrayList<>();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			fieldNames.add(field.getName());
		}
		return null;
	}

}
