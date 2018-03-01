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
import com.winway.android.edcollection.adding.viewholder.HwgViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.DicNoEntity;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.task.entity.TaskDeviceEntity;
import com.winway.android.edcollection.task.entity.TaskStateTypeEnum;
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
 * 环网柜
 * 
 * @author
 *
 */
public class HwgControll extends BaseControll<HwgViewHolder> {
	private List<OfflineAttach> comUpload;
	private EcHwgEntityVo ecHwgEntityVo;
	private ArrayList<String> attachFiles = null;
	private GlobalCommonBll globalCommonBll;
	private ComUploadBll comUploadBll;
	private TaskDeviceEntity deviceEntity;
	private final int HwgLink_RequestCode = 5;// 设备关联请求码
	private String rootPath;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		comUploadBll = new ComUploadBll(mActivity, GlobalEntry.prjDbUrl);
		globalCommonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		initAttachment();
		initData();
		initEvent();
	}
	
	// 初始化附件
	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/环网柜/";
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
		viewHolder.getInconJddz().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInconByjcxjgs().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
			viewHolder.getInconSsds().setVisibility(View.VISIBLE);
		}
		initSelect();
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		ecHwgEntityVo = (EcHwgEntityVo) bundle.getSerializable("HwgEntity");
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		if (deviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			ecHwgEntityVo = (EcHwgEntityVo) deviceEntity.getDevice();
			ecHwgEntityVo.setAttr(deviceEntity.getComUploadEntityList());
		}
		//使用百度地图地址
		String BMapLocationAddr= bundle.getString("BMapLocationAddr");
		if (BMapLocationAddr!=null) {
			viewHolder.getInconZz().setEdtText(BMapLocationAddr);
		}
		if (ecHwgEntityVo != null) {
			updateData();
			initSelect();
		}
	}

	private void initSelect() {
		// TODO Auto-generated method stub
		// 初始电压等级下拉列表
		if (viewHolder.getInscDldj().getEdtTextValue().equals("")) {
			String dydjTypeNo = DicNoEntity.dydjTypeNo;// 电压等级
			InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity, globalCommonBll.getDictionaryNameList(dydjTypeNo));
			viewHolder.getInscDldj().setAdapter(dydjAdapter);
		}
		// 初始设备状态下拉列表
		if (viewHolder.getInscSbzt().getEdtTextValue().equals("")) {
			String sbztTypeNo = "010402";// 设备状态
			InputSelectAdapter sbztAdapter = new InputSelectAdapter(mActivity, globalCommonBll.getDictionaryNameList(sbztTypeNo));
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
		// 初始地区特征下拉列表
		if (viewHolder.getInscDqtz().getEdtTextValue().equals("")) {
			String sqtzTypeNo = "0199001";// 地区特征
			InputSelectAdapter sqtzAdapter = new InputSelectAdapter(mActivity, globalCommonBll.getDictionaryNameList(sqtzTypeNo));
			viewHolder.getInscDqtz().setAdapter(sqtzAdapter);
		}
		// 初始重要程度下拉列表
		if (viewHolder.getInscZycd().getEdtTextValue().equals("")) {
			String zycdTypeNo = "0199003";// 重要程度
			InputSelectAdapter zycdAdapter = new InputSelectAdapter(mActivity, globalCommonBll.getDictionaryNameList(zycdTypeNo));
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
		// 设备标识
		// viewHolder.getInconSbid().setEdtText(ecHwgEntityVo.getSbid());
		viewHolder.getInconSbmc().setEdtText(ecHwgEntityVo.getSbmc());
		viewHolder.getInconYxbh().setEdtText(ecHwgEntityVo.getYxbh());
		viewHolder.getInconDxmpid().setEdtText(ecHwgEntityVo.getDxmpid());
		viewHolder.getInscDldj().setEdtTextValue(ecHwgEntityVo.getDldj());
		if (GlobalEntry.ssds) {
			viewHolder.getInconSsds().setEdtText(ecHwgEntityVo.getSsds());
		}
		viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
		viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		viewHolder.getInscSbzt().setEdtTextValue(ecHwgEntityVo.getSbzt());
		viewHolder.getInscSfdw().setEdtTextValue(ecHwgEntityVo.getSfdw());
		viewHolder.getInscSfnw().setEdtTextValue(ecHwgEntityVo.getSfnw());
		viewHolder.getInconXh().setEdtText(ecHwgEntityVo.getXh());
		viewHolder.getInconSccj().setEdtText(ecHwgEntityVo.getSccj());
		viewHolder.getInconCcbh().setEdtText(ecHwgEntityVo.getCcbh());
		String ccrq = DateUtils.date2Str(ecHwgEntityVo.getCcrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getInconCcrq().setEdtText(ccrq);
		String tyrq = DateUtils.date2Str(ecHwgEntityVo.getTyrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getInconTyrq().setEdtText(tyrq);
		viewHolder.getInconJddz().setEdtText(StringUtils.nullStrToEmpty(ecHwgEntityVo.getJddz()));
		viewHolder.getInconByjcxjgs().setEdtText(ecHwgEntityVo.getByjcxjgs());
		viewHolder.getInconZz().setEdtText(ecHwgEntityVo.getZz());
		viewHolder.getInscDqtz().setEdtTextValue(ecHwgEntityVo.getDqtz());
		viewHolder.getInscZycd().setEdtTextValue(ecHwgEntityVo.getZycd());
		viewHolder.getInscZcxz().setEdtTextValue(ecHwgEntityVo.getZcxz());
		viewHolder.getInconZcdw().setEdtText(ecHwgEntityVo.getZcdw());
		viewHolder.getInconGcbh().setEdtText(ecHwgEntityVo.getGcbh());
		viewHolder.getInconGcmc().setEdtText(ecHwgEntityVo.getGcmc());
		viewHolder.getInscSbzjfs().setEdtTextValue(ecHwgEntityVo.getSbzjfs());
		viewHolder.getInconBz().setEdtText(ecHwgEntityVo.getBz());
		// 显示附件
		List<OfflineAttach> offlineAttachs = ecHwgEntityVo.getAttr();
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
				intent.putExtra("hwg", ecHwgEntityVo);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: { // 确定
//				if (viewHolder.getInconSbmc().getEdtTextValue().isEmpty()) {
//					ToastUtil.show(mActivity, "设备名称不能为空", 200);
//					return;
//				}
				saveData();
				if (ecHwgEntityVo == null) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("hwg", ecHwgEntityVo);
				// 任务明细那里接收此对象
				intent.putExtra("hwgTaskState", TaskStateTypeEnum.ycj.getState());
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
		intent.putExtra("LinkDeviceMark", TableNameEnum.HWG.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, HwgLink_RequestCode);
	}

	/**
	 * 保存数据
	 */
	protected void saveData() {
		if (ecHwgEntityVo == null) {
			ecHwgEntityVo = new EcHwgEntityVo();
			ecHwgEntityVo.setObjId(UUIDGen.randomUUID());
			ecHwgEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecHwgEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecHwgEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecHwgEntityVo.setOperateMark(1);// 编辑下新增
			}
		}
		ecHwgEntityVo.setGxsj(DateUtils.getDate());
		ecHwgEntityVo.setSbmc(viewHolder.getInconSbmc().getEdtTextValue().trim());
		ecHwgEntityVo.setYxbh(viewHolder.getInconYxbh().getEdtTextValue().trim());
		ecHwgEntityVo.setDxmpid(viewHolder.getInconDxmpid().getEdtTextValue().trim());
		ecHwgEntityVo.setDldj(viewHolder.getInscDldj().getEdtTextValue().trim());
		Date ccrqDate = DateUtils.str2Date(viewHolder.getInconCcrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
		if (ccrqDate == null) {
			ccrqDate = DateUtils.getDate();
		}
		ecHwgEntityVo.setCcrq(ccrqDate);
		Date tyrqDate = DateUtils.str2Date(viewHolder.getInconTyrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
		if (tyrqDate == null) {
			tyrqDate = DateUtils.getDate();
		}
		ecHwgEntityVo.setTyrq(tyrqDate);
		if (GlobalEntry.ssds) {
			ecHwgEntityVo.setSsds(viewHolder.getInconSsds().getEdtTextValue().trim());
		}
		ecHwgEntityVo.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecHwgEntityVo.setWhbz(GlobalEntry.loginResult.getDeptid());
		ecHwgEntityVo.setSbzt(viewHolder.getInscSbzt().getEdtTextValue().trim());
		ecHwgEntityVo.setSfdw(viewHolder.getInscSfdw().getEdtTextValue().trim());
		ecHwgEntityVo.setSfnw(viewHolder.getInscSfnw().getEdtTextValue().trim());
		ecHwgEntityVo.setXh(viewHolder.getInconXh().getEdtTextValue().trim());
		ecHwgEntityVo.setSccj(viewHolder.getInconSccj().getEdtTextValue().trim());
		ecHwgEntityVo.setCcbh(viewHolder.getInconCcbh().getEdtTextValue().trim());
		String jddz = viewHolder.getInconJddz().getEdtTextValue().trim();
		ecHwgEntityVo.setJddz(Integer.parseInt(jddz.isEmpty()? "0" : jddz));
		ecHwgEntityVo.setByjcxjgs(viewHolder.getInconByjcxjgs().getEdtTextValue().trim());
		ecHwgEntityVo.setZz(viewHolder.getInconZz().getEdtTextValue().trim());
		ecHwgEntityVo.setDqtz(viewHolder.getInscDqtz().getEdtTextValue().trim());
		ecHwgEntityVo.setZycd(viewHolder.getInscZycd().getEdtTextValue().trim());
		ecHwgEntityVo.setZcxz(viewHolder.getInscZcxz().getEdtTextValue().trim());
		ecHwgEntityVo.setZcdw(viewHolder.getInconZcdw().getEdtTextValue().trim());
		ecHwgEntityVo.setGcbh(viewHolder.getInconGcbh().getEdtTextValue().trim());
		ecHwgEntityVo.setGcmc(viewHolder.getInconGcmc().getEdtTextValue().trim());
		ecHwgEntityVo.setSbzjfs(viewHolder.getInscSbzjfs().getEdtTextValue().trim());
		ecHwgEntityVo.setBz(viewHolder.getInconBz().getEdtTextValue().trim());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAvAttachment().finish();
		// 附件操作
		saveAttachment();
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (ecHwgEntityVo == null) {
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
		offlineAttachs = ecHwgEntityVo.getAttr();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setOwnerCode(TableNameEnum.HWG.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecHwgEntityVo.getObjId());
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
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.HWG.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecHwgEntityVo.getObjId());
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
		ecHwgEntityVo.setAttr(comUpload);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == HwgLink_RequestCode) {
				EcHwgEntity entity = (EcHwgEntity) data.getSerializableExtra("LinkDeviceResult");
				try {
					ecHwgEntityVo = new EcHwgEntityVo();
					ClonesUtil.clones(ecHwgEntityVo, entity);
					if (ecHwgEntityVo != null) {
						ecHwgEntityVo.setOperateMark(3);// 设备关联
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
