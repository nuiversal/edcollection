package com.winway.android.edcollection.adding.controll;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.viewholder.CabinetOrBoxViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
import com.winway.android.media.photo.photoselector.model.PhotoModel;
import com.winway.android.media.photo.photoselector.ui.PhotoPreviewActivity;
import com.winway.android.media.photo.photoselector.util.PhotoUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.TimeUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 环网柜/分接箱
 * 
 * @author lyh
 * @version 创建时间：2016年12月19日 上午9:19:09
 * 
 */
public class CabinetOrBoxControll extends BaseControll<CabinetOrBoxViewHolder> {
	public static int TAKE_PICTURE = 0x00001;// 背景拍照标示
	private String currentTakepicImageName = null;// 当前拍照的图片名
	private List<String> takePicNames = new ArrayList<String>();// 保存已拍照片名;
	private ArrayList<PhotoModel> takePicPaths = new ArrayList<PhotoModel>();// 照片全路径
	private LinearLayout view; // 图片显示视图
	private int selectRadio = 1;
	private List<OfflineAttach> comUpload;
	private EcDistBoxEntityVo distBox;
	private ComUploadBll comUploadBll;

	@Override
	public void init() {
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		initEvent();
		initData();
	}

	private void initData() {
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		distBox = (EcDistBoxEntityVo) bundle.getSerializable("DistBoxEntity");
		// 如果不为空则说明是修改
		if (distBox != null) {
			selectRadio = distBox.getJointType();
			((RadioButton) viewHolder.getRgDeviceType().getChildAt(selectRadio - 1)).setChecked(true);
			viewHolder.getIcName().setEdtText(distBox.getDevName());
			viewHolder.getIcLoop().setEdtText(distBox.getLinesCount() + "");
			List<OfflineAttach> comUploadlist = distBox.getAttr();
			// 显示照片
			if (comUploadlist != null && comUploadlist.size() > 0) {
				for (OfflineAttach comUpload : comUploadlist) {
					disImgShrinkDiagram(comUpload.getFilePath(), comUpload);
				}
			}
		}
	}

	/**
	 * 初始化点击事件
	 */
	private void initEvent() {
		// TODO Auto-generated method stub
		viewHolder.getHcSubHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcSubHead().getOkView().setOnClickListener(ocl);
		viewHolder.getTpcPhoto().getTakePhotoView().setOnClickListener(ocl);
		viewHolder.getTpcPhoto().getDelPhotoView().setOnClickListener(ocl);
		viewHolder.getRgDeviceType().setOnCheckedChangeListener(occl);
		viewHolder.getIcLoop().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		view = (LinearLayout) viewHolder.getTpcPhoto().getLlImagesBox();
	}

