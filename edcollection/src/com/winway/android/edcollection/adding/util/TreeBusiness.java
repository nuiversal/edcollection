package com.winway.android.edcollection.adding.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.common.base.FinalizablePhantomReference;
import com.lidroid.xutils.DbUtils;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.SubstationActivity;
import com.winway.android.edcollection.adding.bll.DeviceQueryBll;
import com.winway.android.edcollection.adding.controll.DeviceReadQueryControll;
import com.winway.android.edcollection.adding.controll.SubstationControll;
import com.winway.android.edcollection.adding.util.StationDialogUtils.OnItemDialogListener;
import com.winway.android.edcollection.adding.viewholder.NewFragmentViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.IndexEcNodeEntity;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenter.CallBack;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.edp.OrgResult;
import com.winway.android.edcollection.datacenter.entity.edp.OrgTreeResult;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.main.controll.MainControll;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcDistBoxEntity;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.core.TreeNode.TreeNodeLongClickListener;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.holder.BaseTreeNodeHolder;
import com.winway.android.ewidgets.tree.holder.CommonHolder;
import com.winway.android.ewidgets.tree.holder.CommonHolder.CommonTreeNodeClickListener;
import com.winway.android.ewidgets.tree.utils.TreeUtil;
import com.winway.android.map.util.GraphicsDrawUtils;
import com.winway.android.map.util.MapUtils;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.ClonesUtil;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ObjectUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.ToastUtils;

/**
 * 台账树业务
 * 
 * @author mr-lao
 *
 */
public class TreeBusiness {
	private Activity mActivity;
	private NewFragmentViewHolder viewHolder;
	private DataCenter dataCenter = null;
	private String projectBDUrl = null;
	private boolean hadInitTree = false;
	private AndroidTreeView androidTree = null;
	private TreeNode rootNode = TreeNode.root();
	private CommonHolder.Config config = null;
	private CommonHolder.CommonTreeNodeClickListener commListener = null;
	private BaseTreeNodeHolder.CheckBoxCheckListener checkBoxListener = null;
	private TreeDataLoder treeDataLoder = null;
	private TreeDataTranslator treeDataTranslator = null;
	private TreeMapUtil treeMapUtil = null;
	private TreeRefleshUtil treeRefleshUtil = null;
	private DeviceQueryBll deviceQueryBll;
	private CommonBll commonBll = null;

	public TreeBusiness(Activity mActivity, NewFragmentViewHolder viewHolder) {
		this.viewHolder = viewHolder;
		this.mActivity = mActivity;
	}

