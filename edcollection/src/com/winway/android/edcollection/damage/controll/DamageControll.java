package com.winway.android.edcollection.damage.controll;

import java.util.Date;

import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.util.CoordsUtils;
import com.winway.android.edcollection.base.view.BaseFragmentControll;
import com.winway.android.edcollection.damage.bll.DamageBll;
import com.winway.android.edcollection.damage.entity.DamageFlag;
import com.winway.android.edcollection.damage.viewholder.DamageViewHolder;
import com.winway.android.edcollection.project.entity.EmDangerInfoEntity;
import com.winway.android.sensor.geolocation.GPS;
import com.winway.android.util.BroadcastReceiverUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 隐患
 * 
 * @author zgq
 *
 */
public class DamageControll extends BaseFragmentControll<DamageViewHolder> {
	private DamageBll damageBll;

	@Override
	public void init() {
		String dbUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		damageBll = new DamageBll(mActivity, dbUrl);
		initDatas();
		initEvents();
		initSettings();
	}

	private void initSettings() {
		// TODO Auto-generated method stub
		
	}

	private void initEvents() {
		// viewHolder.getBtnSave().setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// save();
		// }
		// });

	}

	private void initDatas() {

	}

	// 保存
	private void save() {
//		String linkType = viewHolder.getIcLinkType().getEdtTextValue();
//		String damageState = viewHolder.getIcDamageState().getEdtTextValue();
//		String desc = viewHolder.getEditTxtDesc().getText().toString();
//		if (desc.isEmpty()) {
//			ToastUtil.show(mActivity, "隐患描述信息不能为空", 200);
//			viewHolder.getEditTxtDesc().setFocusable(true);
//			return;
//		}
//		EmDangerInfoEntity emDangerInfoEntity = new EmDangerInfoEntity();
//		String coordinates = null;
//		try {
//			double[] xy = new double[] { GPS.nowLoaction.getLongitude(), GPS.nowLoaction.getLatitude() };
//			coordinates = CoordsUtils.getInstance().coord2Geom(xy);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			coordinates = null;
//		}
//		emDangerInfoEntity.setCoordinates(coordinates);
//		emDangerInfoEntity.setDescription(desc);
//		emDangerInfoEntity.setEmDangerInfoId(UUIDGen.randomUUID());
//		emDangerInfoEntity.setLnkObjId(null);
//		emDangerInfoEntity.setLnkType(linkType);
//		emDangerInfoEntity.setRecordor(GlobalEntry.loginResult.getUsername());
//		emDangerInfoEntity.setRecordTime(new Date());
//		emDangerInfoEntity.setStatus(0);
//		boolean bRes = damageBll.save(emDangerInfoEntity);
//		if (bRes) {
//			// 保存日志表
//			CommonBll commonBll = new CommonBll(mActivity, GlobalEntry.prjDbUrl);
//			commonBll.handleDataLog(emDangerInfoEntity.getEmDangerInfoId(), TableNameEnum.EM_DANGER_INFO.getTableName(),
//					DataLogOperatorType.Add, WhetherEnum.NO, "新增隐患", true);
//			ToastUtil.show(mActivity, "保存成功", 200);
//			BroadcastReceiverUtils.getInstance().sendCommand(mActivity, DamageFlag.flag_add_damage_success);
//			clearInput();
//		} else {
//			ToastUtil.show(mActivity, "保存失败", 200);
//		}

	}

	private void clearInput() {
		viewHolder.getEditTxtDesc().setText("");
	}

}
