package com.winway.android.edcollection.datacenter.service;

import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.util.ResourceDeviceUtil;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;

import android.content.Context;

/**
 * 标签中心
 * @author mr-lao
 *
 */
public class LabelCenter {
	private BaseDBUtil dbUtil;

	public LabelCenter(Context context, String dbUrl) {
		this.dbUtil = new BaseDBUtil(context, dbUrl);
	}

	public LabelCenter(BaseDBUtil dbUtil) {
		super();
		this.dbUtil = dbUtil;
	}

	/**
	 * 获取设备的附属电子标签
	 * @param deviceObj
	 * @return
	 */
	public List<EcLineLabelEntity> getDeviceLabel(Object deviceObj) {
		String objid = ResourceDeviceUtil.getDevicePrimaryKey(deviceObj);
		String rescode = ResourceDeviceUtil.getDeviceCode(deviceObj.getClass());
		EcLineLabelEntity queryEntity = new EcLineLabelEntity();
		queryEntity.setDevObjId(objid);
		queryEntity.setDeviceType(rescode);
		try {
			return dbUtil.excuteQuery(EcLineLabelEntity.class, queryEntity);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取被标签绑扎的设备
	 * @param labelno
	 * @return
	 */
	public Object getLabelTargetDevice(String labelno) {
		EcLineLabelEntity queryEntity = new EcLineLabelEntity();
		queryEntity.setLabelNo(labelno);
		try {
			List<EcLineLabelEntity> queryList = dbUtil.excuteQuery(EcLineLabelEntity.class, queryEntity);
			if (null == queryList || queryList.isEmpty()) {
				return null;
			}
			return ResourceDeviceUtil.getDeviceObj(queryList.get(0).getDevObjId(),
					queryList.get(0).getDeviceType() + "", dbUtil);

		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
}
