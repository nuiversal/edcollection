package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.datacenter.entity.PageDataResult;
import com.winway.android.edcollection.datacenter.entity.edp.OrgResult;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.utils.ImageURIUtil;

import android.text.TextUtils;

public class TreeDataUtli {

	public static ItemEntity orgToItem(OrgResult org) {
		ItemEntity item = new ItemEntity();
		item.setHasChild(true);
		item.setShowImage(true);
		item.setText(org.getOrgname() + "");
		item.setObjData(org);
		item.setHasChildButNeddLoad(1);
		return item;
	}

	public static TreeNode createTreeNode(OrgResult org) {
		ItemEntity item = orgToItem(org);
		TreeNode node = new TreeNode(item);
		orgTreeNodeList.add(node);
		return node;
	}

	public static ArrayList<TreeNode> orgTreeNodeList = new ArrayList<>();

	public static void addOrgdataToTreeNode(OrgResult org, TreeNode node) {
		TreeNode nodeChild = createTreeNode(org);
		node.addChild(nodeChild);
		List<OrgResult> list = org.getList();
		if (list == null || list.isEmpty()) {
			return;
		}
		for (OrgResult oo : list) {
			addOrgdataToTreeNode(oo, nodeChild);
		}
	}

	public static void addPageOrgdataToTreeNode(PageDataResult<OrgResult> data, TreeNode node) {
		// 清除机构缓存
		orgTreeNodeList.clear();
		List<OrgResult> rows = data.getRows();
		if (rows == null || rows.isEmpty()) {
			return;
		}
		for (OrgResult org : rows) {
			addOrgdataToTreeNode(org, node);
		}
	}

	public static ItemEntity lintToItem(EcLineEntity line) {
		ItemEntity item = new ItemEntity();
		item.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.dl16));
		item.setHasChild(true);
		item.setText(line.getName());
		item.setObjData(line);
		return item;
	}

	public static TreeNode createTreeNode(EcLineEntity line) {
		ItemEntity item = lintToItem(line);
		TreeNode node = new TreeNode(item);
		return node;
	}

	public static void addLineToTreeNode(TreeNode node, EcLineEntity line, List<EcLineEntity> data) {
		if (data != null && !data.isEmpty()) {
			ArrayList<EcLineEntity> childLineList = new ArrayList<EcLineEntity>();
			ArrayList<EcLineEntity> childsChildLineList = new ArrayList<EcLineEntity>();
			// 找下级线路
			for (EcLineEntity el : data) {
				if (line.getEcLineId().equals(el.getParentNo())) {
					childLineList.add(el);
				} else {
					childsChildLineList.add(el);
				}
			}
			// 添加下级线路
			for (EcLineEntity el : childLineList) {
				TreeNode childNode = createTreeNode(el);
				node.addChild(childNode);
				addLineToTreeNode(childNode, el, childsChildLineList);
			}
		}
	}

	public static List<TreeNode> linesToTreeNodeList(List<EcLineEntity> data) {
		if (data == null || data.isEmpty()) {
			return null;
		}
		// 根线路
		ArrayList<EcLineEntity> rootList = new ArrayList<EcLineEntity>();
		ArrayList<EcLineEntity> childList = new ArrayList<EcLineEntity>();
		for (EcLineEntity line : data) {
			if (TextUtils.isEmpty(line.getParentNo())) {
				rootList.add(line);
			} else {
				childList.add(line);
			}
		}
		ArrayList<TreeNode> nodeList = new ArrayList<TreeNode>();
		for (EcLineEntity line : rootList) {
			TreeNode node = createTreeNode(line);
			addLineToTreeNode(node, line, childList);
			nodeList.add(node);
		}
		return nodeList;
	}
}
