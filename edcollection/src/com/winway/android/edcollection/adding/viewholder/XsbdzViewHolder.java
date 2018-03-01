package com.winway.android.edcollection.adding.viewholder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;
import com.winway.android.ewidgets.attachment.AttachmentView;

/**
 * 箱式变电站
 * 
 * @author lyh
 * @data 2017年2月15日
 */
public class XsbdzViewHolder extends BaseViewHolder {

	@ViewInject(R.id.atta_xsbdz)
	private AttachmentView attaXsbdz;// 附件

	@ViewInject(R.id.hc_xsbdz_head)
	private HeadComponent hcHead;// head组件

	// @ViewInject(R.id.inCon_xsbdz_sbbs)
	// private InputComponent inconSbbs; // 设备标识

	@ViewInject(R.id.inCon_xsbdz_sbmc)
	private InputComponent inconSbmc; // 设备名称

	@ViewInject(R.id.inCon_xsbdz_yxbh)
	private InputComponent inconYxbh; // 运行编号

	@ViewInject(R.id.inCon_xsbdz_dxmpid)
	private InputComponent inconDxmpid; // 电系铭牌id
	
	@ViewInject(R.id.inSelCom_xsbdz_dydj)
	private InputSelectComponent inSelConDydj; // 电压等级

	@ViewInject(R.id.inSelCom_xsbdz_ssds)
	private InputComponent inSelConSsds; // 所属地市

	@ViewInject(R.id.inSelCom_xsbdz_ywdw)
	private InputSelectComponent inSelConYwdw; // 运维单位

	@ViewInject(R.id.inSelCom_xsbdz_whbz)
	private InputSelectComponent inSelConWhbz; // 维护班组

	@ViewInject(R.id.inSelCom_xsbdz_sbzt)
	private InputSelectComponent inSelConSbzt; // 设备状态

	@ViewInject(R.id.inSelCom_xsbdz_sfdw)
	private InputSelectComponent inSelConSfdw; // 是否代维

	@ViewInject(R.id.inSelCom_xsbdz_sfnw)
	private InputSelectComponent inSelConSfnw; // 是否农网

	@ViewInject(R.id.inSelCom_xsbdz_xblx)
	private InputSelectComponent inSelConXblx; // 箱变类型

	@ViewInject(R.id.inSelCom_xsbdz_sfjyhw)
	private InputSelectComponent inSelConSfjyhwg; // 是否具有环网

	@ViewInject(R.id.inCon_xsbdz_xh)
	private InputComponent inconXh; // 型号

	@ViewInject(R.id.inCon_xsbdz_sccj)
	private InputComponent inconSccj; // 生产厂家

	@ViewInject(R.id.inCon_xsbdz_ccbh)
	private InputComponent inconCcbh; // 出厂编号

	@ViewInject(R.id.inCon_xsbdz_ccrq)
	private InputComponent inconCcrq; // 出厂日期

	@ViewInject(R.id.inCon_xsbdz_tyrq)
	private InputComponent inconTyrq; // 投运日期

	@ViewInject(R.id.inCon_xsbdz_pbzrl)
	private InputComponent inconPbzrl; // 配变总容量

	@ViewInject(R.id.inCon_xsbdz_jddz)
	private InputComponent inconJddz; // 接地电阻

	@ViewInject(R.id.inCon_xsbdz_zz)
	private InputComponent inconZz; // 站址

	@ViewInject(R.id.inSelCom_xsbdz_dqtz)
	private InputSelectComponent inSelConDqtz; // 地区特征

	@ViewInject(R.id.inSelCom_xsbdz_zycd)
	private InputSelectComponent inSelConZycd; // 重要程度

	@ViewInject(R.id.inSelCom_xsbdz_zcxz)
	private InputSelectComponent inSelConZcxz; // 资产性质

	@ViewInject(R.id.inCon_xsbdz_zcdw)
	private InputComponent inconZcdw; // 资产单位

