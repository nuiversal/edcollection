package com.winway.android.edcollection.adding.viewholder;

import android.widget.Button;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.ewidgets.input.InputComponent;

/**
 * @author lyh
 * @version 创建时间：2017年4月20日
 *
 */
public class DialogShowGridHelpViewHolder {

	@ViewInject(R.id.btn_gj_dl_not_grid)
	private Button btnNoGrid; // 无网格

	@ViewInject(R.id.btn_gj_dl_grid_sure)
	private Button btnGridSure; // 有网格

	@ViewInject(R.id.incon_dialog_wg_setting_x)
	private InputComponent inconX;

	@ViewInject(R.id.incon_dialog_wg_setting_y)
	private InputComponent inconY;

	public Button getBtnNoGrid() {
		return btnNoGrid;
	}

	public void setBtnNoGrid(Button btnNoGrid) {
		this.btnNoGrid = btnNoGrid;
	}

	public Button getBtnGridSure() {
		return btnGridSure;
	}

	public void setBtnGridSure(Button btnGridSure) {
		this.btnGridSure = btnGridSure;
	}

	public InputComponent getInconX() {
		return inconX;
	}

	public void setInconX(InputComponent inconX) {
		this.inconX = inconX;
	}

	public InputComponent getInconY() {
		return inconY;
	}

	public void setInconY(InputComponent inconY) {
		this.inconY = inconY;
	}
}
