package com.winway.android.edcollection.export.entity;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年1月30日 下午1:53:49
* Excel截面
*/
public class ExeclEntity4Section {
	@CellColum(headerName = "截面主键", index = 0)
	private String sectionid;
	@CellColum(headerName = "通道标识", index = 1, lenth = 12)
	private String channelid;
	@CellColum(headerName = "标识器编号", index = 2, lenth = 12)
	private String markerno;
	@CellColum(headerName = "截面名称", index = 3, lenth = 12)
	private String jmname;
	@CellColum(headerName = "截面角度", index = 4, lenth = 8)
	private double jmdegress;
	@CellColum(headerName = "截面宽度", index = 5, lenth = 8)
	private double jmwidth;
	@CellColum(headerName = "截面高度", index = 6, lenth = 8)
	private double jmheight;
	@CellColum(headerName = "截面逻辑", index = 7)
	private String jmjson;
	@CellColum(headerName = "截面图片", index = 8, lenth = 20)
	private String jmimage;
	@CellColum(headerName = "备注", index = 9)
	private String remark;

	public String getSectionid() {
		return sectionid;
	}

	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getMarkerno() {
		return markerno;
	}

	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}

	public String getJmname() {
		return jmname;
	}

	public void setJmname(String jmname) {
		this.jmname = jmname;
	}

	public double getJmdegress() {
		return jmdegress;
	}

	public void setJmdegress(double jmdegress) {
		this.jmdegress = jmdegress;
	}

	public double getJmwidth() {
		return jmwidth;
	}

	public void setJmwidth(double jmwidth) {
		this.jmwidth = jmwidth;
	}

	public double getJmheight() {
		return jmheight;
	}

	public void setJmheight(double jmheight) {
		this.jmheight = jmheight;
	}

	public String getJmjson() {
		return jmjson;
	}

	public void setJmjson(String jmjson) {
		this.jmjson = jmjson;
	}

	public String getJmimage() {
		return jmimage;
	}

	public void setJmimage(String jmimage) {
		this.jmimage = jmimage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
