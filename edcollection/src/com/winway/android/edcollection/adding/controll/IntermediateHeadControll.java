package com.winway.android.edcollection.adding.controll;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.LableActivity;
import com.winway.android.edcollection.adding.activity.SelectDeviceActivity;
import com.winway.android.edcollection.adding.entity.TechFeatureType;
import com.winway.android.edcollection.adding.viewholder.IntermediateHeadViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.task.entity.TaskDeviceEntity;
import com.winway.android.ewidgets.attachment.AttachmentAttrs;
import com.winway.android.ewidgets.attachment.AttachmentInit;
import com.winway.android.ewidgets.attachment.AttachmentUtil;
import com.winway.android.ewidgets.attachment.AttachmentUtil.AddDeleteResult;
import com.winway.android.ewidgets.attachment.AttachmentView.AttachmentResult;
import com.winway.android.ewidgets.datetimepicker.DateTimePickDialogUtil;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.ClonesUtil;
import com.winway.android.util.DateUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 电缆中间接头
 * 
 * @author ly
 *
 */
public class IntermediateHeadControll extends BaseControll<IntermediateHeadViewHolder> {
	private List<OfflineAttach> comUpload;
	private EcMiddleJointEntityVo middleJoint;
	private ArrayList<String> attachFiles = null;
	private ComUploadBll comUploadBll;
	private TaskDeviceEntity deviceEntity;
	private final int ZjjtLink_RequestCode = 5;// 设备关联请求码
	private final int BQ_RequestCode = 12; // 标签采集请求码
	private EcLineLabelEntityVo ecLineLabelEntityVo;// 标签数据
	private GlobalCommonBll globalCommonBll;
	private String rootPath;

