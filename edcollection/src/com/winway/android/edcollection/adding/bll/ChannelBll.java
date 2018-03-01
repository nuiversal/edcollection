package com.winway.android.edcollection.adding.bll;

import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.adding.activity.ChannelDGActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLCActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLGActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLGDActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLQActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLSDActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLZMActivity;
import com.winway.android.edcollection.adding.activity.ChannelJkActivity;
import com.winway.android.edcollection.adding.controll.ChannelControll;
import com.winway.android.edcollection.adding.controll.ChannelControll.ObjidAndTableName;
import com.winway.android.edcollection.adding.dto.ChannelDevices;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.ToastUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * 通道信息业务处理
 * 
 * @author zgq
 *
 */
@SuppressLint("DefaultLocale")
public class ChannelBll extends BaseBll<Object> {
	// 日志业务
	private CommonBll logBll;

	public ChannelBll(Context context, String dbUrl) {
		super(context, dbUrl);
		if (null == logBll) {
			logBll = new CommonBll(context, dbUrl);
		}
		if (null == baseDBUtil) {
			baseDBUtil = new BaseDBUtil(this.getDbUtils());
		}
	}

	/**
	 * 新增通道
	 * 
	 * @param activity
	 * @param channelType
	 */
	public void createChannel(Activity activity, String channelType) {
		Intent intent = new Intent();
		if (channelType.equals(ResourceEnum.TLG.getName())) {// 拖拉管、顶管
			intent.setClass(activity, ChannelDGActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(activity, intent, ChannelControll.requestCode_dg);
		} else if (channelType.equals(ResourceEnum.DG.getName())) {// 电缆沟
			intent.setClass(activity, ChannelDGActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(activity, intent, ChannelControll.requestCode_gd);
		}else if (channelType.equals(ResourceEnum.GD.getName())) {// 电缆沟
			intent.setClass(activity, ChannelDLGActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(activity, intent, ChannelControll.requestCode_gd);
		} else if (channelType.equals(ResourceEnum.PG.getName())) {// 电缆排管
			intent.setClass(activity, ChannelDLGDActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(activity, intent, ChannelControll.requestCode_pg);
		}else if (channelType.equals(ResourceEnum.MG.getName())) {// 电缆埋管
			intent.setClass(activity, ChannelDLGDActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(activity, intent, ChannelControll.requestCode_pg);
		} else if (channelType.equals(ResourceEnum.QJ.getName())) {// 电缆桥
			intent.setClass(activity, ChannelDLQActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(activity, intent, ChannelControll.requestCode_qj);
		} else if (channelType.equals(ResourceEnum.SD.getName())) {// 电缆隧道
			intent.setClass(activity, ChannelDLSDActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(activity, intent, ChannelControll.requestCode_sd);
		} else if (channelType.equals(ResourceEnum.ZM.getName())) {// 电缆直埋
			intent.setClass(activity, ChannelDLZMActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(activity, intent, ChannelControll.requestCode_zm);
		} else if (channelType.equals(ResourceEnum.JKXL.getName())) {// 架空
			intent.setClass(activity, ChannelJkActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(activity, intent, ChannelControll.requestCode_jk);
		}else if (channelType.equals(ResourceEnum.DLC.getName())) {// 电缆槽
			intent.setClass(activity, ChannelDLCActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(activity, intent, ChannelControll.requestCode_dlc);
		}
	}

	/**
	 * 获取通道设施列表
	 * 
	 * @param channelType
	 */
	public ChannelDevices getChannelListByChannelType(String channelType) {
		ChannelDevices channelDevices = new ChannelDevices();
		String expr = " 1=1 ORDER BY CJSJ DESC ";
		if (channelType.equals(ResourceEnum.TLG.getName())) {// 拖拉管、顶管
			List<EcChannelDgEntity> ecChannelDgEntities = queryByExpr2(EcChannelDgEntity.class, expr);
			channelDevices.setEcChannelDgEntities(ecChannelDgEntities);
		} else if (channelType.equals(ResourceEnum.GD.getName())) {// 电缆沟
			List<EcChannelDlgEntity> ecChannelDlgEntities = queryByExpr2(EcChannelDlgEntity.class, expr);
			channelDevices.setEcChannelDlgEntities(ecChannelDlgEntities);
		} else if (channelType.equals(ResourceEnum.PG.getName())) {// 电缆管道
			List<EcChannelDlgdEntity> ecChannelDlgdEntities = queryByExpr2(EcChannelDlgdEntity.class, expr);
			channelDevices.setEcChannelDlgdEntities(ecChannelDlgdEntities);
		} else if (channelType.equals(ResourceEnum.QJ.getName())) {// 电缆桥
			List<EcChannelDlqEntity> ecChannelDlqEntities = queryByExpr2(EcChannelDlqEntity.class, expr);
			channelDevices.setEcChannelDlqEntities(ecChannelDlqEntities);
		} else if (channelType.equals(ResourceEnum.SD.getName())) {// 电缆隧道
			List<EcChannelDlsdEntity> ecChannelDlsdEntities = queryByExpr2(EcChannelDlsdEntity.class, expr);
			channelDevices.setEcChannelDlsdEntities(ecChannelDlsdEntities);
		} else if (channelType.equals(ResourceEnum.ZM.getName())) {// 电缆直埋
			List<EcChannelDlzmEntity> ecChannelDlzmEntities = queryByExpr2(EcChannelDlzmEntity.class, expr);
			channelDevices.setEcChannelDlzmEntities(ecChannelDlzmEntities);
		} else if (channelType.equals(ResourceEnum.JKXL.getName())) {// 架空
			List<EcChannelJkEntity> ecChannelJkEntities = queryByExpr2(EcChannelJkEntity.class, expr);
			channelDevices.setEcChannelJkEntities(ecChannelJkEntities);
		}else if (channelType.equals(ResourceEnum.DLC.getName())) {
			List<EcChannelDlcEntity> ecChannelDlcEntities = queryByExpr2(EcChannelDlcEntity.class, expr);
			channelDevices.setEcChannelDlcEntities(ecChannelDlcEntities);
		}
		return channelDevices;
	}

	public void saveChannel(EcChannelEntity ecChannelEntity) {
		this.save(ecChannelEntity);
	}

	public void saveChannelNode(EcChannelNodesEntity ecChannelNodesEntity) {
		this.save(ecChannelNodesEntity);
	}

	public void saveChannel() {

	}

	private BaseDBUtil baseDBUtil;

	/**
	 * 删除通道
	 */
	public void deleteChannel(EcChannelEntity channel, Object dataEntity) {
		try {
			// 删除通道
			getDbUtils().delete(channel);
			// 删除通道数据
			getDbUtils().delete(dataEntity);
			// 删除与通道相关的线路

			// 删除通道相关节点
			// 日志处理
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改通道类型
	 * @param channel
	 * @param newChannelData
	 * @param oldChannelData
	 */
	public void updateChannelType(EcChannelEntity channel, Object newChannelData, Object oldChannelData) {
		try {
			// 更新通道实体
			baseDBUtil.update(channel);
			logBll.handleDataLog(channel.getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(), DataLogOperatorType.Add,
					WhetherEnum.NO, "更新通道数据" + channel.getEcChannelId(), false);
			// 删掉旧的通道数据实体
			baseDBUtil.delete(oldChannelData);
			EmCDataLogEntity queryEntity = new EmCDataLogEntity();
			ObjidAndTableName oldObjIdAndTableName = ChannelControll.getObjIdAndTableName(oldChannelData);
			queryEntity.setTableName(oldObjIdAndTableName.tableName.toUpperCase());
			queryEntity.setDataKey(oldObjIdAndTableName.objid);
			List<EmCDataLogEntity> queryList = baseDBUtil.excuteQuery(EmCDataLogEntity.class, queryEntity);
			if (null != queryList && !queryList.isEmpty()) {
				if (queryList.get(0).getIsUpload() == WhetherEnum.YES.getValue()
						|| queryList.get(0).getOptType() == DataLogOperatorType.update.getValue()) {
					// 修改日志
					queryList.get(0).setOptType(DataLogOperatorType.delete.getValue());
					queryList.get(0).setIsUpload(WhetherEnum.NO.getValue());
					baseDBUtil.update(queryList.get(0));
				} else if (queryList.get(0).getOptType() == DataLogOperatorType.Add.getValue()) {
					// 删除
					baseDBUtil.delete(queryList.get(0));
				}
			}
			// 保存新的通道数据实体
			baseDBUtil.save(newChannelData);
			ObjidAndTableName objIdAndTableName = ChannelControll.getObjIdAndTableName(newChannelData);
			logBll.handleDataLog(objIdAndTableName.objid, objIdAndTableName.tableName, DataLogOperatorType.Add,
					WhetherEnum.NO, "插入具体的通道数据" + objIdAndTableName.objid, true);
		} catch (DbException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 检查通道序号是否重复
	 * @param channelID 通道ID
	 * @param noIndex 序号
	 * @return
	 */
	public boolean checkChannelNoIndexRepeat(String channelID,String noIndex) {
		String expr = "EC_CHANNEL_ID = '"+channelID+"' and N_INDEX = '"+noIndex+"'";
		List<EcChannelNodesEntity> ecChannelNodesEntities = queryByExpr2(EcChannelNodesEntity.class,expr);
		if (null != ecChannelNodesEntities && !ecChannelNodesEntities.isEmpty()) {
			return true;
		}
		return false;
	}
}
