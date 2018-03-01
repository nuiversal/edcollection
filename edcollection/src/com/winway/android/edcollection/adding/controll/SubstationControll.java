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
import android.view.View.OnFocusChangeListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.SelectDeviceActivity;
import com.winway.android.edcollection.adding.bll.SubstationBll;
import com.winway.android.edcollection.adding.dto.SubstationData;
import com.winway.android.edcollection.adding.entity.RightPro;
import com.winway.android.edcollection.adding.util.FragmentEntry;
import com.winway.android.edcollection.adding.viewholder.SubstationViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.DicNoEntity;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.task.entity.TaskDeviceEntity;
import com.winway.android.ewidgets.attachment.AttachmentAttrs;
import com.winway.android.ewidgets.attachment.AttachmentInit;
import com.winway.android.ewidgets.attachment.AttachmentUtil;
import com.winway.android.ewidgets.attachment.AttachmentUtil.AddDeleteResult;
import com.winway.android.ewidgets.attachment.AttachmentView.AttachmentResult;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.ClonesUtil;
import com.winway.android.util.DateUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.NumberUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 变电站
 * 
 * @author lyh
 * @version 创建时间：2016年12月19日 下午5:31:12
 * 
 */
public class SubstationControll extends BaseControll<SubstationViewHolder> {
	private List<OfflineAttach> comUpload;
	private SubstationBll substationBll;
	private EcSubstationEntityVo ecSubstationEntityVo = null;
	private ArrayList<String> attachFiles = null;
	private ComUploadBll comUploadBll;
	private TaskDeviceEntity deviceEntity;
	private boolean update = false;
	private final int SubstationlLink_RequestCode = 5;// 设备关联请求码
	private GlobalCommonBll globalCommonBll;
	private final int substationmap_requestcode = 6;// 地图选点请求码
	private double[] xy;
	public static boolean mapSelectCoord = false; // 是否是地图选点跳转
	public static boolean isNewSubstation = false; // 判断是否是单独创建变电站
	public static final int mapSelectPoint = 001;
	private String rootPath;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		String prjDBUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		substationBll = new SubstationBll(mActivity, prjDBUrl);
		comUploadBll = new ComUploadBll(mActivity, prjDBUrl);
		globalCommonBll = new GlobalCommonBll(mActivity, GlobalEntry.globalDbUrl);
		initAttachment();
		if (isNewSubstation) { // 单独创建变电站的初始化
			initNewSubstationDatas();
		} else {
			initDatas(); // 任务明细创建变电站的初始化
		}
		initEvent();
	}

	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		String fileDir =rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/变电站/";
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
	 * 单独创建变电站的初始化数据
	 */
	private void initNewSubstationDatas() {
		// TODO Auto-generated method stub
		// 初始化产权属性下拉
		InputSelectAdapter adapter = new InputSelectAdapter(mActivity, substationBll.getRightProNameList());
		viewHolder.getIscRigtPro().setAdapter(adapter);
		// 初始电压等级下拉列表 dydj
		String dydjTypeNo = "dydj";// 电压等级
		InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
				globalCommonBll.getDictionaryNameList(dydjTypeNo));
		viewHolder.getIscVoltage().setAdapter(dydjAdapter);

		// 控制输入类型
		viewHolder.getIscVoltage()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIscRigtPro()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

		// viewHolder.getIcSubtationLongitude().setEditTextFocus(false);
		// viewHolder.getIcSubtationLatitude().setEditTextFocus(false);
		if (mapSelectCoord) {// 地图选点跳转过来
			Intent intent = mActivity.getIntent();
			String cancle = intent.getStringExtra("cancel");
			if (cancle != null) {// 取消按钮
				viewHolder.getRlLinkDevice().setVisibility(View.GONE);
				xy = intent.getDoubleArrayExtra("coord");
				if (xy != null) {
					viewHolder.getIcSubtationLongitude().setEdtText(NumberUtils.format(xy[0], 7));
					viewHolder.getIcSubtationLatitude().setEdtText(NumberUtils.format(xy[1], 7));
					Bundle bundle = intent.getExtras();
					ecSubstationEntityVo = (EcSubstationEntityVo) bundle.getSerializable("EcSubstationEntity");
					// 填写编辑状态设置好的数据
					if (SubstationData.ecSubstationEntityVo != null) {
						setSubstationData(SubstationData.ecSubstationEntityVo, viewHolder.getIcName(),
								viewHolder.getIcSubNo(), viewHolder.getIscVoltage(), viewHolder.getIscRigtPro());
					}
				}

			} else { // 确定按钮
				xy = intent.getDoubleArrayExtra("coord");
				viewHolder.getRlLinkDevice().setVisibility(View.GONE);
				if (xy != null) {
					viewHolder.getIcSubtationLongitude().setEdtText(NumberUtils.format(xy[0], 7));
					viewHolder.getIcSubtationLatitude().setEdtText(NumberUtils.format(xy[1], 7));
					Bundle bundle = intent.getExtras();
					ecSubstationEntityVo = (EcSubstationEntityVo) bundle.getSerializable("EcSubstationEntity");
					// 填写编辑状态设置好的数据
					if (SubstationData.ecSubstationEntityVo != null) {
						setSubstationData(SubstationData.ecSubstationEntityVo, viewHolder.getIcName(),
								viewHolder.getIcSubNo(), viewHolder.getIscVoltage(), viewHolder.getIscRigtPro());
					}
				}
			}
			if (!FragmentEntry.getInstance().stationAttach.isEmpty()) {
				viewHolder.getAvAttachment().addExistsAttachments(FragmentEntry.getInstance().stationAttach);
			}
		} else {
			Intent intent = mActivity.getIntent();
			// 初始化经纬度
			xy = intent.getDoubleArrayExtra("coord");
			String tag = intent.getStringExtra("tag");
			if (tag != null) {
				viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			}
			if (xy != null) {
				viewHolder.getIcSubtationLongitude().setEdtText(NumberUtils.format(xy[0], 7));
				viewHolder.getIcSubtationLatitude().setEdtText(NumberUtils.format(xy[1], 7));
			}
			Bundle bundle = intent.getExtras();
			ecSubstationEntityVo = (EcSubstationEntityVo) bundle.getSerializable("EcSubstationEntity");
			/*
			 * // 从任务明细表跳过来的 deviceEntity = (TaskDeviceEntity)
			 * bundle.getSerializable(TaskDeviceEntity.INTENT_KEY); if (deviceEntity !=
			 * null) { viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			 * ecSubstationEntityVo = (EcSubstationEntityVo) deviceEntity.getDevice();
			 * ecSubstationEntityVo.setComUploadEntityList(deviceEntity.
			 * getComUploadEntityList()); }
			 */
		}

		// 如果不为空则说明修改
		if (ecSubstationEntityVo != null) {
			updateData();
			initSelect();
			update = true;
		}
	}

	private void initSelect() {
		// TODO Auto-generated method stub
		// 初始化产权属性下拉
		if (viewHolder.getIscRigtPro().getEdtTextValue().equals("")) {
			InputSelectAdapter adapter = new InputSelectAdapter(mActivity, substationBll.getRightProNameList());
			viewHolder.getIscRigtPro().setAdapter(adapter);
		}
		// 初始电压等级下拉列表 dydj
		if (viewHolder.getIscVoltage().getEdtTextValue().equals("")) {
			String dydjTypeNo = "dydj";// 电压等级
			InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
					globalCommonBll.getDictionaryNameList(dydjTypeNo));
			viewHolder.getIscVoltage().setAdapter(dydjAdapter);
		}
	}

	private void initDatas() {
		// TODO Auto-generated method stub
		// 初始化产权属性下拉
		InputSelectAdapter adapter = new InputSelectAdapter(mActivity, substationBll.getRightProNameList());
		viewHolder.getIscRigtPro().setAdapter(adapter);
		// 初始电压等级下拉列表
		String dydjTypeNo = DicNoEntity.dydjTypeNo;// 电压等级
		InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
				globalCommonBll.getDictionaryNameList(dydjTypeNo));
		viewHolder.getIscVoltage().setAdapter(dydjAdapter);

		// 控制输入类型
		viewHolder.getIscVoltage()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIscRigtPro()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

		// 如果不是单独创建变电站 隐藏坐标布局
		viewHolder.getIcSubtationLongitude().setVisibility(View.GONE);
		viewHolder.getIcSubtationLatitude().setVisibility(View.GONE);
		viewHolder.getBtn_substation_map().setVisibility(View.GONE);
		// viewHolder.getBtnCreateSubNo().setVisibility(View.GONE);
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		ecSubstationEntityVo = (EcSubstationEntityVo) bundle.getSerializable("EcSubstationEntity");
		// 从任务明细表跳过来的
		deviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		if (deviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			ecSubstationEntityVo = (EcSubstationEntityVo) deviceEntity.getDevice();
			ecSubstationEntityVo.setComUploadEntityList(deviceEntity.getComUploadEntityList());
		}
		// 如果不为空则说明修改
		if (ecSubstationEntityVo != null) {
			updateData();
			initSelect();
			update = true;
		}
	}

	private void updateData() {
		viewHolder.getIcSubNo().getEditTextView().setEnabled(false);
		// viewHolder.getIcSubNo().setEditClean(true);
		if (ecSubstationEntityVo.getVoltage() != null) {
			viewHolder.getIscVoltage().setEdtTextValue(ecSubstationEntityVo.getVoltage());
		}
		if (ecSubstationEntityVo.getRightPro() != null) {
			if (ecSubstationEntityVo.getRightPro().equals(RightPro.GY.getValue())) {
				viewHolder.getIscRigtPro().setEdtTextValue(RightPro.GY.getName());
			} else if (ecSubstationEntityVo.getRightPro().equals(RightPro.ZY.getValue())) {
				viewHolder.getIscRigtPro().setEdtTextValue(RightPro.ZY.getName());
			}
		}

		viewHolder.getIscRigtPro().getEdtTextValue();
		viewHolder.getIcName().setEdtText(ecSubstationEntityVo.getName());
		viewHolder.getIcSubNo().setEdtText(ecSubstationEntityVo.getStationNo());
		// 显示附件
		List<OfflineAttach> offlineAttachs = ecSubstationEntityVo.getComUploadEntityList();
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
		// 标题
		viewHolder.getHcSubHead().getReturnView().setOnClickListener(ocls);
		viewHolder.getHcSubHead().getOkView().setOnClickListener(ocls);
		viewHolder.getIscRigtPro().getEditTextView().setEnabled(false);
		viewHolder.getBtn_substation_map().setOnClickListener(ocls);
		viewHolder.getIcSubNo().setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (update == false) {
					boolean isExistNo = substationBll.isExistSubstationNo(viewHolder.getIcSubNo().getEdtTextValue());
					if (isExistNo) {
						ToastUtil.show(mActivity, "已经存在该编号的变电站", 200);
						viewHolder.getIcSubNo().getEditTextView().setFocusable(true);
						viewHolder.getIcSubNo().setEdtText("");
						return;
					}
				}
			}
		});

		viewHolder.getIvLinkDeviceEnter().setOnClickListener(ocls);
		viewHolder.getBtnLinkDeviceLink().setOnClickListener(ocls);
		viewHolder.getBtnCreateSubNo().setOnClickListener(ocls);

	}

	/**
	 * 单击事件
	 */
	private OnClickListener ocls = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_head_item_return: // 返回
			{
				if (isNewSubstation) {
					isNewSubstation = false;
					mapSelectCoord = false;
				}
				saveAttachment();
				Intent intent = new Intent();
				intent.putExtra("sub", ecSubstationEntityVo);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok:// 确定
			{

				if (isNewSubstation) {
					ecSubstationEntityVo = saveData();// 单独添加变电站的确定事件
					if (ecSubstationEntityVo == null) {
						return;
					}
					double[] coordArr = new double[] {
							Double.parseDouble(viewHolder.getIcSubtationLongitude().getEdtTextValue().isEmpty() ? 0 + ""
									: viewHolder.getIcSubtationLongitude().getEdtTextValue()),
							Double.parseDouble(viewHolder.getIcSubtationLatitude().getEdtTextValue().isEmpty() ? 0 + ""
									: viewHolder.getIcSubtationLatitude().getEdtTextValue()) };
					Intent intent = new Intent();
					intent.putExtra("coord", coordArr);
					intent.putExtra("sub", ecSubstationEntityVo);
					mActivity.setResult(Activity.RESULT_OK, intent);
					mActivity.finish();
					isNewSubstation = false;
					mapSelectCoord = false;
					SubstationData.reset();
				} else {
					ecSubstationEntityVo = saveData();
					if (ecSubstationEntityVo == null) {
						return;
					}
					Intent intent = new Intent();
					intent.putExtra("sub", ecSubstationEntityVo);
					mActivity.setResult(Activity.RESULT_OK, intent);
					mActivity.finish();
				}

				break;
			}
			case R.id.btn_substation_map:// 地图选点
			{
				Intent intent = new Intent();
				intent.putExtra("coord", xy);
				String voltage = viewHolder.getIscVoltage().getEdtTextValue();
				String subNo = viewHolder.getIcSubNo().getEdtTextValue();
				String subName = viewHolder.getIcName().getEdtTextValue();
				String rigtPro = viewHolder.getIscRigtPro().getEdtTextValue();
				Integer rightProValue = substationBll.getRightProValue(rigtPro);
				EcSubstationEntityVo ecSubstationEntityVo = new EcSubstationEntityVo();
				ecSubstationEntityVo.setVoltage(voltage);
				ecSubstationEntityVo.setStationNo(subNo);
				ecSubstationEntityVo.setName(subName);
				ecSubstationEntityVo.setRightPro(rightProValue);
				obtainAttachCache();
				SubstationData.ecSubstationEntityVo = ecSubstationEntityVo;
				Bundle bundle = new Bundle();
				intent.putExtras(bundle);
				mActivity.setResult(mapSelectPoint, intent);
				mActivity.finish();
				break;
			}
			case R.id.inSelCom_sub_rightPro:// 产权属性
			{
				showToast("点击了产权属性");
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
			case R.id.btn_substation_createNo: {// 生成编号
				viewHolder.getIcSubNo().setEdtText(UUIDGen.randomUUID());
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * 生成附件缓存
	 */
	private void obtainAttachCache() {
		viewHolder.getAvAttachment().finish();
		// 查找出附件组件中存在的附件路径
		AttachmentResult result = viewHolder.getAvAttachment().getResult();
		// 查找出添加、删除、未改变的附件
		AddDeleteResult addDeleteResult = AttachmentUtil.findAddDeleteResult(result, attachFiles);
		// 添加的路径或者是重命名的附件
		ArrayList<String> addList = addDeleteResult.addList;

		if (addList != null) {
			FragmentEntry.getInstance().stationAttach.clear();
			FragmentEntry.getInstance().stationAttach.addAll(addList);
		}
	}

	/**
	 * 保存数据
	 */
	protected EcSubstationEntityVo saveData() {
		if (ecSubstationEntityVo == null) {
			ecSubstationEntityVo = new EcSubstationEntityVo();
			ecSubstationEntityVo.setEcSubstationId(UUIDGen.randomUUID());
			ecSubstationEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecSubstationEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecSubstationEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecSubstationEntityVo.setOperateMark(1);// 编辑下的新增
			}
		}
		ecSubstationEntityVo.setGxsj(DateUtils.getDate());
		if (viewHolder.getIscVoltage().getEdtTextValue().isEmpty()) {
			ToastUtil.show(mActivity, "电压等级不能为空", 200);
			viewHolder.getIscVoltage().getEditTextView().setFocusable(true);
			return null;
		}
		if (viewHolder.getIcSubNo().getEdtTextValue().isEmpty()) {
			ToastUtil.show(mActivity, "变电站编号不能为空", 200);
			viewHolder.getIcSubNo().getEditTextView().setFocusable(true);
			return null;
		}
		if (viewHolder.getIcName().getEdtTextValue().isEmpty()) {
			ToastUtil.show(mActivity, "变电站名称不能为空", 200);
			viewHolder.getIcName().getEditTextView().setFocusable(true);
			return null;
		}
		if (viewHolder.getIscRigtPro().getEdtTextValue().isEmpty()) {
			ToastUtil.show(mActivity, "产权属性不能为空", 200);
			viewHolder.getIscRigtPro().getEditTextView().setFocusable(true);
			return null;
		}
		String rigtPro = viewHolder.getIscRigtPro().getEdtTextValue();
		if (RightPro.GY.getName().equals(rigtPro)) {
			ecSubstationEntityVo.setRightPro(RightPro.GY.getValue());
		} else if (RightPro.ZY.getName().equals(rigtPro)) {
			ecSubstationEntityVo.setRightPro(RightPro.ZY.getValue());
		}
		String voltage = viewHolder.getIscVoltage().getEdtTextValue();
		ecSubstationEntityVo.setVoltage(voltage);
		ecSubstationEntityVo.setName(viewHolder.getIcName().getEdtTextValue());
		ecSubstationEntityVo.setStationNo(viewHolder.getIcSubNo().getEdtTextValue());
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAvAttachment().finish();
		// 附件操作
		saveAttachment();
		ecSubstationEntityVo.setComUploadEntityList(comUpload);
		return ecSubstationEntityVo;
	}
	/**
	 * 设置变电站数据
	 * @param substationEntityVo
	 * @param icName
	 * @param icSubNo
	 * @param iscVoltage
	 * @param iscRigtPro
	 */
	@SuppressWarnings("unused")
	private void setSubstationData(EcSubstationEntityVo substationEntityVo,InputComponent icName,InputComponent icSubNo,
			InputSelectComponent iscVoltage,InputSelectComponent iscRigtPro){
		if (substationEntityVo.getName() != null) {
			icName.getEditTextView()
					.setText(SubstationData.ecSubstationEntityVo.getName());
		}
		if (substationEntityVo.getStationNo() != null) {
			icSubNo.getEditTextView()
					.setText(SubstationData.ecSubstationEntityVo.getStationNo());
		}
		if (substationEntityVo.getVoltage() != null) {
			iscVoltage.getEditTextView()
					.setText(SubstationData.ecSubstationEntityVo.getVoltage());
		}
		if (substationEntityVo.getRightPro() != null) {
			String rightProName = substationBll
					.getRightProName(substationEntityVo.getRightPro());
			iscRigtPro.setEdtTextValue(rightProName);
		}
	}
	
	/**
	 * 关联设备
	 */
	protected void linkDevice() {
		Intent intent = new Intent();
		intent.setClass(mActivity, SelectDeviceActivity.class);
		intent.putExtra("LinkDeviceMark", TableNameEnum.BDZ.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, SubstationlLink_RequestCode);
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (ecSubstationEntityVo == null) {
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
		offlineAttachs = ecSubstationEntityVo.getComUploadEntityList();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.BDZ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecSubstationEntityVo.getEcSubstationId());
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
				offlineAttach.setOwnerCode(TableNameEnum.BDZ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(ecSubstationEntityVo.getEcSubstationId());
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
		ecSubstationEntityVo.setComUploadEntityList(comUpload);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SubstationlLink_RequestCode) {// 设备关联回调
				EcSubstationEntity entity = (EcSubstationEntity) data.getSerializableExtra("LinkDeviceResult");
				try {
					ecSubstationEntityVo = new EcSubstationEntityVo();
					ClonesUtil.clones(ecSubstationEntityVo, entity);
					if (ecSubstationEntityVo != null) {
						ecSubstationEntityVo.setOperateMark(3);// 设备关联操作
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
			viewHolder.getHcSubHead().getReturnView().performClick();
		}
		return super.onKeyDown(keyCode, event);
	}
}
