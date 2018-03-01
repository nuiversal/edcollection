package com.winway.android.edcollection.adding.controll;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.bll.VrBll;
import com.winway.android.edcollection.adding.fragment.BasicInfoFragment;
import com.winway.android.edcollection.adding.viewholder.ScenePhotoViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseFragmentControll;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcVRRefEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.ewidgets.attachment.AttachmentAttrs;
import com.winway.android.ewidgets.attachment.AttachmentInit;
import com.winway.android.ewidgets.attachment.AttachmentUtil;
import com.winway.android.ewidgets.attachment.AttachmentUtil.AddDeleteResult;
import com.winway.android.ewidgets.attachment.AttachmentView.AttachmentResult;
import com.winway.android.ewidgets.attachment.AttachmentView.OnAttachmentViewClickListener;
import com.winway.android.ewidgets.attachment.RicohUtil;
import com.winway.android.ewidgets.attachment.entity.Panoramic;
import com.winway.android.media.photo.photoselector.util.PhotoUtils;
import com.winway.android.util.DateUtils;
import com.winway.android.util.DensityUtil;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ListUtil;
import com.winway.android.util.PhotoUtil;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 其它信息业务处理
 * 
 * @author xs
 * 
 */
public class ScenePhotoControll extends BaseFragmentControll<ScenePhotoViewHolder> {
	private ComUploadBll comUploadBll;
	private ArrayList<String> attachFiles = null;// 已经存在的离线附件路径
	private List<OfflineAttach> comUpload;// 离线附件集合
	private VrBll vrBll = null;
	private CommonBll commonBll = null;
	private String rootPath = "";
	public String fileDir = "";
	private String marderId = "";
	private AttachmentAttrs attrs = null;

	/**
	 * 初始化
	 */
	@Override
	public void init() {
		String projectBDUrl = GlobalEntry.prjDbUrl;
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		commonBll = new CommonBll(mActivity, projectBDUrl);
		vrBll = new VrBll(mActivity, projectBDUrl);
		attrs = new AttachmentAttrs();
		attrs.hasFinish = false;
		attrs.hasVr = true;// 打开全景采集
		viewHolder.getSceneAttachment().setAttachmentAttrs(attrs);
		initEditDatas();
		initEvent();
	}

	/**
	 * 初始化编辑数据
	 */
	private void initEditDatas() {
		// 获取已经存在的附件信息
		Intent intent = getIntent();
		EcNodeEntity ecNodeEntity = (EcNodeEntity) intent.getSerializableExtra("EcNodeEntity");
		if (ecNodeEntity == null) {
			return;
		}
		String oid = ecNodeEntity.getOid();
		String expr = "WORK_NO='" + oid + "'";
		comUpload = comUploadBll.queryByExpr2(OfflineAttach.class, expr);
		if (comUpload == null) {
			return;
		}
		if (GlobalEntry.editNode) {
			initAttachment();
		}
		// 显示附件
		if (comUpload != null && comUpload.size() > 0) {
			attachFiles = new ArrayList<String>();
			for (int i = 0; i < comUpload.size(); i++) {
				OfflineAttach attach = comUpload.get(i);
				if (attach.getFileName().startsWith("position_")) {
					positionAttach = attach;
					comUpload.remove(i);
					i = i - 1;
					continue;
				}
				if (attach.getFileName().startsWith("background_")) {
					backgroundAttach = attach;
					comUpload.remove(i);
					i = i - 1;
					continue;
				}
				attachFiles.add(attach.getFilePath());
			}
		}
		viewHolder.getSceneAttachment().addExistsAttachments(attachFiles);
		if (positionAttach != null) {
			positionPhotoAbsolutelyRoot = positionAttach.getFilePath();
			boolean isExsit = showPhoto(positionPhotoAbsolutelyRoot, viewHolder.getIvPostionPhoto(),
					viewHolder.getIvPositionDelete());
			if (!isExsit) {
				positionPhotoAbsolutelyRoot = null;
			}
		}
		if (backgroundAttach != null) {
			backgroundPhotoAbsolutelyRoot = backgroundAttach.getFilePath();
			boolean isExsit = showPhoto(backgroundPhotoAbsolutelyRoot, viewHolder.getIvBackgroundPhoto(),
					viewHolder.getIvBackgroundDelete());
			if (!isExsit) {
				backgroundPhotoAbsolutelyRoot = null;
			}
		}
	}

