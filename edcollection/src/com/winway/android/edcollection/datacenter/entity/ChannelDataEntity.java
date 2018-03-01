package com.winway.android.edcollection.datacenter.entity;

import java.util.List;

import com.winway.android.edcollection.base.entity.IndexEcNodeEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelLinesEntity;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;

/**
 * 通道数据实体
 * @author mr-lao
 * @date 2017-01-09
 */
public class ChannelDataEntity {
	// 通道数据
	private EcChannelEntity data;
	// 起点设备
	private EcNodeEntity startNode;
	// 终点设备
	private EcNodeEntity endNode;
	// 通道关联线路
	private List<EcLineEntity> lines;
	private List<EcChannelLinesEntity> channelLines;
	// 通道关联节点
	private List<IndexEcNodeEntity<EcChannelNodesEntity>> nodes;
	// 通道类型
	private Object channelType;
	// 当前节点（只有通过节点ID来获取通道的时候，此值才会不为null）
	private EcChannelNodesEntity currentChannelNode;

	public EcChannelEntity getData() {
		return data;
	}

	public void setData(EcChannelEntity data) {
		this.data = data;
	}

	public EcNodeEntity getStartNode() {
		return startNode;
	}

	public void setStartNode(EcNodeEntity startNode) {
		this.startNode = startNode;
	}

	public EcNodeEntity getEndNode() {
		return endNode;
	}

	public void setEndNode(EcNodeEntity endNode) {
		this.endNode = endNode;
	}

	public List<EcLineEntity> getLines() {
		return lines;
	}

	public void setLines(List<EcLineEntity> lines) {
		this.lines = lines;
	}

	public List<EcChannelLinesEntity> getChannelLines() {
		return channelLines;
	}

	public void setChannelLines(List<EcChannelLinesEntity> channelLines) {
		this.channelLines = channelLines;
	}

	public List<IndexEcNodeEntity<EcChannelNodesEntity>> getNodes() {
		return nodes;
	}

	public void setNodes(List<IndexEcNodeEntity<EcChannelNodesEntity>> nodes) {
		this.nodes = nodes;
	}

	public Object getChannelType() {
		return channelType;
	}

	public void setChannelType(Object channelType) {
		this.channelType = channelType;
	}

	public EcChannelNodesEntity getCurrentChannelNode() {
		return currentChannelNode;
	}

	public void setCurrentChannelNode(EcChannelNodesEntity currentChannelNode) {
		this.currentChannelNode = currentChannelNode;
	}

}
