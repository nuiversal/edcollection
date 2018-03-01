package com.winway.android.ewidgets.tree.test;

import java.util.List;

public class GetCustomListResult extends MessageBase {
	private List<DataCustom> customs;

	public GetCustomListResult() {
		super();
	}

	public GetCustomListResult(int code, String msg, List<DataCustom> customs) {
		super();
		this.customs = customs;
		setCode(code);
		setMsg(msg);
	}

	public List<DataCustom> getCustoms() {
		return customs;
	}

	public void setCustoms(List<DataCustom> customs) {
		this.customs = customs;
	}

}
