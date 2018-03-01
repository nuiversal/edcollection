package com.winway.android.media.photo.photoselector.ui;

import java.util.ArrayList;

import com.winway.android.media.R;
import com.winway.android.media.photo.photoselector.model.PhotoModel;
import com.winway.android.media.photo.photoselector.ui.PhotoItem.onItemClickListener;
import com.winway.android.media.photo.photoselector.ui.PhotoItem.onPhotoItemCheckedListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.TextView;

/**
 * 照片选择适配器
 * 
 * @author Aizaz AZ
 *
 */

public class PhotoSelectorAdapter extends MBaseAdapter<PhotoModel> {

	private int itemWidth;
	private int horizentalNum = 3;
	private onPhotoItemCheckedListener listener;
	private LayoutParams itemLayoutParams;
	private onItemClickListener mCallback;
	private OnClickListener cameraListener;

	private PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models) {
		super(context, models);
	}

	public PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models, int screenWidth,
			onPhotoItemCheckedListener listener, onItemClickListener mCallback, OnClickListener cameraListener) {
		this(context, models);
		setItemWidth(screenWidth);
		this.listener = listener;
		this.mCallback = mCallback;
		this.cameraListener = cameraListener;
	}

	/** 设置每一个Item的宽高 */
	public void setItemWidth(int screenWidth) {
		int horizentalSpace = context.getResources().getDimensionPixelSize(R.dimen.sticky_item_horizontalSpacing);
		this.itemWidth = (screenWidth - (horizentalSpace * (horizentalNum - 1))) / horizentalNum;
		this.itemLayoutParams = new LayoutParams(itemWidth, itemWidth);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PhotoItem item = null;
		if (convertView == null || !(convertView instanceof PhotoItem)) {
			item = new PhotoItem(context, listener);
			item.setLayoutParams(itemLayoutParams);
			convertView = item;
		} else {
			item = (PhotoItem) convertView;
		}
		item.setImageDrawable(models.get(position));
		item.setSelected(models.get(position).isChecked());
		item.setOnClickListener(mCallback, position);
		return convertView;
	}
}
