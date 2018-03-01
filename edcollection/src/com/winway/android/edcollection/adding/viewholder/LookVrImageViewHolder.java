package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 查看全景图片
 * 
 * @author lyh
 * @version 创建时间：2017年5月19日
 *
 */
public class LookVrImageViewHolder extends BaseViewHolder {

	@ViewInject(R.id.btn_layout_vr_image_show_up)
	// 上一张
	private Button btnUp;

	@ViewInject(R.id.btn_layout_vr_image_show_down)
	// 下一张
	private Button btnDown;

	@ViewInject(R.id.iv_vr_image_close)
	// 关闭图标
	private ImageView ivClose;

	// @ViewInject(R.id.ll_vr_image_show)
	// private LinearLayout llShow;

	@ViewInject(R.id.rl_vr_image_container)
	private RelativeLayout rlContainer;

	public RelativeLayout getRlContainer() {
		return rlContainer;
	}

	public void setRlContainer(RelativeLayout rlContainer) {
		this.rlContainer = rlContainer;
	}

	public Button getBtnUp() {
		return btnUp;
	}

	public void setBtnUp(Button btnUp) {
		this.btnUp = btnUp;
	}

	public Button getBtnDown() {
		return btnDown;
	}

	public void setBtnDown(Button btnDown) {
		this.btnDown = btnDown;
	}

	public ImageView getIvClose() {
		return ivClose;
	}

	public void setIvClose(ImageView ivClose) {
		this.ivClose = ivClose;
	}

}
