package com.winway.android.map.util;

import java.util.ArrayList;

import ocn.himap.base.HiCoordinate;
import ocn.himap.events.HiEvent;
import ocn.himap.events.HiIEventListener;
import ocn.himap.events.HiToolEvent;
import ocn.himap.graphics.HiPolygon;
import ocn.himap.manager.HiToolManager;
import ocn.himap.tools.HiAreaTool;
import ocn.himap.tools.HiDistaceTool;
import ocn.himap.tools.HiSketchPolygonTool;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.winway.android.map.R;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.entity.MapToolState;

/**
 * 地图工具类--最新
 * 
 * @author lyh
 * @version 创建时间：2018年1月25日
 *
 */
public class MapToolUtil {

	private static MapToolUtil instance;
	/** 测量工具 */
	private HiDistaceTool distaceTool;
	/** 面积量算工具 */
	private HiAreaTool areaTool;
	/** 多边形工具 */
	private HiSketchPolygonTool hiSketchPolygonTool;
	private View view;
	/** 测量结果 */
	private TextView tvMeasureRes;
	/** 撤消上一操作 */
	private ImageView ivReturn;
	/** 清除标记 */
	private ImageView ivClean;
	/** 关闭窗口 */
	private ImageView ivClose;
	private RelativeLayout rlHint;
	private TextView tvHint;
	private MapToolCallback mapToolCallback = null;
	private int openType = 0; // 

	private MapToolUtil(Context context) {
		addMapTools(context);
		initMapTool(context);
		initEvent();
	}
	
	/**
	 * 单例
	 * @return
	 */
	public static MapToolUtil getInstance(Context context) {
		if (instance == null) {
			synchronized (MapToolUtil.class) {
				if (instance == null) {
					instance = new MapToolUtil(context);
				}
			}
		}
		return instance;
	}

	/**
	 * 初始地图工具
	 */
	private void initMapTool(Context context) {
		view = View.inflate(context, R.layout.map_tool, null);
		tvMeasureRes = (TextView)view.findViewById(R.id.tv_map_tools_measure_result);
		ivReturn = (ImageView) view.findViewById(R.id.iv_map_tools_return);
		ivClean = (ImageView) view.findViewById(R.id.iv_map_tools_del);
		ivClose = (ImageView) view.findViewById(R.id.iv_map_tools_close);
		rlHint = (RelativeLayout) view.findViewById(R.id.rl_hint);
		tvHint = (TextView) view.findViewById(R.id.tv_hint);
	}
	
	/**
	 * 初始化事件
	 */
	private void initEvent() {
		ivReturn.setOnClickListener(ocl);
		ivClean.setOnClickListener(ocl);
		ivClose.setOnClickListener(ocl);
	}
	
