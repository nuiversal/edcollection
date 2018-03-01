package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.util.CoordsUtils;
import com.winway.android.edcollection.map.SpatialAnalysis;
import com.winway.android.edcollection.map.entity.SpatialAnalysisDataType;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.ewidgets.radar.SearchDevicesView;
import com.winway.android.map.entity.GraphicObj;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.util.GraphicsDrawUtils;
import com.winway.android.util.ToastUtil;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import ocn.himap.base.HiCoordinate;
import ocn.himap.datamanager.HiElement;

/**
 * 周边查询
 * 
 * @author zgq
 *
 */
public class NewZbsbTool {
	private static NewZbsbTool instance;
	public static boolean isOpenZbss = false;// 是否打开周边搜索的功能
	public static List<HiElement> pointElementList = null;// 查询列表
	private List<GraphicObj> markers = new ArrayList<>();

	private NewZbsbTool() {
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static NewZbsbTool getInstance() {
		if (instance == null) {
			synchronized (NewZbsbTool.class) {
				if (instance == null) {
					instance = new NewZbsbTool();
				}
			}
		}
		return instance;
	}

	/**
	 * 打开查找周边工具
	 * 
	 * @param activity
	 * @param imageButton查找周边按钮
	 * @param searchDevicesView雷达视图
	 */
	public void openZbsbTool(final Activity activity, ImageButton btnZbsb, final SearchDevicesView viewRadar) {
		if (!isOpenZbss) {
			int mapLevel = MapCache.map.getMapsLevel();
			if (mapLevel < 14) {
				ToastUtil.show(activity, "放大地图等级才能使用该功能", 300);
				return;
			}
			isOpenZbss = true;
			ToastUtil.show(activity, "已打开", 300);

			btnZbsb.setImageResource(R.drawable.sm_s);
			viewRadar.setVisibility(View.VISIBLE);
			viewRadar.setWillNotDraw(false);
			viewRadar.setSearching(true);
			// 计时器 用于关闭雷达图案
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// 1秒之后关闭雷达
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// 更新UI
							viewRadar.setVisibility(View.GONE);
							viewRadar.setSearching(false);
							// 执行查询
							HiCoordinate minCrd = MapCache.map.getMapsBounds().MinCrd;
							HiCoordinate maxCrd = MapCache.map.getMapsBounds().MaxCrd;
							ArrayList<HiCoordinate> coords = new ArrayList<HiCoordinate>();// 矩形范围
							coords.add(minCrd);
							coords.add(maxCrd);
							ArrayList<String> pointNameArr = new ArrayList<String>();
							pointNameArr.add("point");
							pointElementList = SpatialAnalysis.getInstance().searchByRect(coords, pointNameArr);
							if (pointElementList == null || pointElementList.size() < 1) {
								return;
							}
							// 绘制元素
							SpatialAnalysis.getInstance().drawPoints(pointElementList, activity);
							// 绘制工井标注
							for (HiElement hiElement : pointElementList) {
								Object obj = hiElement.DataReference;
								if (obj instanceof EcWorkWellEntity) {
									EcWorkWellEntityVo ecWorkWellEntity = (EcWorkWellEntityVo) obj;
									ArrayList<HiCoordinate> coord = hiElement.Crds;
									double[] xy = new double[] { coord.get(0).x, coord.get(0).y };
									GraphicObj graphicObj = GraphicsDrawUtils.getInstance().drawTextMarker(
											ecWorkWellEntity, "zbcx_marker_" + ecWorkWellEntity.getObjId(), xy,
											ecWorkWellEntity.getSbmc(), "#ff0000");
									markers.add(graphicObj);
								}
							}

						}

					});
					timer.cancel();
				}
			}, 1000);
		} else {
			closeZbss(activity, btnZbsb, viewRadar);
			ToastUtil.show(activity, "已关闭", 300);
		}
	}

	public void closeZbss(Activity activity, ImageButton btnZbsb, SearchDevicesView viewRadar) {
		isOpenZbss = false;
		btnZbsb.setImageResource(R.drawable.sm_n);
		viewRadar.setVisibility(View.GONE);
		viewRadar.setSearching(false);
		// 移除元素
		SpatialAnalysis.getInstance().removePoints(pointElementList);
		// 移除标注
		for (int i = 0; i < markers.size(); i++) {
			GraphicObj graphicObj = markers.get(i);
			if (graphicObj != null) {
				GraphicsDrawUtils.getInstance().removePoint(graphicObj.getObjId());
			}
		}
		markers.clear();
		MapCache.map.refresh();
	}

	/**
	 * 获取查找周边是否打开
	 * 
	 * @return
	 */
	public boolean isOpenZbss() {
		return isOpenZbss;
	}

	/**
	 * 设置是否打开查找周报
	 * 
	 * @param isOpenZbss
	 */
	public void setOpenZbss(boolean isOpenZbss) {
		this.isOpenZbss = isOpenZbss;
	}

}
