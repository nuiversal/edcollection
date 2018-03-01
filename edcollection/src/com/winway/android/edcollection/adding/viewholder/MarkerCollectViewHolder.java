package com.winway.android.edcollection.adding.viewholder;

import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.noscrollviewpager.NoScrollViewPager;

/**
 * 采集信息界面ui初始化
 * 
 * @author lyh
 * @version 创建时间：2016年12月12日 上午11:11:50
 * 
 */
public class MarkerCollectViewHolder extends BaseViewHolder {
	@ViewInject(R.id.vp_marker_collect_viewpage)
	private NoScrollViewPager vpmarkMainVp;

	// 基本信息
	@ViewInject(R.id.ll_mark_tab_basic_info)
	private LinearLayout llBasicInfo;
	@ViewInject(R.id.tv_mark_tab_basic_info)
	private TextView tvBasicInfo;

	// 通道信息
	@ViewInject(R.id.ll_mark_tab_channel_info)
	private LinearLayout llChannelInfo;
	@ViewInject(R.id.tv_mark_tab_channel_info)
	private TextView tvChannelInfo;

	// 设备信息
	@ViewInject(R.id.ll_mark_tab_device_info)
	private LinearLayout llDeviceInfo;
	@ViewInject(R.id.tv_mark_tab_device_info)
	private TextView tvDeviceInfo;

	// 现场照片
	@ViewInject(R.id.ll_mark_tab_scene_photo)
	private LinearLayout llScenePhoto;
	@ViewInject(R.id.tv_mark_tab_scene_photo)
	private TextView tvScenePhoto;

	// 通道节点现状
	@ViewInject(R.id.ll_mark_tab_channelnodeStatus)
	private LinearLayout llChannelnodeStatus;
	@ViewInject(R.id.tv_mark_tab_channelnodeStatus)
	private TextView tvChannelnodeStatus;

	// 标题返回功能
	@ViewInject(R.id.hc_marker_collect_head)
	private HeadComponent hcHead;

	// 同上一标识信息
	@ViewInject(R.id.btn_marker_collect_sameinfo)
	private Button btnSameInfo;

	// 保存
	@ViewInject(R.id.btn_marker_collect_save)
	private Button btnSave;

	public LinearLayout getLlChannelInfo() {
		return llChannelInfo;
	}

	public void setLlChannelInfo(LinearLayout llChannelInfo) {
		this.llChannelInfo = llChannelInfo;
	}

	public TextView getTvChannelInfo() {
		return tvChannelInfo;
	}

	public void setTvChannelInfo(TextView tvChannelInfo) {
		this.tvChannelInfo = tvChannelInfo;
	}

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public Button getBtnSameInfo() {
		return btnSameInfo;
	}

	public void setBtnSameInfo(Button btnSameInfo) {
		this.btnSameInfo = btnSameInfo;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public NoScrollViewPager getVpmarkMainVp() {
		return vpmarkMainVp;
	}

	public void setVpmarkMainVp(NoScrollViewPager vpmarkMainVp) {
		this.vpmarkMainVp = vpmarkMainVp;
	}

	public LinearLayout getLlBasicInfo() {
		return llBasicInfo;
	}

	public void setLlBasicInfo(LinearLayout llBasicInfo) {
		this.llBasicInfo = llBasicInfo;
	}

	public LinearLayout getLlDeviceInfo() {
		return llDeviceInfo;
	}

	public void setLlDeviceInfo(LinearLayout llDeviceInfo) {
		this.llDeviceInfo = llDeviceInfo;
	}

	public LinearLayout getLlScenePhoto() {
		return llScenePhoto;
	}

	public void setLlScenePhoto(LinearLayout llScenePhoto) {
		this.llScenePhoto = llScenePhoto;
	}

	public TextView getTvBasicInfo() {
		return tvBasicInfo;
	}

	public void setTvBasicInfo(TextView tvBasicInfo) {
		this.tvBasicInfo = tvBasicInfo;
	}

	public TextView getTvDeviceInfo() {
		return tvDeviceInfo;
	}

	public void setTvDeviceInfo(TextView tvDeviceInfo) {
		this.tvDeviceInfo = tvDeviceInfo;
	}

	public TextView getTvScenePhoto() {
		return tvScenePhoto;
	}

	public void setTvScenePhoto(TextView tvScenePhoto) {
		this.tvScenePhoto = tvScenePhoto;
	}

	public LinearLayout getLlChannelnodeStatus() {
		return llChannelnodeStatus;
	}

	public void setLlChannelnodeStatus(LinearLayout llChannelnodeStatus) {
		this.llChannelnodeStatus = llChannelnodeStatus;
	}

	public TextView getTvChannelnodeStatus() {
		return tvChannelnodeStatus;
	}

	public void setTvChannelnodeStatus(TextView tvChannelnodeStatus) {
		this.tvChannelnodeStatus = tvChannelnodeStatus;
	}

}
