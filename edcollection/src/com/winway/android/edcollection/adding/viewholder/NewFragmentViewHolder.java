package com.winway.android.edcollection.adding.viewholder;

import com.ant.liao.GifView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;
import com.winway.android.ewidgets.radar.SearchDevicesView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import ocn.himap.maps.HiBaseMaps;

/**
 * 新增ui初始化
 * 
 * @author zgq
 *
 */
public class NewFragmentViewHolder extends BaseViewHolder {
	@ViewInject(R.id.tv_a_title_line)
	private TextView tvTitleLine;
	@ViewInject(com.winway.android.map.R.id.imgBtn_map_zoomIn)
	private ImageButton zoomIn;// 放大
	@ViewInject(com.winway.android.map.R.id.imgBtn_map_zoomOut)
	private ImageButton zoomOut;// 缩小
	@ViewInject(com.winway.android.map.R.id.imgBtn_map_mapSwitch)
	private ImageButton mapSwitch;// 地图切换
	@ViewInject(com.winway.android.map.R.id.imgBtn_map_measure)
	private ImageButton mapMeasure;// 地图量算
	@ViewInject(com.winway.android.map.R.id.tv_map_measureResult)
	private TextView tvMeasureResult;// 量算结果
	@ViewInject(com.winway.android.map.R.id.imgBtn_map_location)
	private ImageButton location;// 地图定位
	@ViewInject(R.id.map_container)
	private HiBaseMaps map;
	@ViewInject(R.id.imgBtn_fragment_new_way)
	private ImageButton btnShowWay;// 标识器状态
	@ViewInject(R.id.imgBtn_fragment_new_zbsb)
	private ImageButton btnZbsb;// 查找周边
	@ViewInject(R.id.search_member_view_radar)
	private SearchDevicesView viewRadar;// 雷达

	@ViewInject(R.id.gifView_fragment_new_rtk)
	private GifView gifViewRtk;// rtk打开

	@ViewInject(R.id.imgBtn_fragment_new_rtk)
	private ImageButton imgBtnRtk;// rtk关闭

	
	// 数据中转ui
	@ViewInject(R.id.include_main_item_transfer)
	private View includeMainItemTransfer;
	@ViewInject(R.id.btn_main_item_transfer_ok)
	private Button btnMainItemTransferOk;
	@ViewInject(R.id.btn_main_item_transfer_cancel)
	private Button btnMainItemTransferCancel;

	@ViewInject(R.id.imgBtn_fragment_lableReadQuery)
	private ImageButton btnLableReadQuery;
	
	// 台账树点击按键
	@ViewInject(R.id.imgBtn_fragment_new_tree)
	private ImageButton btnTree;
	// 隐藏或者显示台账树
	@ViewInject(R.id.layout_tree_compoment)
	private View HSTreeCompoment;
	// 台账树存放容器
	@ViewInject(R.id.tree_container)
	private LinearLayout treeContainer;

	// 搜索按钮
	@ViewInject(R.id.imgBtn_fragment_new_search)
	private ImageButton btnSearch;
	@ViewInject(R.id.search_result_container)
	private LinearLayout searchResultContainer;
	@ViewInject(R.id.listview_search_result_data)
	private ListView searchResultLV;

	@ViewInject(R.id.search_result_bsq)
	private TextView bsqRESTTV;
	@ViewInject(R.id.search_result_td)
	private TextView tdRESTTV;
	@ViewInject(R.id.search_result_line)
	private TextView lineRESTTV;
	@ViewInject(R.id.search_result_well)
	private TextView wellRESTTV;
	@ViewInject(R.id.search_result_tower)
	private TextView towerRESTTV;
	@ViewInject(R.id.search_result_room)
	private TextView roomRESTTV;
	@ViewInject(R.id.search_result_transformer)
	private TextView transformerRESTTV;
	@ViewInject(R.id.search_result_station)
	private TextView stationRESTTV;
	@ViewInject(R.id.search_result_xsbdz)
	private TextView xxbdzRESTTV;
	@ViewInject(R.id.search_result_dypdx)
	private TextView dypdxRESTTV;
	@ViewInject(R.id.search_result_kgz)
	private TextView kgzRESTTV;
	@ViewInject(R.id.search_result_lable)
	private TextView lableRESTTV;
	@ViewInject(R.id.search_result_midle)
	private TextView midleRESTTV;
	@ViewInject(R.id.search_result_hwg)
	private TextView hwgRESTTV;
	@ViewInject(R.id.search_result_dlfzx)
	private TextView dlfzxRESTTV;
	@ViewInject(R.id.search_result_dydlfzx)
	private TextView dydlfzxRESTTV;
	@ViewInject(R.id.search_result_jg)
	private TextView jgRESTTV;
	@ViewInject(R.id.search_result_back)
	private TextView btnBack;
	@ViewInject(R.id.search_kind_result_container)
	private View kindContainer;
	@ViewInject(R.id.search_result_more_down)
	private View moredown;
	@ViewInject(R.id.search_result_more_up)
	private View moreup;
	@ViewInject(R.id.search_result_container_close)
	private View searchClose;

