package com.winway.android.edcollection.adding.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 线路节点顺序（缓存使用）
 * 
 * @author zgq
 *
 */
@Table(name = "line_node_cache")
public class LineNodeSort {
	@Id
	@Column(column = "id")
	private Integer id;// 字段ID
	@Column(column = "lineId")
	private String lineId;// 线路id
	@Column(column = "sort")
	private Integer sort;// 序号

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
