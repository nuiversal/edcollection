package com.winway.android.edcollection.main.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseActivity;
import com.winway.android.edcollection.base.view.BaseFragmentActivity;
import com.winway.android.edcollection.main.controll.MainControll;
import com.winway.android.edcollection.main.viewholder.MainViewHolder;
import com.winway.android.util.ToastUtil;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

/**
 * 主界面
 * 
 * @author zgq
 *
 */
public class MainActivity extends BaseFragmentActivity<MainControll, MainViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

}
