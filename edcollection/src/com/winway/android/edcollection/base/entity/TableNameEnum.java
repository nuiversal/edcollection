package com.winway.android.edcollection.base.entity;

import com.winway.android.edcollection.base.util.AnnotationUtil;
import com.winway.android.edcollection.login.entity.EdpOrgInfoEntity;
import com.winway.android.edcollection.login.entity.EdpRoleInfoEntity;
import com.winway.android.edcollection.login.entity.EdpUserInfoEntity;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
import com.winway.android.edcollection.project.entity.EcChannelLinesEntity;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcChannelSectionEntity;
import com.winway.android.edcollection.project.entity.EcChannelSectionPointsEntity;
import com.winway.android.edcollection.project.entity.EcDeviceClassEntity;
import com.winway.android.edcollection.project.entity.EcDeviceCostEntity;
import com.winway.android.edcollection.project.entity.EcDeviceEntity;
import com.winway.android.edcollection.project.entity.EcDevicePlanEntity;
import com.winway.android.edcollection.project.entity.EcDistBoxEntity;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcExternalDamageEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLayingLineEntity;
import com.winway.android.edcollection.project.entity.EcLineBaseEntity;
import com.winway.android.edcollection.project.entity.EcLineBranchEntity;
import com.winway.android.edcollection.project.entity.EcLineDeviceEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcLinePEntity;
import com.winway.android.edcollection.project.entity.EcLinePartEntity;
import com.winway.android.edcollection.project.entity.EcMarkerIdsEntity;
import com.winway.android.edcollection.project.entity.EcMarkerPEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcVRRefEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.edcollection.project.entity.Em4gHistoryEntity;
import com.winway.android.edcollection.project.entity.Em4gPackageEntity;
import com.winway.android.edcollection.project.entity.EmAutoQcEntity;
import com.winway.android.edcollection.project.entity.EmAutoQcItemEntity;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.edcollection.project.entity.EmCheckEntity;
import com.winway.android.edcollection.project.entity.EmDangerInfoEntity;
import com.winway.android.edcollection.project.entity.EmMembersEntity;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.edcollection.project.entity.EmProjectMoreinfoEntity;
import com.winway.android.edcollection.project.entity.EmTaskItemEntity;
import com.winway.android.edcollection.project.entity.EmTasksEntity;
import com.winway.android.edcollection.project.entity.EmWillCheckEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;

/**
 * 表名枚举类
 * 
 * @author Administrator
 *
 */