	private OnClickListener ocl = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.iv_map_tools_return) { //撤消上一操作 
			}else if (id == R.id.iv_map_tools_del) {  //清除标记 
				cleanMark();
				tvMeasureRes.setText("");
			}else if (id == R.id.iv_map_tools_close) { //关闭窗口
				clearMapTools();
				tvMeasureRes.setText("");
				view.setVisibility(View.GONE);
			}
		}
	};
	
	/**
	 * 消除标记
	 */
	private void cleanMark() {
		clearMapTools();
		if (openType ==1) {
			if (!distaceTool.isToolOpened) {
				distaceTool.openTool();
			}
		}else if(openType ==2){
			if (!areaTool.isToolOpened) {
				areaTool.openTool();
			}
		}else if (openType ==3) {
			if (!hiSketchPolygonTool.isToolOpened) {
				hiSketchPolygonTool.openTool();
			}
		}	
		MapCache.map.refresh();
	}

	/**
	 * 打开面积工具
	 */
	public void openAreaTool(LinearLayout container) {
		openType = 2;
		tvHint.setText("单击屏幕添加点(至少3个)");
		if (container != null) {
			container.removeAllViews();
		}
		view.setVisibility(View.VISIBLE);
		tvMeasureRes.setVisibility(View.VISIBLE);
		if (areaTool != null) {
			if (!areaTool.isToolOpened) {
				areaTool.openTool();
			}
		}
		container.addView(view);
	}
	
	/**
	 * 关闭面积工具
	 */
	private void closeAreaTool() {
		if (areaTool != null) {
			if (areaTool.isToolOpened) {
				areaTool.closeTool();
			}
			areaTool.clearMarkers();
		}
	}
	
	/**
	 * 打开距离工具
	 */
	public void openDistaceTool(LinearLayout container) {
		openType = 1;
		tvHint.setText("单击屏幕添加测距点");
		if (container != null) {
			container.removeAllViews();
		}
		view.setVisibility(View.VISIBLE);
		tvMeasureRes.setVisibility(View.VISIBLE);
		if (distaceTool != null) {
			if (!distaceTool.isToolOpened) {
				distaceTool.openTool();
			}
		}
		container.addView(view);
	}
	
	/**
	 * 关闭距离工具
	 */
	private void closeDistaceTool() {
		if (distaceTool != null) {
			if (distaceTool.isToolOpened) {
				distaceTool.closeTool();
			}
			distaceTool.clearMarkers();
		}
	}

	/**
	 * 打开画多边形工具
	 */
	public void openSketchPolygonTool(LinearLayout container) {
		openType = 3;
		tvHint.setText("在地图上画任意多边形");
		if (container != null) {
			container.removeAllViews();
		}
		view.setVisibility(View.VISIBLE);
		tvMeasureRes.setVisibility(View.VISIBLE);
		if (hiSketchPolygonTool != null) {
			if (!hiSketchPolygonTool.isToolOpened) {
				hiSketchPolygonTool.openTool();
			}
		}
		container.addView(view);
	}

	/**
	 * 关闭打开画多边形工具
	 */
	public void closeSketchPolygonTool() {
		if (hiSketchPolygonTool != null) {
			if (hiSketchPolygonTool.isToolOpened) {
				hiSketchPolygonTool.closeTool();
			}
			hiSketchPolygonTool.clearMarkers();
			if (mapToolCallback != null) {
				mapToolCallback.clear();
			}
		}

	}
	
	/**
	 * 显示测量结果
	 * 
	 * @param value
	 */
	private void showMeasureResult(Context context, String resultStr, Integer value) {
		// 获取显示结果的view
		String result = formatMeasureResult((int) Math.floor(Double.parseDouble(resultStr)), value);
		tvMeasureRes.setText(result);
	}
	
	/**
	 * 添加地图工具
	 */
	public void addMapTools(final Context context) {
		HiToolManager toolManager = new HiToolManager();
		toolManager.setMaplet(MapCache.map);
		// 测距
		distaceTool = new HiDistaceTool();
		distaceTool.addEventListener(HiToolEvent.GraphicsChanged, new HiIEventListener() {
			@Override
			public void dispose() {
			}
			@Override
			public void Run(HiEvent e) {
				// TODO Auto-generated method stub
				rlHint.setVisibility(View.GONE);
				HiToolEvent event = (HiToolEvent) e;
				showMeasureResult(context, event.ResultValue, MapToolState.MEASURE_DISTANCE.getValue());
			}
		});
		// 测面
		areaTool = new HiAreaTool();
		areaTool.addEventListener(HiToolEvent.GraphicsChanged, new HiIEventListener() {
			@Override
			public void dispose() {
			}
			@Override
			public void Run(HiEvent e) {
				// TODO Auto-generated method stub
				rlHint.setVisibility(View.GONE);
				HiToolEvent event = (HiToolEvent) e;
				showMeasureResult(context, event.ResultValue, MapToolState.MEASURE_AREA.getValue());
			}
		});
		// 画多边形
		hiSketchPolygonTool = new HiSketchPolygonTool();
		hiSketchPolygonTool.addEventListener(HiToolEvent.CloseTool, new HiIEventListener() {
			@Override
			public void dispose() {
			}
			@Override
			public void Run(HiEvent e) {
				rlHint.setVisibility(View.GONE);
				HiToolEvent evt = (HiToolEvent) e;
				HiPolygon hiPolygon = (HiPolygon) evt.ResultGraphics;
				ArrayList<HiCoordinate> coordinates = hiPolygon.Coordinates;
				if (mapToolCallback != null && coordinates != null && coordinates.size() > 0) {// 执行回调
					mapToolCallback.polygonCallback(coordinates);
				}
			}
		});
		toolManager.addTool(distaceTool);
		toolManager.addTool(areaTool);
		toolManager.addTool(hiSketchPolygonTool);
	}
	
	/**
	 * 清除地图工具痕迹
	 */
	public void clearMapTools() {
		// 清除工具
		closeDistaceTool();
		closeAreaTool();
		closeSketchPolygonTool();
		MapCache.map.refresh();
	}
	
	/**
	 * 格式化数值
	 * 
	 * @param num
	 * @param flag
	 * @return
	 */
	private String formatMeasureResult(int num, Integer flag) {
		String result = "";
		if (flag.equals(MapToolState.MEASURE_DISTANCE.getValue())) {
			if (num > 1000) {
				result = "距离：" + String.format("%.2f", (double) num / (double) 1000) + "公里";
			} else {
				result = "距离：" + Integer.toString(num) + "米";
			}
		} else if (flag.equals(MapToolState.MEASURE_AREA.getValue())) {
			if (num < 1000000 && num > 10000) {
				result = "面积：" + String.format("%.2f", (double) num / (double) 1000000) + "平方公里";
			} else if (num > 1000000) {
				result = "面积：" + String.format("%.2f", (double) num / (double) 1000000) + "平方公里";
			} else {
				result = "面积：" + Integer.toString(num) + "平方米";
			}
		}
		return result;
	}
	
	/**
	 * 地图工具回调的引用
	 * 
	 * @param mapToolCallback
	 */
	public void setMapToolCallback(MapToolCallback mapToolCallback) {
		this.mapToolCallback = mapToolCallback;
	}

	/**
	 * 地图工具回调
	 * 
	 * @author zgq
	 *
	 */
	public interface MapToolCallback {
		void polygonCallback(ArrayList<HiCoordinate> hiCoordinates);

		void clear();
	}

	/**
	 * 销毁单例
	 */
	public static void destroy() {
		instance = null;
	}
}
