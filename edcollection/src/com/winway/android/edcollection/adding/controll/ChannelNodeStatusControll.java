package com.winway.android.edcollection.adding.controll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.winway.android.edcollection.adding.bll.ChannelDangerBll;
import com.winway.android.edcollection.adding.entity.DangerStatusType;
import com.winway.android.edcollection.adding.util.FragmentEntry;
import com.winway.android.edcollection.adding.viewholder.ChannelNodeStatusViewholder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.base.view.BaseFragmentControll;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EmDangerInfoEntity;
import com.winway.android.util.UUIDGen;

import android.content.Intent;

/***
 * 通道节点现状
 * 
 * @author winway_zgq
 *
 */
public class ChannelNodeStatusControll extends BaseFragmentControll<ChannelNodeStatusViewholder> {

	private EmDangerInfoEntity emDangerInfoEntity = null;
	private ChannelDangerBll channelDangerBll = null;
	private CommonBll commonBll = null;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		channelDangerBll = new ChannelDangerBll(mActivity, GlobalEntry.prjDbUrl);
		commonBll = new CommonBll(mActivity, GlobalEntry.prjDbUrl);
		initDatas();
		initSettings();
		initEvents();

	}

	private void initEvents() {
		// TODO Auto-generated method stub

	}

	private void initSettings() {
		// TODO Auto-generated method stub

	}

	private void initDatas() {
		Intent intent = getIntent();
		EcNodeEntity ecNodeEntity = (EcNodeEntity) intent.getSerializableExtra("EcNodeEntity");
		if (ecNodeEntity != null) {
			String oid = ecNodeEntity.getOid();
			emDangerInfoEntity = channelDangerBll.getDangerByOid(oid);
		}
		List<String> typeDatas = channelDangerBll.getDangerStatusTypes();
		InputSelectAdapter adapter = new InputSelectAdapter(mActivity, typeDatas);
		viewHolder.getIcDamageStatus().setAdapter(adapter);
		viewHolder.getIcDamageStatus().setEdtTextValue(DangerStatusType.ZC.getValue());

		if (emDangerInfoEntity != null) {// 已经存在
			viewHolder.getEditTxtDesc().setText(emDangerInfoEntity.getDescription());
			String dangerType = emDangerInfoEntity.getDangerType();
			viewHolder.getIcDamageStatus().setEdtTextValue(dangerType);
		}
		if (FragmentEntry.getInstance().emDangerInfoEntity.getDescription() != null) { // 有缓存数据-描述
			viewHolder.getEditTxtDesc().setText(FragmentEntry.getInstance().emDangerInfoEntity.getDescription());
		}
		if (FragmentEntry.getInstance().emDangerInfoEntity.getDangerType() != null) {// 有缓存数据
																						// -
																						// 状况
			viewHolder.getIcDamageStatus()
					.setEdtTextValue(FragmentEntry.getInstance().emDangerInfoEntity.getDangerType());
		}
	}

	/**
	 * 保存通道隐患信息
	 * 
	 * @param ecNodeEntity
	 */
	public void saveChannelDanger(EcNodeEntity ecNodeEntity) {
		// TODO Auto-generated method stub
		if (emDangerInfoEntity == null) {
			emDangerInfoEntity = new EmDangerInfoEntity();
			emDangerInfoEntity.setEmDangerInfoId(UUIDGen.randomUUID());
		}
		emDangerInfoEntity.setCoordinates(ecNodeEntity.getGeom());
		String description = viewHolder.getEditTxtDesc().getText().toString();
		emDangerInfoEntity.setDescription(description);
		emDangerInfoEntity.setLnkObjId(ecNodeEntity.getOid());
		emDangerInfoEntity.setLnkType(ResourceEnum.DLJ.getValue());
		emDangerInfoEntity.setRecordor(GlobalEntry.loginResult.getUid());
		emDangerInfoEntity.setRecordTime(new Date());
		emDangerInfoEntity.setStatus(0);
		String dangerType = viewHolder.getIcDamageStatus().getEdtTextValue();
		emDangerInfoEntity.setDangerType(dangerType);

		// 保存隐患
		channelDangerBll.saveOrUpdate(emDangerInfoEntity);
		// 记录日志
		if (GlobalEntry.editNode) {
			commonBll.handleDataLog(emDangerInfoEntity.getEmDangerInfoId(), TableNameEnum.EM_DANGER_INFO.getTableName(),
					DataLogOperatorType.update, WhetherEnum.NO, "隐患：" + emDangerInfoEntity.getDangerType(), false);
		} else {
			commonBll.handleDataLog(emDangerInfoEntity.getEmDangerInfoId(), TableNameEnum.EM_DANGER_INFO.getTableName(),
					DataLogOperatorType.Add, WhetherEnum.NO, "隐患：" + emDangerInfoEntity.getDangerType(), true);
		}
		

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (FragmentEntry.isSelectMap) { // 地图选点，保存缓存
			FragmentEntry.getInstance().emDangerInfoEntity
					.setDangerType(viewHolder.getIcDamageStatus().getEdtTextValue());
			FragmentEntry.getInstance().emDangerInfoEntity
					.setDescription(viewHolder.getEditTxtDesc().getText().toString());
		}
	}
}
