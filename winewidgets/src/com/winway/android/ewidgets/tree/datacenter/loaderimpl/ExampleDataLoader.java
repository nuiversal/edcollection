package com.winway.android.ewidgets.tree.datacenter.loaderimpl;

import com.winway.android.ewidgets.net.NetManager;
import com.winway.android.ewidgets.net.service.BaseService;
import com.winway.android.ewidgets.net.service.BaseService.RequestCallBack;
import com.winway.android.ewidgets.net.service.BaseService.WayFrom;
import com.winway.android.ewidgets.tree.core.AndroidTreeView;
import com.winway.android.ewidgets.tree.core.TreeNode;
import com.winway.android.ewidgets.tree.datacenter.DataLoader;
import com.winway.android.ewidgets.tree.datacenter.DataTranslator;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.entity.LevelEntity;
import com.winway.android.ewidgets.tree.test.GetCustomListResult;
import com.winway.android.ewidgets.tree.utils.TreeUtil;

public class ExampleDataLoader implements DataLoader {

	@Override
	public void loadNode(final AndroidTreeView treeView, final TreeNode node, ItemEntity item) {
		// 获取在线服务 http://192.168.0.231:8901/mg?action=getcustomlist
		try {
			BaseService net = NetManager.getLineService();
			net.getRequest(item.getUrl(), null, null, GetCustomListResult.class,
					new RequestCallBack<GetCustomListResult>() {
						@Override
						public void error(int code, WayFrom from) {

						}

						@Override
						public void callBack(GetCustomListResult request, WayFrom from) {
							if (null == request) {
								return;
							}
							LevelEntity levelEntity = translator.translateToLevelEntity(request);
							if (null != levelEntity) {
								if (null != treeView) {
									treeView.addNode(node, TreeUtil.levelToNodeList(levelEntity));
								}
							}
						}

					});
		} catch (Exception e) {

		}
	}

	private DataTranslator translator;

	@Override
	public void setDataTranslator(DataTranslator t) {
		this.translator = t;
	}

	@Override
	public DataTranslator getDataTranslator() {
		return translator;
	}

}
