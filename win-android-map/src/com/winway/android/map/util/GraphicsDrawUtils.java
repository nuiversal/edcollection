package com.winway.android.map.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.winway.android.map.entity.GraphicObj;
import com.winway.android.map.entity.MapCache;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Paint.Cap;
import android.provider.MediaStore.Images.Thumbnails;
import ocn.himap.base.HiCoordinate;
import ocn.himap.base.HiGraphicsBufferType;
import ocn.himap.base.HiLineType;
import ocn.himap.base.HiPointType;
import ocn.himap.graphics.HiBaseGraphics;
import ocn.himap.graphics.HiGraphicsStyle;
import ocn.himap.graphics.HiLine;
import ocn.himap.graphics.HiPoint;
import ocn.himap.graphics.HiPolygon;
import ocn.himap.layer.HiBaseLayer;
import ocn.himap.layer.HiGraphicsLayer;
import ocn.himap.layer.HiMarkerLayer;
import ocn.himap.maps.HiBaseMaps;
import ocn.himap.markers.HiBaseMarker;
import ocn.himap.markers.HiMarkerStyle;
import ocn.himap.markers.HiPointMarker;
import ocn.himap.markers.HiTag;
import ocn.himap.markers.HiTextMarker;

/**
 * 图形绘制工具类
 * 
 * @author zgq
 *
 */
public class GraphicsDrawUtils {
	public static GraphicsDrawUtils instance;

	private GraphicsDrawUtils() {

	}

	public static GraphicsDrawUtils getInstance() {
		if (instance == null) {
			synchronized (GraphicsDrawUtils.class) {
				if (instance == null) {
					instance = new GraphicsDrawUtils();

				}
			}
		}
		return instance;
	}
	
	/**
	 * 销毁单例
	 * 
	 * @note 在app销毁的时候执行
	 */
	public static void destroy() {
		instance = null;
	}

	/**
	 * 存点图层
	 */
	private ArrayList<GraphicObj> pointList = new ArrayList<GraphicObj>();

	/**
	 * 存线图层
	 */
	private ArrayList<GraphicObj> lineList = new ArrayList<GraphicObj>();

	/**
	 * 存面数据
	 */
	private ArrayList<GraphicObj> polygonList = new ArrayList<GraphicObj>();

	/**
	 * 当前闪烁线
	 */
	private HiLine currentFlashLine = null;
	/**
	 * 当前闪烁点
	 */
	private HiPointMarker currentFlashPoint = null;

	/**
	 * 获取点列表
	 * 
	 * @return
	 */
	public ArrayList<GraphicObj> getPointList() {
		return this.pointList;
	}

	/**
	 * 获取线列表
	 * 
	 * @return
	 */
	public ArrayList<GraphicObj> getLineList() {
		return this.lineList;
	}

	/**
	 * 关闭其他闪烁
	 */
	public void stopOtherFlash() {
		if (currentFlashLine != null) {
			currentFlashLine.stopFlashing();
		}
		if (currentFlashPoint != null) {
			currentFlashPoint.stopFlashing();
		}
	}

	/**
	 * 闪烁要素
	 * 
	 * @param ele
	 */
	public <T> void flashFeature(T ele) {
		if (ele == null) {
			return;
		}
		Class cls = ele.getClass();
		if (cls.equals(HiLine.class)) {// 线路
			HiLine line = (HiLine) ele;
			line.startFlashing(200);
			currentFlashLine = line;
		} else if (cls.equals(HiPointMarker.class)) {// 点标注
			HiPointMarker marker = (HiPointMarker) ele;
			marker.startFlashing(200);
			currentFlashPoint = marker;
		}
	}

