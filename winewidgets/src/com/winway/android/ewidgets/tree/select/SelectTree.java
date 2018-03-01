package com.winway.android.ewidgets.tree.select;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.datacenter.DataLoader;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.holder.BaseTreeNodeHolder;
import com.winway.android.ewidgets.tree.holder.CommonHolder.CommonTreeNodeClickListener;
import com.winway.android.ewidgets.tree.utils.TreeUtil;
import com.winway.android.util.ToastUtils;

/**
 * 选择台账
 * @author mr-lao
 *
 */
public class SelectTree {
	// 树控件
	private AndroidTreeView androidTree = null;
	// 树根
	private TreeNode rootNode;
	private Activity mActivity;
	// 数据加载器
	private DataLoader dataLoader;
	// 配置
	private SelectConfig config;
	// 选中对象
	private final ArrayList<ItemEntity> selectList = new ArrayList<>();

	private final ArrayList<TreeNode> selectNodeList = new ArrayList<TreeNode>();

	public ArrayList<ItemEntity> getSelectItems() {
		return selectList;
	}

	public ArrayList<TreeNode> getSelectNodeList() {
		return selectNodeList;
	}

	private SelectTree() {
	}

	public AndroidTreeView getAndroidTree() {
		return androidTree;
	}

	public static SelectTree init(Activity mActivity, DataLoader dataLoader, SelectConfig config, TreeNode rootNode,
			ViewGroup treeContainer) {
		SelectTree tree = new SelectTree();
		tree.mActivity = mActivity;
		tree.config = config;
		tree.dataLoader = dataLoader;
		tree.rootNode = rootNode;
		tree.initTree();
		treeContainer.addView(tree.androidTree.getView());
		return tree;
	}

	private boolean isCanSelectItem(ItemEntity value, boolean checkSize) throws Exception {
		if (selectList.size() >= config.SELECT_MAX && checkSize) {
			ToastUtils.show(mActivity, "最多只能选择" + config.SELECT_MAX + "个数据");
			return false;
		}
		if (value.getObjData() == null) {
			return false;
		}
		Class<?> valueType = value.getObjData().getClass();
		// 判断是否要自定义处理
		if (config.selectVerifier != null) {
			boolean needValify = false;
			for (SelectType type : config.selectVerifier.needValifyTypes()) {
				if (type instanceof GenericsType) {
					// 泛型验证
					GenericsType genericsType = (GenericsType) type;
					if (!genericsType.entityType.equals(valueType)) {
						continue;
					}
					Method method = genericsType.entityType.getMethod(genericsType.getGenericsEntityMethodName);
					if (method.invoke(value.getObjData()).getClass().equals(genericsType.genericsType)) {
						needValify = true;
						break;
					}
				} else {
					// 非泛型验证，直接验证类型
					if (valueType.equals(type.entityType)) {
						needValify = true;
						break;
					}
				}
			}
			if (needValify) {
				return config.selectVerifier.valify(value);
			}
		}
		// 框架自动处理
		for (SelectType type : config.SELECT_ITEM_TYPE) {
			if (type instanceof GenericsType) {
				// 泛型验证
				// 泛型验证
				GenericsType genericsType = (GenericsType) type;
				if (!genericsType.entityType.equals(valueType)) {
					continue;
				}
				Method method = genericsType.entityType.getMethod(genericsType.getGenericsEntityMethodName);
				if (method.invoke(value.getObjData()).getClass().equals(genericsType.genericsType)) {
					return true;
				}
			} else {
				// 非泛型验证，直接验证类型
				if (valueType.equals(type.entityType)) {
					return true;
				}
			}
		}
		return false;
	}

	// 选择框事件监听
	private BaseTreeNodeHolder.CheckBoxCheckListener checkBoxListener = new BaseTreeNodeHolder.CheckBoxCheckListener() {
		@Override
		public void onCheckedChanged(TreeNode node, CompoundButton buttonView, boolean isChecked) {
			if (customCheckBoxCheckListener != null) {
				if (customCheckBoxCheckListener.onCheckedChanged(node, buttonView, isChecked)) {
					return;
				}
			}
			try {
				// 判断是否是可以选择的类型
				boolean canSelect = isCanSelectItem((ItemEntity) node.getValue(), true);
				if (canSelect) {
					// 可以选择的类型
					if (isChecked) {
						// 选择框被选择了
						selectList.add((ItemEntity) node.getValue());
						selectNodeList.add(node);
					} else {
						// 选择框取消了
						selectList.remove((ItemEntity) node.getValue());
						selectNodeList.remove(node);
					}
				} else {
					// 判断下级节点是否为可选择的
					List<TreeNode> children = node.getChildren();
					if (null == children || children.isEmpty()) {
						// 不可以选择
						if (isChecked) {
							buttonView.setChecked(false);
						}
						return;
					}
					canSelect = isCanSelectItem((ItemEntity) children.get(0).getValue(), false);
					if (canSelect) {
						if (!node.isExpanded()) {
							androidTree.expandNode(node);
						}
						TreeUtil.setChildNodeCheckBox(node, isChecked, true, androidTree.getConfig());
					} else {
						// 不可以选择
						if (isChecked) {
							buttonView.setChecked(false);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public boolean isClick() {
			return false;
		}

		@Override
		public void setClick(boolean click) {

		}
	};

	// 初始化台账树
	private void initTree() {
		androidTree = new AndroidTreeView(mActivity);
		androidTree.setRoot(rootNode);
		androidTree.setCommonDataLoader(dataLoader);
		androidTree.setCommonCheckBoxListener(checkBoxListener);
	}

	private CustomCheckBoxCheckListener customCheckBoxCheckListener;

	public void setCustomCheckBoxCheckListener(CustomCheckBoxCheckListener customCheckBoxCheckListener) {
		this.customCheckBoxCheckListener = customCheckBoxCheckListener;
	}

	public interface CustomCheckBoxCheckListener {
		/**
		 * 如果返回值为true，则内置的checkBoxListener处理逻辑不生效
		 * @time 2017年9月13日 上午10:47:42
		 * @param node
		 * @param buttonView
		 * @param isChecked
		 * @return
		 */
		boolean onCheckedChanged(TreeNode node, CompoundButton buttonView, boolean isChecked);
	}

	/**
	 * 设置选项被选中事件
	 * @time 2017年9月13日 下午4:40:51
	 * @param commListener
	 */
	public void setCommonClickListener(CommonTreeNodeClickListener commListener) {
		commListener.setCustomDataLoader(null);
		androidTree.setCommonClickListener(commListener);
	}
}
