package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.page.PageControl;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 关联设备
 * 
 * @author zgq
 *
 */
public class SelectDeviceViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_select_device_head)
	private HeadComponent hcHead;// head

	@ViewInject(R.id.edtTxt_select_device_name)
	private EditText edtTxtKeywords;// 关键字输入框

	@ViewInject(R.id.btn_select_device_query)
	private Button btnQuery;// 查询按钮

	@ViewInject(R.id.lv_select_device_list)
	private ListView lvSelectDeviceList;// 查询列表

	@ViewInject(R.id.pc_select_device_paging)
	private PageControl pageControl;// 分页组件

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public EditText getEdtTxtKeywords() {
		return edtTxtKeywords;
	}

	public void setEdtTxtKeywords(EditText edtTxtKeywords) {
		this.edtTxtKeywords = edtTxtKeywords;
	}

	public Button getBtnQuery() {
		return btnQuery;
	}

	public void setBtnQuery(Button btnQuery) {
		this.btnQuery = btnQuery;
	}

	public ListView getLvSelectDeviceList() {
		return lvSelectDeviceList;
	}

	public void setLvSelectDeviceList(ListView lvSelectDeviceList) {
		this.lvSelectDeviceList = lvSelectDeviceList;
	}

	public PageControl getPageControl() {
		return pageControl;
	}

	public void setPageControl(PageControl pageControl) {
		this.pageControl = pageControl;
	}

}
