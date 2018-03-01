package com.winway.android.ewidgets.simpletable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.simpletable.MyScrollView.OnScrollListener;

/**
 * 符合APP的简单的表格
 * @author mr-lao
 *
 */
public class TableView {
	private List<Object> groupNameList;
	private Map<Object, List<Object[]>> groupItemsMap;
	private Context context;
	private List<Object[]> itemsList;
	/**初始化展开分组*/
	private List<Object> expandGroupList;
	// item项的背景颜色，正常背景颜色、点击的背景颜色
	private int[] itembgColors;
	private int[] itembgImages;
	// 去除需要改变背景的分组的item,excludeGroupItemBgColor存放的是分组名
	private List<Object> excludeGroupItemBg;
	/**滑动展开分组*/
	private List<Object> scrollExpandGroupList;

	/**
	 * 背景颜色
	 * @param itembgColors  颜色数组，数组长度固定为2， itembgColors[0]表示正常的背景颜色， itembgColors[1]表示按下的背景颜色
	 */
	public void setItembgColors(int[] itembgColors) {
		if (itembgColors.length != 2) {
			throw new RuntimeException("参数itembgColors的长度只能等于2");
		}
		this.itembgColors = itembgColors;
	}

	/**
	 * 背景图片
	 * @param itembgImages  颜色数组，数组长度固定为2， itembgImages[0]表示正常的背景图片， itembgImages[1]表示按下的背景图片
	 */
	public void setItembgImages(int[] itembgImages) {
		this.itembgImages = itembgImages;
	}

	/**
	 * 不需要背景改动的分组
	 * @param excludeGroupItemBg
	 */
	public void setExcludeGroupItemBg(List<Object> excludeGroupItemBg) {
		this.excludeGroupItemBg = excludeGroupItemBg;
	}

	/**
	 * 不需要背景改动的分组
	 * @param excludeGroupItemBg
	 */
	public void setExcludeGroupItemBg(Object[] excludeGroupItemBg) {
		this.excludeGroupItemBg = new ArrayList<>();
		for (Object group : excludeGroupItemBg) {
			this.excludeGroupItemBg.add(group);
		}
	}

	/**
	 * 设置展开分组
	 * @param expandGroup
	 */
	public void setExpandGroup(List<Object> expandGroup) {
		this.expandGroupList = expandGroup;
	}

	/**
	 * 设置滑动展开分组
	 * @time 2017年9月1日 下午3:53:39
	 * @param scrollExpandGroupList
	 */
	public void setScrollExpandGroupList(List<Object> scrollExpandGroupList) {
		this.scrollExpandGroupList = scrollExpandGroupList;
	}

	public TableView(Context context, List<Object> groupNameList, Map<Object, List<Object[]>> groupItemsMap) {
		super();
		this.context = context;
		this.groupNameList = groupNameList;
		this.groupItemsMap = groupItemsMap;
	}

	public TableView(Context context, List<Object[]> itemsList) {
		this.context = context;
		this.itemsList = itemsList;
	}

