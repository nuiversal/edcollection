package com.winway.android.edcollection.export.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.project.entity.EcLineDeviceEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;

/**
 * 导出Excel工具类
 * 
 * @author xs
 *
 */
public class ExportExcelUtils {
	/**
	 * 单例
	 */
	private static ExportExcelUtils instance;
	
	public static ExportExcelUtils getInstance() {
		if (null == instance) {
			synchronized (ExportExcelUtils.class) {
				if (null == instance) {
					instance = new ExportExcelUtils();
				}
			}
		}
		return instance;
	}
	
	private ExportExcelUtils() {}

	/**
	 * 根据节点设备主键id获取当前项目节点map
	 * 
	 * @param devStr
	 * @throws DbException
	 */
	public Map<String, EcNodeEntity> getNodeMapsByNodeDevId(Context context, BaseDBUtil dbUtil, String devStr,
			Map<String, EcNodeEntity> mOid2NodeMap) throws DbException {
		Map<String, EcNodeEntity> nodeDevId2NodeMap = new HashMap<>();
		String where = " DEV_OBJ_ID in('" + devStr + "')";
		List<EcNodeDeviceEntity> nodeDevices = dbUtil.excuteWhereQuery(EcNodeDeviceEntity.class, where);
		if (null == nodeDevices || nodeDevices.isEmpty()) {
			return null;
		}
		for (EcNodeDeviceEntity nodeDeviceEntity : nodeDevices) {
			EcNodeEntity nodeEntity = mOid2NodeMap.get(nodeDeviceEntity.getOid());
			nodeDevId2NodeMap.put(nodeDeviceEntity.getDevObjId(), nodeEntity);
		}
		return nodeDevId2NodeMap;
	}
	
	/**
	 * 根据线路设备主键id获取线路节点设备集合
	 * 
	 * @param devStr
	 * @throws DbException
	 */
	private List<EcLineDeviceEntity> getLineDevByLineDevId(BaseDBUtil dbUtil, String devStr) throws DbException {
		String where = " DEV_OBJ_ID in('" + devStr + "')";
		List<EcLineDeviceEntity> nodeDevices = dbUtil.excuteWhereQuery(EcLineDeviceEntity.class, where);
		if (null == nodeDevices || nodeDevices.isEmpty()) {
			return null;
		}
		return nodeDevices;
	}
	
	/**
	 * 根据线路id获取线路map
	 * @param dbUtil
	 * @param lineId 线路id
	 * @param mOid2NodeMap
	 * @return
	 * @throws DbException
	 */
	public Map<String, EcLineEntity> getLineMapBylineId(BaseDBUtil dbUtil,String lineId,Map<String, EcLineEntity> mLineMap) throws DbException {
		String where = " EC_LINE_ID in('" + lineId + "')";
		List<EcLineEntity> whereQuery = dbUtil.excuteWhereQuery(EcLineEntity.class, where);
		if (null == whereQuery || whereQuery.isEmpty()) {
			return null;
		}
		Map<String, EcLineEntity> lineDevId2NodeMap = new HashMap<>();
		for (EcLineEntity nodeDeviceEntity : whereQuery) {
			lineDevId2NodeMap.put(nodeDeviceEntity.getEcLineId(), nodeDeviceEntity);
		}
		return lineDevId2NodeMap;
	}
	
	/**
	 *  根据oid获取节点map
	 * @param dbUtil
	 * @param oid
	 * @param mOid2NodeMap
	 * @return
	 * @throws DbException
	 */
	public Map<String, EcNodeEntity> getNodeMapByOid(BaseDBUtil dbUtil,String oid,Map<String, EcNodeEntity> mOid2NodeMap) throws DbException {
		String where = " OID in('" + oid + "')";
		List<EcNodeEntity> whereQuery = dbUtil.excuteWhereQuery(EcNodeEntity.class, where);
		if (null == whereQuery || whereQuery.isEmpty()) {
			return null;
		}
		Map<String, EcNodeEntity> NodeMap = new HashMap<>();
		for (EcNodeEntity nodeDeviceEntity : whereQuery) {
			NodeMap.put(nodeDeviceEntity.getOid(), nodeDeviceEntity);
		}
		return NodeMap;
	}
	
	
	/**
	 * 根据线路设备主键id获取当前项目节点map
	 * @param context
	 * @param dbUtil
	 * @param devStr
	 * @param mOid2NodeMap
	 * @return
	 * @throws DbException
	 */
	public Map<String, EcNodeEntity> getNodeMapsByLineDevId(Context context, BaseDBUtil dbUtil, String devStr,Map<String, EcNodeEntity> mOid2NodeMap) throws DbException {
		Map<String, EcNodeEntity> lineDevId2NodeMap = new HashMap<>();
		List<EcLineDeviceEntity> lineDevByLineDevId = getLineDevByLineDevId(dbUtil, devStr);
		if (lineDevByLineDevId == null) {
			return null;
		}
		for (EcLineDeviceEntity nodeDeviceEntity : lineDevByLineDevId) {
			EcNodeEntity nodeEntity = mOid2NodeMap.get(nodeDeviceEntity.getOid());
			lineDevId2NodeMap.put(nodeDeviceEntity.getDevObjId(), nodeEntity);
		}
		return lineDevId2NodeMap;
	}
	
	/**
	 * 根据线路设备主键id获取当前项目线路map
	 * @param context
	 * @param dbUtil
	 * @param devStr
	 * @param mOid2NodeMap
	 * @return
	 * @throws DbException
	 */
	public Map<String, EcLineEntity> getLineMapsByLineDevId(Context context, BaseDBUtil dbUtil, String devStr,Map<String, EcLineEntity> mLineMap) throws DbException {
		Map<String, EcLineEntity> lineDevLineMap = new HashMap<>();
		List<EcLineDeviceEntity> lineDevByLineDevId = getLineDevByLineDevId(dbUtil, devStr);
		if (lineDevByLineDevId == null) {
			return null;
		}
		for (EcLineDeviceEntity nodeDeviceEntity : lineDevByLineDevId) {
			EcLineEntity nodeEntity = mLineMap.get(nodeDeviceEntity.getLineNo());
			lineDevLineMap.put(nodeDeviceEntity.getDevObjId(), nodeEntity);
		}
		return lineDevLineMap;
	}
	
}
