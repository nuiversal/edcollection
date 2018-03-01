package com.winway.android.edcollection.adding.controll;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ocn.himap.datamanager.HiElement;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.location.LocationClient;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.FeaturePointAdapter;
import com.winway.android.edcollection.adding.bll.BasicInfoBll;
import com.winway.android.edcollection.adding.bll.CollectFragmentCacheBll;
import com.winway.android.edcollection.adding.bll.DataCacheBll;
import com.winway.android.edcollection.adding.entity.BLocationListener;
import com.winway.android.edcollection.adding.entity.DeviceDesc;
import com.winway.android.edcollection.adding.entity.InstallPosition;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.adding.entity.NodeRemark;
import com.winway.android.edcollection.adding.entity.UpMarkerCache;
import com.winway.android.edcollection.adding.fragment.LayingDitchFragment;
import com.winway.android.edcollection.adding.util.FragmentEntry;
import com.winway.android.edcollection.adding.util.MarkerIDWatcher;
import com.winway.android.edcollection.adding.util.RtkInfoDialog;
import com.winway.android.edcollection.adding.util.RtkInfoDialog.ConfirmCallBack;
import com.winway.android.edcollection.adding.util.SelectCollectObjPopWindow;
import com.winway.android.edcollection.adding.viewholder.BasicInfoViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.util.CoordsUtils;
import com.winway.android.edcollection.base.view.BaseFragmentControll;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.entity.NodeDevicesEntity;
import com.winway.android.edcollection.map.SpatialAnalysis;
import com.winway.android.edcollection.map.entity.SpatialAnalysisDataType;
import com.winway.android.edcollection.map.util.CombineSpatialDatasUtil;
import com.winway.android.edcollection.project.bll.ComUploadBll;
import com.winway.android.edcollection.project.entity.EcMarkerIdsEntity;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcDypdxEntityVo;
import com.winway.android.edcollection.project.vo.EcKgzEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.edcollection.project.vo.EcXsbdzEntityVo;
import com.winway.android.sensor.marker.MarkerReadActivity;
import com.winway.android.sensor.nfc.bluetoothnfc.BluetoothNFC_DK_Activity;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.BroadcastReceiverUtils;
import com.winway.android.util.LogUtil;
import com.winway.android.util.NumberUtils;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 标识器采集——基本信息业务处理
 * 
 * @author lyh
 * @version 创建时间：2016年12月14日 上午9:43:29
 * 
 */
public class BasicInfoControll extends BaseFragmentControll<BasicInfoViewHolder> {

	private BasicInfoBll basicInfoBll;
	private DataCacheBll dataCacheBll;
	private DataCenter dataCenter = null;
	private CommonBll commonBll;
	public static LocationClient mLocationClient = null;
	private boolean featureIsOpen = false;// 是否显示特征点列表，默认不显示

	public static EcSubstationEntityVo ecSubstationEntityVo;// 变电站
	public static EcTowerEntityVo ecTowerEntityVo;// 杆塔
	public static EcTransformerEntityVo ecTransformerEntityVo;// 变压器
	public static EcDistributionRoomEntityVo ecDistributionRoomEntityVo;// 配电房
	public static EcXsbdzEntityVo ecXsbdzEntityVo;// 箱式变电站
	public static EcKgzEntityVo ecKgzEntityVo;// 开关站
	public static EcDypdxEntityVo ecDypdxEntityVo;// 低压配电箱
	public static EcWorkWellEntityVo ecWorkWellEntityVo = null;// 工井

	public static final int requestCode_substation = 1;
	public static final int requestCode_tower = 2;
	public static final int requestCode_transformer = 3;
	public static final int requestCode_distributionRoom = 4;
	public static final int requestCode_workWell = 5;
	public static final int requestCode_xsbdz = 6;
	public static final int requestCode_kgz = 7;
	public static final int requestCode_dypdx = 8;
	public static final int ajh_radiobutton_id = 4;
	public static String flag_geoLocation = "flag_geoLocation";// 百度地图获取poi位置
	public static String flag_districtLocation = "flag_districtLocation";// 百度地图获取县区街道位置
	private int REQUEST_AJH_ID2 = 300;// 高频nfc
	private int REQUEST_AJH_ID3 = 400;// 高频蓝牙
	private String editOid;// 记录编辑的oid
	public static String initEditMarkerNo = null;// 编辑操作初始编号
	public static RtkInfoDialog rtkInfoDialog;
	private FeaturePointAdapter featurePointAdapter;// 特征点列表适配器
	/** 百度定位地址 */
	public static String BMapLocationAddrStr = null;
	/** 百度定位区县 */
	public static String BMaplocationDistrict = null;
	/** 百度定位街道 */
	public static String BMaplocationStreet = null;
	/** 百度定位城市 */
	public static String BMaplocationCity = null;
	/** 标识器编号 */
	public static String markerNo = null;
	public static int markerType;// 标识类型
	private ComUploadBll comUploadBll;

	public static boolean isWrittenMarkerNo = false;

	@Override
	public void init() {
		String projectBDUrl = GlobalEntry.prjDbUrl;
		basicInfoBll = new BasicInfoBll(mActivity, projectBDUrl);
		dataCacheBll = new DataCacheBll(mActivity, GlobalEntry.dataCacheDbUrl);
		commonBll = new CommonBll(mActivity, projectBDUrl);
		comUploadBll = new ComUploadBll(mActivity, projectBDUrl);
		dataCenter = new DataCenterImpl(mActivity, projectBDUrl);
		// 初始化百度地图定位
		mLocationClient = basicInfoBll.initBaiduMapLocation(new BLocationListener(mActivity), mActivity);
		initCommonDatas();
		if (GlobalEntry.editNode == true) {
			initEditDatas();
		} else {
			initDatas();
		}
		initEvent();
	}

	/**
	 * 初始化新增和编辑通用的
	 */
	private void initCommonDatas() {
		if (basicInfoBll.isNetworkConnected(mActivity)) {
			mLocationClient.start();// 开始定位
			// 注册广播
			BroadcastReceiverUtils.getInstance().registReceiver(mActivity, flag_geoLocation, geoLocationReceiver);
		}
		viewHolder.getIcAltitude().getEditTextView().setEnabled(false);
		// 初始化敷设方式下拉列表
		// String[] stringArray =
		// mActivity.getResources().getStringArray(R.array.stateGridType);
		// List<String> layTypes = new ArrayList<String>(Arrays.asList(stringArray));
		// final InputSelectAdapter layTypeAdapter = new InputSelectAdapter(mActivity,
		// layTypes);
		// viewHolder.getIscLayType().setAdapter(layTypeAdapter);
		// 初始化安转位置下拉数据
		List<InstallPosition> installPositions = dataCacheBll.getDataCache(InstallPosition.class);
		ArrayList<String> installPositionDatas = new ArrayList<String>();
		if (installPositions != null) {
			for (InstallPosition installPosition : installPositions) {
				installPositionDatas.add(installPosition.getName());
				InputSelectAdapter adapter = new InputSelectAdapter(mActivity, installPositionDatas);
				viewHolder.getIscInstallPath().setAdapter(adapter);
			}
		}
		// 初始化设备描述下拉数据
		List<DeviceDesc> deviceDescs = dataCacheBll.getDataCache(DeviceDesc.class);
		if (deviceDescs != null) {
			ArrayList<String> deviceDescsList = new ArrayList<String>();
			for (DeviceDesc deviceDesc : deviceDescs) {
				deviceDescsList.add(deviceDesc.getName());
			}
			InputSelectAdapter deviceDescAdapter = new InputSelectAdapter(mActivity, deviceDescsList);
			viewHolder.getIscDescribe().setAdapter(deviceDescAdapter);
		}
		// 初始化其他信息
		List<NodeRemark> remarks = dataCacheBll.getDataCache(NodeRemark.class);
		if (remarks != null) {
			ArrayList<String> nodeRemarkList = new ArrayList<String>();
			for (NodeRemark nodeRemark : remarks) {
				nodeRemarkList.add(nodeRemark.getName());
			}
			InputSelectAdapter remarkAdapter = new InputSelectAdapter(mActivity, nodeRemarkList);
			viewHolder.getIscOther().setAdapter(remarkAdapter);
		}
		// 初始化特征点显示
		ArrayList<ResourceEnum> featurePointList = basicInfoBll.getFeaturePointList();
		featurePointAdapter = new FeaturePointAdapter(mActivity, featurePointList, mLocationClient, basicInfoBll);
		viewHolder.getListViewFeaturePoint().setAdapter(featurePointAdapter);
		viewHolder.getListViewFeaturePoint().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// 初始化intent中传递过来的数据(处理地图选点情况)
		Intent intent = mActivity.getIntent();
		double[] xy = intent.getDoubleArrayExtra("coord");
		if (xy != null) {
			viewHolder.getIcLongitude().setEdtText(NumberUtils.format(xy[0], 10));
			viewHolder.getIcLatitude().setEdtText(NumberUtils.format(xy[1], 10));
			FragmentEntry.getInstance().ecNodeEntityCache
					.setLongitude(Double.parseDouble(NumberUtils.format(xy[0], 10)));
			FragmentEntry.getInstance().ecNodeEntityCache
					.setLatitude(Double.parseDouble(NumberUtils.format(xy[1], 10)));
		}
		// 设置输入框的类型
		viewHolder.getIcWidth().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		viewHolder.getIcFlootHeight().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		viewHolder.getIcLongitude().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		viewHolder.getIcAltitude().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		viewHolder.getIcLatitude().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		viewHolder.getIcMarkerId().setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
		viewHolder.getIcMarkerId().getEditTextView().setText("000-");
		// 限制标识器id不超过12位
		viewHolder.getIcMarkerId().getEditTextView().setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
		// 限制用户输入敷设方式
		// viewHolder.getIscLayType().getEditTextView().setEnabled(false);
	}

