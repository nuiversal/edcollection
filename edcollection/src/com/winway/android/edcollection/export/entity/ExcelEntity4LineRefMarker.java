package com.winway.android.edcollection.export.entity;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年1月30日 上午9:32:12
* 线路与节点关联
*/
public class ExcelEntity4LineRefMarker {
	@CellColum(headerName = "线路编号", index = 0, lenth = 16)
	private String lineno;
	@CellColum(headerName = "标识器编号", index = 1, lenth = 16)
	private String markerno;
	@CellColum(headerName = "节点顺序", index = 2, lenth = 5)
	private int index;
	@CellColum(headerName = "上一节点距离", index = 3)
	private String upSpace;
	@CellColum(headerName = "上一节点敷设方式", index = 4)
	private String upLay;
	@CellColum(headerName = "备注", index = 5)
	private String remark;

	public ExcelEntity4LineRefMarker() {
		super();
	}

	public ExcelEntity4LineRefMarker(String lineno, String markerno, int index, String upSpace, String upLay,
			String remark) {
		super();
		this.lineno = lineno;
		this.markerno = markerno;
		this.index = index;
		this.upSpace = upSpace;
		this.upLay = upLay;
		this.remark = remark;
	}

	public String getUpSpace() {
		return upSpace;
	}

	public void setUpSpace(String upSpace) {
		this.upSpace = upSpace;
	}

	public String getUpLay() {
		return upLay;
	}

	public void setUpLay(String upLay) {
		this.upLay = upLay;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLineno() {
		return lineno;
	}

	public void setLineno(String lineno) {
		this.lineno = lineno;
	}

	public String getMarkerno() {
		return markerno;
	}

	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
