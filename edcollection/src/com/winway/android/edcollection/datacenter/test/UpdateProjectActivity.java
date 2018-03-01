package com.winway.android.edcollection.datacenter.test;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.winway.android.edcollection.datacenter.loader.GetPrjUsersLoader;
import com.winway.android.edcollection.datacenter.loader.UpdateProjectLoader;
import com.winway.android.edcollection.project.entity.EmProjectMoreinfoEntity;
import com.winway.android.ewidgets.net.NetManager;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.ewidgets.net.service.impl.DataPackageServiceImpl;

/**
 * 
 * 
 * @author lyh
 * @version 创建时间：2016年12月30日 下午3:41:50
 * 
 */
public class UpdateProjectActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		UpdateProjectLoader getInfo = new UpdateProjectLoader(this, null);
		DataPackageServiceImpl.addSystemDataPackageLoader(getInfo);
		BaseService dataPackageService = NetManager.getDataPackageService(this);

		RequestCallBack<String> call = new RequestCallBack<String>() {

			@Override
			public void error(int code,BaseService.WayFrom from) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "失败！！！" + code,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void callBack(String request,BaseService.WayFrom from) {
				// TODO Auto-generated method stub
				request = "成功aaaaa！！！";
				Toast.makeText(getApplicationContext(), request,
						Toast.LENGTH_SHORT).show();
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "em_updateproject");
		params.put("type", "1");
		params.put("data", "data");
		try {
			dataPackageService.getRequestString(null, params, null, call);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