public enum TableNameEnum {
	JSXX(AnnotationUtil.getTableNameXutilAnno(EdpRoleInfoEntity.class), EdpRoleInfoEntity.class), // 角色信息表
	JGXX(AnnotationUtil.getTableNameXutilAnno(EdpOrgInfoEntity.class), EdpOrgInfoEntity.class), // 机构信息表
	YHXX(AnnotationUtil.getTableNameXutilAnno(EdpUserInfoEntity.class), EdpUserInfoEntity.class), // 用户信息表
	GCCY(AnnotationUtil.getTableNameXutilAnno(EmMembersEntity.class), EmMembersEntity.class), // 工程成员
	XMXX(AnnotationUtil.getTableNameXutilAnno(EmProjectEntity.class), EmProjectEntity.class), // 项目信息表
	XMXXXX(AnnotationUtil.getTableNameXutilAnno(EmProjectMoreinfoEntity.class), EmProjectMoreinfoEntity.class), // 项目详细信息
	SWKTC(AnnotationUtil.getTableNameXutilAnno(Em4gPackageEntity.class), Em4gPackageEntity.class), // 上网卡套餐信息
	SWKSYLS(AnnotationUtil.getTableNameXutilAnno(Em4gHistoryEntity.class), Em4gHistoryEntity.class), // 上网卡使用历史记录
	SJGXRZ(AnnotationUtil.getTableNameXutilAnno(EmCDataLogEntity.class), EmCDataLogEntity.class), // 数据更新日志
	DSHSJ(AnnotationUtil.getTableNameXutilAnno(EmWillCheckEntity.class), EmWillCheckEntity.class), // 待审核数据表
	//SJSHJL(AnnotationUtil.getTableNameXutilAnno(EmMembersEntity.class), EmMembersEntity.class), // 数据审核记录
	DLDZBQ(AnnotationUtil.getTableNameXutilAnno(EcLineLabelEntity.class), EcLineLabelEntity.class), // 电缆电子标签
	FJX(AnnotationUtil.getTableNameXutilAnno(EcDistBoxEntity.class), EcDistBoxEntity.class), // 分接箱
	DLZJJT(AnnotationUtil.getTableNameXutilAnno(EcMiddleJointEntity.class), EcMiddleJointEntity.class), // 电缆中间接头
	XLFSSB(AnnotationUtil.getTableNameXutilAnno(EcLineDeviceEntity.class), EcLineDeviceEntity.class), // 线路附属设备
	//TD(AnnotationUtil.getTableNameXutilAnno(EcChannelEntity.class), EcChannelEntity.class), // 通道
	//TDFSLX(AnnotationUtil.getTableNameXutilAnno(EcChannelLinesEntity.class), EcChannelLinesEntity.class), // 通道敷设线路
	//TDJDGL(AnnotationUtil.getTableNameXutilAnno(EcChannelNodesEntity.class), EcChannelNodesEntity.class), // 通道节点关联表
	LJD(AnnotationUtil.getTableNameXutilAnno(EcNodeEntity.class), EcNodeEntity.class), // 路径点
	LJDSB(AnnotationUtil.getTableNameXutilAnno(EcNodeDeviceEntity.class), EcNodeDeviceEntity.class), // 路径点设备
	XLJDGL(AnnotationUtil.getTableNameXutilAnno(EcLineNodesEntity.class), EcLineNodesEntity.class), // 线路节点关联表
	SDXL(AnnotationUtil.getTableNameXutilAnno(EcLineEntity.class), EcLineEntity.class), // 输电线路/架空导线
	GJ(AnnotationUtil.getTableNameXutilAnno(EcWorkWellEntity.class), EcWorkWellEntity.class), // 工井
	JG(AnnotationUtil.getTableNameXutilAnno(EcWorkWellCoverEntity.class), EcWorkWellCoverEntity.class), // 井盖
	PDF(AnnotationUtil.getTableNameXutilAnno(EcDistributionRoomEntity.class), EcDistributionRoomEntity.class), // 配电房
	BDZ(AnnotationUtil.getTableNameXutilAnno(EcSubstationEntity.class), EcSubstationEntity.class), // 变电站
	BYQ(AnnotationUtil.getTableNameXutilAnno(EcTransformerEntity.class), EcTransformerEntity.class), // 变压器
	GT(AnnotationUtil.getTableNameXutilAnno(EcTowerEntity.class), EcTowerEntity.class), // 杆塔
	// YXCS("EM_M_PARAM"), // 运行参数表
	BSQIDJH(AnnotationUtil.getTableNameXutilAnno(EcMarkerIdsEntity.class), EcMarkerIdsEntity.class), // 标识器ID集合
	LXFJ(AnnotationUtil.getTableNameXutilAnno(OfflineAttach.class), OfflineAttach.class), // 离线附件表
	HWG(AnnotationUtil.getTableNameXutilAnno(EcHwgEntity.class), EcHwgEntity.class), // 环网柜表
	DLFZX(AnnotationUtil.getTableNameXutilAnno(EcDlfzxEntity.class), EcDlfzxEntity.class), // 电缆分支箱表
	DYDLFZX(AnnotationUtil.getTableNameXutilAnno(EcDydlfzxEntity.class), EcDydlfzxEntity.class), // 低压电缆分支箱表
	KGZ(AnnotationUtil.getTableNameXutilAnno(EcKgzEntity.class), EcKgzEntity.class), // 开关站表
	XSBDZ(AnnotationUtil.getTableNameXutilAnno( EcXsbdzEntity.class), EcXsbdzEntity.class), // 箱式变电站表
	DYPDX(AnnotationUtil.getTableNameXutilAnno(EcDypdxEntity.class), EcDypdxEntity.class), // 低压配电箱表

	// 增加缺失的实体
	EC_CHANNEL_DG(AnnotationUtil.getTableNameXutilAnno(EcChannelDgEntity.class), EcChannelDgEntity.class),//顶管
	EC_CHANNEL_DLGD(AnnotationUtil.getTableNameXutilAnno(EcChannelDlgdEntity.class),EcChannelDlgdEntity.class), //电缆管道
	EC_CHANNEL_DLG(AnnotationUtil.getTableNameXutilAnno(EcChannelDlgEntity.class), EcChannelDlgEntity.class),//电缆沟
	EC_CHANNEL_DLQ(AnnotationUtil.getTableNameXutilAnno(EcChannelDlqEntity.class), EcChannelDlqEntity.class),//电缆桥
	EC_CHANNEL_DLSD(AnnotationUtil.getTableNameXutilAnno(EcChannelDlsdEntity.class),EcChannelDlsdEntity.class),//电缆隧道 
	EC_CHANNEL_DLZM(AnnotationUtil.getTableNameXutilAnno(EcChannelDlzmEntity.class), EcChannelDlzmEntity.class),//电缆直埋
	EC_CHANNEL_JK(AnnotationUtil.getTableNameXutilAnno(EcChannelJkEntity.class), EcChannelJkEntity.class),//架空
	EC_CHANNEL_DLC(AnnotationUtil.getTableNameXutilAnno(EcChannelDlcEntity.class),EcChannelDlcEntity.class),//电缆槽
	
