package com.winway.android.edcollection.export.entity;

import java.util.ArrayList;

import com.winway.jxl.anomotion.CellColum;

/**
* @author winway-laohw
* @time 2018年1月31日 上午9:30:30
* 离线附件
*/
public class ExcelEntity4OfflineAttach {
	@CellColum(headerName = "标识器编号", index = 0)
	private String markerno;
	/**路径点、工井、电子标签、中间接头、杆塔、配电室、变电站、配电箱、环网柜 ....*/
	@CellColum(headerName = "设备类型", index = 1)
	private String deviceType;
	/**
	 * 附件相对路径
	 * 规则:文件名!!!!相对路径
	 * 例如：label001.jpg!!!!images/lable001.jpg
	 * 文件名是label001.jpg  ,  文件路径是images/lable001.jpg
	 */
	@CellColum(headerName = "文件路径", index = 2)
	private ArrayList<String> pathList;

	public String getMarkerno() {
		return markerno;
	}

	public void setMarkerno(String markerno) {
		this.markerno = markerno;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public ArrayList<String> getPathList() {
		return pathList;
	}

	public void setPathList(ArrayList<String> pathList) {
		this.pathList = pathList;
	}

}
