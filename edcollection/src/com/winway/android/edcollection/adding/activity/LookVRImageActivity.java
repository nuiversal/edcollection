package com.winway.android.edcollection.adding.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.LookVrImageController;
import com.winway.android.edcollection.adding.viewholder.LookVrImageViewHolder;
import com.winway.android.edcollection.base.view.BaseActivity;

import android.os.Bundle;

public class LookVRImageActivity extends BaseActivity<LookVrImageController, LookVrImageViewHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.layout_vr_image_show);
	}

}
