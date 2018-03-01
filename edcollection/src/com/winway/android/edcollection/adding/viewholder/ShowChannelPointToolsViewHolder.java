package com.winway.android.edcollection.adding.viewholder;

import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;

/**
 * @author lyh
 * @version 创建时间：2017年4月20日
 *
 */
public class ShowChannelPointToolsViewHolder {
	@ViewInject(R.id.tv_layout_menu_wgfz)
	private TextView tvWgfz; // 网格辅助-空管

	@ViewInject(R.id.tv_layout_menu_wgfz_unknow_line)
	private TextView tvUnknowLine; // 网格辅助-未知线路

	@ViewInject(R.id.tv_layout_menu_zjzj)
	private TextView tvZjzj; // 增加支架

	@ViewInject(R.id.tv_layout_menu_qxzj)
	private TextView tvQxzj; // 取消支架

	@ViewInject(R.id.tv_layout_menu_lp)
	private TextView openLp; // 打开罗盘

	@ViewInject(R.id.tv_layout_menu_jm_bg_image)
	private TextView jmBgImg; // 截面背景图

	@ViewInject(R.id.tv_layout_menu_vr_bg_image)
	private TextView vrBgImg; // VR背景图

	@ViewInject(R.id.tv_layout_menu_vr_ps_image)
	private TextView vrPsImg; // VR前景图

	@ViewInject(R.id.tv_layout_menu_look_vr_bg_image)
	private TextView lookVrBgImg; // 查看VR背景景图

	@ViewInject(R.id.tv_layout_menu_look_vr_ps_image)
	private TextView lookVrPsImg; // 查看VR前景图

	@ViewInject(R.id.rg_activity_channel_planing_operation)
	private RadioGroup rgOperation; // 单选容器

	@ViewInject(R.id.iv_layout_menu_close)
	private ImageView ivClose;// 关闭

	@ViewInject(R.id.iv_layout_menu_fold)
	private ImageView ivFold;// 折叠
	
	@ViewInject(R.id.tv_layout_menu_jm_history_image)
	private TextView tvHistroyImg;// 历史截面

	public TextView getTvUnknowLine() {
		return tvUnknowLine;
	}

	public void setTvUnknowLine(TextView tvUnknowLine) {
		this.tvUnknowLine = tvUnknowLine;
	}

	public ImageView getIvFold() {
		return ivFold;
	}

	public void setIvFold(ImageView ivFold) {
		this.ivFold = ivFold;
	}

	public TextView getTvQxzj() {
		return tvQxzj;
	}

	public void setTvQxzj(TextView tvQxzj) {
		this.tvQxzj = tvQxzj;
	}

	public RadioGroup getRgOperation() {
		return rgOperation;
	}

	public void setRgOperation(RadioGroup rgOperation) {
		this.rgOperation = rgOperation;
	}

	public TextView getOpenLp() {
		return openLp;
	}

	public void setOpenLp(TextView openLp) {
		this.openLp = openLp;
	}

	public TextView getTvWgfz() {
		return tvWgfz;
	}

	public void setTvWgfz(TextView tvWgfz) {
		this.tvWgfz = tvWgfz;
	}

	public TextView getTvZjzj() {
		return tvZjzj;
	}

	public void setTvZjzj(TextView tvZjzj) {
		this.tvZjzj = tvZjzj;
	}

	public ImageView getIvClose() {
		return ivClose;
	}

	public void setIvClose(ImageView ivClose) {
		this.ivClose = ivClose;
	}

	public TextView getJmBgImg() {
		return jmBgImg;
	}

	public void setJmBgImg(TextView jmBgImg) {
		this.jmBgImg = jmBgImg;
	}

	public TextView getVrBgImg() {
		return vrBgImg;
	}

	public void setVrBgImg(TextView vrBgImg) {
		this.vrBgImg = vrBgImg;
	}

	public TextView getVrPsImg() {
		return vrPsImg;
	}

	public void setVrPsImg(TextView vrPsImg) {
		this.vrPsImg = vrPsImg;
	}

	public TextView getLookVrBgImg() {
		return lookVrBgImg;
	}

	public void setLookVrBgImg(TextView lookVrBgImg) {
		this.lookVrBgImg = lookVrBgImg;
	}

	public TextView getLookVrPsImg() {
		return lookVrPsImg;
	}

	public void setLookVrPsImg(TextView lookVrPsImg) {
		this.lookVrPsImg = lookVrPsImg;
	}

	public TextView getTvHistroyImg() {
		return tvHistroyImg;
	}

	public void setTvHistroyImg(TextView tvHistroyImg) {
		this.tvHistroyImg = tvHistroyImg;
	}
	
}
