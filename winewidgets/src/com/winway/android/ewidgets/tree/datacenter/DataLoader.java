package com.winway.android.ewidgets.tree.datacenter;

import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.entity.ItemEntity;

/**
 * 数据加载器
 * @author mr-lao
 *
 */
public interface DataLoader {
	public void loadNode(AndroidTreeView treeView, TreeNode node, ItemEntity item);

	public void setDataTranslator(DataTranslator translator);

	public DataTranslator getDataTranslator();
}
