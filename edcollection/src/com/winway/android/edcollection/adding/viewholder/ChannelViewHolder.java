package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

import android.widget.Button;
import android.widget.ListView;

/**
 * 通道采集页
 * 
 * @author zgq
 *
 */
public class ChannelViewHolder extends BaseViewHolder {
	@ViewInject(R.id.isc_fragment_channel_channelType)
	private InputSelectComponent iscChannelType;// 通道类型

	@ViewInject(R.id.inCon_fragment_channel_sort)
	private InputComponent icChannelSort;// 通道节点序号

	@ViewInject(R.id.inCon_fragment_channel_name)
	private InputComponent icChannelName;// 通道名称

	@ViewInject(R.id.btn_fragment_channel_select)
	private Button btnChannelSelect;// 通道选择

	@ViewInject(R.id.btn_fragment_channel_new)
	private Button btnChannelNew;// 新增

	@ViewInject(R.id.btn_fragment_channel_add)
	private Button btnChannelAdd;// 选择

	@ViewInject(R.id.lv_fragment_channel_list)
	private ListView lvChannelList;// 通道列表

	@ViewInject(R.id.btn_fragment_channel_intellect_select)
	private Button btnIntellectSelect;// 智能选择通道

	public InputSelectComponent getIscChannelType() {
		return iscChannelType;
	}

	public void setIscChannelType(InputSelectComponent iscChannelType) {
		this.iscChannelType = iscChannelType;
	}

	public InputComponent getIcChannelSort() {
		return icChannelSort;
	}

	public void setIcChannelSort(InputComponent icChannelSort) {
		this.icChannelSort = icChannelSort;
	}

	public InputComponent getIcChannelName() {
		return icChannelName;
	}

	public void setIcChannelName(InputComponent icChannelName) {
		this.icChannelName = icChannelName;
	}

	public Button getBtnChannelSelect() {
		return btnChannelSelect;
	}

	public void setBtnChannelSelect(Button btnChannelSelect) {
		this.btnChannelSelect = btnChannelSelect;
	}

	public Button getBtnChannelNew() {
		return btnChannelNew;
	}

	public void setBtnChannelNew(Button btnChannelNew) {
		this.btnChannelNew = btnChannelNew;
	}

	public Button getBtnChannelAdd() {
		return btnChannelAdd;
	}

	public void setBtnChannelAdd(Button btnChannelAdd) {
		this.btnChannelAdd = btnChannelAdd;
	}

	public ListView getLvChannelList() {
		return lvChannelList;
	}

	public void setLvChannelList(ListView lvChannelList) {
		this.lvChannelList = lvChannelList;
	}

	public Button getBtnIntellectSelect() {
		return btnIntellectSelect;
	}

	public void setBtnIntellectSelect(Button btnIntellectSelect) {
		this.btnIntellectSelect = btnIntellectSelect;
	}

}
