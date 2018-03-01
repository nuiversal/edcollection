package com.winway.android.edcollection.adding.viewholder;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.edcollection.base.widgets.TakePhotoComponent;

/**
 * 环网柜/分接箱UI初始化
 * 
 * @author lyh
 * @version 创建时间：2016年12月19日 上午9:18:39
 * 
 */
public class CabinetOrBoxViewHolder extends BaseViewHolder {
	
	@ViewInject(R.id.rg_cabinet_device_type)
	private RadioGroup rgDeviceType; // 设备类型
	
	@ViewInject(R.id.rdoBtn_cabinet_or_box_cabinet)
	private RadioButton cabinet; // 分接箱

	@ViewInject(R.id.rdoBtn_cabinet_or_box_box)
	private RadioButton box; // 环网柜

	@ViewInject(R.id.hc_cabinet_head)
	private HeadComponent hcSubHead; // 标题组件

	@ViewInject(R.id.inCon_cabinet_device_name)
	private InputComponent icName; // 设备全称

	@ViewInject(R.id.inCon_cabinet_loop_total)
	private InputComponent icLoop; // 回路总数

	@ViewInject(R.id.tpc_cabinet_photo)
	private TakePhotoComponent tpcPhoto; // 拍照

	public RadioButton getCabinet() {
		return cabinet;
	}

	public void setCabinet(RadioButton cabinet) {
		this.cabinet = cabinet;
	}

	public RadioButton getBox() {
		return box;
	}

	public void setBox(RadioButton box) {
		this.box = box;
	}

	public HeadComponent getHcSubHead() {
		return hcSubHead;
	}

	public void setHcSubHead(HeadComponent hcSubHead) {
		this.hcSubHead = hcSubHead;
	}

	public InputComponent getIcName() {
		return icName;
	}

	public void setIcName(InputComponent icName) {
		this.icName = icName;
	}

	public InputComponent getIcLoop() {
		return icLoop;
	}

	public void setIcLoop(InputComponent icLoop) {
		this.icLoop = icLoop;
	}

	public TakePhotoComponent getTpcPhoto() {
		return tpcPhoto;
	}

	public void setTpcPhoto(TakePhotoComponent tpcPhoto) {
		this.tpcPhoto = tpcPhoto;
	}

	public RadioGroup getRgDeviceType() {
		return rgDeviceType;
	}

	public void setRgDeviceType(RadioGroup rgDeviceType) {
		this.rgDeviceType = rgDeviceType;
	}

}
