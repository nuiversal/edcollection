package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.baidu.location.LocationClient;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.DistributionRoomActivity;
import com.winway.android.edcollection.adding.activity.DypdxActivity;
import com.winway.android.edcollection.adding.activity.KgzActivity;
import com.winway.android.edcollection.adding.activity.SubstationActivity;
import com.winway.android.edcollection.adding.activity.TowerActivity;
import com.winway.android.edcollection.adding.activity.TransformerActivity;
import com.winway.android.edcollection.adding.activity.WorkWellActivity;
import com.winway.android.edcollection.adding.activity.XsbdzActivity;
import com.winway.android.edcollection.adding.bll.BasicInfoBll;
import com.winway.android.edcollection.adding.controll.BasicInfoControll;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcDypdxEntityVo;
import com.winway.android.edcollection.project.vo.EcKgzEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.edcollection.project.vo.EcXsbdzEntityVo;
import com.winway.android.util.ToastUtil;

/**
 * 特征点
 * 
 * @author zgq
 *
 */
public class FeaturePointAdapter extends MBaseAdapter<ResourceEnum> {
	// 选中状态数组
	private static Map<Integer, Boolean> checkedStateMap = new HashMap<Integer, Boolean>();
	private LocationClient mLocationClient=null;
	private BasicInfoBll basicInfoBll = null;
	public FeaturePointAdapter(Context context, ArrayList<ResourceEnum> models,
			LocationClient mLocationClient,BasicInfoBll basicInfoBll) {
		super(context, models);
		// TODO Auto-generated constructor stub
		for (int i = 0; i < models.size(); i++) {
			checkedStateMap.put(i, false);
		}
		this.mLocationClient=mLocationClient;
		this.basicInfoBll = basicInfoBll;
	}

	/**
	 * 设置选择情况
	 * 
	 * @param position
	 * @param checkState
	 */
	public void setChecked(Integer position, boolean checkState) {
		checkedStateMap.put(position, checkState);
	}

	/**
	 * 重新赋值
	 * 
	 * @param checkedStateMap
	 */
	public static void setSelectedMap(Map<Integer, Boolean> checkedStateMap) {
		FeaturePointAdapter.checkedStateMap = checkedStateMap;
	}

