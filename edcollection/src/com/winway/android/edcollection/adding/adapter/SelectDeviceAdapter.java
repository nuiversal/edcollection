package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;

/**
 * 设备选择适配器
 * 
 * @author zgq
 *
 */
public class SelectDeviceAdapter extends MBaseAdapter<Object> {

	private Map<Integer, Boolean> chkMaps = new HashMap<Integer, Boolean>();// 保存勾选状态

	public SelectDeviceAdapter(Context context, ArrayList<Object> models) {
		super(context, models);
		// TODO Auto-generated constructor stub
		resetChkMaps();
	}

	/**
	 * 重置所有勾选状态
	 */
	public void resetChkMaps() {
		for (int i = 0; i < models.size(); i++) {
			chkMaps.put(i, false);
		}
	}

	/**
	 * 获取勾选项
	 * 
	 * @return
	 */
	public int getCheckItem() {
		for (int i = 0; i < chkMaps.size(); i++) {
			if (chkMaps.get(i)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.select_device_item, null);
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_select_device_item_name);
			viewHolder.rBtnSelect = (RadioButton) convertView.findViewById(R.id.rBtn_select_device_item_select);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 赋值
		Object obj = models.get(position);
		Class cls = obj.getClass();
		if (cls.equals(EcSubstationEntity.class)) {// 变电站
			EcSubstationEntity bdz = (EcSubstationEntity) obj;
			viewHolder.tvName.setText(bdz.getName());
		} else if (cls.equals(EcXsbdzEntity.class)) {// 箱式变电站
			EcXsbdzEntity xsbdz = (EcXsbdzEntity) obj;
			viewHolder.tvName.setText(xsbdz.getSbmc());
		} else if (cls.equals(EcKgzEntity.class)) {// 开关站
			EcKgzEntity kgz = (EcKgzEntity) obj;
			viewHolder.tvName.setText(kgz.getSbmc());
		} else if (cls.equals(EcDypdxEntity.class)) {// 低压配电箱
			EcDypdxEntity dypdx = (EcDypdxEntity) obj;
			viewHolder.tvName.setText(dypdx.getSbmc());
		} else if (cls.equals(EcTowerEntity.class)) {// 杆塔
			EcTowerEntity gt = (EcTowerEntity) obj;
			viewHolder.tvName.setText(gt.getTowerNo()+"");
		} else if (cls.equals(EcDistributionRoomEntity.class)) {// 配电室
			EcDistributionRoomEntity pds = (EcDistributionRoomEntity) obj;
			viewHolder.tvName.setText(pds.getSbmc());
		} else if (cls.equals(EcTransformerEntity.class)) {// 变压器
			EcTransformerEntity byq = (EcTransformerEntity) obj;
			viewHolder.tvName.setText(byq.getSbmc());
		} else if (cls.equals(EcWorkWellEntity.class)) {// 电缆井
			EcWorkWellEntity gj = (EcWorkWellEntity) obj;
			viewHolder.tvName.setText(gj.getSbmc());
		} else if (cls.equals(EcHwgEntity.class)) {// 环网柜
			EcHwgEntity hwg = (EcHwgEntity) obj;
			viewHolder.tvName.setText(hwg.getSbmc());
		} else if (cls.equals(EcLineLabelEntity.class)) {// 电子标签
			EcLineLabelEntity dzbq = (EcLineLabelEntity) obj;
			viewHolder.tvName.setText(dzbq.getDevName());
		} else if (cls.equals(EcDlfzxEntity.class)) {// 电缆分支箱
			EcDlfzxEntity dlfzx = (EcDlfzxEntity) obj;
			viewHolder.tvName.setText(dlfzx.getSbmc());
		} else if (cls.equals(EcDydlfzxEntity.class)) {// 低压电缆分支箱
			EcDydlfzxEntity dydlfzx = (EcDydlfzxEntity) obj;
			viewHolder.tvName.setText(dydlfzx.getSbmc());
		} else if (cls.equals(EcMiddleJointEntity.class)) {// 中间接头
			EcMiddleJointEntity zjjt = (EcMiddleJointEntity) obj;
			viewHolder.tvName.setText(zjjt.getSbmc());
		}
		viewHolder.rBtnSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				resetChkMaps();
				if (chkMaps.get(position)) {
					chkMaps.put(position, false);
				} else {
					chkMaps.put(position, true);
				}
				notifyDataSetChanged();
			}
		});

		viewHolder.rBtnSelect.setChecked(chkMaps.get(position));

		return convertView;
	}

	class ViewHolder {
		public TextView tvName;
		public RadioButton rBtnSelect;
	}

}
