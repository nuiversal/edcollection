package com.winway.android.edcollection.adding.viewholder;

import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

/**
 * 电缆桥
 *
 * @author lyh
 * @data 2017年2月13日
 */
public class ChannelDLQViewHolder extends BaseViewHolder {

	@ViewInject(R.id.hc_tower_head)
	private HeadComponent hcHead;// head组件

	@ViewInject(R.id.inCon_channel_dlq_running_no)
	private InputComponent inConYxbh; // 运行编号

	// @ViewInject(R.id.inCon_channel_dlq_tdcd)
	// private InputComponent inConTdcd; // 通道长度

	@ViewInject(R.id.insc_channel_dlq_operation_unit)
	private InputSelectComponent inscYwdw;// 运维单位

	@ViewInject(R.id.insc_channel_dlq_whbz)
	private InputSelectComponent inscWhbz;// 维护班组

	@ViewInject(R.id.inSelCom_channel_dlq_dqtz)
	private InputSelectComponent inSelConDqtz;// 地区特征

	@ViewInject(R.id.inSelCom_channel_dlq_cz)
	private InputSelectComponent inSconCz;// 材质

	@ViewInject(R.id.inCon_channel_dlq_fhcl)
	private InputComponent inConFhcl;// 防火材料

	@ViewInject(R.id.inCon_channel_dlq_sgdw)
	private InputComponent inConSgdw;// 施工单位

	@ViewInject(R.id.inCon_channel_dlq_sgrq)
	private InputComponent inConSgrq;// 施工日期

	@ViewInject(R.id.inCon_channel_dlq_jgrq)
	private InputComponent inConJgrq;// 峻工日期

	@ViewInject(R.id.insc_channel_dlq_zcxz)
	private InputSelectComponent inscZcxz;// 资产性质

	@ViewInject(R.id.inCon_channel_dlq_zcdw)
	private InputComponent inConZcdw;// 资产单位

	@ViewInject(R.id.insc_channel_dlq_zyfl)
	private InputSelectComponent inscZyfl;// 专业分类

	@ViewInject(R.id.inSelCom_channel_dlq_sbzt)
	private InputSelectComponent inSconSbzt;// 设备状态
	
	@ViewInject(R.id.rl_channel_dlq)
	private RelativeLayout rlNameConventions;

	public RelativeLayout getRlNameConventions() {
		return rlNameConventions;
	}

	public void setRlNameConventions(RelativeLayout rlNameConventions) {
		this.rlNameConventions = rlNameConventions;
	}

	// @ViewInject(R.id.inCon_channel_dlq_remarks)
	// private InputComponent inConBz;// 备注

	private CommentChannelViewHolder commentChannelViewHolder = new CommentChannelViewHolder();

	public CommentChannelViewHolder getCommentChannelViewHolder() {
		return commentChannelViewHolder;
	}

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public InputComponent getInConYxbh() {
		return inConYxbh;
	}

	public void setInConYxbh(InputComponent inConYxbh) {
		this.inConYxbh = inConYxbh;
	}

	public InputSelectComponent getInscYwdw() {
		return inscYwdw;
	}

	public void setInscYwdw(InputSelectComponent inscYwdw) {
		this.inscYwdw = inscYwdw;
	}

	public InputSelectComponent getInscWhbz() {
		return inscWhbz;
	}

	public void setInscWhbz(InputSelectComponent inscWhbz) {
		this.inscWhbz = inscWhbz;
	}

	public InputSelectComponent getInSelConDqtz() {
		return inSelConDqtz;
	}

	public void setInSelConDqtz(InputSelectComponent inSelConDqtz) {
		this.inSelConDqtz = inSelConDqtz;
	}

	public InputSelectComponent getInSconCz() {
		return inSconCz;
	}

	public void setInSconCz(InputSelectComponent inSconCz) {
		this.inSconCz = inSconCz;
	}

	public InputComponent getInConFhcl() {
		return inConFhcl;
	}

	public void setInConFhcl(InputComponent inConFhcl) {
		this.inConFhcl = inConFhcl;
	}

	public InputComponent getInConSgdw() {
		return inConSgdw;
	}

	public void setInConSgdw(InputComponent inConSgdw) {
		this.inConSgdw = inConSgdw;
	}

	public InputComponent getInConSgrq() {
		return inConSgrq;
	}

	public void setInConSgrq(InputComponent inConSgrq) {
		this.inConSgrq = inConSgrq;
	}

	public InputComponent getInConJgrq() {
		return inConJgrq;
	}

	public void setInConJgrq(InputComponent inConJgrq) {
		this.inConJgrq = inConJgrq;
	}

	public InputSelectComponent getInscZcxz() {
		return inscZcxz;
	}

	public void setInscZcxz(InputSelectComponent inscZcxz) {
		this.inscZcxz = inscZcxz;
	}

	public InputComponent getInConZcdw() {
		return inConZcdw;
	}

	public void setInConZcdw(InputComponent inConZcdw) {
		this.inConZcdw = inConZcdw;
	}

	public InputSelectComponent getInscZyfl() {
		return inscZyfl;
	}

	public void setInscZyfl(InputSelectComponent inscZyfl) {
		this.inscZyfl = inscZyfl;
	}

	public InputSelectComponent getInSconSbzt() {
		return inSconSbzt;
	}

	public void setInSconSbzt(InputSelectComponent inSconSbzt) {
		this.inSconSbzt = inSconSbzt;
	}

}