	private void getMarderId() {
		MarkerCollectControll markerCollectControll = MarkerCollectControll.getInstance();
		BasicInfoFragment basicInfoFragment = markerCollectControll.getBasicInfoFragment();
		marderId = basicInfoFragment.getAction().getViewHolder().getIcMarkerId().getEdtTextValue();
	}

	/**
	 * 初始化附件
	 */

	private void initAttachment() {
		getFileDir();
		// attrs = new AttachmentAttrs();
		AttachmentInit.initAttachmentAttrs(attrs, fileDir + "/video", fileDir + "/photo", fileDir + "/voice",
				fileDir + "/text", fileDir + "/vr");
		attrs.activity = mActivity;
		/** 控件附件采集的最大数量 */
		attrs.maxPhoto = 5;
		attrs.maxVideo = 5;
		attrs.maxVoice = 5;
		attrs.maxText = 5;
		attrs.maxVr = 5;
		// attrs.hasFinish = false;// 隐藏完毕按钮
		attrs.hasVr = true;// 打开全景采集
		viewHolder.getSceneAttachment().setAttachmentAttrs(attrs);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void getFileDir() {
		getMarderId();
		rootPath = comUploadBll.getRootPath(rootPath, mActivity);
		if (!fileDir.equals("")) { // 说明已经初始化一次了
			String oldMarkerId = fileDir.substring(fileDir.lastIndexOf("/") + 1); // 路径中的标识器id
			if (!oldMarkerId.equals(marderId)) { // 路径中的id与采集信息界面中的id不一致时，使用路径中的id
				fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/路径点/" + oldMarkerId;
			} else {
				fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/路径点/" + marderId;
			}
		} else {
			fileDir = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/路径点/" + marderId;
		}
	}

	private OfflineAttach positionAttach;
	private OfflineAttach backgroundAttach;

	/**
	 * 保存现场照片信息
	 * 
	 * @param objId
	 */
	public void saveScenePhotoInfo(String objId) {
		getMarderId();
		if (comUpload == null) {
			comUpload = new ArrayList<>();
		}
		viewHolder.getSceneAttachment().finish();
		// 查找出附件组件中存在的附件路径
		AttachmentResult result = viewHolder.getSceneAttachment().getResult();
		// 查找出添加、删除、未改变的附件
		AddDeleteResult addDeleteResult = AttachmentUtil.findAddDeleteResult(result, attachFiles);
		// 添加的路径或者是重命名的附件
		ArrayList<String> addList = addDeleteResult.addList;
		// 删除的附件
		ArrayList<String> deleteList = addDeleteResult.deleteList;
		// 未改变的附件
		ArrayList<String> noChangeList = addDeleteResult.nochangeList;

		OfflineAttach offlineAttach = null;
		List<OfflineAttach> offlineAttachs = comUpload;
		// 操作添加的路径或者是重命名的附件
		if (addList != null && addList.size() > 0) {
			for (String path : addList) {
				offlineAttach = new OfflineAttach(UUIDGen.randomUUID());
				offlineAttach.setAppCode(null);
				offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setFileName(FileUtil.getFileName(path));
				offlineAttach.setFilePath(path);
				offlineAttach.setWorkNo(objId);
				offlineAttach.setOwnerCode(TableNameEnum.LJD.getTableName());
				comUpload.add(offlineAttach);
			}
		}
		// 操作未改变的附件
		if (noChangeList != null && noChangeList.size() > 0) {
			for (String path : noChangeList) {
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
				offlineAttach.setFileName(FileUtil.getFileName(path));
				offlineAttach.setFilePath(path);
				// offlineAttach.setIsUploaded(WhetherEnum.NO.getValue());
				offlineAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				offlineAttach.setOwnerCode(TableNameEnum.LJD.getTableName());
				offlineAttach.setWorkNo(objId);
				comUpload.add(offlineAttach);
			}
		}
		List<OfflineAttach> list = new ArrayList<OfflineAttach>();
		// 操作删除的附件 主要是编辑时删除数据库数据
		if (deleteList != null && deleteList.size() > 0) {
			for (String path : deleteList) {
				if (offlineAttachs != null && offlineAttachs.size() > 0) {
					for (OfflineAttach attach : offlineAttachs) {
						if (attach.getFilePath().equals(path) && attach.getComUploadId() != null) {
							comUploadBll.deleteById(attach.getComUploadId());
							list.add(attach);
						}
					}
				}
			}
		}
		if (list != null && !list.isEmpty()) {
			for (OfflineAttach offlineAttach2 : list) {
				boolean inList = ListUtil.isInList(offlineAttach2, comUpload);
				if (inList) {
					comUpload.remove(offlineAttach2);
				}
			}
		}
		// 保存附件信息
		String oldMarkerId = fileDir.substring(fileDir.lastIndexOf("/") + 1); // 路径中的标识器id
		if (comUpload != null && comUpload.size() > 0) {
			for (int i = 0; i < comUpload.size(); i++) {
				String filePath = comUpload.get(i).getFilePath();
				// 如果采集界面中标识器id与数据库中的标识器id不一致
				if (!oldMarkerId.equals(marderId)) {
					// 修改数据库的路径
					filePath = filePath.replaceAll(oldMarkerId, marderId);
					comUpload.get(i).setFilePath(filePath);
				}
				if (filePath.lastIndexOf(".vr") != -1) {// 处理vr情况
					EcVRRefEntity vrEntity = vrBll.combileVREntity(comUpload.get(i).getComUploadId(),
							ResourceEnum.DZBZQ.getValue(), comUpload.get(i).getWorkNo(), objId,
							comUpload.get(i).getFileName(), GlobalEntry.loginResult.getOrgid(),
							GlobalEntry.currentProject.getEmProjectId());
					vrBll.saveOrUpdate(vrEntity);
					// 记录日志
					commonBll.handleDataLog(vrEntity.getId(), TableNameEnum.EC_VRREF.getTableName(),
							DataLogOperatorType.Add, WhetherEnum.NO, "全景：" + vrEntity.getName(), true);
				}
			}
			// 修改文件路径
			// File oldFile = new File(fileDir);
			// File newFile = new File(rootPath + "附件/" +
			// GlobalEntry.currentProject.getPrjNo() + "/路径点/"+marderId);
			// oldFile.renameTo(newFile);
			comUploadBll.saveOrUpdate(comUpload);
		}
		// 保存位置图/背景图信息
		if (positionPhotoAbsolutelyRoot != null) {
			if (positionAttach == null) {
				positionAttach = new OfflineAttach(UUIDGen.randomUUID());
				positionAttach.setAppCode(null);
				positionAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				positionAttach.setWorkNo(objId);
				positionAttach.setOwnerCode(TableNameEnum.LJD.getTableName());
			}
			if (GlobalEntry.editNode) {
				if (positionPhotoChange) {
					positionAttach.setIsUploaded(WhetherEnum.NO.getValue());
				}
			} else {
				positionAttach.setIsUploaded(WhetherEnum.NO.getValue());
			}
			positionAttach.setFileName(FileUtil.getFileName(positionPhotoAbsolutelyRoot));
			// 如果采集界面中标识器id与数据库中的标识器id不一致
			if (!oldMarkerId.equals(marderId)) {
				positionPhotoAbsolutelyRoot = positionPhotoAbsolutelyRoot.replace(oldMarkerId, marderId);
			}
			positionAttach.setFilePath(positionPhotoAbsolutelyRoot);

			comUploadBll.saveOrUpdate(positionAttach);
		}

		if (backgroundPhotoAbsolutelyRoot != null) {
			if (backgroundAttach == null) {
				backgroundAttach = new OfflineAttach(UUIDGen.randomUUID());
				backgroundAttach.setAppCode(null);
				backgroundAttach.setOrgid(GlobalEntry.currentProject.getOrgId());
				backgroundAttach.setOwnerCode(TableNameEnum.LJD.getTableName());
				backgroundAttach.setWorkNo(objId);
			}
			if (GlobalEntry.editNode) {
				if (backgroundPhotoChange) {
					backgroundAttach.setIsUploaded(WhetherEnum.NO.getValue());
				}
			} else {
				backgroundAttach.setIsUploaded(WhetherEnum.NO.getValue());
			}
			backgroundAttach.setFileName(FileUtil.getFileName(backgroundPhotoAbsolutelyRoot));
			// 如果采集界面中标识器id与数据库中的标识器id不一致
			if (!oldMarkerId.equals(marderId)) {
				backgroundPhotoAbsolutelyRoot = backgroundPhotoAbsolutelyRoot.replace(oldMarkerId, marderId);
			}
			backgroundAttach.setFilePath(backgroundPhotoAbsolutelyRoot);

			comUploadBll.saveOrUpdate(backgroundAttach);
		}
		// 修改文件路径
		File oldFile = new File(fileDir);
		File newFile = new File(rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/路径点/" + marderId);
		if (!oldFile.getAbsolutePath().equals(newFile.getAbsolutePath())) {
			oldFile.renameTo(newFile);
		}
		// if (comUpload == null || comUpload.isEmpty()) {
		// if (backgroundPhotoAbsolutelyRoot == null &&
		// positionPhotoAbsolutelyRoot == null) {
		// FileUtil.deleteDirectory(fileDir);
		// }
		// }
	}

	private void initEvent() {
		viewHolder.getIvPostionPhoto().setOnClickListener(ocl);
		viewHolder.getIvPositionDelete().setOnClickListener(ocl);
		viewHolder.getIvBackgroundPhoto().setOnClickListener(ocl);
		viewHolder.getIvBackgroundDelete().setOnClickListener(ocl);
		viewHolder.getSceneAttachment().setOnAttachmentViewClickListener(new OnAttachmentViewClickListener() {

			@Override
			public void onClick(View v) {
				initAttachment();
			}
		});
	}

	private final String POSITION_PHOTO_FILE_NAME = "position_"; // 位置图文件前缀
	private final String BACKGROUND_PHOTO_FILE_NAME = "background_";// 背景图文件前缀
	private final int POSITION_PHOTO_REQUEST = 0x00000000012; // 位置图请求码
	private final int BACKGROUND_PHOTO_REQUEST = 0x00000000013; // 背景图请求码
	private String positionPhotoAbsolutelyRoot; // 位置图的绝对路径
	private String backgroundPhotoAbsolutelyRoot; // 背景图的绝对路径
	private boolean positionPhotoChange; // 位置图是否有改变
	private boolean backgroundPhotoChange; // 背景图是否有改变

	private View.OnClickListener ocl = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_scene_position_photo:// 拍位置图
				if (positionPhotoAbsolutelyRoot == null) {
					getFileDir();
					String tmpFileDir = fileDir + "/position";
					String tmpFileName = POSITION_PHOTO_FILE_NAME + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss")
							+ ".jpg";
					PhotoUtils.takePicture(tmpFileDir, tmpFileName, mActivity, POSITION_PHOTO_REQUEST);
					positionPhotoAbsolutelyRoot = tmpFileDir + "/" + tmpFileName;
					positionPhotoChange = true;
				} else {
					PhotoUtil.openPhoto(mActivity, positionPhotoAbsolutelyRoot);
				}
				break;
			case R.id.iv_scene_background_photo:// 拍背景图
				if (backgroundPhotoAbsolutelyRoot == null) {
					getFileDir();
					String tmpFileDir = fileDir + "/background";
					String tmpFileName = BACKGROUND_PHOTO_FILE_NAME + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss")
							+ ".jpg";
					PhotoUtils.takePicture(tmpFileDir, tmpFileName, mActivity, BACKGROUND_PHOTO_REQUEST);
					backgroundPhotoAbsolutelyRoot = tmpFileDir+ "/" + tmpFileName;
					backgroundPhotoChange = true;
				} else {
					PhotoUtil.openPhoto(mActivity, backgroundPhotoAbsolutelyRoot);
				}
				break;
			case R.id.iv_scene_position_delete:// 删除位置图
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
			case R.id.iv_scene_background_delete:// 删除背景图
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

	/**
	 * 删除照片
	 * 
	 * @param photoUrl
	 *            照片路径
	 * @param ivPhoto
	 *            照片展示控件
	 * @param ivDelete
	 *            删除图标控件
	 * @param attach
	 *            附件实体
	 * @return
	 */
	private String deletePhoto(String photoUrl, ImageView ivPhoto, ImageView ivDelete, OfflineAttach attach) {
		removePhoto(ivPhoto, ivDelete);
		if (photoUrl != null) {
			File file = new File(photoUrl);
			if (file.exists()) {
				file.delete();
			}
			if (attach != null) {
				comUploadBll.deleteById(OfflineAttach.class, attach.getComUploadId());
			}
		}
		return null;
	}

	/**
	 * 移除照片
	 * 
	 * @param ivPhoto
	 * @param ivDelete
	 */
	public void removePhoto(ImageView ivPhoto, ImageView ivDelete) {
		ivDelete.setVisibility(View.GONE);
		ivPhoto.setImageResource(R.drawable.scene_photo);
	}

	/**
	 * 显示照片缩略图
	 * 
	 * @param photoUrl
	 *            照片路径
	 * @param ivPhoto
	 *            照片展示控件
	 * @param ivDelete
	 *            删除图标控件
	 * @return
	 */
	public boolean showPhoto(String photoUrl, ImageView ivPhoto, ImageView ivDelete) {
		File file = new File(photoUrl);
		if (!file.exists()) {
			photoUrl = null;
			return false;
		}
		int width = DensityUtil.dip2px(mActivity, 180);
		int height = DensityUtil.dip2px(mActivity, 150);
		Glide.with(mActivity).load(file).override(width, height).centerCrop().into(ivPhoto);
		ivDelete.setVisibility(View.VISIBLE);
		return true;
	}

	/**
	 * 显示缓存的附件
	 * 
	 * @param attachs
	 */
	public void showAttachmentCache(ArrayList<String> attachs) {
		this.attachFiles = attachs;
		viewHolder.getSceneAttachment().addExistsAttachments(attachFiles);
	}

	/**
	 * 生成附件路径信息
	 * 
	 * @return
	 */
	public boolean obtainAttachsPath() {
		attachFiles = new ArrayList<>();

		viewHolder.getSceneAttachment().finish();
		// 查找出附件组件中存在的附件路径
		AttachmentResult result = viewHolder.getSceneAttachment().getResult();
		// 查找出添加、删除、未改变的附件
		AddDeleteResult addDeleteResult = AttachmentUtil.findAddDeleteResult(result, attachFiles);
		// 添加的路径或者是重命名的附件
		ArrayList<String> addList = addDeleteResult.addList;

		if (addList != null) {
			attachFiles.addAll(addList);
		}
		if (attachFiles != null) {
			return true;
		}
		return false;
	}

	public String getPositionPhotoAbsolutelyRoot() {
		return positionPhotoAbsolutelyRoot;
	}

	public void setPositionPhotoAbsolutelyRoot(String positionPhotoAbsolutelyRoot) {
		this.positionPhotoAbsolutelyRoot = positionPhotoAbsolutelyRoot;
	}

	public String getBackgroundPhotoAbsolutelyRoot() {
		return backgroundPhotoAbsolutelyRoot;
	}

	public void setBackgroundPhotoAbsolutelyRoot(String backgroundPhotoAbsolutelyRoot) {
		this.backgroundPhotoAbsolutelyRoot = backgroundPhotoAbsolutelyRoot;
	}

	public ArrayList<String> getAttachFiles() {
		return attachFiles;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
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
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == POSITION_PHOTO_REQUEST) {
				boolean showPhoto = showPhoto(positionPhotoAbsolutelyRoot, viewHolder.getIvPostionPhoto(),
						viewHolder.getIvPositionDelete());
				if (!showPhoto) {
					positionPhotoAbsolutelyRoot = null;
				}
				return;
			}
			if (requestCode == BACKGROUND_PHOTO_REQUEST) {
				boolean showPhoto = showPhoto(backgroundPhotoAbsolutelyRoot, viewHolder.getIvBackgroundPhoto(),
						viewHolder.getIvBackgroundDelete());
				if (!showPhoto) {
					backgroundPhotoAbsolutelyRoot = null;
				}
				return;
			}
			viewHolder.getSceneAttachment().onActivityResult(requestCode, resultCode, data);
		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (attrs != null && attrs.panoramic != null) {
			Panoramic panoramic = attrs.panoramic;
			if (panoramic.getOpenRicohTime() > 0) {
				try {
					RicohUtil.copyPicture(panoramic.getFilePath(), panoramic.getOpenRicohTime());
					if (!FileUtil.checkFilePathExists(panoramic.getFilePath())) {
						panoramic.setFilePath(null);
						return;
					}
					// 显示缩列图
					viewHolder.getSceneAttachment().addAttachmentInContainer(panoramic.getFilePath(),
							viewHolder.getSceneAttachment().REQUEST_CODE_VR);
				} catch (IOException e) {
					e.printStackTrace();
					panoramic.setFilePath(null);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					attrs.panoramic = null;
				}
			}
		}
	}

}
