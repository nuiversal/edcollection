package com.winway.android.ewidgets.tree.utils;

import java.util.ArrayList;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.entity.LevelEntity;
import com.winway.android.ewidgets.tree.holder.BaseTreeNodeHolder;
import com.winway.android.ewidgets.tree.holder.CommonHolder.Config;

public class TreeUtil {
	/**
	 * 将节点的选择框选上或者取消，包括它的下一级节点（此方法与BaseTreeViewHolder绑定使用）
	 * @param node
	 * @param isChecked
	 */
	public static void selectOrNotNodeInclueChild(TreeNode node, boolean isChecked) {
		((CheckBox) node.getViewHolder().getView().findViewById(R.id.checkbox)).setChecked(isChecked);
		node.getViewHolder().getView().findViewById(R.id.tree_close).setVisibility(View.GONE);
		node.getViewHolder().getView().findViewById(R.id.tree_expend).setVisibility(View.VISIBLE);
		if (node.getChildren() != null) {
			for (TreeNode n : node.getChildren()) {
				BaseTreeNodeHolder.CheckBoxCheckListener l = ((BaseTreeNodeHolder<?>) node.getViewHolder())
						.getCheckBoxCheckListener();
				l.setClick(false);
				((CheckBox) n.getViewHolder().getView().findViewById(R.id.checkbox)).setChecked(isChecked);
				l.setClick(true);
			}
		}
	}

	/**
	 * 设置节点的子节点选择框选中状态
	 * @time 2017年9月13日 下午3:24:38
	 * @param node    要操作的节点
	 * @param isChecked    选中状态，true表示选中，false表示取消
	 * @param cheboxListener    true表示要触发节点的选择框事件，false表示不需要触发节点选择框事件
	 * @param config   配置对象，生成台账树的时候创建的那个config对象
	 * @example
	 * {
	 * 		AndroidTree tree;
	 * 		CheckBoxListener cbl = new CheckBoxListener(){
	 * 			public void 选择框状态改变方法(){
	 * 				do-something;
	 * 			}
	 * 		}
	 *      tree.选择框事件 = cbl;
	 * 		TreeUtil.setNodeCheckBox(towernode,true,true,tree.config);
	 * }
	 * 此段代码，参数cheboxListener=true,则cbl中的方法会被触发，如果cheboxListener=false则不会触发cbl监听器
	 */
	public static void setChildNodeCheckBox(TreeNode node, boolean isChecked, boolean cheboxListener, Config config) {
		if (node.getChildren() != null) {
			for (TreeNode n : node.getChildren()) {
				CheckBox checkBox = (CheckBox) n.getViewHolder().getView().findViewById(config.checkboxId);
				checkBox.setTag(cheboxListener);
				checkBox.setChecked(isChecked);
				// 复位
				checkBox.setTag(true);
			}
		}
	}

	public static void addLevelToNode(TreeNode node, LevelEntity levelEntity) {
		node.addChildren(levelToNodeList(levelEntity));
	}

	public static ArrayList<TreeNode> levelToNodeList(LevelEntity levelEntity) {
		ArrayList<ItemEntity> datas = levelEntity.getDatas();
		ArrayList<TreeNode> nodeList = new ArrayList<>();
		for (ItemEntity item : datas) {
			TreeNode treeNode = new TreeNode(item);
			nodeList.add(treeNode);
		}
		return nodeList;
	}

	/**
	 * 设置节点的选择框选中状态
	 * @time 2017年9月13日 下午3:24:38
	 * @param node    要操作的节点
	 * @param isChecked    选中状态，true表示选中，false表示取消
	 * @param cheboxListener    true表示要触发节点的选择框事件，false表示不需要触发节点选择框事件
	 * @param config   配置对象，生成台账树的时候创建的那个config对象
	 * @example
	 * {
	 * 		AndroidTree tree;
	 * 		CheckBoxListener cbl = new CheckBoxListener(){
	 * 			public void 选择框状态改变方法(){
	 * 				do-something;
	 * 			}
	 * 		}
	 *      tree.选择框事件 = cbl;
	 * 		TreeUtil.setNodeCheckBox(towernode,true,true,tree.config);
	 * }
	 * 此段代码，参数cheboxListener=true,则cbl中的方法会被触发，如果cheboxListener=false则不会触发cbl监听器
	 */
	public static void setNodeCheckBox(TreeNode node, boolean isChecked, boolean cheboxListener, Config config) {
		CheckBox checkBox = (CheckBox) node.getViewHolder().getView().findViewById(config.checkboxId);
		checkBox.setTag(cheboxListener);
		checkBox.setChecked(isChecked);
		// 复位
		checkBox.setTag(true);
	}

	/**
	 * 判断节点的选择框是否被选中
	 * @param node    要操作的节点
	 * @param config  配置对象，生成台账树的时候创建的那个config对象
	 * @return
	 */
	public static boolean nodeIsChecked(TreeNode node, Config config) {
		CheckBox checkBox = (CheckBox) node.getViewHolder().getView().findViewById(config.checkboxId);
		return checkBox.isChecked();
	}

	/**
	 * 设置节点的图标
	 * @time 2017年9月13日 下午3:36:27
	 * @param node   要设置的节点
	 * @param config  配置对象，生成台账树的时候创建的那个config对象
	 * @param drawableId   图标
	 */
	public static void setNodeImage(TreeNode node, Config config, int drawableId) {
		if (node.getViewHolder() != null && node.getViewHolder().getView() != null) {
			ImageView imgage = (ImageView) node.getViewHolder().getView().findViewById(config.imageId);
			imgage.setImageResource(drawableId);
		} else {
			ItemEntity item = (ItemEntity) node.getValue();
			item.setImageURI(ImageURIUtil.createDrawableImageURI(drawableId));
		}
	}

	/**
	 * 设置节点的图标
	 * @time 2017年9月13日 下午3:36:27
	 * @param node   要设置的节点
	 * @param config 配置对象，生成台账树的时候创建的那个config对象
	 * @param text   文字内容
	 */
	public static void setNodeText(TreeNode node, Config config, String text) {
		if (node.getViewHolder() != null && node.getViewHolder().getView() != null) {
			TextView textview = (TextView) node.getViewHolder().getView().findViewById(config.textId);
			textview.setText(text);
		} else {
			ItemEntity item = (ItemEntity) node.getValue();
			item.setText(text);
		}
	}

	/**
	 * 隐藏图标
	 * @time 2017年9月13日 下午6:30:18
	 * @param node
	 * @param config
	 */
	public static void hidenNodeImage(TreeNode node, Config config) {
		ImageView image = (ImageView) node.getViewHolder().getView().findViewById(config.imageId);
		ItemEntity item = (ItemEntity) node.getValue();
		if (item.getViewHidenType() == View.GONE) {
			image.setVisibility(View.GONE);
		} else {
			image.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 隐藏CheBox
	 * @time 2017年9月13日 下午6:36:24
	 * @param node
	 * @param config
	 */
	public static void hidenNodeCheckBox(TreeNode node, Config config) {
		CheckBox checkbox = (CheckBox) node.getViewHolder().getView().findViewById(config.checkboxId);
		ItemEntity item = (ItemEntity) node.getValue();
		if (item.getViewHidenType() == View.GONE) {
			checkbox.setVisibility(View.GONE);
		} else {
			checkbox.setVisibility(View.INVISIBLE);
		}
		ImageView checkImage = (ImageView) node.getViewHolder().getView().findViewById(config.checkboxImageId);
		if (null != checkImage) {
			checkImage.setVisibility(View.GONE);
		}
	}
}
