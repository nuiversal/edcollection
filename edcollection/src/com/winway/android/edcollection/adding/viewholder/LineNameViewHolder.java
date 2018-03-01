package com.winway.android.edcollection.adding.viewholder;

import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.edcollection.project.entity.EcDistBoxEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

/**
 * 线路名称
 * 
 * @author
 *
 */
public class LineNameViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_line_name_head)
	private HeadComponent hcHead;// head组件
	@ViewInject(R.id.tv_line_name_lineName)
	private TextView tvLineName;// 线路名称
	@ViewInject(R.id.inCon_line_name_lineNo)
	private InputComponent inconLineNo;// 序号
	@ViewInject(R.id.insc_line_name_layTyape)
	private InputSelectComponent inscLayType;// 敷设方式
	@ViewInject(R.id.lv_line_name_listview)
	private ListView lvListView;// 同沟附件
	@ViewInject(R.id.inCon_laying_ditch_Distance)
	private InputComponent icDistance;// 与上一节点距离
	@ViewInject(R.id.inCon_line_name_deviceDesc)
	private InputComponent icDeviceDesc; // 设备描述

	private EcLineLabelEntity lineLabelEntity;// 接收电子标签的数据
	private EcDistBoxEntity distBoxEntity;// 环网柜/分接箱
	private EcMiddleJointEntity middleJointEntity;// 电缆中间接头

	public InputComponent getIcDeviceDesc() {
		return icDeviceDesc;
	}

	public void setIcDeviceDesc(InputComponent icDeviceDesc) {
		this.icDeviceDesc = icDeviceDesc;
	}

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public TextView getTvLineName() {
		return tvLineName;
	}

	public void setTvLineName(TextView tvLineName) {
		this.tvLineName = tvLineName;
	}

	public InputComponent getInconLineNo() {
		return inconLineNo;
	}

	public void setInconLineNo(InputComponent inconLineNo) {
		this.inconLineNo = inconLineNo;
	}

	public InputSelectComponent getInscLayType() {
		return inscLayType;
	}

	public void setInscLayType(InputSelectComponent inscLayType) {
		this.inscLayType = inscLayType;
	}

	public ListView getLvListView() {
		return lvListView;
	}

	public void setLvListView(ListView lvListView) {
		this.lvListView = lvListView;
	}

	public EcLineLabelEntity getLineLabelEntity() {
		return lineLabelEntity;
	}

	public void setLineLabelEntity(EcLineLabelEntity lineLabelEntity) {
		this.lineLabelEntity = lineLabelEntity;
	}

	public EcDistBoxEntity getDistBoxEntity() {
		return distBoxEntity;
	}

	public void setDistBoxEntity(EcDistBoxEntity distBoxEntity) {
		this.distBoxEntity = distBoxEntity;
	}

	public EcMiddleJointEntity getMiddleJointEntity() {
		return middleJointEntity;
	}

	public void setMiddleJointEntity(EcMiddleJointEntity middleJointEntity) {
		this.middleJointEntity = middleJointEntity;
	}

	public InputComponent getIcDistance() {
		return icDistance;
	}

	public void setIcDistance(InputComponent icDistance) {
		this.icDistance = icDistance;
	}

}
