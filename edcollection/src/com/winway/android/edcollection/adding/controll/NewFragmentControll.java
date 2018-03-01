package com.winway.android.edcollection.adding.controll;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.view.annotation.event.OnClick;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.DeviceReadQueryActivity;
import com.winway.android.edcollection.adding.activity.MarkerCollectActivity;
import com.winway.android.edcollection.adding.activity.SubstationActivity;
import com.winway.android.edcollection.adding.util.NewDeviceReadTool;
import com.winway.android.edcollection.adding.util.NewMarkWayTool;
import com.winway.android.edcollection.adding.util.NewZbsbTool;
import com.winway.android.edcollection.adding.util.OLocation;
import com.winway.android.edcollection.adding.util.SearchBusiness;
import com.winway.android.edcollection.adding.util.TableDataUtil;
import com.winway.android.edcollection.adding.util.TableShowUtil;
import com.winway.android.edcollection.adding.util.TreeBusiness;
import com.winway.android.edcollection.adding.util.TreeMapUtil;
import com.winway.android.edcollection.adding.viewholder.NewFragmentViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.util.CoordsUtils;
import com.winway.android.edcollection.base.view.BaseFragmentControll;
import com.winway.android.edcollection.colist.controll.CollectedFragmentControll;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.main.bll.MainBll;
import com.winway.android.edcollection.main.controll.MainControll;
import com.winway.android.edcollection.map.EcollectMapUtils;
import com.winway.android.edcollection.map.SpatialAnalysis;
import com.winway.android.edcollection.map.TestDraw;
import com.winway.android.edcollection.map.entity.MapOperatorFlag;
import com.winway.android.edcollection.map.entity.SpatialAnalysisDataType;
import com.winway.android.edcollection.map.util.CombineSpatialDatasUtil;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.rtk.entity.RtkConstant;
import com.winway.android.map.MapLoader;
import com.winway.android.map.entity.MapCache;
import com.winway.android.map.util.ChangeMapUtils;
import com.winway.android.map.util.MapToolUtils;
import com.winway.android.map.util.MapToolUtils.MapToolCallback;
import com.winway.android.map.util.MapUtils;
import com.winway.android.sensor.geolocation.GPS;
import com.winway.android.sensor.marker.MarkerReadActivity;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.BroadcastReceiverUtils;
import com.winway.android.util.DensityUtil;
import com.winway.android.util.FileUtil;
import com.winway.android.util.LogUtil;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import ocn.himap.base.HiCoordinate;
import ocn.himap.datamanager.HiElement;

/**
 * 新增
 * 
 * @author zgq
 *
 */
public class NewFragmentControll extends BaseFragmentControll<NewFragmentViewHolder> {
	/** 数据中心 */
	private DataCenter dataCenter = null;
	private String projectBDUrl = null;
	private TreeMapUtil treeMapUtil = null;

	// 搜索业务
	SearchBusiness searchBSS;
	// 台账树业务
	TreeBusiness treeBSS;

	private GlobalCommonBll globalBLL = null;
	private double[] xy;
	private EcSubstationEntityVo substationEntityVo;
	private EcSubstationEntityVo ecSubstationEntityVo;
	private MainBll mainBll = null;
	private ComUploadBll comUploadBll;
	private CommonBll commonBll;
	public static final int REQUEST_CODE_MARKER_READ = 999;
	private OfflineAttachCenter center;

