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
import com.winway.android.edcollection.adding.viewholder.DypdxViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.DicNoEntity;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDypdxEntityVo;
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
 * 低压配电箱
 * 
 * @author Administrator
 *
 */
public class DypdxControll extends BaseControll<DypdxViewHolder> {
	private List<OfflineAttach> comUpload;
	private EcDypdxEntityVo ecDypdxEntityVo;
	private GlobalCommonBll globalCommonBll;
	private ArrayList<String> attachFiles = null;
	private ComUploadBll comUploadBll;
	private TaskDeviceEntity deviceEntity;
	private final int DypdxLink_RequestCode = 5;// 设备关联请求码
	private String rootPath;

	@Override
	public void init() {
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		globalCommonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		initAttachment();
		initData();
		initEvent();

	}

	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir = rootPath+ "附件/" + GlobalEntry.currentProject.getPrjNo() + "/低压配电箱/";
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

	// 初始化事件
	private void initEvent() {
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getIvLinkDeviceEnter().setOnClickListener(ocl);
		viewHolder.getBtnLinkDeviceLink().setOnClickListener(ocl);
		viewHolder.getInscYwdw().getEditTextView().setEnabled(false);
		viewHolder.getInscWhbz().getEditTextView().setEnabled(false);
		// 接地电阻
		viewHolder.getInconJddz()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// 投运日期
		viewHolder.getInconTyrq().setEditTextFocus(false);
		viewHolder.getInconTyrq().getEditTextView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconTyrq().getEditTextView());
			}
		});

		// 出厂日期
		viewHolder.getInconCcrq().setEditTextFocus(false);
		viewHolder.getInconCcrq().getEditTextView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconCcrq().getEditTextView());
			}
		});
	}

	// 初始化数据
	private void initData() {
		if (GlobalEntry.loginResult != null) {
			viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		}
		// 所属地市是否显示
		if (GlobalEntry.ssds) {
			viewHolder.getInscSsds().setVisibility(View.VISIBLE);
		}
		initSelect();
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		ecDypdxEntityVo = (EcDypdxEntityVo) bundle.getSerializable("EcDypdxEntity");
		// 从任务明细传过来的对象
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		if (deviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			ecDypdxEntityVo = (EcDypdxEntityVo) deviceEntity.getDevice();
			ecDypdxEntityVo.setComUploadEntityList(deviceEntity.getComUploadEntityList());
		}

		if (ecDypdxEntityVo != null) {
			updateData();
			initSelect();
		}
	}

	private void initSelect() {
		// 初始电压等级下拉列表
		if(viewHolder.getInconDldj().getEdtTextValue().equals("")){
			String dydjTypeNo = DicNoEntity.dydjTypeNo;// 电压等级
			InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(dydjTypeNo));
			viewHolder.getInconDldj().setAdapter(dydjAdapter);
		}
		// 初始类型下拉列表
		if(viewHolder.getInconLx().getEdtTextValue().equals("")){
			String lxTypeNo = "dlpdx010605013";// 类型
			InputSelectAdapter lxAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(lxTypeNo));
			viewHolder.getInconLx().setAdapter(lxAdapter);
		}
		// 初始设备状态下拉列表
		if(viewHolder.getInconSbzt().getEdtTextValue().equals("")){
			String sbztTypeNo = "010402";// 设备状态
			InputSelectAdapter sbztAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(sbztTypeNo));
			viewHolder.getInconSbzt().setAdapter(sbztAdapter);
		}
		// 初始资产性质下拉列表
		if(viewHolder.getInconZcxz().getEdtTextValue().equals("")){
			String zcxzTypeNoGw = "010403";// 资产性质国网
			String zcxzTypeNoNw = "a010404";//资产性质南网
			if(GlobalEntry.zcxz){
				InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
						globalCommonBll.getDictionaryNameList(zcxzTypeNoNw));
				viewHolder.getInconZcxz().setAdapter(zcxzAdapter);
			}else {
				InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
						globalCommonBll.getDictionaryNameList(zcxzTypeNoGw));
				viewHolder.getInconZcxz().setAdapter(zcxzAdapter);
			}
		}
	}

	private void updateData() {
		// 设备标识
		// viewHolder.getInconSbid().setEdtText(ecChannelDypdxEntityVo.getSbid());
		viewHolder.getInconSbmc().setEdtText(ecDypdxEntityVo.getSbmc());
		viewHolder.getInconDldj().setEdtTextValue(ecDypdxEntityVo.getDldj());
		viewHolder.getInconLx().setEdtTextValue(ecDypdxEntityVo.getLx());
		viewHolder.getInconSbzt().setEdtTextValue(ecDypdxEntityVo.getSbzt());
		viewHolder.getInconZcxz().setEdtTextValue(ecDypdxEntityVo.getZcxz());
		viewHolder.getInconYxbh().setEdtText(ecDypdxEntityVo.getYxbh());
		viewHolder.getInconDxmpid().setEdtText(ecDypdxEntityVo.getDxmpid());
		if (GlobalEntry.ssds) {
			viewHolder.getInscSsds().setEdtText(ecDypdxEntityVo.getSsds());
		}
		viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
		viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		String tyrq = DateUtils.date2Str(ecDypdxEntityVo.getTyrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getInconTyrq().setEdtText(tyrq);
		viewHolder.getInconZcdw().setEdtText(ecDypdxEntityVo.getZcdw());
		viewHolder.getInconSccj().setEdtText(ecDypdxEntityVo.getSccj());
		viewHolder.getInconCcbh().setEdtText(ecDypdxEntityVo.getCcbh());
		viewHolder.getInconXh().setEdtText(ecDypdxEntityVo.getXh());
		String ccrq = DateUtils.date2Str(ecDypdxEntityVo.getCcrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getInconCcrq().setEdtText(ccrq);
		viewHolder.getInconJddz().setEdtText(StringUtils.nullStrToEmpty(ecDypdxEntityVo.getJddz()));
		viewHolder.getInconBz().setEdtText(ecDypdxEntityVo.getBz());
		// 显示附件
		List<OfflineAttach> offlineAttachs = ecDypdxEntityVo.getComUploadEntityList();
		if (offlineAttachs != null && offlineAttachs.size() > 0) {
			attachFiles = new ArrayList<String>();
			for (OfflineAttach attach : offlineAttachs) {
				attachFiles.add(attach.getFilePath());
			}
		}
		viewHolder.getAvAttachment().addExistsAttachments(attachFiles);
	}

	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: { // 返回
				saveAttachment();
				Intent intent = new Intent();
				intent.putExtra("dypdx", ecDypdxEntityVo);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: { // 确定
				saveData();
				if (ecDypdxEntityVo == null) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("dypdx", ecDypdxEntityVo);
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
		intent.putExtra("LinkDeviceMark", TableNameEnum.DYPDX.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, DypdxLink_RequestCode);
	}

	/**
	 * 保存数据
	 */
	protected void saveData() {
		if (ecDypdxEntityVo == null) {
			ecDypdxEntityVo = new EcDypdxEntityVo();
			ecDypdxEntityVo.setObjId(UUIDGen.randomUUID());
			ecDypdxEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecDypdxEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecDypdxEntityVo.setCjsj(DateUtils.getDate());
			Date tyrqDate = DateUtils.str2Date(viewHolder.getInconTyrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
			if (tyrqDate == null) {
				tyrqDate = DateUtils.getDate();
			}
			ecDypdxEntityVo.setTyrq(tyrqDate);
			Date ccrqDate = DateUtils.str2Date(viewHolder.getInconCcrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
			if (ccrqDate == null) {
				ccrqDate = DateUtils.getDate();
			}
			ecDypdxEntityVo.setCcrq(ccrqDate);
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecDypdxEntityVo.setOperateMark(1);// 编辑下添加的
			}
		}
		ecDypdxEntityVo.setGxsj(DateUtils.getDate());
		// 设备标识
		// ecChannelDypdxEntityVo.setSbid(viewHolder.getInconSbid().getEdtTextValue().trim());;
		ecDypdxEntityVo.setSbmc(viewHolder.getInconSbmc().getEdtTextValue().trim());
		ecDypdxEntityVo.setYxbh(viewHolder.getInconYxbh().getEdtTextValue().trim());
		ecDypdxEntityVo.setDxmpid(viewHolder.getInconDxmpid().getEdtTextValue().trim());

		ecDypdxEntityVo.setDldj(viewHolder.getInconDldj().getEdtTextValue().trim());
		if (GlobalEntry.ssds) {
			ecDypdxEntityVo.setSsds(viewHolder.getInscSsds().getEdtTextValue().trim());
		}
		ecDypdxEntityVo.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecDypdxEntityVo.setWhbz(GlobalEntry.loginResult.getDeptid());
		ecDypdxEntityVo.setLx(viewHolder.getInconLx().getEdtTextValue().trim());
		ecDypdxEntityVo.setSbzt(viewHolder.getInconSbzt().getEdtTextValue().trim());
		ecDypdxEntityVo.setZcxz(viewHolder.getInconZcxz().getEdtTextValue().trim());
		ecDypdxEntityVo.setZcdw(viewHolder.getInconZcdw().getEdtTextValue().trim());
		ecDypdxEntityVo.setXh(viewHolder.getInconXh().getEdtTextValue().trim());
		ecDypdxEntityVo.setSccj(viewHolder.getInconSccj().getEdtTextValue().trim());
		ecDypdxEntityVo.setCcbh(viewHolder.getInconCcbh().getEdtTextValue().trim());
		if (viewHolder.getInconJddz().getEdtTextValue().isEmpty()) {
			ecDypdxEntityVo.setJddz(0);
		} else {
			ecDypdxEntityVo.setJddz(Integer.parseInt(viewHolder.getInconJddz().getEdtTextValue().trim()));
		}
		ecDypdxEntityVo.setBz(viewHolder.getInconBz().getEdtTextValue().trim());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAvAttachment().finish();
		// 附件操作
		saveAttachment();
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (ecDypdxEntityVo == null) {
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
		offlineAttachs = ecDypdxEntityVo.getComUploadEntityList();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.DYPDX.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecDypdxEntityVo.getObjId());
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
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
//				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.DYPDX.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecDypdxEntityVo.getObjId());
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
		ecDypdxEntityVo.setComUploadEntityList(comUpload);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == DypdxLink_RequestCode) {// 设备关联
				EcDypdxEntity entity = (EcDypdxEntity) data.getSerializableExtra("LinkDeviceResult");
				try {
					ecDypdxEntityVo = new EcDypdxEntityVo();
					ClonesUtil.clones(ecDypdxEntityVo, entity);
					if (ecDypdxEntityVo != null) {
						ecDypdxEntityVo.setOperateMark(3);// 设备关联
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