	@Override
	public void init() {
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/"
				+ GlobalEntry.currentProject.getPrjNo() + ".db";
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		globalCommonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		initAttachment();
		initData();
		initEvent();
	}

	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/中间接头/";
		AttachmentInit.initGloble(fileDir + "/video", fileDir + "/photo", fileDir + "/voice",
				fileDir + "/text", fileDir + "/vr");
		AttachmentAttrs attrs = AttachmentAttrs.getSinggleAttr();
		attrs.activity = mActivity;
		/** 控件附件采集的最大数量 */
		attrs.maxPhoto = 5;
		attrs.maxVideo = 5;
		attrs.maxVoice = 5;
		attrs.maxText = 5;
		attrs.maxVr = 5;
		attrs.hasFinish = false;// 隐藏完毕按钮
		viewHolder.getAvAttachment().setAttachmentAttrs(attrs);
		viewHolder.getInconeTemperature().getEditTextView()
				.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInconeAmpacity().getEditTextView()
				.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
	}

	// 初始化数据
	private void initData() {
		initSelect();
		viewHolder.getInconeWorker().setEdtText(GlobalEntry.loginResult.getUsername());
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		middleJoint = (EcMiddleJointEntityVo) bundle.getSerializable("IntermediateHeadEntity");
		String zjjtName = bundle.getString("zjjtName");
		viewHolder.getInconEquipmentName().setEdtText(zjjtName);
		// 从任务明细传过来的对象
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		if (deviceEntity != null) {
			viewHolder.getIvLinkDeviceEnter().setVisibility(View.GONE);
			viewHolder.getBtnLinkDeviceLink().setVisibility(View.GONE);
			middleJoint = (EcMiddleJointEntityVo) deviceEntity.getDevice();
			middleJoint.setAttr(deviceEntity.getComUploadEntityList());
		}
		// 如果不为空则说明修改
		if (middleJoint != null) {
			updateData();
			initSelect();
		}
	}

	private void initSelect() {
		// TODO Auto-generated method stub
		// 工艺特征下拉框
		if (viewHolder.getInscFeature().getEdtTextValue().equals("")) {
			String featureTypeNo = "GytzType000";
			InputSelectAdapter featureAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(featureTypeNo));
			viewHolder.getInscFeature().setAdapter(featureAdapter);
			viewHolder.getInscFeature().getEditTextView().setEnabled(false);
		}
		// 类型下拉框
		if (viewHolder.getInscType().getEdtTextValue().equals("")) {
			String typeTypeNo = "010601006";
			InputSelectAdapter typeAdapter = new InputSelectAdapter(mActivity, globalCommonBll.getDictionaryNameList(typeTypeNo));
			viewHolder.getInscType().setAdapter(typeAdapter);
			viewHolder.getInscType().getEditTextView().setEnabled(false);
		}
	}

	private void updateData() {
		viewHolder.getInconEquipmentName().setEdtText(StringUtils.nullStrToEmpty(middleJoint.getSbmc()));
		viewHolder.getInconUnitType().setEdtText(StringUtils.nullStrToEmpty(middleJoint.getDevModel()));
		String feature = "";
		if (middleJoint.getTechFeature() != null) {
			if (middleJoint.getTechFeature() == TechFeatureType.lss.getIndex()) {
				feature = TechFeatureType.lss.getValue();
			}else if (middleJoint.getTechFeature() == TechFeatureType.rss.getIndex()) {
				feature = TechFeatureType.rss.getValue();
			}else if (middleJoint.getTechFeature() == TechFeatureType.yzs.getIndex()) {
				feature = TechFeatureType.yzs.getValue();
			}else if (middleJoint.getTechFeature() == TechFeatureType.cys.getIndex()) {
				feature = TechFeatureType.cys.getValue();
			}
		}
		viewHolder.getInscFeature().setEdtTextValue(feature);
		viewHolder.getInconFactory().setEdtText(StringUtils.nullStrToEmpty(middleJoint.getManufacutrer()));
		viewHolder.getInconCateDate().setEdtText(
				DateUtils.date2Str(middleJoint.getScDate(), DateUtils.date_sdf_wz_hm));
		viewHolder.getInconeOperationDate().setEdtText(
				DateUtils.date2Str(middleJoint.getTyDate(), DateUtils.date_sdf_wz_hm));
		viewHolder.getInconeInstall().setEdtText(StringUtils.nullStrToEmpty(middleJoint.getInstallationUnit()));
		viewHolder.getInconeWorker().setEdtText(StringUtils.nullStrToEmpty(middleJoint.getWorker()));
		viewHolder.getInscType().setEdtTextValue(StringUtils.nullStrToEmpty(middleJoint.getJointType()));
		viewHolder.getInconePhone().setEdtText(StringUtils.nullStrToEmpty(middleJoint.getCjlxfs()));
		viewHolder.getInconeFault().setEdtText(StringUtils.nullStrToEmpty(middleJoint.getZjjtgzqk()));
		viewHolder.getInconePatrolDate().setEdtText(
				DateUtils.date2Str(middleJoint.getScxjrq(), DateUtils.date_sdf_wz_hm));
		viewHolder.getInconeAmpacity().setEdtText(StringUtils.nullStrToEmpty(middleJoint.getDlzll()));
		viewHolder.getInconeTemperature().setEdtText(StringUtils.nullStrToEmpty(middleJoint.getZjjtwd()));
		viewHolder.getInconeGroundCurrent().setEdtText(StringUtils.nullStrToEmpty(middleJoint.getJddlqk()));
		// 显示附件
		List<OfflineAttach> offlineAttachs = middleJoint.getAttr();
		if (offlineAttachs != null && offlineAttachs.size() > 0) {
			attachFiles = new ArrayList<String>();
			for (OfflineAttach attach : offlineAttachs) {
				attachFiles.add(attach.getFilePath());
			}
		}
		viewHolder.getAvAttachment().addExistsAttachments(attachFiles);
	}

	// 初始化事件
	private void initEvent() {
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getIvLinkDeviceEnter().setOnClickListener(ocl);
		viewHolder.getBtnLinkDeviceLink().setOnClickListener(ocl);
		viewHolder.getBtnLinkLabelLink().setOnClickListener(ocl);
		// 生产日期
		viewHolder.getInconCateDate().getEditTextView().setFocusable(false);
		viewHolder.getInconCateDate().getEditTextView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconCateDate().getEditTextView());
			}
		});
		// 投运日期
		viewHolder.getInconeOperationDate().getEditTextView().setFocusable(false);
		viewHolder.getInconeOperationDate().getEditTextView()
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity,
								null);
						updaUtil.dateTimePicKDialog(viewHolder.getInconeOperationDate()
								.getEditTextView());
					}
				});
		// 上次巡检日期
		viewHolder.getInconePatrolDate().getEditTextView().setFocusable(false);
		viewHolder.getInconePatrolDate().getEditTextView()
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity,
								null);
						updaUtil.dateTimePicKDialog(viewHolder.getInconePatrolDate()
								.getEditTextView());
					}
				});
	}

	// 点击事件
	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_head_item_return : { //返回按钮
					Intent intent = new Intent();
					intent.putExtra("zjjt", middleJoint);
					// 任务明细设置状态时调用
					intent.putExtra("isTask", true);
					mActivity.setResult(Activity.RESULT_OK, intent);
					mActivity.finish();
					break;
				}
				case R.id.tv_head_item_ok : { //确定按钮
//					sif (viewHolder.getInconEquipmentName().getEdtTextValue().isEmpty()) {
//						ToastUtil.show(mActivity, "设备名称不能为空", 200);
//						return;
//					}
					saveData();
					if (middleJoint == null) {
						return;
					}
					Intent intent = new Intent();
					intent.putExtra("zjjt", middleJoint);
					mActivity.setResult(Activity.RESULT_OK, intent);
					mActivity.finish();
				}
					break;
				case R.id.iv_link_device_enter : {// 关联
					if (viewHolder.getBtnLinkDeviceLink().getVisibility() == View.VISIBLE) {
						viewHolder.getBtnLinkDeviceLink().setVisibility(View.GONE);
						viewHolder.getIvLinkDeviceEnter()
								.setBackgroundResource(R.drawable.right_32);
					} else {
						viewHolder.getBtnLinkDeviceLink().setVisibility(View.VISIBLE);
						viewHolder.getIvLinkDeviceEnter().setBackgroundResource(R.drawable.left_32);
					}
					break;
				}
				case R.id.btn_link_device_link : {// 关联
					linkDevice();
					break;
				}
				case R.id.btn_link_label: {// 标签采集
					if (viewHolder.getInconEquipmentName().getEdtTextValue().equals("")) {
						ToastUtil.show(mActivity, "请先输入设备名称！", 200);
						break;
					}
					Intent intent = new Intent(mActivity, LableActivity.class);
					if (middleJoint != null) { 
						EcLineLabelEntityVo ecLineLabelEntityVo = middleJoint.getEcLineLabelEntityVo();
						if (ecLineLabelEntityVo != null) {
							intent.putExtra("LableEntity", ecLineLabelEntityVo);
						}
					} else { 
						intent.putExtra("LableEntity", ecLineLabelEntityVo);
					}
					intent.putExtra("devName", viewHolder.getInconEquipmentName().getEdtTextValue().trim());
					intent.putExtra("devType", ResourceEnum.ZJJT.getName());
					AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, BQ_RequestCode);
					break;
				}
				default :
					break;
			}
		}
	};

	/**
	 * 关联设备
	 */
	protected void linkDevice() {
		Intent intent = new Intent();
		intent.setClass(mActivity, SelectDeviceActivity.class);
		intent.putExtra("LinkDeviceMark", TableNameEnum.DLZJJT.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, ZjjtLink_RequestCode);
	}

	/**
	 * 保存
	 */
	protected void saveData() {
		// middleJoint为空两种情况一种是新增时另一种是编辑时在新增时没添加
		if (middleJoint == null) {
			middleJoint = new EcMiddleJointEntityVo();
			middleJoint.setObjId((UUIDGen.randomUUID()));
			middleJoint.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			middleJoint.setOrgid(GlobalEntry.currentProject.getOrgId());
			middleJoint.setCjsj(DateUtils.getDate());
			middleJoint.setScDate(DateUtils.str2Date(viewHolder.getInconCateDate().getEdtTextValue(),
					DateUtils.date_sdf_wz_hm));
			middleJoint.setTyDate(DateUtils.str2Date(viewHolder.getInconeOperationDate()
					.getEdtTextValue(), DateUtils.date_sdf_wz_hm));
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				middleJoint.setOperateMark(1);// 编辑下新增
			}
		}
		middleJoint.setGxsj(DateUtils.getDate());
		middleJoint.setSbmc(viewHolder.getInconEquipmentName().getEdtTextValue());
		middleJoint.setDevModel(viewHolder.getInconUnitType().getEdtTextValue());
		Integer feature = 0;
		if (viewHolder.getInscFeature().getEdtTextValue().equals(TechFeatureType.lss.getValue())) {
			feature = TechFeatureType.lss.getIndex();
		}else if (viewHolder.getInscFeature().getEdtTextValue().equals(TechFeatureType.rss.getValue())) {
			feature = TechFeatureType.rss.getIndex();
		}else if (viewHolder.getInscFeature().getEdtTextValue().equals(TechFeatureType.yzs.getValue())) {
			feature = TechFeatureType.yzs.getIndex();
		}else if (viewHolder.getInscFeature().getEdtTextValue().equals(TechFeatureType.cys.getValue())) {
			feature = TechFeatureType.cys.getIndex();
		}
		middleJoint.setTechFeature(feature);
		middleJoint.setManufacutrer(viewHolder.getInconFactory().getEdtTextValue());
		middleJoint.setInstallationUnit(viewHolder.getInconeInstall().getEdtTextValue());
		middleJoint.setWorker(viewHolder.getInconeWorker().getEdtTextValue());
		middleJoint.setJointType(viewHolder.getInscType().getEdtTextValue());
		middleJoint.setCjlxfs(viewHolder.getInconePhone().getEdtTextValue());
		middleJoint.setZjjtgzqk(viewHolder.getInconeFault().getEdtTextValue());
		middleJoint.setScxjrq(DateUtils.str2Date(
				viewHolder.getInconePatrolDate().getEdtTextValue(), DateUtils.date_sdf_wz_hm));
		String dlzll = viewHolder.getInconeAmpacity().getEdtTextValue() + "";
		middleJoint.setDlzll(dlzll.equals("") ? null : Integer.parseInt(dlzll));
		String zjjtwd = viewHolder.getInconeTemperature().getEdtTextValue();
		middleJoint.setZjjtwd(zjjtwd.equals("") ? null : Double.parseDouble(zjjtwd));
		middleJoint.setJddlqk(viewHolder.getInconeGroundCurrent().getEdtTextValue());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAvAttachment().finish();
		// 附件操作
		saveAttachment();
		// 保存标签采集的数据
		if (ecLineLabelEntityVo != null) {
			ecLineLabelEntityVo.setDeviceType(ResourceEnum.ZJJT.getValue() + "");
			ecLineLabelEntityVo.setDevObjId(middleJoint.getObjId());
			middleJoint.setEcLineLabelEntityVo(ecLineLabelEntityVo);
		}
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (middleJoint == null) {
			return;
		}
		comUpload = new ArrayList<OfflineAttach>();
		// 查找出附件组件中存在的附件路径
		AttachmentResult result = viewHolder.getAvAttachment().getResult();
		// 查找出添加、删除、未改变的附件
		AddDeleteResult addOrDelete = AttachmentUtil.findAddDeleteResult(result, attachFiles);
		// 添加的路径或者是重命名的附件
		ArrayList<String> addList = addOrDelete.addList;
		// 删除的附件
		ArrayList<String> deleteList = addOrDelete.deleteList;
		// 未改变的附件
		ArrayList<String> nochangeList = addOrDelete.nochangeList;
		OfflineAttach offlineAttach = null;
		List<OfflineAttach> offlineAttachs = null;
		offlineAttachs = middleJoint.getAttr();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.DLZJJT.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(middleJoint.getObjId());
				comUpload.add(offlineAttach);
			}
		}
		// 操作未改变的附件
		if (nochangeList != null && nochangeList.size() > 0) {
			for (String path : nochangeList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				// 如果是编辑时取出附件的id,防止添加数据时重复插入数据
				if (offlineAttachs != null && offlineAttachs.size() > 0) {
					for (OfflineAttach attach : offlineAttachs) {
						if (attach.getFilePath().equals(path)) {
							if (attach.getComUploadId() != null) {
								offlineAttach.setComUploadId(attach.getComUploadId());
								offlineAttach.setIsUploaded(attach.getIsUploaded());
							}
						}
					}
				}
				offlineAttach.setAppCode(null);
//				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.DLZJJT.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(middleJoint.getObjId());
				comUpload.add(offlineAttach);
			}
		}
		// 操作删除的附件 主要是编辑时删除数据库数据
		if (deleteList != null && deleteList.size() > 0) {
			for (String path : deleteList) {
				if (offlineAttachs != null && offlineAttachs.size() > 0) {
					for (OfflineAttach attach : offlineAttachs) {
						if (attach.getFilePath().equals(path) && attach.getComUploadId() != null) {
							comUploadBll.deleteById(attach.getComUploadId());
						}
					}
				}
			}
		}
		middleJoint.setAttr(comUpload);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == ZjjtLink_RequestCode) {
				EcMiddleJointEntity entity = (EcMiddleJointEntity) data
						.getSerializableExtra("LinkDeviceResult");
				try {
					middleJoint = new EcMiddleJointEntityVo();
					ClonesUtil.clones(middleJoint, entity);
					if (middleJoint != null) {
						middleJoint.setOperateMark(3);// 设备关联
						updateData();
					}
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (requestCode == BQ_RequestCode) { // 标签采集
				ecLineLabelEntityVo = (EcLineLabelEntityVo) data.getSerializableExtra("lable");
				if (middleJoint != null) {
					if (ecLineLabelEntityVo != null) {
						middleJoint.setEcLineLabelEntityVo(ecLineLabelEntityVo);
					}
				}
			}
			viewHolder.getAvAttachment().onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			viewHolder.getHcHead().getReturnView().performClick();
		}
		return super.onKeyDown(keyCode, event);
	}
}
