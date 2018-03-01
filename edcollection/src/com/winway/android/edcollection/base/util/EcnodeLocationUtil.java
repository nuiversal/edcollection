package com.winway.android.edcollection.base.util;

/**
 * 将EcNodeEntity实体中的geom字符串中的坐标信息提取出来
 * 
 * @author mr-lao
 *
 */
public class EcnodeLocationUtil {
	/**
	 * 经度与纬度
	 * 
	 * @author mr-lao
	 *
	 */
	public static class LongitudeLatitudeEntity {
		public double longitude;
		public double latitude;
	}

	/**
	 * 
	 * @param geom
	 *            {"coordinates":[113.3632613,22.5135934],"type":"Point"}
	 * @return
	 */
	public static LongitudeLatitudeEntity parseGeom(String geom) {
		if (geom == null) {
			return null;
		}
		int begin = geom.indexOf("[") + 1;
		int end = geom.lastIndexOf("]");
		LongitudeLatitudeEntity ll = new LongitudeLatitudeEntity();
		String llstr = geom.substring(begin, end);
		String[] split = llstr.split(",");
		ll.longitude = Double.parseDouble(split[0]);
		ll.latitude = Double.parseDouble(split[1]);
		return ll;
	}

	/**
	 * 生成点坐标信息
	 * 
	 * @param x
	 * @param y
	 * @return {"coordinates":[113.3632613,22.5135934],"type":"Point"}
	 */
	public static String createGeom(double x, double y) {
		return "{\"coordinates\":[" + x + "," + y + "],\"type\":\"Point\"}";
	}

}