	/**
	 * 初始化编辑操作的数据
	 */
	private void initEditDatas() {
		Intent intent = getIntent();
		EcNodeEntity ecNodeEntity = (EcNodeEntity) intent.getSerializableExtra("EcNodeEntity");
		BMapLocationAddrStr = ecNodeEntity.getGeoLocation();// 地址
		editOid = ecNodeEntity.getOid();
		initEditMarkerNo = ecNodeEntity.getMarkerNo();
		// TODO
		// 初始化标识类型
		String[] nodeType = mActivity.getResources().getStringArray(R.array.nodeType);
		List<String> nodeTypes = new ArrayList<String>(Arrays.asList(nodeType));
		basicInfoBll.addMarkTypes(viewHolder.getRgMarkerType(), nodeTypes);
		Integer markType = ecNodeEntity.getMarkerType();
		if (markType.equals(NodeMarkerType.BSQ.getValue())) {
			viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(0).getId());
		} else if (markType.equals(NodeMarkerType.BSD.getValue())) {
			viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(1).getId());
		} else if (markType.equals(NodeMarkerType.XND.getValue())) {
			viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(2).getId());
		} else if (markType.equals(NodeMarkerType.GT.getValue())) {
			viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(3).getId());
		} else if (markType.equals(NodeMarkerType.AJH.getValue())) {
			viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(4).getId());
		}
		// 标识器id号
		viewHolder.getIcMarkerId().setEdtText(ecNodeEntity.getMarkerNo());
		// 经纬度
		double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
		viewHolder.getIcLongitude().setEdtText(xy[0] + "");
		viewHolder.getIcLatitude().setEdtText(xy[1] + "");
		// 高程
		viewHolder.getIcAltitude().setEdtText(StringUtils.nullStrToEmpty(ecNodeEntity.getAltitude()));
		viewHolder.getIcCoordNo().setEdtText(StringUtils.nullStrToEmpty(ecNodeEntity.getCoordinateNo()));
		// viewHolder.getIscLayType().setEdtTextValue(ecNodeEntity.getLayType());
		// 安装位置、地理位置
		viewHolder.getIscInstallPath().setEdtTextValue(StringUtils.nullStrToEmpty(ecNodeEntity.getInstallPosition()));
		viewHolder.getIcGeographyPos().setEdtText(StringUtils.nullStrToEmpty(ecNodeEntity.getGeoLocation()));
		// 宽、高
		viewHolder.getIcWidth().setEdtText(StringUtils.nullStrToEmpty(ecNodeEntity.getCableWidth()));
		viewHolder.getIcFlootHeight().setEdtText(StringUtils.nullStrToEmpty(ecNodeEntity.getCableDeepth()));
		// 描述、其他
		viewHolder.getIscDescribe().setEdtTextValue(StringUtils.nullStrToEmpty(ecNodeEntity.getDeviceDesc()));
		viewHolder.getIscOther().setEdtTextValue(StringUtils.nullStrToEmpty(ecNodeEntity.getRemark()));
		// 查找路径点设备
		NodeDevicesEntity data = dataCenter.getPathNodeDetails(ecNodeEntity.getOid(), null, null);
		// 路径点设备-变电站
		if (ecSubstationEntityVo != null) {// 地图选点回来，恢复勾选,则显示缓存数据
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.BDZ, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		} else { // 没有缓存数据，则显示数据库中数据
			List<DeviceEntity<EcSubstationEntityVo>> listSubs = data.getStation();
			if (listSubs != null && listSubs.size() > 0) {
				EcSubstationEntityVo ecSubstationEntityVo = listSubs.get(0).getData();
				ecSubstationEntityVo.setOperateMark(2);// 编辑下的编辑操作
				BasicInfoControll.ecSubstationEntityVo = ecSubstationEntityVo;
				// 勾选
				CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.BDZ,
						viewHolder.getListViewFeaturePoint());
				// 获取变电站的附件
				List<OfflineAttach> offlineAttachs = dataCenter.getOfflineAttachListByWorkNo(
						BasicInfoControll.ecSubstationEntityVo.getEcSubstationId(), null, null);
				BasicInfoControll.ecSubstationEntityVo.setComUploadEntityList(offlineAttachs);
			}
		}
		// 路径点设备-箱式变电站
		if (ecXsbdzEntityVo != null) {// 地图选点回来，恢复勾选,则显示缓存数据
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.XSBDZ,
					viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		} else {// 没有缓存数据，则显示数据库中数据
			List<DeviceEntity<EcXsbdzEntityVo>> xsbdzList = data.getXsbdz();
			if (xsbdzList != null && xsbdzList.size() > 0) {
				EcXsbdzEntityVo ecXsbdzEntity = xsbdzList.get(0).getData();
				ecXsbdzEntity.setOperateMark(2);// 编辑下的编辑操作
				BasicInfoControll.ecXsbdzEntityVo = ecXsbdzEntity;
				// 勾选
				CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.XSBDZ,
						viewHolder.getListViewFeaturePoint());
				// 获取箱式变电站的附件
				List<OfflineAttach> offlineAttachs = dataCenter
						.getOfflineAttachListByWorkNo(BasicInfoControll.ecXsbdzEntityVo.getObjId(), null, null);
				BasicInfoControll.ecXsbdzEntityVo.setComUploadEntityList(offlineAttachs);
			}
		}
		// 路径点设备-开关站
		if (ecKgzEntityVo != null) {// 地图选点回来，恢复勾选，则显示缓存数据
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.KGZ, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		} else {// 没有缓存数据，则显示数据库中数据
			List<DeviceEntity<EcKgzEntityVo>> listKgz = data.getKgz();
			if (listKgz != null && listKgz.size() > 0) {
				EcKgzEntityVo ecKgzEntityVo = listKgz.get(0).getData();
				ecKgzEntityVo.setOperateMark(2);// 编辑下的编辑操作
				BasicInfoControll.ecKgzEntityVo = ecKgzEntityVo;
				// 勾选
				CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.KGZ,
						viewHolder.getListViewFeaturePoint());
				// 获取开关站的附件
				List<OfflineAttach> offlineAttachs = dataCenter
						.getOfflineAttachListByWorkNo(BasicInfoControll.ecKgzEntityVo.getObjId(), null, null);
				BasicInfoControll.ecKgzEntityVo.setComUploadEntityList(offlineAttachs);
			}
		}
		// 路径点设备-杆塔
		if (ecTowerEntityVo != null) {// 地图选点回来，恢复勾选，则显示缓存数据
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.GT, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		} else {// 没有缓存数据，则显示数据库中数据
			List<DeviceEntity<EcTowerEntityVo>> listTower = data.getTower();
			if (listTower != null && listTower.size() > 0) {
				EcTowerEntityVo ecTowerEntityVo = listTower.get(0).getData();
				ecTowerEntityVo.setOperateMark(2);// 编辑下的编辑操作
				BasicInfoControll.ecTowerEntityVo = ecTowerEntityVo;
				// 获取杆塔的附件
				List<OfflineAttach> offlineAttachs = dataCenter
						.getOfflineAttachListByWorkNo(BasicInfoControll.ecTowerEntityVo.getObjId(), null, null);
				BasicInfoControll.ecTowerEntityVo.setComUploadEntityList(offlineAttachs);
				// 勾选
				CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.GT,
						viewHolder.getListViewFeaturePoint());
			}
		}
		// 路径点设备-变压器
		if (ecTransformerEntityVo != null) {// 地图选点回来，恢复勾选，则显示缓存数据
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.BYQ, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		} else {// 没有缓存数据，则显示数据库中数据
			List<DeviceEntity<EcTransformerEntityVo>> listTransformer = data.getTranformer();
			if (listTransformer != null && listTransformer.size() > 0) {
				EcTransformerEntityVo ecTransformerEntityVo = listTransformer.get(0).getData();
				ecTransformerEntityVo.setOperateMark(2);// 编辑下的编辑操作
				BasicInfoControll.ecTransformerEntityVo = ecTransformerEntityVo;
				// 获取的附件
				List<OfflineAttach> offlineAttachs = dataCenter
						.getOfflineAttachListByWorkNo(BasicInfoControll.ecTransformerEntityVo.getObjId(), null, null);
				BasicInfoControll.ecTransformerEntityVo.setComUploadEntityList(offlineAttachs);
				// 勾选
				CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.BYQ,
						viewHolder.getListViewFeaturePoint());
			}
		}
		// 路径点设备-配电房
		if (ecDistributionRoomEntityVo != null) { // 地图选点回来，恢复勾选，则显示缓存数据
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.PDS, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		} else {// 没有缓存数据，则显示数据库中数据
			List<DeviceEntity<EcDistributionRoomEntityVo>> listDistributionRoom = data.getDistribution_room();
			if (listDistributionRoom != null && listDistributionRoom.size() > 0) {
				EcDistributionRoomEntityVo ecDistributionRoomEntityVo = listDistributionRoom.get(0).getData();
				ecDistributionRoomEntityVo.setOperateMark(2);// 编辑下的编辑操作
				BasicInfoControll.ecDistributionRoomEntityVo = ecDistributionRoomEntityVo;
				// 获取配电房的附件
				List<OfflineAttach> offlineAttachs = dataCenter.getOfflineAttachListByWorkNo(
						BasicInfoControll.ecDistributionRoomEntityVo.getObjId(), null, null);
				BasicInfoControll.ecDistributionRoomEntityVo.setComUploadEntityList(offlineAttachs);
				// 勾选
				CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.PDS,
						viewHolder.getListViewFeaturePoint());
			}
		}
		// 路径点设备-低压配电箱
		if (ecDypdxEntityVo != null) {// 地图选点回来，恢复勾选，则显示缓存数据
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.DYPDX,
					viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		} else {// 没有缓存数据，则显示数据库中数据
			List<DeviceEntity<EcDypdxEntityVo>> listDypdx = data.getDypdx();
			if (listDypdx != null && listDypdx.size() > 0) {
				EcDypdxEntityVo ecDypdxEntityVo = listDypdx.get(0).getData();
				ecDypdxEntityVo.setOperateMark(2);// 编辑下的编辑操作
				BasicInfoControll.ecDypdxEntityVo = ecDypdxEntityVo;
				// 获取配电房的附件
				List<OfflineAttach> offlineAttachs = dataCenter
						.getOfflineAttachListByWorkNo(BasicInfoControll.ecDypdxEntityVo.getObjId(), null, null);
				BasicInfoControll.ecDypdxEntityVo.setComUploadEntityList(offlineAttachs);
				// 勾选
				CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.DYPDX,
						viewHolder.getListViewFeaturePoint());
			}
		}
		// 路径点设备-工井
		if (ecWorkWellEntityVo != null) {// 地图选点回来，恢复勾选，则显示缓存数据
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.DLJ, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		} else {// 没有缓存数据，则显示数据库中数据
			List<DeviceEntity<EcWorkWellEntityVo>> listWorkWell = data.getWell();
			if (listWorkWell != null && listWorkWell.size() > 0) {
				EcWorkWellEntityVo ecWorkWellEntityVo = listWorkWell.get(0).getData();
				ecWorkWellEntityVo.setOperateMark(2);// 编辑下的编辑操作
				BasicInfoControll.ecWorkWellEntityVo = ecWorkWellEntityVo;
				// 根据所属工井查找井盖
				String expr = "SSGJ = '" + BasicInfoControll.ecWorkWellEntityVo.getObjId() + "'";
				ArrayList<EcWorkWellCoverEntity> queryByExpr = (ArrayList<EcWorkWellCoverEntity>) commonBll
						.queryByExpr2(EcWorkWellCoverEntity.class, expr);
				BasicInfoControll.ecWorkWellEntityVo.setJgEntityList(queryByExpr);
				// 根据所属工井查找工井标签
				String sql = "DEV_OBJ_ID = '" + BasicInfoControll.ecWorkWellEntityVo.getObjId() + "'";
				ArrayList<EcLineLabelEntityVo> ecLineLabelEntityVos = (ArrayList<EcLineLabelEntityVo>) commonBll
						.queryByExpr2(EcLineLabelEntityVo.class, sql);
				if (ecLineLabelEntityVos != null && ecLineLabelEntityVos.size() > 0) {
					for (EcLineLabelEntityVo ecLineLabelEntityVo : ecLineLabelEntityVos) {
						// 获取所属工井标签的附件
						List<OfflineAttach> attachs = dataCenter
								.getOfflineAttachListByWorkNo(ecLineLabelEntityVo.getObjId(), null, null);
						ecLineLabelEntityVo.setAttr(attachs);
						BasicInfoControll.ecWorkWellEntityVo.setEcLineLabelEntityVo(ecLineLabelEntityVo);
					}
				}
				// 获取工井的附件
				List<OfflineAttach> offlineAttachs = dataCenter
						.getOfflineAttachListByWorkNo(BasicInfoControll.ecWorkWellEntityVo.getObjId(), null, null);
				BasicInfoControll.ecWorkWellEntityVo.setComUploadEntityList(offlineAttachs);
				// 勾选
				CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.DLJ,
						viewHolder.getListViewFeaturePoint());
			}
		}

	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		// 初始化标识类型
		String markTypeStr = (String) SelectCollectObjPopWindow.getIntance(mActivity).getTypeValue();
		String[] nodeType = mActivity.getResources().getStringArray(R.array.nodeType);
		List<String> nodeTypes = new ArrayList<String>(Arrays.asList(nodeType));
		basicInfoBll.addMarkTypes(viewHolder.getRgMarkerType(), nodeTypes);
		// TODO
		if (markTypeStr.equals(NodeMarkerType.BSQ.getName())) {
			viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(0).getId());
		} else if (markTypeStr.equals(NodeMarkerType.BSD.getName())) {
			viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(1).getId());
		} else if (markTypeStr.equals(NodeMarkerType.XND.getName())) {
			viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(2).getId());
			markerType = NodeMarkerType.XND.getValue();
			String uuid = UUIDGen.randomUUID();
			viewHolder.getIcMarkerId().setEdtText(uuid);
			isWrittenMarkerNo = true;
		} else if (markTypeStr.equals(NodeMarkerType.GT.getName())) {
			viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(3).getId());
		} else if (markTypeStr.equals(NodeMarkerType.AJH.getName())) {
			viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(4).getId());
			viewHolder.getIcMarkerId().getEditTextView().setText("");
			viewHolder.getIcMarkerId().getEditTextView().setHint("");
		}
		recoverFeaturePointCheck();
	}

	/**
	 * 恢复特征点列表勾选状态
	 */
	private void recoverFeaturePointCheck() {
		if (ecDistributionRoomEntityVo != null) { // 地图选点回来，恢复勾选
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.PDS, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		}
		if (ecDypdxEntityVo != null) {// 地图选点回来，恢复勾选
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.DYPDX,
					viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		}
		if (ecKgzEntityVo != null) {// 地图选点回来，恢复勾选
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.KGZ, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		}
		if (ecSubstationEntityVo != null) {// 地图选点回来，恢复勾选
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.BDZ, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		}
		if (ecTowerEntityVo != null) {// 地图选点回来，恢复勾选
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.GT, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		}
		if (ecTransformerEntityVo != null) {// 地图选点回来，恢复勾选
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.BYQ, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		}
		if (ecWorkWellEntityVo != null) {// 地图选点回来，恢复勾选
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.DLJ, viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		}
		if (ecXsbdzEntityVo != null) {// 地图选点回来，恢复勾选
			CollectFragmentCacheBll.getInstance().setNodeDevice(ResourceEnum.XSBDZ,
					viewHolder.getListViewFeaturePoint());
			featurePointAdapter.notifyDataSetInvalidated();
		}
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		viewHolder.getBtnReceive().setOnClickListener(ocls);
		viewHolder.getBtnNetworking().setOnClickListener(ocls);
		viewHolder.getBtnMap().setOnClickListener(ocls);
		viewHolder.getBtnRtk().setOnClickListener(ocls);
		viewHolder.getIvMore().setOnClickListener(ocls);
		// 给标识器ID设置文本监听器
		MarkerIDWatcher marketIDWatcher = new MarkerIDWatcher(viewHolder.getIcMarkerId().getEditTextView(), mActivity,
				basicInfoBll);
		marketIDWatcher.setListener(marketIDWatcher.defaulltMarketIDWatcherListener);
		viewHolder.getRgMarkerType().setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (!GlobalEntry.editNode) {
					if (checkedId == viewHolder.getRgMarkerType().getChildAt(0).getId()) {
						markerType = NodeMarkerType.BSQ.getValue();
						marderIdSetting();
					} else if (checkedId == viewHolder.getRgMarkerType().getChildAt(1).getId()) {
						markerType = NodeMarkerType.BSD.getValue();
						marderIdSetting();
					} else if (checkedId == viewHolder.getRgMarkerType().getChildAt(2).getId()) {
						markerType = NodeMarkerType.XND.getValue();
						String uuid = UUIDGen.randomUUID();
						viewHolder.getIcMarkerId().getEditTextView()
								.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
						viewHolder.getIcMarkerId().setEdtText(uuid);
					} else if (checkedId == viewHolder.getRgMarkerType().getChildAt(3).getId()) {
						markerType = NodeMarkerType.GT.getValue();
						marderIdSetting();
					} else if (checkedId == viewHolder.getRgMarkerType().getChildAt(4).getId()) {
						markerType = NodeMarkerType.AJH.getValue();
						viewHolder.getIcMarkerId().setEdtText("");
						viewHolder.getIcMarkerId().getEditTextView().setHint("");
						viewHolder.getIcMarkerId().getEditTextView()
								.setFilters(new InputFilter[] { new InputFilter.LengthFilter(32) }); // 输入数量最大长度限制
					}
				}
			}
		});
	}

	private void marderIdSetting() {
		viewHolder.getIcMarkerId().setEdtText("000-");
		viewHolder.getIcMarkerId().getEditTextView().setSelection(4); // 从第四个值开始
		viewHolder.getIcMarkerId().getEditTextView().setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) }); // 输入数量最大长度限制
	}

	/**
	 * 单击事件
	 */
	private OnClickListener ocls = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_marker_basicinfo_receive: // 接收标识器编号
			{
				// 安键环
				if (viewHolder.getRgMarkerType().getCheckedRadioButtonId() == ajh_radiobutton_id) {
					if (hasNfc(mActivity)) {// 带nfc模块标签读取
						Intent intent = new Intent(mActivity, BluetoothNFC_DK_Activity.class);
						mActivity.startActivityForResult(intent, REQUEST_AJH_ID2);
						// AndroidBasicComponentUtils.launchActivityForResult(mActivity,
						// BluetoothNFC_DK_Activity.class, REQUEST_AJH_ID2);
					} else {
						// AndroidBasicComponentUtils.launchActivityForResult(mActivity,
						// BluetoothNFC_DK_Activity.class, REQUEST_AJH_ID3);
						Intent intent = new Intent(mActivity, BluetoothNFC_DK_Activity.class);
						mActivity.startActivityForResult(intent, REQUEST_AJH_ID3);
					}
				} else {
					AndroidBasicComponentUtils.launchActivityForResult(mActivity, MarkerReadActivity.class,
							REQUEST_CODE_MARKER_READ);
				}
				break;
			}
			case R.id.btn_marker_basicinfo_networking:// 联网获取
			{
				viewHolder.getIcGeographyPos().setEdtText("");
				ToastUtil.show(mActivity, "联网获取中...", 200);
				// 开始定位
				mLocationClient.start();
				// 注册广播
				BroadcastReceiverUtils.getInstance().registReceiver(mActivity, flag_geoLocation, geoLocationReceiver);
				break;
			}
			case R.id.btn_marker_basicinfo_map:// 地图获取坐标
			{
				mapSelectCoord();
				break;
			}
			case R.id.btn_marker_basicinfo_rtk:// 获取rtk位置信息
			{
				if (GlobalEntry.rtkLocationInfo == null || GlobalEntry.rtkLocationInfo.getLon() == null
						|| GlobalEntry.rtkLocationInfo.getLat() == null) {
					ToastUtil.show(mActivity, "无法获取rtk坐标", 100);
					return;
				}
				rtkInfoDialog = new RtkInfoDialog(mActivity, R.style.dialog_nameing_conventions);
				rtkInfoDialog.setConfirmCallBack(new RtkConfirmCallBackImpl());
				rtkInfoDialog.show();
				break;
			}
			case R.id.iv_marker_basic_more:// 控制路径点设备列表的显示
			{
				if (featureIsOpen) {// 可见
					featureIsOpen = false;
					viewHolder.getIvMore().setBackgroundResource(R.drawable.more_up);
					LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0,
							1.0f);
					viewHolder.getLlFeature().setLayoutParams(bParams);
					viewHolder.getListViewFeaturePoint().setVisibility(View.GONE);

				} else {
					featureIsOpen = true;
					LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT, 0, 5.0f);
					viewHolder.getLlFeature().setLayoutParams(bParams);
					viewHolder.getIvMore().setBackgroundResource(R.drawable.more_down);
					viewHolder.getListViewFeaturePoint().setVisibility(View.VISIBLE);
				}
				break;
			}

			default:
				break;
			}
		}
	};

	/**
	 * 地图选点获取坐标
	 */
	private void mapSelectCoord() {
		// 如果是编辑下修改地图位置
		if (GlobalEntry.editNode) {
			LayingDitchControll.ditchCableList.clear();
		}
		FragmentEntry.isSelectMap = true;
		EcNodeEntity ecNodeEntity = (EcNodeEntity) getIntent().getSerializableExtra("EcNodeEntity");
		if (null != ecNodeEntity) {
			ScenePhotoControll scenePhotoControll = MarkerCollectControll.getInstance().getScenePhotoFragment()
					.getAction();
			if (scenePhotoControll != null) {
				scenePhotoControll.saveScenePhotoInfo(ecNodeEntity.getOid());
			}
		}
		if (GlobalEntry.editNode == true) {
			GlobalEntry.editMapSelectCoord = true;
		}
		mActivity.setResult(Activity.RESULT_OK);
		mActivity.finish();
	}

	/**
	 * 百度定位广播接收者
	 */
	private BroadcastReceiver geoLocationReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(flag_geoLocation)) {// 百度地图定位
				if (BMapLocationAddrStr != null) {
					if (TextUtils.isEmpty(viewHolder.getIcGeographyPos().getEdtTextValue())) {
						viewHolder.getIcGeographyPos().setEdtText(BMapLocationAddrStr);
					}
				} else {
					ToastUtil.show(mActivity, "定位失败", 200);
				}
				// BMapLocationAddrStr = null;
				mLocationClient.stop();
			}

		}
	};

	/**
	 * 保存缓存数据
	 */
	private void saveDataCache() {
		// 安装位置
		dataCacheBll.saveInstallPosition(viewHolder.getIscInstallPath().getEdtTextValue().trim());
		// 设备描述
		dataCacheBll.saveDeviceDesc(viewHolder.getIscDescribe().getEdtTextValue().trim());
		// 其他
		dataCacheBll.saveNodeRemark(viewHolder.getIscOther().getEdtTextValue().trim());
		// 保存上一标识器信息
		UpMarkerCache upMarkerCache = new UpMarkerCache();
		// 深度
		if (!viewHolder.getIcFlootHeight().getEdtTextValue().isEmpty()) {
			upMarkerCache.setCableDeepth(Double.parseDouble(viewHolder.getIcFlootHeight().getEdtTextValue()));
		}
		// 宽度
		if (!viewHolder.getIcWidth().getEdtTextValue().isEmpty()) {
			upMarkerCache.setCableWidth(Double.parseDouble(viewHolder.getIcWidth().getEdtTextValue()));
		}
		// 设备描述
		upMarkerCache.setDeviceDesc(viewHolder.getIscDescribe().getEdtTextValue());
		// 安装位置
		upMarkerCache.setInstallPosition(viewHolder.getIscInstallPath().getEdtTextValue());
		// 地理位置
		upMarkerCache.setGeographicalPosition(viewHolder.getIcGeographyPos().getEdtTextValue());
		// 路径点敷设方式
		// upMarkerCache.setLayType(StringUtils.nullStrToEmpty(viewHolder.getIscLayType().getEdtTextValue()));
		// 路径点类型
		upMarkerCache.setMarkerType(markerType);
		// 备注
		upMarkerCache.setRemark(viewHolder.getIscOther().getEdtTextValue());
		dataCacheBll.saveUpMarkerCache(upMarkerCache);
	}

	/**
	 * 保存采集基本信息
	 */
	public EcNodeEntity saveBasicInfo() {
		// 标识类型
		markerType = NodeMarkerType.BSQ.getValue();
		int markTypeId = viewHolder.getRgMarkerType().getCheckedRadioButtonId();
		if (markTypeId == viewHolder.getRgMarkerType().getChildAt(0).getId()) {// 球
			markerType = NodeMarkerType.BSQ.getValue();
		} else if (markTypeId == viewHolder.getRgMarkerType().getChildAt(1).getId()) {// 钉
			markerType = NodeMarkerType.BSD.getValue();
		} else if (markTypeId == viewHolder.getRgMarkerType().getChildAt(2).getId()) {// 路径点
			markerType = NodeMarkerType.XND.getValue();
		} else if (markTypeId == viewHolder.getRgMarkerType().getChildAt(3).getId()) {// 杆塔
			markerType = NodeMarkerType.GT.getValue();
		} else if (markTypeId == viewHolder.getRgMarkerType().getChildAt(4).getId()) {// 安键环
			markerType = NodeMarkerType.AJH.getValue();
		}
		// 标识器id
		String markerId = viewHolder.getIcMarkerId().getEdtTextValue();
		// 坐标
		double[] coordArr = new double[] {
				Double.parseDouble(viewHolder.getIcLongitude().getEdtTextValue().isEmpty() ? 0 + ""
						: viewHolder.getIcLongitude().getEdtTextValue()),
				Double.parseDouble(viewHolder.getIcLatitude().getEdtTextValue().isEmpty() ? 0 + ""
						: viewHolder.getIcLatitude().getEdtTextValue()) };
		String geom = CoordsUtils.getInstance().coord2Geom(coordArr);
		// 高程
		String altitude = viewHolder.getIcAltitude().getEdtTextValue();
		// 电缆沟宽度
		String cableWidth = viewHolder.getIcWidth().getEdtTextValue();
		// 电缆沟高度
		String cableDeepth = viewHolder.getIcFlootHeight().getEdtTextValue();
		// 入库
		// 1、节点表
		EcNodeEntity ecNodeEntity = new EcNodeEntity();
		ecNodeEntity
				.setAltitude(GlobalEntry.rtkLocationInfo != null ? GlobalEntry.rtkLocationInfo.getAltitude() : null);
		if (!cableDeepth.isEmpty()) {
			DecimalFormat df = new DecimalFormat("#.00");
			ecNodeEntity.setCableDeepth(Double.parseDouble(df.format(Double.parseDouble(cableDeepth))));
		}
		MarkerCollectControll markerCollectControll = MarkerCollectControll.getInstance();
		LayingDitchFragment layingDitchFragment = markerCollectControll.getLayingDitchFragment();
		LayingDitchControll layingDitchControll = layingDitchFragment.getAction();
		if (layingDitchControll != null) {
			String cableLoop = layingDitchControll.getViewHolder().getIcLoopNum().getEdtTextValue();
			if (!cableLoop.isEmpty()) {
				ecNodeEntity.setCableLoop(Integer.parseInt(cableLoop));// 同沟回路数
			}
		}
		if (!cableWidth.isEmpty()) {
			DecimalFormat df = new DecimalFormat("#.00");
			ecNodeEntity.setCableWidth(Double.parseDouble(df.format(Double.parseDouble(cableWidth))));
		}
		ecNodeEntity.setCoordinateNo(viewHolder.getIcCoordNo().getEdtTextValue());
		ecNodeEntity.setDeviceDesc(viewHolder.getIscDescribe().getEdtTextValue());
		ecNodeEntity.setGeoLocation(viewHolder.getIcGeographyPos().getEdtTextValue());
		ecNodeEntity.setGeom(geom);
		ecNodeEntity.setAltitude(altitude.isEmpty() ? null : Double.parseDouble(altitude));
		ecNodeEntity.setInstallPosition(viewHolder.getIscInstallPath().getEdtTextValue());
		// ecNodeEntity.setLayType(viewHolder.getIscLayType().getEdtTextValue());
		ecNodeEntity.setMarkerNo(markerId);
		ecNodeEntity.setMarkerType(markerType);
		ecNodeEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
		if (GlobalEntry.editNode) {
			ecNodeEntity.setOid(editOid);
		} else {
			ecNodeEntity.setOid(UUIDGen.randomUUID());
		}
		ecNodeEntity.setPlaceMarkerTime(new Date());
		ecNodeEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
		ecNodeEntity.setRemark(viewHolder.getIscOther().getEdtTextValue());
		ecNodeEntity.setUpdateTime(new Date());
		// 入库到节点表
		basicInfoBll.saveOrUpdate(ecNodeEntity);
		// 添加元素到数据集中
		double[] xy = CoordsUtils.getInstance().geom2Coord(ecNodeEntity.getGeom());
		HiElement hiElement = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, ecNodeEntity,
				ecNodeEntity.getOid());
		SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElement,
				ecNodeEntity);
		basicInfoBll.nodehandleDataLog(ecNodeEntity);
		// 2、路径点设备
		// 路径点设备-变电站
		if (ecSubstationEntityVo != null) {
			if (ecSubstationEntityVo.getOperateMark() == 0) {// 新增
				// 1、节点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.BDZ,
						ecSubstationEntityVo.getEcSubstationId(), ecNodeEntity.getOid());
				basicInfoBll.save(ecNodeDeviceEntity);
				basicInfoBll.subAddhandleDataLog(ecSubstationEntityVo, ecNodeDeviceEntity);
			} else if (ecSubstationEntityVo.getOperateMark() == 1) {// 编辑下新增
				// 1、节点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.BDZ,
						ecSubstationEntityVo.getEcSubstationId(), editOid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.subEditHandleDataLog(ecSubstationEntityVo, ecNodeDeviceEntity);
			} else if (ecSubstationEntityVo.getOperateMark() == 2) {// 编辑下编辑
				commonBll.handleDataLog(ecSubstationEntityVo.getEcSubstationId(), TableNameEnum.BDZ.getTableName(),
						DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecSubstationEntityVo.getName(), false);
			} else if (ecSubstationEntityVo.getOperateMark() == 3) {// 关联设备
				// 1、节点设备关联表
				String oid = null;
				if (!GlobalEntry.editNode) {
					oid = ecNodeEntity.getOid();
				} else {
					oid = editOid;
				}
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.BDZ,
						ecSubstationEntityVo.getEcSubstationId(), oid);
				basicInfoBll.save(ecNodeDeviceEntity);
				basicInfoBll.subRelationDevHandleDataLog(ecSubstationEntityVo, ecNodeDeviceEntity);
			}
			basicInfoBll.saveOrUpdate(ecSubstationEntityVo);
			// 保存变电站附件
			basicInfoBll.saveSubAttaData(ecSubstationEntityVo);
			// 添加变电站元素到数据集中
			HiElement hiElementBdz = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, ecSubstationEntityVo,
					ecSubstationEntityVo.getEcSubstationId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElementBdz,
					ecSubstationEntityVo);
			ecSubstationEntityVo = null;
		}

		// 路径点设备-箱式变电站
		if (ecXsbdzEntityVo != null) {
			if (GlobalEntry.editNode == false && ecXsbdzEntityVo.getOperateMark() == 0) {// 新增
				// 1、节点设备关联
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.XSBDZ,
						ecXsbdzEntityVo.getObjId(), ecNodeEntity.getOid());
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、日志
				basicInfoBll.xsbdzAddHandleDataLog(ecXsbdzEntityVo, ecNodeDeviceEntity);
			} else if (ecXsbdzEntityVo.getOperateMark() == 1) {// 编辑下新增
				// 1、路径点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.XSBDZ,
						ecXsbdzEntityVo.getObjId(), editOid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.xsbdzEditHandleDataLog(ecXsbdzEntityVo, ecNodeDeviceEntity);
			} else if (ecXsbdzEntityVo.getOperateMark() == 2) {// 编辑下编辑
				commonBll.handleDataLog(ecXsbdzEntityVo.getObjId(), TableNameEnum.XSBDZ.getTableName(),
						DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecXsbdzEntityVo.getObjId(), false);
			} else if (ecXsbdzEntityVo.getOperateMark() == 3) {// 关联设备
				// 1、路径点设备关联表
				String oid = null;
				if (!GlobalEntry.editNode) {
					oid = ecNodeEntity.getOid();
				} else {
					oid = editOid;
				}
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.XSBDZ,
						ecXsbdzEntityVo.getObjId(), oid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.xsbdzRelationDevHandleDataLog(ecXsbdzEntityVo, ecNodeDeviceEntity);
			}
			basicInfoBll.saveOrUpdate(ecXsbdzEntityVo);
			// 保存箱式变电站附件
			basicInfoBll.saveXsbdzAttaData(ecXsbdzEntityVo);
			// 添加箱式变电站元素到数据集中
			HiElement hiElementXsbdz = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, ecXsbdzEntityVo,
					ecXsbdzEntityVo.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElementXsbdz,
					ecXsbdzEntityVo);
			ecXsbdzEntityVo = null;
		}

		// 路径点设备-开关站
		if (ecKgzEntityVo != null) {
			if (GlobalEntry.editNode == false && ecKgzEntityVo.getOperateMark() == 0) {// 新增
				// 1、路径点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.KGZ,
						ecKgzEntityVo.getObjId(), ecNodeEntity.getOid());
				basicInfoBll.save(ecNodeDeviceEntity);
				// 操作日志
				basicInfoBll.kgzAddDataLog(ecKgzEntityVo, ecNodeDeviceEntity);
			} else if (ecKgzEntityVo.getOperateMark() == 1) {// 编辑下新增
				// 1、路径点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.KGZ,
						ecKgzEntityVo.getObjId(), editOid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.kgzEditHandleDataLog(ecKgzEntityVo, ecNodeDeviceEntity);
			} else if (ecKgzEntityVo.getOperateMark() == 2) {// 编辑下编辑
				commonBll.handleDataLog(ecKgzEntityVo.getObjId(), TableNameEnum.KGZ.getTableName(),
						DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecKgzEntityVo.getObjId(), false);
			} else if (ecKgzEntityVo.getOperateMark() == 3) {// 关联设备
				// 1、路径点设备关联表
				String oid = null;
				if (!GlobalEntry.editNode) {
					oid = ecNodeEntity.getOid();
				} else {
					oid = editOid;
				}
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.KGZ,
						ecKgzEntityVo.getObjId(), oid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.kgzRelationDevHandleDataLog(ecKgzEntityVo, ecNodeDeviceEntity);
			}
			basicInfoBll.saveOrUpdate(ecKgzEntityVo);
			// 保存开关站附件
			basicInfoBll.savekgzAttaData(ecKgzEntityVo);
			// 添加开关站元素到数据集中
			HiElement hiElementKgz = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, ecKgzEntityVo,
					ecKgzEntityVo.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElementKgz,
					ecKgzEntityVo);
			ecKgzEntityVo = null;
		}

		// 路径点设备-低压配电箱
		if (ecDypdxEntityVo != null) {
			if (GlobalEntry.editNode == false && ecDypdxEntityVo.getOperateMark() == 0) {// 新增
				// 1、路径点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.DYPDX,
						ecDypdxEntityVo.getObjId(), ecNodeEntity.getOid());
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.dypdxAddDataLog(ecDypdxEntityVo, ecNodeDeviceEntity);
			} else if (ecDypdxEntityVo.getOperateMark() == 1) {// 编辑下新增
				// 1、路径点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.DYPDX,
						ecDypdxEntityVo.getObjId(), editOid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.dypdxEditHandleDataLog(ecDypdxEntityVo, ecNodeDeviceEntity);
			} else if (ecDypdxEntityVo.getOperateMark() == 2) {// 编辑下编辑
				commonBll.handleDataLog(ecDypdxEntityVo.getObjId(), TableNameEnum.DYPDX.getTableName(),
						DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecDypdxEntityVo.getSbmc(), false);
			} else if (ecDypdxEntityVo.getOperateMark() == 3) {// 关联设备
				// 1、路径点设备关联表
				String oid = null;
				if (!GlobalEntry.editNode) {
					oid = ecNodeEntity.getOid();
				} else {
					oid = editOid;
				}
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.DYPDX,
						ecDypdxEntityVo.getObjId(), oid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.dypdxRelationDevHandleDataLog(ecDypdxEntityVo, ecNodeDeviceEntity);
			}
			basicInfoBll.saveOrUpdate(ecDypdxEntityVo);
			// 保存低压配电室附件
			basicInfoBll.saveDypdxAttaData(ecDypdxEntityVo);
			// 添加低压配电箱元素到数据集中
			HiElement hiElementDypdx = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, ecDypdxEntityVo,
					ecDypdxEntityVo.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElementDypdx,
					ecDypdxEntityVo);
			ecDypdxEntityVo = null;
		}
		// 路径点设备-杆塔
		if (ecTowerEntityVo != null) {
			if (GlobalEntry.editNode == false && ecTowerEntityVo.getOperateMark() == 0) {// 新增
				// 1、路径点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.GT,
						ecTowerEntityVo.getObjId(), ecNodeEntity.getOid());
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.towerAddDataLog(ecTowerEntityVo, ecNodeDeviceEntity);
			} else if (ecTowerEntityVo.getOperateMark() == 1) {// 编辑下新增
				// 1、路径点设备关联
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.GT,
						ecTowerEntityVo.getObjId(), editOid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、日志
				basicInfoBll.towerEditHandleDataLog(ecTowerEntityVo, ecNodeDeviceEntity);
			} else if (ecTowerEntityVo.getOperateMark() == 2) {// 编辑下编辑
				commonBll.handleDataLog(ecTowerEntityVo.getObjId(), TableNameEnum.GT.getTableName(),
						DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecTowerEntityVo.getTowerNo(), false);
			} else if (ecTowerEntityVo.getOperateMark() == 3) {// 关联设备
				// 1、路径点设备关联
				String oid = null;
				if (!GlobalEntry.editNode) {
					oid = ecNodeEntity.getOid();
				} else {
					oid = editOid;
				}
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.GT,
						ecTowerEntityVo.getObjId(), oid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、日志
				basicInfoBll.towerRelationDevHandleDataLog(ecTowerEntityVo, ecNodeDeviceEntity);
			}
			basicInfoBll.saveOrUpdate(ecTowerEntityVo);
			// 保存杆塔附件
			basicInfoBll.saveTowerAttaData(ecTowerEntityVo);
			// 添加杆塔元素到数据集中
			HiElement hiElementGt = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, ecTowerEntityVo,
					ecTowerEntityVo.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElementGt,
					ecTowerEntityVo);
			ecTowerEntityVo = null;
		}
		// 路径点设备-变压器
		if (ecTransformerEntityVo != null) {
			if (GlobalEntry.editNode == false && ecTransformerEntityVo.getOperateMark() == 0) {// 新增
				// 1、路径点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.BYQ,
						ecTransformerEntityVo.getObjId(), ecNodeEntity.getOid());
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、日志
				basicInfoBll.transAddDataLog(ecTransformerEntityVo, ecNodeDeviceEntity);
			} else if (ecTransformerEntityVo.getOperateMark() == 1) {// 编辑下新增
				// 1、路径点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.BYQ,
						ecTransformerEntityVo.getObjId(), editOid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.transEditHandleDataLog(ecTransformerEntityVo, ecNodeDeviceEntity);
			} else if (ecTransformerEntityVo.getOperateMark() == 2) {// 编辑下编辑
				commonBll.handleDataLog(ecTransformerEntityVo.getObjId(), TableNameEnum.BYQ.getTableName(),
						DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecTransformerEntityVo.getSbmc(), false);
			} else if (ecTransformerEntityVo.getOperateMark() == 3) {// 关联设备
				// 1、路径点设备关联表
				String oid = null;
				if (!GlobalEntry.editNode) {
					oid = ecNodeEntity.getOid();
				} else {
					oid = editOid;
				}
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.BYQ,
						ecTransformerEntityVo.getObjId(), oid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.transRelationDevHandleDataLog(ecTransformerEntityVo, ecNodeDeviceEntity);
			}
			basicInfoBll.saveOrUpdate(ecTransformerEntityVo);
			// 保存变压器附件
			basicInfoBll.saveTransAttaData(ecTransformerEntityVo);
			// 添加变压器元素到数据集中
			HiElement hiElementByq = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, ecTransformerEntityVo,
					ecTransformerEntityVo.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElementByq,
					ecTransformerEntityVo);
			ecTransformerEntityVo = null;
		}
		// 路径点设备-配电房
		if (ecDistributionRoomEntityVo != null) {
			if (GlobalEntry.editNode == false && ecDistributionRoomEntityVo.getOperateMark() == 0) {// 新增
				// 1、路径点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.PDS,
						ecDistributionRoomEntityVo.getObjId(), ecNodeEntity.getOid());
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.pdfAddDataLog(ecDistributionRoomEntityVo, ecNodeDeviceEntity);
			} else if (ecDistributionRoomEntityVo.getOperateMark() == 1) {// 编辑下新增
				// 1、路径设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.PDS,
						ecDistributionRoomEntityVo.getObjId(), editOid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作
				basicInfoBll.pdfEditHandleDataLog(ecDistributionRoomEntityVo, ecNodeDeviceEntity);
			} else if (ecDistributionRoomEntityVo.getOperateMark() == 2) {// 编辑下编辑
				commonBll.handleDataLog(ecDistributionRoomEntityVo.getObjId(), TableNameEnum.PDF.getTableName(),
						DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecDistributionRoomEntityVo.getSbmc(),
						false);
			} else if (ecDistributionRoomEntityVo.getOperateMark() == 3) {// 关联设备
				// 1、路径设备关联表
				String oid = null;
				if (!GlobalEntry.editNode) {
					oid = ecNodeEntity.getOid();
				} else {
					oid = editOid;
				}
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.PDS,
						ecDistributionRoomEntityVo.getObjId(), oid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.pdfRelationDevHandleDataLog(ecDistributionRoomEntityVo, ecNodeDeviceEntity);
			}
			basicInfoBll.saveOrUpdate(ecDistributionRoomEntityVo);
			// 保存配电房附件
			basicInfoBll.savePdfAttaData(ecDistributionRoomEntityVo);
			// 添加配电房元素到数据集中
			HiElement hiElementPdf = CombineSpatialDatasUtil.getInstane().combineHiElement(xy,
					ecDistributionRoomEntityVo, ecDistributionRoomEntityVo.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElementPdf,
					ecDistributionRoomEntityVo);
			ecDistributionRoomEntityVo = null;
		}
		// 路径点设备-工井
		if (ecWorkWellEntityVo != null) {
			// 如果是自动添加的工井，工井名称设为GJ+标识器ID
			if (ecWorkWellEntityVo.getGxsj() == null) {
				if (!ecWorkWellEntityVo.getSbmc().equals("GJ" + viewHolder.getIcMarkerId().getEdtTextValue())) {
					ecWorkWellEntityVo.setSbmc("GJ" + viewHolder.getIcMarkerId().getEdtTextValue());
				}
			}
			if (GlobalEntry.editNode == false && ecWorkWellEntityVo.getOperateMark() == 0) {// 新增
				// 1、路径点设备关联表
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.DLJ,
						ecWorkWellEntityVo.getObjId(), ecNodeEntity.getOid());
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.gjAddDataLog(ecWorkWellEntityVo, ecNodeDeviceEntity);
			} else if (ecWorkWellEntityVo.getOperateMark() == 1) {// 编辑下新增
				// 1、路径点设备关联
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.DLJ,
						ecWorkWellEntityVo.getObjId(), editOid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.gjEditHandleDataLog(ecWorkWellEntityVo, ecNodeDeviceEntity);
			} else if (ecWorkWellEntityVo.getOperateMark() == 2) {// 编辑下编辑
				commonBll.handleDataLog(ecWorkWellEntityVo.getObjId(), TableNameEnum.GJ.getTableName(),
						DataLogOperatorType.update, WhetherEnum.NO, "编辑工井：", false);
			} else if (ecWorkWellEntityVo.getOperateMark() == 3) {// 设备关联
				// 1、路径点设备关联
				String oid = null;
				if (!GlobalEntry.editNode) {
					oid = ecNodeEntity.getOid();
				} else {
					oid = editOid;
				}
				EcNodeDeviceEntity ecNodeDeviceEntity = basicInfoBll.combineNodeDevice(ResourceEnum.DLJ,
						ecWorkWellEntityVo.getObjId(), oid);
				basicInfoBll.save(ecNodeDeviceEntity);
				// 2、操作日志
				basicInfoBll.gjRelationDevHandleDataLog(ecWorkWellEntityVo, ecNodeDeviceEntity);
			}
			basicInfoBll.saveOrUpdate(ecWorkWellEntityVo);
			// 保存工井附件
			basicInfoBll.saveGjAttaData(ecWorkWellEntityVo);
			// 保存井盖数据
			List<EcWorkWellCoverEntity> jgEntityList = ecWorkWellEntityVo.getJgEntityList();
			if (jgEntityList != null && jgEntityList.size() > 0) {
				for (int i = 0; i < jgEntityList.size(); i++) {
					EcWorkWellCoverEntity coverEntity = jgEntityList.get(i);
					// 初始化日志
					commonBll.initIsLocal(coverEntity.getObjId(), TableNameEnum.JG.getTableName(), "OBJ_ID");
					// 保存
					commonBll.saveOrUpdate(coverEntity);
					// 保存日志
					commonBll.handleDataLog(coverEntity.getObjId(), TableNameEnum.JG.getTableName(),
							DataLogOperatorType.Add, WhetherEnum.NO, "井盖操作");
				}
			}
			// 保存工井标签数据
			EcLineLabelEntityVo ecLineLabelEntityVo = ecWorkWellEntityVo.getEcLineLabelEntityVo();
			if (ecLineLabelEntityVo != null) {
				// 初始化日志
				commonBll.initIsLocal(ecLineLabelEntityVo.getObjId(), TableNameEnum.DLDZBQ.getTableName(), "OBJ_ID");
				// 保存
				ecLineLabelEntityVo.setOid(ecNodeEntity.getOid());
				commonBll.saveOrUpdate(ecLineLabelEntityVo);
				// 保存日志
				commonBll.handleDataLog(ecLineLabelEntityVo.getObjId(), TableNameEnum.DLDZBQ.getTableName(),
						DataLogOperatorType.Add, WhetherEnum.NO, "工井标签操作");
				// 保存工井标签附件
				List<OfflineAttach> comUploadEntityWorkWellLabelList = ecLineLabelEntityVo.getAttr();
				if (comUploadEntityWorkWellLabelList != null && comUploadEntityWorkWellLabelList.size() > 0) {
					for (OfflineAttach offlineAttach : comUploadEntityWorkWellLabelList) {
						// 保存附件
						comUploadBll.saveOrUpdate(offlineAttach);
					}
				}
			}
			// 添加工井元素到数据集中
			HiElement hiElementGj = CombineSpatialDatasUtil.getInstane().combineHiElement(xy, ecWorkWellEntityVo,
					ecWorkWellEntityVo.getObjId());
			SpatialAnalysis.getInstance().addElement2DatasetWithCheck(SpatialAnalysisDataType.point, hiElementGj,
					ecWorkWellEntityVo);
			// ecWorkWellEntityVo = null;
		}
		// 保存数据缓存
		saveDataCache();
		// 保存操作日志
		if (GlobalEntry.editNode == false) {// 新增
			EcMarkerIdsEntity ecMarkerIdsEntity = new EcMarkerIdsEntity();
			ecMarkerIdsEntity.setId(UUIDGen.randomUUID());
			ecMarkerIdsEntity.setEcMarkerIdsId(markerId);
			ecMarkerIdsEntity.setSaveTime(new Date());
			basicInfoBll.saveOrUpdate(ecMarkerIdsEntity);
			// 日志
			commonBll.handleDataLog(ecMarkerIdsEntity.getId(), TableNameEnum.BSQIDJH.getTableName(),
					DataLogOperatorType.Add, WhetherEnum.NO, "新增标识器id:" + markerId, true);
		} else {// 编辑
			// 入库到全局标识器编号表
			EcMarkerIdsEntity ecMarkerIdsEntity = basicInfoBll.getMarkerIdsEntity(initEditMarkerNo);
			if (ecMarkerIdsEntity == null) {
				ecMarkerIdsEntity = new EcMarkerIdsEntity();
				ecMarkerIdsEntity.setId(UUIDGen.randomUUID());
			}
			ecMarkerIdsEntity.setEcMarkerIdsId(markerId);
			ecMarkerIdsEntity.setSaveTime(new Date());
			basicInfoBll.saveOrUpdate(ecMarkerIdsEntity);
			// 日志
			commonBll.handleDataLog(ecMarkerIdsEntity.getId(), TableNameEnum.BSQIDJH.getTableName(),
					DataLogOperatorType.update, WhetherEnum.NO, "编辑标识器id:" + markerId, false);
		}
		return ecNodeEntity;
	}

	private static final int REQUEST_CODE_MARKER_READ = 888;

	protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == requestCode_substation) {// 变电站
				ecSubstationEntityVo = (EcSubstationEntityVo) data.getSerializableExtra("sub");
			} else if (requestCode == requestCode_xsbdz) {// 箱式变电站
				ecXsbdzEntityVo = (EcXsbdzEntityVo) data.getSerializableExtra("xsbdz");
			} else if (requestCode == requestCode_kgz) {// 开关站
				ecKgzEntityVo = (EcKgzEntityVo) data.getSerializableExtra("kgz");
			} else if (requestCode == requestCode_tower) {// 岗塔
				ecTowerEntityVo = (EcTowerEntityVo) data.getSerializableExtra("tower");
			} else if (requestCode == requestCode_transformer) {// 变压器
				ecTransformerEntityVo = (EcTransformerEntityVo) data.getSerializableExtra("transformer");
			} else if (requestCode == requestCode_distributionRoom) {// 配电室
				ecDistributionRoomEntityVo = (EcDistributionRoomEntityVo) data.getSerializableExtra("distributionRoom");
			} else if (requestCode == requestCode_dypdx) {// 低压配电箱
				ecDypdxEntityVo = (EcDypdxEntityVo) data.getSerializableExtra("dypdx");
			} else if (requestCode == requestCode_workWell) {// 工井
				ecWorkWellEntityVo = (EcWorkWellEntityVo) data.getSerializableExtra("gj");
			} else if (requestCode == REQUEST_CODE_MARKER_READ) {
				// 标识器读取
				String markerID = data.getStringExtra(MarkerReadActivity.INTENT_KEY_MARKER_ID);
				if (!TextUtils.isEmpty(markerID)) {
					FragmentEntry.getInstance().ecNodeEntityCache.setMarkerNo(markerID);
				}
			} else if (requestCode == REQUEST_AJH_ID2) {// nfc
				String rfcId = data.getStringExtra(BluetoothNFC_DK_Activity.RESULT_DATA_KEY);
				if (rfcId != null) {
					FragmentEntry.getInstance().ecNodeEntityCache.setMarkerNo(rfcId);
				}
			} else if (requestCode == REQUEST_AJH_ID3) {// 蓝牙
				LogUtil.i("蓝牙标签读取");
				// 接收高频标签ID
				String rfcId = data.getStringExtra(BluetoothNFC_DK_Activity.RESULT_DATA_KEY);
				if (rfcId != null) {
					FragmentEntry.getInstance().ecNodeEntityCache.setMarkerNo(rfcId);
				}
			}
		}
	};

	/**
	 * rtk实时数据广播接受者
	 */
	public static BroadcastReceiver rtkBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (rtkInfoDialog != null && GlobalEntry.rtkLocationInfo != null) {
				rtkInfoDialog.setData();
			}
		}
	};

	/**
	 * 判断nfc模块是否启用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNfc(Context context) {
		boolean bRet = false;
		if (context == null)
			return bRet;
		NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
		NfcAdapter adapter = manager.getDefaultAdapter();
		if (adapter != null && adapter.isEnabled()) {
			// adapter存在，能启用
			bRet = true;
		}
		return bRet;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (geoLocationReceiver != null) {
			try {
				mActivity.unregisterReceiver(geoLocationReceiver);
				geoLocationReceiver = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rtkBroadcastReceiver != null) {
			try {
				mActivity.unregisterReceiver(rtkBroadcastReceiver);
				rtkBroadcastReceiver = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rtkInfoDialog != null) {
			rtkInfoDialog = null;
		}
		if (mLocationClient != null) {
			mLocationClient = null;
		}
	}

	private class RtkConfirmCallBackImpl implements ConfirmCallBack {
		@Override
		public void onClick() {
			rtkInfoDialog.dismiss();
			basicInfoBll.setRtkCoord(mActivity, viewHolder.getIcLongitude(), viewHolder.getIcLatitude(),
					viewHolder.getIcAltitude());
			rtkInfoDialog = null;
		}
	}

	@Override
	public void onPause() {
		LogUtil.i("onPause");
		super.onPause();
	}
}