	@Override
	public void init() {
		// 初始化创建单独变电站需要的对象
		String projectBDUrl = GlobalEntry.prjDbUrl;
		mainBll = new MainBll(mActivity, projectBDUrl);
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		commonBll = new CommonBll(mActivity, projectBDUrl);
		center = new OfflineAttachCenter(mActivity, projectBDUrl);
		MapCache.map = viewHolder.getMap();
		// 初始化地图 成都（103.94620595594148,30.768282197451732）
		MapLoader.getInstance().initMap(mActivity, MapCache.map,
				FileUtil.AppRootPath + File.separator + "winway_maps" + File.separator + "maps/",
				FileUtil.AppRootPath + File.separator + "winway_maps" + File.separator + "cache/", 113.39, 22.52, 10);
		// 添加注记图层
		MapLoader.getInstance().addTDTTextLayer(MapCache.map);
		MapLoader.getInstance().addLayer(MapCache.map);
		initSetting();
		initEvent();
		projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		// 数据中心
		dataCenter = new DataCenterImpl(mActivity, projectBDUrl);
		// 树节点点击与地图交互工具类
		treeMapUtil = TreeMapUtil.getInstance(dataCenter, mActivity);
		((DataCenterImpl) dataCenter).setUseCache(true);
		// 台账树业务
		treeBSS = new TreeBusiness(mActivity, viewHolder);
		treeBSS.init(dataCenter, treeMapUtil);
		// 搜索业务
		searchBSS = new SearchBusiness(viewHolder, mActivity, projectBDUrl);
		searchBSS.init(treeMapUtil);
		// 表格数据
		TableDataUtil.treeDataCenter = (DataCenterImpl) dataCenter;
		if (null == TableDataUtil.globalBLL) {
			globalBLL = new GlobalCommonBll(mActivity, FileUtil.AppRootPath + "/db/common/global.db");
			TableDataUtil.globalBLL = globalBLL;
		}
		// 添加地图事件
		EcollectMapUtils.getInstance(mActivity).addMapListener();
		initDatas();

		/**
		 * XXX 打开定位(执行打开定位的代码程序会产生异常，以致下面的代码无法执行，请修复，在修复此BUG之前， init方法中的所有代码必须要打开定位之前)
		 */
		viewHolder.getLocation().performClick();

	}

	private void initSetting() {
		int padding = DensityUtil.dip2px(mActivity, 5);
		viewHolder.getMapSwitch().setPadding(padding, padding, padding, padding);
		viewHolder.getMapMeasure().setPadding(padding, padding, padding, padding);
		viewHolder.getMapSwitch().setScaleType(ScaleType.FIT_XY);
		viewHolder.getMapMeasure().setScaleType(ScaleType.FIT_XY);
	}

	// 初始化
	private void initDatas() {
		// TODO Auto-generated method stub
		viewHolder.getGifViewRtk().setGifImage(R.drawable.rtk_s);
		viewHolder.getGifViewRtk().setShowDimension(80, 80);

		// 初始化工程名称
		viewHolder.getTvPrjName().setText("当前工程：" + GlobalEntry.currentProject.getPrjName());

		// 注册广播
		BroadcastReceiverUtils.getInstance().registReceiver(mActivity, RtkConstant.flag_rtk_on, bReceiver);
		BroadcastReceiverUtils.getInstance().registReceiver(mActivity, RtkConstant.flag_rtk_off, bReceiver);

	}

	private void initEvent() {
		viewHolder.getZoomIn().setOnClickListener(orcl);
		viewHolder.getZoomOut().setOnClickListener(orcl);
		viewHolder.getMapSwitch().setOnClickListener(orcl);
		viewHolder.getMapMeasure().setOnClickListener(orcl);
		viewHolder.getLocation().setOnClickListener(orcl);
		viewHolder.getBtnShowWay().setOnClickListener(orcl);
		viewHolder.getBtnZbsb().setOnClickListener(orcl);
		viewHolder.getBtnMainItemTransferOk().setOnClickListener(orcl);
		viewHolder.getBtnMainItemTransferCancel().setOnClickListener(orcl);
		viewHolder.getBtnTree().setOnClickListener(orcl);
		viewHolder.getBtnLableReadQuery().setOnClickListener(orcl);
	}

	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case com.winway.android.map.R.id.imgBtn_map_zoomIn: {// 放大
				MapUtils.getInstance().zoomIn();
				break;
			}

			case com.winway.android.map.R.id.imgBtn_map_zoomOut: {// 缩小
				MapUtils.getInstance().zoomOut();
				break;
			}

			case com.winway.android.map.R.id.imgBtn_map_mapSwitch: {// 地图切换
				ChangeMapUtils.getInstance().changeMap(mActivity, viewHolder.getMapSwitch());
				break;
			}

