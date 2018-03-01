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
import com.winway.android.edcollection.adding.viewholder.XsbdzViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.DicNoEntity;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcXsbdzEntityVo;
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
 * @author lyh
 * @data 2017年2月15日
 */
public class XsbdzControll extends BaseControll<XsbdzViewHolder> {

	private List<OfflineAttach> comUpload;
	private EcXsbdzEntityVo ecXsbdzEntityVo;
	private GlobalCommonBll globalCommonBll;
	private ArrayList<String> attachFiles = null;
	private ComUploadBll comUploadBll;
	private TaskDeviceEntity deviceEntity;
	private final int XsbdzLink_RequestCode = 5;// 设备关联请求码
	private String rootPath;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		globalCommonBll = new GlobalCommonBll(mActivity, GlobalEntry.globalDbUrl);
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		initAttachment();
		initDatas();
		initEvent();
	}

	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/箱式变电站/";
		AttachmentInit.initGloble(fileDir + "/video", fileDir + "/photo", fileDir + "/voice", fileDir + "/text",
				fileDir + "/vr");
		AttachmentAttrs attrs = AttachmentAttrs.getSinggleAttr();
		attrs.activity = mActivity;
		/** 控件附件采集的最大数量 */
		attrs.maxPhoto = 5;
		attrs.maxVideo = 5;
		attrs.maxVoice = 5;
		attrs.maxText = 5;
		attrs.hasFinish = false;// 隐藏完毕按钮
		viewHolder.getAttaXsbdz().setAttachmentAttrs(attrs);
	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		if (GlobalEntry.loginResult != null) {
			viewHolder.getInSelConYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInSelConWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		}
		// 所属地市是否显示
		if (GlobalEntry.ssds) {
			viewHolder.getInSelConSsds().setVisibility(View.VISIBLE);
		}
		initSelect();
		viewHolder.getInSelConDydj()
		.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
viewHolder.getInconPbzrl()
		.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
