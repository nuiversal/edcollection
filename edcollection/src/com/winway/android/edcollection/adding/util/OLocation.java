package com.winway.android.edcollection.adding.util;

import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.map.entity.MapCache;
import com.winway.android.sensor.geolocation.GPS;
import com.winway.android.util.LogUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import ocn.himap.markers.HiMarkerStyle;
import ocn.himap.markers.HiPointMarker;

/**
 * 定位
 * 
 * @author ly
 *
 */
public class OLocation {
	private Context mContext;
	private static OLocation instance;
	private HiPointMarker pointMarker;// 定位图标Marker
	private Bitmap locationImg;// 定位图标
	private Location tLocation;// 保存临时的Location用来判断与上个Location是否发生改变
	private final int flag_location_change = 0X0001;
	private boolean isLocation = false;// 判断是否点击定位进入的
	private boolean isFirst = true;

	private OLocation() {
	}

	private OLocation(Context context) {
		this.mContext = context;
	}

	/**
	 * 获取单例
	 * 
	 * @param context
	 * @return
	 */
	public static OLocation getInstance(Context context) {
		if (instance == null) {
			synchronized (OLocation.class) {
				if (instance == null) {
					instance = new OLocation(context);
				}
			}
		}
		return instance;
	}

	/**
	 * 判断是否有GPS模块设备
	 * 
	 * @param context
	 * @return
	 */
	public boolean hasGPSDevice() {
		final LocationManager mgr = (LocationManager) this.mContext.getSystemService(Context.LOCATION_SERVICE);
		if (mgr == null)
			return false;
		final List<String> providers = mgr.getAllProviders();
		if (providers == null)
			return false;
		return providers.contains(LocationManager.GPS_PROVIDER);
	}

	/**
	 * 定位
	 */
	public void startLocation() {
		isLocation = true;
		// 判断是否点击定位进入的是就移动到屏幕中心
		if (isLocation) {
			isLocation = false;
			// 移动到屏幕中心
			MapCache.map.smoothMove(GPS.nowLoaction.getLongitude(), GPS.nowLoaction.getLatitude());
			MapCache.map.setMapsLevel(17);
		}
		if (isFirst) {
			// 监听GPSLocation是否发生改变线程
			new Thread(new Runnable() {
				@Override
				public void run() {
					LogUtil.i("Runnable:");
					while (true) {
						// 获取GPS里的Location并且判断它是否发生改变
						if (GPS.nowLoaction != null) {
							Message msg = new Message();
							if (tLocation != GPS.nowLoaction) {
								tLocation = GPS.nowLoaction;
								msg.what = flag_location_change;
								msg.obj = tLocation;
								uiHander.sendMessage(msg);// 通知Handler
							}
						}
					}
				}
			}).start();
			isFirst = false;
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler uiHander = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Location locs = (Location) msg.obj;
			switch (msg.what) {
			// GPSLocation发生了改变就改变定位图标的位置
			case flag_location_change:
				if (GPS.nowLoaction != null) {
					// 定位图标
					if (locationImg == null) {
						locationImg = BitmapFactory.decodeResource(mContext.getResources(),
								R.drawable.ico_location_point);
					}
					if (pointMarker == null) {
						HiMarkerStyle markerStyle = new HiMarkerStyle();
						markerStyle.Icon = locationImg;
						pointMarker = new HiPointMarker();
						pointMarker.MarkerStyle = markerStyle;
						pointMarker.Title = "";
						pointMarker.name = "";
						pointMarker.nameStr = "";
						pointMarker.MarkerStyle.OffsetX = -12;
						pointMarker.MarkerStyle.OffsetY = -13;
						pointMarker.setMaplet(MapCache.map);
					}
					pointMarker.Cx = locs.getLongitude();
					pointMarker.Cy = locs.getLatitude();
					MapCache.map.addMarker(pointMarker);
					pointMarker.refresh();
					MapCache.map.refresh();

					LogUtil.i("经度:" + locs.getLongitude() + " 纬度:" + locs.getLatitude());
				}
			}
		}
	};
	
	
	public void startLocation(double lon,double lat){
		MapCache.map.smoothMove(lon,lat);
	}
}
