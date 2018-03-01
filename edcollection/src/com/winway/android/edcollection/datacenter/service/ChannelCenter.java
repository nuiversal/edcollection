package com.winway.android.edcollection.datacenter.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.entity.IndexEcNodeEntity;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.util.ResourceDeviceUtil;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.loader.LoaderConfig;
import com.winway.android.edcollection.datacenter.util.DataDealUtil;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
import com.winway.android.edcollection.project.entity.EcChannelLinesEntity;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcChannelSectionEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;

import android.content.Context;
import android.text.TextUtils;

/**
 * 通道信息中心
 * @author mr-lao
 *
 */
public class ChannelCenter {
	private BaseDBUtil dbUtil;
	private NodeInfoCenter nodeInfoCenter;

	public ChannelCenter(Context context, String dbUrl) {
		super();

		if (dbUrl == null) {
			dbUrl = LoaderConfig.dbUrl;
		}
		dbUtil = new BaseDBUtil(context, new File(dbUrl));
		nodeInfoCenter = new NodeInfoCenter(dbUtil);
	}

	public ChannelCenter(BaseDBUtil dbUtil) {
		super();
		this.dbUtil = dbUtil;
		nodeInfoCenter = new NodeInfoCenter(dbUtil);
	}

	public ChannelCenter(BaseDBUtil dbUtil, NodeInfoCenter nodeInfoCenter) {
		super();
		this.dbUtil = dbUtil;
		this.nodeInfoCenter = nodeInfoCenter;
	}

	private EcNodeEntity getEcNodeEntityByMarkerNo(String markerno) throws DbException {
		if (TextUtils.isEmpty(markerno)) {
			return null;
		}
		EcNodeEntity queryEntity = new EcNodeEntity();
		queryEntity.setMarkerNo(markerno);
		List<EcNodeEntity> queryList = dbUtil.excuteQuery(EcNodeEntity.class, queryEntity);
		if (null == queryList || queryList.isEmpty()) {
			return null;
		}
		return queryList.get(0);
	}

