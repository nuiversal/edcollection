package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.customview.CompassView;
import com.winway.android.edcollection.adding.customview.GJDrawView;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChannelPlanningViewHolder extends BaseViewHolder {
	// 画板
	@ViewInject(R.id.activity_channel_planning_gjdrawview)
	private GJDrawView drawView;

	@ViewInject(R.id.iv_activity_channel_planing_down)
	private ImageView IvDown;

	@ViewInject(R.id.insc_activity_channel_planing_size)
	private InputSelectComponent inscSize; // 截面大小

	@ViewInject(R.id.incon_activity_channel_planing_sstd)
	private InputComponent inconSstd; // 所属通道

	@ViewInject(R.id.incon_activity_channel_planing_jmmc)
	private InputComponent inconJmmc; // 截面名称

	@ViewInject(R.id.incon_activity_channel_planing_jmjd)
	private InputComponent inconJmjd; // 截面角度

	@ViewInject(R.id.activity_channel_planing_compass_container)
	private View compassContainer;// 罗盘容器
	@ViewInject(R.id.activity_channel_planing_compass_view)
	private CompassView compassView;// 罗盘
	@ViewInject(R.id.activity_channel_planing_compass_degress)
	private TextView compassDgressTV;// 罗盘度数
	@ViewInject(R.id.activity_channel_planing_compass_sure)
	private Button compassSureBT;

	@ViewInject(R.id.activity_channel_planing_mini_drawview_container)
	private LinearLayout miniDrawViewContainer;// 迷你画板容器

	@ViewInject(R.id.activity_channel_planing_add_jm)
	private Button addJmBT;// 添加截面
	@ViewInject(R.id.activity_channel_planing_save_data)
	private Button saveDataBT;// 保存数据

	@ViewInject(R.id.menu_container)
	private View menuContainer;

	public ImageView getIvDown() {
		return IvDown;
	}

	public void setIvDown(ImageView ivDown) {
		IvDown = ivDown;
	}

	public InputSelectComponent getInscSize() {
		return inscSize;
	}

	public void setInscSize(InputSelectComponent inscSize) {
		this.inscSize = inscSize;
	}

	public InputComponent getInconSstd() {
		return inconSstd;
	}

	public void setInconSstd(InputComponent inconSstd) {
		this.inconSstd = inconSstd;
	}

	public InputComponent getInconJmmc() {
		return inconJmmc;
	}

	public void setInconJmmc(InputComponent inconJmmc) {
		this.inconJmmc = inconJmmc;
	}

	public InputComponent getInconJmjd() {
		return inconJmjd;
	}

	public void setInconJmjd(InputComponent inconJmjd) {
		this.inconJmjd = inconJmjd;
	}

	public GJDrawView getDrawView() {
		return drawView;
	}

	public void setDrawView(GJDrawView drawView) {
		this.drawView = drawView;
	}

	public View getCompassContainer() {
		return compassContainer;
	}

	public void setCompassContainer(View compassContainer) {
		this.compassContainer = compassContainer;
	}

	public CompassView getCompassView() {
		return compassView;
	}

	public void setCompassView(CompassView compassView) {
		this.compassView = compassView;
	}

	public TextView getCompassDgressTV() {
		return compassDgressTV;
	}

	public void setCompassDgressTV(TextView compassDgressTV) {
		this.compassDgressTV = compassDgressTV;
	}

	public Button getCompassSureBT() {
		return compassSureBT;
	}

	public void setCompassSureBT(Button compassSureBT) {
		this.compassSureBT = compassSureBT;
	}

	public LinearLayout getMiniDrawViewContainer() {
		return miniDrawViewContainer;
	}

	public void setMiniDrawViewContainer(LinearLayout miniDrawViewContainer) {
		this.miniDrawViewContainer = miniDrawViewContainer;
	}

	public Button getAddJmBT() {
		return addJmBT;
	}

	public void setAddJmBT(Button addJmBT) {
		this.addJmBT = addJmBT;
	}

	public Button getSaveDataBT() {
		return saveDataBT;
	}

	public void setSaveDataBT(Button saveDataBT) {
		this.saveDataBT = saveDataBT;
	}

	public View getMenuContainer() {
		return menuContainer;
	}

	public void setMenuContainer(View menuContainer) {
		this.menuContainer = menuContainer;
	}

}
