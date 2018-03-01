package com.winway.android.edcollection.base.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;

/**
 * 注解操作工具类
 * 
 * @author zgq
 *
 */
public class AnnotationUtil {

	/**
	 * 根据类名获取xutils注解的表名（限xutil使用）
	 * 
	 * @param cls
	 *            实体类
	 * @return
	 */
	public static String getTableNameXutilAnno(Class<?> cls) {
		Table table = cls.getAnnotation(Table.class);
		return table.name();
	}

	/**
	 * 根据实体属性名称获取表字段列名（限xutil使用）
	 * 
	 * @param cls
	 *            实体类
	 * @param proName
	 *            属性名
	 * @return
	 */
	public static String getDbFieldByProNameXutil(Class<?> cls, String proName) {
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if (proName.equals(field.getName())) {
				Column column = field.getAnnotation(Column.class);
				return column.column();
			}
		}
		return null;
	}

}
