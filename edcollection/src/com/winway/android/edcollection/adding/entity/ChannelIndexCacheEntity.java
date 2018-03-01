package com.winway.android.edcollection.adding.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "ChnnelIndexCache")
public class ChannelIndexCacheEntity {
	@Id
	@Column(column = "chnelid")
	private String chnnelId;
	@Column(column = "begin")
	private int begin;
	@Column(column = "end")
	private int next;

	public String getChnnelId() {
		return chnnelId;
	}

	public void setChnnelId(String chnnelId) {
		this.chnnelId = chnnelId;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

}
