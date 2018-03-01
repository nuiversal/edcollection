package com.winway.android.ewidgets.tree.holder;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.entity.ItemEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * @author mr-lao
 * 使用这个TreeViewHolder，必须有一个id为item_content的布局控件，而且，所有视图内容必须在item_content容器中。
 * 必须在布局文件中要有id为checkbox的CheckBox控件，有一个id为text的TextView控件
 * 注意的是：此类当时是基于电缆可视化3.0的业务需求而设计的，基本能满足电缆可视化3.0的需求
 * 
 * @update 2016-12-15   增加控制选择框和点击箭头
 * @note 建议使用<code>CommonHolder</code>类
 */

public class BaseTreeNodeHolder<E> extends TreeNode.BaseNodeViewHolder<E> {
	private Context context;
	private int layoutID;
	private int margin = 200;

	private boolean hasCheckBox = true;
	private boolean hasECXImage = true;

	public boolean isHasCheckBox() {
		return hasCheckBox;
	}

	public void setHasCheckBox(boolean hasCheckBox) {
		this.hasCheckBox = hasCheckBox;
	}

	public boolean isHasECXImage() {
		return hasECXImage;
	}

	public void setHasECXImage(boolean hasECXImage) {
		this.hasECXImage = hasECXImage;
	}

	private BaseTreeNodeHolder(Context context) {
		super(context);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public BaseTreeNodeHolder(Context context, int layoutid) {
		this(context);
		this.context = context;
		this.layoutID = layoutid;
	}

	//
	int i = 0;

	@Override
	public View createNodeView(final TreeNode node, E value) {
		Log.i("base-info", "" + i++);
		Log.i("info", "进来了。。。。。。。。。。。。。");
		View layoutView = View.inflate(context, layoutID, null);
		TextView text = (TextView) layoutView.findViewById(R.id.text);
		text.setText("" + value);

		CheckBox checkBox = (CheckBox) layoutView.findViewById(R.id.checkbox);
		if (hasCheckBox) {
			// 设置选择框点击事件
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (null != checkBoxCheckListener) {
						checkBoxCheckListener.onCheckedChanged(node, buttonView, isChecked);
					}
				}
			});
		} else {
			checkBox.setVisibility(View.GONE);
		}

		View contentView = layoutView.findViewById(R.id.item_content);

		final View imgEC = layoutView.findViewById(R.id.tree_close);
		final View imgEX = layoutView.findViewById(R.id.tree_expend);
		if (node.getChildren() == null || node.getChildren().isEmpty()) {
			imgEX.setVisibility(View.INVISIBLE);
			imgEC.setVisibility(View.INVISIBLE);
		}

		// 箭头变化
		layoutView.setOnTouchListener(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (node.getChildren() == null || node.getChildren().isEmpty()) {
					return false;
				}
				if (!node.isExpanded()) {
					imgEX.setVisibility(View.VISIBLE);
					imgEC.setVisibility(View.GONE);
				} else {
					imgEC.setVisibility(View.VISIBLE);
					imgEX.setVisibility(View.GONE);
				}
				return false;
			}
		});
		// 偏移
		setMargin(contentView, node, margin);
		return layoutView;
	}

	protected static void setMargin(View contentView, TreeNode node, int margin) {
		// 设置偏移
		LayoutParams layoutParams = contentView.getLayoutParams();
		if (layoutParams instanceof LinearLayout.LayoutParams) {
			// 线性布局
			LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) layoutParams;
			param.leftMargin = (node.getLevel() - 1) * margin;
			contentView.setLayoutParams(param);
		} else if (layoutParams instanceof RelativeLayout.LayoutParams) {
			// 相对布局
			RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layoutParams;
			param.leftMargin = (node.getLevel() - 1) * margin;
			contentView.setLayoutParams(param);
		} else if (layoutParams instanceof FrameLayout.LayoutParams) {
			// 帧布局
			FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) layoutParams;
			param.leftMargin = (node.getLevel() - 1) * margin;
			contentView.setLayoutParams(param);
		} else if (layoutParams instanceof TableLayout.LayoutParams) {
			// 表格布局
			TableLayout.LayoutParams param = (TableLayout.LayoutParams) layoutParams;
			param.leftMargin = (node.getLevel() - 1) * margin;
			contentView.setLayoutParams(param);
		}
	}

	private CheckBoxCheckListener checkBoxCheckListener = new DefaultCheckBoxCheckListener();

	// 设置节点选择框事件监听器
	public void setCheckBoxCheckListener(CheckBoxCheckListener checkBoxCheckListener) {
		this.checkBoxCheckListener = checkBoxCheckListener;
	}

	public CheckBoxCheckListener getCheckBoxCheckListener() {
		return checkBoxCheckListener;
	}

	public interface CheckBoxCheckListener {
		void onCheckedChanged(TreeNode node, CompoundButton buttonView, boolean isChecked);

		boolean isClick();

		void setClick(boolean click);
	}

	/**
	 * 选择框基本处理事件类，抽象类
	 * @author mr-lao
	 *
	 */
	static abstract class BaseCheckBoxCheckListener implements CheckBoxCheckListener {
		private boolean checkNodeBox = false;

		public void setCheckNodeBox(boolean b) {
			checkNodeBox = b;
		}

		protected void selectNodeCheckBox(TreeNode node, boolean isChecked) {
			((CheckBox) node.getViewHolder().getView().findViewById(R.id.checkbox))
					.setChecked(isChecked);
			if (node.getChildren() != null && !node.getChildren().isEmpty()) {
				for (TreeNode n : node.getChildren()) {
					((CheckBox) n.getViewHolder().getView().findViewById(R.id.checkbox))
							.setChecked(isChecked);
				}
			}
		}

		@Override
		public void onCheckedChanged(TreeNode node, CompoundButton buttonView, boolean isChecked) {
			if (checkNodeBox) {
				selectNodeCheckBox(node, isChecked);
			}
			checkedChangedPerform(node, buttonView, isChecked);
		}

		abstract void checkedChangedPerform(TreeNode node, CompoundButton buttonView,
				boolean isChecked);
	}

	// 默认的节点CheckBox点击事件监听器
	class DefaultCheckBoxCheckListener extends BaseCheckBoxCheckListener {
		@Override
		void checkedChangedPerform(TreeNode node, CompoundButton buttonView, boolean isChecked) {

		}

		@Override
		public boolean isClick() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setClick(boolean click) {
			// TODO Auto-generated method stub

		}
	}
}
