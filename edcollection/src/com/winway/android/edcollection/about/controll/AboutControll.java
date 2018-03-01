package com.winway.android.edcollection.about.controll;

import com.winway.android.edcollection.about.IAbout;
import com.winway.android.edcollection.about.impl.AboutImpl;
import com.winway.android.edcollection.about.viewholder.AboutViewHolder;
import com.winway.android.edcollection.base.view.BaseControll;
import android.view.View;

/**
 * 关于Controll
 * 
 * @author xs
 *
 */
public class AboutControll extends BaseControll<AboutViewHolder> {
	private IAbout about;

	@Override
	public void init() {
		about = new AboutImpl();
		initData();
		initEvent();
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		viewHolder.getHcHead().getReturnView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.finish();
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		viewHolder.getTvApp().setText(about.getAppVersion(mActivity));
		viewHolder.getTvEcloudDB().setText(about.getEcloudDBVersion(mActivity));
		viewHolder.getTvEDDB().setText(about.getEDDBVersion(mActivity));
		viewHolder.getTvAppName().setText(about.getAppName(mActivity));
	}

}
