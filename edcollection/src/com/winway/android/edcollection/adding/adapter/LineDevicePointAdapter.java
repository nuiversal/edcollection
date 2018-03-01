package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.DlfzxActivity;
import com.winway.android.edcollection.adding.activity.DydlfzxActivity;
import com.winway.android.edcollection.adding.activity.HwgActivity;
import com.winway.android.edcollection.adding.activity.IntermediateHeadActivity;
import com.winway.android.edcollection.adding.activity.LableActivity;
import com.winway.android.edcollection.adding.bll.SameGrooveInformationBll;
import com.winway.android.edcollection.adding.controll.BasicInfoControll;
import com.winway.android.edcollection.adding.controll.LineNameControll;
import com.winway.android.edcollection.adding.controll.MarkerCollectControll;
import com.winway.android.edcollection.adding.entity.DitchCable;
import com.winway.android.edcollection.adding.fragment.BasicInfoFragment;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.util.ToastUtil;

/**
 * 线路附属设备列表适配器
 * 
 * @author zgq
 *
 */
public class LineDevicePointAdapter extends MBaseAdapter<ResourceEnum> {
	// 选中状态数组
	private List<Map<Integer, Boolean>> checkedStateList = new ArrayList<Map<Integer, Boolean>>();
	private SameGrooveInformationBll bll;
	private DitchCable ditchCable;
	private MarkerCollectControll markerCollectControll;
	
