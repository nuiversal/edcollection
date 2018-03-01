package com.winway.android.edcollection.adding.viewholder;

import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

/**
 * 顶管、拖拉管
 * 
 * @author
 *
 */
public class ChannelDGViewHolder extends BaseViewHolder {

	@ViewInject(R.id.hc_pipe_jacking_head)
	private HeadComponent hcHead;// head组件

	@ViewInject(R.id.inCom_pipe_jacking_yxbh)
	private InputComponent inComPipeJackYxbh;// 运行编号

	@ViewInject(R.id.insc_pipe_jacking_ywdw)
	private InputSelectComponent inScPipeJackYwdw;// 运维单位

	@ViewInject(R.id.insc_pipe_jacking_jgxs)
	private InputSelectComponent inscPipeJackJgxs;// 结构形式

	@ViewInject(R.id.inCom_pipe_jacking_zx)
	private InputComponent inComPipeJackZx;// 走向

	@ViewInject(R.id.inCom_pipe_jacking_ltgx)
	private InputComponent inComPipeJackLtgx;// 连通关系

	@ViewInject(R.id.inCom_pipe_jacking_hxpm)
	private InputComponent inComPipeJackHxpm;// 横向剖面

	@ViewInject(R.id.inCom_pipe_jacking_zxpm)
	private InputComponent inComPipeJackZxpm;// 纵向剖面

	@ViewInject(R.id.inCom_pipe_jacking_kd)
	private InputComponent inComPipeJackKd;// 宽度

	@ViewInject(R.id.inCom_pipe_jacking_tlgsl)
	private InputComponent inComPipeJackTlgsl;// 拖拉管数量

	@ViewInject(R.id.inCom_pipe_jacking_tlggj)
	private InputComponent inComPipeJackTlggj;// 拖拉管管径

	@ViewInject(R.id.inCom_pipe_jacking_gccz)
	private InputComponent inComPipeJackGccz;// 管材材质
	
	@ViewInject(R.id.rl_pipe_jacking)
	private RelativeLayout rlNameConventions;

	public RelativeLayout getRlNameConventions() {
		return rlNameConventions;
	}

	public void setRlNameConventions(RelativeLayout rlNameConventions) {
		this.rlNameConventions = rlNameConventions;
	}

	// @ViewInject(R.id.inCom_pipe_jacking_bz)
	// private InputComponent inComPipeJackBz;// 备注

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

	public InputComponent getInComPipeJackYxbh() {
		return inComPipeJackYxbh;
	}

	public void setInComPipeJackYxbh(InputComponent inComPipeJackYxbh) {
		this.inComPipeJackYxbh = inComPipeJackYxbh;
	}

	public InputSelectComponent getInscPipeJackJgxs() {
		return inscPipeJackJgxs;
	}

	public void setInscPipeJackJgxs(InputSelectComponent inscPipeJackJgxs) {
		this.inscPipeJackJgxs = inscPipeJackJgxs;
	}

	public InputComponent getInComPipeJackZx() {
		return inComPipeJackZx;
	}

	public void setInComPipeJackZx(InputComponent inComPipeJackZx) {
		this.inComPipeJackZx = inComPipeJackZx;
	}

	public InputComponent getInComPipeJackLtgx() {
		return inComPipeJackLtgx;
	}

	public void setInComPipeJackLtgx(InputComponent inComPipeJackLtgx) {
		this.inComPipeJackLtgx = inComPipeJackLtgx;
	}

	public InputComponent getInComPipeJackHxpm() {
		return inComPipeJackHxpm;
	}

	public void setInComPipeJackHxpm(InputComponent inComPipeJackHxpm) {
		this.inComPipeJackHxpm = inComPipeJackHxpm;
	}

	public InputComponent getInComPipeJackZxpm() {
		return inComPipeJackZxpm;
	}

	public void setInComPipeJackZxpm(InputComponent inComPipeJackZxpm) {
		this.inComPipeJackZxpm = inComPipeJackZxpm;
	}

	public InputComponent getInComPipeJackKd() {
		return inComPipeJackKd;
	}

	public void setInComPipeJackKd(InputComponent inComPipeJackKd) {
		this.inComPipeJackKd = inComPipeJackKd;
	}

	public InputComponent getInComPipeJackTlgsl() {
		return inComPipeJackTlgsl;
	}

	public void setInComPipeJackTlgsl(InputComponent inComPipeJackTlgsl) {
		this.inComPipeJackTlgsl = inComPipeJackTlgsl;
	}

	public InputComponent getInComPipeJackTlggj() {
		return inComPipeJackTlggj;
	}

	public void setInComPipeJackTlggj(InputComponent inComPipeJackTlggj) {
		this.inComPipeJackTlggj = inComPipeJackTlggj;
	}

	public InputComponent getInComPipeJackGccz() {
		return inComPipeJackGccz;
	}

	public void setInComPipeJackGccz(InputComponent inComPipeJackGccz) {
		this.inComPipeJackGccz = inComPipeJackGccz;
	}

	public InputSelectComponent getInScPipeJackYwdw() {
		return inScPipeJackYwdw;
	}

	public void setInScPipeJackYwdw(InputSelectComponent inScPipeJackYwdw) {
		this.inScPipeJackYwdw = inScPipeJackYwdw;
	}

}
