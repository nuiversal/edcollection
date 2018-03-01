package com.winway.android.edcollection.adding.viewholder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;
import com.winway.android.ewidgets.attachment.AttachmentView;

/**
 * 工井
 *
 * @author lyh
 * @data 2017年2月15日
 */
public class GjViewHolder extends BaseViewHolder {

	@ViewInject(R.id.av_gj_attachment)
	private AttachmentView avAttachment;// 附件

	@ViewInject(R.id.hc_gj_head)
	private HeadComponent hcHead;// head组件

	@ViewInject(R.id.inCon_gj_dmxx)
	private InputComponent incomDmxx;// 断面信息

	@ViewInject(R.id.inCon_gj_gcmc)
	private InputComponent incomGcmc;// 工程名称

	@ViewInject(R.id.btn_namingConventions)
	// 命名规范
	private Button btnNameingConventions;

	@ViewInject(R.id.inCon_gj_bkhd)
	private InputComponent incomBkhd;// 盖板厚度

	@ViewInject(R.id.inCon_gj_gbks)
	private InputComponent incomGbks;// 盖板块数

	@ViewInject(R.id.insc_gj_ssds)
	private InputComponent inscSsds;// 所属地市

	@ViewInject(R.id.insc_gj_ywdw)
	private InputSelectComponent inscYwdw;// 运维单位

	@ViewInject(R.id.insc_gj_whbz)
	private InputSelectComponent inscWhbz;// 维护班组

	@ViewInject(R.id.inCon_gj_sbmc)
	private InputComponent incomSbmc;// 设备名称

	@ViewInject(R.id.inCon_gj_sszrq)
	private InputComponent incomSszrq;// 所属责任区

	@ViewInject(R.id.insc_gj_gjxz)
	private InputSelectComponent inscGjxz;// 工井形状

	@ViewInject(R.id.insc_gj_dqtz)
	private InputSelectComponent inscDqtz;// 地区特征

	@ViewInject(R.id.inCon_gj_pos)
	private InputComponent incomDlwz;// 地理位置

	@ViewInject(R.id.inCon_gj_jwz)
	private InputComponent incomJwz;// 井位置

	@ViewInject(R.id.insc_gj_jlx)
	private InputSelectComponent inscJlx;// 井类型

	@ViewInject(R.id.insc_gj_jg)
	private InputSelectComponent inscJg;// 结构

	@ViewInject(R.id.inCon_gj_jmgc)
	private InputComponent incomJmgc;// 井面高程(m)

	@ViewInject(R.id.inCon_gj_ndgc)
	private InputComponent incomNdgc;// 内底高程(m)

	@ViewInject(R.id.insc_gj_jgxz)
	private InputSelectComponent inscJgxz;// 井盖形状

	@ViewInject(R.id.inCon_gj_jgcc)
	private InputComponent incomJgcc;// 井盖尺寸

	@ViewInject(R.id.insc_gj_jgcz)
	private InputSelectComponent inscJgcz;// 井盖材质

	@ViewInject(R.id.inCon_gj_jgsccj)
	private InputComponent incomJgsccj;// 井盖生产厂家

	@ViewInject(R.id.inCon_gj_jgccrq)
	private InputComponent incomJgccrq;// 井盖出厂日期

	@ViewInject(R.id.inCon_gj_ptcs)
	private InputComponent incomPtcs;// 平台层数

	@ViewInject(R.id.inCon_gj_sgdw)
	private InputComponent incomSgdw; // 施工单位

	@ViewInject(R.id.inCon_gj_sgrq)
	private InputComponent incomSgrq;// 施工日期

	@ViewInject(R.id.inCon_gj_fjrq)
	private InputComponent incomJgrq;// 峻工日期

	@ViewInject(R.id.inCon_gj_tzbh)
	private InputComponent incomTzbh;// 图纸编号

	@ViewInject(R.id.insc_gj_zcxz)
	private InputSelectComponent inscZcxz;// 资产性质

