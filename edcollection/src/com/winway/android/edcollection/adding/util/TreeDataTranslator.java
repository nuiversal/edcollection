package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.entity.IndexEcNodeEntity;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcDistBoxEntity;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.ewidgets.tree.datacenter.DataTranslator;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.entity.LevelEntity;
import com.winway.android.ewidgets.tree.utils.ImageURIUtil;

/**
 * 数据转换器，将数据转化成台账树支持的数据结构
 * 
 * @author mr-lao
 *
 */
public class TreeDataTranslator implements DataTranslator {

	private ItemCustomViewUtil itemCustomViewUtil;

	public void setItemCustomViewUtil(ItemCustomViewUtil itemCustomViewUtil) {
		this.itemCustomViewUtil = itemCustomViewUtil;
	}

	@Override
	public LevelEntity translateToLevelEntity(Object data) {
		LevelEntity level = null;
		if (data instanceof List) {
			// 列表
			level = processList((List<?>) data);
		}
		return level;
	}

	@SuppressWarnings("unchecked")
	private LevelEntity processList(List<?> datas) {
		Object object = datas.get(0);
		if (object instanceof EcSubstationEntity) {
			// 变电站
			return substationsToLevel((List<EcSubstationEntity>) datas);
		}
		if (object instanceof EcLineEntity) {
			// 线路
			return linesToLevel((List<EcLineEntity>) datas);
		}
		if (object instanceof EcNodeEntity) {
			// 路径点
			return lineNodesToLevel((List<EcNodeEntity>) datas);
		}
		if (object instanceof DeviceEntity) {
			// 设备
			Object obj = ((DeviceEntity<?>) object).getData();
			if (obj instanceof EcLineLabelEntity) {
				// 标签
				return lineLabelToLevel((List<DeviceEntity<EcLineLabelEntity>>) datas);
			}
			if (obj instanceof EcWorkWellEntity) {
				// 工井
				return wellsToLevel((List<DeviceEntity<EcWorkWellEntity>>) datas);
			}
			if (obj instanceof EcMiddleJointEntity) {
				// 中间接头
				return middleToLevel((List<DeviceEntity<EcMiddleJointEntity>>) datas);
			}
			if (obj instanceof EcDistBoxEntity) {
				// 分接箱
				return distboxToLevel((List<DeviceEntity<EcDistBoxEntity>>) datas);
			}
			if (obj instanceof EcTowerEntity) {
				// 杆塔
				return towerToLevel((List<DeviceEntity<EcTowerEntity>>) datas);
			}
			if (obj instanceof EcTransformerEntity) {
				// 变压器
				return transformerToLevel((List<DeviceEntity<EcTransformerEntity>>) datas);
			}
			if (obj instanceof EcDistributionRoomEntity) {
				// 配电房
				return roomToLevel((List<DeviceEntity<EcDistributionRoomEntity>>) datas);
			}
			if (obj instanceof EcHwgEntity) {
				// 环网柜
				return hwgToLevel((List<DeviceEntity<EcHwgEntity>>) datas);
			}
			if (obj instanceof EcXsbdzEntity) {
				// 箱式变电站
				return xsbdzToLevel((List<DeviceEntity<EcXsbdzEntity>>) datas);
			}
			if (obj instanceof EcDlfzxEntity) {
				return dlfzxToLevel((List<DeviceEntity<EcDlfzxEntity>>) datas);
			}
			if (obj instanceof EcDydlfzxEntity) {
				// 低压电缆分支箱
				return dydlfzxToLevel((List<DeviceEntity<EcDydlfzxEntity>>) datas);
			}
			if (obj instanceof EcDypdxEntity) {
				// 低压配电箱
				return dypdxToLevel((List<DeviceEntity<EcDypdxEntity>>) datas);
			}
			if (obj instanceof EcKgzEntity) {
				// 开关站
				return kgzToLevel((List<DeviceEntity<EcKgzEntity>>) datas);
			}
			if (obj instanceof EcWorkWellCoverEntity) {
				// 井盖
				return coverToLevel((List<DeviceEntity<EcWorkWellCoverEntity>>) datas);
			}
		}
		if (object instanceof ChannelDataEntity) {
			return channelToLevel((List<ChannelDataEntity>) datas);
		}
		if (object instanceof IndexEcNodeEntity) {// 通道节点
			return channelNodesToLevel((List<IndexEcNodeEntity<EcChannelNodesEntity>>) datas);
		}
		return null;
	}