	/**
	 * 绘制点标注（带闪烁效果）
	 * 
	 * @param t
	 *            业务对象
	 * @param objId
	 *            业务对象主键
	 * @param coords
	 *            坐标
	 * @param bitmapStyle1
	 *            样式一(本体样式)
	 * @param bitmapStyle2
	 *            样式二（闪烁样式）
	 * @param name
	 *            名称
	 * @param title
	 *            标题
	 * @return
	 */
	public <T> GraphicObj drawPointMarker(T t, String objId, double[] coords, Bitmap bitmapStyle1, Bitmap bitmapStyle2,
			String name, String title) {
		HiPointMarker marker = new HiPointMarker();
		marker.MarkerStyle.OffsetX = -15;
		marker.MarkerStyle.OffsetY = -15;
		HiMarkerStyle markerStyle1 = new HiMarkerStyle();// 标注样式
		HiMarkerStyle markerStyle2 = new HiMarkerStyle();// 标注样式
		markerStyle2.OffsetX = -15;
		markerStyle2.OffsetY = -15;
		marker.Cx = coords[0];
		marker.Cy = coords[1];
		// 设置样式
		marker.MarkerStyle.Icon = bitmapStyle1;
		markerStyle1 = marker.MarkerStyle;
		markerStyle2.Icon = bitmapStyle2;
		marker.StyleList = new HiMarkerStyle[] { markerStyle1, markerStyle2 };
		marker.nameStr = objId;// 约定为主键，供地图点击获取详情使用
		marker.name = name;
		marker.Title = title;
		GraphicObj graphicObj = makeupGraphicObj(objId, marker, t);
		pointList.add(graphicObj);
		marker.setMaplet(MapCache.map);
		MapCache.map.addMarker(marker);
		return graphicObj;
	}

	/**
	 * 绘制点标注（带闪烁效果）
	 * 
	 * @param t
	 *            业务对象
	 * @param objId
	 *            业务对象主键
	 * @param coords
	 *            坐标
	 * @param bitmapStyle1
	 *            样式一(本体样式)
	 * @param bitmapStyle2
	 *            样式二（闪烁样式）
	 * @param name
	 *            名称
	 * @param title
	 *            标题
	 * 
	 * @param offsetX
	 *            横向偏移值
	 * @param offsetY
	 *            纵向偏移值
	 * @return
	 */
	public <T> GraphicObj drawPointMarker(T t, String objId, double[] coords, Bitmap bitmapStyle1, Bitmap bitmapStyle2,
			String name, String title, int offsetX, int offsetY) {
		HiPointMarker marker = new HiPointMarker();
		marker.MarkerStyle.OffsetX = offsetX;
		marker.MarkerStyle.OffsetY = offsetY;
		HiMarkerStyle markerStyle1 = new HiMarkerStyle();// 标注样式
		HiMarkerStyle markerStyle2 = new HiMarkerStyle();// 标注样式
		markerStyle2.OffsetX = offsetX;
		markerStyle2.OffsetY = offsetY;
		marker.Cx = coords[0];
		marker.Cy = coords[1];
		// 设置样式
		marker.MarkerStyle.Icon = bitmapStyle1;
		markerStyle1 = marker.MarkerStyle;
		markerStyle2.Icon = bitmapStyle2;
		marker.StyleList = new HiMarkerStyle[] { markerStyle1, markerStyle2 };
		marker.nameStr = objId;// 约定为主键，供地图点击获取详情使用
		marker.name = name;
		marker.Title = title;
		GraphicObj graphicObj = makeupGraphicObj(objId, marker, t);
		pointList.add(graphicObj);
		marker.setMaplet(MapCache.map);
		MapCache.map.addMarker(marker);
		return graphicObj;
	}

	/**
	 * 绘制点标注（不带闪烁效果）
	 * 
	 * @param t
	 *            业务对象
	 * @param objId
	 *            业务对象主键
	 * @param coords
	 *            坐标
	 * @param bitmapStyle1
	 *            样式
	 * @param name
	 *            名称
	 * @param title
	 *            标题
	 * @return
	 */
	public <T> GraphicObj drawPointMarker(T t, String objId, double[] coords, Bitmap bitmapStyle1, String name,
			String title) {
		HiPointMarker marker = new HiPointMarker();
		marker.MarkerStyle.OffsetX = -15;
		marker.MarkerStyle.OffsetY = -15;
		marker.Cx = coords[0];
		marker.Cy = coords[1];
		// 设置样式
		marker.MarkerStyle.Icon = bitmapStyle1;
		marker.nameStr = objId;// 约定为主键，供地图点击获取详情使用
		marker.name = name;
		marker.Title = title;
		GraphicObj graphicObj = makeupGraphicObj(objId, marker, t);
		pointList.add(graphicObj);
		marker.setMaplet(MapCache.map);
		MapCache.map.addMarker(marker);
		return graphicObj;
	}