	public void init(DataCenter center, TreeMapUtil mapUtil) {
		projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		deviceQueryBll = new DeviceQueryBll(mActivity, projectBDUrl);
		commonBll = new CommonBll(mActivity, projectBDUrl);
		this.dataCenter = center;
		this.treeMapUtil = mapUtil;
		((DataCenterImpl) dataCenter).setUseCache(true);
		viewHolder.getBtnTree().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (viewHolder.getHSTreeCompoment().getVisibility() == View.VISIBLE) {
					// 隐藏台账树
					viewHolder.getHSTreeCompoment().setVisibility(View.GONE);
					viewHolder.getBtnTree().setImageResource(R.drawable.tree);
				} else {
					// 显示台账树
					viewHolder.getHSTreeCompoment().setVisibility(View.VISIBLE);
					viewHolder.getBtnTree().setImageResource(R.drawable.tree_s);
				}
				// 只获取当前项目的数据
				initTree(GlobalEntry.currentProject.getOrgId(), GlobalEntry.loginResult.getCredit());
			}
		});
	}

	// 节点长按事件（更新数据监听器）
	private TreeNodeLongClickListener refleshListener = new TreeNodeLongClickListener() {
		@Override
		public boolean onLongClick(TreeNode node, Object value) {
			ItemEntity item = (ItemEntity) value;

			if (item.getObjData() instanceof OrgResult) {
				if (node.getChildren().size() == 0) {
					return false;
				}
				ItemEntity itemChild = (ItemEntity) node.getChildren().get(0).getValue();
				if (itemChild.getObjData() instanceof OrgResult) {
					return true;
				}
			}
			if (item.getObjData() instanceof EcSubstationEntity) {
				EcSubstationEntity station = null;
				if (node.getChildren().size() == 0) {
					station = (EcSubstationEntity) item.getObjData();
				}
				handlerStation(station);
				return false;
			}
			treeRefleshUtil.reflesh(node, item);
			return true;
		}
	};

	// 初始化台账树
	private void initTree(String orgId, String credit) {
		if (hadInitTree) {
			return;
		}
		androidTree = new AndroidTreeView(mActivity);
		// 初始台账树化数据加载器和数据转换器
		treeDataLoder = new TreeDataLoder(dataCenter, mActivity, projectBDUrl);
		treeDataTranslator = new TreeDataTranslator();
		treeDataTranslator.setItemCustomViewUtil(
				new ItemCustomViewUtil(mActivity, dataCenter, new OfflineAttachCenter(mActivity, projectBDUrl)));
		treeDataLoder.setDataTranslator(treeDataTranslator);
		// 初始化台账树配置
		config = new CommonHolder.Config();
		config.margin = 50;
		commListener = new CommonTreeNodeClickListener(config) {
			@Override
			public void onNodeClick(TreeNode node, ItemEntity value) {
				Object objData = value.getObjData();
				OfflineAttachCenter center = new OfflineAttachCenter(mActivity, projectBDUrl);
				if (null == objData) {
					return;
				}
				TableDataUtil.treeDataCenter = (DataCenterImpl) dataCenter;
				// 节点
				if (objData instanceof EcNodeEntity) {
					// 去树节点拿到实体
					ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
					// 拿到线路id
					String lineid = ((EcLineEntity) parentItem.getObjData()).getEcLineId();
					TableShowUtil.showNodeDetailed((EcNodeEntity) objData, lineid, center, mActivity);
					TreeUtil.setNodeCheckBox(node, true, true, config);
					return;
				}
				// 设备
				if (objData instanceof DeviceEntity) {
					proccessDeviceNodeClick(node, objData, center);
					return;
				}
				if (objData instanceof IndexEcNodeEntity) {// 通道节点
					IndexEcNodeEntity<EcChannelNodesEntity> indexEcNodeEntity = (IndexEcNodeEntity<EcChannelNodesEntity>) objData;
					String lineid = null;
					TableShowUtil.showNodeDetailed(indexEcNodeEntity.getNode(), lineid, center, mActivity);
					TreeUtil.setNodeCheckBox(node, true, true, config);
					return;
				}

			}
		};
		commListener.setNeedProcessCheckBox(false);
		commListener.setCustomDataLoader(treeDataLoder);
		commListener.setTree(androidTree);

		checkBoxListener = new BaseTreeNodeHolder.CheckBoxCheckListener() {
			@Override
			public void onCheckedChanged(TreeNode node, CompoundButton buttonView, boolean isChecked) {
				ItemEntity value = (ItemEntity) node.getValue();
				// 节点点击事件处理
				Object objData = value.getObjData();
				if (null == objData) {
					return;
				}
				// 变电站节点
				proccessStationBoxCheckd(isChecked, value, objData);
				// 线路节点
				proccessLineBoxChecked(node, isChecked, value, objData);
				// 路径点节点
				processNodeBoxChecked(node, isChecked, objData);
				// 设备父节点
				proccessDeviceParentBoxChecked(node, isChecked, value, objData);
				// 具体设备节点
				proccessDeviceBox(node, isChecked, objData);
				// 处理通道
				proccessChannelBox(node, objData, value, isChecked);
				// 通道节点
				processChannelNodeBoxChecked(node, isChecked, objData);

			}

			@Override
			public boolean isClick() {
				return true;
			}

			@Override
			public void setClick(boolean click) {

			}
		};

		// 加载机构信息
		dataCenter.getOrgTree(orgId, GlobalEntry.loginResult.getLogicSysNo(), credit, new CallBack<OrgTreeResult>() {

			@Override
			public void call(OrgTreeResult data) {
				if (null == data.getRows() || data.getRows().isEmpty()) {
					ToastUtil.show(mActivity, "获取机构树失败", 200);
					viewHolder.getBtnTree().setImageResource(R.drawable.tree);
				}
				// 将机构信息转化成节点
				TreeDataUtli.addPageOrgdataToTreeNode(data, rootNode);
				androidTree.setRoot(rootNode);
				androidTree.setCommonViewHolder(CommonHolder.class, config, checkBoxListener, commListener);
				viewHolder.getTreeContainer().addView(androidTree.getView());
			}
		});
		androidTree.setDefaultNodeLongClickListener(refleshListener);
		treeRefleshUtil = TreeRefleshUtil.getSingleton(androidTree, dataCenter, treeMapUtil);
		NewMarkWayTool.getInstance().treeMapUtil = treeMapUtil;
		hadInitTree = true;
	}
	
	/**
	 * 处理变电站删除,编辑
	 * @param station
	 * @author Zhengyang
	 */
	protected void handlerStation(final EcSubstationEntity station) {
		// TODO Auto-generated method stub
		StationDialogUtils utils = new StationDialogUtils(mActivity);
		utils.setOnItemDialogListenter(new OnItemDialogListener() {
			@Override
			public void onItem(int item) {
				// TODO Auto-generated method stub
				switch (item) {
				case 0: //编辑
					editStation(station);
					break;
				case 1: //删除
					deleteStation(station);
					break;
				}
			}
		});
		utils.showAlertDialog("变电站操作",station != null ? new String[]{"编辑","删除"}:new String[] {"编辑"});
	}

	/**
	 * 处理变电站编辑
	 * @param station
	 * @author Zhengyang
	 */
	protected void editStation(EcSubstationEntity station) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(mActivity, SubstationActivity.class);
		Bundle bundle = new Bundle();
		EcSubstationEntityVo ecvo = new EcSubstationEntityVo();
		try {
			if(station != null) {
				ClonesUtil.clones(ecvo, station);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bundle.putSerializable("EcSubstationEntity", ecvo);
		intent.putExtra("coord", MapUtils.getInstance().getMapCenter());
		intent.putExtra("tag", MainControll.requestCode_subtation + "");
		intent.putExtras(bundle);
		// mActivity.startActivityForResult(intent,MainControll.requestCode_subtation);
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent,MainControll.requestCode_subtation);
		SubstationControll.isNewSubstation = true;
	}

	private void proccessChannelBox(TreeNode node, Object objData, ItemEntity value, boolean isChecked) {
		if (!(objData instanceof ChannelDataEntity)) {
			return;
		}
		ChannelDataEntity channel = (ChannelDataEntity) objData;
		if (isChecked) {
			if (node.getChildren() == null || node.getChildren().isEmpty()) {
				// 加载路径点
				treeDataLoder.loadNode(androidTree, node, value);
			}
			androidTree.expandNode(node);
			treeMapUtil.drawChannel(channel);
			TreeUtil.setChildNodeCheckBox(node, true, true, config);
		} else {
			treeMapUtil.removeChannel(channel.getData().getEcChannelId());
			TreeUtil.setChildNodeCheckBox(node, false, true, config);
		}
	}

	@SuppressWarnings("unchecked")
	private void proccessDeviceNodeClick(TreeNode node, Object objData, OfflineAttachCenter center) {
		Object data = ((DeviceEntity<?>) objData).getData();
		if (data == null) {
			return;
		}
		if (data instanceof EcLineLabelEntity) {
			String oid = ((EcLineLabelEntity) data).getOid();
			if (oid == null) {
				DeviceReadQueryControll.isEdit = false;
				TableShowUtil.showLabelDetailed((EcLineLabelEntity) data, center, mActivity);
				TreeUtil.setNodeCheckBox(node, true, true, config);
			} else {
				DeviceReadQueryControll.isEdit = true;
				String expr = "OID = '" + oid + "'";
				List<EcNodeEntity> nodes = deviceQueryBll.queryByExpr2(EcNodeEntity.class, expr);
				EcNodeEntity ecNodeEntity = nodes.get(0);
				GlobalEntry.currentEditNode = ecNodeEntity;
				TableShowUtil.showLabelDetailed((EcLineLabelEntity) data, center, mActivity);
				TreeUtil.setNodeCheckBox(node, true, true, config);
			}
			return;
		}
		if (data instanceof EcMiddleJointEntity) {
			// 电缆中间接头
			TableShowUtil.showMiddleDetailed((EcMiddleJointEntity) data, center, mActivity);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcWorkWellEntity) {
			// 工井
			TableShowUtil.showWellDetailed((EcWorkWellEntity) data, center, mActivity);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcDistBoxEntity) {
			// 分接箱
			TableShowUtil.showBoxDetailed((EcDistBoxEntity) data, center, mActivity);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcTowerEntity) {
			// 杆塔
			TableShowUtil.showTowerDetailed((EcTowerEntity) data, center, mActivity);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcDistributionRoomEntity) {
			// 配电房
			TableShowUtil.showRoomDetailed((EcDistributionRoomEntity) data, center, mActivity);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcTransformerEntity) {
			// 变压器
			TableShowUtil.showTransDetailed((EcTransformerEntity) data, center, mActivity);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcHwgEntity) {
			// 环网柜
			TableShowUtil.showHwgDetailed((EcHwgEntity) data, mActivity, dataCenter);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcXsbdzEntity) {
			// 箱式变电站
			TableShowUtil.showXsbdzDetailed((EcXsbdzEntity) data, mActivity, dataCenter);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcDlfzxEntity) {
			// 电缆分接箱
			TableShowUtil.showDlfzxDetailed((EcDlfzxEntity) data, mActivity, dataCenter);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcDydlfzxEntity) {
			// 低压电缆分接箱
			TableShowUtil.showDydlfzxDetailed((EcDydlfzxEntity) data, mActivity, dataCenter);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcDypdxEntity) {
			// 低压配电箱
			TableShowUtil.showDypdxDetailed((EcDypdxEntity) data, mActivity, dataCenter);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcKgzEntity) {
			// 开关站
			TableShowUtil.showKgzDetailed((EcKgzEntity) data, mActivity, dataCenter);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
		if (data instanceof EcWorkWellCoverEntity) {
			// 开关站
			ItemEntity value = (ItemEntity) node.getParent().getValue();
			TableShowUtil.showCover((EcWorkWellCoverEntity) data,
					((DeviceEntity<EcWorkWellEntity>) value.getObjData()).getData(), mActivity);
			TreeUtil.setNodeCheckBox(node, true, true, config);
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private void proccessDeviceBox(TreeNode node, boolean isChecked, Object objData) {
		if (objData instanceof DeviceEntity) {
			if (((DeviceEntity<?>) objData).getNode() == null
					|| TextUtils.isEmpty(((DeviceEntity<?>) objData).getNode().getGeom())) {
				ToastUtils.show(mActivity, "此设备没有坐标参数，无法在地图上显示");
				return;
			}
			Object data = ((DeviceEntity<?>) objData).getData();
			if (data instanceof EcLineLabelEntity) {
				// 标签
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				// 设备
				if (isChecked) {
					treeMapUtil.drawLabel((DeviceEntity<EcLineLabelEntityVo>) objData, linename);
				} else {
					treeMapUtil.removeLabel(((DeviceEntity<EcNodeEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}

			if (data instanceof EcMiddleJointEntity) {
				// 中间接头
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawMiddle((DeviceEntity<EcMiddleJointEntityVo>) objData, linename);
				} else {
					treeMapUtil.removeMiddle(((DeviceEntity<EcNodeEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}

			if (data instanceof EcWorkWellEntity) {
				// 工井
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawWell((DeviceEntity<EcWorkWellEntityVo>) objData, linename);
				} else {
					treeMapUtil.removeWell(((DeviceEntity<EcWorkWellEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}
			if (data instanceof EcDistBoxEntity) {
				// 分接箱
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawBox((DeviceEntity<EcDistBoxEntityVo>) objData, linename);
				} else {
					treeMapUtil.removeBox(((DeviceEntity<EcDistBoxEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}
			if (data instanceof EcTowerEntity) {
				// 杆塔
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawTower((DeviceEntity<EcTowerEntityVo>) objData, linename);
				} else {
					treeMapUtil.removeTower(((DeviceEntity<EcTowerEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}
			if (data instanceof EcDistributionRoomEntity) {
				// 配电房
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawRoom((DeviceEntity<EcDistributionRoomEntityVo>) objData, linename);
				} else {
					treeMapUtil.removeRoom(((DeviceEntity<EcDistributionRoomEntity>) objData).getNode().getOid(),
							linename);
				}
				return;
			}
			if (data instanceof EcTransformerEntity) {
				// 变压器
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawTrans((DeviceEntity<EcTransformerEntityVo>) objData, linename);
				} else {
					treeMapUtil.removeTrans(((DeviceEntity<EcTransformerEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}
			if (data instanceof EcHwgEntity) {
				// 环网柜
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawHwg((DeviceEntity<EcHwgEntity>) objData, linename);
				} else {
					treeMapUtil.removeHwg(((DeviceEntity<EcHwgEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}
			if (data instanceof EcXsbdzEntity) {
				// 箱式变电站
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawXsbdz((DeviceEntity<EcXsbdzEntity>) objData, linename);
				} else {
					treeMapUtil.removeXsbdz(((DeviceEntity<EcXsbdzEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}
			if (data instanceof EcDlfzxEntity) {
				// 电缆分接箱
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawDlfzx((DeviceEntity<EcDlfzxEntity>) objData, linename);
				} else {
					treeMapUtil.removeDlfzx(((DeviceEntity<EcDlfzxEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}
			if (data instanceof EcDydlfzxEntity) {
				// 低压电缆分接箱
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawDydlfzx((DeviceEntity<EcDydlfzxEntity>) objData, linename);
				} else {
					treeMapUtil.removeDydlfzx(((DeviceEntity<EcDydlfzxEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}
			if (data instanceof EcDypdxEntity) {
				// 低压配电箱
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawDypdx((DeviceEntity<EcDypdxEntity>) objData, linename);
				} else {
					treeMapUtil.removeDypdx(((DeviceEntity<EcDypdxEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}
			if (data instanceof EcKgzEntity) {
				// 低压配电箱
				ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
				String linename = ((EcLineEntity) parentItem.getObjData()).getName();
				if (isChecked) {
					treeMapUtil.drawKgz((DeviceEntity<EcKgzEntity>) objData, linename);
				} else {
					treeMapUtil.removeKgz(((DeviceEntity<EcKgzEntity>) objData).getNode().getOid(), linename);
				}
				return;
			}
			if (data instanceof EcWorkWellCoverEntity) {
				// 井盖
				if (isChecked) {
					treeMapUtil.drawCover((DeviceEntity<EcWorkWellCoverEntity>) objData);
				} else {
					treeMapUtil.removeCover(((DeviceEntity<EcWorkWellCoverEntity>) objData).getNode().getOid(),
							((DeviceEntity<EcWorkWellCoverEntity>) objData).getData().getObjId());
				}
				return;
			}
		}
	}

	private void proccessDeviceParentBoxChecked(final TreeNode node, final boolean isChecked, ItemEntity value,
			Object objData) {
		if (objData instanceof EcLineEntity && !TextUtils.isEmpty(value.getUrl())) {
			//
			if ("#BSQ#".equals(value.getUrl())) {
				// 标识器
				if (node.getChildren() == null || node.getChildren().isEmpty()) {
					// 加载路径点
					treeDataLoder.loadNode(androidTree, node, value);
				}
				androidTree.expandNode(node);
			}
			// 勾选下级节点选择框
			TreeUtil.setChildNodeCheckBox(node, isChecked, true, config);
			// 关闭闪烁
			GraphicsDrawUtils.getInstance().stopOtherFlash();
		}
	}

	private void proccessStationBoxCheckd(boolean isChecked, ItemEntity value, Object objData) {
		if (objData instanceof EcSubstationEntity && TextUtils.isEmpty(value.getUrl())) {
			// 加载变电站
			String stationId = ((EcSubstationEntity) objData).getEcSubstationId();
			if (isChecked) {
				treeMapUtil.drawStation(stationId);
			} else {
				treeMapUtil.removeStation(stationId);
			}
			return;
		}
	}

	private void proccessLineBoxChecked(TreeNode node, boolean isChecked, ItemEntity value, Object objData) {
		/*
		 * if (objData instanceof EcLineEntity && TextUtils.isEmpty(value.getUrl())) {
		 * // 加载线路 String lineId = ((EcLineEntity) objData).getEcLineId(); if
		 * (isChecked) { treeMapUtil.drawLine((EcLineEntity) objData, lineId); if
		 * (node.getChildren() == null || node.getChildren().isEmpty()) { // 加载节点
		 * treeDataLoder.loadNode(androidTree, node, value); } // 展开节点
		 * androidTree.expandNode(node); } else { treeMapUtil.removeLine(lineId); } //
		 * 勾选路径点 TreeUtil.setNodeCheckBox(node.getChildren().get(0), isChecked, true,
		 * config); return; }
		 */
		if (!(objData instanceof EcLineEntity && TextUtils.isEmpty(value.getUrl()))) {
			return;
		}
		boolean isCheckParentLine = false;
		List<TreeNode> childrens = node.getChildren();
		if (childrens != null && !childrens.isEmpty()) {
			if (!node.isExpanded()) {
				androidTree.expandNode(node);
			}
			for (TreeNode ch : childrens) {
				ItemEntity v = (ItemEntity) ch.getValue();
				if (v != null && v.getObjData() != null && v.getObjData() instanceof EcLineEntity
						&& TextUtils.isEmpty(v.getUrl())) {
					isCheckParentLine = true;
					// 画出下级线路
					if (isChecked) {
						treeMapUtil.drawLine((EcLineEntity) objData, ((EcLineEntity) v.getObjData()).getEcLineId());
					} else {
						treeMapUtil.removeLine(((EcLineEntity) v.getObjData()).getEcLineId());
					}
					TreeUtil.setNodeCheckBox(ch, isChecked, false, config);
				}
			}
		}
		if (isCheckParentLine) {
			return;
		}

		String lineId = ((EcLineEntity) objData).getEcLineId();
		if (isChecked) {
			treeMapUtil.drawLine((EcLineEntity) objData, lineId);
			if (node.getChildren() == null || node.getChildren().isEmpty()) {
				// 加载节点
				if (treeDataLoder != null) {
					treeDataLoder.loadNode(androidTree, node, value);
				}
			}
			// 展开节点
			androidTree.expandNode(node);
		} else {
			treeMapUtil.removeLine(lineId);
		}

		// 勾选子节点
		if (null != node.getChildren() && !node.getChildren().isEmpty()) {
			if (isChecked && !node.isExpanded()) {
				androidTree.expandNode(node);
			}
			TreeUtil.setNodeCheckBox(node.getChildren().get(0), isChecked, true, config);
		}
	}

	private void processNodeBoxChecked(TreeNode node, boolean isChecked, Object objData) {
		if (objData instanceof EcNodeEntity) {
			ItemEntity parentItem = (ItemEntity) node.getParent().getValue();
			String lineid = ((EcLineEntity) parentItem.getObjData()).getEcLineId();
			if (isChecked) {
				treeMapUtil.drawNode((EcNodeEntity) objData, lineid);
			} else {
				treeMapUtil.removeNode(((EcNodeEntity) objData).getOid(), lineid);
			}
			return;
		}
	}

	/**
	 * 处理通道节点勾选
	 * 
	 * @param node
	 * @param isChecked
	 * @param objData
	 */
	private void processChannelNodeBoxChecked(TreeNode node, boolean isChecked, Object objData) {
		if (objData instanceof IndexEcNodeEntity) {
			IndexEcNodeEntity<EcChannelNodesEntity> indexEcNodeEntity = (IndexEcNodeEntity<EcChannelNodesEntity>) objData;
			if (isChecked) {
				treeMapUtil.drawNodeWithChannelId(indexEcNodeEntity.getNode(),
						indexEcNodeEntity.getIndex().getEcChannelId());
			} else {
				treeMapUtil.removeChannelNode(indexEcNodeEntity.getNode().getOid(),
						indexEcNodeEntity.getIndex().getEcChannelId());
			}
			return;
		}
	}

	/**
	 * 删除变电站
	 * 
	 * @param station
	 *            变电站实体
	 * @author xs
	 */
	private void deleteStation( EcSubstationEntity station) {
			// 通过变电站id判断变电站下是否挂靠线路
			String lineSql = "START_STATION ='" + station.getEcSubstationId() + "' OR END_STATION = '"
					+ station.getEcSubstationId() + "'";
			List<EcLineEntity> lines = commonBll.queryByExpr2(EcLineEntity.class, lineSql);
			if (null != lines && !lines.isEmpty()) {
				ToastUtil.showShort(mActivity, "此变电站下存在线路，无法删除！");
				return;
			}

			// 通过变电站编号判断变电站下是否挂靠线路
			String lineSql2 = "START_STATION ='" + station.getStationNo() + "' OR END_STATION = '"
					+ station.getEcSubstationId() + "'";
			List<EcLineEntity> lines2 = commonBll.queryByExpr2(EcLineEntity.class, lineSql2);
			if (null != lines2 && !lines2.isEmpty()) {
				ToastUtil.showShort(mActivity, "此变电站下存在线路，无法删除！");
				return;
			}

			// 是否需要删除对应节点
			boolean canDeleteNode = false;

			String sql = "DEV_OBJ_ID = '" + station.getEcSubstationId() + "'";
			List<EcNodeDeviceEntity> stationDev = commonBll.queryByExpr2(EcNodeDeviceEntity.class, sql);
			if (null != stationDev && !stationDev.isEmpty()) {
				// 变电站对应节点信息实体
				EcNodeDeviceEntity nodeDev = stationDev.get(0);
				// 查询变电站关联节点下除变电站外所有节点设备
				String nodeSql = "DEVICE_TYPE <> '" + ResourceEnum.BDZ.getValue() + "'AND OID = '"
						+ nodeDev.getOid() + "'";
				List<EcNodeDeviceEntity> nodeDevs = commonBll.queryByExpr2(EcNodeDeviceEntity.class, nodeSql);
				if (null == nodeDevs || nodeDevs.isEmpty()) {
					canDeleteNode = true;
				}
				if (!canDeleteNode) {
					// 查询变电站关联节点下所有线路设备
					String lineDevSql = "OID = '" + nodeDev.getOid() + "'";
					List<EcLineNodesEntity> lineDevs = commonBll.queryByExpr2(EcLineNodesEntity.class,
							lineDevSql);
					if (null == lineDevs || lineDevs.isEmpty()) {
						canDeleteNode = true;
					}
				}
				if (!canDeleteNode) {
					// 查询变电站关联节点下所有通道节点
					String channelSql = "OID = '" + nodeDev.getOid() + "'";
					List<EcChannelNodesEntity> channelNodes = commonBll.queryByExpr2(EcChannelNodesEntity.class,
							channelSql);
					if (null == channelNodes || channelNodes.isEmpty()) {
						canDeleteNode = true;
					}
				}
				if (canDeleteNode) {
					String oid = nodeDev.getOid();
					EmCDataLogEntity dataLogEntity = commonBll.getDataLogEntity(oid);
					if (null == dataLogEntity) {
						return;
					}
					Integer optType = dataLogEntity.getOptType();
					if(optType==DataLogOperatorType.Add.getValue()){
						// 删除变电站对应节点
						commonBll.deleteById(EcNodeEntity.class, nodeDev.getOid());
						commonBll.deleteDataLog(nodeDev.getOid());
						/*// 更新日志
						commonBll.handleDataLog(nodeDev.getOid(), TableNameEnum.LJD.getTableName(),
								DataLogOperatorType.delete, WhetherEnum.NO, "删除APP端添加变电站是产生的虚拟点", false);*/
					}else if(optType==DataLogOperatorType.update.getValue()){
						// 更新日志
						commonBll.handleDataLog(nodeDev.getOid(), TableNameEnum.LJD.getTableName(),
								DataLogOperatorType.delete, WhetherEnum.NO, "删除APP端添加变电站是产生的虚拟点", false);
					}
				}
			}
			// 删除变电站附件
			String attachSql = "OWNER_CODE ='" + TableNameEnum.BDZ.getTableName() + "' AND WORK_NO ='"
					+ station.getEcSubstationId() + "'";
			List<OfflineAttach> attachs = commonBll.queryByExpr2(OfflineAttach.class, attachSql);
			if (null != attachs && !attachs.isEmpty()) {
				for (int i = 0; i < attachs.size(); i++) {
					// 附件实体
					OfflineAttach attach = attachs.get(i);
					// 删除文件
					FileUtil.deleteFileByFilePath(attach.getFilePath());
					// 删除附件表信息
					commonBll.deleteById(OfflineAttach.class, attach.getComUploadId());
				}
			}
			EmCDataLogEntity dataLogEntity = commonBll.getDataLogEntity(station.getEcSubstationId());
			if (null == dataLogEntity) {
				return;
			}
			Integer optType = dataLogEntity.getOptType();
			if(optType==DataLogOperatorType.Add.getValue()){
				// 删除变电站
				commonBll.deleteById(EcSubstationEntity.class, station.getEcSubstationId());
				//删除日志
				commonBll.deleteDataLog(station.getEcSubstationId());
			}else if (optType==DataLogOperatorType.update.getValue()) {
				// 更新日志
				commonBll.handleDataLog(station.getEcSubstationId(), TableNameEnum.BDZ.getTableName(),
						DataLogOperatorType.delete, WhetherEnum.NO, "删除没有挂靠线路的变电站", false);
				treeRefleshUtil.refleshWholeTree();
			}
			ToastUtil.show(mActivity, "删除成功", 200);
			treeRefleshUtil.refleshWholeTree();
	}

}
