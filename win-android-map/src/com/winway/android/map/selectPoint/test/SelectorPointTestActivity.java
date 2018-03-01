package com.winway.android.map.selectPoint.test;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.winway.android.map.selectPoint.activity.MapSelectPointActivity;
import com.winway.android.util.LogUtil;

/** 地图选点调用实例
 * @author lyh
 * @version 创建时间：2018年1月21日 
 *
 */
public class SelectorPointTestActivity extends Activity {
	private  int REQUESTCODE = 0X111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent data = new Intent();
		data.setClass(getApplicationContext(), MapSelectPointActivity.class);
		//传入经纬度
		MapSelectPointActivity.setLngLat(104.08296, 38.65777);
		//跳转界面
		startActivityForResult(data, REQUESTCODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUESTCODE) {
				//拿到地图选点界面的经纬度
				List<Double> resultList = MapSelectPointActivity.getResult();
				if (null != resultList && !resultList.isEmpty()) {
					LogUtil.i("info", "经度:" + resultList.get(0) + ",纬度:" + resultList.get(1));
				}
			}
		}
	}
}
