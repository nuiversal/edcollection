package com.winway.android.edcollection.datacenter.entity;

import com.winway.android.edcollection.project.entity.EcNodeEntity;

public class DeviceEntity<T> {
	private T data;
	private EcNodeEntity node;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public EcNodeEntity getNode() {
		return node;
	}

	public void setNode(EcNodeEntity node) {
		this.node = node;
	}

}
