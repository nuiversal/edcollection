package com.winway.android.edcollection.adding.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

import android.widget.Button;

/**
 * 通道设备共用的字段
 * 
 * @author lyh
 * @data 2017年3月5日
 */
public class CommentChannelViewHolder {

	@ViewInject(R.id.btn_link_label_label)
	private Button btnLinkLabelLink; // 标签采集

	@ViewInject(R.id.inCom_pipe_jacking_mc)
	private InputComponent inconMc;// 名称

	@ViewInject(R.id.inCom_pipe_jacking_tdkd)
	private InputComponent inconTdkd; // 通道宽度

	@ViewInject(R.id.inCom_pipe_jacking_tdsd)
	private InputComponent inconTdsd; // 通道深度

	@ViewInject(R.id.insc_pipe_jacking_tdcz)
	private InputSelectComponent inscTdcz;// 通道材质
	
	@ViewInject(R.id.btn_namingConventions)
	private Button btnNamingConventions; // 命名规范

	@ViewInject(R.id.insc_pipe_jacking_dydj)
	private InputSelectComponent inscDydj;// 电压等级

	@ViewInject(R.id.inCon_channel_comment_ssds)
	private InputComponent inconSsds;// 所属地市

	@ViewInject(R.id.insc_pipe_jacking_zt)
	private InputSelectComponent inscZt;// 状态

	@ViewInject(R.id.inCom_pipe_jacking_syrl)
	private InputComponent inconSyrl;// 剩余容量

	@ViewInject(R.id.inCom_pipe_jacking_zrl)
	private InputComponent inconZrl;// 总容量

	@ViewInject(R.id.inCom_pipe_jacking_sszrq)
	private InputComponent inconSszrq;// 所属责任区

	@ViewInject(R.id.inCom_pipe_jacking_yxdw)
	private InputComponent inconYxdw;// 运行单位

	@ViewInject(R.id.inCom_pipe_jacking_qdsbbh)
	private InputComponent inconQdsbbh;// 起点设备编号

	@ViewInject(R.id.inCom_pipe_jacking_zdsbbh)
	private InputSelectComponent inconZdsbbh;// 终点设备编号

	@ViewInject(R.id.inCom_pipe_jacking_tdcd)
	private InputComponent inconTdcd;// 通道长度

	@ViewInject(R.id.inCom_pipe_jacking_bz)
	private InputComponent inconBz;// 备注

	public InputComponent getInconTdkd() {
		return inconTdkd;
	}

	public InputComponent getInconTdsd() {
		return inconTdsd;
	}

	public InputSelectComponent getInscTdcz() {
		return inscTdcz;
	}

	public InputComponent getInconSsds() {
		return inconSsds;
	}

	public void setInconSsds(InputComponent inconSsds) {
		this.inconSsds = inconSsds;
	}

	public Button getBtnLinkLabelLink() {
		return btnLinkLabelLink;
	}

	public void setBtnLinkLabelLink(Button btnLinkLabelLink) {
		this.btnLinkLabelLink = btnLinkLabelLink;
	}

	public InputComponent getInconTdcd() {
		return inconTdcd;
	}

	public void setInconTdcd(InputComponent inconTdcd) {
		this.inconTdcd = inconTdcd;
	}

	public InputComponent getInconBz() {
		return inconBz;
	}

	public void setInconBz(InputComponent inconBz) {
		this.inconBz = inconBz;
	}

	public InputComponent getInconYxdw() {
		return inconYxdw;
	}

	public void setInconYxdw(InputComponent inconYxdw) {
		this.inconYxdw = inconYxdw;
	}

	public InputComponent getInconMc() {
		return inconMc;
	}

	public void setInconMc(InputComponent inconMc) {
		this.inconMc = inconMc;
	}

	public InputSelectComponent getInscDydj() {
		return inscDydj;
	}

	public void setInscDydj(InputSelectComponent inscDydj) {
		this.inscDydj = inscDydj;
	}

	public InputSelectComponent getInscZt() {
		return inscZt;
	}

	public void setInscZt(InputSelectComponent inscZt) {
		this.inscZt = inscZt;
	}

	public InputComponent getInconSyrl() {
		return inconSyrl;
	}

	public void setInconSyrl(InputComponent inconSyrl) {
		this.inconSyrl = inconSyrl;
	}

	public InputComponent getInconZrl() {
		return inconZrl;
	}

	public void setInconZrl(InputComponent inconZrl) {
		this.inconZrl = inconZrl;
	}

	public InputComponent getInconSszrq() {
		return inconSszrq;
	}

	public void setInconSszrq(InputComponent inconSszrq) {
		this.inconSszrq = inconSszrq;
	}

	public InputComponent getInconQdsbbh() {
		return inconQdsbbh;
	}

	public void setInconQdsbbh(InputComponent inconQdsbbh) {
		this.inconQdsbbh = inconQdsbbh;
	}

	public InputSelectComponent getInconZdsbbh() {
		return inconZdsbbh;
	}

	public void setInconZdsbbh(InputSelectComponent inconZdsbbh) {
		this.inconZdsbbh = inconZdsbbh;
	}

	public Button getBtnNamindConventions() {
		return btnNamingConventions;
	}

	public void setBtnNamindConventions(Button btnNamingConventions) {
		this.btnNamingConventions = btnNamingConventions;
	}

}
