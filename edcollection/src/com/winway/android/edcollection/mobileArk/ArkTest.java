package com.winway.android.edcollection.mobileArk;

import com.fiberhome.mobileark.sso_v2.GetParamListener;
import com.fiberhome.mobileark.sso_v2.GetTokenListener;
import com.winway.android.util.LogUtil;

import android.content.Context;

public class ArkTest {

	public void test(Context mActivity) {
		SsoV2Utils.getInstance(mActivity).getToken(new GetTokenListener() {

			@Override
			public void finishCallBack(int resultCode, String resultMessage, String token) {
				// TODO Auto-generated method stub
				LogUtil.e("loginToken", "resultCode:" + resultCode + "===" + "resultMessage:" + resultMessage + "==="
						+ "token:" + token);
			}
		});

		SsoV2Utils.getInstance(mActivity).getParam("loginname", new GetParamListener() {

			@Override
			public void finishCallBack(int resultCode, String resultMessage, String value) {
				// TODO Auto-generated method stub
				LogUtil.e("loginParam", "resultCode:" + resultCode + "===" + "resultMessage:" + resultMessage + "==="
						+ "value:" + value);
			}
		});
		SsoV2Utils.getInstance(mActivity).getParam("ecid", new GetParamListener() {

			@Override
			public void finishCallBack(int resultCode, String resultMessage, String value) {
				// TODO Auto-generated method stub
				LogUtil.e("loginParam", "resultCode:" + resultCode + "===" + "resultMessage:" + resultMessage + "==="
						+ "value:" + value);
			}
		});
	}

}
