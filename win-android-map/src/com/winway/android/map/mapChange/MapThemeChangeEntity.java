package com.winway.android.map.mapChange;

/**
 * @author lyh
 * @version 创建时间：2018年1月22日
 *
 */
public class MapThemeChangeEntity {

	private int unSelectId;// 图片未选中的id
	private int selectId;;// 图片选中的id
	private String mapName; // 底图名称
	
	public int getUnSelectId() {
		return unSelectId;
	}
	public void setUnSelectId(int unSelectId) {
		this.unSelectId = unSelectId;
	}
	public int getSelectId() {
		return selectId;
	}
	public void setSelectId(int selectId) {
		this.selectId = selectId;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

}
