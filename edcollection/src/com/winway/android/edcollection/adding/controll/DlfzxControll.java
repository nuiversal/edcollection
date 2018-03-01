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
import com.winway.android.edcollection.adding.viewholder.DlfzxViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.DicNoEntity;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
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
 * 电缆分支箱
 * 
 * @author
 *
 */
public class DlfzxControll extends BaseControll<DlfzxViewHolder> {
	private List<OfflineAttach> comUpload;
	private EcDlfzxEntityVo ecDlfzxEntityVo;
	private ArrayList<String> attachFiles = null;
	private GlobalCommonBll globalCommonBll;
	private ComUploadBll comUploadBll;
	private TaskDeviceEntity deviceEntity;
	private final int DlfzxlLink_RequestCode = 5;// 设备关联请求码
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

	// 初始化附件
	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/电缆分支箱/";
		AttachmentInit.initGloble(fileDir + "/video", fileDir + "/photo", fileDir + "/voice", fileDir + "/text",fileDir+"/vr");
		AttachmentAttrs attrs = AttachmentAttrs.getSinggleAttr();
		attrs.activity = mActivity;
		/** 控件附件采集的最大数量 */
		attrs.maxPhoto = 5;
		attrs.maxVideo = 5;
		attrs.maxVoice = 5;
		attrs.maxText = 5;
		attrs.maxVr=5;
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
		// 出厂日期
		viewHolder.getInconCcrq().setEditTextFocus(false);
		viewHolder.getInconCcrq().getEditTextView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconCcrq().getEditTextView());
			}
		});
		// 投运日期
		viewHolder.getInconTyrq().setEditTextFocus(false);
		viewHolder.getInconTyrq().getEditTextView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconTyrq().getEditTextView());
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
		ecDlfzxEntityVo = (EcDlfzxEntityVo) bundle.getSerializable("DlfzxEntity");
		// 从任务明细传过来的对象
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		if (deviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			ecDlfzxEntityVo = (EcDlfzxEntityVo) deviceEntity.getDevice();
			ecDlfzxEntityVo.setAttr(deviceEntity.getComUploadEntityList());
		}

		// 使用百度地图地址
		String BMapLocationAddr = bundle.getString("BMapLocationAddr");
		if (BMapLocationAddr != null) {
			viewHolder.getInconZz().setEdtText(BMapLocationAddr);
		}
		if (ecDlfzxEntityVo != null) {
			updateData();
			initSelect();
		}
	}

	private void initSelect() {
		// TODO Auto-generated method stub
		// 初始电压等级下拉列表
		if (viewHolder.getInscDldj().getEdtTextValue().equals("")) {
			String dydjTypeNo = DicNoEntity.dydjTypeNo;// 电压等级
			InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(dydjTypeNo));
			viewHolder.getInscDldj().setAdapter(dydjAdapter);
		}
		// 初始设备状态下拉列表
		if (viewHolder.getInscSbzt().getEdtTextValue().equals("")) {
			String sbztTypeNo = "010402";// 设备状态
			InputSelectAdapter sbztAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(sbztTypeNo));
			viewHolder.getInscSbzt().setAdapter(sbztAdapter);
		}
		// 初始是否下拉列表
		if (viewHolder.getInscSfdw().getEdtTextValue().equals("")) {
			String sfTypeNo = "010599";// 是否
			List<String> list = globalCommonBll.getDictionaryNameList(sfTypeNo);
			// 初始是否代维下拉列表
			InputSelectAdapter sfdwAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInscSfdw().setAdapter(sfdwAdapter);
		}
		// 初始是否农网下拉列表
		if (viewHolder.getInscSfnw().getEdtTextValue().equals("")) {
			String sfTypeNo = "010599";// 是否
			List<String> list = globalCommonBll.getDictionaryNameList(sfTypeNo);
			InputSelectAdapter sfnwAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInscSfnw().setAdapter(sfnwAdapter);
		}
		// // 初始类型下拉列表
		if (viewHolder.getInscLx().getEdtTextValue().equals("")) {
			String lxTypeNo = "fzx010604088";// 类型
			InputSelectAdapter lxAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(lxTypeNo));
			viewHolder.getInscLx().setAdapter(lxAdapter);
		}
		// 初始地区特征下拉列表
		if (viewHolder.getInscDqtz().getEdtTextValue().equals("")) {
			String sqtzTypeNo = "0199001";// 地区特征
			InputSelectAdapter sqtzAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(sqtzTypeNo));
			viewHolder.getInscDqtz().setAdapter(sqtzAdapter);
		}
		// 初始重要程度下拉列表
		if (viewHolder.getInscZycd().getEdtTextValue().equals("")) {
			String zycdTypeNo = "0199003";// 重要程度
			InputSelectAdapter zycdAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(zycdTypeNo));
			viewHolder.getInscZycd().setAdapter(zycdAdapter);
		}
		// 初始资产性质下拉列表
		if (viewHolder.getInscZcxz().getEdtTextValue().equals("")) {
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
		// 初始设备增加方式下拉列表
		if (viewHolder.getInscSbzjfs().getEdtTextValue().equals("")) {
			String sbzjfsTypeNo = "010419";// 设备增加方式
			InputSelectAdapter sbzjfsAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(sbzjfsTypeNo));
			viewHolder.getInscSbzjfs().setAdapter(sbzjfsAdapter);
		}
	}

	private void updateData() {
		viewHolder.getInconSbmc().setEdtText(ecDlfzxEntityVo.getSbmc());
		viewHolder.getInconYxbh().setEdtText(ecDlfzxEntityVo.getYxbh());
		viewHolder.getInconDxmpid().setEdtText(ecDlfzxEntityVo.getDxmpid());
		viewHolder.getInscDldj().setEdtTextValue(ecDlfzxEntityVo.getDldj());
		if (GlobalEntry.ssds) {
			viewHolder.getInscSsds().setEdtText(ecDlfzxEntityVo.getSsds());
		}
		viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
		viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		viewHolder.getInscSbzt().setEdtTextValue(ecDlfzxEntityVo.getSbzt());
		viewHolder.getInscSfdw().setEdtTextValue(ecDlfzxEntityVo.getSfdw());
		viewHolder.getInscSfnw().setEdtTextValue(ecDlfzxEntityVo.getSfnw());
		viewHolder.getInscLx().setEdtTextValue(ecDlfzxEntityVo.getLx());
		viewHolder.getInconXh().setEdtText(ecDlfzxEntityVo.getXh());
		viewHolder.getInconSccj().setEdtText(ecDlfzxEntityVo.getSccj());
		viewHolder.getInconCcbh().setEdtText(ecDlfzxEntityVo.getCcbh());
		String ccrq = DateUtils.date2Str(ecDlfzxEntityVo.getCcrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getInconCcrq().setEdtText(ccrq);
		String tyrq = DateUtils.date2Str(ecDlfzxEntityVo.getTyrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getInconTyrq().setEdtText(tyrq);
		viewHolder.getInconJddz().setEdtText(StringUtils.nullStrToEmpty(ecDlfzxEntityVo.getJddz()));
		viewHolder.getInconZz().setEdtText(ecDlfzxEntityVo.getZz());
		viewHolder.getInscDqtz().setEdtTextValue(ecDlfzxEntityVo.getDqtz());
		viewHolder.getInscZycd().setEdtTextValue(ecDlfzxEntityVo.getZycd());
		viewHolder.getInscZcxz().setEdtTextValue(ecDlfzxEntityVo.getZcxz());
		viewHolder.getInconZcdw().setEdtText(ecDlfzxEntityVo.getZcdw());
		viewHolder.getInconGcbh().setEdtText(ecDlfzxEntityVo.getGcbh());
		viewHolder.getInconGcmc().setEdtText(ecDlfzxEntityVo.getGcmc());
		viewHolder.getInscSbzjfs().setEdtTextValue(ecDlfzxEntityVo.getSbzjfs());
		viewHolder.getInconBz().setEdtText(ecDlfzxEntityVo.getBz());
		// 显示附件
		List<OfflineAttach> offlineAttachs = ecDlfzxEntityVo.getAttr();
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
			case R.id.tv_head_item_return: {
				saveAttachment();
				Intent intent = new Intent();
				intent.putExtra("dlfzx", ecDlfzxEntityVo);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: {
//				if (viewHolder.getInconSbmc().getEdtTextValue().isEmpty()) {
//					ToastUtil.show(mActivity, "设备名称不能为空", 200);
//					return;
//				}
				saveData();
				if (ecDlfzxEntityVo == null) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("dlfzx", ecDlfzxEntityVo);
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
		intent.putExtra("LinkDeviceMark", TableNameEnum.DLFZX.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, DlfzxlLink_RequestCode);

	}

	/**
	 * 保存数据
	 */
	protected void saveData() {
		if (ecDlfzxEntityVo == null) {
			ecDlfzxEntityVo = new EcDlfzxEntityVo();
			ecDlfzxEntityVo.setObjId(UUIDGen.randomUUID());
			ecDlfzxEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecDlfzxEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecDlfzxEntityVo.setCjsj(DateUtils.getDate());
			Date ccrqDate = DateUtils.str2Date(viewHolder.getInconCcrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
			if (ccrqDate == null) {
				ccrqDate = DateUtils.getDate();
			}
			ecDlfzxEntityVo.setCcrq(ccrqDate);
			Date tyrqDate = DateUtils.str2Date(viewHolder.getInconTyrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
			if (tyrqDate == null) {
				tyrqDate = DateUtils.getDate();
			}
			ecDlfzxEntityVo.setTyrq(tyrqDate);
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecDlfzxEntityVo.setOperateMark(1);// 编辑下添加
			}
		}
		ecDlfzxEntityVo.setGxsj(DateUtils.getDate());
		ecDlfzxEntityVo.setSbmc(viewHolder.getInconSbmc().getEdtTextValue().trim());
		ecDlfzxEntityVo.setYxbh(viewHolder.getInconYxbh().getEdtTextValue().trim());
		ecDlfzxEntityVo.setDxmpid(viewHolder.getInconDxmpid().getEdtTextValue().trim());
		ecDlfzxEntityVo.setDldj(viewHolder.getInscDldj().getEdtTextValue().trim());
		if (GlobalEntry.ssds) {
			ecDlfzxEntityVo.setSsds(viewHolder.getInscSsds().getEdtTextValue().trim());
		}
		ecDlfzxEntityVo.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecDlfzxEntityVo.setWhbz(GlobalEntry.loginResult.getDeptid());
		ecDlfzxEntityVo.setSbzt(viewHolder.getInscSbzt().getEdtTextValue().trim());
		ecDlfzxEntityVo.setSfdw(viewHolder.getInscSfdw().getEdtTextValue().trim());
		ecDlfzxEntityVo.setSfnw(viewHolder.getInscSfnw().getEdtTextValue().trim());
		ecDlfzxEntityVo.setLx(viewHolder.getInscLx().getEdtTextValue().trim());
		ecDlfzxEntityVo.setXh(viewHolder.getInconXh().getEdtTextValue().trim());
		ecDlfzxEntityVo.setSccj(viewHolder.getInconSccj().getEdtTextValue().trim());
		ecDlfzxEntityVo.setCcbh(viewHolder.getInconCcbh().getEdtTextValue().trim());
		String jddz = viewHolder.getInconJddz().getEdtTextValue().trim();
		ecDlfzxEntityVo.setJddz(Integer.parseInt(jddz.isEmpty()  ? "0" : jddz));
		ecDlfzxEntityVo.setZz(viewHolder.getInconZz().getEdtTextValue().trim());
		ecDlfzxEntityVo.setDqtz(viewHolder.getInscDqtz().getEdtTextValue().trim());
		ecDlfzxEntityVo.setZycd(viewHolder.getInscZycd().getEdtTextValue().trim());
		ecDlfzxEntityVo.setZcxz(viewHolder.getInscZcxz().getEdtTextValue().trim());
		ecDlfzxEntityVo.setZcdw(viewHolder.getInconZcdw().getEdtTextValue().trim());
		ecDlfzxEntityVo.setGcbh(viewHolder.getInconGcbh().getEdtTextValue().trim());
		ecDlfzxEntityVo.setGcmc(viewHolder.getInconGcmc().getEdtTextValue().trim());
		ecDlfzxEntityVo.setSbzjfs(viewHolder.getInscSbzjfs().getEdtTextValue().trim());
		ecDlfzxEntityVo.setBz(viewHolder.getInconBz().getEdtTextValue().trim());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAvAttachment().finish();
		// 附件操作
		saveAttachment();
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (ecDlfzxEntityVo == null) {
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
		offlineAttachs = ecDlfzxEntityVo.getAttr();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.DLFZX.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecDlfzxEntityVo.getObjId());
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
				offlineAttach.setOwnerCode(TableNameEnum.DLFZX.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecDlfzxEntityVo.getObjId());
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
		ecDlfzxEntityVo.setAttr(comUpload);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == DlfzxlLink_RequestCode) {// 设备关联
				EcDlfzxEntity entity = (EcDlfzxEntity) data.getSerializableExtra("LinkDeviceResult");
				try {
					ecDlfzxEntityVo = new EcDlfzxEntityVo();
					ClonesUtil.clones(ecDlfzxEntityVo, entity);
					if (ecDlfzxEntityVo != null) {
						ecDlfzxEntityVo.setOperateMark(3);// 设备关联
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
