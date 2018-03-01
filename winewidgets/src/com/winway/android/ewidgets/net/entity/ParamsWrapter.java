package com.winway.android.ewidgets.net.entity;

import java.util.HashMap;
import java.util.Map;

/**
* @author winway-laohw
* @time 2017年9月14日 下午4:03:57
* 参数包装器
*/
public class ParamsWrapter<K, V> {
	public Map<K, V> params;

	public Map<K, V> getParams() {
		return params;
	}

	public void setParams(HashMap<K, V> params) {
		this.params = params;
	}

	public ParamsWrapter(Map<K, V> params) {
		super();
		this.params = params;
	}
}
