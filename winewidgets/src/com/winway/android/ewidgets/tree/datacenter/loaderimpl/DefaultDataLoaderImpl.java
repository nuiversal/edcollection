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
import com.winway.android.ewidgets.tree.utils.TreeUtil;

/**
 * 默认的数据加载器
 * 使用条件：接口url返回的数据符合<code>entity.LevelEntity</code>
 * @author mr-lao
 *
 */
public class DefaultDataLoaderImpl implements DataLoader {
	public void loadNode(final AndroidTreeView treeView,final TreeNode node, ItemEntity item) {
		try {
			// 数据包优先
			BaseService service = NetManager.getTreeLoderService(NetManager.DATA_PACKET_FIRST);
			service.getRequest(item.getUrl(), null, null, LevelEntity.class,
					new RequestCallBack<LevelEntity>() {
						@Override
						public void error(int code,WayFrom from) {

						}

						@Override
						public void callBack(LevelEntity request,WayFrom from) {
							if (null == request) {
								return;
							}
							if (treeView != null) {
								// tree.expandNode(node);
								treeView.addNode(node, TreeUtil.levelToNodeList(request));
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setDataTranslator(DataTranslator translator) {
		// TODO Auto-generated method stub

	}

	@Override
	public DataTranslator getDataTranslator() {
		return null;
	}

}