	@ViewInject(R.id.inCon_gj_zcdw)
	private InputComponent incomZcdw;// 资产单位

	@ViewInject(R.id.inCon_gj_zcbh)
	private InputComponent incomZcbh;// 资产编号

	@ViewInject(R.id.inCon_gj_zyfl)
	private InputComponent incomZyfl;// 专业分类

	@ViewInject(R.id.inCon_gj_bz)
	private InputComponent incomBz;// 备注

	@ViewInject(R.id.iv_link_device_enter)
	private ImageView ivLinkDeviceEnter;// 关联

	@ViewInject(R.id.btn_link_device_link)
	private Button btnLinkDeviceLink;// 关联

	@ViewInject(R.id.btn_gj_jgcj)
	private Button btnJgcj;// 井盖采集

	@ViewInject(R.id.btn_gj_bqcj)
	private Button btnBqcj;// 标签采集

	@ViewInject(R.id.rl_link_device)
	private RelativeLayout rlLinkDevice;// 关联布局

	@ViewInject(R.id.inCon_gj_dlldjl)
	private InputComponent inComDlldjl;// 电缆离地面距离

	@ViewInject(R.id.inCon_gj_dlljdjl)
	private InputComponent inComDlljdjl;// 电缆离井底距离

	@ViewInject(R.id.inCon_gj_dllzcjl)
	private InputComponent inComDllzcjl;// 电缆离左侧距离

	@ViewInject(R.id.inCon_gj_dllycjl)
	private InputComponent inComDllycjl;// 电缆离右侧距离

	@ViewInject(R.id.inCon_gj_gjcd)
	private InputComponent inComGjcd;// 工井长度

	@ViewInject(R.id.inCon_gj_gjkd)
	private InputComponent inComGjkd;// 工井宽度

	@ViewInject(R.id.inCon_gj_gjsd)
	private InputComponent inComGjsd;// 工井深度

	@ViewInject(R.id.inCon_gj_jkjgbh)
	private InputComponent inComJkjgbh;// 监控井盖编号

	@ViewInject(R.id.inCon_gj_xygjfx)
	private InputComponent inComXygjfx;// 下一工井方向

	@ViewInject(R.id.inCon_gj_jxygjjl)
	private InputComponent inComJxygjjl;// 距下一工井距离

	@ViewInject(R.id.inCon_gj_fssbqk)
	private InputComponent inComFssbqk;// 附属设备情况

	@ViewInject(R.id.inCon_gj_tddmc)
	private InputComponent inComTddmc;// 通道段名称

	@ViewInject(R.id.inCon_gj_jqdwz)
	private InputComponent inComJqdwz;// 距起点位置

	@ViewInject(R.id.btn_gj_ts)
	private Button btnGjTs;//同上
	
	
	/*@ViewInject(R.id.ll_gj_activity_workwell_name)
	private RelativeLayout llWorkwellName; // 命名规范内容

	public RelativeLayout getLlWorkwellName() {
		return llWorkwellName;
	}

	public void setLlWorkwellName(RelativeLayout llWorkwellName) {
		this.llWorkwellName = llWorkwellName;
	}*/

	public Button getBtnGjTs() {
		return btnGjTs;
	}

	public void setBtnGjTs(Button btnGjTs) {
		this.btnGjTs = btnGjTs;
	}

	public InputComponent getInComDlldjl() {
		return inComDlldjl;
	}

	public void setInComDlldjl(InputComponent inComDlldjl) {
		this.inComDlldjl = inComDlldjl;
	}

	public InputComponent getInComDlljdjl() {
		return inComDlljdjl;
	}

	public void setInComDlljdjl(InputComponent inComDlljdjl) {
		this.inComDlljdjl = inComDlljdjl;
	}

	public InputComponent getInComDllzcjl() {
		return inComDllzcjl;
	}

	public void setInComDllzcjl(InputComponent inComDllzcjl) {
		this.inComDllzcjl = inComDllzcjl;
	}

	public InputComponent getInComDllycjl() {
		return inComDllycjl;
	}

