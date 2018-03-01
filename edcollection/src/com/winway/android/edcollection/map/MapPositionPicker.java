package com.winway.android.edcollection.map;

import ocn.himap.base.HiMapsType;
import ocn.himap.config.HiMapsConfig;
import ocn.himap.events.HiEvent;
import ocn.himap.events.HiIEventListener;
import ocn.himap.events.HiMapsEvent;
import ocn.himap.maps.HiBaseMaps;
import ocn.himap.markers.HiImageMarker;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winway.android.edcollection.R;
import com.winway.android.map.MapLoader;
import com.winway.android.map.entity.MapConfigs;
import com.winway.android.map.entity.MapType;
import com.winway.android.map.util.MapUtils;
import com.winway.android.util.DensityUtil;

/**
 * 地图选点工具 用法： MapPositionPicker.getInstance().setOnPickListener(l);
 * MapPositionPicker.getInstance().open(context, 113, 23, 16);
 * 
 * @author WINWAY
 *
 */
public class MapPositionPicker {

	private AlertDialog mAlertDialog;
	private HiBaseMaps mMap;
	private HiImageMarker mImageMarker;

	private OnPickListener onPickListener;

	private static MapPositionPicker _instance;

	public static MapPositionPicker getInstance() {
		if (_instance == null) {
			synchronized (MapPositionPicker.class) {
				if (_instance == null) {
					_instance = new MapPositionPicker();
				}
			}
		}
		return _instance;
	}

	public void setOnPickListener(OnPickListener onPickListener) {
		this.onPickListener = onPickListener;
	}

