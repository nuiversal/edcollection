package com.winway.android.edcollection.map;

import java.util.ArrayList;
import java.util.List;

import ocn.himap.base.HiCoordinate;
import ocn.himap.datamanager.HiElement;
import ocn.himap.events.HiEvent;
import ocn.himap.events.HiGraphicsEvent;
import ocn.himap.events.HiIEventListener;
import ocn.himap.events.HiLayerEvent;
import ocn.himap.events.HiMapsEvent;
import ocn.himap.events.HiMarkerEvent;
import ocn.himap.graphics.HiBaseGraphics;
import ocn.himap.graphics.HiLine;
import ocn.himap.markers.HiBaseMarker;
import ocn.himap.markers.HiPointMarker;
import android.app.Activity;
import android.content.Context;

import com.winway.android.edcollection.adding.util.NewZbsbTool;
import com.winway.android.edcollection.adding.util.ShowDetailedInfoUtil;
import com.winway.android.edcollection.adding.util.TableDataUtil;
import com.winway.android.edcollection.adding.util.TableShowUtil;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.map.entity.MapOperatorFlag;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.map.entity.GraphicObj;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.util.GraphicsDrawUtils;
import com.winway.android.util.BroadcastReceiverUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ToastUtil;

/**
 * 地图操作
 * 
 * @author zgq
 *
 */
public class EcollectMapUtils {
	public static EcollectMapUtils instance;
	private Context mContext;
	/**
	 * 判断是否地图上路径点处于编辑状态下进入路径点编辑选择经纬度
	 */
	public static boolean isMapGpsEdit = false;
	private String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";

	public static EcollectMapUtils getInstance(Context context) {
		if (instance == null) {
			synchronized (EcollectMapUtils.class) {
				if (instance == null) {
					instance = new EcollectMapUtils(context);
				}
			}
		}
		return instance;
	}

	private EcollectMapUtils(Context context) {
		this.mContext = context;
	}

