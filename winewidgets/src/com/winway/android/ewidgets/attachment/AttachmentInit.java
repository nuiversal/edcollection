package com.winway.android.ewidgets.attachment;

public class AttachmentInit {
	public static boolean hasInit = false;

	/**
	 * 初始化全局的附件采集控件设置对象
	 * @param videoDir
	 * @param photoDir
	 * @param voiceDir
	 * @param textDir
	 * @param vrDir
	 */
	public static void initGloble(String videoDir, String photoDir, String voiceDir, String textDir, String vrDir) {
		AttachmentAttrs attr = AttachmentAttrs.getSinggleAttr();
		attr.videoDir = videoDir;
		attr.photoDir = photoDir;
		attr.voiceDir = voiceDir;
		attr.textDir = textDir;
		attr.vrDir = vrDir;
		hasInit = true;
	}

	/**
	 * 初始化附件采集控件设置对象
	 * @param attr
	 * @param videoDir
	 * @param photoDir
	 * @param voiceDir
	 * @param textDir
	 * @param vrDir
	 * @param selectImgDir
	 */
	public static void initAttachmentAttrs(AttachmentAttrs attr, String videoDir, String photoDir, String voiceDir,
			String textDir, String vrDir) {
		attr.videoDir = videoDir;
		attr.photoDir = photoDir;
		attr.voiceDir = voiceDir;
		attr.textDir = textDir;
		attr.vrDir = vrDir;
	}
}
