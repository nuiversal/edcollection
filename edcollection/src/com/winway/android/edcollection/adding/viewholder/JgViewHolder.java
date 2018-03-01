package com.winway.android.edcollection.adding.viewholder;

import android.widget.Button;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

/**
 * @author lyh
 * @version 创建时间：2017年3月28日 井盖
 */
public class JgViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_jg_head)
	private HeadComponent hcHead;// head组件
	@ViewInject(R.id.inCon_jg_xh)
	private InputComponent inconXh; // 序号

	@ViewInject(R.id.inCon_jg_sbmc)
	private InputComponent inconSbmc; // 设备名称

	@ViewInject(R.id.insc_jg_sfxtzcz)
	private InputSelectComponent inscSfxtzcz; // 是否系统中存在

	@ViewInject(R.id.inCon_jg_ywdw)
	private InputComponent inconYwdw; // 运维单位

	@ViewInject(R.id.inCon_jg_whbz)
	private InputComponent inconWhbz; // 维护班组

	@ViewInject(R.id.inCon_jg_sszrq)
	private InputComponent inconSszrq; // 所属责任区

	@ViewInject(R.id.inCon_jg_jgcd)
	private InputComponent inconJgcd; // 井盖长度

	@ViewInject(R.id.inCon_jg_jgkd)
	private InputComponent inconJgkd; // 井盖宽度

	@ViewInject(R.id.inCon_jg_jghd)
	private InputComponent inconJghd; // 井盖厚度

	@ViewInject(R.id.inCon_jg_zb)
	private InputComponent inconZb; // 备注

	@ViewInject(R.id.btn_jg_Add)
	private Button btnAdd; // 添加

	@ViewInject(R.id.lv_jg_jglist)
	private ListView lvJgList;

	public ListView getLvJgList() {
		return lvJgList;
	}

	public void setLvJgList(ListView lvJgList) {
		this.lvJgList = lvJgList;
	}

	public InputComponent getInconJgcd() {
		return inconJgcd;
	}

	public void setInconJgcd(InputComponent inconJgcd) {
		this.inconJgcd = inconJgcd;
	}

	public InputComponent getInconJgkd() {
		return inconJgkd;
	}

	public void setInconJgkd(InputComponent inconJgkd) {
		this.inconJgkd = inconJgkd;
	}

	public InputComponent getInconJghd() {
		return inconJghd;
	}

	public void setInconJghd(InputComponent inconJghd) {
		this.inconJghd = inconJghd;
	}

	public Button getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(Button btnAdd) {
		this.btnAdd = btnAdd;
	}

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public InputComponent getInconXh() {
		return inconXh;
	}

	public void setInconXh(InputComponent inconXh) {
		this.inconXh = inconXh;
	}

	public InputComponent getInconSbmc() {
		return inconSbmc;
	}

	public void setInconSbmc(InputComponent inconSbmc) {
		this.inconSbmc = inconSbmc;
	}

	public InputSelectComponent getInscSfxtzcz() {
		return inscSfxtzcz;
	}

	public void setInscSfxtzcz(InputSelectComponent inscSfxtzcz) {
		this.inscSfxtzcz = inscSfxtzcz;
	}

	public InputComponent getInconYwdw() {
		return inconYwdw;
	}

	public void setInconYwdw(InputComponent inconYwdw) {
		this.inconYwdw = inconYwdw;
	}

	public InputComponent getInconWhbz() {
		return inconWhbz;
	}

	public void setInconWhbz(InputComponent inconWhbz) {
		this.inconWhbz = inconWhbz;
	}

	public InputComponent getInconSszrq() {
		return inconSszrq;
	}

	public void setInconSszrq(InputComponent inconSszrq) {
		this.inconSszrq = inconSszrq;
	}

	public InputComponent getInconZb() {
		return inconZb;
	}

	public void setInconZb(InputComponent inconZb) {
		this.inconZb = inconZb;
	}

}
