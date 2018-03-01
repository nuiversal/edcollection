package com.winway.android.edcollection.adding.customview;

public class Point {
	public float x;// 画板x坐标
	public float y;// 画板y坐标
	public float circleR = GJDrawView.POINT_R_MEDIUM;// 画板圆点半径
	public float r;// 管口直径
	private String lineId;// 线路编号
	private String no;// 管线编号
	private Integer isPlugging; // 管孔现状：0表示通、1表示不通、2表示半通
	private String phaseSeq; // 电缆相序
	// 未知线路[UNKNOW-LINE]
	private String lineName;// 线路名称
	private String ringColor;// 环颜色
	private String fillColor;// 填充颜色

	public String getPhaseSeq() {
		return phaseSeq;
	}

	public void setPhaseSeq(String phaseSeq) {
		this.phaseSeq = phaseSeq;
	}

	public String getRingColor() {
		return ringColor;
	}

	public void setRingColor(String ringColor) {
		this.ringColor = ringColor;
	}

	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Point(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.circleR = r;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getR() {
		return r;
	}

	public float getCircleR() {
		return circleR;
	}

	public void setCircleR(float circleR) {
		this.circleR = circleR;
	}

	public void setR(float r) {
		this.r = r;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String msg) {
		this.no = msg;
	}

	public Integer getIsPlugging() {
		return isPlugging;
	}

	public void setIsPlugging(Integer isPlugging) {
		this.isPlugging = isPlugging;
	}

}
