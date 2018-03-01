package com.winway.android.edcollection.datacenter.test;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.winway.android.edcollection.datacenter.entity.GetMemberListResult;
import com.winway.android.edcollection.datacenter.loader.GetprojectsInfoLoader;
import com.winway.android.edcollection.project.dto.GetProjectListResult;
import com.winway.android.ewidgets.net.NetManager;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.ewidgets.net.service.BaseService.RequestMethod;
import com.winway.android.ewidgets.net.service.impl.DataPackageServiceImpl;
import com.winway.android.util.LogUtil;

/**
 * @author lyh
 * @version 创建时间：2016年12月30日 下午3:41:50
 * 
 */
public class getProjectsInfoTestActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GetprojectsInfoLoader getInfo = new GetprojectsInfoLoader(this, null);
		DataPackageServiceImpl.addSystemDataPackageLoader(getInfo);
		// BaseService dataPackageService =
		// NetManager.getDataPackageService(this);
		BaseService dataPackageService = NetManager
				.getTreeLoderService(NetManager.ONLINE_FIRST);
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
				LogUtil.i("info", request);
			}
		};
		RequestCallBack<GetProjectListResult> call1 = new RequestCallBack<GetProjectListResult>() {
			
			@Override
			public void error(int code,BaseService.WayFrom from) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "失败！！！" + code,
						Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void callBack(GetProjectListResult request,BaseService.WayFrom from) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), request+"成功",
						Toast.LENGTH_SHORT).show();
			}
		};
		String credit = "850a51500a94473b93be598784d07f72";
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "em_getprojects");
		params.put("credit", credit);
		// params.put("name", "汕头输电所");
//		Map<String, String> header = new HashMap<String, String>();
		//header.put("ggg", "头部");
		try {
			RequestMethod method = RequestMethod.POST;
			String url  = "http://120.25.84.217:8902/";
			Class<GetProjectListResult> cls = GetProjectListResult.class;
			dataPackageService.getRequestString(url,
					params, null, call);
//			dataPackageService.getRequest(url, params, null, cls, call1);
//			 dataPackageService.request(url, params, null, cls, method ,
//			 call1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
