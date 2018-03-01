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
import com.winway.android.edcollection.adding.util.LengthTextWatcher;
import com.winway.android.edcollection.adding.viewholder.TowerViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
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
 * 杆塔
 * 
 * @author
 *
 */
public class TowerControll extends BaseControll<TowerViewHolder> {
	private List<OfflineAttach> comUpload;
	private EcTowerEntityVo towerEntity;
	private ComUploadBll comUploadBll;
	private ArrayList<String> attachFiles = null;
	private TaskDeviceEntity deviceEntity;
	private GlobalCommonBll globalCommonBll;
	private final int TowerlLink_RequestCode = 5;// 设备关联请求码
	private String rootPath;

	@Override
	public void init() {
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		globalCommonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		initAttachment();
		initData();
		initEvent();
	}

	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir = rootPath+ "附件/" + GlobalEntry.currentProject.getPrjNo() + "/杆塔/";
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

	// 初始化数据
	private void initData() {
		viewHolder.getInconTowerNo().setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
		viewHolder.getInconTowerNo().getEditTextView().addTextChangedListener(new LengthTextWatcher(
				(Integer.MAX_VALUE + "").length() - 1, viewHolder.getInconTowerNo().getEditTextView()));
		// viewHolder.getInconCornerDegree()
		// .setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInconHeight()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

		// 初始设备型号下拉列表 =====暂时没数据
		// String sbxhTypeNo = "bzk0201";// 设备型号
		// InputSelectAdapter sbxhAdapter = new InputSelectAdapter(mActivity,
		// globalCommonBll.getDictionaryNameList(sbxhTypeNo));
		// viewHolder.getInscEquipmentModel().setAdapter(sbxhAdapter);
		initSelect();
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		towerEntity = (EcTowerEntityVo) bundle.getSerializable("EcTowerEntity");
		// 从任务明细传过来的对象
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		if (deviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			towerEntity = (EcTowerEntityVo) deviceEntity.getDevice();
			towerEntity.setComUploadEntityList(deviceEntity.getComUploadEntityList());
		}
		// 如果不为空则说明修改
		if (towerEntity != null) {
			updateData();
			initSelect();
		}
	}

