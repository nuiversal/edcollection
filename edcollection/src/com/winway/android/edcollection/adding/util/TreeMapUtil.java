package com.winway.android.edcollection.adding.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.entity.IndexEcNodeEntity;
import com.winway.android.edcollection.base.entity.pointGeometry;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenter.CallBack;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.service.LineInfoCenter;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.map.entity.GraphicObj;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.util.GraphicsDrawUtils;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.ToastUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import ocn.himap.base.HiCoordinate;
import ocn.himap.base.HiMapsStatus;
import ocn.himap.maps.HiBaseMaps;
import ocn.himap.markers.HiTextMarker;

/**
 * 树节点地图工具类
 * 
 * @author mr-lao
 *
 */
public class TreeMapUtil {
	private DataCenter dataCenter;
	// 地图加载器
	private GraphicsDrawUtils maploader = GraphicsDrawUtils.getInstance();
	private Context context;
	// 线路闪烁颜色
	private String lineFlashColor = "#ff0000";
	// 线路颜色
	private String lineColor = "#235fd9";

	private TreeMapUtil(DataCenter dataCenter, Context context) {
		this.dataCenter = dataCenter;
		this.context = context;
	}

	public TreeMapUtil(Context context) {
		this.context = context;
	}

	private static TreeMapUtil instance = null;

	/**
	 * 单例
	 * 
	 * @param dataCenter
	 * @param context
	 * @return
	 */
	public static TreeMapUtil getInstance(DataCenter dataCenter, Context context) {
		synchronized (TreeMapUtil.class) {
			if (null == instance) {
				instance = new TreeMapUtil(dataCenter, context);
			}
		}
		return instance;
	}

	static LinkedList<String> lineIds = new LinkedList<String>();
	static LinkedList<String> pointIds = new LinkedList<String>();

	/** 判断设备是否持有正确的坐标 */
	boolean isHasRightLocation(DeviceEntity<?> device) {
		if (device.getNode() == null || TextUtils.isEmpty(device.getNode().getGeom())) {
			ToastUtils.show(context, "设备没有坐标信息，无法在地图上显示");
			return false;
		}
		return true;
	}

	/**
	 * 画线
	 * 
	 * @param lineId
	 */
	public void drawLine(final EcLineEntity objData, final String lineId) {
		dataCenter.getLineNodeList(lineId, "", new CallBack<List<EcNodeEntity>>() {
			@Override
			public void call(List<EcNodeEntity> data) {
				if (null == data || data.isEmpty()) {
					return;
				}
				GraphicsDrawUtils.getInstance().stopOtherFlash();
				// 坐标
				pointGeometry pointGeometry = null;
				ArrayList<HiCoordinate> coordArr = new ArrayList<HiCoordinate>();
				for (EcNodeEntity ecnode : data) {
					try {
						String geom = ecnode.getGeom();
						final Gson gson = GsonUtils.build();
						final Type pointGeometryType = new TypeToken<pointGeometry>() {
						}.getType();
						pointGeometry = gson.fromJson(geom, pointGeometryType);
						HiCoordinate addr = new HiCoordinate(pointGeometry.getX(), pointGeometry.getY());
						coordArr.add(addr);
					} catch (Exception e) {
						ToastUtils.show(context, "节点【" + ecnode.getMarkerNo() + "】无坐标信息，线路无法显示");
						return;
					}
				}
				GraphicObj graphicObj = maploader.drawLine(objData, lineId, coordArr, lineColor, lineFlashColor, null);
				/***/
				lineIds.add(lineId);
				/***/
				GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
				MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
				translationMapsViewTo(MapCache.map, coordArr);
				MapCache.map.refresh();
			}
		});
	}

	/**
	 * 移除线路
	 * 
	 * @param lineId
	 */
	public void removeLine(String lineId) {
		maploader.removeLine(lineId);
		MapCache.map.refresh();
	}

