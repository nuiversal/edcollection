package com.winway.android.db.xutils;

import java.util.ArrayList;

/**
 * join语句的SQL生成工具类，此标有<code>Join<code>注解的实体生成相应的SQL查询语句
 * @author mr-lao
 *
 */
public class JoinUtil {

	private static String createJoinSQL(ArrayList<String> has, Join join1, Join join2) {
		if (has.contains(join1.tbname()) && has.contains(join2.tbname())) {
			return "";
		}
		String join1_join_cloum = join2.joinColum();
		String join2_join_cloum = join1.joinColum();
		if (!"".equals(join1.joinTableColum())) {
			join1_join_cloum = join1.joinTableColum();
		}
		if (!"".equals(join2.joinTableColum())) {
			join2_join_cloum = join2.joinTableColum();
		}
		String asstr = "";
		if (has.contains("join")) {
			asstr = join1.joinType();
		}
		if (!has.contains(join1.tbname())) {
			asstr += " " + join1.tbname() + " as " + join1.asname();
			has.add(join1.tbname());
			if (!has.contains("join")) {
				asstr += " " + join1.joinType();
				has.add("join");
			}
		}
		if (!has.contains(join2.tbname())) {
			asstr += " " + join2.tbname() + " as " + join2.asname();
			has.add(join2.tbname());
		}
		String onstr = join1.asname() + "." + join2_join_cloum + " = " + join2.asname() + "." + join1_join_cloum;
		String SQL = asstr + " on " + onstr;
		return SQL;
	}

	private static String createSelectColumns(Join... joins) {
		StringBuilder builder = new StringBuilder();
		for (Join join : joins) {
			StringBuilder b = new StringBuilder();
			for (String c : join.columns()) {
				b.append(join.asname()).append(".").append(c).append(",");
			}
			builder.append(b.toString());
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	/**
	 * @note 此方法目前仅支持两张或者三张表关联的查询
	 * 创建使用join联合查寻的SQL语句
	 * @param cls1    类名必须有<code>Join</code>注解
	 * @param cls2    类名必须有<code>Join</code>注解
	 * @return
	 */
	public static String createSQL(Class<?> cls1, Class<?> cls2) {
		Join join1 = cls1.getAnnotation(Join.class);
		Join join1_join = null;
		if (join1.joinTableClass() != Object.class) {
			join1_join = join1.joinTableClass().getAnnotation(Join.class);
		}
		Join join2 = cls2.getAnnotation(Join.class);
		Join join2_join = null;
		if (join2.joinTableClass() != Object.class) {
			join2_join = join2.joinTableClass().getAnnotation(Join.class);
		}
		if (null == join1_join && null == join2_join) {
			join1_join = join2;
			join2_join = join1;
		} else {
			if (null == join1_join) {
				if (join2.joinTableClass() == cls1) {
					join1_join = join2;
				} else {
					join1_join = join2_join;
				}
			} else {
				if (join1.joinTableClass() == cls2) {
					join2_join = join1;
				} else {
					join2_join = join1_join;
				}
			}
		}
		ArrayList<String> has = new ArrayList<>();
		String SQL = "select " + createSelectColumns(join1, join2) + " from " + createJoinSQL(has, join1, join1_join)
				+ " " + createJoinSQL(has, join2, join2_join);
		return SQL;
	}
}
