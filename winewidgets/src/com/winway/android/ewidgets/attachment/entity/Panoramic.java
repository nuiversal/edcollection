package com.winway.android.ewidgets.attachment.entity;

/**
 * 全景对象
 * 
 * @author winway_zgq
 *
 */
public class Panoramic {
	private long openRicohTime=-1;// 打开全景时间
	private String filePath;// 文件路径

	public long getOpenRicohTime() {
		return openRicohTime;
	}

	public void setOpenRicohTime(long openRicohTime) {
		this.openRicohTime = openRicohTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
