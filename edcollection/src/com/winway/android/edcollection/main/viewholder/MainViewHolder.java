package com.winway.android.edcollection.main.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;

import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.R;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 主界面ui初始化
 * 
 * @author zgq
 *
 */
public class MainViewHolder extends BaseViewHolder {
	@ViewInject(R.id.rl_main_content)
	private RelativeLayout rlMainContent;
	@ViewInject(R.id.include_main_bottom)
	private View include;
	@ViewInject(R.id.rl_bottom_new)
	// 新增
	private RelativeLayout rlNew;
	@ViewInject(R.id.imgBtn_bottom_new)
	private ImageButton imgBtnNew;
	@ViewInject(R.id.tv_bottom_new)
	private TextView tvNew;
	@ViewInject(R.id.iv_bottom_selectCollectObj)
	private ImageView ivSelectCollectObjNew;
	// 关联
	@ViewInject(R.id.ll_bottom_link)
	private LinearLayout llLink;
	@ViewInject(R.id.imgBtn_bottom_link)
	private ImageButton imgBtnLink;
	@ViewInject(R.id.tv_bottom_link)
	private TextView tvLink;
	// 已采
	@ViewInject(R.id.ll_bottom_collected)
	private LinearLayout llCollected;
	@ViewInject(R.id.imgBtn_bottom_collected)
	private ImageButton imgBtnCollected;
	@ViewInject(R.id.tv_bottom_collected)
	private TextView tvCollected;
	// 设置
	@ViewInject(R.id.ll_bottom_setting)
	private LinearLayout llSetting;
	@ViewInject(R.id.imgBtn_bottom_setting)
	private ImageButton imgBtnSetting;
	@ViewInject(R.id.tv_bottom_setting)
	private TextView tvSetting;
	// 任务列表
	@ViewInject(R.id.ll_bottom_tasklist)
	private LinearLayout llTasklist;
	@ViewInject(R.id.imgBtn_bottom_tasklist)
	private ImageButton imgBtnTasklist;
	@ViewInject(R.id.tv_bottom_tasklist)
	private TextView tvTasklist;

	// 分割线
	@ViewInject(R.id.view_main_line)
	private View viewLine;

	public LinearLayout getLlTasklist() {
		return llTasklist;
	}

	public void setLlTasklist(LinearLayout llTasklist) {
		this.llTasklist = llTasklist;
	}

	public ImageButton getImgBtnTasklist() {
		return imgBtnTasklist;
	}

	public void setImgBtnTasklist(ImageButton imgBtnTasklist) {
		this.imgBtnTasklist = imgBtnTasklist;
	}

	public TextView getTvTasklist() {
		return tvTasklist;
	}

	public void setTvTasklist(TextView tvTasklist) {
		this.tvTasklist = tvTasklist;
	}

	public View getViewLine() {
		return viewLine;
	}

	public void setViewLine(View viewLine) {
		this.viewLine = viewLine;
	}

	public RelativeLayout getRlMainContent() {
		return rlMainContent;
	}

	public void setRlMainContent(RelativeLayout rlMainContent) {
		this.rlMainContent = rlMainContent;
	}

	public View getInclude() {
		return include;
	}

	public void setInclude(View include) {
		this.include = include;
	}

	public RelativeLayout getRlNew() {
		return rlNew;
	}

	public void setRlNew(RelativeLayout rlNew) {
		this.rlNew = rlNew;
	}

	public ImageButton getImgBtnNew() {
		return imgBtnNew;
	}

	public void setImgBtnNew(ImageButton imgBtnNew) {
		this.imgBtnNew = imgBtnNew;
	}

	public TextView getTvNew() {
		return tvNew;
	}

	public void setTvNew(TextView tvNew) {
		this.tvNew = tvNew;
	}

	public ImageView getIvSelectCollectObjNew() {
		return ivSelectCollectObjNew;
	}

	public void setIvSelectCollectObjNew(ImageView ivSelectCollectObjNew) {
		this.ivSelectCollectObjNew = ivSelectCollectObjNew;
	}

	public LinearLayout getLlLink() {
		return llLink;
	}

	public void setLlLink(LinearLayout llLink) {
		this.llLink = llLink;
	}

	public ImageButton getImgBtnLink() {
		return imgBtnLink;
	}

	public void setImgBtnLink(ImageButton imgBtnLink) {
		this.imgBtnLink = imgBtnLink;
	}

	public TextView getTvLink() {
		return tvLink;
	}

	public void setTvLink(TextView tvLink) {
		this.tvLink = tvLink;
	}

	public LinearLayout getLlCollected() {
		return llCollected;
	}

	public void setLlCollected(LinearLayout llCollected) {
		this.llCollected = llCollected;
	}

	public ImageButton getImgBtnCollected() {
		return imgBtnCollected;
	}

	public void setImgBtnCollected(ImageButton imgBtnCollected) {
		this.imgBtnCollected = imgBtnCollected;
	}

	public TextView getTvCollected() {
		return tvCollected;
	}

	public void setTvCollected(TextView tvCollected) {
		this.tvCollected = tvCollected;
	}

	public LinearLayout getLlSetting() {
		return llSetting;
	}

	public void setLlSetting(LinearLayout llSetting) {
		this.llSetting = llSetting;
	}

	public ImageButton getImgBtnSetting() {
		return imgBtnSetting;
	}

	public void setImgBtnSetting(ImageButton imgBtnSetting) {
		this.imgBtnSetting = imgBtnSetting;
	}

	public TextView getTvSetting() {
		return tvSetting;
	}

	public void setTvSetting(TextView tvSetting) {
		this.tvSetting = tvSetting;
	}

}
