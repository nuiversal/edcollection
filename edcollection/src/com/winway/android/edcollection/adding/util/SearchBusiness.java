package com.winway.android.edcollection.adding.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.SearchAdapter;
import com.winway.android.edcollection.adding.bll.DeviceQueryBll;
import com.winway.android.edcollection.adding.controll.DeviceReadQueryControll;
import com.winway.android.edcollection.adding.entity.LayType;
import com.winway.android.edcollection.adding.entity.LineDeviceType;
import com.winway.android.edcollection.adding.entity.NodeDeviceType;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.adding.viewholder.NewFragmentViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.util.ResourceDeviceUtil;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.ChannelDataEntity;
import com.winway.android.edcollection.datacenter.entity.DeviceEntity;
import com.winway.android.edcollection.datacenter.service.ChannelCenter;
import com.winway.android.edcollection.datacenter.service.OfflineAttachCenter;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
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
import com.winway.android.edcollection.project.vo.EcDistributionRoomEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;
import com.winway.android.edcollection.project.vo.EcTowerEntityVo;
import com.winway.android.edcollection.project.vo.EcTransformerEntityVo;
import com.winway.android.edcollection.project.vo.EcWorkWellEntityVo;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.ObjectUtils;

/**
 * 搜索业务
 * 
 * @author mr-lao
 *
 */
@SuppressLint("HandlerLeak")
public class SearchBusiness {
	private NewFragmentViewHolder viewHolder;
	private Activity mActivity;
	private String projectBDUrl;
	private TreeMapUtil treeMapUtil;
	private OfflineAttachCenter center;

	// 路径点
	List<EcNodeEntity> marketidList;
	// 线路
	List<EcLineEntity> lineList;
	// 通道
	List<EcChannelEntity> channelList;
	// 路径点设备
	List<EcWorkWellEntity> wellList;
	List<EcTowerEntity> towerList;
	List<EcSubstationEntity> stationList;
	List<EcDistributionRoomEntity> roomList;
	List<EcTransformerEntity> transfomerList;
	List<EcKgzEntity> kgzList;
	List<EcXsbdzEntity> xsbdzList;
	List<EcDypdxEntity> dypdxList;
	List<EcWorkWellCoverEntity> jgList;
	// 线路附属设备
	List<EcLineLabelEntity> labelList;
	List<EcDlfzxEntity> dlfzxList;
	List<EcDydlfzxEntity> dydlfzxList;
	List<EcHwgEntity> hwgList;
	List<EcMiddleJointEntity> midleList;
	// 数据中心
	private DataCenterImpl dataCenterImpl;
	// 搜索结果
	private ArrayList<Object> searchDatas = new ArrayList<Object>();
	private SearchAdapter searchAdapter = null;
	ChannelCenter channelCenter;
	private DeviceQueryBll deviceQueryBll;

	public SearchBusiness(NewFragmentViewHolder viewHolder, Activity mActivity, String projectBDUrl) {
		super();
		this.viewHolder = viewHolder;
		this.mActivity = mActivity;
		this.projectBDUrl = projectBDUrl;
		center = new OfflineAttachCenter(mActivity, projectBDUrl);
		channelCenter = new ChannelCenter(mActivity, projectBDUrl);
		dataCenterImpl = new DataCenterImpl(mActivity, projectBDUrl);
		deviceQueryBll = new DeviceQueryBll(mActivity, projectBDUrl);
	}

	public void init(TreeMapUtil mapUtil) {
		this.treeMapUtil = mapUtil;
		viewHolder.getBtnSearch().setOnClickListener(orcl);
		viewHolder.getBsqRESTTV().setOnClickListener(orcl);
		viewHolder.getTdRESTTV().setOnClickListener(orcl);
		viewHolder.getLineRESTTV().setOnClickListener(orcl);
		viewHolder.getWellRESTTV().setOnClickListener(orcl);
		viewHolder.getTowerRESTTV().setOnClickListener(orcl);
		viewHolder.getStationRESTTV().setOnClickListener(orcl);
		viewHolder.getTransformerRESTTV().setOnClickListener(orcl);
		viewHolder.getRoomRESTTV().setOnClickListener(orcl);
		viewHolder.getXxbdzRESTTV().setOnClickListener(orcl);
		viewHolder.getDypdxRESTTV().setOnClickListener(orcl);
		viewHolder.getKgzRESTTV().setOnClickListener(orcl);
		viewHolder.getLableRESTTV().setOnClickListener(orcl);
		viewHolder.getMidleRESTTV().setOnClickListener(orcl);
		viewHolder.getHwgRESTTV().setOnClickListener(orcl);
		viewHolder.getDlfzxRESTTV().setOnClickListener(orcl);
		viewHolder.getDydlfzxRESTTV().setOnClickListener(orcl);
		viewHolder.getJgRESTTV().setOnClickListener(orcl);
		// 搜索结果点击事件
		viewHolder.getSearchResultLV().setOnItemClickListener(restClickListener);
		viewHolder.getMoredown().setOnClickListener(orcl);
		viewHolder.getMoreup().setOnClickListener(orcl);
		viewHolder.getSearchClose().setOnClickListener(orcl);
	}

