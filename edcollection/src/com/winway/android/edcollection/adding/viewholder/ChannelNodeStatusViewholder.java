package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

import android.widget.EditText;

/**
 * 通道节点现状
 * 
 * @author winway_zgq
 *
 */
public class ChannelNodeStatusViewholder extends BaseViewHolder {
	@ViewInject(R.id.isc_damage_collect_damageStatus)
	private InputSelectComponent icDamageStatus;// 隐患状况

	@ViewInject(R.id.edtTxt_damage_collect_desc)
	private EditText editTxtDesc;// 描述信息

	public InputSelectComponent getIcDamageStatus() {
		return icDamageStatus;
	}

	public void setIcDamageStatus(InputSelectComponent icDamageStatus) {
		this.icDamageStatus = icDamageStatus;
	}

	public EditText getEditTxtDesc() {
		return editTxtDesc;
	}

	public void setEditTxtDesc(EditText editTxtDesc) {
		this.editTxtDesc = editTxtDesc;
	}
}