	/**
	 * 加载井盖
	 * 
	 * @param coverList
	 * @return
	 */
	private LevelEntity coverToLevel(List<DeviceEntity<EcWorkWellCoverEntity>> coverList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcWorkWellCoverEntity> cover : coverList) {
			ItemEntity item = createItemEntity(cover.getData().getSbmc(), false, false, cover, R.drawable.cover);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(cover.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载通道
	 * 
	 * @param data
	 * @return
	 */
	private LevelEntity channelToLevel(List<ChannelDataEntity> data) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> items = new ArrayList<ItemEntity>();
		for (ChannelDataEntity channel : data) {
			Object channelType = channel.getChannelType();
			if (null == channelType) {
				continue;
			}
			String text = "";
			int image = NO_IMAGE;
			if (channelType instanceof EcChannelDgEntity) {
				// 电缆拖拉管/顶管
				text = "拖拉管/顶管" + "【" + channel.getData().getName() + "】";
			}
			if (channelType instanceof EcChannelDlgdEntity) {
				// 电缆管道，即排管
				// text = "电缆管道";
				text = "排管" + "【" + channel.getData().getName() + "】";
			}
			if (channelType instanceof EcChannelDlgEntity) {
				// 电缆沟
				text = "沟道" + "【" + channel.getData().getName() + "】";
				// text = "电缆沟";
			}
			if (channelType instanceof EcChannelDlqEntity) {
				// 电缆桥
				text = "桥架" + "【" + channel.getData().getName() + "】";
				// text = "电缆桥";
			}
			if (channelType instanceof EcChannelDlsdEntity) {
				// 电缆隧道
				text = "隧道" + "【" + channel.getData().getName() + "】";
				// text = "电缆隧道";
			}
			if (channelType instanceof EcChannelDlzmEntity) {
				// 电缆直埋
				text = "直埋" + "【" + channel.getData().getName() + "】";
				// text = "电缆直埋";
			}
			if (channelType instanceof EcChannelJkEntity) {
				// 架空
				text = "架空" + "【" + channel.getData().getName() + "】";
				// text = "架空";
			}
			if (channelType instanceof EcChannelDlcEntity){
				// 电缆槽
				text = "电缆槽" + "【" + channel.getData().getName() + "】";
				// text = "电缆槽";
			}
			if (!items.contains(text)) {
				items.add(createItemEntity(text, true, true, channel, image));
			}
		}
		level.setDatas(items);
		level.setDatasCount(items.size());
		return level;
	}

	/**
	 * 构建通道节点
	 * 
	 * @param data
	 * @return
	 */
	private LevelEntity channelNodesToLevel(List<IndexEcNodeEntity<EcChannelNodesEntity>> data) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> items = new ArrayList<ItemEntity>();
		String imageURI = null;
		for (IndexEcNodeEntity<EcChannelNodesEntity> indexEcNodeEntity : data) {
			EcNodeEntity node = indexEcNodeEntity.getNode();
			if (node == null) {
				continue;
			}
			if (node.getMarkerType() == 1) {
				// 标识球
				imageURI = ImageURIUtil.createDrawableImageURI(R.drawable.bsq);
			} else if (node.getMarkerType() == 2) {
				// 标识钉
				imageURI = ImageURIUtil.createDrawableImageURI(R.drawable.bsd);
			} else if (node.getMarkerType() == 0) {
				// 路径点
				imageURI = ImageURIUtil.createDrawableImageURI(R.drawable.ljd);
			} else if (node.getMarkerType() == 3) {
				// 杆塔
				imageURI = ImageURIUtil.createDrawableImageURI(R.drawable.tower);
			} else if (node.getMarkerType() == 4) {
				// 安键环
				imageURI = ImageURIUtil.createDrawableImageURI(R.drawable.ajh);
			}
			ItemEntity itemEntity = new ItemEntity();
			itemEntity.setImageURI(imageURI);
			itemEntity.setHasChild(false);
			itemEntity.setText(node.getMarkerNo());
			itemEntity.setObjData(indexEcNodeEntity);
			// 图标
			if (null != itemCustomViewUtil) {
				List<EcLineLabelEntity> labelList = itemCustomViewUtil
						.getChannelLabel(indexEcNodeEntity.getIndex().getEcChannelId(), node.getOid());
				itemCustomViewUtil.createLabelView(labelList, itemEntity);
			}
			items.add(itemEntity);
		}
		level.setDatas(items);
		level.setDatasCount(items.size());
		return level;
	}

