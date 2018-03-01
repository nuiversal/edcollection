package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.customview.DensityUtil;
import com.winway.android.edcollection.adding.entity.LineDeviceType;
import com.winway.android.edcollection.adding.entity.NodeDeviceType;
import com.winway.android.edcollection.base.entity.IndexEcNodeEntity;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenter.CallBack;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.edp.OrgResult;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDypdxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcKgzEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.edcollection.project.vo.EcXsbdzEntityVo;
import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.datacenter.DataLoader;
import com.winway.android.ewidgets.tree.datacenter.DataTranslator;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.entity.LevelEntity;
import com.winway.android.ewidgets.tree.utils.ImageURIUtil;
import com.winway.android.ewidgets.tree.utils.TreeUtil;
import com.winway.android.util.ListUtil;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 台账树数据加载器
 * 
 * 
 * @author mr-lao
 *
 */
public class TreeDataLoder implements DataLoader {
	private DataCenter dataCenter = null;
	Context context;
	String projectUrl;

	private DataTranslator translator;

	@Override
	public void setDataTranslator(DataTranslator translator) {
		this.translator = translator;
	}

	@Override
	public DataTranslator getDataTranslator() {
		return translator;
	}

	public TreeDataLoder(Context context, String projectUrl) {
		dataCenter = new DataCenterImpl(context, projectUrl);
	}

	public TreeDataLoder(DataCenter dataCenter, Context context, String projectUrl) {
		this.dataCenter = dataCenter;
		this.context = context;
		this.projectUrl = projectUrl;
	}

