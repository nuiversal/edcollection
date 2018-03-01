package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 路线名称同沟附件适配器
 * 
 * @author ly
 *
 */
public class DitchAccAdapter extends MBaseAdapter<String> {
	private Map<Integer, Boolean> selectedPosition;// 用来记录item是否选择

	@SuppressLint("UseSparseArrays")
	public DitchAccAdapter(Context context, ArrayList<String> models) {
		super(context, models);
		selectedPosition = new HashMap<Integer, Boolean>();
		initDate();
	}


	/**
	 * 获取多选的值
	 * 
	 * @return
	 */
	public List<Object> getSelectdValue() {
		List<Object> values = new ArrayList<Object>();
		for (Map.Entry<Integer, Boolean> entry : selectedPosition.entrySet()) {
			if (entry.getValue()) {
				values.add(models.get(entry.getKey()));
			}
		}
		return values;
	}

	@SuppressLint({ "CutPasteId", "UseSparseArrays" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.line_accessory_item, null);
			viewHolder.ivIco = (ImageView) convertView.findViewById(R.id.iv_line_acc_ico);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_line_acc_content);
			viewHolder.cbIscheck = (CheckBox) convertView.findViewById(R.id.cb_line_acc_ischeck);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvContent.setText(models.get(position));
		// 根据isSelected来设置checkbox的选中状况
		viewHolder.cbIscheck.setChecked(getSelectedPosition().get(position));
		if (viewHolder.cbIscheck.isChecked()) {
			viewHolder.ivIco.setImageDrawable(context.getResources().getDrawable(R.drawable.selected));
		} else {
			viewHolder.ivIco.setImageDrawable(context.getResources().getDrawable(R.drawable.select));

		}
		return convertView;
	}

	/**
	 * 获取选择
	 * 
	 * @return
	 */
	public Map<Integer, Boolean> getSelectedPosition() {
		return selectedPosition;
	}

	/**
	 * 设置选择
	 * 
	 * @param selectedPosition
	 */
	public void setSelectedPosition(Map<Integer, Boolean> selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	// 初始化selectedPosition的数据
	private void initDate() {
		for (int i = 0; i < models.size(); i++) {
			getSelectedPosition().put(i, false);
		}
	}

	public class ViewHolder {
		public ImageView ivIco;
		public TextView tvContent;
		public CheckBox cbIscheck;
	}
}
