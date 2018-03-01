package com.winway.android.edcollection.login.controll;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.fiberhome.mobileark.sso_v2.SSOStatusListener;
import com.lidroid.xutils.exception.DbException;
import com.sangfor.ssl.easyapp.SangforAuthForward;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.dbVersion.IDbVersion;
import com.winway.android.edcollection.base.dbVersion.impl.DbVersionImpl;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.login.activity.SettingServerAddressActivity;
import com.winway.android.edcollection.login.bll.LoginBll;
import com.winway.android.edcollection.login.entity.EdpUserInfoEntity;
import com.winway.android.edcollection.login.viewholder.LoginViewHolder;
import com.winway.android.edcollection.mobileArk.SsoV2Utils;
import com.winway.android.edcollection.mobileArk.entity.MobilleArkConstant;
import com.winway.android.edcollection.test.Test;
import com.winway.android.util.FileUtil;
import com.winway.android.util.LogUtil;
import com.winway.android.util.SharedPreferencesUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.ValidateCode;

public class LoginControll extends BaseControll<LoginViewHolder> {
	private ValidateCode validateCode;
	private LoginBll bll;

	@Override
	public void init() {
		bll = new LoginBll(mActivity, FileUtil.AppRootPath + "/db/common/global.db");
		initEvent();
		initData();
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		/**烽火的移动平台*/
		GlobalEntry.isUseMobileArk = false;
		if (GlobalEntry.isUseMobileArk) {
			// 初始化代理
			try {
				SangforAuthForward.getInstance().initSangforHook();
			} catch (Exception e) {
				e.printStackTrace();
			}
			/** 初始化烽火单点登录 */
			try {
				SsoV2Utils.getInstance(mActivity).setMobilearkPackagename(MobilleArkConstant.ArkPackagename);
				SsoV2Utils.getInstance(mActivity).initSSOAgent(new SSOStatusListener() {

					@Override
					public void finishCallBack(int resultCode, String resultMessage) {
						LogUtil.e("loginV2", "resultCode:" + resultCode + "===" + "resultMessage:" + resultMessage);
					}
				});
			} catch (Exception e) {
			}
		}
		
		
		validateCode = new ValidateCode();
		viewHolder.getIvCheckcode().setImageBitmap(validateCode.createBitmap());
		viewHolder.getEdtCheckcode().setText(validateCode.getCode());
		if (SharedPreferencesUtils.contains(mActivity, "#loginname#")) {
			String loginname = (String) SharedPreferencesUtils.get(mActivity, "#loginname#", "");
			// String password = (String) SharedPreferencesUtils.get(mActivity,
			// "#password#", "");
			viewHolder.getEdtUsername().setText(loginname);
			// viewHolder.getEdtPassword().setText(password);
		}
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		viewHolder.getBtnLogin().setOnClickListener(orcl);
		viewHolder.getIvCheckcode().setOnClickListener(orcl);
		viewHolder.getBtnConfig().setOnClickListener(orcl);
	}

	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_login_login: {// 登录
				login();
				break;
			}
			case R.id.iv_login_checkcode: {// 验证码
				viewHolder.getIvCheckcode().setImageBitmap(validateCode.createBitmap());
				viewHolder.getEdtCheckcode().setText(validateCode.getCode());
				break;
			}
			case R.id.btn_login_config:
				startActivity(new Intent(mActivity, SettingServerAddressActivity.class));
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 登录
	 */
	private void login() {
		String loginName = viewHolder.getEdtUsername().getText().toString().trim();
		String password = viewHolder.getEdtPassword().getText().toString().trim();
		String code = viewHolder.getEdtCheckcode().getText().toString().trim();
		if (TextUtils.isEmpty(loginName)) {
			ToastUtil.show(mActivity, "账号不能为空", 200);
			viewHolder.getEdtUsername().requestFocus();
			return;
		} else if (TextUtils.isEmpty(password)) {
			ToastUtil.show(mActivity, "密码不能为空", 200);
			viewHolder.getEdtPassword().requestFocus();
			return;
		} else if (TextUtils.isEmpty(code)) {
			ToastUtil.show(mActivity, "验证码不能为空", 200);
			viewHolder.getEdtCheckcode().requestFocus();
			return;
		} else if (!code.toLowerCase().equals(validateCode.getCode().toLowerCase())) {
			ToastUtil.show(mActivity, "验证码有误", 200);
			viewHolder.getEdtCheckcode().requestFocus();
			return;
		}

		// 离线登录
		if (bll.checkIsExistOfflineUser(loginName, password)) {
			EdpUserInfoEntity userinfoEntity = new EdpUserInfoEntity();
			userinfoEntity.setUserNo(loginName);
			userinfoEntity.setUserPwd(password);
			// 执行离线登录
			bll.offlineLogin(userinfoEntity, mActivity);
		} else {
			EdpUserInfoEntity userInfoEntity = new EdpUserInfoEntity();
			userInfoEntity.setUserNo(loginName);
			userInfoEntity.setUserPwd(password);
			// 执行在线登录
			bll.login(userInfoEntity, mActivity);
		}

	}

}