	public LineDevicePointAdapter(Context context, ArrayList<ResourceEnum> models,SameGrooveInformationBll sameGrooveInformationBll,DitchCable ditchCable) {
		super(context, models);
		// TODO Auto-generated constructor stub
		this.bll = sameGrooveInformationBll;
		this.ditchCable = ditchCable;
		markerCollectControll =MarkerCollectControll.getInstance();
		for (int i = 0; i < models.size(); i++) {
			Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
			map.put(i, false);
			checkedStateList.add(map);
		}
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

		// 赋值
		viewHolder.chk.setText(this.models.get(position).getName());
		viewHolder.chk.setChecked(checkedStateList.get(position).get(position));
		// 监听器
		viewHolder.rlItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (models.get(position).equals(ResourceEnum.HWG)) {// 环网柜
					if (checkedStateList.get(position).get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, HwgActivity.class);
						bundle.putSerializable("HwgEntity", LineNameControll.ditchCable.getHwgEntityVo());
						bundle.putString("BMapLocationAddr", BasicInfoControll.BMapLocationAddrStr);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, LineNameControll.requestCode_hwg);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.DZBQ)) {// 电子标签
					if (checkedStateList.get(position).get(position)) {
						BasicInfoFragment basicInfoFragment = markerCollectControll.getBasicInfoFragment();
						LineNameControll.ditchCable.getLineLabelEntityVo().setDevName("BQ"+basicInfoFragment.getAction().getViewHolder().getIcMarkerId().getEdtTextValue());
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, LableActivity.class);
						intent.putExtra("devName", LineNameControll.ditchCable.getLineName());
						intent.putExtra("devType", ResourceEnum.XL.getName());
						bundle.putSerializable("LableEntity", LineNameControll.ditchCable.getLineLabelEntityVo());
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, LineNameControll.requestCode_dzbq);
					
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.DLFZX)) {// 电缆分支箱
					if (checkedStateList.get(position).get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, DlfzxActivity.class);
						bundle.putSerializable("DlfzxEntity",
								LineNameControll.ditchCable.getDlfzxEntityVo());
						bundle.putString("BMapLocationAddr", BasicInfoControll.BMapLocationAddrStr);
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, LineNameControll.requestcode_dlfzx);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.DYDLFZX)) {// 低压电缆分支箱
					if (checkedStateList.get(position).get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, DydlfzxActivity.class);
						bundle.putSerializable("DydlfzxEntity",
								LineNameControll.ditchCable.getDydlfzxEntityVo());
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, LineNameControll.requestcode_dydlfzx);
					} else {
						ToastUtil.show(context, "请先勾选", 200);
					}
				} else if (models.get(position).equals(ResourceEnum.ZJJT)) {// 中间接头
					if (checkedStateList.get(position).get(position)) {
						Bundle bundle = new Bundle();
						Intent intent = new Intent(context, IntermediateHeadActivity.class);
						bundle.putString("zjjtName", LineNameControll.zjjtName);
						bundle.putSerializable("IntermediateHeadEntity",
								LineNameControll.ditchCable.getMiddleJointEntityVo());
						intent.putExtras(bundle);
						((Activity) context).startActivityForResult(intent, LineNameControll.requestcode_zjjt);
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
				
				Map<Integer, Boolean> map = checkedStateList.get(position);
				if (map.get(position)) {
					if (models.get(position).equals(ResourceEnum.HWG)) {// 环网柜
						//当数据库存在此数据时，取消勾选时提示用户是事确认删除。
						if (bll.findHwgById() != null) {
							EcHwgEntityVo hwgEntityVo = LineNameControll.ditchCable.getHwgEntityVo();
							delDialog(hwgEntityVo.getObjId(),position,hwgEntityVo.getClass());
						}else {
							LineNameControll.ditchCable.setHwgEntityVo(null);
						}
					}else if(models.get(position).equals(ResourceEnum.DZBQ)) {// 电子标签
						//当数据库存在此数据时，取消勾选时提示用户是事确认删除。
						 if (bll.findLabelById() != null) {
							 EcLineLabelEntityVo lineLabelEntityVo = LineNameControll.ditchCable.getLineLabelEntityVo();
							 delDialog(lineLabelEntityVo.getObjId(), position, lineLabelEntityVo.getClass());
						 }else {
							 LineNameControll.ditchCable.setLineLabelEntityVo(null);
						}
						
					} else if (models.get(position).equals(ResourceEnum.DLFZX)) {// 电缆分支箱
						//当数据库存在此数据时，取消勾选时提示用户是事确认删除。
						if (bll.findDlfzxById() != null) {
							EcDlfzxEntityVo dlfzxEntityVo = LineNameControll.ditchCable.getDlfzxEntityVo();
							delDialog(dlfzxEntityVo.getObjId(), position, dlfzxEntityVo.getClass());
						}else {
							LineNameControll.ditchCable.setDlfzxEntityVo(null);
						}
					} else if (models.get(position).equals(ResourceEnum.DYDLFZX)) {// 低压电缆分支箱
						//当数据库存在此数据时，取消勾选时提示用户是事确认删除。
						if (bll.findDydlfzxById() != null) {
							EcDydlfzxEntityVo dydlfzxEntityVo = LineNameControll.ditchCable.getDydlfzxEntityVo();
							delDialog(dydlfzxEntityVo.getObjId(), position, dydlfzxEntityVo.getClass());
						}else {
							LineNameControll.ditchCable.setDydlfzxEntityVo(null);
						}
					}else if (models.get(position).equals(ResourceEnum.ZJJT)) {// 中间接头
						//当数据库存在此数据时，取消勾选时提示用户是事确认删除。
						if (bll.findZjjtById() != null) {
							EcMiddleJointEntityVo zjjtEntityVo = LineNameControll.ditchCable.getMiddleJointEntityVo();
							delDialog(zjjtEntityVo.getObjId(), position, zjjtEntityVo.getClass());
						}else {
							LineNameControll.ditchCable.setMiddleJointEntityVo(null);
						}
					}
					map.put(position, false);
				} else {
					if (models.get(position).equals(ResourceEnum.HWG)) {// 环网柜
						if (!checkedStateList.get(position).get(position)) {
							bll.setHwgVoData();
						}
					}else if(models.get(position).equals(ResourceEnum.DZBQ)) {// 电子标签
						if (!checkedStateList.get(position).get(position)) {
							bll.setDzbqVoData(context);
						}
					} else if (models.get(position).equals(ResourceEnum.DLFZX)) {// 电缆分支箱
						if (!checkedStateList.get(position).get(position)) {
							bll.setDlfzxVoData();
						}
					} else if (models.get(position).equals(ResourceEnum.DYDLFZX)) {// 低压电缆分支箱
						if (!checkedStateList.get(position).get(position)) {
							bll.setDydlfzxVoData();
						}
					}else if (models.get(position).equals(ResourceEnum.ZJJT)) {// 中间接头
						if (!checkedStateList.get(position).get(position)) {
							bll.setZjjtVoData(ditchCable);
						}
					}
					map.put(position, true);
				}

			}
		});
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
				checkedStateList.get(position).put(position, true);
				LineDevicePointAdapter.this.notifyDataSetChanged();
				dialog.dismiss();
			}
		});
		dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				checkedStateList.get(position).put(position, false);
				if (models.get(position).equals(ResourceEnum.HWG)) {// 环网柜
					LineNameControll.ditchCable.setHwgEntityVo(null);
				} else if(models.get(position).equals(ResourceEnum.DZBQ)) {// 电子标签
					LineNameControll.ditchCable.setLineLabelEntityVo(null);
				} else if (models.get(position).equals(ResourceEnum.DLFZX)) {// 电缆分支箱
					LineNameControll.ditchCable.setDlfzxEntityVo(null);
				} else if (models.get(position).equals(ResourceEnum.DYDLFZX)) {// 低压电缆分支箱
					LineNameControll.ditchCable.setDydlfzxEntityVo(null);
				} else if (models.get(position).equals(ResourceEnum.ZJJT)) {// 中间接头
					LineNameControll.ditchCable.setMiddleJointEntityVo(null);
				}
				bll.deleteById(entity,objId);
			}
		});
		dialog.setCancelable(false);
		dialog.show();
	}

	class ViewHolder {
		public CheckBox chk;
		public RelativeLayout rlItem;
	}

	public List<Map<Integer, Boolean>> getCheckedStateList() {
		return checkedStateList;
	}

	public void setCheckedStateList(List<Map<Integer, Boolean>> checkedStateList) {
		this.checkedStateList = checkedStateList;
	}

}
