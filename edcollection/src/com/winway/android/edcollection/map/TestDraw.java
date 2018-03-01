package com.winway.android.edcollection.map;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.bll.BasicInfoBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.pointGeometry;
import com.winway.android.edcollection.base.util.CoordsUtils;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.LineDetailsEntity;
import com.winway.android.edcollection.datacenter.api.DataCenter.CallBack;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.map.entity.GraphicObj;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.util.GraphicsDrawUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.GsonUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import ocn.himap.base.HiCoordinate;
import ocn.himap.markers.HiPointMarker;
import ocn.himap.markers.HiTextMarker;

public class TestDraw {

	private static List<String> ids = new ArrayList<>();

	private static GraphicObj graphicObj = null;

	/**
	 * 测试绘制文本标注
	 * 
	 * @param mActivity
	 */
	public static void testDrawText(Context mActivity) {
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		DataCenter dataCenter = new DataCenterImpl(mActivity, projectBDUrl);
		String nodeId = "b346099cc2ff48c7bf7a3f9dc914e464";
		EcNodeEntity nodeEntity = dataCenter.getNodeDetail(nodeId, null, null);
		String colorString = "#ff0000";
		double[] coords = CoordsUtils.getInstance().geom2Coord(nodeEntity.getGeom());
		graphicObj = GraphicsDrawUtils.getInstance().drawTextMarker(nodeEntity, nodeEntity.getOid(), coords,
				nodeEntity.getMarkerNo(), colorString);
		MapCache.map.refresh();
		MapCache.map.smoothMove(coords[0], coords[1]);

	}

	public static void clear() {
		for (int i = 0; i < ids.size(); i++) {
			GraphicsDrawUtils.getInstance().removePoint(ids.get(i));
		}
		MapCache.map.refresh();

	}

	public static void changeTextMarkerName() {
		HiTextMarker hiTextMarker = (HiTextMarker) graphicObj.getGraphicObj();
		hiTextMarker.Text = "123";
		MapCache.map.refresh();
	}

	/**
	 * 测试线路绘制
	 * 
	 * @param mActivity
	 */
	public static void testDrawLine(Activity mActivity) {
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		DataCenter dataCenter = new DataCenterImpl(mActivity, projectBDUrl);
		String lineId = "5dcb555eb3c44aeebc4d33f87b3399c0";
		LineDetailsEntity lineDetailsEntity = dataCenter.getLineDetails(lineId, null, true, null);
		EcLineEntity ecLineEntity = lineDetailsEntity.getData();
		List<EcNodeEntity> nodes = lineDetailsEntity.getNodes();

		ArrayList<HiCoordinate> coordArr = new ArrayList<HiCoordinate>();
		double[] cxy = new double[2];
		for (int i = 0; i < nodes.size(); i++) {
			EcNodeEntity ecNodeEntity = nodes.get(i);
			String geomStr = ecNodeEntity.getGeom();
			double[] xy = CoordsUtils.getInstance().geom2Coord(geomStr);
			HiCoordinate hiCoordinate = new HiCoordinate(xy[0], xy[1]);
			coordArr.add(hiCoordinate);
			if (i == 0) {
				cxy = xy;
			}
		}
		GraphicObj graphicObj = GraphicsDrawUtils.getInstance().drawLine(ecLineEntity, lineId, coordArr, "#ff0000",
				"#00ff00", "name");
		GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
		// MapCache.map.smoothMove(cxy[0], cxy[1]);
		MapCache.map.refresh();
	}

	private void testDraw(Context mActivity, String projectBDUrl, DataCenter dataCenter) {
		BasicInfoBll basicInfoBll = new BasicInfoBll(mActivity, projectBDUrl);
		// 画点
		List<EcNodeEntity> ecnodeList = basicInfoBll.findAll(EcNodeEntity.class);
		Bitmap bitmapStyle1 = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.biaoshiqiu_n);
		Bitmap bitmapStyle2 = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.biaoshiqiu_s);
		final Gson gson = GsonUtils.build();
		final Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		for (int i = 0; i < ecnodeList.size(); i++) {
			EcNodeEntity nodeEntity = ecnodeList.get(i);
			String geomStr = nodeEntity.getGeom();
			pointGeometry pointGeometry = gson.fromJson(geomStr, pointGeometryType);
			GraphicObj graphicObj = GraphicsDrawUtils.getInstance().drawPointMarker(nodeEntity, nodeEntity.getOid(),
					pointGeometry.getCoordinates(), bitmapStyle1, bitmapStyle2, nodeEntity.getOid(), "title");
			System.out.println(graphicObj.getObjId());
			if (i == (ecnodeList.size() - 1)) {
				GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
			}
		}

		final String lineId = "4924ea7a5d084b4aa0ab61df73e0474f";
		final EcLineEntity ecLineEntity = basicInfoBll.findById(EcLineEntity.class, lineId);
		// 画线
		dataCenter.getLineNodeList(lineId, null, new CallBack<List<EcNodeEntity>>() {

			@Override
			public void call(List<EcNodeEntity> data) {
				// TODO Auto-generated method stub
				ArrayList<HiCoordinate> coordArr = new ArrayList<HiCoordinate>();
				for (int i = 0; i < data.size(); i++) {
					EcNodeEntity ecNodeEntity = data.get(i);
					String geomStr = ecNodeEntity.getGeom();
					pointGeometry pointGeometry = gson.fromJson(geomStr, pointGeometryType);
					HiCoordinate hiCoordinate = new HiCoordinate(pointGeometry.getX(), pointGeometry.getY());
					coordArr.add(hiCoordinate);
				}
				GraphicObj graphicObj = GraphicsDrawUtils.getInstance().drawLine(ecLineEntity, lineId, coordArr,
						"#ff0000", "#00ff00", "name");
				GraphicsDrawUtils.getInstance().flashFeature(graphicObj.getGraphicObj());
			}
		});

		MapCache.map.refresh();
		// MapCache.map.smoothMove(pointGeometry.getX(), pointGeometry.getY());
	}

}