	/**
	 * 绘制点标注（不带闪烁效果）
	 * 
	 * @param t
	 *            业务对象
	 * @param objId
	 *            业务对象主键
	 * @param coords
	 *            坐标
	 * @param bitmapStyle1
	 *            样式
	 * @param name
	 *            名称
	 * @param title
	 *            标题
	 * @param offsetX
	 *            横向偏移值
	 * @param offsetY
	 *            纵向偏移值
	 * @return
	 */
	public <T> GraphicObj drawPointMarker(T t, String objId, double[] coords, Bitmap bitmapStyle1, String name,
			String title, int offsetX, int offsetY) {
		HiPointMarker marker = new HiPointMarker();
		marker.MarkerStyle.OffsetX = offsetX;
		marker.MarkerStyle.OffsetY = offsetY;
		marker.Cx = coords[0];
		marker.Cy = coords[1];
		// 设置样式
		marker.MarkerStyle.Icon = bitmapStyle1;
		marker.nameStr = objId;// 约定为主键，供地图点击获取详情使用
		marker.name = name;
		marker.Title = title;
		GraphicObj graphicObj = makeupGraphicObj(objId, marker, t);
		pointList.add(graphicObj);
		marker.setMaplet(MapCache.map);
		MapCache.map.addMarker(marker);
		return graphicObj;
	}

	/**
	 * 构建点标注对象
	 * 
	 * @param t
	 *            业务对象
	 * @param objId
	 *            业务对象主键
	 * @param coords
	 *            坐标
	 * @param bitmapStyle1
	 *            样式
	 * @param name
	 *            名称
	 * @param title
	 *            标题
	 * @return
	 */
	public <T> GraphicObj makeUpPointMarker(T t, String objId, double[] coords, Bitmap bitmapStyle1, String name,
			String title) {
		HiPointMarker marker = new HiPointMarker();
		marker.MarkerStyle.OffsetX = -15;
		marker.MarkerStyle.OffsetY = -15;
		marker.Cx = coords[0];
		marker.Cy = coords[1];
		// 设置样式
		marker.MarkerStyle.Icon = bitmapStyle1;
		marker.nameStr = objId;// 约定为主键，供地图点击获取详情使用
		marker.name = name;
		marker.Title = title;
		marker.setMaplet(MapCache.map);
		GraphicObj graphicObj = makeupGraphicObj(objId, marker, t);
		return graphicObj;
	}

	/**
	 * 构建点标注对象
	 * 
	 * @param t
	 * @param objId
	 * @param coords
	 * @param bitmapStyle1
	 * @param name
	 * @param title
	 * @return
	 */
	public <T> GraphicObj combinePointMarker(T t, String objId, double[] coords, Bitmap bitmapStyle1, String name,
			String title) {
		HiPointMarker marker = new HiPointMarker();
		marker.MarkerStyle.OffsetX = -15;
		marker.MarkerStyle.OffsetY = -15;
		marker.Cx = coords[0];
		marker.Cy = coords[1];
		// 设置样式
		marker.MarkerStyle.Icon = bitmapStyle1;
		marker.nameStr = objId;// 约定为主键，供地图点击获取详情使用
		marker.name = name;
		marker.Title = title;
		GraphicObj graphicObj = new GraphicObj();
		graphicObj.setObjId(objId);
		graphicObj.setGraphicObj(marker);
		graphicObj.setBusinessObj(t);
		return graphicObj;

	}

	/**
	 * 移除点(更改为有返回值，供图形图像过滤用)
	 */
	public Object removePoint(String id) {
		// TODO Auto-generated method stub
		if (pointList.size() > 0) {
			for (int i = 0; i < pointList.size(); i++) {
				GraphicObj graphicObj = pointList.get(i);
				HiPointMarker marker = null;
				HiTextMarker textMarker = null;
				HiPoint point = null;
				if (graphicObj.getObjId().equals(id)) {
					Object graphic = graphicObj.getGraphicObj();
					if (graphic instanceof HiPointMarker) {
						marker = (HiPointMarker) graphic;
						MapCache.map.removeMarker(marker);
						marker.dispose();
					} else if (graphic instanceof HiTextMarker) {
						textMarker = (HiTextMarker) graphic;
						MapCache.map.removeMarker(textMarker);
						textMarker.dispose();
					} else if (graphic instanceof HiPoint) {
						point = (HiPoint) graphic;
						MapCache.map.removeGraphicsMarker(point);
						point.dispose();
					}
					pointList.remove(i);
					return graphic;
				}
			}
		}
		return null;
	}

	/**
	 * 根据id获取点图形对象
	 * 
	 * @param id
	 * @return
	 */
	public GraphicObj getPointGraphicObj(String id) {
		if (pointList.size() > 0) {
			for (int i = 0; i < pointList.size(); i++) {
				GraphicObj graphicObj = pointList.get(i);
				if (graphicObj.getObjId().equals(id)) {
					return graphicObj;
				}
			}
		}
		return null;
	}

