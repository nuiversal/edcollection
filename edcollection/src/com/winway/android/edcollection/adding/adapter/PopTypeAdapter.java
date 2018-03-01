package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 新增PopupWindow路径点适配器
 * 
 * @author ly
 *
 */
public class PopTypeAdapter extends MBaseAdapter<String> {
	private int selectedPosition = 0;

	public PopTypeAdapter(Context context, ArrayList<String> models) {
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
			convertView = View.inflate(context, R.layout.new_pop_item, null);
			viewHolder.ico = (ImageView) convertView.findViewById(R.id.iv_ico);
			viewHolder.content = (TextView) convertView.findViewById(R.id.tv_showcontent);
			viewHolder.select = (ImageView) convertView.findViewById(R.id.iv_select);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (models.get(position).equals(NodeMarkerType.BSQ.getName())) {
			viewHolder.ico.setImageDrawable(context.getResources().getDrawable(R.drawable.bsq));
		} else if (models.get(position).equals(NodeMarkerType.BSD.getName())) {
			viewHolder.ico.setImageDrawable(context.getResources().getDrawable(R.drawable.bsd));
		} else if (models.get(position).equals(NodeMarkerType.XND.getName())) {
			viewHolder.ico.setImageDrawable(context.getResources().getDrawable(R.drawable.ljd));
		} else if (models.get(position).equals(NodeMarkerType.GT.getName())) {
			viewHolder.ico.setImageDrawable(context.getResources().getDrawable(R.drawable.tower));
		} else if (models.get(position).equals(NodeMarkerType.AJH.getName())) {
			viewHolder.ico.setImageDrawable(context.getResources().getDrawable(R.drawable.ajh));
		}
		viewHolder.content.setText(models.get(position));
		if (selectedPosition == position) {
			viewHolder.select.setVisibility(View.VISIBLE);
			viewHolder.content.setTextColor(Color.BLACK);
		} else {
			viewHolder.select.setVisibility(View.GONE);
			viewHolder.content.setTextColor(Color.GRAY);
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popTypeItemClickListener.onItemClick(position);
			}
		});
		return convertView;
	}

	class ViewHolder {
		public ImageView ico;
		public TextView content;
		public ImageView select;
	}

	private OnPopTypeItemClickListener popTypeItemClickListener;

	public void setPopTypeItemClickListener(OnPopTypeItemClickListener popTypeItemClickListener) {
		this.popTypeItemClickListener = popTypeItemClickListener;
	}

	public interface OnPopTypeItemClickListener {
		void onItemClick(int position);
	}

}
