package com.winway.android.ewidgets.tree.select;

import com.winway.android.ewidgets.tree.entity.ItemEntity;

public class SelectConfig {
	/**
	 * 最大选择数，默认是10000个最大数
	 */
	public int SELECT_MAX = 10000;
	/**
	 * 最小选择数
	 */
	public int SELECT_MIN;

	/**
	 * 选择类型
	 */
	public SelectType[] SELECT_ITEM_TYPE;

	/**
	 * 验证器
	 * @author mr-lao
	 *
	 */
	public interface SelectVerifier {
		/**
		 * 需要验证的类型（比如，在台账树中，父级线路的节点和子级线路的节点类型是一样的，这时候根据<code>SELECT_ITEM_TYPE</code>这个字段就无法满足需求）
		 * @return
		 */
		public SelectType[] needValifyTypes();

		/**
		 * 手动验证
		 * @param type
		 * @return
		 */
		public boolean valify(ItemEntity item);
	}

	/**
	 * 验证器
	 */
	public SelectVerifier selectVerifier;

	public SelectConfig(Class<?>... SELECT_ITEM_TYPE) {
		this.SELECT_ITEM_TYPE = new SelectType[SELECT_ITEM_TYPE.length];
		for (int i = 0; i < SELECT_ITEM_TYPE.length; i++) {
			Class<?> type = SELECT_ITEM_TYPE[i];
			this.SELECT_ITEM_TYPE[i] = new SelectType(type);
		}
	}

	public SelectConfig(SelectType... SELECT_ITEM_TYPE) {
		this.SELECT_ITEM_TYPE = SELECT_ITEM_TYPE;
	}
}
