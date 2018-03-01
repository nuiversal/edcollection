package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.project.entity.EcLineEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class LayingDitchAddLinesAdapter extends MBaseAdapter<EcLineEntity> {
	private Map<Integer, Boolean> selectedMap;// 用来记录item是否选择

	public LayingDitchAddLinesAdapter(Context context, ArrayList<EcLineEntity> models) {
		super(context, models);
		initSelected();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.laying_ditch_add_lines_item, null);
			holder.ivLineico = (ImageView) convertView.findViewById(R.id.iv_laying_ditch_add_lines_item_ico);
			holder.tvLineName = (TextView) convertView.findViewById(R.id.tv_laying_ditch_add_lines_item_linename);
			holder.ivLineSelected = (ImageView) convertView.findViewById(R.id.iv_laying_ditch_add_lines_item_selected);
			holder.cbIsCheck = (CheckBox) convertView.findViewById(R.id.cb_iv_laying_ditch_add_lines_item_ischeck);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		EcLineEntity ecLineEntity = models.get(position);
		holder.cbIsCheck.setChecked(getSelectedPosition().get(position));
		if (holder.cbIsCheck.isChecked()) {
			holder.ivLineSelected.setVisibility(View.VISIBLE);
		} else {
			holder.ivLineSelected.setVisibility(View.GONE);
		}
		holder.tvLineName.setText(ecLineEntity.getName());
		holder.ivLineico.setVisibility(View.GONE);
		return convertView;
	}

	// 初始化selectedPosition的数据
	@SuppressLint("UseSparseArrays")
	public void initSelected() {
		selectedMap = new HashMap<Integer, Boolean>();
		for (int i = 0; i < models.size(); i++) {
			selectedMap.put(i, false);
		}
	}

	/**
	 * 获取选择
	 * 
	 * @return
	 */
	public Map<Integer, Boolean> getSelectedPosition() {
		return selectedMap;
	}

	/**
	 * 更新数据
	 * 
	 * @param models
	 */
	public void updateData(ArrayList<EcLineEntity> models) {
		if (models == null || models.size() < 1) {
			return;
		}
		this.models.clear();
		this.models.addAll(models);
		initSelected();
		this.notifyDataSetChanged();
	}

	/**
	 * 获取CheckBox状态
	 * 
	 * @param i
	 * @return
	 */
	public boolean getSelectedMapItem(Integer i) {
		return selectedMap.get(i);
	}

	/**
	 * 设置选择
	 * 
	 * @param selectedPosition
	 */
	public void setSelectedPosition(Map<Integer, Boolean> selectedMap) {
		this.selectedMap = selectedMap;
	}

	/**
	 * 获取多选的值
	 * 
	 * @return
	 */
	public List<EcLineEntity> getSelectdValue() {
		List<EcLineEntity> values = new ArrayList<EcLineEntity>();
		for (int i = 0; i < selectedMap.size(); i++) {
			Boolean isSelected = selectedMap.get(i);
			if (isSelected) {
				values.add(models.get(i));
			}
		}
		return values;
	}

	public class ViewHolder {
		public ImageView ivLineico;
		public TextView tvLineName;
		public ImageView ivLineSelected;
		public CheckBox cbIsCheck;
	}
}
