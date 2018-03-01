package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.project.entity.EcLineEntity;

/** 已选线路列表适配器
* @author lyh  
* @version 创建时间：2017年2月9日 下午2:50:55 
* 
*/
public class SelectedLineListAdapter extends MBaseAdapter<EcLineEntity> {
	public SelectedLineListAdapter(Context context,
			ArrayList<EcLineEntity> models) {
		super(context, models);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint({ "CutPasteId", "UseSparseArrays" })
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.line_search_add, null);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.tv_select_line_showcontent_add);
			viewHolder.select = (ImageView) convertView
					.findViewById(R.id.iv_select_line_select_del);
			viewHolder.add = (ImageView) convertView
					.findViewById(R.id.iv_select_line_select_add);
			viewHolder.add.setVisibility(View.GONE);
			viewHolder.select.setVisibility(View.VISIBLE);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//移除当前线路
				models.remove(position);
				//刷新列表
				SelectedLineListAdapter.this.notifyDataSetChanged();
			}
		});
		//去重复
		if (!models.contains(models.get(position).getName())) {
			viewHolder.content.setText(models.get(position).getName());
		}
		return convertView;
	}

	public class ViewHolder {
		public TextView content;
		public ImageView select;
		public ImageView add;
	}
}
