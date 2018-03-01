package com.winway.android.map.util;

import java.util.ArrayList;

import com.winway.android.map.R;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.entity.MapToolState;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;
import ocn.himap.base.HiCoordinate;
import ocn.himap.base.HiMapsType;
import ocn.himap.events.HiEvent;
import ocn.himap.events.HiIEventListener;
import ocn.himap.events.HiToolEvent;
import ocn.himap.graphics.HiPolygon;
import ocn.himap.manager.HiToolManager;
import ocn.himap.tools.HiAreaTool;
import ocn.himap.tools.HiDistaceTool;
import ocn.himap.tools.HiSketchPolygonTool;

/**
 * 地图工具类
 * 
 * @author zgq
 *
 */
public class MapToolUtils {
	private Context context;
	private static MapToolUtils instance;

	/* 地图量算---开始 */
	private boolean isOpenMapMeaure = false;// 是否打开地图量算
	private PopupWindow popupWindowMapMeasure = null;// 地图量算
	private View mapMeasureView = null;// 地图量算view
	private TextView tvMeasureRes = null;// 量算结果显示的控件

	private ImageButton mapToolEnter;// 地图工具入口按钮
	/* 地图量算---结束 */
	/* 地图工具---开始 */
	private HiDistaceTool distaceTool;
	private HiAreaTool areaTool;
	private HiSketchPolygonTool hiSketchPolygonTool;
	/* 地图工具---结束 */

	private MapToolUtils() {

	}

	public static MapToolUtils getInstance() {
		if (instance == null) {
			synchronized (MapToolUtils.class) {
				if (instance == null) {
					instance = new MapToolUtils();
				}
			}
		}

		return instance;
	}