			case com.winway.android.map.R.id.imgBtn_map_measure: {// 地图工具入口
				MapToolUtils.getInstance().mapTool(mActivity, viewHolder.getMapMeasure(),
						viewHolder.getTvMeasureResult());
				MapToolUtils.getInstance().setMapToolCallback(new MapToolCallbackImpl());
				break;
			}
			case com.winway.android.map.R.id.imgBtn_map_location: {// 地图定位
				if (!OLocation.getInstance(mActivity).hasGPSDevice()) {
					ToastUtil.show(mActivity, "该设备不支持GPS模块", 200);
					return;
				}
				// 如果rtk有位置信息，就以rtk位置信息定位
				if (GlobalEntry.rtkLocationInfo != null && GlobalEntry.rtkLocationInfo.getLon() != null
						&& GlobalEntry.rtkLocationInfo.getLat() != null) {

					OLocation.getInstance(mActivity).startLocation(GlobalEntry.rtkLocationInfo.getLon(),
							GlobalEntry.rtkLocationInfo.getLat());
					return;
				}

				if (GPS.getInstance(mActivity).open()) {
					OLocation.getInstance(mActivity).startLocation();
				} else {
					ToastUtil.show(mActivity, "获取当前位置异常,请检查GPS信号", 200);
				}

				break;
			}
			case R.id.imgBtn_fragment_new_way:// 标识器显示方式
			{
				NewMarkWayTool.getInstance().markShowTool(mActivity.getApplicationContext(), (ImageButton) v);
				NewZbsbTool.getInstance().closeZbss(mActivity, viewHolder.getBtnZbsb(), viewHolder.getViewRadar());
				NewDeviceReadTool.getInstance(mActivity).closePopWid();
				break;
			}
			case R.id.imgBtn_fragment_new_zbsb:// 查找周边
			{
				NewZbsbTool.getInstance().openZbsbTool(mActivity, viewHolder.getBtnZbsb(), viewHolder.getViewRadar());
				NewMarkWayTool.getInstance().closePopWid();
				// 注册广播
				BroadcastReceiverUtils.getInstance().registReceiver(mActivity,
						MapOperatorFlag.flag_zbcx_map_level_change, bReceiver);
				break;
			}
			case R.id.btn_main_item_transfer_ok: {// 确定
				if (SubstationControll.isNewSubstation) {
					Intent intent = new Intent(mActivity, SubstationActivity.class);
					intent.putExtra("coord", MapUtils.getInstance().getMapCenter());
					Bundle bundle = new Bundle();
					bundle.putSerializable("EcSubstationEntity", MainControll.ecSubstationEntityVo);
					intent.putExtras(bundle);
					mActivity.startActivityForResult(intent, MainControll.requestCode_subtation);
					SubstationControll.mapSelectCoord = true;
					SubstationControll.isNewSubstation = true;
					viewHolder.getIncludeMainItemTransfer().setVisibility(View.GONE);
					break;
				}
				handleTransferOk();
				break;
			}
			case R.id.btn_main_item_transfer_cancel: {// 取消
				if (SubstationControll.isNewSubstation) {
					Intent intent = new Intent(mActivity, SubstationActivity.class);
					intent.putExtra("coord", xy);
					intent.putExtra("cancel", "cancel");
					Bundle bundle = new Bundle();
					bundle.putSerializable("EcSubstationEntity", MainControll.ecSubstationEntityVo);
					intent.putExtras(bundle);
					mActivity.startActivityForResult(intent, MainControll.requestCode_subtation);
					SubstationControll.mapSelectCoord = true;
					SubstationControll.isNewSubstation = true;
					viewHolder.getIncludeMainItemTransfer().setVisibility(View.GONE);
					break;
				} else if (EcollectMapUtils.isMapGpsEdit) {
					viewHolder.getIncludeMainItemTransfer().setVisibility(View.GONE);
					EcollectMapUtils.isMapGpsEdit = false;
					break;
				}
				handleTransferCancel();
				break;
			}
			case R.id.imgBtn_fragment_lableReadQuery: { // 标签读取查询
				NewDeviceReadTool.getInstance(mActivity).markShowTool(mActivity.getApplicationContext(),
						(ImageButton) v);
				NewMarkWayTool.getInstance().closePopWid();
				/*
				 * Intent intent = new Intent(mActivity,DeviceReadQueryActivity.class);
				 * mActivity.startActivityForResult(intent,
				 * MainControll.requestCode_devicequery);
				 */
			}
			default:
				break;
			}
		}
	};

	/**
	 * 处理中转确定按钮
	 */
	private void handleTransferOk() {
		if (GlobalEntry.editNode == true) {// 如果是编辑功能使用
			Intent intent = new Intent(mActivity, MarkerCollectActivity.class);
			intent.putExtra("EcNodeEntity", GlobalEntry.currentEditNode);
			if (GlobalEntry.editMapSelectCoord == true) {// 编辑下地图选点
				intent.putExtra("coord", MapUtils.getInstance().getMapCenter());
			}
			AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, MainControll.flag_marker_edit);

		} else {
			Intent intent = new Intent(mActivity, MarkerCollectActivity.class);
			intent.putExtra("coord", MapUtils.getInstance().getMapCenter());
			AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, MainControll.flag_marker_collect);
		}

		// 隐藏
		viewHolder.getIncludeMainItemTransfer().setVisibility(View.GONE);
		GlobalEntry.editMapSelectCoord = false;
	}

	/**
	 * 处理中转取消按钮
	 */
	private void handleTransferCancel() {
		if (GlobalEntry.editNode == true) {// 如果是编辑功能使用
			Intent intent = new Intent(mActivity, MarkerCollectActivity.class);
			intent.putExtra("EcNodeEntity", GlobalEntry.currentEditNode);
			AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, MainControll.flag_marker_edit);

		} else {
			Intent intent = new Intent(mActivity, MarkerCollectActivity.class);
			AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, MainControll.flag_marker_collect);
		}

		// 隐藏
		viewHolder.getIncludeMainItemTransfer().setVisibility(View.GONE);
		GlobalEntry.editMapSelectCoord = false;
	}

	// 广播
	private BroadcastReceiver bReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals(MapOperatorFlag.flag_zbcx_map_level_change)) {// 地图等级改变(周边查询)
				viewHolder.getBtnZbsb().performClick();
			} else if (action.equals(RtkConstant.flag_rtk_on)) {// 开启闪烁
				viewHolder.getGifViewRtk().setVisibility(View.VISIBLE);
				viewHolder.getImgBtnRtk().setVisibility(View.GONE);
			} else if (action.equals(RtkConstant.flag_rtk_off)) {// 停止闪烁
				viewHolder.getGifViewRtk().setVisibility(View.GONE);
				viewHolder.getImgBtnRtk().setVisibility(View.VISIBLE);
			}

		}
	};

	@OnClick(R.id.search_result_container_close)
	public void closeSearchResult(View view) {
		viewHolder.getSearchResultContainer().setVisibility(View.GONE);
	}

	@OnClick(R.id.search_result_back)
	public void backSearchResult(View view) {
		viewHolder.getKindContainer().setVisibility(View.VISIBLE);
		viewHolder.getSearchResultLV().setVisibility(View.GONE);
		viewHolder.getBtnBack().setVisibility(View.GONE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == MainControll.flag_marker_collect || requestCode == MainControll.flag_marker_edit) {
				if (null != data) {
					EcNodeEntity historyNode = (EcNodeEntity) data.getSerializableExtra("historyNode");
					if (null != historyNode) {
						editHistoryNodeInfo(historyNode);
					}
				} else {
					viewHolder.getIncludeMainItemTransfer().setVisibility(View.VISIBLE);
				}
			} else if (requestCode == MainControll.requestCode_subtation) {// 变电站
				ecSubstationEntityVo = (EcSubstationEntityVo) data.getSerializableExtra("sub");
				double[] coord = data.getDoubleArrayExtra("coord");
				if (coord == null) {
					if (ecSubstationEntityVo != null) {
						saveSubstation(mainBll, comUploadBll, ecSubstationEntityVo);
						ecSubstationEntityVo = null;
					}
				} else {
					saveSubstation(mainBll, comUploadBll, ecSubstationEntityVo);
					saveBasicInfo(coord, mainBll);
					ecSubstationEntityVo = null;
				}
			}
		} else if (resultCode == SubstationControll.mapSelectPoint) {
			viewHolder.getIncludeMainItemTransfer().setVisibility(View.VISIBLE);
			if (data != null) {
				xy = data.getDoubleArrayExtra("coord");
			}
		} else if (requestCode == REQUEST_CODE_MARKER_READ && data != null) {
			// 标识器读取
			String markerID = data.getStringExtra(MarkerReadActivity.INTENT_KEY_MARKER_ID);
			if (markerID != null) {
				List<EcNodeEntity> nodeEctityList = commonBll.getNodeEctityList(markerID);
				EcNodeEntity ecNodeEntity = nodeEctityList.get(0);
				GlobalEntry.currentEditNode = ecNodeEntity;
				TableShowUtil.showNodeDetailed(ecNodeEntity, null, center, mActivity);
			}
		}

	};

	/**
	 * 编辑历史信息
	 * 
	 * @param node
	 */
	private void editHistoryNodeInfo(final EcNodeEntity node) {
		final Handler handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				GlobalEntry.editNode = true;
				Intent data = new Intent(mActivity, MarkerCollectActivity.class);
				data.putExtra("EcNodeEntity", node);
				mActivity.startActivityForResult(data, MainControll.flag_marker_edit);
				return false;
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					handler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void onResume() {
		if (CollectedFragmentControll.isCheckMapBtn) {
			viewHolder.getIncludeMainItemTransfer().setVisibility(View.VISIBLE);
			CollectedFragmentControll.isCheckMapBtn = false;
		}
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			mActivity.unregisterReceiver(bReceiver);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private List<HiElement> pointElementList = null;

	// 地图工具操作回调
	class MapToolCallbackImpl implements MapToolCallback {

		@Override
		public void polygonCallback(ArrayList<HiCoordinate> hiCoordinates) {
			// TODO Auto-generated method stub
			ArrayList<String> pointNameArr = new ArrayList<String>();
			pointNameArr.add("point");
			pointElementList = SpatialAnalysis.getInstance().searchByPolygon(hiCoordinates, pointNameArr);
			// 绘制元素
			SpatialAnalysis.getInstance().drawPoints(pointElementList, mActivity);
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			if (pointElementList != null) {
				SpatialAnalysis.getInstance().removePoints(pointElementList);
			}

		}

	}

	private void saveSubstation(MainBll mainBll, ComUploadBll comUploadBll, EcSubstationEntityVo ecSubstationEntityVo) {
		mainBll.saveOrUpdate(ecSubstationEntityVo);
		// 保存变电站附件
		List<OfflineAttach> ComUploadEntitySubList = ecSubstationEntityVo.getComUploadEntityList();
		if (ComUploadEntitySubList != null && ComUploadEntitySubList.size() > 0) {
			for (int i = 0; i < ComUploadEntitySubList.size(); i++) {
				OfflineAttach offlineAttach = ComUploadEntitySubList.get(i);
				// 保存附件
				comUploadBll.saveOrUpdate(offlineAttach);
			}

		}
	}

	private void saveBasicInfo(double[] coord, MainBll mainBll) {
		// TODO Auto-generated method stub
		String geom = CoordsUtils.getInstance().coord2Geom(coord);
		EcNodeEntity ecNodeEntity = new EcNodeEntity();
		ecNodeEntity.setGeom(geom);
		ecNodeEntity.setMarkerType(0);
		String randomUUID = UUIDGen.randomUUID();
		randomUUID = randomUUID.substring(0, 12);
		ecNodeEntity.setMarkerNo(randomUUID);
		ecNodeEntity.setOid(UUIDGen.randomUUID());
		ecNodeEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
		ecNodeEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
		mainBll.saveOrUpdate(ecNodeEntity);
		// 添加元素到数据集中
		double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
		HiElement hiElement = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, ecNodeEntity,
				ecNodeEntity.getOid());
		SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElement,
				ecNodeEntity);
		// 保存操作日志
		if (GlobalEntry.editNode) {// 编辑
			commonBll.handleDataLog(ecNodeEntity.getOid(), TableNameEnum.LJD.getTableName(), DataLogOperatorType.update,
					WhetherEnum.NO, "编辑节点：" + ecNodeEntity.getMarkerNo(), false);
		} else {
			commonBll.handleDataLog(ecNodeEntity.getOid(), TableNameEnum.LJD.getTableName(), DataLogOperatorType.Add,
					WhetherEnum.NO, "新增节点：" + ecNodeEntity.getMarkerNo(), true);
		}
		if (ecSubstationEntityVo != null) {
			EcNodeDeviceEntity ecNodeDeviceEntity = mainBll.combineNodeDevice(ResourceEnum.BDZ,
					ecSubstationEntityVo.getEcSubstationId(), ecNodeEntity.getOid());
			mainBll.save(ecNodeDeviceEntity);
			commonBll.handleDataLog(ecSubstationEntityVo.getEcSubstationId(), TableNameEnum.BDZ.getTableName(),
					DataLogOperatorType.Add, WhetherEnum.NO, "新增：" + ecSubstationEntityVo.getName(), true);
			commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
					DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecSubstationEntityVo.getName(), true);
		}
	}

}