	@ViewInject(R.id.iv_a_title_fragment_new_prjName)
	private TextView tvPrjName;// 工程名称

	public TextView getTvPrjName() {
		return tvPrjName;
	}

	public void setTvPrjName(TextView tvPrjName) {
		this.tvPrjName = tvPrjName;
	}

	public TextView getJgRESTTV() {
		return jgRESTTV;
	}

	public void setJgRESTTV(TextView jgRESTTV) {
		this.jgRESTTV = jgRESTTV;
	}

	public TextView getTdRESTTV() {
		return tdRESTTV;
	}

	public void setTdRESTTV(TextView tdRESTTV) {
		this.tdRESTTV = tdRESTTV;
	}

	public ImageButton getImgBtnRtk() {
		return imgBtnRtk;
	}

	public void setImgBtnRtk(ImageButton imgBtnRtk) {
		this.imgBtnRtk = imgBtnRtk;
	}

	public GifView getGifViewRtk() {
		return gifViewRtk;
	}

	public void setGifViewRtk(GifView gifViewRtk) {
		this.gifViewRtk = gifViewRtk;
	}

	public ImageButton getBtnTree() {
		return btnTree;
	}

	public void setBtnTree(ImageButton btnTree) {
		this.btnTree = btnTree;
	}

	public View getHSTreeCompoment() {
		return HSTreeCompoment;
	}

	public void setHSTreeCompoment(View hSTreeCompoment) {
		HSTreeCompoment = hSTreeCompoment;
	}

	public LinearLayout getTreeContainer() {
		return treeContainer;
	}

	public void setTreeContainer(LinearLayout treeContainer) {
		this.treeContainer = treeContainer;
	}

	public View getIncludeMainItemTransfer() {
		return includeMainItemTransfer;
	}

	public void setIncludeMainItemTransfer(View includeMainItemTransfer) {
		this.includeMainItemTransfer = includeMainItemTransfer;
	}

	public Button getBtnMainItemTransferOk() {
		return btnMainItemTransferOk;
	}

	public void setBtnMainItemTransferOk(Button btnMainItemTransferOk) {
		this.btnMainItemTransferOk = btnMainItemTransferOk;
	}

	public Button getBtnMainItemTransferCancel() {
		return btnMainItemTransferCancel;
	}

	public void setBtnMainItemTransferCancel(Button btnMainItemTransferCancel) {
		this.btnMainItemTransferCancel = btnMainItemTransferCancel;
	}

	public ImageButton getLocation() {
		return location;
	}

	public void setLocation(ImageButton location) {
		this.location = location;
	}

	public HiBaseMaps getMap() {
		return map;
	}

	public void setMap(HiBaseMaps map) {
		this.map = map;
	}

	public ImageButton getZoomIn() {
		return zoomIn;
	}

	public void setZoomIn(ImageButton zoomIn) {
		this.zoomIn = zoomIn;
	}

	public ImageButton getZoomOut() {
		return zoomOut;
	}

	public void setZoomOut(ImageButton zoomOut) {
		this.zoomOut = zoomOut;
	}

	public ImageButton getMapSwitch() {
		return mapSwitch;
	}

	public void setMapSwitch(ImageButton mapSwitch) {
		this.mapSwitch = mapSwitch;
	}

	public ImageButton getMapMeasure() {
		return mapMeasure;
	}

	public void setMapMeasure(ImageButton mapMeasure) {
		this.mapMeasure = mapMeasure;
	}

	public TextView getTvMeasureResult() {
		return tvMeasureResult;
	}

	public void setTvMeasureResult(TextView tvMeasureResult) {
		this.tvMeasureResult = tvMeasureResult;
	}

	public ImageButton getBtnShowWay() {
		return btnShowWay;
	}

	public void setBtnShowWay(ImageButton btnShowWay) {
		this.btnShowWay = btnShowWay;
	}

	public ImageButton getBtnZbsb() {
		return btnZbsb;
	}

	public void setBtnZbsb(ImageButton btnZbsb) {
		this.btnZbsb = btnZbsb;
	}

	public SearchDevicesView getViewRadar() {
		return viewRadar;
	}

	public void setViewRadar(SearchDevicesView viewRadar) {
		this.viewRadar = viewRadar;
	}

	public TextView getTvTitleLine() {
		return tvTitleLine;
	}

	public void setTvTitleLine(TextView tvTitleLine) {
		this.tvTitleLine = tvTitleLine;
	}