	/**
	 * 添加变电站到地图
	 * 
	 * @param station
	 * @param ms
	 * @return
	 */
	private pointGeometry addStationToMap(DeviceEntity<EcSubstationEntityVo> station, String ms) {
		// 坐标
		pointGeometry pointGeometry = null;
		EcNodeEntity ecnode = station.getNode();
		String geom = ecnode.getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biandianzhan_s);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biandianzhan_n);
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		/***/
		pointIds.add(ecnode.getOid() + ms);
		/***/
		maploader.drawPointMarker(station, ecnode.getOid() + ms, coords, bitmapStyle1, bitmapStyle2, "", "");
		return pointGeometry;
	}

	/**
	 * 添加线路节点到地图
	 * 
	 * @param ecnode
	 * @param ms
	 * @return
	 */
	private pointGeometry addNodeToMap(EcNodeEntity ecnode, String ms, String lineId) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = ecnode.getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		Bitmap bitmapStyle1, bitmapStyle2;
		if (ecnode.getMarkerType() == 1) {
			bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biaoshiqiu_s);
			bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biaoshiqiu_n);
		} else if (ecnode.getMarkerType() == 2) {
			bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biaoshiding_s);
			bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biaoshiding_n);
		} else if (ecnode.getMarkerType() == 0) {
			bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.xnd_n);
			bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.xnd_s);
		} else if (ecnode.getMarkerType() == 3) {
			bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ganta_n);
			bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ganta_s);
		} else {
			bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ajh16);
			bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ajh16_red);
		}
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		String nodeMapObjid = ecnode.getOid() + ms;
		/***/
		pointIds.add(nodeMapObjid);
		/***/
		maploader.drawPointMarker(ecnode, nodeMapObjid, coords, bitmapStyle1, bitmapStyle2, "", "");
		String markerColor = "#FF0000";
		String text = "";
		// 画标注
		if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_NO) {
			text = ecnode.getMarkerNo();
		} else if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_ORDER) {
			String order = "";
			EcLineNodesEntity lineNode = null;
			if (null != dataCenter) {
				lineNode = dataCenter.getLineNode(ecnode.getOid(), lineId, null, null);
			} else {
				lineNode = LineInfoCenter.getLineNodeIndex(lineId, ecnode.getOid());
			}
			if (null != lineNode) {
				order += lineNode.getNIndex();
			}
			text = order;
		}
		if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_NO
				|| NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_ORDER) {
			maploader.drawTextMarker(ecnode, nodeMapObjid + "#TEXT-LAYER", coords, text, markerColor);
			pointIds.add(nodeMapObjid + "#TEXT-LAYER");
		}
		return pointGeometry;
	}

	/**
	 * 添加标签到地图
	 * 
	 * @param device
	 * @param ms
	 * @return
	 */
	private pointGeometry addLabelToMap(DeviceEntity<EcLineLabelEntityVo> device, String ms) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = device.getNode().getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.zhadai_s);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.zhadai_n);
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		/***/
		pointIds.add(device.getNode().getOid() + ms);
		/***/
		maploader.drawPointMarker(device, device.getNode().getOid() + ms, coords, bitmapStyle1, bitmapStyle2, "", "");
		return pointGeometry;
	}

	/**
	 * 添加中间接头到地图
	 * 
	 * @param device
	 * @param ms
	 * @return
	 */
	private pointGeometry addMiddleToMap(DeviceEntity<EcMiddleJointEntityVo> device, String ms) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = device.getNode().getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jietou_s);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jietou_n);
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		/***/
		pointIds.add(device.getNode().getOid() + ms);
		/***/
		maploader.drawPointMarker(device, device.getNode().getOid() + ms, coords, bitmapStyle1, bitmapStyle2, "", "");
		return pointGeometry;
	}

	/**
	 * 添加工井到地图
	 * 
	 * @param device
	 * @param ms
	 * @return
	 */
	private pointGeometry addWellToMap(DeviceEntity<EcWorkWellEntityVo> device, String ms) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = device.getNode().getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gongjing_s);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gongjing_n);
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		/***/
		pointIds.add(device.getNode().getOid() + ms);
		/***/
		maploader.drawPointMarker(device, device.getNode().getOid() + ms, coords, bitmapStyle1, bitmapStyle2, "", "");
		return pointGeometry;
	}

	/**
	 * 添加分接箱到地图
	 * 
	 * @param device
	 * @param ms
	 * @return
	 */
	private pointGeometry addBoxToMap(DeviceEntity<EcDistBoxEntityVo> device, String ms) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = device.getNode().getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		if (device.getData().getJointType() == 1) {
			Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jiexianxiang_s);
			Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jiexianxiang_n);
			double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
			maploader.drawPointMarker(device, device.getNode().getOid() + ms, coords, bitmapStyle1, bitmapStyle2, "",
					"");
		} else {
			Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.huanwanggui_s);
			Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.huanwanggui_n);
			double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
			maploader.drawPointMarker(device, device.getNode().getOid() + ms, coords, bitmapStyle1, bitmapStyle2, "",
					"");
		}
		/***/
		pointIds.add(device.getNode().getOid() + ms);
		/***/
		return pointGeometry;
	}

	/**
	 * 添加杆塔到地图
	 * 
	 * @param device
	 * @param ms
	 * @return
	 */
	private pointGeometry addTowerToMap(DeviceEntity<EcTowerEntityVo> device, String ms) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = device.getNode().getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ganta_s);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ganta_n);
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		maploader.drawPointMarker(device, device.getNode().getOid() + ms, coords, bitmapStyle1, bitmapStyle2, "", "");
		/***/
		pointIds.add(device.getNode().getOid() + ms);
		/***/
		return pointGeometry;
	}

	/**
	 * 添加配电房到地图
	 * 
	 * @param device
	 * @param ms
	 * @return
	 */
	private pointGeometry addRoomToMap(DeviceEntity<EcDistributionRoomEntityVo> device, String ms) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = device.getNode().getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pdpdz);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pdpdz_s);
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		maploader.drawPointMarker(device, device.getNode().getOid() + ms, coords, bitmapStyle1, bitmapStyle2, "", "");
		/***/
		pointIds.add(device.getNode().getOid() + ms);
		/***/
		return pointGeometry;
	}

	/**
	 * 添加变压器到地图
	 * 
	 * @param device
	 * @param ms
	 * @return
	 */
	private pointGeometry addTransToMap(DeviceEntity<EcTransformerEntityVo> device, String ms) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = device.getNode().getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bianyaqi_s);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bianyaqi_n);
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		maploader.drawPointMarker(device, device.getNode().getOid() + ms, coords, bitmapStyle1, bitmapStyle2, "", "");
		/***/
		pointIds.add(device.getNode().getOid() + ms);
		/***/
		return pointGeometry;
	}

	/**
	 * 画变电站
	 * 
	 * @param stationId
	 */
	public void drawStation(final String stationId) {
		DeviceEntity<EcSubstationEntityVo> data = dataCenter.getStation(stationId, null, null);
		if (null == data || !isHasRightLocation(data)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		pointGeometry pointGeometry = addStationToMap(data, "STATION");
		GraphicObj graphicObj = maploader.getPointGraphicObj(data.getNode().getOid() + "STATION");
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 画变电站
	 * 
	 * @param stationId
	 */
	public void drawStation(DeviceEntity<EcSubstationEntityVo> station) {
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		if (!isHasRightLocation(station)) {
			return;
		}
		pointGeometry pointGeometry = addStationToMap(station, "STATION");
		GraphicObj graphicObj = maploader.getPointGraphicObj(station.getNode().getOid() + "STATION");
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除变电站
	 * 
	 * @param oid
	 */
	public void removeStation(String stationId) {
		dataCenter.getStation(stationId, "", new CallBack<DeviceEntity<EcSubstationEntityVo>>() {
			@Override
			public void call(DeviceEntity<EcSubstationEntityVo> data) {
				if (null == data) {
					return;
				}
				if (data.getNode() == null) {
					return;
				}
				String oid = data.getNode().getOid();
				maploader.removePoint(oid + "STATION");
				MapCache.map.refresh();
			}
		});
	}

	/**
	 * 画节点
	 * 
	 * @param ecnode
	 */
	public void drawNode(EcNodeEntity ecnode, String lineid) {
		if (TextUtils.isEmpty(ecnode.getGeom())) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		pointGeometry pointGeometry = addNodeToMap(ecnode, "#" + lineid, lineid);
		GraphicObj graphicObj = maploader.getPointGraphicObj(ecnode.getOid() + "#" + lineid);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 添加通道节点到地图
	 * 
	 * @param ecnode
	 * @param ms
	 * @return
	 */
	private pointGeometry addChannelNodeToMap(EcNodeEntity ecnode, String ms, String channelId) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = ecnode.getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		Bitmap bitmapStyle1, bitmapStyle2;
		if (ecnode.getMarkerType() == 1) {
			bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biaoshiqiu_s);
			bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biaoshiqiu_n);
		} else if (ecnode.getMarkerType() == 2) {
			bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biaoshiding_s);
			bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.biaoshiding_n);
		} else if (ecnode.getMarkerType() == 0) {
			bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.xnd_n);
			bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.xnd_s);
		} else if (ecnode.getMarkerType() == 3) {
			bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ganta_n);
			bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ganta_s);
		} else {
			bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ajh16);
			bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ajh16_red);
		}
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		String nodeMapObjid = ecnode.getOid() + ms;
		/***/
		pointIds.add(nodeMapObjid);
		/***/
		maploader.drawPointMarker(ecnode, nodeMapObjid, coords, bitmapStyle1, bitmapStyle2, "", "");
		String markerColor = "#F5B50D";
		String text = "";
		// 画标注
		if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_NO) {
			text = ecnode.getMarkerNo();
		} else if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_ORDER) {
			String order = "";
			EcChannelNodesEntity channelNode = null;
			if (null != dataCenter) {
				channelNode = dataCenter.getChannelNode(ecnode.getOid(), channelId, null, null);
			}
			if (null != channelNode) {
				order += channelNode.getNIndex();
			}
			text = order;
		}
		if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_NO
				|| NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_ORDER) {
			maploader.drawTextMarker(ecnode, nodeMapObjid + "#Channel-TEXT-LAYER", coords, text, markerColor);
			pointIds.add(nodeMapObjid + "#Channel-TEXT-LAYER");
		}
		return pointGeometry;
	}

	/**
	 * 画通道节点
	 * 
	 * @param ecnode
	 */
	public void drawNodeWithChannelId(EcNodeEntity ecnode, String channelId) {
		if (TextUtils.isEmpty(ecnode.getGeom())) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		pointGeometry pointGeometry = addChannelNodeToMap(ecnode, "#Channel" + channelId, channelId);
		GraphicObj graphicObj = maploader.getPointGraphicObj(ecnode.getOid() + "#Channel" + channelId);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 画节点
	 * 
	 * @param ecnode
	 */
	public void drawNode(EcNodeEntity ecnode) {
		drawNode(ecnode, "");
	}

	/**
	 * 移除节点
	 * 
	 * @param oid
	 */
	public void removeNode(String oid, String lineid) {
		maploader.removePoint(oid + "#" + lineid);
		maploader.removePoint(oid + "#" + null);
		maploader.removePoint(oid + "#" + lineid + "#TEXT-LAYER");
		MapCache.map.refresh();
	}

	/**
	 * 移除通道节点
	 * 
	 * @param oid
	 */
	public void removeChannelNode(String oid, String channelId) {
		maploader.removePoint(oid + "#Channel" + channelId);
		maploader.removePoint(oid + "#Channel" + channelId + "#Channel-TEXT-LAYER");
		MapCache.map.refresh();
	}

	/**
	 * 画标签
	 * 
	 * @param device
	 */
	public void drawLabel(DeviceEntity<EcLineLabelEntityVo> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		pointGeometry pointGeometry = addLabelToMap(device, "LABEL#LINE_NAME#" + linename);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "LABEL#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除标签
	 * 
	 * @param oid
	 */
	public void removeLabel(String oid, String linename) {
		maploader.removePoint(oid + "LABEL#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 画中间接头
	 * 
	 * @param device
	 */
	public void drawMiddle(DeviceEntity<EcMiddleJointEntityVo> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		pointGeometry pointGeometry = addMiddleToMap(device, "MIDDLE#LINE_NAME#" + linename);
		GraphicObj graphicObj = maploader
				.getPointGraphicObj(device.getNode().getOid() + "MIDDLE#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除中间接头
	 * 
	 * @param oid
	 */
	public void removeMiddle(String oid, String linename) {
		maploader.removePoint(oid + "MIDDLE#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 画工井
	 * 
	 * @param device
	 */
	public void drawWell(DeviceEntity<EcWorkWellEntityVo> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		pointGeometry pointGeometry = addWellToMap(device, "WELL#LINE_NAME#" + linename);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "WELL#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除工井
	 * 
	 * @param oid
	 */
	public void removeWell(String oid, String linename) {
		maploader.removePoint(oid + "WELL#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 画分接箱
	 * 
	 * @param device
	 */
	public void drawBox(DeviceEntity<EcDistBoxEntityVo> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		pointGeometry pointGeometry = addBoxToMap(device, "BOX#LINE_NAME#" + linename);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "BOX#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除分接箱
	 * 
	 * @param oid
	 */
	public void removeBox(String oid, String linename) {
		maploader.removePoint(oid + "BOX#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 画杆塔
	 * 
	 * @param device
	 */
	public void drawTower(DeviceEntity<EcTowerEntityVo> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		pointGeometry pointGeometry = addTowerToMap(device, "TOWER#LINE_NAME#" + linename);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "TOWER#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除杆塔
	 * 
	 * @param oid
	 */
	public void removeTower(String oid, String linename) {
		maploader.removePoint(oid + "TOWER#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 画配电房
	 * 
	 * @param device
	 */
	public void drawRoom(DeviceEntity<EcDistributionRoomEntityVo> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		pointGeometry pointGeometry = addRoomToMap(device, "ROOM#LINE_NAME#" + linename);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "ROOM#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除配电房
	 * 
	 * @param oid
	 */
	public void removeRoom(String oid, String linename) {
		maploader.removePoint(oid + "ROOM#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 画变压器
	 * 
	 * @param device
	 */
	public void drawTrans(DeviceEntity<EcTransformerEntityVo> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		pointGeometry pointGeometry = addTransToMap(device, "TRANS#LINE_NAME#" + linename);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "TRANS#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除变压器
	 * 
	 * @param oid
	 */
	public void removeTrans(String oid, String linename) {
		maploader.removePoint(oid + "TRANS#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 清除所有的地图覆盖物
	 */
	public void clearMapOverlays() {
		for (String lineid : lineIds) {
			maploader.removeLine(lineid);
		}
		for (String pointid : pointIds) {
			maploader.removePoint(pointid);
		}
		MapCache.map.refresh();
	}

	/**
	 * 添加节点到地图
	 * 
	 * @param ecnode
	 * @param ms
	 * @return
	 */
	private pointGeometry addDeviceToMap(DeviceEntity<?> device, String ms, Bitmap bitmapStyle1, Bitmap bitmapStyle2) {
		// 坐标
		pointGeometry pointGeometry = null;
		EcNodeEntity ecnode = device.getNode();
		if (ecnode == null) {
			return null;
		}
		String geom = ecnode.getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		/***/
		pointIds.add(ecnode.getOid() + ms);
		/***/
		maploader.drawPointMarker(device, ecnode.getOid() + ms, coords, bitmapStyle1, bitmapStyle2, "", "");
		return pointGeometry;
	}

	/**
	 * 画环网柜
	 * 
	 * @param device
	 * @param linename
	 */
	public void drawHwg(DeviceEntity<EcHwgEntity> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.huanwanggui_n);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.huanwanggui_s);
		pointGeometry pointGeometry = addDeviceToMap(device, "HWG#LINE_NAME#" + linename, bitmapStyle1, bitmapStyle2);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "HWG#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除环网柜
	 * 
	 * @param oid
	 * @param linename
	 */
	public void removeHwg(String oid, String linename) {
		maploader.removePoint(oid + "HWG#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 画箱式变电站
	 * 
	 * @param device
	 * @param linename
	 */
	public void drawXsbdz(DeviceEntity<EcXsbdzEntity> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pdxb);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pdxb_s);
		pointGeometry pointGeometry = addDeviceToMap(device, "XSBDZ#LINE_NAME#" + linename, bitmapStyle1, bitmapStyle2);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "XSBDZ#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除箱式变电站
	 * 
	 * @param oid
	 * @param linename
	 */
	public void removeXsbdz(String oid, String linename) {
		maploader.removePoint(oid + "XSBDZ#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 画电缆分支箱
	 * 
	 * @param device
	 * @param linename
	 */
	public void drawDlfzx(DeviceEntity<EcDlfzxEntity> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jiexianxiang_n);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jiexianxiang_s);
		pointGeometry pointGeometry = addDeviceToMap(device, "DLFZX#LINE_NAME#" + linename, bitmapStyle1, bitmapStyle2);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "DLFZX#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除电缆分支箱
	 * 
	 * @param oid
	 * @param linename
	 */
	public void removeDlfzx(String oid, String linename) {
		maploader.removePoint(oid + "DLFZX#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 画低压电缆分支箱
	 * 
	 * @param device
	 * @param linename
	 */
	public void drawDydlfzx(DeviceEntity<EcDydlfzxEntity> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pdhwkgx);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pdhwkgx_s);
		pointGeometry pointGeometry = addDeviceToMap(device, "DYDLFZX#LINE_NAME#" + linename, bitmapStyle1,
				bitmapStyle2);
		GraphicObj graphicObj = maploader
				.getPointGraphicObj(device.getNode().getOid() + "DYDLFZX#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除低压电缆分支箱
	 * 
	 * @param oid
	 * @param linename
	 */
	public void removeDydlfzx(String oid, String linename) {
		maploader.removePoint(oid + "DYDLFZX#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 低压配电箱
	 * 
	 * @param device
	 * @param linename
	 */
	public void drawDypdx(DeviceEntity<EcDypdxEntity> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.peidianxiang);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.peidianxiang);
		pointGeometry pointGeometry = addDeviceToMap(device, "DYPDX#LINE_NAME#" + linename, bitmapStyle1, bitmapStyle2);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "DYPDX#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除低压配电箱
	 * 
	 * @param oid
	 * @param linename
	 */
	public void removeDypdx(String oid, String linename) {
		maploader.removePoint(oid + "DYPDX#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 开关站
	 * 
	 * @param device
	 * @param linename
	 */
	public void drawKgz(DeviceEntity<EcKgzEntity> device, String linename) {
		if (!isHasRightLocation(device)) {
			return;
		}
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pdkgf);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pdkgf_s);
		pointGeometry pointGeometry = addDeviceToMap(device, "KGZ#LINE_NAME#" + linename, bitmapStyle1, bitmapStyle2);
		GraphicObj graphicObj = maploader.getPointGraphicObj(device.getNode().getOid() + "KGZ#LINE_NAME#" + linename);
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除开关站
	 * 
	 * @param oid
	 * @param linename
	 */
	public void removeKgz(String oid, String linename) {
		maploader.removePoint(oid + "KGZ#LINE_NAME#" + linename);
		MapCache.map.refresh();
	}

	/**
	 * 画设备（根据设备类型画出对应的设备图标）
	 * 
	 * @param device
	 */
	@SuppressWarnings("unchecked")
	public void drawDevice(DeviceEntity<?> device) {
		Object deviceObj = device.getData();
		if (deviceObj instanceof EcWorkWellEntity) {
			// 电缆井
			drawWell((DeviceEntity<EcWorkWellEntityVo>) device, "");
		} else if (deviceObj instanceof EcSubstationEntity) {
			// 变电站
			drawStation((DeviceEntity<EcSubstationEntityVo>) device);
		} else if (deviceObj instanceof EcKgzEntity) {
			// 开关站
			drawKgz((DeviceEntity<EcKgzEntity>) device, "");
		} else if (deviceObj instanceof EcDistributionRoomEntity) {
			// 配电房
			drawRoom((DeviceEntity<EcDistributionRoomEntityVo>) device, "");
		} else if (deviceObj instanceof EcHwgEntity) {
			// 环网柜
			drawHwg((DeviceEntity<EcHwgEntity>) device, "");
		} else if (deviceObj instanceof EcXsbdzEntity) {
			drawXsbdz((DeviceEntity<EcXsbdzEntity>) device, "");
		} else if (deviceObj instanceof EcDlfzxEntity) {
			drawDlfzx((DeviceEntity<EcDlfzxEntity>) device, "");
		} else if (deviceObj instanceof EcDydlfzxEntity) {
			drawDydlfzx((DeviceEntity<EcDydlfzxEntity>) device, "");
		} else if (deviceObj instanceof EcDypdxEntity) {
			drawDypdx((DeviceEntity<EcDypdxEntity>) device, "");
		} else if (deviceObj instanceof EcTransformerEntity) {
			// 变压器
			drawTrans((DeviceEntity<EcTransformerEntityVo>) device, "");
		} else if (deviceObj instanceof EcTowerEntity) {
			// 杆塔
			drawTower((DeviceEntity<EcTowerEntityVo>) device, "");
		} else if (deviceObj instanceof EcMiddleJointEntity) {
			// 中间接头
			drawMiddle((DeviceEntity<EcMiddleJointEntityVo>) device, "");
		} else if (deviceObj instanceof EcLineLabelEntity) {
			// 电子标签
			drawLabel((DeviceEntity<EcLineLabelEntityVo>) device, "");
		}
	}

	// 画通道线路
	private void drawChannelLine(String color1, String color2, ChannelDataEntity channel, String id) {
		GraphicsDrawUtils.getInstance().stopOtherFlash();
		// 坐标
		pointGeometry pointGeometry = null;
		ArrayList<HiCoordinate> coordArr = new ArrayList<HiCoordinate>();
		List<IndexEcNodeEntity<EcChannelNodesEntity>> nodes = channel.getNodes();
		ArrayList<EcNodeEntity> nodeList = new ArrayList<EcNodeEntity>();
		for (IndexEcNodeEntity<EcChannelNodesEntity> n : nodes) {
			nodeList.add(n.getNode());
		}
		for (EcNodeEntity ecnode : nodeList) {
			try {
				String geom = ecnode.getGeom();
				final Gson gson = GsonUtils.build();
				final Type pointGeometryType = new TypeToken<pointGeometry>() {
				}.getType();
				pointGeometry = gson.fromJson(geom, pointGeometryType);
				HiCoordinate addr = new HiCoordinate(pointGeometry.getX(), pointGeometry.getY());
				coordArr.add(addr);
			} catch (Exception e) {
				if (null != ecnode) {
					ToastUtils.show(context, "节点【" + ecnode.getMarkerNo() + "】无坐标信息，线路无法显示");
				} else {
					ToastUtils.show(context, "通道数据不全，无法显示通道");
				}
				return;
			}
		}
		GraphicObj graphicObj = maploader.drawLine(channel, id, coordArr, color1, color2, null);
		/***/
		lineIds.add(id);
		/***/
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		if (pointGeometry == null) {
			ToastUtils.show(context, "此通道设备没有经纬度，无法在地图上显示", 200);
		} else {
			MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
			translationMapsViewTo(MapCache.map, coordArr);
		}
		MapCache.map.refresh();
	}

	/**
	 * 画通道
	 * 
	 * @param channel
	 */
	public void drawChannel(ChannelDataEntity channel) {
		String color1 = "#ffff00";
		String color2 = "#d7d7d7";
		String id = channel.getData().getEcChannelId() + "";
		if (channel.getChannelType() instanceof EcChannelDgEntity) {
			// 顶管
			color1 = "#F30A92";
			// color2 = "#F30A92";
		}
		if (channel.getChannelType() instanceof EcChannelDlqEntity) {
			// 电缆桥
			color1 = "#B200FF";
			// color2 = "#B200FF";
		}
		if (channel.getChannelType() instanceof EcChannelDlgdEntity) {
			// 电缆管道
			color1 = "#ffff00";
			// color2 = "#456788";
		}
		if (channel.getChannelType() instanceof EcChannelDlsdEntity) {
			// 电缆隧道
			color1 = "#974806";
			// color2 = "#974806";
		}
		if (channel.getChannelType() instanceof EcChannelDlzmEntity) {
			// 电缆直埋
			color1 = "#00B0F0";
			// color2 = "#00B0F0";
		}
		if (channel.getChannelType() instanceof EcChannelDlgEntity) {
			// 电缆沟
			color1 = "#00B050";
			// color2 = "#00B050";
		}
		if (channel.getChannelType() instanceof EcChannelJkEntity) {
			// 架空
			color1 = "#a64d79";
			// color2 = "#00B050";
		}
		if (channel.getChannelType() instanceof EcChannelDlcEntity) {
			// 电缆槽
			color1 = "#005c3f";
			// color2 = "#00B050";
		}
		drawChannelLine(color1, color2, channel, id);
	}

	/**
	 * 移除通道
	 * 
	 * @param channelID
	 */
	public void removeChannel(String channelID) {
		maploader.removeLine(channelID + "");
		MapCache.map.refresh();
	}

	/**
	 * 画标注
	 */
	public void drawTextLayer() {
		ArrayList<GraphicObj> pointList = maploader.getPointList();
		if (null == pointList || pointList.isEmpty()) {
			return;
		}
		// 路径点
		ArrayList<GraphicObj> nodeList = new ArrayList<>();
		ArrayList<GraphicObj> channelNodeList = new ArrayList<>();
		// 标注
		HashMap<String, GraphicObj> nodeLayerMap = new HashMap<>();
		HashMap<String, GraphicObj> channelNodeLayerMap = new HashMap<>();
		for (GraphicObj graphicObj : pointList) {
			if (null == graphicObj.getBusinessObj() || null == graphicObj.getObjId()) {
				continue;
			}
			if (graphicObj.getBusinessObj() instanceof EcNodeEntity) {
				// 路径点或者是标注
				if (graphicObj.getObjId().contains("#TEXT-LAYER")) {
					nodeLayerMap.put(graphicObj.getObjId(), graphicObj);
				} else if (graphicObj.getObjId().contains("#Channel-TEXT-LAYER")) {
					channelNodeLayerMap.put(graphicObj.getObjId(), graphicObj);
				} else if (graphicObj.getObjId().contains("#Channel")) {
					channelNodeList.add(graphicObj);
				} else {
					nodeList.add(graphicObj);
				}
			}
		}
		if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.BSQ_IMG) {
			// 移除所有标注文本
			if (nodeLayerMap != null && !nodeLayerMap.isEmpty()) {
				Set<String> keySet = nodeLayerMap.keySet();
				for (String key : keySet) {
					maploader.removePoint(key);
				}
			}
			if (channelNodeLayerMap != null && !channelNodeLayerMap.isEmpty()) {
				Set<String> channelKeySet = channelNodeLayerMap.keySet();
				for (String string : channelKeySet) {
					maploader.removePoint(string);
				}
			}
			MapCache.map.refresh();
			return;
		}
		if (nodeList != null && !nodeList.isEmpty()) {
			for (GraphicObj graphicObj : nodeList) {
				// 改变或者添加文本
				GraphicObj obj = nodeLayerMap.get(graphicObj.getObjId() + "#TEXT-LAYER");
				if (obj != null) {
					String text = "";
					if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_NO) {
						text = ((EcNodeEntity) obj.getBusinessObj()).getMarkerNo();
					} else if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_ORDER) {
						EcLineNodesEntity lineNode = dataCenter.getLineNode(
								((EcNodeEntity) obj.getBusinessObj()).getOid(),
								obj.getObjId().split("#").length > 1 ? obj.getObjId().split("#")[1] : null, null, null);
						if (lineNode != null) {
							text = lineNode.getNIndex() + "";
						}
					}
					HiTextMarker hiTextMarker = (HiTextMarker) obj.getGraphicObj();
					hiTextMarker.Text = text;
					/*
					 * maploader.removePoint(obj.getObjId()); addTextLayerToMap((EcNodeEntity)
					 * graphicObj.getBusinessObj(), graphicObj.getObjId().split("#").length > 1 ?
					 * graphicObj.getObjId().split("#")[1] : null);
					 */
				} else {
					addTextLayerToMap((EcNodeEntity) graphicObj.getBusinessObj(),
							graphicObj.getObjId().split("#").length > 1 ? graphicObj.getObjId().split("#")[1] : null);
				}
			}
		}
		if (channelNodeList != null && !channelNodeList.isEmpty()) {
			for (GraphicObj graphicObj : channelNodeList) {
				// 改变或者添加通道文本
				GraphicObj channelObj = channelNodeLayerMap.get(graphicObj.getObjId() + "#Channel-TEXT-LAYER");
				if (channelObj != null) {
					String text = "";
					if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_NO) {
						text = ((EcNodeEntity) channelObj.getBusinessObj()).getMarkerNo();
					} else if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_ORDER) {
						EcChannelNodesEntity channelNode = dataCenter.getChannelNode(
								((EcNodeEntity) channelObj.getBusinessObj()).getOid(),
								channelObj.getObjId().split("#Channel").length > 1
										? channelObj.getObjId().split("#Channel")[1]
										: null,
								null, null);
						if (channelNode != null) {
							text = channelNode.getNIndex() + "";
						}
					}
					HiTextMarker hiTextMarker = (HiTextMarker) channelObj.getGraphicObj();
					hiTextMarker.Text = text;

				} else {
					addChannelTextLayerToMap((EcNodeEntity) graphicObj.getBusinessObj(),
							graphicObj.getObjId().split("#Channel").length > 1
									? graphicObj.getObjId().split("#Channel")[1]
									: null);
				}

			}
		}
		MapCache.map.refresh();
	}

	private pointGeometry addTextLayerToMap(EcNodeEntity ecnode, String lineId) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = ecnode.getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		String markerColor = "#FF0000";
		String text = "";
		// 画标注
		if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_NO) {
			text = ecnode.getMarkerNo();
		} else if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_ORDER) {
			String order = "";
			EcLineNodesEntity lineNode = null;
			if (null != dataCenter) {
				lineNode = dataCenter.getLineNode(ecnode.getOid(), lineId, null, null);
			} else {
				lineNode = LineInfoCenter.getLineNodeIndex(lineId, ecnode.getOid());
			}
			if (null != lineNode) {
				order += lineNode.getNIndex();
			}
			text = order;
		}
		String pointMapId = ecnode.getOid() + "#" + lineId + "#TEXT-LAYER";
		maploader.drawTextMarker(ecnode, pointMapId, coords, text, markerColor);
		pointIds.add(pointMapId);
		return pointGeometry;
	}

	private pointGeometry addChannelTextLayerToMap(EcNodeEntity ecnode, String channelId) {
		// 坐标
		pointGeometry pointGeometry = null;
		String geom = ecnode.getGeom();
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry = gson.fromJson(geom, pointGeometryType);
		double coords[] = new double[] { pointGeometry.getX(), pointGeometry.getY() };
		String markerColor = "#F5B50D";
		String text = "";
		// 画标注
		if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_NO) {
			text = ecnode.getMarkerNo();
		} else if (NewMarkWayTool.getInstance().WHAT == NewMarkWayTool.MARKER_ORDER) {
			String order = "";
			EcChannelNodesEntity channelNode = null;
			if (null != dataCenter) {
				channelNode = dataCenter.getChannelNode(ecnode.getOid(), channelId, null, null);
			}
			if (null != channelNode) {
				order += channelNode.getNIndex();
			}
			text = order;
		}
		String pointMapId = ecnode.getOid() + "#Channel" + channelId + "#Channel-TEXT-LAYER";
		maploader.drawTextMarker(ecnode, pointMapId, coords, text, markerColor);
		pointIds.add(pointMapId);
		return pointGeometry;
	}

	/**
	 * 画井盖
	 * 
	 * @param device
	 */
	public void drawCover(DeviceEntity<EcWorkWellCoverEntity> device) {
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cover);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cover_s);
		pointGeometry pointGeometry = addDeviceToMap(device, "#" + device.getData().getObjId(), bitmapStyle1,
				bitmapStyle2);
		if (pointGeometry == null) {
			return;
		}
		GraphicObj graphicObj = maploader
				.getPointGraphicObj(device.getNode().getOid() + "#" + device.getData().getObjId());
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
		MapCache.map.refresh();
	}

	/**
	 * 移除井盖
	 * 
	 * @param nodeId
	 * @param covorId
	 */
	public void removeCover(String nodeId, String coverId) {
		maploader.removePoint(nodeId + "#" + coverId);
		MapCache.map.refresh();
	}

	/**
	 * 移动缩放地图，使路线完全展示
	 * 
	 * @param mapsView
	 * @param crdList
	 */
	public void translationMapsViewTo(HiBaseMaps mapsView, List<HiCoordinate> crdList) {
		HiCoordinate[] array = (HiCoordinate[]) crdList.toArray(new HiCoordinate[crdList.size()]);
		HiMapsStatus status = mapsView.getAppropriateMapsStatus(array);
		try {
			int level = status.MapsLevel > mapsView.getMaxLevel() ? mapsView.getMaxLevel()
					: (status.MapsLevel > 2 ? status.MapsLevel - 1 : (status.MapsLevel > 1 ? status.MapsLevel : 1));
			mapsView.setMapsLevel(level);
			mapsView.smoothMove(status.Center.x, status.Center.y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
