package com.winway.android.map;

import com.winway.android.map.entity.MapConfigs;
import com.winway.android.map.entity.MapType;
import com.winway.android.map.util.MapToolUtil;
import com.winway.android.map.util.MapToolUtils;
import com.winway.android.map.util.MapUtils;
import com.winway.android.util.FileUtil;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import ocn.himap.base.HiCoordinatesType;
import ocn.himap.base.HiMapsDataType;
import ocn.himap.base.HiMapsType;
import ocn.himap.config.HiMapsConfig;
import ocn.himap.layer.HiBaseTileLayer;
import ocn.himap.layer.TDTLayer;
import ocn.himap.maps.HiBaseMaps;

/**
 * 地图加载器
 * 
 * @author zgq
 *
 */
public class MapLoader {

	private static MapLoader instance;
	private TDTLayer tdtLayer;

	private MapLoader() {

	}

	public static MapLoader getInstance() {
		if (instance == null) {
			synchronized (MapLoader.class) {
				if (instance == null) {
					instance = new MapLoader();
				}
			}
		}
		return instance;
	}

	/**
	 * 获取地图对象
	 * 
	 * @param context
	 * @return
	 */
	public HiBaseMaps getMap(Context context) {
		HiBaseMaps hiBaseMaps = null;
		View mapView = View.inflate(context, R.layout.map, null);
		hiBaseMaps = (HiBaseMaps) mapView.findViewById(R.id.map_container);
		return hiBaseMaps;
	}

	/**
	 * 获取地图控件所在的界面
	 * 
	 * @param context
	 * @return
	 */
	public View getMapContainer(Context context) {
		View view = null;
		view = View.inflate(context, R.layout.map, null);
		return view;
	}

	/**
	 * 初始化地图
	 * 
	 * @param context
	 * @param map
	 *            地图对象
	 * @param mapsLocalRoot
	 *            地图根目录
	 * @param mapsCacheRoot
	 *            地图缓存目录
	 * @param centerX
	 *            地图中心点x坐标
	 * @param centerY
	 *            地图中心点y坐标
	 * @param level
	 *            初始等级
	 */
	public void initMap(Context context, HiBaseMaps map, String mapsLocalRoot, String mapsCacheRoot, double centerX,
			double centerY, int level) {
		// 加载xml中的配置
		MapUtils.readMapsConfigs(context);
		HiMapsConfig.MapsLocalRoot = mapsLocalRoot;
		HiMapsConfig.MapsCacheRoot = mapsCacheRoot;
		map.setMapsType(HiMapsType.Typical);
		map.mapsDragInertiaEnabled = false;
		setMapsConfig(MapType.MAP_2D.getValue());
		map.initMaps(centerX, centerY, level);
		// 添加地图工具
		MapToolUtils.getInstance().addMapTools(context);
		//优化版添加地图工具
		MapToolUtil.getInstance(context).addMapTools(context);
		map.show();
		map.refresh();
	}

	/**
	 * 初始化地图
	 * 
	 * @param context
	 * @param map
	 *            地图对象
	 * @param mapsLocalRoot
	 *            地图根目录
	 * @param mapsCacheRoot
	 *            地图缓存目录
	 * @param centerX
	 *            地图中心点x坐标
	 * @param centerY
	 *            地图中心点y坐标
	 * @param level
	 *            初始等级
	 * @param 地图类型
	 */
	public void initMap(Context context, HiBaseMaps map, String mapsLocalRoot, String mapsCacheRoot, double centerX,
			double centerY, int level, MapType mapType) {
		// 加载xml中的配置
		MapUtils.readMapsConfigs(context);
		HiMapsConfig.MapsLocalRoot = mapsLocalRoot;
		HiMapsConfig.MapsCacheRoot = mapsCacheRoot;
		//map.setMapsType(mapType.MAP_CUSTOM.getValue());
		map.mapsDragInertiaEnabled = false;
		setMapsConfig(mapType.getValue());
		map.initMaps(centerX, centerY, level);
		// 添加地图工具
		MapToolUtils.getInstance().addMapTools(context);
		//优化版添加地图工具
		MapToolUtil.getInstance(context).addMapTools(context);
		map.show();
		map.refresh();
	}

	/**
	 * 添加天地图文字标注图层
	 * 
	 */
	public void addTDTTextLayer(HiBaseMaps map) {
		if (tdtLayer == null) {
			tdtLayer = new TDTLayer("http://t0.tianditu.com/");
			tdtLayer.nameStr = "tdtTextLayer";
			tdtLayer.pMaplet = map;
		}
		map.addLayer(tdtLayer);
		tdtLayer.show();
		map.refresh();
	}

	/**
	 * 添加图层
	 * 
	 * @param map
	 */
	public void addLayer(HiBaseMaps map) {
		String url = "http://t0.tianditu.com/";
		HiBaseTileLayer layer = new HiBaseTileLayer(url);
		layer.nameStr = "tdtTextLayer";
		layer.pMaplet = map;
		map.addLayer(layer);
		layer.show();
		map.refresh();
	}

	/**
	 * 配置地图参数
	 * 
	 * @param strFlag
	 *            "2d"或"25d"或custom
	 */
	private static void setMapsConfig(String strFlag) {
		/******************************************************/
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

}