	@Override
	public void loadNode(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		Object objData = item.getObjData();
		if (null == objData) {
			return;
		}
		if (objData instanceof OrgResult) {
			// 加载变电站数据
			loadSubStations(treeView, node, item);
		} else if (objData instanceof EcSubstationEntity) {
			// 加载线路
			loadLines(treeView, node, item);
		} else if (objData instanceof EcLineEntity) {
			// 加载线路下的路径点、敷设段等信息
			if (TextUtils.isEmpty(item.getUrl())) {
				loadLinesNodesAndDevices(treeView, node, item);
				return;
			}
			// 加载设备
			if ("#BSQ#".equals(item.getUrl())) {
				// 路径点（标识器）
				loadLinesNodes(treeView, node, item);
			} else if ("#TD#".equals(item.getUrl())) {
				// 通道
				loadChannel(treeView, node, item);
				// loadChannelTest(treeView, node, item);
			} else if ("#LABLE#".equals(item.getUrl())) {
				// 标签
				loadLineLabels(treeView, node, item);
			} else if ("#MIDDLE#".equals(item.getUrl())) {
				// 加载中间头
				loadLineMiddle(treeView, node, item);
			} else if ("#WELL#".equals(item.getUrl())) {
				// 加载工井
				loadLineWell(treeView, node, item);
			} else if ("#FJX#".equals(item.getUrl())) {
				// 加载分接箱
				loadLineDistBox(treeView, node, item);
			} else if ("#TOWER#".equals(item.getUrl())) {
				// 加载杆塔
				loadLineTower(treeView, node, item);
			} else if ("#ROOM#".equals(item.getUrl())) {
				// 加载配电房
				loadLineRoom(treeView, node, item);
			} else if ("#TRANSFORMER#".equals(item.getUrl())) {
				// 加载变压器
				loadLineTransformer(treeView, node, item);
			} else if ("#HWG#".equals(item.getUrl())) {
				// 环网柜
				loadHWG(treeView, node, item);
			} else if ("#XSBDZ#".equals(item.getUrl())) {
				// 箱式变电站
				loadXSBDZ(treeView, node, item);
			} else if ("#DLFZX#".equals(item.getUrl())) {
				// 电缆分接箱
				loadDLFZX(treeView, node, item);
			} else if ("#DYDLFZX#".equals(item.getUrl())) {
				// 低压电缆分接箱
				loadDYDLFZX(treeView, node, item);
			} else if ("#DYPDX#".equals(item.getUrl())) {
				// 低压配电箱
				loadDYPDX(treeView, node, item);
			} else if ("#KGZ#".equals(item.getUrl())) {
				// 低压配电箱
				loadKGZ(treeView, node, item);
			}
		} else if (objData instanceof DeviceEntity) {
			Object deviceData = ((DeviceEntity<?>) objData).getData();
			if (deviceData instanceof EcWorkWellEntity) {
				// 加载井盖
				loadCovers(treeView, node, item);
			}
		} else if (objData instanceof ChannelDataEntity) {// 通道项节点
			loadChannelNodes(treeView, node, item);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadCovers(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		String wellId = ((DeviceEntity<EcWorkWellEntity>) item.getObjData()).getData().getObjId();
		List<DeviceEntity<EcWorkWellCoverEntity>> coverList = dataCenter.getWellCoverList(null, wellId, true, null);
		if (null == coverList || coverList.isEmpty()) {
			return;
		}
		addTreeNode(treeView, node, coverList);
	}

	/** 加载开关站 */
	private void loadKGZ(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		List<EcNodeEntity> nodeList = getLineNodeList(item);
		ArrayList<DeviceEntity<EcKgzEntityVo>> list = new ArrayList<DeviceEntity<EcKgzEntityVo>>();
		for (EcNodeEntity ecnode : nodeList) {
			List<DeviceEntity<EcKgzEntityVo>> data = dataCenter.getNodeKgzList(ecnode.getOid(), null, null);
			if (null == data || data.isEmpty()) {
				continue;
			}
			ListUtil.copyList(list, data);
		}
		addTreeNode(treeView, node, list);
	}

	private List<EcNodeEntity> getLineNodeList(ItemEntity item) {
		EcLineEntity ecline = (EcLineEntity) item.getObjData();
		DataCenterImpl.dataCacheCenter.remove("getLineNodeList_" + ecline.getEcLineId());
		return dataCenter.getLineNodeList(ecline.getEcLineId(), null, null);
	}

	/** 加载低压配电箱 */
	private void loadDYPDX(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		List<EcNodeEntity> nodeList = getLineNodeList(item);
		ArrayList<DeviceEntity<EcDypdxEntityVo>> list = new ArrayList<DeviceEntity<EcDypdxEntityVo>>();
		for (EcNodeEntity ecnode : nodeList) {
			List<DeviceEntity<EcDypdxEntityVo>> data = dataCenter.getNodeDypdxList(ecnode.getOid(), null, null);
			if (null == data || data.isEmpty()) {
				continue;
			}
			ListUtil.copyList(list, data);
		}
		addTreeNode(treeView, node, list);
	}

	/** 加载低压电缆分支箱 */
	private void loadDYDLFZX(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		EcLineEntity ecline = (EcLineEntity) item.getObjData();
		List<DeviceEntity<EcDydlfzxEntityVo>> list = dataCenter.getDydlfzxList(ecline.getEcLineId(), null, null);
		addTreeNode(treeView, node, list);
	}

	/** 加载电缆分支箱 */
	private void loadDLFZX(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		EcLineEntity ecline = (EcLineEntity) item.getObjData();
		List<DeviceEntity<EcDlfzxEntityVo>> list = dataCenter.getDlfzxList(ecline.getEcLineId(), null, null);
		addTreeNode(treeView, node, list);
	}

	/** 加载箱式变电站 */
	private void loadXSBDZ(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		List<EcNodeEntity> nodeList = getLineNodeList(item);
		ArrayList<DeviceEntity<EcXsbdzEntityVo>> list = new ArrayList<DeviceEntity<EcXsbdzEntityVo>>();
		for (EcNodeEntity ecnode : nodeList) {
			List<DeviceEntity<EcXsbdzEntityVo>> data = dataCenter.getNodeXsbdzList(ecnode.getOid(), null, null);
			if (null == data || data.isEmpty()) {
				continue;
			}
			ListUtil.copyList(list, data);
		}
		addTreeNode(treeView, node, list);
	}

	/** 加载环网柜 */
	private void loadHWG(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		EcLineEntity ecline = (EcLineEntity) item.getObjData();
		List<DeviceEntity<EcHwgEntityVo>> list = dataCenter.getHwgList(ecline.getEcLineId(), null, null);
		addTreeNode(treeView, node, list);
	}

	/** 加载变压器 */
	private void loadLineTransformer(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		List<EcNodeEntity> nodeList = getLineNodeList(item);
		ArrayList<DeviceEntity<EcTransformerEntityVo>> list = new ArrayList<DeviceEntity<EcTransformerEntityVo>>();
		for (EcNodeEntity ecnode : nodeList) {
			List<DeviceEntity<EcTransformerEntityVo>> data = dataCenter.getNodedTranformerList(ecnode.getOid(), null,
					null);
			if (null == data || data.isEmpty()) {
				continue;
			}
			ListUtil.copyList(list, data);
		}
		addTreeNode(treeView, node, list);
	}

	/** 加载配电房 */
	private void loadLineRoom(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		List<EcNodeEntity> nodeList = getLineNodeList(item);
		ArrayList<DeviceEntity<EcDistributionRoomEntityVo>> list = new ArrayList<DeviceEntity<EcDistributionRoomEntityVo>>();
		for (EcNodeEntity ecnode : nodeList) {
			List<DeviceEntity<EcDistributionRoomEntityVo>> data = dataCenter
					.getNodeDistributionRoomList(ecnode.getOid(), null, null);
			if (null == data || data.isEmpty()) {
				continue;
			}
			ListUtil.copyList(list, data);
		}
		addTreeNode(treeView, node, list);
	}

	/** 加载杆塔 */
	private void loadLineTower(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		List<EcNodeEntity> nodeList = getLineNodeList(item);
		ArrayList<DeviceEntity<EcTowerEntityVo>> list = new ArrayList<DeviceEntity<EcTowerEntityVo>>();
		for (EcNodeEntity ecnode : nodeList) {
			List<DeviceEntity<EcTowerEntityVo>> data = dataCenter.getNodeTowerList(ecnode.getOid(), null, null);
			if (null == data || data.isEmpty()) {
				continue;
			}
			ListUtil.copyList(list, data);
		}
		addTreeNode(treeView, node, list);
	}

	/** 加载分接箱 */
	private void loadLineDistBox(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		EcLineEntity ecline = (EcLineEntity) item.getObjData();
		List<DeviceEntity<EcDistBoxEntityVo>> distBoxList = dataCenter.getLineDistBoxList(ecline.getEcLineId(), null,
				null);
		addTreeNode(treeView, node, distBoxList);
	}

	/** 工井 */
	private void loadLineWell(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		// 当前线路所有节点
		List<EcNodeEntity> nodeList = getLineNodeList(item);
		ArrayList<DeviceEntity<EcWorkWellEntityVo>> wellList = new ArrayList<DeviceEntity<EcWorkWellEntityVo>>();
		for (EcNodeEntity ecnode : nodeList) {
			List<DeviceEntity<EcWorkWellEntityVo>> nodeWellList = dataCenter.getNodeWellList(ecnode.getOid(), null,
					null);
			if (null == nodeWellList || nodeWellList.isEmpty()) {
				continue;
			}
			ListUtil.copyList(wellList, nodeWellList);
		}
		addTreeNode(treeView, node, wellList);
	}

	/** 加载中间接头 */
	private void loadLineMiddle(AndroidTreeView treeView, TreeNode node, ItemEntity item) {
		EcLineEntity ecline = (EcLineEntity) item.getObjData();
		// 当前线路所有中间头
		List<DeviceEntity<EcMiddleJointEntityVo>> middleList = dataCenter.getLineMiddleList(ecline.getEcLineId(), null,
				null);
		addTreeNode(treeView, node, middleList);
	}

	private void addTreeNode(AndroidTreeView treeView, TreeNode node, List<?> data) {
		if (null == data || data.isEmpty()) {
			return;
		}
		LevelEntity levelEntity = translator.translateToLevelEntity(data);
		ArrayList<TreeNode> nodeList = TreeUtil.levelToNodeList(levelEntity);
		treeView.addNode(node, nodeList);
	}

	// 加载变电站
	private void loadSubStations(final AndroidTreeView treeView, final TreeNode node, ItemEntity item) {
		String orgId = ((OrgResult) item.getObjData()).getOrgid();
		List<EcSubstationEntity> data = dataCenter.getSubStationsList(orgId, null, null);
		addTreeNode(treeView, node, data);
	}

	// 加载线路
	private void loadLines(final AndroidTreeView treeView, final TreeNode node, ItemEntity item) {
		EcSubstationEntity station = (EcSubstationEntity) item.getObjData();
		dataCenter.getSubStationLineList(station.getEcSubstationId(), "", new CallBack<List<EcLineEntity>>() {
			@Override
			public void call(List<EcLineEntity> data) {
				// 所有线路信息
				List<TreeNode> nodeList = TreeDataUtli.linesToTreeNodeList(data);
				if (nodeList == null) {
					return;
				}
				for (TreeNode treeNode : nodeList) {
					treeView.addNode(node, treeNode);
				}
			}
		});
	}

	// 加载线路下的节点和设备信息等
	private void loadLinesNodesAndDevices(final AndroidTreeView treeView, final TreeNode node, ItemEntity item) {
		EcLineEntity line = (EcLineEntity) item.getObjData();
		// 路径点
		ItemEntity lineNodeItem = new ItemEntity();
		lineNodeItem.setText("路径点");
		lineNodeItem.setHasChild(true);
		lineNodeItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.xnd_n));
		lineNodeItem.setUrl("#BSQ#");
		lineNodeItem.setObjData(line);
		TreeNode n1 = new TreeNode(lineNodeItem);
		treeView.addNode(node, n1);
		// 通道
		ItemEntity fsdItem = new ItemEntity();
		fsdItem.setText("通道");
		fsdItem.setHasChild(true);
		fsdItem.setUrl("#TD#");
		fsdItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.tongdao));
		fsdItem.setObjData(line);
		TreeNode n2 = new TreeNode(fsdItem);
		treeView.addNode(node, n2);
		// 标签
		ItemEntity lableItem = new ItemEntity();
		lableItem.setText(LineDeviceType.DZBQ.getName());
		lableItem.setHasChild(true);
		lableItem.setUrl("#LABLE#");
		lableItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.zhadai_n));
		lableItem.setObjData(line);
		TreeNode n3 = new TreeNode(lableItem);
		treeView.addNode(node, n3);
		// 中间头
		ItemEntity middleItem = new ItemEntity();
		middleItem.setText(LineDeviceType.ZJJT.getName());
		middleItem.setHasChild(true);
		middleItem.setUrl("#MIDDLE#");
		middleItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.jietou_n));
		middleItem.setObjData(line);
		TreeNode n4 = new TreeNode(middleItem);
		treeView.addNode(node, n4);
		/*
		 * // 分接箱 ItemEntity jxxItem = new ItemEntity();
		 * jxxItem.setText(LineDeviceType.FJX.getName());
		 * jxxItem.setHasChild(true); jxxItem.setUrl("#FJX#");
		 * jxxItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.
		 * jiexianxiang_s)); jxxItem.setObjData(line); TreeNode n5 = new
		 * TreeNode(jxxItem); treeView.addNode(node, n5);
		 */
		// 环网柜
		ItemEntity jxxItem = new ItemEntity();
		jxxItem.setText(LineDeviceType.HWG.getName());
		jxxItem.setHasChild(true);
		jxxItem.setUrl("#HWG#");
		jxxItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.huanwanggui_n));
		jxxItem.setObjData(line);
		TreeNode n5 = new TreeNode(jxxItem);
		treeView.addNode(node, n5);
		// 工井
		ItemEntity wellItem = new ItemEntity();
		wellItem.setText(NodeDeviceType.GJ.getName());
		wellItem.setHasChild(true);
		wellItem.setUrl("#WELL#");
		wellItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.gongjing_n));
		wellItem.setObjData(line);
		TreeNode n6 = new TreeNode(wellItem);
		treeView.addNode(node, n6);
		// 杆塔
		ItemEntity towerItem = new ItemEntity();
		towerItem.setText(NodeDeviceType.GT.getName());
		towerItem.setHasChild(true);
		towerItem.setUrl("#TOWER#");
		towerItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.ganta_n));
		towerItem.setObjData(line);
		TreeNode n7 = new TreeNode(towerItem);
		treeView.addNode(node, n7);
		// 配电房
		ItemEntity roomItem = new ItemEntity();
		roomItem.setText(NodeDeviceType.PDF.getName());
		roomItem.setHasChild(true);
		roomItem.setUrl("#ROOM#");
		roomItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.pdpdz));
		roomItem.setObjData(line);
		TreeNode n8 = new TreeNode(roomItem);
		treeView.addNode(node, n8);
		// 变压器
		ItemEntity transformerItem = new ItemEntity();
		transformerItem.setText(NodeDeviceType.BYQ.getName());
		transformerItem.setHasChild(true);
		transformerItem.setUrl("#TRANSFORMER#");
		transformerItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.bianyaqi_n));
		transformerItem.setObjData(line);
		TreeNode n9 = new TreeNode(transformerItem);
		treeView.addNode(node, n9);
		// 箱式变电站
		ItemEntity xsbdzItem = new ItemEntity();
		xsbdzItem.setText(NodeDeviceType.XSBDZ.getName());
		xsbdzItem.setHasChild(true);
		xsbdzItem.setUrl("#XSBDZ#");
		xsbdzItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.pdxb));
		xsbdzItem.setObjData(line);
		TreeNode n10 = new TreeNode(xsbdzItem);
		treeView.addNode(node, n10);
		// 电缆分支箱
		ItemEntity dlfzxItem = new ItemEntity();
		dlfzxItem.setText("电缆分支箱");
		dlfzxItem.setHasChild(true);
		dlfzxItem.setUrl("#DLFZX#");
		dlfzxItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.jiexianxiang_n));
		dlfzxItem.setObjData(line);
		TreeNode n11 = new TreeNode(dlfzxItem);
		treeView.addNode(node, n11);
		// 低压电缆分支箱
		ItemEntity dydlfzxItem = new ItemEntity();
		dydlfzxItem.setText("低压电缆分支箱");
		dydlfzxItem.setHasChild(true);
		dydlfzxItem.setUrl("#DYDLFZX#");
		dydlfzxItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.pdhwkgx));
		dydlfzxItem.setObjData(line);
		TreeNode n12 = new TreeNode(dydlfzxItem);
		treeView.addNode(node, n12);
		// 低压配电箱
		ItemEntity dypdxItem = new ItemEntity();
		dypdxItem.setText(NodeDeviceType.DYPDX.getName());
		dypdxItem.setHasChild(true);
		dypdxItem.setUrl("#DYPDX#");
		dypdxItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.peidianxiang));
		dypdxItem.setObjData(line);
		TreeNode n13 = new TreeNode(dypdxItem);
		treeView.addNode(node, n13);
		// 开关站
		ItemEntity kgzItem = new ItemEntity();
		kgzItem.setText(NodeDeviceType.KGZ.getName());
		kgzItem.setHasChild(true);
		kgzItem.setUrl("#KGZ#");
		kgzItem.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.pdkgf));
		kgzItem.setObjData(line);
		TreeNode n14 = new TreeNode(kgzItem);
		treeView.addNode(node, n14);
	}

	ImageView createImageView(Context context, int drawableID) {
		ImageView iv = new ImageView(context);
		int width = DensityUtil.dip2px(context, 16);
		int height = DensityUtil.dip2px(context, 16);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
		params.rightMargin = DensityUtil.dip2px(context, 4);
		iv.setLayoutParams(params);
		iv.setImageResource(drawableID);
		return iv;
	}

	OfflineAttachCenter offlineAttachCenter = null;

	// 路径点
	private void loadLinesNodes(final AndroidTreeView treeView, final TreeNode node, ItemEntity item) {
		EcLineEntity line = (EcLineEntity) item.getObjData();
		dataCenter.getLineNodeList(line.getEcLineId(), null, new CallBack<List<EcNodeEntity>>() {
			@Override
			public void call(List<EcNodeEntity> nodedata) {
				if (null == nodedata || nodedata.isEmpty()) {
					return;
				}
				addTreeNode(treeView, node, nodedata);
			}
		});
	}

	// 加载通道
	private void loadChannel(final AndroidTreeView treeView, final TreeNode node, ItemEntity item) {
		if (item.getObjData() == null) {
			return;
		}
		EcLineEntity lineEntity = (EcLineEntity) item.getObjData();
		dataCenter.getChannelListByLineID(lineEntity.getEcLineId(), null, new CallBack<List<ChannelDataEntity>>() {
			@Override
			public void call(List<ChannelDataEntity> data) {
				addTreeNode(treeView, node, data);
			}
		});
	}

	/**
	 * 加载通道节点
	 * 
	 * @param treeView
	 * @param node
	 * @param item
	 */
	private void loadChannelNodes(final AndroidTreeView treeView, final TreeNode node, ItemEntity item) {
		if (item.getObjData() == null) {
			return;
		}
		ChannelDataEntity channelDataEntity = (ChannelDataEntity) item.getObjData();
		List<IndexEcNodeEntity<EcChannelNodesEntity>> data = channelDataEntity.getNodes();
		addTreeNode(treeView, node, data);
	}

	// 加载标签
	private void loadLineLabels(final AndroidTreeView treeView, final TreeNode node, ItemEntity item) {
		EcLineEntity line = (EcLineEntity) item.getObjData();
		List<DeviceEntity<EcLineLabelEntityVo>> labelList = dataCenter.getLineLabelList(line.getEcLineId(), null, null);
		addTreeNode(treeView, node, labelList);
	}

}