	public void setInComDllycjl(InputComponent inComDllycjl) {
		this.inComDllycjl = inComDllycjl;
	}

	public InputComponent getInComGjcd() {
		return inComGjcd;
	}

	public void setInComGjcd(InputComponent inComGjcd) {
		this.inComGjcd = inComGjcd;
	}

	public InputComponent getInComGjkd() {
		return inComGjkd;
	}

	public void setInComGjkd(InputComponent inComGjkd) {
		this.inComGjkd = inComGjkd;
	}

	public InputComponent getInComGjsd() {
		return inComGjsd;
	}

	public void setInComGjsd(InputComponent inComGjsd) {
		this.inComGjsd = inComGjsd;
	}

	public Button getBtnJgcj() {
		return btnJgcj;
	}

	public void setBtnJgcj(Button btnJgcj) {
		this.btnJgcj = btnJgcj;
	}

	public Button getBtnBqcj() {
		return btnBqcj;
	}

	public void setBtnBqcj(Button btnBqcj) {
		this.btnBqcj = btnBqcj;
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

	public InputComponent getIncomDmxx() {
		return incomDmxx;
	}

	public void setIncomDmxx(InputComponent incomDmxx) {
		this.incomDmxx = incomDmxx;
	}

	public InputComponent getIncomGcmc() {
		return incomGcmc;
	}

	public void setIncomGcmc(InputComponent incomGcmc) {
		this.incomGcmc = incomGcmc;
	}

	public InputComponent getIncomBkhd() {
		return incomBkhd;
	}

	public void setIncomBkhd(InputComponent incomBkhd) {
		this.incomBkhd = incomBkhd;
	}

	public InputComponent getIncomGbks() {
		return incomGbks;
	}

	public void setIncomGbks(InputComponent incomGbks) {
		this.incomGbks = incomGbks;
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

	public InputComponent getIncomSbmc() {
		return incomSbmc;
	}

	public void setIncomSbmc(InputComponent incomSbmc) {
		this.incomSbmc = incomSbmc;
	}

	public InputComponent getIncomSszrq() {
		return incomSszrq;
	}

	public void setIncomSszrq(InputComponent incomSszrq) {
		this.incomSszrq = incomSszrq;
	}

	public InputSelectComponent getInscGjxz() {
		return inscGjxz;
	}

	public void setInscGjxz(InputSelectComponent inscGjxz) {
		this.inscGjxz = inscGjxz;
	}

	public InputSelectComponent getInscDqtz() {
		return inscDqtz;
	}

	public void setInscDqtz(InputSelectComponent inscDqtz) {
		this.inscDqtz = inscDqtz;
	}

	public InputComponent getIncomDlwz() {
		return incomDlwz;
	}

	public void setIncomDlwz(InputComponent incomDlwz) {
		this.incomDlwz = incomDlwz;
	}

	public InputComponent getIncomJwz() {
		return incomJwz;
	}

	public void setIncomJwz(InputComponent incomJwz) {
		this.incomJwz = incomJwz;
	}

	public InputSelectComponent getInscJlx() {
		return inscJlx;
	}

	public void setInscJlx(InputSelectComponent inscJlx) {
		this.inscJlx = inscJlx;
	}

	public InputSelectComponent getInscJg() {
		return inscJg;
	}

	public void setInscJg(InputSelectComponent inscJg) {
		this.inscJg = inscJg;
	}

	public InputComponent getIncomJmgc() {
		return incomJmgc;
	}

	public void setIncomJmgc(InputComponent incomJmgc) {
		this.incomJmgc = incomJmgc;
	}

	public InputComponent getIncomNdgc() {
		return incomNdgc;
	}

	public void setIncomNdgc(InputComponent incomNdgc) {
		this.incomNdgc = incomNdgc;
	}

	public InputComponent getIncomJgcc() {
		return incomJgcc;
	}

	public void setIncomJgcc(InputComponent incomJgcc) {
		this.incomJgcc = incomJgcc;
	}

	public InputSelectComponent getInscJgxz() {
		return inscJgxz;
	}

	public void setInscJgxz(InputSelectComponent inscJgxz) {
		this.inscJgxz = inscJgxz;
	}

	public InputSelectComponent getInscJgcz() {
		return inscJgcz;
	}

	public void setInscJgcz(InputSelectComponent inscJgcz) {
		this.inscJgcz = inscJgcz;
	}

	public InputComponent getIncomJgsccj() {
		return incomJgsccj;
	}

	public void setIncomJgsccj(InputComponent incomJgsccj) {
		this.incomJgsccj = incomJgsccj;
	}

	public InputComponent getIncomJgccrq() {
		return incomJgccrq;
	}

	public void setIncomJgccrq(InputComponent incomJgccrq) {
		this.incomJgccrq = incomJgccrq;
	}

	public InputComponent getIncomPtcs() {
		return incomPtcs;
	}

	public void setIncomPtcs(InputComponent incomPtcs) {
		this.incomPtcs = incomPtcs;
	}

	public InputComponent getIncomSgdw() {
		return incomSgdw;
	}

	public void setIncomSgdw(InputComponent incomSgdw) {
		this.incomSgdw = incomSgdw;
	}

	public InputComponent getIncomSgrq() {
		return incomSgrq;
	}

	public void setIncomSgrq(InputComponent incomSgrq) {
		this.incomSgrq = incomSgrq;
	}

	public InputComponent getIncomJgrq() {
		return incomJgrq;
	}

	public void setIncomJgrq(InputComponent incomJgrq) {
		this.incomJgrq = incomJgrq;
	}

	public InputComponent getIncomTzbh() {
		return incomTzbh;
	}

	public void setIncomTzbh(InputComponent incomTzbh) {
		this.incomTzbh = incomTzbh;
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

	public InputComponent getIncomZcbh() {
		return incomZcbh;
	}

	public void setIncomZcbh(InputComponent incomZcbh) {
		this.incomZcbh = incomZcbh;
	}

	public InputComponent getIncomZyfl() {
		return incomZyfl;
	}

	public void setIncomZyfl(InputComponent incomZyfl) {
		this.incomZyfl = incomZyfl;
	}

	public InputComponent getIncomBz() {
		return incomBz;
	}

	public void setIncomBz(InputComponent incomBz) {
		this.incomBz = incomBz;
	}

	public AttachmentView getAvAttachment() {
		return avAttachment;
	}

	public void setAvAttachment(AttachmentView avAttachment) {
		this.avAttachment = avAttachment;
	}

	public Button getBtnNameingConventions() {
		return btnNameingConventions;
	}

	public void setBtnNameingConventions(Button btnNameingConventions) {
		this.btnNameingConventions = btnNameingConventions;
	}

	public InputComponent getInComJkjgbh() {
		return inComJkjgbh;
	}

	public void setInComJkjgbh(InputComponent inComJkjgbh) {
		this.inComJkjgbh = inComJkjgbh;
	}

	public InputComponent getInComXygjfx() {
		return inComXygjfx;
	}

	public void setInComXygjfx(InputComponent inComXygjfx) {
		this.inComXygjfx = inComXygjfx;
	}

	public InputComponent getInComJxygjjl() {
		return inComJxygjjl;
	}

	public void setInComJxygjjl(InputComponent inComJxygjjl) {
		this.inComJxygjjl = inComJxygjjl;
	}

	public InputComponent getInComFssbqk() {
		return inComFssbqk;
	}

	public void setInComFssbqk(InputComponent inComFssbqk) {
		this.inComFssbqk = inComFssbqk;
	}

	public InputComponent getInComTddmc() {
		return inComTddmc;
	}

	public void setInComTddmc(InputComponent inComTddmc) {
		this.inComTddmc = inComTddmc;
	}

	public InputComponent getInComJqdwz() {
		return inComJqdwz;
	}

	public void setInComJqdwz(InputComponent inComJqdwz) {
		this.inComJqdwz = inComJqdwz;
	}

}
