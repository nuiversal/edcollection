package com.winway.android.edcollection.project.vo;

import java.io.Serializable;
import java.util.List;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.entity.EcDistBoxEntity;

@Table(name = "ec_dist_box")
public class EcDistBoxEntityVo extends EcDistBoxEntity implements Serializable {
	// 存放离线附件表数据
	@Transient
	private List<OfflineAttach> attr;
	// 用来判断是否在编辑中添加的
	@Transient
	private boolean isEditAdd = false;

	public List<OfflineAttach> getAttr() {
		return attr;
	}

	public void setAttr(List<OfflineAttach> attr) {
		this.attr = attr;
	}

	public boolean isEditAdd() {
		return isEditAdd;
	}

	public void setEditAdd(boolean isEditAdd) {
		this.isEditAdd = isEditAdd;
	}

}
