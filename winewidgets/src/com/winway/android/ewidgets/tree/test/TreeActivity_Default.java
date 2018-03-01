package com.winway.android.ewidgets.tree.test;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.datacenter.loaderimpl.DefaultDataLoaderImpl;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.holder.CommonHolder;
import com.winway.android.ewidgets.tree.utils.ImageURIUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class TreeActivity_Default extends Activity {
	TreeNode rootNode = TreeNode.root();
	AndroidTreeView treeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tree);
		initRoot();
		treeView = new AndroidTreeView(this);
		CommonHolder.Config config = new CommonHolder.Config();
		config.layoutRes = R.layout.dlksh_tree_item;
		CommonHolder.CommonTreeNodeClickListener commListener = new CommonHolder.CommonTreeNodeClickListener(
				config) {
			@Override
			public void onNodeClick(TreeNode node, ItemEntity value) {

			}
		};
		commListener.setDefaultDataLoder(new DefaultDataLoaderImpl());
		commListener.setTree(treeView);
		treeView.setCommonViewHolder(CommonHolder.class, config, null, commListener);
		treeView.setRoot(rootNode);
		LinearLayout content = (LinearLayout) findViewById(R.id.tree);
		content.addView(treeView.getView());
	}

	void initRoot() {
		ItemEntity ri = new ItemEntity();
		ri.setText("江门供电局");
		ri.setShowCheckBox(true);
		ri.setImageURI(ImageURIUtil.createImageURI("" + R.drawable.ico_go, 1));
		ri.setHasChild(true);
		TreeNode node = new TreeNode(ri);
		rootNode.addChild(node);
	}
}
