package com.winway.android.edcollection.project.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.winway.android.edcollection.base.dto.MessageBase;

/**
 * 数据同步结果返回或待上传的数据
 * 
 * @author zgq
 *
 */
public class SyncDataResult extends MessageBase {
	private String time;
	private List<TableDataItem> tables;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<TableDataItem> getTables() {
		return tables;
	}

	public void setTables(List<TableDataItem> tables) {
		this.tables = tables;
	}

	@Override
	public String toString() {
		return "SyncDataResult [time=" + time + ", tables=" + tables == null ? ""
				: Arrays.toString(tables.toArray()) + "]";
	}

}
