package com.winway.android.edcollection.task.util;

import java.util.List;

import android.content.Context;

import com.winway.android.edcollection.adding.controll.ChannelControll;
import com.winway.android.edcollection.adding.controll.ChannelControll.ObjidAndTableName;
import com.winway.android.edcollection.adding.entity.ChannelLabelEntity;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
import com.winway.android.edcollection.project.entity.EmTaskItemEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDypdxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcKgzEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.edcollection.project.vo.EcXsbdzEntityVo;

/**
 * 路径点设备和线路设备的更新操作
 *
 * @author lyh
 * @data 2017年2月24日
 */
public class UpdateDeviceAndAttr extends BaseBll<Object> {
	private  CommonBll commonBll;

	public UpdateDeviceAndAttr(Context context, String dbUrl) {
		super(context, dbUrl);
		commonBll = new CommonBll(context, dbUrl);
	}

	/**
	 * 更新工井数据
	 * 
	 * @param ecWorkWellEntity
	 */
	public  void updateGjDatas(EcWorkWellEntityVo ecWorkWellEntity, EmTaskItemEntity emTaskItemEntity) {
		commonBll.handleDataLog(ecWorkWellEntity.getObjId(), TableNameEnum.GJ.getTableName(), DataLogOperatorType.update,
				WhetherEnum.NO, "编辑"+ecWorkWellEntity.getSbmc(), false);
		commonBll.update(ecWorkWellEntity);
		commonBll.update(emTaskItemEntity);
		//保存标签数据
		EcLineLabelEntityVo ecLineLabelEntityVo = ecWorkWellEntity.getEcLineLabelEntityVo();
		if (ecLineLabelEntityVo != null) {
			commonBll.saveOrUpdate(ecLineLabelEntityVo);
			List<OfflineAttach> labelAttrList = ecLineLabelEntityVo.getAttr();
			saveOrUpdateAttach(labelAttrList);
			// 保存日志
			commonBll.handleDataLog(ecLineLabelEntityVo.getObjId(), TableNameEnum.DLDZBQ.getTableName(),
					DataLogOperatorType.update, WhetherEnum.NO, TableNameEnum.GJ.getTableName()+"-"+ecWorkWellEntity.getSbmc() + "标签操作");			
		}
		// 保存工井附件
		List<OfflineAttach> comUploadEntityWorkWellList = ecWorkWellEntity.getComUploadEntityList();
		saveOrUpdateAttach(comUploadEntityWorkWellList);
	}

	/**
	 * 更新变电站数据
	 * 
	 * @param ecSubstationEntityVo
	 */
	public  void updateBdzDatas(EcSubstationEntityVo ecSubstationEntityVo, EmTaskItemEntity emTaskItemEntity) {
		commonBll.handleDataLog(ecSubstationEntityVo.getEcSubstationId(), TableNameEnum.BDZ.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecSubstationEntityVo.getName(), false);
		commonBll.update(ecSubstationEntityVo);
		commonBll.update(emTaskItemEntity);
		// 保存变电站附件
		List<OfflineAttach> ComUploadEntitySubList = ecSubstationEntityVo.getComUploadEntityList();
		saveOrUpdateAttach(ComUploadEntitySubList);
	}

	/**
	 * 更新箱式变电站数据
	 * 
	 * @param ecXsbdzEntityVo
	 */
	public  void updateXsbdzDatas(EcXsbdzEntityVo ecXsbdzEntityVo, EmTaskItemEntity emTaskItemEntity) {
		commonBll.handleDataLog(ecXsbdzEntityVo.getObjId(), TableNameEnum.XSBDZ.getTableName(), DataLogOperatorType.update,
				WhetherEnum.NO, "编辑：" + ecXsbdzEntityVo.getObjId(), false);
		commonBll.update(ecXsbdzEntityVo);
		commonBll.update(emTaskItemEntity);
		// 保存箱式变电站附件
		List<OfflineAttach> ComUploadEntitySubList = ecXsbdzEntityVo.getComUploadEntityList();
		saveOrUpdateAttach(ComUploadEntitySubList);

	}

	/**
	 * 更新开关站数据
	 * 
	 * @param ecKgzEntityVo
	 */
	public  void updateKgzDatas(EcKgzEntityVo ecKgzEntityVo, EmTaskItemEntity emTaskItemEntity) {
		commonBll.handleDataLog(ecKgzEntityVo.getObjId(), TableNameEnum.KGZ.getTableName(), DataLogOperatorType.update,
				WhetherEnum.NO, "编辑：" + ecKgzEntityVo.getObjId(), false);
		commonBll.update(ecKgzEntityVo);
		commonBll.update(emTaskItemEntity);
		// 保存开关站附件
		List<OfflineAttach> ComUploadEntitySubList = ecKgzEntityVo.getComUploadEntityList();
		saveOrUpdateAttach(ComUploadEntitySubList);
	}

