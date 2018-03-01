package com.winway.android.edcollection.adding.viewholder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.attachment.AttachmentView;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;

public class LableViewHolder extends BaseViewHolder {
	@ViewInject(R.id.hc_lable_head)
	private HeadComponent hcHead;// head组件
	@ViewInject(R.id.inCon_lable_sbmc)
	private InputComponent icSbmc; // 设备名称
	@ViewInject(R.id.inCon_linelable_id)
	private InputComponent inconLableNo;// 标签ID
	@ViewInject(R.id.btn_lable_receive)
	private Button btnReceive;// 超高频接受按钮
	@ViewInject(R.id.btn_lable_gp)
	private Button btnGpReceive;// 高频接收按钮
	@ViewInject(R.id.insc_lable_bind_target)
	private InputSelectComponent inscBindTarget;// 绑扎对象
	@ViewInject(R.id.inCon_lable_crossover)
	private InputComponent inconCrossover;// 与上一个标签的距离
	@ViewInject(R.id.inCon_lable_zjgjfx)
	private InputComponent inconZjgjfx;// 最近工井方向
	@ViewInject(R.id.inCon_lable_jzjgjjl)
	private InputComponent inconJzjgjjl;// 距最近工井距离
	@ViewInject(R.id.inCon_lable_fsssqk)
	private InputComponent inconFsssqk;// 附属设施情况
	@ViewInject(R.id.inCon_lable_wzdt)
	private InputComponent inconWzdt;// 位置地图
	@ViewInject(R.id.inCon_lable_zwtdfbqk)
	private InputComponent inconZwtdfbqk;// 周围通道分布情况
	@ViewInject(R.id.inCon_lable_tdndlqk)
	private InputComponent inconTdndlqk;// 通道内电缆情况
	@ViewInject(R.id.inCon_lable_dblkxx)
	private InputComponent inconDblkxx;// 地标路口信息
	@ViewInject(R.id.inCon_lable_dev_name)
	private InputComponent inconDevName;// 设备名称
	@ViewInject(R.id.inCon_lable_dev_type)
	private InputComponent inconDevType;// 设备类型
	@ViewInject(R.id.av_lable_attachment)
	private AttachmentView avAttachment;
	@ViewInject(R.id.inCon_label_sequence)
	private InputComponent inconSequence;// 安装序号
	@ViewInject(R.id.iv_link_device_enter)
	private ImageView ivLinkDeviceEnter;// 关联
	@ViewInject(R.id.btn_link_device_link)
	private Button btnLinkDeviceLink;// 关联
	@ViewInject(R.id.rl_link_device)
	private RelativeLayout rlLinkDevice;// 关联布局
	
	@ViewInject(R.id.inCon_lable_dxxh)
	private InputComponent inconLableDxxh;//对象型号
	
	@ViewInject(R.id.inCon_lable_dxcd)
	private InputComponent inConLableDxcd;//对象长度
	
	@ViewInject(R.id.iv_scene_position_photo)
	private ImageView ivPostionPhoto;// 位置图

	@ViewInject(R.id.iv_scene_background_photo)
	private ImageView ivBackgroundPhoto;// 背景图

	@ViewInject(R.id.iv_scene_position_delete)
	private ImageView ivPositionDelete;// 位置图删除按钮

	@ViewInject(R.id.iv_scene_background_delete)
	private ImageView ivBackgroundDelete;// 背景图删除按钮
	
	
	
	public InputComponent getInconLableDxxh() {
		return inconLableDxxh;
	}

	public void setInconLableDxxh(InputComponent inconLableDxxh) {
		this.inconLableDxxh = inconLableDxxh;
	}

	public InputComponent getInConLableDxcd() {
		return inConLableDxcd;
	}

	public void setInConLableDxcd(InputComponent inConLableDxcd) {
		this.inConLableDxcd = inConLableDxcd;
	}

	public ImageView getIvPostionPhoto() {
		return ivPostionPhoto;
	}

	public void setIvPostionPhoto(ImageView ivPostionPhoto) {
		this.ivPostionPhoto = ivPostionPhoto;
	}

