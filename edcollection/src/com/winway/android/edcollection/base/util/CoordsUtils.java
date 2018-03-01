package com.winway.android.edcollection.base.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winway.android.edcollection.base.entity.pointGeometry;
import com.winway.android.edcollection.map.SpatialAnalysis;
import com.winway.android.util.GsonUtils;

import ocn.himap.datamanager.HiDataManager;

/**
 * 坐标处理
 * 
 * @author zgq
 *
 */
public class CoordsUtils {
	private static CoordsUtils instance;

	public static CoordsUtils getInstance() {
		if (instance == null) {
			synchronized (CoordsUtils.class) {
				if (instance == null) {
					instance = new CoordsUtils();
				}
			}
		}
		return instance;
	}

	/**
	 * 坐标转geom
	 * 
	 * @param xy
	 * @return
	 */
	public String coord2Geom(double[] xy) {
		if (xy == null) {
			return null;
		}
		pointGeometry pointGeometry = new pointGeometry(xy);
		pointGeometry.setType("Point");
		Gson gson = GsonUtils.build();
		Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		String geom = gson.toJson(pointGeometry, pointGeometryType);
		return geom;
	}

	/**
	 * geom转坐标
	 * 
	 * @param geomStr
	 * @return
	 */
	public double[] geom2Coord(String geomStr) {
		double[] xy = new double[2];
		Gson gson = GsonUtils.build();
		Type pointGeometryType = new TypeToken<pointGeometry>() {
		}.getType();
		pointGeometry pointGeometry = gson.fromJson(geomStr, pointGeometryType);
		xy[0] = pointGeometry.getX();
		xy[1] = pointGeometry.getY();
		return xy;

	}
}
