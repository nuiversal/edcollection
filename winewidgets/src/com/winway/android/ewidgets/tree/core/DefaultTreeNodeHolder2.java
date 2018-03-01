package com.winway.android.ewidgets.tree.core;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.tree.core.TreeNode.TreeNodeClickListener;
import com.winway.android.ewidgets.tree.holder.BaseTreeNodeHolder;
import com.winway.android.ewidgets.tree.utils.ImageURIUtil;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 泛型E的实体，要求必须提供getText()方法
 * 可选getUrl()方法、getImageURI()方法、isChecked()方法
 * @author mr-lao
 * @date 2016-12-15
 *
 */
@Deprecated
public class DefaultTreeNodeHolder2 extends BaseTreeNodeHolder<Object>
		implements TreeNodeClickListener {
	/**
	 * 设置
	 * @author mr-lao
	 *
	 */
	public static class Config {
		public int layoutRes = R.layout.dlksh_tree_item;
		public int contentId = R.id.item_content;
		public int textId = R.id.text;
		public int checkboxId = R.id.checkbox;
		public int imageId = R.id.image;
	}

	private Config config = null;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public DefaultTreeNodeHolder2(Context context, int layoutid, AndroidTreeView tree) {
		super(context, layoutid);
		init(context, layoutid, tree);
	}

	public DefaultTreeNodeHolder2(Context context, AndroidTreeView tree) {
		super(context, R.layout.dlksh_tree_item);
		init(context, R.layout.dlksh_tree_item, tree);
	}

	void init(Context context, int layoutid, AndroidTreeView tree) {
		tree.setDefaultNodeClickListener(this);
		config = new Config();
		config.layoutRes = layoutid;
	}

	@Override
	public View createNodeView(final TreeNode node, Object value) {
		try {
			View layoutView = View.inflate(getContext(), config.layoutRes, null);
			// 设置显示文本内容
			TextView textView = (TextView) layoutView.findViewById(config.textId);
			Method getText = value.getClass().getMethod("getText");
			String text = null;
			if (null == getText) {
				text = value.toString();
			} else {
				Object invoke = getText.invoke(value);
				text = null == invoke ? "" : (String) invoke;
			}
			textView.setText(text);
			// 设置选择框点击事件与选择框选择状态
			CheckBox checkBox = (CheckBox) layoutView.findViewById(R.id.checkbox);
			if (isHasCheckBox()) {
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (null != getCheckBoxCheckListener()) {
							getCheckBoxCheckListener().onCheckedChanged(node, buttonView,
									isChecked);
						}
					}
				});
				Method isChecked = value.getClass().getMethod("isChecked");
				if (null != isChecked) {
					checkBox.setChecked((boolean) isChecked.invoke(value));
				}
			} else {
				checkBox.setVisibility(View.GONE);
			}
			// 设置图片内容
			Method getImageURI = value.getClass().getMethod("getImageURI");
			if (null != getImageURI) {
				Object invoke = getImageURI.invoke(value);
				if (invoke != null) {
					ImageView imageView = (ImageView) layoutView.findViewById(R.id.image);
					try {
						ImageURIUtil.loadImage((String) invoke, getContext(), imageView);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// 设置箭头
			ImageView imgEC = (ImageView) layoutView.findViewById(R.id.tree_close);
			ImageView imgEX = (ImageView) layoutView.findViewById(R.id.tree_expend);
			if (!isHasECXImage()) {
				imgEX.setVisibility(View.GONE);
				imgEC.setVisibility(View.GONE);
			}
			// 进行内容偏移
			View contentView = layoutView.findViewById(config.contentId);
			setMargin(contentView, node, getMargin());
			return layoutView;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private TreeNodeClickListener treeNodeClickListener = null;

	public void setTreeNodeClickListener(TreeNodeClickListener treeNodeClickListener) {
		this.treeNodeClickListener = treeNodeClickListener;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(TreeNode node, Object value) {
		View nodeView = node.getViewHolder().getNodeView();
		ImageView imgEC = (ImageView) nodeView.findViewById(R.id.tree_close);
		ImageView imgEX = (ImageView) nodeView.findViewById(R.id.tree_expend);
		CheckBox checkBox = (CheckBox) nodeView.findViewById(R.id.checkbox);
		if (!node.isExpanded()) {
			// 展开
			if (isHasECXImage()) {
				imgEX.setVisibility(View.VISIBLE);
				imgEC.setVisibility(View.GONE);
			}
			if (isHasCheckBox()) {
				checkBox.setChecked(true);
			}
			if (node.getChildren() == null || node.getChildren().isEmpty()) {
				// 可能有子类
				try {
					Method getUrl = value.getClass().getMethod("getUrl");
					Object url = getUrl.invoke(value);
					if (url != null) {
						Method getParams = value.getClass().getMethod("getParams");
						Method getHeaders = value.getClass().getMethod("getHeaders");
						URLNodeLoader urlNodeLoader = node.getUrlNodeLoader();
						if (urlNodeLoader != null) {
							Object getP = getParams.invoke(value);
							Object getH = getHeaders.invoke(value);
							urlNodeLoader.loadNode((String) url,
									null == getP ? null : (Map<String, String>) getP,
									null == getH ? null : (Map<String, String>) getH, node);
						} else {

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			// 关闭
			if (isHasECXImage()) {
				imgEC.setVisibility(View.VISIBLE);
				imgEX.setVisibility(View.GONE);
			}
			if (isHasCheckBox()) {
				checkBox.setChecked(false);
			}
		}
		if (null != treeNodeClickListener) {
			treeNodeClickListener.onClick(node, value);
		}
	}

	public interface URLNodeLoader {
		void loadNode(String url, Map<String, String> params, Map<String, String> headers,
				TreeNode node);
	}

	public interface TreeNodeClickListener {
		void onClick(TreeNode node, Object value);
	}
}
