package com.winway.android.ewidgets.tree.test;

import java.util.ArrayList;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.entity.LevelEntity;
import com.winway.android.ewidgets.tree.holder.BaseTreeNodeHolder;
import com.winway.android.ewidgets.tree.holder.BaseTreeNodeHolder.CheckBoxCheckListener;
import com.winway.android.ewidgets.tree.utils.ImageURIUtil;
import com.winway.android.ewidgets.tree.holder.CommonHolder;
import com.winway.android.util.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class TreeActivity2 extends Activity {
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

		// commListener.setAndroidTreeView(treeView);

		CheckBoxCheckListener checkBoxCheckListener = new CheckBoxCheckListener() {
			private boolean cl = true;

			@Override
			public void onCheckedChanged(TreeNode node, CompoundButton buttonView,
					boolean isChecked) {
				if (!isClick()) {
					return;
				}
				// 展开下一级机构
				// treeView.expandNode(node);
				// BaseTreeNodeHolder.Utils.selectOrNotNodeInclueChild(node,
				// isChecked);
				ToastUtil.showShort(getApplicationContext(), "checked : " + isChecked);
			}

			@Override
			public boolean isClick() {
				// TODO Auto-generated method stub
				return cl;
			}

			@Override
			public void setClick(boolean click) {
				// TODO Auto-generated method stub
				cl = click;
			}
		};
		treeView.setCommonViewHolder(CommonHolder.class, config, checkBoxCheckListener,
				commListener);

		/**treeView.setBaseViewHolder(BaseTreeNodeHolder.class, R.layout.dlksh_tree_item, null);*/
		treeView.setRoot(rootNode);
		LinearLayout content = (LinearLayout) findViewById(R.id.tree);
		content.addView(treeView.getView());
	}

	void initRoot() {
		ItemEntity ri = new ItemEntity();
		ri.setText("江门供电局");
		ri.setShowCheckBox(true);
		ri.setImageURI(ImageURIUtil.createImageURI("" + R.drawable.ico_go, 1));
		ri.setEx(true);
		TreeNode node = new TreeNode(ri);

		rootNode.addChild(node);

		LevelEntity levelEntity = new LevelEntity();
		levelEntity.setDefaultUrl("http://localhost/default-url");
		ArrayList<ItemEntity> items = new ArrayList<>();
		ItemEntity i1 = new ItemEntity();
		i1.setText("新会供电局");
		i1.setChecked(false);
		i1.setImageURI(ImageURIUtil.createImageURI("" + R.drawable.ico_go, 1));
		i1.setChecked(true);
		items.add(i1);
		node.addChild(new TreeNode(i1));
		node.addChild(new TreeNode(i1));
		node.addChild(new TreeNode(i1));
		TreeNode n = new TreeNode(i1);
		node.addChild(n);
		ItemEntity i2 = new ItemEntity();
		i2.setText("电缆一班");
		i2.setChecked(false);
		i2.setImageURI(ImageURIUtil.createImageURI("" + R.drawable.ico_go, 1));
		i2.setChecked(true);
		TreeNode child = new TreeNode(i2);
		n.addChild(child);

	}
}
