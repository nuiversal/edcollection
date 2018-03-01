package com.winway.android.ewidgets.attachment;

import com.winway.android.ewidgets.attachment.entity.Panoramic;

import android.app.Activity;

public class AttachmentAttrs {
	private static AttachmentAttrs attr = null;

	public static AttachmentAttrs getSinggleAttr() {
		if (null == attr) {
			attr = new AttachmentAttrs();
		}
		return attr;
	}

	public Activity activity;

	/** 视频保存目录 */
	public String videoDir;
	/** 照片保存目录 */
	public String photoDir;
	/** 录音保存目录 */
	public String voiceDir;
	/** 文本保存目录 */
	public String textDir;
	/** 文本保存目录 */
	public String vrDir;
	/** 文件前缀名称 */
	public String prefxName;

	/** 控制附件采集组件显示的功能点 */
	public boolean hasVideo = true;
	public boolean hasPhoto = true;
	public boolean hasVoice = true;
	public boolean hasText = true;
	public boolean hasVr = false;
	/** 控制“完毕”按钮的显示 */
	public boolean hasFinish = true;

	/** 控件附件采集的最大数量 */
	public int maxVideo = 100;
	public int maxPhoto = 100;
	public int maxVoice = 100;
	public int maxText = 100;
	public int maxVr = 100;

	public boolean isJustShow = false;

	final static AttachmentAttrs justShowAttrs = new AttachmentAttrs();

	public static AttachmentAttrs getJustShowAttrs(Activity activity) {
		justShowAttrs.isJustShow = true;
		justShowAttrs.activity = activity;
		return justShowAttrs;
	}

	public Panoramic panoramic;// 全景对象
	public String titleName;// 左侧标题

}