	/**
	 * 地图工具
	 * 
	 * @param context
	 * @param mapMeasure
	 * @param tvMeasureResult
	 */
	public void mapTool(Context context, final ImageButton mapMeasure, TextView tvMeasureResult) {
		this.mapToolEnter = mapMeasure;
		if (isOpenMapMeaure) {
			isOpenMapMeaure = false;
			if (popupWindowMapMeasure != null) {
				popupWindowMapMeasure.dismiss();
				mapMeasure.setImageResource(R.drawable.ic_measure);
			}
			resetMapMeasure();

		} else {
			isOpenMapMeaure = true;
			if (popupWindowMapMeasure == null) {
				popupWindowMapMeasure = new PopupWindow();
				View contentView = View.inflate(context, R.layout.map_measure, null);
				mapMeasureView = contentView;
				tvMeasureRes = tvMeasureResult;
				ImageButton measureDistance = (ImageButton) contentView.findViewById(R.id.imgBtn_map_measure_dis);
				ImageButton measureArea = (ImageButton) contentView.findViewById(R.id.imgBtn_map_measure_area);
				ImageButton measureClear = (ImageButton) contentView.findViewById(R.id.imgBtn_map_measure_clear);
				ImageButton measurePolygon = (ImageButton) contentView.findViewById(R.id.imgBtn_map_measure_polygon);

				measureDistance.setOnClickListener(orcl);
				measureArea.setOnClickListener(orcl);
				measureClear.setOnClickListener(orcl);
				measurePolygon.setOnClickListener(orcl);
				popupWindowMapMeasure.setContentView(contentView);
				popupWindowMapMeasure.setWidth(LayoutParams.WRAP_CONTENT);
				popupWindowMapMeasure.setHeight(LayoutParams.WRAP_CONTENT);
				// popupWindowMapMeasure.setFocusable(false);
				// popupWindowMapMeasure.setOutsideTouchable(true);

				popupWindowMapMeasure.setTouchable(true);
				popupWindowMapMeasure.setFocusable(true);
				popupWindowMapMeasure.setBackgroundDrawable(new BitmapDrawable());
				popupWindowMapMeasure.setOutsideTouchable(true);

				popupWindowMapMeasure.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						mapMeasure.setImageResource(R.drawable.ic_measure);
						isOpenMapMeaure = false;
					}
				});
			}
			popupWindowMapMeasure.showAsDropDown(mapMeasure);
			mapMeasure.setImageResource(R.drawable.ic_close);

		}
	}

	private MapToolCallback mapToolCallback = null;

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
	 * 监听器
	 */
	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.imgBtn_map_measure_dis) {// 距离量算
				resetMapMeasure();
				openDistaceTool();
				// 关闭窗口
				popupWindowMapMeasure.dismiss();
				if (mapToolEnter != null) {
					mapToolEnter.setImageResource(R.drawable.ic_dis);
					isOpenMapMeaure = false;
				}
			} else if (id == R.id.imgBtn_map_measure_area) {// 面积量算
				resetMapMeasure();
				openAreaTool();
				// 关闭窗口
				popupWindowMapMeasure.dismiss();
				if (mapToolEnter != null) {
					mapToolEnter.setImageResource(R.drawable.ic_area);
					isOpenMapMeaure = false;
				}
			} else if (id == R.id.imgBtn_map_measure_polygon) {// 多边形
				resetMapMeasure();
				openSketchPolygonTool();
				tvMeasureRes.setVisibility(View.GONE);
				// 关闭窗口
				popupWindowMapMeasure.dismiss();
				if (mapToolEnter != null) {
					mapToolEnter.setImageResource(R.drawable.ic_polygon);
					isOpenMapMeaure = false;
				}
			} else if (id == R.id.imgBtn_map_measure_clear) {// 清除
				resetMapMeasure();
				clearMapTools();
				popupWindowMapMeasure.dismiss();
				if (mapToolEnter != null) {
					mapToolEnter.setImageResource(R.drawable.ic_measure);
					isOpenMapMeaure = false;
				}
			}
		}
	};

	/**
	 * 重置地图量算
	 */
	private void resetMapMeasure() {
		if (mapMeasureView != null) {
			ImageButton measureDistance = (ImageButton) mapMeasureView.findViewById(R.id.imgBtn_map_measure_dis);
			ImageButton measureArea = (ImageButton) mapMeasureView.findViewById(R.id.imgBtn_map_measure_area);
			ImageButton measureClear = (ImageButton) mapMeasureView.findViewById(R.id.imgBtn_map_measure_clear);
			ImageButton measurePolygon = (ImageButton) mapMeasureView.findViewById(R.id.imgBtn_map_measure_polygon);
			measureDistance.setImageResource(R.drawable.ic_dis);
			measureArea.setImageResource(R.drawable.ic_area);
			measureClear.setImageResource(R.drawable.ic_clear);
			measurePolygon.setImageResource(R.drawable.ic_polygon);
		}
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
				// TODO Auto-generated method stub

			}

			@Override
			public void Run(HiEvent e) {
				// TODO Auto-generated method stub
				HiToolEvent event = (HiToolEvent) e;
				showMeasureResult(context, event.ResultValue, MapToolState.MEASURE_DISTANCE.getValue());

			}
		});
		// 测面
		areaTool = new HiAreaTool();
		areaTool.addEventListener(HiToolEvent.GraphicsChanged, new HiIEventListener() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub

			}

			@Override
			public void Run(HiEvent e) {
				// TODO Auto-generated method stub
				HiToolEvent event = (HiToolEvent) e;
				showMeasureResult(context, event.ResultValue, MapToolState.MEASURE_AREA.getValue());
			}
		});
		// 画多边形
		hiSketchPolygonTool = new HiSketchPolygonTool();
		hiSketchPolygonTool.addEventListener(HiToolEvent.CloseTool, new HiIEventListener() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub

			}

			@Override
			public void Run(HiEvent e) {
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
	 * 显示测量结果
	 * 
	 * @param value
	 */
	private void showMeasureResult(Context context, String resultStr, Integer value) {
		// TODO Auto-generated method stub
		// 获取显示结果的view
		String result = formatMeasureResult((int) Math.floor(Double.parseDouble(resultStr)), value);
		tvMeasureRes.setText(result);
		tvMeasureRes.setVisibility(View.VISIBLE);
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
	 * 清除地图工具痕迹
	 */
	public void clearMapTools() {
		// 清除工具
		closeDistaceTool();
		closeAreaTool();
		closeSketchPolygonTool();
		tvMeasureRes.setVisibility(View.GONE);
		MapCache.map.refresh();
	}

	/**
	 * 是否打开画多边形工具
	 * 
	 * @return
	 */
	private boolean isOpenSketchPolygonTool() {
		if (hiSketchPolygonTool != null) {
			return hiSketchPolygonTool.isToolOpened;
		}
		return false;
	}

	/**
	 * 打开画多边形工具
	 */
	public void openSketchPolygonTool() {
		if (hiSketchPolygonTool != null) {
			if (!hiSketchPolygonTool.isToolOpened) {
				hiSketchPolygonTool.openTool();
			}
		}
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
	 * 打开面积工具
	 */
	public void openAreaTool() {
		if (areaTool != null) {
			if (!areaTool.isToolOpened) {
				areaTool.openTool();
			}
		}
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
	public void openDistaceTool() {
		if (distaceTool != null) {
			if (!distaceTool.isToolOpened) {
				distaceTool.openTool();
			}
		}
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

	public void setContext(Context context) {
		this.context = context;
	}

	public void setTvMeasureRes(TextView tvMeasureRes) {
		this.tvMeasureRes = tvMeasureRes;
	}

}
