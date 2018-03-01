package com.winway.android.ewidgets.tree.select;

public class GenericsType extends SelectType {
	/**
	 * 泛型类
	 */
	public final Class<?> genericsType;

	public final String getGenericsEntityMethodName;

	public GenericsType(Class<?> entityType, Class<?> genericsType, String getGenericsEntityMethodName) {
		super(entityType);
		this.genericsType = genericsType;
		this.getGenericsEntityMethodName = getGenericsEntityMethodName;
	}
}