	/**
	 * 更新杆塔数据
	 * 
	 * @param ecTowerEntityVo
	 */
	public  void updateGtDatas(EcTowerEntityVo ecTowerEntityVo, EmTaskItemEntity emTaskItemEntity) {
		commonBll.handleDataLog(ecTowerEntityVo.getObjId(), TableNameEnum.GT.getTableName(), DataLogOperatorType.update,
				WhetherEnum.NO, "编辑：" + ecTowerEntityVo.getTowerNo(), false);
		commonBll.update(ecTowerEntityVo);
		commonBll.update(emTaskItemEntity);
		// 保存杆塔附件
		List<OfflineAttach> comUploadEntityTowerList = ecTowerEntityVo.getComUploadEntityList();
		saveOrUpdateAttach(comUploadEntityTowerList);
	}

	/**
	 * 更新变压器数据
	 * 
	 * @param ecTransformerEntityVo
	 */
	public  void updateByqDatas(EcTransformerEntityVo ecTransformerEntityVo, EmTaskItemEntity emTaskItemEntity) {
		commonBll.handleDataLog(ecTransformerEntityVo.getObjId(), TableNameEnum.BYQ.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecTransformerEntityVo.getSbmc(), false);
		commonBll.update(ecTransformerEntityVo);
		commonBll.update(emTaskItemEntity);
		// 保存变压器附件
		List<OfflineAttach> comUploadEntityTransformerList = ecTransformerEntityVo.getComUploadEntityList();
		saveOrUpdateAttach(comUploadEntityTransformerList);
	}

	/**
	 * 更新配电室数据
	 * 
	 * @param ecDistributionRoomEntityVo
	 */
	public  void updatePdsDatas(EcDistributionRoomEntityVo ecDistributionRoomEntityVo,
			EmTaskItemEntity emTaskItemEntity) {
		commonBll.handleDataLog(ecDistributionRoomEntityVo.getObjId(), TableNameEnum.PDF.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecDistributionRoomEntityVo.getSbmc(), false);
		commonBll.update(ecDistributionRoomEntityVo);
		commonBll.update(emTaskItemEntity);
		// 保存配电房附件
		List<OfflineAttach> comUploadEntityDistRoomList = ecDistributionRoomEntityVo.getComUploadEntityList();
		saveOrUpdateAttach(comUploadEntityDistRoomList);
	}

	/**
	 * 更新低压配电箱数据
	 * 
	 * @param ecDypdxEntityVo
	 */
	public  void updateDypdxDatas(EcDypdxEntityVo ecDypdxEntityVo, EmTaskItemEntity emTaskItemEntity) {
		commonBll.handleDataLog(ecDypdxEntityVo.getObjId(), TableNameEnum.DYPDX.getTableName(), DataLogOperatorType.update,
				WhetherEnum.NO, "编辑：" + ecDypdxEntityVo.getSbmc(), false);
		commonBll.update(ecDypdxEntityVo);
		commonBll.update(emTaskItemEntity);
		// 保存低压配电室附件
		List<OfflineAttach> ComUploadEntitySubList = ecDypdxEntityVo.getComUploadEntityList();
		saveOrUpdateAttach(ComUploadEntitySubList);
	}

	/**
	 * 更新环网柜数据
	 * 
	 * @param ecHwgEntityVo
	 */
	public  void updateHwgDatas(EcHwgEntityVo ecHwgEntityVo, EmTaskItemEntity taskItemEntity) {
		commonBll.update(taskItemEntity);
		commonBll.update(ecHwgEntityVo);
		commonBll.handleDataLog(ecHwgEntityVo.getObjId(), TableNameEnum.HWG.getTableName(), DataLogOperatorType.update,
				WhetherEnum.NO, "编辑"+ecHwgEntityVo.getSbmc(), false);
		// 保存离线附件表
		List<OfflineAttach> comUploadList = ecHwgEntityVo.getAttr();
		saveOrUpdateAttach(comUploadList);
	}

	/**
	 * 更新电子标签数据
	 * 
	 * @param ecLineLabelEntityVo
	 */
	public  void updateDzbqDatas(EcLineLabelEntityVo ecLineLabelEntityVo, EmTaskItemEntity emTaskItemEntity) {
		commonBll.update(ecLineLabelEntityVo);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(ecLineLabelEntityVo.getObjId(), TableNameEnum.DLDZBQ.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+ecLineLabelEntityVo.getDevName(), false);
		// 保存离线附件表
		List<OfflineAttach> comUploadList = ecLineLabelEntityVo.getAttr();
		saveOrUpdateAttach(comUploadList);
	}

