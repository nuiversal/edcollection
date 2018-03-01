package com.winway.android.edcollection.project.adapter;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.project.dto.EmProjectEntityDto;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.edcollection.project.entity.EmTasksEntity;
import com.winway.android.ewidgets.textview.ExpandableTextView;
import com.winway.android.util.DensityUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 二级列表适配器
 * 
 * @author WINWAY
 *
 */
public class ProjectingExpandableAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<EmProjectEntityDto> parents;
	private ArrayList<List<EmTasksEntity>> childs;

	private int selectedPosition = 0, selectedChildPosition = -1;

	private ExpandableListView mExpandableListView;

	public ProjectingExpandableAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		mContext = context;
	}

	public void setExpandableListView(ExpandableListView mExpandableListView) {
		this.mExpandableListView = mExpandableListView;
	}

	public void setParents(ArrayList<EmProjectEntityDto> parents) {
		this.parents = parents;
	}

	public void setChilds(ArrayList<List<EmTasksEntity>> childs) {
		this.childs = childs;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	public int getSelectedChildPosition() {
		return selectedChildPosition;
	}

	public void setSelectedChildPosition(int selectedChildPosition) {
		this.selectedChildPosition = selectedChildPosition;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return parents == null ? 0 : parents.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (childs == null) {
			return 0;
		}
		if (getGroupCount() > groupPosition) {
			return childs.get(groupPosition).size();
		}
		return 0;
	}

	@Override
	public EmProjectEntity getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		if (parents == null) {
			return null;
		}
		if (getGroupCount() > groupPosition) {
			return parents.get(groupPosition);
		}
		return null;
	}

	@Override
	public EmTasksEntity getChild(int groupPosition, int childPosition) {
		if (childs == null) {
			return null;
		}
		if (getGroupCount() > groupPosition && getChildrenCount(groupPosition) > childPosition) {
			return childs.get(groupPosition).get(childPosition);
		}
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	private int dp2px(Context context, int n) {
		return DensityUtil.dip2px(context, n);
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_projecting_lv_item, null);
			holder = new ViewHolder();
			holder.exp = (ImageView) convertView.findViewById(R.id.iv_pro_lv_item_expand);
			holder.text = (TextView) convertView.findViewById(R.id.tv_pro_lv_item_projectName);
			holder.img = (ImageView) convertView.findViewById(R.id.iv_pro_lv_item_confirm);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int dp15 = dp2px(mContext, 15);
		// 缩进
		holder.text.setPadding(dp2px(mContext, 60), dp15, dp15, dp15);
		// 处理展开收缩图标
		int childCount = getChildrenCount(groupPosition);
		if (childCount > 0) {
			holder.exp.setVisibility(View.VISIBLE);
			holder.exp.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (null != mExpandableListView) {
						if (isExpanded) {
							mExpandableListView.collapseGroup(groupPosition);
							onGroupCollapsed(groupPosition);
						} else {
							mExpandableListView.expandGroup(groupPosition);
						}
					}
				}
			});
		} else {
			holder.exp.setVisibility(View.GONE);
		}
		//
		EmProjectEntity emProjectEntity = getGroup(groupPosition);
		if (emProjectEntity != null) {
			holder.text.setText(emProjectEntity.getPrjName());
		}
		// 背景
		if (isExpanded) {
			holder.exp.setImageResource(R.drawable.more_up);
		} else {
			holder.exp.setImageResource(R.drawable.more_down);
		}

		// 是否选中
		if (selectedPosition == groupPosition && selectedChildPosition < 0) {
			// selectedChildPosition<0为当前已选中父级item
			((ViewGroup) holder.text.getParent()).setBackgroundColor(0xffdddddd);
			holder.img.setVisibility(View.VISIBLE);
		} else {
			holder.img.setVisibility(View.GONE);
			((ViewGroup) holder.text.getParent()).setBackgroundColor(0xffffffff);
		}
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_projecting_lv_item, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.tv_pro_lv_item_projectName);
			holder.img = (ImageView) convertView.findViewById(R.id.iv_pro_lv_item_confirm);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int dp15 = dp2px(mContext, 15);
		// 缩进
		((ViewGroup) holder.text.getParent()).setPadding(dp2px(mContext, 100), dp15, dp15, dp15);
		EmTasksEntity tasksEntity = getChild(groupPosition, childPosition);
		if (tasksEntity != null) {
			holder.text.setText(tasksEntity.getTaskName());
		}
		// 是否选中
		if (selectedPosition == groupPosition && selectedChildPosition == childPosition) {
			holder.img.setVisibility(View.VISIBLE);
		} else {
			holder.img.setVisibility(View.GONE);
		}
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	class ViewHolder {
		public ImageView exp;
		public TextView text;
		public ImageView img;
	}

	public void refresh() {
		this.notifyDataSetChanged();
	}

	// public void update(ArrayList<EmProjectEntityDto> list) {
	// this.parents = list;
	// this.notifyDataSetChanged();
	// }

}
