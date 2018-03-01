package com.winway.android.edcollection.project.vo;

import java.io.Serializable;
import java.util.List;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;

/**
 * 开关站
 *
 * @author lyh
 * @data 2017年2月14日
 */
@Table(name = "ec_kgz")
public class EcKgzEntityVo extends EcKgzEntity implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -4750989054930847328L;
	// 不入库 存放离线附件表数据
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
