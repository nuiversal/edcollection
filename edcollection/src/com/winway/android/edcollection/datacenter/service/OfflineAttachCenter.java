package com.winway.android.edcollection.datacenter.service;

import java.io.File;
import java.util.List;

import android.content.Context;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.datacenter.loader.LoaderConfig;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistBoxEntityVo;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;

/**
 * @author lyh
 * @version 创建时间：2017年1月13日 下午6:47:55
 * 
 */
public class OfflineAttachCenter {
	BaseDBUtil dbUtil;

	public OfflineAttachCenter(Context context, String dbUrl) {
		super();

		if (dbUrl == null) {
			dbUrl = LoaderConfig.dbUrl;
		}
		dbUtil = new BaseDBUtil(context, new File(dbUrl));
	}

	public OfflineAttachCenter(BaseDBUtil dbUtil) {
		super();
		this.dbUtil = dbUtil;
	}

	/**
	 * 拿到工井实体
	 * 
	 * @param ecWorkWellId
	 * @return
	 */
	public EcWorkWellEntityVo getWorkWell(String ecWorkWellId) {
		String where = "WORK_NO = '" + ecWorkWellId + "'";
		try {
			List<OfflineAttach> list = dbUtil.excuteWhereQuery(OfflineAttach.class, where);
			if (list == null) {
				return null;
			}
			EcWorkWellEntityVo ecWorkWellEntityVo = new EcWorkWellEntityVo();
			ecWorkWellEntityVo.setComUploadEntityList(list);
			return ecWorkWellEntityVo;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拿到配电房实体
	 * 
	 * @param ecDistributionRoomId
	 * @return
	 */
	public EcDistributionRoomEntityVo getRoom(String ecDistributionRoomId) {
		String where = "WORK_NO = '" + ecDistributionRoomId + "'";
		try {
			List<OfflineAttach> list = dbUtil.excuteWhereQuery(OfflineAttach.class, where);
			if (list == null) {
				return null;
			}
			EcDistributionRoomEntityVo distributionRoomEntityVo = new EcDistributionRoomEntityVo();
			distributionRoomEntityVo.setComUploadEntityList(list);
			return distributionRoomEntityVo;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拿到变电站实体
	 * 
	 * @param ecSubstationId
	 * @return
	 */
	public EcSubstationEntityVo getStation(String ecSubstationId) {
		String where = "WORK_NO = '" + ecSubstationId + "'";
		try {
			List<OfflineAttach> list = dbUtil.excuteWhereQuery(OfflineAttach.class, where);
			if (list == null) {
				return null;
			}
			EcSubstationEntityVo ecSubstationEntityVo = new EcSubstationEntityVo();
			ecSubstationEntityVo.setComUploadEntityList(list);
			return ecSubstationEntityVo;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拿到变压器实体
	 * 
	 * @param ecTransformerId
	 * @return
	 */
	public EcTransformerEntityVo getTrans(String ecTransformerId) {
		String where = "WORK_NO = '" + ecTransformerId + "'";
		try {
			List<OfflineAttach> list = dbUtil.excuteWhereQuery(OfflineAttach.class, where);
			if (list == null) {
				return null;
			}
			EcTransformerEntityVo ecTransformerEntityVo = new EcTransformerEntityVo();
			ecTransformerEntityVo.setComUploadEntityList(list);
			return ecTransformerEntityVo;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拿到杆塔实体
	 * 
	 * @param ecTowerId
	 * @return
	 */
	public EcTowerEntityVo getTower(String ecTowerId) {
		String where = "WORK_NO = '" + ecTowerId + "'";
		try {
			List<OfflineAttach> list = dbUtil.excuteWhereQuery(OfflineAttach.class, where);
			if (list == null) {
				return null;
			}
			EcTowerEntityVo ecTransformerEntityVo = new EcTowerEntityVo();
			ecTransformerEntityVo.setComUploadEntityList(list);
			return ecTransformerEntityVo;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拿到标签实体（含图片集合）
	 * 
	 * @param ecLineLabelId
	 * @return
	 */
	public EcLineLabelEntityVo getLabel(String ecLineLabelId) {
		try {
			String where = "WORK_NO ='" + ecLineLabelId + "'";
			List<OfflineAttach> excuteWhereQuery = dbUtil.excuteWhereQuery(OfflineAttach.class,
					where);
			if (excuteWhereQuery == null) {
				return null;
			}
			EcLineLabelEntityVo labelEntityVo = new EcLineLabelEntityVo();
			labelEntityVo.setAttr(excuteWhereQuery);
			return labelEntityVo;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拿到中间接头实体
	 * 
	 * @param ecMiddleJointId
	 * @return
	 */
	public EcMiddleJointEntityVo getMiddle(String ecMiddleJointId) {
		String where = "WORK_NO = '" + ecMiddleJointId + "'";
		try {
			List<OfflineAttach> list = dbUtil.excuteWhereQuery(OfflineAttach.class, where);
			if (list == null) {
				return null;
			}
			EcMiddleJointEntityVo ecMiddleJointEntityVo = new EcMiddleJointEntityVo();
			ecMiddleJointEntityVo.setAttr(list);
			return ecMiddleJointEntityVo;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拿到分接箱实体
	 * 
	 * @param ecDistBoxId
	 * @return
	 */
	public EcDistBoxEntityVo getBox(String ecDistBoxId) {
		String where = "WORK_NO = '" + ecDistBoxId + "'";
		try {
			List<OfflineAttach> list = dbUtil.excuteWhereQuery(OfflineAttach.class, where);
			if (list == null) {
				return null;
			}
			EcDistBoxEntityVo ecDistBoxEntityVo = new EcDistBoxEntityVo();
			ecDistBoxEntityVo.setAttr(list);
			return ecDistBoxEntityVo;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据oid去离线附件表拿到照片集合
	 * 
	 * @param oid
	 * @return
	 */
	public List<OfflineAttach> getNode(String oid) {
		List<OfflineAttach> offlineAttachs = null;
		try {
			String where = "WORK_NO ='" + oid + "'";
			offlineAttachs = dbUtil.excuteWhereQuery(OfflineAttach.class, where);
			if (offlineAttachs == null || offlineAttachs.isEmpty()) {
				return null;
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return offlineAttachs;
	}

	/**
	 * 根据对象id去离线附件表拿到照片集合
	 * 
	 * @param workNo
	 * @return
	 */
	public List<OfflineAttach> getOfflineAttachListByWorkNo(String workNo) {
		try {
			String where = "WORK_NO ='" + workNo + "'";
			return dbUtil.excuteWhereQuery(OfflineAttach.class, where);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
