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
 * 变电站UI初始化
 * 
 * @author lyh
 * @version 创建时间：2016年12月19日 下午5:31:25
 * 
 */
public class SubstationViewHolder extends BaseViewHolder {

	@ViewInject(R.id.av_sub_attachment)
	private AttachmentView avAttachment;// 附件

	@ViewInject(R.id.hc_sub_head)
	private HeadComponent hcSubHead; // 标题组件

	@ViewInject(R.id.inCon_substation_longitude) // 经度
	private InputComponent icSubtationLongitude;

	@ViewInject(R.id.inCon_substation_latitude) // 纬度
	private InputComponent icSubtationLatitude;

	@ViewInject(R.id.inSelCom_sub_sub_voltage)
	private InputSelectComponent iscVoltage; // 电压等级

	@ViewInject(R.id.inCom_sub_sub_no)
	private InputComponent icSubNo; // 变电站编号

	@ViewInject(R.id.inCom_sub_sub_name)
	private InputComponent icName; // 变电站名称

	@ViewInject(R.id.inSelCom_sub_rightPro)
	private InputSelectComponent iscRigtPro; // 产权属性

	@ViewInject(R.id.iv_link_device_enter)
	private ImageView ivLinkDeviceEnter;// 关联

	@ViewInject(R.id.btn_link_device_link)
	private Button btnLinkDeviceLink;// 关联

	@ViewInject(R.id.rl_link_device)
	private RelativeLayout rlLinkDevice;// 关联布局

	@ViewInject(R.id.btn_substation_map)
	private Button btn_substation_map;

	@ViewInject(R.id.btn_substation_createNo)
	private Button btnCreateSubNo;// 生成变电站编号

	public Button getBtnCreateSubNo() {
		return btnCreateSubNo;
	}

	public void setBtnCreateSubNo(Button btnCreateSubNo) {
		this.btnCreateSubNo = btnCreateSubNo;
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

	public HeadComponent getHcSubHead() {
		return hcSubHead;
	}

	public void setHcSubHead(HeadComponent hcSubHead) {
		this.hcSubHead = hcSubHead;
	}

	public InputSelectComponent getIscVoltage() {
		return iscVoltage;
	}

	public void setIscVoltage(InputSelectComponent iscVoltage) {
		this.iscVoltage = iscVoltage;
	}

	public InputComponent getIcSubNo() {
		return icSubNo;
	}

	public void setIcSubNo(InputComponent icSubNo) {
		this.icSubNo = icSubNo;
	}

	public InputComponent getIcName() {
		return icName;
	}

	public void setIcName(InputComponent icName) {
		this.icName = icName;
	}

	public InputSelectComponent getIscRigtPro() {
		return iscRigtPro;
	}

	public void setIscRigtPro(InputSelectComponent iscRigtPro) {
		this.iscRigtPro = iscRigtPro;
	}

	public AttachmentView getAvAttachment() {
		return avAttachment;
	}

	public void setAvAttachment(AttachmentView avAttachment) {
		this.avAttachment = avAttachment;
	}

	public InputComponent getIcSubtationLongitude() {
		return icSubtationLongitude;
	}

	public void setIcSubtationLongitude(InputComponent icSubtationLongitude) {
		this.icSubtationLongitude = icSubtationLongitude;
	}

	public InputComponent getIcSubtationLatitude() {
		return icSubtationLatitude;
	}

	public void setIcSubtationLatitude(InputComponent icSubtationLatitude) {
		this.icSubtationLatitude = icSubtationLatitude;
	}

	public Button getBtn_substation_map() {
		return btn_substation_map;
	}

	public void setBtn_substation_map(Button btn_substation_map) {
		this.btn_substation_map = btn_substation_map;
	}

}
