package com.winway.android.edcollection.adding.viewholder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

/**
 * 标识器采集-基本信息ui初始化
 * 
 * @author lyh
 * @version 创建时间：2016年12月14日 上午9:38:56
 * 
 */
public class BasicInfoViewHolder extends BaseViewHolder {

	@ViewInject(R.id.baseTabScrollView)
	private ScrollView scrollViewContent;// 属性容器view

	@ViewInject(R.id.rg_marker_basic_type)
	private RadioGroup rgMarkerType;
	
	@ViewInject(R.id.btn_marker_basicinfo_receive)
	private Button btnReceive;// 接收

	@ViewInject(R.id.btn_marker_basicinfo_map)
	private Button btnMap;// 地图

	@ViewInject(R.id.btn_marker_basicinfo_rtk)
	private Button btnRtk;// rtk

	@ViewInject(R.id.btn_marker_basicinfo_networking)
	private Button btnNetworking;// 联网获取

	@ViewInject(R.id.inCon_marker_basic_marker_id)
	private InputComponent icMarkerId;// 标识器id号

	@ViewInject(R.id.inCon_marker_basic_longitude)
	private InputComponent icLongitude;// 经度

	@ViewInject(R.id.inCon_marker_basic_latitude)
	private InputComponent icLatitude;// 纬度

	@ViewInject(R.id.inCon_marker_basic_coordNo)
	private InputComponent icCoordNo;// 坐标编号

	@ViewInject(R.id.inSelCom_marker_basic_install_path)
	private InputSelectComponent iscInstallPath;// 安装位置

	@ViewInject(R.id.inCon_marker_basic_geography_pos)
	private InputComponent icGeographyPos;// 地理位置

	@ViewInject(R.id.inCon_marker_basic_width)
	private InputComponent icWidth;// 管块尺寸

	@ViewInject(R.id.inCon_marker_basic_floot_height)
	private InputComponent icFlootHeight;// 沟底到地面高度

	@ViewInject(R.id.inCon_marker_basic_altitude)
	private InputComponent icAltitude;// 高程

	// @ViewInject(R.id.inCon_marker_basic_marker_height)
	// private InputComponent icMarkerHeight;// 标识器到地面高度

	@ViewInject(R.id.inSelCom_marker_basic_describe)
	private InputSelectComponent iscDescribe;// 设备描述

	@ViewInject(R.id.inSelCom_marker_basic_other)
	private InputSelectComponent iscOther;// 其他

	@ViewInject(R.id.ll_marker_basic_feature)
	private LinearLayout llFeature; // 特征点布局

	@ViewInject(R.id.iv_marker_basic_more)
	private ImageView ivMore; // 特征点下拉图标

	@ViewInject(R.id.lv_marker_basic_featurePoint)
	private ListView listViewFeaturePoint;// 特征点

//	@ViewInject(R.id.isc_marker_basic_layType)
//	private InputSelectComponent iscLayType;// 敷设类型

	public InputComponent getIcAltitude() {
		return icAltitude;
	}

	public void setIcAltitude(InputComponent icAltitude) {
		this.icAltitude = icAltitude;
	}

	public ScrollView getScrollViewContent() {
		return scrollViewContent;
	}

	public void setScrollViewContent(ScrollView scrollViewContent) {
		this.scrollViewContent = scrollViewContent;
	}

	public LinearLayout getLlFeature() {
		return llFeature;
	}

	public void setLlFeature(LinearLayout llFeature) {
		this.llFeature = llFeature;
	}

	public ImageView getIvMore() {
		return ivMore;
	}

	public void setIvMore(ImageView ivMore) {
		this.ivMore = ivMore;
	}

	public InputComponent getIcCoordNo() {
		return icCoordNo;
	}

	public void setIcCoordNo(InputComponent icCoordNo) {
		this.icCoordNo = icCoordNo;
	}

	public RadioGroup getRgMarkerType() {
		return rgMarkerType;
	}

	public void setRgMarkerType(RadioGroup rgMarkerType) {
		this.rgMarkerType = rgMarkerType;
	}

	public Button getBtnReceive() {
		return btnReceive;
	}

	public void setBtnReceive(Button btnReceive) {
		this.btnReceive = btnReceive;
	}

	public Button getBtnRtk() {
		return btnRtk;
	}

	public void setBtnRtk(Button btnRtk) {
		this.btnRtk = btnRtk;
	}

	public Button getBtnMap() {
		return btnMap;
	}

	public void setBtnMap(Button btnMap) {
		this.btnMap = btnMap;
	}

	public Button getBtnNetworking() {
		return btnNetworking;
	}

	public void setBtnNetworking(Button btnNetworking) {
		this.btnNetworking = btnNetworking;
	}

	public InputComponent getIcMarkerId() {
		return icMarkerId;
	}

	public void setIcMarkerId(InputComponent icMarkerId) {
		this.icMarkerId = icMarkerId;
	}

	public InputComponent getIcLongitude() {
		return icLongitude;
	}

	public void setIcLongitude(InputComponent icLongitude) {
		this.icLongitude = icLongitude;
	}

	public InputComponent getIcLatitude() {
		return icLatitude;
	}

	public void setIcLatitude(InputComponent icLatitude) {
		this.icLatitude = icLatitude;
	}

	public InputSelectComponent getIscInstallPath() {
		return iscInstallPath;
	}

	public void setIscInstallPath(InputSelectComponent iscInstallPath) {
		this.iscInstallPath = iscInstallPath;
	}

	public InputComponent getIcGeographyPos() {
		return icGeographyPos;
	}

	public void setIcGeographyPos(InputComponent icGeographyPos) {
		this.icGeographyPos = icGeographyPos;
	}

	public InputComponent getIcWidth() {
		return icWidth;
	}

	public void setIcWidth(InputComponent icWidth) {
		this.icWidth = icWidth;
	}

	public InputComponent getIcFlootHeight() {
		return icFlootHeight;
	}

	public void setIcFlootHeight(InputComponent icFlootHeight) {
		this.icFlootHeight = icFlootHeight;
	}

	public InputSelectComponent getIscDescribe() {
		return iscDescribe;
	}

	public void setIscDescribe(InputSelectComponent iscDescribe) {
		this.iscDescribe = iscDescribe;
	}

	public InputSelectComponent getIscOther() {
		return iscOther;
	}

	public void setIscOther(InputSelectComponent iscOther) {
		this.iscOther = iscOther;
	}

	public ListView getListViewFeaturePoint() {
		return listViewFeaturePoint;
	}

	public void setListViewFeaturePoint(ListView listViewFeaturePoint) {
		this.listViewFeaturePoint = listViewFeaturePoint;
	}

}
