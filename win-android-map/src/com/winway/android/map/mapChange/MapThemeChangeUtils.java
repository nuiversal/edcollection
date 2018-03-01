package com.winway.android.map.mapChange;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winway.android.map.R;
import com.winway.android.util.ImageHelper;

/**
 * 地图切换
 * 
 * @author lyh
 * @version 创建时间：2018年1月22日
 *
 */
public class MapThemeChangeUtils{
	private Context context;
	private boolean isSelect = false;
	public int paddingLeft = 0;
	public int paddingRight= 10;
	public int paddingTop = 0;
	public int paddingBottom = 0;
	private List<MapThemeChangeEntity> list;
	private int position = 0;
	
	public MapThemeChangeUtils(Context context,List<MapThemeChangeEntity> list) {
		this.context = context;
		if (list == null) {
			this.list = new ArrayList<MapThemeChangeEntity>();
		}else {
			this.list = list;
		}
	}
	
	/**
	 * 添加底图
	 */
	final List<View> ivlistImageViews = new ArrayList<>();
	public void addMapTheme(final LinearLayout containerView) {
		if (null == list || list.isEmpty()) {
			return;
		}
		if (containerView != null) {
			containerView.removeAllViews();
		}
		for (int i = 0; i<list.size(); i++) {
			final MapThemeChangeEntity entity  = list.get(i);
			final View view = View.inflate(context, R.layout.map_theme_change, null);
			 LinearLayout llContainer = (LinearLayout) view.findViewById(R.id.ll_map_theme_change);
			llContainer.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
			final ImageView ivMapChange = (ImageView) view.findViewById(R.id.iv_map_theme_change);
			ivMapChange.setImageBitmap(ImageHelper.decodeSampledBitmapFromResource(context.getResources(),entity.getUnSelectId(), 90, 60));
			ivMapChange.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int k = 0; k < list.size(); k++) {
						if (!list.get(k).equals(entity)) {
							isSelect = false;
						}else {
							position = k;
						}
					}
					if (mapChangeViewClickListener != null) {
						mapChangeViewClickListener.onClick(position);
					}
					if (isSelect) {
						ivMapChange.setImageBitmap(ImageHelper.decodeSampledBitmapFromResource(context.getResources(),entity.getUnSelectId(), 90, 60));
						isSelect = false;
					}else {
						for (int j = 0 ; j < ivlistImageViews.size() ; j++) {
							for (int k = 0; k < list.size(); k++) {
								View view2 = ivlistImageViews.get(j);
								ImageView clickImageViewIV = (ImageView) view2.findViewById(R.id.iv_map_theme_change);
								clickImageViewIV.setImageBitmap(ImageHelper.decodeSampledBitmapFromResource(context.getResources(),list.get(j).getUnSelectId(), 90, 60));
							}
						}
						ivMapChange.setImageBitmap(ImageHelper.decodeSampledBitmapFromResource(context.getResources(),entity.getSelectId(), 90, 60));
						isSelect = true;
					}
				}
			});
			//底图名称
			TextView nameTv = (TextView) view.findViewById(R.id.tv_map_theme_change);
			nameTv.setTextColor(ColorStateList.valueOf(Color.BLACK));
			nameTv.setText(entity.getMapName());
			containerView.addView(view);
			ivlistImageViews.add(i, view);
		}
	}
	
	/**
	 * 移除底图
	 */
	public void removeMapTheme(MapThemeChangeEntity entity, LinearLayout containerView) {
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(entity)) {
					list.remove(entity);
					ivlistImageViews.remove(i);
				}
			}
			if (containerView != null) {
				containerView.removeAllViews();
			}
			for (View view : ivlistImageViews) {
				containerView.addView(view);
			}
		}
	}

	private onMapChangeViewClickListener mapChangeViewClickListener;

	public void setMapChangeViewClickListener(onMapChangeViewClickListener mapChangeViewClickListener) {
		this.mapChangeViewClickListener = mapChangeViewClickListener;
	}

	public interface onMapChangeViewClickListener {
		public void onClick(int position);
	}
}
