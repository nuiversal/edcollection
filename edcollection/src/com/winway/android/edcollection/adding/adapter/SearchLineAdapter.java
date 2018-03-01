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
import com.winway.android.util.ToastUtil;

/** 待选线路列表
* @author lyh  
* @version 创建时间：2017年2月9日 下午2:04:41 
* 
*/
public class SearchLineAdapter extends MBaseAdapter<EcLineEntity> {
	private SearchLineAdapter(Context context, ArrayList<EcLineEntity> models) {
		super(context, models);
		// TODO Auto-generated constructor stub
	}

	// 已经选择的线路列表适配器
	private SelectedLineListAdapter selectedLineAdapter;
	// 已选择列表数据容器中
	ArrayList<EcLineEntity> selectedDatas;

	public SearchLineAdapter(Context context, ArrayList<EcLineEntity> models,
			SelectedLineListAdapter selectedLineAdapter,
			ArrayList<EcLineEntity> selectedDatas) {
		super(context, models);
		this.selectedLineAdapter = selectedLineAdapter;
		this.selectedDatas = selectedDatas;
	}
	
	public void setSelectedDatas(ArrayList<EcLineEntity> selectedDatas) {
		this.selectedDatas = selectedDatas;
	}

	@SuppressLint({ "CutPasteId", "UseSparseArrays" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.line_search_add, null);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.tv_select_line_showcontent_add);
			viewHolder.select = (ImageView) convertView
					.findViewById(R.id.iv_select_line_select_add);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final EcLineEntity ecLineEntity = models.get(position);
		viewHolder.content.setText(ecLineEntity.getName());
		viewHolder.select.setVisibility(View.VISIBLE);
		viewHolder.select.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 判断eclineEntity是否在selectedDatas容器中存在
				// 如果存在，则不添加
				// 如果不存在 ，则添加
				if(selectedDatas.contains(ecLineEntity)) {
					ToastUtil.show(context, "该线路已添加", 100);
					return ;
				}
				selectedDatas.add(ecLineEntity);
				selectedLineAdapter.notifyDataSetChanged();
			}
		});
		return convertView;
	}

	public class ViewHolder {
		public TextView content;
		public ImageView select;
	}
}
