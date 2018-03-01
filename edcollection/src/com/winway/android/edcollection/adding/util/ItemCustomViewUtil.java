package com.winway.android.edcollection.adding.util;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.ChannelPlanningActivity;
import com.winway.android.edcollection.adding.activity.LookVRImageActivity;
import com.winway.android.edcollection.adding.customview.DensityUtil;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.NodeDevicesEntity;
import com.winway.android.edcollection.datacenter.service.ChannelCenter;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.project.entity.EcChannelSectionEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcVRRefEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.ListUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ItemCustomViewUtil {
	private Activity context;
	private DataCenter dataCenter;
	OfflineAttachCenter offlineAttachCenter = null;
	BaseDBUtil projectBDUtil = null;

	public ItemCustomViewUtil(Activity context, DataCenter dataCenter,
			OfflineAttachCenter offlineAttachCenter) {
		super();
		this.context = context;
		this.dataCenter = dataCenter;
		this.offlineAttachCenter = offlineAttachCenter;
		projectBDUtil = new BaseDBUtil(context, new File(GlobalEntry.prjDbUrl));
	}

	/**
	 * 生成图片
	 * 
	 * @param context
	 * @param drawableID
	 * @return
	 */
	private ImageView createImageView(Context context, int drawableID) {
		ImageView iv = new ImageView(context);
		int width = DensityUtil.dip2px(context, 16);
		int height = DensityUtil.dip2px(context, 16);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
		params.rightMargin = DensityUtil.dip2px(context, 4);
		iv.setLayoutParams(params);
		iv.setImageResource(drawableID);
		return iv;
	}

	/**
	 * 为设备添加电子标签等附属信息
	 * 
	 * @param deviceObj
	 * @param item
	 */
	public void createNodeAndLineDeviceView(Object deviceObj, ItemEntity item) {
		List<EcLineLabelEntity> deviceLabelList = dataCenter.getDeviceLabel(null, deviceObj, null);
		ArrayList<ImageView> imgList = new ArrayList<ImageView>();
		if (deviceObj instanceof EcWorkWellEntity) {
			List<EcVRRefEntity> vrList = dataCenter.getPanoramicByDeviceId(
					((EcWorkWellEntity) deviceObj).getObjId(), null, null);
			launchActivity(vrList, imgList);
			createSectionView((EcWorkWellEntity) deviceObj, imgList);
		}
		if (deviceLabelList != null && !deviceLabelList.isEmpty()) {
			for (final EcLineLabelEntity ecLineLabelEntity : deviceLabelList) {
				ImageView imageView = createImageView(context, R.drawable.zhadai_s);
				imgList.add(imageView);
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// 弹出标签详细信息
						TableShowUtil.showLabelDetailed(ecLineLabelEntity, offlineAttachCenter,
								context);
					}
				});
			}
		}
		// 路径点及线路设备关联的标签
		if (!imgList.isEmpty()) {
			LinearLayout linearLayout = new LinearLayout(context);
			for (ImageView imageView : imgList) {
				linearLayout.addView(imageView);
			}
			item.setCustomItemView(linearLayout);
		}
	}

	/**
	 * 为通道节点添加标签图标
	 * 
	 * @param labelList
	 */
	public void createLabelView(List<EcLineLabelEntity> labelList,ItemEntity item) {
		ArrayList<ImageView> imgList = new ArrayList<ImageView>();
		if (labelList != null && !labelList.isEmpty()) {
			for (final EcLineLabelEntity ecLineLabelEntity : labelList) {
				ImageView imageView = createImageView(context, R.drawable.zhadai_s);
				imgList.add(imageView);
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// 弹出标签详细信息
						TableShowUtil.showLabelDetailed(ecLineLabelEntity, offlineAttachCenter,
								context);
					}
				});
			}
		}
		// 路径点及线路设备关联的标签
		if (!imgList.isEmpty()) {
			LinearLayout linearLayout = new LinearLayout(context);
			for (ImageView imageView : imgList) {
				linearLayout.addView(imageView);
			}
			item.setCustomItemView(linearLayout);
		}
	}
	/**
	 * 为路径点添加路径点设备图标
	 * 
	 * @param item
	 */
	public void createNodeDeviceView(ItemEntity item) {
		EcNodeEntity ecnode = (EcNodeEntity) item.getObjData();
		DataCenterImpl.dataCacheCenter.remove("getPathNodeDetails_" + ecnode.getOid());
		final NodeDevicesEntity data = dataCenter.getPathNodeDetails(ecnode.getOid(), null, null);
		if (data == null) {
			return;
		}
		ArrayList<ImageView> imgList = new ArrayList<ImageView>();
		if (data.getDistribution_room() != null && !data.getDistribution_room().isEmpty()) {
			ImageView imageView = createImageView(context, R.drawable.pdpdz_s);
			imgList.add(imageView);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 弹出配电室详细信息
					TableShowUtil.showRoomDetailed(data.getDistribution_room().get(0).getData(),
							offlineAttachCenter, (Activity) context);
				}
			});
		}
		if (data.getStation() != null && !data.getStation().isEmpty()) {
			// 变电站
			ImageView imageView = createImageView(context, R.drawable.bdz);
			imgList.add(imageView);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 弹出变电站详细信息
					TableShowUtil.showStationDetailed(data.getStation().get(0).getData(),
							offlineAttachCenter, (Activity) context);
				}
			});
		}
		if (data.getTower() != null && !data.getTower().isEmpty()) {
			// 杆塔
			ImageView imageView = createImageView(context, R.drawable.ganta_s);
			imgList.add(imageView);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 弹出杆塔详细信息
					TableShowUtil.showTowerDetailed(data.getTower().get(0).getData(),
							offlineAttachCenter, (Activity) context);
				}
			});
		}
		if (data.getTranformer() != null && !data.getTranformer().isEmpty()) {
			ImageView imageView = createImageView(context, R.drawable.bianyaqi_n);
			imgList.add(imageView);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 显示变压器
					TableShowUtil.showTransDetailed(data.getTranformer().get(0).getData(),
							offlineAttachCenter, (Activity) context);
				}
			});
		}
		if (data.getWell() != null && !data.getWell().isEmpty()) {
			// 工井
			ImageView imageView = createImageView(context, R.drawable.gongjing_s);
			imgList.add(imageView);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 弹出工井详细信息
					TableShowUtil.showWellDetailed(data.getWell().get(0).getData(),
							offlineAttachCenter, (Activity) context);
				}
			});
		}
		if (data.getDypdx() != null && !data.getDypdx().isEmpty()) {
			// 低压配电箱
			ImageView imageView = createImageView(context, R.drawable.peidianxiang);
			imgList.add(imageView);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 弹出详细信息
					TableShowUtil.showDypdxDetailed(data.getDypdx().get(0).getData(),
							(Activity) context, dataCenter);
				}
			});
		}
		if (data.getKgz() != null && !data.getKgz().isEmpty()) {
			// 开关站
			ImageView imageView = createImageView(context, R.drawable.pdkgf_s);
			imgList.add(imageView);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 弹出详细信息
					TableShowUtil.showKgzDetailed(data.getKgz().get(0).getData(),
							(Activity) context, dataCenter);
				}
			});
		}
		if (data.getXsbdz() != null && !data.getXsbdz().isEmpty()) {
			// 箱式变电站
			ImageView imageView = createImageView(context, R.drawable.pdxb_s);
			imgList.add(imageView);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 弹出详细信息
					TableShowUtil.showXsbdzDetailed(data.getXsbdz().get(0).getData(),
							(Activity) context, dataCenter);
				}
			});
		}
		// 标识器中的路径点设备
		if (!imgList.isEmpty()) {
			LinearLayout linearLayout = new LinearLayout(context);
			for (ImageView imageView : imgList) {
				linearLayout.addView(imageView);
			}
			item.setCustomItemView(linearLayout);
		}
	}

	public void createSectionView(EcWorkWellEntity well, List<ImageView> imgList) {
		final ChannelCenter channelCenter = ((DataCenterImpl) dataCenter).getChannelCenter();
		final EcChannelSectionEntity section = channelCenter.getEcChannelSectionEntityByObjid(
				well.getObjId(), ResourceEnum.DLJ.getValue() + "");
		if (section != null) {
			ImageView imageView = createImageView(context, R.drawable.pm);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ChannelDataEntity channel = channelCenter.getChannel(section.getEcChannelId());
					AndroidBasicComponentUtils.launchActivityForResult(context,
							ChannelPlanningActivity.class, new String[]{"channel_id",
									"channel_name", "node_id", "isNeedSave"},
							new String[]{section.getEcChannelId(), channel.getData().getName(),
									section.getEcNodeId(), "1"}, 1080);
				}
			});
			imgList.add(imageView);
		}
	}

	/**
	 * 跳转到全景展示界面
	 * 
	 * @param vrList
	 * @param allImgList
	 */
	private void launchActivity(List<EcVRRefEntity> vrList, ArrayList<ImageView> allImgList) {
		final List<EcVRRefEntity> copyVrList = new ArrayList<EcVRRefEntity>();
		if (vrList != null && !vrList.isEmpty()) {
			ListUtil.copyList(copyVrList, vrList);
			ImageView imageView = createImageView(context, R.drawable.panoramic);
			allImgList.add(imageView);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle b = new Bundle();
					b.putSerializable("vrList", (Serializable) copyVrList);
					AndroidBasicComponentUtils
							.launchActivity(context, LookVRImageActivity.class, b);
				}
			});
		}
	}

	/**
	 * 根据通道id获取标签列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<EcLineLabelEntity> getChannelLabel(String channelId,String oid) {
		EcLineLabelEntity queryEntity = new EcLineLabelEntity();
		queryEntity.setDevObjId(channelId);
		queryEntity.setOid(oid);
		try {
			return projectBDUtil.excuteQuery(EcLineLabelEntity.class, queryEntity);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
}
