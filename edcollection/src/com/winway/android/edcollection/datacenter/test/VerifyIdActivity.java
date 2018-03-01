package com.winway.android.edcollection.datacenter.test;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.winway.android.edcollection.datacenter.entity.VerityIDResult;
import com.winway.android.edcollection.datacenter.loader.VerifyIDLoader;
import com.winway.android.edcollection.project.dto.GetProjectListResult;
import com.winway.android.ewidgets.net.NetManager;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.ewidgets.net.service.BaseService.RequestMethod;
import com.winway.android.ewidgets.net.service.impl.DataPackageServiceImpl;

/**
 * @author lyh
 * @version 创建时间：2016年12月30日 下午3:41:50
 * 
 */
public class VerifyIdActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		VerifyIDLoader verifyIDLoader = new VerifyIDLoader(this, null);
		DataPackageServiceImpl.addSystemDataPackageLoader(verifyIDLoader);
		BaseService dataPackageService = NetManager.getLineService();
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
				request = "成功！！！";
				Toast.makeText(getApplicationContext(), request,
						Toast.LENGTH_SHORT).show();
			}
		};
		RequestCallBack<VerityIDResult> call1 = new RequestCallBack<VerityIDResult>() {
			
			@Override
			public void error(int code,BaseService.WayFrom from) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "失败！！！" + code,
						Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void callBack(VerityIDResult request,BaseService.WayFrom from) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), request+"成功aaaaaaaaaaaa",
						Toast.LENGTH_SHORT).show();
			}
		};
		
		
			Map<String, String> params = new HashMap<String, String>();
			String credit = "850a51500a94473b93be598784d07f72";
			params.put("action", "em_checkmarkerid");
			params.put("credit", credit);
			params.put("id", "00003");
			Map<String, String> header = new HashMap<String, String>();
			header.put("头部", "头部");
			
			try {
				RequestMethod method = RequestMethod.POST;
				String url  = "http://120.25.84.217:8902/";
				Class<VerityIDResult> cls = VerityIDResult.class;
				//验证标识器id
//				dataPackageService.getRequestString(url, params, null, call);
//				dataPackageService.getRequest(url, params, null, cls, call1);
				 dataPackageService.request(url, params, null, cls, method ,
				 call1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
