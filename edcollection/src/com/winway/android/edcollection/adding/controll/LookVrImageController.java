package com.winway.android.edcollection.adding.controll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.winway.android.basicbusiness.business.OfflineAttachGeter;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.viewholder.LookVrImageViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcVRRefEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.media.vr.PanoramaView;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author lyh
 * @version 创建时间：2017年5月19日
 *
 */
public class LookVrImageController extends BaseControll<LookVrImageViewHolder> {
	private List<EcVRRefEntity> vrList;
	private List<OfflineAttach> attachList;
	private BaseDBUtil baseDBUtil;
	private int index = 0;

	static OfflineAttach translater(com.winway.android.basicbusiness.entity.OfflineAttach attach) {
		OfflineAttach result = new OfflineAttach();
		result.setComUploadId(attach.getComUploadId());
		result.setAppCode(attach.getAppCode());
		result.setFileName(attach.getFileName());
		result.setFilePath(attach.getFilePath());
		result.setIsUploaded(attach.getIsUploaded());
		result.setOrgid(GlobalEntry.currentProject.getOrgId());
		result.setOwnerCode(attach.getOwnerCode());
		result.setWorkNo(attach.getWorkNo());
		return result;
	}

	private boolean hasGetFirst = false;

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		Intent intent = getIntent();
		vrList = (List<EcVRRefEntity>) intent.getSerializableExtra("vrList");
		baseDBUtil = new BaseDBUtil(mActivity, new File(GlobalEntry.prjDbUrl));
		attachList = new ArrayList<OfflineAttach>();
		if (vrList != null && !vrList.isEmpty()) {
			OfflineAttachGeter geter = new OfflineAttachGeter(mActivity, baseDBUtil, GlobalEntry.fileServerUrl,
					GlobalEntry.loginResult.getCredit(), GlobalEntry.downLoadDir);
			for (EcVRRefEntity eVrRefEntity : vrList) {
				if (eVrRefEntity.getId() == null) {
					continue;
				}
				try {
					geter.getOfflieAttach(eVrRefEntity.getId(), ".vr", new OfflineAttachGeter.CallBack() {
						@Override
						public void callFile(com.winway.android.basicbusiness.entity.OfflineAttach attach) {
							OfflineAttach translater = translater(attach);
							attachList.add(translater);
							if (!hasGetFirst) {
								hasGetFirst = true;
								initData();
							}
						}

						@Override
						public void callAttachs(int code,
								List<com.winway.android.basicbusiness.entity.OfflineAttach> list, boolean hasfile) {

						}

						@Override
						public void callError(int code) {
							// TODO Auto-generated method stub
							
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// initData();
		initEvent();
	}

	private void initData() {
		if (attachList != null && !attachList.isEmpty()) {
			PanoramaView panoramaView = new PanoramaView(mActivity, attachList.get(0).getFilePath());
			viewHolder.getRlContainer().addView(panoramaView);
		}
	}

	private void showVRImage(String imgPath) {
		viewHolder.getRlContainer().removeAllViews();
		PanoramaView panoramaView = new PanoramaView(mActivity, imgPath);
		viewHolder.getRlContainer().addView(panoramaView);
	}

	private void initEvent() {
		viewHolder.getBtnDown().setOnClickListener(ocl);
		viewHolder.getBtnUp().setOnClickListener(ocl);
		viewHolder.getIvClose().setOnClickListener(ocl);
	}

	private OnClickListener ocl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_layout_vr_image_show_up: // 上一张
			{
				// 当没有全景照片或全景只有一张时，提示没有上一张照片了
				if (attachList == null || attachList.isEmpty() || attachList.size() == 1) {
					showToast("没有上一张了喔");
					break;
				}
				// 当前图片为第一张时，点击上一张后会将最后一张图片显示出来
				if (index == 0) {
					// showToast("没有上一张了喔");
					// break;
					index = attachList.size() - 1;
					showVRImage(attachList.get(index).getFilePath());
					break;
				}
				index = index - 1;
				showVRImage(attachList.get(index).getFilePath());
			}
				break;
			case R.id.btn_layout_vr_image_show_down: // 下一张
			{
				// 当没有全景照片或全景只有一张时，提示没有下一张照片了
				if (attachList == null || attachList.isEmpty() || attachList.size() == 1) {
					showToast("没有下一张了喔");
					break;
				}
				// 当前图片为最后一张时，点击下一张后会将第一张图片显示出来
				if (index == attachList.size() - 1) {
					index = 0;
					showVRImage(attachList.get(index).getFilePath());
					// showToast("没有下一张了喔");
					break;
				}
				index = index + 1;
				showVRImage(attachList.get(index).getFilePath());
			}
				break;
			case R.id.iv_vr_image_close: // 关闭图标
			{
				mActivity.finish();
			}
				break;

			default:
				break;
			}
		}
	};
}
