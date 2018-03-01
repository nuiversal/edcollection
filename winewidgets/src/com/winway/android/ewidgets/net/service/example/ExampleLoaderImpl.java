package com.winway.android.ewidgets.net.service.example;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.winway.android.ewidgets.net.service.impl.BaseDataPackageLoader;
import com.winway.android.ewidgets.tree.entity.LevelEntity;
import com.winway.android.util.GsonUtils;

public class ExampleLoaderImpl extends BaseDataPackageLoader {
	static HashMap<String, String> urlMap = new HashMap<>();

	static {
		urlMap.put(MonitorDataUtil.first, MonitorDataUtil.first);
		urlMap.put(MonitorDataUtil.second, MonitorDataUtil.second);
		urlMap.put(MonitorDataUtil.last, MonitorDataUtil.last);
	}

	@Override
	public boolean canProcessThis(String url, Map<String, String> params, Map<String, String> heades) {
		if (null == url) {
			return true;
		}
		if (urlMap.containsKey(url)) {
			return true;
		}
		return false;
	}

	@Override
	public void getRequestString(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<String> call) throws Exception {
		/**测试代码*/
		LevelEntity data = null;
		if (null == url) {
			data = MonitorDataUtil.getData(MonitorDataUtil.first);
		} else if (url.equals(MonitorDataUtil.first)) {
			data = MonitorDataUtil.getData(MonitorDataUtil.second);
		} else if (url.equals(MonitorDataUtil.second)) {
			data = MonitorDataUtil.getData(MonitorDataUtil.last);
		}
		if (null != data) {
			call.callBack(GsonUtils.build().toJson(data), WayFrom.DATA_PACKAGE);
		} else {
			call.error(-1, WayFrom.DATA_PACKAGE);
		}
	}

	@Override
	public void getRequestFile(String url, Map<String, String> params, Map<String, String> headers,
			RequestCallBack<File> call) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void downLoadFile(String url, Map<String, String> params, Map<String, String> headers, String filepath,
			RequestCallBack<File> call) throws Exception {
		// TODO Auto-generated method stub

	}

}
