package com.winway.android.edcollection.login.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseActivity;
import com.winway.android.edcollection.login.controll.SettingServerAddressControll;
import com.winway.android.edcollection.login.viewholder.SettingServerAddressHolder;

import android.os.Bundle;
/**
 * 
 * @author xs
 *
 */
public class SettingServerAddressActivity extends BaseActivity<SettingServerAddressControll, SettingServerAddressHolder>{

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.setting_server_address);
	}

}