	private void initSelect() {
		// 初始杆塔材质下拉列表
		if(viewHolder.getInscMaterial().getEdtTextValue().equals("")){
			String gtczTypeNo = "010603013";// 杆塔材质
			InputSelectAdapter gtczAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(gtczTypeNo));
			viewHolder.getInscMaterial().setAdapter(gtczAdapter);
		}
	}

	private void updateData() {
		viewHolder.getInconTowerNo().setEdtText(StringUtils.nullStrToEmpty(towerEntity.getTowerNo()));
		viewHolder.getInconmaintenanceDepartment().setEdtText(towerEntity.getMaintenanceDepartment());
		viewHolder.getInconRunningStatus().setEdtText(towerEntity.getRunningStatus());
		viewHolder.getInconPropertyRights().setEdtText(towerEntity.getPropertyRights());
		viewHolder.getInconCommissioningDate()
				.setEdtText(DateUtils.date2Str(towerEntity.getCommissioningDate(), DateUtils.date_sdf_wz_hm));
		viewHolder.getInconManufacturer().setEdtText(towerEntity.getManufacturer());
		viewHolder.getInscEquipmentModel().setEdtTextValue(towerEntity.getEquipmentModel());
		viewHolder.getInconManufacturingNum().setEdtText(towerEntity.getManufacturingNumber());
		viewHolder.getInconaccomplishDate()
				.setEdtText(DateUtils.date2Str(towerEntity.getAccomplishDate(), DateUtils.date_sdf_wz_hm));
		viewHolder.getInscMaterial().setEdtTextValue(towerEntity.getMaterial());
		viewHolder.getInconCornerDegree().setEdtText(StringUtils.nullStrToEmpty(towerEntity.getCornerDegree()));
		viewHolder.getInconHeight().setEdtText(StringUtils.nullStrToEmpty(towerEntity.getHeight()));
		viewHolder.getInconRemarks().setEdtText(towerEntity.getRemarks());

		// 显示附件
		List<OfflineAttach> offlineAttachs = towerEntity.getComUploadEntityList();
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

		viewHolder.getInconCommissioningDate().setEditTextFocus(false);
		viewHolder.getInconaccomplishDate().setEditTextFocus(false);
		// 投运日期
		viewHolder.getInconCommissioningDate().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconCommissioningDate().getEditTextView());
			}
		});
		// 出厂日期
		viewHolder.getInconaccomplishDate().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconaccomplishDate().getEditTextView());
			}
		});
	}

	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: // 返回
			{
				saveAttachment();
				Intent intent = new Intent();
				intent.putExtra("tower", towerEntity);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: // 确定
			{
				saveData();
				if (towerEntity == null) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("tower", towerEntity);
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
		intent.putExtra("LinkDeviceMark", TableNameEnum.GT.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, TowerlLink_RequestCode);

	}

	/**
	 * 保存数据
	 */
	protected EcTowerEntity saveData() {
		if (towerEntity == null) {
			towerEntity = new EcTowerEntityVo();
			towerEntity.setObjId(UUIDGen.randomUUID());
			towerEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			towerEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			towerEntity.setCreateTime(DateUtils.getDate());
			Date commissparse = DateUtils.str2Date(viewHolder.getInconCommissioningDate().getEdtTextValue(),
					DateUtils.date_sdf_wz_hm);
			Date accomparse = DateUtils.str2Date(viewHolder.getInconaccomplishDate().getEdtTextValue(),
					DateUtils.date_sdf_wz_hm);
			if (commissparse == null) {
				commissparse = DateUtils.getDate();
			}
			towerEntity.setCommissioningDate(commissparse);
			if (accomparse == null) {
				accomparse = DateUtils.getDate();
			}
			towerEntity.setAccomplishDate(accomparse);
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				towerEntity.setOperateMark(1);// 编辑下新增
			}
		}
		towerEntity.setUpdateTime(DateUtils.getDate());
		String towerNo = viewHolder.getInconTowerNo().getEdtTextValue().trim();
		towerEntity.setTowerNo(towerNo);
		// if (viewHolder.getInconTowerNo().getEdtTextValue().equals("") ||
		// towerEntity.getTowerNo() == null) {
		// towerEntity.setTowerNo(0);
		// } else {
		// String towerNo =
		// viewHolder.getInconTowerNo().getEdtTextValue().trim();
		// String regex = "\\d{1,9}";
		// if (!towerNo.matches(regex)) {
		// ToastUtil.show(mActivity, "杆塔编号最大不能超过9位", 200);
		// return null;
		// }
		// towerEntity.setTowerNo(Integer.parseInt(towerNo));
		// }
		towerEntity.setMaintenanceDepartment(viewHolder.getInconmaintenanceDepartment().getEdtTextValue().trim());
		towerEntity.setRunningStatus(viewHolder.getInconRunningStatus().getEdtTextValue().trim());
		towerEntity.setPropertyRights(viewHolder.getInconPropertyRights().getEdtTextValue().trim());
		towerEntity.setManufacturer(viewHolder.getInconManufacturer().getEdtTextValue().trim());
		towerEntity.setEquipmentModel(viewHolder.getInscEquipmentModel().getEdtTextValue().trim());
		towerEntity.setManufacturingNumber(viewHolder.getInconManufacturingNum().getEdtTextValue().trim());
		towerEntity.setMaterial(viewHolder.getInscMaterial().getEdtTextValue().trim());
		towerEntity.setCornerDegree(viewHolder.getInconCornerDegree().getEdtTextValue().trim());
		towerEntity.setHeight(viewHolder.getInconHeight().getEdtTextValue().equals("") ? null 
				: Double.valueOf(viewHolder.getInconHeight().getEdtTextValue()));
		towerEntity.setRemarks(viewHolder.getInconRemarks().getEdtTextValue().trim());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAvAttachment().finish();
		// 附件操作
		saveAttachment();
		return towerEntity;
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (towerEntity == null) {
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
		offlineAttachs = towerEntity.getComUploadEntityList();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.GT.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(towerEntity.getObjId());
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
				offlineAttach.setOwnerCode(TableNameEnum.GT.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(towerEntity.getObjId());
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
		towerEntity.setComUploadEntityList(comUpload);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == TowerlLink_RequestCode) {
				EcTowerEntity entity = (EcTowerEntity) data.getSerializableExtra("LinkDeviceResult");
				try {
					towerEntity = new EcTowerEntityVo();
					ClonesUtil.clones(towerEntity, entity);
					if (towerEntity != null) {
						towerEntity.setOperateMark(3);// 设备关联操作
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
