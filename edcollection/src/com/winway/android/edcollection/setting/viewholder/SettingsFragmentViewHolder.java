package com.winway.android.edcollection.setting.viewholder;

import android.widget.ListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;

/**
 * 设置Ui初始化
 * 
 * @author xs
 *
 */
public class SettingsFragmentViewHolder extends BaseViewHolder {
	
	@ViewInject(R.id.lv_setting_list)
	private ListView lvSettingList;

	public ListView getLvSettingList() {
		return lvSettingList;
	}

	public void setLvSettingList(ListView lvSettingList) {
		this.lvSettingList = lvSettingList;
	}
	
	
}
