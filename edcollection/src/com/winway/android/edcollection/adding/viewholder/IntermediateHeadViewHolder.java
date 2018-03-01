package com.winway.android.edcollection.adding.viewholder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;
import com.winway.android.ewidgets.attachment.AttachmentView;

/**
 * 电缆中间接头
 * 
 * @author ly
 *
 */
public class IntermediateHeadViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_intermediate_head_head)
	private HeadComponent hcHead;// head组件

	@ViewInject(R.id.inCon_intermediate_head_equipmentName)
	private InputComponent inconEquipmentName;// 设备名称

	@ViewInject(R.id.insc_intermediate_head_layType)
	private InputSelectComponent inscLaytype;// 敷设方式

	@ViewInject(R.id.inCon_intermediate_head_unitType)
	private InputComponent inconUnitType;// 设备型号

	@ViewInject(R.id.insc_intermediate_head_feature)
	private InputSelectComponent inscFeature;// 工艺特征

	@ViewInject(R.id.inCon_intermediate_head_factory)
	private InputComponent inconFactory;// 生产厂家

	@ViewInject(R.id.inCon_intermediate_head_crateDate)
	private InputComponent inconCateDate;// 生产日期

	@ViewInject(R.id.inCon_intermediate_head_operationDate)
	private InputComponent inconeOperationDate;// 投运日期

	@ViewInject(R.id.inCon_intermediate_head_install)
	private InputComponent inconeInstall;// 安装单位

	@ViewInject(R.id.inCon_intermediate_head_worker)
	private InputComponent inconeWorker;// 施工员

	@ViewInject(R.id.insc_intermediate_head_type)
	private InputSelectComponent inscType;// 类型

	@ViewInject(R.id.inCon_intermediate_head_phone)
	private InputComponent inconePhone;// 厂家联系方式

	@ViewInject(R.id.inCon_intermediate_head_fault)
	private InputComponent inconeFault;// 中间接头故障情况

	@ViewInject(R.id.inCon_intermediate_head_patrolDate)
	private InputComponent inconePatrolDate;// 上次巡检日期

	@ViewInject(R.id.inCon_intermediate_head_ampacity)
	private InputComponent inconeAmpacity;// 电缆载流量

	@ViewInject(R.id.inCon_intermediate_head_temperature)
	private InputComponent inconeTemperature;// 中间接头温度

	@ViewInject(R.id.inCon_intermediate_head_groundCurrent)
	private InputComponent inconeGroundCurrent;// 接地电流情况

	@ViewInject(R.id.av_zjjt_attachment)
	private AttachmentView avAttachment;// 拍照组件

	@ViewInject(R.id.iv_link_device_enter)
	private ImageView ivLinkDeviceEnter;// 关联

	@ViewInject(R.id.btn_link_device_link)
	private Button btnLinkDeviceLink;// 关联

	@ViewInject(R.id.btn_link_label)
	private Button btnLinkLabelLink; // 标签采集

	@ViewInject(R.id.rl_link_device)
	private RelativeLayout rlLinkDevice;// 关联布局

	public Button getBtnLinkLabelLink() {
		return btnLinkLabelLink;
	}

	public void setBtnLinkLabelLink(Button btnLinkLabelLink) {
		this.btnLinkLabelLink = btnLinkLabelLink;
	}

	public InputSelectComponent getInscType() {
		return inscType;
	}

	public void setInscType(InputSelectComponent inscType) {
		this.inscType = inscType;
	}

	public InputComponent getInconePhone() {
		return inconePhone;
	}

	public void setInconePhone(InputComponent inconePhone) {
		this.inconePhone = inconePhone;
	}

	public InputComponent getInconeFault() {
		return inconeFault;
	}

	public void setInconeFault(InputComponent inconeFault) {
		this.inconeFault = inconeFault;
	}

	public InputComponent getInconePatrolDate() {
		return inconePatrolDate;
	}

	public void setInconePatrolDate(InputComponent inconePatrolDate) {
		this.inconePatrolDate = inconePatrolDate;
	}

	public InputComponent getInconeAmpacity() {
		return inconeAmpacity;
	}

	public void setInconeAmpacity(InputComponent inconeAmpacity) {
		this.inconeAmpacity = inconeAmpacity;
	}

	public InputComponent getInconeTemperature() {
		return inconeTemperature;
	}

	public void setInconeTemperature(InputComponent inconeTemperature) {
		this.inconeTemperature = inconeTemperature;
	}

	public InputComponent getInconeGroundCurrent() {
		return inconeGroundCurrent;
	}

	public void setInconeGroundCurrent(InputComponent inconeGroundCurrent) {
		this.inconeGroundCurrent = inconeGroundCurrent;
	}

	public RelativeLayout getRlLinkDevice() {
		return rlLinkDevice;
	}

	public void setRlLinkDevice(RelativeLayout rlLinkDevice) {
		this.rlLinkDevice = rlLinkDevice;
	}

	public ImageView getIvLinkDeviceEnter() {
		return ivLinkDeviceEnter;
	}

	public void setIvLinkDeviceEnter(ImageView ivLinkDeviceEnter) {
		this.ivLinkDeviceEnter = ivLinkDeviceEnter;
	}

	public Button getBtnLinkDeviceLink() {
		return btnLinkDeviceLink;
	}

	public void setBtnLinkDeviceLink(Button btnLinkDeviceLink) {
		this.btnLinkDeviceLink = btnLinkDeviceLink;
	}

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public InputComponent getInconEquipmentName() {
		return inconEquipmentName;
	}

	public void setInconEquipmentName(InputComponent inconEquipmentName) {
		this.inconEquipmentName = inconEquipmentName;
	}

	public InputComponent getInconUnitType() {
		return inconUnitType;
	}

	public void setInconUnitType(InputComponent inconUnitType) {
		this.inconUnitType = inconUnitType;
	}

	public InputSelectComponent getInscLaytype() {
		return inscLaytype;
	}

	public void setInscLaytype(InputSelectComponent inscLaytype) {
		this.inscLaytype = inscLaytype;
	}

	public InputSelectComponent getInscFeature() {
		return inscFeature;
	}

	public void setInscFeature(InputSelectComponent inscFeature) {
		this.inscFeature = inscFeature;
	}

	public InputComponent getInconFactory() {
		return inconFactory;
	}

	public void setInconFactory(InputComponent inconFactory) {
		this.inconFactory = inconFactory;
	}

	public InputComponent getInconCateDate() {
		return inconCateDate;
	}

	public void setInconCateDate(InputComponent inconCateDate) {
		this.inconCateDate = inconCateDate;
	}

	public InputComponent getInconeOperationDate() {
		return inconeOperationDate;
	}

	public void setInconeOperationDate(InputComponent inconeOperationDate) {
		this.inconeOperationDate = inconeOperationDate;
	}

	public InputComponent getInconeInstall() {
		return inconeInstall;
	}

	public void setInconeInstall(InputComponent inconeInstall) {
		this.inconeInstall = inconeInstall;
	}

	public InputComponent getInconeWorker() {
		return inconeWorker;
	}

	public void setInconeWorker(InputComponent inconeWorker) {
		this.inconeWorker = inconeWorker;
	}

	public AttachmentView getAvAttachment() {
		return avAttachment;
	}

	public void setAvAttachment(AttachmentView avAttachment) {
		this.avAttachment = avAttachment;
	}

}
