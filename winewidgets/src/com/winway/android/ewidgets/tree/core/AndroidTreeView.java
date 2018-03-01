package com.winway.android.ewidgets.tree.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.tree.core.TreeNode.BaseNodeViewHolder;
import com.winway.android.ewidgets.tree.datacenter.DataLoader;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.holder.BaseTreeNodeHolder;
import com.winway.android.ewidgets.tree.holder.CommonHolder;
import com.winway.android.ewidgets.tree.holder.CommonHolder.CommonTreeNodeClickListener;

/**
 * Created by Bogdan Melnychuk on 2/10/15.
 * 
 * 简单教程：
 * 1、setCommonClickListener(clickListener)
 * 2、setCommonDataLoader(dataLoader)
 * 3、setCommonCheckBoxListener(cheboxListener)
 * 4、containerView.addView(tree.getView())
 * @Example
 * {
 *      View containerView = findViewById(R.id.tree_container);
 *      AndroidTreeView tree = new AndroidTreeView(Context context);
 *      tree.setRoot(root);
 *      tree.setCommonClickListener(clickListener)
 *      tree.setCommonDataLoader(dataLoader)
 *      tree.setCommonCheckBoxListener(cheboxListener)
 *      containerView.addView(tree.getView());
 * }
 */
public class AndroidTreeView {
	private static final String NODES_PATH_SEPARATOR = ";";

	protected TreeNode mRoot;
	private Context mContext;
	private boolean applyForRoot;
	private int containerStyle = 0;
	private Class<? extends TreeNode.BaseNodeViewHolder> defaultViewHolderClass = SimpleViewHolder.class;
	private Class<? extends BaseTreeNodeHolder> baseViewHolderClass = null;
	private int layoutID;
	private CommonHolder.Config config = null;
	private BaseTreeNodeHolder.CheckBoxCheckListener checkBoxListener;
	private TreeNode.TreeNodeClickListener nodeClickListener;
	private TreeNode.TreeNodeLongClickListener nodeLongClickListener;
	private boolean mSelectionModeEnabled;
	private boolean mUseDefaultAnimation = false;
	private boolean use2dScroll = false;
	private boolean enableAutoToggle = true;

	public Context getmContext() {
		return mContext;
	}

	public CommonHolder.Config getConfig() {
		return config;
	}

	public void setConfig(CommonHolder.Config config) {
		this.config = config;
	}

	// 增加设置默认的ViewHolder，建议使用setCommonViewHolder
	public void setBaseViewHolder(@SuppressWarnings("rawtypes") Class<BaseTreeNodeHolder> baseViewHolderClass,
			int layoutID, BaseTreeNodeHolder.CheckBoxCheckListener checkBoxListener) {
		this.baseViewHolderClass = baseViewHolderClass;
		this.layoutID = layoutID;
		this.checkBoxListener = checkBoxListener;
	}

	private CommonTreeNodeClickListener commListener;

	/**
	 * 
	 * @time 2017年9月13日 下午4:13:39
	 * @param commListener
	 */
	public void setCommonClickListener(CommonTreeNodeClickListener commListener) {
		if (null != this.commListener && null == commListener.getCustomDataLoader()) {
			DataLoader customDataLoader = this.commListener.getCustomDataLoader();
			this.commListener = commListener;
			this.commListener.setCustomDataLoader(customDataLoader);
		} else {
			this.commListener = commListener;
		}
		setCommonViewHolder(CommonHolder.class, config, checkBoxListener, commListener);
	}

	public void setCommonDataLoader(DataLoader dataLoader, CommonHolder.Config config) {
		if (null == this.commListener) {
			this.commListener = new CommonTreeNodeClickListener(config) {
				@Override
				public void onNodeClick(TreeNode node, ItemEntity value) {

				}
			};
			setCommonViewHolder(CommonHolder.class, config, checkBoxListener, this.commListener);
		}
		commListener.setCustomDataLoader(dataLoader);
	}

