package com.winway.android.edcollection.adding.controll;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.SelectDeviceActivity;
import com.winway.android.edcollection.adding.entity.BindTargetType;
import com.winway.android.edcollection.adding.viewholder.LableViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.task.entity.TaskDeviceEntity;
import com.winway.android.ewidgets.attachment.AttachmentAttrs;
import com.winway.android.ewidgets.attachment.AttachmentInit;
import com.winway.android.ewidgets.attachment.AttachmentUtil;
import com.winway.android.ewidgets.attachment.AttachmentUtil.AddDeleteResult;
import com.winway.android.ewidgets.attachment.AttachmentView.AttachmentResult;
import com.winway.android.media.photo.photoselector.util.PhotoUtils;
import com.winway.android.sensor.label.LabelReadActivity;
import com.winway.android.sensor.nfc.bluetoothnfc.BluetoothNFC_DK_Activity;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.ClonesUtil;
import com.winway.android.util.DateUtils;
import com.winway.android.util.DensityUtil;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.FileUtil;
import com.winway.android.util.PhotoUtil;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 标签
 * 
 * @author
 *
 */
public class LableControll extends BaseControll<LableViewHolder> {
	private List<OfflineAttach> comUpload;
	private EcLineLabelEntityVo lineLabel;
	private ArrayList<String> attachFiles = null;
	private ComUploadBll comUploadBll;
	private TaskDeviceEntity taskDeviceEntity;
	private final int LableLink_RequestCode = 5;// 设备关联请求码
	private Integer bindTargetType;// 设备类型| 统一资源编号
	private int deviceLable;
	private final int workWellDeviceLable = 11;// 工井标签
	private String deviceName; // 类型名称
	private String deviceType; // 设备类型

	private final String POSITION_PHOTO_FILE_NAME = "position_";
	private final String BACKGROUND_PHOTO_FILE_NAME = "background_";
	private final int POSITION_PHOTO_REQUEST = 0x00000000012;
	private final int BACKGROUND_PHOTO_REQUEST = 0x00000000013;
	private String positionPhotoAbsolutelyRoot;
	private String backgroundPhotoAbsolutelyRoot;
	private boolean lablePositionPhoteChange = false;
	private boolean lableBackGroudPhoteChange = false;
	private OfflineAttach positionAttach;
	private OfflineAttach backgroundAttach;
	private String rootPath;
	private String fileDir;

	@Override
	public void init() {
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		initAttachment();
		initData();
		initEvent();
		initSetting();
	}

	private void initSetting() {
		// TODO Auto-generated method stub
		viewHolder.getInconSequence().setVisibility(View.GONE);
		viewHolder.getInConLableDxcd().setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
	}

	// 初始化附件
	private void initAttachment() {
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/标签";
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
		viewHolder.getInscBindTarget().getEditTextView().setEnabled(false);
		viewHolder.getInconDevName().getEditTextView().setEnabled(false);
		viewHolder.getInconDevType().getEditTextView().setEnabled(false);
		viewHolder.getInconCrossover()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInconSequence()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInconJzjgjjl()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		ArrayList<String> list = new ArrayList<String>();
		list.add(BindTargetType.dlbt.getType());
		list.add(BindTargetType.zjjt.getType());
		list.add(BindTargetType.zdsb.getType());
		InputSelectAdapter adapter = new InputSelectAdapter(mActivity, list);
		viewHolder.getInscBindTarget().setAdapter(adapter);
	}

