package com.winway.android.map.selectPoint.activity;

import java.util.ArrayList;
import java.util.List;

import ocn.himap.base.HiMapsType;
import ocn.himap.events.HiEvent;
import ocn.himap.events.HiIEventListener;
import ocn.himap.events.HiMapsEvent;
import ocn.himap.maps.HiBaseMaps;
import ocn.himap.markers.HiBaseMarker;
import ocn.himap.markers.HiPointMarker;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.winway.android.map.MapLoader;
import com.winway.android.map.R;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.selectPoint.entity.GetJsonBaseEntity;
import com.winway.android.map.util.MapUtils;
import com.winway.android.network.HttpUtils;
import com.winway.android.sensor.geolocation.serviceImpl.GPSService;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.LogUtil;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtil;

/**
 * 地图选点
 *  调用此功能需在调用到此activity前调用setLngLat（）方法，将经纬度传入到此类，
 *  调用getResult（）方法拿到最终经纬度，返回到应用层。
 * @author lyh
 * @version 创建时间：2018年1月3日
 *
 */
public class MapSelectPointActivity extends Activity {
	private static HiBaseMaps maps; // 地图
	private ImageView ivReturn; // 返回
	private ImageView ivCurrentPosition; // 当前位置
	private ImageView ivLocation; // 定位
	private ImageView ivZoomIn; // 放大
	private ImageView ivZoomOut; // 缩小
	private TextView tvAboutLocation; // 大概位置
	private TextView tvSpecificLocation; // 具体位置
	private TextView tvConfirm; // 确定
	private Activity mActivity;
	private static double longitude; // 经度
	private static double latitude; // 纬度
	private Location mLocation; // 当前位置
	private GPSService.GPSChangeListener gpsChangeListener;
	private HiPointMarker locationMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		setContentView(R.layout.activity_map_select_point);
		init();
	}

	private void init() {
		mActivity.getIntent();
		initSetting();
		initMap();
		reverseGeoCod(maps);
		initEvent();
	}
	
	/**
	 * 初始配置
	 */
	private void initSetting() {
		maps = (HiBaseMaps) findViewById(R.id.maps_activity_map_select_point_map);
		ivCurrentPosition = (ImageView) findViewById(R.id.tv_map_select_point_cent_point);
		ivLocation = (ImageView) findViewById(R.id.iv_activity_map_select_point_location);
		ivReturn = (ImageView) findViewById(R.id.iv_map_select_point_return);
		ivZoomIn = (ImageView) findViewById(R.id.iv_map_select_point_zoomIn);
		ivZoomOut = (ImageView) findViewById(R.id.iv_map_select_point_zoomOut);
		tvAboutLocation = (TextView) findViewById(R.id.tv_activity_map_select_point_about_location);
		tvSpecificLocation = (TextView) findViewById(R.id.tv_activity_map_select_point_specific_location);
		tvConfirm = (TextView) findViewById(R.id.tv_activity_map_select_point_confirm);
	}
	
	/**
	 * 初始化地图
	 */
	private void initMap() {
		// 判断经纬度
		if (longitude != 0 && latitude != 0) {
			int level = maps.getMaxLevel() - 5;
			maps.initMaps(longitude, latitude, level);
		} else {
			maps.initMaps(MapCache.map.getCx(), MapCache.map.getCy(), MapCache.map.getMapsLevel());
		}
		maps.setMapsType(HiMapsType.Typical);
		maps.mapsDragInertiaEnabled = false;
		maps.show();
		maps.refresh();
		MapLoader.getInstance().addTDTTextLayer(maps);
		initLocationListener();
		addEventListener();
	}
	
	/**
	 * 设置经纬度--调用此activity前需先调此方法传入经纬度
	 * @param longitude1 经度
	 * @param latitude1 纬度
	 */
	public static void setLngLat(double longitude1,double latitude1) {
		longitude = longitude1;
		latitude = latitude1;
	}
	
	/**
	 * 拿到经纬度结果
	 * resultList.get(0) : 拿到经度
	 * resultList.get(1) : 拿到纬度
	 * @return
	 */
	public static List<Double> getResult() {
		List<Double> resultList = new ArrayList<Double>();
		resultList.add(0,maps.getCx());
		resultList.add(1,maps.getCy());
		return resultList;
	}

	/***
	 * 初始化事件
	 */
	private void initEvent() {
		ivZoomIn.setOnClickListener(orcl);
		ivZoomOut.setOnClickListener(orcl);
		ivReturn.setOnClickListener(orcl);
		ivLocation.setOnClickListener(orcl);
		tvConfirm.setOnClickListener(orcl);
	}
	
	private OnClickListener orcl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.tv_activity_map_select_point_confirm) {  //确定
				mActivity.setResult(Activity.RESULT_OK);
				mActivity.finish();
			}else if (id == R.id.iv_activity_map_select_point_location) { //定位
				try {
					moveToLocation();
					reverseGeoCod(maps);
					new Handler().postDelayed(new Runnable() {
				        @Override
				        public void run() {
				        	ivCurrentPosition.setImageResource(R.drawable.dw);
				          }
				       }, 500);    //延时1s执行
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (id == R.id.iv_map_select_point_return) { //返回
				mActivity.finish();
			}else if (id == R.id.iv_map_select_point_zoomOut) { //缩小
				MapUtils.getInstance().zoomOut(maps);
			}else if (id == R.id.iv_map_select_point_zoomIn) { //放大
				MapUtils.getInstance().zoomIn(maps);
			}
		};
	};

	/**
	 * 移动到用户当前位置
	 */
	private void moveToLocation() {
		if (mLocation != null) {
			maps.smoothMove(mLocation.getLongitude(), mLocation.getLatitude());
			//maps.setCenter((float) mLocation.getLongitude(),(float) mLocation.getLatitude());
			maps.refresh();
		} else {
			ToastUtil.showShort(mActivity, "获取当前位置异常，请检查GPS信号");
		}
	}

	/**
	 * 地图监听
	 */
	@SuppressLint("NewApi")
	private void addEventListener() {
		// 地图中心点改变事件
		maps.addEventListener(HiMapsEvent.MapsCenterChanged, new HiIEventListener() {

			@Override
			public void dispose() {
			}

			@Override
			public void Run(HiEvent arg0) {
				LogUtil.i("info", "执行了MapsCenterChanged -- Run---");
				ivCurrentPosition.setImageResource(R.drawable.dw);
				maps.refresh();
				reverseGeoCod(maps);
			}
		});
		maps.addEventListener(HiMapsEvent.MapsCenterChanging, new HiIEventListener() {
			
			@Override
			public void dispose() {
			}
			
			@Override
			public void Run(HiEvent arg0) {
				LogUtil.i("info", "执行了MapsCenterChanging -- Run---");
				ivCurrentPosition.setImageResource(R.drawable.dw_hight);
			}
		});
	}

	private final int flagSuccess = 1;// 成功标识
	private final int flagFail = 2;// 失败标识
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case flagSuccess :
				{
					ivCurrentPosition.setImageResource(R.drawable.dw);
					GetJsonBaseEntity baseEntity = (GetJsonBaseEntity)msg.obj;
					tvAboutLocation.setText(StringUtils.nullStrToEmpty(baseEntity.getResult().getFormatted_address()));
					//当前位置描述
					String description = baseEntity.getResult().getSematic_description();
					boolean contains = description.contains("附近");
					String[] split = description.split("\\d+"+"米");
					if (contains) {
						//当值带有“附近”时，截取位置到“附近”两字，去除XX米
						for (String string : split) {
							tvSpecificLocation.setText("在"+string.toString());
						}
					}else {
						//当值未带有“附近”时，去除XX米，在字符串最后加上附近两字
						for (String string : split) {
							tvSpecificLocation.setText("在"+string.toString()+"附近");
						}
					}
				}
					break;
				case flagFail :
				{
					ToastUtil.show(mActivity, "位置获取失败", 100);
				}
				break;

				default :
					break;
			}
		}
	};
	
	private void reverseGeoCod(HiBaseMaps maps) {
		final String url = "http://api.map.baidu.com/geocoder/v2/?location="+ maps.getCy()+ ","+ maps.getCx()
				+"&coordtype=wgs84ll"+"&output=json"+"&pois=1&ak=tDFe5NXB20cPQawRGYAPQYYbx7ZRp8rw&mcode="
				+ "B3:14:78:A5:FD:FA:3E:E4:81:4D:6B:49:CC:04:C1:48:D3:6D:38:14;com.winway.android.map&qq-pf-to=pcqq.c2c";
		new Thread( new Runnable() {
			@Override
			public void run() {
				String json = HttpUtils.doGet(url);
				GetJsonBaseEntity baseEntity = GsonUtils.build().fromJson(json, GetJsonBaseEntity.class);
				Message handlermessage = Message.obtain();
				if (baseEntity == null) {
					handlermessage.what = flagFail;
					handler.sendMessage(handlermessage);
				}else {
					handlermessage.what = flagSuccess;
					handlermessage.obj = baseEntity;
					handler.sendMessage(handlermessage);
				}
			}
		}).start();	
	}

	private void initLocationListener() {
		gpsChangeListener = new GPSService.GPSChangeListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onOrientation(int degree) {
			}

			@Override
			public void onGPSCount(int count) {
			}

			@Override
			public void onEnabled() {
			}

			@Override
			public void onDisabled() {
			}

			@Override
			public void onChanged(Location location) {
				refreshLocationMarker(location.getLongitude(), location.getLatitude());
				mLocation = location;
			}
		};
		GPSService.addListener(gpsChangeListener);
	}

	/**
	 * 刷新位置
	 * 
	 * @param x
	 * @param y
	 */
	private void refreshLocationMarker(double x, double y) {
		if (locationMarker == null) {
			locationMarker = new HiPointMarker();
			locationMarker.MarkerStyle.Icon = BitmapFactory.decodeResource(
					mActivity.getResources(), R.drawable.location);
			locationMarker.MarkerStyle.OffsetX = -12;
			locationMarker.MarkerStyle.OffsetY = -13;
		}
		locationMarker.Cx = x;
		locationMarker.Cy = y;
		addMarker(locationMarker);
	}

	/**
	 * 添加marker
	 * 
	 * @param marker
	 */
	private void addMarker(HiBaseMarker marker) {
		marker.setMaplet(maps);
		if (maps != null) {
			maps.addMarker(marker);
			marker.refresh();
			maps.refresh();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		maps.dispose();
		maps = null;
	}
}
