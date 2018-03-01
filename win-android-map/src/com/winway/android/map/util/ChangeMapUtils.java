package com.winway.android.map.util;

import com.winway.android.map.R;
import com.winway.android.map.entity.MapCache;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import ocn.himap.base.HiMapsType;
import ocn.himap.maps.HiBaseMaps;

/**
 * 地图切换
 * 
 * @author zgq
 *
 */
public class ChangeMapUtils {
	private static ChangeMapUtils instance;
	/* 地图切换---开始 */
	private boolean isOpenMapSwitch = false;// 是否打开地图切换缩略图
	private PopupWindow popupWindowMapSwitch = null;// 地图切换
	private View mapThumbnailView = null;// 地图缩列图view
	private ImageView vectorIv; // 矢量地图缩略图
	private TextView vectorTv;// 矢量地图说明文字
	private ImageView satelliteIv;// 卫星地图缩略图
	private TextView satelliteTv;// 卫星地图说明文字
	/* 地图切换---结束 */

	public static ChangeMapUtils getInstance() {
		if (instance == null) {
			synchronized (ChangeMapUtils.class) {
				if (instance == null) {
					instance = new ChangeMapUtils();
				}
			}
		}

		return instance;
	}

	private ChangeMapUtils() {

	}

	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.map_switch_vector) {// 矢量
				resetMapThumbnail();
				vectorIv.setImageResource(R.drawable.ic_vector_d);
				vectorTv.setTextColor(Color.BLACK);
				satelliteIv.setImageResource(R.drawable.ic_satellite);
				satelliteTv.setTextColor(Color.GRAY);
				if (hiBaseMapsTmp != null) {
					hiBaseMapsTmp.setMapsType(HiMapsType.Typical);
				} else {
					MapCache.map.setMapsType(HiMapsType.Typical);
				}
			} else if (id == R.id.map_switch_satellite) {// 卫星
				resetMapThumbnail();
				satelliteIv.setImageResource(R.drawable.ic_satellite_d);
				satelliteTv.setTextColor(Color.BLACK);
				vectorIv.setImageResource(R.drawable.ic_vector);
				vectorTv.setTextColor(Color.GRAY);
				if (hiBaseMapsTmp != null) {
					hiBaseMapsTmp.setMapsType(HiMapsType.Airscape);
				} else {
					MapCache.map.setMapsType(HiMapsType.Airscape);
				}
			}
		}
	};
	/**
	 * 另外一个地图对象
	 */
	private HiBaseMaps hiBaseMapsTmp;

	/**
	 * 另外一个地图对象的时候用到,更改地图类型要设置进去，完了之后请设回null
	 * 
	 * @param hiBaseMaps
	 */
	public void setHiBaseMapsTmp(HiBaseMaps hiBaseMaps) {
		this.hiBaseMapsTmp = hiBaseMaps;
	}

	/**
	 * 地图切换
	 * 
	 * @param context
	 * @param mapSwitch
	 *            触发按钮
	 */
	public void changeMap(Context context, final ImageButton mapSwitch) {

		if (isOpenMapSwitch) {
			isOpenMapSwitch = false;
			if (popupWindowMapSwitch != null) {
				popupWindowMapSwitch.dismiss();
				mapSwitch.setImageResource(R.drawable.ic_mapswitcher);
			}

		} else {
			isOpenMapSwitch = true;
			if (popupWindowMapSwitch == null) {
				popupWindowMapSwitch = new PopupWindow();
				View contentView = View.inflate(context, R.layout.map_switch, null);
				mapThumbnailView = contentView;
				// 矢量
				View vectorView = contentView.findViewById(R.id.map_switch_vector);
				vectorIv = (ImageView) vectorView.findViewById(R.id.iv_map_thumbnail);
				vectorIv.setImageResource(R.drawable.ic_vector_d);
				vectorTv = (TextView) vectorView.findViewById(R.id.tv_map_thumbnail);
				vectorTv.setText("矢量地图");
				vectorTv.setTextColor(Color.BLACK);
				vectorView.setOnClickListener(orcl);

				// 卫星
				View satelliteView = contentView.findViewById(R.id.map_switch_satellite);
				satelliteIv = (ImageView) satelliteView.findViewById(R.id.iv_map_thumbnail);
				satelliteIv.setImageResource(R.drawable.ic_satellite);
				satelliteTv = (TextView) satelliteView.findViewById(R.id.tv_map_thumbnail);
				satelliteTv.setText("卫星地图");
				satelliteTv.setTextColor(Color.GRAY);
				satelliteView.setOnClickListener(orcl);

				popupWindowMapSwitch.setContentView(contentView);
				popupWindowMapSwitch.setWidth(LayoutParams.WRAP_CONTENT);
				popupWindowMapSwitch.setHeight(LayoutParams.WRAP_CONTENT);

				// popupWindowMapSwitch.setFocusable(false);
				// popupWindowMapSwitch.setOutsideTouchable(true);

				popupWindowMapSwitch.setTouchable(true);
				popupWindowMapSwitch.setFocusable(true);
				popupWindowMapSwitch.setBackgroundDrawable(new BitmapDrawable());
				popupWindowMapSwitch.setOutsideTouchable(true);

				popupWindowMapSwitch.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						mapSwitch.setImageResource(R.drawable.ic_mapswitcher);
						isOpenMapSwitch = false;
					}
				});

			}
			popupWindowMapSwitch.showAsDropDown(mapSwitch);
			mapSwitch.setImageResource(R.drawable.ic_close);
		}

	}

	/**
	 * 地图切换
	 * 
	 * @param View
	 * 
	 */
	public void changeMap(View contentView) {
		View vectorView = contentView.findViewById(R.id.map_switch_vector);
		vectorIv = (ImageView) vectorView.findViewById(R.id.iv_map_thumbnail);
		vectorIv.setImageResource(R.drawable.ic_vector_d);
		vectorTv = (TextView) vectorView.findViewById(R.id.tv_map_thumbnail);
		vectorTv.setText("矢量地图");
		vectorTv.setTextColor(Color.BLACK);

		View satelliteView = contentView.findViewById(R.id.map_switch_satellite);
		satelliteIv = (ImageView) satelliteView.findViewById(R.id.iv_map_thumbnail);
		satelliteIv.setImageResource(R.drawable.ic_satellite);
		satelliteTv = (TextView) satelliteView.findViewById(R.id.tv_map_thumbnail);
		satelliteTv.setText("卫星地图");
		satelliteTv.setTextColor(Color.GRAY);

		vectorView.setOnClickListener(orcl);
		satelliteView.setOnClickListener(orcl);
	}

	/**
	 * 重置地图缩列图
	 */
	private void resetMapThumbnail() {
		if (mapThumbnailView != null) {
			// 矢量
			View vectorView = mapThumbnailView.findViewById(R.id.map_switch_vector);
			vectorIv = (ImageView) vectorView.findViewById(R.id.iv_map_thumbnail);
			vectorIv.setImageResource(R.drawable.ic_vector);
			// 卫星
			View satelliteView = mapThumbnailView.findViewById(R.id.map_switch_satellite);
			satelliteIv = (ImageView) satelliteView.findViewById(R.id.iv_map_thumbnail);
			satelliteIv.setImageResource(R.drawable.ic_satellite);

		}
	}

}
