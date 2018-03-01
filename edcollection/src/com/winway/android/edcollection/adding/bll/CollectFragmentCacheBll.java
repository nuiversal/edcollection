package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;

import com.winway.android.edcollection.adding.adapter.FeaturePointAdapter;
import com.winway.android.edcollection.base.entity.ResourceEnum;

import android.widget.ListView;

/**
 * 采集片段缓存
 * 
 * @author zgq
 *
 */
public class CollectFragmentCacheBll {
	private static CollectFragmentCacheBll instance;

	public static CollectFragmentCacheBll getInstance() {
		if (instance == null) {
			synchronized (CollectFragmentCacheBll.class) {
				if (instance == null) {
					instance = new CollectFragmentCacheBll();
				}
			}
		}
		return instance;
	}

	/**
	 * 设置节点设备列表选中
	 * 
	 * @param nodeDeviceType
	 * @param listView
	 */
	public void setNodeDevice(ResourceEnum nodeDeviceType, ListView listView) {

		FeaturePointAdapter featurePointAdapter = (FeaturePointAdapter) listView.getAdapter();
		ArrayList<ResourceEnum> nodeDeviceList = featurePointAdapter.getDataList();
		for (int i = 0; i < nodeDeviceList.size(); i++) {
			if (nodeDeviceType.equals(ResourceEnum.BDZ)) {
				if (nodeDeviceList.get(i).equals(ResourceEnum.BDZ)) {
					featurePointAdapter.setChecked(i, true);
					featurePointAdapter.notifyDataSetChanged();
					break;
				}
			} else if (nodeDeviceType.equals(ResourceEnum.XSBDZ)) {
				if (nodeDeviceList.get(i).equals(ResourceEnum.XSBDZ)) {
					featurePointAdapter.setChecked(i, true);
					featurePointAdapter.notifyDataSetChanged();
					break;
				}
			} else if (nodeDeviceType.equals(ResourceEnum.KGZ)) {
				if (nodeDeviceList.get(i).equals(ResourceEnum.KGZ)) {
					featurePointAdapter.setChecked(i, true);
					featurePointAdapter.notifyDataSetChanged();
					break;
				}
			} else if (nodeDeviceType.equals(ResourceEnum.BYQ)) {
				if (nodeDeviceList.get(i).equals(ResourceEnum.BYQ)) {
					featurePointAdapter.setChecked(i, true);
					featurePointAdapter.notifyDataSetChanged();
					break;
				}
			} else if (nodeDeviceType.equals(ResourceEnum.DLJ)) {
				if (nodeDeviceList.get(i).equals(ResourceEnum.DLJ)) {
					featurePointAdapter.setChecked(i, true);
					featurePointAdapter.notifyDataSetChanged();
					break;
				}
			} else if (nodeDeviceType.equals(ResourceEnum.GT)) {
				if (nodeDeviceList.get(i).equals(ResourceEnum.GT)) {
					featurePointAdapter.setChecked(i, true);
					featurePointAdapter.notifyDataSetChanged();
					break;
				}
			} else if (nodeDeviceType.equals(ResourceEnum.DYPDX)) {
				if (nodeDeviceList.get(i).equals(ResourceEnum.DYPDX)) {
					featurePointAdapter.setChecked(i, true);
					featurePointAdapter.notifyDataSetChanged();
					break;
				}
			} else if (nodeDeviceType.equals(ResourceEnum.PDS)) {
				if (nodeDeviceList.get(i).equals(ResourceEnum.PDS)) {
					featurePointAdapter.setChecked(i, true);
					featurePointAdapter.notifyDataSetChanged();
					break;
				}
			}

		}

	}
}