	static final int NO_IMAGE = -100000;

	/**
	 * 构建节点数据模型
	 * 
	 * @param text
	 * @param hasChild
	 * @param showEC
	 * @param objData
	 * @param drawbleimg
	 * @return
	 */
	ItemEntity createItemEntity(String text, boolean hasChild, boolean showEC, Object objData, int drawbleimg) {
		ItemEntity item = new ItemEntity();
		item.setText(text);
		item.setHasChild(hasChild);
		item.setShowEX(showEC);
		item.setObjData(objData);
		if (drawbleimg != NO_IMAGE) {
			item.setImageURI(ImageURIUtil.createDrawableImageURI(drawbleimg));
		}
		return item;
	}

	/**
	 * 加载开关站
	 * 
	 * @param kgzList
	 * @return
	 */
	private LevelEntity kgzToLevel(List<DeviceEntity<EcKgzEntity>> kgzList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcKgzEntity> kgz : kgzList) {
			ItemEntity item = createItemEntity(kgz.getData().getSbmc(), false, false, kgz, R.drawable.pdkgf_s);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(kgz.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载低压配电箱
	 * 
	 * @param dypdxList
	 * @return
	 */
	private LevelEntity dypdxToLevel(List<DeviceEntity<EcDypdxEntity>> dypdxList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcDypdxEntity> dypdx : dypdxList) {
			ItemEntity item = createItemEntity(dypdx.getData().getSbmc(), false, false, dypdx, R.drawable.peidianxiang);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(dypdx.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载电缆分支箱
	 * 
	 * @param dlfzxList
	 * @return
	 */
	private LevelEntity dlfzxToLevel(List<DeviceEntity<EcDlfzxEntity>> dlfzxList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcDlfzxEntity> dlfzx : dlfzxList) {
			ItemEntity item = createItemEntity(dlfzx.getData().getSbmc(), false, false, dlfzx,
					R.drawable.jiexianxiang_s);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(dlfzx.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载低压电缆分支箱
	 * 
	 * @param dydlfzxList
	 * @return
	 */
	private LevelEntity dydlfzxToLevel(List<DeviceEntity<EcDydlfzxEntity>> dydlfzxList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcDydlfzxEntity> dydlfzx : dydlfzxList) {
			ItemEntity item = createItemEntity(dydlfzx.getData().getSbmc(), false, false, dydlfzx,
					R.drawable.pdhwkgx_s);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(dydlfzx.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载箱式变电站
	 * 
	 * @param xsbdzList
	 * @return
	 */
	private LevelEntity xsbdzToLevel(List<DeviceEntity<EcXsbdzEntity>> xsbdzList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcXsbdzEntity> xsbdz : xsbdzList) {
			ItemEntity item = createItemEntity(xsbdz.getData().getSbmc(), false, false, xsbdz, R.drawable.pdxb_s);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(xsbdz.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载环球柜
	 * 
	 * @param hwgList
	 * @return
	 */
	private LevelEntity hwgToLevel(List<DeviceEntity<EcHwgEntity>> hwgList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcHwgEntity> hwg : hwgList) {
			ItemEntity item = createItemEntity(hwg.getData().getSbmc(), false, false, hwg, R.drawable.huanwanggui_s);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(hwg.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载变压器
	 * 
	 * @param transformerList
	 * @return
	 */
	private LevelEntity transformerToLevel(List<DeviceEntity<EcTransformerEntity>> transformerList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcTransformerEntity> transformer : transformerList) {
			ItemEntity item = createItemEntity(transformer.getData().getSbmc(), false, false, transformer,
					R.drawable.bianyaqi_s);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(transformer.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载配电房
	 * 
	 * @param roomList
	 * @return
	 */
	private LevelEntity roomToLevel(List<DeviceEntity<EcDistributionRoomEntity>> roomList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcDistributionRoomEntity> room : roomList) {
			ItemEntity item = createItemEntity(room.getData().getSbmc(), false, false, room, R.drawable.pdpdz_s);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(room.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载杆塔
	 * 
	 * @param towerList
	 * @return
	 */
	private LevelEntity towerToLevel(List<DeviceEntity<EcTowerEntity>> towerList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcTowerEntity> tower : towerList) {
			ItemEntity item = createItemEntity(tower.getData().getTowerNo() + "", false, false, tower,
					R.drawable.ganta_s);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(tower.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * @deprecated 分接箱在设计中已经被拆分成电缆分支箱与低压电缆分支箱两个类型了 加载分接箱
	 * @param boxList
	 * @return
	 */
	private LevelEntity distboxToLevel(List<DeviceEntity<EcDistBoxEntity>> boxList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcDistBoxEntity> box : boxList) {
			ItemEntity item = createItemEntity(box.getData().getDevName(), false, false, box, NO_IMAGE);
			if (box.getData().getJointType() == 1) {
				// 分接箱
				item.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.jiexianxiang_s));
				// 图标
				if (null != itemCustomViewUtil) {
					itemCustomViewUtil.createNodeAndLineDeviceView(box.getData(), item);
				}
			} else {
				// 环网柜
				item.setImageURI(ImageURIUtil.createDrawableImageURI(R.drawable.huanwanggui_s));
				// 图标
				if (null != itemCustomViewUtil) {
					itemCustomViewUtil.createNodeAndLineDeviceView(box.getData(), item);
				}
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载中间接头
	 * 
	 * @param middleList
	 * @return
	 */
	private LevelEntity middleToLevel(List<DeviceEntity<EcMiddleJointEntity>> middleList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcMiddleJointEntity> middle : middleList) {
			ItemEntity item = createItemEntity(middle.getData().getSbmc(), false, false, middle, R.drawable.jietou_s);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(middle.getData(), item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 加载工井
	 * 
	 * @param wellList
	 * @return
	 */
	private LevelEntity wellsToLevel(List<DeviceEntity<EcWorkWellEntity>> wellList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcWorkWellEntity> well : wellList) {
			ItemEntity item = createItemEntity(well.getData().getSbmc(), true, false, well, R.drawable.gongjing_s);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(well.getData(), item);
				/*
				 * itemCustomViewUtil.createSectionView(well.getData(), item);
				 */
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 变电站
	 * 
	 * @param list
	 * @return
	 */
	private LevelEntity substationsToLevel(List<EcSubstationEntity> list) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (EcSubstationEntity station : list) {
			ItemEntity item = createItemEntity(station.getName(), true, true, station, R.drawable.bdz);
			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeAndLineDeviceView(station, item);
			}
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 线路
	 * 
	 * @param list
	 * @return
	 */
	private LevelEntity linesToLevel(List<EcLineEntity> list) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (EcLineEntity line : list) {
			ItemEntity item = new ItemEntity();
			item.setHasChild(true);
			item.setText(line.getName());
			item.setObjData(line);
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 节点
	 * 
	 * @param list
	 * @return
	 */
	private LevelEntity lineNodesToLevel(List<EcNodeEntity> list) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (EcNodeEntity node : list) {
			ItemEntity item = new ItemEntity();
			String imageURI = null;
			if (node.getMarkerType() == 1) {
				// 标识球
				imageURI = ImageURIUtil.createDrawableImageURI(R.drawable.bsq);
			} else if (node.getMarkerType() == 2) {
				// 标识钉
				imageURI = ImageURIUtil.createDrawableImageURI(R.drawable.bsd);
			} else if (node.getMarkerType() == 0) {
				// 路径点
				imageURI = ImageURIUtil.createDrawableImageURI(R.drawable.ljd);
			} else if (node.getMarkerType() == 3) {
				// 杆塔
				imageURI = ImageURIUtil.createDrawableImageURI(R.drawable.tower);
			} else if (node.getMarkerType() == 4) {
				imageURI = ImageURIUtil.createDrawableImageURI(R.drawable.ajh);
			}
			item.setImageURI(imageURI);
			item.setHasChild(false);
			item.setText(node.getMarkerNo());
			item.setObjData(node);

			// 图标
			if (null != itemCustomViewUtil) {
				itemCustomViewUtil.createNodeDeviceView(item);
			}

			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}

	/**
	 * 标签
	 * 
	 * @param labelList
	 * @return
	 */
	private LevelEntity lineLabelToLevel(List<DeviceEntity<EcLineLabelEntity>> labelList) {
		LevelEntity level = new LevelEntity();
		ArrayList<ItemEntity> itemList = new ArrayList<ItemEntity>();
		for (DeviceEntity<EcLineLabelEntity> deviceEntity : labelList) {
			ItemEntity item = createItemEntity(deviceEntity.getData().getDevName(), false, false, deviceEntity,
					R.drawable.zhadai_s);
			itemList.add(item);
		}
		level.setDatas(itemList);
		level.setDatasCount(itemList.size());
		return level;
	}
}