	public ImageView getIvBackgroundPhoto() {
		return ivBackgroundPhoto;
	}

	public void setIvBackgroundPhoto(ImageView ivBackgroundPhoto) {
		this.ivBackgroundPhoto = ivBackgroundPhoto;
	}

	public ImageView getIvPositionDelete() {
		return ivPositionDelete;
	}

	public void setIvPositionDelete(ImageView ivPositionDelete) {
		this.ivPositionDelete = ivPositionDelete;
	}

	public ImageView getIvBackgroundDelete() {
		return ivBackgroundDelete;
	}

	public void setIvBackgroundDelete(ImageView ivBackgroundDelete) {
		this.ivBackgroundDelete = ivBackgroundDelete;
	}

	public InputComponent getIcSbmc() {
		return icSbmc;
	}

	public void setIcSbmc(InputComponent icSbmc) {
		this.icSbmc = icSbmc;
	}

	public InputComponent getInconZjgjfx() {
		return inconZjgjfx;
	}

	public void setInconZjgjfx(InputComponent inconZjgjfx) {
		this.inconZjgjfx = inconZjgjfx;
	}

	public InputComponent getInconJzjgjjl() {
		return inconJzjgjjl;
	}

	public void setInconJzjgjjl(InputComponent inconJzjgjjl) {
		this.inconJzjgjjl = inconJzjgjjl;
	}

	public InputComponent getInconFsssqk() {
		return inconFsssqk;
	}

	public void setInconFsssqk(InputComponent inconFsssqk) {
		this.inconFsssqk = inconFsssqk;
	}

	public InputComponent getInconWzdt() {
		return inconWzdt;
	}

	public void setInconWzdt(InputComponent inconWzdt) {
		this.inconWzdt = inconWzdt;
	}

	public InputComponent getInconZwtdfbqk() {
		return inconZwtdfbqk;
	}

	public void setInconZwtdfbqk(InputComponent inconZwtdfbqk) {
		this.inconZwtdfbqk = inconZwtdfbqk;
	}

	public InputComponent getInconTdndlqk() {
		return inconTdndlqk;
	}

	public void setInconTdndlqk(InputComponent inconTdndlqk) {
		this.inconTdndlqk = inconTdndlqk;
	}

	public InputComponent getInconDblkxx() {
		return inconDblkxx;
	}

	public void setInconDblkxx(InputComponent inconDblkxx) {
		this.inconDblkxx = inconDblkxx;
	}

	public InputComponent getInconDevName() {
		return inconDevName;
	}

	public void setInconDevName(InputComponent inconDevName) {
		this.inconDevName = inconDevName;
	}

	public InputComponent getInconDevType() {
		return inconDevType;
	}

	public void setInconDevType(InputComponent inconDevType) {
		this.inconDevType = inconDevType;
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

	public Button getBtnReceive() {
		return btnReceive;
	}

	public void setBtnReceive(Button btnReceive) {
		this.btnReceive = btnReceive;
	}

	public InputComponent getInconCrossover() {
		return inconCrossover;
	}

	public void setInconCrossover(InputComponent inconCrossover) {
		this.inconCrossover = inconCrossover;
	}

	public InputSelectComponent getInscBindTarget() {
		return inscBindTarget;
	}

	public void setInscBindTarget(InputSelectComponent inscBindTarget) {
		this.inscBindTarget = inscBindTarget;
	}

	public AttachmentView getAvAttachment() {
		return avAttachment;
	}

	public void setAvAttachment(AttachmentView avAttachment) {
		this.avAttachment = avAttachment;
	}

	public InputComponent getInconLableNo() {
		return inconLableNo;
	}

	public void setInconLableNo(InputComponent inconLableNo) {
		this.inconLableNo = inconLableNo;
	}

	public InputComponent getInconSequence() {
		return inconSequence;
	}

	public void setInconSequence(InputComponent inconSequence) {
		this.inconSequence = inconSequence;
	}

	public Button getBtnGpReceive() {
		return btnGpReceive;
	}

	public void setBtnGpReceive(Button btnGpReceive) {
		this.btnGpReceive = btnGpReceive;
	}

}
