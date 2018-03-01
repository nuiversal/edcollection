package com.winway.android.edcollection.project.dto;

/**
 * 记录行对象
 * 
 * @author zgq
 *
 */
public class TableRow {
	private String key;// 主键值
	private Integer type;// 更新类型1 新增，2 修改，3 删除
	private String update;// 更新时间
	private String recorder;// 记录人
	private Object entity;// 实体对象，如果为删除，则可留空

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}
}
