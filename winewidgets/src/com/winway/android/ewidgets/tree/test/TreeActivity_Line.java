package com.winway.android.ewidgets.tree.test;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.datacenter.loaderimpl.ExampleDataLoader;
import com.winway.android.ewidgets.tree.datacenter.translatorimpl.ExampleTranslatorImpl;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.holder.CommonHolder;
import com.winway.android.ewidgets.tree.utils.ImageURIUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class TreeActivity_Line extends Activity {
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

		/**设置数据加载器*/
		ExampleDataLoader exampleDataLoader = new ExampleDataLoader();
		ExampleTranslatorImpl translatorImpl = new ExampleTranslatorImpl();
		exampleDataLoader.setDataTranslator(translatorImpl);

		commListener.setTree(treeView);
		commListener.setCustomDataLoader(exampleDataLoader);
		commListener.setNeedProcessCheckBox(true);

		treeView.setCommonViewHolder(CommonHolder.class, config, null, commListener);
		treeView.setRoot(rootNode);
		LinearLayout content = (LinearLayout) findViewById(R.id.tree);
		content.addView(treeView.getView());
	}

	void initRoot() {
		ItemEntity ri = new ItemEntity();
		ri.setText("所有云平台客户列表");
		ri.setShowCheckBox(true);
		ri.setImageURI(ImageURIUtil.createImageURI("" + R.drawable.ico_go, 1));
		ri.setHasChild(true);
		ri.setUrl("http://192.168.0.231:8901/mg?action=getcustomlist");
		TreeNode node = new TreeNode(ri);
		rootNode.addChild(node);
	}
}
