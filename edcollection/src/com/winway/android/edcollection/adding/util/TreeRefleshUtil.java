package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.edp.OrgResult;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.util.ClonesUtil;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.ListUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;

/**
 * 台账树数据刷新工具类
 * 作用：当台账树构建完成之后，台账树的数据就很少会更新，而且更新台账树数据的操作比较复杂，代码也比较多。工具类的作用是使更新台账树数据变得更加简单。
 * @author mr-lao
 *
 */
public class TreeRefleshUtil {
	private static TreeRefleshUtil util;

	/**
	 * 单例台账树刷新工具（若是一个工程里面只棵一个台账的话，建议使用此方法 ）
	 * 方法的三个参数，如果单例还未创建，则三个参数的值全部不能为空。若是单例已经创建，则三个参数全部可以为空，也建议全部参数为空
	 * @param treeView
	 * @param dataCenter
	 * @param treeNodeClickUtil
	 * @return
	 */
	public static TreeRefleshUtil getSingleton(AndroidTreeView treeView, DataCenter dataCenter,
			TreeMapUtil treeNodeClickUtil) {
		if (null == util) {
			util = new TreeRefleshUtil(treeView, dataCenter, treeNodeClickUtil);
		}
		return util;
	}

	/**
	 * 单例台账树刷新工具（若是一个工程里面只棵一个台账的话，建议使用此方法 ）
	 * 使用此方法，前提是确保间例已经创建
	 * @return
	 */
	public static TreeRefleshUtil getSingleton() {
		return util;
	}

	private AndroidTreeView treeView;
	private DataCenterImpl dataCenter;
	private TreeMapUtil treeMapUtil;

	public TreeRefleshUtil(AndroidTreeView treeView, DataCenter dataCenter,
			TreeMapUtil treeNodeClickUtil) {
		this.treeView = treeView;
		this.dataCenter = (DataCenterImpl) dataCenter;
		this.treeMapUtil = treeNodeClickUtil;
	}

	private DialogUtil dialogUtil = null;

