package com.winway.android.edcollection.setting.entity;

/**
 * SettingListView展示数据封装
 * 
 * @author xs
 *
 */
public class SettingListViewItem {
	private String settingItem;
	private String settingState;

	public String getSettingItem() {
		return settingItem;
	}

	public void setSettingItem(String settingItem) {
		this.settingItem = settingItem;
	}

	public String getSettingState() {
		return settingState;
	}

	public void setSettingState(String settingState) {
		this.settingState = settingState;
	}

	public SettingListViewItem(String settingItem, String settingState) {
		super();
		this.settingItem = settingItem;
		this.settingState = settingState;
	}

	@Override
	public String toString() {
		return "AdapterItem [settingItem=" + settingItem + ", settingState=" + settingState + "]";
	}

}
