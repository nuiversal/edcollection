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
import com.winway.android.edcollection.adding.activity.JgActivity;
import com.winway.android.edcollection.adding.activity.LableActivity;
import com.winway.android.edcollection.adding.activity.SelectDeviceActivity;
import com.winway.android.edcollection.adding.fragment.BasicInfoFragment;
import com.winway.android.edcollection.adding.viewholder.GjViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
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
import com.winway.android.util.ToastUtils;
import com.winway.android.util.UUIDGen;

/**
 * 工井
 * 
 * @author lyh
 * @data 2017年2月15日
 */
public class WorkWellControll extends BaseControll<GjViewHolder> {
	private List<OfflineAttach> comUpload;
	private EcWorkWellEntityVo ecWorkWellEntityVo;
	private ComUploadBll comUploadBll;
	private GlobalCommonBll globalCommonBll;
	private ArrayList<String> attachFiles = null;
	private TaskDeviceEntity deviceEntity;
	private final int WorkWellLink_RequestCode = 5;// 设备关联请求码
	private final int JG_RequestCode = 11; // 井盖采集请求码
	private final int BQ_RequestCode = 12; // 标签采集请求码
	private ArrayList<EcWorkWellCoverEntity> coverList; // 井盖数据集合
	private EcLineLabelEntityVo ecLineLabelEntityVo;// 标签数据
	private MarkerCollectControll markerCollectControll;
	private String rootPath;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		globalCommonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		markerCollectControll = MarkerCollectControll.getInstance();
		initAttachment();
		initDatas();
		initEvent();
		initSettings();

	}

	private void initSettings() {
		// TODO Auto-generated method stub
		viewHolder.getIncomBkhd()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIncomGbks()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIncomJmgc()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIncomNdgc()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIncomJgcc()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIncomPtcs()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInComDlldjl()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInComDlljdjl()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInComDllzcjl()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInComDllycjl()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInComGjcd()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInComGjkd()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInComGjsd()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInComJxygjjl()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
	}

	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/工井/";
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
		// if (BasicInfoControll.BMaplocationDistrict != null &&
		// BasicInfoControll.BMaplocationStreet !=
		// null&&BasicInfoControll.BMaplocationCity!=null) {
		// viewHolder.getIncomSbmc().setEdtText(
		// BasicInfoControll.BMaplocationDistrict + "-" +
		// BasicInfoControll.BMaplocationStreet + "-");
		// viewHolder.getInscSsds().setEdtText(BasicInfoControll.BMaplocationCity);
		// viewHolder.getIncomSszrq().setEdtText(BasicInfoControll.BMaplocationDistrict);
		// BasicInfoControll.mLocationClient.stop();
		// } else {
		// ToastUtil.show(mActivity, "定位失败,按照规范填写设备名称", 200);
		// }
		if (GlobalEntry.loginResult != null) {
			viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		}
		// 所属地市是否显示
		// if (GlobalEntry.ssds) {
		// viewHolder.getInscSsds().setVisibility(View.VISIBLE);
		// }
		initSelect();
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		ecWorkWellEntityVo = (EcWorkWellEntityVo) bundle.getSerializable("EcWorkWellEntity");
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		// 初始设备名称为GJ+标识器ID号
		BasicInfoFragment basicInfoFragment = markerCollectControll.getBasicInfoFragment();
		viewHolder.getIncomSbmc()
				.setEdtText("GJ" + basicInfoFragment.getAction().getViewHolder().getIcMarkerId().getEdtTextValue());
		if (deviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			ecWorkWellEntityVo = (EcWorkWellEntityVo) deviceEntity.getDevice();
			ecWorkWellEntityVo.setComUploadEntityList(deviceEntity.getComUploadEntityList());
		}
		// 取路径点的位置
		viewHolder.getIncomDlwz()
				.setEdtText(bundle.getString("BMapLocationAddr") != null ? bundle.getString("BMapLocationAddr") : "");

		// 读取rtk高程
		if (GlobalEntry.rtkLocationInfo != null && GlobalEntry.rtkLocationInfo.getAltitude() != null) {
			viewHolder.getIncomJmgc().setEdtText(GlobalEntry.rtkLocationInfo.getAltitude() + "");
		}

		if (ecWorkWellEntityVo != null) {
			updateData();
			initSelect();
		}
	}

	private void initSelect() {
		// TODO Auto-generated method stub
		// 初始地区特征下拉列表
		if(viewHolder.getInscDqtz().getEdtTextValue().equals("")){
			String sqtzTypeNo = "0199001";// 地区特征
			InputSelectAdapter sqtzAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(sqtzTypeNo));
			viewHolder.getInscDqtz().setAdapter(sqtzAdapter);
		}
		// 初始工井形状下拉列表
		if(viewHolder.getInscGjxz().getEdtTextValue().equals("")){
			String gjxzTypeNo = "010606008";// 工井形状
			InputSelectAdapter gjxzAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(gjxzTypeNo));
			viewHolder.getInscGjxz().setAdapter(gjxzAdapter);
		}
		// 初始井类型下拉列表
		if(viewHolder.getInscJlx().getEdtTextValue().equals("")){
			String jlxTypeNo = "010606005";// 井类型
			InputSelectAdapter jlxAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(jlxTypeNo));
			viewHolder.getInscJlx().setAdapter(jlxAdapter);
		}
		// 初始井结构下拉列表
		if(viewHolder.getInscJg().getEdtTextValue().equals("")){
			String jgTypeNo = "010606007";// 井结构
			InputSelectAdapter jgAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(jgTypeNo));
			viewHolder.getInscJg().setAdapter(jgAdapter);
		}
		// 初始 井盖形状下拉列表
		if(viewHolder.getInscJgxz().getEdtTextValue().equals("")){
			String jgxzTypeNo = "010606008";// 井盖形状
			InputSelectAdapter jgxzAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(jgxzTypeNo));
			viewHolder.getInscJgxz().setAdapter(jgxzAdapter);
		}
		// 初始 井盖材质下拉列表
		if(viewHolder.getInscJgcz().getEdtTextValue().equals("")){
			String jgczTypeNo = "010606006";// 井盖材质
			InputSelectAdapter jgczAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(jgczTypeNo));
			viewHolder.getInscJgcz().setAdapter(jgczAdapter);
		}
		// 初始资产性质下拉列表
		if(viewHolder.getInscZcxz().getEdtTextValue().equals("")){
			String zcxzTypeNoGw = "010403";// 资产性质国网
			String zcxzTypeNoNw = "a010404";//资产性质南网
			if(GlobalEntry.zcxz){
				List<String> dictionaryNameList = globalCommonBll.getDictionaryNameList(zcxzTypeNoNw);
				if(dictionaryNameList!=null && dictionaryNameList.size()>0){
					InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity, dictionaryNameList);
					viewHolder.getInscZcxz().setAdapter(zcxzAdapter);
				}
			}else {
				InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
						globalCommonBll.getDictionaryNameList(zcxzTypeNoGw));
				viewHolder.getInscZcxz().setAdapter(zcxzAdapter);
			}
		}
	}

	private void updateData() {
		viewHolder.getIncomDmxx().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getDmxx()));
		viewHolder.getIncomGcmc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getGcmc()));
		viewHolder.getIncomBkhd().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getGbhd()));
		viewHolder.getIncomGbks().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getGbks()));
		// if (GlobalEntry.ssds) {
		viewHolder.getInscSsds().setEdtText(ecWorkWellEntityVo.getSsds());
		// }
		viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
		viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		viewHolder.getIncomSbmc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getSbmc()));
		viewHolder.getIncomSszrq().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getSszrq()));
		viewHolder.getInscGjxz().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getGjxz()));
		viewHolder.getInscDqtz().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getDqtz()));
		viewHolder.getIncomDlwz().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getDlwz()));
		viewHolder.getIncomJwz().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJwz()));
		viewHolder.getInscJlx().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJlx()));
		viewHolder.getInscJg().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJg()));
		viewHolder.getIncomJmgc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJmgc()));
		viewHolder.getIncomNdgc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getNdgc()));
		viewHolder.getInscJgxz().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJgxz()));
		viewHolder.getIncomJgcc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJgcc()));
		viewHolder.getInscJgcz().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJgcz()));
		viewHolder.getIncomJgsccj().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJgsccj()));
		viewHolder.getIncomPtcs().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getPtcs()));
		viewHolder.getIncomSgdw().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getSgdw()));
		viewHolder.getIncomTzbh().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getTzbh()));
		viewHolder.getInscZcxz().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getZcxz()));
		viewHolder.getIncomZcdw().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getZcdw()));
		viewHolder.getIncomZcbh().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getZcbh()));
		viewHolder.getIncomZyfl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getZyfl()));
		viewHolder.getIncomBz().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getBz()));
		String jgcc = DateUtils.date2Str(ecWorkWellEntityVo.getJgccrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getIncomJgccrq().setEdtText(jgcc);
		String sgrq = DateUtils.date2Str(ecWorkWellEntityVo.getSgrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getIncomSgrq().setEdtText(sgrq);
		String jgrq = DateUtils.date2Str(ecWorkWellEntityVo.getJgrq(), DateUtils.date_sdf_wz_hm);
		viewHolder.getIncomJgrq().setEdtText(jgrq);
		viewHolder.getInComDlldjl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getDlldjl()));
		viewHolder.getInComDlljdjl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getDlljdjl()));
		viewHolder.getInComDllzcjl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getDllzcjl()));
		viewHolder.getInComDllycjl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getDllycjl()));
		viewHolder.getInComGjcd().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getGjcd()));
		viewHolder.getInComGjkd().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getGjkd()));
		viewHolder.getInComGjsd().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getGjsd()));
		viewHolder.getInComJkjgbh().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJkjgbh()));
		viewHolder.getInComXygjfx().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getXygjfx()));
		viewHolder.getInComJxygjjl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJxygjjl()));
		viewHolder.getInComFssbqk().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getFsssqk()));
		viewHolder.getInComTddmc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getTddmc()));
		;
		viewHolder.getInComJqdwz().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntityVo.getJqdwz()));
		// 显示附件
		List<OfflineAttach> offlineAttachs = ecWorkWellEntityVo.getComUploadEntityList();
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
		viewHolder.getInscYwdw().getEditTextView().setEnabled(false);
		viewHolder.getInscWhbz().getEditTextView().setEnabled(false);
		// 井盖出厂日期
		viewHolder.getIncomJgccrq().setEditTextFocus(false);
		viewHolder.getIncomJgccrq().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getIncomJgccrq().getEditTextView());
			}
		});

		// 施工日期
		viewHolder.getIncomSgrq().setEditTextFocus(false);
		viewHolder.getIncomSgrq().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getIncomSgrq().getEditTextView());
			}
		});

		// 峻工日期
		viewHolder.getIncomJgrq().setEditTextFocus(false);
		viewHolder.getIncomJgrq().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getIncomJgrq().getEditTextView());
			}
		});
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getIvLinkDeviceEnter().setOnClickListener(ocl);
		viewHolder.getBtnLinkDeviceLink().setOnClickListener(ocl);
		viewHolder.getBtnJgcj().setOnClickListener(ocl);
		viewHolder.getBtnBqcj().setOnClickListener(ocl);
		viewHolder.getBtnGjTs().setOnClickListener(ocl);
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
				intent.putExtra("gj", ecWorkWellEntityVo);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: // 确定
			{
				/*
				 * if (viewHolder.getIncomSbmc().getEdtTextValue().isEmpty()) {
				 * ToastUtil.show(mActivity, "设备名称不能为空!", 200); //获取焦点
				 * viewHolder.getIncomSbmc().getEditTextView().requestFocus();
				 * return; } if
				 * (viewHolder.getInscSsds().getEdtTextValue().isEmpty()) {
				 * ToastUtil.show(mActivity, "所属地市不能为空!", 200); //获取焦点
				 * viewHolder.getInscSsds().getEditTextView().requestFocus();
				 * return; } if
				 * (viewHolder.getIncomSszrq().getEdtTextValue().isEmpty()) {
				 * ToastUtil.show(mActivity, "所属责任区不能为空!", 200); //获取焦点
				 * viewHolder.getIncomSszrq().getEditTextView().requestFocus();
				 * return; }
				 */
				saveData();
				if (ecWorkWellEntityVo == null) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("gj", ecWorkWellEntityVo);
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
			case R.id.btn_gj_jgcj: {// 井盖操作
				Intent intent = new Intent(mActivity, JgActivity.class);
				if (ecWorkWellEntityVo != null) { // 编辑状态下从工井里拿到井盖数据
					ArrayList<EcWorkWellCoverEntity> jgEntityList = ecWorkWellEntityVo.getJgEntityList();
					if (jgEntityList != null && !jgEntityList.isEmpty()) {
						intent.putExtra("coverList", jgEntityList);
					}
				} else { // 井盖未保存到数据库的话直接把从井盖操作界面传过来的数据再传回去
					intent.putExtra("coverList", coverList);
				}
				AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, JG_RequestCode);
				break;
			}
			case R.id.btn_gj_bqcj: {// 标签采集
				if (viewHolder.getIncomSbmc().getEdtTextValue().equals("")) {
					ToastUtil.show(mActivity, "请先输入设备名称！", 200);
					break;
				}
				Intent intent = new Intent(mActivity, LableActivity.class);
				int workWellLable = 11;
				if (ecWorkWellEntityVo != null) {
					EcLineLabelEntityVo ecLineLabelEntityVo = ecWorkWellEntityVo.getEcLineLabelEntityVo();
					if (ecLineLabelEntityVo != null) {
						intent.putExtra("LableEntity", ecLineLabelEntityVo);
						intent.putExtra("deviceLable", workWellLable);
					}
				} else {
					intent.putExtra("LableEntity", ecLineLabelEntityVo);
					intent.putExtra("deviceLable", workWellLable);
				}
				intent.putExtra("devName", viewHolder.getIncomSbmc().getEdtTextValue().trim());
				intent.putExtra("devType", ResourceEnum.DLJ.getName());
				AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, BQ_RequestCode);
				break;
			}
			case R.id.btn_gj_ts:{
				EcWorkWellEntity workWellEntity = comUploadBll.queryTheLastWellData();
				setData(workWellEntity);
				break;
			}
			default:
				break;
			}
		}
	};

	private void setData(EcWorkWellEntity ecWorkWellEntity) {
		viewHolder.getIncomSbmc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getSbmc()));//设备名称
		viewHolder.getInscSsds().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getSsds()));//所属地市
		viewHolder.getIncomSszrq().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getSszrq()));//所属责任区
		viewHolder.getInComJkjgbh().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getJkjgbh()));//监控井盖编号
		viewHolder.getIncomDmxx().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getDmxx()));//断面信息
		viewHolder.getIncomGcmc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getGcmc()));//工程名称
		viewHolder.getIncomBkhd().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getGbhd()));//盖板厚度
		viewHolder.getIncomGbks().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getGbks()));//盖板块数
		viewHolder.getInComFssbqk().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getFsssqk()));//附属设施情况
		viewHolder.getInComTddmc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getTddmc()));//通道段名称
		viewHolder.getInComJqdwz().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getJqdwz()));//距起点位置
		viewHolder.getInComDlldjl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getDlldjl()));//电缆离地距离
		viewHolder.getInComDlljdjl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getDlljdjl()));//电缆离井底距离
		viewHolder.getInComDllzcjl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getDllzcjl()));//电缆离左侧距离
		viewHolder.getInComDllycjl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getDllzcjl()));//电缆离右侧距离
		viewHolder.getInComGjcd().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getGjcd()));//工井长度
		viewHolder.getInComGjsd().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getGjsd()));//工井深度
		viewHolder.getInscGjxz().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntity.getGjxz()));//工井形状
		viewHolder.getInscDqtz().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntity.getDqtz()));//地区特征
		viewHolder.getIncomDlwz().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getDlwz()));//地理位置
		viewHolder.getIncomJwz().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getJwz()));//井位置
		viewHolder.getInscJlx().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntity.getJlx()));//井类型
		viewHolder.getInscJg().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntity.getJg()));//结构
		viewHolder.getIncomJmgc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getJmgc()));//井面高程
		viewHolder.getIncomJgcc().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getJgcc()));//井盖尺寸
		viewHolder.getInscJgcz().setEdtTextValue(StringUtils.nullStrToEmpty(ecWorkWellEntity.getJgcz()));//井盖材质
		viewHolder.getIncomJgsccj().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getJgsccj()));//井盖生产产家
		viewHolder.getIncomPtcs().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getPtcs()));//平台层数
		viewHolder.getIncomSgdw().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getSgdw()));//施工单位
		viewHolder.getIncomSgrq().setEdtText(StringUtils.nullStrToEmpty(DateUtils.date2Str(ecWorkWellEntity.getSgrq(),DateUtils.date_sdf_wz_hm)));//施工日期
		viewHolder.getIncomJgrq().setEdtText(StringUtils.nullStrToEmpty(DateUtils.date2Str(ecWorkWellEntity.getJgrq(), DateUtils.date_sdf_wz_hm)));//竣工日期
		viewHolder.getIncomTzbh().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getTzbh()));//图纸编号
		viewHolder.getIncomZcdw().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getZcdw()));//资产单位
		viewHolder.getIncomZcbh().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getZcbh()));//资产编号
		viewHolder.getIncomZyfl().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getZyfl()));//专业分类
		viewHolder.getIncomBz().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellEntity.getBz()));
	}
	
	/**
	 * 关联设备
	 */
	protected void linkDevice() {
		Intent intent = new Intent();
		intent.setClass(mActivity, SelectDeviceActivity.class);
		intent.putExtra("LinkDeviceMark", TableNameEnum.GJ.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, WorkWellLink_RequestCode);
	}

	/**
	 * 保存数据
	 */
	protected void saveData() {
		if (ecWorkWellEntityVo == null) {
			ecWorkWellEntityVo = new EcWorkWellEntityVo();
			ecWorkWellEntityVo.setObjId(UUIDGen.randomUUID());
			ecWorkWellEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecWorkWellEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecWorkWellEntityVo.setCjsj(DateUtils.getDate());
			Date jgccDate = DateUtils.str2Date(viewHolder.getIncomJgccrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
			if (jgccDate == null) {
				jgccDate = DateUtils.getDate();
			}
			ecWorkWellEntityVo.setJgccrq(jgccDate);

			Date sgrqDate = DateUtils.str2Date(viewHolder.getIncomSgrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
			if (sgrqDate == null) {
				sgrqDate = DateUtils.getDate();
			}
			ecWorkWellEntityVo.setSgrq(sgrqDate);
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecWorkWellEntityVo.setOperateMark(1);
			}
		}
		Date jgrqDate = DateUtils.str2Date(viewHolder.getIncomJgrq().getEdtTextValue(), DateUtils.date_sdf_wz_hm);
		if (jgrqDate == null) {
			jgrqDate = DateUtils.getDate();
		}
		ecWorkWellEntityVo.setJgrq(jgrqDate);
		ecWorkWellEntityVo.setGxsj(DateUtils.getDate());
		ecWorkWellEntityVo.setDmxx(viewHolder.getIncomDmxx().getEdtTextValue().trim());
		ecWorkWellEntityVo.setGcmc(viewHolder.getIncomGcmc().getEdtTextValue().trim());
		if (viewHolder.getIncomBkhd().getEdtTextValue().isEmpty()) {
			ecWorkWellEntityVo.setGbhd(0.0);
		} else {
			ecWorkWellEntityVo.setGbhd(Double.parseDouble(viewHolder.getIncomBkhd().getEdtTextValue().trim()));
		}
		if (viewHolder.getIncomGbks().getEdtTextValue().isEmpty()) {
			ecWorkWellEntityVo.setGbks(0);
		} else {
			ecWorkWellEntityVo.setGbks(Integer.parseInt(viewHolder.getIncomGbks().getEdtTextValue().trim()));
		}
		// if (GlobalEntry.ssds) {
		ecWorkWellEntityVo.setSsds(viewHolder.getInscSsds().getEdtTextValue().trim());
		// }
		ecWorkWellEntityVo.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecWorkWellEntityVo.setWhbz(GlobalEntry.loginResult.getDeptid());
		ecWorkWellEntityVo.setSbmc(viewHolder.getIncomSbmc().getEdtTextValue().trim());
		ecWorkWellEntityVo.setSszrq(viewHolder.getIncomSszrq().getEdtTextValue().trim());
		ecWorkWellEntityVo.setGjxz(viewHolder.getInscGjxz().getEdtTextValue().trim());
		ecWorkWellEntityVo.setDqtz(viewHolder.getInscDqtz().getEdtTextValue().trim());
		ecWorkWellEntityVo.setDlwz(viewHolder.getIncomDlwz().getEdtTextValue().trim());
		ecWorkWellEntityVo.setJwz(viewHolder.getIncomJwz().getEdtTextValue().trim());
		ecWorkWellEntityVo.setJlx(viewHolder.getInscJlx().getEdtTextValue().trim());
		ecWorkWellEntityVo.setJg(viewHolder.getInscJg().getEdtTextValue().trim());
		if (viewHolder.getIncomJmgc().getEdtTextValue().isEmpty()) {
			ecWorkWellEntityVo.setJmgc(0.0);
		} else {
			ecWorkWellEntityVo.setJmgc(
					Double.parseDouble(StringUtils.nullStrToEmpty(viewHolder.getIncomJmgc().getEdtTextValue())));
		}
		if (viewHolder.getIncomNdgc().getEdtTextValue().isEmpty()) {
			ecWorkWellEntityVo.setNdgc(0.0);
		} else {
			ecWorkWellEntityVo.setNdgc(Double.parseDouble(viewHolder.getIncomNdgc().getEdtTextValue().trim()));
		}
		ecWorkWellEntityVo.setJgxz(viewHolder.getInscJgxz().getEdtTextValue().trim());
		if (viewHolder.getIncomJgcc().getEdtTextValue().isEmpty()) {
			ecWorkWellEntityVo.setJgcc(0.0);
		} else {
			ecWorkWellEntityVo.setJgcc(Double.parseDouble(viewHolder.getIncomJgcc().getEdtTextValue().trim()));
		}
		ecWorkWellEntityVo.setJgcz(viewHolder.getInscJgcz().getEdtTextValue().trim());
		ecWorkWellEntityVo.setJgsccj(viewHolder.getIncomJgsccj().getEdtTextValue().trim());
		ecWorkWellEntityVo.setPtcs(viewHolder.getIncomPtcs().getEdtTextValue().trim());
		ecWorkWellEntityVo.setSgdw(viewHolder.getIncomSgdw().getEdtTextValue().trim());
		ecWorkWellEntityVo.setTzbh(viewHolder.getIncomTzbh().getEdtTextValue().trim());
		ecWorkWellEntityVo.setZcxz(viewHolder.getInscZcxz().getEdtTextValue().trim());
		ecWorkWellEntityVo.setZcdw(viewHolder.getIncomZcdw().getEdtTextValue().trim());
		ecWorkWellEntityVo.setZcbh(viewHolder.getIncomZcbh().getEdtTextValue().trim());
		ecWorkWellEntityVo.setZyfl(viewHolder.getIncomZyfl().getEdtTextValue().trim());
		ecWorkWellEntityVo.setBz(viewHolder.getIncomBz().getEdtTextValue());
		Double dlldjl = (viewHolder.getInComDlldjl().getEdtTextValue().isEmpty() ? null
				: Double.parseDouble(viewHolder.getInComDlldjl().getEdtTextValue()));
		Double dlljdjl = (viewHolder.getInComDlljdjl().getEdtTextValue().isEmpty() ? null
				: Double.parseDouble(viewHolder.getInComDlljdjl().getEdtTextValue()));
		Double dllzcjl = (viewHolder.getInComDllzcjl().getEdtTextValue().isEmpty() ? null
				: Double.parseDouble(viewHolder.getInComDllzcjl().getEdtTextValue()));
		Double dllycjl = (viewHolder.getInComDllycjl().getEdtTextValue().isEmpty() ? null
				: Double.parseDouble(viewHolder.getInComDllycjl().getEdtTextValue()));
		Double gjcd = (viewHolder.getInComGjcd().getEdtTextValue().isEmpty() ? null
				: Double.parseDouble(viewHolder.getInComGjcd().getEdtTextValue()));
		Double gjkd = (viewHolder.getInComGjkd().getEdtTextValue().isEmpty() ? null
				: Double.parseDouble(viewHolder.getInComGjkd().getEdtTextValue()));
		Double gjsd = (viewHolder.getInComGjsd().getEdtTextValue().isEmpty() ? null
				: Double.parseDouble(viewHolder.getInComGjsd().getEdtTextValue()));
		ecWorkWellEntityVo.setDlldjl(dlldjl);
		ecWorkWellEntityVo.setDlljdjl(dlljdjl);
		ecWorkWellEntityVo.setDllzcjl(dllzcjl);
		ecWorkWellEntityVo.setDllycjl(dllycjl);
		ecWorkWellEntityVo.setGjcd(gjcd);
		ecWorkWellEntityVo.setGjkd(gjkd);
		ecWorkWellEntityVo.setGjsd(gjsd);
		ecWorkWellEntityVo.setJkjgbh(viewHolder.getInComJkjgbh().getEdtTextValue());
		ecWorkWellEntityVo.setXygjfx(viewHolder.getInComXygjfx().getEdtTextValue());
		Double jxygjjl = (viewHolder.getInComJxygjjl().getEdtTextValue().isEmpty() ? null
				: Double.parseDouble(viewHolder.getInComJxygjjl().getEdtTextValue()));
		ecWorkWellEntityVo.setJxygjjl(jxygjjl);
		ecWorkWellEntityVo.setFsssqk(viewHolder.getInComFssbqk().getEdtTextValue());
		ecWorkWellEntityVo.setTddmc(viewHolder.getInComTddmc().getEdtTextValue());
		ecWorkWellEntityVo.setJqdwz(viewHolder.getInComJqdwz().getEdtTextValue());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAvAttachment().finish();
		// 附件操作
		saveAttachment();
		// 保存井盖采集的数据
		if (coverList != null && !coverList.isEmpty()) {
			for (EcWorkWellCoverEntity cover : coverList) {
				cover.setSsgj(ecWorkWellEntityVo.getObjId());
			}
			ecWorkWellEntityVo.setJgEntityList(coverList);
		}
		// 保存标签采集的数据
		if (ecLineLabelEntityVo != null) {
			ecLineLabelEntityVo.setDeviceType(ResourceEnum.DLJ.getValue() + "");
			ecLineLabelEntityVo.setDevObjId(ecWorkWellEntityVo.getObjId());
			ecWorkWellEntityVo.setEcLineLabelEntityVo(ecLineLabelEntityVo);
		}
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (ecWorkWellEntityVo == null) {
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
		offlineAttachs = ecWorkWellEntityVo.getComUploadEntityList();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.GJ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecWorkWellEntityVo.getObjId());
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
				offlineAttach.setOwnerCode(TableNameEnum.GJ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecWorkWellEntityVo.getObjId());
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
		ecWorkWellEntityVo.setComUploadEntityList(comUpload);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == WorkWellLink_RequestCode) {// 设备关联
				EcWorkWellEntity entity = (EcWorkWellEntity) data.getSerializableExtra("LinkDeviceResult");
				try {
					ecWorkWellEntityVo = new EcWorkWellEntityVo();
					ClonesUtil.clones(ecWorkWellEntityVo, entity);
					if (ecWorkWellEntityVo != null) {
						ecWorkWellEntityVo.setOperateMark(3);// 设备关联
						updateData();
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (requestCode == JG_RequestCode) { // 井盖采集
				coverList = (ArrayList<EcWorkWellCoverEntity>) data.getSerializableExtra("coverList");
				if (ecWorkWellEntityVo != null) {
					if (coverList != null && !coverList.isEmpty()) {
						ecWorkWellEntityVo.setJgEntityList(coverList);
					}
				}
			} else if (requestCode == BQ_RequestCode) { // 标签采集
				ecLineLabelEntityVo = (EcLineLabelEntityVo) data.getSerializableExtra("lable");
				if (ecWorkWellEntityVo != null) {
					if (ecLineLabelEntityVo != null) {
						ecWorkWellEntityVo.setEcLineLabelEntityVo(ecLineLabelEntityVo);
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
