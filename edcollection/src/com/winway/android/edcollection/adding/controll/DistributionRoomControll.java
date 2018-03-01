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
import com.winway.android.edcollection.adding.viewholder.DistributionRoomViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.DicNoEntity;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
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
 * 配电室
 * 
 * @author lyh
 * @data 2017年2月13日
 */
public class DistributionRoomControll extends BaseControll<DistributionRoomViewHolder> {
	private List<OfflineAttach> comUpload;
	private EcDistributionRoomEntityVo pdsEntity;
	private GlobalCommonBll globalCommonBll;
	private ArrayList<String> attachFiles = null;
	private ComUploadBll comUploadBll;
	private TaskDeviceEntity deviceEntity;
	private final int DistributionRoomlLink_RequestCode = 5;// 设备关联请求码
	private String rootPath;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		globalCommonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		initAttachment();
		initDatas();
		initEvent();
	}

	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir = rootPath+ "附件/" + GlobalEntry.currentProject.getPrjNo() + "/配电室/";
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

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		if (GlobalEntry.loginResult != null) {
			viewHolder.getInSelComYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInSelComWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		}
		// 所属地市是否显示
		if (GlobalEntry.ssds) {
			viewHolder.getInSelComSsds().setVisibility(View.VISIBLE);
		}
		viewHolder.getInSelcomDydj()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIncomPbts()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIncomPbzrl()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIncomJddz()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIncomWgbcrl()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		initSelect();
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		pdsEntity = (EcDistributionRoomEntityVo) bundle.getSerializable("EcDistributionRoomEntity");
		// 从任务明细传过来的对象
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		if (deviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			pdsEntity = (EcDistributionRoomEntityVo) deviceEntity.getDevice();
			pdsEntity.setComUploadEntityList(deviceEntity.getComUploadEntityList());
		}
		if (pdsEntity != null) {
			updateData();
			initSelect();
		}
	}

	private void initSelect() {
		// 初始电压等级下拉列表
		if (viewHolder.getInSelcomDydj().getEdtTextValue().equals("")) {
			String dydjTypeNo = DicNoEntity.dydjTypeNo;// 电压等级
			InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(dydjTypeNo));
			viewHolder.getInSelcomDydj().setAdapter(dydjAdapter);
		}

		// 初始设备状态下拉列表
		if (viewHolder.getInSelcomSbzt().getEdtTextValue().equals("")) {
			String sbztTypeNo = "010402";// 设备状态
			InputSelectAdapter sbztAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(sbztTypeNo));
			viewHolder.getInSelcomSbzt().setAdapter(sbztAdapter);
		}

		// 初始是否下拉列表
		String sfTypeNo = "010599";// 是否
		List<String> list = globalCommonBll.getDictionaryNameList(sfTypeNo);
		if (viewHolder.getInSelcomSfdw().getEdtTextValue().equals("")) {
			// 初始是否代维下拉列表
			InputSelectAdapter sfdwAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInSelcomSfdw().setAdapter(sfdwAdapter);
		}
		if (viewHolder.getInSelcomSfnw().getEdtTextValue().equals("")) {
			// 初始是否农网下拉列表
			InputSelectAdapter sfnwAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInSelcomSfnw().setAdapter(sfnwAdapter);
		}
		if (viewHolder.getInSelcomSfdljzw().getEdtTextValue().equals("")) {
			// 初始是否独立建筑物下拉列表
			InputSelectAdapter sfdljzwAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInSelcomSfdljzw().setAdapter(sfdljzwAdapter);
		}
		if (viewHolder.getInSelcomSfdxz().getEdtTextValue().equals("")) {
			// 初始是否地下站下拉列表
			InputSelectAdapter sfdxzAdapter = new InputSelectAdapter(mActivity, list);
			viewHolder.getInSelcomSfdxz().setAdapter(sfdxzAdapter);
		}
		// 初始放误方式下拉列表
		if (viewHolder.getInSelComFwfs().getEdtTextValue().equals("")) {
			String fwfsTypeNo = "010604086";// 防误方式
			InputSelectAdapter fwfsAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(fwfsTypeNo));
			viewHolder.getInSelComFwfs().setAdapter(fwfsAdapter);
		}
		// 初始地区特征下拉列表
		if (viewHolder.getInSelcomDqtz().getEdtTextValue().equals("")) {
			String dqtzTypeNo = "0199001";// 地区特征
			InputSelectAdapter dqtzAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(dqtzTypeNo));
			viewHolder.getInSelcomDqtz().setAdapter(dqtzAdapter);
		}
		// 初始重要程度下拉列表
		if (viewHolder.getInSelcomZycd().getEdtTextValue().equals("")) {
			String zycdTypeNo = "0199003";// 重要程度
			InputSelectAdapter zycdAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(zycdTypeNo));
			viewHolder.getInSelcomZycd().setAdapter(zycdAdapter);
		}
		// 初始资产性质下拉列表
		if (viewHolder.getInSelcomZcxz().getEdtTextValue().equals("")) {
			String zcxzTypeNoGw = "010403";// 资产性质国网
			String zcxzTypeNoNw = "a010404";//资产性质南网
			if(GlobalEntry.zcxz){
				InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
						globalCommonBll.getDictionaryNameList(zcxzTypeNoNw));
				viewHolder.getInSelcomZcxz().setAdapter(zcxzAdapter);
			}else {
				InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
						globalCommonBll.getDictionaryNameList(zcxzTypeNoGw));
				viewHolder.getInSelcomZcxz().setAdapter(zcxzAdapter);
			}
		}
	}

	private void updateData() {
		String tyrq = DateUtils.date2Str(pdsEntity.getTyrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getIncomTyrq().setEdtText(tyrq);
		viewHolder.getIncomSbmc().setEdtText(pdsEntity.getSbmc());
		viewHolder.getIncomYxbh().setEdtText(pdsEntity.getYxbh());
		viewHolder.getIncomDxmpid().setEdtText(pdsEntity.getDxmpid());
		if (GlobalEntry.ssds) {
			viewHolder.getInSelComSsds().setEdtText(pdsEntity.getSsds());
		}
		viewHolder.getInSelComYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
		viewHolder.getInSelComWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		viewHolder.getIncomPbts().setEdtText(StringUtils.nullStrToEmpty(pdsEntity.getPbts()));
		viewHolder.getIncomPbzrl().setEdtText(pdsEntity.getPbzrl());
		viewHolder.getIncomWgbcrl().setEdtText(pdsEntity.getWgbcrl());
		viewHolder.getInSelComFwfs().setEdtTextValue(pdsEntity.getFwfs());
		viewHolder.getIncomJddz().setEdtText(StringUtils.nullStrToEmpty(pdsEntity.getJddz()));
		viewHolder.getIncomZz().setEdtText(pdsEntity.getZz());
		viewHolder.getIncomGcbh().setEdtText(pdsEntity.getGcbh());
		viewHolder.getIncomGcmc().setEdtText(pdsEntity.getGcmc());
		viewHolder.getIncomZcdw().setEdtText(pdsEntity.getZcdw());
		viewHolder.getIncomBz().setEdtText(pdsEntity.getBz());
		viewHolder.getInSelcomDydj().setEdtTextValue(pdsEntity.getDydj());
		viewHolder.getInSelcomSbzt().setEdtTextValue(pdsEntity.getSbzt());
		viewHolder.getInSelcomSfnw().setEdtTextValue(pdsEntity.getSfnw());
		viewHolder.getInSelcomSfdw().setEdtTextValue(pdsEntity.getSfdw());
		viewHolder.getInSelcomSfdljzw().setEdtTextValue(pdsEntity.getSfdljzw());
		viewHolder.getInSelcomSfdxz().setEdtTextValue(pdsEntity.getSfdxz());
		viewHolder.getInSelcomDqtz().setEdtTextValue(pdsEntity.getDqtz());
		viewHolder.getInSelcomZycd().setEdtTextValue(pdsEntity.getZycd());
		viewHolder.getInSelcomZcxz().setEdtTextValue(pdsEntity.getZcxz());

		// 显示附件
		List<OfflineAttach> offlineAttachs = pdsEntity.getComUploadEntityList();
		if (offlineAttachs != null && offlineAttachs.size() > 0) {
			attachFiles = new ArrayList<String>();
			for (OfflineAttach attach : offlineAttachs) {
				attachFiles.add(attach.getFilePath());
			}
		}
		viewHolder.getAvAttachment().addExistsAttachments(attachFiles);
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		// TODO Auto-generated method stub
		viewHolder.getInSelComYwdw().getEditTextView().setEnabled(false);
		viewHolder.getInSelComWhbz().getEditTextView().setEnabled(false);
		// 投运日期
		viewHolder.getIncomTyrq().setEditTextFocus(false);
		viewHolder.getIncomTyrq().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getIncomTyrq().getEditTextView());
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
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_head_item_return: // 返回
			{
				// 附件操作
				saveAttachment();
				Intent intent = new Intent();
				intent.putExtra("distributionRoom", pdsEntity);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: // 确定
			{
				saveData();
				if (pdsEntity == null) {
					return;
				}

				Intent intent = new Intent();
				intent.putExtra("distributionRoom", pdsEntity);
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
		intent.putExtra("LinkDeviceMark", TableNameEnum.PDF.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, DistributionRoomlLink_RequestCode);

	}

	/**
	 * 保存数据
	 */
	protected void saveData() {
		if (pdsEntity == null) {
			pdsEntity = new EcDistributionRoomEntityVo();
			pdsEntity.setObjId(UUIDGen.randomUUID());
			pdsEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			pdsEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			pdsEntity.setCjsj(DateUtils.getDate());
			Date tyrqDate = DateUtils.str2Date(viewHolder.getIncomTyrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
			if (tyrqDate == null) {
				tyrqDate = DateUtils.getDate();
			}
			pdsEntity.setTyrq(tyrqDate);
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				pdsEntity.setOperateMark(1);// 编辑下新增
			}
		}
		pdsEntity.setGxsj(DateUtils.getDate());
		pdsEntity.setSbmc(viewHolder.getIncomSbmc().getEdtTextValue().trim());
		pdsEntity.setYxbh(viewHolder.getIncomYxbh().getEdtTextValue().trim());
		pdsEntity.setDydj(viewHolder.getInSelcomDydj().getEdtTextValue().trim());
		pdsEntity.setDxmpid(viewHolder.getIncomDxmpid().getEdtTextValue().trim());
		if (GlobalEntry.ssds) {
			pdsEntity.setSsds(viewHolder.getInSelComSsds().getEdtTextValue().trim());
		}
		pdsEntity.setYwdw(GlobalEntry.loginResult.getOrgid());
		pdsEntity.setWhbz(GlobalEntry.loginResult.getDeptid());
		pdsEntity.setSbzt(viewHolder.getInSelcomSbzt().getEdtTextValue().trim());
		pdsEntity.setSfnw(viewHolder.getInSelcomSfnw().getEdtTextValue().trim());
		pdsEntity.setSfdw(viewHolder.getInSelcomSfdw().getEdtTextValue().trim());
		if (viewHolder.getIncomPbts().getEdtTextValue().isEmpty()) {
			pdsEntity.setPbts(0);
		} else {
			pdsEntity.setPbts(Integer.parseInt(viewHolder.getIncomPbts().getEdtTextValue().trim()));
		}
		pdsEntity.setPbzrl(viewHolder.getIncomPbzrl().getEdtTextValue().trim());
		pdsEntity.setWgbcrl(viewHolder.getIncomWgbcrl().getEdtTextValue().trim());
		pdsEntity.setFwfs(viewHolder.getInSelComFwfs().getEdtTextValue().trim());
		pdsEntity.setSfdljzw(viewHolder.getInSelcomSfdljzw().getEdtTextValue().trim());
		pdsEntity.setSfdxz(viewHolder.getInSelcomSfdxz().getEdtTextValue().trim());
		if (viewHolder.getIncomJddz().getEdtTextValue().isEmpty()) {
			pdsEntity.setJddz(0);
		} else {
			pdsEntity.setJddz(Integer.parseInt(viewHolder.getIncomJddz().getEdtTextValue().trim()));
		}
		pdsEntity.setZz(viewHolder.getIncomZz().getEdtTextValue());
		pdsEntity.setDqtz(viewHolder.getInSelcomDqtz().getEdtTextValue().trim());
		pdsEntity.setZycd(viewHolder.getInSelcomZycd().getEdtTextValue().trim());
		pdsEntity.setGcbh(viewHolder.getIncomGcbh().getEdtTextValue().trim());
		pdsEntity.setGcmc(viewHolder.getIncomGcmc().getEdtTextValue().trim());
		pdsEntity.setZcxz(viewHolder.getInSelcomZcxz().getEdtTextValue().trim());
		pdsEntity.setZcdw(viewHolder.getIncomZcdw().getEdtTextValue().trim());
		pdsEntity.setBz(viewHolder.getIncomBz().getEdtTextValue().trim());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAvAttachment().finish();
		// 附件操作
		saveAttachment();
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (pdsEntity == null) {
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
		offlineAttachs = pdsEntity.getComUploadEntityList();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.PDF.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(pdsEntity.getObjId());
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
				offlineAttach.setOwnerCode(TableNameEnum.PDF.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(pdsEntity.getObjId());
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
		pdsEntity.setComUploadEntityList(comUpload);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == DistributionRoomlLink_RequestCode) {// 设备关联
				EcDistributionRoomEntity entity = (EcDistributionRoomEntity) data
						.getSerializableExtra("LinkDeviceResult");
				try {
					pdsEntity = new EcDistributionRoomEntityVo();
					ClonesUtil.clones(pdsEntity, entity);
					if (pdsEntity != null) {
						pdsEntity.setOperateMark(3);// 设备关联
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