	/**
	 * 根据id获取线图形对象
	 * 
	 * @param id
	 * @return
	 */
	public GraphicObj getLineGraphicObj(String id) {
		if (lineList.size() > 0) {
			for (int i = 0; i < lineList.size(); i++) {
				GraphicObj graphicObj = lineList.get(i);
				if (graphicObj.getObjId().equals(id)) {
					return graphicObj;
				}
			}
		}
		return null;
	}

	/**
	 * 画线（带闪烁效果）
	 * 
	 * @param t
	 *            业务实体类
	 * @param objId
	 *            业务主键
	 * @param coordArr
	 *            坐标
	 * @param colorString1
	 *            颜色字符串(如#ff00aa)本体样式
	 * @param colorString2
	 *            颜色字符串(如#ff00aa)闪烁样式
	 * @param name
	 * @return
	 */
	public <T> GraphicObj drawLine(T t, String objId, ArrayList<HiCoordinate> coordArr, String colorString1,
			String colorString2, String name) {
		HiLine line = makeUpLine(objId, coordArr, name, colorString1);
		HiGraphicsStyle style1 = new HiGraphicsStyle();// 线条原本样式
		style1 = line.GraphicsStyle;
		// 闪烁样式
		HiGraphicsStyle style2 = new HiGraphicsStyle();
		style2.LWeight = 8;
		style2.PointType = HiPointType.CirclePoint;
		style2.Offset = 10;
		style2.LColor = Color.parseColor(colorString2);
		style2.BufferType = HiGraphicsBufferType.None;
		line.StyleList = new HiGraphicsStyle[] { style1, style2 };

		GraphicObj graphicObj = makeupGraphicObj(objId, line, t);
		lineList.add(graphicObj);
		line.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(line);
		return graphicObj;

	}

	/**
	 * 画线(不带闪烁效果)
	 * 
	 * @param t
	 *            业务实体类
	 * @param objId
	 *            业务主键
	 * @param coordArr
	 *            坐标
	 * @param colorString
	 *            颜色字符串(如#ff00aa)
	 * @param name
	 * @return
	 */
	public <T> GraphicObj drawLine(T t, String objId, ArrayList<HiCoordinate> coordArr, String colorString,
			String name) {
		HiLine line = makeUpLine(objId, coordArr, name, colorString);
		GraphicObj graphicObj = makeupGraphicObj(objId, line, t);
		lineList.add(graphicObj);
		line.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(line);
		return graphicObj;
	}

	/** 构建线路 */
	private HiLine makeUpLine(String nameStr, ArrayList<HiCoordinate> coordArr, String name, String colorString) {
		HiLine line = new HiLine();
		line.name = name;
		line.nameStr = nameStr;// 约定为主键，供地图点击获取详情使用
		// 设置坐标
		line.Coordinates = coordArr;
		line.GraphicsStyle.LWeight = 8;
		line.GraphicsStyle.PointType = HiPointType.CirclePoint;
		line.GraphicsStyle.Offset = 10;
		line.GraphicsStyle.LColor = Color.parseColor(colorString);
		line.GraphicsStyle.FillVisible = false;
		line.GraphicsStyle.BufferType = HiGraphicsBufferType.None;
		return line;
	}

	/** 构建图形对象 */
	private GraphicObj makeupGraphicObj(String objId, Object graphicObj, Object businessObj) {
		GraphicObj pgraphicObj = new GraphicObj();
		pgraphicObj.setObjId(objId);
		pgraphicObj.setGraphicObj(graphicObj);
		pgraphicObj.setBusinessObj(businessObj);
		return pgraphicObj;
	}

