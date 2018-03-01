package com.winway.android.edcollection.adding.dto;

import java.io.Serializable;

/**
 * 采集过程中使用的线路和通道的映射实体
 * 
 * @author zgq
 *
 */
public class LineId2Channel implements Serializable {
	private String lineId;// 线路id
	private String channelType;// 通道类型
	private ChannelDevice channelDevice;// 通道设施数据

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public ChannelDevice getChannelDevice() {
		return channelDevice;
	}

	public void setChannelDevice(ChannelDevice channelDevice) {
		this.channelDevice = channelDevice;
	}

}