	public ImageButton getBtnSearch() {
		return btnSearch;
	}

	public void setBtnSearch(ImageButton btnSearch) {
		this.btnSearch = btnSearch;
	}

	public LinearLayout getSearchResultContainer() {
		return searchResultContainer;
	}

	public void setSearchResultContainer(LinearLayout searchResultContainer) {
		this.searchResultContainer = searchResultContainer;
	}

	public ListView getSearchResultLV() {
		return searchResultLV;
	}

	public void setSearchResultLV(ListView searchResultLV) {
		this.searchResultLV = searchResultLV;
	}

	public TextView getBsqRESTTV() {
		return bsqRESTTV;
	}

	public void setBsqRESTTV(TextView bsqRESTTV) {
		this.bsqRESTTV = bsqRESTTV;
	}

	public TextView getLineRESTTV() {
		return lineRESTTV;
	}

	public void setLineRESTTV(TextView lineRESTTV) {
		this.lineRESTTV = lineRESTTV;
	}

	public TextView getWellRESTTV() {
		return wellRESTTV;
	}

	public void setWellRESTTV(TextView wellRESTTV) {
		this.wellRESTTV = wellRESTTV;
	}

	public TextView getTowerRESTTV() {
		return towerRESTTV;
	}

	public void setTowerRESTTV(TextView towerRESTTV) {
		this.towerRESTTV = towerRESTTV;
	}

	public TextView getRoomRESTTV() {
		return roomRESTTV;
	}

	public void setRoomRESTTV(TextView roomRESTTV) {
		this.roomRESTTV = roomRESTTV;
	}

	public TextView getTransformerRESTTV() {
		return transformerRESTTV;
	}

	public void setTransformerRESTTV(TextView transformerRESTTV) {
		this.transformerRESTTV = transformerRESTTV;
	}

	public TextView getStationRESTTV() {
		return stationRESTTV;
	}

	public void setStationRESTTV(TextView stationRESTTV) {
		this.stationRESTTV = stationRESTTV;
	}

	public TextView getBtnBack() {
		return btnBack;
	}

	public void setBtnBack(TextView btnBack) {
		this.btnBack = btnBack;
	}

	public View getKindContainer() {
		return kindContainer;
	}

	public void setKindContainer(View kindContainer) {
		this.kindContainer = kindContainer;
	}

	public View getMoredown() {
		return moredown;
	}

	public void setMoredown(View moredown) {
		this.moredown = moredown;
	}

	public View getMoreup() {
		return moreup;
	}

	public void setMoreup(View moreup) {
		this.moreup = moreup;
	}

	public View getSearchClose() {
		return searchClose;
	}

	public void setSearchClose(View searchClose) {
		this.searchClose = searchClose;
	}

	public TextView getXxbdzRESTTV() {
		return xxbdzRESTTV;
	}

	public void setXxbdzRESTTV(TextView xxbdzRESTTV) {
		this.xxbdzRESTTV = xxbdzRESTTV;
	}

	public TextView getDypdxRESTTV() {
		return dypdxRESTTV;
	}

	public void setDypdxRESTTV(TextView dypdxRESTTV) {
		this.dypdxRESTTV = dypdxRESTTV;
	}

	public TextView getKgzRESTTV() {
		return kgzRESTTV;
	}

	public void setKgzRESTTV(TextView kgzRESTTV) {
		this.kgzRESTTV = kgzRESTTV;
	}

	public TextView getLableRESTTV() {
		return lableRESTTV;
	}

	public void setLableRESTTV(TextView lableRESTTV) {
		this.lableRESTTV = lableRESTTV;
	}

	public TextView getMidleRESTTV() {
		return midleRESTTV;
	}

	public void setMidleRESTTV(TextView midleRESTTV) {
		this.midleRESTTV = midleRESTTV;
	}

	public TextView getHwgRESTTV() {
		return hwgRESTTV;
	}

	public void setHwgRESTTV(TextView hwgRESTTV) {
		this.hwgRESTTV = hwgRESTTV;
	}

	public TextView getDlfzxRESTTV() {
		return dlfzxRESTTV;
	}

	public void setDlfzxRESTTV(TextView dlfzxRESTTV) {
		this.dlfzxRESTTV = dlfzxRESTTV;
	}

	public TextView getDydlfzxRESTTV() {
		return dydlfzxRESTTV;
	}

	public void setDydlfzxRESTTV(TextView dydlfzxRESTTV) {
		this.dydlfzxRESTTV = dydlfzxRESTTV;
	}

	public ImageButton getBtnLableReadQuery() {
		return btnLableReadQuery;
	}

	public void setBtnLableReadQuery(ImageButton btnLableReadQuery) {
		this.btnLableReadQuery = btnLableReadQuery;
	}
	
	

}
