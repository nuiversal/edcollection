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
import com.winway.android.edcollection.adding.viewholder.KgzViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.DicNoEntity;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcKgzEntityVo;
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
import com.winway.android.util.UUIDGen;

/**
 * 开关站
 * 
 * @author lyh
 *
 */
public class KgzControll extends BaseControll<KgzViewHolder> {
	private List<OfflineAttach> comUpload;
	private EcKgzEntityVo ecKgzEntityVo;
	private ComUploadBll comUploadBll;
	private GlobalCommonBll globalCommonBll;
	private ArrayList<String> attachFiles = null;
	private TaskDeviceEntity deviceEntity;
	private final int KgzLink_RequestCode = 5;// 设备关联请求码
	private String rootPath;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		globalCommonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		initAttachment();
		initDatas();
		initEvent();
	}

	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/开关站/";
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
		viewHolder.getAttaKgz().setAttachmentAttrs(attrs);
	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		if (GlobalEntry.loginResult != null) {
			viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		}
		// 所属地市是否显示
		if (GlobalEntry.ssds) {
			viewHolder.getInscSsds().setVisibility(View.VISIBLE);
		}
		initSelect();
		viewHolder.getIncomByjcxjgs()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		ecKgzEntityVo = (EcKgzEntityVo) bundle.getSerializable("EcKgzEntity");
		// 从任务明细传过来的对象
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		if (deviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			ecKgzEntityVo = (EcKgzEntityVo) deviceEntity.getDevice();
			ecKgzEntityVo.setComUploadEntityList(deviceEntity.getComUploadEntityList());
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
		}
		// 取路径点的位置
		viewHolder.getIncomZz()
				.setEdtText(bundle.getString("BMapLocationAddr") != null ? bundle.getString("BMapLocationAddr") : "");
		if (ecKgzEntityVo != null) {
			updateData();
			initSelect();
		}
	}

	private void initSelect() {
		// TODO Auto-generated method stub
		// 初始是否下拉列表
		String sfTypeNo = "010599";// 是否
		List<String> list = globalCommonBll.getDictionaryNameList(sfTypeNo);
		if(viewHolder.getInscSfdw().getEdtTextValue().equals("")){
			// 初始是否代维下拉列表
			InputSelectAdapter sfdwAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInscSfdw().setAdapter(sfdwAdapter);
		}
		if(viewHolder.getInscSfnw().getEdtTextValue().equals("")){
			// 初始是否农网下拉列表
			InputSelectAdapter sfnwAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInscSfnw().setAdapter(sfnwAdapter);
		}
		if(viewHolder.getInscSfznbdz().getEdtTextValue().equals("")){
			// 初始是否智能变电站下拉列表
			InputSelectAdapter sfznbdzAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInscSfznbdz().setAdapter(sfznbdzAdapter);
		}
		// 初始电压等级下拉列表
		if(viewHolder.getInscDydj().getEdtTextValue().equals("")){
			String dydjTypeNo = DicNoEntity.dydjTypeNo;// 电压等级
			InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(dydjTypeNo));
			viewHolder.getInscDydj().setAdapter(dydjAdapter);
		}
		// 初始设备状态下拉列表
		if(viewHolder.getInscSbzt().getEdtTextValue().equals("")){
			String sbztTypeNo = "010402";// 设备状态
			InputSelectAdapter sbztAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(sbztTypeNo));
			viewHolder.getInscSbzt().setAdapter(sbztAdapter);
		}
		// 初始资产性质下拉列表
		if(viewHolder.getInscZcxz().getEdtTextValue().equals("")){
			String zcxzTypeNoGw = "010403";// 资产性质国网
			String zcxzTypeNoNw = "a010404";//资产性质南网
			if(GlobalEntry.zcxz){
				InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
						globalCommonBll.getDictionaryNameList(zcxzTypeNoNw));
				viewHolder.getInscZcxz().setAdapter(zcxzAdapter);
			}else {
				InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
						globalCommonBll.getDictionaryNameList(zcxzTypeNoGw));
				viewHolder.getInscZcxz().setAdapter(zcxzAdapter);
			}
		}
		// 初始防误误方式下拉列表
		if(viewHolder.getInscFwfs().getEdtTextValue().equals("")){
			String fwfsTypeNo = "010604086";// 防误误方式
			InputSelectAdapter fwfsAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(fwfsTypeNo));
			viewHolder.getInscFwfs().setAdapter(fwfsAdapter);
		}
		// 初始 电站重要级别下拉列表
		if(viewHolder.getInscDzzsjb().getEdtTextValue().equals("")){
			String dzzsjbTypeNo = "010604110";// 电站重要级别
			InputSelectAdapter dzzsjbAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(dzzsjbTypeNo));
			viewHolder.getInscDzzsjb().setAdapter(dzzsjbAdapter);
		}
		// 初始值班方式下拉列表
		if(viewHolder.getInscZbfs().getEdtTextValue().equals("")){
			String zbfsTypeNo = "010501";// 值班方式
			InputSelectAdapter zbfsAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(zbfsTypeNo));
			viewHolder.getInscZbfs().setAdapter(zbfsAdapter);
		}
		// 初始布置误方式下拉列表
		if(viewHolder.getInscBzfs().getEdtTextValue().equals("")){
			String bzfsTypeNo = "010604010";// 布置误方式
			InputSelectAdapter bzfsAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(bzfsTypeNo));
			viewHolder.getInscBzfs().setAdapter(bzfsAdapter);
		}
		// 初始污秽等级下拉列表
		if(viewHolder.getInscWsdj().getEdtTextValue().equals("")){
			String wsdjTypeNo = "010101";// 污秽等级
			InputSelectAdapter wsdjAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(wsdjTypeNo));
			viewHolder.getInscWsdj().setAdapter(wsdjAdapter);
		}
	}

	private void updateData() {

		viewHolder.getIncomSbmc().setEdtText(ecKgzEntityVo.getSbmc());
		viewHolder.getIncomYxbh().setEdtText(ecKgzEntityVo.getYxbh());
		viewHolder.getInscDydj().setEdtTextValue(ecKgzEntityVo.getDydj());
		viewHolder.getIncomDxmpId().setEdtText(ecKgzEntityVo.getDxmpid());
		if (GlobalEntry.ssds) {
			viewHolder.getInscSsds().setEdtText(ecKgzEntityVo.getSsds());
		}
		viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
		viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		viewHolder.getInscSbzt().setEdtTextValue(ecKgzEntityVo.getSbzt());
		String tyrp = DateUtils.date2Str(ecKgzEntityVo.getTyrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getIncomTyrq().setEdtText(tyrp);
		viewHolder.getInscZcxz().setEdtTextValue(ecKgzEntityVo.getZcxz());
		viewHolder.getIncomZcdw().setEdtText(ecKgzEntityVo.getZcdw());
		viewHolder.getInscSfznbdz().setEdtTextValue(ecKgzEntityVo.getSfznbdz());
		viewHolder.getIncomByjcxjgs().setEdtText(ecKgzEntityVo.getByjcxjg());
		viewHolder.getInscFwfs().setEdtTextValue(ecKgzEntityVo.getFwfs());
		viewHolder.getInscDzzsjb().setEdtTextValue(ecKgzEntityVo.getDzzyjb());
		viewHolder.getInscSfdw().setEdtTextValue(ecKgzEntityVo.getSfdw());
		viewHolder.getInscSfnw().setEdtTextValue(ecKgzEntityVo.getSfnw());
		viewHolder.getInscZbfs().setEdtTextValue(ecKgzEntityVo.getZbfs());
		viewHolder.getInscBzfs().setEdtTextValue(ecKgzEntityVo.getBzfs());
		viewHolder.getInscWsdj().setEdtTextValue(ecKgzEntityVo.getWhdj());
		viewHolder.getIncomGcbh().setEdtText(ecKgzEntityVo.getGcbh());
		viewHolder.getIncomGcmc().setEdtText(ecKgzEntityVo.getGcmc());
		viewHolder.getIncomBz().setEdtText(ecKgzEntityVo.getBz());
		viewHolder.getIncomZz().setEdtText(ecKgzEntityVo.getAddress());

		// 显示附件
		List<OfflineAttach> offlineAttachs = ecKgzEntityVo.getComUploadEntityList();
		if (offlineAttachs != null && offlineAttachs.size() > 0) {
			attachFiles = new ArrayList<String>();
			for (OfflineAttach attach : offlineAttachs) {
				attachFiles.add(attach.getFilePath());
			}
		}
		viewHolder.getAttaKgz().addExistsAttachments(attachFiles);

	}

	private void initEvent() {
		viewHolder.getInscYwdw().getEditTextView().setEnabled(false);
		viewHolder.getInscWhbz().getEditTextView().setEnabled(false);
		viewHolder.getIncomTyrq().setEditTextFocus(false);
		viewHolder.getIncomTyrq().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getIncomTyrq().getEditTextView());
			}
		});
		// 标题
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		// 关联
		viewHolder.getIvLinkDeviceEnter().setOnClickListener(ocl);
		viewHolder.getBtnLinkDeviceLink().setOnClickListener(ocl);
	}

	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_head_item_return: // 返回
			{
				saveAttachment();
				Intent intent = new Intent();
				intent.putExtra("kgz", ecKgzEntityVo);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: // 确定
			{
				saveData();
				if (ecKgzEntityVo == null) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("kgz", ecKgzEntityVo);
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
		intent.putExtra("LinkDeviceMark", TableNameEnum.KGZ.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, KgzLink_RequestCode);
	}

	/**
	 * 保存数据
	 */
	protected void saveData() {
		if (ecKgzEntityVo == null) {
			ecKgzEntityVo = new EcKgzEntityVo();
			ecKgzEntityVo.setObjId(UUIDGen.randomUUID());
			ecKgzEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecKgzEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecKgzEntityVo.setCjsj(DateUtils.getDate());
			Date tyrqDate = DateUtils.str2Date(viewHolder.getIncomTyrq().getEdtTextValue().trim(),
					DateUtils.date_sdf_wz_hm);
			if (tyrqDate == null) {
				tyrqDate = DateUtils.getDate();
			}
			ecKgzEntityVo.setTyrq(tyrqDate);
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecKgzEntityVo.setOperateMark(1);// 编辑下新增
			}
		}
		ecKgzEntityVo.setGxsj(DateUtils.getDate());
		ecKgzEntityVo.setSbmc(viewHolder.getIncomSbmc().getEdtTextValue().trim());
		ecKgzEntityVo.setYxbh(viewHolder.getIncomYxbh().getEdtTextValue().trim());
		ecKgzEntityVo.setDydj(viewHolder.getInscDydj().getEdtTextValue().trim());
		ecKgzEntityVo.setDxmpid(viewHolder.getIncomDxmpId().getEdtTextValue().trim());
		if (GlobalEntry.ssds) {
			ecKgzEntityVo.setSsds(viewHolder.getInscSsds().getEdtTextValue().trim());
		}
		ecKgzEntityVo.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecKgzEntityVo.setWhbz(GlobalEntry.loginResult.getDeptid());
		ecKgzEntityVo.setSbzt(viewHolder.getInscSbzt().getEdtTextValue().trim());
		ecKgzEntityVo.setZcxz(viewHolder.getInscZcxz().getEdtTextValue().trim());
		ecKgzEntityVo.setZcdw(viewHolder.getIncomZcdw().getEdtTextValue().trim());
		ecKgzEntityVo.setSfznbdz(viewHolder.getInscSfznbdz().getEdtTextValue().trim());
		ecKgzEntityVo.setByjcxjg(viewHolder.getIncomByjcxjgs().getEdtTextValue().trim());
		ecKgzEntityVo.setFwfs(viewHolder.getInscFwfs().getEdtTextValue().trim());
		ecKgzEntityVo.setDzzyjb(viewHolder.getInscDzzsjb().getEdtTextValue().trim());
		ecKgzEntityVo.setSfdw(viewHolder.getInscSfdw().getEdtTextValue().trim());
		ecKgzEntityVo.setSfnw(viewHolder.getInscSfnw().getEdtTextValue().trim());
		ecKgzEntityVo.setZbfs(viewHolder.getInscZbfs().getEdtTextValue().trim());
		ecKgzEntityVo.setBzfs(viewHolder.getInscBzfs().getEdtTextValue().trim());
		ecKgzEntityVo.setWhdj(viewHolder.getInscWsdj().getEdtTextValue().trim());
		ecKgzEntityVo.setGcbh(viewHolder.getIncomGcbh().getEdtTextValue().trim());
		ecKgzEntityVo.setGcmc(viewHolder.getIncomGcmc().getEdtTextValue().trim());
		ecKgzEntityVo.setBz(viewHolder.getIncomBz().getEdtTextValue().trim());
		ecKgzEntityVo.setAddress(viewHolder.getIncomZz().getEdtTextValue().trim());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAttaKgz().finish();
		// 附件操作
		saveAttachment();

	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (ecKgzEntityVo == null) {
			return;
		}
		comUpload = new ArrayList<OfflineAttach>();
		// 查找出附件组件中存在的附件路径
		AttachmentResult result = viewHolder.getAttaKgz().getResult();
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
		offlineAttachs = ecKgzEntityVo.getComUploadEntityList();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.KGZ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecKgzEntityVo.getObjId());
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
				offlineAttach.setOwnerCode(TableNameEnum.KGZ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecKgzEntityVo.getObjId());
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
		ecKgzEntityVo.setComUploadEntityList(comUpload);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == KgzLink_RequestCode) {// 设备关联回调
				EcKgzEntity entity = (EcKgzEntity) data.getSerializableExtra("LinkDeviceResult");
				try {
					ecKgzEntityVo = new EcKgzEntityVo();
					ClonesUtil.clones(ecKgzEntityVo, entity);
					if (ecKgzEntityVo != null) {
						ecKgzEntityVo.setOperateMark(3);// 设备关联
						updateData();
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			viewHolder.getAttaKgz().onActivityResult(requestCode, resultCode, data);
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
