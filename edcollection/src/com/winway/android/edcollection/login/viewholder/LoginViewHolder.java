package com.winway.android.edcollection.login.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginViewHolder extends BaseViewHolder{
	@ViewInject(R.id.edt_login_username)
	private EditText edtUsername;
	@ViewInject(R.id.edt_login_password)
	private EditText edtPassword;
	@ViewInject(R.id.edt_login_checkcode)
	private EditText edtCheckcode;
	@ViewInject(R.id.iv_login_checkcode)
	private ImageView ivCheckcode;
	@ViewInject(R.id.btn_login_login)
	private Button btnLogin;
	@ViewInject(R.id.btn_login_config)
	private Button btnConfig;

	public EditText getEdtUsername() {
		return edtUsername;
	}

	public void setEdtUsername(EditText edtUsername) {
		this.edtUsername = edtUsername;
	}

	public EditText getEdtPassword() {
		return edtPassword;
	}

	public void setEdtPassword(EditText edtPassword) {
		this.edtPassword = edtPassword;
	}

	public EditText getEdtCheckcode() {
		return edtCheckcode;
	}

	public void setEdtCheckcode(EditText edtCheckcode) {
		this.edtCheckcode = edtCheckcode;
	}

	public ImageView getIvCheckcode() {
		return ivCheckcode;
	}

	public void setIvCheckcode(ImageView ivCheckcode) {
		this.ivCheckcode = ivCheckcode;
	}

	public Button getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(Button btnLogin) {
		this.btnLogin = btnLogin;
	}

	public Button getBtnConfig() {
		return btnConfig;
	}

	public void setBtnConfig(Button btnConfig) {
		this.btnConfig = btnConfig;
	}

}
