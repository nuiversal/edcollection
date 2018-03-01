package com.winway.android.edcollection.adding.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

/**
 * 同沟信息
 * 
 * @author lyh
 * @version 创建时间：2016年12月21日 上午10:25:57
 * 
 */
public class LayingDitchViewHolder extends BaseViewHolder {

	@ViewInject(R.id.insc_laying_ditch_lineName)
	private InputSelectComponent inscLineName;// 线路名称

	@ViewInject(R.id.insc_laying_ditch_layTyape)
	private InputSelectComponent insclayType;// 敷设方式

	@ViewInject(R.id.inCon_laying_ditch_lineNodeSort)
	private InputComponent icLineNodeSort;// 序号

	@ViewInject(R.id.ic_laying_ditch_loopNum)
	private InputComponent icLoopNum;// 同沟回路数

	@ViewInject(R.id.btn_laying_ditch_add)
	private Button btnAdd;// 添加

	@ViewInject(R.id.lv_laying_ditch_swipeListView)
	private ListView lvSwipeListView;// 添加

	@ViewInject(R.id.btn_laying_ditch_lineName_adds)
	private Button btnLineNameAdds;// 添加多条线路按钮

	@ViewInject(R.id.inCon_laying_ditch_Distance)
	private InputComponent icDistance;// 于上一路径点距离

	@ViewInject(R.id.inCon_laying_ditch_deviceDescription)
	private InputComponent icDeviceDescription;//设备描述
	
	@ViewInject(R.id.ic_laying_ditch_unknown_loopNum)
	private InputComponent icUnknownLoopNum; // 未知回路数

	public InputComponent getIcDeviceDescription() {
		return icDeviceDescription;
	}

	public void setIcDeviceDescription(InputComponent icDeviceDescription) {
		this.icDeviceDescription = icDeviceDescription;
	}

	public ListView getLvSwipeListView() {
		return lvSwipeListView;
	}

	public void setLvSwipeListView(ListView lvSwipeListView) {
		this.lvSwipeListView = lvSwipeListView;
	}

	public InputSelectComponent getInscLineName() {
		return inscLineName;
	}

	public void setInscLineName(InputSelectComponent inscLineName) {
		this.inscLineName = inscLineName;
	}

	public InputSelectComponent getInsclayType() {
		return insclayType;
	}

	public void setInsclayType(InputSelectComponent insclayType) {
		this.insclayType = insclayType;
	}

	public InputComponent getIcLineNodeSort() {
		return icLineNodeSort;
	}

	public void setIcLineNodeSort(InputComponent icLineNodeSort) {
		this.icLineNodeSort = icLineNodeSort;
	}

	public InputComponent getIcLoopNum() {
		return icLoopNum;
	}

	public void setIcLoopNum(InputComponent icLoopNum) {
		this.icLoopNum = icLoopNum;
	}

	public Button getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(Button btnAdd) {
		this.btnAdd = btnAdd;
	}

	public InputComponent getIcDistance() {
		return icDistance;
	}

	public void setIcDistance(InputComponent icDistance) {
		this.icDistance = icDistance;
	}

	public Button getBtnLineNameAdds() {
		return btnLineNameAdds;
	}

	public void setBtnLineNameAdds(Button btnLineNameAdds) {
		this.btnLineNameAdds = btnLineNameAdds;
	}

	public InputComponent getIcUnknownLoopNum() {
		return icUnknownLoopNum;
	}

	public void setIcUnknownLoopNum(InputComponent icUnknownLoopNum) {
		this.icUnknownLoopNum = icUnknownLoopNum;
	}

}
