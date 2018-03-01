package com.winway.android.edcollection.project.vo;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Transient;
import com.winway.android.edcollection.project.entity.EmTaskItemEntity;

public class EmTaskItemEntityVo extends EmTaskItemEntity implements Serializable {
	@Transient
	private String detailName;// 对象名称

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

}
