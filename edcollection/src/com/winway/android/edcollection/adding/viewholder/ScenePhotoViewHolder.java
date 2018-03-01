package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.ewidgets.attachment.AttachmentView;

import android.widget.ImageView;

/**
 * 现场照片ui初始化
 * 
 * @author lyh
 * @version 创建时间：2016年12月14日 上午9:38:56
 * 
 */
public class ScenePhotoViewHolder extends BaseViewHolder {
	@ViewInject(R.id.av_scene_attachment)
	private AttachmentView sceneAttachment; // 附件组件

	@ViewInject(R.id.iv_scene_position_photo)
	private ImageView ivPostionPhoto;// 位置图

	@ViewInject(R.id.iv_scene_background_photo)
	private ImageView ivBackgroundPhoto;// 背景图

	@ViewInject(R.id.iv_scene_position_delete)
	private ImageView ivPositionDelete;// 位置图删除按钮

	@ViewInject(R.id.iv_scene_background_delete)
	private ImageView ivBackgroundDelete;// 背景图删除按钮

	public AttachmentView getSceneAttachment() {
		return sceneAttachment;
	}

	public void setSceneAttachment(AttachmentView sceneAttachment) {
		this.sceneAttachment = sceneAttachment;
	}

	public ImageView getIvPostionPhoto() {
		return ivPostionPhoto;
	}

	public void setIvPostionPhoto(ImageView ivPostionPhoto) {
		this.ivPostionPhoto = ivPostionPhoto;
	}

	public ImageView getIvBackgroundPhoto() {
		return ivBackgroundPhoto;
	}

	public void setIvBackgroundPhoto(ImageView ivBackgroundPhoto) {
		this.ivBackgroundPhoto = ivBackgroundPhoto;
	}

	public ImageView getIvPositionDelete() {
		return ivPositionDelete;
	}

	public void setIvPositionDelete(ImageView ivPositionDelete) {
		this.ivPositionDelete = ivPositionDelete;
	}

	public ImageView getIvBackgroundDelete() {
		return ivBackgroundDelete;
	}

	public void setIvBackgroundDelete(ImageView ivBackgroundDelete) {
		this.ivBackgroundDelete = ivBackgroundDelete;
	}

}
