package com.winway.android.media.photo.photoselector.ui;

/**
 * 
 * @author Aizaz AZ
 *
 */
import java.util.ArrayList;

import com.winway.android.media.photo.photoselector.model.AlbumModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 相册列表适配器
 * 
 * @author zgq
 *
 */
public class AlbumAdapter extends MBaseAdapter<AlbumModel> {

	public AlbumAdapter(Context context, ArrayList<AlbumModel> models) {
		super(context, models);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AlbumItem albumItem = null;
		if (convertView == null) {
			albumItem = new AlbumItem(context);
			convertView = albumItem;
		} else
			albumItem = (AlbumItem) convertView;
		albumItem.update(models.get(position));
		return convertView;
	}

}
