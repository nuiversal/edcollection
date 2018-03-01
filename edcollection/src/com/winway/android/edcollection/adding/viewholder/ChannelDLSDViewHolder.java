package com.winway.android.edcollection.adding.viewholder;

import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

/**
 * 电缆隧道
 * 
 * @author
 *
 */
public class ChannelDLSDViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_dlsd_head)
	private HeadComponent hcHead;// head组件
	@ViewInject(R.id.inCon_dlsd_sdbh)
	private InputComponent inconsdbh;// 隧道编号
	@ViewInject(R.id.insc_dlsd_ywdw)
	private InputSelectComponent inscYwdw;// 运维单位
	@ViewInject(R.id.insc_dlsd_whbz)
	private InputSelectComponent inscWhbz;// 维护班组
	@ViewInject(R.id.insc_dlsd_sbzt)
	private InputSelectComponent inscSbzt;// 设备状态
	@ViewInject(R.id.insc_dlsd_zcxz)
	private InputSelectComponent inscZcxz;// 资产性质
	@ViewInject(R.id.inCon_dlsd_zcdw)
	private InputComponent inconZcdw;// 资产单位
	@ViewInject(R.id.insc_dlsd_dqtz)
	private InputSelectComponent inscDqtz;// 地区特征
	@ViewInject(R.id.inCon_dlsd_tyrq)
	private InputComponent inconTyrq;// 投运日期
	@ViewInject(R.id.inCon_dlsd_damc)
	private InputComponent inconDamc;// 档案名称
	@ViewInject(R.id.insc_dlsd_jgxs)
	private InputSelectComponent inscJgxs;// 结构型式
	@ViewInject(R.id.insc_dlsd_xgfs)
	private InputSelectComponent inscXgfs;// 悬挂方式
	@ViewInject(R.id.insc_dlsd_jmlx)
	private InputComponent inscJmlx;// 截面类型
	@ViewInject(R.id.inCon_dlsd_dmcc)
	private InputComponent inconSmcc;// 断面尺寸
	// @ViewInject(R.id.inCon_dlsd_sdcd)
	// private InputComponent inconSdcd;// 隧道长度
	@ViewInject(R.id.inCon_dlsd_jsl)
	private InputComponent inconJsl;// 井数量
	@ViewInject(R.id.inCon_dlsd_sgdw)
	private InputComponent inconSgdw;// 施工单位
	@ViewInject(R.id.inCon_dlsd_jgrq)
	private InputComponent inconJgrq;// 峻工日期
	@ViewInject(R.id.inCon_dlsd_tzbh)
	private InputComponent inconTzbh;// 图纸编号
	@ViewInject(R.id.insc_dlsd_zyfl)
	private InputSelectComponent inscZyfl;// 专业分类
	@ViewInject(R.id.insc_dlsd_sfazfhc)
	private InputSelectComponent inscSfazfhc;// 是否安装防火槽盒
	@ViewInject(R.id.inCon_dlsd_sdrl)
	private InputComponent inconSdrl;// 隧道容量
	@ViewInject(R.id.inCon_dlsd_cgsl)
	private InputComponent inconCgsl;// 穿管数量
	@ViewInject(R.id.rl_dlsd)
	private RelativeLayout rlNameConventions;

	public RelativeLayout getRlNameConventions() {
		return rlNameConventions;
	}

	public void setRlNameConventions(RelativeLayout rlNameConventions) {
		this.rlNameConventions = rlNameConventions;
	}
	// @ViewInject(R.id.inCon_dlsd_bz)
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

	public InputComponent getInconsdbh() {
		return inconsdbh;
	}

	public void setInconsdbh(InputComponent inconsdbh) {
		this.inconsdbh = inconsdbh;
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

	public InputSelectComponent getInscSbzt() {
		return inscSbzt;
	}

	public void setInscSbzt(InputSelectComponent inscSbzt) {
		this.inscSbzt = inscSbzt;
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

	public InputSelectComponent getInscJgxs() {
		return inscJgxs;
	}

	public void setInscJgxs(InputSelectComponent inscJgxs) {
		this.inscJgxs = inscJgxs;
	}

	public InputSelectComponent getInscXgfs() {
		return inscXgfs;
	}

	public void setInscXgfs(InputSelectComponent inscXgfs) {
		this.inscXgfs = inscXgfs;
	}

	public InputComponent getInscJmlx() {
		return inscJmlx;
	}

	public void setInscJmlx(InputComponent inscJmlx) {
		this.inscJmlx = inscJmlx;
	}

	public InputComponent getInconSmcc() {
		return inconSmcc;
	}

	public void setInconSmcc(InputComponent inconSmcc) {
		this.inconSmcc = inconSmcc;
	}

	public InputComponent getInconJsl() {
		return inconJsl;
	}

	public void setInconJsl(InputComponent inconJsl) {
		this.inconJsl = inconJsl;
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

	public InputSelectComponent getInscZyfl() {
		return inscZyfl;
	}

	public void setInscZyfl(InputSelectComponent inscZyfl) {
		this.inscZyfl = inscZyfl;
	}

	public InputSelectComponent getInscSfazfhc() {
		return inscSfazfhc;
	}

	public void setInscSfazfhc(InputSelectComponent inscSfazfhc) {
		this.inscSfazfhc = inscSfazfhc;
	}

	public InputComponent getInconSdrl() {
		return inconSdrl;
	}

	public void setInconSdrl(InputComponent inconSdrl) {
		this.inconSdrl = inconSdrl;
	}

	public InputComponent getInconCgsl() {
		return inconCgsl;
	}

	public void setInconCgsl(InputComponent inconCgsl) {
		this.inconCgsl = inconCgsl;
	}

}