	/**
	 * 绘制通道顶管
	 * 
	 * @param t
	 *            业务实体类
	 * @param objId
	 *            业务主键
	 * @param coordArr
	 *            坐标
	 * @param colorString1
	 *            颜色字符串(如#ff00aa)本体样式
	 * @param colorString2
	 *            颜色字符串(如#ff00aa)闪烁样式
	 * @param name
	 * @return
	 */
	public <T> GraphicObj drawChannelDg(T t, String objId, ArrayList<HiCoordinate> coordArr, String colorString1,
			String colorString2, String name) {
		HiLine line = makeUpLine(objId, coordArr, name, colorString1);

		HiGraphicsStyle style1 = new HiGraphicsStyle();// 线条原本样式
		line.GraphicsStyle.LineType = HiLineType.DotSuperposition;
		line.GraphicsStyle.DotDistance = 40;
		line.GraphicsStyle.SuperpositionDotColor = Color.parseColor("#d26111");
		line.GraphicsStyle.LColor = Color.parseColor("#d26111");
		line.GraphicsStyle.SuperpositionDotWeight = 20;
		line.GraphicsStyle.PointType = HiPointType.RectPoint;
		style1 = line.GraphicsStyle;

		HiGraphicsStyle style2 = new HiGraphicsStyle();
		style2.LineType = HiLineType.DotSuperposition;
		style2.LWeight = 8;
		style2.DotDistance = 40;
		style2.SuperpositionDotColor = Color.parseColor("#00b050");
		style2.SuperpositionDotWeight = 20;
		style2.PointType = HiPointType.RectPoint;
		style2.LColor = Color.parseColor("#00b050");
		line.StyleList = new HiGraphicsStyle[] { style1, style2 };

		GraphicObj graphicObj = makeupGraphicObj(objId, line, t);
		lineList.add(graphicObj);
		line.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(line);
		return graphicObj;
	}

	/**
	 * 绘制通道-电缆沟
	 * 
	 * @param t
	 *            业务实体类
	 * @param objId
	 *            业务主键
	 * @param coordArr
	 *            坐标
	 * @param colorString1
	 *            颜色字符串(如#ff00aa)本体样式
	 * @param colorString2
	 *            颜色字符串(如#ff00aa)闪烁样式
	 * @param name
	 * @return
	 */
	public <T> GraphicObj drawChannelDlg(T t, String objId, ArrayList<HiCoordinate> coordArr, String colorString1,
			String colorString2, String name) {
		HiLine line = makeUpLine(objId, coordArr, name, colorString1);

		HiGraphicsStyle style1 = new HiGraphicsStyle();// 线条原本样式
		line.GraphicsStyle.LineType = HiLineType.Dash;
		line.GraphicsStyle.LineCap = Cap.ROUND;
		line.GraphicsStyle.LColor = Color.parseColor("#ec625c");
		line.GraphicsStyle.LineIntervals = new float[] { 20, 20 };
		style1 = line.GraphicsStyle;

		HiGraphicsStyle style2 = new HiGraphicsStyle();
		style2.LineType = HiLineType.Dash;
		style2.LineCap = Cap.ROUND;
		style2.LineIntervals = new float[] { 20, 20 };
		style2.LColor = Color.parseColor("#00FFFF");
		line.StyleList = new HiGraphicsStyle[] { style1, style2 };

		GraphicObj graphicObj = makeupGraphicObj(objId, line, t);
		lineList.add(graphicObj);
		line.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(line);
		return graphicObj;
	}

	/**
	 * 绘制通道-电缆直埋
	 * 
	 * @param t
	 *            业务实体类
	 * @param objId
	 *            业务主键
	 * @param coordArr
	 *            坐标
	 * @param colorString1
	 *            颜色字符串(如#ff00aa)本体样式
	 * @param colorString2
	 *            颜色字符串(如#ff00aa)闪烁样式
	 * @param name
	 * @return
	 */
	public <T> GraphicObj drawChannelDlzm(T t, String objId, ArrayList<HiCoordinate> coordArr, String colorString1,
			String colorString2, String name) {
		HiLine line = makeUpLine(objId, coordArr, name, colorString1);

		HiGraphicsStyle style1 = new HiGraphicsStyle();// 线条原本样式
		line.GraphicsStyle.LineType = HiLineType.Dash;
		line.GraphicsStyle.LineCap = Cap.ROUND;
		line.GraphicsStyle.LColor = Color.parseColor("#6561fa");
		line.GraphicsStyle.LineIntervals = new float[] { 40, 40 };
		line.GraphicsStyle.LWeight =12;
		style1 = line.GraphicsStyle;

		HiGraphicsStyle style2 = new HiGraphicsStyle();
		style2.LineType = HiLineType.Dash;
		style2.LineCap = Cap.ROUND;
		style2.LineIntervals = new float[] { 40, 40 };
		style2.LColor = Color.parseColor("#00FFFF");
		style2.LWeight=12;
		line.StyleList = new HiGraphicsStyle[] { style1, style2 };

		GraphicObj graphicObj = makeupGraphicObj(objId, line, t);
		lineList.add(graphicObj);
		line.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(line);
		return graphicObj;
	}

