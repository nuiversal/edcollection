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
 * 变压器
 * 
 * @author
 *
 */
public class TransformerViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_transformer_head)
	private HeadComponent hcHead;// head组件

	@ViewInject(R.id.inCon_transformer_deviceName)
	private InputComponent icDeviceName;// 设备名称

	@ViewInject(R.id.inCon_transformer_maintenanceDepartment)
	private InputComponent icMaintenanceDepartment;// 维护部门

	@ViewInject(R.id.inSelCom_transformer_voltage)
	private InputSelectComponent iscVoltage;// 电压等级

	@ViewInject(R.id.inCon_transformer_runningStatus)
	private InputComponent icRunningStatus;// 运行状态

	@ViewInject(R.id.inCon_transformer_stationHouse)
	private InputComponent icStationHouse;// 所属站房

	@ViewInject(R.id.inCon_transformer_space)
	private InputComponent icSpace;// 所属间隔

	@ViewInject(R.id.inCon_transformer_equipmentModel)
	private InputComponent icEquipmentModel;// 设备型号

	@ViewInject(R.id.inCon_transformer_ratedCapacity)
	private InputComponent icRatedCapacity;// 额定容量(KVA)

	@ViewInject(R.id.inCon_transformer_propertyRights)
	private InputComponent icPropertyRights;// 产权性质

	@ViewInject(R.id.inCon_transformer_commissioningDate)
	private InputComponent icCommissioningDate;// 投运日期

	@ViewInject(R.id.inCon_transformer_manufacturer)
	private InputComponent icManufacturer;// 生产厂家

	@ViewInject(R.id.inCon_transformer_manufacturingNum)
	private InputComponent icManufacturingNum;// 出厂编号

	@ViewInject(R.id.inCon_transformer_userImportantGrade)
	private InputComponent icUserImportantGrade;// 用户重要等级

	@ViewInject(R.id.inCon_transformer_agMark)
	private InputComponent icAgMark;// 专/公标志

	@ViewInject(R.id.inCon_transformer_customerName)
	private InputComponent icCustomerName;// 客户名称

	@ViewInject(R.id.inCon_transformer_customerNum)
	private InputComponent icCustomerNum;// 客户名称

	@ViewInject(R.id.inCon_transformer_noLoadCurrent)
	private InputComponent icNoLoadCurrent;// 空载电流(A)

	@ViewInject(R.id.inCon_transformer_noLoadLoss)
	private InputComponent icNoLoadLoss;// 空载损耗(KW)

	@ViewInject(R.id.inCon_transformer_loadLoss)
	private InputComponent icLoadLoss;// 负载损耗(KW)

	@ViewInject(R.id.inCon_transformer_remarks)
	private InputComponent icRemarks;// 备注

	@ViewInject(R.id.av_transformer_attachment)
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

	public InputComponent getIcDeviceName() {
		return icDeviceName;
	}

	public void setIcDeviceName(InputComponent icDeviceName) {
		this.icDeviceName = icDeviceName;
	}

	public InputComponent getIcMaintenanceDepartment() {
		return icMaintenanceDepartment;
	}

	public void setIcMaintenanceDepartment(InputComponent icMaintenanceDepartment) {
		this.icMaintenanceDepartment = icMaintenanceDepartment;
	}

	public InputSelectComponent getIscVoltage() {
		return iscVoltage;
	}

	public void setIscVoltage(InputSelectComponent iscVoltage) {
		this.iscVoltage = iscVoltage;
	}

	public InputComponent getIcRunningStatus() {
		return icRunningStatus;
	}

	public void setIcRunningStatus(InputComponent icRunningStatus) {
		this.icRunningStatus = icRunningStatus;
	}

	public InputComponent getIcStationHouse() {
		return icStationHouse;
	}

	public void setIcStationHouse(InputComponent icStationHouse) {
		this.icStationHouse = icStationHouse;
	}

	public InputComponent getIcSpace() {
		return icSpace;
	}

	public void setIcSpace(InputComponent icSpace) {
		this.icSpace = icSpace;
	}

	public InputComponent getIcEquipmentModel() {
		return icEquipmentModel;
	}

	public void setIcEquipmentModel(InputComponent icEquipmentModel) {
		this.icEquipmentModel = icEquipmentModel;
	}

	public InputComponent getIcRatedCapacity() {
		return icRatedCapacity;
	}

	public void setIcRatedCapacity(InputComponent icRatedCapacity) {
		this.icRatedCapacity = icRatedCapacity;
	}

	public InputComponent getIcPropertyRights() {
		return icPropertyRights;
	}

	public void setIcPropertyRights(InputComponent icPropertyRights) {
		this.icPropertyRights = icPropertyRights;
	}

	public InputComponent getIcCommissioningDate() {
		return icCommissioningDate;
	}

	public void setIcCommissioningDate(InputComponent icCommissioningDate) {
		this.icCommissioningDate = icCommissioningDate;
	}

	public InputComponent getIcManufacturer() {
		return icManufacturer;
	}

	public void setIcManufacturer(InputComponent icManufacturer) {
		this.icManufacturer = icManufacturer;
	}

	public InputComponent getIcManufacturingNum() {
		return icManufacturingNum;
	}

	public void setIcManufacturingNum(InputComponent icManufacturingNum) {
		this.icManufacturingNum = icManufacturingNum;
	}

	public InputComponent getIcUserImportantGrade() {
		return icUserImportantGrade;
	}

	public void setIcUserImportantGrade(InputComponent icUserImportantGrade) {
		this.icUserImportantGrade = icUserImportantGrade;
	}

	public InputComponent getIcAgMark() {
		return icAgMark;
	}

	public void setIcAgMark(InputComponent icAgMark) {
		this.icAgMark = icAgMark;
	}

	public InputComponent getIcCustomerName() {
		return icCustomerName;
	}

	public void setIcCustomerName(InputComponent icCustomerName) {
		this.icCustomerName = icCustomerName;
	}

	public InputComponent getIcNoLoadCurrent() {
		return icNoLoadCurrent;
	}

	public void setIcNoLoadCurrent(InputComponent icNoLoadCurrent) {
		this.icNoLoadCurrent = icNoLoadCurrent;
	}

	public InputComponent getIcNoLoadLoss() {
		return icNoLoadLoss;
	}

	public void setIcNoLoadLoss(InputComponent icNoLoadLoss) {
		this.icNoLoadLoss = icNoLoadLoss;
	}

	public InputComponent getIcLoadLoss() {
		return icLoadLoss;
	}

	public void setIcLoadLoss(InputComponent icLoadLoss) {
		this.icLoadLoss = icLoadLoss;
	}

	public InputComponent getIcRemarks() {
		return icRemarks;
	}

	public void setIcRemarks(InputComponent icRemarks) {
		this.icRemarks = icRemarks;
	}

	public InputComponent getIcCustomerNum() {
		return icCustomerNum;
	}

	public void setIcCustomerNum(InputComponent icCustomerNum) {
		this.icCustomerNum = icCustomerNum;
	}

}
