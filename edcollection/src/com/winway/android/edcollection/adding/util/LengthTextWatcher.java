package com.winway.android.edcollection.adding.util;

import com.winway.android.util.ToastUtils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * 输入框文字长度监听器
 * @author mr-lao
 *
 */
public class LengthTextWatcher implements TextWatcher {
	private int maxLength = -1;
	private TextView editTV;

	private boolean isShowToast = true;

	public LengthTextWatcher(int maxLength, TextView editTV) {
		super();
		this.maxLength = maxLength;
		this.editTV = editTV;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public boolean isShowToast() {
		return isShowToast;
	}

	public void setShowToast(boolean isShowToast) {
		this.isShowToast = isShowToast;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		if (maxLength > 0 && s.length() > maxLength) {
			s.delete(s.length() - 1, s.length());
			if (isShowToast) {
				ToastUtils.show(editTV.getContext(), "此输入框最大字符长度为:" + maxLength);
			}
		}
	}

}
