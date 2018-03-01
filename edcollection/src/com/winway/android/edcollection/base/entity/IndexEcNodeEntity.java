package com.winway.android.edcollection.base.entity;

import com.winway.android.edcollection.project.entity.EcNodeEntity;

/**
 * 带索引顺序的路径点实体
 * @author mr-lao
 *
 * @param <T>
 */
public class IndexEcNodeEntity<T> {
	private T index;
	private EcNodeEntity node;

	public IndexEcNodeEntity(T index, EcNodeEntity node) {
		super();
		this.index = index;
		this.node = node;
	}

	public T getIndex() {
		return index;
	}

	public void setIndex(T index) {
		this.index = index;
	}

	public EcNodeEntity getNode() {
		return node;
	}

	public void setNode(EcNodeEntity node) {
		this.node = node;
	}

}
