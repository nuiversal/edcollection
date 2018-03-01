package com.winway.android.map.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;

import com.winway.android.map.MapLoader;
import com.winway.android.map.R;
import com.winway.android.map.entity.LayerItemConfig;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.entity.MapConfigs;
import com.winway.android.map.entity.MapItemConfig;
import com.winway.android.map.entity.MapToolState;
import com.winway.android.util.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import ocn.himap.base.HiMapsType;
import ocn.himap.events.HiEvent;
import ocn.himap.events.HiIEventListener;
import ocn.himap.events.HiToolEvent;
import ocn.himap.manager.HiToolManager;
import ocn.himap.maps.HiBaseMaps;
import ocn.himap.tools.HiAreaTool;
import ocn.himap.tools.HiDistaceTool;

/**
 * 地图处理工具类
 * 
 * @author zgq
 *
 */
public class MapUtils {

	private static MapUtils instance;

	private MapUtils() {
	}

	public static MapUtils getInstance() {
		if (instance == null) {
			synchronized (MapUtils.class) {
				if (instance == null) {
					instance = new MapUtils();
				}
			}

		}
		return instance;
	}

	/**
	 * 读取地图配置，并保存在缓存类中
	 * 
	 * @param context
	 */
	public static void readMapsConfigs(Context context) {
		try {
			String fileName = "mapsconfig.xml";
			AssetManager assetManager = context.getAssets();
			InputStream inputStream = assetManager.open(fileName);
			XmlPullParser xmlParse = Xml.newPullParser();
			xmlParse.setInput(inputStream, "utf-8");
			int evnType = xmlParse.getEventType();
			String element;
			MapItemConfig mapItemConfig = null;
			LayerItemConfig layer = null;
			while (evnType != XmlPullParser.END_DOCUMENT) {
				switch (evnType) {
				case XmlPullParser.START_DOCUMENT: {
					break;
				}
				case XmlPullParser.START_TAG: {
					element = xmlParse.getName();
					if (element.equals("maps")) {
						if (MapConfigs.maps == null)
							MapConfigs.maps = new ArrayList<MapItemConfig>();
					}
					if (MapConfigs.maps != null) {
						if (element.equals("map")) {
							mapItemConfig = new MapItemConfig();
						}
						if (element.equals("Name")) {// 地图名称
							mapItemConfig.name = StringUtils.getInstance().replaceBlank(xmlParse.nextText());
						}
						if (element.equals("MapsRoot")) {// 地图地址
							mapItemConfig.MapsRoot = StringUtils.getInstance().replaceBlank(xmlParse.nextText());
						}
						if (element.equals("MapsCoordinatesType")) {// 地图坐标类型
							String result = StringUtils.getInstance().replaceBlank(xmlParse.nextText())
									.toLowerCase(Locale.getDefault());
							if (result.equalsIgnoreCase("wgs84")) {
								mapItemConfig.MapsCoordinatesType = "WGS84";
							}
							if (result.equalsIgnoreCase("beijing54")) {
								mapItemConfig.MapsCoordinatesType = "BeiJing54";
							}
						}
						if (element.equals("MapsDataType")) {// 地图数据类型
							String result = StringUtils.getInstance().replaceBlank(xmlParse.nextText())
									.toLowerCase(Locale.getDefault());
							if (result.equalsIgnoreCase("tianditu")) {
								mapItemConfig.MapsDataType = "TianDiTu";
							}
							if (result.equalsIgnoreCase("ocn")) {
								mapItemConfig.MapsDataType = "OCN";
							}
							if (result.equalsIgnoreCase("hitarget")) {
								mapItemConfig.MapsDataType = "HiTarget";
							}
							if (result.equalsIgnoreCase("wmts")) {
								mapItemConfig.MapsDataType = "WMTS";
							}
						}
						if (element.equals("Directorys")) {// 地图等级目录
							mapItemConfig.Directorys = StringUtils.getInstance().replaceBlank(xmlParse.nextText())
									.split(",");
						}
						if (element.equals("ScaleFactors")) {// 地图分辨率因子
							mapItemConfig.ScaleFactors = StringUtils.getInstance().parse2DoubleArray(
									StringUtils.getInstance().replaceBlank(xmlParse.nextText()), ",");
						}
						if (element.equals("GridFactors")) {// 网格因子
							mapItemConfig.GridFactors = StringUtils.getInstance()
									.parse2IntArray(StringUtils.getInstance().replaceBlank(xmlParse.nextText()), ",");
						}
						if (element.equals("MapsLevelCount")) {// 地图等级数
							mapItemConfig.MapsLevelCount = Integer
									.parseInt(StringUtils.getInstance().replaceBlank(xmlParse.nextText()));
						}
						if (element.equals("OriginX")) {// 地图原点x坐标
							mapItemConfig.OriginX = Double
									.parseDouble(StringUtils.getInstance().replaceBlank(xmlParse.nextText()));
						}
						if (element.equals("OriginY")) {// 地图原点y坐标
							mapItemConfig.OriginY = Double
									.parseDouble(StringUtils.getInstance().replaceBlank(xmlParse.nextText()));
						}
						if (element.equals("MapsImageType")) {// 地图图片类型
							mapItemConfig.MapsImageType = StringUtils.getInstance().replaceBlank(xmlParse.nextText());
						}
						if (element.equals("MapsImageWidth")) {// 地图图片宽度
							mapItemConfig.MapsImageWidth = Integer
									.parseInt(StringUtils.getInstance().replaceBlank(xmlParse.nextText()));
						}
						if (element.equals("MapsImageHeight")) {// 地图图片高度
							mapItemConfig.MapsImageHeight = Integer
									.parseInt(StringUtils.getInstance().replaceBlank(xmlParse.nextText()));
						}
						if (element.equals("DataImageWidth")) {// 地图图片宽度
							mapItemConfig.DataImageWidth = Integer
									.parseInt(StringUtils.getInstance().replaceBlank(xmlParse.nextText()));
						}
						if (element.equals("DataImageHeight")) {// 地图图片高度
							mapItemConfig.DataImageHeight = Integer
									.parseInt(StringUtils.getInstance().replaceBlank(xmlParse.nextText()));
						}
						if (element.equals("MatrixWidth")) {// 矩阵因子宽度
							mapItemConfig.MatrixWidth = StringUtils.getInstance()
									.parse2LongArray(StringUtils.getInstance().replaceBlank(xmlParse.nextText()), ",");
						}
						if (element.equals("MatrixHeight")) {// 矩阵因子高度
							mapItemConfig.MatrixHeight = StringUtils.getInstance()
									.parse2LongArray(StringUtils.getInstance().replaceBlank(xmlParse.nextText()), ",");
						}
						if (element.equals("ViewAngle")) {// 地图视图角度
							mapItemConfig.ViewAngle = Double
									.parseDouble(StringUtils.getInstance().replaceBlank(xmlParse.nextText()));
						}
						if (element.equals("MapsAngle")) {// 地图角度
							mapItemConfig.MapsAngle = Double
									.parseDouble(StringUtils.getInstance().replaceBlank(xmlParse.nextText()));
						}
						if (element.equals("ConversionCoefficient")) {// 转换因子
							mapItemConfig.ConversionCoefficient = Double
									.parseDouble(StringUtils.getInstance().replaceBlank(xmlParse.nextText()));
						}
					}
					if (element.equals("layers")) {
						if (MapConfigs.layers == null) {
							MapConfigs.layers = new ArrayList<LayerItemConfig>();
						}
					}
					if (MapConfigs.layers != null) {
						if (element.equals("layer")) {
							layer = new LayerItemConfig();
						}
						if (element.equals("LayerId")) {
							layer.LayerId = StringUtils.getInstance().replaceBlank(xmlParse.nextText());
						}
						if (element.equals("LayerName")) {
							layer.LayerName = StringUtils.getInstance().replaceBlank(xmlParse.nextText());
						}
						if (element.equals("LayerType")) {
							layer.LayerType = StringUtils.getInstance().replaceBlank(xmlParse.nextText());
						}
						if (element.equals("ShowType")) {
							layer.ShowType = StringUtils.getInstance().replaceBlank(xmlParse.nextText());
						}
						if (element.equals("Url")) {
							layer.url = StringUtils.getInstance().replaceBlank(xmlParse.nextText());
						}
					}

					break;
				}
				case XmlPullParser.END_TAG: {
					if (xmlParse.getName().equals("map")) {
						MapConfigs.maps.add(mapItemConfig);
					}
					if (xmlParse.getName().equals("layer")) {
						MapConfigs.layers.add(layer);
					}
					break;
				}
				default:
					break;
				}
				evnType = xmlParse.next();
			}
			inputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * 放大地图
	 */
	public void zoomIn() {
		if (MapCache.map.getMapsLevel() < MapCache.map.getMaxLevel()) {
			MapCache.map.smoothZoomLevel(MapCache.map.getMapsLevel() + 1);
		}
		MapCache.map.refresh();
	}

	/**
	 * 放大地图
	 */
	public void zoomIn(HiBaseMaps map) {
		if (map.getMapsLevel() < map.getMaxLevel()) {
			map.smoothZoomLevel(map.getMapsLevel() + 1);
		}
		map.refresh();
	}

	/**
	 * 缩小地图
	 */
	public void zoomOut() {
		if (MapCache.map.getMapsLevel() > 0) {
			MapCache.map.smoothZoomLevel(MapCache.map.getMapsLevel() - 1);
		}
		MapCache.map.refresh();
	}

	/**
	 * 缩小地图
	 */
	public void zoomOut(HiBaseMaps map) {
		if (map.getMapsLevel() > 0) {
			map.smoothZoomLevel(map.getMapsLevel() - 1);
		}
		map.refresh();
	}

	/**
	 * 控制十字丝的显示情况
	 * 
	 * @param isShow
	 *            true表示显示，false表示不显示
	 * @param context
	 */
	public void setCrosshairShow(boolean isShow, Context context) {
		View mapView = View.inflate(context, R.layout.map, null);
		if (isShow) {
			mapView.findViewById(R.id.iv_map_crosshair).setVisibility(View.VISIBLE);
		} else {
			mapView.findViewById(R.id.iv_map_crosshair).setVisibility(View.GONE);
		}
	}

	/**
	 * 获取地图中心点
	 * 
	 * @return
	 */
	public double[] getMapCenter() {
		double cx = MapCache.map.getCx();
		double cy = MapCache.map.getCy();
		return new double[] { cx, cy };
	}

}