	public void setCommonDataLoader(DataLoader dataLoader) {
		setCommonDataLoader(dataLoader, CommonHolder.defaultConfig());
	}

	/**
	 * 设置选择框
	 * @time 2017年9月13日 下午4:13:27
	 * @param checkBoxListener
	 */
	public void setCommonCheckBoxListener(BaseTreeNodeHolder.CheckBoxCheckListener checkBoxListener) {
		this.checkBoxListener = checkBoxListener;
	}

	/**
	 * 设置默认的ViewHolder（建议使用此方法），功能和setBaseViewHolder相同，但是不建议使用setBaseViewHolder
	 * 注意：使用CommonHolder的时候，TreeNode里面的数据实体必须得是ItemEntity类型，否则会出错
	 * @param baseViewHolderClass
	 * @param config
	 * @param checkBoxListener
	 * @param commListener  CommonHolder中实现TreeNodeClickListener接口的抽象类，目的是提供是些共用的点击事件处理功能
	 */
	public void setCommonViewHolder(Class<CommonHolder> baseViewHolderClass, CommonHolder.Config config,
			BaseTreeNodeHolder.CheckBoxCheckListener checkBoxListener, CommonTreeNodeClickListener commListener) {
		this.commListener = commListener;
		this.commListener.setTree(this);
		this.baseViewHolderClass = baseViewHolderClass;
		this.config = config;
		this.commListener.setConfig(this.config);
		this.checkBoxListener = checkBoxListener;
		setDefaultNodeClickListener(commListener);
	}

	/**
	 * 设置默认的ViewHolder（建议使用此方法），功能和setBaseViewHolder相同，但是不建议使用setBaseViewHolder
	 * 注意：使用CommonHolder的时候，TreeNode里面的数据实体必须得是ItemEntity类型，否则会出错
	 * @param config 树节点配置
	 * @param checkBoxListener  选择框选择事件
	 * @param dataLoader  数据加载器，加载台账树数据
	 */
	public void setCommonViewHolder(CommonHolder.Config config,
			BaseTreeNodeHolder.CheckBoxCheckListener checkBoxListener, DataLoader dataLoader) {
		CommonTreeNodeClickListener commListener = new CommonTreeNodeClickListener(config) {
			@Override
			public void onNodeClick(TreeNode node, ItemEntity value) {

			}
		};
		commListener.setTree(this);
		commListener.setCustomDataLoader(dataLoader);
		commListener.setNeedProcessCheckBox(false);
		setCommonViewHolder(CommonHolder.class, config, checkBoxListener, commListener);
	}

	/**
	 * 
	 * @time 2017年9月13日 下午4:02:32
	 * @param checkBoxListener
	 * @param dataLoader
	 */
	public void setCommonViewHolder(BaseTreeNodeHolder.CheckBoxCheckListener checkBoxListener, DataLoader dataLoader) {
		CommonHolder.Config config = CommonHolder.defaultConfig();
		setCommonViewHolder(config, checkBoxListener, dataLoader);
	}

	public AndroidTreeView(Context context) {
		mContext = context;
	}

	public void setRoot(TreeNode mRoot) {
		this.mRoot = mRoot;
	}

	public TreeNode getRoot() {
		return mRoot;
	}

	public AndroidTreeView(Context context, TreeNode root) {
		mRoot = root;
		mContext = context;
	}

	public void setDefaultAnimation(boolean defaultAnimation) {
		this.mUseDefaultAnimation = defaultAnimation;
	}

	public void setDefaultContainerStyle(int style) {
		setDefaultContainerStyle(style, false);
	}

	public void setDefaultContainerStyle(int style, boolean applyForRoot) {
		containerStyle = style;
		this.applyForRoot = applyForRoot;
	}

	public void setUse2dScroll(boolean use2dScroll) {
		this.use2dScroll = use2dScroll;
	}

	public boolean is2dScrollEnabled() {
		return use2dScroll;
	}

