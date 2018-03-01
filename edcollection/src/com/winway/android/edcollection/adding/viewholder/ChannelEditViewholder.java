package com.winway.android.edcollection.adding.viewholder;

import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

/**
 * 通道修改
 * 
 * @author lyh
 * @version 创建时间：2017年4月1日
 *
 */
public class ChannelEditViewholder extends BaseViewHolder {
	@ViewInject(R.id.hc_update_channel_head)
	private HeadComponent hcHead;// head组件

	@ViewInject(R.id.tv_update_channel_channelName)
	private TextView tvChannelName; // 通道名称

	@ViewInject(R.id.tv_update_channel_channelType)
	private TextView tvChannelType; // 通道类型输入框

	@ViewInject(R.id.btn_update_channel_attrUpdate)
	private Button btnAttrEdit; // 通道属性修改

	@ViewInject(R.id.inSelCom_update_channel_channelType)
	private InputSelectComponent inscChanneyType; // 通道类型下拉

	@ViewInject(R.id.btn_update_channel_typeUpdate)
	private Button btnTypeEdit; // 通道类型修改

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public TextView getTvChannelName() {
		return tvChannelName;
	}

	public void setTvChannelName(TextView tvChannelName) {
		this.tvChannelName = tvChannelName;
	}

	public TextView getTvChannelType() {
		return tvChannelType;
	}

	public void setTvChannelType(TextView tvChannelType) {
		this.tvChannelType = tvChannelType;
	}

	public Button getBtnAttrEdit() {
		return btnAttrEdit;
	}

	public void setBtnAttrEdit(Button btnAttrEdit) {
		this.btnAttrEdit = btnAttrEdit;
	}

	public InputSelectComponent getInscChanneyType() {
		return inscChanneyType;
	}

	public void setInscChanneyType(InputSelectComponent inscChanneyType) {
		this.inscChanneyType = inscChanneyType;
	}

	public Button getBtnTypeEdit() {
		return btnTypeEdit;
	}

	public void setBtnTypeEdit(Button btnTypeEdit) {
		this.btnTypeEdit = btnTypeEdit;
	}

}
