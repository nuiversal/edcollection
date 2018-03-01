package com.winway.android.edcollection.adding.controll;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.ChannelDGActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLCActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLGActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLGDActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLQActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLSDActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLZMActivity;
import com.winway.android.edcollection.adding.activity.ChannelJkActivity;
import com.winway.android.edcollection.adding.activity.ChannelPlanningActivity;
import com.winway.android.edcollection.adding.activity.ChannelSelectActivity;
import com.winway.android.edcollection.adding.adapter.ChannelsAdapter;
import com.winway.android.edcollection.adding.bll.ChannelBll;
import com.winway.android.edcollection.adding.bll.CollectCommonBll;
import com.winway.android.edcollection.adding.customview.Point;
import com.winway.android.edcollection.adding.entity.ChannelIndexCacheEntity;
import com.winway.android.edcollection.adding.entity.ChannelLabelEntity;
import com.winway.android.edcollection.adding.entity.DitchCable;
import com.winway.android.edcollection.adding.entity.channelplaning.ChannelPlaningEntity;
import com.winway.android.edcollection.adding.entity.channelplaning.JMEntity;
import com.winway.android.edcollection.adding.util.ChannelPlaningUtil;
import com.winway.android.edcollection.adding.util.FragmentEntry;
import com.winway.android.edcollection.adding.util.JMsWindowDisplayUtil;
import com.winway.android.edcollection.adding.util.LengthTextWatcher;
import com.winway.android.edcollection.adding.util.NamingConventionsFloatWin;
import com.winway.android.edcollection.adding.util.SelectCollectObjPopWindow;
import com.winway.android.edcollection.adding.viewholder.ChannelViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.util.ResourceDeviceUtil;
import com.winway.android.edcollection.base.util.ResourceEnumUtil;
import com.winway.android.edcollection.base.view.BaseFragmentControll;
import com.winway.android.edcollection.datacenter.service.ChannelCenter;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
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
import com.winway.android.edcollection.project.entity.EcChannelSectionPointsEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcVRRefEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.DateUtils;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.FileUtil;
import com.winway.android.util.GsonUtils;
import com.winway.android.util.ListUtil;
import com.winway.android.util.ToastUtils;
import com.winway.android.util.UUIDGen;

/**
 * 通道采集
 * 
 * 通道注意事项： 1、新建的通道不会关联任何线路 2、存在的通道，但未关联线路的通道才能关联线路，所关联的线路为所采集的线路 3、编辑状态下不可以关联线路
 * 4、已经关联了线路的通道不可以再关联新线路 5、对已经关联了线路的通道只能在通道修改界面里面进行线路增加（暂不支持关联的线路删除）
 * 
 * @author zgq
 *
 */
public class ChannelControll extends BaseFragmentControll<ChannelViewHolder> {
	private CollectCommonBll collectCommonBll = null;
	private BaseDBUtil projectBDUtil = null;
	private ChannelBll channelBll;
	private ChannelsAdapter channelsAdapter = null;
	private OfflineAttachCenter attachCenter;
	private List<ChannelLabelEntity> channelLabelList = new ArrayList<ChannelLabelEntity>();
	private ChannelLabelEntity channelLabelEntity;

	// 通道设施
	public static final int requestCode_dg = 3;// 顶管
	public static final int requestCode_gd = 4;// 沟道
	public static final int requestCode_pg = 5;// 排管
	public static final int requestCode_qj = 6;// 桥架
	public static final int requestCode_sd = 7;// 隧道
	public static final int requestCode_zm = 8;// 直埋
	public static final int requestCode_jk = 10;// 架空
	public static final int requestCode_dlc = 11;// 电缆槽

	public static final int requestCode_select_channel = 9;// 选择通道

	boolean checkRequestCode(int requestCode) {
		if (requestCode == requestCode_select_channel) {
			return true;
		}
		if (requestCode == requestCode_dg) {
			return true;
		}
		if (requestCode == requestCode_gd) {
			return true;
		}
		if (requestCode == requestCode_qj) {
			return true;
		}
		if (requestCode == requestCode_sd) {
			return true;
		}
		if (requestCode == requestCode_pg) {
			return true;
		}
		if (requestCode == requestCode_zm) {
			return true;
		}
		if (requestCode == requestCode_jk) {
			return true;
		}
		if (requestCode == requestCode_dlc) {
			return true;
		}
		return false;
	}

	private BaseDBUtil cacheDBUtil;

	private ChannelCenter channelCenter;

	@Override
	public void init() {
		collectCommonBll = new CollectCommonBll(mActivity, GlobalEntry.prjDbUrl);
		projectBDUtil = new BaseDBUtil(mActivity, new File(GlobalEntry.prjDbUrl));
		channelBll = new ChannelBll(mActivity, GlobalEntry.prjDbUrl);
		attachCenter = new OfflineAttachCenter(mActivity, GlobalEntry.prjDbUrl);
		cacheDBUtil = new BaseDBUtil(mActivity, FileUtil.AppRootPath + "/db/project/data_cache.db");
		channelCenter = new ChannelCenter(projectBDUtil);
		initDatas();
		initEvents();
		initSetting();

		//
		projectBDUtil.createTableIfNotExist(EcVRRefEntity.class);
	}

	private void initSetting() {
		// 限制通道类型无法输入
		viewHolder.getIscChannelType().getEditTextView().setEnabled(false);
		// 限制通道名称无法输入
		viewHolder.getIcChannelName().getEditTextView().setEnabled(false);
		viewHolder.getIcChannelSort().getEditTextView().addTextChangedListener(new LengthTextWatcher(
				("" + Integer.MAX_VALUE).length() - 1, viewHolder.getIcChannelSort().getEditTextView()));
	}

	private ChannelDataEntity updateChannelDataCenter = null;

