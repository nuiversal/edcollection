package com.winway.android.edcollection.export.entity;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年2月5日 下午5:19:46
* 通道与标识器关联
*/
public class ExcelEntity4ChannelRefMarker {
	@CellColum(headerName = "通道主键", index = 0)
	private String channelid;
	@CellColum(headerName = "标识器编号", index = 1)
	private String markerno;
	@CellColum(headerName = "序号", index = 2)
	private Integer index;

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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
