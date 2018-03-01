package com.winway.android.edcollection.adding.util;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winway.android.basicbusiness.business.OfflineAttachGeter;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.ChannelEditActivity;
import com.winway.android.edcollection.adding.activity.MarkerCollectActivity;
import com.winway.android.edcollection.adding.controll.DeviceReadQueryControll;
import com.winway.android.edcollection.adding.customview.DensityUtil;
import com.winway.android.edcollection.adding.entity.LineDeviceType;
import com.winway.android.edcollection.adding.entity.NodeDeviceType;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.ResourceEnumUtil;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.main.controll.MainControll;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
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
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.ewidgets.attachment.AttachmentAttrs;
import com.winway.android.ewidgets.attachment.AttachmentView;
import com.winway.android.ewidgets.dialog.table.KtableView;
import com.winway.android.ewidgets.dialog.table.TableUtils;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.ImageHelper;
import com.winway.android.util.ListUtil;
import com.winway.android.util.PhotoUtil;
import com.winway.android.util.ToastUtils;

public class TableShowUtil {

	static ImageView createImageView(Activity mActivity, String imagePath) {
		ImageView imageView = new ImageView(mActivity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(mActivity, 300),
				DensityUtil.dip2px(mActivity, 400));
		params.bottomMargin = DensityUtil.dip2px(mActivity, 5);
		imageView.setLayoutParams(params);
		imageView.setScaleType(ScaleType.FIT_XY);
		imageView.setImageBitmap(ImageHelper.decodeSampledBitmapFromImagePath(imagePath, 300, 400));
		// 设置图片点击事件（点击图片查看大图）
		if (imgClickListener.getActivity() == null || imgClickListener.getActivity() != mActivity) {
			imgClickListener.setActivity(mActivity);
		}
		imageView.setTag(imagePath);
		imageView.setOnClickListener(imgClickListener);
		return imageView;
	}

	static class ImageClickListener implements OnClickListener {
		private Activity activity;

		public void setActivity(Activity activity) {
			this.activity = activity;
		}

		public Activity getActivity() {
			return activity;
		}

		@Override
		public void onClick(View v) {
			String imgPath = (String) v.getTag();
			PhotoUtil.openPhoto(activity, imgPath);
		}

	}

	static ImageClickListener imgClickListener = new ImageClickListener();

	// 移除容器中的非图片对象
	static void removeUnlessImage(List<String> fileList) {
		if (fileList != null && !fileList.isEmpty()) {
			final ArrayList<String> container = new ArrayList<String>();
			ListUtil.copyList(container, fileList);
			for (String file : container) {
				int lastIndexOf = file.lastIndexOf(".jpg");
				if (lastIndexOf < 0) {
					fileList.remove(file);
				}
			}
		}
	}

	/**
	 * 路径点类型为杆塔的时候才会使用此方法
	 * 
	 * @param list
	 * @param mActivity
	 * @param titleName
	 * @param images
	 * @param markerType
	 */
	public static void showTable(ArrayList<Object[]> list, final Activity mActivity, String titleName,
			HashMap<String, List<String>> images, Integer markerType, String dburl, String workno, String id, String sufx) {
		showTable(list, null, mActivity, titleName, images, null, markerType, dburl, workno, id, sufx);
	}

	/**
	 * 要同时显示线路的时候才会使用此方法（显示标签、中间接头）
	 * 
	 * @param list
	 * @param lineDataList
	 * @param mActivity
	 * @param titleName
	 * @param images
	 */
	public static void showTable(ArrayList<Object[]> list, ArrayList<Object[]> lineDataList,ArrayList<Object[]> middleDataList, final Activity mActivity,
			String titleName, HashMap<String, List<String>> images, Integer markerType, String dburl, String workno,
			String id, String sufx) {
		showTable(list, lineDataList, mActivity, titleName, images, null, null, dburl, workno, id, sufx);
	}