	/**
	 * 更新电缆分支箱数据
	 * 
	 * @param ecDlfzxEntityVo
	 */
	public  void updateDlfzxDatas(EcDlfzxEntityVo ecDlfzxEntityVo, EmTaskItemEntity emTaskItemEntity) {
		commonBll.update(ecDlfzxEntityVo);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(ecDlfzxEntityVo.getObjId(), TableNameEnum.DLFZX.getTableName(), DataLogOperatorType.update,
				WhetherEnum.NO, "编辑"+ecDlfzxEntityVo.getSbmc(), false);
		// 保存离线附件表
		List<OfflineAttach> comUploadList = ecDlfzxEntityVo.getAttr();
		saveOrUpdateAttach(comUploadList);
	}

	/**
	 * 更新低压电缆分支箱数据
	 * 
	 * @param ecDydlfzxEntityVo
	 */
	public  void updateDydlfzxDatas(EcDydlfzxEntityVo ecDydlfzxEntityVo, EmTaskItemEntity emTaskItemEntity) {
		commonBll.update(ecDydlfzxEntityVo);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(ecDydlfzxEntityVo.getObjId(), TableNameEnum.DYDLFZX.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+ecDydlfzxEntityVo.getSbmc(), false);
		// 保存离线附件表
		List<OfflineAttach> comUploadList = ecDydlfzxEntityVo.getAttr();
		saveOrUpdateAttach(comUploadList);
	}

	/**
	 * 更新中间接头数据
	 * 
	 * @param ecMiddleJointEntityVo
	 */
	public  void updateZjjtDatas(EcMiddleJointEntityVo ecMiddleJointEntityVo, EmTaskItemEntity emTaskItemEntity) {
		commonBll.update(ecMiddleJointEntityVo);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(ecMiddleJointEntityVo.getObjId(), TableNameEnum.DLZJJT.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+ecMiddleJointEntityVo.getSbmc(), false);
		//保存标签数据
		EcLineLabelEntityVo ecLineLabelEntityVo = ecMiddleJointEntityVo.getEcLineLabelEntityVo();
		if (ecLineLabelEntityVo != null) {
			commonBll.saveOrUpdate(ecLineLabelEntityVo);
			List<OfflineAttach> labelAttrList = ecLineLabelEntityVo.getAttr();
			saveOrUpdateAttach(labelAttrList);
			// 保存日志
			commonBll.handleDataLog(ecLineLabelEntityVo.getObjId(), TableNameEnum.DLDZBQ.getTableName(),
					DataLogOperatorType.update, WhetherEnum.NO, TableNameEnum.DLZJJT.getTableName()+"-"+ecMiddleJointEntityVo.getSbmc() + "标签操作");			
		}		
		// 保存离线附件表
		List<OfflineAttach> comUploadList = ecMiddleJointEntityVo.getAttr();
		saveOrUpdateAttach(comUploadList);
	}

	/**
	 * 更新顶管数据
	 * 
	 * @param ecChannelDgEntity
	 * @param channelEntity
	 * @param emTaskItemEntity
	 */
	public  void updateDgDatas(EcChannelDgEntity ecChannelDgEntity, EcChannelEntity channelEntity,
			EmTaskItemEntity emTaskItemEntity) {
		commonBll.saveOrUpdate(ecChannelDgEntity);
		commonBll.saveOrUpdate(channelEntity);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL_DG.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑顶管/拖拉管", false);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+channelEntity.getName(), false);
	}

	/**
	 * 更新电缆管
	 * 
	 * @param ecChannelDlgEntity
	 * @param channelEntity
	 * @param emTaskItemEntity
	 */
	public  void updateDlgDatas(EcChannelDlgEntity ecChannelDlgEntity, EcChannelEntity channelEntity,
			EmTaskItemEntity emTaskItemEntity) {
		commonBll.saveOrUpdate(ecChannelDlgEntity);
		commonBll.saveOrUpdate(channelEntity);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLG.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑电缆沟", false);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+channelEntity.getName(), false);
	}

	/**
	 * 更新电缆管道
	 * 
	 * @param ecChannelDlgdEntity
	 * @param channelEntity
	 * @param emTaskItemEntity
	 */
	public  void updateDlgdDatas(EcChannelDlgdEntity ecChannelDlgdEntity, EcChannelEntity channelEntity,
			EmTaskItemEntity emTaskItemEntity) {
		commonBll.saveOrUpdate(ecChannelDlgdEntity);
		commonBll.saveOrUpdate(channelEntity);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLGD.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑电缆管道", false);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+channelEntity.getName(), false);
	}

	/**
	 * 更新电缆桥
	 * 
	 * @param ecChannelDlqEntity
	 * @param channelEntity
	 * @param emTaskItemEntity
	 */
	public  void updateDlqDatas(EcChannelDlqEntity ecChannelDlqEntity, EcChannelEntity channelEntity,
			EmTaskItemEntity emTaskItemEntity) {
		commonBll.saveOrUpdate(ecChannelDlqEntity);
		commonBll.saveOrUpdate(channelEntity);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLQ.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑电缆桥", false);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+channelEntity.getName(), false);
	}

