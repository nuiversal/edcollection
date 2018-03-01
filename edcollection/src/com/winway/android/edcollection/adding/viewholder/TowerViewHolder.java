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
 * 杆塔
 * 
 * @author
 *
 */
public class TowerViewHolder extends BaseViewHolder {

	@ViewInject(R.id.hc_tower_head)
	private HeadComponent hcHead;// head组件

	@ViewInject(R.id.inCon_tower_towerno)
	private InputComponent inconTowerNo;// 杆塔编号

	@ViewInject(R.id.inCon_tower_maintenance_department)
	private InputComponent inconmaintenanceDepartment;// 维护部门

	@ViewInject(R.id.inCon_tower_running_status)
	private InputComponent inconRunningStatus;// 运行状态

	@ViewInject(R.id.inCon_tower_property_rights)
	private InputComponent inconPropertyRights;// 产权性质

	@ViewInject(R.id.inCon_tower_commissioning_date)
	private InputComponent inconCommissioningDate;// 投运日期

	@ViewInject(R.id.inCon_tower_manufacturer)
	private InputComponent inconManufacturer;// 生产厂家

	@ViewInject(R.id.inCon_tower_equipment_model)
	private InputSelectComponent inscEquipmentModel;// 设备型号

	@ViewInject(R.id.inCon_tower_manufacturing_num)
	private InputComponent inconManufacturingNum;// 出厂编号

	@ViewInject(R.id.inCon_tower_accomplish_date)
	private InputComponent inconaccomplishDate;// 出厂日期

	@ViewInject(R.id.insc_tower_material)
	private InputSelectComponent inscMaterial;// 杆塔材质

	@ViewInject(R.id.inCon_tower_corner_degree)
	private InputComponent inconCornerDegree;// 转角度数

	@ViewInject(R.id.inCon_tower_height)
	private InputComponent inconHeight;// 杆塔全高

	@ViewInject(R.id.inCon_tower_remarks)
	private InputComponent inconRemarks;// 备注

	@ViewInject(R.id.av_tower_attachment)
	private AttachmentView avAttachment;// 附件

	@ViewInject(R.id.iv_link_device_enter)
	private ImageView ivLinkDeviceEnter;// 关联

	@ViewInject(R.id.btn_link_device_link)
	private Button btnLinkDeviceLink;// 关联
	
	@ViewInject(R.id.rl_link_device)
	private RelativeLayout rlLinkDevice;// 关联布局

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

	public AttachmentView getAvAttachment() {
		return avAttachment;
	}

	public void setAvAttachment(AttachmentView avAttachment) {
		this.avAttachment = avAttachment;
	}

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public InputComponent getInconTowerNo() {
		return inconTowerNo;
	}

	public void setInconTowerNo(InputComponent inconTowerNo) {
		this.inconTowerNo = inconTowerNo;
	}

	public InputComponent getInconmaintenanceDepartment() {
		return inconmaintenanceDepartment;
	}

	public void setInconmaintenanceDepartment(
			InputComponent inconmaintenanceDepartment) {
		this.inconmaintenanceDepartment = inconmaintenanceDepartment;
	}

	public InputComponent getInconRunningStatus() {
		return inconRunningStatus;
	}

	public void setInconRunningStatus(InputComponent inconRunningStatus) {
		this.inconRunningStatus = inconRunningStatus;
	}

	public InputComponent getInconPropertyRights() {
		return inconPropertyRights;
	}

	public void setInconPropertyRights(InputComponent inconPropertyRights) {
		this.inconPropertyRights = inconPropertyRights;
	}

	public InputComponent getInconCommissioningDate() {
		return inconCommissioningDate;
	}

	public void setInconCommissioningDate(InputComponent inconCommissioningDate) {
		this.inconCommissioningDate = inconCommissioningDate;
	}

	public InputComponent getInconManufacturer() {
		return inconManufacturer;
	}

	public void setInconManufacturer(InputComponent inconManufacturer) {
		this.inconManufacturer = inconManufacturer;
	}

	public InputSelectComponent getInscEquipmentModel() {
		return inscEquipmentModel;
	}

	public void setInscEquipmentModel(InputSelectComponent inscEquipmentModel) {
		this.inscEquipmentModel = inscEquipmentModel;
	}

	public InputComponent getInconManufacturingNum() {
		return inconManufacturingNum;
	}

	public void setInconManufacturingNum(InputComponent inconManufacturingNum) {
		this.inconManufacturingNum = inconManufacturingNum;
	}

	public InputComponent getInconaccomplishDate() {
		return inconaccomplishDate;
	}

	public void setInconaccomplishDate(InputComponent inconaccomplishDate) {
		this.inconaccomplishDate = inconaccomplishDate;
	}

	public InputSelectComponent getInscMaterial() {
		return inscMaterial;
	}

	public void setInscMaterial(InputSelectComponent inscMaterial) {
		this.inscMaterial = inscMaterial;
	}

	public InputComponent getInconCornerDegree() {
		return inconCornerDegree;
	}

	public void setInconCornerDegree(InputComponent inconCornerDegree) {
		this.inconCornerDegree = inconCornerDegree;
	}

	public InputComponent getInconHeight() {
		return inconHeight;
	}

	public void setInconHeight(InputComponent inconHeight) {
		this.inconHeight = inconHeight;
	}

	public InputComponent getInconRemarks() {
		return inconRemarks;
	}

	public void setInconRemarks(InputComponent inconRemarks) {
		this.inconRemarks = inconRemarks;
	}

}
