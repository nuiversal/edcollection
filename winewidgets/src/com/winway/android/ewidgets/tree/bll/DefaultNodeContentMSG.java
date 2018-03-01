package com.winway.android.ewidgets.tree.bll;

@Deprecated
public class DefaultNodeContentMSG extends BaseNoteContentMSG {
	/**
	 * 显示的文件内容
	 */
	private String text;
	/**
	 * 图片URI
	 * 注意：图片URI可以确定一个图片所在的位置，如网络图片、本地文件图片、资源文件图片，虽然图片URI是一个字符串，但是，为了使图片URI能顺利解释，
	 * 所以要通过<code>ImageURIUtil</code>工具类来生成
	 */
	private String imageURI;
	/**
	 * 选择框
	 */
	private boolean checked;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageURI() {
		return imageURI;
	}

	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
