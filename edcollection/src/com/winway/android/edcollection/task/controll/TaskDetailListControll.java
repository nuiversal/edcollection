package com.winway.android.edcollection.task.controll;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.adding.activity.ChannelDGActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLCActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLGActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLGDActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLQActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLSDActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLZMActivity;
import com.winway.android.edcollection.adding.activity.ChannelJkActivity;
import com.winway.android.edcollection.adding.activity.DistributionRoomActivity;
import com.winway.android.edcollection.adding.activity.DlfzxActivity;
import com.winway.android.edcollection.adding.activity.DydlfzxActivity;
import com.winway.android.edcollection.adding.activity.DypdxActivity;
import com.winway.android.edcollection.adding.activity.HwgActivity;
import com.winway.android.edcollection.adding.activity.IntermediateHeadActivity;
import com.winway.android.edcollection.adding.activity.KgzActivity;
import com.winway.android.edcollection.adding.activity.LableActivity;
import com.winway.android.edcollection.adding.activity.SubstationActivity;
import com.winway.android.edcollection.adding.activity.TowerActivity;
import com.winway.android.edcollection.adding.activity.TransformerActivity;
import com.winway.android.edcollection.adding.activity.WorkWellActivity;
import com.winway.android.edcollection.adding.activity.XsbdzActivity;
import com.winway.android.edcollection.adding.util.TableDataUtil;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.util.ResourceDeviceUtil;
import com.winway.android.edcollection.base.util.ResourceEnumUtil;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.edcollection.project.entity.EmTaskItemEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDypdxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcKgzEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.edcollection.project.vo.EcXsbdzEntityVo;
import com.winway.android.edcollection.task.adapter.TaskDetailListAdapter;
import com.winway.android.edcollection.task.entity.TaskDetailItemData;
import com.winway.android.edcollection.task.entity.TaskDetailResult;
import com.winway.android.edcollection.task.entity.TaskDeviceEntity;
import com.winway.android.edcollection.task.entity.TaskStateTypeEnum;
import com.winway.android.edcollection.task.util.UpdateDeviceAndAttr;
import com.winway.android.edcollection.task.viewholder.TaskDetailViewHolder;
import com.winway.android.ewidgets.net.NetManager;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ToastUtils;

/**
 * 任务详情
 * 
 * @author zgq
 *
 */
public class TaskDetailListControll extends BaseControll<TaskDetailViewHolder> {
	private BaseDBUtil dbUtil = null;
	private TaskDetailListAdapter adapter;
	private List<TaskDetailItemData> dataList;
	private String dbUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
	private DataCenterImpl dataCenter = null;
	private EmTaskItemEntity taskItemEntity;
	private ComUploadBll comUploadBll;
	private List<OfflineAttach> offlineAttachs;
	private CommonBll commonBll;
	private EcChannelEntity channelEntity;
	private UpdateDeviceAndAttr updateDeviceAndAttr;
	// 路径点设备实体
	private EcWorkWellEntityVo ecWorkWellEntity;
	private EcSubstationEntityVo ecSubstationEntityVo;
	private EcXsbdzEntityVo ecXsbdzEntityVo;
	private EcKgzEntityVo ecKgzEntityVo;
	private EcTowerEntityVo ecTowerEntityVo;
	private EcTransformerEntityVo ecTransformerEntityVo;
	private EcDistributionRoomEntityVo ecDistributionRoomEntityVo;
	private EcDypdxEntityVo ecDypdxEntityVo;
	// 线路设备实体
	private EcHwgEntityVo ecHwgEntityVo;
	private EcLineLabelEntityVo ecLineLabelEntityVo;
	private EcDlfzxEntityVo ecDlfzxEntityVo;
	private EcDydlfzxEntityVo ecDydlfzxEntityVo;
	private EcMiddleJointEntityVo ecMiddleJointEntityVo;
	// 通道设备实体
	private EcChannelDgEntity channelDgEntity;
	private EcChannelDlgdEntity channelDlgdEntity;
	private EcChannelDlgEntity channelDlgEntity;
	private EcChannelDlqEntity channelDlqEntity;
	private EcChannelDlsdEntity channelDlsdEntity;
	private EcChannelDlzmEntity channelDlzmEntity;
	private EcChannelJkEntity channelJkEntity;
	private EcChannelDlcEntity channelDlcEntity;
	// 路径占设备请求编号
	private int requestCode_substation = 1;
	private int requestCode_tower = 2;
	private int requestCode_transformer = 3;
	private int requestCode_distributionRoom = 4;
	private int requestCode_workWell = 5;
	private int requestCode_xsbdz = 6;
	private int requestCode_kgz = 7;
	private int requestCode_dypdx = 8;
	// 线路设备请求编号
	private int requestCode_hwg = 9;
	private int requestCode_dzbq = 10;
	private int requestCode_dlfzx = 11;
	private int requestCode_dydlfzx = 12;
	private int requestCode_zjjt = 13;

