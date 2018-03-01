package com.winway.android.edcollection.project.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.project.entity.EmProjectEntity;

/**
 * @author lyh
 * @version 创建时间：2016年12月6日 下午4:39:25
 * 
 */
public class ProjectingAdapter extends MBaseAdapter<EmProjectEntity> {

	private Context context;
	private List<EmProjectEntity> list;
	private int selectedPosition = 0;

	public ProjectingAdapter(Context context, ArrayList<EmProjectEntity> models) {
		super(context, models);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = models;
	}

	public void setSelectedPosition(int position) {
		// TODO Auto-generated method stub
		selectedPosition = position;
	}

	public int getSelectPosition() {
		return selectedPosition;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.activity_projecting_lv_item, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.tv_pro_lv_item_projectName);
			holder.img = (ImageView) convertView.findViewById(R.id.iv_pro_lv_item_confirm);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		EmProjectEntity entity = list.get(position);
		holder.text.setText(entity.getPrjName());
		if (selectedPosition == position) {
			holder.img.setVisibility(View.VISIBLE);
		} else {
			holder.img.setVisibility(View.GONE);
		}
		return convertView;
	}

}

class ViewHolder {
	public TextView text;
	public ImageView img;
}