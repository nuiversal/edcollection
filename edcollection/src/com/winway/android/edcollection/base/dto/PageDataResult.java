package com.winway.android.edcollection.base.dto;

import java.util.List;

/**
 * 分页数据消息实体
 * @author bobo
 *
 */
public class PageDataResult<T> extends MessageBase{

	private long total;
	
	private List<T> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
