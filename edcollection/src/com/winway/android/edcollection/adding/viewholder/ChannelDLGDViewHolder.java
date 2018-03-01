package com.winway.android.edcollection.adding.viewholder;

import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

/**
 * 电缆管道
 * 
 * @author
 *
 */
public class ChannelDLGDViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_dlgd_head)
	private HeadComponent hcHead;// head组件
	@ViewInject(R.id.inCon_dlgd_mbmc)
	private InputComponent inconmbmc;// 模板名称
	@ViewInject(R.id.inCon_dlgd_row)
	private InputComponent inconRow;// 行数
	@ViewInject(R.id.inCon_dlgd_col)
	private InputComponent inconCol;// 列数
	@ViewInject(R.id.inCon_dlgd_gdbh)
	private InputComponent inconGdbh;// 管道编号
	@ViewInject(R.id.insc_dlgd_ywdw)
	private InputSelectComponent inscYwdw;// 运维单位
	@ViewInject(R.id.insc_dlgd_whbz)
	private InputSelectComponent inscWhbz;// 维护班组
	@ViewInject(R.id.inCon_dlgd_zcxz)
	private InputSelectComponent inscZcxz;// 资产性质
	@ViewInject(R.id.inCon_dlgd_zcdw)
	private InputComponent inconZcdw;// 资产单位
	@ViewInject(R.id.inCon_dlgd_dqtz)
	private InputSelectComponent inscDqtz;// 地区特征
	@ViewInject(R.id.insc_dlgd_sbzt)
	private InputSelectComponent inscSbzt;// 设备状态
	@ViewInject(R.id.inCon_dlgd_tyrq)
	private InputComponent inconTyrq;// 投运日期
	@ViewInject(R.id.inCon_dlgd_damc)
	private InputComponent inconDamc;// 档案名称
	@ViewInject(R.id.inCon_dlgd_jmlx)
	private InputComponent inconJmlx;// 截面类型
	@ViewInject(R.id.inCon_dlgd_cl)
	private InputComponent inconCl;// 材料
	@ViewInject(R.id.inCon_dlgd_gj)
	private InputComponent inconGj;// 管径
	// @ViewInject(R.id.inCon_dlgd_gdcd)
	// private InputComponent inconGdcd;// 管道长度
	@ViewInject(R.id.inCon_dlgd_cgsl)
	private InputComponent inconCgsl;// 穿管数量
	@ViewInject(R.id.inCon_dlgd_jsl)
	private InputComponent inconJsl;// 井数量
	@ViewInject(R.id.insc_dlgd_gdlx)
	private InputSelectComponent inscGdlx;// 管道类型
	@ViewInject(R.id.inCon_dlgd_sgdw)
	private InputComponent inconSgdw;// 施工单位
	@ViewInject(R.id.insc_dlgd_sgfs)
	private InputSelectComponent inscSgfs;// 施工方式
	@ViewInject(R.id.inCon_dlgd_jgrq)
	private InputComponent inconJgrq;// 峻工日期
	@ViewInject(R.id.inCon_dlgd_tzbh)
	private InputComponent inconTzbh;// 图纸编号
	@ViewInject(R.id.insc_dlgd_zyfl)
	private InputSelectComponent inscZyfl;// 专业分类
	@ViewInject(R.id.inCon_dlgd_gdrl)
	private InputComponent inconGdrl;// 管道容量
	
	@ViewInject(R.id.rl_dlgd)
	private RelativeLayout rlNameConventions;

	public RelativeLayout getRlNameConventions() {
		return rlNameConventions;
	}

	public void setRlNameConventions(RelativeLayout rlNameConventions) {
		this.rlNameConventions = rlNameConventions;
	}
	// @ViewInject(R.id.inCon_dlgd_bz)
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

	public InputComponent getInconmbmc() {
		return inconmbmc;
	}

	public void setInconmbmc(InputComponent inconmbmc) {
		this.inconmbmc = inconmbmc;
	}

	public InputComponent getInconRow() {
		return inconRow;
	}

	public void setInconRow(InputComponent inconRow) {
		this.inconRow = inconRow;
	}

	public InputComponent getInconCol() {
		return inconCol;
	}

	public void setInconCol(InputComponent inconCol) {
		this.inconCol = inconCol;
	}

	public InputComponent getInconGdbh() {
		return inconGdbh;
	}

	public void setInconGdbh(InputComponent inconGdbh) {
		this.inconGdbh = inconGdbh;
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

	public InputComponent getInconZcdw() {
		return inconZcdw;
	}

	public void setInconZcdw(InputComponent inconZcdw) {
		this.inconZcdw = inconZcdw;
	}

	public InputSelectComponent getInscZcxz() {
		return inscZcxz;
	}

	public void setInscZcxz(InputSelectComponent inscZcxz) {
		this.inscZcxz = inscZcxz;
	}

	public InputSelectComponent getInscDqtz() {
		return inscDqtz;
	}

	public void setInscDqtz(InputSelectComponent inscDqtz) {
		this.inscDqtz = inscDqtz;
	}

	public InputComponent getInconTyrq() {
		return inconTyrq;
	}

	public void setInconTyrq(InputComponent inconTyrq) {
		this.inconTyrq = inconTyrq;
	}

	public InputComponent getInconDamc() {
		return inconDamc;
	}

	public void setInconDamc(InputComponent inconDamc) {
		this.inconDamc = inconDamc;
	}

	public InputComponent getInconJmlx() {
		return inconJmlx;
	}

	public void setInconJmlx(InputComponent inconJmlx) {
		this.inconJmlx = inconJmlx;
	}

	public InputComponent getInconCl() {
		return inconCl;
	}

	public void setInconCl(InputComponent inconCl) {
		this.inconCl = inconCl;
	}

	public InputComponent getInconGj() {
		return inconGj;
	}

	public void setInconGj(InputComponent inconGj) {
		this.inconGj = inconGj;
	}

	public InputComponent getInconCgsl() {
		return inconCgsl;
	}

	public void setInconCgsl(InputComponent inconCgsl) {
		this.inconCgsl = inconCgsl;
	}

	public InputComponent getInconJsl() {
		return inconJsl;
	}

	public void setInconJsl(InputComponent inconJsl) {
		this.inconJsl = inconJsl;
	}

	public InputSelectComponent getInscGdlx() {
		return inscGdlx;
	}

	public void setInscGdlx(InputSelectComponent inscGdlx) {
		this.inscGdlx = inscGdlx;
	}

	public InputComponent getInconSgdw() {
		return inconSgdw;
	}

	public void setInconSgdw(InputComponent inconSgdw) {
		this.inconSgdw = inconSgdw;
	}

	public InputSelectComponent getInscSgfs() {
		return inscSgfs;
	}

	public void setInscSgfs(InputSelectComponent inscSgfs) {
		this.inscSgfs = inscSgfs;
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

	public InputComponent getInconGdrl() {
		return inconGdrl;
	}

	public void setInconGdrl(InputComponent inconGdrl) {
		this.inconGdrl = inconGdrl;
	}

}
