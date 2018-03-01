package com.winway.android.edcollection.adding.bll;

import java.util.List;

import com.winway.android.edcollection.adding.entity.DeviceDesc;
import com.winway.android.edcollection.adding.entity.InstallPosition;
import com.winway.android.edcollection.adding.entity.LineNodeSort;
import com.winway.android.edcollection.adding.entity.NodeRemark;
import com.winway.android.edcollection.adding.entity.UpMarkerCache;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;

import android.content.Context;

/**
 * 采集中缓存业务
 * 
 * @author zgq
 *
 */
public class DataCacheBll extends BaseBll<Object> {

	public DataCacheBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 保存安装位置
	 * 
	 * @return
	 */
	public void saveInstallPosition(String position) {
		if (position.isEmpty()) {
			return;
		}
		InstallPosition installPosition = new InstallPosition();
		installPosition.setName(position);
		List<InstallPosition> installPositions = getDataCache(InstallPosition.class);
		// 判断是否存在数据
		if (installPositions != null && installPositions.size() > 0) {// 最新添加的与上次相同跳过
			if (installPositions.get(0).getName().equals(position)) {
				return;
			}
			// 查找以前是否存在
			for (int i = 1; i < installPositions.size(); i++) {
				if (installPositions.get(i).getName().equals(position)) {
					this.deleteById(InstallPosition.class, installPositions.get(i).getId());// 存在删除
					this.save(installPosition);
					return;
				}
			}
			if (installPositions.size() >= 5) {// 数据大于等于5条就删除最后一条
				this.deleteById(InstallPosition.class, installPositions.get(installPositions.size() - 1).getId());
			}
			this.save(installPosition);
		} else {// 没有直接保存
			this.save(installPosition);
		}
	}

	/**
	 * 保存设备描述
	 * 
	 * @return
	 */
	public void saveDeviceDesc(String position) {
		if (position.isEmpty()) {
			return;
		}
		DeviceDesc deviceDesc = new DeviceDesc();
		deviceDesc.setName(position);
		List<DeviceDesc> deviceDescs = getDataCache(DeviceDesc.class);
		// 判断是否存在数据
		if (deviceDescs != null && deviceDescs.size() > 0) {// 最新添加的与上次相同跳过
			if (deviceDescs.get(0).getName().equals(position)) {
				return;
			}
			// 查找以前是否存在
			for (int i = 1; i < deviceDescs.size(); i++) {
				if (deviceDescs.get(i).getName().equals(position)) {
					this.deleteById(DeviceDesc.class, deviceDescs.get(i).getId());// 存在删除
					this.save(deviceDesc);
					return;
				}
			}
			if (deviceDescs.size() >= 5) {// 数据大于等于5条就删除最后一条
				this.deleteById(InstallPosition.class, deviceDescs.get(deviceDescs.size() - 1).getId());
			}
			this.save(deviceDesc);
		} else {// 没有直接保存
			this.save(deviceDesc);
		}
	}

	/**
	 * 保存其他（备注）
	 * 
	 * @return
	 */
	public void saveNodeRemark(String position) {
		if (position.isEmpty()) {
			return;
		}
		NodeRemark nodeRemark = new NodeRemark();
		nodeRemark.setName(position);
		List<NodeRemark> nodeRemarks = getDataCache(NodeRemark.class);
		// 判断是否存在数据
		if (nodeRemarks != null && nodeRemarks.size() > 0) {// 最新添加的与上次相同跳过
			if (nodeRemarks.get(0).getName().equals(position)) {
				return;
			}
			// 查找以前是否存在
			for (int i = 1; i < nodeRemarks.size(); i++) {
				if (nodeRemarks.get(i).getName().equals(position)) {
					this.deleteById(NodeRemark.class, nodeRemarks.get(i).getId());// 存在删除
					this.save(nodeRemark);
					return;
				}
			}
			if (nodeRemarks.size() >= 5) {// 数据大于等于5条就删除最后一条
				this.deleteById(NodeRemark.class, nodeRemarks.get(nodeRemarks.size() - 1).getId());
			}
			this.save(nodeRemark);
		} else {// 没有直接保存
			this.save(nodeRemark);
		}
	}

	/**
	 * 获取
	 * 
	 * @param <X>
	 * 
	 * @return
	 */
	public <X> List<X> getDataCache(Class<X> entityClass) {
		List<X> list = null;
		String expr = " 1=1 order by id desc";
		list = this.queryByExpr2(entityClass, expr);
		return list;
	}

	/**
	 * 根据线路id查找线路节点顺序
	 * 
	 * @param lineId
	 * @return
	 */
	public List<LineNodeSort> findLineNodeSortById(String lineId) {
		String expr = "lineId='" + lineId + "' order by id desc";
		List<LineNodeSort> lineNodeSorts = this.queryByExpr2(LineNodeSort.class, expr);
		return lineNodeSorts;
	}

	/**
	 * 保存或修改线路节点顺序
	 * 
	 * @param lineId
	 * @param sort
	 */
	public void saveLineNodeSort(String lineId, Integer sort) {
		List<LineNodeSort> lineNodeSorts = this.findLineNodeSortById(lineId);
		// 如果保存数据不为空或者大于两条则删除最后一条在添加
		if (lineNodeSorts != null && lineNodeSorts.size() >= 2) {
			this.deleteById(LineNodeSort.class, lineNodeSorts.get(lineNodeSorts.size() - 1).getId());
		}
		LineNodeSort entity = new LineNodeSort();
		entity.setLineId(lineId);
		entity.setSort(sort);
		save(entity);
	}

	/**
	 * 获取上一个标识器
	 * 
	 * @return
	 */
	public UpMarkerCache getUpMarkerCache() {
		List<UpMarkerCache> list = this.findAll(UpMarkerCache.class);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 保存上一个标识器的信息
	 * 
	 * @param upMarkerCache
	 */
	public void saveUpMarkerCache(UpMarkerCache upMarkerCache) {
		List<UpMarkerCache> list = this.findAll(UpMarkerCache.class);
		if (list != null && list.size() > 0) {
			UpMarkerCache upMarkerCachetTmp = list.get(0);
			upMarkerCachetTmp.setCableDeepth(upMarkerCache.getCableDeepth());
			upMarkerCachetTmp.setCableWidth(upMarkerCache.getCableWidth());
			upMarkerCachetTmp.setDeviceDesc(upMarkerCache.getDeviceDesc());
			upMarkerCachetTmp.setInstallPosition(upMarkerCache.getInstallPosition());
			upMarkerCachetTmp.setGeographicalPosition(upMarkerCache.getGeographicalPosition());
			upMarkerCachetTmp.setLayType(upMarkerCache.getLayType());
			upMarkerCachetTmp.setMarkerType(upMarkerCache.getMarkerType());
			upMarkerCachetTmp.setRemark(upMarkerCache.getRemark());
			this.update(upMarkerCachetTmp);
		} else {
			this.save(upMarkerCache);
		}

	}

}