	/** 搜索结果列表点击事件 */
	private OnItemClickListener restClickListener = new OnItemClickListener() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			view.setBackgroundResource(R.drawable.list_item_selector);
			Object object = searchDatas.get(position);
			if (object instanceof EcNodeEntity) {
				// 地图交互
				treeMapUtil.drawNode((EcNodeEntity) object, null);
				// 表格详情
				TableShowUtil.showNodeDetailed((EcNodeEntity) object, null, center, mActivity);
				return;
			}
			if (object instanceof EcChannelEntity) {
				// 根据通道id获取到通道
				ChannelDataEntity channelDataEntity = channelCenter.getChannel(((EcChannelEntity) object).getEcChannelId());
				// 地图交互
				treeMapUtil.drawChannel(channelDataEntity);
				// 表格详情
				TableShowUtil.showChannel(channelDataEntity, mActivity);
			}

			if (object instanceof EcLineEntity) {
				// 线路
				treeMapUtil.drawLine((EcLineEntity) object, ((EcLineEntity) object).getEcLineId());
				ArrayList<Object[]> list = TableDataUtil.getByEcLineEntity((EcLineEntity) object);
				TableShowUtil.showTable(list, mActivity, "线路", null,null,null,null,null,null);
				return;
			}
			if (object instanceof EcWorkWellEntity) {
				// 工井
				DeviceEntity<EcWorkWellEntityVo> workWell = dataCenterImpl
						.getWorkWell(((EcWorkWellEntity) object).getObjId(), null, null);
				treeMapUtil.drawWell(workWell, "");
				TableShowUtil.showWellDetailed(workWell.getData(), center, mActivity);
				return;
			}
			if (object instanceof EcTowerEntity) {
				// 杆塔
				DeviceEntity<EcTowerEntityVo> tower = dataCenterImpl.getTower(((EcTowerEntity) object).getObjId(), null,
						null);
				treeMapUtil.drawTower(tower, "");
				TableShowUtil.showTowerDetailed(tower.getData(), center, mActivity);
				return;
			}
			if (object instanceof EcSubstationEntity) {
				// 变电站
				DeviceEntity<EcSubstationEntityVo> station = dataCenterImpl
						.getStation(((EcSubstationEntity) object).getEcSubstationId(), null, null);
				treeMapUtil.drawStation(station);
				TableShowUtil.showStationDetailed(station.getData(), center, mActivity);
				return;
			}
			if (object instanceof EcDistributionRoomEntity) {
				// 配电房
				DeviceEntity<EcDistributionRoomEntityVo> distributionRoom = dataCenterImpl
						.getDistributionRoom(((EcDistributionRoomEntity) object).getObjId(), null, null);
				treeMapUtil.drawRoom(distributionRoom, "");
				TableShowUtil.showRoomDetailed(distributionRoom.getData(), center, mActivity);
				return;
			}
			if (object instanceof EcTransformerEntity) {
				// 变压器
				DeviceEntity<EcTransformerEntityVo> transformer = dataCenterImpl
						.getTransformer(((EcTransformerEntity) object).getObjId(), null, null);
				treeMapUtil.drawTrans(transformer, "");
				TableShowUtil.showTransDetailed(transformer.getData(), center, mActivity);
				return;
			}
			if (object instanceof EcDypdxEntity) {
				// 低压配电箱
				Object dypdx = dataCenterImpl.getDeviceCenter().getDypdx(((EcDypdxEntity) object).getObjId());
				treeMapUtil.drawDypdx((DeviceEntity<EcDypdxEntity>) dypdx, "");
				TableShowUtil.showDypdxDetailed(((DeviceEntity<EcDypdxEntity>) dypdx).getData(), mActivity, dataCenterImpl);
				return;
			}
			if (object instanceof EcXsbdzEntity) {
				// 箱式变电站
				DeviceEntity xsbdz = dataCenterImpl.getDeviceCenter().getXsbdz(((EcXsbdzEntity) object).getObjId());
				treeMapUtil.drawXsbdz(xsbdz, "");
				TableShowUtil.showXsbdzDetailed((EcXsbdzEntity) xsbdz.getData(), mActivity, dataCenterImpl);
				return;
			}
			if (object instanceof EcKgzEntity) {
				// 开关站
				Object kgz = dataCenterImpl.getDeviceCenter().getKgz(((EcKgzEntity) object).getObjId());
				treeMapUtil.drawKgz((DeviceEntity<EcKgzEntity>) kgz, "");
				TableShowUtil.showKgzDetailed(((DeviceEntity<EcKgzEntity>) kgz).getData(), mActivity, dataCenterImpl);
				return;
			}
			if (object instanceof EcLineLabelEntity) {
				// 标签
				// XXX 修正点击标签地图显示
				String oid = ((EcLineLabelEntity)object).getOid();
				if(oid==null){
					DeviceReadQueryControll.isEdit=false;
					DeviceEntity label = dataCenterImpl.getLabel(((EcLineLabelEntity) object).getObjId(), null, null);
					treeMapUtil.drawLabel(label, "");
					TableShowUtil.showLabelDetailed((EcLineLabelEntity) label.getData(), center, mActivity);
				}else {
					DeviceReadQueryControll.isEdit=true;
					String expr = "OID = '" + oid + "'";
					List<EcNodeEntity> nodes = deviceQueryBll.queryByExpr2(EcNodeEntity.class, expr);
					EcNodeEntity ecNodeEntity = nodes.get(0);
					GlobalEntry.currentEditNode=ecNodeEntity;
					DeviceEntity label = dataCenterImpl.getLabel(((EcLineLabelEntity) object).getObjId(), null, null);
					treeMapUtil.drawLabel(label, "");
					TableShowUtil.showLabelDetailed((EcLineLabelEntity) label.getData(), center, mActivity);
				}
				return;
			}
			if (object instanceof EcMiddleJointEntity) {
				// 中间接头
				DeviceEntity<EcMiddleJointEntityVo> middle = dataCenterImpl
						.getMiddle(((EcMiddleJointEntity) object).getObjId(), null, null);
				treeMapUtil.drawMiddle(middle, "");
				TableShowUtil.showMiddleDetailed(middle.getData(), center, mActivity);
				return;
			}
			if (object instanceof EcDlfzxEntity) {
				// 电缆分支箱
				DeviceEntity dlfzx = dataCenterImpl.getDeviceCenter().getDlfzx(((EcDlfzxEntity) object).getObjId());
				treeMapUtil.drawDlfzx(dlfzx, "");
				TableShowUtil.showDlfzxDetailed((EcDlfzxEntity) dlfzx.getData(), mActivity, dataCenterImpl);
				return;
			}
			if (object instanceof EcDydlfzxEntity) {
				// 低压电缆分支箱
				DeviceEntity dydlfzx = dataCenterImpl.getDeviceCenter().getDydlfzx(((EcDydlfzxEntity) object).getObjId());
				treeMapUtil.drawDydlfzx(dydlfzx, "");
				TableShowUtil.showDydlfzxDetailed((EcDydlfzxEntity) dydlfzx.getData(), mActivity, dataCenterImpl);
				return;
			}
			if (object instanceof EcHwgEntity) {
				// 环网柜
				DeviceEntity hwg = dataCenterImpl.getDeviceCenter().getHwg(((EcHwgEntity) object).getObjId());
				treeMapUtil.drawHwg(hwg, "");
				TableShowUtil.showHwgDetailed((EcHwgEntity) hwg.getData(), mActivity, dataCenterImpl);
				return;
			}
			if (object instanceof EcWorkWellCoverEntity) {
				// 井盖
				List<DeviceEntity<EcWorkWellCoverEntity>> wellCoverList = dataCenterImpl.getDeviceCenter()
						.getWellCoverList(((EcWorkWellCoverEntity) object).getSsgj(), true);
				if (null != wellCoverList && !wellCoverList.isEmpty()) {
					for (DeviceEntity<EcWorkWellCoverEntity> deviceEntity : wellCoverList) {
						EcWorkWellCoverEntity coverEntity = deviceEntity.getData();
						treeMapUtil.drawCover(deviceEntity);
						TableShowUtil.showCover(coverEntity, dataCenterImpl, mActivity);
						return;
					}
				}
			}
		}
	};

	/** 点击事件监听器 */
	private OnClickListener orcl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 关闭
			if (v.getId() == R.id.search_result_container_close) {
				viewHolder.getSearchResultContainer().setVisibility(View.GONE);
				// 清除地图标记
				treeMapUtil.clearMapOverlays();
				return;
			}
			// 上拉或者下拉
			if (v.getId() == R.id.search_result_more_down) {
				viewHolder.getMoredown().setVisibility(View.GONE);
				viewHolder.getMoreup().setVisibility(View.VISIBLE);
				if (viewHolder.getBtnBack().getVisibility() != View.GONE) {
					// 显示
					viewHolder.getSearchResultLV().setVisibility(View.VISIBLE);
				} else {
					// 类型
					viewHolder.getKindContainer().setVisibility(View.VISIBLE);
				}
				return;
			}
			if (v.getId() == R.id.search_result_more_up) {
				viewHolder.getMoredown().setVisibility(View.VISIBLE);
				viewHolder.getMoreup().setVisibility(View.GONE);
				if (viewHolder.getBtnBack().getVisibility() != View.GONE) {
					// 显示
					viewHolder.getSearchResultLV().setVisibility(View.GONE);
				} else {
					// 类型
					viewHolder.getKindContainer().setVisibility(View.GONE);
				}
				return;
			}
			// 清除
			searchDatas.clear();
			switch (v.getId()) {
			case R.id.imgBtn_fragment_new_search:
				// 搜索
				showSearchDialog();
				break;
			case R.id.search_result_bsq:
				// 显示标识器详情
				copyList(searchDatas, marketidList);
				break;
			case R.id.search_result_td:
				// 显示通道详情
				copyList(searchDatas, channelList);
				break;
			case R.id.search_result_line:
				// 显示线路
				copyList(searchDatas, lineList);
				break;
			case R.id.search_result_well:
				// 显示工井
				copyList(searchDatas, wellList);
				break;
			case R.id.search_result_tower:
				// 显示杆塔
				copyList(searchDatas, towerList);
				break;
			case R.id.search_result_station:
				// 显示变电站
				copyList(searchDatas, stationList);
				break;
			case R.id.search_result_room:
				// 显示配电室
				copyList(searchDatas, roomList);
				break;
			case R.id.search_result_transformer:
				// 显示变压器
				copyList(searchDatas, transfomerList);
				break;
			case R.id.search_result_xsbdz:
				// 显示箱式变电站
				copyList(searchDatas, xsbdzList);
				break;
			case R.id.search_result_kgz:
				// 显示开关站
				copyList(searchDatas, kgzList);
				break;
			case R.id.search_result_dypdx:
				// 显示低压配电箱
				copyList(searchDatas, dypdxList);
				break;
			case R.id.search_result_lable:
				// 显示电子标签
				copyList(searchDatas, labelList);
				break;
			case R.id.search_result_midle:
				// 中间接头
				copyList(searchDatas, midleList);
				break;
			case R.id.search_result_hwg:
				// 显示环网柜
				copyList(searchDatas, hwgList);
				break;
			case R.id.search_result_dlfzx:
				// 显示电缆分支箱
				copyList(searchDatas, dlfzxList);
				break;
			case R.id.search_result_dydlfzx:
				// 显示低压电缆分支箱
				copyList(searchDatas, dydlfzxList);
				break;
			case R.id.search_result_jg:
				// 显示低压电缆分支箱
				copyList(searchDatas, jgList);
				break;
			}
			// 刷新并显示数据列表
			refleshDataList();
		}
	};

	/** 清除上一次查询的结果 */
	private void cleanLastResult() {
		if (null != marketidList) {
			marketidList.clear();
		}
		if (null != channelList) {
			channelList.clear();
		}
		if (null != lineList) {
			lineList.clear();
		}
		// 路径点设备
		if (null != wellList) {
			wellList.clear();
		}
		if (null != towerList) {
			towerList.clear();
		}
		if (null != stationList) {
			stationList.clear();
		}
		if (null != roomList) {
			roomList.clear();
		}
		if (null != transfomerList) {
			transfomerList.clear();
		}
		if (null != dypdxList) {
			dypdxList.clear();
		}
		if (null != xsbdzList) {
			xsbdzList.clear();
		}
		if (null != kgzList) {
			kgzList.clear();
		}
		// 线路设备
		if (null != labelList) {
			labelList.clear();
		}
		if (null != midleList) {
			midleList.clear();
		}
		if (null != hwgList) {
			hwgList.clear();
		}
		if (null != dlfzxList) {
			dlfzxList.clear();
		}
		if (null != dydlfzxList) {
			dydlfzxList.clear();
		}
		if (null != jgList) {
			jgList.clear();
		}
	}

	static final int HANDLER_UI_REFLES = 0;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 数据库查询线束，刷新UI
			switch (msg.what) {
			case HANDLER_UI_REFLES:
				refleshSearchResult();
				break;
			}
		}
	};

	final static ExecutorService threadPool = Executors.newFixedThreadPool(1);

	/** 搜索 */
	private void search(final String marketstr, String linestr, String devicestr, String channelstr, String jgstr) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				cleanLastResult();
				searchMarkets(marketstr);
				searchLines(marketstr);
				searchNodeDevices(marketstr);
				searchLineDevices(marketstr);
				searchChannel(marketstr);
				searchJg(marketstr);
				handler.sendEmptyMessage(HANDLER_UI_REFLES);
			}
		});
		searchDilog.dismissDialog();

	}

	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	Date getDate(String datestr) {
		return null;
	}

	/** 搜索线路设备 */
	private void searchLineDevices(String linedevice) {
		if (TextUtils.isEmpty(linedevice)) {
			return;
		}
		try {
			if (LineDeviceType.HWG.getName().equals(linedevice)) {
				hwgList = dbUtil.getAll(EcHwgEntity.class);
				return;
			}
			if (ResourceEnum.DLFZX.getName().equals(linedevice)) {
				dlfzxList = dbUtil.getAll(EcDlfzxEntity.class);
				return;
			}
			if (ResourceEnum.DYDLFZX.getName().equals(linedevice)) {
				dydlfzxList = dbUtil.getAll(EcDydlfzxEntity.class);
				return;
			}
			if ("分支箱".equals(linedevice) || "分接箱".equals(linedevice)) {
				dydlfzxList = dbUtil.getAll(EcDydlfzxEntity.class);
				dlfzxList = dbUtil.getAll(EcDlfzxEntity.class);
				return;
			}
			if (LineDeviceType.ZJJT.getName().equals(linedevice) || "中间头".equals(linedevice)) {
				midleList = dbUtil.getAll(EcMiddleJointEntity.class);
				return;
			}
			if (LineDeviceType.DZBQ.getName().equals(linedevice) || "标签".equals(linedevice)) {
				labelList = dbUtil.getAll(EcLineLabelEntity.class);
				return;
			}
			// 环网柜
			EcHwgEntity queryHwg = new EcHwgEntity();
			ObjectUtils.setFileds(queryHwg, linedevice);
			hwgList = dbUtil.excuteQuery(EcHwgEntity.class, queryHwg,"like", "or", null, null);
			// 电缆分支箱
			EcDlfzxEntity queryDlfzx = new EcDlfzxEntity();
			ObjectUtils.setFileds(queryDlfzx, linedevice);
			dlfzxList = dbUtil.excuteQuery(EcDlfzxEntity.class, queryDlfzx,"like", "or", null, null);
			// 低压电缆分支箱
			EcDydlfzxEntity queryDydlfzx = new EcDydlfzxEntity();
			ObjectUtils.setFileds(queryDydlfzx, linedevice);
			dydlfzxList = dbUtil.excuteQuery(EcDydlfzxEntity.class, queryDydlfzx,"like", "or", null, null);
			// 电子标签
			EcLineLabelEntity queryLable = new EcLineLabelEntity();
			ObjectUtils.setFileds(queryLable, linedevice);
			labelList = dbUtil.excuteQuery(EcLineLabelEntity.class, queryLable,"like", "or", null, null);
			// 中间接头
			EcMiddleJointEntity queryMidle = new EcMiddleJointEntity();
			ObjectUtils.setFileds(queryMidle, linedevice);
			midleList = dbUtil.excuteQuery(EcMiddleJointEntity.class, queryMidle,"like", "or", null, null);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/** 搜索路径点设备 */
	private void searchNodeDevices(String devicestr) {
		if (!TextUtils.isEmpty(devicestr)) {
			try {
				// 设备有工井、杆塔、变电站、变压器、配电房五种
				if (NodeDeviceType.GJ.getName().equals(devicestr) || ResourceEnum.DLJ.getName().equals(devicestr)) {
					// 工井
					wellList = dbUtil.getAll(EcWorkWellEntity.class);
					return;
				}
				if (NodeDeviceType.GT.getName().equals(devicestr)) {
					// 杆塔
					towerList = dbUtil.getAll(EcTowerEntity.class);
					return;
				}
				if (NodeDeviceType.BYQ.getName().equals(devicestr)) {
					// 变压器
					transfomerList = dbUtil.getAll(EcTransformerEntity.class);
					return;
				}
				if (NodeDeviceType.BDZ.getName().equals(devicestr)) {
					// 变电站
					stationList = dbUtil.getAll(EcSubstationEntity.class);
					return;
				}
				if (NodeDeviceType.PDF.getName().equals(devicestr) || "配电房".equals(devicestr)) {
					// 配电室
					roomList = dbUtil.getAll(EcDistributionRoomEntity.class);
					return;
				}
				if (NodeDeviceType.DYPDX.getName().equals(devicestr)) {
					// 低压配电箱
					dypdxList = dbUtil.getAll(EcDypdxEntity.class);
					return;
				}
				if (NodeDeviceType.XSBDZ.getName().equals(devicestr)) {
					// 箱式变电站
					xsbdzList = dbUtil.getAll(EcXsbdzEntity.class);
					return;
				}
				if (NodeDeviceType.KGZ.getName().equals(devicestr)) {
					// 开关站
					kgzList = dbUtil.getAll(EcKgzEntity.class);
					return;
				}
				Date date = getDate(devicestr);
				// 工井
				EcWorkWellEntity queryWell = new EcWorkWellEntity();
				ObjectUtils.setFileds(queryWell, devicestr);
				wellList = dbUtil.excuteQuery(EcWorkWellEntity.class, queryWell, "like", "or", null, null);
				// 杆塔
				EcTowerEntity queryTower = new EcTowerEntity();
				if (date != null) {
					queryTower.setAccomplishDate(date);
					queryTower.setCommissioningDate(date);
					queryTower.setCreateTime(date);
					queryTower.setUpdateTime(date);
				} else {
					ObjectUtils.setFileds(queryTower, devicestr);
				}
				towerList = dbUtil.excuteQuery(EcTowerEntity.class, queryTower, "like", "or", null, null);
				// 变电站
				EcSubstationEntity queryStation = new EcSubstationEntity();
				// 变电站名称
				ObjectUtils.setFileds(queryStation, devicestr);
				stationList = dbUtil.excuteQuery(EcSubstationEntity.class, queryStation, "like", "or", null, null);
				// 变压器
				EcTransformerEntity queryTransformer = new EcTransformerEntity();
				if (date != null) {
					queryTransformer.setCommissioningDate(date);
					queryTransformer.setCreateTime(date);
					queryTransformer.setUpdateTime(date);
				} else {
					ObjectUtils.setFileds(queryTransformer, devicestr);
				}
				transfomerList = dbUtil.excuteQuery(EcTransformerEntity.class, queryTransformer, "like", "or", null, null);
				// 配电房
				EcDistributionRoomEntity queryRoom = new EcDistributionRoomEntity();
				ObjectUtils.setFileds(queryRoom, devicestr);
				roomList = dbUtil.excuteQuery(EcDistributionRoomEntity.class, queryRoom, "like", "or", null, null);
				// 开关站
				EcKgzEntity queryKgz = new EcKgzEntity();
				ObjectUtils.setFileds(queryKgz, devicestr);
				kgzList = dbUtil.excuteQuery(EcKgzEntity.class, queryKgz, "like", "or", null, null);
				// 箱式变电站
				EcXsbdzEntity queryXsbdz = new EcXsbdzEntity();
				ObjectUtils.setFileds(queryXsbdz, devicestr);
				xsbdzList = dbUtil.excuteQuery(EcXsbdzEntity.class, queryXsbdz, "like", "or", null, null);
				// 低压配电箱
				EcDypdxEntity queryDypdx = new EcDypdxEntity();
				ObjectUtils.setFileds(queryDypdx, devicestr);
				dypdxList = dbUtil.excuteQuery(EcDypdxEntity.class, queryDypdx, "like", "or", null, null);
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 搜索井盖
	 * 
	 * @param jgstr
	 */
	private void searchJg(String jgstr) {
		if (!TextUtils.isEmpty(jgstr)) {
			try {
				if ("井盖".equals(jgstr)) {
					// 井盖
					jgList = dbUtil.getAll(EcWorkWellCoverEntity.class);
					return;
				}
				Date date = getDate(jgstr);
				EcWorkWellCoverEntity coverEntity = new EcWorkWellCoverEntity();
				if (null != null) {
					// 日期查询
					coverEntity.setCjsj(date);
					coverEntity.setGxsj(date);
				} else {
					ObjectUtils.setFileds(coverEntity, jgstr);
				}
				// 通道查询结果
				jgList = dbUtil.excuteQuery(EcWorkWellCoverEntity.class, coverEntity, "like", "or", null, null);
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
	}

	/** 搜索线路 */
	private void searchLines(String linestr) {
		if (!TextUtils.isEmpty(linestr)) {
			try {
				EcLineEntity queryEntity = new EcLineEntity();
				if ("线路".equals(linestr)) {
					// 通道
					lineList = dbUtil.getAll(EcLineEntity.class);
					return;
				}
				Date date = getDate(linestr);
				if (date != null) {
					queryEntity.setCreateTime(date);
					queryEntity.setUpdateTime(date);
				} else {
					ObjectUtils.setFileds(queryEntity, linestr);
					// 线路类型
					if ("电缆工程线路".equals(linestr)) {
						queryEntity.setLineType(1);
					} else if ("架空工程线路".equals(linestr)) {
						queryEntity.setLineType(2);
					} else if ("电缆".equals(linestr)) {
						queryEntity.setLineType(3);
					} else if ("架空导线".equals(linestr)) {
						queryEntity.setLineType(4);
					}
				}
				lineList = dbUtil.excuteQuery(EcLineEntity.class, queryEntity, "like", "or", null, null);
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
	}

	/** 搜索通道 */
	private void searchChannel(String channelstr) {
		if (!TextUtils.isEmpty(channelstr)) {
			try {
				if ("通道".equals(channelstr)) {
					// 通道
					channelList = dbUtil.getAll(EcChannelEntity.class);
					return;
				}
				Date date = getDate(channelstr);
				EcChannelEntity queryEntity = new EcChannelEntity();
				if (null != date) {
					// 日期查询
					queryEntity.setCreateTime(date);
					queryEntity.setUpdateTime(date);
				} else {
					// 通道类型
					if (LayType.dg.getValue().equals(channelstr)) {
						queryEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(EcChannelDgEntity.class));
					} else if (LayType.tlg.getValue().equals(channelstr)) {
						queryEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(EcChannelDgEntity.class));
					} else if ("电缆管道".equals(channelstr) || LayType.pg.getValue().equals(channelstr)) {
						queryEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(EcChannelDlgEntity.class));
					} else if ("沟道".equals(channelstr) || LayType.dlg.getValue().equals(channelstr)) {
						queryEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(EcChannelDlgdEntity.class));
					} else if (LayType.sd.getValue().equals(channelstr)) {
						queryEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(EcChannelDlsdEntity.class));
					} else if (LayType.qj.getValue().equals(channelstr)) {
						queryEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(EcChannelDlqEntity.class));
					} else if (LayType.zm.getValue().equals(channelstr)) {
						queryEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(EcChannelDlzmEntity.class));
					} else if (LayType.jk.getValue().equals(channelstr)) {
						queryEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(EcChannelJkEntity.class));
					} else if (LayType.mg.getValue().equals(channelstr)) {
						queryEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(EcChannelDlgEntity.class));
					} else if (LayType.dlc.getValue().equals(channelstr)) {
						queryEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(EcChannelDlcEntity.class));
					}else {
						ObjectUtils.setFileds(queryEntity, channelstr);
					}
				}
				// 通道查询结果
				channelList = dbUtil.excuteQuery(EcChannelEntity.class, queryEntity, "like", "or", null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** 搜索标识器 */
	private void searchMarkets(String marketstr) {
		if (!TextUtils.isEmpty(marketstr)) {
			try {
				if ("标识器".equals(marketstr)) {
					// 标识器
					marketidList = dbUtil.getAll(EcNodeEntity.class);
					return;
				}
				Date date = getDate(marketstr);
				EcNodeEntity queryEntity = new EcNodeEntity();
				if (null != date) {
					// 日期查询
					queryEntity.setUpdateTime(date);
					queryEntity.setPlaceMarkerTime(date);
				} else {
					ObjectUtils.setFileds(queryEntity, marketstr);
					// 敷设类型
//					if (LayType.jk.getValue().equals(marketstr)) {
//						queryEntity.setLayType(LayType.jk.getValue());
//					} else if (LayType.dlg.getValue().equals(marketstr)) {
//						queryEntity.setLayType(LayType.dlg.getValue());
//					} else if (LayType.dlc.getValue().equals(marketstr)) {
//						queryEntity.setLayType(LayType.dlc.getValue());
//					} else if (LayType.mg.getValue().equals(marketstr)) {
//						queryEntity.setLayType(LayType.mg.getValue());
//					} else if (LayType.dg.getValue().equals(marketstr)) {
//						queryEntity.setLayType(LayType.dg.getValue());
//					} else if (LayType.sd.getValue().equals(marketstr)) {
//						queryEntity.setLayType(LayType.sd.getValue());
//					} else if (LayType.qj.getValue().equals(marketstr)) {
//						queryEntity.setLayType(LayType.qj.getValue());
//					} else if (LayType.qj.getValue().equals(marketstr)) {
//						queryEntity.setLayType(LayType.qj.getValue());
//					} else if (LayType.zhg.getValue().equals(marketstr)) {
//						queryEntity.setLayType(LayType.zhg.getValue());
//					}else if (LayType.tlg.getValue().equals(marketstr)) {
//						queryEntity.setLayType(LayType.tlg.getValue());
//					}
					// 标识器类型
					if (NodeMarkerType.BSQ.getName().equals(marketstr)) {
						queryEntity.setMarkerType(NodeMarkerType.BSQ.getValue());
					} else if (NodeMarkerType.BSD.getName().equals(marketstr)) {
						queryEntity.setMarkerType(NodeMarkerType.BSD.getValue());
					} else if ("路径点".equals(marketstr) || NodeMarkerType.XND.getName().equals(marketstr)) {
						queryEntity.setMarkerType(NodeMarkerType.XND.getValue());
						dbUtil.EGNORE_VALUE = -100;
					} else if (NodeMarkerType.GT.getName().equals(marketstr)) {
						queryEntity.setMarkerType(NodeMarkerType.GT.getValue());
					}else if (NodeMarkerType.AJH.getName().equals(marketstr)) {
						queryEntity.setMarkerType(NodeMarkerType.AJH.getValue());
					}
				}
				// 标识器查询结果
				marketidList = dbUtil.excuteQuery(EcNodeEntity.class, queryEntity, "like", "or", null, null);
				dbUtil.EGNORE_VALUE = BaseDBUtil.EGNORE_DEFAULT_VALUE;
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
	}

	private DialogUtil searchDilog = null;
	private BaseDBUtil dbUtil = null;

	/** 搜索对话框 */
	private void showSearchDialog() {
		if (null == searchDilog) {
			searchDilog = new DialogUtil(mActivity);
			dbUtil = new BaseDBUtil(mActivity, new File(projectBDUrl));
			View view = View.inflate(mActivity, R.layout.dialog_search_input, null);
			final EditText marketidET = (EditText) view.findViewById(R.id.edtTxt_market_id);
			final EditText linenameET = (EditText) view.findViewById(R.id.edtTxt_line_name);
			final EditText devicenameET = (EditText) view.findViewById(R.id.edtTxt_device_name);
			final EditText channelDevice = (EditText) view.findViewById(R.id.edtTxt_channel_device);
			final EditText jgET = (EditText) view.findViewById(R.id.edtTxt_jg_name);
			view.findViewById(R.id.btn_search).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String marketstr = marketidET.getText().toString();
					String linestr = linenameET.getText().toString();
					String devicestr = devicenameET.getText().toString();
					String channelstr = channelDevice.getText().toString();
					String jgstr = jgET.getText().toString();
					search(marketstr, linestr, devicestr, channelstr, jgstr);
				}
			});
			view.findViewById(R.id.close).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					searchDilog.dismissDialog();
				}
			});

			searchDilog.showAlertDialog(view);
		} else {
			searchDilog.showAlertDialog(null);
		}
	}

	/** 刷新显示列表 */
	void refleshDataList() {
		viewHolder.getKindContainer().setVisibility(View.GONE);
		viewHolder.getSearchResultLV().setVisibility(View.VISIBLE);
		viewHolder.getBtnBack().setVisibility(View.VISIBLE);
		if (null == searchAdapter) {
			searchAdapter = new SearchAdapter(mActivity, searchDatas);
			viewHolder.getSearchResultLV().setAdapter(searchAdapter);
		} else {
			searchAdapter.notifyDataSetChanged();
		}
	}

	// 刷新搜索结果
	void refleshSearchResult() {
		viewHolder.getSearchResultContainer().setVisibility(View.VISIBLE);
		viewHolder.getKindContainer().setVisibility(View.VISIBLE);
		viewHolder.getBtnBack().setVisibility(View.GONE);
		// 通道
		if (null == channelList || channelList.isEmpty()) {
			viewHolder.getTdRESTTV().setText("通道：0");
			viewHolder.getTdRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getTdRESTTV().setText("通道: " + channelList.size());
			viewHolder.getTdRESTTV().setVisibility(View.VISIBLE);
		}
		// 路径点
		if (null == marketidList || marketidList.isEmpty()) {
			viewHolder.getBsqRESTTV().setText("标识器：0");
			viewHolder.getBsqRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getBsqRESTTV().setText("标识器：" + marketidList.size());
			viewHolder.getBsqRESTTV().setVisibility(View.VISIBLE);
		}
		// 线路
		if (null == lineList || lineList.isEmpty()) {
			viewHolder.getLineRESTTV().setText("线路：0");
			viewHolder.getLineRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getLineRESTTV().setText("线路：" + lineList.size());
			viewHolder.getLineRESTTV().setVisibility(View.VISIBLE);
		}
		// 路径点设备
		if (null == wellList || wellList.isEmpty()) {
			viewHolder.getWellRESTTV().setText("工井：0");
			viewHolder.getWellRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getWellRESTTV().setText("工井：" + wellList.size());
			viewHolder.getWellRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == towerList || towerList.isEmpty()) {
			viewHolder.getTowerRESTTV().setText("杆塔：0");
			viewHolder.getTowerRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getTowerRESTTV().setText("杆塔：" + towerList.size());
			viewHolder.getTowerRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == stationList || stationList.isEmpty()) {
			viewHolder.getStationRESTTV().setText("变电站：0");
			viewHolder.getStationRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getStationRESTTV().setText("变电站：" + stationList.size());
			viewHolder.getStationRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == transfomerList || transfomerList.isEmpty()) {
			viewHolder.getTransformerRESTTV().setText("变压器：0");
			viewHolder.getTransformerRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getTransformerRESTTV().setText("变压器：" + transfomerList.size());
			viewHolder.getTransformerRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == roomList || roomList.isEmpty()) {
			viewHolder.getRoomRESTTV().setText("配电室：0");
			viewHolder.getRoomRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getRoomRESTTV().setText("配电室：" + roomList.size());
			viewHolder.getRoomRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == dypdxList || dypdxList.isEmpty()) {
			viewHolder.getDypdxRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getDypdxRESTTV().setText("低压配电箱：" + dypdxList.size());
			viewHolder.getDypdxRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == kgzList || kgzList.isEmpty()) {
			viewHolder.getKgzRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getKgzRESTTV().setText("开关站：" + kgzList.size());
			viewHolder.getKgzRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == xsbdzList || xsbdzList.isEmpty()) {
			viewHolder.getXxbdzRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getXxbdzRESTTV().setText("箱式变电站：" + xsbdzList.size());
			viewHolder.getXxbdzRESTTV().setVisibility(View.VISIBLE);
		}
		// 线路附属设备
		if (null == labelList || labelList.isEmpty()) {
			viewHolder.getLableRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getLableRESTTV().setText("电子标签：" + labelList.size());
			viewHolder.getLableRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == midleList || midleList.isEmpty()) {
			viewHolder.getMidleRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getMidleRESTTV().setText("中间接头：" + midleList.size());
			viewHolder.getMidleRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == hwgList || hwgList.isEmpty()) {
			viewHolder.getHwgRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getHwgRESTTV().setText("环网柜：" + hwgList.size());
			viewHolder.getHwgRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == dlfzxList || dlfzxList.isEmpty()) {
			viewHolder.getDlfzxRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getDlfzxRESTTV().setText("电缆分支箱：" + dlfzxList.size());
			viewHolder.getDlfzxRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == dydlfzxList || dydlfzxList.isEmpty()) {
			viewHolder.getDydlfzxRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getDydlfzxRESTTV().setText("低压电缆分支箱：" + dydlfzxList.size());
			viewHolder.getDydlfzxRESTTV().setVisibility(View.VISIBLE);
		}
		if (null == jgList || jgList.isEmpty()) {
			viewHolder.getJgRESTTV().setVisibility(View.GONE);
		} else {
			viewHolder.getJgRESTTV().setText("井盖：" + jgList.size());
			viewHolder.getJgRESTTV().setVisibility(View.VISIBLE);
		}
	}

	// 拷贝
	static void copyList(List<Object> desc, List<?> source) {
		if (null == source || null == desc) {
			return;
		}
		for (Object object : source) {
			desc.add(object);
		}
	}

}
