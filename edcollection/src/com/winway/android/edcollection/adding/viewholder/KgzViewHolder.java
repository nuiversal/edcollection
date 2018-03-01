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
 * 开关站
 * 
 * @author lyh
 *
 */
public class KgzViewHolder extends BaseViewHolder {
	@ViewInject(R.id.atta_kgz)
	private AttachmentView attaKgz;// 附件

	@ViewInject(R.id.inCon_switch_station_device_name)
	private InputComponent incomSbmc;// 设备名称

	@ViewInject(R.id.inCon_switch_station_running_no)
	private InputComponent incomYxbh; // 运行编号

	 @ViewInject(R.id.inCon_switch_station_dxmpid)
	private InputComponent incomDxmpId; // 电系铭牌ID

	@ViewInject(R.id.insc_switch_station_voltage)
	private InputSelectComponent inscDydj;// 电压等级

	@ViewInject(R.id.insc_switch_station_city)
	private InputComponent inscSsds;// 所属地市

	@ViewInject(R.id.insc_switch_station_operation_unit)
	private InputSelectComponent inscYwdw;// 运维单位

	@ViewInject(R.id.insc_switch_station_whbz)
	private InputSelectComponent inscWhbz;// 维护班组

	@ViewInject(R.id.insc_switch_station_sbzt)
	private InputSelectComponent inscSbzt;// 设备状态

	@ViewInject(R.id.inCon_switch_station_tyrq)
	private InputComponent incomTyrq;// 设运日期

	@ViewInject(R.id.insc_switch_station_zcxz)
	private InputSelectComponent inscZcxz;// 资产性质

	@ViewInject(R.id.inCon_switch_station_zcdw)
	private InputComponent incomZcdw;// 资产单位

	@ViewInject(R.id.insc_switch_station_sfznbdz)
	private InputSelectComponent inscSfznbdz;// 是否智能变电站

	@ViewInject(R.id.inCon_switch_station_byjcxjgs)
	private InputComponent incomByjcxjgs;// 备用出线间隔数

	@ViewInject(R.id.insc_switch_station_fwfs)
	private InputSelectComponent inscFwfs;// 防误误方式

	@ViewInject(R.id.insc_switch_station_dzzsjb)
	private InputSelectComponent inscDzzsjb;// 电站重要级别

	@ViewInject(R.id.insc_switch_station_sfdw)
	private InputSelectComponent inscSfdw;// 是否代维

	@ViewInject(R.id.insc_switch_station_sfnw)
	private InputSelectComponent inscSfnw;// 是否农网

	@ViewInject(R.id.insc_switch_station_zbfs)
	private InputSelectComponent inscZbfs;// 值班

	@ViewInject(R.id.insc_switch_station_bzfs)
	private InputSelectComponent inscBzfs;// 防误方式

	@ViewInject(R.id.insc_switch_station_wsdj)
	private InputSelectComponent inscWsdj;// 污秽等级

	@ViewInject(R.id.inCon_switch_station_gcbh)
	private InputComponent incomGcbh;// 工程编号

	@ViewInject(R.id.inCon_switch_station_gcmc)
	private InputComponent incomGcmc;// 工程名称

	@ViewInject(R.id.inCon_switch_station_bz)
	private InputComponent incomBz;// 备注

	@ViewInject(R.id.inCon_switch_station_zz)
	private InputComponent incomZz;// 站址

	@ViewInject(R.id.iv_link_device_enter)
	private ImageView ivLinkDeviceEnter;// 关联

	@ViewInject(R.id.btn_link_device_link)
	private Button btnLinkDeviceLink;// 关联

	@ViewInject(R.id.rl_link_device)
	private RelativeLayout rlLinkDevice;// 关联布局

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

	public InputComponent getIncomZz() {
		return incomZz;
	}

	public void setIncomZz(InputComponent incomZz) {
		this.incomZz = incomZz;
	}

	@ViewInject(R.id.hc_kgz_head)
	private HeadComponent hcHead;// head组件

	public AttachmentView getAttaKgz() {
		return attaKgz;
	}

	public void setAttaKgz(AttachmentView attaKgz) {
		this.attaKgz = attaKgz;
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

	public InputComponent getIncomDxmpId() {
		return incomDxmpId;
	}

	public void setIncomDxmpId(InputComponent incomDxmpId) {
		this.incomDxmpId = incomDxmpId;
	}

	public InputSelectComponent getInscDydj() {
		return inscDydj;
	}

	public void setInscDydj(InputSelectComponent inscDydj) {
		this.inscDydj = inscDydj;
	}

	public InputComponent getInscSsds() {
		return inscSsds;
	}

	public void setInscSsds(InputComponent inscSsds) {
		this.inscSsds = inscSsds;
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

	public InputComponent getIncomTyrq() {
		return incomTyrq;
	}

	public void setIncomTyrq(InputComponent incomTyrq) {
		this.incomTyrq = incomTyrq;
	}

	public InputSelectComponent getInscZcxz() {
		return inscZcxz;
	}

	public void setInscZcxz(InputSelectComponent inscZcxz) {
		this.inscZcxz = inscZcxz;
	}

	public InputComponent getIncomZcdw() {
		return incomZcdw;
	}

	public void setIncomZcdw(InputComponent incomZcdw) {
		this.incomZcdw = incomZcdw;
	}

	public InputSelectComponent getInscSfznbdz() {
		return inscSfznbdz;
	}

	public void setInscSfznbdz(InputSelectComponent inscSfznbdz) {
		this.inscSfznbdz = inscSfznbdz;
	}

	public InputComponent getIncomByjcxjgs() {
		return incomByjcxjgs;
	}

	public void setIncomByjcxjgs(InputComponent incomByjcxjgs) {
		this.incomByjcxjgs = incomByjcxjgs;
	}

	public InputSelectComponent getInscFwfs() {
		return inscFwfs;
	}

	public void setInscFwfs(InputSelectComponent inscFwfs) {
		this.inscFwfs = inscFwfs;
	}

	public InputSelectComponent getInscDzzsjb() {
		return inscDzzsjb;
	}

	public void setInscDzzsjb(InputSelectComponent inscDzzsjb) {
		this.inscDzzsjb = inscDzzsjb;
	}

	public InputSelectComponent getInscSfdw() {
		return inscSfdw;
	}

	public void setInscSfdw(InputSelectComponent inscSfdw) {
		this.inscSfdw = inscSfdw;
	}

	public InputSelectComponent getInscSfnw() {
		return inscSfnw;
	}

	public void setInscSfnw(InputSelectComponent inscSfnw) {
		this.inscSfnw = inscSfnw;
	}

	public InputSelectComponent getInscZbfs() {
		return inscZbfs;
	}

	public void setInscZbfs(InputSelectComponent inscZbfs) {
		this.inscZbfs = inscZbfs;
	}

	public InputSelectComponent getInscBzfs() {
		return inscBzfs;
	}

	public void setInscBzfs(InputSelectComponent inscBzfs) {
		this.inscBzfs = inscBzfs;
	}

	public InputSelectComponent getInscWsdj() {
		return inscWsdj;
	}

	public void setInscWsdj(InputSelectComponent inscWsdj) {
		this.inscWsdj = inscWsdj;
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

	public InputComponent getIncomBz() {
		return incomBz;
	}

	public void setIncomBz(InputComponent incomBz) {
		this.incomBz = incomBz;
	}

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

}