	@ViewInject(R.id.inCon_xsbdz_gcbh)
	private InputComponent inconGcbh; // 工程编号

	@ViewInject(R.id.inCon_xsbdz_gcmc)
	private InputComponent inconGcmc; // 工程名称

	@ViewInject(R.id.inSelCom_xsbdz_sbzjfs)
	private InputSelectComponent inSelComSbzjfs; // 设备增加误方式

	@ViewInject(R.id.inCon_xsbdz_bz)
	private InputComponent inconBz; // 备注
	
	@ViewInject(R.id.iv_link_device_enter)
	private ImageView ivLinkDeviceEnter;// 关联

	@ViewInject(R.id.btn_link_device_link)
	private Button btnLinkDeviceLink;// 关联
	
	@ViewInject(R.id.rl_link_device)
	private RelativeLayout rlLinkDevice;// 关联布局

	public InputComponent getInconDxmpid() {
		return inconDxmpid;
	}

	public void setInconDxmpid(InputComponent inconDxmpid) {
		this.inconDxmpid = inconDxmpid;
	}

	public RelativeLayout getRlLinkDevice() {
		return rlLinkDevice;
	}

	public void setRlLinkDevice(RelativeLayout rlLinkDevice) {
		this.rlLinkDevice = rlLinkDevice;
	}

	public ImageView getIvLinkDeviceEnter() {
		return ivLinkDeviceEnter;
	}

	public void setIvLinkDeviceEnter(ImageView ivLinkDeviceEnter) {
		this.ivLinkDeviceEnter = ivLinkDeviceEnter;
	}

	public Button getBtnLinkDeviceLink() {
		return btnLinkDeviceLink;
	}

	public void setBtnLinkDeviceLink(Button btnLinkDeviceLink) {
		this.btnLinkDeviceLink = btnLinkDeviceLink;
	}

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public InputComponent getInconSbmc() {
		return inconSbmc;
	}

	public void setInconSbmc(InputComponent inconSbmc) {
		this.inconSbmc = inconSbmc;
	}

	public InputComponent getInconYxbh() {
		return inconYxbh;
	}

	public void setInconYxbh(InputComponent inconYxbh) {
		this.inconYxbh = inconYxbh;
	}

	public InputComponent getInSelConSsds() {
		return inSelConSsds;
	}

	public void setInSelConSsds(InputComponent inSelConSsds) {
		this.inSelConSsds = inSelConSsds;
	}

	public InputSelectComponent getInSelConYwdw() {
		return inSelConYwdw;
	}

	public void setInSelConYwdw(InputSelectComponent inSelConYwdw) {
		this.inSelConYwdw = inSelConYwdw;
	}

	public InputSelectComponent getInSelConWhbz() {
		return inSelConWhbz;
	}

	public void setInSelConWhbz(InputSelectComponent inSelConWhbz) {
		this.inSelConWhbz = inSelConWhbz;
	}

	public InputComponent getInconXh() {
		return inconXh;
	}

	public void setInconXh(InputComponent inconXh) {
		this.inconXh = inconXh;
	}

	public InputComponent getInconSccj() {
		return inconSccj;
	}

	public void setInconSccj(InputComponent inconSccj) {
		this.inconSccj = inconSccj;
	}

	public InputComponent getInconCcbh() {
		return inconCcbh;
	}

	public void setInconCcbh(InputComponent inconCcbh) {
		this.inconCcbh = inconCcbh;
	}

	public InputComponent getInconCcrq() {
		return inconCcrq;
	}

	public void setInconCcrq(InputComponent inconCcrq) {
		this.inconCcrq = inconCcrq;
	}

	public InputComponent getInconTyrq() {
		return inconTyrq;
	}

	public void setInconTyrq(InputComponent inconTyrq) {
		this.inconTyrq = inconTyrq;
	}

