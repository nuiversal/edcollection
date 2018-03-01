package com.winway.android.edcollection.adding.entity.channelplaning;

import java.util.List;

import com.winway.android.edcollection.adding.customview.Point;

/**
 * 截面实体
 * @author mr-lao
 *
 */
public class JMEntity {
	// 标识一个唯一的截面
	private String uuid;
	// 截面画板宽度
	private int dwawViewWidth;
	// 截面画板高度
	private int drawViewHeight;
	// 截面点集合（管孔）
	private List<Point> pointList;
	// 电缆支架
	private List<Point> zjList;
	// 截面名称
	private String name;
	// 截面背景图
	private String backgroundImage;
	// 截面角度
	private float degress;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getDwawViewWidth() {
		return dwawViewWidth;
	}

	public void setDwawViewWidth(int dwawViewWidth) {
		this.dwawViewWidth = dwawViewWidth;
	}

	public int getDrawViewHeight() {
		return drawViewHeight;
	}

	public void setDrawViewHeight(int drawViewHeight) {
		this.drawViewHeight = drawViewHeight;
	}

	public List<Point> getPointList() {
		return pointList;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}

	public List<Point> getZjList() {
		return zjList;
	}

	public void setZjList(List<Point> zjList) {
		this.zjList = zjList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public float getDegress() {
		return degress;
	}

	public void setDegress(float degress) {
		this.degress = degress;
	}

}
