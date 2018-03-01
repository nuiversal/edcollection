package com.winway.android.edcollection.adding.controll;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.LineInputSelectAdapter;
import com.winway.android.edcollection.adding.adapter.SubstationSelectAdapter;
import com.winway.android.edcollection.adding.bll.AddTempLineBll;
import com.winway.android.edcollection.adding.viewholder.AddTempLineViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.DicNoEntity;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.ewidgets.input.InputSelectComponent.DropDownSelectListener;
import com.winway.android.ewidgets.input.entity.DataItem;
import com.winway.android.util.DateUtils;
import com.winway.android.util.ListUtil;
import com.winway.android.util.ListUtils;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 添加临时线路
 * 
 * @author zgq
 *
 */
public class AddTempLineControll extends BaseControll<AddTempLineViewHolder> {

	private AddTempLineBll addTempLineBll;
	private CommonBll commonBll;
	private List<EcSubstationEntity> substationEntities = null;// 变电站列表
	private GlobalCommonBll globalCommonBll;
	private EcLineEntity ecLineEntity;
	private boolean isEditLine = false;
	private boolean isMainLine = false;//是否主线
	private List<EcLineEntity> lineList = null;
	private LineInputSelectAdapter lineInputSelectAdapter = null;
	private List<DataItem> dataItemList = null;
	private EcLineEntity mainLineEntity = null;//父线
	@Override
	public void init() {
		addTempLineBll = new AddTempLineBll(mActivity, GlobalEntry.prjDbUrl);
		commonBll = new CommonBll(mActivity, GlobalEntry.prjDbUrl);
		globalCommonBll = new GlobalCommonBll(mActivity, GlobalEntry.globalDbUrl);
		initData();
		initEvent();
	}

