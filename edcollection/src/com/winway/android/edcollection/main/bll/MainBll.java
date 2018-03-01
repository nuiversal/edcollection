package com.winway.android.edcollection.main.bll;

import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.util.UUIDGen;

import android.content.Context;

/**
 * 主界面业务逻辑
 * 
 * @author zgq
 *
 */
public class MainBll extends BaseBll<Object> {

	public MainBll(Context context,String dbUrl){
		super(context, dbUrl);
	}
	/**
	 * 构建路径点设备关联表对象
	 * 
	 * @param nodeDeviceType
	 *            节点设备类型
	 * @param objId
	 *            对象id
	 * @param oid
	 *            路径点oid
	 * @return
	 */
	public EcNodeDeviceEntity combineNodeDevice(ResourceEnum nodeDeviceType, String objId, String oid) {
		EcNodeDeviceEntity ecNodeDeviceEntity = new EcNodeDeviceEntity();
		ecNodeDeviceEntity.setDeviceType(nodeDeviceType.getValue()+"");
		ecNodeDeviceEntity.setDevObjId(objId);
		ecNodeDeviceEntity.setEcNodeDeviceId(UUIDGen.randomUUID());// 主键
		ecNodeDeviceEntity.setOid(oid);
		ecNodeDeviceEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
		ecNodeDeviceEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
		return ecNodeDeviceEntity;
	}
	
}
