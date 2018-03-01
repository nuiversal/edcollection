package com.winway.android.ewidgets.tree.datacenter;

import com.winway.android.ewidgets.tree.entity.LevelEntity;

/**
 * 数据转化器
 * @author mr-lao
 *
 */
public interface DataTranslator {
	public LevelEntity translateToLevelEntity(Object data);
}