	private void initEvent() {
		if (ecLineEntity != null) {
			viewHolder.getBtnAddTempLineNo().setEnabled(false);
		}else {
			viewHolder.getBtnAddTempLineNo().setEnabled(true);
		}
		viewHolder.getBtnAddtemplineSave().setOnClickListener(orcl);
		viewHolder.getHcAddtemplineHead().getReturnView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isEditLine = false;
				mActivity.finish();
			}
		});
		// 设置变电站只读
		viewHolder.getIscSubstation().getEditTextView().setFocusable(false);
		viewHolder.getBtnAddTempLineNo().setOnClickListener(orcl);
		viewHolder.getCbAddtemplineNo().setOnClickListener(orcl);
		viewHolder.getCbAdtempLineYes().setOnClickListener(orcl);
		
		
		
	}

	private void initData() {
		viewHolder.getIcAddtemplineCableLength().setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
		viewHolder.getIscSubstation().setDropDownSelectListener(new DownSelectListener());
		viewHolder.getIcAddtemplineSelectline().setDropDownSelectListener(new SelectLineDropDownSelectListener());
		// 初始化变电站下拉框
		substationEntities = addTempLineBll.getSubstationListByOrgId(GlobalEntry.currentProject.getOrgId());
		if (substationEntities != null && substationEntities.size() > 0) {
			SubstationSelectAdapter substationSelectAdapter = new SubstationSelectAdapter(mActivity,
					substationEntities);
			viewHolder.getIscSubstation().setAdapter(substationSelectAdapter);
		}
		viewHolder.getIcAddtemplineSelectline().setSearchResultListener(new SearchResultListener());
		// 初始化线路下拉框
		if(substationEntities != null && substationEntities.size()>0){
			EcSubstationEntity ecSubstationEntity = substationEntities.get(viewHolder.getIscSubstation().getSelectedItemPosition());
			lineList =  commonBll.searchLine(ecSubstationEntity.getStationNo());
			if(lineList!=null && lineList.size()>0){
				lineInputSelectAdapter = new LineInputSelectAdapter(mActivity, lineList);
				viewHolder.getIcAddtemplineSelectline().setAdapter(lineInputSelectAdapter);
			}else {
				viewHolder.getCbAddtemplineNo().setEnabled(false);
				viewHolder.getCbAdtempLineYes().setEnabled(false);
			}
			//关键字搜索需要的线路数据
			dataItemList = commonBll.searchLineTransformationDataItems((substationEntities.get(viewHolder.getIscSubstation().getSelectedItemPosition()).getStationNo()));
			viewHolder.getIcAddtemplineSelectline().setDataItemList(dataItemList);
		}
		// 初始电压等级下拉列表
		String dydjTypeNo = DicNoEntity.dydjTypeNo;// 电压等级
		InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
				globalCommonBll.getDictionaryNameList(dydjTypeNo));
		viewHolder.getIcAddtemplineLevel().setAdapter(dydjAdapter);
		// 输入框类型控制
		viewHolder.getIcAddtemplineLevel().setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
		viewHolder.getHcAddtemplineHead().setTitle("新增临时线路");
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			ecLineEntity = (EcLineEntity)intent.getExtras().getSerializable("ecLineEntity");
		}
		if (ecLineEntity != null) {
			initEditData();
			viewHolder.getHcAddtemplineHead().setTitle("编辑临时线路");
		}
	}

	private void initEditData() {
		viewHolder.getIcAddtemplineNo().getEditTextView().setEnabled(false);
		viewHolder.getIcAddtemplineLevel().setEdtTextValue(ecLineEntity.getVoltage());
		viewHolder.getIcAddtemplineNo().setEdtText(ecLineEntity.getLineNo());
		viewHolder.getIcAddtemplineName().setEdtText(ecLineEntity.getName());
		viewHolder.getIscSubstation().setEdtTextValue(addTempLineBll.getSubName(ecLineEntity.getStartStation()));
		viewHolder.getIcAddtemplineCableLength().setEdtText(StringUtils.nullStrToEmpty(ecLineEntity.getMeasuredLength()));
		viewHolder.getIcAddtemplineCableModel().setEdtText(ecLineEntity.getWireMaterial());
		viewHolder.getIcAddtemplineNo().setShowClearIcon(false);
		String parentNo = ecLineEntity.getParentNo();
		if(parentNo!=null){
			viewHolder.getCbAddtemplineNo().setChecked(true);
			viewHolder.getIcAddtemplineSelectline().setVisibility(View.VISIBLE);
			mainLineEntity = addTempLineBll.findById(EcLineEntity.class, parentNo);
			viewHolder.getIcAddtemplineSelectline().setEdtTextValue(mainLineEntity.getName());
			if(viewHolder.getCbAdtempLineYes().isChecked()){
				viewHolder.getCbAdtempLineYes().setChecked(false);
			}
			isMainLine = true;//不是主线
		}else {
			viewHolder.getCbAdtempLineYes().setChecked(true);
			viewHolder.getIcAddtemplineSelectline().setVisibility(View.GONE);
			isMainLine = false;
		}
		isEditLine = true;
		
	}

	
	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_addtempline_save: {
				save();
				break;
			}
			case R.id.btn_add_temp_line_no:
				viewHolder.getIcAddtemplineNo().setEdtText(UUIDGen.randomUUID());
				break;
			case R.id.cb_addtempline_no:{// 否  子线
				boolean isChecked = viewHolder.getCbAddtemplineNo().isChecked();
				if (isChecked) {
					isMainLine = true;
					if(substationEntities != null && substationEntities.size()>0){
						EcSubstationEntity ecSubstationEntity = substationEntities.get(viewHolder.getIscSubstation().getSelectedItemPosition());
						lineList =  commonBll.searchLine(ecSubstationEntity.getStationNo());
						lineInputSelectAdapter = new LineInputSelectAdapter(mActivity, lineList);
						viewHolder.getIcAddtemplineSelectline().setAdapter(lineInputSelectAdapter);
						mainLineEntity=lineList.get(viewHolder.getIcAddtemplineSelectline().getSelectedItemPosition());
						String subName = addTempLineBll.getSubName(mainLineEntity.getStartStation());
						viewHolder.getIscSubstation().setEdtTextValue(subName);
					}
					viewHolder.getCbAdtempLineYes().setChecked(false);
					viewHolder.getIcAddtemplineSelectline().setVisibility(View.VISIBLE);
					
				}else {
					isMainLine = false;
					viewHolder.getCbAdtempLineYes().setChecked(true);
					viewHolder.getIcAddtemplineSelectline().setVisibility(View.GONE);
				}
				break;
			}
			case R.id.cb_addtempline_yes:{//是 主线
				boolean isChecked = viewHolder.getCbAdtempLineYes().isChecked();
				if(isChecked){
					isMainLine = false;
					viewHolder.getCbAddtemplineNo().setChecked(false);
					viewHolder.getIcAddtemplineSelectline().setVisibility(View.GONE);
				}else {
					isMainLine = true;
					viewHolder.getCbAddtemplineNo().setChecked(true);
					viewHolder.getIcAddtemplineSelectline().setVisibility(View.VISIBLE);
				}
				
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * 保存临时线路
	 */
	private void save() {
		boolean bRes = false;
		if (viewHolder.getIcAddtemplineName().getEdtTextValue().isEmpty()) {
			viewHolder.getIcAddtemplineName().getEditTextView().setFocusable(true);
			ToastUtil.show(mActivity, "线路名称不能为空", 200);
			return;
		}
		if (viewHolder.getIcAddtemplineNo().getEdtTextValue().isEmpty()) {
			viewHolder.getIcAddtemplineNo().getEditTextView().setFocusable(true);
			ToastUtil.show(mActivity, "线路编号不能为空", 200);
			return;
		}
		if (viewHolder.getIcAddtemplineLevel().getEdtTextValue().isEmpty()) {
			viewHolder.getIcAddtemplineLevel().getEditTextView().setFocusable(true);
			ToastUtil.show(mActivity, "电压等级不能为空", 200);
			return;
		}
		if(isEditLine){
			if(isMainLine){//不是主线
				if(mainLineEntity.getEcLineId().equals(ecLineEntity.getEcLineId())){
					ToastUtil.show(mActivity, "不能选择自己为主线", 200);
					return;
				}
			}
		}
		if (ecLineEntity == null) {
			boolean isExistName = addTempLineBll.isExistLineName(viewHolder.getIcAddtemplineName().getEdtTextValue());
			if (isExistName) {
				ToastUtil.show(mActivity, "已经存在该名称的线路", 200);
				viewHolder.getIcAddtemplineName().getEditTextView().setFocusable(true);
				viewHolder.getIcAddtemplineName().setEdtText("");
				return;
			}
			boolean isExistNo = addTempLineBll.isExistLineNo(viewHolder.getIcAddtemplineNo().getEdtTextValue());
			if (isExistNo) {
				ToastUtil.show(mActivity, "已经存在该编号的线路", 200);
				viewHolder.getIcAddtemplineNo().getEditTextView().setFocusable(true);
				viewHolder.getIcAddtemplineNo().setEdtText("");
				return;
			}
		    ecLineEntity = new EcLineEntity();
			ecLineEntity.setEcLineId(UUIDGen.randomUUID());
			ecLineEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecLineEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecLineEntity.setCreateTime(DateUtils.getDate());
		}
		if(isMainLine){//子线
			if(mainLineEntity!=null){
				ecLineEntity.setParentNo(mainLineEntity.getEcLineId());
			}
		}else {//主线，将parrntNo赋为null  方便工程人员操作，目前只支持二级线路
			ecLineEntity.setParentNo(null);
		}
		String cableLength = viewHolder.getIcAddtemplineCableLength().getEdtTextValue();
		if(!cableLength.isEmpty()){
			ecLineEntity.setMeasuredLength(Double.parseDouble(cableLength));
		}
		String cableModel = viewHolder.getIcAddtemplineCableModel().getEdtTextValue();
		if(!cableModel.isEmpty()){
			ecLineEntity.setWireMaterial(cableModel);
		}
		ecLineEntity.setUpdateTime(DateUtils.getDate());
		ecLineEntity.setLineNo(viewHolder.getIcAddtemplineNo().getEdtTextValue());
		ecLineEntity.setName(viewHolder.getIcAddtemplineName().getEdtTextValue());
		ecLineEntity.setVoltage(viewHolder.getIcAddtemplineLevel().getEdtTextValue());
		/*if (substationEntities != null && substationEntities.size() > 0) {
			EcSubstationEntity ecSubstationEntity = substationEntities
					.get(viewHolder.getIscSubstation().getSelectedItemPosition());
			ecLineEntity.setStartStation(ecSubstationEntity.getStationNo());
		}*/
		String subStationName = viewHolder.getIscSubstation().getEdtTextValue();
		EcSubstationEntity substationEntity = addTempLineBll.getSubstationByName(subStationName);
		if(substationEntity !=null){
			ecLineEntity.setStartStation(substationEntity.getStationNo());
		}
		bRes = addTempLineBll.saveLine(ecLineEntity);
		if(isEditLine){
			commonBll.handleDataLog(ecLineEntity.getEcLineId(), TableNameEnum.SDXL.getTableName(), DataLogOperatorType.update,
					WhetherEnum.NO, "编辑临时线路：" + ecLineEntity.getName(),true);
			for(int i=0;i<SelectLineControll.selectedDatas.size();i++){
				if(SelectLineControll.selectedDatas.get(i).getEcLineId().equals(ecLineEntity.getEcLineId())){
					SelectLineControll.selectedDatas.remove(i);
					SelectLineControll.selectedDatas.add(i, ecLineEntity);
				}
			}
			isEditLine = false;
		}else {
			commonBll.handleDataLog(ecLineEntity.getEcLineId(), TableNameEnum.SDXL.getTableName(), DataLogOperatorType.Add,
					WhetherEnum.NO, "新增临时线路：" + ecLineEntity.getName(),true);
		}
		mActivity.setResult(Activity.RESULT_OK);
		mActivity.finish();
		if (bRes) {
			ToastUtil.show(mActivity, "保存成功", 200);
		} else {
			ToastUtil.show(mActivity, "保存失败", 200);
		}
	}
	//选择变电站回调
	private class DownSelectListener implements DropDownSelectListener{

		@Override
		public void handle(int index) {
			if(substationEntities !=null && substationEntities.size()>0){
				EcSubstationEntity ecSubstationEntity = substationEntities.get(index);
				List<EcLineEntity> list = commonBll.searchLine(ecSubstationEntity.getStationNo());
				lineList.clear();
				lineList.addAll(list);
				
				// TODO  新增和编辑两种状态  新增有一条线路是可以增加子线，编辑状态下只有一条线路是不可以增加子线的
					if(null!= lineList && lineList.size()>0){
						lineInputSelectAdapter.notifyDataSetChanged();
						viewHolder.getIcAddtemplineSelectline().setEdtTextValue(lineList.get(0).getName());
						if(isEditLine){//编辑状态不允许选择自己为主线
							if(lineList.get(0).getEcLineId().equals(ecLineEntity.getEcLineId())){
								isMainLine = false;
								viewHolder.getCbAdtempLineYes().setChecked(true);
								viewHolder.getCbAddtemplineNo().setChecked(false);
								viewHolder.getIcAddtemplineSelectline().setVisibility(View.GONE);
								return;
							}
						}
						if(viewHolder.getCbAdtempLineYes().isChecked()){
							isMainLine = false;
							viewHolder.getIcAddtemplineSelectline().setVisibility(View.GONE);
						}
						viewHolder.getCbAddtemplineNo().setEnabled(true);
						viewHolder.getCbAdtempLineYes().setEnabled(true);
					}else {
						ToastUtil.show(mActivity, "此变电站下没有线路", 200);
						isMainLine = false;
						viewHolder.getCbAdtempLineYes().setChecked(true);
						viewHolder.getCbAddtemplineNo().setChecked(false);
						viewHolder.getCbAddtemplineNo().setEnabled(false);
						viewHolder.getCbAdtempLineYes().setEnabled(false);
						viewHolder.getIcAddtemplineSelectline().setVisibility(View.GONE);
					}
				List<DataItem> itemList = commonBll.searchLineTransformationDataItems(ecSubstationEntity.getStationNo());
				
				if(null != itemList && !itemList.isEmpty()){
					dataItemList.clear();
					dataItemList.addAll(itemList);
					viewHolder.getIcAddtemplineSelectline().setDataItemList(dataItemList);
				}
			}
		}
	}
	//关键字查询回调
	private class SearchResultListener implements com.winway.android.ewidgets.input.InputSelectComponent.SearchResultListener{

		@Override
		public void searchResult(DataItem dataItem) {
			mainLineEntity = (EcLineEntity) dataItem.getBusiObj();
		}
	}
	
	// 选择主线回调
	private class SelectLineDropDownSelectListener implements DropDownSelectListener{

		@Override
		public void handle(int index) {
			mainLineEntity=lineList.get(index);
			String subName = addTempLineBll.getSubName(mainLineEntity.getStartStation());
			viewHolder.getIscSubstation().setEdtTextValue(subName);
			//不能选择自身这条线为主线
			if(mainLineEntity.getEcLineId().equals(ecLineEntity.getEcLineId())){
				isMainLine = false;
				viewHolder.getCbAdtempLineYes().setChecked(true);
				viewHolder.getCbAddtemplineNo().setChecked(false);
				viewHolder.getIcAddtemplineSelectline().setVisibility(View.GONE);
			}
		}}
}