	/**
	 * 1、获取通道
	 * 
	 * @param channelId
	 * @return
	 */
	public ChannelDataEntity getChannel(String channelId) {
		try {
			// 通道实体
			ChannelDataEntity channelData = new ChannelDataEntity();
			EcChannelEntity channel = dbUtil.getById(EcChannelEntity.class, channelId);
			if(channel!=null){
				
				channelData.setData(channel);
				// 起点
				EcNodeEntity startNode = dbUtil.getById(EcNodeEntity.class, channel.getStartDeviceNum());
				channelData.setStartNode(startNode);
				// 终点
				EcNodeEntity endNode = dbUtil.getById(EcNodeEntity.class, channel.getEndDeviceNum());
				channelData.setEndNode(endNode);
				// 线路
				ArrayList<EcLineEntity> lines = new ArrayList<EcLineEntity>();
				EcChannelLinesEntity queryChannelLines = new EcChannelLinesEntity();
				queryChannelLines.setEcChannelId(channelId);
				List<EcChannelLinesEntity> channelLinesList = dbUtil.excuteQuery(EcChannelLinesEntity.class,
						queryChannelLines);
				if (null != channelLinesList && !channelLinesList.isEmpty()) {
					for (EcChannelLinesEntity channelLines : channelLinesList) {
						// //中山图形化app中EcLineEntity的lineId对应的是EcChannelLinesEntity中的lineNo
						EcLineEntity queryLine = dbUtil.getById(EcLineEntity.class, channelLines.getLineId());
						lines.add(queryLine);
						// 管控app中EcLineEntity的lineNo对应的是EcChannelLinesEntity中的lineNo
						if (queryLine == null) {
							queryLine = new EcLineEntity();
							queryLine.setLineNo(channelLines.getLineId());
							List<EcLineEntity> lineList = dbUtil.excuteQuery(EcLineEntity.class, queryLine);
							if (null != lineList && !lineList.isEmpty()) {
								lines.add(lineList.get(0));
							}
						}
					}
				}
				channelData.setChannelLines(channelLinesList);
				channelData.setLines(lines);
				// 路径点
				ArrayList<IndexEcNodeEntity<EcChannelNodesEntity>> nodes = new ArrayList<IndexEcNodeEntity<EcChannelNodesEntity>>();
				EcChannelNodesEntity queryChannelNode = new EcChannelNodesEntity();
				queryChannelNode.setEcChannelId(channelId);
				List<EcChannelNodesEntity> channelNodesList = dbUtil.excuteQuery(EcChannelNodesEntity.class,
						queryChannelNode);
				if (null != channelNodesList && !channelNodesList.isEmpty()) {
					for (EcChannelNodesEntity channelNode : channelNodesList) {
						nodes.add(new IndexEcNodeEntity<EcChannelNodesEntity>(channelNode,
								dbUtil.getById(EcNodeEntity.class, channelNode.getOid())));
					}
				}
				// 对节点进行排序
				DataDealUtil.sortChannelNodes(nodes);
				Object deviceObj = ResourceDeviceUtil.getDeviceObj(channelId, channel.getChannelType(), dbUtil);
				if (null == deviceObj) {
					String type = channel.getChannelType();
					if (type.equals(ResourceEnum.TLG.getValue())) {
						// 电缆拖拉管/顶管
						EcChannelDgEntity channelType = new EcChannelDgEntity();
						channelType.setObjId(channelId);
						channelData.setChannelType(channelType);
					}
					if (type.equals(ResourceEnum.PG.getValue())) {
						// 电缆管道，即排管
						EcChannelDlgdEntity channelType = new EcChannelDlgdEntity();
						channelType.setObjId(channelId);
						channelData.setChannelType(channelType);
					}
					if (type.equals(ResourceEnum.GD.getValue())) {
						// 电缆沟
						EcChannelDlgEntity channelType = new EcChannelDlgEntity();
						channelType.setObjId(channelId);
						channelData.setChannelType(channelType);
					}
					if (type.equals(ResourceEnum.QJ.getValue())) {
						// 电缆桥
						EcChannelDlqEntity channelType = new EcChannelDlqEntity();
						channelType.setObjId(channelId);
						channelData.setChannelType(channelType);
					}
					if (type.equals(ResourceEnum.SD.getValue())) {
						// 电缆隧道
						EcChannelDlsdEntity channelType = new EcChannelDlsdEntity();
						channelType.setObjId(channelId);
						channelData.setChannelType(channelType);
					}
					if (type.equals(ResourceEnum.ZM.getValue())) {
						// 电缆直埋
						EcChannelDlzmEntity channelType = new EcChannelDlzmEntity();
						channelType.setObjId(channelId);
						channelData.setChannelType(channelType);
					}
					if (type.equals(ResourceEnum.JKXL.getValue())) {
						// 架空线路
						EcChannelJkEntity channelType = new EcChannelJkEntity();
						channelType.setObjId(channelId);
						channelData.setChannelType(channelType);
					}
					if(type.equals(ResourceEnum.DLC.getValue())){
						//电缆槽
						EcChannelDlcEntity channelType = new EcChannelDlcEntity();
						channelType.setObjId(channelId);
						channelData.setChannelType(channelType);
					}
				} else {
					channelData.setChannelType(deviceObj);
				}
				channelData.setNodes(nodes);
				return channelData;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得线路穿过的所有通道
	 * @param lineid
	 * @return
	 */
	public List<ChannelDataEntity> getChannelListByLineID(String lineid) {
		try {
			// 管控app中EcLineEntity的lineNo对应的是EcChannelLinesEntity中的lineNo
			EcLineEntity lineEntity = dbUtil.getById(EcLineEntity.class, lineid);
			if (lineEntity == null) {
				return null;
			}
			// 增强软件数据容错能力，首先通过线路ID值来查找，如果找不着，则通过线路编号来查找
			EcChannelLinesEntity queryChannelLines = new EcChannelLinesEntity();
			// 中山图形化app中EcLineEntity的lineId对应的是EcChannelLinesEntity中的lineNo
			queryChannelLines.setLineId(lineid);
			// 管控app中EcLineEntity的lineNo对应的是EcChannelLinesEntity中的lineNo
			List<EcChannelLinesEntity> channelLineList = dbUtil.excuteQuery(EcChannelLinesEntity.class,
					queryChannelLines);
			if (null == channelLineList | channelLineList.isEmpty()) {
				queryChannelLines.setLineId(lineEntity.getLineNo());
				channelLineList = dbUtil.excuteQuery(EcChannelLinesEntity.class, queryChannelLines);
			}
			if (null == channelLineList || channelLineList.isEmpty()) {
				return null;
			}
			ArrayList<ChannelDataEntity> list = new ArrayList<ChannelDataEntity>();
			for (EcChannelLinesEntity channelLine : channelLineList) {
				ChannelDataEntity channelData = getChannel(channelLine.getEcChannelId());
				if (null == channelData) {
					continue;
				}
				list.add(channelData);
			}
			// 进行从小到大排序
			DataDealUtil.sortLineChannel(list, nodeInfoCenter, lineid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得所有通道
	 * @return
	 */
	public List<ChannelDataEntity> getAllChannel() {
		try {
			List<EcChannelEntity> all = dbUtil.getAll(EcChannelEntity.class);
			if (null == all || all.isEmpty()) {
				return null;
			}
			List<ChannelDataEntity> list = new ArrayList<ChannelDataEntity>();
			for (EcChannelEntity channel : all) {
				ChannelDataEntity channelDataEntity = getChannel(channel.getEcChannelId());
				if (null != channelDataEntity) {
					list.add(channelDataEntity);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取通过路径点的所有通道（一般情况下只会有一个路径点只会关联一条通道，只有在通道交叉点的时候才会关联多条通道）
	 * @param nodeId
	 * @return
	 */
	public List<ChannelDataEntity> getChannelListByNodeID(String nodeId) {
		try {
			EcChannelNodesEntity queryEntity = new EcChannelNodesEntity();
			queryEntity.setOid(nodeId);
			List<EcChannelNodesEntity> queryList = dbUtil.excuteQuery(EcChannelNodesEntity.class, queryEntity);
			if (queryList == null || queryList.isEmpty()) {
				return null;
			}
			ArrayList<ChannelDataEntity> list = new ArrayList<>();
			for (EcChannelNodesEntity channelNode : queryList) {
				ChannelDataEntity channel = getChannel(channelNode.getEcChannelId());
				if (channel != null) {
					channel.setCurrentChannelNode(channelNode);
					list.add(channel);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取通道截面（目前主要是工井会用到）
	 * @param objid
	 * @param resType
	 * @return
	 */
	public EcChannelSectionEntity getEcChannelSectionEntityByObjid(String objid, String resType) {
		EcChannelSectionEntity queryEntity = new EcChannelSectionEntity();
		queryEntity.setObjid(objid);
		queryEntity.setResType(resType);
		try {
			List<EcChannelSectionEntity> list = dbUtil.excuteQuery(EcChannelSectionEntity.class, queryEntity);
			if (null != list && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
}
