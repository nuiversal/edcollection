package com.winway.android.edcollection.task.activity;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.view.BaseActivity;
import com.winway.android.edcollection.task.controll.EmTaskDetailsController;
import com.winway.android.edcollection.task.viewholder.EmTaskDetailsHolder;

import android.os.Bundle;

public class EmTaskDetailActivity extends BaseActivity<EmTaskDetailsController, EmTaskDetailsHolder> {

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_emtask_details);
	}

}