	/**
	 * 绘制通道-电缆管道
	 * 
	 * @param t
	 *            业务实体类
	 * @param objId
	 *            业务主键
	 * @param coordArr
	 *            坐标
	 * @param colorString1
	 *            颜色字符串(如#ff00aa)本体样式
	 * @param colorString2
	 *            颜色字符串(如#ff00aa)闪烁样式
	 * @param name
	 * @return
	 */
	public <T> GraphicObj drawChannelDlgd(T t, String objId, ArrayList<HiCoordinate> coordArr, String colorString1,
			String colorString2, String name) {
		HiLine line = makeUpLine(objId, coordArr, name, colorString1);
		line.GraphicsStyle.LWeight = 10;
		line.GraphicsStyle.LColor = Color.parseColor("#00b1f1");
		;
		HiGraphicsStyle style1 = new HiGraphicsStyle();// 线条原本样式
		style1 = line.GraphicsStyle;
		// 闪烁样式
		HiGraphicsStyle style2 = new HiGraphicsStyle();
		style2.LWeight = 10;
		style2.PointType = HiPointType.CirclePoint;
		style2.Offset = 10;
		style2.LColor = Color.parseColor(colorString2);
		style2.BufferType = HiGraphicsBufferType.None;
		line.StyleList = new HiGraphicsStyle[] { style1, style2 };

		GraphicObj graphicObj = makeupGraphicObj(objId, line, t);
		lineList.add(graphicObj);
		line.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(line);
		return graphicObj;
	}

	/**
	 * 绘制通道-电缆隧道
	 * 
	 * @param t
	 *            业务实体类
	 * @param objId
	 *            业务主键
	 * @param coordArr
	 *            坐标
	 * @param colorString1
	 *            颜色字符串(如#ff00aa)本体样式
	 * @param colorString2
	 *            颜色字符串(如#ff00aa)闪烁样式
	 * @param name
	 * @return
	 */
	public <T> GraphicObj drawChannelDlsd(T t, String objId, ArrayList<HiCoordinate> coordArr, String colorString1,
			String colorString2, String name) {
		HiLine line = makeUpLine(objId, coordArr, name, colorString1);

		HiGraphicsStyle style1 = new HiGraphicsStyle();// 线条原本样式
		line.GraphicsStyle.LineType = HiLineType.DotSuperposition;
		line.GraphicsStyle.DotDistance = 40;
		line.GraphicsStyle.SuperpositionDotColor = Color.parseColor("#da4294");
		line.GraphicsStyle.LColor = Color.parseColor("#da4294");
		line.GraphicsStyle.SuperpositionDotWeight = 20;
		line.GraphicsStyle.PointType = HiPointType.RectPoint;
		style1 = line.GraphicsStyle;

		HiGraphicsStyle style2 = new HiGraphicsStyle();
		style2.LineType = HiLineType.DotSuperposition;
		style2.DotDistance = 40;
		style2.SuperpositionDotColor = Color.parseColor("#00b050");
		style2.SuperpositionDotWeight = 20;
		style2.PointType = HiPointType.RectPoint;
		style2.LColor = Color.parseColor("#00FFFF");
		line.StyleList = new HiGraphicsStyle[] { style1, style2 };

		GraphicObj graphicObj = makeupGraphicObj(objId, line, t);
		lineList.add(graphicObj);
		line.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(line);
		return graphicObj;
	}

	/**
	 * 绘制通道-电缆桥
	 * 
	 * @param t
	 *            业务实体类
	 * @param objId
	 *            业务主键
	 * @param coordArr
	 *            坐标
	 * @param colorString1
	 *            颜色字符串(如#ff00aa)本体样式
	 * @param colorString2
	 *            颜色字符串(如#ff00aa)闪烁样式
	 * @param name
	 * @return
	 */
	public <T> GraphicObj drawChannelDlq(T t, String objId, ArrayList<HiCoordinate> coordArr, String colorString1,
			String colorString2, String name) {
		HiLine line = makeUpLine(objId, coordArr, name, colorString1);

		HiGraphicsStyle style1 = new HiGraphicsStyle();// 线条原本样式
		line.GraphicsStyle.LineType = HiLineType.DotSuperposition;
		line.GraphicsStyle.DotDistance = 40;
		line.GraphicsStyle.SuperpositionDotColor = Color.parseColor("#97c840");
		line.GraphicsStyle.LColor = Color.parseColor("#97c840");
		line.GraphicsStyle.SuperpositionDotWeight = 20;
		line.GraphicsStyle.PointType = HiPointType.CirclePoint;
		style1 = line.GraphicsStyle;

		HiGraphicsStyle style2 = new HiGraphicsStyle();
		style2.LineType = HiLineType.DotSuperposition;
		style2.DotDistance = 40;
		style2.SuperpositionDotColor = Color.parseColor("#00b050");
		style2.SuperpositionDotWeight = 20;
		style2.PointType = HiPointType.CirclePoint;
		style2.LColor = Color.parseColor("#00FFFF");
		line.StyleList = new HiGraphicsStyle[] { style1, style2 };

		GraphicObj graphicObj = makeupGraphicObj(objId, line, t);
		lineList.add(graphicObj);
		line.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(line);
		return graphicObj;
	}

