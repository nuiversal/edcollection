package com.winway.android.db.xutils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给需要使用联合查询的表实体加上此注解
 * @author mr-lao
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
	/**
	 * 表名
	 * @return
	 */
	public String tbname();

	/**
	 * 关联的表
	 * @return
	 */
	public Class<?> joinTableClass();

	/**
	 * 表别名
	 * @return
	 */
	public String asname();

	/**
	 * 查询的字段
	 * @return
	 */
	public String[] columns() default { "*" };

	/**
	 * 关联的列名
	 * @return
	 */
	public String joinColum();

	/***
	 * 连接的方式
	 * @example [left join,right join,inner join]
	 * @return
	 */
	public String joinType() default "inner join";

	/**
	 * 连接表的字段
	 * @example 比如，EcNodeDeviceEntity与设备的关联是dev_obj_id，与节点的关联是oid，这时候与EcNodeDeviceEntity关联的设备实体就是设置此字段的值为dev_obj_id了
	 * @return
	 */
	public String joinTableColum() default "";
}
