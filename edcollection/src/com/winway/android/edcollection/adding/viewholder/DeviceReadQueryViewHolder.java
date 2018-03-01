package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;

import android.widget.Button;
import android.widget.ListView;

public class DeviceReadQueryViewHolder extends BaseViewHolder{
	@ViewInject(R.id.hc_lable_head)
	HeadComponent hc_lable_head;
	
	@ViewInject(R.id.btn_lable_search)
	Button btn_lable_search;
	
	@ViewInject(R.id.btn_hflable_read)
	Button btn_lable_read;
	
	@ViewInject(R.id.btn_uhflable_read)
	Button btn_uhflable_read;
	
	@ViewInject(R.id.lv_deviceList)
	ListView lv_deviceList;

	public Button getBtn_uhflable_read() {
		return btn_uhflable_read;
	}

	public void setBtn_uhflable_read(Button btn_uhflable_read) {
		this.btn_uhflable_read = btn_uhflable_read;
	}

	@ViewInject(R.id.inCon_linelable_id)
	private InputComponent inconLableNo;// 标签ID
	public HeadComponent getHc_lable_head() {
		return hc_lable_head;
	}

	public void setHc_lable_head(HeadComponent hc_lable_head) {
		this.hc_lable_head = hc_lable_head;
	}

	public Button getBtn_lable_search() {
		return btn_lable_search;
	}

	public void setBtn_lable_search(Button btn_lable_search) {
		this.btn_lable_search = btn_lable_search;
	}

	public Button getBtn_lable_read() {
		return btn_lable_read;
	}

	public void setBtn_lable_read(Button btn_lable_read) {
		this.btn_lable_read = btn_lable_read;
	}

	public ListView getLv_deviceList() {
		return lv_deviceList;
	}

	public void setLv_deviceList(ListView lv_deviceList) {
		this.lv_deviceList = lv_deviceList;
	}

	public InputComponent getInconLableNo() {
		return inconLableNo;
	}

	public void setInconLableNo(InputComponent inconLableNo) {
		this.inconLableNo = inconLableNo;
	}
	
	
}