	public InputComponent getInconPbzrl() {
		return inconPbzrl;
	}

	public void setInconPbzrl(InputComponent inconPbzrl) {
		this.inconPbzrl = inconPbzrl;
	}

	public InputComponent getInconJddz() {
		return inconJddz;
	}

	public void setInconJddz(InputComponent inconJddz) {
		this.inconJddz = inconJddz;
	}

	public InputComponent getInconZz() {
		return inconZz;
	}

	public void setInconZz(InputComponent inconZz) {
		this.inconZz = inconZz;
	}

	public InputSelectComponent getInSelConDydj() {
		return inSelConDydj;
	}

	public void setInSelConDydj(InputSelectComponent inSelConDydj) {
		this.inSelConDydj = inSelConDydj;
	}

	public InputSelectComponent getInSelConSbzt() {
		return inSelConSbzt;
	}

	public void setInSelConSbzt(InputSelectComponent inSelConSbzt) {
		this.inSelConSbzt = inSelConSbzt;
	}

	public InputSelectComponent getInSelConSfdw() {
		return inSelConSfdw;
	}

	public void setInSelConSfdw(InputSelectComponent inSelConSfdw) {
		this.inSelConSfdw = inSelConSfdw;
	}

	public InputSelectComponent getInSelConSfnw() {
		return inSelConSfnw;
	}

	public void setInSelConSfnw(InputSelectComponent inSelConSfnw) {
		this.inSelConSfnw = inSelConSfnw;
	}

	public InputSelectComponent getInSelConXblx() {
		return inSelConXblx;
	}

	public void setInSelConXblx(InputSelectComponent inSelConXblx) {
		this.inSelConXblx = inSelConXblx;
	}

	public InputSelectComponent getInSelConSfjyhwg() {
		return inSelConSfjyhwg;
	}

	public void setInSelConSfjyhwg(InputSelectComponent inSelConSfjyhwg) {
		this.inSelConSfjyhwg = inSelConSfjyhwg;
	}

	public InputSelectComponent getInSelConDqtz() {
		return inSelConDqtz;
	}

	public void setInSelConDqtz(InputSelectComponent inSelConDqtz) {
		this.inSelConDqtz = inSelConDqtz;
	}

	public InputSelectComponent getInSelConZycd() {
		return inSelConZycd;
	}

	public void setInSelConZycd(InputSelectComponent inSelConZycd) {
		this.inSelConZycd = inSelConZycd;
	}

	public InputSelectComponent getInSelConZcxz() {
		return inSelConZcxz;
	}

	public void setInSelConZcxz(InputSelectComponent inSelConZcxz) {
		this.inSelConZcxz = inSelConZcxz;
	}

	public InputComponent getInconZcdw() {
		return inconZcdw;
	}

	public void setInconZcdw(InputComponent inconZcdw) {
		this.inconZcdw = inconZcdw;
	}

	public InputComponent getInconGcbh() {
		return inconGcbh;
	}

	public void setInconGcbh(InputComponent inconGcbh) {
		this.inconGcbh = inconGcbh;
	}

	public InputComponent getInconGcmc() {
		return inconGcmc;
	}

	public void setInconGcmc(InputComponent inconGcmc) {
		this.inconGcmc = inconGcmc;
	}

	public InputSelectComponent getInSelComSbzjfs() {
		return inSelComSbzjfs;
	}

	public void setInSelComSbzjfs(InputSelectComponent inSelComSbzjfs) {
		this.inSelComSbzjfs = inSelComSbzjfs;
	}

	public InputComponent getInconBz() {
		return inconBz;
	}

	public void setInconBz(InputComponent inconBz) {
		this.inconBz = inconBz;
	}

	public AttachmentView getAttaXsbdz() {
		return attaXsbdz;
	}

	public void setAttaXsbdz(AttachmentView attaXsbdz) {
		this.attaXsbdz = attaXsbdz;
	}

}