	private int requestCode_dg = 14;
	private int requestCode_gd = 15;
	private int requestCode_pg = 16;
	private int requestCode_qj = 17;
	private int requestCode_sd = 18;
	private int requestCode_zm = 19;
	private int requestCode_jk = 20;
	private int requestCode_dlc = 21;

	private int taskDetailItemPosition;
	boolean isReturn = true;

	@Override
	public void init() {
		comUploadBll = new ComUploadBll(mActivity, dbUrl);
		dbUtil = new BaseDBUtil(mActivity, new File(dbUrl));
		commonBll = new CommonBll(mActivity, dbUrl);
		if (TableDataUtil.treeDataCenter == null) {
			TableDataUtil.treeDataCenter = new DataCenterImpl(mActivity, dbUrl);
		}
		dataCenter = TableDataUtil.treeDataCenter;
		updateDeviceAndAttr = new UpdateDeviceAndAttr(mActivity, dbUrl);
		String taskID = getIntent().getExtras().getString("taskId");
		if (!getDetailFromDatabase(taskID)) {
			getDetailFromNetWork(taskID);
		}
		initEvent();
	}

	/**
	 * 根据设备id拿到附件
	 * 
	 * @param id
	 */
	private void getAttr(String id) {
		String where = "WORK_NO ='" + id + "'";
		offlineAttachs = comUploadBll.queryByExpr(OfflineAttach.class, where);
	}