	/**
	 * 获取表格视图
	 * 使用方法：tableContainer.addView(table.getView());
	 * @return
	 */
	public View getView() {
		MyScrollView scrollView = new MyScrollView(context);
		scrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		LinearLayout contaner = new LinearLayout(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		contaner.setLayoutParams(layoutParams);
		contaner.setOrientation(LinearLayout.VERTICAL);
		if (null != groupNameList) {
			for (Object groupName : groupNameList) {
				addTableGroup(context, contaner, groupName, groupItemsMap.get(groupName));
			}
		} else {
			addTableGroup(context, contaner, null, itemsList);
		}

		scrollView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onTop() {

			}

			@Override
			public void onBottom() {
				if (null != scrollExpandGroupList && !scrollExpandGroupList.isEmpty()) {
					// 展开
					Object groupname = scrollExpandGroupList.remove(0);
					ViewGroup viewGroup = groupMap.get(groupname);
					if (null != viewGroup) {
						viewGroup.setVisibility(View.VISIBLE);
					}
				}
			}
		});
		scrollView.addView(contaner);
		return scrollView;
	}

	HashMap<Object, ViewGroup> groupMap = new HashMap<Object, ViewGroup>();

	public void addTableGroup(Context context, LinearLayout contaner, final Object groupName,
			final List<Object[]> groupItems) {
		// 分组
		View groupView = View.inflate(context, R.layout.layout_table, null);
		final LinearLayout itemContaiuner = (LinearLayout) groupView.findViewById(R.id.table_group_item_container);
		LinearLayout groupContaiuner = (LinearLayout) groupView.findViewById(R.id.table_group);
		// 添加分组到容器中
		groupMap.put(groupName, itemContaiuner);
		final ImageView groupIV = (ImageView) groupView.findViewById(R.id.table_group_image);
		if (null == groupName) {
			groupView.findViewById(R.id.space_line).setVisibility(View.GONE);
			groupContaiuner.setVisibility(View.GONE);
			itemContaiuner.setVisibility(View.VISIBLE);
		} else {
			TextView groupNameTV = (TextView) groupView.findViewById(R.id.table_group_name);
			groupNameTV.setText(groupName.toString());
			groupContaiuner.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					boolean expand = false;
					if (itemContaiuner.getVisibility() == View.VISIBLE) {
						itemContaiuner.setVisibility(View.GONE);
						groupIV.setImageResource(R.drawable.jt_r);
						expand = false;
					} else {
						itemContaiuner.setVisibility(View.VISIBLE);
						groupIV.setImageResource(R.drawable.jt_b);
						expand = true;
					}
					if (null != tableGroupItemClickListener) {
						tableGroupItemClickListener.onGroupItemClick(groupName, groupItems, expand);
					}
				}
			});
			if (null != expandGroupList && expandGroupList.contains(groupName)) {
				itemContaiuner.setVisibility(View.VISIBLE);
				groupIV.setImageResource(R.drawable.jt_b);
			}
		}
		// 分组内的item
		if (null == groupItems || groupItems.isEmpty()) {
			contaner.addView(groupView);
			return;
		}
		for (final Object[] item : groupItems) {
			View itemview = null;
			if (null == tableItemCreator) {
				itemview = View.inflate(context, R.layout.layout_table_item, null);
				TextView nameTV = (TextView) itemview.findViewById(R.id.item_name);
				TextView messageTV = (TextView) itemview.findViewById(R.id.item_message);
				nameTV.setText(item[0].toString());
				messageTV.setText(item[1].toString());
				itemContaiuner.addView(itemview);
				if (onItemCreateListener != null) {
					onItemCreateListener.onItemCreate(nameTV, messageTV, item);
				}
			} else {
				itemview = tableItemCreator.createItemView(itemContaiuner, item);
			}
			// 设置item项点击事件
			if (null != tableItemClickListener) {
				itemview.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						tableItemClickListener.onItemClick(groupName, item);
					}
				});
			}
			// 设置item项触摸背景
			if ((null != itembgColors || itembgImages != null)) {
				if ((excludeGroupItemBg != null && excludeGroupItemBg.contains(groupName))) {
					continue;
				}
				itemview.setOnTouchListener(new OnTouchListener() {
					@SuppressLint("ClickableViewAccessibility")
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						View realItemView = (v.getTag() == null ? v : (View) v.getTag());
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							if (null == itembgImages) {
								realItemView.setBackgroundColor(itembgColors[1]);
							} else {
								realItemView.setBackgroundResource(itembgImages[1]);
							}
						} else if (event.getAction() == MotionEvent.ACTION_UP
								|| event.getAction() == MotionEvent.ACTION_OUTSIDE
								|| event.getAction() == MotionEvent.ACTION_CANCEL) {
							if (null == itembgImages) {
								realItemView.setBackgroundColor(itembgColors[0]);
							} else {
								realItemView.setBackgroundResource(itembgImages[0]);
							}
						}
						return false;
					}
				});
			}
		}
		contaner.addView(groupView);
	}

	private OnItemCreateListener onItemCreateListener;

	public void setOnItemCreateListener(OnItemCreateListener listener) {
		onItemCreateListener = listener;
	}

	public interface OnItemCreateListener {
		public void onItemCreate(TextView nameTV, TextView messageTV, Object[] item);
	}

	private TableItemCreator tableItemCreator;

	public void setTableItemCreator(TableItemCreator tableItemCreator) {
		this.tableItemCreator = tableItemCreator;
	}

	public interface TableItemCreator {
		public View createItemView(ViewGroup itemContaiuner, Object[] item);
	}

	public static class DefaultTableItemCreator implements TableItemCreator {
		@Override
		public View createItemView(ViewGroup itemContaiuner, Object[] item) {
			View itemview = View.inflate(itemContaiuner.getContext(), R.layout.layout_table_item, null);
			TextView nameTV = (TextView) itemview.findViewById(R.id.item_name);
			TextView messageTV = (TextView) itemview.findViewById(R.id.item_message);
			nameTV.setText(item[0].toString());
			messageTV.setText(item[1].toString());
			itemContaiuner.addView(itemview);
			return itemview;
		}
	}

	private TableItemClickListener tableItemClickListener;

	public void setTableItemClickListener(TableItemClickListener tableItemClickListener) {
		this.tableItemClickListener = tableItemClickListener;
	}

	public interface TableItemClickListener {
		public void onItemClick(Object group, Object[] item);
	}

	private TableGroupItemClickListener tableGroupItemClickListener;

	public void setTableGroupItemClickListener(TableGroupItemClickListener tableGroupItemClickListener) {
		this.tableGroupItemClickListener = tableGroupItemClickListener;
	}

	public interface TableGroupItemClickListener {
		public void onGroupItemClick(Object group, List<Object[]> groupItems, boolean expand);
	}
}
