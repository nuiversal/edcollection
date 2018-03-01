package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChannelSelectViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_select_device_head)
	private HeadComponent hcSubHead; // 标题组件
	@ViewInject(R.id.edtTxt_select_channel_name)
	private EditText channelNameET;
	@ViewInject(R.id.btn_select_device_query)
	private Button queryBT;
	@ViewInject(R.id.lv_select_device_list)
	private ListView channelLV;
	@ViewInject(R.id.load_more)
	private TextView moreTV;

	public HeadComponent getHcSubHead() {
		return hcSubHead;
	}

	public void setHcSubHead(HeadComponent hcSubHead) {
		this.hcSubHead = hcSubHead;
	}

	public EditText getChannelNameET() {
		return channelNameET;
	}

	public void setChannelNameET(EditText channelNameET) {
		this.channelNameET = channelNameET;
	}

	public Button getQueryBT() {
		return queryBT;
	}

	public void setQueryBT(Button queryBT) {
		this.queryBT = queryBT;
	}

	public ListView getChannelLV() {
		return channelLV;
	}

	public void setChannelLV(ListView channelLV) {
		this.channelLV = channelLV;
	}

	public TextView getMoreTV() {
		return moreTV;
	}

	public void setMoreTV(TextView moreTV) {
		this.moreTV = moreTV;
	}

}
