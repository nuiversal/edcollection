package com.winway.android.edcollection.rtk.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;

import android.widget.ListView;
import com.winway.android.edcollection.R;

/**
 * 蓝牙设备发现
 * 
 * @author zgq
 *
 */
public class DiscoveryViewHolder extends BaseViewHolder {

	@ViewInject(R.id.hc_discovery_head)
	private HeadComponent hcDiscoveryHead;// head

	@ViewInject(R.id.lv_disveryDevices)
	private ListView lvDisveryDevices;// 搜索的设备列表

	public HeadComponent getHcDiscoveryHead() {
		return hcDiscoveryHead;
	}

	public void setHcDiscoveryHead(HeadComponent hcDiscoveryHead) {
		this.hcDiscoveryHead = hcDiscoveryHead;
	}

	public ListView getLvDisveryDevices() {
		return lvDisveryDevices;
	}

	public void setLvDisveryDevices(ListView lvDisveryDevices) {
		this.lvDisveryDevices = lvDisveryDevices;
	}

}
