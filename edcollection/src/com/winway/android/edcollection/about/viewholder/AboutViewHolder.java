package com.winway.android.edcollection.about.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.edcollection.base.widgets.HeadComponent;

import android.widget.TextView;

/**
 * 关于ViewHolder
 * 
 * @author xs
 *
 */
public class AboutViewHolder extends BaseViewHolder {

	@ViewInject(R.id.hc_activity_about_head)
	private HeadComponent hcHead;// 头部组件

	@ViewInject(R.id.tv_activity_about_appversion)
	private TextView tvApp;// APP版本

	@ViewInject(R.id.tv_activity_about_eclouddbversion)
	private TextView tvEcloudDB;// Ecloud DB版本

	@ViewInject(R.id.tv_activity_about_eddbversion)
	private TextView tvEDDB;// ed DB版本

	@ViewInject(R.id.tv_activity_about_appname)
	private TextView tvAppName;

	public HeadComponent getHcHead() {
		return hcHead;
	}

	public void setHcHead(HeadComponent hcHead) {
		this.hcHead = hcHead;
	}

	public TextView getTvApp() {
		return tvApp;
	}

	public void setTvApp(TextView tvApp) {
		this.tvApp = tvApp;
	}

	public TextView getTvEcloudDB() {
		return tvEcloudDB;
	}

	public void setTvEcloudDB(TextView tvEcloudDB) {
		this.tvEcloudDB = tvEcloudDB;
	}

	public TextView getTvEDDB() {
		return tvEDDB;
	}

	public void setTvEDDB(TextView tvEDDB) {
		this.tvEDDB = tvEDDB;
	}

	public TextView getTvAppName() {
		return tvAppName;
	}

	public void setTvAppName(TextView tvAppName) {
		this.tvAppName = tvAppName;
	}

}