	public void setUseAutoToggle(boolean enableAutoToggle) {
		this.enableAutoToggle = enableAutoToggle;
	}

	public boolean isAutoToggleEnabled() {
		return enableAutoToggle;
	}

	public void setDefaultViewHolder(
			@SuppressWarnings("rawtypes") Class<? extends TreeNode.BaseNodeViewHolder> viewHolder) {
		defaultViewHolderClass = viewHolder;
	}

	public void setDefaultNodeClickListener(TreeNode.TreeNodeClickListener listener) {
		nodeClickListener = listener;
	}

	public void setDefaultNodeLongClickListener(TreeNode.TreeNodeLongClickListener listener) {
		nodeLongClickListener = listener;
	}

	public void expandAll() {
		expandNode(mRoot, true);
	}

	public void collapseAll() {
		if (mRoot != null) {
			for (TreeNode n : mRoot.getChildren()) {
				collapseNode(n, true);
			}
		}
	}

	private ViewGroup view;

	// 最多支持六级树,此方法暂时没有实现，不建议使用
	private int getNodeHeight(TreeNode node) {
		int y = 1;
		TreeNode parent = node.getParent();
		if (parent != null) {
			TreeNode parent2 = parent.getParent();
			if (parent2 != null) {
				TreeNode parent3 = parent2.getParent();
				if (parent3 != null) {
					TreeNode parent4 = parent3.getParent();
					if (parent4 != null) {
						TreeNode parent5 = parent4.getParent();
						if (parent5 != null) {

						} else {
							// 第五层节点
						}
					} else {
						// 第四层节点
					}
				} else {
					// 第三层节点
				}
			} else {
				// 第二层节点
			}
			// 第一层节点
			int indexOf1 = parent2.getChildren().indexOf(node);
		} else {
			// 根节点，直接返回1
		}
		return y;
	}

	// 递归算法求出树某节点的深度
	private int getNodeHeightByDG(TreeNode node) {
		int y = 1;
		if (node.getParent() == null) {
			// 表示已经处于根节点了
			return 0;
		} else {
			// 找到节点在父节点中的索引位置
			int indexOf = node.getParent().getChildren().indexOf(node);
			if (indexOf < 0) {
				// 找不到
				return -10000;
			} else if (indexOf == 0) {
				indexOf = 1;
			}
			y = node.getViewHolder().getView().getHeight() * indexOf;
			// 如果父节点再有父节点，则进行递归
			y = y + getNodeHeightByDG(node.getParent());
		}
		return y;
	}

	// 界面定位到具体的节点处
	public void scrollTo(TreeNode node) {
		int indexOf = node.getParent().getChildren().indexOf(node);
		Log.i("info", "indexof = " + indexOf);
		int nodeHeightByDG = getNodeHeightByDG(node);
		Log.i("info", "height :" + nodeHeightByDG);
		scrollTo(1000, nodeHeightByDG);
	}

	// 界面定位到具体坐标处
	public void scrollTo(int x, int y) {
		if (view instanceof ScrollView) {
			ScrollView scroll = (ScrollView) view;
			scroll.scrollTo(x, y);
		} else if (view instanceof TwoDScrollView) {
			TwoDScrollView scroll = (TwoDScrollView) view;
			scroll.scrollTo(x, y);

		}
	}

	@SuppressWarnings("rawtypes")
	@SuppressLint("NewApi")
	public View getView(int style) {
		if (style > 0) {
			ContextThemeWrapper newContext = new ContextThemeWrapper(mContext, style);
			view = use2dScroll ? new TwoDScrollView(newContext) : new ScrollView(newContext);
		} else {
			view = use2dScroll ? new TwoDScrollView(mContext) : new ScrollView(mContext);
		}
		view.setVerticalScrollBarEnabled(false);
		Context containerContext = mContext;
		if (containerStyle != 0 && applyForRoot) {
			containerContext = new ContextThemeWrapper(mContext, containerStyle);
		}
		final LinearLayout viewTreeItems = new LinearLayout(containerContext, null, containerStyle);

		viewTreeItems.setId(R.id.tree_items);
		viewTreeItems.setOrientation(LinearLayout.VERTICAL);
		view.addView(viewTreeItems);

		mRoot.setViewHolder(new TreeNode.BaseNodeViewHolder(mContext) {
			@Override
			public View createNodeView(TreeNode node, Object value) {
				return null;
			}

			@Override
			public ViewGroup getNodeItemsView() {
				return viewTreeItems;
			}
		});

		expandNode(mRoot, false);
		return view;
	}

