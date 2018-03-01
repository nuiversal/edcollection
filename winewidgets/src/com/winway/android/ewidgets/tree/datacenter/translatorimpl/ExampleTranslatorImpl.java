package com.winway.android.ewidgets.tree.datacenter.translatorimpl;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.datacenter.DataTranslator;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.entity.LevelEntity;
import com.winway.android.ewidgets.tree.test.DataCustom;
import com.winway.android.ewidgets.tree.test.GetCustomListResult;

public class ExampleTranslatorImpl implements DataTranslator {

	@Override
	public LevelEntity translateToLevelEntity(Object data) {
		GetCustomListResult result = (GetCustomListResult) data;
		List<DataCustom> customs = result.getCustoms();
		if (customs == null || customs.isEmpty()) {
			return null;
		}
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> list = new ArrayList<>();
		for (DataCustom ctm : customs) {
			ItemEntity item = new ItemEntity();
			item.setText(ctm.getName());
			list.add(item);
		}
		level.setDatas(list);
		return level;
	}

}
