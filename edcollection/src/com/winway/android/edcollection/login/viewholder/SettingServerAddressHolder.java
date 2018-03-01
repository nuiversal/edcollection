package com.winway.android.edcollection.login.viewholder;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;
import com.winway.android.ewidgets.input.InputComponent;
/**
 * 
 * @author xs
 *
 */
public class SettingServerAddressHolder extends BaseViewHolder {

	@ViewInject(R.id.inCon_server_loginaddr)
	private InputComponent inConServerLoginAddr;// 登录地址

	@ViewInject(R.id.inCon_server_dataserviceaddr)
	private InputComponent inConServerDataServiceAddr;// 数据服务接口地址

	@ViewInject(R.id.inCon_server_fileconnectservice)
	private InputComponent inConServerFileConnectService;// 文件通讯服务

	@ViewInject(R.id.server_setting_head)
	private HeadComponent serverSettingHead;// 设置服务器标题框

	@ViewInject(R.id.rg_server_zcxz)
	private RadioGroup rgServerZcxz;// 资产性质

	@ViewInject(R.id.rb_server_zcxz_nw)
	private RadioButton rbServerZcxzNw;// 资产性质南网

	@ViewInject(R.id.rb_server_zcxz_gw)
	private RadioButton rbServerZcxzGw;// 资产性质国网

	@ViewInject(R.id.rl_server_address_changedir)
	private RelativeLayout rlChangerDir;// 切换SD卡

	public RelativeLayout getRlChangerDir() {
		return rlChangerDir;
	}
	public void setRlChangerDir(RelativeLayout rlChangerDir) {
		this.rlChangerDir = rlChangerDir;
	}
	public RadioGroup getRgServerZcxz() {
		return rgServerZcxz;
	}
	public void setRgServerZcxz(RadioGroup rgServerZcxz) {
		this.rgServerZcxz = rgServerZcxz;
	}
	public RadioButton getRbServerZcxzNw() {
		return rbServerZcxzNw;
	}
	public void setRbServerZcxzNw(RadioButton rbServerZcxzNw) {
		this.rbServerZcxzNw = rbServerZcxzNw;
	}
	public RadioButton getRbServerZcxzGw() {
		return rbServerZcxzGw;
	}
	public void setRbServerZcxzGw(RadioButton rbServerZcxzGw) {
		this.rbServerZcxzGw = rbServerZcxzGw;
	}
	public InputComponent getInConServerLoginAddr() {
		return inConServerLoginAddr;
	}
	public void setInConServerLoginAddr(InputComponent inConServerLoginAddr) {
		this.inConServerLoginAddr = inConServerLoginAddr;
	}
	public InputComponent getInConServerDataServiceAddr() {
		return inConServerDataServiceAddr;
	}
	public void setInConServerDataServiceAddr(InputComponent inConServerDataServiceAddr) {
		this.inConServerDataServiceAddr = inConServerDataServiceAddr;
	}
	public InputComponent getInConServerFileConnectService() {
		return inConServerFileConnectService;
	}
	public void setInConServerFileConnectService(InputComponent inConServerFileConnectService) {
		this.inConServerFileConnectService = inConServerFileConnectService;
	}
	public HeadComponent getServerSettingHead() {
		return serverSettingHead;
	}
	public void setServerSettingHead(HeadComponent serverSettingHead) {
		this.serverSettingHead = serverSettingHead;
	}
}
