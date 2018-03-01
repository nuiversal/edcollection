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
import com.winway.android.edcollection.adding.controll.ScenePhotoControll;
import com.winway.android.edcollection.adding.entity.ImageTypeEnum;
import com.winway.android.edcollection.adding.util.FragmentEntry;
import com.winway.android.edcollection.adding.viewholder.ScenePhotoViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.view.BaseFragment;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.media.photo.photoselector.model.PhotoModel;
import com.winway.android.media.photo.photoselector.ui.PhotoPreviewActivity;
import com.winway.android.media.photo.photoselector.util.PhotoUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.LogUtil;

/**
 * 路径点照片
 * 
 * @author zgq
 *
 */
public class ScenePhotoFragment extends BaseFragment<ScenePhotoControll, ScenePhotoViewHolder> {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.scene_photo, container, false);
		LogUtil.e("ScenePhotoFragment", "initView");
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtil.e("ScenePhotoFragment", "onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.e("ScenePhotoFragment", "onCreate");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.e("ScenePhotoFragment", "onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtil.e("ScenePhotoFragment", "onStart");

	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtil.e("ScenePhotoFragment", "onResume");
		if (!GlobalEntry.editNode) {
			if (!FragmentEntry.isSelectMap) {
				if (FragmentEntry.getInstance().positionPhotoAbsolutelyRoot != null) {
					getAction().showPhoto(FragmentEntry.getInstance().positionPhotoAbsolutelyRoot,
							getAction().getViewHolder().getIvPostionPhoto(),
							getAction().getViewHolder().getIvPositionDelete());
					getAction().setPositionPhotoAbsolutelyRoot(FragmentEntry.getInstance().positionPhotoAbsolutelyRoot);
				}
				if (FragmentEntry.getInstance().backgroundPhotoAbsolutelyRoot != null) {
					getAction().showPhoto(FragmentEntry.getInstance().backgroundPhotoAbsolutelyRoot,
							getAction().getViewHolder().getIvBackgroundPhoto(),
							getAction().getViewHolder().getIvBackgroundDelete());
					getAction().setBackgroundPhotoAbsolutelyRoot(
							FragmentEntry.getInstance().backgroundPhotoAbsolutelyRoot);
				}
				if (!FragmentEntry.getInstance().attachs.isEmpty()) {
					getAction().showAttachmentCache(FragmentEntry.getInstance().attachs);
					FragmentEntry.getInstance().attachs.clear();
				}
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtil.e("ScenePhotoFragment", "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtil.e("ScenePhotoFragment", "onStop");
		if (!GlobalEntry.editNode) {
			if (FragmentEntry.isSelectMap) {
			FragmentEntry.getInstance().positionPhotoAbsolutelyRoot = this.getAction().getPositionPhotoAbsolutelyRoot();
			FragmentEntry.getInstance().backgroundPhotoAbsolutelyRoot = this.getAction()
					.getBackgroundPhotoAbsolutelyRoot();
				if (getAction().obtainAttachsPath()) {
					FragmentEntry.getInstance().attachs.addAll(getAction().getAttachFiles());
				}
			getAction().removePhoto(getAction().getViewHolder().getIvPostionPhoto(),
					getAction().getViewHolder().getIvPositionDelete());
			getAction().removePhoto(getAction().getViewHolder().getIvBackgroundPhoto(),
					getAction().getViewHolder().getIvBackgroundDelete());
			}
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtil.e("ScenePhotoFragment", "onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.e("ScenePhotoFragment", "onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LogUtil.e("ScenePhotoFragment", "onDetach");
	}
}
