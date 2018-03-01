package com.winway.android.edcollection.task.entity;

import java.io.Serializable;
import java.util.List;

import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;

/**
 * 任务详情使用，点击任务详情后，以此实体作为参数传递给对应的采集界面
 * @author mr-lao
 *
 */
public class TaskDeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**意图KEY*/
	public static final String INTENT_KEY = "TASK_DEVICEENTITY_INTENT_KEY";
	/**设备任务实体，此值不能为空，值以要跳转到的采集Activity对应，比如要跳转到工井采集Activity，则device的值一定要为EcWorkWellEntity类型*/
	public Object device;
	/**路径点，在跳转到采集Activity时此值可以为空*/
	public EcNodeEntity ecnode;
	/** 用于存入附件*/
	private List<OfflineAttach> comUploadEntityList;

	public List<OfflineAttach> getComUploadEntityList() {
		return comUploadEntityList;
	}

	public void setComUploadEntityList(List<OfflineAttach> comUploadEntityList) {
		this.comUploadEntityList = comUploadEntityList;
	}

	public Object getDevice() {
		return device;
	}

	public void setDevice(Object device) {
		this.device = device;
	}

	public EcNodeEntity getEcnode() {
		return ecnode;
	}

	public void setEcnode(EcNodeEntity ecnode) {
		this.ecnode = ecnode;
	}

}
