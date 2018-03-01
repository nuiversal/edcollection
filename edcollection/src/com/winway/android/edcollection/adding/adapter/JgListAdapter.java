package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.JgControll;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;

/** 
 * @author lyh
 * @version 创建时间：2017年3月28日 
 *
 */
public class JgListAdapter extends MBaseAdapter<EcWorkWellCoverEntity>{

	public JgListAdapter(Context context, ArrayList<EcWorkWellCoverEntity> models) {
		super(context, models);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.line_search_add, null);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.tv_select_line_showcontent_add);
			viewHolder.del = (ImageView) convertView
					.findViewById(R.id.iv_select_line_select_del);
			viewHolder.add = (ImageView) convertView
					.findViewById(R.id.iv_select_line_select_add);
			viewHolder.add.setVisibility(View.GONE);
			viewHolder.del.setVisibility(View.VISIBLE);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//移除当前线路
				models.remove(position);
				//刷新列表
				JgListAdapter.this.notifyDataSetChanged();
			}
		});
		viewHolder.name.setText (models.get(position).getSbmc());
		return convertView;
	}
	public class ViewHolder {
		public TextView name;
		public ImageView add;
		public ImageView del;
	}
}