	public View getView() {
		if (view != null) {
			return view;
		}
		return getView(-1);
	}

	public void expandLevel(int level) {
		for (TreeNode n : mRoot.getChildren()) {
			expandLevel(n, level);
		}
	}

	private void expandLevel(TreeNode node, int level) {
		if (node.getLevel() <= level) {
			expandNode(node, false);
		}
		for (TreeNode n : node.getChildren()) {
			expandLevel(n, level);
		}
	}

	public void expandNode(TreeNode node) {
		expandNode(node, false);
	}

	// 将节点的所有父节点展开
	public void expandNoteWithParents(TreeNode node) {
		ArrayList<TreeNode> allParents = getAllParents(node);
		// allParents.add(node);
		for (TreeNode n : allParents) {
			expandNode(n);
		}
	}

	// 获取节点的所有父节点
	public static ArrayList<TreeNode> getAllParents(TreeNode node) {
		ArrayList<TreeNode> list = new ArrayList<TreeNode>();
		if (node.getParent() != null) {
			// 添加自身的父类
			list.add(node.getParent());
			// 添加父类的父类们
			list.addAll(getAllParents(node.getParent()));
		}
		return list;
	}

	public void collapseNode(TreeNode node) {
		collapseNode(node, false);
	}

	public String getSaveState() {
		final StringBuilder builder = new StringBuilder();
		getSaveState(mRoot, builder);
		if (builder.length() > 0) {
			builder.setLength(builder.length() - 1);
		}
		return builder.toString();
	}

	public void restoreState(String saveState) {
		if (!TextUtils.isEmpty(saveState)) {
			collapseAll();
			final String[] openNodesArray = saveState.split(NODES_PATH_SEPARATOR);
			final Set<String> openNodes = new HashSet<String>(Arrays.asList(openNodesArray));
			restoreNodeState(mRoot, openNodes);
		}
	}

	private void restoreNodeState(TreeNode node, Set<String> openNodes) {
		for (TreeNode n : node.getChildren()) {
			if (openNodes.contains(n.getPath())) {
				expandNode(n);
				restoreNodeState(n, openNodes);
			}
		}
	}

	private void getSaveState(TreeNode root, StringBuilder sBuilder) {
		for (TreeNode node : root.getChildren()) {
			if (node.isExpanded()) {
				sBuilder.append(node.getPath());
				sBuilder.append(NODES_PATH_SEPARATOR);
				getSaveState(node, sBuilder);
			}
		}
	}

	public void toggleNode(TreeNode node) {
		if (node.isExpanded()) {
			collapseNode(node, false);
		} else {
			expandNode(node, false);
		}

	}

	private void collapseNode(TreeNode node, final boolean includeSubnodes) {
		node.setExpanded(false);
		TreeNode.BaseNodeViewHolder nodeViewHolder = getViewHolderForNode(node);

		if (mUseDefaultAnimation) {
			collapse(nodeViewHolder.getNodeItemsView());
		} else {
			nodeViewHolder.getNodeItemsView().setVisibility(View.GONE);
		}
		nodeViewHolder.toggle(false);
		if (includeSubnodes) {
			for (TreeNode n : node.getChildren()) {
				collapseNode(n, includeSubnodes);
			}
		}
	}