	private void initEvent() {
		viewHolder.getLvTasklist().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Object deviceObj = dataList.get(position).getEntity();
				if (deviceObj == null) {
					ToastUtils.show(mActivity, "任务信息错误！");
					return;
				}
				taskDetailItemPosition = position;
				if (deviceObj instanceof EcWorkWellEntity) { // 电缆井
					String wellId = ((EcWorkWellEntity) deviceObj).getObjId();
					getAttr(wellId);
					lineAndNodeDeviceStartActivity(deviceObj, WorkWellActivity.class, requestCode_workWell,
							offlineAttachs);
				} else if (deviceObj instanceof EcSubstationEntity) { // 变电站
					String subId = ((EcSubstationEntity) deviceObj).getEcSubstationId();
					getAttr(subId);
					lineAndNodeDeviceStartActivity(deviceObj, SubstationActivity.class, requestCode_substation,
							offlineAttachs);
				} else if (deviceObj instanceof EcKgzEntity) { // 开关站
					String kgzId = ((EcKgzEntity) deviceObj).getObjId();
					getAttr(kgzId);
					lineAndNodeDeviceStartActivity(deviceObj, KgzActivity.class, requestCode_kgz, offlineAttachs);
				} else if (deviceObj instanceof EcDistributionRoomEntity) { // 配电室
					String pdsId = ((EcDistributionRoomEntity) deviceObj).getObjId();
					getAttr(pdsId);
					lineAndNodeDeviceStartActivity(deviceObj, DistributionRoomActivity.class,
							requestCode_distributionRoom, offlineAttachs);
				} else if (deviceObj instanceof EcHwgEntity) { // 环网柜
					String hwgId = ((EcHwgEntityVo) deviceObj).getObjId();
					getAttr(hwgId);
					lineAndNodeDeviceStartActivity(deviceObj, HwgActivity.class, requestCode_hwg, offlineAttachs);
				} else if (deviceObj instanceof EcXsbdzEntity) { // 箱式变电站
					String xsbdzId = ((EcXsbdzEntity) deviceObj).getObjId();
					getAttr(xsbdzId);
					lineAndNodeDeviceStartActivity(deviceObj, XsbdzActivity.class, requestCode_xsbdz, offlineAttachs);
				} else if (deviceObj instanceof EcDlfzxEntity) { // 电缆分支箱
					String dlfzxId = ((EcDlfzxEntityVo) deviceObj).getObjId();
					getAttr(dlfzxId);
					lineAndNodeDeviceStartActivity(deviceObj, DlfzxActivity.class, requestCode_dlfzx, offlineAttachs);
				} else if (deviceObj instanceof EcDydlfzxEntity) { // 低压电缆分支箱
					String dydlfzxId = ((EcDydlfzxEntity) deviceObj).getObjId();
					getAttr(dydlfzxId);
					lineAndNodeDeviceStartActivity(deviceObj, DydlfzxActivity.class, requestCode_dydlfzx,
							offlineAttachs);
				} else if (deviceObj instanceof EcDypdxEntity) { // 低压配电箱
					String dypdxId = ((EcDypdxEntity) deviceObj).getObjId();
					getAttr(dypdxId);
					lineAndNodeDeviceStartActivity(deviceObj, DypdxActivity.class, requestCode_dypdx, offlineAttachs);
				} else if (deviceObj instanceof EcTransformerEntity) { // 变压器
					String byqId = ((EcTransformerEntity) deviceObj).getObjId();
					getAttr(byqId);
					lineAndNodeDeviceStartActivity(deviceObj, TransformerActivity.class, requestCode_transformer,
							offlineAttachs);
				} else if (deviceObj instanceof EcTowerEntity) { // 杆塔
					String gtId = ((EcTowerEntity) deviceObj).getObjId();
					getAttr(gtId);
					lineAndNodeDeviceStartActivity(deviceObj, TowerActivity.class, requestCode_tower, offlineAttachs);
				} else if (deviceObj instanceof EcMiddleJointEntity) { // 中间接头
					String zjjtId = ((EcMiddleJointEntityVo) deviceObj).getObjId();
					getAttr(zjjtId);
					lineAndNodeDeviceStartActivity(deviceObj, IntermediateHeadActivity.class, requestCode_zjjt,
							offlineAttachs);
				} else if (deviceObj instanceof EcLineLabelEntity) { // 电子标签
					String dzbqId = ((EcLineLabelEntityVo) deviceObj).getObjId();
					getAttr(dzbqId);
					lineAndNodeDeviceStartActivity(deviceObj, LableActivity.class, requestCode_dzbq, offlineAttachs);
				} else if (deviceObj instanceof EcChannelEntity) { // 通道
					// 通道类型
					String channelType = ((EcChannelEntity) deviceObj).getChannelType();
					// 通道设备名称
					String devicename = ResourceEnumUtil.get(channelType).getName();
					String channelId = ((EcChannelEntity) deviceObj).getEcChannelId();
					if (devicename.equals(ResourceEnum.TLG.getName())) {// 顶管
						channelDgEntity = commonBll.findById(EcChannelDgEntity.class, channelId);
						channelDeviceStartActivity(channelDgEntity, deviceObj, ChannelDGActivity.class, requestCode_dg);
					} else if (devicename.equals(ResourceEnum.GD.getName())) {// 沟道
						channelDlgEntity = commonBll.findById(EcChannelDlgEntity.class, channelId);
						channelDeviceStartActivity(channelDlgEntity, deviceObj, ChannelDLGActivity.class,
								requestCode_gd);
					} else if (devicename.equals(ResourceEnum.PG.getName())) {// 排管
						channelDlgdEntity = commonBll.findById(EcChannelDlgdEntity.class, channelId);
						channelDeviceStartActivity(channelDlgdEntity, deviceObj, ChannelDLGDActivity.class,
								requestCode_pg);
					} else if (devicename.equals(ResourceEnum.QJ.getName())) {// 桥架
						channelDlqEntity = commonBll.findById(EcChannelDlqEntity.class, channelId);
						channelDeviceStartActivity(channelDlqEntity, deviceObj, ChannelDLQActivity.class,
								requestCode_qj);
					} else if (devicename.equals(ResourceEnum.SD.getName())) {// 隧道
						channelDlsdEntity = commonBll.findById(EcChannelDlsdEntity.class, channelId);
						channelDeviceStartActivity(channelDlsdEntity, deviceObj, ChannelDLSDActivity.class,
								requestCode_sd);
					} else if (devicename.equals(ResourceEnum.ZM.getName())) {// 直埋
						channelDlzmEntity = commonBll.findById(EcChannelDlzmEntity.class, channelId);
						channelDeviceStartActivity(channelDlzmEntity, deviceObj, ChannelDLZMActivity.class,
								requestCode_zm);
					} else if (devicename.equals(ResourceEnum.JKXL.getName())) {// 架空
						channelJkEntity = commonBll.findById(EcChannelJkEntity.class, channelId);
						channelDeviceStartActivity(channelJkEntity, deviceObj, ChannelJkActivity.class, requestCode_jk);
					} else if (devicename.equals(ResourceEnum.DLC.getName())) {//电缆槽
						channelDlcEntity = commonBll.findById(EcChannelDlcEntity.class, channelId);
						channelDeviceStartActivity(channelDlcEntity, deviceObj, ChannelDLCActivity.class, requestCode_dlc);
					}
				} else if (deviceObj instanceof EcChannelDgEntity) {// 顶管
					EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
							((EcChannelDgEntity) deviceObj).getObjId());
					channelDeviceStartActivity(deviceObj, ecChannelEntity, ChannelDGActivity.class, requestCode_gd);
				} else if (deviceObj instanceof EcChannelDlqEntity) {// 桥架
					EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
							((EcChannelDlqEntity) deviceObj).getObjId());
					channelDeviceStartActivity(deviceObj, ecChannelEntity, ChannelDLQActivity.class, requestCode_qj);
				} else if (deviceObj instanceof EcChannelDlsdEntity) {// 隧道
					EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
							((EcChannelDlsdEntity) deviceObj).getObjId());
					channelDeviceStartActivity(deviceObj, ecChannelEntity, ChannelDLSDActivity.class, requestCode_sd);
				} else if (deviceObj instanceof EcChannelDlzmEntity) {// 直埋
					EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
							((EcChannelDlzmEntity) deviceObj).getObjId());
					channelDeviceStartActivity(deviceObj, ecChannelEntity, ChannelDLZMActivity.class, requestCode_zm);
				} else if (deviceObj instanceof EcChannelDlcEntity) {//电缆槽
					EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class, ((EcChannelDlcEntity)deviceObj).getObjId());
					channelDeviceStartActivity(deviceObj, ecChannelEntity, ChannelDLCActivity.class, requestCode_dlc);
				} else if (deviceObj instanceof EcChannelDlgdEntity) {// 排管
					EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
							((EcChannelDlgdEntity) deviceObj).getObjId());
					channelDeviceStartActivity(deviceObj, ecChannelEntity, ChannelDLGDActivity.class, requestCode_pg);
				} else if (deviceObj instanceof EcChannelDlgEntity) {// 沟道
					EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
							((EcChannelDlgEntity) deviceObj).getObjId());
					channelDeviceStartActivity(deviceObj, ecChannelEntity, ChannelDLGActivity.class, requestCode_gd);
				} else if (deviceObj instanceof EcChannelJkEntity) {// 架空
					EcChannelEntity ecChannelEntity = commonBll.findById(EcChannelEntity.class,
							((EcChannelJkEntity) deviceObj).getObjId());
					channelDeviceStartActivity(deviceObj, ecChannelEntity, ChannelJkActivity.class, requestCode_jk);
				}
			}
		});
	}

	/**
	 * 通道设备页面跳转操作
	 * 
	 * @param deviceObj
	 *            通道设备实体
	 * @param channelObj
	 *            通道实体
	 * @param deviceClass
	 *            跳转目标activity
	 * @param requestCode
	 *            请求编号
	 */
	private void channelDeviceStartActivity(Object deviceObj, Object channelObj, Class<?> deviceClass,
			int requestCode) {
		Bundle bundle = new Bundle();
		Intent intent = new Intent(mActivity, deviceClass);
		bundle.putSerializable(GlobalEntry.INTENT_KEY_CHANNEL_DATA, (Serializable) deviceObj);
		bundle.putSerializable(GlobalEntry.INTENT_KEY_CHANNEL, (Serializable) channelObj);
		intent.putExtras(bundle);
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, requestCode);
	}

	/**
	 * 线路设备及路径点设备页面跳转操作
	 * 
	 * @param deviceObj
	 *            设备实体
	 * @param activity
	 *            跳转目标activity
	 * @param requestCode
	 *            请求编号
	 * @param offlineAttachs
	 *            附件
	 */
	private void lineAndNodeDeviceStartActivity(Object deviceObj, Class<?> activity, int requestCode,
			List<OfflineAttach> offlineAttachs) {
		TaskDeviceEntity deviceEntity = new TaskDeviceEntity();
		deviceEntity.setComUploadEntityList(offlineAttachs);
		deviceEntity.setDevice(deviceObj);
		Bundle bundle = new Bundle();
		Intent intent = new Intent(mActivity, activity);
		bundle.putSerializable(TaskDeviceEntity.INTENT_KEY, deviceEntity);
		intent.putExtras(bundle);
		mActivity.startActivityForResult(intent, requestCode);
	}

	private Handler h = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();
		}
	};

	private void initDataListDevice() {
		new Thread() {
			public void run() {
				for (TaskDetailItemData taskDetailItem : dataList) {
					Object deviceObj = ResourceDeviceUtil.getDeviceObj(taskDetailItem.getItem().getObjId(),
							taskDetailItem.getItem().getDeviceType() + "", dbUtil);
					taskDetailItem.setEntity(deviceObj);
				}
				h.sendEmptyMessage(0);
			}
		}.start();
	}

	boolean getDetailFromDatabase(String taskID) {
		try {
			EmTaskItemEntity emTaskItemEntity = new EmTaskItemEntity();
			emTaskItemEntity.setEmTasksId(taskID);
			List<EmTaskItemEntity> list = dbUtil.excuteQuery(EmTaskItemEntity.class, emTaskItemEntity);
			if (null != list && !list.isEmpty()) {
				dataList = new ArrayList<TaskDetailItemData>();
				for (EmTaskItemEntity TaskItemEntity : list) {
					TaskDetailItemData item = new TaskDetailItemData();
					item.setEntity(null);
					item.setItem(TaskItemEntity);
					dataList.add(item);
				}
				initTaskDetailListView(dataList);
				return true;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return false;
	}

	void getDetailFromNetWork(String taskID) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "em_gettaskitems");
		params.put("credit", GlobalEntry.loginResult.getCredit());
		params.put("taskid", taskID);
		BaseService netService = NetManager.getLineService();
		try {
			netService.getRequest(GlobalEntry.dataServerUrl, params, null, TaskDetailResult.class,
					new BaseService.RequestCallBack<TaskDetailResult>() {
						@Override
						public void error(int code, BaseService.WayFrom from) {
							ToastUtils.show(mActivity, "未知错误");
						}

						@Override
						public void callBack(TaskDetailResult request, BaseService.WayFrom from) {
							if (null == request || request.getList() == null || request.getList().isEmpty()) {
								return;
							}
							ArrayList<EmTaskItemEntity> list = new ArrayList<EmTaskItemEntity>();
							for (TaskDetailItemData data : request.getList()) {
								if (null == data.getItem()) {
									continue;
								}
								list.add(data.getItem());
							}
							try {
								dbUtil.save(list);
							} catch (DbException e) {
								e.printStackTrace();
							}
							// 刷新
							initTaskDetailListView(request.getList());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initTaskDetailListView(List<TaskDetailItemData> dataList) {
		this.dataList = dataList;
		adapter = new TaskDetailListAdapter(mActivity, (ArrayList<TaskDetailItemData>) dataList);
		viewHolder.getLvTasklist().setAdapter(adapter);
		initDataListDevice();
		adapter.setDataCenter(dataCenter, dbUtil, commonBll);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == requestCode_workWell) {// 工井
				ecWorkWellEntity = (EcWorkWellEntityVo) data.getSerializableExtra("gj");
				dataList.get(taskDetailItemPosition).setEntity(ecWorkWellEntity);
				if (ecWorkWellEntity != null) {
					getItemState(data);
					updateDeviceAndAttr.updateGjDatas(ecWorkWellEntity, taskItemEntity);
					ecWorkWellEntity = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_substation) { // 变电站
				ecSubstationEntityVo = (EcSubstationEntityVo) data.getSerializableExtra("sub");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecSubstationEntityVo);
				if (ecSubstationEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateBdzDatas(ecSubstationEntityVo, taskItemEntity);
					ecSubstationEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_xsbdz) { // 箱式变电站
				ecXsbdzEntityVo = (EcXsbdzEntityVo) data.getSerializableExtra("xsbdz");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecXsbdzEntityVo);
				if (ecXsbdzEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateXsbdzDatas(ecXsbdzEntityVo, taskItemEntity);
					ecXsbdzEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_kgz) { // 开关站
				ecKgzEntityVo = (EcKgzEntityVo) data.getSerializableExtra("kgz");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecKgzEntityVo);
				if (ecKgzEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateKgzDatas(ecKgzEntityVo, taskItemEntity);
					ecKgzEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_tower) { // 杆塔
				ecTowerEntityVo = (EcTowerEntityVo) data.getSerializableExtra("tower");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecTowerEntityVo);
				if (ecTowerEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateGtDatas(ecTowerEntityVo, taskItemEntity);
					ecTowerEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_transformer) { // 变压器
				ecTransformerEntityVo = (EcTransformerEntityVo) data.getSerializableExtra("transformer");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecTransformerEntityVo);
				if (ecTransformerEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateByqDatas(ecTransformerEntityVo, taskItemEntity);
					ecTransformerEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_distributionRoom) { // 配电室
				ecDistributionRoomEntityVo = (EcDistributionRoomEntityVo) data.getSerializableExtra("distributionRoom");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecDistributionRoomEntityVo);
				if (ecDistributionRoomEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updatePdsDatas(ecDistributionRoomEntityVo, taskItemEntity);
					ecDistributionRoomEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_dypdx) { // 低压配电箱
				ecDypdxEntityVo = (EcDypdxEntityVo) data.getSerializableExtra("dypdx");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecDypdxEntityVo);
				if (ecDypdxEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDypdxDatas(ecDypdxEntityVo, taskItemEntity);
					ecDypdxEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_hwg) { // 环网柜
				ecHwgEntityVo = (EcHwgEntityVo) data.getSerializableExtra("hwg");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecHwgEntityVo);
				if (ecHwgEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateHwgDatas(ecHwgEntityVo, taskItemEntity);
					ecHwgEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_dzbq) { // 电子标签
				ecLineLabelEntityVo = (EcLineLabelEntityVo) data.getSerializableExtra("lable");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecLineLabelEntityVo);
				if (ecLineLabelEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDzbqDatas(ecLineLabelEntityVo, taskItemEntity);
					ecLineLabelEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_dlfzx) { // 电缆分支箱
				ecDlfzxEntityVo = (EcDlfzxEntityVo) data.getSerializableExtra("dlfzx");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecDlfzxEntityVo);
				if (ecDlfzxEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDlfzxDatas(ecDlfzxEntityVo, taskItemEntity);
					ecDlfzxEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_dydlfzx) { // 低压电缆分支箱
				ecDydlfzxEntityVo = (EcDydlfzxEntityVo) data.getSerializableExtra("dydlfzx");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecDydlfzxEntityVo);
				if (ecDydlfzxEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDydlfzxDatas(ecDydlfzxEntityVo, taskItemEntity);
					ecDydlfzxEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_zjjt) { // 中间接头
				ecMiddleJointEntityVo = (EcMiddleJointEntityVo) data.getSerializableExtra("zjjt");
				// 把当前实体的数据放进数据集合中
				dataList.get(taskDetailItemPosition).setEntity(ecMiddleJointEntityVo);
				if (ecMiddleJointEntityVo != null) {
					getItemState(data);
					updateDeviceAndAttr.updateZjjtDatas(ecMiddleJointEntityVo, taskItemEntity);
					ecMiddleJointEntityVo = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_dg) { // 顶管、拖拉管
				channelDgEntity = (EcChannelDgEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
				channelEntity = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
				// 只把通道设备数据添加进去（可能通道数据会没有）
				dataList.get(taskDetailItemPosition).setEntity(channelDgEntity);
				if (channelDgEntity != null || channelEntity != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDgDatas(channelDgEntity, channelEntity, taskItemEntity);
					channelDgEntity = null;
					channelEntity = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_gd) { // 电缆沟
				channelDlgEntity = (EcChannelDlgEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
				channelEntity = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
				dataList.get(taskDetailItemPosition).setEntity(channelDlgEntity);
				if (channelDlgEntity != null || channelEntity != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDlgDatas(channelDlgEntity, channelEntity, taskItemEntity);
					channelDlgEntity = null;
					channelEntity = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_pg) { // 电缆管道
				channelDlgdEntity = (EcChannelDlgdEntity) data
						.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
				channelEntity = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
				dataList.get(taskDetailItemPosition).setEntity(channelDlgdEntity);
				if (channelDlgdEntity != null || channelEntity != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDlgdDatas(channelDlgdEntity, channelEntity, taskItemEntity);
					channelDlgdEntity = null;
					channelEntity = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_qj) { // 电缆桥
				channelDlqEntity = (EcChannelDlqEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
				channelEntity = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
				dataList.get(taskDetailItemPosition).setEntity(channelDlqEntity);
				if (channelDlqEntity != null || channelEntity != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDlqDatas(channelDlqEntity, channelEntity, taskItemEntity);
					channelDlqEntity = null;
					channelEntity = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_sd) { // 电缆隧道
				channelDlsdEntity = (EcChannelDlsdEntity) data
						.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
				channelEntity = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
				dataList.get(taskDetailItemPosition).setEntity(channelDlsdEntity);
				if (channelDlsdEntity != null || channelEntity != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDlsdDatas(channelDlsdEntity, channelEntity, taskItemEntity);
					channelDlsdEntity = null;
					channelEntity = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_zm) { // 电缆直埋
				channelDlzmEntity = (EcChannelDlzmEntity) data
						.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
				channelEntity = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
				dataList.get(taskDetailItemPosition).setEntity(channelDlzmEntity);
				if (channelDlzmEntity != null || channelEntity != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDlzmDatas(channelDlzmEntity, channelEntity, taskItemEntity);
					channelDlzmEntity = null;
					channelEntity = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_jk) { // 架空
				channelJkEntity = (EcChannelJkEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
				channelEntity = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
				dataList.get(taskDetailItemPosition).setEntity(channelJkEntity);
				if (channelJkEntity != null || channelEntity != null) {
					getItemState(data);
					updateDeviceAndAttr.updateJkDatas(channelJkEntity, channelEntity, taskItemEntity);
					channelJkEntity = null;
					channelEntity = null;
				}
				adapter.notifyDataSetChanged();
			} else if (requestCode == requestCode_dlc) { //电缆槽
				channelDlcEntity = (EcChannelDlcEntity) data
						.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
				channelEntity = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
				dataList.get(taskDetailItemPosition).setEntity(channelDlzmEntity);
				if (channelDlzmEntity != null || channelEntity != null) {
					getItemState(data);
					updateDeviceAndAttr.updateDlcDatas(channelDlcEntity, channelEntity, taskItemEntity);
					channelDlzmEntity = null;
					channelEntity = null;
				}
				adapter.notifyDataSetChanged();
			}
		}
	}

	private void getItemState(Intent data) {
		taskItemEntity = dataList.get(taskDetailItemPosition).getItem();
		// 接收设备点击返回按钮时传过来的值
		isReturn = data.getBooleanExtra("isTask", false);
		// 如果是点击返回按钮传过来的值则状态不改变，反之把状态改成已采集
		if (!isReturn) {
			taskItemEntity.setStatus(TaskStateTypeEnum.ycj.getState());
		}
	}
}