	/**
	 * 绘制通道-架空线路
	 * 
	 * @param t
	 *            业务实体类
	 * @param objId
	 *            业务主键
	 * @param coordArr
	 *            坐标
	 * @param colorString1
	 *            颜色字符串(如#ff00aa)本体样式
	 * @param colorString2
	 *            颜色字符串(如#ff00aa)闪烁样式
	 * @param name
	 * @return
	 */
	public <T> GraphicObj drawChannelJk(T t, String objId, ArrayList<HiCoordinate> coordArr, String colorString1,
			String colorString2, String name) {
		HiLine line = makeUpLine(objId, coordArr, name, colorString1);
		line.GraphicsStyle.LineType = HiLineType.Dash;
		line.GraphicsStyle.LineCap = Cap.SQUARE;
		line.GraphicsStyle.LineIntervals = new float[] { 5, 20, 20, 20 };
		line.GraphicsStyle.LColor = Color.parseColor("#4ec47d");

		HiGraphicsStyle style1 = new HiGraphicsStyle();// 线条原本样式
		style1 = line.GraphicsStyle;

		HiGraphicsStyle style2 = new HiGraphicsStyle();
		style2.LineType = HiLineType.Dash;
		style2.LineCap = Cap.SQUARE;
		style2.LineIntervals = new float[] { 5, 20, 20, 20 };
		style2.LColor = Color.parseColor("#00FFFF");
		line.StyleList = new HiGraphicsStyle[] { style1, style2 };

		GraphicObj graphicObj = makeupGraphicObj(objId, line, t);
		lineList.add(graphicObj);
		line.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(line);
		return graphicObj;
	}

	/**
	 * 移除点
	 */
	public void removeLine(String id) {
		// TODO Auto-generated method stub
		if (lineList.size() > 0) {
			for (int i = 0; i < lineList.size(); i++) {
				GraphicObj graphicObj = lineList.get(i);
				HiLine line = null;
				if (graphicObj.getObjId().equals(id)) {
					line = (HiLine) graphicObj.getGraphicObj();
					MapCache.map.removeGraphicsMarker(line);
					line.dispose();
					lineList.remove(i);
					break;
				}
			}
		}
	}

	/**
	 * 绘制文本标注
	 * 
	 * @param t
	 *            业务对象
	 * @param objId
	 *            主键
	 * @param coords
	 *            坐标
	 * @param name
	 *            显示的文字标注
	 * @param colorString
	 *            颜色字符串如（#ff0000）
	 * @return
	 */
	public <T> GraphicObj drawTextMarker(T t, String objId, double[] coords, String name, String colorString) {
		HiTextMarker hiTextMarker = new HiTextMarker(name);
		hiTextMarker.MarkerStyle.OffsetX = -15;
		hiTextMarker.MarkerStyle.OffsetY = -15;
		hiTextMarker.MarkerStyle.FontSize = 30;
		hiTextMarker.MarkerStyle.color = Color.parseColor(colorString);
		hiTextMarker.Cx = coords[0];
		hiTextMarker.Cy = coords[1];
		// 设置样式
		hiTextMarker.nameStr = objId;// 约定为主键，供地图点击获取详情使用
		hiTextMarker.name = name;
		hiTextMarker.Text = name;
		GraphicObj graphicObj = makeupGraphicObj(objId, hiTextMarker, t);
		pointList.add(graphicObj);
		hiTextMarker.setMaplet(MapCache.map);// 绑定地图对象
		MapCache.map.addMarker(hiTextMarker);// 添加到地图对象
		return graphicObj;
	}

	/**
	 * 添加标注图层
	 * 
	 * @param markerList
	 */
	public HiMarkerLayer addMarkerLayer(ArrayList<HiBaseMarker> markerList) {
		HiMarkerLayer hiMarkerLayer = new HiMarkerLayer();
		hiMarkerLayer.markers = markerList;
		hiMarkerLayer.pMaplet=MapCache.map;
		MapCache.map.addLayer(hiMarkerLayer);
		hiMarkerLayer.show();
		return hiMarkerLayer;
	}

