package com.winway.android.edcollection.adding.viewholder;

import android.widget.Button;
import android.widget.ImageView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

public class DialogChanelPointViewHolder {
	@ViewInject(R.id.dialog_channel_point_line)
	private InputSelectComponent lineSelect; // 关联线路

	@ViewInject(R.id.dialog_channel_point_point_no)
	private InputComponent pointNo; // 管孔序号

	@ViewInject(R.id.dialog_channel_point_point_r)
	private InputComponent pointR;// 管孔直径

	@ViewInject(R.id.dialog_channel_point_is_plugging)
	private InputSelectComponent isPlugging; // 管孔现状

	@ViewInject(R.id.dialog_channel_point_fill_color)
	private InputComponent IcFillColor; // 填充颜色

	@ViewInject(R.id.dialog_channel_point_choose_color)
	private Button btnChooseColor; // 选择

	@ViewInject(R.id.isc_dialog_channel_point_phase_seq)
	private InputSelectComponent iscPhaseSeq; // 电缆相序

	@ViewInject(R.id.dialog_channel_point_sure)
	private Button sureBT;// 确定

	@ViewInject(R.id.iv_dialog_point_ref_line_selected_close)
	private ImageView ivClose; // 关闭图标

	public ImageView getIvClose() {
		return ivClose;
	}

	public void setIvClose(ImageView ivClose) {
		this.ivClose = ivClose;
	}

	public InputSelectComponent getIscPhaseSeq() {
		return iscPhaseSeq;
	}

	public void setIscPhaseSeq(InputSelectComponent iscPhaseSeq) {
		this.iscPhaseSeq = iscPhaseSeq;
	}

	public InputComponent getIcFillColor() {
		return IcFillColor;
	}

	public void setIcFillColor(InputComponent icFillColor) {
		IcFillColor = icFillColor;
	}

	public Button getBtnChooseColor() {
		return btnChooseColor;
	}

	public void setBtnChooseColor(Button btnChooseColor) {
		this.btnChooseColor = btnChooseColor;
	}

	public InputSelectComponent getLineSelect() {
		return lineSelect;
	}

	public void setLineSelect(InputSelectComponent lineSelect) {
		this.lineSelect = lineSelect;
	}

	public InputComponent getPointNo() {
		return pointNo;
	}

	public void setPointNo(InputComponent pointNo) {
		this.pointNo = pointNo;
	}

	public InputComponent getPointR() {
		return pointR;
	}

	public void setPointR(InputComponent pointR) {
		this.pointR = pointR;
	}

	public InputSelectComponent getIsPlugging() {
		return isPlugging;
	}

	public void setIsPlugging(InputSelectComponent isPlugging) {
		this.isPlugging = isPlugging;
	}

	public Button getSureBT() {
		return sureBT;
	}

	public void setSureBT(Button sureBT) {
		this.sureBT = sureBT;
	}

}