	private void initData() {
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle == null) {
			lineLabel = (EcLineLabelEntityVo) intent.getSerializableExtra("LableEntity");
			bindTargetType = intent.getIntExtra("deviceType", -1);
			// 从任务明细传过来的对象
			taskDeviceEntity = (TaskDeviceEntity) intent.getSerializableExtra(TaskDeviceEntity.INTENT_KEY);
		} else {
			lineLabel = (EcLineLabelEntityVo) bundle.getSerializable("LableEntity");
			bindTargetType = bundle.getInt("deviceType");// 获取设备类型
			// 从任务明细传过来的对象
			taskDeviceEntity = (TaskDeviceEntity) bundle.getSerializable(TaskDeviceEntity.INTENT_KEY);
		}
		deviceLable = intent.getIntExtra("deviceLable", -1);
		deviceName = (String) intent.getSerializableExtra("devName");
		deviceType = (String) intent.getSerializableExtra("devType");
		viewHolder.getInconDevName().setEdtText(deviceName);
		viewHolder.getInconDevType().setEdtText(deviceType);
		if (taskDeviceEntity != null) {
			viewHolder.getRlLinkDevice().setVisibility(View.GONE);
			lineLabel = (EcLineLabelEntityVo) taskDeviceEntity.getDevice();
			lineLabel.setAttr(taskDeviceEntity.getComUploadEntityList());
		}
		// 如果不为空则说明是修改
		if (lineLabel != null) {
			updateData();
		}
		if (deviceLable == workWellDeviceLable) {
			viewHolder.getInscBindTarget().setEdtTextValue(ResourceEnum.DLJ.getName());
		}

	}

	private void updateData() {
		viewHolder.getIcSbmc().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getDevName()));
		viewHolder.getInconLableNo().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getLabelNo()));
		viewHolder.getInconZjgjfx().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getZjgjfx()));
		viewHolder.getInconJzjgjjl().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getJzjgjjl()));
		viewHolder.getInconFsssqk().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getFsssqk()));
		viewHolder.getInconWzdt().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getWzdt()));
		viewHolder.getInconZwtdfbqk().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getZwtdfbqk()));
		viewHolder.getInconTdndlqk().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getTdndlqk()));
		viewHolder.getInconDblkxx().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getDblkxx()));
		viewHolder.getInconSequence().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getSequence()));
		viewHolder.getInconCrossover().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getDistance()));
		viewHolder.getInconLableDxxh().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getDxxh()));
		viewHolder.getInConLableDxcd().setEdtText(StringUtils.nullStrToEmpty(lineLabel.getDxcd()));
		if (lineLabel.getBindTarget() != null) {
			if (lineLabel.getBindTarget() == BindTargetType.dlbt.getIndex()) {
				viewHolder.getInscBindTarget().getEditTextView().setText(BindTargetType.dlbt.getType());
			} else if (lineLabel.getBindTarget() == BindTargetType.zjjt.getIndex()) {
				viewHolder.getInscBindTarget().getEditTextView().setText(BindTargetType.zjjt.getType());
			} else if (lineLabel.getBindTarget() == BindTargetType.zdsb.getIndex()) {
				viewHolder.getInscBindTarget().getEditTextView().setText(BindTargetType.zdsb.getType());
			}
		}
		// 显示附件
		List<OfflineAttach> offlineAttachs = lineLabel.getAttr();
		if (offlineAttachs != null && offlineAttachs.size() > 0) {
			attachFiles = new ArrayList<String>();

			for (int i = 0; i < offlineAttachs.size(); i++) {
				OfflineAttach attach = offlineAttachs.get(i);
				if (attach.getFileName().startsWith("position_")) {
					positionAttach = attach;
					offlineAttachs.remove(i);
					i = i - 1;
					continue;
				}
				if (attach.getFileName().startsWith("background_")) {
					backgroundAttach = attach;
					offlineAttachs.remove(i);
					i = i - 1;
					continue;
				}
				attachFiles.add(attach.getFilePath());
			}
		}
		viewHolder.getAvAttachment().addExistsAttachments(attachFiles);

		if (positionAttach != null) {
			positionPhotoAbsolutelyRoot = positionAttach.getFilePath();
			showPhoto(positionPhotoAbsolutelyRoot, viewHolder.getIvPostionPhoto(), viewHolder.getIvPositionDelete());
		}
		if (backgroundAttach != null) {
			backgroundPhotoAbsolutelyRoot = backgroundAttach.getFilePath();
			showPhoto(backgroundPhotoAbsolutelyRoot, viewHolder.getIvBackgroundPhoto(),
					viewHolder.getIvBackgroundDelete());
		}
	}

	// 初始化事件
	private void initEvent() {
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getBtnReceive().setOnClickListener(ocl);
		viewHolder.getIvLinkDeviceEnter().setOnClickListener(ocl);
		viewHolder.getBtnLinkDeviceLink().setOnClickListener(ocl);
		viewHolder.getBtnGpReceive().setOnClickListener(ocl);
		viewHolder.getIvPostionPhoto().setOnClickListener(ocl);
		viewHolder.getIvBackgroundPhoto().setOnClickListener(ocl);
		viewHolder.getIvPositionDelete().setOnClickListener(ocl);
		viewHolder.getIvBackgroundDelete().setOnClickListener(ocl);
	}

	// 点击
	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: {
				saveAttachment();
				Intent intent = new Intent();
				intent.putExtra("lable", lineLabel);
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				intent.putExtra("isReturn", "true");
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: {
				String lableId = StringUtils.nullStrToEmpty(viewHolder.getInconLableNo().getEdtTextValue());
				// String sequence =
				// StringUtils.nullStrToEmpty(viewHolder.getInconSequence().getEdtTextValue());
				// if (viewHolder.getIcSbmc().getEdtTextValue().equals("")) {
				// ToastUtil.show(mActivity, "设备名称不能为空", 200);
				// viewHolder.getIcSbmc().getEditTextView().requestFocus();
				// return;
				// }
				// String crossover =
				// StringUtils.nullStrToEmpty(viewHolder.getInconCrossover()
				// .getEdtTextValue());
				// if (lableId.isEmpty()) {
				// ToastUtil.show(mActivity, "标签ID不能为空", 200);
				// viewHolder.getInconCrossover().getEditTextView().requestFocus();
				// return;
				// }
				// if (sequence.isEmpty()) {
				// ToastUtil.show(mActivity, "序号不能为空", 200);
				// return;
				// }
				// if (crossover.isEmpty()) {
				// ToastUtil.show(mActivity, "与上一个标签的距离不能为空", 200);
				// return;
				// }
				saveData();
				Intent intent = new Intent();
				intent.putExtra("lable", lineLabel);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.btn_lable_receive: {// 超高频
				AndroidBasicComponentUtils.launchActivityForResult(mActivity, LabelReadActivity.class,
						REQUEST_LABEL_ID);
				break;
			}
			case R.id.btn_lable_gp: { // 高频

				if (hasNfc(mActivity)) { // 高频手机带有nfc模块读取
					Intent intent = new Intent(mActivity, BluetoothNFC_DK_Activity.class);
					mActivity.startActivityForResult(intent, REQUEST_LABEL_ID2);
				} else {// 高频蓝牙
					Intent intent = new Intent(mActivity, BluetoothNFC_DK_Activity.class);
					// intent.putExtra("isInDevelopment", true);
					mActivity.startActivityForResult(intent, REQUEST_LABEL_ID3);
				}

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
			case R.id.iv_scene_position_photo:// 位置图
				if (positionPhotoAbsolutelyRoot == null) {
					rootPath = comUploadBll.getRootPath(rootPath, mActivity);
					fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/标签";
					String fileName = POSITION_PHOTO_FILE_NAME + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".jpg";
					PhotoUtils.takePicture(fileDir + "/position",
							POSITION_PHOTO_FILE_NAME + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".jpg",
							mActivity, POSITION_PHOTO_REQUEST);
					positionPhotoAbsolutelyRoot = fileDir + "/position" + "/" + fileName;
					lablePositionPhoteChange = true;
				} else {
					PhotoUtil.openPhoto(mActivity, positionPhotoAbsolutelyRoot);
				}
				break;
			case R.id.iv_scene_background_photo:// 背景图
				if (backgroundPhotoAbsolutelyRoot == null) {
					rootPath = comUploadBll.getRootPath(rootPath, mActivity);
					fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/标签";
					String fileName = BACKGROUND_PHOTO_FILE_NAME + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".jpg";
					PhotoUtils.takePicture(fileDir + "/background",
							fileName,
							mActivity, BACKGROUND_PHOTO_REQUEST);
					backgroundPhotoAbsolutelyRoot = fileDir + "/background" + "/" + fileName;
					lableBackGroudPhoteChange=true;
				} else {
					PhotoUtil.openPhoto(mActivity, backgroundPhotoAbsolutelyRoot);
				}
				break;
			case R.id.iv_scene_position_delete:// 位置图删除
				final DialogUtil dialogU = new DialogUtil(mActivity);
				dialogU.showAlertDialog("是否删除位置图", new String[] { "删除", "取消" }, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							positionPhotoAbsolutelyRoot = deletePhoto(positionPhotoAbsolutelyRoot,
									viewHolder.getIvPostionPhoto(), viewHolder.getIvPositionDelete(), positionAttach);
						}
						dialog.dismiss();
					}

				}, true);
				break;
			case R.id.iv_scene_background_delete:// 背景图删除
				final DialogUtil dialogB = new DialogUtil(mActivity);
				dialogB.showAlertDialog("是否删除背景图", new String[] { "删除", "取消" }, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							backgroundPhotoAbsolutelyRoot = deletePhoto(backgroundPhotoAbsolutelyRoot,
									viewHolder.getIvBackgroundPhoto(), viewHolder.getIvBackgroundDelete(),
									backgroundAttach);
						}
						dialog.dismiss();
					}

				}, true);
				break;
			default:
				break;
			}
		}
	};

	private String deletePhoto(String photoUrl, ImageView ivPhoto, ImageView ivDelete, OfflineAttach attach) {
		if (photoUrl != null) {
			File file = new File(photoUrl);
			if (file.exists()) {
				file.delete();
			}
			if (attach != null) {
				comUploadBll.deleteById(OfflineAttach.class, attach.getComUploadId());
			}
		}
		ivDelete.setVisibility(View.GONE);
		ivPhoto.setImageResource(R.drawable.scene_photo);
		return null;
	}

	private boolean showPhoto(String photoUrl, ImageView ivPhoto, ImageView ivDelete) {
		File file = new File(photoUrl);
		if (!file.exists()) {
			ToastUtil.showShort(mActivity, "文件已经被删除");
			photoUrl = null;
			return false;
		}
		int width = DensityUtil.dip2px(mActivity, 180);
		int height = DensityUtil.dip2px(mActivity, 130);
		Glide.with(mActivity).load(file).override(width, height).centerCrop().into(ivPhoto);
		ivDelete.setVisibility(View.VISIBLE);
		return true;
	}

	/**
	 * 关联设备
	 */
	protected void linkDevice() {
		Intent intent = new Intent();
		intent.setClass(mActivity, SelectDeviceActivity.class);
		intent.putExtra("LinkDeviceMark", TableNameEnum.DLDZBQ.getTableName());
		AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, LableLink_RequestCode);
	}

	protected void saveData() {
		// lineLabel为空两种情况一种是新增时另一种是编辑时在新增时没添加
		if (lineLabel == null) {
			lineLabel = new EcLineLabelEntityVo();
			lineLabel.setObjId((UUIDGen.randomUUID()));
			lineLabel.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			lineLabel.setOrgid(GlobalEntry.currentProject.getOrgId());
			lineLabel.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				lineLabel.setOperateMark(1);// 编辑下新增
			}
		}
		lineLabel.setGxsj(DateUtils.getDate());
		if (bindTargetType != -1) {
			lineLabel.setDeviceType(bindTargetType + "");// 标签挂接的设备类型
		}
		// lineLabel.setDevObjId(devObjId);
		if (BindTargetType.dlbt.getType().equals(viewHolder.getInscBindTarget().getEdtTextValue())) {
			lineLabel.setBindTarget((BindTargetType.dlbt.getIndex()));
		} else if (BindTargetType.zjjt.getType().equals(viewHolder.getInscBindTarget().getEdtTextValue())) {
			lineLabel.setBindTarget((BindTargetType.zjjt.getIndex()));
		} else if (BindTargetType.zdsb.getType().equals(viewHolder.getInscBindTarget().getEdtTextValue())) {
			lineLabel.setBindTarget((BindTargetType.zdsb.getIndex()));
		}
		lineLabel.setDevName(viewHolder.getIcSbmc().getEdtTextValue().trim());
		lineLabel.setLabelNo(viewHolder.getInconLableNo().getEdtTextValue().trim());
		lineLabel.setZjgjfx(viewHolder.getInconZjgjfx().getEdtTextValue().trim());
		String jzjgjjl = viewHolder.getInconJzjgjjl().getEdtTextValue().trim();
		lineLabel.setJzjgjjl("".equals(jzjgjjl) ? null : Double.valueOf(jzjgjjl));
		lineLabel.setFsssqk(viewHolder.getInconFsssqk().getEdtTextValue().trim());
		lineLabel.setWzdt(viewHolder.getInconWzdt().getEdtTextValue().trim());
		lineLabel.setZwtdfbqk(viewHolder.getInconZwtdfbqk().getEdtTextValue().trim());
		lineLabel.setTdndlqk(viewHolder.getInconTdndlqk().getEdtTextValue().trim());
		lineLabel.setDblkxx(viewHolder.getInconDblkxx().getEdtTextValue().trim());
		lineLabel.setSequence("".equals(viewHolder.getInconSequence().getEdtTextValue().trim()) ? null
				: Integer.valueOf(viewHolder.getInconSequence().getEdtTextValue()));
		lineLabel.setDistance("".equals(viewHolder.getInconCrossover().getEdtTextValue().trim()) ? null :
			Double.valueOf(viewHolder.getInconCrossover().getEdtTextValue().trim()));
		lineLabel.setDxxh(viewHolder.getInconLableDxxh().getEdtTextValue().trim());
		lineLabel.setDxcd("".equals(viewHolder.getInConLableDxcd().getEdtTextValue().trim())?null:
			Double.valueOf(viewHolder.getInConLableDxcd().getEdtTextValue().trim()));
		// 调用附件的完毕方法 否则无法取到附件的路径
		viewHolder.getAvAttachment().finish();
		// 附件操作
		saveAttachment();
	}

	/**
	 * 附件操作
	 */
	private void saveAttachment() {
		if (lineLabel == null) {
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
		offlineAttachs = lineLabel.getAttr();
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setFileName(FileUtil.getPathName(path));
				offlineAttach.setOwnerCode(TableNameEnum.DLDZBQ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(lineLabel.getObjId());
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
				offlineAttach.setOwnerCode(TableNameEnum.DLDZBQ.getTableName());
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(lineLabel.getObjId());
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

		// 保存位置图/背景图信息
		if (positionPhotoAbsolutelyRoot != null) {
			if (positionAttach == null) {
				positionAttach = new OfflineAttach(UUIDGen.randomUUID());
				positionAttach.setAppCode(null);
				positionAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				positionAttach.setWorkNo(lineLabel.getObjId());
				positionAttach.setOwnerCode(TableNameEnum.DLDZBQ.getTableName());
			}
			if(GlobalEntry.editNode){
				if(lablePositionPhoteChange){//如果标签背景图已改变
					positionAttach.setIsUploaded(WhetherEnum.NO.getValue());
				}
			}else {
				positionAttach.setIsUploaded(WhetherEnum.NO.getValue());
			}
			positionAttach.setFileName(FileUtil.getFileName(positionPhotoAbsolutelyRoot));
			positionAttach.setFilePath(positionPhotoAbsolutelyRoot);

			//comUploadBll.saveOrUpdate(positionAttach);
			comUpload.add(positionAttach);
		}

		if (backgroundPhotoAbsolutelyRoot != null) {
			if (backgroundAttach == null) {
				backgroundAttach = new OfflineAttach(UUIDGen.randomUUID());
				backgroundAttach.setAppCode(null);
				backgroundAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				backgroundAttach.setOwnerCode(TableNameEnum.DLDZBQ.getTableName());
				backgroundAttach.setWorkNo(lineLabel.getObjId());
			}
			if(GlobalEntry.editNode){
				if(lableBackGroudPhoteChange){//如果标签背景图已改变
					backgroundAttach.setIsUploaded(WhetherEnum.NO.getValue());
				}
			}else {
				backgroundAttach.setIsUploaded(WhetherEnum.NO.getValue());
			}
			backgroundAttach.setFileName(FileUtil.getFileName(backgroundPhotoAbsolutelyRoot));
			backgroundAttach.setFilePath(backgroundPhotoAbsolutelyRoot);

			//comUploadBll.saveOrUpdate(backgroundAttach);
			comUpload.add(backgroundAttach);
		}
		lineLabel.setAttr(comUpload);
	}

	private int REQUEST_LABEL_ID = 0x21333334;// 超高频
	private int REQUEST_LABEL_ID2 = 0x21333335;// 高频nfc
	private int REQUEST_LABEL_ID3 = 0x21333333;// 高频蓝牙

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == LableLink_RequestCode) {
				EcLineLabelEntity entity = (EcLineLabelEntity) data.getSerializableExtra("LinkDeviceResult");
				try {
					lineLabel = new EcLineLabelEntityVo();
					ClonesUtil.clones(lineLabel, entity);
					if (lineLabel != null) {
						lineLabel.setOperateMark(3);// 设备关联
						updateData();
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} else if (requestCode == REQUEST_LABEL_ID) {
				// 标签ID
				viewHolder.getInconLableNo()
						.setEdtText("" + data.getStringExtra(LabelReadActivity.INTENT_KEY_LABEL_ID));
				viewHolder.getInconLableNo().getEditTextView().setEnabled(false);
			} else if (requestCode == REQUEST_LABEL_ID2) {//
				// 接收高频标签ID
				String rfcId = data.getStringExtra(BluetoothNFC_DK_Activity.RESULT_DATA_KEY);
				viewHolder.getInconLableNo().setEdtText(rfcId);
				viewHolder.getInconLableNo().getEditTextView().setEnabled(false);
			} else if (requestCode == REQUEST_LABEL_ID3) {
				// 接收高频标签ID
				String rfcId = data.getStringExtra(BluetoothNFC_DK_Activity.RESULT_DATA_KEY);
				viewHolder.getInconLableNo().setEdtText(rfcId);
				viewHolder.getInconLableNo().getEditTextView().setEnabled(false);
			} else if (requestCode == POSITION_PHOTO_REQUEST) {
				boolean showPhoto = showPhoto(positionPhotoAbsolutelyRoot, viewHolder.getIvPostionPhoto(),
						viewHolder.getIvPositionDelete());
				if (!showPhoto) {
					positionPhotoAbsolutelyRoot = null;
				}
				return;
			} else if (requestCode == BACKGROUND_PHOTO_REQUEST) {
				boolean showPhoto = showPhoto(backgroundPhotoAbsolutelyRoot, viewHolder.getIvBackgroundPhoto(),
						viewHolder.getIvBackgroundDelete());
				if (!showPhoto) {
					backgroundPhotoAbsolutelyRoot = null;
				}
				return;
			} else {
				viewHolder.getAvAttachment().onActivityResult(requestCode, resultCode, data);
			}
		}

		if (resultCode != Activity.RESULT_OK) {
			if (requestCode == POSITION_PHOTO_REQUEST) {
				ToastUtil.showShort(mActivity, "已取消位置图的拍摄");
				positionPhotoAbsolutelyRoot = null;
				return;
			}
			if (requestCode == BACKGROUND_PHOTO_REQUEST) {
				ToastUtil.showShort(mActivity, "已取消背景图的拍摄");
				backgroundPhotoAbsolutelyRoot = null;
				return;
			}
		}
	}

	/**
	 * 判断nfc模块是否启用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNfc(Context context) {
		boolean bRet = false;
		if (context == null)
			return bRet;
		NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
		NfcAdapter adapter = manager.getDefaultAdapter();
		if (adapter != null && adapter.isEnabled()) {
			// adapter存在，能启用
			bRet = true;
		}
		return bRet;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			viewHolder.getHcHead().getReturnView().performClick();
		}
		return super.onKeyDown(keyCode, event);
	}
}
