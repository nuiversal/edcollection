package com.winway.android.edcollection.adding.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 安装位置
 * 
 * @author zgq
 *
 */
@Table(name = "install_position")
public class InstallPosition {
	@Id
	@Column(column = "id")
	private Integer id;// 字段ID
	@Column(column = "name")
	private String name;// 名称

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
