package com.winway.android.edcollection.adding.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.ChannelNodeStatusControll;
import com.winway.android.edcollection.adding.controll.ScenePhotoControll;
import com.winway.android.edcollection.adding.entity.ImageTypeEnum;
import com.winway.android.edcollection.adding.util.FragmentEntry;
import com.winway.android.edcollection.adding.viewholder.ChannelNodeStatusViewholder;
import com.winway.android.edcollection.adding.viewholder.ScenePhotoViewHolder;
import com.winway.android.edcollection.base.view.BaseFragment;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.media.photo.photoselector.model.PhotoModel;
import com.winway.android.media.photo.photoselector.ui.PhotoPreviewActivity;
import com.winway.android.media.photo.photoselector.util.PhotoUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.LogUtil;

/**
 * 通道节点现状
 * 
 * @author zgq
 *
 */
public class ChannelNodeStatusFragment extends BaseFragment<ChannelNodeStatusControll, ChannelNodeStatusViewholder> {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.damage_collect, container, false);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}
