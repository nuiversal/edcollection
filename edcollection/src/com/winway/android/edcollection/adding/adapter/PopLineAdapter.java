package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.Tag;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.util.LogUtil;

/**
 * 新增PopupWindow线路适配器
 * 
 * @author ly
 *
 */
public class PopLineAdapter extends MBaseAdapter<EcLineEntity> {
	private Map<Integer, Boolean> selectedMap;// 用来记录item是否选择

	@SuppressLint("UseSparseArrays")
	public PopLineAdapter(Context context, ArrayList<EcLineEntity> models) {
		super(context, models);
		initSelected();
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

	@SuppressLint({ "CutPasteId", "UseSparseArrays" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.new_pop_item, null);
			viewHolder.ico = (ImageView) convertView.findViewById(R.id.iv_ico);
			viewHolder.content = (TextView) convertView.findViewById(R.id.tv_showcontent);
			viewHolder.select = (ImageView) convertView.findViewById(R.id.iv_select);
			viewHolder.cbIscheck = (CheckBox) convertView.findViewById(R.id.cb_ischeck);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.cbIscheck.setChecked(getSelectedPosition().get(position));
		if (viewHolder.cbIscheck.isChecked()) {
			viewHolder.select.setVisibility(View.VISIBLE);
		} else {
			viewHolder.select.setVisibility(View.GONE);
		}
		EcLineEntity ecLineEntity = models.get(position);
		viewHolder.content.setText(ecLineEntity.getName());
		viewHolder.ico.setVisibility(View.GONE);
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popLineItemClickListener.onClickItem(v, position);
			}
		});
		convertView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				popLineItemClickListener.onLongClickItem(v, position);
				return false;
			}
		});
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
	 * 设置选择
	 * 
	 * @param selectedPosition
	 */
	public void setSelectedPosition(Map<Integer, Boolean> selectedMap) {
		this.selectedMap = selectedMap;
	}
	
	public class ViewHolder {
		public ImageView ico;
		public TextView content;
		public ImageView select;
		public CheckBox cbIscheck;
	}

	private OnPopLineItemClickListener popLineItemClickListener;

	public void setPopLineItemClickListener(OnPopLineItemClickListener popLineItemClickListener) {
		this.popLineItemClickListener = popLineItemClickListener;
	}

	public interface OnPopLineItemClickListener {
		void onClickItem(View v, int position);
		void onLongClickItem(View v,int positoion);
	}
	
}
