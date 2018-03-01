package com.winway.android.ewidgets.tree.entity;

import java.util.ArrayList;

/**
 * 数据接口获得的数据格式
 * @author mr-lao
 * @date 2016-12-26
 */
public class LevelEntity {
	// 节点数据
	private ArrayList<ItemEntity> datas;
	// 节点数
	private int datasCount;
	// 节点默认点击请求接口
	private String defaultUrl;

	public ArrayList<ItemEntity> getDatas() {
		return datas;
	}

	public void setDatas(ArrayList<ItemEntity> datas) {
		this.datas = datas;
	}

	public int getDatasCount() {
		return datasCount;
	}

	public void setDatasCount(int datasCount) {
		this.datasCount = datasCount;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

}