	/**
	 * 启动页面跳转
	 * 
	 * @param position
	 * @param deviceClass
	 */
	private void startActivity(int position, Class<?> deviceClass, int requestCode, String resourceNo, String objId) {
		updateChannelDataCenter = channelList.get(position);
		Bundle bundle = new Bundle();
		Intent intent = new Intent(mActivity, deviceClass);
		bundle.putSerializable(GlobalEntry.INTENT_KEY_CHANNEL_DATA,
				(Serializable) updateChannelDataCenter.getChannelType());
		bundle.putSerializable(GlobalEntry.INTENT_KEY_CHANNEL, updateChannelDataCenter.getData());
		if (channelLabelList != null && !channelLabelList.isEmpty()) {
			for (ChannelLabelEntity channelLabelEntity : channelLabelList) {
				if (resourceNo.equals(channelLabelEntity.getTypeNo())) {
					if (objId != null) {
						if (objId.equals(channelLabelEntity.getObjId())) {
							bundle.putSerializable("channelLabelEntity", channelLabelEntity);
						}
					}
				}
			}
		}
		intent.putExtras(bundle);
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, requestCode);
	}

	/**
	 * 智能选择通道
	 */
	@Deprecated
	private void intellectSelectChannel(String lineid) {
		List<com.winway.android.edcollection.datacenter.entity.ChannelDataEntity> list = channelCenter
				.getChannelListByLineID(lineid);
		if (null == list || list.isEmpty()) {
			return;
		}
		ChannelDataEntity channelData = new ChannelDataEntity();
		int position = list.size() - 1;
		channelData.data = list.get(position).getData();
		channelData.channelLines = list.get(position).getChannelLines();
		channelData.channelType = list.get(position).getChannelType();
		if (list.get(position).getChannelLines() != null && !list.get(position).getChannelLines().isEmpty()) {
			channelData.isAlreadyContactsLines = true;
		}
		channelData.setExist(true);
		currentChannelDataEntity = channelData;
		showSelectChannel(channelData.data);
	}

	private void initEvents() {
		viewHolder.getBtnChannelNew().setOnClickListener(orcl);
		viewHolder.getBtnChannelAdd().setOnClickListener(orcl);
		viewHolder.getBtnChannelSelect().setOnClickListener(orcl);
		viewHolder.getLvChannelList().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				final DialogUtil dialogUtil = new DialogUtil(mActivity);
				dialogUtil.showAlertDialog("选择", new String[] { "通道修改", "截面采集" },
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (which == 0) {
									String channelType = ResourceEnumUtil
											.get(channelList.get(position).getData().getChannelType()).getName();
									String objId = "";
									if (channelType.equals(ResourceEnum.TLG.getName())) { // 顶管/拖拉管
										objId = ((EcChannelDgEntity) channelList.get(position).getChannelType())
												.getObjId();
										startActivity(position, ChannelDGActivity.class, requestCode_dg,
												ResourceEnum.TLG.getValue(), objId);
									} else if (channelType.equals(ResourceEnum.GD.getName())) { // 沟道
										objId = ((EcChannelDlgEntity) channelList.get(position).getChannelType())
												.getObjId();
										startActivity(position, ChannelDLGActivity.class, requestCode_gd,
												ResourceEnum.GD.getValue(), objId);
									} else if (channelType.equals(ResourceEnum.PG.getName())) { // 排管
										objId = ((EcChannelDlgdEntity) channelList.get(position).getChannelType())
												.getObjId();
										startActivity(position, ChannelDLGDActivity.class, requestCode_pg,
												ResourceEnum.PG.getValue(), objId);
									} else if (channelType.equals(ResourceEnum.QJ.getName())) { // 桥架
										objId = ((EcChannelDlqEntity) channelList.get(position).getChannelType())
												.getObjId();
										startActivity(position, ChannelDLQActivity.class, requestCode_qj,
												ResourceEnum.QJ.getValue(), objId);
									} else if (channelType.equals(ResourceEnum.SD.getName())) { // 隧道
										objId = ((EcChannelDlsdEntity) channelList.get(position).getChannelType())
												.getObjId();
										startActivity(position, ChannelDLSDActivity.class, requestCode_sd,
												ResourceEnum.SD.getValue(), objId);
									} else if (channelType.equals(ResourceEnum.ZM.getName())) { // 直埋
										objId = ((EcChannelDlzmEntity) channelList.get(position).getChannelType())
												.getObjId();
										startActivity(position, ChannelDLZMActivity.class, requestCode_zm,
												ResourceEnum.ZM.getValue(), objId);
									} else if (channelType.equals(ResourceEnum.JKXL.getName())) { // 架空
										objId = ((EcChannelJkEntity) channelList.get(position).getChannelType())
												.getObjId();
										startActivity(position, ChannelJkActivity.class, requestCode_jk,
												ResourceEnum.JKXL.getValue(), objId);
									} else if (channelType.equals(ResourceEnum.DLC.getName())) {
										objId = ((EcChannelDlcEntity) channelList.get(position).getChannelType())
												.getObjId();
										startActivity(position, ChannelDLCActivity.class, requestCode_dlc,
												ResourceEnum.DLC.getValue(), objId);
									}
								} else if (which == 1) {
									ChannelDataEntity dataEntity = channelList.get(position);
									String nodeId = "";
									if (GlobalEntry.editNode) {
										Intent intent = getIntent();
										EcNodeEntity ecNodeEntity = (EcNodeEntity) intent
												.getSerializableExtra("EcNodeEntity");
										nodeId = ecNodeEntity.getOid();
									}
									AndroidBasicComponentUtils
											.launchActivityForResult(mActivity, ChannelPlanningActivity.class,
													new String[] { "channel_id", "channel_name", "node_id",
															"isNeedSave" },
													new String[] { dataEntity.getData().getEcChannelId(),
															dataEntity.getData().getName(), nodeId, "0" },
													1080);
								}
								dialogUtil.dismissDialog();
								dialogUtil.destroy();
							}
						}, true);
			}
		});
		viewHolder.getBtnIntellectSelect().setOnClickListener(orcl);
	}

	/**
	 * 初始编辑进来的标签数据
	 * 
	 * @param objId
	 * @param sql
	 */
	private void initEditLabelDatas(String objId, String typeNo, String oid) {
		String sql = "DEV_OBJ_ID = '" + objId + "'and OID = '" + oid + "'";
		ArrayList<EcLineLabelEntityVo> ecLineLabelEntityVos = (ArrayList<EcLineLabelEntityVo>) collectCommonBll
				.queryByExpr2(EcLineLabelEntityVo.class, sql);
		if (ecLineLabelEntityVos != null && ecLineLabelEntityVos.size() > 0) {
			for (EcLineLabelEntityVo ecLineLabelEntityVo : ecLineLabelEntityVos) {
				// 获取所属通道标签的附件
				List<OfflineAttach> attachs = attachCenter.getOfflineAttachListByWorkNo(ecLineLabelEntityVo.getObjId());
				ecLineLabelEntityVo.setAttr(attachs);
				ChannelLabelEntity channelLabelEntity = new ChannelLabelEntity();
				channelLabelEntity.setObjId(objId);
				channelLabelEntity.setTypeNo(typeNo);
				channelLabelEntity.setEcLineLabelEntityVo(ecLineLabelEntityVo);
				channelLabelList.add(channelLabelEntity);
			}
		}
	}

	private void initDatas() {
		viewHolder.getIcChannelSort().setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
		// 初始化通道类型下拉列表
		String[] stringArray = mActivity.getResources().getStringArray(R.array.stateGridType);
		List<String> channelTypes = new ArrayList<String>(Arrays.asList(stringArray));
		// List<String> channelTypes = collectCommonBll.getChannelTypeList();
		final InputSelectAdapter channelTypeAdapter = new InputSelectAdapter(mActivity, channelTypes);
		viewHolder.getIscChannelType().setAdapter(channelTypeAdapter);
		// 通道列表
		if (GlobalEntry.editNode) {
			// 编辑状态下
			Intent intent = getIntent();
			EcNodeEntity ecNodeEntity = (EcNodeEntity) intent.getSerializableExtra("EcNodeEntity");
			String editOid = ecNodeEntity.getOid();

			if (FragmentEntry.getInstance().channelList.isEmpty()) {// 地图选点，数据已经保存在缓存之中
				List<com.winway.android.edcollection.datacenter.entity.ChannelDataEntity> list = channelCenter
						.getChannelListByNodeID(editOid);
				if (null != list) {
					for (com.winway.android.edcollection.datacenter.entity.ChannelDataEntity entity : list) {
						ChannelDataEntity data = new ChannelDataEntity();
						data.channelLines = entity.getChannelLines();
						data.channelNode = entity.getCurrentChannelNode();
						data.channelType = entity.getChannelType();
						data.data = entity.getData();
						data.setEdit(true);
						if (null != data.getChannelLines() && !data.getChannelLines().isEmpty()) {
							data.setAlreadyContactsLines(true);
						}
						data.setExist(true);
						channelList.add(data);
						if (data.channelType instanceof EcChannelDgEntity) {
							EcChannelDgEntity channelDgEntity = (EcChannelDgEntity) data.channelType;
							initEditLabelDatas(channelDgEntity.getObjId(), ResourceEnum.TLG.getValue() + "", editOid);
						} else if (data.channelType instanceof EcChannelDlgEntity) {
							EcChannelDlgEntity dlgEntity = (EcChannelDlgEntity) data.channelType;
							initEditLabelDatas(dlgEntity.getObjId(), ResourceEnum.GD.getValue() + "", editOid);
						} else if (data.channelType instanceof EcChannelDlgdEntity) {
							EcChannelDlgdEntity dlgdEntity = (EcChannelDlgdEntity) data.channelType;
							initEditLabelDatas(dlgdEntity.getObjId(), ResourceEnum.PG.getValue() + "", editOid);
						} else if (data.channelType instanceof EcChannelDlsdEntity) {
							EcChannelDlsdEntity dlsdEntity = (EcChannelDlsdEntity) data.channelType;
							initEditLabelDatas(dlsdEntity.getObjId(), ResourceEnum.SD.getValue() + "", editOid);
						} else if (data.channelType instanceof EcChannelDlzmEntity) {
							EcChannelDlzmEntity dlzmEntityV = (EcChannelDlzmEntity) data.channelType;
							initEditLabelDatas(dlzmEntityV.getObjId(), ResourceEnum.ZM.getValue() + "", editOid);
						} else if (data.channelType instanceof EcChannelDlqEntity) {
							EcChannelDlqEntity dlqEntity = (EcChannelDlqEntity) data.channelType;
							initEditLabelDatas(dlqEntity.getObjId(), ResourceEnum.QJ.getValue() + "", editOid);
						} else if (data.channelType instanceof EcChannelJkEntity) {
							EcChannelJkEntity jkEntity = (EcChannelJkEntity) data.channelType;
							initEditLabelDatas(jkEntity.getObjId(), ResourceEnum.JKXL.getValue() + "", editOid);
						} else if (data.channelType instanceof EcChannelDlcEntity) {
							EcChannelDlcEntity dlcEntity = (EcChannelDlcEntity) data.channelType;
							initEditLabelDatas(dlcEntity.getObjId(), ResourceEnum.DLC.getValue() + "", editOid);
						}
					}
				}
			}
		}
		if (!FragmentEntry.getInstance().channelList.isEmpty()) {
			// 地图选点，数据恢复
			ListUtil.copyList(channelList, FragmentEntry.getInstance().channelList);
		}
		channelsAdapter = new ChannelsAdapter(mActivity, channelList);
		viewHolder.getLvChannelList().setAdapter(channelsAdapter);
	}

	public boolean needSave() {
		return canAdd(false);
	}

	private OnClickListener orcl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_fragment_channel_add: {// 添加（确定选择）
				addChannelInList();
				break;
			}
			case R.id.btn_fragment_channel_new: {// 新增
				createChannel();
				break;
			}

			case R.id.btn_fragment_channel_select: {// 选择
				selectChannel();
				break;
			}
			case R.id.btn_fragment_channel_intellect_select: {
				List<EcLineEntity> lines = SelectCollectObjPopWindow.getIntance(mActivity).getWaitingLines();
				if (lines == null || lines.isEmpty()) {
					return;
				}
				intellectSelectChannel(lines.get(0).getEcLineId());
				break;
			}
			default:
				break;
			}
		}
	};

	private boolean canAdd(boolean showToast) {
		if (null == currentChannelDataEntity || channelList.contains(currentChannelDataEntity)) {
			// 通道未选择之前不能添加，也不能重复添加
			if (showToast) {
				if (null == currentChannelDataEntity) {
					ToastUtils.show(mActivity, "请选择已有通道或者创建新通道再添加");
				} else {
					ToastUtils.show(mActivity, "你已经添加过了此通道，无需重复添加");
				}
			}
			return false;
		}
		if (TextUtils.isEmpty(viewHolder.getIcChannelName().getEdtTextValue())) {
			return false;
		}
		String channelID = currentChannelDataEntity.getData().getEcChannelId();
		for (ChannelDataEntity channel : channelList) {
			// 通过通道ID比较来判断是否重复
			if (channelID.equals(channel.getData().getEcChannelId())) {
				if (showToast) {
					ToastUtils.show(mActivity, "你已经添加过了此通道，无需重复添加");
				}
				return false;
			}
			// 通过通道名称与通道类型来判断是否重复
			if (currentChannelDataEntity.getData().getChannelType().equals(channel.getData().getChannelType())
					&& currentChannelDataEntity.getData().getName().equals(channel.getData().getName())) {
				if (showToast) {
					ToastUtils.show(mActivity, "同种类型，同名称通道不能重复添加");
				}
				return false;
			}
		}
		// 通道关联节点
		if (TextUtils.isEmpty(viewHolder.getIcChannelSort().getEdtTextValue())) {
			if (showToast) {
				ToastUtils.show(mActivity, "请输入通道序号");
			}
			return false;
		} else {
			if (channelBll.checkChannelNoIndexRepeat(channelID, viewHolder.getIcChannelSort().getEdtTextValue())) {
				if (showToast) {
					ToastUtils.show(mActivity,
							"通道序号" + viewHolder.getIcChannelSort().getEdtTextValue() + "已经被使用过了，请换一个序号");
				}
				return false;
			}
			/*
			 * EcChannelNodesEntity queryEntity = new EcChannelNodesEntity();
			 * queryEntity.setEcChannelId(channelID);
			 * queryEntity.setNIndex(Integer.parseInt(viewHolder.
			 * getIcChannelSort().getEdtTextValue())); try {
			 * List<EcChannelNodesEntity> excuteQuery =
			 * projectBDUtil.excuteQuery(EcChannelNodesEntity.class,
			 * queryEntity); if (null != excuteQuery && !excuteQuery.isEmpty())
			 * { if (showToast) { ToastUtils.show(mActivity, "通道序号" +
			 * queryEntity.getNIndex() + "已经被使用过了，请换一个序号"); } return false; } }
			 * catch (DbException e) { e.printStackTrace(); }
			 */
		}
		return true;
	}

	private void addChannelInList() {
		if (!canAdd(true)) {
			return;
		}
		// 检查序号是否可用
		EcChannelNodesEntity channelNode = new EcChannelNodesEntity();
		channelNode.setEcChannelId(currentChannelDataEntity.getData().getEcChannelId());
		channelNode.setNIndex(Integer.parseInt(viewHolder.getIcChannelSort().getEdtTextValue()));
		channelNode.setOrgid(currentChannelDataEntity.getData().getOrgid());
		channelNode.setPrjid(currentChannelDataEntity.getData().getPrjid());
		channelNode.setEcChannelNodesId(UUIDGen.randomUUID());
		currentChannelDataEntity.setChannelNode(channelNode);
		channelList.add(currentChannelDataEntity);
		channelsAdapter.notifyDataSetChanged();
		viewHolder.getIcChannelName().setEdtText("");
		viewHolder.getIcChannelSort().setEdtText("");
		currentChannelDataEntity = null;
	}

	/**
	 * 创建通道
	 */
	protected void createChannel() {
		channelBll.createChannel(mActivity, viewHolder.getIscChannelType().getEdtTextValue());
		NamingConventionsFloatWin.getInstance().setChannelType(viewHolder.getIscChannelType().getEdtTextValue());
		BasicInfoControll.mLocationClient.start();
	}

	/**
	 * 选择通道
	 */
	protected void selectChannel() {
		Intent intent = new Intent();
		intent.putExtra("channelType", viewHolder.getIscChannelType().getEdtTextValue());
		intent.setClass(mActivity, ChannelSelectActivity.class);
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, requestCode_select_channel);
	}

	// 所有通道实体
	private ArrayList<ChannelDataEntity> channelList = new ArrayList<>();

	public ArrayList<ChannelDataEntity> getChannelList() {
		return channelList;
	}

	private ChannelDataEntity currentChannelDataEntity = null;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && checkRequestCode(requestCode)) {
			ChannelDataEntity channelData = new ChannelDataEntity();
			Serializable channelType = null;
			EcChannelEntity ecChannelEntity = null;
			String endNodeOid = null;
			if (requestCode == requestCode_select_channel) {// 选择通道
				String channelid = data.getStringExtra(ChannelSelectController.RESULT_DATA_KEY);
				ecChannelEntity = channelBll.findById(EcChannelEntity.class, channelid);
				channelType = (Serializable) ResourceDeviceUtil.getDeviceObj(channelid,
						ecChannelEntity.getChannelType(), channelBll);
				if (null != ecChannelEntity) {
					com.winway.android.edcollection.datacenter.entity.ChannelDataEntity dataEntity = channelCenter
							.getChannel(ecChannelEntity.getEcChannelId());
					if (null != dataEntity.getChannelLines() && !dataEntity.getChannelLines().isEmpty()) {
						// channelData.setAlreadyContactsLines(true);
						channelData.channelLines = dataEntity.getChannelLines();
					}
					channelData.setExist(true);
				}
			} else {
				ecChannelEntity = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
				channelType = data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
				channelLabelEntity = (ChannelLabelEntity) data.getSerializableExtra("channelLabelEntity");
				if (channelLabelEntity != null) {
					channelLabelList.add(channelLabelEntity);
				}
				endNodeOid = data.getStringExtra("endNodeOid");
			}

			if (null == channelType) {
				if (null == ecChannelEntity) {
					ToastUtils.show(mActivity, "无效的通道，请重新选择");
				}
				return;
			}
			if (updateChannelDataCenter != null) {
				// 更新
				updateChannelDataCenter.setData(ecChannelEntity);
				updateChannelDataCenter.setChannelType(channelType);
				updateChannelDataCenter.setEndNodeOid(endNodeOid);
				updateChannelDataCenter = null;
				channelsAdapter.notifyDataSetChanged();
			} else {
				// 增加
				channelData.setData(ecChannelEntity);
				channelData.setChannelType(channelType);
				channelData.setEndNodeOid(endNodeOid);
				currentChannelDataEntity = channelData;
				showSelectChannel(ecChannelEntity);
			}
		}
	}

	// 显示当前创建或则选择的通道
	private void showSelectChannel(EcChannelEntity ecChannelEntity) {
		viewHolder.getIcChannelName().setEdtText(ecChannelEntity.getName());
		// 序号
		String order = "";
		try {
			ChannelIndexCacheEntity cacheEntity = cacheDBUtil.getById(ChannelIndexCacheEntity.class,
					ecChannelEntity.getEcChannelId());
			if (cacheEntity != null) {
				// 相差
				int cha = cacheEntity.getNext() - cacheEntity.getBegin();
				if (cha < 100 && cha > 0) {
					cha = 100;
				}
				if (cha < -100) {
					cha = -100;
				}
				order += (cacheEntity.getNext() + cha);
			} else {
				String sql = "SELECT * FROM EC_CHANNEL_NODES WHERE EC_CHANNEL_ID= '" + ecChannelEntity.getEcChannelId()
						+ "'ORDER BY N_INDEX DESC LIMIT 2";
				BaseDBUtil dbUtil = new BaseDBUtil(mActivity, GlobalEntry.prjDbUrl);
				List<EcChannelNodesEntity> ecChannelNodes = dbUtil.excuteQuery(EcChannelNodesEntity.class, sql);
				if (ecChannelNodes == null || ecChannelNodes.isEmpty() || ecChannelNodes.size() < 1) {
					return;
				}
				if (ecChannelNodes.size() == 1) {
					order = ecChannelNodes.get(0).getNIndex() + 100 + "";
				} else {
					int previous = 0, next = 0;
					for (int i = 0; i < ecChannelNodes.size(); i++) {
						EcChannelNodesEntity ecChannelNode = ecChannelNodes.get(i);
						if (i == 0) {
							next = ecChannelNode.getNIndex();
						} else {
							previous = ecChannelNode.getNIndex();
						}
					}
					int cha = next - previous;
					if (cha < 100 && cha > 0) {
						cha = 100;
					}
					if (cha < -100) {
						cha = -100;
					}
					order = (next + cha) + "";
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		viewHolder.getIcChannelSort().setEdtText(order);
	}

	public static class ChannelDataEntity implements Serializable {
		private static final long serialVersionUID = 1L;
		private EcChannelEntity data;
		private Object channelType;
		private EcChannelNodesEntity channelNode;
		private List<EcChannelLinesEntity> channelLines;

		private boolean edit = false;

		private String endNodeOid = "";

		private boolean exist = false;

		/** 是否已经关联过了线路 */
		private boolean isAlreadyContactsLines = false;

		public String getEndNodeOid() {
			return endNodeOid;
		}

		public void setEndNodeOid(String endNodeOid) {
			this.endNodeOid = endNodeOid;
		}

		public EcChannelEntity getData() {
			return data;
		}

		public void setData(EcChannelEntity data) {
			this.data = data;
		}

		public Object getChannelType() {
			return channelType;
		}

		public void setChannelType(Object channelType) {
			this.channelType = channelType;
		}

		public EcChannelNodesEntity getChannelNode() {
			return channelNode;
		}

		public void setChannelNode(EcChannelNodesEntity channelNode) {
			this.channelNode = channelNode;
		}

		public List<EcChannelLinesEntity> getChannelLines() {
			return channelLines;
		}

		public void setChannelLines(List<EcChannelLinesEntity> channelLines) {
			this.channelLines = channelLines;
		}

		public boolean isEdit() {
			return edit;
		}

		public void setEdit(boolean edit) {
			this.edit = edit;
		}

		public boolean isAlreadyContactsLines() {
			return isAlreadyContactsLines;
		}

		public void setAlreadyContactsLines(boolean isAlreadyContactsLines) {
			this.isAlreadyContactsLines = isAlreadyContactsLines;
		}

		public boolean isExist() {
			return exist;
		}

		public void setExist(boolean exist) {
			this.exist = exist;
		}

	}

	/**
	 * 保存通道
	 * 
	 * @param oid
	 * @throws DbException
	 */
	public void saveChannel(EcNodeEntity node, ArrayList<DitchCable> ditchCableList, List<EcLineEntity> edtLineList) {
		for (ChannelDataEntity channel : channelList) {
			// 起始设备
			if (TextUtils.isEmpty(channel.getData().getStartDeviceNum())) {
				channel.getData().setStartDeviceNum(node.getOid());
			}
			// 终点设备
			if ("是".equals(channel.getEndNodeOid())) {
				channel.getData().setEndDeviceNum(node.getOid());
			} else if ("否".equals(channel.getEndNodeOid())) {
				channel.getData().setEndDeviceNum(null);
			}
			// 关联节点
			channel.getChannelNode().setOid(node.getOid());
			// 关联线路
			List<EcChannelLinesEntity> lineList = channel.getChannelLines();
			if (null == lineList) {
				lineList = new ArrayList<>();
			}

			// 关联线路（关联线路只能新增，不能修改）
			if (null != ditchCableList) {
				for (DitchCable ditchCable : ditchCableList) {
					if (containsLine(channel, ditchCable.getLineId())) {
						continue;
					}
					saveChanelLine(ditchCable.getLineId(), channel, lineList);
				}
			} else if (null != edtLineList && !edtLineList.isEmpty()) {
				// 编辑进来时的线路列表
				for (EcLineEntity ecLineEntity : edtLineList) {
					saveChanelLine(ecLineEntity.getEcLineId(), channel, lineList);
				}
			}
			channel.setChannelLines(lineList);
		}
		// 保存入库
		for (ChannelDataEntity channel : channelList) {
			saveLog(channel);
			saveLabelDatas(channel);
			// 更新通道
			channelBll.saveOrUpdate(channel.getData());
			if (null != channel.getChannelType()) {
				// 通道通道数据关联
				channelBll.saveOrUpdate(channel.getChannelType());
			}
			// 更新节点关联
			channelBll.saveOrUpdate(channel.getChannelNode());

			if (canSaveChannelLines(channel)) {
				// 更新线路关联
				channelBll.saveOrUpdate(channel.getChannelLines());
			}

			if (!channel.isEdit()) {
				try {
					// 保存缓存
					ChannelIndexCacheEntity cache = cacheDBUtil.getById(ChannelIndexCacheEntity.class,
							channel.getData().getEcChannelId());
					if (null == cache) {
						cache = new ChannelIndexCacheEntity();
						cache.setChnnelId(channel.getData().getEcChannelId());
					}
					cache.setBegin(cache.getNext());
					cache.setNext(channel.getChannelNode().getNIndex());
					cacheDBUtil.saveOrUpdate(cache);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 保存截面
			try {
				saveChannelPlaningSection(node, channel);
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveChanelLine(String lineId, ChannelDataEntity channel, List<EcChannelLinesEntity> linesList) {
		if (null != linesList && !linesList.isEmpty()) {
			for (EcChannelLinesEntity ecChannelLinesEntity : linesList) {
				if (ecChannelLinesEntity.getLineId().equals(lineId)) {
					return;
				}
			}
		}
		EcChannelLinesEntity channelLineEntity = new EcChannelLinesEntity();
		// 主键
		channelLineEntity.setEcChannelLinesId(createChannelLineID());
		channelLineEntity.setEcChannelId(channel.getData().getEcChannelId());
		channelLineEntity.setLineId(lineId);// 使用线路id
		channelLineEntity.setOrgid(channel.getData().getOrgid());
		channelLineEntity.setPrjid(channel.getData().getPrjid());
		linesList.add(channelLineEntity);
	}

	private void saveLabelDatas(ChannelDataEntity channel) {
		if (channelLabelList == null || channelLabelList.isEmpty()) {
			return;
		}
		for (ChannelLabelEntity channelLabelEntity : channelLabelList) {
			ObjidAndTableName objIdAndTableName = getObjIdAndTableName(channel.getChannelType());
			if (channelLabelEntity.getEcLineLabelEntityVo() == null) {
				return;
			}
			if (channelLabelEntity.getObjId().equals(objIdAndTableName.objid)) {
				channelLabelEntity.getEcLineLabelEntityVo().setOid(channel.getChannelNode().getOid());
				// 初始化日志
				collectCommonBll.initIsLocal(channelLabelEntity.getEcLineLabelEntityVo().getObjId(),
						TableNameEnum.DLDZBQ.getTableName(), "OBJ_ID");
				// 保存
				collectCommonBll.saveOrUpdate(channelLabelEntity.getEcLineLabelEntityVo());
				// 保存日志
				collectCommonBll.handleDataLog(channelLabelEntity.getEcLineLabelEntityVo().getObjId(),
						TableNameEnum.DLDZBQ.getTableName(), DataLogOperatorType.update, WhetherEnum.NO,
						objIdAndTableName.tableName + "标签操作");
				// 保存通道标签附件
				List<OfflineAttach> comUploadEntityZjjtLabelList = channelLabelEntity.getEcLineLabelEntityVo()
						.getAttr();
				if (comUploadEntityZjjtLabelList != null && comUploadEntityZjjtLabelList.size() > 0) {
					for (OfflineAttach offlineAttach : comUploadEntityZjjtLabelList) {
						// 保存附件
						channelBll.saveOrUpdate(offlineAttach);
					}
				}
			}
		}
	}

	private void saveChannelPlaningSection(EcNodeEntity node, ChannelDataEntity channel) throws DbException {
		ChannelPlaningEntity channelPlaningEntity = ChannelPlaningUtil
				.getChannelPlanning(channel.getData().getEcChannelId(), node.getOid(), projectBDUtil);
		if (null != channelPlaningEntity) {
			// 判断离线附件表是否存在，如果不存在，则创建离线附件表
			try {
				projectBDUtil.getDbUtils().createTableIfNotExist(OfflineAttach.class);
			} catch (DbException e1) {
				e1.printStackTrace();
			}

			OfflineAttachCenter offlineAttachCenter = new OfflineAttachCenter(projectBDUtil);

			// 截面实体（一个截面对应一个截面实体）
			ArrayList<EcChannelSectionEntity> sectionList = new ArrayList<>();
			// 截面背景图片
			ArrayList<OfflineAttach> jmbgAttachList = new ArrayList<>();
			List<EcChannelSectionPointsEntity> sectionPointList = new ArrayList<>();
			for (JMEntity jm : channelPlaningEntity.getJmList()) {
				// 通道截面实体
				EcChannelSectionEntity ecChannelSectionEntity = ChannelPlaningUtil.getEcChannelSection(jm.getUuid());
				if (null == ecChannelSectionEntity) {
					ecChannelSectionEntity = new EcChannelSectionEntity();
					ecChannelSectionEntity.setEcChannelSectionId(UUIDGen.randomUUID());
					ecChannelSectionEntity.setEcChannelId(channel.getData().getEcChannelId());
					ecChannelSectionEntity.setEcNodeId(node.getOid());
					ecChannelSectionEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
					ecChannelSectionEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
					ecChannelSectionEntity.setCjsj(DateUtils.getDate());
				}
				if (BasicInfoControll.ecWorkWellEntityVo != null) {
					// 关联工井
					ecChannelSectionEntity.setResType(ResourceDeviceUtil.getDeviceCode(EcWorkWellEntityVo.class));
					ecChannelSectionEntity.setObjid(BasicInfoControll.ecWorkWellEntityVo.getObjId());
				}
				ecChannelSectionEntity.setRotate((double) jm.getDegress());
				// 将截面的UUID设置成截面实体的主键
				jm.setUuid(ecChannelSectionEntity.getEcChannelSectionId());
				ecChannelSectionEntity.setSectionDesc(GsonUtils.build().toJson(jm));
				ecChannelSectionEntity.setGxsj(DateUtils.getDate());
				sectionList.add(ecChannelSectionEntity);
				List<Point> pointList = jm.getPointList();
				if (pointList != null && !pointList.isEmpty()) {
					for (Point point : pointList) {
						EcChannelSectionPointsEntity ectionPointsEntity = new EcChannelSectionPointsEntity();
						ectionPointsEntity.setCjsj(DateUtils.getDate());
						ectionPointsEntity.setDiameter((double) point.getR());
						ectionPointsEntity.setEcChannelSectionId(ecChannelSectionEntity.getEcChannelSectionId());
						ectionPointsEntity.setEcChannelSectionPointsId(UUIDGen.randomUUID());
						ectionPointsEntity.setGxsj(DateUtils.getDate());
						ectionPointsEntity.setIdxNo(point.getNo());
						ectionPointsEntity.setIsPlugging(point.getIsPlugging());
						ectionPointsEntity.setPhaseSeq(point.getPhaseSeq());
						ectionPointsEntity.setLineId(point.getLineId());
						ectionPointsEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
						ectionPointsEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
						sectionPointList.add(ectionPointsEntity);
					}
				}
				// 已经保存入库的附件数据
				List<OfflineAttach> attachList = offlineAttachCenter
						.getOfflineAttachListByWorkNo(ecChannelSectionEntity.getEcChannelSectionId());

				OfflineAttach jmbgAttach = null;// 截面背景
				if (!TextUtils.isEmpty(jm.getBackgroundImage())) {
					if (null != attachList && !attachList.isEmpty()) {
						for (OfflineAttach offlineAttach : attachList) {
							if (offlineAttach.getFileName().contains("_JMBG")) {
								jmbgAttach = offlineAttach;
								if (!TextUtils.isEmpty(offlineAttach.getFilePath())
										&& !jm.getBackgroundImage().equals(offlineAttach.getFilePath())) {
									// 删除旧的图片
									FileUtil.deleteFileByFilePath(offlineAttach.getFilePath());
								}
								break;
							}
						}
					}
					if (null == jmbgAttach) {
						jmbgAttach = new OfflineAttach(UUIDGen.randomUUID());
					}
					// 截面背景
					jmbgAttach.setFilePath(jm.getBackgroundImage());
					jmbgAttach.setIsUploaded(WhetherEnum.NO.getValue());
					jmbgAttach.setFileName(FileUtil.getFileName(jm.getBackgroundImage()));
					jmbgAttach.setWorkNo(ecChannelSectionEntity.getEcChannelSectionId());
					jmbgAttach.setOwnerCode(TableNameEnum.EC_CHANNEL_SECTION.getTableName());
					jmbgAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
					jmbgAttachList.add(jmbgAttach);
				}
			}

			// 保存数据入库
			try {
				projectBDUtil.saveOrUpdate(sectionList);
				if (null != jmbgAttachList) {
					projectBDUtil.saveOrUpdate(jmbgAttachList);
				}
				if (sectionPointList != null) {
					projectBDUtil.saveOrUpdate(sectionPointList);
				}
			} catch (DbException e) {
				e.printStackTrace();
			}
			if (null != sectionPointList && !sectionPointList.isEmpty()) {
				for (EcChannelSectionPointsEntity pointsEntity : sectionPointList) {// 管孔
					if (null == collectCommonBll.findById(EcChannelSectionPointsEntity.class,
							pointsEntity.getEcChannelSectionPointsId())) {
						// 新增
						collectCommonBll.handleDataLog(pointsEntity.getEcChannelSectionPointsId(),
								TableNameEnum.EC_CHANNEL_SECTION_POINT.getTableName(), DataLogOperatorType.Add,
								WhetherEnum.NO, "插入通道截面管孔信息" + pointsEntity.getEcChannelSectionId(), true);
					} else {
						// 编辑
						collectCommonBll.handleDataLog(pointsEntity.getEcChannelSectionPointsId(),
								TableNameEnum.EC_CHANNEL_SECTION_POINT.getTableName(), DataLogOperatorType.update,
								WhetherEnum.NO, "通道截面管孔信息：" + pointsEntity.getEcChannelSectionPointsId(), false);
					}

				}
			}
			// 保存日志信息
			if (null != sectionList && !sectionList.isEmpty()) {// 截面
				for (EcChannelSectionEntity ecChannelSectionEntity : sectionList) {
					if (null == collectCommonBll.findById(EcChannelSectionEntity.class,
							ecChannelSectionEntity.getEcChannelSectionId())) {
						// 新增
						collectCommonBll.handleDataLog(ecChannelSectionEntity.getEcChannelSectionId(),
								TableNameEnum.EC_CHANNEL_SECTION.getTableName(), DataLogOperatorType.Add,
								WhetherEnum.NO, "插入通道截面数据" + ecChannelSectionEntity.getEcChannelSectionId(), true);
					} else {
						// 编辑
						collectCommonBll.handleDataLog(ecChannelSectionEntity.getEcChannelSectionId(),
								TableNameEnum.EC_CHANNEL_SECTION.getTableName(), DataLogOperatorType.update,
								WhetherEnum.NO, "通道截面数据" + ecChannelSectionEntity.getEcChannelSectionId(), false);
					}

				}
			}

		}
	}

	// 判断线路是否已经存在于通道中
	boolean containsLine(ChannelDataEntity channel, String lineId) {
		if (channel.getChannelLines() != null && !channel.getChannelLines().isEmpty()) {
			for (EcChannelLinesEntity channelLine : channel.getChannelLines()) {
				if (lineId.equals(channelLine.getLineId())) {
					return true;
				}
			}
		}
		// 去数据库里面找
		try {
			EcChannelLinesEntity queryEntity = new EcChannelLinesEntity();
			queryEntity.setEcChannelId(channel.getData().getEcChannelId());
			queryEntity.setLineId(lineId);
			List<EcChannelLinesEntity> excuteQuery = projectBDUtil.excuteQuery(EcChannelLinesEntity.class, queryEntity);
			if (null != excuteQuery && !excuteQuery.isEmpty()) {
				return true;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 生成唯一的通道线路ID
	String createChannelLineID() {
		return UUIDGen.randomUUID();
	}

	/**
	 * 保存通道相关日志
	 * 
	 * @param channel
	 */
	public void saveLog(ChannelDataEntity channel) {
		ObjidAndTableName objIdAndTableName = getObjIdAndTableName(channel.getChannelType());
		if (null == objIdAndTableName) {
			return;
		}
		// 关联通道与通道数据日志
		if (null == collectCommonBll.findById(EcChannelEntity.class, channel.getData().getEcChannelId())) {
			// 如果通道不存在，则必定是新增操作
			// 通道主表
			collectCommonBll.handleDataLog(channel.getData().getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
					DataLogOperatorType.Add, WhetherEnum.NO, "插入通道数据" + channel.getData().getEcChannelId(), true);
			// 通道设施表
			collectCommonBll.handleDataLog(objIdAndTableName.objid, objIdAndTableName.tableName,
					DataLogOperatorType.Add, WhetherEnum.NO, "插入具体的通道数据" + objIdAndTableName.objid, true);
		} else {
			// // 可能新增，也可能是修改
			// collectCommonBll.handleDataLog3(channel.getData().getEcChannelId(),
			// TableNameEnum.EC_CHANNEL.getTableName(),
			// WhetherEnum.NO, "插入通道数据" + channel.getData().getEcChannelId());
			// collectCommonBll.handleDataLog3(objIdAndTableName.objid,
			// objIdAndTableName.tableName, WhetherEnum.NO,
			// "插入具体的通道数据" + objIdAndTableName.objid);

			// 主表已经存在，说明是做编辑操作
			collectCommonBll.handleDataLog(channel.getData().getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
					DataLogOperatorType.update, WhetherEnum.NO, "编辑通道：" + channel.getData().getEcChannelId(), false);
			collectCommonBll.handleDataLog(objIdAndTableName.objid, objIdAndTableName.tableName,
					DataLogOperatorType.update, WhetherEnum.NO, "编辑通道设施：" + objIdAndTableName.objid, false);
		}

		// 通道节点日志
		if (null == collectCommonBll.findById(EcChannelNodesEntity.class,
				channel.getChannelNode().getEcChannelNodesId())) {
			// 新增
			collectCommonBll.handleDataLog(channel.getChannelNode().getEcChannelNodesId(),
					TableNameEnum.EC_CHANNEL_NODE.getTableName(), DataLogOperatorType.Add, WhetherEnum.NO,
					"插入通道节点数据：" + channel.getChannelNode().getEcChannelId(), true);
		} else {
			// collectCommonBll.handleDataLog3(channel.getChannelNode().getEcChannelNodesId(),
			// TableNameEnum.EC_CHANNEL_NODE.getTableName(), WhetherEnum.NO,
			// "插入通道关联节点数据" + channel.getChannelNode().getEcChannelId());
			// 编辑
			collectCommonBll.handleDataLog(channel.getChannelNode().getEcChannelNodesId(),
					TableNameEnum.EC_CHANNEL_NODE.getTableName(), DataLogOperatorType.update, WhetherEnum.NO,
					"编辑通道节点数据：" + channel.getChannelNode().getEcChannelId(), false);
		}
		// 通道敷设线路
		if (canSaveChannelLines(channel)) {
			// 通道线路关联表日志
			for (EcChannelLinesEntity channelLine : channel.getChannelLines()) {
				// 新增
				if (null == collectCommonBll.findById(EcChannelLinesEntity.class, channelLine.getEcChannelLinesId())) {
					collectCommonBll.handleDataLog(channelLine.getEcChannelLinesId(),
							TableNameEnum.EC_CHANNEL_LINE.getTableName(), DataLogOperatorType.Add, WhetherEnum.NO,
							"插入通道关联线路数据：" + channelLine.getEcChannelLinesId(), true);
				} else {
					// 编辑
					collectCommonBll.handleDataLog(channelLine.getEcChannelLinesId(),
							TableNameEnum.EC_CHANNEL_LINE.getTableName(), DataLogOperatorType.update, WhetherEnum.NO,
							"编辑通道关联线路数据：" + channelLine.getEcChannelLinesId(), false);
				}
			}
		}
	}

	/**
	 * 是否能保存通道线路（关联通道线路）
	 * 
	 * @param channel
	 * @return
	 */
	boolean canSaveChannelLines(ChannelDataEntity channel) {
		// 编辑状态下不能保存关联线路
		// if (GlobalEntry.editNode) {
		// return false;
		// }
		// 如果已经保存过线路了，则就不必再保存了
		if (channel.isAlreadyContactsLines) {
			return false;
		}

		// if (channel.isExist() || channelList.size() <= 1) {
		// // 只有在通道存在的时候，才会关联线路，或者是只创建一条通道的时候
		// return true;
		// }
		return true;
	}

	public static ObjidAndTableName getObjIdAndTableName(Object obj) {
		if (obj instanceof EcChannelDlzmEntity) {
			return new ObjidAndTableName(TableNameEnum.EC_CHANNEL_DLZM.getTableName(),
					((EcChannelDlzmEntity) obj).getObjId());
		}
		if (obj instanceof EcChannelDlgdEntity) {
			return new ObjidAndTableName(TableNameEnum.EC_CHANNEL_DLGD.getTableName(),
					((EcChannelDlgdEntity) obj).getObjId());
		}
		if (obj instanceof EcChannelDlqEntity) {
			return new ObjidAndTableName(TableNameEnum.EC_CHANNEL_DLQ.getTableName(),
					((EcChannelDlqEntity) obj).getObjId());
		}
		if (obj instanceof EcChannelDlsdEntity) {
			return new ObjidAndTableName(TableNameEnum.EC_CHANNEL_DLSD.getTableName(),
					((EcChannelDlsdEntity) obj).getObjId());
		}
		if (obj instanceof EcChannelDgEntity) {
			return new ObjidAndTableName(TableNameEnum.EC_CHANNEL_DG.getTableName(),
					((EcChannelDgEntity) obj).getObjId());
		}
		if (obj instanceof EcChannelDlgEntity) {
			return new ObjidAndTableName(TableNameEnum.EC_CHANNEL_DLG.getTableName(),
					((EcChannelDlgEntity) obj).getObjId());
		}
		if (obj instanceof EcChannelJkEntity) {
			return new ObjidAndTableName(TableNameEnum.EC_CHANNEL_JK.getTableName(),
					((EcChannelJkEntity) obj).getObjId());
		}
		if (obj instanceof EcChannelDlcEntity) {
			return new ObjidAndTableName(TableNameEnum.EC_CHANNEL_DLC.getTableName(),
					((EcChannelDlcEntity) obj).getObjId());
		}
		return null;
	}

	public static final class ObjidAndTableName {
		public String tableName;
		public String objid;

		public ObjidAndTableName(String tableName, String objid) {
			super();
			this.tableName = tableName;
			this.objid = objid;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 地图选点，保存缓存
		if (FragmentEntry.isSelectMap) {
			FragmentEntry.getInstance().channelList.clear();
			ListUtil.copyList(FragmentEntry.getInstance().channelList, channelList);
		} else {
			// 移除内存中的通道截面
			ChannelPlaningUtil.clean();
			// 移除内存中的历史截面工具
			JMsWindowDisplayUtil.getInstance().recycle();
		}
	}
}