viewHolder.getInconJddz()
		.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		ecXsbdzEntityVo = (EcXsbdzEntityVo) bundle.getSerializable("EcXsbdzEntity");
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		if (deviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			ecXsbdzEntityVo = (EcXsbdzEntityVo) deviceEntity.getDevice();
			ecXsbdzEntityVo.setComUploadEntityList(deviceEntity.getComUploadEntityList());
		}

		// 取路径点的位置
		viewHolder.getInconZz()
				.setEdtText(bundle.getString("BMapLocationAddr") != null ? bundle.getString("BMapLocationAddr") : "");
		// 处理编辑情况
		if (ecXsbdzEntityVo != null) {
			updateData();
			initSelect();
		}

	}

	private void initSelect() {
		// TODO Auto-generated method stub
		// 初始电压等级下拉列表
		if(viewHolder.getInSelConDydj().getEdtTextValue().equals("")){
			String dydjTypeNo = DicNoEntity.dydjTypeNo;// 电压等级
			InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(dydjTypeNo));
			viewHolder.getInSelConDydj().setAdapter(dydjAdapter);
		}
		// 初始设备状态下拉列表
		if(viewHolder.getInSelConSbzt().getEdtTextValue().equals("")){
			String sbztTypeNo = "010402";// 设备状态
			InputSelectAdapter sbztAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(sbztTypeNo));
			viewHolder.getInSelConSbzt().setAdapter(sbztAdapter);
		}
		// 初始是否下拉列表
		String sfTypeNo = "010599";// 是否
		List<String> list = globalCommonBll.getDictionaryNameList(sfTypeNo);
		if(viewHolder.getInSelConSfdw().getEdtTextValue().equals("")){
			// 初始是否代维下拉列表
			InputSelectAdapter sfdwAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInSelConSfdw().setAdapter(sfdwAdapter);
		}
		if(viewHolder.getInSelConSfnw().getEdtTextValue().equals("")){
			// 初始是否农网下拉列表
			InputSelectAdapter sfnwAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInSelConSfnw().setAdapter(sfnwAdapter);
		}
		if(viewHolder.getInSelConSfjyhwg().getEdtTextValue().equals("")){
			// 初始是否具有环网下拉列表
			InputSelectAdapter sfjyhwAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInSelConSfjyhwg().setAdapter(sfjyhwAdapter);
		}
		// 初始箱变类型下拉列表
		if(viewHolder.getInSelConXblx().getEdtTextValue().equals("")){
			String xblxTypeNo = "010604087";// 箱变类型
			InputSelectAdapter xblxAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(xblxTypeNo));
			viewHolder.getInSelConXblx().setAdapter(xblxAdapter);
		}
		// 初始地区特征下拉列表
		if(viewHolder.getInSelConDqtz().getEdtTextValue().equals("")){
			String dqtzTypeNo = "0199001";// 地区特征
			InputSelectAdapter dqtzAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(dqtzTypeNo));
			viewHolder.getInSelConDqtz().setAdapter(dqtzAdapter);
		}
		// 初始重要程度下拉列表
		if(viewHolder.getInSelConZycd().getEdtTextValue().equals("")){
			String zycdTypeNo = "0199003";// 重要程度
			InputSelectAdapter zycdAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(zycdTypeNo));
			viewHolder.getInSelConZycd().setAdapter(zycdAdapter);
		}
		// 初始资产性质下拉列表
		if(viewHolder.getInSelConZcxz().getEdtTextValue().equals("")){
			String zcxzTypeNoGw = "010403";// 资产性质国网
			String zcxzTypeNoNw = "a010404";//资产性质南网
			if(GlobalEntry.zcxz){
				InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
						globalCommonBll.getDictionaryNameList(zcxzTypeNoNw));
				viewHolder.getInSelConZcxz().setAdapter(zcxzAdapter);
			}else {
				InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
						globalCommonBll.getDictionaryNameList(zcxzTypeNoGw));
				viewHolder.getInSelConZcxz().setAdapter(zcxzAdapter);
			}
		}
		// 初始设备增加方式下拉列表
		if(viewHolder.getInSelComSbzjfs().getEdtTextValue().equals("")){
			
			String sbzjfsTypeNo = "010419";// 设备增加方式
			InputSelectAdapter sbzjfsAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(sbzjfsTypeNo));
			viewHolder.getInSelComSbzjfs().setAdapter(sbzjfsAdapter);
		}
		
	}

	private void updateData() {
		viewHolder.getInconSbmc().setEdtText(ecXsbdzEntityVo.getSbmc());
		viewHolder.getInconYxbh().setEdtText(ecXsbdzEntityVo.getYxbh());
		viewHolder.getInconDxmpid().setEdtText(ecXsbdzEntityVo.getDxmpid());
		if (GlobalEntry.ssds) {
			viewHolder.getInSelConSsds().setEdtText(ecXsbdzEntityVo.getSsds());
		}
		viewHolder.getInSelConYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
		viewHolder.getInSelConWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		viewHolder.getInconXh().setEdtText(ecXsbdzEntityVo.getXh());
		viewHolder.getInconSccj().setEdtText(ecXsbdzEntityVo.getSccj());
		viewHolder.getInconCcbh().setEdtText(ecXsbdzEntityVo.getCcbh());
		String ccrq = DateUtils.date2Str(ecXsbdzEntityVo.getCcrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getInconCcrq().setEdtText(ccrq);
		String tyrq = DateUtils.date2Str(ecXsbdzEntityVo.getTyrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getInconTyrq().setEdtText(tyrq);
		viewHolder.getInconPbzrl().setEdtText(ecXsbdzEntityVo.getPbzrl());
		viewHolder.getInconJddz().setEdtText(StringUtils.nullStrToEmpty(ecXsbdzEntityVo.getJddz()));
		viewHolder.getInconZz().setEdtText(ecXsbdzEntityVo.getZz());
		viewHolder.getInSelConDydj().setEdtTextValue(ecXsbdzEntityVo.getDydj());
		viewHolder.getInSelConSbzt().setEdtTextValue(ecXsbdzEntityVo.getSbzt());
		viewHolder.getInSelConSfdw().setEdtTextValue(ecXsbdzEntityVo.getSfdw());
		viewHolder.getInSelConSfnw().setEdtTextValue(ecXsbdzEntityVo.getSfnw());
		viewHolder.getInSelConXblx().setEdtTextValue(ecXsbdzEntityVo.getXblx());
		viewHolder.getInSelConSfjyhwg().setEdtTextValue(ecXsbdzEntityVo.getSfjyhw());
		viewHolder.getInSelConDqtz().setEdtTextValue(ecXsbdzEntityVo.getDqtz());
		viewHolder.getInSelConZycd().setEdtTextValue(ecXsbdzEntityVo.getZycd());
		viewHolder.getInSelConZcxz().setEdtTextValue(ecXsbdzEntityVo.getZcxz());
		viewHolder.getInconZcdw().setEdtText(ecXsbdzEntityVo.getZcdw());
		viewHolder.getInconGcbh().setEdtText(ecXsbdzEntityVo.getGcbh());
		viewHolder.getInconGcmc().setEdtText(ecXsbdzEntityVo.getGcmc());
		viewHolder.getInSelComSbzjfs().setEdtTextValue(ecXsbdzEntityVo.getSbzjfs());
		viewHolder.getInconBz().setEdtText(ecXsbdzEntityVo.getBz());

		// 显示附件
		List<OfflineAttach> offlineAttachs = ecXsbdzEntityVo.getComUploadEntityList();
		if (offlineAttachs != null && offlineAttachs.size() > 0) {
			attachFiles = new ArrayList<String>();
			for (OfflineAttach attach : offlineAttachs) {
				attachFiles.add(attach.getFilePath());
			}
		}
		viewHolder.getAttaXsbdz().addExistsAttachments(attachFiles);
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		viewHolder.getInSelConYwdw().getEditTextView().setEnabled(false);
		viewHolder.getInSelConWhbz().getEditTextView().setEnabled(false);
		// TODO Auto-generated method stub
		// 出厂日期
		viewHolder.getInconCcrq().setEditTextFocus(false);
		viewHolder.getInconCcrq().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconCcrq().getEditTextView());
			}
		});

		// 投运日期
		viewHolder.getInconTyrq().setEditTextFocus(false);
		viewHolder.getInconTyrq().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconTyrq().getEditTextView());
			}
		});
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getIvLinkDeviceEnter().setOnClickListener(ocl);
		viewHolder.getBtnLinkDeviceLink().setOnClickListener(ocl);
	}

	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: { // 返回
				saveAttachment();
				Intent intent = new Intent();
				intent.putExtra("xsbdz", ecXsbdzEntityVo);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: { // 确定
				saveData();
				if (ecXsbdzEntityVo == null) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("xsbdz", ecXsbdzEntityVo);
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
		intent.putExtra("LinkDeviceMark", TableNameEnum.XSBDZ.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, XsbdzLink_RequestCode);
	}

	/**
	 * 保存数据
	 */
	protected void saveData() {
		if (ecXsbdzEntityVo == null) {
			ecXsbdzEntityVo = new EcXsbdzEntityVo();
			ecXsbdzEntityVo.setObjId(UUIDGen.randomUUID());
			ecXsbdzEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecXsbdzEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecXsbdzEntityVo.setCjsj(DateUtils.getDate());
			Date ccrqDate = DateUtils.str2Date(viewHolder.getInconCcrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
			if (ccrqDate == null) {
				ccrqDate = DateUtils.getDate();
			}
			ecXsbdzEntityVo.setCcrq(ccrqDate);
			Date tyrqDate = DateUtils.str2Date(viewHolder.getInconTyrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
			if (tyrqDate == null) {
				tyrqDate = DateUtils.getDate();
			}
			ecXsbdzEntityVo.setTyrq(tyrqDate);
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecXsbdzEntityVo.setOperateMark(1);// 编辑下新增操作
			}
		}
		ecXsbdzEntityVo.setGxsj(DateUtils.getDate());
		// 设备标识
		ecXsbdzEntityVo.setSbmc(viewHolder.getInconSbmc().getEdtTextValue().trim());
		ecXsbdzEntityVo.setYxbh(viewHolder.getInconYxbh().getEdtTextValue().trim());
		ecXsbdzEntityVo.setDydj(viewHolder.getInSelConDydj().getEdtTextValue().trim());
		ecXsbdzEntityVo.setDxmpid(viewHolder.getInconDxmpid().getEdtTextValue().trim());
		if (GlobalEntry.ssds) {
			ecXsbdzEntityVo.setSsds(viewHolder.getInSelConSsds().getEdtTextValue().trim());
		}
		ecXsbdzEntityVo.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecXsbdzEntityVo.setWhbz(GlobalEntry.loginResult.getDeptid());
		ecXsbdzEntityVo.setSbzt(viewHolder.getInSelConSbzt().getEdtTextValue().trim());
		ecXsbdzEntityVo.setSfdw(viewHolder.getInSelConSfdw().getEdtTextValue().trim());
		ecXsbdzEntityVo.setSfnw(viewHolder.getInSelConSfnw().getEdtTextValue().trim());
		ecXsbdzEntityVo.setXblx(viewHolder.getInSelConXblx().getEdtTextValue().trim());
		ecXsbdzEntityVo.setSfjyhw(viewHolder.getInSelConSfjyhwg().getEdtTextValue().trim());
		ecXsbdzEntityVo.setXh(viewHolder.getInconXh().getEdtTextValue().trim());
		ecXsbdzEntityVo.setSccj(viewHolder.getInconSccj().getEdtTextValue().trim());
		ecXsbdzEntityVo.setCcbh(viewHolder.getInconCcbh().getEdtTextValue().trim());
		ecXsbdzEntityVo.setPbzrl(viewHolder.getInconPbzrl().getEdtTextValue().trim());
		if (viewHolder.getInconJddz().getEdtTextValue().isEmpty()) {
			ecXsbdzEntityVo.setJddz(0);
		} else {
			ecXsbdzEntityVo.setJddz(Integer.parseInt(viewHolder.getInconJddz().getEdtTextValue().trim()));
		}
		ecXsbdzEntityVo.setZz(viewHolder.getInconZz().getEdtTextValue().trim());
		ecXsbdzEntityVo.setDqtz(viewHolder.getInSelConDqtz().getEdtTextValue().trim());
		ecXsbdzEntityVo.setZycd(viewHolder.getInSelConZycd().getEdtTextValue().trim());
		ecXsbdzEntityVo.setZcxz(viewHolder.getInSelConZcxz().getEdtTextValue().trim());
		ecXsbdzEntityVo.setZcdw(viewHolder.getInconZcdw().getEdtTextValue().trim());
		ecXsbdzEntityVo.setGcbh(viewHolder.getInconGcbh().getEdtTextValue().trim());
		ecXsbdzEntityVo.setGcmc(viewHolder.getInconGcmc().getEdtTextValue().trim());
		ecXsbdzEntityVo.setSbzjfs(viewHolder.getInSelComSbzjfs().getEdtTextValue().trim());
		ecXsbdzEntityVo.setBz(viewHolder.getInconBz().getEdtTextValue().trim());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAttaXsbdz().finish();
		// 附件操作
		saveAttachment();
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (ecXsbdzEntityVo == null) {
			return;
		}
		comUpload = new ArrayList<OfflineAttach>();
		// 查找出附件组件中存在的附件路径
		AttachmentResult result = viewHolder.getAttaXsbdz().getResult();
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
		offlineAttachs = ecXsbdzEntityVo.getComUploadEntityList();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.XSBDZ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecXsbdzEntityVo.getObjId());
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
				offlineAttach.setOwnerCode(TableNameEnum.XSBDZ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecXsbdzEntityVo.getObjId());
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
		ecXsbdzEntityVo.setComUploadEntityList(comUpload);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == XsbdzLink_RequestCode) {// 设备关联回调
				EcXsbdzEntity entity = (EcXsbdzEntity) data.getSerializableExtra("LinkDeviceResult");
				try {
					ecXsbdzEntityVo = new EcXsbdzEntityVo();
					ClonesUtil.clones(ecXsbdzEntityVo, entity);
					if (ecXsbdzEntityVo != null) {
						ecXsbdzEntityVo.setOperateMark(3);// 设备关联
						updateData();
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			viewHolder.getAttaXsbdz().onActivityResult(requestCode, resultCode, data);
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
