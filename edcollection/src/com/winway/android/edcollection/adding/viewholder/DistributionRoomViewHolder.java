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
 * 配电室
 *
 * @author lyh
 * @data 2017年2月13日
 */
public class DistributionRoomViewHolder extends BaseViewHolder {

	@ViewInject(R.id.hc_tower_head)
	private HeadComponent hcHead;// head组件

	// @ViewInject(R.id.inCon_channel_pds_sbbs)
	// private InputComponent incomSbbs; // 设备标识

	@ViewInject(R.id.inCon_channel_pds_sbmc)
	private InputComponent incomSbmc; // 设备名称

	@ViewInject(R.id.inCon_channel_pds_yxbh)
	private InputComponent incomYxbh; // 运行编号
	
	@ViewInject(R.id.inCon_channel_pds_dxmpid)
	private InputComponent incomDxmpid; // 运行编号

	@ViewInject(R.id.inSelCom_channel_pds_dydj)
	private InputSelectComponent inSelcomDydj; // 电压等级

	@ViewInject(R.id.inCon_channel_pds_ssds)
	private InputComponent inSelComSsds; // 所属地市

	@ViewInject(R.id.inSelCom_channel_pds_ywdw)
	private InputSelectComponent inSelComYwdw; // 运维单位

	@ViewInject(R.id.inSelCom_channel_pds_whbz)
	private InputSelectComponent inSelComWhbz; // 维护班组

	@ViewInject(R.id.inSelCom_channel_pds_sbzt)
	private InputSelectComponent inSelcomSbzt; // 设备状态

	@ViewInject(R.id.inCon_channel_pds_tyrq)
	private InputComponent incomTyrq; // 投运日期

	@ViewInject(R.id.inSelCom_channel_pds_sfnw)
	private InputSelectComponent inSelcomSfnw; // 是否农网

	@ViewInject(R.id.inSelCom_channel_pds_sfdw)
	private InputSelectComponent inSelcomSfdw; // 是否代维

	@ViewInject(R.id.inCon_channel_pds_pbts)
	private InputComponent incomPbts; // 配变台数

	@ViewInject(R.id.inCon_channel_pds_pbzrl)
	private InputComponent incomPbzrl; // 配变总容量

	@ViewInject(R.id.inCon_channel_pds_wgbcrl)
	private InputComponent incomWgbcrl; // 无功补偿容量

	@ViewInject(R.id.inSelCom_channel_pds_fwfs)
	private InputSelectComponent inSelComFwfs; // 防误方式

	@ViewInject(R.id.inSelCom_channel_pds_sfdljzw)
	private InputSelectComponent inSelcomSfdljzw; // 是否独立建筑物

	@ViewInject(R.id.inSelCom_channel_pds_sfdxz)
	private InputSelectComponent inSelcomSfdxz; // 是否地下站

	@ViewInject(R.id.inCon_channel_pds_jddz)
	private InputComponent incomJddz; // 接地电阻

	@ViewInject(R.id.inCon_channel_pds_zz)
	private InputComponent incomZz; // 站址

	@ViewInject(R.id.inSelCom_channel_pds_dqtz)
	private InputSelectComponent inSelcomDqtz; // 地区特征

	@ViewInject(R.id.inSelCom_channel_pds_zycd)
	private InputSelectComponent inSelcomZycd; // 重要程度

	@ViewInject(R.id.inCon_channel_pds_gcbh)
	private InputComponent incomGcbh; // 工程编号

	@ViewInject(R.id.inCon_channel_pds_gcmc)
	private InputComponent incomGcmc; // 工程名称

	@ViewInject(R.id.inSelCom_channel_pds_zcxz)
	private InputSelectComponent inSelcomZcxz; // 资产性质

	@ViewInject(R.id.inCon_channel_pds_zcdw)
	private InputComponent incomZcdw; // 资产单位

	@ViewInject(R.id.inCon_channel_pds_bz)
	private InputComponent incomBz; // 备注

	@ViewInject(R.id.av_pds_attachment)
	private AttachmentView avAttachment;// 附件

	@ViewInject(R.id.iv_link_device_enter)
	private ImageView ivLinkDeviceEnter;// 关联

	@ViewInject(R.id.btn_link_device_link)
	private Button btnLinkDeviceLink;// 关联
	
	@ViewInject(R.id.rl_link_device)
	private RelativeLayout rlLinkDevice;// 关联布局

	public InputComponent getIncomDxmpid() {
		return incomDxmpid;
	}