	/**
	 * 更新电缆隧道
	 * 
	 * @param ecChannelDlsdEntity
	 * @param channelEntity
	 * @param emTaskItemEntity
	 */
	public  void updateDlsdDatas(EcChannelDlsdEntity ecChannelDlsdEntity, EcChannelEntity channelEntity,
			EmTaskItemEntity emTaskItemEntity) {
		commonBll.saveOrUpdate(ecChannelDlsdEntity);
		commonBll.saveOrUpdate(channelEntity);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLSD.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑电缆隧道", false);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+channelEntity.getName(), false);
	}

	/**
	 * 更新电缆直埋
	 * 
	 * @param ecChannelDlzmEntity
	 * @param channelEntity
	 * @param emTaskItemEntity
	 */
	public  void updateDlzmDatas(EcChannelDlzmEntity ecChannelDlzmEntity, EcChannelEntity channelEntity,
			EmTaskItemEntity emTaskItemEntity) {
		commonBll.saveOrUpdate(ecChannelDlzmEntity);
		commonBll.saveOrUpdate(channelEntity);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLZM.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑电缆直埋", false);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+channelEntity.getName(), false);
	}
	
	/**
	 * 更新电缆槽
	 * 
	 * @param ecChannelDlzmEntity
	 * @param channelEntity
	 * @param emTaskItemEntity
	 */
	public  void updateDlcDatas(EcChannelDlcEntity ecChannelDlcEntity, EcChannelEntity channelEntity,
			EmTaskItemEntity emTaskItemEntity) {
		commonBll.saveOrUpdate(ecChannelDlcEntity);
		commonBll.saveOrUpdate(channelEntity);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLC.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑电缆槽", false);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+channelEntity.getName(), false);
	}

	/**
	 * 更新架空
	 * 
	 * @param ecChannelJkEntity
	 * @param channelEntity
	 * @param emTaskItemEntity
	 */
	public  void updateJkDatas(EcChannelJkEntity ecChannelJkEntity, EcChannelEntity channelEntity,
			EmTaskItemEntity emTaskItemEntity) {
		commonBll.saveOrUpdate(ecChannelJkEntity);
		commonBll.saveOrUpdate(channelEntity);
		commonBll.update(emTaskItemEntity);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL_JK.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑电缆直埋", false);
		commonBll.handleDataLog(channelEntity.getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑"+channelEntity.getName(), false);
	}
	
	/**
	 * 更新通道标签
	 * @param channelLabelEntity
	 * @param channel
	 */
	public void updateChannelLabels(ChannelLabelEntity channelLabelEntity,EcChannelEntity channel) {

		ObjidAndTableName objIdAndTableName = ChannelControll.getObjIdAndTableName(channel.getChannelType());
		if (channelLabelEntity.getEcLineLabelEntityVo() == null) {
			return;
		}
		if (channelLabelEntity.getObjId().equals(objIdAndTableName.objid)) {
			channelLabelEntity.getEcLineLabelEntityVo().setOid(channelLabelEntity.getEcLineLabelEntityVo().getOid());
			// 初始化日志
			commonBll.initIsLocal(channelLabelEntity.getEcLineLabelEntityVo().getObjId(),
					objIdAndTableName.tableName, "OBJ_ID");
			// 保存
			commonBll.saveOrUpdate(channelLabelEntity.getEcLineLabelEntityVo());
			// 保存日志
			commonBll.handleDataLog(objIdAndTableName.objid, objIdAndTableName.tableName,
					DataLogOperatorType.update, WhetherEnum.NO, objIdAndTableName.tableName + "标签操作");
			// 保存通道标签附件
			List<OfflineAttach> comUploadEntityZjjtLabelList = channelLabelEntity.getEcLineLabelEntityVo()
					.getAttr();
			if (comUploadEntityZjjtLabelList != null && comUploadEntityZjjtLabelList.size() > 0) {
				for (OfflineAttach offlineAttach : comUploadEntityZjjtLabelList) {
					// 保存附件
					commonBll.saveOrUpdate(offlineAttach);
				}
			}
		}
	
	}

	/**
	 * 保存设备离线附件表
	 * 
	 * @param comUploadList
	 */
	public  void saveOrUpdateAttach(List<OfflineAttach> ComUploadEntitySubList) {
		if (ComUploadEntitySubList != null && ComUploadEntitySubList.size() > 0) {
			for (int i = 0; i < ComUploadEntitySubList.size(); i++) {
				OfflineAttach offlineAttach = ComUploadEntitySubList.get(i);
				// 保存附件
				commonBll.saveOrUpdate(offlineAttach);
			}
		}
	}
}