	private OnCheckedChangeListener occl = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if (R.id.rdoBtn_cabinet_or_box_box == checkedId) {
				selectRadio = 1;
			} else {
				selectRadio = 2;
			}
		}
	};

	/**
	 * 单击事件
	 */
	private OnClickListener ocl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: { // 返回
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: { // 确定
				if (viewHolder.getIcName().getEdtTextValue().isEmpty()) {
					ToastUtil.show(mActivity, "设备名称不能为空", 200);
					return;
				}
				if (viewHolder.getIcLoop().getEdtTextValue().isEmpty()) {
					ToastUtil.show(mActivity, "回路总数不能为空", 200);
					return;
				}
				saveData();
				Intent intent = new Intent();
				intent.putExtra("CabinetOrBox", distBox);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.iv_take_photo_takePic: { // 拍照
				takePhoth();
				break;
			}
			case R.id.iv_take_photo_delPic: { // 删除照片
				// 删除sd卡下照片
				for (PhotoModel string : takePicPaths) {
					FileUtil.deleteFileByFilePath(string.getOriginalPath());
				}
				// 编辑删除表中数据
				if (GlobalEntry.editNode) {
					List<OfflineAttach> comUploadlist = distBox.getAttr();
					if (comUploadlist != null && comUploadlist.size() > 0) {
						for (OfflineAttach comUpload : comUploadlist) {
							comUploadBll.deleteById(comUpload.getComUploadId());
						}
					}
				}
				// 清空图片视图里的所有图片
				view.removeAllViews();
				// 清空已拍照片名的所有名称
				takePicNames.clear();
				// 清空已拍照片的所有路径
				takePicPaths.clear();
				if (distBox != null) {
					distBox.setAttr(null);
				}
				break;
			}

			default:
				break;
			}
		}
	};

	/**
	 * 拍照
	 */
	private void takePhoth() {
		// 创建存放图片的文件夹
		String fileDir = FileUtil.AppRootPath + "picture/" + LineNameControll.ditchCable.getLineNo() + "/分接箱or环网柜/";
		String fileName = TimeUtils.generateTimePrefix() + ".jpg";
		currentTakepicImageName = fileDir + fileName;
		PhotoUtils.takePicture(fileDir, fileName, mActivity, TAKE_PICTURE);
	}

	/**
	 * 保存
	 */
	protected void saveData() {
		// distBox为空两种情况一种是新增时另一种是编辑时在新增时没添加
		if (distBox == null) {
			distBox = new EcDistBoxEntityVo();
			distBox.setEcDistBoxId(UUIDGen.randomUUID());
			distBox.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			distBox.setOrgid(GlobalEntry.currentProject.getOrgId());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				distBox.setEditAdd(true);
			}
		}
		distBox.setDevName(viewHolder.getIcName().getEdtTextValue());
		distBox.setJointType(selectRadio);
		distBox.setLinesCount(Integer.valueOf("".equals(viewHolder.getIcLoop().getEdtTextValue()) ? 0 + ""
				: viewHolder.getIcLoop().getEdtTextValue()));

		// 保存照片
		if (takePicPaths.size() > 0) {
			comUpload = new ArrayList<OfflineAttach>();
			OfflineAttach comUploadEntity;
			for (PhotoModel model : takePicPaths) {
				comUploadEntity = new OfflineAttach(UUIDGen.randomUUID());
				if (distBox.getAttr() != null && distBox.getAttr().size() > 0) {
					for (int i = 0; i < distBox.getAttr().size(); i++) {
						if (distBox.getAttr().get(i).getFilePath().equals(model.getOriginalPath())) {
							comUploadEntity.setComUploadId(distBox.getAttr().get(i).getComUploadId());
						}
					}
				}
				comUploadEntity.setAppCode(null);
				comUploadEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
				comUploadEntity.setIsUploaded(WhetherEnum.NO.getValue());
				comUploadEntity.setFileName(FileUtil.getPathName(model.getOriginalPath()));
				comUploadEntity.setOwnerCode(TableNameEnum.FJX.getTableName());
				comUploadEntity.setFilePath(model.getOriginalPath());
				comUploadEntity.setWorkNo(distBox.getEcDistBoxId());
				comUpload.add(comUploadEntity);
			}
			distBox.setAttr(comUpload);
		} else {
			distBox.setAttr(null);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
			// 找到图片界面
			if (view == null) {
				view = (LinearLayout) viewHolder.getTpcPhoto().getLlImagesBox();
			}
			disImgShrinkDiagram(currentTakepicImageName, null);
		}
	}

	/**
	 * 照片缩略图
	 * 
	 * @param view显示图片的容器
	 */
	private void disImgShrinkDiagram(String imgPath, final OfflineAttach entity) {
		final View imgItem = View.inflate(mActivity, R.layout.image_item, null);
		ImageView imgView = (ImageView) imgItem.findViewById(R.id.imgview_image_item);
		Button close = (Button) imgItem.findViewById(R.id.btnClose_image_item);
		final PhotoModel model = new PhotoModel(imgPath, true);
		takePicPaths.add(model);// 保存路径+图片名
		String imageUrl = "file://" + imgPath;
		PhotoUtils.showImage(mActivity, imageUrl, imgView);
		final String picName = FileUtil.getPathName(imgPath);// 获取图片名
		takePicNames.add(picName);// 保存图片名
		// 预览
		imgView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("photos", takePicPaths);
				startActivity(PhotoPreviewActivity.class, bundle);
			}
		});
		// 删除
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				view.removeView(imgItem);// 移除图片视图
				takePicNames.remove(picName);// 从保存已拍照片名中删除
				takePicPaths.remove(model);// 从选择的照片中删除
				FileUtil.deleteFileByFilePath(model.getOriginalPath());
				if (entity != null && GlobalEntry.editNode) {
					comUploadBll.deleteById(entity.getComUploadId());
				}
			}
		});
		view.addView(imgItem);
	}
}