	/**
	 * 打开
	 * 
	 * @param context
	 * @param title
	 * @param x
	 * @param y
	 * @param l
	 */
	public void open(Context context, String title, double x, double y, int l) {
		close();
		mMap = createMap(context, x, y, l);
		MapLoader.getInstance().addLayer(mMap);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		mAlertDialog = builder.create();
		mAlertDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				dispose();
			}
		});
		mAlertDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dispose();
			}
		});
		mAlertDialog.show();
		Window window = mAlertDialog.getWindow();
		window.setContentView(createView(context, title, x, y, l, mMap));
	}

	private View createView(final Context context, String title, final double x, final double y, final int l,
			final HiBaseMaps map) {
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
		int dp1 = DensityUtil.dip2px(context, 15);
		int dp2 = DensityUtil.dip2px(context, 23);

		LinearLayout mContainer = new LinearLayout(context);
		mContainer.setOrientation(LinearLayout.VERTICAL);
		mContainer.setPadding(0, 0, 0, 0);

		LinearLayout titleLayout = new LinearLayout(context);
		titleLayout.setOrientation(LinearLayout.HORIZONTAL);
		titleLayout.setPadding(0, 0, 0, 0);
		TextView titleTxt = new TextView(context);
		titleTxt.setPadding(dp1, dp1, dp1, dp1);
		titleTxt.setTextColor(0xffffffff);
		titleTxt.setGravity(Gravity.CENTER_VERTICAL);
		titleTxt.setBackgroundResource(R.drawable.bg_title_bar);
		titleTxt.setText(title);
		Button btnClose = new Button(context);
		btnClose.setTextColor(0xffffffff);
		btnClose.setPadding(dp2, dp1, dp2, dp1);
		btnClose.setBackgroundColor(0xff0099cc);
		btnClose.setText("X");
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAlertDialog.dismiss();
			}
		});
		titleLayout.addView(titleTxt, lp1);
		titleLayout.addView(btnClose);

		LinearLayout bottomLayout = new LinearLayout(context);
		bottomLayout.setOrientation(LinearLayout.HORIZONTAL);
		bottomLayout.setPadding(0, 0, 0, 0);
		Button btn1 = new Button(context);
		btn1.setTextColor(0xffffffff);
		btn1.setPadding(dp1, dp1, dp1, dp1);
		btn1.setBackgroundResource(R.drawable.bg_title_bar);
		btn1.setText("重置位置");
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onPickListener != null) {
					map.smoothMove(x, y);
				} else {
					map.setCenter(x, y);
				}
			}
		});
		Button btn2 = new Button(context);
		btn2.setTextColor(0xffffffff);
		btn2.setPadding(dp1, dp1, dp1, dp1);
		btn2.setBackgroundResource(R.drawable.bg_title_bar);
		btn2.setText(onPickListener == null ? "确定" : "更新位置");
		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onPickListener != null) {
					showConfirmDialog(context);
				} else {
					mAlertDialog.dismiss();
				}
			}
		});
		bottomLayout.addView(btn2, lp1);
		bottomLayout.addView(btn1, lp1);

		mContainer.addView(titleLayout);
		mContainer.addView(map, lp2);
		mContainer.addView(bottomLayout);
		return mContainer;
	}

	private void showConfirmDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog_app_style);
		builder.setTitle("确定要更新吗");
		builder.setPositiveButton("是", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (onPickListener != null) {
					onPickListener.onPicked(mMap.getCx(), mMap.getCy());
				}
				mAlertDialog.dismiss();
			}
		});
		builder.setNegativeButton("否", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();
	}

	/**
	 * 关闭
	 */
	public void close() {
		if (mAlertDialog != null) {
			if (mAlertDialog.isShowing()) {
				mAlertDialog.dismiss();
			}
			mAlertDialog = null;
		}
		dispose();
	}

	/**
	 * 释放
	 */
	private void dispose() {
		if (mMap != null) {
			if (mImageMarker != null) {
				mMap.removeMarker(mImageMarker);
				mImageMarker.dispose();
				mImageMarker = null;
			}
			mMap.dispose();
			mMap = null;
		}
	}

	/**
	 * 创建地图
	 * 
	 * @param context
	 * @param x
	 * @param y
	 * @param l
	 * @return
	 */
	private HiBaseMaps createMap(Context context, double x, double y, int l) {
		HiBaseMaps map = new HiBaseMaps(context);
		// 加载xml中的配置
		MapUtils.readMapsConfigs(context);
		map.setMapsType(HiMapsType.Typical);
		map.mapsDragInertiaEnabled = false;
		setMapsConfig(MapType.MAP_2D.getValue());
		map.initMaps(x, y, l);
		map.show();
		map.refresh();
		if (onPickListener != null) {
			addMapEvent(map);
		} else {
			map.mapsZoomEnabled = false;// 不让缩放
			map.mapsMoveEnabled = false;// 不让移动
			// map.setOnTouchListener(new View.OnTouchListener() {
			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// return true;
			// }
			// });
		}
		addMarker(context, map, x, y);
		return map;
	}

	private void addMapEvent(HiBaseMaps map) {
		map.mapsZoomEnabled = false;// 不让缩放
		map.addEventListener(HiMapsEvent.MapsCenterChanging, new HiIEventListener() {
			@Override
			public void dispose() {

			}

			@Override
			public void Run(HiEvent e) {
				if (mImageMarker != null) {
					HiMapsEvent event = (HiMapsEvent) e;
					mImageMarker.MarkerStyle.OffsetY = -74;
					mImageMarker.Cx = event.cx;
					mImageMarker.Cy = event.cy;
					mImageMarker.refresh();
				}
			}
		});
		map.addEventListener(HiMapsEvent.MapsCenterChanged, new HiIEventListener() {
			@Override
			public void dispose() {

			}

			@Override
			public void Run(HiEvent e) {
				if (mImageMarker != null) {
					HiMapsEvent event = (HiMapsEvent) e;
					mImageMarker.MarkerStyle.OffsetY = -64;
					mImageMarker.Cx = event.cx;
					mImageMarker.Cy = event.cy;
					mImageMarker.refresh();
				}
			}
		});
	}

	private void addMarker(Context context, HiBaseMaps map, double x, double y) {
		mImageMarker = new HiImageMarker();
		mImageMarker.setMaplet(map);
		mImageMarker.Cx = x;
		mImageMarker.Cy = y;
		mImageMarker.MarkerStyle.Icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.gps_g_n);
		mImageMarker.MarkerStyle.OffsetX = -32;
		mImageMarker.MarkerStyle.OffsetY = -64;
		map.addMarker(mImageMarker);
	}

	private void setMapsConfig(String strFlag) {
		int flag = 0;
		for (int i = 0; i < MapConfigs.maps.size(); i++) {
			if (MapConfigs.maps.get(i).name.equals(strFlag)) {
				flag = i;
				break;
			}
		}
		try {
			HiMapsConfig.MapsRoot = MapConfigs.maps.get(flag).MapsRoot;
			HiMapsConfig.MapsCoordinatesType = MapConfigs.maps.get(flag).MapsCoordinatesType;
			// HiMapsConfig.MapsDataType = HiMapsDataType.HiTarget;
			HiMapsConfig.MapsDataType = MapConfigs.maps.get(flag).MapsDataType;
			HiMapsConfig.Directorys = MapConfigs.maps.get(flag).Directorys;
			HiMapsConfig.ScaleFactors = MapConfigs.maps.get(flag).ScaleFactors;
			HiMapsConfig.GridFactors = MapConfigs.maps.get(flag).GridFactors;
			HiMapsConfig.MapsLevelCount = MapConfigs.maps.get(flag).MapsLevelCount;
			HiMapsConfig.OriginX = MapConfigs.maps.get(flag).OriginX;
			HiMapsConfig.OriginY = MapConfigs.maps.get(flag).OriginY;
			HiMapsConfig.MapsImageType = MapConfigs.maps.get(flag).MapsImageType;
			HiMapsConfig.MapsImageWidth = MapConfigs.maps.get(flag).MapsImageWidth;
			HiMapsConfig.MapsImageHeight = MapConfigs.maps.get(flag).MapsImageHeight;
			HiMapsConfig.DataImageWidth = MapConfigs.maps.get(flag).DataImageWidth;
			HiMapsConfig.DataImageHeight = MapConfigs.maps.get(flag).DataImageHeight;
			HiMapsConfig.MatrixWidth = MapConfigs.maps.get(flag).MatrixWidth;
			HiMapsConfig.MatrixHeight = MapConfigs.maps.get(flag).MatrixHeight;
			HiMapsConfig.ViewAngle = MapConfigs.maps.get(flag).ViewAngle;
			HiMapsConfig.MapsAngle = MapConfigs.maps.get(flag).MapsAngle;
			HiMapsConfig.ConversionCoefficient = MapConfigs.maps.get(flag).ConversionCoefficient;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public interface OnPickListener {
		void onPicked(double x, double y);
	}

}
