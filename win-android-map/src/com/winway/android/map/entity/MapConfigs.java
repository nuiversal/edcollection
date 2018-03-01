package com.winway.android.map.entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 地图配置
 * 
 * @author zgq
 *
 */
public class MapConfigs {
	/**
	 * host
	 */
	public static String host;
	/**
	 * 端口
	 */
	public static String port;
	/**
	 * 地图缓存文件夹
	 */
	public static String mapscacheroot;
	/**
	 * 上传轨迹点心跳频率
	 */
	public static Long heartbeatinterval;
	/**
	 * 初始化地图中心经度
	 */
	public static double centerX;
	/**
	 * 初始化地图中心纬度
	 */
	public static double centerY;
	/**
	 * 初始化地图中心等级
	 */
	public static int level;
	/**
	 * 初始化地图中心等级差
	 */
	public static int level_difference;
	/**
	 * GPS误差精度
	 */
	public static float GPSPRECISION;
	/**
	 * 关键字URL标志
	 */
	public static String url_keywordsearch;
	/**
	 * 公交换乘URL标志
	 */
	public static String url_traffictransfer;
	/**
	 * 是否有上传轨迹功能
	 */
	public static boolean function_uploadpath;
	/**
	 * 是否有测距测面功能
	 */
	public static boolean function_measuretools;
	/**
	 * 是否有图层控制功能
	 */
	public static boolean function_layercontrol;
	/**
	 * service列表
	 */
	public static ArrayList<HashMap<String, Object>> services;
	/**
	 * 地图配置
	 */
	public static ArrayList<MapItemConfig> maps;
	/**
	 * 图层配置
	 */
	public static ArrayList<LayerItemConfig> layers;

	public static int screenDpi;
}
