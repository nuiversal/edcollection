package com.winway.android.media.vr;

import com.winway.android.media.vr.PanoramaView;
import com.winway.android.util.ToastUtils;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup.LayoutParams;

public class LookVRImageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String vrImagePath = getIntent().getStringExtra("vr-image-path");
		if (TextUtils.isEmpty(vrImagePath)) {
			ToastUtils.show(this, "没有VR背景图");
		}
		PanoramaView panoramaView = new PanoramaView(this, vrImagePath);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		panoramaView.setLayoutParams(params);
		
		
		setContentView(panoramaView);
	}
}