	EC_CHANNEL(AnnotationUtil.getTableNameXutilAnno(EcChannelEntity.class), EcChannelEntity.class),//通道
	EC_CHANNEL_NODE(AnnotationUtil.getTableNameXutilAnno(EcChannelNodesEntity.class), EcChannelNodesEntity.class),//通道关联节点
	EC_CHANNEL_LINE(AnnotationUtil.getTableNameXutilAnno(EcChannelLinesEntity.class), EcChannelLinesEntity.class),//通道关联线路
	
	
	// EC_CHANNEL_PLANNING("EC_CHANNEL_PLANNING",EcChannelPlanningEntity.class),
	// EC_CHANNEL_PLANNING_LINE("EC_CHANNEL_PLANNING_LINE",EcChannelPlanningLineEntity.class),
	// EC_CHANNEL_PLANNING_LINE_REF("EC_CHANNEL_PLANNING_LINE_REF",EcChannelPlanningLineRefEntity.class),
	EC_DEVICE_CLASS(AnnotationUtil.getTableNameXutilAnno(EcDeviceClassEntity.class), EcDeviceClassEntity.class),
	EC_DEVICE_COST(AnnotationUtil.getTableNameXutilAnno(EcDeviceCostEntity.class),EcDeviceCostEntity.class), 
	EC_DEVICE(AnnotationUtil.getTableNameXutilAnno(EcDeviceEntity.class), EcDeviceEntity.class), 
	EC_DEVICE_PLAN(AnnotationUtil.getTableNameXutilAnno(EcDevicePlanEntity.class),EcDevicePlanEntity.class), 
	EC_EXTERNAL_DAMAGE(AnnotationUtil.getTableNameXutilAnno(EcExternalDamageEntity.class),EcExternalDamageEntity.class), 
	EC_LAYING_LINE(AnnotationUtil.getTableNameXutilAnno(EcLayingLineEntity.class),EcLayingLineEntity.class),
	EC_LINE_BASE(AnnotationUtil.getTableNameXutilAnno(EcLineBaseEntity.class),EcLineBaseEntity.class), 
	EC_LINE_BRANCH(AnnotationUtil.getTableNameXutilAnno(EcLineBranchEntity.class),EcLineBranchEntity.class), 
	EC_LINE_PART(AnnotationUtil.getTableNameXutilAnno(EcLinePartEntity.class),EcLinePartEntity.class), 
	EC_LINE_P(AnnotationUtil.getTableNameXutilAnno(EcLinePEntity.class),EcLinePEntity.class), 
	EC_MARKER_P(AnnotationUtil.getTableNameXutilAnno(EcMarkerPEntity.class),EcMarkerPEntity.class), 
	EM_AUTO_QC(AnnotationUtil.getTableNameXutilAnno(EmAutoQcEntity.class),EmAutoQcEntity.class), 
	EM_AUTO_QC_ITEM(AnnotationUtil.getTableNameXutilAnno(EmAutoQcItemEntity.class),EmAutoQcItemEntity.class),
	EM_CHECK(AnnotationUtil.getTableNameXutilAnno(EmCheckEntity.class),EmCheckEntity.class),
	EM_DANGER_INFO(AnnotationUtil.getTableNameXutilAnno(EmDangerInfoEntity.class),EmDangerInfoEntity.class), 
	EM_TASK_ITEM(AnnotationUtil.getTableNameXutilAnno(EmTaskItemEntity.class),EmTaskItemEntity.class),
	EM_TASKS(AnnotationUtil.getTableNameXutilAnno(EmTasksEntity.class),EmTasksEntity.class),
	
	//通道截面表
	EC_CHANNEL_SECTION(AnnotationUtil.getTableNameXutilAnno(EcChannelSectionEntity.class),EcChannelSectionEntity.class),
	EC_CHANNEL_SECTION_POINT(AnnotationUtil.getTableNameXutilAnno(EcChannelSectionPointsEntity.class),EcChannelSectionPointsEntity.class),
	EC_VRREF(AnnotationUtil.getTableNameXutilAnno(EcVRRefEntity.class),EcVRRefEntity.class);
	
	

	private String tableName;
	private Class<?> cls;

	TableNameEnum(String tableName, Class<?> cls) {
		this.tableName = tableName;
		this.cls = cls;
	}

	public String getTableName() {
		return tableName;
	}

	public Class<?> getCls() {
		return cls;
	}
	
	/**
	 * 根据表名获取枚举类中Class对象
	 * @param tableName
	 * @return
	 */
	public static Class<?> getClsByTableName(String tableName) {
		for (TableNameEnum tableNameEnum : TableNameEnum.values()) {
			if (tableNameEnum.getTableName().equalsIgnoreCase(tableName)) {
				return tableNameEnum.getCls();
			}
		}
		return null;

	}
	

}