	/**
	 * 获取数据列表
	 * 
	 * @return
	 */
	public ArrayList<ResourceEnum> getDataList() {
		return this.models;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.feature_point_item, null);
			viewHolder = new ViewHolder();
			viewHolder.chk = (CheckBox) convertView.findViewById(R.id.chk_feature_point);
			viewHolder.rlItem = (RelativeLayout) convertView.findViewById(R.id.rl_feature_point);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 监听器
		viewHolder.rlItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (models.get(position).equals(ResourceEnum.BDZ)) {// 变电站
					if (checkedStateMap.get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, SubstationActivity.class);
						bundle.putSerializable("EcSubstationEntity", BasicInfoControll.ecSubstationEntityVo);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, BasicInfoControll.requestCode_substation);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.XSBDZ)) {// 箱式变电站
					if (checkedStateMap.get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, XsbdzActivity.class);
						bundle.putSerializable("EcXsbdzEntity", BasicInfoControll.ecXsbdzEntityVo);
						bundle.putString("BMapLocationAddr", BasicInfoControll.BMapLocationAddrStr);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, BasicInfoControll.requestCode_xsbdz);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.KGZ)) {// 开关站
					if (checkedStateMap.get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, KgzActivity.class);
						bundle.putSerializable("EcKgzEntity", BasicInfoControll.ecKgzEntityVo);
						bundle.putString("BMapLocationAddr", BasicInfoControll.BMapLocationAddrStr);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, BasicInfoControll.requestCode_kgz);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.GT)) {// 杆塔
					if (checkedStateMap.get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, TowerActivity.class);
						bundle.putSerializable("EcTowerEntity", BasicInfoControll.ecTowerEntityVo);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, BasicInfoControll.requestCode_tower);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.BYQ)) {// 变压器
					if (checkedStateMap.get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, TransformerActivity.class);
						bundle.putSerializable("EcTransformerEntity", BasicInfoControll.ecTransformerEntityVo);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, BasicInfoControll.requestCode_transformer);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.PDS)) {// 配电室
					if (checkedStateMap.get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, DistributionRoomActivity.class);
						bundle.putSerializable("EcDistributionRoomEntity",
								BasicInfoControll.ecDistributionRoomEntityVo);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent,
								BasicInfoControll.requestCode_distributionRoom);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.DYPDX)) {// 低压配电箱
					if (checkedStateMap.get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, DypdxActivity.class);
						bundle.putSerializable("EcDypdxEntity", BasicInfoControll.ecDypdxEntityVo);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, BasicInfoControll.requestCode_dypdx);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.DLJ)) {// 工井
					if (checkedStateMap.get(position)) {
						mLocationClient.start();
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, WorkWellActivity.class);
						bundle.putSerializable("EcWorkWellEntity", BasicInfoControll.ecWorkWellEntityVo);
						bundle.putString("BMapLocationAddr", BasicInfoControll.BMapLocationAddrStr);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, BasicInfoControll.requestCode_workWell);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				}
			}
		});

		viewHolder.chk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkedStateMap.get(position)) { //取消勾选
					ResourceEnum nodeDeviceType = models.get(position);
					if (nodeDeviceType.equals(ResourceEnum.BDZ)) {
						if (basicInfoBll.findSubById() != null) { //数据库存在此数据时，取消勾选提示是否删除
							EcSubstationEntityVo substationEntityVo = BasicInfoControll.ecSubstationEntityVo;
							delDialog(substationEntityVo.getEcSubstationId(), position, substationEntityVo.getClass());
						}else {
							BasicInfoControll.ecSubstationEntityVo=null;
						}
					} else if (nodeDeviceType.equals(ResourceEnum.XSBDZ)) { //数据库存在此数据时，取消勾选提示是否删除
						if (basicInfoBll.findXsbdzById() != null) {
							EcXsbdzEntityVo xsbdzEntityVo = BasicInfoControll.ecXsbdzEntityVo;
							delDialog(xsbdzEntityVo.getObjId(), position, xsbdzEntityVo.getClass());
						}else {
							 BasicInfoControll.ecXsbdzEntityVo =null;
						}
					} else if (nodeDeviceType.equals(ResourceEnum.KGZ)) { //数据库存在此数据时，取消勾选提示是否删除
						if ( basicInfoBll.findKgzById() != null) {
							EcKgzEntityVo kgzEntityVo = BasicInfoControll.ecKgzEntityVo;
							delDialog(kgzEntityVo.getObjId(), position, kgzEntityVo.getClass());
						}else {
							BasicInfoControll.ecKgzEntityVo =null;
						}
					} else if (nodeDeviceType.equals(ResourceEnum.GT)) { //数据库存在此数据时，取消勾选提示是否删除
						EcTowerEntityVo towerEntityVo = BasicInfoControll.ecTowerEntityVo;
						if (basicInfoBll.findTowerById() != null) {
							delDialog(towerEntityVo.getObjId(), position, towerEntityVo.getClass());
						}else {
							BasicInfoControll.ecTowerEntityVo =null;
						}
					} else if (nodeDeviceType.equals(ResourceEnum.BYQ)) { //数据库存在此数据时，取消勾选提示是否删除
						if (basicInfoBll.findTransFormerById() != null) {
							EcTransformerEntityVo transformerEntityVo = BasicInfoControll.ecTransformerEntityVo;
							delDialog(transformerEntityVo.getObjId(), position, transformerEntityVo.getClass());
						}else {
							BasicInfoControll.ecTransformerEntityVo =null;
						}
					} else if (nodeDeviceType.equals(ResourceEnum.PDS)) { //数据库存在此数据时，取消勾选提示是否删除
						if (basicInfoBll.findPdsById() != null) {
							EcDistributionRoomEntityVo roomEntityVo = BasicInfoControll.ecDistributionRoomEntityVo;
							delDialog(roomEntityVo.getObjId(), position, roomEntityVo.getClass());
						}else {
							BasicInfoControll.ecDistributionRoomEntityVo =null;
						}
					} else if (nodeDeviceType.equals(ResourceEnum.DYPDX)) { //数据库存在此数据时，取消勾选提示是否删除
						if (basicInfoBll.findDypdxById() != null) {
							EcDypdxEntityVo dypdxEntityVo = BasicInfoControll.ecDypdxEntityVo;
							delDialog(dypdxEntityVo.getObjId(), position, dypdxEntityVo.getClass());
						}else {
							BasicInfoControll.ecDypdxEntityVo =null;
						}
					} else if (nodeDeviceType.equals(ResourceEnum.DLJ)) { //数据库存在此数据时，取消勾选提示是否删除
						if (basicInfoBll.findWorkWellById() != null) {
							EcWorkWellEntityVo workWellEntityVo = BasicInfoControll.ecWorkWellEntityVo;
							delDialog(workWellEntityVo.getObjId(), position, workWellEntityVo.getClass());
						}else {
							BasicInfoControll.ecWorkWellEntityVo =null;
						}
					}
					checkedStateMap.put(position, false);
					setSelectedMap(checkedStateMap);
				} else { //勾选多选框
					checkedStateMap.put(position, true);
					setSelectedMap(checkedStateMap);
					ResourceEnum nodeDeviceType = models.get(position);
					if (nodeDeviceType.equals(ResourceEnum.BDZ)) {
						basicInfoBll.saveEmptySubstation();
					} else if (nodeDeviceType.equals(ResourceEnum.XSBDZ)) {
						basicInfoBll.saveEmptyXsbdz();
					} else if (nodeDeviceType.equals(ResourceEnum.KGZ)) {
						basicInfoBll.saveEmptyKgz();
					} else if (nodeDeviceType.equals(ResourceEnum.GT)) {
						basicInfoBll.saveEmptyTower();
					} else if (nodeDeviceType.equals(ResourceEnum.BYQ)) {
						basicInfoBll.saveEmptyByq();
					} else if (nodeDeviceType.equals(ResourceEnum.PDS)) {
						basicInfoBll.saveEmptyPds();
					} else if (nodeDeviceType.equals(ResourceEnum.DYPDX)) {
						basicInfoBll.saveEmptyDypdx();
					} else if (nodeDeviceType.equals(ResourceEnum.DLJ)) {
						basicInfoBll.saveEmptyWorkWell();
					}
					
				}
			}
		});

		// 赋值
		viewHolder.chk.setText(this.models.get(position).getName());
		viewHolder.chk.setChecked(checkedStateMap.get(position));
		return convertView;
	}
	
	/**
	 * 确认删除对话框
	 * @param objId 对象主键ID
	 * @param position 位置
	 * @param entity 实体
	 */
	private void delDialog(final String objId,final int position,final Class<?> entity) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.dialog_app_style);
		dialog.setTitle("温馨提示");
		dialog.setMessage("数据库已存在此数据，确认删除？");
		dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				checkedStateMap.put(position, true);
				FeaturePointAdapter.this.notifyDataSetChanged();
				dialog.dismiss();
			}
		});
		dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				checkedStateMap.put(position, false);
				if (models.get(position).equals(ResourceEnum.BDZ)) {
					BasicInfoControll.ecSubstationEntityVo = null;
				} else if (models.get(position).equals(ResourceEnum.XSBDZ)) { 
					BasicInfoControll.ecXsbdzEntityVo = null;
				} else if (models.get(position).equals(ResourceEnum.KGZ)) {
					BasicInfoControll.ecKgzEntityVo = null;
				} else if (models.get(position).equals(ResourceEnum.GT)) {
					BasicInfoControll.ecTowerEntityVo = null;
				} else if (models.get(position).equals(ResourceEnum.BYQ)) {
					BasicInfoControll.ecTransformerEntityVo = null;
				} else if (models.get(position).equals(ResourceEnum.PDS)) {
					BasicInfoControll.ecDistributionRoomEntityVo = null;
				} else if (models.get(position).equals(ResourceEnum.DYPDX)) {
					BasicInfoControll.ecDypdxEntityVo = null;
				} else if (models.get(position).equals(ResourceEnum.DLJ)) {	
					BasicInfoControll.ecWorkWellEntityVo = null;
				}
				basicInfoBll.deleteById(entity,objId);
			}
		});
		dialog.setCancelable(false);
		dialog.show();
	}


	class ViewHolder {
		public CheckBox chk;
		public RelativeLayout rlItem;
	}

}
