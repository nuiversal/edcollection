package com.winway.android.ewidgets.tree.core;

import java.io.IOException;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.net.NetManager;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.ewidgets.net.service.BaseService.WayFrom;
import com.winway.android.ewidgets.tree.bll.DefaultNodeContentMSG;
import com.winway.android.ewidgets.tree.core.TreeNode.TreeNodeClickListener;
import com.winway.android.ewidgets.tree.holder.BaseTreeNodeHolder;
import com.winway.android.ewidgets.tree.utils.ImageURIUtil;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author mr-lao
 * @date 2016-12-15
 *
 */
@Deprecated
public class DefaultTreeNodeHolder extends BaseTreeNodeHolder<DefaultNodeContentMSG> implements TreeNodeClickListener {
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

	public DefaultTreeNodeHolder(Context context, int layoutid, AndroidTreeView tree) {
		super(context, layoutid);
		init(context, layoutid, tree);
	}

	public DefaultTreeNodeHolder(Context context, AndroidTreeView tree) {
		super(context, R.layout.dlksh_tree_item);
		init(context, R.layout.dlksh_tree_item, tree);
	}

	void init(Context context, int layoutid, AndroidTreeView tree) {
		tree.setDefaultNodeClickListener(this);
		config = new Config();
		config.layoutRes = layoutid;
	}

	@Override
	public View createNodeView(final TreeNode node, DefaultNodeContentMSG value) {
		View layoutView = View.inflate(getContext(), config.layoutRes, null);
		// 设置显示文本内容
		TextView text = (TextView) layoutView.findViewById(config.textId);
		if (null != value.getText()) {
			text.setText(value.getText());
		} else {
			text.setText("" + value);
		}
		// 设置选择框点击事件与选择框选择状态
		CheckBox checkBox = (CheckBox) layoutView.findViewById(R.id.checkbox);
		if (isHasCheckBox()) {
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (null != getCheckBoxCheckListener()) {
						getCheckBoxCheckListener().onCheckedChanged(node, buttonView, isChecked);
					}
				}
			});
			checkBox.setChecked(value.isChecked());
		} else {
			checkBox.setVisibility(View.GONE);
		}
		// 设置图片内容
		if (null != value.getImageURI()) {
			ImageView imageView = (ImageView) layoutView.findViewById(R.id.image);
			try {
				ImageURIUtil.loadImage(value.getImageURI(), getContext(), imageView);
			} catch (IOException e) {
				e.printStackTrace();
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
			DefaultNodeContentMSG msg = (DefaultNodeContentMSG) value;
			if (!TextUtils.isEmpty(msg.getUrl())) {
				if (node.getChildren() == null || node.getChildren().isEmpty()) {
					// 加载子级
					BaseService service = NetManager.getService(getContext(), NetManager.ODER_DATA_LOCAL_LINE, false);
					try {
						service.getRequestString(msg.getUrl(), msg.getParams(), msg.getHeaders(),
								new RequestCallBack<String>() {
									@Override
									public void error(int code, WayFrom from) {

									}

									@Override
									public void callBack(String request, WayFrom from) {
										// 数据集合

									}

								});
					} catch (Exception e) {

						e.printStackTrace();
					}
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

	public interface TreeNodeClickListener {
		void onClick(TreeNode node, Object value);
	}
}
