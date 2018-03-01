package com.winway.android.ewidgets.tree.entity;

import java.util.HashMap;

import android.view.View;

/**
 * AndroidTree节点数据模型
 * 
 * @author mr-lao
 * @date 2016-12-26
 * @update 2017-01-13 增加自定义节点视图
 */
public class ItemEntity {
	// 节点显示文本
	private String text;
	// 图片
	private String imageURI;
	// 图片是否显示
	private boolean showImage = true;
	// 节点被点击后请求数据的URL
	private String url;
	// 是否拥有子节点
	private boolean hasChild = false;
	// 是显示点击框
	private boolean showCheckBox = true;
	// 点击框是否被选中
	private boolean checked = false;
	// 是否显示箭头
	private boolean showEX = true;
	private boolean ex = false;
	// JSON数据（初始目前是通过接口返回的JSON数据来显示台账树）
	private String jsonData;
	// 对象数据（目前台账树主要用对象来存放数据）
	private Object objData;
	/** 字体颜色 */
	private String textColor;
	/*
	 * 设置view控件的隐藏方式，比如，让checkbox消失，如果viewHidenType=View.GONE的话，则checkbox直接GONE掉，
	 * 如果viewHidenType=View.INVISIBLE，则checkbox直接INVISIBLE
	 */
	private int viewHidenType = View.GONE;

	/**
	 * 节点持有子节点数据了，但是仍然需要加载[大于0仍然要加载，小于0不需要加载]
	 */
	private int hasChildButNeddLoad = -1;

	/**
	 * 辅助数据存储容器（使用情形：当objData已经被用来存放数据了，而又有新的数据将要存在，这时候就可以将数据存放在辅助存储容器中了）
	 * 当辅助容器没有存放数据的时候，容器对象为null，辅助容器只提供get和put两个方法。
	 */
	private HashMap<String, Object> datamap;

	/** 自定义节点视图 */
	private View customItemView;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getImageURI() {
		return imageURI;
	}

	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
		// 显示图片
		setShowImage(true);
	}

	public boolean isShowImage() {
		return showImage;
	}

	public void setShowImage(boolean showImage) {
		this.showImage = showImage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public boolean isShowCheckBox() {
		return showCheckBox;
	}

	public void setShowCheckBox(boolean showCheckBox) {
		this.showCheckBox = showCheckBox;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isShowEX() {
		return showEX;
	}

	public void setShowEX(boolean showEX) {
		this.showEX = showEX;
	}

	public boolean isEx() {
		return ex;
	}

	public void setEx(boolean ex) {
		this.ex = ex;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public Object getObjData() {
		return objData;
	}

	public void setObjData(Object objData) {
		this.objData = objData;
	}

	public View getCustomItemView() {
		return customItemView;
	}

	public void setCustomItemView(View customItemView) {
		this.customItemView = customItemView;
	}

	public int getViewHidenType() {
		return viewHidenType;
	}

	public void setViewHidenType(int viewHidenType) {
		this.viewHidenType = viewHidenType;
	}

	@Override
	public String toString() {
		return text;
	}

	/**
	 * 把数据存放于辅助容器中
	 * 
	 * @param tagKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getTagData(String tagKey) {
		if (null != datamap) {
			return (T) datamap.get(tagKey);
		}
		return null;
	}

	/**
	 * 从辅助容器中获取数据
	 * 
	 * @param tagKey
	 * @param data
	 */
	public void putTagData(String tagKey, Object data) {
		if (null == datamap) {
			datamap = new HashMap<>();
		}
		datamap.put(tagKey, data);
	}

	public int getHasChildButNeddLoad() {
		int rest = hasChildButNeddLoad;
		hasChildButNeddLoad--;
		return rest;
	}

	public void setHasChildButNeddLoad(int hasChildButNeddLoad) {
		this.hasChildButNeddLoad = hasChildButNeddLoad;
	}

}
