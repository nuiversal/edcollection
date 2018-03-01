package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * 添加临时线路
 * 
 * @author zgq
 *
 */
public class AddTempLineViewHolder extends BaseViewHolder {
	@ViewInject(R.id.ic_addtempline_level)
	private InputSelectComponent icAddtemplineLevel;// 电压等级

	@ViewInject(R.id.ic_addtempline_lineName)
	private InputComponent icAddtemplineName;// 线路名称

	@ViewInject(R.id.ic_addtempline_lineNo)
	private InputComponent icAddtemplineNo;// 线路编号

	@ViewInject(R.id.btn_add_temp_line_no)
	private Button btnAddTempLineNo;// 线路编号生成按钮

	@ViewInject(R.id.btn_addtempline_save)
	private Button btnAddtemplineSave;// 保存

	@ViewInject(R.id.hc_addtempline_head)
	private HeadComponent hcAddtemplineHead; // head

	@ViewInject(R.id.ic_addtempline_startSubstation)
	private InputSelectComponent iscSubstation;// 所属变电站
	
	@ViewInject(R.id.cb_addtempline_no)//否
	private CheckBox cbAddtemplineNo;
	
	@ViewInject(R.id.cb_addtempline_yes)//是
	private CheckBox cbAdtempLineYes;
	
	@ViewInject(R.id.ic_addtempline_select_line)//选择子线
	private InputSelectComponent icAddtemplineSelectline;
	
	@ViewInject(R.id.ic_addtempline_cable_length)//电缆长度
	private InputComponent icAddtemplineCableLength;
	
	@ViewInject(R.id.ic_addtempline_cable_model)//电缆型号
	private InputComponent icAddtemplineCableModel;
	
	@ViewInject(R.id.ll_addtempline_display)//选择主线布局
	private LinearLayout llAddtemplineDisplay;
	
	
	public LinearLayout getLlAddtemplineDisplay() {
		return llAddtemplineDisplay;
	}

	public void setLlAddtemplineDisplay(LinearLayout llAddtemplineDisplay) {
		this.llAddtemplineDisplay = llAddtemplineDisplay;
	}

	public InputComponent getIcAddtemplineCableLength() {
		return icAddtemplineCableLength;
	}

	public void setIcAddtemplineCableLength(InputComponent icAddtemplineCableLength) {
		this.icAddtemplineCableLength = icAddtemplineCableLength;
	}

	public InputComponent getIcAddtemplineCableModel() {
		return icAddtemplineCableModel;
	}

	public void setIcAddtemplineCableModel(InputComponent icAddtemplineCableModel) {
		this.icAddtemplineCableModel = icAddtemplineCableModel;
	}

	public InputSelectComponent getIscSubstation() {
		return iscSubstation;
	}
	
	public CheckBox getCbAddtemplineNo() {
		return cbAddtemplineNo;
	}

	public void setCbAddtemplineNo(CheckBox cbAddtemplineNo) {
		this.cbAddtemplineNo = cbAddtemplineNo;
	}

	public CheckBox getCbAdtempLineYes() {
		return cbAdtempLineYes;
	}

	public void setCbAdtempLineYes(CheckBox cbAdtempLineYes) {
		this.cbAdtempLineYes = cbAdtempLineYes;
	}

	public InputSelectComponent getIcAddtemplineSelectline() {
		return icAddtemplineSelectline;
	}

	public void setIcAddtemplineSelectline(InputSelectComponent icAddtemplineSelectline) {
		this.icAddtemplineSelectline = icAddtemplineSelectline;
	}

	public void setIscSubstation(InputSelectComponent iscSubstation) {
		this.iscSubstation = iscSubstation;
	}

	public InputSelectComponent getIcAddtemplineLevel() {
		return icAddtemplineLevel;
	}

	public void setIcAddtemplineLevel(InputSelectComponent icAddtemplineLevel) {
		this.icAddtemplineLevel = icAddtemplineLevel;
	}

	public InputComponent getIcAddtemplineName() {
		return icAddtemplineName;
	}

	public void setIcAddtemplineName(InputComponent icAddtemplineName) {
		this.icAddtemplineName = icAddtemplineName;
	}

	public InputComponent getIcAddtemplineNo() {
		return icAddtemplineNo;
	}

	public void setIcAddtemplineNo(InputComponent icAddtemplineNo) {
		this.icAddtemplineNo = icAddtemplineNo;
	}

	public Button getBtnAddtemplineSave() {
		return btnAddtemplineSave;
	}

	public void setBtnAddtemplineSave(Button btnAddtemplineSave) {
		this.btnAddtemplineSave = btnAddtemplineSave;
	}

	public HeadComponent getHcAddtemplineHead() {
		return hcAddtemplineHead;
	}

	public void setHcAddtemplineHead(HeadComponent hcAddtemplineHead) {
		this.hcAddtemplineHead = hcAddtemplineHead;
	}

	public Button getBtnAddTempLineNo() {
		return btnAddTempLineNo;
	}

	public void setBtnAddTempLineNo(Button btnAddTempLineNo) {
		this.btnAddTempLineNo = btnAddTempLineNo;
	}
	
}
