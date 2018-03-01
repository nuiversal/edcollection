package com.winway.android.media.photo.photoselector.model;

/**
 * 相册实体
 * 
 * @author zgq
 *
 */
public class AlbumModel {

	private String name;// 相册名称

	private int count;// 相册中的照片数

	private String recent;// 相册缩列图路径

	private boolean isCheck;// 选中状态

	public AlbumModel() {
		super();
	}

	public AlbumModel(String name) {
		this.name = name;
	}

	public AlbumModel(String name, int count, String recent) {
		super();
		this.name = name;
		this.count = count;
		this.recent = recent;
	}

	public AlbumModel(String name, int count, String recent, boolean isCheck) {
		super();
		this.name = name;
		this.count = count;
		this.recent = recent;
		this.isCheck = isCheck;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRecent() {
		return recent;
	}

	public void setRecent(String recent) {
		this.recent = recent;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public void increaseCount() {
		count++;
	}

}
