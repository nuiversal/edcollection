package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.util.AnnotationUtil;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineDeviceEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;

/**
 * 设备关联
 * 
 * @author zgq
 *
 */
public class DeviceLinkBll extends BaseBll<Object> {

	public DeviceLinkBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取记录数
	 * 
	 * @param tbName
	 * @param keyWord
	 * @return
	 */
	public Integer getRecordCount(String tbName, String keyWord) {
		String where = combineWhere(tbName, keyWord);
		List<?> list = this.queryByExpr2(getClsByTbName(tbName), where);
		if (list == null) {
			return null;
		}
		return list.size();
	}

	/**
	 * 获取记录数（带过滤）
	 * 
	 * @param tbName
	 * @param keyWord
	 * @return
	 */
	public Integer getCount(String tbName, String keyWord) {
		String where = combineWhere(tbName, keyWord);
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) this.queryByExpr2(getClsByTbName(tbName), where);
		if (list == null) {
			return null;
		}
		// 2、过滤数据
		List<Object> datas = new ArrayList<>();
		datas = filterDatas(list);
		return datas.size();
	}

	/**
	 * 构建查询条件
	 * 
	 * @param tbName
	 * @param keyWord
	 * @return
	 */
	private String combineWhere(String tbName, String keyWord) {
		String sbmc = "sbmc";
		String where = null;
		if (TableNameEnum.BDZ.getTableName().equals(tbName)) {// 变电站
			where = "(" + AnnotationUtil.getDbFieldByProNameXutil(EcSubstationEntity.class, "name") + " like '%"
					+ keyWord + "%' or "
					+ AnnotationUtil.getDbFieldByProNameXutil(EcSubstationEntity.class, "stationNo") + " like '%"
					+ keyWord + "%')";
		} else if (TableNameEnum.XSBDZ.getTableName().equals(tbName)) {// 箱式变电站
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcXsbdzEntity.class, sbmc) + " like '%" + keyWord
					+ "%' ";
		} else if (TableNameEnum.KGZ.getTableName().equals(tbName)) {// 开关站
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcKgzEntity.class, sbmc) + " like '%" + keyWord
					+ "%' ";
		} else if (TableNameEnum.GT.getTableName().equals(tbName)) {// 杆塔
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcTowerEntity.class, "towerNo") + " like '%" + keyWord
					+ "%' ";
		} else if (TableNameEnum.BYQ.getTableName().equals(tbName)) {// 变压器
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcTransformerEntity.class, sbmc) + " like '%"
					+ keyWord + "%' ";
		} else if (TableNameEnum.PDF.getTableName().equals(tbName)) {// 配电室
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcDistributionRoomEntity.class, sbmc) + " like '%"
					+ keyWord + "%' ";
		} else if (TableNameEnum.DYPDX.getTableName().equals(tbName)) {// 低压配电箱
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcDypdxEntity.class, sbmc) + " like '%" + keyWord
					+ "%' ";
		} else if (TableNameEnum.GJ.getTableName().equals(tbName)) {// 电缆井
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcWorkWellEntity.class, sbmc) + " like '%" + keyWord
					+ "%' ";
		} else if (TableNameEnum.HWG.getTableName().equals(tbName)) {// 环网柜
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcHwgEntity.class, sbmc) + " like '%" + keyWord
					+ "%' ";
		} else if (TableNameEnum.DLDZBQ.getTableName().equals(tbName)) {// 电子标签
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcLineLabelEntity.class, "labelNo") + " like '%"
					+ keyWord + "%' ";
		} else if (TableNameEnum.DLFZX.getTableName().equals(tbName)) {// 电缆分支箱
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcDlfzxEntity.class, sbmc) + " like '%" + keyWord
					+ "%' ";
		} else if (TableNameEnum.DYDLFZX.getTableName().equals(tbName)) {// 低压电缆分支箱
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcDydlfzxEntity.class, sbmc) + " like '%" + keyWord
					+ "%' ";
		} else if (TableNameEnum.DLZJJT.getTableName().equals(tbName)) {// 中间接头
			where = " " + AnnotationUtil.getDbFieldByProNameXutil(EcMiddleJointEntity.class, sbmc) + " like '%"
					+ keyWord + "%' ";
		}
		where += " and orgid='" + GlobalEntry.loginResult.getOrgid() + "'";
		return where;
	}

	/**
	 * 查询
	 * 
	 * @param tbName
	 *            表名
	 * @param keyWord
	 *            查询关键字
	 * @param pageIndex
	 *            页索引
	 * @param pageSize
	 *            页大小
	 * @return
	 */
	public List<?> queryDataListPaging(String tbName, String keyWord, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		String where = combineWhere(tbName, keyWord);
		return this.queryBypaging(getClsByTbName(tbName), where, pageIndex, pageSize);

	}

	/**
	 * 过滤数据
	 * 
	 * @param list
	 * @return
	 */
	private List<Object> filterDatas(List<Object> list) {
		List<Object> datas = new ArrayList<>();
		for (Object obj : list) {
			if (obj instanceof EcSubstationEntity) {// 变电站
				EcSubstationEntity ecSubstationEntity = (EcSubstationEntity) obj;
				if (isConnectedNodeDevice(ecSubstationEntity.getEcSubstationId())) {
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcXsbdzEntity) {// 箱式变电站
				EcXsbdzEntity ecXsbdzEntity = (EcXsbdzEntity) obj;
				if (isConnectedNodeDevice(ecXsbdzEntity.getObjId())) {
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcKgzEntity) {// 开关站
				EcKgzEntity ecKgzEntity = (EcKgzEntity) obj;
				if (isConnectedNodeDevice(ecKgzEntity.getObjId())) {
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcTowerEntity) {// 杆塔
				EcTowerEntity ecTowerEntity = (EcTowerEntity) obj;
				if (isConnectedNodeDevice(ecTowerEntity.getObjId())) {
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcTransformerEntity) {// 变压器
				EcTransformerEntity ecTransformerEntity = (EcTransformerEntity) obj;
				if (isConnectedNodeDevice(ecTransformerEntity.getObjId())) {
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcDistributionRoomEntity) {// 配电室
				EcDistributionRoomEntity ecDistributionRoomEntity = (EcDistributionRoomEntity) obj;
				if (isConnectedNodeDevice(ecDistributionRoomEntity.getObjId())) {
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcDypdxEntity) {// 低压配电箱
				EcDypdxEntity ecDypdxEntity = (EcDypdxEntity) obj;
				if (isConnectedNodeDevice(ecDypdxEntity.getObjId())) {
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcWorkWellEntity) {// 电缆井
				//暂时先去掉判断条件
				// EcWorkWellEntity ecWorkWellEntity = (EcWorkWellEntity) obj;
				// if (isConnectedNodeDevice(ecWorkWellEntity.getObjId())) {
				// continue;
				// }
				datas.add(obj);
			} else if (obj instanceof EcHwgEntity) {// 环网柜=====线路设备开始
				EcHwgEntity ecHwgEntity = (EcHwgEntity) obj;
				if (isConnectedlineDevices(ecHwgEntity.getObjId())) {
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcLineLabelEntity) {// 电子标签
				EcLineLabelEntity ecLineLabelEntity = (EcLineLabelEntity) obj;
				if (isConnectedlineDevices(ecLineLabelEntity.getObjId())) {// 过滤掉关联过线路设备的标签
					continue;
				}
				if (isConnectedWorkWellLable(ecLineLabelEntity.getDevObjId())) {// 过滤掉关联过工井的标签
					continue;
				}
				if (isConnectedMiddleJointLable(ecLineLabelEntity.getDevObjId())) {// 过滤掉关联过中间接头的标签
					continue;
				}
				if (isConnectedChannelLable(ecLineLabelEntity.getDevObjId())) {// 过滤掉关联过通道的标签
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcDlfzxEntity) {// 电缆分支箱
				EcDlfzxEntity ecDlfzxEntity = (EcDlfzxEntity) obj;
				if (isConnectedlineDevices(ecDlfzxEntity.getObjId())) {
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcDydlfzxEntity) {// 低压电缆分支箱
				EcDydlfzxEntity ecDydlfzxEntity = (EcDydlfzxEntity) obj;
				if (isConnectedlineDevices(ecDydlfzxEntity.getObjId())) {
					continue;
				}
				datas.add(obj);
			} else if (obj instanceof EcMiddleJointEntity) {// 中间接头
				EcMiddleJointEntity ecMiddleJointEntity = (EcMiddleJointEntity) obj;
				if (isConnectedlineDevices(ecMiddleJointEntity.getObjId())) {
					continue;
				}
				datas.add(obj);
			}
		}
		return datas;
	}

	/**
	 * 获取查询数据（带过滤）
	 * 
	 * @param tbName
	 * @param keyWord
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Object> getDatas(String tbName, String keyWord, int pageIndex, int pageSize) {
		List<Object> datas = new ArrayList<>();
		@SuppressWarnings("unchecked")
		// 1、获取查询数据
		List<Object> list = (List<Object>) queryDataListPaging(tbName, keyWord, pageIndex, pageSize);
		if (list == null) {
			return null;
		}
		// 2、过滤数据
		datas = filterDatas(list);
		return datas;
	}

	/**
	 * 判断是否关联过（节点设备）
	 * 
	 * @param objId
	 *            true表示关联过
	 * @return
	 */
	public boolean isConnectedNodeDevice(String objId) {
		String expr = "DEV_OBJ_ID='" + objId + "'";
		List<EcNodeDeviceEntity> nodeDevices = this.queryByExpr2(EcNodeDeviceEntity.class, expr);
		if (nodeDevices != null && nodeDevices.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否关联过（线路设备）
	 * 
	 * @param objId
	 *            true表示关联过
	 * @return
	 */
	public boolean isConnectedlineDevices(String objId) {
		String expr = "DEV_OBJ_ID='" + objId + "'";
		List<EcLineDeviceEntity> lineDevices = this.queryByExpr2(EcLineDeviceEntity.class, expr);
		if (lineDevices != null && lineDevices.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断这个标签是否关联过（工井）
	 * 
	 * @param objId
	 *            true表示关联过
	 * @return
	 */
	public boolean isConnectedWorkWellLable(String objId) {
		String expr = "OBJ_ID='" + objId + "'";
		List<EcWorkWellEntity> workWellLineLabels = this.queryByExpr2(EcWorkWellEntity.class, expr);
		if (workWellLineLabels != null && workWellLineLabels.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断这个标签是否关联过（中间接头）
	 * 
	 * @param objId
	 *            true表示关联过
	 * @return
	 */
	public boolean isConnectedMiddleJointLable(String objId) {
		String expr = "OBJ_ID='" + objId + "'";
		List<EcMiddleJointEntity> middleJointLables = this.queryByExpr2(EcMiddleJointEntity.class, expr);
		if (middleJointLables != null && middleJointLables.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断这个标签是否关联过（通道）
	 * 
	 * @param objId
	 *            true表示关联过
	 * @return
	 */
	public boolean isConnectedChannelLable(String objId) {
		String expr = "EC_CHANNEL_ID='" + objId + "'";
		List<EcChannelEntity> channelLables = this.queryByExpr2(EcChannelEntity.class, expr);
		if (channelLables != null && channelLables.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据表名获取对应的类
	 * 
	 * @param tbName
	 * @return
	 */
	public Class<?> getClsByTbName(String tbName) {
		for (TableNameEnum tableNameEnum : TableNameEnum.values()) {
			if (tbName.equals(tableNameEnum.getTableName())) {
				return tableNameEnum.getCls();
			}
		}
		return null;
	}

}
