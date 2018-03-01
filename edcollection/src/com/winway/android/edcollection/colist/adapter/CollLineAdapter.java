package com.winway.android.edcollection.colist.adapter;

import java.util.ArrayList;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.project.entity.EcLineEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


/**
 * 已采集PopupWindow时间适配器
 * 
 * @author lyh
 * @version 创建时间：2016年12月9日 下午2:44:39
 * 
 */
public class CollLineAdapter extends MBaseAdapter<EcLineEntity> {
	private int selectedPosition = -1;

	public CollLineAdapter(Context context, ArrayList<EcLineEntity> models) {
		super(context, models);
	}

	public int getSelectedPosition() {
		return this.selectedPosition;
	}

	public void setSelectedPosition(int position) {
		// TODO Auto-generated method stub
		selectedPosition = position;
	}

	@SuppressLint("CutPasteId")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.coll_pop_item, null);
			viewHolder.ico = (ImageView) convertView.findViewById(R.id.iv_ico);
			viewHolder.content = (TextView) convertView.findViewById(R.id.tv_showcontent);
			viewHolder.select = (ImageView) convertView.findViewById(R.id.iv_select);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		EcLineEntity entity = models.get(position);
		viewHolder.content.setText(entity.getName());
		if (selectedPosition == position) {
			viewHolder.select.setVisibility(View.VISIBLE);
			viewHolder.content.setTextColor(context.getResources().getColor(R.color.main_tab_f));
		} else {
			viewHolder.select.setVisibility(View.GONE);
			viewHolder.content.setTextColor(Color.BLACK);
		}
		viewHolder.ico.setVisibility(View.GONE);
		return convertView;
	}

	class ViewHolder {
		public ImageView ico;
		public TextView content;
		public ImageView select;
		public RadioButton radio;
	}
}
