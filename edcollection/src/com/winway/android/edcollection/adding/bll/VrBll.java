package com.winway.android.edcollection.adding.bll;

import java.util.Date;

import android.content.Context;

import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.entity.EcVRRefEntity;

/**
 * vr业务处理
 * 
 * @author winway_zgq
 *
 */
public class VrBll extends BaseBll<EcVRRefEntity> {

	public VrBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构建vr实体对象
	 * 
	 * @param id
	 *            主键id
	 * @param recType
	 *            关联的设备的资源类型
	 * @param devObjId
	 *            关联设备id
	 * @param nodeOid
	 *            节点id
	 * @param name
	 *            全景名称
	 * @return
	 */
	public EcVRRefEntity combileVREntity(String id, String recType, String devObjId, String nodeOid, String name,
			String orgId, String prjId) {
		EcVRRefEntity vrRefEntity = new EcVRRefEntity();
		vrRefEntity.setId(id + "");
		vrRefEntity.setDeviceType(recType);
		vrRefEntity.setDevObjId(devObjId);
		// vrRefEntity.setOrgid(GlobalEntry.loginResult.getOrgid());
		vrRefEntity.setOid(nodeOid);
		vrRefEntity.setCjsj(new Date());
		vrRefEntity.setGxsj(new Date());
		vrRefEntity.setName(name);
		vrRefEntity.setOrgid(orgId);
		vrRefEntity.setPrjid(prjId);
		return vrRefEntity;
	}

}
