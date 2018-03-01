package com.winway.android.edcollection.adding.viewholder;

import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

/**
 * 电缆沟
 * 
 * @author
 *
 */
public class ChannelDLGViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_dlg_head)
	private HeadComponent hcHead;// head组件
	@ViewInject(R.id.inCon_dlg_dlgbh)
	private InputComponent inconDlgbh;// 电缆沟编号
	@ViewInject(R.id.insc_dlg_ywdw)
	private InputSelectComponent inscYwdw;// 运维单位
	@ViewInject(R.id.insc_dlg_whbz)
	private InputSelectComponent inscWhbz;// 维护班组
	@ViewInject(R.id.inCon_dlg_zcxz)
	private InputSelectComponent inscZcxz;// 资产性质
	@ViewInject(R.id.inCon_dlg_zcdw)
	private InputComponent inconZcdw;// 资产单位
	@ViewInject(R.id.inCon_dlg_zcbh)
	private InputComponent inconZcbh;// 资产编号
	@ViewInject(R.id.inCon_dlg_tyrq)
	private InputComponent inconTyrq;// 投运日期
	@ViewInject(R.id.insc_dlg_sbzt)
	private InputSelectComponent inscSbzt;// 设备状态
	@ViewInject(R.id.insc_dlg_dqtz)
	private InputSelectComponent inscDqtz;// 地区特征
	@ViewInject(R.id.inCon_dlg_dmcc)
	private InputComponent inconDmcc;// 断面尺寸
	// @ViewInject(R.id.inCon_dlg_dlgcd)
	// private InputComponent inconDlgcd;// 电缆沟长度
	@ViewInject(R.id.inCon_dlg_dlgbgsl)
	private InputComponent inconDlgbgsl;// 电缆沟盖板数量
	@ViewInject(R.id.inCon_dlg_gbcz)
	private InputComponent inconGbcz;// 盖板材质
	@ViewInject(R.id.inCon_dlg_sgdw)
	private InputComponent inconSgdw;// 施工单位
	@ViewInject(R.id.inCon_dlg_jgrq)
	private InputComponent inconJgrq;// 峻工日期
	@ViewInject(R.id.inCon_dlg_tzbh)
	private InputComponent inconTzbh;// 图纸编号
	@ViewInject(R.id.insc_dlg_zyfl)
	private InputSelectComponent inscZyfl;// 专业分类
	
	@ViewInject(R.id.rl_dlg)
	private RelativeLayout rlNameConventions;

	public RelativeLayout getRlNameConventions() {
		return rlNameConventions;
	}

	public void setRlNameConventions(RelativeLayout rlNameConventions) {
		this.rlNameConventions = rlNameConventions;
	}
	// @ViewInject(R.id.inCon_dlg_bz)
	// private InputComponent inconBz;// 备注

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

	public InputComponent getInconDlgbh() {
		return inconDlgbh;
	}

	public void setInconDlgbh(InputComponent inconDlgbh) {
		this.inconDlgbh = inconDlgbh;
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

	public InputSelectComponent getInscZcxz() {
		return inscZcxz;
	}

	public void setInscZcxz(InputSelectComponent inscZcxz) {
		this.inscZcxz = inscZcxz;
	}

	public InputComponent getInconZcdw() {
		return inconZcdw;
	}

	public void setInconZcdw(InputComponent inconZcdw) {
		this.inconZcdw = inconZcdw;
	}

	public InputComponent getInconZcbh() {
		return inconZcbh;
	}

	public void setInconZcbh(InputComponent inconZcbh) {
		this.inconZcbh = inconZcbh;
	}

	public InputComponent getInconTyrq() {
		return inconTyrq;
	}

	public void setInconTyrq(InputComponent inconTyrq) {
		this.inconTyrq = inconTyrq;
	}

	public InputSelectComponent getInscDqtz() {
		return inscDqtz;
	}

	public void setInscDqtz(InputSelectComponent inscDqtz) {
		this.inscDqtz = inscDqtz;
	}

	public InputComponent getInconDmcc() {
		return inconDmcc;
	}

	public void setInconDmcc(InputComponent inconDmcc) {
		this.inconDmcc = inconDmcc;
	}

	public InputComponent getInconDlgbgsl() {
		return inconDlgbgsl;
	}

	public void setInconDlgbgsl(InputComponent inconDlgbgsl) {
		this.inconDlgbgsl = inconDlgbgsl;
	}

	public InputComponent getInconGbcz() {
		return inconGbcz;
	}

	public void setInconGbcz(InputComponent inconGbcz) {
		this.inconGbcz = inconGbcz;
	}

	public InputComponent getInconSgdw() {
		return inconSgdw;
	}

	public void setInconSgdw(InputComponent inconSgdw) {
		this.inconSgdw = inconSgdw;
	}

	public InputComponent getInconJgrq() {
		return inconJgrq;
	}

	public void setInconJgrq(InputComponent inconJgrq) {
		this.inconJgrq = inconJgrq;
	}

	public InputComponent getInconTzbh() {
		return inconTzbh;
	}

	public void setInconTzbh(InputComponent inconTzbh) {
		this.inconTzbh = inconTzbh;
	}

	public InputSelectComponent getInscSbzt() {
		return inscSbzt;
	}

	public void setInscSbzt(InputSelectComponent inscSbzt) {
		this.inscSbzt = inscSbzt;
	}

	public InputSelectComponent getInscZyfl() {
		return inscZyfl;
	}

	public void setInscZyfl(InputSelectComponent inscZyfl) {
		this.inscZyfl = inscZyfl;
	}

}
