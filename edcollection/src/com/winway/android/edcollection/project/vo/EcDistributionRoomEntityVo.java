package com.winway.android.edcollection.project.vo;

import java.io.Serializable;
import java.util.List;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;

@Table(name = "ec_distribution_room")
public class EcDistributionRoomEntityVo extends EcDistributionRoomEntity implements Serializable {
	// 不入库存放离线附件表数据
	@Transient
	private List<OfflineAttach> comUploadEntityList;
	@Transient
	/**
	 * 操作标识
	 */
	private int operateMark = 0;// 0表示新增，默认新增；1表示编辑下的新增；2表示编辑下的编辑；3表示关联设备的操作

	public int getOperateMark() {
		return operateMark;
	}

	public void setOperateMark(int operateMark) {
		this.operateMark = operateMark;
	}

	public List<OfflineAttach> getComUploadEntityList() {
		return comUploadEntityList;
	}

	public void setComUploadEntityList(List<OfflineAttach> comUploadEntityList) {
		this.comUploadEntityList = comUploadEntityList;
	}

}