	/**
	 * 添加地图相关事件监听器
	 */
	public void addMapListener() {
		// 地图标注监听
		MapCache.map.addEventListener(HiMarkerEvent.MarkerClicked, new HiIEventListener() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub

			}

			@Override
			public void Run(HiEvent hiEvent) {
				HiMarkerEvent hiMarkerEvent = (HiMarkerEvent) hiEvent;
				HiBaseMarker hiBaseMarker = hiMarkerEvent.pMarker;
				OfflineAttachCenter center = new OfflineAttachCenter((Activity) mContext, projectBDUrl);
				DataCenter dataCenter = new DataCenterImpl(mContext, projectBDUrl);
				if (hiBaseMarker.getClass().equals(HiPointMarker.class)) {// 处理点标注
					HiPointMarker pointMarker = (HiPointMarker) hiMarkerEvent.pMarker;
					pointMarker.setNormalIndex();
					String id = pointMarker.nameStr;
					GraphicObj graphicObj = GraphicsDrawUtils.getInstance().getPointGraphicObj(id);
					Object businessObj = graphicObj.getBusinessObj();
					ShowDetailedInfoUtil.showDevice(businessObj, id, (Activity) mContext, center, dataCenter);
				}
			}
		});

		// 地图图形监听
		MapCache.map.addEventListener(HiGraphicsEvent.GraphicsClicked, new HiIEventListener() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub

			}

			@Override
			public void Run(HiEvent hiEvent) {
				// TODO Auto-generated method stub
				HiGraphicsEvent hiGraphicsEvent = (HiGraphicsEvent) hiEvent;
				HiBaseGraphics hiBaseGraphics = hiGraphicsEvent.pGraphics;
				if (hiBaseGraphics.getClass().equals(HiLine.class)) {// 处理线
					String id = hiBaseGraphics.nameStr;
					GraphicObj graphicObj = GraphicsDrawUtils.getInstance().getLineGraphicObj(id);
					if (graphicObj == null) {
						return;
					}
					Object businessObj = graphicObj.getBusinessObj();
					// 线路
					if (businessObj instanceof EcLineEntity) {
						ArrayList<Object[]> list = TableDataUtil.getByEcLineEntity((EcLineEntity) businessObj);
						TableShowUtil.showTable(list, (Activity) mContext, "线路", null,null,null,null,null,null);
					}else if (businessObj instanceof ChannelDataEntity) {
						TableShowUtil.showChannel(((ChannelDataEntity) businessObj), (Activity) mContext);
					}
				}
			}
		});
		// 监听地图等级改变
		MapCache.map.addEventListener(HiMapsEvent.MapsLevelChanged, new HiIEventListener() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub

			}

			@Override
			public void Run(HiEvent hiEvent) {
				// TODO Auto-generated method stub
				// 如果周边查询是打开的，当地图级别太小时，自动关闭搜索功能
				if (NewZbsbTool.isOpenZbss) {
					int mapLevel = MapCache.map.getMapsLevel();
					if (mapLevel < 14) {
						BroadcastReceiverUtils.getInstance().sendCommand(mContext,
								MapOperatorFlag.flag_zbcx_map_level_change);
						return;
					}
				}
			}
		});
		// 图层要素点击
		MapCache.map.addEventListener(HiLayerEvent.FeatureClicked, new HiIEventListener() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub

			}

			@Override
			public void Run(HiEvent hiEvent) {
				// TODO Auto-generated method stub

			}
		});
		// 地图点击监听
		MapCache.map.addEventListener(HiMapsEvent.MapsClicked, new HiIEventListener() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub

			}

			@Override
			public void Run(HiEvent hEvent) {
				// TODO Auto-generated method stub

			}
		});
		// 地图中心点改变监听
		MapCache.map.addEventListener(HiMapsEvent.MapsCenterChanged, new HiIEventListener() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub

			}

			@Override
			public void Run(HiEvent hiEvent) {
				// TODO Auto-generated method stub
				
				if (GlobalEntry.editNode == true) {// 编辑功能打开
					if (GlobalEntry.editMapSelectCoord == true) {// 编辑状态下地图选点获取坐标
						return;
					} else {
						HiCoordinate cCoord = new HiCoordinate(MapCache.map.getCx(), MapCache.map.getCy());
						ArrayList<String> nameArr = new ArrayList<String>();
						nameArr.add("point");
						List<HiElement> listEle = SpatialAnalysis.getInstance().searchByPoint(cCoord, 2, nameArr);
						if (listEle != null && listEle.size() == 1) {
							GlobalEntry.currentEditNode = (EcNodeEntity) listEle.get(0).DataReference;
							ToastUtil.show(mContext, "找到标识器: " + GlobalEntry.currentEditNode.getMarkerNo() + "，可以进行编辑",
									500);
							isMapGpsEdit = true;
							// 显示确定按钮
							BroadcastReceiverUtils.getInstance().sendCommand(mContext, MapOperatorFlag.flag_edit_show);
						} else {
							// 取消显示按钮
							BroadcastReceiverUtils.getInstance().sendCommand(mContext,
									MapOperatorFlag.flag_edit_show_cancel);
						}
					}

				}
				// 视野内查询
				if (NewZbsbTool.isOpenZbss) {
					// 1.移除元素
					// 点
					SpatialAnalysis.getInstance().removePoints(NewZbsbTool.pointElementList);
					// 2.重新绘制新视野内的元素
					// 执行查询
					HiCoordinate minCrd = MapCache.map.getMapsBounds().MinCrd;
					HiCoordinate maxCrd = MapCache.map.getMapsBounds().MaxCrd;
					ArrayList<HiCoordinate> coords = new ArrayList<HiCoordinate>();// 矩形范围
					coords.add(minCrd);
					coords.add(maxCrd);
					ArrayList<String> pointNameArr = new ArrayList<String>();
					pointNameArr.add("point");
					NewZbsbTool.pointElementList = SpatialAnalysis.getInstance().searchByRect(coords, pointNameArr);
					// 绘制元素
					SpatialAnalysis.getInstance().drawPoints(NewZbsbTool.pointElementList, mContext);
				}
			}
		});

	}
}
