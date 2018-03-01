package com.winway.android.edcollection.adding.controll;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.SelectDeviceActivity;
import com.winway.android.edcollection.adding.viewholder.TransformerViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.DicNoEntity;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
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
import com.winway.android.util.UUIDGen;

/**
 * 变压器
 * 
 * @author
 *
 */
public class TransformerControll extends BaseControll<TransformerViewHolder> {
	private List<OfflineAttach> comUpload;
	private EcTransformerEntityVo transformerEntity;
	private ComUploadBll comUploadBll;
	private ArrayList<String> attachFiles = null;
	private TaskDeviceEntity deviceEntity;
	private final int TransformerLink_RequestCode = 5;// 设备关联请求码
	private GlobalCommonBll globalCommonBll;
	private String rootPath;

	@Override
	public void init() {
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		globalCommonBll = new GlobalCommonBll(mActivity, GlobalEntry.globalDbUrl);
		initAttachment();
		initDatas();
		initEvent();
	}

	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/变压器/";
		AttachmentInit.initGloble(fileDir + "/video", fileDir + "/photo", fileDir + "/voice", fileDir + "/text",
				fileDir + "/vr");
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
	}

	private void initDatas() {
		// TODO Auto-generated method stub

		viewHolder.getIcRatedCapacity()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIcNoLoadCurrent()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIcNoLoadLoss()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIcLoadLoss()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		initSelect();
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		transformerEntity = (EcTransformerEntityVo) bundle.getSerializable("EcTransformerEntity");
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		if (deviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			transformerEntity = (EcTransformerEntityVo) deviceEntity.getDevice();
			transformerEntity.setComUploadEntityList(deviceEntity.getComUploadEntityList());
		}
		// 如果不为空则说明修改
		if (transformerEntity != null) {
			updateData();
			initSelect();
		}
	}

	private void initSelect() {
		// TODO Auto-generated method stub
		// 初始电压等级下拉列表
		if (viewHolder.getIscVoltage().getEdtTextValue().equals("")) {
			String dydjTypeNo = DicNoEntity.dydjTypeNo;// 电压等级
			InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(dydjTypeNo));
			viewHolder.getIscVoltage().setAdapter(dydjAdapter);
		}
	}

	private void updateData() {
		viewHolder.getIcDeviceName().setEdtText(transformerEntity.getSbmc());
		viewHolder.getIcMaintenanceDepartment().setEdtText(transformerEntity.getMaintenanceDepartment());
		viewHolder.getIscVoltage().setEdtTextValue(StringUtils.nullStrToEmpty(transformerEntity.getVoltage()));
		viewHolder.getIcRunningStatus().setEdtText(transformerEntity.getRunningStatus());
		viewHolder.getIcStationHouse().setEdtText(transformerEntity.getStationHouse());
		viewHolder.getIcSpace().setEdtText(transformerEntity.getSpace());
		viewHolder.getIcEquipmentModel().setEdtText(transformerEntity.getEquipmentModel());
		if (transformerEntity.getRatedCapacity() != null) {
			viewHolder.getIcRatedCapacity().setEdtText(String.valueOf(transformerEntity.getRatedCapacity()));
		}
		viewHolder.getIcPropertyRights().setEdtText(transformerEntity.getPropertyRights());
		viewHolder.getIcCommissioningDate()
				.setEdtText(DateUtils.date2Str(transformerEntity.getCommissioningDate(), DateUtils.date_sdf_wz_hm));
		viewHolder.getIcManufacturer().setEdtText(transformerEntity.getManufacturer());
		viewHolder.getIcManufacturingNum().setEdtText(transformerEntity.getManufacturingNumber());
		viewHolder.getIcUserImportantGrade().setEdtText(transformerEntity.getUserImportantGrade());
		viewHolder.getIcAgMark().setEdtText(transformerEntity.getZgMark());
		viewHolder.getIcCustomerName().setEdtText(transformerEntity.getCustomerName());
		viewHolder.getIcCustomerNum().setEdtText(transformerEntity.getCustomerNumber());
		if (transformerEntity.getNoLoadCurrent() != null) {
			viewHolder.getIcNoLoadCurrent().setEdtText(String.valueOf(transformerEntity.getNoLoadCurrent()));
		}
		if (transformerEntity.getNoLoadLoss() != null) {
			viewHolder.getIcNoLoadLoss().setEdtText(String.valueOf(transformerEntity.getNoLoadLoss()));
		}
		if (transformerEntity.getLoadLoss() != null) {
			viewHolder.getIcLoadLoss().setEdtText(String.valueOf(transformerEntity.getLoadLoss()));
		}
		viewHolder.getIcRemarks().setEdtText(transformerEntity.getRemarks());

		// 显示附件
		List<OfflineAttach> offlineAttachs = transformerEntity.getComUploadEntityList();
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
		viewHolder.getIcCommissioningDate().setEditTextFocus(false);
		// 投运日期
		viewHolder.getIcCommissioningDate().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getIcCommissioningDate().getEditTextView());
			}
		});
	}

	// 点击事件
	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: // 返回
			{
				saveAttachment();
				Intent intent = new Intent();
				intent.putExtra("transformer", transformerEntity);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: // 确定
			{
				saveData();
				if (transformerEntity == null) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("transformer", transformerEntity);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.iv_link_device_enter: {// 关联
				if (viewHolder.getBtnLinkDeviceLink().getVisibility() == View.VISIBLE) {
					viewHolder.getBtnLinkDeviceLink().setVisibility(View.GONE);
					viewHolder.getIvLinkDeviceEnter().setBackgroundResource(R.drawable.right_32);
				} else {
					viewHolder.getBtnLinkDeviceLink().setVisibility(View.VISIBLE);
					viewHolder.getIvLinkDeviceEnter().setBackgroundResource(R.drawable.left_32);
				}
				break;
			}
			case R.id.btn_link_device_link: {// 关联
				linkDevice();
				break;
			}
			default:
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
		intent.putExtra("LinkDeviceMark", TableNameEnum.BYQ.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, TransformerLink_RequestCode);
	}

	/**
	 * 保存数据
	 */
	protected EcTransformerEntity saveData() {
		if (transformerEntity == null) {
			transformerEntity = new EcTransformerEntityVo();
			transformerEntity.setObjId(UUIDGen.randomUUID());
			transformerEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			transformerEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			transformerEntity.setCreateTime(DateUtils.getDate());
			Date commissioningDate = DateUtils.str2Date(viewHolder.getIcCommissioningDate().getEdtTextValue().trim(),
					DateUtils.date_sdf_wz_hm);
			if (commissioningDate == null) {
				commissioningDate = DateUtils.getDate();
			}
			transformerEntity.setCommissioningDate(commissioningDate);
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				transformerEntity.setOperateMark(1);// 编辑下新增
			}
		}
		transformerEntity.setUpdateTime(DateUtils.getDate());
		String voltage = viewHolder.getIscVoltage().getEdtTextValue();
		transformerEntity.setVoltage(voltage);
		if (viewHolder.getIcRatedCapacity().getEdtTextValue().isEmpty()) {
			transformerEntity.setRatedCapacity(0);
		} else {
			transformerEntity
					.setRatedCapacity(Integer.parseInt(viewHolder.getIcRatedCapacity().getEdtTextValue().trim()));
		}
		if (viewHolder.getIcNoLoadCurrent().getEdtTextValue().isEmpty()) {
			transformerEntity.setNoLoadCurrent(0);
		} else {
			transformerEntity
					.setNoLoadCurrent(Integer.parseInt(viewHolder.getIcNoLoadCurrent().getEdtTextValue().trim()));
		}
		if (viewHolder.getIcNoLoadLoss().getEdtTextValue().isEmpty()) {
			transformerEntity.setNoLoadLoss(0);
		} else {
			transformerEntity.setNoLoadLoss(Integer.parseInt(viewHolder.getIcNoLoadLoss().getEdtTextValue().trim()));
		}
		if (viewHolder.getIcLoadLoss().getEdtTextValue().isEmpty()) {
			transformerEntity.setLoadLoss(0);
		} else {
			transformerEntity.setLoadLoss(Integer.parseInt(viewHolder.getIcLoadLoss().getEdtTextValue().trim()));
		}
		transformerEntity.setSbmc((viewHolder.getIcDeviceName().getEdtTextValue().trim()));
		transformerEntity.setMaintenanceDepartment(viewHolder.getIcMaintenanceDepartment().getEdtTextValue().trim());
		transformerEntity.setRunningStatus(viewHolder.getIcRunningStatus().getEdtTextValue().trim());
		transformerEntity.setStationHouse(viewHolder.getIcStationHouse().getEdtTextValue().trim());
		transformerEntity.setSpace(viewHolder.getIcSpace().getEdtTextValue().trim());
		transformerEntity.setEquipmentModel(viewHolder.getIcEquipmentModel().getEdtTextValue().trim());
		transformerEntity.setPropertyRights(viewHolder.getIcPropertyRights().getEdtTextValue().trim());
		transformerEntity.setManufacturer(viewHolder.getIcManufacturer().getEdtTextValue().trim());
		transformerEntity.setManufacturingNumber(viewHolder.getIcManufacturingNum().getEdtTextValue().trim());
		transformerEntity.setUserImportantGrade(viewHolder.getIcUserImportantGrade().getEdtTextValue().trim());
		transformerEntity.setZgMark(viewHolder.getIcAgMark().getEdtTextValue().trim());
		transformerEntity.setCustomerName(viewHolder.getIcCustomerName().getEdtTextValue().trim());
		transformerEntity.setCustomerNumber(viewHolder.getIcCustomerNum().getEdtTextValue().trim());
		transformerEntity.setRemarks(viewHolder.getIcRemarks().getEdtTextValue().trim());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAvAttachment().finish();
		// 附件操作
		saveAttachment();
		return transformerEntity;
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (transformerEntity == null) {
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
		offlineAttachs = transformerEntity.getComUploadEntityList();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.BYQ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(transformerEntity.getObjId());
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
				offlineAttach.setOwnerCode(TableNameEnum.BYQ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(transformerEntity.getObjId());
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
		transformerEntity.setComUploadEntityList(comUpload);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == TransformerLink_RequestCode) {// 设备关联
				EcTransformerEntity entity = (EcTransformerEntity) data.getSerializableExtra("LinkDeviceResult");
				try {
					transformerEntity = new EcTransformerEntityVo();
					ClonesUtil.clones(transformerEntity, entity);
					if (transformerEntity != null) {
						transformerEntity.setOperateMark(3);// 设备关联
						updateData();
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
