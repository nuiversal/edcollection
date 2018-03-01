package com.winway.android.ewidgets.tree.holder;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.core.TreeNode.TreeNodeClickListener;
import com.winway.android.ewidgets.tree.datacenter.DataLoader;
import com.winway.android.ewidgets.tree.datacenter.loaderimpl.DefaultDataLoaderImpl;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.utils.ImageURIUtil;

/**
 * @author mr-lao
 * @date 2016-12-28
 *
 */
public class CommonHolder extends BaseTreeNodeHolder<ItemEntity> {
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
		public int checkboxImageId = R.id.checkbox_image;
		public int imageId = R.id.image;
		public int imageECID = R.id.tree_close;
		public int imageEXID = R.id.tree_expend;
		public int customViewContainer = R.id.custom_view_container;
		public int margin = 200;
	}

	/**
	 * 获取默认的配置
	 * @return
	 */
	public static Config defaultConfig() {
		Config config = new Config();
		config.margin = 50;
		return config;
	}

	public CommonHolder(Context context, int layoutid) {
		super(context, layoutid);
		this.config = new Config();
		this.config.layoutRes = layoutid;
		init(context, this.config);
	}

	public CommonHolder(Context context, Config config) {
		super(context, config.layoutRes);
		init(context, config);
	}

	private Config config = null;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	private Context context;

	void init(Context context, Config config) {
		this.context = context;
		this.config = config;
	}

	@Override
	public View createNodeView(final TreeNode node, final ItemEntity value) {
		// LogUtil.i("getview", "node text :" + value.getText());
		try {
			View layoutView = View.inflate(context, config.layoutRes, null);
			// 设置显示文本内容
			final TextView textView = (TextView) layoutView.findViewById(config.textId);
			String text = value.getText();
			if (TextUtils.isEmpty(text)) {
				textView.setVisibility(View.GONE);
			} else {
				textView.setText(text);
			}
			// 设置选择框点击事件与选择框选择状态
			CheckBox checkBox = (CheckBox) layoutView.findViewById(config.checkboxId);
			final ImageView checkboxImage = (ImageView) layoutView.findViewById(R.id.checkbox_image);
			if (value.isShowCheckBox()) {
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked) {
							checkboxImage.setImageResource(R.drawable.ico_checkbox_s);
							// 设置文本颜色，默认是黑色
							String textColor = value.getTextColor();
							if (TextUtils.isEmpty(textColor)) {
								textView.setTextColor(Color.BLACK);
							} else {
								textView.setTextColor(Color.parseColor(textColor));
							}
						} else {
							checkboxImage.setImageResource(R.drawable.ico_checkbox);
							textView.setTextColor(Color.BLACK);
						}

						Object tag = buttonView.getTag();
						if (null != tag && !((boolean) tag)) {
							return;
						}
						if (null != getCheckBoxCheckListener()) {
							getCheckBoxCheckListener().onCheckedChanged(node, buttonView, isChecked);
						}
					}
				});
				checkBox.setChecked(value.isChecked());
			} else {
				if (value.getViewHidenType() == View.INVISIBLE) {
					// checkBox.setVisibility(View.INVISIBLE);
					layoutView.findViewById(R.id.checkbox_container).setVisibility(View.INVISIBLE);
				} else {
					// checkBox.setVisibility(View.GONE);
					layoutView.findViewById(R.id.checkbox_container).setVisibility(View.GONE);
				}
			}
			// 设置图片内容
			ImageView imageView = (ImageView) layoutView.findViewById(R.id.image);
			if (!value.isShowImage()) {
				if (value.getViewHidenType() == View.INVISIBLE) {
					imageView.setVisibility(View.INVISIBLE);
				} else {
					imageView.setVisibility(View.GONE);
				}
			} else if (null != value.getImageURI()) {
				imageView.setVisibility(View.VISIBLE);
				ImageURIUtil.loadImage(value.getImageURI(), context, imageView);
			}
			// 设置箭头
			ImageView imgEC = (ImageView) layoutView.findViewById(config.imageECID);
			ImageView imgEX = (ImageView) layoutView.findViewById(config.imageEXID);
			if (!value.isShowEX()) {
				// 箭头不显示
				imgEX.setVisibility(View.INVISIBLE);
				imgEC.setVisibility(View.GONE);
			} else {
				// 箭头显示
				if ((!value.isHasChild() && (node.getChildren() == null || node.getChildren().isEmpty()))) {
					imgEX.setVisibility(View.INVISIBLE);
					imgEC.setVisibility(View.GONE);
				} else {
					if (value.isEx()) {
						// 展开
						imgEC.setVisibility(View.GONE);
						imgEX.setVisibility(View.VISIBLE);
					} else {
						// 关闭
						imgEC.setVisibility(View.VISIBLE);
						imgEX.setVisibility(View.GONE);
					}
				}
			}
			// 设置用户自定义节点视图
			if (null != value.getCustomItemView()) {
				ViewGroup customCVC = (ViewGroup) layoutView.findViewById(config.customViewContainer);
				if (null != customCVC) {
					customCVC.addView(value.getCustomItemView());
				}
			}
			// 进行内容偏移
			View contentView = layoutView.findViewById(config.contentId);
			setMargin(contentView, node, config.margin);
			return layoutView;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 点击事件处理器，提供一些通用的点击处理业务实现
	 * @author mr-lao
	 *
	 */
	public static abstract class CommonTreeNodeClickListener implements TreeNodeClickListener {
		private Config config = null;

		@Deprecated
		public CommonTreeNodeClickListener(Config config) {
			if (null == config) {
				this.config = new Config();
			} else {
				this.config = config;
			}
		}

		/**
		 * 建议使用此方法
		 */
		public CommonTreeNodeClickListener() {

		}

		public void setConfig(Config config) {
			this.config = config;
		}

		private boolean needProcessCheckBox = false;

		public boolean isNeedProcessCheckBox() {
			return needProcessCheckBox;
		}

		public void setNeedProcessCheckBox(boolean needProcessCheckBox) {
			this.needProcessCheckBox = needProcessCheckBox;
		}

		private AndroidTreeView tree;

		public AndroidTreeView getTree() {
			return tree;
		}

		public void setTree(AndroidTreeView tree) {
			this.tree = tree;
		}

		/**默认数据加载器，如果此值不为空，则点击节点的时候会调用加载器加载数据*/
		private DefaultDataLoaderImpl defaultDataLoder;

		public DefaultDataLoaderImpl getDefaultDataLoder() {
			return defaultDataLoder;
		}

		public void setDefaultDataLoder(DefaultDataLoaderImpl defaultDataLoder) {
			this.defaultDataLoder = defaultDataLoder;
		}

		/**用户个人的数据加载器*/
		private DataLoader customDataLoader;

		public DataLoader getCustomDataLoader() {
			return customDataLoader;
		}

		public void setCustomDataLoader(DataLoader customDataLoader) {
			this.customDataLoader = customDataLoader;
		}

		/**
		 * 点击点击事件
		 * 点击事件执行一次，就会getView一次
		 * @param node
		 * @param value
		 */
		public final void onClick(TreeNode node, Object value) {
			ItemEntity itemEntity = (ItemEntity) value;
			// LogUtil.i("click", "node text :" + itemEntity.getText());
			View nodeView = node.getViewHolder().getView();
			ImageView imgEC = (ImageView) nodeView.findViewById(config.imageECID);
			ImageView imgEX = (ImageView) nodeView.findViewById(config.imageEXID);
			CheckBox checkBox = (CheckBox) nodeView.findViewById(config.checkboxId);
			// 点击处理箭头变化
			if (itemEntity.isShowEX()) {
				if (node.getChildren() != null && !node.getChildren().isEmpty()) {
					if (!node.isExpanded()) {
						// 展开
						imgEX.setVisibility(View.VISIBLE);
						imgEC.setVisibility(View.GONE);
						itemEntity.setEx(true);
					} else {
						// 关闭
						imgEC.setVisibility(View.VISIBLE);
						imgEX.setVisibility(View.GONE);
						itemEntity.setEx(false);
					}
				}
			}
			// 点击处理选择框是否被选中
			if (itemEntity.isShowCheckBox() && needProcessCheckBox) {
				checkBox.setTag(false);
				checkBox.setChecked(!node.isExpanded());
			}
			// 只有展开节点的时候才会加载数据，关闭节点的时候不能加载数据
			if (!node.isExpanded()) {
				boolean nodeEXC = false;
				// 默认的数据加载器，只有在客户数据加载器不存在的时候才会起作用
				if (null != defaultDataLoder && itemEntity.isHasChild() && customDataLoader == null) {
					if (itemEntity.getHasChildButNeddLoad() > 0 || node.getChildren() == null
							|| node.getChildren().isEmpty()) {
						defaultDataLoder.loadNode(tree, node, itemEntity);
						nodeEXC = true;
					}
				}
				// 用户数据加载器
				if (customDataLoader != null && itemEntity.isHasChild()) {
					if (itemEntity.getHasChildButNeddLoad() > 0 || node.getChildren() == null
							|| node.getChildren().isEmpty()) {
						customDataLoader.loadNode(tree, node, itemEntity);
						nodeEXC = true;
					}
				}
				// 展开节点
				if (nodeEXC && itemEntity.isShowEX()) {
					imgEX.setVisibility(View.VISIBLE);
					imgEC.setVisibility(View.GONE);
					itemEntity.setEx(true);
				}
			}
			onNodeClick(node, itemEntity);
		}

		public abstract void onNodeClick(TreeNode node, ItemEntity value);
	}
}