	private void expandNode(final TreeNode node, boolean includeSubnodes) {
		node.setExpanded(true);
		final TreeNode.BaseNodeViewHolder parentViewHolder = getViewHolderForNode(node);
		parentViewHolder.getNodeItemsView().removeAllViews();

		parentViewHolder.toggle(true);

		for (final TreeNode n : node.getChildren()) {
			addNode(parentViewHolder.getNodeItemsView(), n);

			if (n.isExpanded() || includeSubnodes) {
				expandNode(n, includeSubnodes);
			}

		}
		if (mUseDefaultAnimation) {
			expand(parentViewHolder.getNodeItemsView());
		} else {
			parentViewHolder.getNodeItemsView().setVisibility(View.VISIBLE);
		}

	}

	private void addNode(ViewGroup container, final TreeNode n) {
		final TreeNode.BaseNodeViewHolder viewHolder = getViewHolderForNode(n);
		final View nodeView = viewHolder.getView();
		container.addView(nodeView);
		if (mSelectionModeEnabled) {
			viewHolder.toggleSelectionMode(mSelectionModeEnabled);
		}

		// TODO 设置节点点击事件
		nodeView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (n.getClickListener() != null) {
					n.getClickListener().onClick(n, n.getValue());
				} else if (nodeClickListener != null) {
					nodeClickListener.onClick(n, n.getValue());
				}
				if (enableAutoToggle) {
					toggleNode(n);
				}
			}
		});

		nodeView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				if (n.getLongClickListener() != null) {
					return n.getLongClickListener().onLongClick(n, n.getValue());
				} else if (nodeLongClickListener != null) {
					return nodeLongClickListener.onLongClick(n, n.getValue());
				}
				if (enableAutoToggle) {
					toggleNode(n);
				}
				return false;
			}
		});
	}

	// ------------------------------------------------------------
	// Selection methods

	public void setSelectionModeEnabled(boolean selectionModeEnabled) {
		if (!selectionModeEnabled) {
			// TODO fix double iteration over tree
			deselectAll();
		}
		mSelectionModeEnabled = selectionModeEnabled;

		for (TreeNode node : mRoot.getChildren()) {
			toggleSelectionMode(node, selectionModeEnabled);
		}

	}

	public <E> List<E> getSelectedValues(Class<E> clazz) {
		List<E> result = new ArrayList<E>();
		List<TreeNode> selected = getSelected();
		for (TreeNode n : selected) {
			Object value = n.getValue();
			if (value != null && value.getClass().equals(clazz)) {
				result.add((E) value);
			}
		}
		return result;
	}

	public boolean isSelectionModeEnabled() {
		return mSelectionModeEnabled;
	}

	private void toggleSelectionMode(TreeNode parent, boolean mSelectionModeEnabled) {
		toogleSelectionForNode(parent, mSelectionModeEnabled);
		if (parent.isExpanded()) {
			for (TreeNode node : parent.getChildren()) {
				toggleSelectionMode(node, mSelectionModeEnabled);
			}
		}
	}

	public List<TreeNode> getSelected() {
		if (mSelectionModeEnabled) {
			return getSelected(mRoot);
		} else {
			return new ArrayList<TreeNode>();
		}
	}

	// TODO Do we need to go through whole tree? Save references or consider
	// collapsed nodes as not selected
	private List<TreeNode> getSelected(TreeNode parent) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		for (TreeNode n : parent.getChildren()) {
			if (n.isSelected()) {
				result.add(n);
			}
			result.addAll(getSelected(n));
		}
		return result;
	}

	public void selectAll(boolean skipCollapsed) {
		makeAllSelection(true, skipCollapsed);
	}

	public void deselectAll() {
		makeAllSelection(false, false);
	}

	private void makeAllSelection(boolean selected, boolean skipCollapsed) {
		if (mSelectionModeEnabled) {
			for (TreeNode node : mRoot.getChildren()) {
				selectNode(node, selected, skipCollapsed);
			}
		}
	}

	public void selectNode(TreeNode node, boolean selected) {
		if (mSelectionModeEnabled) {
			node.setSelected(selected);
			toogleSelectionForNode(node, true);
		}
	}

	private void selectNode(TreeNode parent, boolean selected, boolean skipCollapsed) {
		parent.setSelected(selected);
		toogleSelectionForNode(parent, true);
		boolean toContinue = skipCollapsed ? parent.isExpanded() : true;
		if (toContinue) {
			for (TreeNode node : parent.getChildren()) {
				selectNode(node, selected, skipCollapsed);
			}
		}
	}

	private void toogleSelectionForNode(TreeNode node, boolean makeSelectable) {
		TreeNode.BaseNodeViewHolder holder = getViewHolderForNode(node);
		if (holder.isInitialized()) {
			getViewHolderForNode(node).toggleSelectionMode(makeSelectable);
		}
	}

	/**
	 * 增加CommonHolder支持
	 * @author mr-lao
	 * @update 2016-12-17
	 */
	private TreeNode.BaseNodeViewHolder getViewHolderForNode(TreeNode node) {
		TreeNode.BaseNodeViewHolder viewHolder = node.getViewHolder();
		if (viewHolder == null) {
			try {
				if (null == baseViewHolderClass) {
					final Object object = defaultViewHolderClass.getConstructor(Context.class).newInstance(mContext);
					viewHolder = (TreeNode.BaseNodeViewHolder) object;
				} else {
					Object object = null;
					if (baseViewHolderClass.equals(CommonHolder.class)) {
						object = baseViewHolderClass.getConstructor(Context.class, CommonHolder.Config.class)
								.newInstance(mContext, config);
					} else {
						object = baseViewHolderClass.getConstructor(Context.class, int.class).newInstance(mContext,
								layoutID);
					}
					viewHolder = (BaseNodeViewHolder) object;
					if (null != checkBoxListener) {
						((BaseTreeNodeHolder) viewHolder).setCheckBoxCheckListener(checkBoxListener);
					}
				}
				node.setViewHolder(viewHolder);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Could not instantiate class " + defaultViewHolderClass);
			}
		}
		if (viewHolder.getContainerStyle() <= 0) {
			viewHolder.setContainerStyle(containerStyle);
		}
		if (viewHolder.getTreeView() == null) {
			viewHolder.setTreeViev(this);
		}
		return viewHolder;
	}

	private static void expand(final View v) {
		v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		final int targetHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1 ? LinearLayout.LayoutParams.WRAP_CONTENT
						: (int) (targetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}

	private static void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}

	// -----------------------------------------------------------------
	// Add / Remove

	public void addNode(TreeNode parent, final TreeNode nodeToAdd) {
		parent.addChild(nodeToAdd);
		if (parent.isExpanded()) {
			@SuppressWarnings("rawtypes")
			final TreeNode.BaseNodeViewHolder parentViewHolder = getViewHolderForNode(parent);
			addNode(parentViewHolder.getNodeItemsView(), nodeToAdd);
		}
	}

	/**
	 * @update 2016-12-28  增加往树里增加集合数据
	 * @param parent
	 * @param nodeToAddList
	 */
	public void addNode(TreeNode parent, List<TreeNode> nodeToAddList) {
		for (TreeNode nodeToAdd : nodeToAddList) {
			addNode(parent, nodeToAdd);
		}
	}

	public void removeNode(TreeNode node) {
		if (node.getParent() != null) {
			TreeNode parent = node.getParent();
			int index = parent.deleteChild(node);
			if (parent.isExpanded() && index >= 0) {
				@SuppressWarnings("rawtypes")
				final TreeNode.BaseNodeViewHolder parentViewHolder = getViewHolderForNode(parent);
				parentViewHolder.getNodeItemsView().removeViewAt(index);
			}
		}
	}

	/**
	 * 移除根节点之外的所有节点
	 */
	public void removeAll() {
		if (view != null && null != mRoot && null != mRoot.getChildren()) {
			((ViewGroup) view).removeAllViews();
			mRoot.getChildren().clear();
		}
	}
}
