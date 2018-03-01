package com.winway.android.edcollection.adding.viewholder;

import android.widget.Button;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;

/**
 * @author lyh
 * @version 创建时间：2017年2月9日 上午10:22:53
 * 
 */
public class SelectLineViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_activity_select_line_head)
	private HeadComponent hcTitle; // 标题

	@ViewInject(R.id.inCom_select_line_search)
	private InputComponent icSearch; // 搜索框

	@ViewInject(R.id.btn_select_line_search)
	private Button btnSearch; // 搜索图标

	@ViewInject(R.id.lv_select_line_addlinelist)
	private ListView lvAddLineList; // 添加线路图标

	@ViewInject(R.id.lv_select_line_linelist)
	private ListView lvLineList; // 选择线路图标

//	@ViewInject(R.id.btn_select_line_confirm)
//	private Button btnConfirm; // 确定

	@ViewInject(R.id.btn_NoSelect_line)
	private Button btnAddAllLine; // 添加所有

	@ViewInject(R.id.btn_selected_line)
	private Button btnRemoveAllLine; // 添加所有

	public HeadComponent getHcTitle() {
		return hcTitle;
	}

	public void setHcTitle(HeadComponent hcTitle) {
		this.hcTitle = hcTitle;
	}

	public Button getBtnAddAllLine() {
		return btnAddAllLine;
	}

	public void setBtnAddAllLine(Button btnAddAllLine) {
		this.btnAddAllLine = btnAddAllLine;
	}

	public Button getBtnRemoveAllLine() {
		return btnRemoveAllLine;
	}

	public void setBtnRemoveAllLine(Button btnRemoveAllLine) {
		this.btnRemoveAllLine = btnRemoveAllLine;
	}

	public InputComponent getIcSearch() {
		return icSearch;
	}

	public void setIcSearch(InputComponent icSearch) {
		this.icSearch = icSearch;
	}

	public Button getBtnSearch() {
		return btnSearch;
	}

	public void setBtnSearch(Button btnSearch) {
		this.btnSearch = btnSearch;
	}

	public ListView getLvAddLineList() {
		return lvAddLineList;
	}

	public void setLvAddLineList(ListView lvAddLineList) {
		this.lvAddLineList = lvAddLineList;
	}

	public ListView getLvLineList() {
		return lvLineList;
	}

	public void setLvLineList(ListView lvLineList) {
		this.lvLineList = lvLineList;
	}

}