	/**
	 * 添加图形图层
	 * 
	 * @param graphicsList
	 */
	public HiGraphicsLayer addGraphicsLayer(ArrayList<HiBaseGraphics> graphicsList) {
		HiGraphicsLayer hiGraphicsLayer = new HiGraphicsLayer();
		hiGraphicsLayer.gs = graphicsList;
		MapCache.map.addLayer(hiGraphicsLayer);
		return hiGraphicsLayer;
	}

	/**
	 * 绘制点缓冲
	 * 
	 * @param xy
	 * @param bufferRadius
	 * @param objId
	 * @return
	 */
	public GraphicObj drawPointBuffer(double[] xy, int bufferRadius, String objId) {
		HiPoint point = new HiPoint();
		ArrayList<HiCoordinate> crdArr = new ArrayList<HiCoordinate>();
		crdArr.add(new HiCoordinate(xy[0], xy[1]));
		point.Coordinates = crdArr;
		point.GraphicsStyle.BufferRadius = bufferRadius;
		point.GraphicsStyle.BufferType = HiGraphicsBufferType.AllBuffer;
		point.GraphicsStyle.BorderVisible = true;
		point.GraphicsStyle.BufferColor = 0x90879EC8;
		point.GraphicsStyle.FillVisible = true;
		point.GraphicsStyle.Offset = -80;

		GraphicObj graphicObj = makeupGraphicObj(objId, point, null);
		pointList.add(graphicObj);
		point.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(point);
		return graphicObj;

	}

	/**
	 * 绘制线缓冲
	 * 
	 * @param bufferRadius
	 * @param objId
	 * @param colorString
	 * @param coordArr
	 * @return
	 */
	public GraphicObj drawLineBuffer(int bufferRadius, String objId, String colorString,
			ArrayList<HiCoordinate> coordArr) {
		HiLine line = new HiLine();
		line.name = "";
		line.nameStr = objId;// 约定为主键，供地图点击获取详情使用
		// 设置坐标
		line.Coordinates = coordArr;
		line.GraphicsStyle.LWeight = 8;
		line.GraphicsStyle.PointType = HiPointType.CirclePoint;
		line.GraphicsStyle.Offset = 10;
		line.GraphicsStyle.LColor = Color.parseColor(colorString);
		line.GraphicsStyle.FillVisible = false;
		line.GraphicsStyle.BufferType = HiGraphicsBufferType.AllBuffer;
		line.GraphicsStyle.BufferColor = 0x90879EC8;
		line.GraphicsStyle.BufferRadius = bufferRadius;
		GraphicObj graphicObj = makeupGraphicObj(objId, line, null);
		lineList.add(graphicObj);
		line.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(line);
		return graphicObj;
	}

	/**
	 * 绘制线缓冲
	 * 
	 * @param bufferRadius
	 * @param line
	 */
	public void drawLineBuffer(int bufferRadius, HiLine line) {
		line.GraphicsStyle.BufferType = HiGraphicsBufferType.AllBuffer;
		line.GraphicsStyle.BufferColor = 0x90879EC8;
		line.GraphicsStyle.BufferRadius = bufferRadius;
	}

	public <T> GraphicObj drawPolygon(T t, String objId, ArrayList<HiCoordinate> coordArr, String colorString,
			String name) {
		HiPolygon polygon = new HiPolygon(coordArr);
		polygon.GraphicsStyle.FillColor = Color.RED;
		GraphicObj graphicObj = new GraphicObj();
		graphicObj.setObjId(objId);
		graphicObj.setGraphicObj(polygon);
		graphicObj.setBusinessObj(null);
		polygonList.add(graphicObj);
		polygon.setMaplet(MapCache.map);
		MapCache.map.addGraphicsMarker(polygon);
		return graphicObj;
	}

	/**
	 * 移除多边形
	 */
	public void removePolygon(String id) {
		// TODO Auto-generated method stub
		if (polygonList.size() > 0) {
			for (int i = 0; i < polygonList.size(); i++) {
				GraphicObj graphicObj = polygonList.get(i);
				HiPolygon polygon = null;
				if (graphicObj.getObjId().equals(id)) {
					polygon = (HiPolygon) graphicObj.getGraphicObj();
					MapCache.map.removeGraphicsMarker(polygon);
					polygon.dispose();
					polygonList.remove(i);
					break;
				}
			}
		}
	}

}