	/**
	 * 显示表格
	 * 
	 * @param list
	 * @param lineDataList
	 * @param mActivity
	 * @param titleName
	 * @param images
	 * @param channelData
	 * @param markerType
	 */
	public static void showTable(ArrayList<Object[]> list, ArrayList<Object[]> lineDataList, final Activity mActivity,
			final String titleName, HashMap<String, List<String>> images, final ChannelDataEntity channelData,
			Integer markerType, String dburl, String workno, String id, String sufx) {
		final DialogUtil dialogUtil = new DialogUtil((Activity) mActivity);
		KtableView ktableView = TableUtils.getInstance().addTableInfo(mActivity, null, list);
		View layout = View.inflate(mActivity, R.layout.layout_table_container, null);
		if (list.size() > 15 || (images != null && !images.isEmpty())
				|| (lineDataList != null && list.size() + lineDataList.size() > 15)) {
			layout = View.inflate(mActivity, R.layout.layout_table_container_b, null);
		}
		final LinearLayout tableLL = (LinearLayout) layout.findViewById(R.id.table_container);
		TextView title = (TextView) layout.findViewById(R.id.title);
		title.setText(titleName + "详细信息");
		tableLL.addView(ktableView);
		// 添加线路数据
		if (null != lineDataList && !lineDataList.isEmpty()) {
			KtableView ktableViewline = TableUtils.getInstance().addTableInfo(mActivity, null, lineDataList);
			TextView lineTV = new TextView(mActivity);
			lineTV.setText("附属设备信息");
			LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutparams.leftMargin = DensityUtil.dip2px(mActivity, 4);
			layoutparams.topMargin = DensityUtil.dip2px(mActivity, 4);
			layoutparams.bottomMargin = DensityUtil.dip2px(mActivity, 4);
			lineTV.setLayoutParams(layoutparams);
			tableLL.addView(lineTV);
			tableLL.addView(ktableViewline);
		}
		if (channelData != null) {
			// 进入通道详情
			layout.findViewById(R.id.edit).setVisibility(View.VISIBLE);
			layout.findViewById(R.id.edit).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("channelEntity", channelData.getData());
					bundle.putSerializable("channelTypeEntity", (Serializable) channelData.getChannelType());
					bundle.putString("channelType", titleName);
					AndroidBasicComponentUtils.launchActivity(mActivity, ChannelEditActivity.class, bundle);
				}
			});
		}
		if (NodeMarkerType.BSQ.getName().equals(titleName) || NodeMarkerType.BSD.getName().equals(titleName)
				|| NodeMarkerType.XND.getName().equals(titleName) || NodeMarkerType.GT.getValue() == markerType
				|| NodeMarkerType.AJH.getName().equals(titleName)) { // 路径点
			layout.findViewById(R.id.edit).setVisibility(View.VISIBLE);
			layout.findViewById(R.id.edit).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					GlobalEntry.editNode = true;
					Intent intent = new Intent(mActivity, MarkerCollectActivity.class);
					intent.putExtra("EcNodeEntity", GlobalEntry.currentEditNode);
					AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, MainControll.flag_marker_edit);
				}
			});
		}
		if(DeviceReadQueryControll.isEdit){ //是否给电子标签提供编辑状态，旧数据不允许，新数据允许。
			if(LineDeviceType.DZBQ.getName().equals(titleName)){
				layout.findViewById(R.id.edit).setVisibility(View.VISIBLE);
				layout.findViewById(R.id.edit).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						GlobalEntry.editNode=true;
						Intent intent = new Intent(mActivity,MarkerCollectActivity.class);
						intent.putExtra("EcNodeEntity", GlobalEntry.currentEditNode);
						AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, MainControll.flag_marker_edit);
						DeviceReadQueryControll.isEdit=false;
					}
				});
			}
		}
		
		
		// 处理附件
		layout.findViewById(R.id.close).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogUtil.dismissDialog();
				dialogUtil.destroy();
			}
		});
		layout.findViewById(R.id.background_image_text).setVisibility(View.GONE);
		layout.findViewById(R.id.background_image_image).setVisibility(View.GONE);
		layout.findViewById(R.id.position_image_text).setVisibility(View.GONE);
		layout.findViewById(R.id.position_image_image).setVisibility(View.GONE);
		if (null != images) {
			List<String> bgPathList = images.get("background_image");
			List<String> psPathList = images.get("position_image");
			final ArrayList<String> attachList = new ArrayList<String>();
			if (null != bgPathList) {
				ListUtil.copyList(attachList, bgPathList);
			}
			if (null != psPathList) {
				ListUtil.copyList(attachList, psPathList);
			}
			if (attachList.isEmpty()) {
				return;
			}
			TextView lineTV = new TextView(mActivity);
			lineTV.setText("附件");
			LinearLayout.LayoutParams linelayoutparams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			linelayoutparams.leftMargin = DensityUtil.dip2px(mActivity, 4);
			linelayoutparams.topMargin = DensityUtil.dip2px(mActivity, 4);
			lineTV.setLayoutParams(linelayoutparams);
			tableLL.addView(lineTV);
			AttachmentView attchView = new AttachmentView(mActivity);
			LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutparams.topMargin = DensityUtil.dip2px(mActivity, 4);
			attchView.setLayoutParams(layoutparams);
			attchView.setAttachmentAttrs(AttachmentAttrs.getJustShowAttrs(mActivity));
			attchView.addExistsAttachments(attachList);
			tableLL.addView(attchView);
		} 
		dialogUtil.showAlertDialog(layout);
	}

	/**
	 * 离线或者在线获取附件（暂时不用，有点问题）
	 * 
	 * @param list
	 * @param lineDataList
	 * @param mActivity
	 * @param titleName
	 * @param images
	 * @param channelData
	 * @param markerType
	 */
	public static void _showTable(ArrayList<Object[]> list, ArrayList<Object[]> lineDataList, final Activity mActivity,
			final String titleName, HashMap<String, List<String>> images, final ChannelDataEntity channelData,
			Integer markerType, String dburl, String workno, String id, String sufx) {
		final DialogUtil dialogUtil = new DialogUtil((Activity) mActivity);
		KtableView ktableView = TableUtils.getInstance().addTableInfo(mActivity, null, list);
		View layout = View.inflate(mActivity, R.layout.layout_table_container, null);
		if (list.size() > 15 || (images != null && !images.isEmpty())
				|| (lineDataList != null && list.size() + lineDataList.size() > 15)) {
			layout = View.inflate(mActivity, R.layout.layout_table_container_b, null);
		}
		final LinearLayout tableLL = (LinearLayout) layout.findViewById(R.id.table_container);
		TextView title = (TextView) layout.findViewById(R.id.title);
		title.setText(titleName + "详细信息");
		tableLL.addView(ktableView);
		// 添加线路数据
		if (null != lineDataList && !lineDataList.isEmpty()) {
			KtableView ktableViewline = TableUtils.getInstance().addTableInfo(mActivity, null, lineDataList);
			TextView lineTV = new TextView(mActivity);
			lineTV.setText("线路信息");
			LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutparams.leftMargin = DensityUtil.dip2px(mActivity, 4);
			layoutparams.topMargin = DensityUtil.dip2px(mActivity, 4);
			layoutparams.bottomMargin = DensityUtil.dip2px(mActivity, 4);
			lineTV.setLayoutParams(layoutparams);
			tableLL.addView(lineTV);
			tableLL.addView(ktableViewline);
		}
		// 处理附件
		layout.findViewById(R.id.close).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogUtil.dismissDialog();
				dialogUtil.destroy();
			}
		});

		if (channelData != null) {
			// 进入通道详情
			layout.findViewById(R.id.edit).setVisibility(View.VISIBLE);
			layout.findViewById(R.id.edit).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("channelEntity", channelData.getData());
					bundle.putSerializable("channelTypeEntity", (Serializable) channelData.getChannelType());
					bundle.putString("channelType", titleName);
					AndroidBasicComponentUtils.launchActivity(mActivity, ChannelEditActivity.class, bundle);
				}
			});
		}
		if (NodeMarkerType.BSQ.getName().equals(titleName) || NodeMarkerType.BSD.getName().equals(titleName)
				|| NodeMarkerType.XND.getName().equals(titleName) || NodeMarkerType.GT.getValue() == markerType) { // 路径点
			layout.findViewById(R.id.edit).setVisibility(View.VISIBLE);
			layout.findViewById(R.id.edit).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					GlobalEntry.editNode = true;
					Intent intent = new Intent(mActivity, MarkerCollectActivity.class);
					intent.putExtra("EcNodeEntity", GlobalEntry.currentEditNode);
					AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, MainControll.flag_marker_edit);
				}
			});
		}

		layout.findViewById(R.id.position_image_text).setVisibility(View.GONE);
		layout.findViewById(R.id.position_image_image).setVisibility(View.GONE);
		layout.findViewById(R.id.background_image_text).setVisibility(View.GONE);
		layout.findViewById(R.id.background_image_image).setVisibility(View.GONE);
		// 处理附件
		if (null != dburl) {
			// 处理附件
			BaseDBUtil dbUtil = new BaseDBUtil(mActivity, new File(dburl));
			OfflineAttachGeter geter = new OfflineAttachGeter(mActivity, dbUtil, GlobalEntry.fileServerUrl,
					GlobalEntry.loginResult.getCredit(), GlobalEntry.downLoadDir);
			if (!TextUtils.isEmpty(id)) {
				// 目前只有全景是根据附件主键来拿数据的
			} else {
				try {
					geter.queryOfflieAttach(workno, new OfflineAttachGeter.CallBack() {
						@Override
						public void callFile(com.winway.android.basicbusiness.entity.OfflineAttach attach) {
							ArrayList<com.winway.android.basicbusiness.entity.OfflineAttach> list = new ArrayList<>();
							list.add(attach);
							addOfflineAttach(mActivity, tableLL, list);
						}

						@Override
						public void callAttachs(int code, List<com.winway.android.basicbusiness.entity.OfflineAttach> list,
								boolean hasfile) {
							if (!hasfile || list.isEmpty()) {
								return;
							}
							addOfflineAttach(mActivity, tableLL, list);
						}

						@Override
						public void callError(int code) {
							// TODO Auto-generated method stub

						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtils.show(mActivity, "无法获取离线附件");
				}
			}
		}
		dialogUtil.showAlertDialog(layout);
	}

	/**
	 * 把附件添加到附件组件中
	 * 
	 * @param mActivity
	 * @param tableLL
	 * @param list
	 */
	private static void addOfflineAttach(Activity mActivity, LinearLayout tableLL,
			List<com.winway.android.basicbusiness.entity.OfflineAttach> list) {
		TextView lineTV = (TextView) tableLL.findViewById(999999);
		if (null == lineTV) {
			lineTV = new TextView(mActivity);
			lineTV.setText("附件");
			lineTV.setId(999999);
			LinearLayout.LayoutParams linelayoutparams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			linelayoutparams.leftMargin = DensityUtil.dip2px(mActivity, 4);
			linelayoutparams.topMargin = DensityUtil.dip2px(mActivity, 4);
			lineTV.setLayoutParams(linelayoutparams);
			tableLL.addView(lineTV);
		}
		AttachmentView attchView = (AttachmentView) tableLL.findViewById(888888);
		if (null == attchView) {
			attchView = new AttachmentView(mActivity);
			attchView.setId(888888);
			LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutparams.topMargin = DensityUtil.dip2px(mActivity, 4);
			attchView.setLayoutParams(layoutparams);
			attchView.setAttachmentAttrs(AttachmentAttrs.getJustShowAttrs(mActivity));
			tableLL.addView(attchView);
		}
		ArrayList<String> attachList = new ArrayList<>();
		for (com.winway.android.basicbusiness.entity.OfflineAttach at : list) {
			attachList.add(at.getFilePath());
		}
		attchView.addExistsAttachments(attachList);
	}

	/**
	 * 展示路径点详情
	 * 
	 * @param entity
	 * @param lineid
	 * @param center
	 * @param activity
	 */
	public static void showNodeDetailed(EcNodeEntity entity, String lineid, OfflineAttachCenter center, Activity activity) {
		// 拿到节点实体的数据，并将它们放进集合里面去
		ArrayList<Object[]> list = TableDataUtil.getByEcNodeEntity(entity, lineid);
		// 拿到标识器类型
		Integer markerType = entity.getMarkerType();
		// 拿到oid
		String oid = entity.getOid();
		// 根据oid去离线附件表拿到照片集合
		HashMap<String, List<String>> map = OfflinePictureUtil.getEcLineNodeOfflinePic(oid, center);
		//
		GlobalEntry.currentEditNode = entity;
		if (NodeMarkerType.BSQ.getValue().equals(markerType)) {
			TableShowUtil.showTable(list, activity, NodeMarkerType.BSQ.getName(), map, null, GlobalEntry.prjDbUrl,
					entity.getOid(), null, null);
		} else if (NodeMarkerType.BSD.getValue().equals(markerType)) {
			TableShowUtil.showTable(list, activity, NodeMarkerType.BSD.getName(), map, null, GlobalEntry.prjDbUrl,
					entity.getOid(), null, null);
		} else if (NodeMarkerType.XND.getValue().equals(markerType)) {
			TableShowUtil.showTable(list, activity, NodeMarkerType.XND.getName(), map, null, GlobalEntry.prjDbUrl,
					entity.getOid(), null, null);
		} else if(NodeMarkerType.GT.getValue().equals(markerType)){
			TableShowUtil.showTable(list, activity, NodeMarkerType.GT.getName(), map, markerType, GlobalEntry.prjDbUrl,
					entity.getOid(), null, null);
		} else if(NodeMarkerType.AJH.getValue().equals(markerType)){
			TableShowUtil.showTable(list, activity, NodeMarkerType.AJH.getName(), map, markerType, GlobalEntry.prjDbUrl,
					entity.getOid(), null, null);
		} 

	}

	/**
	 * 显示标签详情
	 * 
	 * @param entity
	 * @param linename
	 * @param center
	 * @param activity
	 */
	public static void showLabelDetailed(EcLineLabelEntity entity, OfflineAttachCenter center, Activity activity) {
		ArrayList<Object[]> list = TableDataUtil.getByEcLineLabelEntity(entity);
		// 拿到标签的id
		String ecLineLabelId = entity.getObjId();
		// 根据id去离线附件中心拿到标签的图片
		HashMap<String, List<String>> map = OfflinePictureUtil.getLabelOfflinePicture(ecLineLabelId, center);
		// 拿到工井实体
		DeviceEntity<EcWorkWellEntityVo> workWell = TableDataUtil.treeDataCenter.getWorkWell(entity.getDevObjId(), null, null);
		if(workWell!=null){
			EcWorkWellEntityVo data = workWell.getData();
			if(data!=null){
				ArrayList<Object[]> workWellDataList = TableDataUtil.getByEcWorkWellEntity(data);
				TableShowUtil.showTable(list, workWellDataList,null, activity, LineDeviceType.DZBQ.getName(), map, null,
						GlobalEntry.prjDbUrl, entity.getObjId(), null, null);
			}
		}
		//拿到中间接头实体
		DeviceEntity<EcMiddleJointEntityVo> middle = TableDataUtil.treeDataCenter.getMiddle(entity.getDevObjId(), null, null);
		if(middle!=null){
			EcMiddleJointEntityVo data = middle.getData();
			if(data!=null){
				ArrayList<Object[]> MiddleJointDataList = TableDataUtil.getByEcMiddleJointEntity(data);
				TableShowUtil.showTable(list, MiddleJointDataList, MiddleJointDataList,activity, LineDeviceType.DZBQ.getName(), map, null,
						GlobalEntry.prjDbUrl, entity.getObjId(), null, null);
			}
		}
		//拿到通道实体
		ChannelDataEntity channel = TableDataUtil.treeDataCenter.getChannel(entity.getDevObjId(), null, null);
		if(channel!=null){
			EcChannelEntity data = channel.getData();
			ArrayList<Object[]> channelDataList = TableDataUtil.getByEcChannelEntity(data, ResourceEnumUtil.get(data.getChannelType()).getName(), channel.getLines(), channel.getStartNode(), channel.getEndNode());
			TableShowUtil.showTable(list, channelDataList, null,activity, LineDeviceType.DZBQ.getName(), map, null,
					GlobalEntry.prjDbUrl, entity.getObjId(), null, null);
		}
		// 拿到线路实体
		EcLineEntity ecline = TableDataUtil.treeDataCenter.getLineDeviceTargetLine(entity.getObjId(), null, null);
		if(ecline!=null){
			ArrayList<Object[]> lineDataList = TableDataUtil.getByEcLineEntity(ecline);
			TableShowUtil.showTable(list, lineDataList, null,activity, LineDeviceType.DZBQ.getName(), map, null,
					GlobalEntry.prjDbUrl, entity.getObjId(), null, null);
		}
	}

	/**
	 * 显示中间接头详情
	 * 
	 * @param entity
	 * @param center
	 * @param activity
	 */
	public static void showMiddleDetailed(EcMiddleJointEntity entity, OfflineAttachCenter center, Activity activity) {
		ArrayList<Object[]> list = TableDataUtil.getByEcMiddleJointEntity(entity);
		HashMap<String, List<String>> map = OfflinePictureUtil.getMiddleOfflinePic(entity.getObjId(), center);
		EcLineEntity ecline = TableDataUtil.treeDataCenter.getLineDeviceTargetLine(entity.getObjId(), null, null);
		if (null == ecline) {
			TableShowUtil.showTable(list, activity, LineDeviceType.ZJJT.getName(), map, null, GlobalEntry.prjDbUrl,
					entity.getObjId(), null, null);
		} else {
			ArrayList<Object[]> lineDataList = TableDataUtil.getByEcLineEntity(ecline);
			TableShowUtil.showTable(list, lineDataList, null,activity, LineDeviceType.ZJJT.getName(), map, null,
					GlobalEntry.prjDbUrl, entity.getObjId(), null, null);
		}
	}

	/**
	 * 显示工井详细信息
	 * 
	 * @param entity
	 * @param center
	 * @param activity
	 */
	public static void showWellDetailed(EcWorkWellEntity entity, OfflineAttachCenter center, Activity activity) {
		ArrayList<Object[]> list = TableDataUtil.getByEcWorkWellEntity(entity);
		HashMap<String, List<String>> map = OfflinePictureUtil.getWorkWellOfflinePic(entity.getObjId(), center);
		TableShowUtil.showTable(list, activity, NodeDeviceType.GJ.getName(), map, null, GlobalEntry.prjDbUrl,
				entity.getObjId(), null, null);
	}

	/**
	 * 显示分接箱详情
	 * 
	 * @param entity
	 * @param center
	 * @param activity
	 */
	public static void showBoxDetailed(EcDistBoxEntity entity, OfflineAttachCenter center, Activity activity) {
		ArrayList<Object[]> list = TableDataUtil.getByEcDistBoxEntity(entity);
		HashMap<String, List<String>> map = OfflinePictureUtil.getBoxOfflinePic(entity.getEcDistBoxId(), center);
		if (entity.getJointType() == 1) {
			TableShowUtil.showTable(list, activity, LineDeviceType.FJX.getName(), map, null, GlobalEntry.prjDbUrl,
					entity.getEcDistBoxId(), null, null);
		} else {
			TableShowUtil.showTable(list, activity, LineDeviceType.HWG.getName(), map, null, GlobalEntry.prjDbUrl,
					entity.getEcDistBoxId(), null, null);
		}
	}

	/**
	 * 显示杆塔详情
	 * 
	 * @param entity
	 * @param center
	 * @param activity
	 */
	public static void showTowerDetailed(EcTowerEntity entity, OfflineAttachCenter center, Activity activity) {
		ArrayList<Object[]> list = TableDataUtil.getByEcTowerEntity(entity);
		HashMap<String, List<String>> map = OfflinePictureUtil.getTowerOfflinePic((entity).getObjId(), center);
		TableShowUtil.showTable(list, activity, NodeDeviceType.GT.getName(), map, null, GlobalEntry.prjDbUrl,
				entity.getObjId(), null, null);
	}

	/**
	 * 显示配电房详情
	 * 
	 * @param entity
	 * @param center
	 * @param activity
	 */
	public static void showRoomDetailed(EcDistributionRoomEntity entity, OfflineAttachCenter center, Activity activity) {
		ArrayList<Object[]> list = TableDataUtil.getByEcDistributionRoomEntity(entity);
		HashMap<String, List<String>> map = OfflinePictureUtil.getRoomOfflinePic((entity).getObjId(), center);
		TableShowUtil.showTable(list, activity, NodeDeviceType.PDF.getName(), map, null, GlobalEntry.prjDbUrl,
				entity.getObjId(), null, null);
	}

	/**
	 * 显示变压器详情
	 * 
	 * @param entity
	 * @param center
	 * @param activity
	 */
	public static void showTransDetailed(EcTransformerEntity entity, OfflineAttachCenter center, Activity activity) {
		ArrayList<Object[]> list = TableDataUtil.getByEcTransformerEntity(entity);
		HashMap<String, List<String>> map = OfflinePictureUtil.getTransOfflinePic((entity).getObjId(), center);
		TableShowUtil.showTable(list, activity, NodeDeviceType.BYQ.getName(), map, null, GlobalEntry.prjDbUrl,
				entity.getObjId(), null, null);
	}

	/**
	 * 显示变电站详情
	 * 
	 * @param entity
	 * @param center
	 * @param activity
	 */
	public static void showStationDetailed(EcSubstationEntity entity, OfflineAttachCenter center, Activity activity) {
		ArrayList<Object[]> stationList = TableDataUtil.getByEcSubStationEntity(entity);
		HashMap<String, List<String>> map = OfflinePictureUtil.getStationOfflinePic((entity).getEcSubstationId(), center);
		TableShowUtil.showTable(stationList, activity, NodeDeviceType.BDZ.getName(), map, null, GlobalEntry.prjDbUrl,
				entity.getEcSubstationId(), null, null);
	}

	/**
	 * 显示线路详情
	 * 
	 * @param lineEntity
	 * @param activity
	 */
	public static void showLineDetailed(EcLineEntity lineEntity, Activity activity) {
		TableShowUtil.showTable(TableDataUtil.getByEcLineEntity(lineEntity), activity, "线路信息-" + lineEntity.getName(), null,
				null, GlobalEntry.prjDbUrl, lineEntity.getEcLineId(), null, null);
	}

	static HashMap<String, List<String>> getAttachmentFileMap(DataCenter dataCenter, String objid) {
		List<OfflineAttach> offlineAttachList = dataCenter.getOfflineAttachListByWorkNo(objid, null, null);
		HashMap<String, List<String>> imageMap = null;
		if (null != offlineAttachList && !offlineAttachList.isEmpty()) {
			imageMap = new HashMap<String, List<String>>();
			ArrayList<String> imageList = new ArrayList<String>();
			for (OfflineAttach attach : offlineAttachList) {
				imageList.add(attach.getFilePath());
			}
			imageMap.put("background_image", imageList);
		}
		return imageMap;
	}

	/**
	 * 显示环网柜详情
	 * 
	 * @param entity
	 * @param activity
	 * @param dataCenter
	 */
	public static void showHwgDetailed(EcHwgEntity entity, Activity activity, DataCenter dataCenter) {
		ArrayList<Object[]> list = TableDataUtil.getByEcHwgEntity(entity);
		TableShowUtil.showTable(list, activity, ResourceEnum.HWG.getName(),
				getAttachmentFileMap(dataCenter, entity.getObjId()), null, GlobalEntry.prjDbUrl, entity.getObjId(), null,
				null);
	}

	/**
	 * 显示箱式变电站详情
	 * 
	 * @param entity
	 * @param activity
	 * @param dataCenter
	 */
	public static void showXsbdzDetailed(EcXsbdzEntity entity, Activity activity, DataCenter dataCenter) {
		ArrayList<Object[]> list = TableDataUtil.getByEcXsbdzEntity(entity);
		TableShowUtil.showTable(list, activity, NodeDeviceType.XSBDZ.getName(),
				getAttachmentFileMap(dataCenter, entity.getObjId()), null, GlobalEntry.prjDbUrl, entity.getObjId(), null,
				null);
	}

	/**
	 * 显示电缆分支箱详情
	 * 
	 * @param entity
	 * @param activity
	 * @param dataCenter
	 */
	public static void showDlfzxDetailed(EcDlfzxEntity entity, Activity activity, DataCenter dataCenter) {
		ArrayList<Object[]> list = TableDataUtil.getByEcDlfzxEntity(entity);
		TableShowUtil.showTable(list, activity, ResourceEnum.DLFZX.getName(),
				getAttachmentFileMap(dataCenter, entity.getObjId()), null, GlobalEntry.prjDbUrl, entity.getObjId(), null,
				null);
	}

	/**
	 * 显示低压电缆分支箱详情
	 * 
	 * @param entity
	 * @param activity
	 * @param dataCenter
	 */
	public static void showDydlfzxDetailed(EcDydlfzxEntity entity, Activity activity, DataCenter dataCenter) {
		ArrayList<Object[]> list = TableDataUtil.getByEcDydlfzxEntity(entity);
		TableShowUtil.showTable(list, activity, ResourceEnum.DYDLFZX.getName(),
				getAttachmentFileMap(dataCenter, entity.getObjId()), null, GlobalEntry.prjDbUrl, entity.getObjId(), null,
				null);
	}

	/**
	 * 显示低压配电箱详情
	 * 
	 * @param entity
	 * @param activity
	 * @param dataCenter
	 */
	public static void showDypdxDetailed(EcDypdxEntity entity, Activity activity, DataCenter dataCenter) {
		ArrayList<Object[]> list = TableDataUtil.getByEcDypdxEntity(entity);
		TableShowUtil.showTable(list, activity, ResourceEnum.DYPDX.getName(),
				getAttachmentFileMap(dataCenter, entity.getObjId()), null, GlobalEntry.prjDbUrl, entity.getObjId(), null,
				null);
	}

	/**
	 * 显示开关站
	 * 
	 * @param entity
	 * @param activity
	 * @param dataCenter
	 */
	public static void showKgzDetailed(EcKgzEntity entity, Activity activity, DataCenter dataCenter) {
		ArrayList<Object[]> list = TableDataUtil.getByEcKgzEntity(entity);
		TableShowUtil.showTable(list, activity, NodeDeviceType.KGZ.getName(),
				getAttachmentFileMap(dataCenter, entity.getObjId()), null, GlobalEntry.prjDbUrl, entity.getObjId(), null,
				null);
	}

	/**
	 * 显示电缆管道
	 * 
	 * @param entity
	 * @param activity
	 */
	public static void showChannelDlgdDetailes(ChannelDataEntity entity, Activity activity) {
		ArrayList<Object[]> channelList = TableDataUtil.getByEcChannelEntity(entity.getData(), ResourceEnum.PG.getName(),
				entity.getLines(), entity.getStartNode(), entity.getEndNode());
		ArrayList<Object[]> data = TableDataUtil.getByEcChannelDlgd((EcChannelDlgdEntity) entity.getChannelType());
		// 合并
		ListUtil.copyList(channelList, data);
		showTable(channelList, null, activity, ResourceEnum.PG.getName(), null, entity, null, null, null, null, null);
	}

	/**
	 * 显示电缆隧道
	 * 
	 * @param entity
	 * @param activity
	 */
	public static void showChannelDlsdDetailes(ChannelDataEntity entity, Activity activity) {
		ArrayList<Object[]> channelList = TableDataUtil.getByEcChannelEntity(entity.getData(), ResourceEnum.SD.getName(),
				entity.getLines(), entity.getStartNode(), entity.getEndNode());
		ArrayList<Object[]> data = TableDataUtil.getByEcChannelDlsd((EcChannelDlsdEntity) entity.getChannelType());
		// 合并
		ListUtil.copyList(channelList, data);
		showTable(channelList, null,activity, ResourceEnum.SD.getName(), null, entity, null, null, null, null, null);
	}

	/**
	 * 显示电缆桥
	 * 
	 * @param entity
	 * @param activity
	 */
	public static void showChannelDlqDetailes(ChannelDataEntity entity, Activity activity) {
		ArrayList<Object[]> channelList = TableDataUtil.getByEcChannelEntity(entity.getData(), ResourceEnum.QJ.getName(),
				entity.getLines(), entity.getStartNode(), entity.getEndNode());
		ArrayList<Object[]> data = TableDataUtil.getByEcChannelDlq((EcChannelDlqEntity) entity.getChannelType());
		// 合并
		ListUtil.copyList(channelList, data);
		showTable(channelList, null, activity, ResourceEnum.QJ.getName(), null, entity, null, null, null, null, null);
	}

	/**
	 * 显示电缆沟
	 * 
	 * @param entity
	 * @param activity
	 */
	public static void showChannelDlgDetailes(ChannelDataEntity entity, Activity activity) {
		ArrayList<Object[]> channelList = TableDataUtil.getByEcChannelEntity(entity.getData(), ResourceEnum.GD.getName(),
				entity.getLines(), entity.getStartNode(), entity.getEndNode());
		ArrayList<Object[]> data = TableDataUtil.getByEcChannelDlg((EcChannelDlgEntity) entity.getChannelType());
		// 合并
		ListUtil.copyList(channelList, data);
		showTable(channelList, null, activity, ResourceEnum.GD.getName(), null, entity, null, null, null, null, null);
	}

	/**
	 * 显示顶管/拖拉管
	 * 
	 * @param entity
	 * @param activity
	 */
	public static void showChannelDgDetailes(ChannelDataEntity entity, Activity activity) {
		ArrayList<Object[]> channelList = TableDataUtil.getByEcChannelEntity(entity.getData(), ResourceEnum.TLG.getName(),
				entity.getLines(), entity.getStartNode(), entity.getEndNode());
		ArrayList<Object[]> data = TableDataUtil.getByEcChannelDg((EcChannelDgEntity) entity.getChannelType());
		// 合并
		ListUtil.copyList(channelList, data);
		showTable(channelList, null, activity, ResourceEnum.TLG.getName(), null, entity, null, null, null, null, null);
	}

	/**
	 * 显示电缆直埋
	 * 
	 * @param entity
	 * @param activity
	 */
	public static void showChannelDlzmDetailes(ChannelDataEntity entity, Activity activity) {
		ArrayList<Object[]> channelList = TableDataUtil.getByEcChannelEntity(entity.getData(), ResourceEnum.ZM.getName(),
				entity.getLines(), entity.getStartNode(), entity.getEndNode());
		ArrayList<Object[]> data = TableDataUtil.getByEcChannelDlzm((EcChannelDlzmEntity) entity.getChannelType());
		// 合并
		ListUtil.copyList(channelList, data);
		showTable(channelList, null, activity, ResourceEnum.ZM.getName(), null, entity, null, null, null, null, null);
	}
	
	/**
	 * 显示电缆槽
	 * 
	 * @param entity
	 * @param activity
	 */
	public static void showChannelDlcDetailes(ChannelDataEntity entity, Activity activity) {
		ArrayList<Object[]> channelList = TableDataUtil.getByEcChannelEntity(entity.getData(), ResourceEnum.DLC.getName(),
				entity.getLines(), entity.getStartNode(), entity.getEndNode());
		ArrayList<Object[]> data = TableDataUtil.getByEcChannelDlc((EcChannelDlcEntity) entity.getChannelType());
		// 合并
		ListUtil.copyList(channelList, data);
		showTable(channelList, null, activity, ResourceEnum.DLC.getName(), null, entity, null, null, null, null, null);
	}

	/**
	 * 显示架空
	 * 
	 * @param entity
	 * @param activity
	 */
	public static void showChannelJkDetailes(ChannelDataEntity entity, Activity activity) {
		ArrayList<Object[]> channelList = TableDataUtil.getByEcChannelEntity(entity.getData(), ResourceEnum.JKXL.getName(),
				entity.getLines(), entity.getStartNode(), entity.getEndNode());
		ArrayList<Object[]> data = TableDataUtil.getByEcChannelJk((EcChannelJkEntity) entity.getChannelType());
		// 合并
		ListUtil.copyList(channelList, data);
		showTable(channelList, null, activity, ResourceEnum.JKXL.getName(), null, entity, null, null, null, null, null);
	}

	/**
	 * 显示通道（管道、隧道、直埋、沟道、顶管、电缆桥、电缆槽）
	 * 
	 * @param entity
	 * @param activity
	 */
	public static void showChannel(ChannelDataEntity entity, Activity activity) {
		if (entity.getChannelType() instanceof EcChannelDlgdEntity) {
			// 电缆管道
			showChannelDlgdDetailes(entity, activity);
		}
		if (entity.getChannelType() instanceof EcChannelDlsdEntity) {
			// 电缆隧道
			showChannelDlsdDetailes(entity, activity);
		}
		if (entity.getChannelType() instanceof EcChannelDlqEntity) {
			// 电缆桥
			showChannelDlqDetailes(entity, activity);
		}
		if (entity.getChannelType() instanceof EcChannelDlgEntity) {
			// 电缆沟
			showChannelDlgDetailes(entity, activity);
		}
		if (entity.getChannelType() instanceof EcChannelDgEntity) {
			// 顶管/拖拉管
			showChannelDgDetailes(entity, activity);
		}
		if (entity.getChannelType() instanceof EcChannelDlzmEntity) {
			// 电缆直埋
			showChannelDlzmDetailes(entity, activity);
		}
		if (entity.getChannelType() instanceof EcChannelJkEntity) {
			// 架空
			showChannelJkDetailes(entity, activity);
		}
		if (entity.getChannelType() instanceof EcChannelDlcEntity){
			//电缆槽
			showChannelDlcDetailes(entity, activity);
		}
	}

	/**
	 * 显示井盖详情
	 * 
	 * @param entity
	 * @param well
	 * @param activity
	 */
	public static void showCover(EcWorkWellCoverEntity entity, EcWorkWellEntity well, Activity activity) {
		TableShowUtil.showTable(TableDataUtil.getByEcWorkWellCoverEntity(entity, well), activity, ResourceEnum.JG.getName(),
				null, null, GlobalEntry.prjDbUrl, entity.getObjId(), null, null);
	}

	/**
	 * 显示井盖详情
	 * 
	 * @param entity
	 * @param activity
	 */
	public static void showCover(EcWorkWellCoverEntity entity, DataCenter dataCenter, Activity activity) {
		DeviceEntity<EcWorkWellEntityVo> workWell = dataCenter.getWorkWell(entity.getSsgj(), null, null);
		TableShowUtil.showTable(
				TableDataUtil.getByEcWorkWellCoverEntity(entity, null == workWell ? null : workWell.getData()), activity,
				ResourceEnum.JG.getName(), null, null, GlobalEntry.prjDbUrl, entity.getObjId(), null, null);
	}
}
