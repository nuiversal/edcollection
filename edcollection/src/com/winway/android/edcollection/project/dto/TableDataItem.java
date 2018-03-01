package com.winway.android.edcollection.project.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 表对象
 * 
 * @author zgq
 *
 */
public class TableDataItem {

	private String name;// 表名
	private List<TableRow> rows;// 记录列表

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TableRow> getRows() {
		return rows;
	}

	public void setRows(List<TableRow> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "TableDataItem [name=" + name + ", rows=" + Arrays.toString(rows.toArray()) + "]";
	}

}