	public void reflesh(final TreeNode node, final ItemEntity item) {
		final Object objData = item.getObjData();
		if (isShouldReturn(node, item)) {
			return;
		}
		dialogUtil = new DialogUtil((Activity) treeView.getmContext());
		String title = "是否重新加载" + item.getText() + "数据？";
		dialogUtil.showAlertDialog(title, new String[] { "取消", "加载" }, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogUtil.dismissDialog();
				if (which == 0) {
					return;
				}
				removeChildTreeNode(node);
				if (objData instanceof OrgResult) {// 刷新机构所有变电站
					List<EcSubstationEntity> subList = DataCenterImpl.dataCacheCenter
							.get("getSubStationsList_" + ((OrgResult) objData).getOrgid());
					for (EcSubstationEntity ecSubstationEntity : subList) {
						treeMapUtil.removeStation(ecSubstationEntity.getEcSubstationId());
					}
					// 清除变电站列表缓存
					DataCenterImpl.dataCacheCenter
							.remove("getSubStationsList_" + ((OrgResult) objData).getOrgid());
					return;
				}
				if (objData instanceof EcSubstationEntity) {// 变电站所有线路
					List<EcLineEntity> lineList = DataCenterImpl.dataCacheCenter
							.get("getSubStationLineList_"
									+ ((EcSubstationEntity) objData).getEcSubstationId());
					for (EcLineEntity ecLineEntity : lineList) {
						treeMapUtil.removeLine(ecLineEntity.getEcLineId());
					}
					// 清除线路列表缓存
					DataCenterImpl.dataCacheCenter.remove("getSubStationLineList_"
							+ ((EcSubstationEntity) objData).getEcSubstationId());
					return;
				}
				if (objData instanceof EcLineEntity) {// 刷新线路
					EcLineEntity line = (EcLineEntity) objData;
					if ("#BSQ#".equals(item.getUrl())) {
						// 标识器
						refleshLineNodes(line);
						return;
					}
					if ("#TD#".equals(item.getUrl())) {
						// 通道
						refleshLineChannel(line);
						return;
					}
					if ("#LABLE#".equals(item.getUrl())) {
						// 标签
						refleshLineLabel(line);
						return;
					} else if ("#MIDDLE#".equals(item.getUrl())) {
						// 中间头
						refleshLineMiddle(line);
					} else if ("#WELL#".equals(item.getUrl())) {
						// 工井
						refleshLineWell(line);
					} else if ("#FJX#".equals(item.getUrl())) {
						// 分接箱
						refleshLineBox(line);
					} else if ("#TOWER#".equals(item.getUrl())) {
						// 杆塔
						refleshLineTower(line);
					} else if ("#ROOM#".equals(item.getUrl())) {
						// 配电房
						refleshLineRoom(line);
					} else if ("#TRANSFORMER#".equals(item.getUrl())) {
						// 变压器
						refleshLineTransformer(line);
					} else if ("#HWG#".equals(item.getUrl())) {
						// 环网柜
						refleshLineHWG(line);
					} else if ("#XSBDZ#".equals(item.getUrl())) {
						// 箱式变电站
						refleshLineXSBDZ(line);
					} else if ("#DLFZX#".equals(item.getUrl())) {
						// 电缆分接箱
						refleshLineDLFZX(line);
					} else if ("#DYDLFZX#".equals(item.getUrl())) {
						// 低压电缆分接箱
						refleshLineDYDLFZX(line);
					} else if ("#DYPDX#".equals(item.getUrl())) {
						// 低压配电箱
						refleshLineDYPDX(line);
					} else if ("#KGZ#".equals(item.getUrl())) {
						// 开关站
						refleshLineKGZ(line);
					}
				}
			}

			private void refleshLineChannel(EcLineEntity line) {
				List<ChannelDataEntity> channelList = DataCenterImpl.dataCacheCenter
						.get("getChannelListByLineID_" + line.getEcLineId());
				if (null == channelList) {
					return;
				}
				for (ChannelDataEntity channel : channelList) {
					treeMapUtil.removeChannel(channel.getData().getEcChannelId());
				}
				DataCenterImpl.dataCacheCenter
						.remove("getChannelListByLineID_" + line.getEcLineId());
			}

			private void refleshLineKGZ(EcLineEntity line) {
				List<EcNodeEntity> nodeList = DataCenterImpl.dataCacheCenter
						.get("getLineNodeList_" + line.getEcLineId());
				if (nodeList == null) {
					return;
				}
				if (null != nodeList) {
					for (EcNodeEntity ecnode : nodeList) {
						// 移除地图
						treeMapUtil.removeKgz(ecnode.getOid(), line.getName());
						// 清理缓存
						DataCenterImpl.dataCacheCenter.remove("getNodeKgzList_" + ecnode.getOid());
					}
				}
				// 清除线路节点缓存
				refleshLineNodes(line);
			}

			private void refleshLineDYPDX(EcLineEntity line) {
				List<EcNodeEntity> nodeList = DataCenterImpl.dataCacheCenter
						.get("getLineNodeList_" + line.getEcLineId());
				if (nodeList == null) {
					return;
				}
				if (null != nodeList) {
					for (EcNodeEntity ecnode : nodeList) {
						// 移除地图
						treeMapUtil.removeDypdx(ecnode.getOid(), line.getName());
						// 清理缓存
						DataCenterImpl.dataCacheCenter
								.remove("getNodeDypdxList_" + ecnode.getOid());
					}
				}
				// 清除线路节点缓存
				refleshLineNodes(line);
			}

			private void refleshLineDYDLFZX(EcLineEntity line) {
				List<DeviceEntity<EcDydlfzxEntityVo>> list = DataCenterImpl.dataCacheCenter
						.get("getDydlfzxList_" + line.getEcLineId());
				if (null == list || list.isEmpty()) {
					return;
				}
				for (DeviceEntity<EcDydlfzxEntityVo> deviceEntity : list) {
					treeMapUtil.removeDydlfzx(deviceEntity.getNode().getOid(), line.getName());
				}
				DataCenterImpl.dataCacheCenter.remove("getDydlfzxList_" + line.getEcLineId());
			}

			private void refleshLineDLFZX(EcLineEntity line) {
				List<DeviceEntity<EcDlfzxEntityVo>> list = DataCenterImpl.dataCacheCenter
						.get("getDlfzxList_" + line.getEcLineId());
				if (null == list || list.isEmpty()) {
					return;
				}
				for (DeviceEntity<EcDlfzxEntityVo> deviceEntity : list) {
					treeMapUtil.removeDlfzx(deviceEntity.getNode().getOid(), line.getName());
				}
				DataCenterImpl.dataCacheCenter.remove("getDlfzxList_" + line.getEcLineId());
			}

			private void refleshLineXSBDZ(EcLineEntity line) {
				List<EcNodeEntity> nodeList = DataCenterImpl.dataCacheCenter
						.get("getLineNodeList_" + line.getEcLineId());
				if (nodeList == null) {
					return;
				}
				if (null != nodeList) {
					for (EcNodeEntity ecnode : nodeList) {
						// 移除地图
						treeMapUtil.removeXsbdz(ecnode.getOid(), line.getName());
						// 清理缓存
						DataCenterImpl.dataCacheCenter
								.remove("getNodeXsbdzList_" + ecnode.getOid());
					}
				}
				// 清除线路节点缓存
				refleshLineNodes(line);
			}

			private void refleshLineHWG(EcLineEntity line) {
				List<DeviceEntity<EcHwgEntityVo>> list = DataCenterImpl.dataCacheCenter
						.get("getHwgList_" + line.getEcLineId());
				if (null == list || list.isEmpty()) {
					return;
				}
				for (DeviceEntity<EcHwgEntityVo> deviceEntity : list) {
					treeMapUtil.removeHwg(deviceEntity.getNode().getOid(), line.getName());
				}
				DataCenterImpl.dataCacheCenter.remove("getHwgList_" + line.getEcLineId());
			}

			private void refleshLineTransformer(EcLineEntity line) {
				List<EcNodeEntity> nodeList = DataCenterImpl.dataCacheCenter
						.get("getLineNodeList_" + line.getEcLineId());
				if (nodeList == null) {
					return;
				}
				if (null != nodeList) {
					for (EcNodeEntity ecnode : nodeList) {
						// 移除地图
						treeMapUtil.removeTrans(ecnode.getOid(), line.getName());
						// 清理缓存
						DataCenterImpl.dataCacheCenter
								.remove("getNodedTranformerList_" + ecnode.getOid());
					}
				}
				// 清除线路节点缓存
				refleshLineNodes(line);
			}

			private void refleshLineRoom(EcLineEntity line) {
				List<EcNodeEntity> nodeList = DataCenterImpl.dataCacheCenter
						.get("getLineNodeList_" + line.getEcLineId());
				if (nodeList == null) {
					return;
				}
				if (null != nodeList) {
					for (EcNodeEntity ecnode : nodeList) {
						// 移除地图
						treeMapUtil.removeRoom(ecnode.getOid(), line.getName());
						// 清理缓存
						DataCenterImpl.dataCacheCenter
								.remove("getNodeDistributionRoomList_" + ecnode.getOid());
					}
				}
				// 清除线路节点缓存
				refleshLineNodes(line);
			}

			private void refleshLineTower(EcLineEntity line) {
				List<EcNodeEntity> nodeList = DataCenterImpl.dataCacheCenter
						.get("getLineNodeList_" + line.getEcLineId());
				if (nodeList == null) {
					return;
				}
				if (null != nodeList) {
					for (EcNodeEntity ecnode : nodeList) {
						// 移除地图
						treeMapUtil.removeTower(ecnode.getOid(), line.getName());
						// 清理缓存
						DataCenterImpl.dataCacheCenter
								.remove("getNodeTowerList_" + ecnode.getOid());
					}
				}
				// 清除线路节点缓存
				refleshLineNodes(line);
			}

			private void refleshLineBox(EcLineEntity line) {
				List<DeviceEntity<EcDistBoxEntityVo>> distBoxList = dataCenter
						.getLineDistBoxList(line.getEcLineId(), null, null);
				for (DeviceEntity<EcDistBoxEntityVo> box : distBoxList) {
					// 移除地图
					treeMapUtil.removeBox(box.getNode().getOid(), line.getName());
				}
				// 清理缓存
				DataCenterImpl.dataCacheCenter.remove("getLineDistBoxList_" + line.getEcLineId());
			}

			private void refleshLineWell(EcLineEntity line) {
				List<EcNodeEntity> nodeList = DataCenterImpl.dataCacheCenter
						.get("getLineNodeList_" + line.getEcLineId());
				if (nodeList == null) {
					return;
				}
				if (null != nodeList) {
					for (EcNodeEntity ecnode : nodeList) {
						// 移除地图
						treeMapUtil.removeWell(ecnode.getOid(), line.getName());
						// 清理缓存
						DataCenterImpl.dataCacheCenter.remove("getNodeWellList_" + ecnode.getOid());
					}
				}
				// 清除线路节点缓存
				refleshLineNodes(line);
			}

			private void refleshLineMiddle(EcLineEntity line) {
				List<DeviceEntity<EcMiddleJointEntityVo>> middleList = dataCenter
						.getLineMiddleList(line.getEcLineId(), null, null);
				for (DeviceEntity<EcMiddleJointEntityVo> middle : middleList) {
					treeMapUtil.removeMiddle(middle.getNode().getOid(), line.getName());
				}
				// 清理缓存
				DataCenterImpl.dataCacheCenter.remove("getLineMiddleList_" + line.getEcLineId());
			}

			private void refleshLineLabel(EcLineEntity line) {
				List<DeviceEntity<EcLineLabelEntity>> labelList = DataCenterImpl.dataCacheCenter
						.get("getLineLabelList_" + line.getEcLineId());
				for (DeviceEntity<EcLineLabelEntity> deviceEntity : labelList) {
					treeMapUtil.removeLabel(deviceEntity.getNode().getOid(), line.getName());
				}
				// 清除标签数据缓存
				DataCenterImpl.dataCacheCenter.remove("getLineLabelList_" + line.getEcLineId());
			}

			private void refleshLineNodes(EcLineEntity line) {
				List<EcNodeEntity> lineNodeList = DataCenterImpl.dataCacheCenter
						.get("getLineNodeList_" + line.getEcLineId());
				if (null == lineNodeList) {
					return;
				}
				for (EcNodeEntity ecNodeEntity : lineNodeList) {
					// 移除节点设备缓存
					DataCenterImpl.dataCacheCenter
							.remove("getPathNodeDetails_" + ecNodeEntity.getOid());
					// 移除所有已经在地图上画好的节点
					treeMapUtil.removeNode(ecNodeEntity.getOid(), line.getEcLineId());
				}
				// 清除线路节点缓存
				DataCenterImpl.dataCacheCenter.remove("getLineNodeList_" + line.getEcLineId());
			}
		}, false);
	}

	void removeChildTreeNode(TreeNode node) {
		// 移除节点
		List<TreeNode> childrens = new ArrayList<TreeNode>();
		ListUtil.copyList(childrens, node.getChildren());
		for (TreeNode chil : childrens) {
			treeView.removeNode(chil);
		}
		treeView.collapseNode(node);
	}

	/**在弹出对话框之前执行的拦截方法，目的是让对话框按照数据的不同而显示或隐藏*/
	boolean isShouldReturn(TreeNode node, ItemEntity item) {
		Object objData = item.getObjData();
		if (objData == null) {
			return true;
		}
		if (objData instanceof EcLineEntity && TextUtils.isEmpty(item.getUrl())) {
			return true;
		}
		if (node.getChildren() == null || node.getChildren().isEmpty()) {
			if (!(objData instanceof EcLineEntity)) {
				return true;
			}
			if ("#BSQ#".equals(item.getUrl())) {
				return true;
			}
			if ("#LABLE#".equals(item.getUrl())) {
				return true;
			}
			if ("#MIDDLE#".equals(item.getUrl())) {
				return true;
			}
			if ("#FJX#".equals(item.getUrl())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 刷新台账树中的节点
	 * @param updatenode
	 */
	public void refleshEcNode(EcNodeEntity updatenode) {
		String prekey = "getLineNodeList_";
		Set<String> keySet = DataCenterImpl.dataCacheCenter.keySet();
		if (null == keySet || keySet.isEmpty()) {
			return;
		}
		for (String key : keySet) {
			if (!key.startsWith(prekey)) {
				continue;
			}
			List<EcNodeEntity> nodeList = DataCenterImpl.dataCacheCenter.get(key);
			if (null == nodeList || nodeList.isEmpty()) {
				continue;
			}
			for (EcNodeEntity ecnode : nodeList) {
				if (updatenode.getOid().equals(ecnode.getOid())) {
					try {
						ClonesUtil.clones(ecnode, updatenode);
					} catch (Exception e) {

					}
				}
			}
		}
	}

	/**
	 * 刷新整棵台账树
	 */
	public void refleshWholeTree() {
		treeView.collapseAll();
		if (TreeDataUtli.orgTreeNodeList.isEmpty()) {
			return;
		}
		for (TreeNode treeNode : TreeDataUtli.orgTreeNodeList) {
			removeChildTreeNode(treeNode);
		}
		// 清除缓存
		DataCenterImpl.dataCacheCenter.clear();
		// 清除地图
		treeMapUtil.clearMapOverlays();
	}

}
