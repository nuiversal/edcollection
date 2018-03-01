package com.winway.android.edcollection.adding.entity.channelplaning;

import java.util.List;

/**
 * 通道截面模型
 * @author mr-lao
 *
 */
public class ChannelPlaningEntity {
	// 通道ID
	private String channelId;
	// 截面集合
	private List<JMEntity> jmList;
	// 全景背景图
	private String backgroundVrImage;
	// 全景位置图
	private String positionVrImage;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public List<JMEntity> getJmList() {
		return jmList;
	}

	public void setJmList(List<JMEntity> jmList) {
		this.jmList = jmList;
	}

	public String getBackgroundVrImage() {
		return backgroundVrImage;
	}

	public void setBackgroundVrImage(String backgroundVrImage) {
		this.backgroundVrImage = backgroundVrImage;
	}

	public String getPositionVrImage() {
		return positionVrImage;
	}

	public void setPositionVrImage(String positionVrImage) {
		this.positionVrImage = positionVrImage;
	}

}
