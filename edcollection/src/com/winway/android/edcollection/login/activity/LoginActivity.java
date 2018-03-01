package com.winway.android.edcollection.login.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseActivity;
import com.winway.android.edcollection.login.controll.LoginControll;
import com.winway.android.edcollection.login.viewholder.LoginViewHolder;

import android.os.Bundle;

/**
 * 登录页面
 * 
 * @author zgq
 *
 */
public class LoginActivity extends BaseActivity<LoginControll, LoginViewHolder> {
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_login);
	}

}