	public void setIncomDxmpid(InputComponent incomDxmpid) {
		this.incomDxmpid = incomDxmpid;
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

	public AttachmentView getAvAttachment() {
		return avAttachment;
	}

	public void setAvAttachmFent(AttachmentView avAttachment) {
		this.avAttachment = avAttachment;
	}

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public InputComponent getIncomSbmc() {
		return incomSbmc;
	}

	public void setIncomSbmc(InputComponent incomSbmc) {
		this.incomSbmc = incomSbmc;
	}

	public InputComponent getIncomYxbh() {
		return incomYxbh;
	}

	public void setIncomYxbh(InputComponent incomYxbh) {
		this.incomYxbh = incomYxbh;
	}

	public InputComponent getInSelComSsds() {
		return inSelComSsds;
	}

	public void setInSelComSsds(InputComponent inSelComSsds) {
		this.inSelComSsds = inSelComSsds;
	}

	public InputSelectComponent getInSelComYwdw() {
		return inSelComYwdw;
	}

	public void setInSelComYwdw(InputSelectComponent inSelComYwdw) {
		this.inSelComYwdw = inSelComYwdw;
	}

	public InputSelectComponent getInSelComWhbz() {
		return inSelComWhbz;
	}

	public void setInSelComWhbz(InputSelectComponent inSelComWhbz) {
		this.inSelComWhbz = inSelComWhbz;
	}

	public InputComponent getIncomTyrq() {
		return incomTyrq;
	}

	public void setIncomTyrq(InputComponent incomTyrq) {
		this.incomTyrq = incomTyrq;
	}

	public InputComponent getIncomPbts() {
		return incomPbts;
	}

	public void setIncomPbts(InputComponent incomPbts) {
		this.incomPbts = incomPbts;
	}

	public InputComponent getIncomPbzrl() {
		return incomPbzrl;
	}

	public void setIncomPbzrl(InputComponent incomPbzrl) {
		this.incomPbzrl = incomPbzrl;
	}

	public InputComponent getIncomWgbcrl() {
		return incomWgbcrl;
	}

	public void setIncomWgbcrl(InputComponent incomWgbcrl) {
		this.incomWgbcrl = incomWgbcrl;
	}

	public InputSelectComponent getInSelComFwfs() {
		return inSelComFwfs;
	}

	public void setInSelComFwfs(InputSelectComponent inSelComFwfs) {
		this.inSelComFwfs = inSelComFwfs;
	}

	public InputComponent getIncomJddz() {
		return incomJddz;
	}

	public void setIncomJddz(InputComponent incomJddz) {
		this.incomJddz = incomJddz;
	}

	public InputComponent getIncomZz() {
		return incomZz;
	}

	public void setIncomZz(InputComponent incomZz) {
		this.incomZz = incomZz;
	}

	public InputComponent getIncomGcbh() {
		return incomGcbh;
	}

	public void setIncomGcbh(InputComponent incomGcbh) {
		this.incomGcbh = incomGcbh;
	}

	public InputComponent getIncomGcmc() {
		return incomGcmc;
	}

	public void setIncomGcmc(InputComponent incomGcmc) {
		this.incomGcmc = incomGcmc;
	}

	public InputComponent getIncomZcdw() {
		return incomZcdw;
	}

	public void setIncomZcdw(InputComponent incomZcdw) {
		this.incomZcdw = incomZcdw;
	}

	public InputComponent getIncomBz() {
		return incomBz;
	}

	public void setIncomBz(InputComponent incomBz) {
		this.incomBz = incomBz;
	}

	public InputSelectComponent getInSelcomDydj() {
		return inSelcomDydj;
	}

	public void setInSelcomDydj(InputSelectComponent inSelcomDydj) {
		this.inSelcomDydj = inSelcomDydj;
	}

	public InputSelectComponent getInSelcomSbzt() {
		return inSelcomSbzt;
	}

	public void setInSelcomSbzt(InputSelectComponent inSelcomSbzt) {
		this.inSelcomSbzt = inSelcomSbzt;
	}

	public InputSelectComponent getInSelcomSfnw() {
		return inSelcomSfnw;
	}

	public void setInSelcomSfnw(InputSelectComponent inSelcomSfnw) {
		this.inSelcomSfnw = inSelcomSfnw;
	}

	public InputSelectComponent getInSelcomSfdw() {
		return inSelcomSfdw;
	}

	public void setInSelcomSfdw(InputSelectComponent inSelcomSfdw) {
		this.inSelcomSfdw = inSelcomSfdw;
	}

	public InputSelectComponent getInSelcomSfdljzw() {
		return inSelcomSfdljzw;
	}

	public void setInSelcomSfdljzw(InputSelectComponent inSelcomSfdljzw) {
		this.inSelcomSfdljzw = inSelcomSfdljzw;
	}

	public InputSelectComponent getInSelcomSfdxz() {
		return inSelcomSfdxz;
	}

	public void setInSelcomSfdxz(InputSelectComponent inSelcomSfdxz) {
		this.inSelcomSfdxz = inSelcomSfdxz;
	}

	public InputSelectComponent getInSelcomDqtz() {
		return inSelcomDqtz;
	}

	public void setInSelcomDqtz(InputSelectComponent inSelcomDqtz) {
		this.inSelcomDqtz = inSelcomDqtz;
	}

	public InputSelectComponent getInSelcomZycd() {
		return inSelcomZycd;
	}

	public void setInSelcomZycd(InputSelectComponent inSelcomZycd) {
		this.inSelcomZycd = inSelcomZycd;
	}

	public InputSelectComponent getInSelcomZcxz() {
		return inSelcomZcxz;
	}

	public void setInSelcomZcxz(InputSelectComponent inSelcomZcxz) {
		this.inSelcomZcxz = inSelcomZcxz;
	}

	public void setAvAttachment(AttachmentView avAttachment) {
		this.avAttachment = avAttachment;
	}

}
