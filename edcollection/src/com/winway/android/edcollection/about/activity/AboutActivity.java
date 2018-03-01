package com.winway.android.edcollection.about.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.about.controll.AboutControll;
import com.winway.android.edcollection.about.viewholder.AboutViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

/**
 * 关于Activity
 * 
 * @author xs
 *
 */
public class AboutActivity extends BaseActivity<AboutControll,AboutViewHolder>{

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_setting_about);
	}

}
