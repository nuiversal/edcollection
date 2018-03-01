package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.BasicInfoControll;
import com.winway.android.edcollection.adding.controll.MarkerCollectControll;
import com.winway.android.edcollection.adding.fragment.BasicInfoFragment;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.project.entity.EcMarkerIdsEntity;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcDypdxEntityVo;
import com.winway.android.edcollection.project.vo.EcKgzEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.edcollection.project.vo.EcXsbdzEntityVo;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.util.DateUtils;
import com.winway.android.util.DensityUtil;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 采集基本信息页业务处理
 * 
 * @author zgq
 *
 */
public class BasicInfoBll extends BaseBll<Object> {
	private Context context;
	private CommonBll commonBll;

	public BasicInfoBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
		this.context = context;
		commonBll = new CommonBll(context, dbUrl);
	}

	/**
	 * 检查标识器的唯一性
	 * 
	 * @return true表示已经存在（不可用），false表示不存在（可以使用）
	 */
	public boolean checkMarkerId(String markerId) {
		String expr = "EC_MARKER_IDS_ID='" + markerId + "'";
		List<EcMarkerIdsEntity> list = this.queryByExpr2(EcMarkerIdsEntity.class, expr);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取已经存在的节点信息
	 * 
	 * @param markerId
	 *            路径点marker_no
	 * @return 对应节点实体
	 * @author xs
	 */
	public EcNodeEntity getExistNode(String markerId) {
		String sql = "MARKER_NO ='" + markerId + "'";
		List<EcNodeEntity> list = this.queryByExpr2(EcNodeEntity.class, sql);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据节点编号获取实体
	 * 
	 * @param markerId
	 * @return
	 */
	public EcMarkerIdsEntity getMarkerIdsEntity(String markerId) {
		String expr = "EC_MARKER_IDS_ID='" + markerId + "'";
		List<EcMarkerIdsEntity> list = this.queryByExpr2(EcMarkerIdsEntity.class, expr);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/***
	 * 根据附件路径获取附件实体
	 * 
	 * @param path
	 * @return
	 */
	public OfflineAttach getAttaByPath(String path) {
		String expr = "FILE_PATH = '" + path + "'";
		List<OfflineAttach> list = this.queryByExpr2(OfflineAttach.class, expr);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	// /**
	// * 获取特征点列表
	// *
	// * @return
	// */
	// public ArrayList<NodeDeviceType> getFeaturePointList() {
	// ArrayList<NodeDeviceType> list = new ArrayList<NodeDeviceType>();
	// list.add(NodeDeviceType.BDZ);
	// list.add(NodeDeviceType.XSBDZ);
	// list.add(NodeDeviceType.KGZ);
	// list.add(NodeDeviceType.GT);
	// list.add(NodeDeviceType.BYQ);
	// list.add(NodeDeviceType.PDF);
	// list.add(NodeDeviceType.DYPDX);
	// list.add(NodeDeviceType.GJ);
	// return list;
	// }

	/**
	 * 获取特征点列表
	 * 
	 * @return
	 */
	public ArrayList<ResourceEnum> getFeaturePointList() {
		ArrayList<ResourceEnum> list = new ArrayList<ResourceEnum>();
		list.add(ResourceEnum.DLJ);
		list.add(ResourceEnum.BDZ);
		list.add(ResourceEnum.XSBDZ);
		list.add(ResourceEnum.KGZ);
		list.add(ResourceEnum.GT);
		list.add(ResourceEnum.BYQ);
		list.add(ResourceEnum.PDS);
		list.add(ResourceEnum.DYPDX);
		return list;
	}

	/**
	 * 设置rtk坐标
	 * 
	 * @return
	 */
	public void setRtkCoord(Context context, InputComponent icLon, InputComponent icLat, InputComponent icAltitude) {
		if (GlobalEntry.rtkLocationInfo == null || GlobalEntry.rtkLocationInfo.getLon() == null
				|| GlobalEntry.rtkLocationInfo.getLat() == null) {
			ToastUtil.show(context, "无法获取rtk坐标", 100);
			return;
		}
		icLon.setEdtText(GlobalEntry.rtkLocationInfo.getLon() + "");
		icLat.setEdtText(GlobalEntry.rtkLocationInfo.getLat() + "");
		icAltitude.setEdtText(GlobalEntry.rtkLocationInfo.getAltitude() + "");
		ToastUtil.show(context, "获取成功", 100);
	}

	/**
	 * 初始化百度地图定位相关
	 * 
	 * @param dBdLocationListener
	 * @param context
	 * @return
	 */
	public LocationClient initBaiduMapLocation(BDLocationListener dBdLocationListener, Context context) {
		LocationClient mLocationClient = new LocationClient(context); // 声明LocationClient类
		mLocationClient.registerLocationListener(dBdLocationListener); // 注册监听函数

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
		mLocationClient.setLocOption(option);
		return mLocationClient;
	}

	/**
	 * 构建路径点设备关联表对象
	 * 
	 * @param nodeDeviceType
	 *            节点设备类型
	 * @param objId
	 *            对象id
	 * @param oid
	 *            路径点oid
	 * @return
	 */
	public EcNodeDeviceEntity combineNodeDevice(ResourceEnum nodeDeviceType, String objId, String oid) {
		EcNodeDeviceEntity ecNodeDeviceEntity = new EcNodeDeviceEntity();
		ecNodeDeviceEntity.setDeviceType(nodeDeviceType.getValue() + "");
		ecNodeDeviceEntity.setDevObjId(objId);
		ecNodeDeviceEntity.setEcNodeDeviceId(UUIDGen.randomUUID());// 主键
		ecNodeDeviceEntity.setOid(oid);
		ecNodeDeviceEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
		ecNodeDeviceEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
		return ecNodeDeviceEntity;
	}

	/**
	 * 动态添加标识类型
	 * 
	 * @param radioGroup
	 * @param nodeType
	 */
	// @SuppressLint("ResourceAsColor")
	public void addMarkTypes(RadioGroup radioGroup, List<String> nodeTypes) {
		for (int i = 0; i < nodeTypes.size(); i++) {
			RadioButton rButton = new RadioButton(context);
			RadioGroup.LayoutParams btnParam = new RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, 1f);
			rButton.setId(i);
			rButton.setGravity(Gravity.CENTER);
			rButton.setTextSize(10);
			rButton.setText(nodeTypes.get(i));
			rButton.setTextColor(Color.BLACK);
			rButton.setButtonDrawable(R.drawable.selected_ic_change);
			rButton.setCompoundDrawablePadding(DensityUtil.dip2px(context, 5));
			// rButton.setPadding(5,5,5,5);
			radioGroup.addView(rButton, btnParam);

			/*
			 * android:id="@+id/rdoBtn_marker_basicinfo_boll_ed"
			 * style="@style/text_Size16_color_000000" android:layout_width="wrap_content"
			 * android:layout_height="wrap_content" android:layout_gravity="center_vertical"
			 * android:layout_weight="1" android:button="@null" android:checked="true"
			 * android:drawableLeft="@drawable/selected_ic_change"
			 * android:drawablePadding="5dp" android:text="@string/marker_ball"
			 */
		}

	}

	/**
	 * 判断是否有网络连接
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 保存工井
	 */
	public void saveEmptyWorkWell() {
		if (BasicInfoControll.ecWorkWellEntityVo == null) {
			// 初始设备名称为GJ+标识器ID号
			MarkerCollectControll markerCollectControll = MarkerCollectControll.getInstance();
			BasicInfoFragment basicInfoFragment = markerCollectControll.getBasicInfoFragment();
			String markerId = basicInfoFragment.getAction().getViewHolder().getIcMarkerId().getEdtTextValue();
			if (markerId.equals("")) {
				ToastUtil.show(context, "请先输入标识器ID号", 100);
				return;
			}

			EcWorkWellEntityVo ecWorkWellEntityVo = new EcWorkWellEntityVo();
			ecWorkWellEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecWorkWellEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecWorkWellEntityVo.setObjId(UUIDGen.randomUUID());
			ecWorkWellEntityVo.setSbmc("GJ" + markerId);
			ecWorkWellEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecWorkWellEntityVo.setOperateMark(1);
			}
			BasicInfoControll.ecWorkWellEntityVo = ecWorkWellEntityVo;
		}
	}

	/**
	 * 保存变电站
	 */
	public void saveEmptySubstation() {
		if (BasicInfoControll.ecSubstationEntityVo == null) {
			EcSubstationEntityVo ecSubstationEntityVo = new EcSubstationEntityVo();
			ecSubstationEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecSubstationEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecSubstationEntityVo.setEcSubstationId(UUIDGen.randomUUID());
			ecSubstationEntityVo.setStationNo(UUIDGen.randomUUID());
			ecSubstationEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecSubstationEntityVo.setOperateMark(1);
			}
			BasicInfoControll.ecSubstationEntityVo = ecSubstationEntityVo;
		}

	}

	/**
	 * 保存箱式变电站
	 */
	public void saveEmptyXsbdz() {
		if (BasicInfoControll.ecXsbdzEntityVo == null) {
			EcXsbdzEntityVo ecXsbdzEntityVo = new EcXsbdzEntityVo();
			ecXsbdzEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecXsbdzEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecXsbdzEntityVo.setObjId(UUIDGen.randomUUID());
			ecXsbdzEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecXsbdzEntityVo.setOperateMark(1);
			}
			BasicInfoControll.ecXsbdzEntityVo = ecXsbdzEntityVo;
		}
	}

	/**
	 * 保存开关站
	 */
	public void saveEmptyKgz() {
		if (BasicInfoControll.ecKgzEntityVo == null) {
			EcKgzEntityVo ecKgzEntityVo = new EcKgzEntityVo();
			ecKgzEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecKgzEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecKgzEntityVo.setObjId(UUIDGen.randomUUID());
			ecKgzEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecKgzEntityVo.setOperateMark(1);
			}
			BasicInfoControll.ecKgzEntityVo = ecKgzEntityVo;
		}
	}

	/**
	 * 保存杆塔
	 */
	public void saveEmptyTower() {
		if (BasicInfoControll.ecTowerEntityVo == null) {
			EcTowerEntityVo ecTowerEntityVo = new EcTowerEntityVo();
			ecTowerEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecTowerEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecTowerEntityVo.setObjId(UUIDGen.randomUUID());
			ecTowerEntityVo.setCreateTime(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecTowerEntityVo.setOperateMark(1);
			}
			BasicInfoControll.ecTowerEntityVo = ecTowerEntityVo;
		}
	}

	/**
	 * 保存变压器
	 */
	public void saveEmptyByq() {
		if (BasicInfoControll.ecTransformerEntityVo == null) {
			EcTransformerEntityVo ecTransformerEntityVo = new EcTransformerEntityVo();
			ecTransformerEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecTransformerEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecTransformerEntityVo.setObjId(UUIDGen.randomUUID());
			ecTransformerEntityVo.setCreateTime(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecTransformerEntityVo.setOperateMark(1);
			}
			BasicInfoControll.ecTransformerEntityVo = ecTransformerEntityVo;
		}
	}

	/**
	 * 保存配电室
	 */
	public void saveEmptyPds() {
		if (BasicInfoControll.ecDistributionRoomEntityVo == null) {
			EcDistributionRoomEntityVo ecDistributionRoomEntityVo = new EcDistributionRoomEntityVo();
			ecDistributionRoomEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecDistributionRoomEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecDistributionRoomEntityVo.setObjId(UUIDGen.randomUUID());
			ecDistributionRoomEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecDistributionRoomEntityVo.setOperateMark(1);
			}
			BasicInfoControll.ecDistributionRoomEntityVo = ecDistributionRoomEntityVo;
		}
	}

	/**
	 * 保存低压变电箱
	 */
	public void saveEmptyDypdx() {
		if (BasicInfoControll.ecDypdxEntityVo == null) {
			EcDypdxEntityVo ecDypdxEntityVo = new EcDypdxEntityVo();
			ecDypdxEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecDypdxEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecDypdxEntityVo.setObjId(UUIDGen.randomUUID());
			ecDypdxEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecDypdxEntityVo.setOperateMark(1);
			}
			BasicInfoControll.ecDypdxEntityVo = ecDypdxEntityVo;
		}
	}

	public EcSubstationEntityVo findSubById() {
		try {
			String objId = BasicInfoControll.ecSubstationEntityVo.getEcSubstationId();
			return findById(EcSubstationEntityVo.class, objId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EcXsbdzEntityVo findXsbdzById() {
		try {
			String objId = BasicInfoControll.ecXsbdzEntityVo.getObjId();
			return findById(EcXsbdzEntityVo.class, objId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EcKgzEntityVo findKgzById() {
		try {
			String objId = BasicInfoControll.ecKgzEntityVo.getObjId();
			return findById(EcKgzEntityVo.class, objId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EcTowerEntityVo findTowerById() {
		try {
			String objId = BasicInfoControll.ecTowerEntityVo.getObjId();
			return findById(EcTowerEntityVo.class, objId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EcTransformerEntityVo findTransFormerById() {
		try {
			String objId = BasicInfoControll.ecTransformerEntityVo.getObjId();
			return findById(EcTransformerEntityVo.class, objId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EcDistributionRoomEntityVo findPdsById() {
		try {
			String objId = BasicInfoControll.ecDistributionRoomEntityVo.getObjId();
			return findById(EcDistributionRoomEntityVo.class, objId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EcDypdxEntityVo findDypdxById() {
		try {
			String objId = BasicInfoControll.ecDypdxEntityVo.getObjId();
			return findById(EcDypdxEntityVo.class, objId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EcWorkWellEntityVo findWorkWellById() {
		try {
			String objId = BasicInfoControll.ecWorkWellEntityVo.getObjId();
			return findById(EcWorkWellEntityVo.class, objId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 路径点保存日志记录 */
	public void nodehandleDataLog(EcNodeEntity ecNodeEntity) {
		// 保存操作日志
		if (GlobalEntry.editNode) {// 编辑
			commonBll.handleDataLog(ecNodeEntity.getOid(), TableNameEnum.LJD.getTableName(), DataLogOperatorType.update,
					WhetherEnum.NO, "编辑节点：" + ecNodeEntity.getMarkerNo(), false);
		} else {
			commonBll.handleDataLog(ecNodeEntity.getOid(), TableNameEnum.LJD.getTableName(), DataLogOperatorType.Add,
					WhetherEnum.NO, "新增节点：" + ecNodeEntity.getMarkerNo(), true);
		}
	}

	/** 变电站新增数据日志 */
	public void subAddhandleDataLog(EcSubstationEntityVo ecSubstationEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecSubstationEntityVo.getEcSubstationId(), TableNameEnum.BDZ.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增：" + ecSubstationEntityVo.getName(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecSubstationEntityVo.getName(), true);
	}

	/** 变电站编辑数据日志 */
	public void subEditHandleDataLog(EcSubstationEntityVo ecSubstationEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecSubstationEntityVo.getEcSubstationId(), TableNameEnum.BDZ.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "新增：" + ecSubstationEntityVo.getName(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "新增路径点设备关联：" + ecSubstationEntityVo.getName(), true);
	}

	/** 变电站关联设备数据日志 */
	public void subRelationDevHandleDataLog(EcSubstationEntityVo ecSubstationEntityVo,
			EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecSubstationEntityVo.getEcSubstationId(), TableNameEnum.BDZ.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecSubstationEntityVo.getName(), false);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增节点关联：" + ecSubstationEntityVo.getName(), true);
	}

	/** 保存变电站附件数据 */
	public void saveSubAttaData(EcSubstationEntityVo ecSubstationEntityVo) {
		List<OfflineAttach> ComUploadEntitySubList = ecSubstationEntityVo.getComUploadEntityList();
		if (ComUploadEntitySubList != null && ComUploadEntitySubList.size() > 0) {
			for (int i = 0; i < ComUploadEntitySubList.size(); i++) {
				OfflineAttach offlineAttach = ComUploadEntitySubList.get(i);
				saveOrUpdate(offlineAttach);
			}
		}
	}

	/** 箱式变电站新增数据日志记录 */
	public void xsbdzAddHandleDataLog(EcXsbdzEntityVo ecXsbdzEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecXsbdzEntityVo.getObjId(), TableNameEnum.XSBDZ.getTableName(), DataLogOperatorType.Add,
				WhetherEnum.NO, "新增：" + ecXsbdzEntityVo.getObjId(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点关联：" + ecXsbdzEntityVo.getSbmc(), true);
	}

	/** 箱式变电站编辑数据日志记录 */
	public void xsbdzEditHandleDataLog(EcXsbdzEntityVo ecXsbdzEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecXsbdzEntityVo.getObjId(), TableNameEnum.XSBDZ.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "新增：" + ecXsbdzEntityVo.getObjId(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "新增路径点关联：" + ecXsbdzEntityVo.getSbmc(), true);
	}

	/** 箱式变电站关联设备数据日志记录 */
	public void xsbdzRelationDevHandleDataLog(EcXsbdzEntityVo ecXsbdzEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecXsbdzEntityVo.getObjId(), TableNameEnum.XSBDZ.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "新增：" + ecXsbdzEntityVo.getObjId(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "新增路径点关联：" + ecXsbdzEntityVo.getSbmc(), true);
	}

	/** 保存箱式变电站附件数据 */
	public void saveXsbdzAttaData(EcXsbdzEntityVo ecXsbdzEntityVo) {
		List<OfflineAttach> ComUploadEntitySubList = ecXsbdzEntityVo.getComUploadEntityList();
		if (ComUploadEntitySubList != null && ComUploadEntitySubList.size() > 0) {
			for (int i = 0; i < ComUploadEntitySubList.size(); i++) {
				OfflineAttach offlineAttach = ComUploadEntitySubList.get(i);
				saveOrUpdate(offlineAttach);
			}
		}
	}

	/** 开关站新增数据日志记录 */
	public void kgzAddDataLog(EcKgzEntityVo ecKgzEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecKgzEntityVo.getObjId(), TableNameEnum.KGZ.getTableName(), DataLogOperatorType.Add,
				WhetherEnum.NO, "新增：" + ecKgzEntityVo.getObjId(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点关联：" + ecKgzEntityVo.getSbmc(), true);
	}

	/** 开关站编辑数据日志记录 */
	public void kgzEditHandleDataLog(EcKgzEntityVo ecKgzEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecKgzEntityVo.getObjId(), TableNameEnum.KGZ.getTableName(), DataLogOperatorType.Add,
				WhetherEnum.NO, "新增：" + ecKgzEntityVo.getObjId(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点关联：" + ecKgzEntityVo.getSbmc(), true);
	}

	/** 开关站关联设备数据日志记录 */
	public void kgzRelationDevHandleDataLog(EcKgzEntityVo ecKgzEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecKgzEntityVo.getObjId(), TableNameEnum.KGZ.getTableName(), DataLogOperatorType.update,
				WhetherEnum.NO, "编辑：" + ecKgzEntityVo.getObjId(), false);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点关联：" + ecKgzEntityVo.getSbmc(), true);
	}

	/** 保存开关站附件数据 */
	public void savekgzAttaData(EcKgzEntityVo ecKgzEntityVo) {
		List<OfflineAttach> ComUploadEntitySubList = ecKgzEntityVo.getComUploadEntityList();
		if (ComUploadEntitySubList != null && ComUploadEntitySubList.size() > 0) {
			for (int i = 0; i < ComUploadEntitySubList.size(); i++) {
				OfflineAttach offlineAttach = ComUploadEntitySubList.get(i);
				saveOrUpdate(offlineAttach);
			}
		}
	}

	/** 低压配电箱新增数据日志记录 */
	public void dypdxAddDataLog(EcDypdxEntityVo ecDypdxEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecDypdxEntityVo.getObjId(), TableNameEnum.DYPDX.getTableName(), DataLogOperatorType.Add,
				WhetherEnum.NO, "新增：" + ecDypdxEntityVo.getSbmc(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecDypdxEntityVo.getSbmc(), true);
	}

	/** 低压配电箱编辑数据日志记录 */
	public void dypdxEditHandleDataLog(EcDypdxEntityVo ecDypdxEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecDypdxEntityVo.getObjId(), TableNameEnum.DYPDX.getTableName(), DataLogOperatorType.Add,
				WhetherEnum.NO, "新增：" + ecDypdxEntityVo.getSbmc(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecDypdxEntityVo.getSbmc(), true);
	}

	/** 低压配电箱关联设备数据日志记录 */
	public void dypdxRelationDevHandleDataLog(EcDypdxEntityVo ecDypdxEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecDypdxEntityVo.getObjId(), TableNameEnum.DYPDX.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecDypdxEntityVo.getSbmc(), false);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecDypdxEntityVo.getSbmc(), true);
	}

	/** 保存低压配电箱附件数据 */
	public void saveDypdxAttaData(EcDypdxEntityVo ecDypdxEntityVo) {
		List<OfflineAttach> ComUploadEntitySubList = ecDypdxEntityVo.getComUploadEntityList();
		if (ComUploadEntitySubList != null && ComUploadEntitySubList.size() > 0) {
			for (int i = 0; i < ComUploadEntitySubList.size(); i++) {
				OfflineAttach offlineAttach = ComUploadEntitySubList.get(i);
				saveOrUpdate(offlineAttach);
			}
		}
	}

	/** 杆塔新增数据日志记录 */
	public void towerAddDataLog(EcTowerEntityVo ecTowerEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecTowerEntityVo.getObjId(), TableNameEnum.GT.getTableName(), DataLogOperatorType.Add,
				WhetherEnum.NO, "新增：" + ecTowerEntityVo.getTowerNo(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecTowerEntityVo.getTowerNo(), true);
	}

	/** 杆塔编辑数据日志记录 */
	public void towerEditHandleDataLog(EcTowerEntityVo ecTowerEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecTowerEntityVo.getObjId(), TableNameEnum.GT.getTableName(), DataLogOperatorType.Add,
				WhetherEnum.NO, "新增：" + ecTowerEntityVo.getTowerNo(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecTowerEntityVo.getTowerNo(), true);
	}

	/** 杆塔关联设备数据日志记录 */
	public void towerRelationDevHandleDataLog(EcTowerEntityVo ecTowerEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecTowerEntityVo.getObjId(), TableNameEnum.GT.getTableName(), DataLogOperatorType.update,
				WhetherEnum.NO, "编辑：" + ecTowerEntityVo.getTowerNo(), false);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecTowerEntityVo.getTowerNo(), true);
	}

	/** 保存杆塔附件数据 */
	public void saveTowerAttaData(EcTowerEntityVo ecTowerEntityVo) {
		List<OfflineAttach> comUploadEntityTowerList = ecTowerEntityVo.getComUploadEntityList();
		if (comUploadEntityTowerList != null && comUploadEntityTowerList.size() > 0) {
			for (int i = 0; i < comUploadEntityTowerList.size(); i++) {
				OfflineAttach offlineAttach = comUploadEntityTowerList.get(i);
				saveOrUpdate(offlineAttach);
			}
		}
	}

	/** 变压器新增数据日志记录 */
	public void transAddDataLog(EcTransformerEntityVo ecTransformerEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecTransformerEntityVo.getObjId(), TableNameEnum.BYQ.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增：" + ecTransformerEntityVo.getSbmc(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点关联：" + ecTransformerEntityVo.getSbmc(), true);
	}

	/** 变压器编辑数据日志记录 */
	public void transEditHandleDataLog(EcTransformerEntityVo ecTransformerEntityVo,
			EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecTransformerEntityVo.getObjId(), TableNameEnum.BYQ.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增：" + ecTransformerEntityVo.getSbmc(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecTransformerEntityVo.getSbmc(), true);
	}

	/** 变压器关联设备数据日志记录 */
	public void transRelationDevHandleDataLog(EcTransformerEntityVo ecTransformerEntityVo,
			EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecTransformerEntityVo.getObjId(), TableNameEnum.BYQ.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecTransformerEntityVo.getSbmc(), false);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点关联：" + ecTransformerEntityVo.getSbmc(), true);
	}

	/** 保存变压器附件数据 */
	public void saveTransAttaData(EcTransformerEntityVo ecTransformerEntityVo) {
		List<OfflineAttach> comUploadEntityTransformerList = ecTransformerEntityVo.getComUploadEntityList();
		if (comUploadEntityTransformerList != null && comUploadEntityTransformerList.size() > 0) {
			for (int i = 0; i < comUploadEntityTransformerList.size(); i++) {
				OfflineAttach offlineAttach = comUploadEntityTransformerList.get(i);
				saveOrUpdate(offlineAttach);
			}
		}
	}

	/** 配电房新增数据日志记录 */
	public void pdfAddDataLog(EcDistributionRoomEntityVo ecDistributionRoomEntityVo,
			EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecDistributionRoomEntityVo.getObjId(), TableNameEnum.PDF.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增：" + ecDistributionRoomEntityVo.getSbmc(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecDistributionRoomEntityVo.getSbmc(), true);
	}

	/** 配电房编辑数据日志记录 */
	public void pdfEditHandleDataLog(EcDistributionRoomEntityVo ecDistributionRoomEntityVo,
			EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecDistributionRoomEntityVo.getObjId(), TableNameEnum.PDF.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增：" + ecDistributionRoomEntityVo.getSbmc(), true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecDistributionRoomEntityVo.getSbmc(), true);
	}

	/** 配电房关联设备数据日志记录 */
	public void pdfRelationDevHandleDataLog(EcDistributionRoomEntityVo ecDistributionRoomEntityVo,
			EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecDistributionRoomEntityVo.getObjId(), TableNameEnum.PDF.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑：" + ecDistributionRoomEntityVo.getSbmc(), false);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecDistributionRoomEntityVo.getSbmc(), true);
	}

	/** 保存配电房附件数据 */
	public void savePdfAttaData(EcDistributionRoomEntityVo ecDistributionRoomEntityVo) {
		List<OfflineAttach> comUploadEntityDistRoomList = ecDistributionRoomEntityVo.getComUploadEntityList();
		if (comUploadEntityDistRoomList != null && comUploadEntityDistRoomList.size() > 0) {
			for (int i = 0; i < comUploadEntityDistRoomList.size(); i++) {
				OfflineAttach offlineAttach = comUploadEntityDistRoomList.get(i);
				saveOrUpdate(offlineAttach);
			}
		}
	}

	/** 工井新增数据日志记录 */
	public void gjAddDataLog(EcWorkWellEntityVo ecWorkWellEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecWorkWellEntityVo.getObjId(), TableNameEnum.GJ.getTableName(), DataLogOperatorType.Add,
				WhetherEnum.NO, "新增工井：", true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：", true);
	}

	/** 工井编辑数据日志记录 */
	public void gjEditHandleDataLog(EcWorkWellEntityVo ecWorkWellEntityVo, EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecWorkWellEntityVo.getObjId(), TableNameEnum.GJ.getTableName(), DataLogOperatorType.Add,
				WhetherEnum.NO, "新增工井：", true);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：", true);
	}

	/** 工井关联设备数据日志记录 */
	public void gjRelationDevHandleDataLog(EcWorkWellEntityVo ecWorkWellEntityVo,
			EcNodeDeviceEntity ecNodeDeviceEntity) {
		commonBll.handleDataLog(ecWorkWellEntityVo.getObjId(), TableNameEnum.GJ.getTableName(),
				DataLogOperatorType.update, WhetherEnum.NO, "编辑工井：" + ecWorkWellEntityVo.getSbmc(), false);
		commonBll.handleDataLog(ecNodeDeviceEntity.getEcNodeDeviceId(), TableNameEnum.LJDSB.getTableName(),
				DataLogOperatorType.Add, WhetherEnum.NO, "新增路径点设备关联：" + ecWorkWellEntityVo.getSbmc(), true);
	}

	/** 保存工井附件数据 */
	public void saveGjAttaData(EcWorkWellEntityVo ecWorkWellEntityVo) {
		List<OfflineAttach> comUploadEntityWorkWellList = ecWorkWellEntityVo.getComUploadEntityList();
		if (comUploadEntityWorkWellList != null && comUploadEntityWorkWellList.size() > 0) {
			for (int i = 0; i < comUploadEntityWorkWellList.size(); i++) {
				OfflineAttach offlineAttach = comUploadEntityWorkWellList.get(i);
				saveOrUpdate(offlineAttach);
			}
		}
	}
}
